package com.mongoose.royalvariations.common.entities.hostile;

import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.common.attachments.RVCapHelper;
import com.mongoose.royalvariations.common.effects.RVEffects;
import com.mongoose.royalvariations.utils.CustomExplosion;
import com.mongoose.royalvariations.utils.MathHelper;
import com.mongoose.royalvariations.utils.MobUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.gameevent.GameEvent;

public class RoyalCreeper extends RoyalMonster implements PowerableMob {
   private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(RoyalCreeper.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(RoyalCreeper.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> DATA_IS_POWERED = SynchedEntityData.defineId(RoyalCreeper.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(RoyalCreeper.class, EntityDataSerializers.BOOLEAN);
   private static final AttributeModifier DETONATE_SPEED_MODIFIER = new AttributeModifier(
      RoyalVariations.location("royal_creeper_detonation_speed"), -0.4, Operation.ADD_MULTIPLIED_TOTAL
   );
   public static String IDLE = "idle";
   public static String BLESS = "bless";
   public static String DETONATE = "detonate";
   private boolean isWalking;
   private int toIdleTick;
   private int oldSwell;
   private int swell;
   private int maxSwell = 45;
   private int droppedSkulls;
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState toIdleAnimationState = new AnimationState();
   public AnimationState blessAnimationState = new AnimationState();
   public AnimationState detonateAnimationState = new AnimationState();
   public AnimationState blinkAnimationState = new AnimationState();
   public AnimationState crownAnimationState = new AnimationState();

   public RoyalCreeper(EntityType<? extends Monster> p_33002_, Level p_33003_) {
      super(p_33002_, p_33003_);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(1, new RoyalCreeper.KnightingGoal(this));
      this.goalSelector.addGoal(2, new RoyalCreeper.RoyalSwellGoal(this));
      this.goalSelector.addGoal(3, new AvoidEntityGoal(this, Ocelot.class, 6.0F, 1.0, 1.2));
      this.goalSelector.addGoal(3, new AvoidEntityGoal(this, Cat.class, 6.0F, 1.0, 1.2));
      this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.1, false));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 35.0)
         .add(Attributes.FOLLOW_RANGE, 16.0)
         .add(Attributes.MOVEMENT_SPEED, 0.23)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
         .add(Attributes.ARMOR, 9.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ANIM_STATE, 0);
      builder.define(DATA_SWELL_DIR, -1);
      builder.define(DATA_IS_POWERED, false);
      builder.define(DATA_IS_IGNITED, false);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      if ((Boolean)this.entityData.get(DATA_IS_POWERED)) {
         compoundTag.putBoolean("powered", true);
      }

      compoundTag.putShort("Fuse", (short)this.maxSwell);
      compoundTag.putBoolean("ignited", this.isIgnited());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.entityData.set(DATA_IS_POWERED, compoundTag.getBoolean("powered"));
      if (compoundTag.contains("Fuse", 99)) {
         this.maxSwell = compoundTag.getShort("Fuse");
      }

      if (compoundTag.getBoolean("ignited")) {
         this.ignite();
      }
   }

   public void setAnimationState(String input) {
      this.setAnimationState(this.getAnimationState(input));
   }

   public void setAnimationState(int id) {
      this.entityData.set(ANIM_STATE, id);
   }

   public int getAnimationState(String animation) {
      if (Objects.equals(animation, IDLE)) {
         return 0;
      } else if (Objects.equals(animation, BLESS)) {
         return 1;
      } else {
         return Objects.equals(animation, DETONATE) ? 2 : 0;
      }
   }

   public void stopAllAnimation() {
      for (AnimationState state : this.getAnimations()) {
         state.stop();
      }
   }

