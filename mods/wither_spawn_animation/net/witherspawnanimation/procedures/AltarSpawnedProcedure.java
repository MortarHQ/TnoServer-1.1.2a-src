package net.witherspawnanimation.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class AltarSpawnedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         Entity _ent = entity;
         _ent.setYRot(0.0F);
         _ent.setXRot(0.0F);
         _ent.setYBodyRot(_ent.getYRot());
         _ent.setYHeadRot(_ent.getYRot());
         _ent.yRotO = _ent.getYRot();
         _ent.xRotO = _ent.getXRot();
         if (_ent instanceof LivingEntity _entity) {
            _entity.yBodyRotO = _entity.getYRot();
            _entity.yHeadRotO = _entity.getYRot();
         }

         if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y + 2.0, z),
                  (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("wither_spawn_animation:tremble")),
                  SoundSource.HOSTILE,
                  1.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x,
                  y + 2.0,
                  z,
                  (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("wither_spawn_animation:tremble")),
                  SoundSource.HOSTILE,
                  1.0F,
                  1.0F,
                  false
               );
            }
         }

         entity.getPersistentData().putBoolean("smash", false);
         entity.getPersistentData().putBoolean("wither_1", false);
         entity.getPersistentData().putBoolean("star", false);
         entity.getPersistentData().putBoolean("wither_2", false);
         entity.getPersistentData().putBoolean("inverse_smash", false);
         entity.getPersistentData().putBoolean("wither_3", false);
         entity.getPersistentData().putBoolean("wither_spawn_mod", false);
      }
   }
}
