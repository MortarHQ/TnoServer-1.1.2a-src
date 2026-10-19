package net.mcreator.rpgstylemorebosses.procedures;

import java.util.Comparator;
import net.mcreator.rpgstylemorebosses.RpgStyleMoreBossesMod;
import net.mcreator.rpgstylemorebosses.entity.KriStrarzEntity;
import net.mcreator.rpgstylemorebosses.network.RpgStyleMoreBossesModVariables;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class KriStrarzOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) != null) {
            RpgStyleMoreBossesModVariables.MapVariables.get(world).BC++;
            RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
            if (RpgStyleMoreBossesModVariables.MapVariables.get(world).BC == 120.0) {
               if (entity instanceof KriStrarzEntity) {
                  ((KriStrarzEntity)entity).setAnimation("animation.KriStrarz.StunAttack");
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 5, false, false));
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 40, 5, false, false));
               }

               RpgStyleMoreBossesMod.queueServerWork(
                  12,
                  () -> {
                     Vec3 _center = new Vec3(
                        entity.level()
                           .clip(
                              new ClipContext(
                                 entity.getEyePosition(1.0F),
                                 entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(1.0)),
                                 Block.OUTLINE,
                                 Fluid.NONE,
                                 entity
                              )
                           )
                           .getBlockPos()
                           .getX(),
                        entity.level()
                           .clip(
                              new ClipContext(
                                 entity.getEyePosition(1.0F),
                                 entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(1.0)),
                                 Block.OUTLINE,
                                 Fluid.NONE,
                                 entity
                              )
                           )
                           .getBlockPos()
                           .getY(),
                        entity.level()
                           .clip(
                              new ClipContext(
                                 entity.getEyePosition(1.0F),
                                 entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(1.0)),
                                 Block.OUTLINE,
                                 Fluid.NONE,
                                 entity
                              )
                           )
                           .getBlockPos()
                           .getZ()
                     );

                     for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(2.5), e -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                        .toList()) {
                        if (!(entityiterator instanceof KriStrarzEntity)) {
                           entityiterator.hurt(new DamageSource(world.holderOrThrow(DamageTypes.GENERIC)), 2.0F);
                           if (entityiterator instanceof LivingEntity _entityx && !_entityx.level().isClientSide()) {
                              _entityx.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 5, false, false));
                           }

                           if (world instanceof ServerLevel _level) {
                              _level.sendParticles(
                                 ParticleTypes.CRIT, entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), 20, 1.0, 1.5, 1.0, 0.0
                              );
                           }

                           RpgStyleMoreBossesMod.queueServerWork(
                              10,
                              () -> {
                                 entityiterator.push(
                                    entity.level()
                                       .clip(
                                          new ClipContext(
                                             entity.getEyePosition(1.0F),
                                             entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(6.0)),
                                             Block.OUTLINE,
                                             Fluid.NONE,
                                             entity
                                          )
                                       )
                                       .getBlockPos()
                                       .getX(),
                                    entity.getY() + 5.0,
                                    entity.level()
                                       .clip(
                                          new ClipContext(
                                             entity.getEyePosition(1.0F),
                                             entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(6.0)),
                                             Block.OUTLINE,
                                             Fluid.NONE,
                                             entity
                                          )
                                       )
                                       .getBlockPos()
                                       .getZ()
                                 );
                                 entityiterator.hurt(new DamageSource(world.holderOrThrow(DamageTypes.GENERIC)), 2.0F);
                                 if (world instanceof ServerLevel _level) {
                                    _level.sendParticles(
                                       ParticleTypes.CRIT, entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), 20, 1.0, 1.5, 1.0, 0.0
                                    );
                                 }
                              }
                           );
                        }
                     }
                  }
               );
            }

            if (RpgStyleMoreBossesModVariables.MapVariables.get(world).BC == 240.0) {
               if (entity instanceof KriStrarzEntity) {
                  ((KriStrarzEntity)entity).setAnimation("animation.KriStrarz.Attack");
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 5, false, false));
               }

               RpgStyleMoreBossesMod.queueServerWork(
                  20,
                  () -> {
                     Vec3 _center = new Vec3(
                        entity.level()
                           .clip(
                              new ClipContext(
                                 entity.getEyePosition(1.0F),
                                 entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(1.0)),
                                 Block.OUTLINE,
                                 Fluid.NONE,
                                 entity
                              )
                           )
                           .getBlockPos()
                           .getX(),
                        entity.level()
                           .clip(
                              new ClipContext(
                                 entity.getEyePosition(1.0F),
                                 entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(1.0)),
                                 Block.OUTLINE,
                                 Fluid.NONE,
                                 entity
                              )
                           )
                           .getBlockPos()
                           .getY(),
                        entity.level()
                           .clip(
                              new ClipContext(
                                 entity.getEyePosition(1.0F),
                                 entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(1.0)),
                                 Block.OUTLINE,
                                 Fluid.NONE,
                                 entity
                              )
                           )
                           .getBlockPos()
                           .getZ()
                     );

                     for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(2.5), e -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                        .toList()) {
                        if (!(entityiterator instanceof KriStrarzEntity)) {
                           entityiterator.hurt(new DamageSource(world.holderOrThrow(DamageTypes.GENERIC)), 2.0F);
                           if (world instanceof ServerLevel _level) {
                              _level.sendParticles(
                                 ParticleTypes.CRIT, entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), 20, 1.0, 1.5, 1.0, 0.0
                              );
                           }
                        }
                     }
                  }
               );
               RpgStyleMoreBossesModVariables.MapVariables.get(world).BC = 0.0;
               RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
            }
         }
      }
   }
}
