package com.foxablazeultimate.ability.skill.ciel;

import com.foxablazeultimate.config.CielConfig;
import com.foxablazeultimate.network.RequestCielTerrainActionPayload;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class CielTakeoverTerrainHandler {
   private static final Map<UUID, CielTakeoverTerrainHandler.BreakState> BREAKING = new HashMap<>();
   private static final Map<UUID, Long> NEXT_PLACE_TIME = new HashMap<>();
   private static final Map<UUID, Long> NEXT_BREAK_REQUEST_TIME = new HashMap<>();

   private CielTakeoverTerrainHandler() {
   }

   public static void handleIntent(ServerPlayer player, RequestCielTerrainActionPayload.Action action, BlockPos suppliedPosition) {
      if (action != null && suppliedPosition != null) {
         if (action == RequestCielTerrainActionPayload.Action.CANCEL_BREAK) {
            clearBreak(player);
         } else if (!canAutomateTerrain(player)) {
            clearBreak(player);
         } else {
            BlockPos position = suppliedPosition.immutable();
            CielConfig.CielSettings config = CielConfig.settings();
            if (action == RequestCielTerrainActionPayload.Action.PLACE_PILLAR) {
               clearBreak(player);
               long now = player.level().getGameTime();
               if (now >= NEXT_PLACE_TIME.getOrDefault(player.getUUID(), 0L)) {
                  NEXT_PLACE_TIME.put(player.getUUID(), now + clamp(config.takeoverEscapeActionIntervalTicks, 1, 40));
                  tryPlacePillar(player, position, config);
               }
            } else {
               CielTakeoverTerrainHandler.BreakState current = BREAKING.get(player.getUUID());
               if (current == null || !current.position.equals(position)) {
                  long now = player.level().getGameTime();
                  if (now >= NEXT_BREAK_REQUEST_TIME.getOrDefault(player.getUUID(), 0L)) {
                     NEXT_BREAK_REQUEST_TIME.put(player.getUUID(), now + 2L);
                     if (config.takeoverTerrainEscape && config.takeoverEscapeBreakBlocks && isValidBreakTarget(player, position, config)) {
                        BlockState initialBlock = player.level().getBlockState(position);
                        int initialToolSlot = findBestToolSlot(player, initialBlock, position);
                        if (initialToolSlot != -1 && fireStartBreakHooks(player, position, initialBlock, initialToolSlot)) {
                           if (player.level().getBlockState(position).equals(initialBlock)) {
                              clearBreak(player);
                              BREAKING.put(player.getUUID(), new CielTakeoverTerrainHandler.BreakState(position, initialBlock, initialToolSlot, now + 10L));
                           }
                        } else {
                           clearBreak(player);
                        }
                     } else {
                        clearBreak(player);
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         CielTakeoverTerrainHandler.BreakState state = BREAKING.get(player.getUUID());
         if (state != null) {
            CielConfig.CielSettings config = CielConfig.settings();
            if (canAutomateTerrain(player)
               && config.takeoverTerrainEscape
               && config.takeoverEscapeBreakBlocks
               && isValidBreakTarget(player, state.position, config)) {
               BlockState block = player.level().getBlockState(state.position);
               if (!block.equals(state.initialBlock)) {
                  clearBreak(player);
               } else {
                  long now = player.level().getGameTime();
                  if (state.toolSlot == -1 || now >= state.nextToolScanTime) {
                     state.toolSlot = findBestToolSlot(player, block, state.position);
                     state.nextToolScanTime = now + 10L;
                  }

                  int toolSlot = state.toolSlot;
                  if (toolSlot == -1) {
                     clearBreak(player);
                  } else {
                     float progress = player.gameMode.isCreative()
                        ? 1.0F
                        : withMainHandSlot(player, toolSlot, false, () -> block.getDestroyProgress(player, player.level(), state.position));
                     progress *= (float)clamp(config.takeoverEscapeBreakSpeedMultiplier, 0.05, 20.0);
                     state.progress = state.progress + Math.max(0.0F, progress);
                     player.level().destroyBlockProgress(player.getId(), state.position, Math.min(9, (int)(state.progress * 10.0F)));
                     if (player.tickCount % 5 == 0) {
                        player.swing(InteractionHand.MAIN_HAND, true);
                     }

                     if (!(state.progress < 1.0F)) {
                        boolean stopAccepted = withMainHandSlot(player, toolSlot, true, () -> CielEmergencyController.runAutomatedBoolean(() -> {
                           LeftClickBlock stop = CommonHooks.onLeftClickBlock(player, state.position, Direction.UP, Action.STOP_DESTROY_BLOCK);
                           if (stop.isCanceled()) {
                              return false;
                           }

                           player.gameMode.destroyBlock(state.position);
                           return true;
                        }));
                        clearBreak(player, !stopAccepted);
                     }
                  }
               }
            } else {
               clearBreak(player);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLogout(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         reset(player);
      }
   }

   @SubscribeEvent
   public static void onDimensionChange(PlayerChangedDimensionEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         reset(player);
      }
   }

   static void reset(ServerPlayer player) {
      clearBreak(player);
      NEXT_PLACE_TIME.remove(player.getUUID());
      NEXT_BREAK_REQUEST_TIME.remove(player.getUUID());
   }

   private static boolean canAutomateTerrain(ServerPlayer player) {
      return player.isAlive() && !player.isSpectator() && CielState.isEmergencyTakeover(player) && CielPassiveHandler.hasPermanentCiel(player);
   }

   private static boolean tryPlacePillar(ServerPlayer player, BlockPos position, CielConfig.CielSettings config) {
      if (config.takeoverTerrainEscape && config.takeoverEscapePlaceBlocks && isReachableAction(player, position) && isPillarBelowPlayer(player, position)) {
         int slot = findPillarSlot(player, position);
         if (slot == -1) {
            return false;
         } else {
            ServerLevel level = player.serverLevel();
            ItemStack source = player.getInventory().getItem(slot);
            if (!(
               source.getItem() instanceof BlockItem blockItem
                  && !(blockItem.getBlock() instanceof EntityBlock)
                  && level.getBlockState(position).canBeReplaced()
                  && level.getBlockEntity(position) == null
                  && !level.getBlockState(position.below()).getCollisionShape(level, position.below()).isEmpty()
            )) {
               return false;
            } else if (level.isUnobstructed(blockItem.getBlock().defaultBlockState(), position, CollisionContext.of(player))
               && level.mayInteract(player, position)
               && player.mayUseItemAt(position, Direction.UP, source)) {
               BlockPos support = position.below();
               BlockHitResult hit = new BlockHitResult(new Vec3(position.getX() + 0.5, position.getY(), position.getZ() + 0.5), Direction.UP, support, false);
               boolean previousShift = player.isShiftKeyDown();

               InteractionResult result;
               try {
                  player.setShiftKeyDown(true);
                  result = withMainHandSlot(
                     player,
                     slot,
                     true,
                     () -> CielEmergencyController.runAutomatedAction(
                        () -> player.gameMode.useItemOn(player, level, player.getMainHandItem(), InteractionHand.MAIN_HAND, hit)
                     )
                  );
               } finally {
                  player.setShiftKeyDown(previousShift);
               }

               if (result.consumesAction() && level.getBlockState(position).is(blockItem.getBlock())) {
                  player.swing(InteractionHand.MAIN_HAND, true);
                  return true;
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private static boolean isValidBreakTarget(ServerPlayer player, BlockPos position, CielConfig.CielSettings config) {
      if (isReachableAction(player, position) && isEscapeBreakPosition(player, position) && player.level().mayInteract(player, position)) {
         BlockState state = player.level().getBlockState(position);
         if (!state.isAir() && !state.getCollisionShape(player.level(), position).isEmpty() && player.level().getBlockEntity(position) == null) {
            float hardness = state.getDestroySpeed(player.level(), position);
            return hardness >= 0.0F && hardness <= clamp(config.takeoverEscapeMaximumBreakHardness, 0.0, 100.0);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean isReachableAction(ServerPlayer player, BlockPos position) {
      if (player.level().isInWorldBounds(position) && player.level().getWorldBorder().isWithinBounds(position) && player.canInteractWithBlock(position, 1.0)) {
         double horizontal = Math.max(Math.abs(position.getX() + 0.5 - player.getX()), Math.abs(position.getZ() + 0.5 - player.getZ()));
         double vertical = Math.abs(position.getY() + 0.5 - player.getEyeY());
         return horizontal <= 2.0 && vertical <= 4.0;
      } else {
         return false;
      }
   }

   private static boolean isPillarBelowPlayer(ServerPlayer player, BlockPos position) {
      double horizontal = Math.max(Math.abs(position.getX() + 0.5 - player.getX()), Math.abs(position.getZ() + 0.5 - player.getZ()));
      double heightAbovePosition = player.getBoundingBox().minY - position.getY();
      return horizontal <= 0.8 && heightAbovePosition >= 0.9 && heightAbovePosition <= 1.5;
   }

   private static boolean isEscapeBreakPosition(ServerPlayer player, BlockPos position) {
      BlockPos feet = BlockPos.containing(player.getX(), player.getBoundingBox().minY + 0.01, player.getZ());
      int dx = Math.abs(position.getX() - feet.getX());
      int dz = Math.abs(position.getZ() - feet.getZ());
      int minimumY = feet.getY();
      int maximumY = Mth.floor(player.getBoundingBox().maxY + 1.0E-4);
      return dx <= 1 && dz <= 1 && (dx == 0 || dz == 0) && position.getY() >= minimumY && position.getY() <= maximumY;
   }

   private static int findPillarSlot(ServerPlayer player, BlockPos position) {
      Inventory inventory = player.getInventory();

      for (TagKey<Item> priority : CielTakeoverTerrainTags.PILLAR_PRIORITY) {
         int selected = inventory.selected;
         if (isUsablePillarStack(player, inventory.getItem(selected), priority, position)) {
            return selected;
         }

         for (int slot = 0; slot < 36; slot++) {
            if (slot != selected && isUsablePillarStack(player, inventory.getItem(slot), priority, position)) {
               return slot;
            }
         }

         if (isUsablePillarStack(player, inventory.getItem(40), priority, position)) {
            return 40;
         }
      }

      return -1;
   }

   private static boolean isUsablePillarStack(ServerPlayer player, ItemStack stack, TagKey<Item> priority, BlockPos position) {
      if (!stack.isEmpty() && stack.is(priority) && stack.getItem() instanceof BlockItem blockItem && !(blockItem.getBlock() instanceof EntityBlock)) {
         BlockState state = blockItem.getBlock().defaultBlockState();
         return state.isCollisionShapeFullBlock(player.level(), player.blockPosition())
            && blockItem.getBlock().isEnabled(player.level().enabledFeatures())
            && player.mayUseItemAt(position, Direction.UP, stack);
      } else {
         return false;
      }
   }

   private static int findBestToolSlot(ServerPlayer player, BlockState state, BlockPos position) {
      Inventory inventory = player.getInventory();
      int bestSlot = inventory.selected;
      double bestScore = toolScore(player, inventory.getItem(bestSlot), state, position);

      for (int slot = 0; slot < 36; slot++) {
         double score = toolScore(player, inventory.getItem(slot), state, position);
         if (score > bestScore) {
            bestScore = score;
            bestSlot = slot;
         }
      }

      double offhandScore = toolScore(player, inventory.getItem(40), state, position);
      if (offhandScore > bestScore) {
         bestScore = offhandScore;
         bestSlot = 40;
      }

      return Double.isFinite(bestScore) ? bestSlot : -1;
   }

   private static double toolScore(ServerPlayer player, ItemStack stack, BlockState state, BlockPos position) {
      if (!stack.isEmpty() && !stack.getItem().canAttackBlock(state, player.level(), position, player)) {
         return Double.NEGATIVE_INFINITY;
      } else if (!canBreakInCurrentMode(player, stack, position)) {
         return Double.NEGATIVE_INFINITY;
      } else {
         return stack.isEmpty() ? 1.0 : stack.getDestroySpeed(state) + (stack.isCorrectToolForDrops(state) ? 1000.0 : 0.0);
      }
   }

   private static boolean canBreakInCurrentMode(ServerPlayer player, ItemStack stack, BlockPos position) {
      GameType mode = player.gameMode.getGameModeForPlayer();
      return mode.isBlockPlacingRestricted() && !player.mayBuild()
         ? mode != GameType.SPECTATOR && !stack.isEmpty() && stack.canBreakBlockInAdventureMode(new BlockInWorld(player.level(), position, false))
         : true;
   }

   private static boolean fireStartBreakHooks(ServerPlayer player, BlockPos position, BlockState block, int toolSlot) {
      return withMainHandSlot(
         player,
         toolSlot,
         true,
         () -> CielEmergencyController.runAutomatedAction(
            () -> {
               LeftClickBlock leftClick = CommonHooks.onLeftClickBlock(player, position, Direction.UP, Action.START_DESTROY_BLOCK);
               if (leftClick.isCanceled()) {
                  return false;
               }

               EnchantmentHelper.onHitBlock(
                  player.serverLevel(),
                  player.getMainHandItem(),
                  player,
                  player,
                  EquipmentSlot.MAINHAND,
                  Vec3.atCenterOf(position),
                  block,
                  stack -> player.onEquippedItemBroken(stack, EquipmentSlot.MAINHAND)
               );
               if (leftClick.getUseBlock() != TriState.FALSE) {
                  block.attack(player.level(), position, player);
               }

               return true;
            }
         )
      );
   }

   private static <T> T withMainHandSlot(ServerPlayer player, int slot, boolean synchronize, Supplier<T> action) {
      Inventory inventory = player.getInventory();
      int selected = inventory.selected;
      if (slot == selected) {
         try {
            return action.get();
         } finally {
            if (synchronize) {
               synchronizeInventory(player);
            }
         }
      } else {
         ItemStack originalMainHand = inventory.getItem(selected);
         ItemStack selectedStack = inventory.getItem(slot);
         inventory.setItem(selected, selectedStack);
         inventory.setItem(slot, originalMainHand);

         try {
            return action.get();
         } finally {
            ItemStack changedSelectedStack = inventory.getItem(selected);
            ItemStack changedSourceStack = inventory.getItem(slot);
            inventory.setItem(slot, changedSelectedStack);
            inventory.setItem(selected, changedSourceStack);
            if (synchronize) {
               synchronizeInventory(player);
            }
         }
      }
   }

   private static void synchronizeInventory(ServerPlayer player) {
      player.getInventory().setChanged();
      player.inventoryMenu.broadcastChanges();
      if (player.containerMenu != player.inventoryMenu) {
         player.containerMenu.broadcastChanges();
      }
   }

   private static void clearBreak(ServerPlayer player) {
      clearBreak(player, true);
   }

   private static void clearBreak(ServerPlayer player, boolean notifyAbort) {
      CielTakeoverTerrainHandler.BreakState removed = BREAKING.remove(player.getUUID());
      if (removed != null) {
         if (notifyAbort) {
            withMainHandSlot(
               player,
               removed.toolSlot,
               true,
               () -> CielEmergencyController.runAutomatedAction(
                  () -> CommonHooks.onLeftClickBlock(player, removed.position, Direction.UP, Action.ABORT_DESTROY_BLOCK)
               )
            );
         }

         player.level().destroyBlockProgress(player.getId(), removed.position, -1);
      }
   }

   private static int clamp(int value, int minimum, int maximum) {
      return Math.max(minimum, Math.min(maximum, value));
   }

   private static double clamp(double value, double minimum, double maximum) {
      return Math.max(minimum, Math.min(maximum, value));
   }

   private static final class BreakState {
      private final BlockPos position;
      private final BlockState initialBlock;
      private float progress;
      private int toolSlot;
      private long nextToolScanTime;

      private BreakState(BlockPos position, BlockState initialBlock, int toolSlot, long nextToolScanTime) {
         this.position = position;
         this.initialBlock = initialBlock;
         this.toolSlot = toolSlot;
         this.nextToolScanTime = nextToolScanTime;
      }
   }
}
