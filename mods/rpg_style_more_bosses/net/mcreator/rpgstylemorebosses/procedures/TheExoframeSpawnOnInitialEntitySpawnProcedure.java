package net.mcreator.rpgstylemorebosses.procedures;

import net.mcreator.rpgstylemorebosses.RpgStyleMoreBossesMod;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeEntity;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeSpawnEntity;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModEntities;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class TheExoframeSpawnOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:devilhand")),
                  SoundSource.AMBIENT,
                  0.3F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x,
                  y,
                  z,
                  (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:devilhand")),
                  SoundSource.AMBIENT,
                  0.3F,
                  1.0F,
                  false
               );
            }
         }

         if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(RpgStyleMoreBossesModMobEffects.STUN, 150, 1, false, false));
         }

         if (entity instanceof TheExoframeSpawnEntity) {
            ((TheExoframeSpawnEntity)entity).setAnimation("spawn");
         }

         RpgStyleMoreBossesMod.queueServerWork(
            17,
            () -> {
               if (world instanceof ServerLevel _level) {
                  _level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 200, 3.0, 0.2, 3.0, 0.1);
               }

               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:heavy_punch")),
                        SoundSource.NEUTRAL,
                        3.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:heavy_punch")),
                        SoundSource.NEUTRAL,
                        3.0F,
                        1.0F,
                        false
                     );
                  }
               }
            }
         );
         RpgStyleMoreBossesMod.queueServerWork(166, () -> {
            if (entity instanceof TheExoframeSpawnEntity) {
               ((TheExoframeSpawnEntity)entity).setAnimation("idle");
            }
         });
         RpgStyleMoreBossesMod.queueServerWork(
            170,
            () -> {
               if (entity instanceof TheExoframeEntity) {
                  ((TheExoframeEntity)entity).setAnimation("empty");
               }

               if (!entity.level().isClientSide()) {
                  entity.discard();
               }

               if (world instanceof ServerLevel _level) {
                  Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.THE_EXOFRAME.get())
                     .spawn(_level, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), MobSpawnType.MOB_SUMMONED);
                  if (entityToSpawn != null) {
                     entityToSpawn.setYRot(entity.getYRot());
                     entityToSpawn.setYBodyRot(entity.getYRot());
                     entityToSpawn.setYHeadRot(entity.getYRot());
                     entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                  }
               }
            }
         );
      }
   }
}
