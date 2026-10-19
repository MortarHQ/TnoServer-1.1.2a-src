package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.AbstractBossEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;
import org.jetbrains.annotations.NotNull;

public class AbstractBossOnEntityTickUpdateProcedure {
   public static Direction getDirection(BlockState state) {
      if (state.getBlock().getStateDefinition().getProperty("facing") instanceof DirectionProperty _dp) {
         return (Direction)state.getValue(_dp);
      } else {
         return state.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty _ep && _ep.getPossibleValues().toArray()[0] instanceof Axis
            ? Direction.fromAxisAndDirection((Axis)state.getValue(_ep), AxisDirection.POSITIVE)
            : Direction.NORTH;
      }
   }

   public static void updateRotations(@NotNull AbstractBossEntity boss) {
      boss.setYRot((float)boss.getPersistentData().getDouble("angle"));
      boss.setXRot(0.0F);
      boss.setYBodyRot(boss.getYRot());
      boss.setYHeadRot(boss.getYRot());
      boss.yRotO = boss.getYRot();
      boss.xRotO = boss.getXRot();
      boss.yBodyRotO = boss.getYRot();
      boss.yHeadRotO = boss.getYRot();
   }

   protected static boolean onDying(@NotNull AbstractBossEntity boss, LevelAccessor world) {
      SynchedEntityData entityData = boss.getEntityData();
      int dieAnimtime = (Integer)entityData.get(AbstractBossEntity.DATA_DIE_ANIMTIME);
      if (dieAnimtime > 0) {
         boss.setDeltaMovement(new Vec3(0.0, -1.0, 0.0));
         entityData.set(AbstractBossEntity.DATA_ATTACK_ANIMTIME, 0);
         entityData.set(AbstractBossEntity.DATA_HIT_ANIMTIME, 0);
         entityData.set(AbstractBossEntity.DATA_DIE_ANIMTIME, --dieAnimtime);
         if (dieAnimtime == 1) {
            boss.hurt(new DamageSource(world.holderOrThrow(DamageTypes.PLAYER_ATTACK), boss.getSourceEntity()), 999.0F);
         }

         return true;
      } else {
         return false;
      }
   }

   protected static void incrementAttackCooldown(@NotNull AbstractBossEntity boss, double aabbSize) {
      if (boss.isTargetNear(aabbSize)) {
         SynchedEntityData entityData = boss.getEntityData();
         entityData.set(AbstractBossEntity.DATA_ATTACK_COOLDOWN, (Integer)entityData.get(AbstractBossEntity.DATA_ATTACK_COOLDOWN) + 1);
      }
   }

   protected static boolean onSpawn(@NotNull AbstractBossEntity boss, LevelAccessor world, int startSpawnAnimtime) {
      SynchedEntityData entityData = boss.getEntityData();
      int spawnAnimtime = (Integer)entityData.get(AbstractBossEntity.DATA_SPAWN_ANIMTIME);
      if (spawnAnimtime <= 0) {
         return false;
      }

      CompoundTag persistentData = boss.getPersistentData();
      double x = boss.getX();
      double y = boss.getY();
      double z = boss.getZ();
      boss.setHealth((float)boss.getAttributeValue(Attributes.MAX_HEALTH));
      boss.setDeltaMovement(Vec3.ZERO);
      if (!world.isClientSide() && spawnAnimtime == startSpawnAnimtime) {
         boss.forEachNearbyPlayer(32.0, player -> {
            double xOffset = Math.abs(x - player.getX()) + Math.abs(z - player.getZ());
            player.teleportTo(x + xOffset * 0.5, y, z + (Math.random() - 0.5) * 4.0);
            double angle = persistentData.getDouble("angle");
            double random = (Math.random() - 0.5) * 4.0;
            double tpX = 0.0;
            double tpZ = 0.0;
            if (angle == 0.0) {
               tpX = random;
               tpZ = xOffset * 0.75;
            } else if (angle == 180.0) {
               tpX = random;
               tpZ = xOffset * -0.75;
            } else if (angle == -90.0) {
               tpX = xOffset * 0.75;
               tpZ = random;
            } else if (angle == 90.0) {
               tpX = xOffset * -0.75;
               tpZ = random;
            }

            player.teleportTo(x + tpX, y, z + tpZ);
            BlockPos blockPos = BlockPos.containing(x, y, z);
            if (world.getBlockState(blockPos).getBlock() == BossesRiseBlocks.BOSS_SPAWNER.get()) {
               world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
            }
         });
      }

      updateRotations(boss);
      if (spawnAnimtime > 10 && spawnAnimtime <= startSpawnAnimtime) {
         boss.forEachNearbyPlayer(32.0, player -> {
            player.setDeltaMovement(Vec3.ZERO);
            player.setYRot((float)(persistentData.getDouble("angle") - 180.0));
            player.setXRot(0.0F);
            player.setYBodyRot(player.getYRot());
            player.setYHeadRot(player.getYRot());
            player.yRotO = player.getYRot();
            player.xRotO = player.getXRot();
            player.yBodyRotO = player.getYRot();
            player.yHeadRotO = player.getYRot();
         });
      }

      entityData.set(AbstractBossEntity.DATA_SPAWN_ANIMTIME, spawnAnimtime - 1);
      return true;
   }
}
