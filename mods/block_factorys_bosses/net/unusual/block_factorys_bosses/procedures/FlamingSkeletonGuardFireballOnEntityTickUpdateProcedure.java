package net.unusual.block_factorys_bosses.procedures;

import java.util.Comparator;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.dragon.guardians.FlamingSkeletonGuardFireballEntity;
import net.unusual.block_factorys_bosses.entity.projectile.BlazingFireBallEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;

public class FlamingSkeletonGuardFireballOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (Math.random() < 0.05) {
            if (world instanceof ServerLevel _level) {
               _level.sendParticles(ParticleTypes.FLAME, entity.getX(), entity.getY() + 1.5, entity.getZ(), 1, 0.4, 0.4, 0.4, 0.0);
            }
         } else if (Math.random() < 0.1 && world instanceof ServerLevel _level) {
            _level.sendParticles(ParticleTypes.SMOKE, entity.getX(), entity.getY() + 1.5, entity.getZ(), 1, 0.4, 0.4, 0.4, 0.0);
         }

         if ((
               entity instanceof FlamingSkeletonGuardFireballEntity _datEntI
                  ? (Integer)_datEntI.getEntityData().get(FlamingSkeletonGuardFireballEntity.DATA_attack_animtime)
                  : 0
            )
            > 0) {
            if ((
                  entity instanceof FlamingSkeletonGuardFireballEntity _datEntI
                     ? (Integer)_datEntI.getEntityData().get(FlamingSkeletonGuardFireballEntity.DATA_attack_animtime)
                     : 0
               )
               == 19) {
               Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8.5), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                  .toList()) {
                  if (entityiterator == (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null)) {
                     entity.lookAt(Anchor.EYES, new Vec3(entityiterator.getX(), entityiterator.getY() + entity.getBbHeight() * 0.5, entityiterator.getZ()));
                     if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                           _level.playSound(
                              null,
                              BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()),
                              SoundEvents.BLAZE_SHOOT,
                              SoundSource.HOSTILE,
                              1.0F,
                              (float)(Mth.nextInt(RandomSource.create(), 8, 12) * 0.1)
                           );
                        } else {
                           _level.playLocalSound(
                              entity.getX(),
                              entity.getY(),
                              entity.getZ(),
                              SoundEvents.BLAZE_SHOOT,
                              SoundSource.HOSTILE,
                              1.0F,
                              (float)(Mth.nextInt(RandomSource.create(), 8, 12) * 0.1),
                              false
                           );
                        }
                     }

                     if (entity.isAlive()) {
                        Entity _shootFrom = entity;
                        Level projectileLevel = _shootFrom.level();
                        if (!projectileLevel.isClientSide()) {
                           Projectile _entityToSpawn = (new Object() {
                              public Projectile getArrow(Level level, Entity shooter, float damage, final int knockback, final byte piercing) {
                                 AbstractArrow entityToSpawn = new BlazingFireBallEntity((EntityType)BossesRiseEntities.BLAZING_FIRE_BALL.get(), level) {
                                    public byte getPierceLevel() {
                                       return piercing;
                                    }

                                    @Override
                                    protected void doKnockback(LivingEntity livingEntity, DamageSource damageSource) {
                                       if (knockback > 0) {
                                          double d1 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                                          Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(knockback * 0.6 * d1);
                                          if (vec3.lengthSqr() > 0.0) {
                                             livingEntity.push(vec3.x, 0.1, vec3.z);
                                          }
                                       }
                                    }
                                 };
                                 entityToSpawn.setOwner(shooter);
                                 entityToSpawn.setBaseDamage(damage);
                                 entityToSpawn.setSilent(true);
                                 return entityToSpawn;
                              }
                           }).getArrow(projectileLevel, entity, 1.0F, 1, (byte)0);
                           _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                           _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1.5F, 3.0F);
                           projectileLevel.addFreshEntity(_entityToSpawn);
                        }
                     }
                     break;
                  }
               }
            }

            if (entity instanceof FlamingSkeletonGuardFireballEntity _datEntSetI) {
               _datEntSetI.getEntityData()
                  .set(
                     FlamingSkeletonGuardFireballEntity.DATA_attack_animtime,
                     (
                           entity instanceof FlamingSkeletonGuardFireballEntity _datEntI
                              ? (Integer)_datEntI.getEntityData().get(FlamingSkeletonGuardFireballEntity.DATA_attack_animtime)
                              : 0
                        )
                        - 1
                  );
            }
         } else if ((
               entity instanceof FlamingSkeletonGuardFireballEntity _datEntI
                  ? (Integer)_datEntI.getEntityData().get(FlamingSkeletonGuardFireballEntity.DATA_attack_cooldown)
                  : 0
            )
            >= 70) {
            Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator == (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null)) {
                  if (entity instanceof FlamingSkeletonGuardFireballEntity _datEntSetI) {
                     _datEntSetI.getEntityData().set(FlamingSkeletonGuardFireballEntity.DATA_attack_cooldown, 0);
                  }

                  if (entity instanceof FlamingSkeletonGuardFireballEntity _datEntSetI) {
                     _datEntSetI.getEntityData().set(FlamingSkeletonGuardFireballEntity.DATA_attack_animtime, 25);
                  }
                  break;
               }
            }
         } else {
            Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(16.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator == (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null)) {
                  if (entity instanceof FlamingSkeletonGuardFireballEntity _datEntSetI) {
                     _datEntSetI.getEntityData()
                        .set(
                           FlamingSkeletonGuardFireballEntity.DATA_attack_cooldown,
                           (
                                 entity instanceof FlamingSkeletonGuardFireballEntity _datEntI
                                    ? (Integer)_datEntI.getEntityData().get(FlamingSkeletonGuardFireballEntity.DATA_attack_cooldown)
                                    : 0
                              )
                              + 1
                        );
                  }
                  break;
               }
            }
         }

         if ((
               entity instanceof FlamingSkeletonGuardFireballEntity _datEntI
                  ? (Integer)_datEntI.getEntityData().get(FlamingSkeletonGuardFireballEntity.DATA_attack_cooldown)
                  : 0
            )
            == 50) {
            if (world instanceof ServerLevel _level) {
               _level.sendParticles(ParticleTypes.FLAME, entity.getX(), entity.getY() + 1.5, entity.getZ(), 10, 0.4, 0.4, 0.4, 0.0);
            }

            if (world instanceof ServerLevel _level) {
               _level.sendParticles(ParticleTypes.SMOKE, entity.getX(), entity.getY() + 1.5, entity.getZ(), 10, 0.4, 0.4, 0.4, 0.0);
            }
         }
      }
   }
}
