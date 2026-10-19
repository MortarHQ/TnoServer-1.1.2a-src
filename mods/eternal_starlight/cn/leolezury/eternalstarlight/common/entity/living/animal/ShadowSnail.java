package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

public class ShadowSnail extends Animal {
   private static final Ingredient FOOD_ITEMS = Ingredient.of(ESTags.Items.SHADOW_SNAIL_FOOD);
   protected static final EntityDataAccessor<Integer> HIDE_STATE = SynchedEntityData.defineId(ShadowSnail.class, EntityDataSerializers.INT);
   public AnimationState hideStartAnimationState = new AnimationState();
   public AnimationState hideAnimationState = new AnimationState();
   public AnimationState hideEndAnimationState = new AnimationState();
   private int transitionTicks;

   public int getHideState() {
      return (Integer)this.getEntityData().get(HIDE_STATE);
   }

   public void setHideState(int hideState) {
      this.getEntityData().set(HIDE_STATE, hideState);
   }

   public ShadowSnail(EntityType<? extends ShadowSnail> type, Level level) {
      super(type, level);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(HIDE_STATE, 0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new ShadowSnail.ShadowSnailDoNothingGoal());
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
      this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, FOOD_ITEMS, false));
      this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.shadowSnail.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.shadowSnail.armor())
         .add(Attributes.MOVEMENT_SPEED, 0.08);
   }

   public void aiStep() {
      super.aiStep();
      if (this.getHideState() != 0) {
         this.transitionTicks++;
      }

      if (this.getHideState() == 1 && this.transitionTicks > 20) {
         this.setHideState(2);
         this.transitionTicks = 0;
      }

      if (this.getHideState() == 2 && this.transitionTicks > 400) {
         this.setHideState(3);
         this.transitionTicks = 0;
      }

      if (this.getHideState() == 3 && this.transitionTicks > 20) {
         this.setHideState(0);
         this.transitionTicks = 0;
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      if (source.is(DamageTypeTags.PANIC_CAUSES) && this.getHideState() == 0) {
         this.setHideState(1);
         this.transitionTicks = 0;
         this.getNavigation().stop();
      }

      return super.hurt(source, !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && this.getHideState() == 2 ? amount / 8.0F : amount);
   }

   public void stopAllAnimStates() {
      this.hideStartAnimationState.stop();
      this.hideAnimationState.stop();
      this.hideEndAnimationState.stop();
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
      super.onSyncedDataUpdated(accessor);
      if (accessor.equals(HIDE_STATE) && this.getHideState() != 0) {
         this.stopAllAnimStates();
         switch (this.getHideState()) {
            case 1:
               this.hideStartAnimationState.start(this.tickCount);
               break;
            case 2:
               this.hideAnimationState.start(this.tickCount);
               break;
            case 3:
               this.hideEndAnimationState.start(this.tickCount);
         }
      }
   }

   public boolean isFood(ItemStack stack) {
      return FOOD_ITEMS.test(stack);
   }

   @Nullable
   public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
      return (AgeableMob)ESEntities.SHADOW_SNAIL.get().create(level);
   }

   protected void ageBoundaryReached() {
      super.ageBoundaryReached();
      if (!this.isBaby()) {
         this.spawnAtLocation((ItemLike)ESItems.SHADOW_SNAIL_SHELL.get());
      }
   }

   public static boolean checkShadowSnailSpawnRules(
      EntityType<? extends ShadowSnail> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && ESConfig.INSTANCE.mobsConfig.shadowSnail.canSpawn();
   }

   class ShadowSnailDoNothingGoal extends Goal {
      public ShadowSnailDoNothingGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public boolean canUse() {
         return ShadowSnail.this.getHideState() != 0;
      }
   }
}
