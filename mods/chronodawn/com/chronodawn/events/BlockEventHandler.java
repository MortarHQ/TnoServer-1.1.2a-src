package com.chronodawn.events;

import com.chronodawn.ChronoDawn;
import com.chronodawn.blocks.BossRoomDoorBlockEntity;
import com.chronodawn.compat.ItemDurabilityHandler;
import com.chronodawn.core.portal.PortalRegistry;
import com.chronodawn.core.portal.PortalStateMachine;
import com.chronodawn.data.ChronoDawnGlobalState;
import com.chronodawn.items.tools.SpatiallyLinkedPickaxeItem;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModDimensions;
import com.chronodawn.registry.ModItems;
import com.chronodawn.worldgen.spawning.ChronosWardenSpawner;
import com.chronodawn.worldgen.spawning.MasterClockBossRoomPlacer;
import com.chronodawn.worldgen.spawning.TimeTyrantSpawner;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.BlockEvent.Break;
import dev.architectury.event.events.common.InteractionEvent.RightClickBlock;
import dev.architectury.event.events.common.TickEvent.Server;
import dev.architectury.event.events.common.TickEvent.ServerLevelTick;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class BlockEventHandler {
   private static final Map<BlockPos, BlockEventHandler.RestorationData> pendingRestorations = new ConcurrentHashMap<>();

   public static void register() {
      BlockEvent.BREAK.register((Break)(level, pos, state, player, xp) -> {
         if (state.is((Block)ModBlocks.REVERSING_TIME_SANDSTONE.get())) {
            handleReversingTimeSandstoneBreak(level, pos, state);
         }

         if (player != null && !level.isClientSide()) {
            handleSpatiallyLinkedPickaxeDropDoubling(level, pos, state, player);
         }

         return EventResult.pass();
      });
      TickEvent.SERVER_POST.register((Server)server -> {
         ChronoDawnGlobalState globalState = ChronoDawnGlobalState.get(server);
         if (globalState.arePortalsUnstable()) {
            ServerLevel chronodawnLevel = server.getLevel(ModDimensions.CHRONO_DAWN_DIMENSION);
            if (chronodawnLevel != null) {
               for (PortalStateMachine portal : PortalRegistry.getInstance().getAllPortals()) {
                  if (portal.getSourceDimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
                     BlockPos portalPos = portal.getPosition();
                     boolean foundAndRemovedBlocks = false;

                     for (int x = -10; x <= 10; x++) {
                        for (int y = -10; y <= 10; y++) {
                           for (int z = -10; z <= 10; z++) {
                              BlockPos checkPos = portalPos.offset(x, y, z);
                              BlockState state = chronodawnLevel.getBlockState(checkPos);
                              if (state.is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get())) {
                                 chronodawnLevel.removeBlock(checkPos, false);
                                 foundAndRemovedBlocks = true;
                              }
                           }
                        }
                     }

                     if (foundAndRemovedBlocks) {
                        ChronoDawn.LOGGER.debug("Removed unstable portal blocks near {}", portalPos);
                     }
                  }
               }
            }
         }
      });
      TickEvent.SERVER_LEVEL_POST.register((ServerLevelTick)level -> processRestorationTimers(level));
      InteractionEvent.RIGHT_CLICK_BLOCK.register((RightClickBlock)(player, hand, pos, face) -> {
         if (!player.level().isClientSide() && hand == InteractionHand.MAIN_HAND) {
            ItemStack heldItem = player.getItemInHand(hand);
            if (!(heldItem.getItem() instanceof AxeItem)) {
               return EventResult.pass();
            }

            BlockState state = player.level().getBlockState(pos);
            BlockState strippedState = getStrippedState(state);
            if (strippedState != null) {
               player.level().playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
               player.level().setBlock(pos, (BlockState)strippedState.setValue(RotatedPillarBlock.AXIS, (Axis)state.getValue(RotatedPillarBlock.AXIS)), 11);
               if (!player.isCreative()) {
                  EquipmentSlot slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                  ItemDurabilityHandler.getInstance().damageItem(heldItem, 1, player, slot);
               }

               return EventResult.interruptTrue();
            } else {
               return EventResult.pass();
            }
         } else {
            return EventResult.pass();
         }
      });
      InteractionEvent.RIGHT_CLICK_BLOCK
         .register(
            (RightClickBlock)(player, hand, pos, face) -> {
               if (!player.level().isClientSide() && hand == InteractionHand.MAIN_HAND) {
                  BlockState state = player.level().getBlockState(pos);
                  if (!state.is((Block)ModBlocks.BOSS_ROOM_DOOR.get())) {
                     return EventResult.pass();
                  }

                  String doorType = getDoorType(player.level(), pos);
                  boolean isBossRoomDoor = "boss_room".equals(doorType);
                  boolean isGuardianVaultDoor = "guardian_vault".equals(doorType);
                  boolean canUnlock;
                  Component message;
                  if (isBossRoomDoor) {
                     canUnlock = hasRequiredAncientGears(player) && hasKeyToMasterClock(player);
                     message = canUnlock
                        ? Component.translatable("message.chronodawn.boss_room_unlocked")
                        : Component.translatable("message.chronodawn.boss_room_locked");
                  } else if (isGuardianVaultDoor) {
                     canUnlock = true;
                     message = Component.translatable("message.chronodawn.guardian_vault_opened");
                  } else {
                     canUnlock = hasKeyToMasterClock(player);
                     message = canUnlock
                        ? Component.translatable("message.chronodawn.master_clock_unlocked")
                        : Component.translatable("message.chronodawn.master_clock_locked");
                  }

                  if (canUnlock) {
                     boolean isOpen = (Boolean)state.getValue(DoorBlock.OPEN);
                     boolean wasOpen = isOpen;
                     BlockState newState = (BlockState)state.setValue(DoorBlock.OPEN, !isOpen);
                     DoubleBlockHalf half = (DoubleBlockHalf)state.getValue(DoorBlock.HALF);
                     BlockPos doorPosToUse;
                     if (half == DoubleBlockHalf.LOWER) {
                        player.level().setBlock(pos, newState, 3);
                        doorPosToUse = pos;
                        BlockPos upperPos = pos.above();
                        BlockState upperState = player.level().getBlockState(upperPos);
                        if (upperState.getBlock() == ModBlocks.BOSS_ROOM_DOOR.get()) {
                           player.level().setBlock(upperPos, (BlockState)upperState.setValue(DoorBlock.OPEN, !isOpen), 3);
                        }
                     } else {
                        player.level().setBlock(pos, newState, 3);
                        BlockPos lowerPos = pos.below();
                        doorPosToUse = lowerPos;
                        BlockState lowerState = player.level().getBlockState(lowerPos);
                        if (lowerState.getBlock() == ModBlocks.BOSS_ROOM_DOOR.get()) {
                           player.level().setBlock(lowerPos, (BlockState)lowerState.setValue(DoorBlock.OPEN, !isOpen), 3);
                        }
                     }

                     player.level().levelEvent(player, isOpen ? 1011 : 1005, pos, 0);
                     if (!isOpen) {
                        player.displayClientMessage(message, true);
                     }

                     if (!isBossRoomDoor && !isGuardianVaultDoor && !wasOpen && player.level() instanceof ServerLevel serverLevel) {
                        ChronoDawn.LOGGER.debug("Entrance door opened at {} - triggering Master Clock boss room placement", doorPosToUse);
                        MasterClockBossRoomPlacer.triggerBossRoomPlacementAtDoor(serverLevel, doorPosToUse);
                     }

                     if (isBossRoomDoor && !wasOpen && player.level() instanceof ServerLevel serverLevel) {
                        ChronoDawn.LOGGER.debug("Boss room door opened at {} - spawning Time Tyrant", doorPosToUse);
                        BlockState openedState = player.level().getBlockState(doorPosToUse);
                        TimeTyrantSpawner.spawnOnDoorOpen(serverLevel, doorPosToUse, openedState);
                     }

                     if (isGuardianVaultDoor && !wasOpen && player.level() instanceof ServerLevel serverLevel) {
                        ChronoDawn.LOGGER.debug("Guardian Vault door opened at {} - spawning Chronos Warden", doorPosToUse);
                        BlockState openedState = player.level().getBlockState(doorPosToUse);
                        ChronosWardenSpawner.spawnOnDoorOpen(serverLevel, doorPosToUse, openedState);
                     }

                     return EventResult.interruptTrue();
                  } else {
                     player.displayClientMessage(message, true);
                     return EventResult.interruptFalse();
                  }
               } else {
                  return EventResult.pass();
               }
            }
         );
      ChronoDawn.LOGGER.debug("Registered BlockEventHandler with Reversing Time Sandstone restoration and Time Hourglass control");
   }

   private static void handleReversingTimeSandstoneBreak(Level level, BlockPos pos, BlockState state) {
      if (!level.isClientSide()) {
         ServerLevel serverLevel = (ServerLevel)level;
         BlockPos immutablePos = pos.immutable();
         pendingRestorations.put(immutablePos, new BlockEventHandler.RestorationData(serverLevel, state));
         ChronoDawn.LOGGER.debug("Scheduled Reversing Time Sandstone restoration at {} in 3 seconds", immutablePos);
      }
   }

   private static void processRestorationTimers(ServerLevel level) {
      if (!pendingRestorations.isEmpty()) {
         HashMap<BlockPos, BlockEventHandler.RestorationData> toRestore = new HashMap<>();
         pendingRestorations.entrySet().removeIf(entryx -> {
            BlockPos pos = (BlockPos)entryx.getKey();
            BlockEventHandler.RestorationData data = (BlockEventHandler.RestorationData)entryx.getValue();
            data.ticksRemaining--;
            if (data.ticksRemaining <= 0) {
               toRestore.put(pos, data);
               return true;
            } else {
               return false;
            }
         });

         for (Entry<BlockPos, BlockEventHandler.RestorationData> entry : toRestore.entrySet()) {
            restoreBlock(entry.getValue().level, entry.getKey(), entry.getValue().originalState);
         }
      }
   }

   private static void restoreBlock(ServerLevel level, BlockPos pos, BlockState originalState) {
      BlockState currentState = level.getBlockState(pos);
      if (!currentState.isAir() && !currentState.is((Block)ModBlocks.REVERSING_TIME_SANDSTONE.get())) {
         level.destroyBlock(pos, false);
      }

      level.setBlock(pos, originalState, 3);
      ChronoDawn.LOGGER.debug("Restored Reversing Time Sandstone at {}", pos);
   }

   private static void handleSpatiallyLinkedPickaxeDropDoubling(Level level, BlockPos pos, BlockState state, Player player) {
      if (!level.isClientSide()) {
         ItemStack mainHandItem = player.getMainHandItem();
         if (SpatiallyLinkedPickaxeItem.isSpatiallyLinkedPickaxe(mainHandItem.getItem())) {
            if (!(level.random.nextDouble() >= SpatiallyLinkedPickaxeItem.getDropDoublingChance())) {
               ServerLevel serverLevel = (ServerLevel)level;
               BlockEntity blockEntity = level.getBlockEntity(pos);
               Builder lootParamsBuilder = new Builder(serverLevel)
                  .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                  .withParameter(LootContextParams.TOOL, mainHandItem)
                  .withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockEntity);
               if (player instanceof ServerPlayer serverPlayer) {
                  lootParamsBuilder.withParameter(LootContextParams.THIS_ENTITY, serverPlayer);
               }

               for (ItemStack drop : state.getDrops(lootParamsBuilder)) {
                  if (!drop.isEmpty()) {
                     ItemEntity itemEntity = new ItemEntity(serverLevel, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop.copy());
                     itemEntity.setDefaultPickUpDelay();
                     serverLevel.addFreshEntity(itemEntity);
                  }
               }

               ChronoDawn.LOGGER.debug("Spatially Linked Pickaxe doubled drops for block at {} (player: {})", pos, player.getName().getString());
            }
         }
      }
   }

   private static PortalStateMachine findNearbyPortal(Level level, BlockPos pos) {
      PortalRegistry registry = PortalRegistry.getInstance();

      for (int x = -5; x <= 5; x++) {
         for (int y = -5; y <= 5; y++) {
            for (int z = -5; z <= 5; z++) {
               BlockPos searchPos = pos.offset(x, y, z);
               PortalStateMachine portal = registry.getPortalAt(searchPos);
               if (portal != null) {
                  return portal;
               }
            }
         }
      }

      return null;
   }

   public static boolean hasRequiredAncientGears(Player player) {
      if (player == null) {
         return false;
      }

      int gearCount = 0;

      for (ItemStack stack : player.getInventory().items) {
         if (stack.is((Item)ModItems.ANCIENT_GEAR.get())) {
            gearCount += stack.getCount();
         }
      }

      return gearCount >= 3;
   }

   private static boolean isBossRoomDoor(Level level, BlockPos pos) {
      return level.getBlockEntity(pos) instanceof BossRoomDoorBlockEntity doorEntity ? doorEntity.isBossRoomDoor() : false;
   }

   private static String getDoorType(Level level, BlockPos pos) {
      return level.getBlockEntity(pos) instanceof BossRoomDoorBlockEntity doorEntity ? doorEntity.getDoorType() : "entrance";
   }

   public static boolean hasKeyToMasterClock(Player player) {
      if (player == null) {
         return false;
      }

      for (ItemStack stack : player.getInventory().items) {
         if (stack.is((Item)ModItems.KEY_TO_MASTER_CLOCK.get())) {
            return true;
         }
      }

      return false;
   }

   private static BlockState getStrippedState(BlockState state) {
      if (state.is((Block)ModBlocks.TIME_WOOD_LOG.get())) {
         return ((Block)ModBlocks.STRIPPED_TIME_WOOD_LOG.get()).defaultBlockState();
      } else if (state.is((Block)ModBlocks.DARK_TIME_WOOD_LOG.get())) {
         return ((Block)ModBlocks.STRIPPED_DARK_TIME_WOOD_LOG.get()).defaultBlockState();
      } else if (state.is((Block)ModBlocks.ANCIENT_TIME_WOOD_LOG.get())) {
         return ((Block)ModBlocks.STRIPPED_ANCIENT_TIME_WOOD_LOG.get()).defaultBlockState();
      } else if (state.is((Block)ModBlocks.TIME_WOOD.get())) {
         return ((Block)ModBlocks.STRIPPED_TIME_WOOD.get()).defaultBlockState();
      } else if (state.is((Block)ModBlocks.DARK_TIME_WOOD.get())) {
         return ((Block)ModBlocks.STRIPPED_DARK_TIME_WOOD.get()).defaultBlockState();
      } else {
         return state.is((Block)ModBlocks.ANCIENT_TIME_WOOD.get()) ? ((Block)ModBlocks.STRIPPED_ANCIENT_TIME_WOOD.get()).defaultBlockState() : null;
      }
   }

   private static class RestorationData {
      final ServerLevel level;
      final BlockState originalState;
      int ticksRemaining;

      RestorationData(ServerLevel level, BlockState originalState) {
         this.level = level;
         this.originalState = originalState;
         this.ticksRemaining = 60;
      }
   }
}