   public int getCurrentAnimation() {
      return (Integer)this.entityData.get(ANIM_STATE);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_219422_) {
      if (ANIM_STATE.equals(p_219422_) && this.level().isClientSide) {
         switch (this.entityData.get(ANIM_STATE)) {
            case 0:
               this.stopAllAnimation();
               break;
            case 1:
               this.stopAllAnimation();
               this.blessAnimationState.startIfStopped(this.tickCount);
               break;
            case 2:
               this.stopAllAnimation();
               this.detonateAnimationState.startIfStopped(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_219422_);
   }

   public List<AnimationState> getAnimations() {
      List<AnimationState> animationStates = new ArrayList<>();
      animationStates.add(this.blessAnimationState);
      animationStates.add(this.detonateAnimationState);
      return animationStates;
   }

   public boolean isCurrentAnimation(String string) {
      return this.getCurrentAnimation() == this.getAnimationState(string);
   }

   public boolean isAlliedTo(Entity p_32665_) {
      if (p_32665_ == null) {
         return false;
      } else if (p_32665_ == this) {
         return true;
      } else {
         return !(p_32665_ instanceof Creeper) ? super.isAlliedTo(p_32665_) : this.getTeam() == null && p_32665_.getTeam() == null;
      }
   }

   protected SoundEvent getHurtSound(DamageSource p_34327_) {
      return SoundEvents.CREEPER_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.CREEPER_DEATH;
   }

   public int getMaxFallDistance() {
      return this.getTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
   }

   public boolean causeFallDamage(float p_149687_, float p_149688_, DamageSource p_149689_) {
      boolean flag = super.causeFallDamage(p_149687_, p_149688_, p_149689_);
      this.swell += (int)(p_149687_ * 1.5F);
      if (this.swell > this.maxSwell - 5) {
         this.swell = this.maxSwell - 5;
      }

      return flag;
   }

   public void setTarget(@Nullable LivingEntity p_149691_) {
      if (!(p_149691_ instanceof Goat)) {
         super.setTarget(p_149691_);
      }
   }

   public boolean doHurtTarget(Entity p_32281_) {
      return true;
   }

   @Override
   public void tick() {
      if (this.isAlive()) {
         this.oldSwell = this.swell;
         if (this.isIgnited()) {
            this.setSwellDir(1);
         }

         int i = this.getSwellDir();
         if (i > 0 && this.swell == 0) {
            this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
            this.gameEvent(GameEvent.PRIME_FUSE);
         }

         this.swell += i;
         if (this.swell < 0) {
            this.swell = 0;
         }

         if (this.swell >= this.maxSwell) {
            this.swell = this.maxSwell;
            this.explodeCreeper();
         }
      }

      super.tick();
      if (this.level().isClientSide) {
         if (!this.walkAnimation.isMoving()) {
            if (this.isWalking) {
               this.isWalking = false;
               this.toIdleTick = MathHelper.secondsToTicks(0.5F);
            }
         } else {
            this.isWalking = true;
         }

         if (this.toIdleTick > 0) {
            this.toIdleTick--;
         }

         boolean flag = !this.walkAnimation.isMoving() && this.isCurrentAnimation(IDLE);
         this.toIdleAnimationState.animateWhen(this.toIdleTick > 0 && flag, this.tickCount);
         this.idleAnimationState.animateWhen(this.toIdleTick <= 0 && flag, this.tickCount);
         this.blinkAnimationState.startIfStopped(this.tickCount);
         this.crownAnimationState.startIfStopped(this.tickCount);
      } else {
         int i = this.getSwellDir();
         if (i > 0) {
            this.setAnimationState(DETONATE);
         } else if (this.isCurrentAnimation(DETONATE)) {
            this.setAnimationState(IDLE);
         }

         if (this.level() instanceof ServerLevel serverLevel) {
            AttributeInstance instance = this.getAttribute(Attributes.MOVEMENT_SPEED);
            if (instance != null) {
               if (i > 0) {
                  instance.removeModifier(DETONATE_SPEED_MODIFIER);
                  instance.addTransientModifier(DETONATE_SPEED_MODIFIER);
               } else if (instance.hasModifier(DETONATE_SPEED_MODIFIER.id())) {
                  instance.removeModifier(DETONATE_SPEED_MODIFIER);
               }
            }

            if (i > 0) {
               serverLevel.sendParticles(
                  (SimpleParticleType)RVParticleTypes.SPARKLE.get(), this.getX(), this.getY() + this.getBbHeight(), this.getZ(), 1, 0.2, 0.2, 0.2, 0.1
               );
            }
         }
      }
   }

   public boolean isPowered() {
      return (Boolean)this.entityData.get(DATA_IS_POWERED);
   }

   public float getSwelling(float p_32321_) {
      return Mth.lerp(p_32321_, this.oldSwell, this.swell) / (this.maxSwell - 2);
   }

   public int getSwellDir() {
      return (Integer)this.entityData.get(DATA_SWELL_DIR);
   }

   public void setSwellDir(int p_32284_) {
      this.entityData.set(DATA_SWELL_DIR, p_32284_);
   }

   public void thunderHit(ServerLevel p_32286_, LightningBolt p_32287_) {
      super.thunderHit(p_32286_, p_32287_);
      this.entityData.set(DATA_IS_POWERED, true);
   }

   private void explodeCreeper() {
      if (!this.level().isClientSide) {
         float f = this.isPowered() ? 2.0F : 1.0F;
         this.dead = true;
         this.level().explode(this, this.getX(), this.getY(), this.getZ(), 6.0F * f, ExplosionInteraction.MOB);
         new CustomExplosion(this.level(), this, this.damageSources().explosion(this, this), this.getX(), this.getY(), this.getZ(), 12.0F * f, 1.0F) {
            @Override
            public void explodeHurt(Entity target, DamageSource damageSource, double x, double y, double z, double seen, float actualDamage) {
               if (target instanceof LivingEntity livingEntity && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target) && target != RoyalCreeper.this) {
                  livingEntity.addEffect(new MobEffectInstance(RVEffects.DAZED, 300));
               }
            }
         };
         this.discard();
         this.spawnLingeringCloud();
      }
   }

   private void spawnLingeringCloud() {
      Collection<MobEffectInstance> collection = this.getActiveEffects();
      if (!collection.isEmpty()) {
         AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
         areaeffectcloud.setRadius(2.5F);
         areaeffectcloud.setRadiusOnUse(-0.5F);
         areaeffectcloud.setWaitTime(10);
         areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
         areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / areaeffectcloud.getDuration());

         for (MobEffectInstance mobeffectinstance : collection) {
            areaeffectcloud.addEffect(new MobEffectInstance(mobeffectinstance));
         }

         this.level().addFreshEntity(areaeffectcloud);
      }
   }

