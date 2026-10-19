package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.registry.block.ModBlocks;
import com.github.wal_bos.moreskills.world.block.PathsPortalBlock;
import io.github.manasmods.tensura.block.template.SimpleLog;
import io.github.manasmods.tensura.registry.block.TensuraBlocks;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;

public final class PathsRitualHandler {
   private static final ResourceKey<Level> PATHS = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "paths"));
   private static final ResourceLocation FELDWAY_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "feldway");
   private static final double PATHS_ENTRY_REQUIREMENT_EP = 5000000.0;
   private static final int PORTAL_COOLDOWN_TICKS = 120;
   private static final int PORTAL_STAND_TICKS_REQUIRED = 100;
   private static final int PATHS_MIN_PORTAL_Y = 110;
   private static final String PORTAL_COOLDOWN_TAG = "TensuraMoreSkillsPathsPortalCooldown";
   private static final String PORTAL_CHARGE_TICKS_TAG = "TensuraMoreSkillsPathsPortalChargeTicks";
   private static final String PORTAL_CHARGE_POS_TAG = "TensuraMoreSkillsPathsPortalChargePos";
   private static final String PORTAL_CHARGE_DIM_TAG = "TensuraMoreSkillsPathsPortalChargeDim";
   private static final String PORTAL_CHARGE_LAST_TICK_TAG = "TensuraMoreSkillsPathsPortalChargeLastTick";
   private static final String RETURN_POS_TAG = "TensuraMoreSkillsPathsReturnPos";
   private static final Set<PathsRitualHandler.NoDropKey> NO_DROP_BLOCKS = new HashSet<>();

   private PathsRitualHandler() {
   }

   @SubscribeEvent
   public static void onRightClickBlock(RightClickBlock event) {
      if (event.getHand() == InteractionHand.MAIN_HAND) {
         Player player = event.getEntity();
         Level level = player.level();
         if (!level.isClientSide()) {
            if (level instanceof ServerLevel serverLevel) {
               if (player instanceof ServerPlayer serverPlayer) {
                  BlockPos clicked = event.getPos();
                  BlockState clickedState = level.getBlockState(clicked);
                  if (clickedState.is((Block)TensuraBlocks.MITHRIL_BLOCK.get())
                     && serverLevel.dimension().equals(PATHS)
                     && trySummonFeldway(serverLevel, serverPlayer, clicked)) {
                     event.setCanceled(true);
                     event.setCancellationResult(InteractionResult.SUCCESS);
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onBlockBreak(BreakEvent event) {
      if (event.getLevel() instanceof ServerLevel level) {
         BlockPos var4 = event.getPos();
         BlockState state = level.getBlockState(var4);
         if (isPathsPortal(state)) {
            NO_DROP_BLOCKS.add(new PathsRitualHandler.NoDropKey(level.dimension().location(), var4));
         }
      }
   }

   @SubscribeEvent
   public static void onBlockDrops(BlockDropsEvent event) {
      if (event.getLevel() instanceof ServerLevel level) {
         PathsRitualHandler.NoDropKey var5 = new PathsRitualHandler.NoDropKey(level.dimension().location(), event.getPos());
         if (NO_DROP_BLOCKS.remove(var5)) {
            for (ItemEntity item : event.getDrops()) {
               item.setItem(ItemStack.EMPTY);
               item.discard();
            }

            event.getDrops().clear();
         }
      }
   }

   public static void tickPlayerInPathsPortal(ServerLevel level, ServerPlayer player, BlockPos portalPos) {
      if (!isPathsPortal(level.getBlockState(portalPos))) {
         resetPortalCharge(player);
      } else {
         int cooldown = player.getPersistentData().getInt("TensuraMoreSkillsPathsPortalCooldown");
         if (cooldown > 0) {
            player.getPersistentData().putInt("TensuraMoreSkillsPathsPortalCooldown", cooldown - 1);
            resetPortalCharge(player);
         } else {
            boolean inPaths = level.dimension().equals(PATHS);
            if (!inPaths) {
               double ep = EnergyHelper.getMaxEP(player);
               if (ep < 5000000.0) {
                  resetPortalCharge(player);
                  if (player.tickCount % 40 == 0) {
                     player.displayClientMessage(
                        Component.literal("You need at least 5,000,000 EP to enter the Paths. Current EP: " + formatEP(ep)).withStyle(ChatFormatting.RED), true
                     );
                     level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.8F, 0.6F);
                     spawnDeniedParticles(level, portalPos);
                  }

                  return;
               }
            }

            int chargeTicks = tickPortalCharge(level, player, portalPos);
            int remainingTicks = Math.max(0, 100 - chargeTicks);
            int remainingSeconds = (remainingTicks + 19) / 20;
            if (chargeTicks < 100) {
               if (player.tickCount % 20 == 0 || chargeTicks == 1) {
                  player.displayClientMessage(
                     Component.literal("Remain in the portal for " + remainingSeconds + "s...").withStyle(ChatFormatting.LIGHT_PURPLE), true
                  );
                  level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PORTAL_AMBIENT, SoundSource.PLAYERS, 0.25F, 1.4F);
                  spawnChargingParticles(level, portalPos, chargeTicks / 100.0F);
               }
            } else {
               resetPortalCharge(player);
               ServerLevel targetLevel = inPaths ? level.getServer().overworld() : level.getServer().getLevel(PATHS);
               if (targetLevel == null) {
                  player.displayClientMessage(Component.literal("The Paths dimension is missing.").withStyle(ChatFormatting.RED), true);
               } else {
                  BlockPos targetPos;
                  if (inPaths) {
                     targetPos = getStoredReturnPos(player);
                     if (targetPos == null || !isSafeOrPortal(targetLevel, targetPos)) {
                        targetPos = findSafeArrival(targetLevel, player.blockPosition());
                     }
                  } else {
                     player.getPersistentData().putLong("TensuraMoreSkillsPathsReturnPos", portalPos.asLong());
                     targetPos = ensurePathsReturnPortal(targetLevel, portalPos, level.getBlockState(portalPos));
                  }

                  teleportPlayer(level, targetLevel, player, portalPos, targetPos);
               }
            }
         }
      }
   }

   private static int tickPortalCharge(ServerLevel level, ServerPlayer player, BlockPos portalPos) {
      String currentDim = level.dimension().location().toString();
      String storedDim = player.getPersistentData().getString("TensuraMoreSkillsPathsPortalChargeDim");
      long storedPos = player.getPersistentData().getLong("TensuraMoreSkillsPathsPortalChargePos");
      int lastTick = player.getPersistentData().getInt("TensuraMoreSkillsPathsPortalChargeLastTick");
      boolean samePortal = storedPos == portalPos.asLong() && currentDim.equals(storedDim);
      boolean continuous = lastTick <= 0 || player.tickCount - lastTick <= 2;
      int charge;
      if (samePortal && continuous) {
         charge = player.getPersistentData().getInt("TensuraMoreSkillsPathsPortalChargeTicks");
      } else {
         charge = 0;
         player.getPersistentData().putLong("TensuraMoreSkillsPathsPortalChargePos", portalPos.asLong());
         player.getPersistentData().putString("TensuraMoreSkillsPathsPortalChargeDim", currentDim);
      }

      charge = Math.min(100, charge + 1);
      player.getPersistentData().putInt("TensuraMoreSkillsPathsPortalChargeTicks", charge);
      player.getPersistentData().putInt("TensuraMoreSkillsPathsPortalChargeLastTick", player.tickCount);
      return charge;
   }

   private static void resetPortalCharge(ServerPlayer player) {
      player.getPersistentData().putInt("TensuraMoreSkillsPathsPortalChargeTicks", 0);
      player.getPersistentData().putLong("TensuraMoreSkillsPathsPortalChargePos", 0L);
      player.getPersistentData().putString("TensuraMoreSkillsPathsPortalChargeDim", "");
      player.getPersistentData().putInt("TensuraMoreSkillsPathsPortalChargeLastTick", 0);
   }

   private static void teleportPlayer(ServerLevel sourceLevel, ServerLevel targetLevel, ServerPlayer player, BlockPos sourcePos, BlockPos targetPos) {
      double x = targetPos.getX() + 0.5;
      double y = targetPos.getY();
      double z = targetPos.getZ() + 0.5;
      player.getPersistentData().putInt("TensuraMoreSkillsPathsPortalCooldown", 120);
      sourceLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PORTAL_TRAVEL, SoundSource.PLAYERS, 0.8F, 0.7F);
      spawnPortalParticles(sourceLevel, sourcePos);
      player.teleportTo(targetLevel, x, y, z, player.getYRot(), player.getXRot());
      player.getPersistentData().putInt("TensuraMoreSkillsPathsPortalCooldown", 120);
      targetLevel.playSound(null, x, y, z, SoundEvents.PORTAL_TRAVEL, SoundSource.PLAYERS, 0.8F, 1.1F);
      spawnPortalParticles(targetLevel, targetPos);
      spawnArrivalParticles(targetLevel, targetPos);
   }

   private static BlockPos getStoredReturnPos(ServerPlayer player) {
      return !player.getPersistentData().contains("TensuraMoreSkillsPathsReturnPos")
         ? null
         : BlockPos.of(player.getPersistentData().getLong("TensuraMoreSkillsPathsReturnPos"));
   }

   private static BlockPos ensurePathsReturnPortal(ServerLevel level, BlockPos pos, BlockState sourcePortalState) {
      if (!level.dimension().equals(PATHS)) {
         return pos;
      }

      BlockPos portalPos = forcePathsPortalAboveGround(level, pos);
      clearArrivalSpace(level, portalPos);
      Axis axis = Axis.X;
      if (sourcePortalState.hasProperty(PathsPortalBlock.AXIS)) {
         axis = (Axis)sourcePortalState.getValue(PathsPortalBlock.AXIS);
      }

      BlockState portal = (BlockState)((Block)ModBlocks.PATHS_PORTAL.get()).defaultBlockState().setValue(PathsPortalBlock.AXIS, axis);
      level.setBlock(portalPos, portal, 3);
      BlockPos below = portalPos.below();
      if (!level.getBlockState(below).isFaceSturdy(level, below, Direction.UP)) {
         level.setBlock(below, ((Block)TensuraBlocks.PURE_MAGISTEEL_BLOCK.get()).defaultBlockState(), 3);
      }

      return portalPos;
   }

   private static BlockPos forcePathsPortalAboveGround(ServerLevel level, BlockPos pos) {
      int x = pos.getX();
      int z = pos.getZ();
      BlockPos surface = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(x, 0, z));
      int y = Math.max(110, surface.getY() + 1);
      y = Math.max(y, level.getMinBuildHeight() + 8);
      y = Math.min(y, level.getMaxBuildHeight() - 3);
      return new BlockPos(x, y, z);
   }

   private static BlockPos findSafeArrival(ServerLevel level, BlockPos around) {
      int x = around.getX();
      int z = around.getZ();
      BlockPos surface = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(x, 0, z));
      int y = Math.max(surface.getY() + 1, level.getMinBuildHeight() + 8);
      y = Math.min(y, level.getMaxBuildHeight() - 3);
      BlockPos arrival = new BlockPos(x, y, z);
      clearArrivalSpace(level, arrival);
      BlockPos below = arrival.below();
      if (!level.getBlockState(below).isFaceSturdy(level, below, Direction.UP)) {
         level.setBlock(below, ((SimpleLog)TensuraBlocks.PALM_WOOD.get()).defaultBlockState(), 3);
      }

      return arrival;
   }

   private static boolean isSafeOrPortal(ServerLevel level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      return (state.isAir() || isPathsPortal(state))
         && level.getBlockState(pos.above()).isAir()
         && level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
   }

   private static void clearArrivalSpace(ServerLevel level, BlockPos pos) {
      level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
      level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 3);
   }

   private static boolean trySummonFeldway(ServerLevel level, ServerPlayer player, BlockPos mithrilPos) {
      if (!isValidFeldwayShrine(level, mithrilPos)) {
         return false;
      } else {
         AABB checkArea = new AABB(mithrilPos).inflate(96.0);
         boolean alreadyNearby = !level.getEntities(
               (Entity)null, checkArea, entity -> entity.isAlive() && BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).equals(FELDWAY_ID)
            )
            .isEmpty();
         if (alreadyNearby) {
            player.displayClientMessage(Component.literal("Feldway is already nearby.").withStyle(ChatFormatting.RED), true);
            return false;
         } else {
            EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(FELDWAY_ID);
            if (!(type.create(level) instanceof Mob feldway)) {
               player.displayClientMessage(Component.literal("Failed to create Feldway entity.").withStyle(ChatFormatting.RED), true);
               return false;
            } else {
               BlockPos spawnPos = mithrilPos.above();
               feldway.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, player.getYRot() + 180.0F, 0.0F);
               feldway.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), MobSpawnType.TRIGGERED, null);
               feldway.setPersistenceRequired();
               level.setBlock(mithrilPos, Blocks.AIR.defaultBlockState(), 3);
               level.addFreshEntity(feldway);
               level.playSound(null, mithrilPos, SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 1.5F, 0.55F);
               level.playSound(null, mithrilPos, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 1.2F, 0.7F);

               for (int i = 0; i < 160; i++) {
                  double ox = (level.random.nextDouble() - 0.5) * 5.0;
                  double oy = level.random.nextDouble() * 3.0;
                  double oz = (level.random.nextDouble() - 0.5) * 5.0;
                  level.sendParticles(
                     ParticleTypes.REVERSE_PORTAL,
                     mithrilPos.getX() + 0.5 + ox,
                     mithrilPos.getY() + 0.8 + oy,
                     mithrilPos.getZ() + 0.5 + oz,
                     1,
                     0.0,
                     0.0,
                     0.0,
                     0.05
                  );
               }

               player.displayClientMessage(Component.literal("Feldway has answered the shrine.").withStyle(ChatFormatting.DARK_PURPLE), true);
               return true;
            }
         }
      }
   }

   private static boolean isValidFeldwayShrine(ServerLevel level, BlockPos mithrilPos) {
      Block pure = (Block)TensuraBlocks.PURE_MAGISTEEL_BLOCK.get();

      for (int x = -1; x <= 1; x++) {
         for (int z = -1; z <= 1; z++) {
            BlockPos base = mithrilPos.offset(x, -1, z);
            if (!level.getBlockState(base).is(pure)) {
               return false;
            }
         }
      }

      return level.getBlockState(mithrilPos).is((Block)TensuraBlocks.MITHRIL_BLOCK.get())
         && level.getBlockState(mithrilPos.above()).isAir()
         && level.getBlockState(mithrilPos.above(2)).isAir();
   }

   private static void spawnPortalParticles(ServerLevel level, BlockPos pos) {
      for (int i = 0; i < 48; i++) {
         double ox = (level.random.nextDouble() - 0.5) * 2.5;
         double oy = level.random.nextDouble() * 2.5;
         double oz = (level.random.nextDouble() - 0.5) * 2.5;
         level.sendParticles(ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.5 + ox, pos.getY() + 0.8 + oy, pos.getZ() + 0.5 + oz, 1, 0.0, 0.0, 0.0, 0.08);
      }
   }

   private static void spawnArrivalParticles(ServerLevel level, BlockPos pos) {
      for (int i = 0; i < 48; i++) {
         double ox = (level.random.nextDouble() - 0.5) * 2.5;
         double oy = level.random.nextDouble() * 2.0;
         double oz = (level.random.nextDouble() - 0.5) * 2.5;
         level.sendParticles(ParticleTypes.PORTAL, pos.getX() + 0.5 + ox, pos.getY() + 1.0 + oy, pos.getZ() + 0.5 + oz, 1, 0.0, 0.0, 0.0, 0.05);
      }
   }

   private static void spawnDeniedParticles(ServerLevel level, BlockPos pos) {
      for (int i = 0; i < 16; i++) {
         double ox = (level.random.nextDouble() - 0.5) * 1.4;
         double oy = level.random.nextDouble() * 1.5;
         double oz = (level.random.nextDouble() - 0.5) * 1.4;
         level.sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5 + ox, pos.getY() + 0.5 + oy, pos.getZ() + 0.5 + oz, 1, 0.0, 0.0, 0.0, 0.02);
      }
   }

   private static void spawnChargingParticles(ServerLevel level, BlockPos pos, float progress) {
      int count = 6 + (int)(progress * 18.0F);

      for (int i = 0; i < count; i++) {
         double angle = level.random.nextDouble() * Math.PI * 2.0;
         double radius = 0.35 + level.random.nextDouble() * 0.75;
         double x = pos.getX() + 0.5 + Math.cos(angle) * radius;
         double y = pos.getY() + 0.25 + level.random.nextDouble() * 1.65;
         double z = pos.getZ() + 0.5 + Math.sin(angle) * radius;
         double dx = (pos.getX() + 0.5 - x) * 0.045;
         double dy = 0.035 + progress * 0.045;
         double dz = (pos.getZ() + 0.5 - z) * 0.045;
         level.sendParticles(ParticleTypes.REVERSE_PORTAL, x, y, z, 1, dx, dy, dz, 0.055 + progress * 0.035);
      }
   }

   private static String formatEP(double ep) {
      if (ep >= 1.0E9) {
         return String.format("%.2fB", ep / 1.0E9);
      } else if (ep >= 1000000.0) {
         return String.format("%.2fM", ep / 1000000.0);
      } else {
         return ep >= 1000.0 ? String.format("%.2fK", ep / 1000.0) : String.valueOf((long)ep);
      }
   }

   private static boolean isPathsPortal(BlockState state) {
      return state.is((Block)ModBlocks.PATHS_PORTAL.get());
   }

   private record NoDropKey(ResourceLocation dimension, BlockPos pos) {
   }
}
