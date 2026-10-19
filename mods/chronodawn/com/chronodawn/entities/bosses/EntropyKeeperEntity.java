package com.chronodawn.entities.bosses;

import com.chronodawn.registry.ModSounds;
import com.chronodawn.worldgen.protection.BlockProtectionHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntropyKeeperEntity extends Monster {
   private static final EntityDataAccessor<Integer> DEGRADATION_STACKS = SynchedEntityData.defineId(EntropyKeeperEntity.class, EntityDataSerializers.INT);
   private static final int PHASE_1 = 1;
   private static final int PHASE_2 = 2;
   private static final float PHASE_2_THRESHOLD = 0.5F;
   private static final float ENTROPY_BURST_THRESHOLD = 0.3F;
   private static final float DECAY_AURA_RADIUS = 4.0F;
   private static final int DECAY_AURA_INTERVAL = 40;
   private static final int TEMPORAL_ROT_COOLDOWN = 160;
   private static final int DEGRADATION_INTERVAL = 1200;
   private static final int MAX_DEGRADATION_STACKS = 3;
   private final ServerBossEvent bossEvent;
   private int decayAuraTicks = 0;
   private int temporalRotCooldown = 0;
   private int degradationTimer = 0;
   private boolean entropyBurstTriggered = false;

   public EntropyKeeperEntity(EntityType<? extends EntropyKeeperEntity> entityType, Level level) {
      super(entityType, level);
      this.bossEvent = new ServerBossEvent(this.getDisplayName(), BossBarColor.GREEN, BossBarOverlay.PROGRESS);
      this.xpReward = 90;
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 160.0)
         .add(Attributes.ATTACK_DAMAGE, 10.0)
         .add(Attributes.ARMOR, 6.0)
         .add(Attributes.MOVEMENT_SPEED, 0.2)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
         .add(Attributes.FOLLOW_RANGE, 32.0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(3, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DEGRADATION_STACKS, 0);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
         this.decayAuraTicks++;
         if (this.decayAuraTicks >= 40) {
            this.applyDecayAura();
            this.decayAuraTicks = 0;
         }

         if (this.temporalRotCooldown > 0) {
            this.temporalRotCooldown--;
         } else if (this.getTarget() != null) {
            this.placeTemporalRot();
            this.temporalRotCooldown = 160;
         }

         if (this.getCurrentPhase() == 2) {
            this.degradationTimer++;
            if (this.degradationTimer >= 1200) {
               this.applyDegradation();
               this.degradationTimer = 0;
            }

            float healthRatio = this.getHealth() / this.getMaxHealth();
            if (!this.entropyBurstTriggered && healthRatio <= 0.3F) {
               this.performEntropyBurst();
               this.entropyBurstTriggered = true;
            }
         }

         if (this.tickCount % 5 == 0 && this.level() instanceof ServerLevel serverLevel) {
            double offsetX = (this.random.nextDouble() - 0.5) * 1.5;
            double offsetY = this.random.nextDouble() * 2.0;
            double offsetZ = (this.random.nextDouble() - 0.5) * 1.5;
            serverLevel.sendParticles(ParticleTypes.SCULK_SOUL, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 1, 0.0, 0.0, 0.0, 0.0);
         }
      }
   }

   private int getCurrentPhase() {
      float healthRatio = this.getHealth() / this.getMaxHealth();
      return healthRatio <= 0.5F ? 2 : 1;
   }

   private void applyDecayAura() {
      AABB aabb = this.getBoundingBox().inflate(4.0);

      for (Player player : this.level().getEntitiesOfClass(Player.class, aabb)) {
         player.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 0));
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         for (int i = 0; i < 10; i++) {
            double angle = (Math.PI * 2) * i / 10.0;
            double x = this.getX() + Math.cos(angle) * 4.0;
            double z = this.getZ() + Math.sin(angle) * 4.0;
            serverLevel.sendParticles(ParticleTypes.SMOKE, x, this.getY() + 0.5, z, 1, 0.0, 0.0, 0.0, 0.0);
         }
      }
   }

   public boolean doHurtTarget(Entity target) {
      boolean hit = super.doHurtTarget(target);
      if (hit && target instanceof Player player) {
         player.getInventory().armor.forEach(stack -> {
            if (!stack.isEmpty() && stack.isDamageableItem()) {
               stack.hurtAndBreak(5, player, player.getEquipmentSlotForItem(stack));
            }
         });
         if (!player.getMainHandItem().isEmpty() && player.getMainHandItem().isDamageableItem()) {
            player.getMainHandItem().hurtAndBreak(5, player, EquipmentSlot.MAINHAND);
         }
      }

      return hit;
   }

   private void placeTemporalRot() {
      BlockPos targetPos = this.blockPosition();
      if (this.level() instanceof ServerLevel serverLevel) {
         for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
               BlockPos pos = targetPos.offset(x, 0, z);
               serverLevel.sendParticles(ParticleTypes.SCULK_SOUL, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, 5, 0.3, 0.0, 0.3, 0.0);
            }
         }
      }

      AABB aabb = new AABB(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ() - 1, targetPos.getX() + 2, targetPos.getY() + 1, targetPos.getZ() + 2);

      for (Player player : this.level().getEntitiesOfClass(Player.class, aabb)) {
         player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
         player.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0));
      }

      this.level().playSound(null, targetPos, (SoundEvent)ModSounds.ENTROPY_ATTACK.get(), SoundSource.HOSTILE, 1.0F, 0.8F);
   }

   private void applyDegradation() {
      int currentStacks = (Integer)this.entityData.get(DEGRADATION_STACKS);
      if (currentStacks < 3) {
         this.entityData.set(DEGRADATION_STACKS, currentStacks + 1);
         this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10.0 + (currentStacks + 1) * 2.0);
         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)ModSounds.ENTROPY_WITHER_ANGER.get(), SoundSource.HOSTILE, 1.0F, 1.2F);
      }
   }

   private void performEntropyBurst() {
      AABB aabb = this.getBoundingBox().inflate(6.0);

      for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, aabb)) {
         if (entity != this) {
            entity.hurt(this.damageSources().mobAttack(this), 10.0F);
            entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 1));
            Vec3 direction = entity.position().subtract(this.position()).normalize();
            entity.setDeltaMovement(entity.getDeltaMovement().add(direction.scale(1.5)));
         }
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY() + 1.0, this.getZ(), 20, 3.0, 1.0, 3.0, 0.1);
         serverLevel.sendParticles(ParticleTypes.SCULK_SOUL, this.getX(), this.getY() + 1.0, this.getZ(), 50, 3.0, 1.0, 3.0, 0.2);
      }

      this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)ModSounds.ENTROPY_BURST.get(), SoundSource.HOSTILE, 2.0F, 0.8F);
   }

   public int getDegradationStacks() {
      return (Integer)this.entityData.get(DEGRADATION_STACKS);
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossEvent.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (this.hasCustomName()) {
         this.bossEvent.setName(this.getDisplayName());
      }

      this.entropyBurstTriggered = compound.getBoolean("EntropyBurstTriggered");
      this.entityData.set(DEGRADATION_STACKS, compound.getInt("DegradationStacks"));
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("EntropyBurstTriggered", this.entropyBurstTriggered);
      compound.putInt("DegradationStacks", (Integer)this.entityData.get(DEGRADATION_STACKS));
   }

   public void setCustomName(Component name) {
      super.setCustomName(name);
      this.bossEvent.setName(this.getDisplayName());
   }

   public void die(DamageSource cause) {
      super.die(cause);
      if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
         BlockProtectionHandler.onBossDefeatedAt(serverLevel, this.blockPosition());
      }
   }

   public boolean isPersistenceRequired() {
      return true;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }
}
