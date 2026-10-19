package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.interfaces.Charger;
import cn.leolezury.eternalstarlight.common.entity.living.goal.ChargeAttackGoal;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class Luminaris extends AbstractSchoolingFish implements Charger {
   protected static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(Luminaris.class, EntityDataSerializers.BOOLEAN);
   public AnimationState swimAnimationState = new AnimationState();
   public AnimationState chargeAnimationState = new AnimationState();

   public Luminaris(EntityType<? extends Luminaris> entityType, Level level) {
      super(entityType, level);
   }

   public boolean isCharging() {
      return (Boolean)this.getEntityData().get(CHARGING);
   }

   @Override
   public void setCharging(boolean charging) {
      this.getEntityData().set(CHARGING, charging);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(CHARGING, false);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.luminaris.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.luminaris.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.luminaris.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.luminaris.followRange());
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new ChargeAttackGoal(this, true, 3.0F, 3, 60, 0.5F));
      this.goalSelector.addGoal(3, new AvoidEntityGoal<Player>(this, Player.class, 8.0F, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test) {
         public boolean canUse() {
            return super.canUse() && this.mob.getTarget() == null;
         }

         public boolean canContinueToUse() {
            return super.canContinueToUse() && this.mob.getTarget() == null;
         }
      });
      this.goalSelector.addGoal(4, new Luminaris.FishSwimGoal(this));
      this.goalSelector.addGoal(5, new FollowFlockLeaderGoal(this));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[0]));
   }

   public void aiStep() {
      super.aiStep();
      if (this.level().isClientSide) {
         this.swimAnimationState.startIfStopped(this.tickCount);
         if (this.isCharging()) {
            this.chargeAnimationState.startIfStopped(this.tickCount);
         }
      }
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
      super.onSyncedDataUpdated(accessor);
      if (accessor.equals(CHARGING) && this.isCharging()) {
         this.chargeAnimationState.start(this.tickCount);
      }
   }

   protected boolean canRandomlySwim() {
      return !this.isFollower() && this.getTarget() == null;
   }

   public boolean isFollower() {
      return super.isFollower() && this.getTarget() == null;
   }

   public boolean isInvulnerableTo(DamageSource damageSource) {
      return super.isInvulnerableTo(damageSource) || damageSource.is(DamageTypes.HOT_FLOOR);
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return ESSoundEvents.LUMINARIS_HURT.get();
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return ESSoundEvents.LUMINARIS_DEATH.get();
   }

   protected SoundEvent getFlopSound() {
      return ESSoundEvents.LUMINARIS_FLOP.get();
   }

   public ItemStack getBucketItemStack() {
      return ESItems.LUMINARIS_BUCKET.get().getDefaultInstance();
   }

   public static boolean checkLuminarisSpawnRules(
      EntityType<? extends Luminaris> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource
   ) {
      int seaLevel = levelAccessor.getSeaLevel();
      return blockPos.getY() <= seaLevel - 40
         && levelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER)
         && levelAccessor.getBlockState(blockPos.above()).is(Blocks.WATER)
         && ESConfig.INSTANCE.mobsConfig.luminaris.canSpawn();
   }

   static class FishSwimGoal extends RandomSwimmingGoal {
      private final Luminaris fish;

      public FishSwimGoal(Luminaris abstractFish) {
         super(abstractFish, 1.0, 40);
         this.fish = abstractFish;
      }

      public boolean canUse() {
         return this.fish.canRandomlySwim() && super.canUse();
      }
   }
}
