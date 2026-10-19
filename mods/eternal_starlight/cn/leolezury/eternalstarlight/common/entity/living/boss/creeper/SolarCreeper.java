package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.MoveToTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SolarCreeper extends ESBoss {
   private static final String TAG_INTRO_COMPLETED = "intro_completed";
   private int oldAnimationTicks;
   private int animationTicks;
   private boolean introCompleted = false;
   private final BehaviorManager<SolarCreeper> behaviorManager = new BehaviorManager(this, List.of(new SolarCreeperIntroPhase()));

   public float getAnimationTicks(float partialTicks) {
      return Mth.lerp(partialTicks, this.oldAnimationTicks, this.animationTicks);
   }

   public void finishIntro() {
      this.introCompleted = true;
   }

   public SolarCreeper(EntityType<? extends SolarCreeper> entityType, Level level) {
      super(entityType, level);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new MoveToTargetGoal(this, 1.0));
      this.goalSelector.addGoal(2, new LookAtTargetGoal(this));
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[]{SolarCreeper.class}).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, AbstractVillager.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.solarCreeper.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.solarCreeper.armor())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.solarCreeper.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.3)
         .add(Attributes.ATTACK_DAMAGE, 12.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.9);
   }

   public void stopAllAnimStates() {
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
      if (accessor.equals(BEHAVIOR_STATE)) {
         this.stopAllAnimStates();
         this.getBehaviorState();
      }

      super.onSyncedDataUpdated(accessor);
   }

   public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
      return false;
   }

   protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
   }

   public void aiStep() {
      if (!this.level().isClientSide) {
         this.setNoGravity(!this.introCompleted);
      }

      super.aiStep();
      if (!this.level().isClientSide) {
         if (this.getTarget() != null && !this.getTarget().isAlive()) {
            this.setTarget(null);
         }

         if (!this.isNoAi() && this.isAlive()) {
            this.behaviorManager.tick();
            if (!this.introCompleted && this.getBehaviorState() != 1) {
               this.setBehaviorState(1);
               this.setBehaviorTicks(0);
            }
         }
      } else {
         this.oldAnimationTicks = this.animationTicks;
         this.animationTicks = this.getBehaviorTicks();
      }
   }

   @Override
   public boolean canBossMove() {
      return this.introCompleted;
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.introCompleted = compoundTag.getBoolean("intro_completed");
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("intro_completed", this.introCompleted);
   }
}
