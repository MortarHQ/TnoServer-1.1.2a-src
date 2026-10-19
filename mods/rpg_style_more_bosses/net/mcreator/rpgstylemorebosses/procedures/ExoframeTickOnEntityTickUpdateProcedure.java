package net.mcreator.rpgstylemorebosses.procedures;

import java.util.Comparator;
import net.mcreator.rpgstylemorebosses.RpgStyleMoreBossesMod;
import net.mcreator.rpgstylemorebosses.entity.ExoframeTickEntity;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModMobEffects;
import net.mcreator.rpgstylemorebosses.network.RpgStyleMoreBossesModVariables;
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
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ExoframeTickOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!((RpgStyleMoreBossesModVariables.PlayerVariables)entity.getData(RpgStyleMoreBossesModVariables.PLAYER_VARIABLES)).TickExplosion
            && !world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(entity.getX(), entity.getY(), entity.getZ()), 4.0, 4.0, 4.0), e -> true).isEmpty()) {
            RpgStyleMoreBossesModVariables.PlayerVariables _vars = (RpgStyleMoreBossesModVariables.PlayerVariables)entity.getData(
               RpgStyleMoreBossesModVariables.PLAYER_VARIABLES
            );
            _vars.TickExplosion = true;
            _vars.syncPlayerVariables(entity);
            if (entity instanceof ExoframeTickEntity) {
               ((ExoframeTickEntity)entity).setAnimation("explosion");
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 6, false, false));
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(RpgStyleMoreBossesModMobEffects.STUN, 100, 1, false, false));
            }

            RpgStyleMoreBossesMod.queueServerWork(
               5,
               () -> {
                  if (world instanceof Level _level) {
                     if (!_level.isClientSide()) {
                        _level.playSound(
                           null,
                           BlockPos.containing(x, y, z),
                           (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:lasertagretplanetside")),
                           SoundSource.AMBIENT,
                           0.7F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:lasertagretplanetside")),
                           SoundSource.AMBIENT,
                           0.7F,
                           1.0F,
                           false
                        );
                     }
                  }

                  Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(7.5), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiterator instanceof Player || entityiterator instanceof ServerPlayer) {
                        entityiterator.setDeltaMovement(
                           new Vec3(
                              entity.getDeltaMovement().x() + entity.getLookAngle().x * 1.0, 0.0, entity.getDeltaMovement().z() + entity.getLookAngle().z * 1.0
                           )
                        );
                     }
                  }

                  RpgStyleMoreBossesMod.queueServerWork(
                     10,
                     () -> {
                        Vec3 _centerx = new Vec3(entity.getX(), entity.getY(), entity.getZ());

                        for (Entity entityiteratorx : world.getEntitiesOfClass(Entity.class, new AABB(_centerx, _centerx).inflate(7.5), e -> true)
                           .stream()
                           .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_centerx)))
                           .toList()) {
                           if ((entityiteratorx instanceof Player || entityiteratorx instanceof ServerPlayer) && entityiteratorx instanceof Mob _entityx) {
                              _entityx.getNavigation().moveTo(entity.getX(), entity.getY(), entity.getZ(), 100.0);
                           }
                        }

                        RpgStyleMoreBossesMod.queueServerWork(
                           10,
                           () -> {
                              Vec3 _centerxx = new Vec3(entity.getX(), entity.getY(), entity.getZ());

                              for (Entity entityiteratorxx : world.getEntitiesOfClass(Entity.class, new AABB(_centerxx, _centerxx).inflate(7.5), e -> true)
                                 .stream()
                                 .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_centerxx)))
                                 .toList()) {
                                 if ((entityiteratorxx instanceof Player || entityiteratorxx instanceof ServerPlayer)
                                    && entityiteratorxx instanceof Mob _entityxx) {
                                    _entityxx.getNavigation().moveTo(entity.getX(), entity.getY(), entity.getZ(), 1.0);
                                 }
                              }

                              RpgStyleMoreBossesMod.queueServerWork(
                                 5,
                                 () -> {
                                    Vec3 _centerxxx = new Vec3(entity.getX(), entity.getY(), entity.getZ());

                                    for (Entity entityiteratorxxx : world.getEntitiesOfClass(
                                          Entity.class, new AABB(_centerxxx, _centerxxx).inflate(7.5), e -> true
                                       )
                                       .stream()
                                       .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_centerxxx)))
                                       .toList()) {
                                       if ((entityiteratorxxx instanceof Player || entityiteratorxxx instanceof ServerPlayer)
                                          && entityiteratorxxx instanceof Mob _entityxxx) {
                                          _entityxxx.getNavigation().moveTo(entity.getX(), entity.getY(), entity.getZ(), 1.0);
                                       }
                                    }

                                    RpgStyleMoreBossesMod.queueServerWork(
                                       5,
                                       () -> {
                                          Vec3 _centerxxxx = new Vec3(entity.getX(), entity.getY(), entity.getZ());

                                          for (Entity entityiteratorxxxx : world.getEntitiesOfClass(
                                                Entity.class, new AABB(_centerxxxx, _centerxxxx).inflate(7.5), e -> true
                                             )
                                             .stream()
                                             .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_centerxxxx)))
                                             .toList()) {
                                             if ((entityiteratorxxxx instanceof Player || entityiteratorxxxx instanceof ServerPlayer)
                                                && entityiteratorxxxx instanceof Mob _entityxxxx) {
                                                _entityxxxx.getNavigation().moveTo(entity.getX(), entity.getY(), entity.getZ(), 1.0);
                                             }
                                          }
                                       }
                                    );
                                 }
                              );
                           }
                        );
                     }
                  );
               }
            );
            RpgStyleMoreBossesMod.queueServerWork(
               41,
               () -> {
                  if (Math.random() < 0.5) {
                     if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                           _level.playSound(
                              null,
                              BlockPos.containing(x, y, z),
                              (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:explosion_small_planetside1")),
                              SoundSource.AMBIENT,
                              1.0F,
                              1.0F
                           );
                        } else {
                           _level.playLocalSound(
                              x,
                              y,
                              z,
                              (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:explosion_small_planetside1")),
                              SoundSource.AMBIENT,
                              1.0F,
                              1.0F,
                              false
                           );
                        }
                     }
                  } else if (Math.random() < 0.5) {
                     if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                           _level.playSound(
                              null,
                              BlockPos.containing(x, y, z),
                              (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:explosion_small_planetside2")),
                              SoundSource.AMBIENT,
                              1.0F,
                              1.0F
                           );
                        } else {
                           _level.playLocalSound(
                              x,
                              y,
                              z,
                              (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:explosion_small_planetside2")),
                              SoundSource.AMBIENT,
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
                           (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:explosion_small_planetside3")),
                           SoundSource.AMBIENT,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("rpg_style_more_bosses:explosion_small_planetside3")),
                           SoundSource.AMBIENT,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }

                  Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5.75), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiterator instanceof Player || entityiterator instanceof ServerPlayer) {
                        entityiterator.hurt(new DamageSource(world.holderOrThrow(DamageTypes.PLAYER_ATTACK)), 30.0F);
                        if (entityiterator instanceof LivingEntity _entityx && !_entityx.level().isClientSide()) {
                           _entityx.addEffect(new MobEffectInstance(RpgStyleMoreBossesModMobEffects.STUN, 20, 1, false, false));
                        }

                        if (world instanceof ServerLevel _level) {
                           _level.sendParticles(ParticleTypes.CRIT, entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), 20, 1.0, 1.5, 1.0, 0.0);
                        }
                     }
                  }
               }
            );
            RpgStyleMoreBossesMod.queueServerWork(71, () -> {
               if (!entity.level().isClientSide()) {
                  entity.discard();
               }
            });
         }
      }
   }
}
