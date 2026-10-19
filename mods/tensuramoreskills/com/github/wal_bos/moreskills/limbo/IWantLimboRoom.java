package com.github.wal_bos.moreskills.limbo;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;

public final class IWantLimboRoom {
   private static final Set<String> BUILT = new HashSet<>();
   private static final int RADIUS = 9;
   private static final int HEIGHT = 8;

   private IWantLimboRoom() {
   }

   public static BlockPos center(ServerLevel level) {
      int x = (Integer)MoreSkillsConfigs.GRAND.iWantToBeJustLikeYou.limboRoomX.get();
      int y = (Integer)MoreSkillsConfigs.GRAND.iWantToBeJustLikeYou.limboRoomY.get();
      int z = (Integer)MoreSkillsConfigs.GRAND.iWantToBeJustLikeYou.limboRoomZ.get();
      y = Math.max(level.getMinBuildHeight() + 8, Math.min(level.getMaxBuildHeight() - 12, y));
      BlockPos configured = new BlockPos(x, y, z);
      WorldBorder border = level.getWorldBorder();
      if (!border.isWithinBounds(configured)) {
         BlockPos spawn = level.getSharedSpawnPos();
         int safeY = Math.max(level.getMinBuildHeight() + 8, Math.min(level.getMaxBuildHeight() - 12, spawn.getY() - 32));
         return new BlockPos(spawn.getX(), safeY, spawn.getZ());
      } else {
         return configured;
      }
   }

   public static void enter(ServerPlayer player) {
      ServerLevel level = player.serverLevel();
      BlockPos center = center(level);
      build(level, center);
      detachOnlyPlayer(player);
      player.removeAllEffects();
      player.teleportTo(level, center.getX() + 0.5, center.getY() + 1.0, center.getZ() + 0.5, 180.0F, 0.0F);
      player.setDeltaMovement(0.0, 0.0, 0.0);
      player.fallDistance = 0.0F;
      IWantLimboData.setLimboPosition(player, level.dimension(), center);
   }

   public static void keepInside(ServerPlayer player) {
      if (IWantLimboData.isActive(player)) {
         ServerLevel expected = player.server.getLevel(IWantLimboData.limboDimension(player));
         if (expected == null) {
            expected = player.serverLevel();
         }

         BlockPos center = center(expected);
         boolean wrongLevel = player.serverLevel() != expected;
         boolean outsideRoom = !isInsideRoom(player.serverLevel(), player.blockPosition());
         if (wrongLevel || outsideRoom) {
            build(expected, center);
            detachOnlyPlayer(player);
            player.removeAllEffects();
            player.teleportTo(expected, center.getX() + 0.5, center.getY() + 1.0, center.getZ() + 0.5, 180.0F, 0.0F);
            player.setDeltaMovement(0.0, 0.0, 0.0);
            player.fallDistance = 0.0F;
         }
      }
   }

   private static void detachOnlyPlayer(ServerPlayer player) {
      player.stopRiding();
      player.ejectPassengers();
   }

   public static BlockPos cardPos(ServerPlayer player, int id) {
      ServerLevel level = player.server.getLevel(IWantLimboData.limboDimension(player));
      return cardPos(level == null ? player.serverLevel() : level, id);
   }

   public static BlockPos cardPos(ServerLevel level, int id) {
      BlockPos center = center(level);

      return switch (id) {
         case 0 -> center.offset(-5, 3, -5);
         case 1 -> center.offset(0, 3, -6);
         case 2 -> center.offset(5, 3, -5);
         case 3 -> center.offset(-6, 3, 0);
         case 4 -> center.offset(6, 3, 0);
         default -> center.offset(0, 3, -5);
      };
   }

   public static AABB bounds(ServerLevel level) {
      BlockPos center = center(level);
      return new AABB(center.getX() - 9, center.getY() - 1, center.getZ() - 9, center.getX() + 9 + 1, center.getY() + 8 + 1, center.getZ() + 9 + 1);
   }

   public static boolean isInsideRoom(ServerLevel level, BlockPos pos) {
      BlockPos center = center(level);
      return Math.abs(pos.getX() - center.getX()) <= 8
         && pos.getY() >= center.getY()
         && pos.getY() <= center.getY() + 8 - 1
         && Math.abs(pos.getZ() - center.getZ()) <= 8;
   }

   public static boolean isRoomBlock(ServerLevel level, BlockPos pos) {
      BlockPos center = center(level);
      return Math.abs(pos.getX() - center.getX()) <= 9
         && pos.getY() >= center.getY() - 1
         && pos.getY() <= center.getY() + 8
         && Math.abs(pos.getZ() - center.getZ()) <= 9;
   }

   public static void build(ServerLevel level, BlockPos center) {
      String key = level.dimension().location() + "|" + center.asLong();
      if (!BUILT.contains(key)) {
         BUILT.add(key);
         BlockState wall = Blocks.BLACK_CONCRETE.defaultBlockState();
         BlockState air = Blocks.AIR.defaultBlockState();

         for (int x = -9; x <= 9; x++) {
            for (int y = -1; y <= 8; y++) {
               for (int z = -9; z <= 9; z++) {
                  boolean edge = x == -9 || x == 9 || z == -9 || z == 9 || y == -1 || y == 8;
                  BlockPos pos = center.offset(x, y, z);
                  level.setBlock(pos, edge ? wall : air, 3);
               }
            }
         }
      }
   }
}
