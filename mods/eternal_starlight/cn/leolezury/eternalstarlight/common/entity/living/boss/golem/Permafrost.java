package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.block.entity.LootChestBlockEntity;
import cn.leolezury.eternalstarlight.common.block.spawner.PermafrostSpawnerBlock;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.RandomFlyGoal;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.util.ModelSnapshot;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class Permafrost extends ESBoss {
   private final BehaviorManager<Permafrost> behaviorManager = new BehaviorManager(
      this,
      List.of(
         new PermafrostMeleePhase(),
         new PermafrostMeleeTransitionPhase(),
         new PermafrostMeleeEndPhase(),
         new PermafrostRangedPhase(),
         new PermafrostSneezePhase()
      )
   );
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState meleeAnimationState = new AnimationState();
   public AnimationState meleeTransitionAnimationState = new AnimationState();
   public AnimationState meleeEndAnimationState = new AnimationState();
   public AnimationState rangedAnimationState = new AnimationState();
   public AnimationState sneezeAnimationState = new AnimationState();
   public Vec3 smokePos = Vec3.ZERO;
   private final Vec3[] deathParticlePos = new Vec3[6];
   public final List<Pair<Vec3, ModelSnapshot>> trailSnapshots = new ArrayList<>(50);
   public float lastTrailTick = 0.0F;

   public Permafrost(EntityType<? extends Permafrost> entityType, Level level) {
      super(entityType, level);
      this.moveControl = new FlyingMoveControl(this, 5, true);
   }

   public boolean shouldAddTrailSnapshot() {
      return Mth.degreesDifferenceAbs(this.getYRot(), this.yBodyRot) < 45.0F
         && Mth.degreesDifferenceAbs(this.getYRot(), this.yBodyRotO) < 45.0F
         && Mth.degreesDifferenceAbs(this.yBodyRot, this.yBodyRotO) < 45.0F
         && (this.getBehaviorState() == 1 || this.getBehaviorState() == 2 || this.getBehaviorState() == 3 && this.getBehaviorTicks() < 3);
   }

   protected PathNavigation createNavigation(Level level) {
      FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
         public boolean isStableDestination(BlockPos blockPos) {
            return this.level.getBlockState(blockPos).isAir();
         }
      };
      navigation.setCanOpenDoors(true);
      navigation.setCanFloat(true);
      navigation.setCanPassDoors(true);
      return navigation;
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new Permafrost.PermafrostFlyToTargetGoal());
      this.goalSelector.addGoal(2, new RandomFlyGoal(this) {
         @Override
         public boolean canUse() {
            return super.canUse() && Permafrost.this.getTarget() == null && Permafrost.this.getBehaviorState() == 0;
         }

         @Override
         public boolean canContinueToUse() {
            return super.canContinueToUse() && Permafrost.this.getTarget() == null && Permafrost.this.getBehaviorState() == 0;
         }
      });
      this.goalSelector.addGoal(3, new LookAtTargetGoal(this));
      this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[]{Permafrost.class}).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, AbstractVillager.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.permafrost.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.permafrost.armor())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.permafrost.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.3)
         .add(Attributes.FLYING_SPEED, 0.8)
         .add(Attributes.ATTACK_DAMAGE, 6.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.75);
   }

   protected void tickDeath() {
      this.setDeltaMovement(Vec3.ZERO);
      if (this.deathTime == 0) {
         this.stopAllAnimStates();
         this.setBehaviorState(0);

         for (int i = 0; i < 6; i++) {
            this.deathParticlePos[i] = this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0);
         }
      }

      Optional<BlockPos> chestPos = this.getLootChestPos();
      if (chestPos.isPresent()) {
         for (int i = 0; i < 6; i++) {
            if (this.deathTime < 40) {
               this.deathParticlePos[i] = ESMathUtil.lerpVec(
                  1.0F / (40.0F - this.deathTime), this.deathParticlePos[i], ESMathUtil.rotationToPosition(chestPos.get().getCenter(), 5.0F, 0.0F, i * 60)
               );
            } else {
               float currentYaw = ESMathUtil.positionToYaw(chestPos.get().getCenter(), this.deathParticlePos[i]);
               this.deathParticlePos[i] = ESMathUtil.rotationToPosition(
                  chestPos.get().getCenter(), (70 - this.deathTime) / 30.0F * 5.0F, 0.0F, currentYaw + 3.0F
               );
            }

            if (this.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(
                  ParticleTypes.SNOWFLAKE, this.deathParticlePos[i].x(), this.deathParticlePos[i].y(), this.deathParticlePos[i].z(), 5, 0.1, 0.1, 0.1, 0.05
               );
            }
         }
      }

      this.deathTime++;
      if (this.deathTime == 70 && !this.level().isClientSide()) {
         this.level().broadcastEntityEvent(this, (byte)60);
         this.remove(RemovalReason.KILLED);
      }
   }

   public void stopAllAnimStates() {
      this.meleeAnimationState.stop();
      this.meleeTransitionAnimationState.stop();
      this.meleeEndAnimationState.stop();
      this.rangedAnimationState.stop();
      this.sneezeAnimationState.stop();
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
      if (accessor.equals(BEHAVIOR_STATE)) {
         this.stopAllAnimStates();
         switch (this.getBehaviorState()) {
            case 1:
               this.meleeAnimationState.start(this.tickCount);
               break;
            case 2:
               this.meleeTransitionAnimationState.start(this.tickCount);
               break;
            case 3:
               this.meleeEndAnimationState.start(this.tickCount);
               break;
            case 4:
               this.rangedAnimationState.start(this.tickCount);
               break;
            case 5:
               this.sneezeAnimationState.start(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(accessor);
   }

   public boolean causeFallDamage(float f, float g, DamageSource damageSource) {
      return false;
   }

   public boolean isAlliedTo(Entity entity) {
      return super.isAlliedTo(entity) || entity.getType().is(ESTags.EntityTypes.STARLIGHT_GOLEM_ALLIES);
   }

   protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
   }

   public void aiStep() {
      super.aiStep();
      if (!this.level().isClientSide) {
         if (this.getTarget() != null && !this.getTarget().isAlive()) {
            this.setTarget(null);
         }

         if (!this.isNoAi() && this.isAlive()) {
            this.behaviorManager.tick();
         }

         if (this.getBehaviorState() == 0) {
            LivingEntity target = this.getTarget();
            if (target != null && this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.0)).contains(this.getTarget())) {
               this.hurtMarked = true;
               this.addDeltaMovement(this.position().subtract(target.position()).multiply(1.0, 0.0, 1.0).normalize().scale(0.1));
            }
         }
      } else {
         if (!this.onGround() && this.smokePos.distanceTo(this.position()) < this.getBbHeight()) {
            Vec3 pos = this.smokePos.subtract(0.0, 0.8125, 0.0);

            for (int i = 0; i < 5; i++) {
               this.level()
                  .addParticle(
                     ParticleTypes.WHITE_SMOKE,
                     pos.x + this.getBbWidth() / 2.0F * (this.getRandom().nextFloat() - 0.5F),
                     pos.y,
                     pos.z + this.getBbWidth() / 2.0F * (this.getRandom().nextFloat() - 0.5F),
                     0.0,
                     -0.15,
                     0.0
                  );
            }
         }

         this.idleAnimationState.startIfStopped(this.tickCount);
      }
   }

   public boolean doHurtTarget(Entity entity) {
      boolean success = super.doHurtTarget(entity);
      if (success && entity.canFreeze()) {
         entity.setTicksFrozen(Math.min(entity.getTicksFrozen() + 150, 300));
      }

      return success;
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return ESSoundEvents.PERMAFROST_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return ESSoundEvents.PERMAFROST_DEATH.get();
   }

   @Override
   protected BlockState getBossSpawner() {
      return ((PermafrostSpawnerBlock)ESBlocks.PERMAFROST_SPAWNER.get()).defaultBlockState();
   }

   @Override
   protected void modifyBossLootChest(LootChestBlockEntity blockEntity) {
      blockEntity.setColor(8947589);
      blockEntity.setOutlineColor(10220788);
      blockEntity.setFlashColor(6868223);
      blockEntity.setRareFlashColor(10220788);
   }

   @Override
   public boolean shouldPlayBossMusic() {
      return false;
   }

   public class PermafrostFlyToTargetGoal extends Goal {
      public PermafrostFlyToTargetGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         return Permafrost.this.getBehaviorState() == 0 && Permafrost.this.getTarget() != null;
      }

      public boolean canContinueToUse() {
         return Permafrost.this.getBehaviorState() == 0 && Permafrost.this.getTarget() != null;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         LivingEntity entity = Permafrost.this.getTarget();
         if (entity != null) {
            Vec3 target = entity.position();
            BlockHitResult result = Permafrost.this.level()
               .clip(
                  new ClipContext(
                     target.add(0.0, entity.getBbHeight() / 2.0F, 0.0),
                     target.add(0.0, entity.getBbHeight() / 2.0F + Permafrost.this.getBbHeight(), 0.0),
                     Block.COLLIDER,
                     Fluid.NONE,
                     Permafrost.this
                  )
               );
            if (result.getType() != Type.MISS) {
               target = result.getLocation();
            } else {
               target = target.add(0.0, entity.getBbHeight() / 2.0F + Permafrost.this.getBbHeight(), 0.0);
            }

            Permafrost.this.getNavigation().moveTo(target.x, target.y - Permafrost.this.getBbHeight(), target.z, 1.0);
         }
      }
   }
}
