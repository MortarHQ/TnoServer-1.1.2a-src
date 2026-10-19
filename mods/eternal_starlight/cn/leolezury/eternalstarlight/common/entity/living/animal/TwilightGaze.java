package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class TwilightGaze extends WaterAnimal {
   private BlockPos abyssPos;
   public AnimationState swimAnimationState = new AnimationState();

   public TwilightGaze(EntityType<? extends TwilightGaze> type, Level level) {
      super(type, level);
      this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
      this.lookControl = new SmoothSwimmingLookControl(this, 10);
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
      this.setAirSupply(this.getMaxAirSupply());
      this.setXRot(0.0F);
      return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.2, true));
      this.goalSelector.addGoal(1, new TwilightGaze.TwilightGazeRandomSwimmingGoal(1.0, 10));
      this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[]{TwilightGaze.class}).setAlertOthers(new Class[0]));
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.twilightGaze.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.twilightGaze.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.twilightGaze.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.twilightGaze.followRange())
         .add(Attributes.MOVEMENT_SPEED, 1.2);
   }

   protected PathNavigation createNavigation(Level level) {
      return new WaterBoundPathNavigation(this, level);
   }

   public int getMaxHeadXRot() {
      return 1;
   }

   public int getMaxHeadYRot() {
      return 1;
   }

   protected boolean canRide(Entity entity) {
      return true;
   }

   public void aiStep() {
      super.aiStep();
      if (!this.level().isClientSide) {
         if (this.tickCount % 20 == 0 && this.level().getBiome(this.blockPosition()).is(ESBiomes.THE_ABYSS)) {
            this.abyssPos = this.blockPosition();
         }
      } else {
         this.swimAnimationState.startIfStopped(this.tickCount);
      }
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return ESSoundEvents.TWILIGHT_GAZE_HURT.get();
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return ESSoundEvents.TWILIGHT_GAZE_DEATH.get();
   }

   public void travel(Vec3 vec3) {
      if (this.isEffectiveAi() && this.isInWater()) {
         this.moveRelative(this.getSpeed(), vec3);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
         if (this.getTarget() == null) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
         }
      } else {
         super.travel(vec3);
      }
   }

   public boolean canBeLeashed() {
      return true;
   }

   public static boolean checkTwilightGazeSpawnRules(
      EntityType<? extends TwilightGaze> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource
   ) {
      int seaLevel = levelAccessor.getSeaLevel();
      return blockPos.getY() <= seaLevel - 40
         && levelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER)
         && levelAccessor.getBlockState(blockPos.above()).is(Blocks.WATER)
         && ESConfig.INSTANCE.mobsConfig.twilightGaze.canSpawn();
   }

   private class TwilightGazeRandomSwimmingGoal extends RandomSwimmingGoal {
      public TwilightGazeRandomSwimmingGoal(double d, int i) {
         super(TwilightGaze.this, d, i);
      }

      @Nullable
      protected Vec3 getPosition() {
         Vec3 pos = super.getPosition();
         if (TwilightGaze.this.abyssPos == null) {
            return pos;
         } else {
            return pos == null
               ? null
               : (TwilightGaze.this.level().getBiome(BlockPos.containing(pos)).is(ESBiomes.THE_ABYSS) ? pos : TwilightGaze.this.abyssPos.getCenter());
         }
      }
   }
}
