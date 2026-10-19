package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.block.entity.LootChestBlockEntity;
import cn.leolezury.eternalstarlight.common.block.spawner.LunarMonstrositySpawnerBlock;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.RayAttackUser;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESServerBossEvent;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class LunarMonstrosity extends ESBoss implements RayAttackUser {
   private final ESServerBossEvent bossEvent = new ESServerBossEvent(this, this.getUUID(), BossBarColor.PURPLE, false);
   private final BehaviorManager<LunarMonstrosity> behaviorManager = new BehaviorManager(
      this,
      List.of(
         new LunarMonstrosityToxicBreathPhase(),
         new LunarMonstrositySporePhase(),
         new LunarMonstrosityThornPhase(),
         new LunarMonstrosityBitePhase(),
         new LunarMonstrosityDigPhase(),
         new LunarMonstrositySneakPhase(),
         new LunarMonstrosityEmergePhase(),
         new LunarMonstrositySoulPhase(),
         new LunarMonstrosityStunPhase()
      )
   );
   public AnimationState toxicBreathAnimationState = new AnimationState();
   public AnimationState sporeAnimationState = new AnimationState();
   public AnimationState thornAnimationState = new AnimationState();
   public AnimationState biteAnimationState = new AnimationState();
   public AnimationState digAnimationState = new AnimationState();
   public AnimationState sneakAnimationState = new AnimationState();
   public AnimationState emergeAnimationState = new AnimationState();
   public AnimationState switchPhaseAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   public Vec3 headPos = Vec3.ZERO;
   private final Vec3[] deathParticlePos = new Vec3[5];
   public int fleeFromLavaCooldown = 0;

   public LunarMonstrosity(EntityType<? extends LunarMonstrosity> entityType, Level level) {
      super(entityType, level);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.bossEvent.setId(this.getUUID());
   }

   public void startSeenByPlayer(ServerPlayer serverPlayer) {
      super.startSeenByPlayer(serverPlayer);
      this.bossEvent.addPlayer(serverPlayer);
   }

   public void stopSeenByPlayer(ServerPlayer serverPlayer) {
      super.stopSeenByPlayer(serverPlayer);
      this.bossEvent.removePlayer(serverPlayer);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new LunarMonstrosity.LunarMonstrosityMeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(2, new LunarMonstrosity.MonstrosityLookAtTargetGoal());
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[]{LunarMonstrosity.class}).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, AbstractVillager.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
   }

   @Override
   public boolean isRayFollowingHeadRotation() {
      return false;
   }

   @Override
   public Vec3 getRayRotationTarget() {
      return this.getTarget() == null
         ? this.position()
            .add(
               this.getBbWidth() * (this.getRandom().nextFloat() - 0.5F),
               this.getBbHeight() * this.getRandom().nextFloat(),
               this.getBbWidth() * (this.getRandom().nextFloat() - 0.5F)
            )
         : this.getTarget()
            .position()
            .add(
               this.getTarget().getBbWidth() * (this.getRandom().nextFloat() - 0.5F),
               this.getTarget().getBbHeight() * this.getRandom().nextFloat(),
               this.getTarget().getBbWidth() * (this.getRandom().nextFloat() - 0.5F)
            );
   }

   @Override
   public void updateRayEnd(Vec3 endPos) {
      ESEntityUtil.instantLook(this, endPos);
   }

   public static Builder createAttributes() {
      return createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.lunarMonstrosity.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.lunarMonstrosity.armor())
         .add(Attributes.ATTACK_DAMAGE, 5.0)
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.lunarMonstrosity.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.35F)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected void tickDeath() {
      if (this.deathTime == 0) {
         this.stopAllAnimStates();
         this.deathAnimationState.start(this.tickCount);
         this.setBehaviorState(0);

         for (int i = 0; i < 5; i++) {
            this.deathParticlePos[i] = this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0);
         }
      }

      Optional<BlockPos> chestPos = this.getLootChestPos();
      if (chestPos.isPresent()) {
         for (int i = 0; i < 5; i++) {
            if (this.deathTime < 40) {
               this.deathParticlePos[i] = ESMathUtil.lerpVec(
                  1.0F / (40.0F - this.deathTime), this.deathParticlePos[i], ESMathUtil.rotationToPosition(chestPos.get().getCenter(), 5.0F, 0.0F, 30 + i * 72)
               );
            } else {
               float currentYaw = ESMathUtil.positionToYaw(chestPos.get().getCenter(), this.deathParticlePos[i]);
               this.deathParticlePos[i] = ESMathUtil.rotationToPosition(
                  chestPos.get().getCenter(), (75 - this.deathTime) / 35.0F * 5.0F, 0.0F, currentYaw + 3.0F
               );
            }

            if (this.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(
                  (SimpleParticleType)ESParticles.SOUL_TRAIL.get(),
                  this.deathParticlePos[i].x(),
                  this.deathParticlePos[i].y(),
                  this.deathParticlePos[i].z(),
                  3,
                  0.1,
                  0.1,
                  0.1,
                  0.01
               );
               serverLevel.sendParticles(
                  (SimpleParticleType)ESParticles.SHADEGRIEVE_LEAVES.get(),
                  this.deathParticlePos[i].x(),
                  this.deathParticlePos[i].y(),
                  this.deathParticlePos[i].z(),
                  3,
                  0.1,
                  0.1,
                  0.1,
                  0.1
               );
            }
         }
      }

      this.deathTime++;
      if (this.deathTime == 75 && !this.level().isClientSide()) {
         this.level().broadcastEntityEvent(this, (byte)60);
         this.remove(RemovalReason.KILLED);
      }
   }

   public void stopAllAnimStates() {
      this.toxicBreathAnimationState.stop();
      this.sporeAnimationState.stop();
      this.thornAnimationState.stop();
      this.biteAnimationState.stop();
      this.digAnimationState.stop();
      this.sneakAnimationState.stop();
      this.emergeAnimationState.stop();
      this.switchPhaseAnimationState.stop();
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
      if (accessor.equals(BEHAVIOR_STATE)) {
         this.stopAllAnimStates();
         switch (this.getBehaviorState()) {
            case 1:
               this.toxicBreathAnimationState.start(this.tickCount);
               break;
            case 2:
               this.sporeAnimationState.start(this.tickCount);
               break;
            case 3:
               this.thornAnimationState.start(this.tickCount);
               break;
            case 4:
               this.biteAnimationState.start(this.tickCount);
               break;
            case 5:
               this.digAnimationState.start(this.tickCount);
               break;
            case 6:
               this.sneakAnimationState.start(this.tickCount);
               break;
            case 7:
               this.emergeAnimationState.start(this.tickCount);
               break;
            case 8:
               this.switchPhaseAnimationState.start(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(accessor);
   }

   protected void blockedByShield(LivingEntity blocker) {
      if (this.getBehaviorState() == 4 && blocker == this.getTarget()) {
         this.setBehaviorState(-1);
      }

      super.blockedByShield(blocker);
   }

   public boolean doHurtTarget(Entity target) {
      if (this.getBehaviorState() == 6) {
         this.setBehaviorState(7);
         this.setBehaviorTicks(0);
      }

      return super.doHurtTarget(target);
   }

   @Override
   public boolean hurt(DamageSource source, float amount) {
      if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
         || this.getBehaviorState() != 6
            && this.getBehaviorState() != 8
            && (source.getEntity() == null || !source.getEntity().getType().is(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLIES))) {
         if (this.getPhase() == 0 && this.getHealth() / this.getMaxHealth() < 0.5) {
            this.setPhase(1);
            this.setBehaviorState(8);
            this.setBehaviorTicks(0);
         }

         if (source.getEntity() != null && this.getTarget() != null && this.getBehaviorState() == 4 && source.getEntity() == this.getTarget() && amount >= 6.0F
            )
          {
            this.setBehaviorState(-1);
            this.setBehaviorTicks(0);
         }

         float actualAmount = !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
               && !this.isOnFire()
               && !this.hasEffect(ESMobEffects.STARFIRE.asHolder())
               && this.getBehaviorState() != -1
            ? Math.min(3.0F, amount)
            : amount;
         boolean success = super.hurt(source, actualAmount);
         if (this.getPhase() == 0 && this.getHealth() / this.getMaxHealth() < 0.5) {
            this.setPhase(1);
            this.setBehaviorState(8);
            this.setBehaviorTicks(0);
         }

         return success;
      } else {
         return false;
      }
   }

   public boolean addEffect(MobEffectInstance instance, @Nullable Entity entity) {
      return instance.getEffect().is(MobEffects.POISON) ? false : super.addEffect(instance, entity);
   }

   public boolean isAlliedTo(Entity entity) {
      return super.isAlliedTo(entity) || entity.getType().is(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLIES);
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      if (itemStack.is(ESTags.Items.LUNAR_MONSTROSITY_IGNITERS)) {
         SoundEvent soundEvent = itemStack.isDamageableItem() ? SoundEvents.FLINTANDSTEEL_USE : SoundEvents.FIRECHARGE_USE;
         this.level().playSound(player, this.getX(), this.getY(), this.getZ(), soundEvent, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
         if (!this.level().isClientSide) {
            this.setRemainingFireTicks(Math.max(this.getRemainingFireTicks(), this.getPhase() == 0 ? 100 : 20));
            if (!itemStack.isDamageableItem()) {
               itemStack.consume(1, player);
            } else {
               itemStack.hurtAndBreak(1, player, getSlotForHand(hand));
            }
         }

         return InteractionResult.sidedSuccess(this.level().isClientSide);
      } else {
         return super.mobInteract(player, hand);
      }
   }

   public EntityDimensions getDefaultDimensions(Pose pose) {
      return this.getBehaviorState() == 6 ? super.getDefaultDimensions(pose).scale(0.1F) : super.getDefaultDimensions(pose);
   }

   public AABB getNormalStateBoundingBox() {
      return super.getDefaultDimensions(this.getPose()).makeBoundingBox(this.position());
   }

   public boolean displayFireAnimation() {
      return this.getBehaviorState() != 6 && super.displayFireAnimation();
   }

   @Override
   protected BlockState getBossSpawner() {
      return ((LunarMonstrositySpawnerBlock)ESBlocks.LUNAR_MONSTROSITY_SPAWNER.get()).defaultBlockState();
   }

   @Override
   protected void modifyBossLootChest(LootChestBlockEntity blockEntity) {
      blockEntity.setColor(8939149);
      blockEntity.setOutlineColor(5917868);
      blockEntity.setFlashColor(12772860);
      blockEntity.setRareFlashColor(5917868);
   }

   @Override
   public SoundEvent getBossMusic() {
      return ESSoundEvents.MUSIC_BOSS_LUNAR_MONSTROSITY.get();
   }

   public boolean canBite() {
      LivingEntity target = this.getTarget();
      if (target == null) {
         return false;
      }

      for (LivingEntity livingEntity : this.level()
         .getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, this.getBoundingBox().inflate(3.0))) {
         Vec3 vec3 = livingEntity.position().vectorTo(this.position()).normalize();
         vec3 = new Vec3(vec3.x, 0.0, vec3.z);
         if (vec3.dot(this.getViewVector(1.0F)) < 0.0 && target == livingEntity) {
            return true;
         }
      }

      return false;
   }

   public void doBiteDamage(float damage) {
      for (LivingEntity livingEntity : this.level()
         .getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, this.getBoundingBox().inflate(3.0))) {
         Vec3 vec3 = livingEntity.position().vectorTo(this.position()).normalize();
         vec3 = new Vec3(vec3.x, 0.0, vec3.z);
         if (vec3.dot(this.getViewVector(1.0F)) < 0.0) {
            livingEntity.hurt(ESDamageTypes.getEntityDamageSource(this.level(), ESDamageTypes.BITE, this), damage);
         }
      }
   }

   public void knockbackNearbyEntities(float radius, float strength, boolean damage) {
      for (LivingEntity living : this.level()
         .getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, this.getBoundingBox().inflate(radius, 0.0, radius))) {
         if (!living.getType().is(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLIES)) {
            Vec3 motion = living.position().subtract(this.position()).normalize().scale(strength);
            living.hurtMarked = true;
            living.setDeltaMovement(living.getDeltaMovement().add(motion));
            if (damage) {
               living.hurt(this.damageSources().mobAttack(this), strength * 3.0F);
            }
         }
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         ESPlatform.INSTANCE
            .sendToAllClients(serverLevel, new ParticlePacket(RingExplosionParticleOptions.LUNAR, this.getX(), this.getY(), this.getZ(), 0.0, 0.2, 0.0));
      }
   }

   public boolean ignoreExplosion(Explosion explosion) {
      LivingEntity cause = explosion.getIndirectSourceEntity();
      return cause != null && cause.getType().is(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLIES);
   }

   public void aiStep() {
      super.aiStep();
      this.bossEvent.update();
      this.refreshDimensions();
      if (!this.level().isClientSide) {
         if (this.getTarget() != null && !this.getTarget().isAlive()) {
            this.setTarget(null);
         }

         if (!this.isNoAi() && this.isAlive()) {
            this.behaviorManager.tick();
         }

         if (this.getBehaviorState() != 6) {
            this.getNavigation().stop();
         }

         if (this.fleeFromLavaCooldown > 0) {
            this.fleeFromLavaCooldown--;
         }
      } else {
         this.level()
            .addParticle(
               ESSmokeParticleOptions.LUNAR_SHORT,
               this.getX() + (this.getRandom().nextDouble() - 0.5) * 3.0,
               this.getY() + 1.0 + (this.getRandom().nextDouble() - 0.5) * 3.0,
               this.getZ() + (this.getRandom().nextDouble() - 0.5) * 3.0,
               0.0,
               0.0,
               0.0
            );
         if (this.deathTime <= 0) {
            if (this.getBehaviorState() == 6) {
               RandomSource randomsource = this.getRandom();
               BlockState blockstate = this.getBlockStateOn();
               if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                  for (int i = 0; i < 30; i++) {
                     double d0 = this.getX() + Mth.randomBetween(randomsource, -0.7F, 0.7F);
                     double d1 = this.getY();
                     double d2 = this.getZ() + Mth.randomBetween(randomsource, -0.7F, 0.7F);
                     this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0, 0.0, 0.0);
                  }
               }
            } else if (this.getBehaviorState() == -1 && this.headPos.distanceTo(this.position()) < this.getBbHeight() * 2.0F) {
               this.level()
                  .addParticle(
                     ParticleTypes.CRIT,
                     this.headPos.x + this.getRandom().nextDouble() - 0.5,
                     this.headPos.y,
                     this.headPos.z + this.getRandom().nextDouble() - 0.5,
                     this.getRandom().nextDouble() / 10.0,
                     0.8,
                     this.getRandom().nextDouble() / 10.0
                  );
            }
         }
      }
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return ESSoundEvents.LUNAR_MONSTROSITY_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return ESSoundEvents.LUNAR_MONSTROSITY_DEATH.get();
   }

   private static class LunarMonstrosityMeleeAttackGoal extends MeleeAttackGoal {
      private LunarMonstrosityMeleeAttackGoal(PathfinderMob mob, double speed, boolean followingTargetEvenIfNotSeen) {
         super(mob, speed, followingTargetEvenIfNotSeen);
      }

      public boolean canUse() {
         boolean canUse = true;
         if (this.mob instanceof LunarMonstrosity boss) {
            canUse = boss.getBehaviorState() == 6;
         }

         return super.canUse() && canUse;
      }

      public boolean canContinueToUse() {
         boolean canUse = true;
         if (this.mob instanceof LunarMonstrosity boss) {
            canUse = boss.getBehaviorState() == 6;
         }

         return super.canContinueToUse() && canUse;
      }
   }

   private class MonstrosityLookAtTargetGoal extends LookAtTargetGoal {
      public MonstrosityLookAtTargetGoal() {
         super(LunarMonstrosity.this);
      }

      @Override
      public void tick() {
         if (LunarMonstrosity.this.getBehaviorState() != 1) {
            super.tick();
         }
      }
   }
}
