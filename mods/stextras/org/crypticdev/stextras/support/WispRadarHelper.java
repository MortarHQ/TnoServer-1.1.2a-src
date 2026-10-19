package org.crypticdev.stextras.support;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.AABB;

public final class WispRadarHelper {
   public static final int SURFACE_COLOR = 3073643;
   public static final int CAVE_COLOR = 8421504;
   public static final int BLOCKED_COLOR = 15022389;

   public static boolean isRadarActive(Player player) {
      return player != null && PlayerPhasingHooks.hasPhaseAbility(player) && isInsideTerrain(player);
   }

   public static boolean isInsideTerrain(Player player) {
      if (player == null) {
         return false;
      }

      AABB checkFolder = player.getBoundingBox().deflate(0.001);
      return player.level().getBlockCollisions(player, checkFolder).iterator().hasNext();
   }

   public static boolean shouldOutlineBlock(Level level, BlockPos pos, BlockState state) {
      if (state.isAir() || state.getCollisionShape(level, pos).isEmpty()) {
         return false;
      } else {
         return hasOpenNeighbor(level, pos) ? true : isLeadingToBlockedZone(level, pos);
      }
   }

   public static int getBlockOutlineColor(Level level, BlockPos pos, BlockState state) {
      Player player = Minecraft.getInstance().player;
      if (PlayerPhasingHooks.isPhaseBlockedAt(level, pos, player)) {
         return 15022389;
      }

      int surfaceY = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ());
      return pos.getY() >= surfaceY - 2 ? 3073643 : 8421504;
   }

   private static boolean isLeadingToBlockedZone(Level level, BlockPos pos) {
      Player player = Minecraft.getInstance().player;
      if (!PlayerPhasingHooks.isPhaseBlockedAt(level, pos, player)) {
         return false;
      }

      MutableBlockPos neighbor = new MutableBlockPos();

      for (Direction dir : Direction.values()) {
         neighbor.setWithOffset(pos, dir);
         if (!PlayerPhasingHooks.isPhaseBlockedAt(level, neighbor, player)) {
            return true;
         }
      }

      return false;
   }

   private static boolean hasOpenNeighbor(Level level, BlockPos pos) {
      MutableBlockPos neighbor = new MutableBlockPos();

      for (Direction dir : Direction.values()) {
         neighbor.setWithOffset(pos, dir);
         if (level.getBlockState(neighbor).getCollisionShape(level, neighbor).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   public static float red(int c) {
      return (c >> 16 & 0xFF) / 255.0F;
   }

   public static float green(int c) {
      return (c >> 8 & 0xFF) / 255.0F;
   }

   public static float blue(int c) {
      return (c & 0xFF) / 255.0F;
   }
}
