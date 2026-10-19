package net.mcreator.rpgstylemorebosses.procedures;

import java.util.Comparator;
import net.mcreator.rpgstylemorebosses.RpgStyleMoreBossesMod;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeEntity;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModEntities;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ChargeProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (entity instanceof TheExoframeEntity) {
            ((TheExoframeEntity)entity).setAnimation("callorbitalstrike1");
         }

         if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(RpgStyleMoreBossesModMobEffects.STUN, 153, 0, false, false));
         }

         RpgStyleMoreBossesMod.queueServerWork(
            61,
            () -> {
               if (Math.random() < 0.34) {
                  Vec3 _center = new Vec3(entity.getX() + entity.getLookAngle().x * 1.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 1.0);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(60.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if ((entityiterator instanceof Player || entityiterator instanceof ServerPlayer) && world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                           .spawn(_level, BlockPos.containing(entityiterator.getX(), entity.getY(), entityiterator.getZ()), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                           entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                        }
                     }
                  }
               } else if (Math.random() < 0.5) {
                  Vec3 _center = new Vec3(entity.getX() + entity.getLookAngle().x * 1.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 1.0);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(60.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiterator instanceof Player || entityiterator instanceof ServerPlayer) {
                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                              .spawn(
                                 _level,
                                 BlockPos.containing(entityiterator.getX() + 3.0, entity.getY(), entityiterator.getZ() + 3.0),
                                 MobSpawnType.MOB_SUMMONED
                              );
                           if (entityToSpawn != null) {
                              entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                           }
                        }

                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                              .spawn(
                                 _level,
                                 BlockPos.containing(entityiterator.getX() - 3.0, entity.getY(), entityiterator.getZ() - 3.0),
                                 MobSpawnType.MOB_SUMMONED
                              );
                           if (entityToSpawn != null) {
                              entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                           }
                        }

                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                              .spawn(
                                 _level,
                                 BlockPos.containing(entityiterator.getX() + 3.0, entity.getY(), entityiterator.getZ() - 3.0),
                                 MobSpawnType.MOB_SUMMONED
                              );
                           if (entityToSpawn != null) {
                              entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                           }
                        }

                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                              .spawn(
                                 _level,
                                 BlockPos.containing(entityiterator.getX() - 3.0, entity.getY(), entityiterator.getZ() + 3.0),
                                 MobSpawnType.MOB_SUMMONED
                              );
                           if (entityToSpawn != null) {
                              entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                           }
                        }
                     }
                  }
               } else {
                  Vec3 _center = new Vec3(entity.getX() + entity.getLookAngle().x * 1.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 1.0);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(60.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if ((entityiterator instanceof Player || entityiterator instanceof ServerPlayer) && world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                           .spawn(
                              _level,
                              BlockPos.containing(
                                 entityiterator.level()
                                       .clip(
                                          new ClipContext(
                                             entityiterator.getEyePosition(1.0F),
                                             entityiterator.getEyePosition(1.0F).add(entityiterator.getViewVector(1.0F).scale(4.0)),
                                             Block.OUTLINE,
                                             Fluid.NONE,
                                             entityiterator
                                          )
                                       )
                                       .getBlockPos()
                                       .getX()
                                    + 0.5,
                                 entity.getY(),
                                 entityiterator.level()
                                       .clip(
                                          new ClipContext(
                                             entityiterator.getEyePosition(1.0F),
                                             entityiterator.getEyePosition(1.0F).add(entityiterator.getViewVector(1.0F).scale(4.0)),
                                             Block.OUTLINE,
                                             Fluid.NONE,
                                             entityiterator
                                          )
                                       )
                                       .getBlockPos()
                                       .getZ()
                                    + 0.5
                              ),
                              MobSpawnType.MOB_SUMMONED
                           );
                        if (entityToSpawn != null) {
                           entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                        }
                     }
                  }
               }
            }
         );
         RpgStyleMoreBossesMod.queueServerWork(
            76,
            () -> {
               if (Math.random() < 0.34) {
                  Vec3 _center = new Vec3(entity.getX() + entity.getLookAngle().x * 1.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 1.0);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(60.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if ((entityiterator instanceof Player || entityiterator instanceof ServerPlayer) && world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                           .spawn(_level, BlockPos.containing(entityiterator.getX(), entity.getY(), entityiterator.getZ()), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                           entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                        }
                     }
                  }
               } else if (Math.random() < 0.5) {
                  Vec3 _center = new Vec3(entity.getX() + entity.getLookAngle().x * 1.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 1.0);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(60.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiterator instanceof Player || entityiterator instanceof ServerPlayer) {
                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                              .spawn(
                                 _level,
                                 BlockPos.containing(entityiterator.getX() + 3.0, entity.getY(), entityiterator.getZ() + 3.0),
                                 MobSpawnType.MOB_SUMMONED
                              );
                           if (entityToSpawn != null) {
                              entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                           }
                        }

                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                              .spawn(
                                 _level,
                                 BlockPos.containing(entityiterator.getX() - 3.0, entity.getY(), entityiterator.getZ() - 3.0),
                                 MobSpawnType.MOB_SUMMONED
                              );
                           if (entityToSpawn != null) {
                              entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                           }
                        }

                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                              .spawn(
                                 _level,
                                 BlockPos.containing(entityiterator.getX() + 3.0, entity.getY(), entityiterator.getZ() - 3.0),
                                 MobSpawnType.MOB_SUMMONED
                              );
                           if (entityToSpawn != null) {
                              entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                           }
                        }

                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                              .spawn(
                                 _level,
                                 BlockPos.containing(entityiterator.getX() - 3.0, entity.getY(), entityiterator.getZ() + 3.0),
                                 MobSpawnType.MOB_SUMMONED
                              );
                           if (entityToSpawn != null) {
                              entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                           }
                        }
                     }
                  }
               } else {
                  Vec3 _center = new Vec3(entity.getX() + entity.getLookAngle().x * 1.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 1.0);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(60.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if ((entityiterator instanceof Player || entityiterator instanceof ServerPlayer) && world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                           .spawn(
                              _level,
                              BlockPos.containing(
                                 entityiterator.level()
                                       .clip(
                                          new ClipContext(
                                             entityiterator.getEyePosition(1.0F),
                                             entityiterator.getEyePosition(1.0F).add(entityiterator.getViewVector(1.0F).scale(4.0)),
                                             Block.OUTLINE,
                                             Fluid.NONE,
                                             entityiterator
                                          )
                                       )
                                       .getBlockPos()
                                       .getX()
                                    + 0.5,
                                 entity.getY(),
                                 entityiterator.level()
                                       .clip(
                                          new ClipContext(
                                             entityiterator.getEyePosition(1.0F),
                                             entityiterator.getEyePosition(1.0F).add(entityiterator.getViewVector(1.0F).scale(4.0)),
                                             Block.OUTLINE,
                                             Fluid.NONE,
                                             entityiterator
                                          )
                                       )
                                       .getBlockPos()
                                       .getZ()
                                    + 0.5
                              ),
                              MobSpawnType.MOB_SUMMONED
                           );
                        if (entityToSpawn != null) {
                           entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                        }
                     }
                  }
               }
            }
         );
         RpgStyleMoreBossesMod.queueServerWork(
            91,
            () -> {
               if (Math.random() < 0.34) {
                  Vec3 _center = new Vec3(entity.getX() + entity.getLookAngle().x * 1.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 1.0);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(60.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if ((entityiterator instanceof Player || entityiterator instanceof ServerPlayer) && world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                           .spawn(_level, BlockPos.containing(entityiterator.getX(), entity.getY(), entityiterator.getZ()), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                           entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                        }
                     }
                  }
               } else if (Math.random() < 0.5) {
                  Vec3 _center = new Vec3(entity.getX() + entity.getLookAngle().x * 1.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 1.0);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(60.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if (entityiterator instanceof Player || entityiterator instanceof ServerPlayer) {
                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                              .spawn(
                                 _level,
                                 BlockPos.containing(entityiterator.getX() + 3.0, entity.getY(), entityiterator.getZ() + 3.0),
                                 MobSpawnType.MOB_SUMMONED
                              );
                           if (entityToSpawn != null) {
                              entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                           }
                        }

                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                              .spawn(
                                 _level,
                                 BlockPos.containing(entityiterator.getX() - 3.0, entity.getY(), entityiterator.getZ() - 3.0),
                                 MobSpawnType.MOB_SUMMONED
                              );
                           if (entityToSpawn != null) {
                              entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                           }
                        }

                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                              .spawn(
                                 _level,
                                 BlockPos.containing(entityiterator.getX() + 3.0, entity.getY(), entityiterator.getZ() - 3.0),
                                 MobSpawnType.MOB_SUMMONED
                              );
                           if (entityToSpawn != null) {
                              entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                           }
                        }

                        if (world instanceof ServerLevel _level) {
                           Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                              .spawn(
                                 _level,
                                 BlockPos.containing(entityiterator.getX() - 3.0, entity.getY(), entityiterator.getZ() + 3.0),
                                 MobSpawnType.MOB_SUMMONED
                              );
                           if (entityToSpawn != null) {
                              entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                           }
                        }
                     }
                  }
               } else {
                  Vec3 _center = new Vec3(entity.getX() + entity.getLookAngle().x * 1.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 1.0);

                  for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(60.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                     .toList()) {
                     if ((entityiterator instanceof Player || entityiterator instanceof ServerPlayer) && world instanceof ServerLevel _level) {
                        Entity entityToSpawn = ((EntityType)RpgStyleMoreBossesModEntities.EXOFRAME_BEAM.get())
                           .spawn(
                              _level,
                              BlockPos.containing(
                                 entityiterator.level()
                                       .clip(
                                          new ClipContext(
                                             entityiterator.getEyePosition(1.0F),
                                             entityiterator.getEyePosition(1.0F).add(entityiterator.getViewVector(1.0F).scale(4.0)),
                                             Block.OUTLINE,
                                             Fluid.NONE,
                                             entityiterator
                                          )
                                       )
                                       .getBlockPos()
                                       .getX()
                                    + 0.5,
                                 entity.getY(),
                                 entityiterator.level()
                                       .clip(
                                          new ClipContext(
                                             entityiterator.getEyePosition(1.0F),
                                             entityiterator.getEyePosition(1.0F).add(entityiterator.getViewVector(1.0F).scale(4.0)),
                                             Block.OUTLINE,
                                             Fluid.NONE,
                                             entityiterator
                                          )
                                       )
                                       .getBlockPos()
                                       .getZ()
                                    + 0.5
                              ),
                              MobSpawnType.MOB_SUMMONED
                           );
                        if (entityToSpawn != null) {
                           entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                        }
                     }
                  }
               }
            }
         );
      }
   }
}