   public boolean isIgnited() {
      return (Boolean)this.entityData.get(DATA_IS_IGNITED);
   }

   public void ignite() {
      this.entityData.set(DATA_IS_IGNITED, true);
   }

   public boolean canDropMobsSkull() {
      return this.isPowered() && this.droppedSkulls < 1;
   }

   public void increaseDroppedSkulls() {
      this.droppedSkulls++;
   }

   static class KnightingGoal extends Goal {
      public static int FLASH_TIME = 15;
      public RoyalCreeper royal;
      public List<Mob> list = new ArrayList<>();
      public int blessTick;

      public KnightingGoal(RoyalCreeper creeper) {
         this.royal = creeper;
         this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
      }

      public boolean canUse() {
         if (this.royal.getSwellDir() > 0) {
            return false;
         } else if (this.royal.getTarget() != null && this.royal.knightCool <= 0) {
            List<Mob> list = this.royal
               .level()
               .getEntitiesOfClass(
                  Mob.class, this.royal.getTarget().getBoundingBox().inflate(6.0), mob -> !(mob instanceof RoyalCreeper) && mob instanceof Enemy
               );
            list.removeIf(mob -> MobUtil.areAllies(mob, this.royal.getTarget()));
            list.removeIf(mob -> !mob.hasLineOfSight(this.royal));
            this.list = list;
            return !this.list.isEmpty();
         } else {
            return false;
         }
      }

