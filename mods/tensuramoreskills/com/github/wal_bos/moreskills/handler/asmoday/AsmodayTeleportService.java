package com.github.wal_bos.moreskills.handler.asmoday;

import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;

public final class AsmodayTeleportService {
   private AsmodayTeleportService() {
   }

   public static boolean teleportToPlayer(ServerPlayer player, ServerPlayer target) {
      ServerLevel level = target.serverLevel();
      Vec3 base = target.position().subtract(target.getLookAngle().normalize().scale(1.65));
      Vec3 destination = findLandingSpot(level, BlockPos.containing(base), target.position());
      return teleport(player, level, destination, target.getYRot(), target.getXRot());
   }

   public static boolean teleportToDimension(ServerPlayer player, ResourceKey<Level> dimension) {
      ServerLevel level = player.server.getLevel(dimension);
      if (level == null) {
         return false;
      }

      Vec3 destination = findLandingSpot(level, BlockPos.containing(player.position()), player.position());
      return teleport(player, level, destination, player.getYRot(), player.getXRot());
   }

   public static boolean teleport(ServerPlayer player, ServerLevel level, Vec3 destination, float yRot, float xRot) {
      AsmodayFx.spawnSpaceClose(player);
      DimensionTransition transition = new DimensionTransition(level, destination, Vec3.ZERO, yRot, xRot, false, DimensionTransition.DO_NOTHING);
      player.changeDimension(transition);
      player.resetFallDistance();
      player.serverLevel()
         .playSound(null, player.getX(), player.getY(), player.getZ(), (SoundEvent)TensuraSoundEvents.INSTANT_MOVE.get(), SoundSource.PLAYERS, 1.0F, 0.65F);
      AsmodayFx.spawnSpaceArrival(player);
      return true;
   }

   private static Vec3 findLandingSpot(ServerLevel level, BlockPos origin, Vec3 fallback) {
      MutableBlockPos mutable = new MutableBlockPos();

      for (int radius = 0; radius <= 8; radius++) {
         for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
               for (int y = 3; y >= -3; y--) {
                  mutable.set(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
                  if (isSafeStand(level, mutable)) {
                     return Vec3.atBottomCenterOf(mutable);
                  }
               }
            }
         }
      }

      return fallback;
   }

   private static boolean isSafeStand(ServerLevel level, BlockPos pos) {
      if (!level.isLoaded(pos)) {
         return false;
      }

      BlockState feet = level.getBlockState(pos);
      BlockState head = level.getBlockState(pos.above());
      BlockState ground = level.getBlockState(pos.below());
      return feet.canBeReplaced() && head.canBeReplaced() && !ground.canBeReplaced();
   }
}
