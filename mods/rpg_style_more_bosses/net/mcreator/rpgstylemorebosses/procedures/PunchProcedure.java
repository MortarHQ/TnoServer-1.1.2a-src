package net.mcreator.rpgstylemorebosses.procedures;

import java.util.Comparator;
import net.mcreator.rpgstylemorebosses.RpgStyleMoreBossesMod;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeEntity;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PunchProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof TheExoframeEntity) {
            ((TheExoframeEntity)entity).setAnimation("punch");
         }

         if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(RpgStyleMoreBossesModMobEffects.STUN, 40, 0, false, false));
         }

         RpgStyleMoreBossesMod.queueServerWork(
            10,
            () -> {
               Vec3 _center = new Vec3(entity.getX() + entity.getLookAngle().x * 1.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 1.0);

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.5), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                  .toList()) {
                  if (entityiterator instanceof Player || entityiterator instanceof ServerPlayer) {
                     entityiterator.hurt(new DamageSource(world.holderOrThrow(DamageTypes.PLAYER_ATTACK)), 15.0F);
                     if (entityiterator instanceof LivingEntity _entityx && !_entityx.level().isClientSide()) {
                        _entityx.addEffect(new MobEffectInstance(RpgStyleMoreBossesModMobEffects.STUN, 20, 0, false, false));
                     }

                     if (world instanceof ServerLevel _level) {
                        _level.sendParticles(ParticleTypes.CRIT, entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), 20, 1.0, 1.5, 1.0, 0.0);
                     }

                     entityiterator.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
                  }
               }

               if (Math.random() < 0.5) {
                  if (world instanceof Level _level) {
                     if (!_level.isClientSide()) {
                        _level.playSound(
                           null,
                           BlockPos.containing(x, y, z),
                           (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:pucnh1")),
                           SoundSource.NEUTRAL,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:pucnh1")),
                           SoundSource.NEUTRAL,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }
               } else if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:punch2")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:punch2")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }
            }
         );
         RpgStyleMoreBossesMod.queueServerWork(
            22,
            () -> {
               Vec3 _center = new Vec3(entity.getX() + entity.getLookAngle().x * 1.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 1.0);

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                  .toList()) {
                  if (entityiterator instanceof Player || entityiterator instanceof ServerPlayer) {
                     entityiterator.hurt(new DamageSource(world.holderOrThrow(DamageTypes.PLAYER_ATTACK)), 20.0F);
                     if (world instanceof ServerLevel _level) {
                        _level.sendParticles(ParticleTypes.CRIT, entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), 20, 1.0, 1.5, 1.0, 0.0);
                     }

                     entityiterator.setDeltaMovement(
                        new Vec3(
                           entity.getDeltaMovement().x() + entity.getLookAngle().x * 1.5,
                           entity.getDeltaMovement().y() + 1.5 + entity.getLookAngle().y * 2.5,
                           entity.getDeltaMovement().z() + entity.getLookAngle().z * 1.5
                        )
                     );
                  }
               }

               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:heavy_punch")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:heavy_punch")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }
            }
         );
      }
   }
}