      public boolean canContinueToUse() {
         return this.blessTick > 0;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void start() {
         super.start();
         this.royal.setAnimationState(RoyalCreeper.BLESS);
         this.royal.getNavigation().stop();
         this.royal.getMoveControl().strafe(0.0F, 0.0F);
         this.blessTick = FLASH_TIME + 1;
         if (this.royal.getTarget() != null) {
            this.royal.getLookControl().setLookAt(this.royal.getTarget(), 100.0F, 100.0F);
            this.royal.lookAt(this.royal.getTarget(), 100.0F, 100.0F);
         }

         if (!this.list.isEmpty()) {
            for (Mob mob : this.list) {
               if (!mob.hasEffect(RVEffects.TIME_BOMB)) {
                  RVCapHelper.setFlashTick(mob, FLASH_TIME);
               }
            }
         }
      }

      public void tick() {
         super.tick();
         this.blessTick--;
         this.royal.getNavigation().stop();
         this.royal.getMoveControl().strafe(0.0F, 0.0F);
         if (this.royal.getTarget() != null) {
            this.royal.getLookControl().setLookAt(this.royal.getTarget(), 100.0F, 100.0F);
            this.royal.lookAt(this.royal.getTarget(), 100.0F, 100.0F);
         }

         if (this.blessTick == 1 && !this.list.isEmpty()) {
            if (this.royal.level() instanceof ServerLevel serverLevel) {
               for (int i = 0; i <= serverLevel.getRandom().nextInt(10) + 10; i++) {
                  serverLevel.sendParticles(
                     (SimpleParticleType)RVParticleTypes.HOLY.get(),
                     this.royal.getRandomX(1.5),
                     this.royal.getRandomY(),
                     this.royal.getRandomZ(1.5),
                     0,
                     0.0,
                     1.0,
                     0.0,
                     1.0
                  );
               }
            }

            this.royal.playSound(SoundEvents.EVOKER_CAST_SPELL, 0.9F, 1.0F);

            for (Mob mob : this.list) {
               if (this.royal.level() instanceof ServerLevel serverLevel) {
                  mob.addEffect(new MobEffectInstance(RVEffects.TIME_BOMB, 100, 0, false, false));
                  mob.playSound(SoundEvents.AMETHYST_CLUSTER_FALL, 0.9F, 1.0F);
                  serverLevel.playSound(null, mob.getX(), mob.getY(), mob.getZ(), SoundEvents.AMETHYST_CLUSTER_FALL, SoundSource.HOSTILE, 0.9F, 1.0F);

                  for (int i = 0; i <= serverLevel.getRandom().nextInt(10) + 10; i++) {
                     serverLevel.sendParticles(
                        (SimpleParticleType)RVParticleTypes.HOLY.get(), mob.getRandomX(1.5), mob.getRandomY(), mob.getRandomZ(1.5), 0, 0.0, 1.0, 0.0, 1.0
                     );
                  }
               }
            }
         }
      }

      public void stop() {
         super.stop();
         this.blessTick = 0;
         this.royal.knightCool = 350;
         this.royal.setAnimationState(RoyalCreeper.IDLE);
         this.list.clear();
      }
   }

   public static class RoyalSwellGoal extends Goal {
      private final RoyalCreeper creeper;
      @Nullable
      private LivingEntity target;

      public RoyalSwellGoal(RoyalCreeper p_25919_) {
         this.creeper = p_25919_;
      }

      public boolean canUse() {
         LivingEntity livingentity = this.creeper.getTarget();
         return this.creeper.getSwellDir() > 0 || livingentity != null && this.creeper.distanceTo(livingentity) < 3.0;
      }

      public void start() {
         this.target = this.creeper.getTarget();
         if (this.target != null) {
            this.creeper.getNavigation().moveTo(this.target, 1.0);
         }
      }

      public void stop() {
         this.target = null;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         if (this.target == null) {
            this.creeper.setSwellDir(-1);
         } else if (this.creeper.distanceTo(this.target) > 7.0) {
            this.creeper.setSwellDir(-1);
         } else if (!this.creeper.getSensing().hasLineOfSight(this.target)) {
            this.creeper.setSwellDir(-1);
         } else {
            this.creeper.setSwellDir(1);
         }
      }
   }
}
