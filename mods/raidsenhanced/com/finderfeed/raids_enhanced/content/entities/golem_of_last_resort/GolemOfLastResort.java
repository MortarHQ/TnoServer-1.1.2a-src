package com.finderfeed.raids_enhanced.content.entities.golem_of_last_resort;

import com.finderfeed.fdlib.nbt.AutoSerializable;
import com.finderfeed.fdlib.nbt.SerializableField;
import com.finderfeed.fdlib.systems.bedrock.animations.Animation;
import com.finderfeed.fdlib.systems.bedrock.animations.TransitionAnimation;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimationSystem;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimationTicker;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.head.HeadControllerContainer;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.head.IHasHead;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.head.HeadControllerContainer.Mode;
import com.finderfeed.fdlib.systems.bedrock.models.FDModel;
import com.finderfeed.fdlib.systems.shake.FDShakeData;
import com.finderfeed.fdlib.systems.shake.PositionedScreenShakePacket;
import com.finderfeed.fdlib.util.FDTargetFinder;
import com.finderfeed.raids_enhanced.REUtil;
import com.finderfeed.raids_enhanced.content.entities.FDRaider;
import com.finderfeed.raids_enhanced.content.entities.falling_block.REFallingBlock;
import com.finderfeed.raids_enhanced.content.entities.raid_blimp.RaiderBomb;
import com.finderfeed.raids_enhanced.content.entities.raid_blimp.raid_airship_parts.RaidBlimpPart;
import com.finderfeed.raids_enhanced.content.particles.SimpleTexturedParticleOptions;
import com.finderfeed.raids_enhanced.init.REAnimations;
import com.finderfeed.raids_enhanced.init.REModels;
import com.finderfeed.raids_enhanced.init.REParticles;
import com.finderfeed.raids_enhanced.init.RESounds;
import java.util.List;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class GolemOfLastResort extends FDRaider implements IHasHead<GolemOfLastResort>, AutoSerializable {
   public static final String MAIN_LAYER = "IDLE";
   public static final String WALKING_LAYER = "WALKING";
   public static final String WHIRLWIND_LAYER = "WHIRLWIND";
   private static FDModel clientModel;
   private static FDModel serverModel;
   @SerializableField
   private int golemBombsCooldown = 0;
   @SerializableField
   private int golemSpecialAttackCooldown = 0;
   private boolean isMeleeAttacking = false;
   private boolean isRangedAttacking = false;
   private boolean walkingWithHands = true;
   private int destroyBlocksTick;
   protected HeadControllerContainer<GolemOfLastResort> headControllerContainer;

   public GolemOfLastResort(EntityType<? extends Raider> p_37839_, Level p_37840_) {
      super(p_37839_, p_37840_);
      this.headControllerContainer = new HeadControllerContainer(this).addHeadController(getModel(p_37840_), "head");
      this.headControllerContainer.setControllersMode(Mode.LOOK);
      this.lookControl = this.headControllerContainer;
      this.getAnimationSystem().startAnimation("IDLE", AnimationTicker.builder(REAnimations.ILLAGER_GOLEM_IDLE).build());
      this.getAnimationSystem().setAnimationsApplyListener(this::onAnimationsApplied);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{Raider.class}).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(3, new GolemOfLastResort.GolemBombsAttack(this));
      this.goalSelector.addGoal(4, new GolemOfLastResort.GolemMeleeAttackGoal(this, 2.5F, 2.5F));
      this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         this.destroyBlocks();
         this.golemBombsCooldown = Mth.clamp(this.golemBombsCooldown - 1, 0, Integer.MAX_VALUE);
         if (this.getTarget() != null && this.distanceTo(this.getTarget()) < 3.0F) {
            this.golemSpecialAttackCooldown = Mth.clamp(this.golemSpecialAttackCooldown - 1, 0, Integer.MAX_VALUE);
         }

         this.setYRot(this.yBodyRot);
         AnimationSystem animSystem = this.getAnimationSystem();
         double x = this.getLookControl().getWantedX();
         double y = this.getLookControl().getWantedY();
         double z = this.getLookControl().getWantedZ();
         Vec3 deltaMovement = this.getDeltaMovement().multiply(1.0, 0.0, 1.0);
         double speed = deltaMovement.length();
         if (x == 0.0 && y == 0.0 && z == 0.0) {
            this.getLookControl().setLookAt(this.getEyePosition().add(this.getLookAngle()));
         }

         AnimationTicker ticker = animSystem.getTicker("IDLE");
         if (ticker != null) {
            Animation animation = ticker.getAnimation();
            if (speed > 0.01) {
               if (this.getTarget() == null) {
                  this.getLookControl().setLookAt(this.getEyePosition().add(this.getLookAngle()));
               }

               if (this.isIdleAnim(animation) || animation.isToNullTransition()) {
                  this.getAnimationSystem().startAnimation("IDLE", AnimationTicker.builder(REAnimations.ILLAGER_GOLEM_WALK).build());
               }

               if (!this.walkingWithHands) {
                  this.getAnimationSystem()
                     .startAnimation("WALKING", AnimationTicker.builder(REAnimations.ILLAGER_GOLEM_WALK_NO_HANDS).setToNullTransitionTime(10).build());
               } else {
                  this.getAnimationSystem().stopAnimation("WALKING");
               }
            } else {
               this.getAnimationSystem().stopAnimation("WALKING");
               if (this.isWalkingAnim(animation) || animation.isToNullTransition()) {
                  animSystem.startAnimation("IDLE", AnimationTicker.builder(REAnimations.ILLAGER_GOLEM_IDLE).build());
               }
            }
         } else {
            animSystem.startAnimation("IDLE", AnimationTicker.builder(REAnimations.ILLAGER_GOLEM_IDLE).build());
         }
      } else if (!this.isDeadOrDying()) {
         this.getHeadControllerContainer().clientTick();
      } else {
         this.getHeadControllerContainer().setControllersMode(Mode.ANIMATION);
      }
   }

   protected void tickDeath() {
      if (!this.level().isClientSide) {
         if (this.deathTime == 0) {
            this.getAnimationSystem().stopAnimation("IDLE");
            this.getAnimationSystem().stopAnimation("WALKING");
            this.getAnimationSystem().stopAnimation("WHIRLWIND");
            this.getAnimationSystem().startAnimation("IDLE", AnimationTicker.builder(REAnimations.ILLAGER_GOLEM_DEATH).build());
         } else if (this.deathTime >= 20) {
            this.spawnDeathParts();
            this.explode();
         }
      }

      this.deathTime++;
   }

   private void explode() {
      this.level().playSound(null, this.position().x, this.position().y, this.position().z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 5.0F, 1.0F);
      Vec3 pos = this.position();

      for (ServerPlayer serverPlayer : FDTargetFinder.getEntitiesInSphere(ServerPlayer.class, this.level(), pos, 160.0F)) {
         for (int i = 0; i < 2; i++) {
            Vec3 ppos = this.position()
               .add(this.random.nextFloat() * 2.0F - 1.0F, 0.25 + this.random.nextFloat() * 1.0F, this.random.nextFloat() * 2.0F - 1.0F);
            ((ServerLevel)this.level()).sendParticles(serverPlayer, ParticleTypes.EXPLOSION_EMITTER, true, ppos.x, ppos.y, ppos.z, 1, 0.0, 0.0, 0.0, 0.0);
         }
      }

      this.setRemoved(RemovalReason.DISCARDED);
   }

   private void spawnDeathParts() {
      for (int i = 0; i < 10; i++) {
         Vec3 v = new Vec3(1.0, 0.0, 0.0).yRot(this.random.nextFloat() * (float) Math.PI * 2.0F);
         Vec3 speed = v.scale(0.1F + this.random.nextFloat() * 1.0F).add(0.0, 0.15 + this.random.nextFloat() * 1.25F, 0.0);
         Vec3 startPos = this.position().add(0.0, 1.0F + this.random.nextFloat(), 0.0).add(v.scale(this.random.nextFloat() * 3.0F));
         RaidBlimpPart.summon(this.level(), startPos, speed, 3, this.random.nextInt(100) + 100);
      }
   }

   protected SoundEvent getDeathSound() {
      return RESounds.ILLAGER_GOLEM_DEATH.get();
   }

   private void destroyBlocks() {
      if (this.destroyBlocksTick > 0) {
         this.destroyBlocksTick--;
         if (this.destroyBlocksTick == 0 && EventHooks.canEntityGrief(this.level(), this)) {
            boolean flag = false;
            int l = Mth.floor(this.getBbWidth() / 2.0F + 1.5F);
            int i1 = Mth.floor(this.getBbHeight());

            for (BlockPos blockpos : BlockPos.betweenClosed(
               this.getBlockX() - l, this.getBlockY(), this.getBlockZ() - l, this.getBlockX() + l, this.getBlockY() + i1, this.getBlockZ() + l
            )) {
               Vec3 center = blockpos.getCenter();
               Vec3 pos = this.position();
               Vec3 between = pos.subtract(center).multiply(1.0, 0.0, 1.0);
               if (between.length() < 2.5) {
                  BlockState blockstate = this.level().getBlockState(blockpos);
                  if (blockstate.canEntityDestroy(this.level(), blockpos, this)
                     && WitherBoss.canDestroy(blockstate)
                     && EventHooks.onEntityDestroyBlock(this, blockpos, blockstate)
                     && !blockstate.getCollisionShape(this.level(), blockpos).isEmpty()) {
                     flag = this.level().destroyBlock(blockpos, true, this) || flag;
                  }
               }
            }

            if (flag) {
               this.level().levelEvent(null, 1022, this.blockPosition(), 0);
            }
         }
      }
   }

   public void checkDespawn() {
      if (!EventHooks.checkMobDespawn(this)) {
         if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
         } else {
            this.noActionTime = 0;
         }
      }
   }

   private void onAnimationsApplied(FDModel model, Float partialTicks) {
      AnimationSystem system = this.getAnimationSystem();
      AnimationTicker ticker = system.getTicker("WHIRLWIND");
      float strength = 1.0F;
      if (ticker != null) {
         float time = ticker.getTime(partialTicks);
         Animation animation = ticker.getAnimation();
         float animTime = animation.getAnimTime();
         float p = time / animTime;
         if (p < 0.25F) {
            strength = 1.0F - p / 0.25F;
         } else if (p >= 0.75F) {
            strength = (p - 0.75F) / 0.25F;
         } else {
            strength = 0.0F;
         }
      }

      strength = Mth.clamp(strength, 0.0F, 1.0F);
      this.getAnimationSystem().setVariable("variable.hand_strength", strength);
   }

   public void applyRaidBuffs(ServerLevel p_348605_, int p_37844_, boolean p_37845_) {
   }

   public boolean hurt(DamageSource src, float damage) {
      Entity entity = src.getEntity();
      if ((entity == null || entity.distanceTo(this) < 10.0F) && super.hurt(src, damage)) {
         this.destroyBlocksTick = 5;
         return true;
      } else {
         return false;
      }
   }

   public boolean isInvulnerableTo(DamageSource src) {
      return super.isInvulnerableTo(src) || src.is(DamageTypes.MOB_PROJECTILE) || src.is(DamageTypes.ARROW);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      this.autoSave(tag);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.autoLoad(tag);
   }

   public boolean isIdleAnim(Animation animation) {
      if (animation == null) {
         return false;
      } else {
         return animation instanceof TransitionAnimation transitionAnimation
            ? transitionAnimation.getTransitionTo() == REAnimations.ILLAGER_GOLEM_IDLE.get()
            : animation == REAnimations.ILLAGER_GOLEM_IDLE.get();
      }
   }

   public boolean isWalkingAnim(Animation animation) {
      if (animation == null) {
         return false;
      } else {
         return animation instanceof TransitionAnimation transitionAnimation
            ? transitionAnimation.getTransitionTo() == REAnimations.ILLAGER_GOLEM_WALK.get()
            : animation == REAnimations.ILLAGER_GOLEM_WALK.get();
      }
   }

   public static FDModel getModel(Level level) {
      if (!level.isClientSide) {
         if (serverModel == null) {
            serverModel = new FDModel(REModels.GOLEM_OF_LAST_RESORT.get());
         }

         return serverModel;
      } else {
         if (clientModel == null) {
            clientModel = new FDModel(REModels.GOLEM_OF_LAST_RESORT.get());
         }

         return clientModel;
      }
   }

   public SoundEvent getCelebrateSound() {
      return null;
   }

   public HeadControllerContainer<GolemOfLastResort> getHeadControllerContainer() {
      return this.headControllerContainer;
   }

   protected SoundEvent getHurtSound(DamageSource p_33034_) {
      return RESounds.RAID_GOLEM_HIT.get();
   }

   public static class GolemBombsAttack extends Goal {
      public int attackTime;
      public GolemOfLastResort golem;

      public GolemBombsAttack(GolemOfLastResort golem) {
         this.golem = golem;
      }

      public boolean canUse() {
         return this.golem.getTarget() != null
            && this.golem.onGround()
            && this.golem.golemBombsCooldown <= 0
            && this.golem.random.nextFloat() < 0.05
            && !this.golem.isMeleeAttacking
            && (this.golem.getTarget().distanceTo(this.golem) > 4.0F || Math.abs(this.golem.getY() - this.golem.getTarget().getY()) > 2.0);
      }

      public void start() {
         super.start();
         this.golem.isMeleeAttacking = false;
         this.golem.isRangedAttacking = true;
         this.attackTime = 0;
      }

      public void tick() {
         super.tick();
         LivingEntity target = this.golem.getTarget();
         if (target == null) {
            this.golem.isRangedAttacking = false;
         } else {
            AnimationSystem system = this.golem.getAnimationSystem();
            this.golem.getNavigation().stop();
            this.golem.getHeadControllerContainer().setControllersMode(Mode.ANIMATION);
            if (this.attackTime == 0) {
               this.golem.setDeltaMovement(Vec3.ZERO);
               system.startAnimation("IDLE", AnimationTicker.builder(REAnimations.ILLAGER_GOLEM_BOMBS).build());
            } else if (this.attackTime == 12) {
               Matrix4f t = this.golem.getModelPartTransformation(this.golem, "cannon", GolemOfLastResort.getModel(this.golem.level()));
               Vec3 pos = new Vec3(t.transformPosition(new Vector3f())).add(this.golem.position());

               for (ServerPlayer player : FDTargetFinder.getEntitiesInSphere(ServerPlayer.class, target.level(), pos, 120.0F)) {
                  ((ServerLevel)this.golem.level())
                     .sendParticles(
                        player,
                        new SimpleTexturedParticleOptions(REParticles.EXPLOSION.get(), 1.5F, 9),
                        true,
                        pos.x,
                        pos.y + 0.75,
                        pos.z,
                        1,
                        0.0,
                        0.0,
                        0.0,
                        0.0
                     );
               }

               ((ServerLevel)this.golem.level()).playSound(null, pos.x, pos.y, pos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 3.0F, 1.5F);
               ((ServerLevel)this.golem.level()).playSound(null, pos.x, pos.y, pos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 3.0F, 0.75F);
               List<Player> targets = FDTargetFinder.getEntitiesInCylinder(
                  Player.class, this.golem.level(), this.golem.position().add(0.0, -20.0, 0.0), 40.0F, 20.0F
               );

               for (Player tr : targets) {
                  if (!tr.isSpectator() && !tr.isCreative()) {
                     this.launchBombAtTarget(tr, pos);
                  }
               }

               if (!targets.contains(target)) {
                  this.launchBombAtTarget(target, pos);
               }
            } else if (this.attackTime > 25) {
               this.golem.getHeadControllerContainer().setControllersMode(Mode.LOOK);
               this.golem.golemBombsCooldown = 100;
               this.golem.isRangedAttacking = false;
            } else if (this.attackTime == 9) {
               ((ServerLevel)this.golem.level())
                  .playSound(
                     null,
                     this.golem.getX(),
                     this.golem.getY() + 1.0,
                     this.golem.getZ(),
                     RESounds.RAID_GOLEM_PREPARE_PUNCH.get(),
                     SoundSource.HOSTILE,
                     2.0F,
                     0.75F
                  );
               ((ServerLevel)this.golem.level())
                  .playSound(
                     null,
                     this.golem.getX(),
                     this.golem.getY() + 1.0,
                     this.golem.getZ(),
                     RESounds.RAID_GOLEM_PREPARE_PUNCH.get(),
                     SoundSource.HOSTILE,
                     2.0F,
                     1.25F
                  );
            }

            this.attackTime++;
         }
      }

      private void launchBombAtTarget(LivingEntity target, Vec3 bombStartPos) {
         Vec3 speed = REUtil.calculateMortarProjectileVelocity(bombStartPos, target.position(), -0.08, 20 + this.golem.random.nextInt(5));
         RaiderBomb.summon(this.golem, bombStartPos, speed);
      }

      public void stop() {
         this.golem.getHeadControllerContainer().setControllersMode(Mode.LOOK);
         this.golem.getNavigation().stop();
         this.golem.getLookControl().setLookAt(this.golem.getEyePosition().add(this.golem.getLookAngle()));
      }

      public boolean canContinueToUse() {
         return this.golem.getTarget() != null && this.golem.golemBombsCooldown <= 0 && this.golem.isRangedAttacking;
      }
   }

   public static class GolemMeleeAttackGoal extends Goal {
      public int meleeAttackType = 0;
      public int attackTime = 0;
      public GolemOfLastResort golem;
      public float golemAttackHeight;
      public float horizontalAttackRange;

      public GolemMeleeAttackGoal(GolemOfLastResort golem, float golemAttackHeight, float horizontalAttackRange) {
         this.golem = golem;
         this.golemAttackHeight = golemAttackHeight;
         this.horizontalAttackRange = horizontalAttackRange;
      }

      public boolean canUse() {
         return this.golem.getTarget() != null && !this.golem.isRangedAttacking;
      }

      public void start() {
         this.attackTime = 0;
      }

      public boolean isTargetInAttackRange(LivingEntity target) {
         Vec3 between = target.position().subtract(this.golem.position());
         double yDiff = between.y;
         if (yDiff >= -1.0 && yDiff <= this.golemAttackHeight) {
            double horizontalDistance = between.multiply(1.0, 0.0, 1.0).length();
            return horizontalDistance < this.horizontalAttackRange;
         } else {
            return false;
         }
      }

      public void tick() {
         if (this.meleeAttackType > 2) {
            this.meleeAttackType = 0;
         }

         LivingEntity target = this.golem.getTarget();
         if (target != null) {
            if (this.attackTime <= 0) {
               this.golem.isMeleeAttacking = false;
               if (this.isTargetInAttackRange(target)) {
                  this.golem.getLookControl().setLookAt(target);
                  this.golem.lookAt(Anchor.FEET, target.position());
                  this.golem.getHeadControllerContainer().setControllersMode(Mode.ANIMATION);
                  if (this.golem.golemSpecialAttackCooldown > 0) {
                     this.meleeAttackType = 0;
                  } else {
                     this.golem.golemSpecialAttackCooldown = 60 + this.golem.random.nextInt(20);
                     this.meleeAttackType = this.golem.random.nextInt(2) + 1;
                  }

                  this.startAnimationAndAttackTicker(this.meleeAttackType);
               } else {
                  Vec3 movement = this.golem.getDeltaMovement();
                  Vec3 between = target.position().subtract(this.golem.position()).multiply(1.0, 0.0, 1.0);
                  if (!between.equals(Vec3.ZERO) && between.length() > 0.5) {
                     Vec3 addition = between.normalize().scale(0.01F);
                     this.golem.setDeltaMovement(movement.add(addition));
                  }

                  this.golem.walkingWithHands = true;
                  this.golem.getHeadControllerContainer().setControllersMode(Mode.LOOK);
                  this.golem.getLookControl().setLookAt(target);
                  PathNavigation navigation = this.golem.getNavigation();
                  navigation.moveTo(target.getX(), target.getY(), target.getZ(), 1.2F);
               }
            } else {
               this.golem.isMeleeAttacking = true;
               this.attackTime--;
               if (this.meleeAttackType == 0) {
                  this.defaultMeleeAttack();
               } else if (this.meleeAttackType == 1) {
                  this.heavyAttack();
               } else if (this.meleeAttackType == 2) {
                  this.whirlwindAttack();
               }
            }
         }
      }

      private void whirlwindAttack() {
         this.golem.walkingWithHands = true;
         LivingEntity target = this.golem.getTarget();
         this.golem.getNavigation().stop();
         if (target != null && this.attackTime < 16) {
            Vec3 movement = this.golem.getDeltaMovement();
            Vec3 between = target.position().subtract(this.golem.position()).multiply(1.0, 0.0, 1.0);
            if (!between.equals(Vec3.ZERO) && between.length() > 0.5) {
               Vec3 addition = between.normalize().scale(0.1F);
               this.golem.setDeltaMovement(movement.add(addition));
            }

            this.golem.lookAt(Anchor.FEET, target.position());
         }

         if (this.attackTime > 10 && this.attackTime < 16) {
            this.golem
               .level()
               .playSound(
                  null,
                  this.golem.getX(),
                  this.golem.getY(),
                  this.golem.getZ(),
                  RESounds.RAID_GOLEM_SWING.get(),
                  SoundSource.HOSTILE,
                  1.0F,
                  1.1F + this.golem.random.nextFloat() * 0.2F
               );

            for (LivingEntity t : FDTargetFinder.getEntitiesInCylinder(
               LivingEntity.class, this.golem.level(), this.golem.position().add(0.0, -1.0, 0.0), this.golemAttackHeight + 1.0F, this.horizontalAttackRange
            )) {
               if (t != this.golem) {
                  this.golem.doHurtTarget(t);
                  if (t instanceof Player player && player.getUseItem().getItem() instanceof ShieldItem) {
                     player.disableShield();
                  }
               }
            }
         } else if (this.attackTime == 19) {
            this.golem
               .level()
               .playSound(
                  null, this.golem.getX(), this.golem.getY(), this.golem.getZ(), RESounds.RAID_GOLEM_PREPARE_PUNCH.get(), SoundSource.HOSTILE, 1.0F, 0.9F
               );
         }
      }

      private void heavyAttack() {
         this.golem.walkingWithHands = false;
         this.golem.getLookControl().setLookAt(this.golem.getTarget());
         this.golem.getNavigation().stop();
         this.golem.lookAt(Anchor.FEET, this.golem.getTarget().position());
         Vec3 smackPos = this.golem.position().add(this.golem.getForward().scale(1.5));
         if (this.attackTime == 10) {
            this.golem
               .level()
               .playSound(
                  null,
                  smackPos.x,
                  smackPos.y,
                  smackPos.z,
                  RESounds.RAID_GOLEM_HEAVY_STRKE.get(),
                  SoundSource.HOSTILE,
                  2.0F,
                  0.8F + this.golem.random.nextFloat() * 0.2F
               );
            REUtil.golemSmackParticles((ServerLevel)this.golem.level(), smackPos, 60.0);
            BlockPos basePos = new BlockPos((int)Math.floor(smackPos.x), (int)Math.floor(smackPos.y + 0.1F) - 1, (int)Math.floor(smackPos.z));

            for (int x = -1; x <= 1; x++) {
               for (int z = -1; z <= 1; z++) {
                  if (Math.abs(x + z) <= 1 || !(this.golem.random.nextFloat() > 0.5)) {
                     BlockPos pos = basePos.offset(x, 0, z);
                     BlockState blockState = this.golem.level().getBlockState(pos);
                     Vec3 summonPos = pos.getCenter();
                     Vec3 between = summonPos.subtract(smackPos).multiply(1.0, 0.0, 1.0);
                     Vec3 speed = between.normalize()
                        .yRot((float) (-Math.PI / 8) + this.golem.level().random.nextFloat() * (float) Math.PI / 4.0F)
                        .scale(0.05F + this.golem.random.nextFloat() * 0.1F)
                        .add(0.0, 0.2 + this.golem.level().random.nextFloat() * 0.2F, 0.0);
                     REFallingBlock var10 = REFallingBlock.summon(this.golem.level(), blockState, pos.getCenter(), speed, 0.08F);
                  }
               }
            }

            PositionedScreenShakePacket.send(
               (ServerLevel)this.golem.level(), FDShakeData.builder().frequency(5.0F).amplitude(5.0F).inTime(0).stayTime(0).outTime(6).build(), smackPos, 10.0
            );
            Vec3 forward = this.golem.getForward();
            Vec2 direction = new Vec2((float)forward.x, (float)forward.z);

            for (LivingEntity t : FDTargetFinder.getEntitiesInArc(
               LivingEntity.class,
               this.golem.level(),
               this.golem.position().add(0.0, -1.0, 0.0),
               direction,
               (float) Math.PI,
               this.golemAttackHeight + 1.0F,
               this.horizontalAttackRange
            )) {
               if (t != this.golem) {
                  this.golem.doHurtTarget(t);
                  if (t instanceof Player player && player.getUseItem().getItem() instanceof ShieldItem) {
                     player.disableShield();
                  }
               }
            }
         } else if (this.attackTime == 16) {
            this.golem
               .level()
               .playSound(
                  null,
                  this.golem.getX(),
                  this.golem.getY(),
                  this.golem.getZ(),
                  RESounds.RAID_GOLEM_PREPARE_PUNCH.get(),
                  SoundSource.HOSTILE,
                  2.0F,
                  0.9F + this.golem.random.nextFloat() * 0.2F
               );
         } else if (this.attackTime == 12) {
            this.golem
               .level()
               .playSound(
                  null,
                  this.golem.getX(),
                  this.golem.getY(),
                  this.golem.getZ(),
                  RESounds.RAID_GOLEM_SWING.get(),
                  SoundSource.HOSTILE,
                  1.0F,
                  1.1F + this.golem.random.nextFloat() * 0.2F
               );
         }
      }

      private void defaultMeleeAttack() {
         this.golem.walkingWithHands = false;
         this.golem.getLookControl().setLookAt(this.golem.getEyePosition().add(this.golem.getLookAngle()));
         this.golem.lookAt(Anchor.FEET, this.golem.getTarget().position());
         this.golem.getNavigation().stop();
         if (this.attackTime == 7) {
            Vec3 forward = this.golem.getForward();
            Vec2 direction = new Vec2((float)forward.x, (float)forward.z);

            for (LivingEntity t : FDTargetFinder.getEntitiesInArc(
               LivingEntity.class,
               this.golem.level(),
               this.golem.position().add(0.0, -1.0, 0.0),
               direction,
               (float) Math.PI,
               this.golemAttackHeight + 1.0F,
               this.horizontalAttackRange
            )) {
               if (t != this.golem) {
                  this.golem.doHurtTarget(t);
               }
            }
         } else if (this.attackTime == 9) {
            this.golem
               .level()
               .playSound(
                  null,
                  this.golem.getX(),
                  this.golem.getY(),
                  this.golem.getZ(),
                  RESounds.RAID_GOLEM_SWING.get(),
                  SoundSource.HOSTILE,
                  1.0F,
                  1.1F + this.golem.random.nextFloat() * 0.2F
               );
         }
      }

      private void startAnimationAndAttackTicker(int attackType) {
         if (attackType == 0) {
            Animation animation = this.golem.random.nextBoolean()
               ? (Animation)REAnimations.ILLAGER_GOLEM_STRIKE_1.get()
               : (Animation)REAnimations.ILLAGER_GOLEM_STRIKE_2.get();
            this.golem.getAnimationSystem().startAnimation("IDLE", AnimationTicker.builder(animation).important().setSpeed(1.0F).build());
            this.golem
               .level()
               .playSound(
                  null,
                  this.golem.getX(),
                  this.golem.getY(),
                  this.golem.getZ(),
                  RESounds.RAID_GOLEM_PREPARE_PUNCH.get(),
                  SoundSource.HOSTILE,
                  1.0F,
                  0.9F + this.golem.random.nextFloat() * 0.2F
               );
            this.attackTime = 10;
         } else if (attackType == 1) {
            this.golem
               .getAnimationSystem()
               .startAnimation("IDLE", AnimationTicker.builder(REAnimations.ILLAGER_GOLEM_HEAVY_STRIKE).important().setSpeed(0.95F).build());
            this.attackTime = 17;
         } else {
            this.golem
               .getAnimationSystem()
               .startAnimation("WHIRLWIND", AnimationTicker.builder(REAnimations.ILLAGER_GOLEM_WHIRLWIND).setToNullTransitionTime(0).build());
            this.attackTime = 20;
         }
      }

      public void stop() {
         this.golem.walkingWithHands = true;
         this.golem.isMeleeAttacking = false;
         this.golem.getNavigation().stop();
         this.golem.getLookControl().setLookAt(this.golem.getEyePosition().add(this.golem.getLookAngle()));
      }

      public boolean canContinueToUse() {
         return this.golem.getTarget() != null && !this.golem.isRangedAttacking;
      }
   }
}
