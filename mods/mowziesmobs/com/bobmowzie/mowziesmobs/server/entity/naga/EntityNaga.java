package com.bobmowzie.mowziesmobs.server.entity.naga;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.model.tools.ControlledAnimation;
import com.bobmowzie.mowziesmobs.client.model.tools.dynamics.DynamicChain;
import com.bobmowzie.mowziesmobs.client.particle.ParticleVanillaCloudExtended;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationProjectileAttackAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.SimpleAnimationAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityPoisonBall;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.bobmowzie.mowziesmobs.server.util.MowzieMathUtil;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public class EntityNaga extends MowzieLLibraryEntity implements RangedAttackMob, Enemy, FlyingAnimal {
   public DynamicChain dc;
   public Vec3[] mouthPos;
   public static final Animation FLAP_ANIMATION = Animation.create(25);
   public static final Animation DODGE_ANIMATION = Animation.create(10);
   public static final Animation SPIT_ANIMATION = Animation.create(50);
   public static final Animation SWOOP_ANIMATION = Animation.create(54);
   public static final Animation HURT_TO_FALL_ANIMATION = Animation.create(20);
   public static final Animation LAND_ANIMATION = Animation.create(8);
   public static final Animation GET_UP_ANIMATION = Animation.create(33);
   public static final Animation TAIL_DEMO_ANIMATION = Animation.create(80);
   public static final Animation DIE_AIR_ANIMATION = Animation.create(70);
   public static final Animation DIE_GROUND_ANIMATION = Animation.create(70);
   private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(EntityNaga.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Float> BANKING = SynchedEntityData.defineId(EntityNaga.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> PREV_BANKING = SynchedEntityData.defineId(EntityNaga.class, EntityDataSerializers.FLOAT);
   public static int MAX_DIST_FROM_HOME = 50;
   private final ControlledAnimation hoverAnim = new ControlledAnimation(10);
   private final ControlledAnimation flapAnim = new ControlledAnimation(10);
   public float hoverAnimFrac;
   public float prevHoverAnimFrac;
   public float flapAnimFrac;
   public float prevFlapAnimFrac;
   private boolean hasFlapSoundPlayed = false;
   public float shoulderRot;
   public float banking;
   public float prevBanking;
   public static final int ROAR_DURATION = 30;
   public int roarAnimation = 0;
   public EntityNaga.EnumNagaMovement movement = EntityNaga.EnumNagaMovement.GLIDING;
   public double prevMotionX;
   public double prevMotionY;
   public double prevMotionZ;
   public static int SPIT_COOLDOWN_MAX = 120;
   public int spitCooldown = 0;
   public static int SWOOP_COOLDOWN_MAX = 90;
   public int swoopCooldown = 0;
   public float swoopTargetCorrectY;
   public float swoopTargetCorrectX;
   public static int GROUND_TIMER_MAX = 60;
   public int onGroundTimer = 0;
   public boolean interrupted = false;

   public EntityNaga(EntityType<? extends EntityNaga> type, Level world) {
      super(type, world);
      if (world.isClientSide) {
         this.dc = new DynamicChain(this);
         this.mouthPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }

      this.xpReward = 10;
      this.moveControl = new EntityNaga.NagaMoveHelper(this);
      this.lookControl = new EntityNaga.NagaLookController(this);
      this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setPathfindingMalus(PathType.WATER_BORDER, -1.0F);
      this.setPathfindingMalus(PathType.FENCE, -1.0F);
      this.setPathfindingMalus(PathType.WALKABLE, -1.0F);
   }

   public float getWalkTargetValue(BlockPos pos, LevelReader worldIn) {
      return worldIn.getBlockState(pos).isAir() ? 10.0F : 0.0F;
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(0, new EntityNaga.FlyOutOfWaterGoal(this));
      this.goalSelector.addGoal(5, new EntityNaga.WanderGoal());
      this.goalSelector.addGoal(4, new EntityNaga.AIFlyAroundTarget(this));
      this.goalSelector.addGoal(3, new EntityNaga.AIFlyTowardsTarget(this));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector
         .addGoal(
            2,
            new NearestAttackableTargetGoal<Player>(
               this, Player.class, 0, true, true, target -> target.blockPosition().closerThan(this.getRestrictCenter(), this.getRestrictRadius())
            ) {
               public boolean canContinueToUse() {
                  return super.canContinueToUse()
                     && EntityNaga.this.getTarget() != null
                     && EntityNaga.this.getTarget().blockPosition().closerThan(EntityNaga.this.getRestrictCenter(), EntityNaga.this.getRestrictRadius())
                     && EntityNaga.this.getAnimation() == IAnimatedEntity.NO_ANIMATION;
               }
            }
         );
      this.goalSelector.addGoal(2, new SimpleAnimationAI<EntityNaga>(this, FLAP_ANIMATION, false) {
         @Override
         public void start() {
            super.start();
            EntityNaga.this.playSound((SoundEvent)MMSounds.ENTITY_NAGA_FLAP_1.get(), 2.0F, (float)(0.85 + EntityNaga.this.random.nextFloat() * 0.2));
         }

         public void tick() {
            super.tick();
            if (EntityNaga.this.getAnimationTick() >= 4 && EntityNaga.this.getAnimationTick() <= 9) {
               EntityNaga.this.setDeltaMovement(EntityNaga.this.getDeltaMovement().add(0.0, 0.1, 0.0));
            }
         }
      });
      this.goalSelector.addGoal(2, new SimpleAnimationAI<>(this, DODGE_ANIMATION, false));
      this.goalSelector.addGoal(2, new AnimationProjectileAttackAI<EntityNaga>(this, SPIT_ANIMATION, 30, null) {
         @Override
         public void start() {
            super.start();
            EntityNaga.this.playSound((SoundEvent)MMSounds.ENTITY_NAGA_ACID_CHARGE.get(), 2.0F, 1.0F);
         }

         @Override
         public void tick() {
            super.tick();
            if (!EntityNaga.this.interrupted) {
               if (EntityNaga.this.getAnimationTick() < 9) {
                  EntityNaga.this.setDeltaMovement(EntityNaga.this.getDeltaMovement().add(0.0, 0.015, 0.0));
               }
            }
         }
      });
      this.goalSelector
         .addGoal(
            2,
            new SimpleAnimationAI<EntityNaga>(this, SWOOP_ANIMATION, true) {
               @Override
               public void start() {
                  super.start();
                  EntityNaga.this.playSound((SoundEvent)MMSounds.ENTITY_NAGA_FLAP_1.get(), 2.0F, 0.7F);
               }

               public void tick() {
                  super.tick();
                  if (!EntityNaga.this.interrupted) {
                     Vec3 v = new Vec3(0.0, 0.0, 0.0);
                     int phase1Length = 15;
                     int phase2Length = 21;
                     LivingEntity target = EntityNaga.this.getTarget();
                     if (EntityNaga.this.getAnimationTick() < phase1Length && target != null) {
                        this.entity.lookAt(target, 100.0F, 100.0F);
                        this.entity.lookControl.setLookAt(target, 30.0F, 30.0F);
                     }

                     if (EntityNaga.this.getAnimationTick() < 23 + phase2Length) {
                        if (EntityNaga.this.getAnimationTick() >= 1 && EntityNaga.this.getAnimationTick() < 1 + phase1Length) {
                           float frame = (float)(EntityNaga.this.getAnimationTick() - 1) / phase1Length;
                           v = v.add(
                              new Vec3(
                                 1.9F * Math.sin(frame * Math.PI * 2.0) * frame,
                                 -1.9F * Math.sin(frame * Math.PI * 2.0) * frame,
                                 0.8F * Math.sin(frame * Math.PI * 2.0)
                              )
                           );
                        } else if (EntityNaga.this.getAnimationTick() >= 16) {
                           if (EntityNaga.this.getAnimationTick() == 23) {
                              if (target != null) {
                                 EntityNaga.this.swoopTargetCorrectY = 0.09F * (float)Math.abs(EntityNaga.this.getY() - target.getY());
                                 EntityNaga.this.swoopTargetCorrectX = 0.1F
                                    * (float)Math.sqrt(
                                       (EntityNaga.this.getX() - target.getX()) * (EntityNaga.this.getX() - target.getX())
                                          + (EntityNaga.this.getZ() - target.getZ()) * (EntityNaga.this.getZ() - target.getZ())
                                    );
                                 if (EntityNaga.this.swoopTargetCorrectX > 1.8F) {
                                    EntityNaga.this.swoopTargetCorrectX = 1.8F;
                                 }

                                 if (EntityNaga.this.swoopTargetCorrectY > 2.0F) {
                                    EntityNaga.this.swoopTargetCorrectY = 2.0F;
                                 }
                              } else {
                                 EntityNaga.this.swoopTargetCorrectX = EntityNaga.this.swoopTargetCorrectY = 1.0F;
                              }
                           }

                           if (EntityNaga.this.getAnimationTick() >= 23 && EntityNaga.this.getAnimationTick() < 23 + phase2Length) {
                              float frame = (float)(EntityNaga.this.getAnimationTick() - 23) / phase2Length;
                              v = v.add(
                                 new Vec3(
                                    EntityNaga.this.swoopTargetCorrectX * 1.4 * (1.0 - Math.exp(2.0F * (frame - 1.0F))),
                                    EntityNaga.this.swoopTargetCorrectY * -1.5 * (Math.cos(frame * Math.PI) * (1.0 - Math.exp(7.0F * (frame - 1.0F)))),
                                    0.0
                                 )
                              );

                              for (LivingEntity entityHit : EntityNaga.this.getEntityLivingBaseNearby(4.0, 4.0, 4.0, 4.0)) {
                                 if (!(entityHit instanceof EntityNaga)) {
                                    EntityNaga.this.doHurtTarget(entityHit);
                                 }
                              }
                           }
                        }

                        v = v.yRot((float)Math.toRadians(-EntityNaga.this.getYRot() - 90.0F));
                        EntityNaga.this.setDeltaMovement(v.x, v.y, v.z);
                     }

                     if (EntityNaga.this.getAnimationTick() == 22) {
                        MMCommon.PROXY.playNagaSwoopSound(this.entity);
                     }

                     if (EntityNaga.this.getAnimationTick() == 7) {
                        EntityNaga.this.playSound((SoundEvent)MMSounds.ENTITY_NAGA_GRUNT_3.get(), 2.0F, 1.0F);
                     }

                     if (EntityNaga.this.getAnimationTick() == 22) {
                        EntityNaga.this.playSound((SoundEvent)MMSounds.ENTITY_NAGA_ROAR_1.get(), 3.0F, 1.0F);
                     }
                  }
               }
            }
         );
      this.goalSelector.addGoal(2, new SimpleAnimationAI<EntityNaga>(this, HURT_TO_FALL_ANIMATION, true) {
         public void tick() {
         }
      });
      this.goalSelector.addGoal(2, new SimpleAnimationAI<EntityNaga>(this, LAND_ANIMATION, true) {
         @Override
         public void start() {
            super.start();
            EntityNaga.this.playSound((SoundEvent)MMSounds.MISC_GROUNDHIT_2.get(), 1.5F, 1.0F);
         }
      });
      this.goalSelector.addGoal(1, new SimpleAnimationAI<EntityNaga>(this, GET_UP_ANIMATION, true) {
         public void tick() {
            super.tick();
            if (EntityNaga.this.getAnimationTick() == 13) {
               EntityNaga.this.playSound((SoundEvent)MMSounds.ENTITY_NAGA_FLAP_1.get(), 2.0F, 1.0F);
            }

            if (EntityNaga.this.getAnimationTick() == 15) {
               EntityNaga.this.setDeltaMovement(EntityNaga.this.getDeltaMovement().add(0.0, 1.6, 0.0));
            }
         }
      });
      this.goalSelector.addGoal(2, new SimpleAnimationAI<>(this, TAIL_DEMO_ANIMATION, false));
   }

   protected PathNavigation createNavigation(Level worldIn) {
      FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, worldIn) {
         public boolean isStableDestination(BlockPos pos) {
            return !this.level.getBlockState(pos.below()).isAir();
         }
      };
      flyingpathnavigator.setCanOpenDoors(false);
      flyingpathnavigator.setCanFloat(false);
      flyingpathnavigator.setCanPassDoors(false);
      return flyingpathnavigator;
   }

   public boolean isFlying() {
      return !this.onGround();
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ATTACKING, false);
      builder.define(BANKING, 0.0F);
      builder.define(PREV_BANKING, 0.0F);
   }

   public AABB getBoundingBoxForCulling() {
      return super.getBoundingBoxForCulling().inflate(12.0);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return MowzieEntity.createAttributes()
         .add(Attributes.MAX_HEALTH, 30.0)
         .add(Attributes.MOVEMENT_SPEED, 12.0)
         .add(Attributes.FOLLOW_RANGE, 40.0)
         .add(Attributes.ATTACK_DAMAGE, 4.0);
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      if (this.getAnimation() != NO_ANIMATION) {
         return null;
      }

      int r = this.random.nextInt(4);
      if (r == 0) {
         this.playSound((SoundEvent)((DeferredHolder)MMSounds.ENTITY_NAGA_ROAR.get(this.random.nextInt(4))).get(), 5.0F, 1.0F);
         this.roarAnimation = 0;
      } else if (r <= 2) {
         this.playSound((SoundEvent)((DeferredHolder)MMSounds.ENTITY_NAGA_GROWL.get(this.random.nextInt(3))).get(), 4.0F, 1.0F);
      }

      return null;
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      this.playSound((SoundEvent)((DeferredHolder)MMSounds.ENTITY_NAGA_GRUNT.get(this.random.nextInt(3))).get(), 2.0F, 1.0F);
      return null;
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      this.playSound((SoundEvent)((DeferredHolder)MMSounds.ENTITY_NAGA_ROAR.get(this.random.nextInt(4))).get(), 3.0F, 1.0F);
      return null;
   }

   @Override
   public void tick() {
      this.prevMotionX = this.getDeltaMovement().x;
      this.prevMotionY = this.getDeltaMovement().y;
      this.prevMotionZ = this.getDeltaMovement().z;
      this.prevHoverAnimFrac = this.hoverAnimFrac;
      this.prevFlapAnimFrac = this.flapAnimFrac;
      super.tick();
      this.yBodyRot = this.getYRot();
      if (this.level().isClientSide()) {
         this.banking = this.getBanking();
         this.prevBanking = this.getPrevBanking();
      }

      if (this.spitCooldown > 0) {
         this.spitCooldown--;
      }

      if (this.swoopCooldown > 0) {
         this.swoopCooldown--;
      }

      if (this.onGroundTimer > 0) {
         this.onGroundTimer--;
      }

      if (this.roarAnimation < 30) {
         this.roarAnimation++;
      }

      if (this.getAnimation() == null) {
         AnimationHandler.INSTANCE.sendAnimationMessage(this, NO_ANIMATION);
      }

      if (this.hasEffect(MobEffects.POISON)) {
         this.removeEffectNoUpdate(MobEffects.POISON);
      }

      if (!this.level().isClientSide) {
         if (this.getTarget() != null
            && this.targetDistance < 29.5
            && this.movement != EntityNaga.EnumNagaMovement.FALLEN
            && this.movement != EntityNaga.EnumNagaMovement.FALLING) {
            this.setAttacking(true);
            if (this.getAnimation() == NO_ANIMATION && this.swoopCooldown == 0 && this.random.nextInt(80) == 0 && this.getY() - this.getTarget().getY() > 0.0) {
               this.interrupted = false;
               AnimationHandler.INSTANCE.sendAnimationMessage(this, SWOOP_ANIMATION);
               this.swoopCooldown = SWOOP_COOLDOWN_MAX;
            } else if (this.getAnimation() == NO_ANIMATION && this.spitCooldown == 0 && this.random.nextInt(80) == 0) {
               this.interrupted = false;
               AnimationHandler.INSTANCE.sendAnimationMessage(this, SPIT_ANIMATION);
               this.spitCooldown = SPIT_COOLDOWN_MAX;
            }
         } else {
            this.setAttacking(false);
         }
      }

      if (this.movement != EntityNaga.EnumNagaMovement.FALLING && this.movement != EntityNaga.EnumNagaMovement.FALLEN) {
         if (this.getAttacking()) {
            this.movement = EntityNaga.EnumNagaMovement.HOVERING;
            this.hoverAnim.increaseTimer();
            if (this.getAnimation() == NO_ANIMATION && !this.level().isClientSide) {
               for (Projectile a : this.getEntitiesNearby(Projectile.class, 30.0)) {
                  Vec3 aActualMotion = new Vec3(a.getX() - a.xo, a.getY() - a.yo, a.getZ() - a.zo);
                  if (!(aActualMotion.length() < 0.1) && a.tickCount > 1) {
                     float dot = (float)a.getDeltaMovement().normalize().dot(this.position().subtract(a.position()).normalize());
                     if (dot > 0.96) {
                        Vec3 dodgeVec = a.getDeltaMovement().cross(new Vec3(0.0, 1.0, 0.0)).normalize().scale(1.2);
                        Vec3 newPosLeft = this.position().add(dodgeVec.scale(2.0));
                        Vec3 newPosRight = this.position().add(dodgeVec.scale(-2.0));
                        Vec3 diffLeft = newPosLeft.subtract(a.position());
                        Vec3 diffRight = newPosRight.subtract(a.position());
                        if (diffRight.dot(a.getDeltaMovement()) > diffLeft.dot(a.getDeltaMovement())) {
                           dodgeVec = dodgeVec.scale(-1.0);
                        }

                        this.setDeltaMovement(this.getDeltaMovement().add(dodgeVec));
                        AnimationHandler.INSTANCE.sendAnimationMessage(this, DODGE_ANIMATION);
                     }
                  }
               }
            }
         } else {
            this.movement = EntityNaga.EnumNagaMovement.GLIDING;
            this.hoverAnim.decreaseTimer();
            this.flapAnim.decreaseTimer();
         }
      } else if (this.movement == EntityNaga.EnumNagaMovement.FALLING && (this.onGround() || this.isInLava() || this.isInWater())) {
         AnimationHandler.INSTANCE.sendAnimationMessage(this, LAND_ANIMATION);
         this.movement = EntityNaga.EnumNagaMovement.FALLEN;
         this.onGroundTimer = GROUND_TIMER_MAX;
         this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
         this.getNavigation().stop();
      }

      if (this.movement == EntityNaga.EnumNagaMovement.FALLEN && this.onGroundTimer <= 0 && this.getAnimation() == NO_ANIMATION) {
         this.setAnimationTick(0);
         AnimationHandler.INSTANCE.sendAnimationMessage(this, GET_UP_ANIMATION);
      }

      if (this.getAnimation() == SWOOP_ANIMATION && this.getAnimationTick() < 43) {
         this.hoverAnim.increaseTimer();
         this.flapAnim.decreaseTimer();
      } else if (this.getAnimation() == HURT_TO_FALL_ANIMATION) {
         this.flapAnim.decreaseTimer();
         this.hoverAnim.increaseTimer();
      } else if (this.getAnimation() == LAND_ANIMATION) {
         this.flapAnim.decreaseTimer();
         this.hoverAnim.increaseTimer();
      } else if (this.getAnimation() == GET_UP_ANIMATION && this.getAnimationTick() < 26) {
         this.flapAnim.decreaseTimer();
         this.hoverAnim.increaseTimer();
      } else if (this.movement == EntityNaga.EnumNagaMovement.FALLEN) {
         this.flapAnim.decreaseTimer();
         this.hoverAnim.increaseTimer();
         this.setYRot(this.yRotO);
         this.yHeadRot = this.yHeadRotO;
         this.setXRot(this.xRotO);
      } else if (this.movement == EntityNaga.EnumNagaMovement.FALLING) {
         this.flapAnim.decreaseTimer();
         this.hoverAnim.increaseTimer();
      } else {
         this.flapAnim.increaseTimer();
      }

      if (this.getAnimation() == SPIT_ANIMATION && this.level().isClientSide && this.mouthPos != null && !this.interrupted) {
         if (this.getAnimationTick() == 33) {
            float explodeSpeed = 2.4F;

            for (int i = 0; i < 25; i++) {
               Vec3 particlePos = new Vec3(0.25, 0.0, 0.0);
               particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
               particlePos = particlePos.xRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
               double value = this.random.nextFloat() * 0.1F;
               double life = this.random.nextFloat() * 10.0F + 20.0F;
               ParticleVanillaCloudExtended.spawnVanillaCloud(
                  this.level(),
                  particlePos.x + this.mouthPos[0].x,
                  particlePos.y + this.mouthPos[0].y,
                  particlePos.z + this.mouthPos[0].z,
                  particlePos.x * explodeSpeed,
                  particlePos.y * explodeSpeed,
                  particlePos.z * explodeSpeed,
                  1.0,
                  0.25 + value,
                  0.75 + value,
                  0.25 + value,
                  0.6,
                  life
               );
            }
         }

         if (this.getAnimationTick() <= 15 && this.mouthPos != null && !this.interrupted) {
            int howMany = 4;

            for (int i = 0; i < howMany; i++) {
               Vec3 particlePos = new Vec3(3.0, 0.0, 0.0);
               particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
               particlePos = particlePos.xRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
               double value = this.random.nextFloat() * 0.15F;
               ParticleVanillaCloudExtended.spawnVanillaCloudDestination(
                  this.level(),
                  particlePos.x + this.mouthPos[0].x,
                  particlePos.y + this.mouthPos[0].y,
                  particlePos.z + this.mouthPos[0].z,
                  0.0,
                  0.0,
                  0.0,
                  1.0,
                  0.25 + value,
                  0.75 + value,
                  0.25 + value,
                  0.9,
                  15.0,
                  this.mouthPos
               );
            }
         }
      }

      if (this.getAnimation() == HURT_TO_FALL_ANIMATION && this.getAnimationTick() == 17) {
         this.movement = EntityNaga.EnumNagaMovement.FALLING;
      }

      if (this.getAnimation() == GET_UP_ANIMATION && this.getAnimationTick() == 26) {
         this.movement = EntityNaga.EnumNagaMovement.HOVERING;
      }

      if (this.level().isClientSide && this.movement == EntityNaga.EnumNagaMovement.HOVERING && this.flapAnim.getAnimationFraction() >= 0.5) {
         if (this.shoulderRot > 0.9) {
            this.hasFlapSoundPlayed = false;
         }

         if (this.shoulderRot <= 0.7 && !this.hasFlapSoundPlayed) {
            this.level()
               .playLocalSound(
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  (SoundEvent)MMSounds.ENTITY_NAGA_FLAP_1.get(),
                  SoundSource.HOSTILE,
                  2.0F,
                  (float)(0.85 + this.random.nextFloat() * 0.2),
                  false
               );
            this.hasFlapSoundPlayed = true;
         }
      }

      this.hoverAnimFrac = this.hoverAnim.getAnimationProgressSinSqrt();
      this.flapAnimFrac = this.flapAnim.getAnimationProgressSinSqrt();
      if (!this.level().isClientSide && this.level().getDifficulty() == Difficulty.PEACEFUL) {
         this.discard();
      }
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn) {
      this.restrictTo(this.blockPosition(), MAX_DIST_FROM_HOME);
      return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn);
   }

   @Override
   protected ConfigHandler.SpawnConfig getSpawnConfig() {
      return ConfigHandler.COMMON.MOBS.NAGA.spawnConfig;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.NAGA.combatConfig;
   }

   public boolean checkSpawnRules(LevelAccessor world, MobSpawnType reason) {
      boolean flag = super.checkSpawnRules(world, reason);
      this.setPos(this.getX(), this.getY() + 5.0, this.getZ());
      return flag && world.getDifficulty() != Difficulty.PEACEFUL;
   }

   public boolean checkSpawnObstruction(LevelReader worldIn) {
      boolean liquid = !this.level().containsAnyLiquid(this.getBoundingBox());
      boolean worldCollision = this.level().noCollision(this, this.getBoundingBox());
      boolean mobCollision = this.level().isUnobstructed(this);
      return liquid && worldCollision && mobCollision;
   }

   @Override
   protected void tickDeath() {
      super.tickDeath();
      if (this.deathTime == 15 && this.movement != EntityNaga.EnumNagaMovement.FALLEN) {
         this.movement = EntityNaga.EnumNagaMovement.FALLING;
      }
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      boolean flag = super.hurt(source, damage);
      boolean isSpitting = this.getAnimation() == SPIT_ANIMATION && this.getAnimationTick() < 30;
      boolean isSwooping = this.getAnimation() == SWOOP_ANIMATION && this.getAnimationTick() < 25;
      if (flag && this.movement != EntityNaga.EnumNagaMovement.FALLING && (isSpitting || isSwooping) && damage > 0.0F) {
         AnimationHandler.INSTANCE.sendAnimationMessage(this, HURT_TO_FALL_ANIMATION);
         this.interrupted = true;
      }

      return flag;
   }

   public void performRangedAttack(LivingEntity target, float distanceFactor) {
      if (!this.interrupted) {
         Vec3 projectilePos = new Vec3(1.0, -0.7, 0.0);
         projectilePos = projectilePos.yRot((float)Math.toRadians(-this.getYRot() - 90.0F));
         projectilePos = projectilePos.add(this.position());
         projectilePos = projectilePos.add(new Vec3(0.0, 0.0, 1.0).xRot((float)Math.toRadians(-this.getXRot())).yRot((float)Math.toRadians(-this.yHeadRot)));
         projectilePos = projectilePos.add(new Vec3(0.0, 0.0, 0.0));
         EntityPoisonBall poisonBall = new EntityPoisonBall((EntityType<? extends EntityPoisonBall>)EntityHandler.POISON_BALL.get(), this.level(), this);
         poisonBall.setPos(projectilePos.x, projectilePos.y, projectilePos.z);
         Vec3 look = this.getLookAngle();
         Vec3 dir = new Vec3(look.x, 0.0, look.z).normalize();
         if (target != null) {
            float dy = (float)(projectilePos.y - target.getY());
            float dx = (float)(projectilePos.x - target.getX());
            float dz = (float)(projectilePos.z - target.getZ());
            float dist = (float)Math.sqrt(dx * dx + dz * dz);
            float timeGuess = (float)Math.sqrt(2.0F * dy / EntityPoisonBall.GRAVITY);
            float speed = Math.min(dist / timeGuess, 0.9F);
            poisonBall.shoot(dir.x * speed, 0.1, dir.z * speed, 1.0F, 0.0F);
         }

         this.level().addFreshEntity(poisonBall);
         this.playSound((SoundEvent)MMSounds.ENTITY_NAGA_ACID_SPIT.get(), 2.0F, 1.0F);
         this.playSound((SoundEvent)MMSounds.ENTITY_NAGA_ACID_SPIT_HISS.get(), 2.0F, 1.0F);
      }
   }

   @Override
   public Animation getDeathAnimation() {
      return this.movement == EntityNaga.EnumNagaMovement.FALLEN ? DIE_GROUND_ANIMATION : DIE_AIR_ANIMATION;
   }

   @Override
   public Animation getHurtAnimation() {
      return null;
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{
         FLAP_ANIMATION,
         DODGE_ANIMATION,
         SWOOP_ANIMATION,
         SPIT_ANIMATION,
         HURT_TO_FALL_ANIMATION,
         LAND_ANIMATION,
         GET_UP_ANIMATION,
         DIE_AIR_ANIMATION,
         DIE_GROUND_ANIMATION,
         TAIL_DEMO_ANIMATION
      };
   }

   public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
      return this.movement == EntityNaga.EnumNagaMovement.FALLING ? super.causeFallDamage(distance, damageMultiplier, source) : false;
   }

   protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
      if (this.movement == EntityNaga.EnumNagaMovement.FALLING) {
         super.checkFallDamage(y, onGroundIn, state, pos);
      }
   }

   public void travel(@NotNull Vec3 motion) {
      double gravity = this.getGravity();
      boolean isFalling = this.getDeltaMovement().y <= 0.0;
      if (isFalling && this.hasEffect(MobEffects.SLOW_FALLING)) {
         gravity = Math.max(gravity, 0.01);
      }

      FluidState fluidstate = this.level().getFluidState(this.blockPosition());
      if ((this.isInWater() || this.isInFluidType(fluidstate) && fluidstate.getFluidType() != NeoForgeMod.LAVA_TYPE.value())
         && this.isAffectedByFluids()
         && !this.canStandOnFluid(fluidstate)) {
         if (this.isInWater() || this.isInFluidType(fluidstate) && !this.moveInFluid(fluidstate, motion, gravity)) {
            double y = this.getY();
            float waterSpeed = this.isSprinting() ? 0.9F : this.getWaterSlowDown();
            float swimSpeed = 0.02F;
            float speedModifier = (float)this.getAttributeValue(Attributes.WATER_MOVEMENT_EFFICIENCY);
            if (!this.onGround()) {
               speedModifier *= 0.5F;
            }

            if (speedModifier > 0.0F) {
               waterSpeed += (0.54600006F - waterSpeed) * speedModifier;
               swimSpeed += (this.getSpeed() - swimSpeed) * speedModifier;
            }

            if (this.hasEffect(MobEffects.DOLPHINS_GRACE)) {
               waterSpeed = 0.96F;
            }

            swimSpeed *= (float)this.getAttributeValue(NeoForgeMod.SWIM_SPEED);
            this.moveRelative(swimSpeed, motion);
            this.move(MoverType.SELF, this.getDeltaMovement());
            Vec3 deltaMovement = this.getDeltaMovement();
            if (this.horizontalCollision && this.onClimbable()) {
               deltaMovement = new Vec3(deltaMovement.x, 0.2, deltaMovement.z);
            }

            this.setDeltaMovement(deltaMovement.multiply(waterSpeed, 0.8F, waterSpeed));
            Vec3 fluidMovement = this.getFluidFallingAdjustedMovement(gravity, isFalling, this.getDeltaMovement());
            this.setDeltaMovement(fluidMovement);
            if (this.horizontalCollision && this.isFree(fluidMovement.x, fluidMovement.y + 0.6F - this.getY() + y, fluidMovement.z)) {
               this.setDeltaMovement(fluidMovement.x, 0.3F, fluidMovement.z);
            }
         }
      } else if (this.isInLava() && this.isAffectedByFluids() && !this.canStandOnFluid(fluidstate)) {
         double y = this.getY();
         this.moveRelative(0.02F, motion);
         this.move(MoverType.SELF, this.getDeltaMovement());
         if (this.getFluidHeight(FluidTags.LAVA) <= this.getFluidJumpThreshold()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.5, 0.8F, 0.5));
            Vec3 fluidMovement = this.getFluidFallingAdjustedMovement(gravity, isFalling, this.getDeltaMovement());
            this.setDeltaMovement(fluidMovement);
         } else {
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
         }

         if (gravity != 0.0) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -gravity / 4.0, 0.0));
         }

         Vec3 deltaMovement = this.getDeltaMovement();
         if (this.horizontalCollision && this.isFree(deltaMovement.x, deltaMovement.y + 0.6F - this.getY() + y, deltaMovement.z)) {
            this.setDeltaMovement(deltaMovement.x, 0.3F, deltaMovement.z);
         }
      } else if (this.movement == EntityNaga.EnumNagaMovement.HOVERING) {
         BlockPos ground = new BlockPos((int)this.getX(), (int)(this.getBoundingBox().minY - 1.0), (int)this.getZ());
         float f = 0.91F;
         if (this.onGround()) {
            f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
         }

         float f1 = 0.16277137F / (f * f * f);
         f = 0.91F;
         if (this.onGround()) {
            f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
         }

         this.moveRelative(this.onGround() ? 0.1F * f1 : 0.02F, motion);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(f));
         BlockPos destination = this.getNavigation().getTargetPos();
         if (destination != null) {
            double dx = destination.getX() - this.getX();
            double dy = destination.getY() - this.getY();
            double dz = destination.getZ() - this.getZ();
            double distanceToDest = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (distanceToDest < 0.1 && this.getAnimation() == NO_ANIMATION) {
               this.setDeltaMovement(0.0, 0.0, 0.0);
            }
         }
      } else if (this.movement == EntityNaga.EnumNagaMovement.GLIDING) {
         Vec3 vec3 = this.getDeltaMovement();
         if (vec3.y > -0.5) {
            this.fallDistance = 1.0F;
         }

         Vec3 moveDirection = this.getLookAngle();
         moveDirection = moveDirection.normalize();
         float f6 = this.getXRot() * (float) (Math.PI / 180.0);
         double d9 = Math.sqrt(moveDirection.x * moveDirection.x + moveDirection.z * moveDirection.z);
         double d11 = Math.sqrt(vec3.horizontalDistanceSqr());
         double d12 = moveDirection.length();
         float f3 = Mth.cos(f6);
         f3 = (float)((double)f3 * f3 * Math.min(1.0, d12 / 0.4));
         vec3 = this.getDeltaMovement().add(0.0, gravity * (-1.0 + f3 * 0.75), 0.0);
         if (vec3.y < 0.0 && d9 > 0.0) {
            double d3 = vec3.y * -0.1 * f3;
            vec3 = vec3.add(moveDirection.x * d3 / d9, d3, moveDirection.z * d3 / d9);
         }

         if (f6 < 0.0F && d9 > 0.0) {
            double d13 = d11 * -Mth.sin(f6) * 0.04;
            vec3 = vec3.add(-moveDirection.x * d13 / d9, d13 * 3.2, -moveDirection.z * d13 / d9);
         }

         if (d9 > 0.0) {
            vec3 = vec3.add((moveDirection.x / d9 * d11 - vec3.x) * 0.1, 0.0, (moveDirection.z / d9 * d11 - vec3.z) * 0.1);
         }

         this.setDeltaMovement(vec3.multiply(0.99F, 0.98F, 0.99F));
         this.move(MoverType.SELF, this.getDeltaMovement());
         if (moveDirection.y() < 0.0 && this.getAnimation() == NO_ANIMATION) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this, FLAP_ANIMATION);
         }
      } else if (this.movement == EntityNaga.EnumNagaMovement.FALLING || this.movement == EntityNaga.EnumNagaMovement.FALLEN || this.isNoAi()) {
         BlockPos blockpos = this.getBlockPosBelowThatAffectsMyMovement();
         float f2 = this.level()
            .getBlockState(this.getBlockPosBelowThatAffectsMyMovement())
            .getFriction(this.level(), this.getBlockPosBelowThatAffectsMyMovement(), this);
         float f3 = this.onGround() ? f2 * 0.91F : 0.91F;
         Vec3 vec35 = this.handleRelativeFrictionAndCalculateMovement(motion, f2);
         double d2 = vec35.y;
         if (this.hasEffect(MobEffects.LEVITATION)) {
            d2 += (0.05 * (this.getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - vec35.y) * 0.2;
         } else if (this.level().isClientSide && !this.level().hasChunkAt(blockpos)) {
            if (this.getY() > this.level().getMinBuildHeight()) {
               d2 = -0.1;
            } else {
               d2 = 0.0;
            }
         } else if (!this.isNoGravity()) {
            d2 -= gravity;
         }

         if (this.shouldDiscardFriction()) {
            this.setDeltaMovement(vec35.x, d2, vec35.z);
         } else {
            this.setDeltaMovement(vec35.x * f3, d2 * 0.98F, vec35.z * f3);
         }
      }

      this.calculateEntityAnimation(true);
   }

   public boolean onClimbable() {
      return false;
   }

   public boolean getAttacking() {
      return (Boolean)this.getEntityData().get(ATTACKING);
   }

   public void setAttacking(boolean attacking) {
      this.getEntityData().set(ATTACKING, attacking);
   }

   public float getBanking() {
      return (Float)this.getEntityData().get(BANKING);
   }

   public void setBanking(float banking) {
      this.getEntityData().set(BANKING, banking);
   }

   public float getPrevBanking() {
      return (Float)this.getEntityData().get(PREV_BANKING);
   }

   public void setPrevBanking(float prevBanking) {
      this.getEntityData().set(PREV_BANKING, prevBanking);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("HomePosX", this.getRestrictCenter().getX());
      compound.putInt("HomePosY", this.getRestrictCenter().getY());
      compound.putInt("HomePosZ", this.getRestrictCenter().getZ());
      compound.putInt("HomeDist", (int)this.getRestrictRadius());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      int i = compound.getInt("HomePosX");
      int j = compound.getInt("HomePosY");
      int k = compound.getInt("HomePosZ");
      int dist = compound.getInt("HomeDist");
      this.restrictTo(new BlockPos(i, j, k), dist);
   }

   @Override
   public int getDespawnDistance() {
      return 256;
   }

   @Override
   public int getNoDespawnDistance() {
      return 128;
   }

   protected ResourceKey<LootTable> getDefaultLootTable() {
      return LootTableHandler.NAGA;
   }

   class AIFlyAroundTarget extends Goal {
      private final EntityNaga parentEntity;

      public AIFlyAroundTarget(EntityNaga naga) {
         this.parentEntity = naga;
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         return this.parentEntity.getTarget() != null && this.parentEntity.getNavigation().isDone() ? this.parentEntity.random.nextInt(60) == 0 : false;
      }

      public boolean canContinueToUse() {
         if (EntityNaga.this.getTarget() == null) {
            return false;
         }

         BlockPos navigatorPos = this.parentEntity.getNavigation().getTargetPos();
         if (navigatorPos == null) {
            return false;
         }

         double dx = navigatorPos.getX() - this.parentEntity.getX();
         double dy = navigatorPos.getY() - this.parentEntity.getY();
         double dz = navigatorPos.getZ() - this.parentEntity.getZ();
         double distanceToDest = Math.sqrt(dx * dx + dy * dy + dz * dz);
         if (distanceToDest > 60.0) {
            return false;
         }

         LivingEntity target = this.parentEntity.getTarget();
         double dx2 = navigatorPos.getX() - target.getX();
         double dy2 = navigatorPos.getY() - target.getY();
         double dz2 = navigatorPos.getZ() - target.getZ();
         double distanceDestToTarget = Math.sqrt(dx2 * dx2 + dy2 * dy2 + dz2 * dz2);
         return !(distanceDestToTarget > 20.0) && !(distanceDestToTarget < 5.0)
            ? EntityNaga.this.navigation.isInProgress() && !EntityNaga.this.navigation.getTargetPos().closerThan(EntityNaga.this.blockPosition(), 1.0)
            : false;
      }

      public void start() {
         RandomSource random = this.parentEntity.getRandom();
         LivingEntity target = this.parentEntity.getTarget();
         float yaw = (float)(random.nextFloat() * Math.PI * 2.0);
         float radius = 16.0F;
         double d0 = target.getX() + Math.cos(yaw) * radius;
         double d1 = target.getY() + 8.0 + random.nextFloat() * 5.0F;
         double d2 = target.getZ() + Math.sin(yaw) * radius;
         double speed = this.parentEntity.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
         if (!this.parentEntity.level().isWaterAt(BlockPos.containing(d0, d1, d2))) {
            this.parentEntity.getNavigation().moveTo(d0, d1, d2, speed);
         }
      }

      public void stop() {
         super.stop();
         EntityNaga.this.getNavigation().stop();
      }
   }

   class AIFlyTowardsTarget extends Goal {
      private final EntityNaga parentEntity;

      public AIFlyTowardsTarget(EntityNaga naga) {
         this.parentEntity = naga;
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         return this.parentEntity.getTarget() != null && EntityNaga.this.distanceToSqr(this.parentEntity.getTarget()) >= 870.25;
      }

      public boolean canContinueToUse() {
         if (EntityNaga.this.getTarget() == null) {
            return false;
         }

         if (EntityNaga.this.getTarget().distanceToSqr(this.parentEntity) <= 500.0) {
            return false;
         }

         BlockPos navigatorPos = this.parentEntity.getNavigation().getTargetPos();
         if (navigatorPos == null) {
            return false;
         }

         double dx = navigatorPos.getX() - this.parentEntity.getX();
         double dy = navigatorPos.getY() - this.parentEntity.getY();
         double dz = navigatorPos.getZ() - this.parentEntity.getZ();
         double distanceToDest = Math.sqrt(dx * dx + dy * dy + dz * dz);
         return distanceToDest > 60.0 ? false : EntityNaga.this.navigation.isInProgress();
      }

      public void tick() {
         super.tick();
         LivingEntity target = this.parentEntity.getTarget();
         double speed = this.parentEntity.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
         BlockPos targetPos = target.blockPosition().above(8);
         if (!this.parentEntity.level().isWaterAt(targetPos)) {
            this.parentEntity.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), speed);
         }
      }

      public void stop() {
         super.stop();
         EntityNaga.this.getNavigation().stop();
      }
   }

   static class AILookAround extends Goal {
      private final EntityNaga parentEntity;

      public AILookAround(EntityNaga naga) {
         this.parentEntity = naga;
         this.setFlags(EnumSet.of(Flag.LOOK));
      }

      public boolean canUse() {
         return true;
      }

      public void tick() {
         if (this.parentEntity.getTarget() == null) {
            Vec3 motion = this.parentEntity.getDeltaMovement();
            this.parentEntity.setYRot(-((float)Mth.atan2(motion.x, motion.z)) * (180.0F / (float)Math.PI));
            this.parentEntity.yBodyRot = this.parentEntity.getYRot();
         } else {
            LivingEntity entitylivingbase = this.parentEntity.getTarget();
            if (entitylivingbase.distanceToSqr(this.parentEntity) < 1600.0) {
               double d1 = entitylivingbase.getX() - this.parentEntity.getX();
               double d2 = entitylivingbase.getZ() - this.parentEntity.getZ();
               this.parentEntity.setYRot(-((float)Mth.atan2(d1, d2)) * (180.0F / (float)Math.PI));
               this.parentEntity.yBodyRot = this.parentEntity.getYRot();
            }
         }
      }
   }

   public enum EnumNagaMovement {
      GLIDING,
      HOVERING,
      SWIMMING,
      FALLING,
      FALLEN;
   }

   public class FlyOutOfWaterGoal extends Goal {
      private final EntityNaga entity;

      public FlyOutOfWaterGoal(EntityNaga entityIn) {
         this.entity = entityIn;
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         return this.entity.movement != EntityNaga.EnumNagaMovement.FALLING
               && this.entity.movement != EntityNaga.EnumNagaMovement.FALLEN
               && this.entity.isInWater()
               && this.entity.getFluidHeight(FluidTags.WATER) > this.entity.getFluidJumpThreshold()
            || this.entity.isInLava();
      }

      public void start() {
         if (this.entity.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, EntityNaga.FLAP_ANIMATION);
         }
      }

      public boolean canContinueToUse() {
         return false;
      }
   }

   class NagaLookController extends LookControl {
      public NagaLookController(Mob entity) {
         super(entity);
      }

      public void tick() {
         if (this.lookAtCooldown > 0 && this.getYRotD().isPresent()) {
            this.lookAtCooldown--;
            this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, (Float)this.getYRotD().get(), this.yMaxRotSpeed);
         } else {
            this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, 10.0F);
         }

         if (!this.mob.getNavigation().isDone()) {
            this.mob.yHeadRot = Mth.rotateIfNecessary(this.mob.yHeadRot, this.mob.yBodyRot, this.mob.getMaxHeadYRot());
         }
      }
   }

   class NagaMoveHelper extends MoveControl {
      private final EntityNaga parentEntity;
      private int courseChangeCooldown;
      private float speedFactor = 0.1F;

      public NagaMoveHelper(EntityNaga naga) {
         super(naga);
         this.parentEntity = naga;
      }

      public void tick() {
         this.speedFactor = 1.0F;
         if (EntityNaga.this.movement == EntityNaga.EnumNagaMovement.GLIDING) {
            if (this.operation == Operation.MOVE_TO) {
               if (EntityNaga.this.horizontalCollision) {
                  EntityNaga naga = EntityNaga.this;
                  naga.setYRot(naga.getYRot() + 180.0F);
                  this.speedFactor = 0.1F;
                  EntityNaga.this.getNavigation().stop();
               }

               float orbitOffsetDiffX = (float)(EntityNaga.this.getNavigation().getTargetPos().getX() - EntityNaga.this.getX());
               float orbitOffsetDiffY = (float)(EntityNaga.this.getNavigation().getTargetPos().getY() - EntityNaga.this.getY());
               float orbitOffsetDiffZ = (float)(EntityNaga.this.getNavigation().getTargetPos().getZ() - EntityNaga.this.getZ());
               double horizontalDistToOrbitOffset = Mth.sqrt(orbitOffsetDiffX * orbitOffsetDiffX + orbitOffsetDiffZ * orbitOffsetDiffZ);
               double yFractionReduction = 1.0 - Mth.abs(orbitOffsetDiffY * 0.7F) / horizontalDistToOrbitOffset;
               orbitOffsetDiffX = (float)(orbitOffsetDiffX * yFractionReduction);
               orbitOffsetDiffZ = (float)(orbitOffsetDiffZ * yFractionReduction);
               horizontalDistToOrbitOffset = Mth.sqrt(orbitOffsetDiffX * orbitOffsetDiffX + orbitOffsetDiffZ * orbitOffsetDiffZ);
               double distToOrbitOffset = Mth.sqrt(
                  orbitOffsetDiffX * orbitOffsetDiffX + orbitOffsetDiffZ * orbitOffsetDiffZ + orbitOffsetDiffY * orbitOffsetDiffY
               );
               float rotationYaw = EntityNaga.this.getYRot();
               float desiredRotationYaw = (float)Mth.atan2(orbitOffsetDiffZ, orbitOffsetDiffX);
               float rotationYawWrapped = Mth.wrapDegrees(EntityNaga.this.getYRot() + 90.0F);
               float desiredRotationYawWrapped = Mth.wrapDegrees(desiredRotationYaw * (180.0F / (float)Math.PI));
               EntityNaga.this.setYRot(Mth.approachDegrees(rotationYawWrapped, desiredRotationYawWrapped, 4.0F) - 90.0F);
               float newBanking = MowzieMathUtil.approachDegreesSmooth(
                  EntityNaga.this.getBanking(), EntityNaga.this.getPrevBanking(), EntityNaga.this.getYRot() - rotationYaw, 0.5F, 0.1F
               );
               EntityNaga.this.setPrevBanking(EntityNaga.this.getBanking());
               EntityNaga.this.setBanking(newBanking);
               EntityNaga.this.yBodyRot = EntityNaga.this.getYRot();
               float desiredPitch = (float)(-(Mth.atan2(-orbitOffsetDiffY, horizontalDistToOrbitOffset) * 180.0F / (float)Math.PI));
               EntityNaga.this.setXRot(Mth.approachDegrees(EntityNaga.this.getXRot(), desiredPitch, 8.0F));
               float rotationYaw1 = EntityNaga.this.getYRot() + 90.0F;
               double xMotion = this.speedFactor * Mth.cos(rotationYaw1 * (float) (Math.PI / 180.0)) * Math.abs(orbitOffsetDiffX / distToOrbitOffset);
               double yMotion = this.speedFactor * Mth.sin(rotationYaw1 * (float) (Math.PI / 180.0)) * Math.abs(orbitOffsetDiffZ / distToOrbitOffset);
               double zMotion = this.speedFactor * Mth.sin(desiredPitch * (float) (Math.PI / 180.0)) * Math.abs(orbitOffsetDiffY / distToOrbitOffset);
               Vec3 motion = EntityNaga.this.getDeltaMovement();
               EntityNaga.this.setDeltaMovement(motion.add(new Vec3(xMotion, zMotion, yMotion).subtract(motion).scale(0.1)));
            }
         } else if (EntityNaga.this.movement == EntityNaga.EnumNagaMovement.HOVERING
            && (EntityNaga.this.getAnimation() == IAnimatedEntity.NO_ANIMATION || EntityNaga.this.getAnimation() == EntityNaga.SPIT_ANIMATION)) {
            LivingEntity target = EntityNaga.this.getTarget();
            if (target != null && EntityNaga.this.distanceToSqr(this.parentEntity) < 1600.0) {
               EntityNaga.this.lookAt(target, 100.0F, 100.0F);
            }

            if (this.operation == Operation.MOVE_TO && this.courseChangeCooldown-- <= 0) {
               this.courseChangeCooldown = this.courseChangeCooldown + this.parentEntity.getRandom().nextInt(5) + 2;
               Vec3 Vector3d = new Vec3(
                  this.wantedX - this.parentEntity.getX(), this.wantedY - this.parentEntity.getY(), this.wantedZ - this.parentEntity.getZ()
               );
               double d0 = Vector3d.length();
               Vector3d = Vector3d.normalize();
               if (this.checkCollisions(Vector3d, Mth.ceil(d0))) {
                  this.parentEntity.setDeltaMovement(this.parentEntity.getDeltaMovement().add(Vector3d.scale(0.1)));
               } else {
                  this.operation = Operation.WAIT;
               }
            }
         }
      }

      public boolean checkCollisions(Vec3 p_220673_1_, int p_220673_2_) {
         AABB axisalignedbb = this.parentEntity.getBoundingBox();

         for (int i = 1; i < p_220673_2_; i++) {
            axisalignedbb = axisalignedbb.move(p_220673_1_);
            if (!this.parentEntity.level().noCollision(this.parentEntity, axisalignedbb)) {
               return false;
            }
         }

         return true;
      }
   }

   class WanderGoal extends Goal {
      private boolean seesGround;

      WanderGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         return EntityNaga.this.navigation.isDone() && EntityNaga.this.getTarget() == null;
      }

      public boolean canContinueToUse() {
         return EntityNaga.this.navigation.isInProgress()
            && !EntityNaga.this.navigation.getTargetPos().closerThan(EntityNaga.this.blockPosition(), 4.0)
            && EntityNaga.this.getTarget() == null;
      }

      public void start() {
         Vec3 vector3d = this.getRandomLocation();
         if (vector3d != null) {
            EntityNaga.this.navigation.setMaxVisitedNodesMultiplier(0.5F);
            EntityNaga.this.navigation.moveTo(EntityNaga.this.navigation.createPath(BlockPos.containing(vector3d), 1), 1.0);
         }
      }

      public void tick() {
         super.tick();
         if (!this.seesGround) {
            Vec3 newLocation = this.getRandomLocation();
            if (newLocation != null && this.seesGround) {
               EntityNaga.this.navigation.moveTo(EntityNaga.this.navigation.createPath(BlockPos.containing(newLocation), 1), 1.0);
            }
         }
      }

      public void stop() {
         super.stop();
         EntityNaga.this.getNavigation().stop();
      }

      @Nullable
      private Vec3 getRandomLocation() {
         Vec3 vector3d = EntityNaga.this.getViewVector(0.0F);
         Vec3 position = HoverRandomPos.getPos(EntityNaga.this, 24, 24, vector3d.x, vector3d.z, (float) (Math.PI / 2), 18, 8);
         if (position == null) {
            Vec3 sumPos = EntityNaga.this.position().add(vector3d);
            position = AirAndWaterRandomPos.getPos(EntityNaga.this, 24, 8, -8, sumPos.x, sumPos.z, (float) (Math.PI / 2));
            this.seesGround = false;
         } else {
            this.seesGround = true;
         }

         if (position != null && EntityNaga.this.level().isEmptyBlock(BlockPos.containing(position).below())) {
            Vec3 offset = position.subtract(EntityNaga.this.position());
            AABB newBB = EntityNaga.this.getBoundingBox().move(offset);
            return EntityNaga.this.level().noCollision(newBB)
                  && EntityNaga.this.level().clip(new ClipContext(EntityNaga.this.position(), position, Block.COLLIDER, Fluid.NONE, EntityNaga.this)).getType()
                     != Type.BLOCK
               ? position
               : null;
         } else {
            return null;
         }
      }
   }
}
