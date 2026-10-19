package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class Luminofish extends AbstractSchoolingFish {
   protected static final EntityDataAccessor<Integer> SWELL_TICKS = SynchedEntityData.defineId(Luminofish.class, EntityDataSerializers.INT);
   public AnimationState swimAnimationState = new AnimationState();
   public AnimationState swellAnimationState = new AnimationState();

   public Luminofish(EntityType<? extends Luminofish> entityType, Level level) {
      super(entityType, level);
   }

   public int getSwellTicks() {
      return (Integer)this.getEntityData().get(SWELL_TICKS);
   }

   public void setSwellTicks(int swellTicks) {
      this.getEntityData().set(SWELL_TICKS, swellTicks);
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.luminofish.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.luminofish.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.luminofish.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.luminofish.followRange());
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(SWELL_TICKS, 0);
   }

   public void aiStep() {
      super.aiStep();
      if (!this.level().isClientSide) {
         int swellTicks = this.getSwellTicks();
         if (swellTicks > 0) {
            this.setSwellTicks(swellTicks - 1);
            LivingEntity target = this.getTarget();
            if (swellTicks == 10 && target != null && target.position().distanceTo(this.position()) < 3.0) {
               this.doHurtTarget(target);
               target.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
            }
         }
      } else {
         this.swimAnimationState.startIfStopped(this.tickCount);
      }
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
      super.onSyncedDataUpdated(accessor);
      if (accessor.equals(SWELL_TICKS) && this.getSwellTicks() == 20) {
         this.swellAnimationState.start(this.tickCount);
      }
   }

   public boolean isInvulnerableTo(DamageSource damageSource) {
      return super.isInvulnerableTo(damageSource) || damageSource.is(DamageTypes.HOT_FLOOR);
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      boolean flag = super.hurt(damageSource, amount);
      if (flag && damageSource.getDirectEntity() instanceof LivingEntity living && this.getSwellTicks() <= 0 && !this.level().isClientSide) {
         this.setSwellTicks(20);
         this.setTarget(living);
      }

      return flag;
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return ESSoundEvents.LUMINOFISH_HURT.get();
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return ESSoundEvents.LUMINOFISH_DEATH.get();
   }

   protected SoundEvent getFlopSound() {
      return ESSoundEvents.LUMINOFISH_FLOP.get();
   }

   public ItemStack getBucketItemStack() {
      return ESItems.LUMINOFISH_BUCKET.get().getDefaultInstance();
   }

   public static boolean checkLuminoFishSpawnRules(
      EntityType<? extends Luminofish> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource
   ) {
      int seaLevel = levelAccessor.getSeaLevel();
      return blockPos.getY() <= seaLevel - 40
         && levelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER)
         && levelAccessor.getBlockState(blockPos.above()).is(Blocks.WATER)
         && ESConfig.INSTANCE.mobsConfig.luminofish.canSpawn();
   }
}
