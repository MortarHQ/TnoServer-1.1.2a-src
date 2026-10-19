package net.mcreator.rpgstylemorebosses.procedures;

import net.mcreator.rpgstylemorebosses.RpgStyleMoreBossesMod;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeEntity;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModEntities;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class TickSummonProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(RpgStyleMoreBossesModMobEffects.STUN, 60, 1, false, false));
         }

         if (entity instanceof TheExoframeEntity) {
            ((TheExoframeEntity)entity).setAnimation("summontick");
         }

         RpgStyleMoreBossesMod.queueServerWork(
            35,
            () -> {
               if (world.getBlockState(BlockPos.containing(entity.getX() + 4.0, entity.getY(), entity.getZ() + 4.0)).getBlock() == Blocks.AIR
                  && world instanceof ServerLevel _level) {
                  Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_TICK.get())
                     .spawn(_level, BlockPos.containing(entity.getX() + 4.0, entity.getY(), entity.getZ() + 4.0), MobSpawnType.MOB_SUMMONED);
                  if (entityToSpawn != null) {
                     entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                  }
               }
            }
         );
         RpgStyleMoreBossesMod.queueServerWork(
            40,
            () -> {
               if (Math.random() < 0.7
                  && world.getBlockState(BlockPos.containing(entity.getX() - 4.0, entity.getY(), entity.getZ() - 4.0)).getBlock() == Blocks.AIR
                  && world instanceof ServerLevel _level) {
                  Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_TICK.get())
                     .spawn(_level, BlockPos.containing(entity.getX() - 4.0, entity.getY(), entity.getZ() - 4.0), MobSpawnType.MOB_SUMMONED);
                  if (entityToSpawn != null) {
                     entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                  }
               }
            }
         );
         RpgStyleMoreBossesMod.queueServerWork(
            45,
            () -> {
               if (world.getBlockState(BlockPos.containing(entity.getX() + 4.0, entity.getY(), entity.getZ() - 4.0)).getBlock() == Blocks.AIR
                  && world instanceof ServerLevel _level) {
                  Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_TICK.get())
                     .spawn(_level, BlockPos.containing(entity.getX() + 4.0, entity.getY(), entity.getZ() - 4.0), MobSpawnType.MOB_SUMMONED);
                  if (entityToSpawn != null) {
                     entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                  }
               }
            }
         );
         RpgStyleMoreBossesMod.queueServerWork(
            50,
            () -> {
               if (Math.random() < 0.45
                  && world.getBlockState(BlockPos.containing(entity.getX() - 4.0, entity.getY(), entity.getZ() + 4.0)).getBlock() == Blocks.AIR
                  && world instanceof ServerLevel _level) {
                  Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_TICK.get())
                     .spawn(_level, BlockPos.containing(entity.getX() - 4.0, entity.getY(), entity.getZ() + 4.0), MobSpawnType.MOB_SUMMONED);
                  if (entityToSpawn != null) {
                     entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                  }
               }
            }
         );
         RpgStyleMoreBossesMod.queueServerWork(71, () -> {
            BlockPos _pos = BlockPos.containing(x, y, z);
            BlockState _bs = world.getBlockState(_pos);
            if (_bs.getBlock().getStateDefinition().getProperty("animation") instanceof IntegerProperty _integerProp) {
               world.setBlock(_pos, (BlockState)_bs.setValue(_integerProp, 0), 3);
            }
         });
      }
   }
}
