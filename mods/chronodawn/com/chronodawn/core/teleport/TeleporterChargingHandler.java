package com.chronodawn.core.teleport;

import com.chronodawn.blocks.ClockTowerTeleporterBlock;
import com.chronodawn.blocks.ClockTowerTeleporterBlockEntity;
import com.chronodawn.registry.ModSounds;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class TeleporterChargingHandler {
   private static final int CHARGE_DURATION_TICKS = 60;
   private static final double MAX_DISTANCE = 3.0;
   private static final DustParticleOptions ORANGE_PARTICLE = new DustParticleOptions(new Vector3f(0.859F, 0.533F, 0.075F), 1.0F);
   private static final Map<UUID, TeleporterChargingHandler.ChargingData> CHARGING_PLAYERS = new ConcurrentHashMap<>();

   public static void startCharging(ServerPlayer player, BlockPos teleporterPos, String direction, long startTime) {
      CHARGING_PLAYERS.put(player.getUUID(), new TeleporterChargingHandler.ChargingData(startTime, teleporterPos, direction));
      ServerLevel level = (ServerLevel)player.level();
      level.playSound(null, teleporterPos, (SoundEvent)ModSounds.TELEPORTER_CHARGE_START.get(), SoundSource.BLOCKS, 0.8F, 1.5F);

      for (int i = 0; i < 20; i++) {
         double offsetX = (level.random.nextDouble() - 0.5) * 1.5;
         double offsetY = level.random.nextDouble() * 1.5;
         double offsetZ = (level.random.nextDouble() - 0.5) * 1.5;
         level.sendParticles(
            ORANGE_PARTICLE,
            teleporterPos.getX() + 0.5 + offsetX,
            teleporterPos.getY() + 0.5 + offsetY,
            teleporterPos.getZ() + 0.5 + offsetZ,
            1,
            0.0,
            0.0,
            0.0,
            0.05
         );
      }
   }

   public static void tick(ServerPlayer player) {
      TeleporterChargingHandler.ChargingData data = CHARGING_PLAYERS.get(player.getUUID());
      if (data != null) {
         ServerLevel level = (ServerLevel)player.level();
         long currentTime = level.getGameTime();
         long elapsedTicks = currentTime - data.startTime;
         double distance = player.position().distanceTo(Vec3.atCenterOf(data.teleporterPos));
         if (distance > 3.0) {
            cancelCharging(player);
         } else {
            spawnChargingParticles(level, data.teleporterPos, player.position(), elapsedTicks);
            if (elapsedTicks == 20L) {
               level.playSound(null, data.teleporterPos, (SoundEvent)ModSounds.TELEPORTER_CHARGING.get(), SoundSource.BLOCKS, 0.5F, 1.0F);
            } else if (elapsedTicks == 40L) {
               level.playSound(null, data.teleporterPos, (SoundEvent)ModSounds.TELEPORTER_CHARGING.get(), SoundSource.BLOCKS, 0.7F, 1.2F);
            }

            if (elapsedTicks >= 60L) {
               performTeleport(player, level, data.teleporterPos, data.direction);
               cancelCharging(player);
            }
         }
      }
   }

   private static void spawnChargingParticles(ServerLevel level, BlockPos teleporterPos, Vec3 playerPos, long elapsedTicks) {
      double progress = Math.min(1.0, elapsedTicks / 60.0);
      int particleCount = (int)(2.0 + progress * 3.0);

      for (int i = 0; i < particleCount; i++) {
         double angle = (elapsedTicks + i) * 0.2;
         double radius = 1.0 + progress * 0.5;
         double offsetX = Math.cos(angle) * radius;
         double offsetY = progress * 2.0;
         double offsetZ = Math.sin(angle) * radius;
         level.sendParticles(
            ORANGE_PARTICLE,
            teleporterPos.getX() + 0.5 + offsetX,
            teleporterPos.getY() + 0.5 + offsetY,
            teleporterPos.getZ() + 0.5 + offsetZ,
            1,
            0.0,
            0.0,
            0.0,
            0.05
         );
      }

      if (progress > 0.66) {
         for (int i = 0; i < 2; i++) {
            double offsetX = (level.random.nextDouble() - 0.5) * 0.8;
            double offsetY = level.random.nextDouble() * 1.5;
            double offsetZ = (level.random.nextDouble() - 0.5) * 0.8;
            level.sendParticles(
               ParticleTypes.FLAME,
               teleporterPos.getX() + 0.5 + offsetX,
               teleporterPos.getY() + 0.5 + offsetY,
               teleporterPos.getZ() + 0.5 + offsetZ,
               1,
               0.0,
               0.1,
               0.0,
               0.02
            );
         }
      }
   }

   private static void performTeleport(ServerPlayer player, ServerLevel level, BlockPos teleporterPos, String direction) {
      BlockState state = level.getBlockState(teleporterPos);
      if (state.getBlock() instanceof ClockTowerTeleporterBlock) {
         BlockPos targetPos;
         if (direction.equals("DOWN")) {
            if (level.getBlockEntity(teleporterPos) instanceof ClockTowerTeleporterBlockEntity be) {
               BlockPos storedTarget = be.getTargetPos();
               if (storedTarget != null) {
                  targetPos = storedTarget;
               } else {
                  int floorHeight = 8;
                  targetPos = teleporterPos.offset(0, -floorHeight, 0);
               }
            } else {
               int floorHeight = 8;
               targetPos = teleporterPos.offset(0, -floorHeight, 0);
            }
         } else {
            int floorHeight = 8;
            targetPos = teleporterPos.offset(0, floorHeight, 0);
         }

         Vec3 safeLandingPos = findSafeLandingPosition(level, targetPos, direction.equals("DOWN"));
         level.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.TELEPORTER_WARP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
         level.sendParticles(ParticleTypes.PORTAL, player.getX(), player.getY() + 1.0, player.getZ(), 50, 0.5, 0.5, 0.5, 0.2);
         player.teleportTo(safeLandingPos.x, safeLandingPos.y, safeLandingPos.z);
         level.playSound(null, targetPos, (SoundEvent)ModSounds.TELEPORTER_WARP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
         level.sendParticles(ParticleTypes.PORTAL, targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5, 50, 0.5, 0.5, 0.5, 0.2);
      }
   }

   public static void cancelCharging(ServerPlayer player) {
      CHARGING_PLAYERS.remove(player.getUUID());
   }

   public static boolean isCharging(ServerPlayer player) {
      return CHARGING_PLAYERS.containsKey(player.getUUID());
   }

   private static Vec3 findSafeLandingPosition(ServerLevel level, BlockPos targetPos, boolean isDownTeleport) {
      int floorOffset = isDownTeleport ? -1 : -3;
      BlockPos[] candidateOffsets = new BlockPos[]{
         targetPos.offset(2, floorOffset, 0),
         targetPos.offset(-2, floorOffset, 0),
         targetPos.offset(0, floorOffset, 2),
         targetPos.offset(0, floorOffset, -2),
         targetPos.offset(1, floorOffset, 1),
         targetPos.offset(-1, floorOffset, -1),
         targetPos.offset(1, floorOffset, -1),
         targetPos.offset(-1, floorOffset, 1),
         targetPos.offset(1, floorOffset, 0),
         targetPos.offset(-1, floorOffset, 0),
         targetPos.offset(0, floorOffset, 1),
         targetPos.offset(0, floorOffset, -1)
      };

      for (BlockPos candidate : candidateOffsets) {
         if (isSafeLandingPosition(level, candidate)) {
            return new Vec3(candidate.getX() + 0.5, candidate.getY(), candidate.getZ() + 0.5);
         }
      }

      double fallbackY = isDownTeleport ? targetPos.getY() - 1.0 : targetPos.getY() - 3.0;
      return new Vec3(targetPos.getX() + 2.5, fallbackY, targetPos.getZ() + 0.5);
   }

   private static boolean isSafeLandingPosition(ServerLevel level, BlockPos pos) {
      if (!level.getBlockState(pos.below()).isSolid()) {
         return false;
      }

      for (int i = 0; i < 2; i++) {
         BlockPos checkPos = pos.above(i);
         BlockState blockState = level.getBlockState(checkPos);
         if (blockState.isSolid()) {
            return false;
         }
      }

      return true;
   }

   private static class ChargingData {
      final long startTime;
      final BlockPos teleporterPos;
      final String direction;

      ChargingData(long startTime, BlockPos teleporterPos, String direction) {
         this.startTime = startTime;
         this.teleporterPos = teleporterPos;
         this.direction = direction;
      }
   }
}
