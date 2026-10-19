package com.chronodawn.entities.bosses;

import com.chronodawn.entities.projectiles.TimeBlastEntity;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TemporalPhantomEntity extends Monster implements RangedAttackMob {
   private static final EntityDataAccessor<Boolean> PHASE_SHIFT_ACTIVE = SynchedEntityData.defineId(TemporalPhantomEntity.class, EntityDataSerializers.BOOLEAN);
   private static final int PHASE_1 = 1;
   private static final int PHASE_2 = 2;
   private static final float PHASE_2_THRESHOLD = 0.5F;
   private static final float PHASE_SHIFT_CHANCE = 0.3F;
   private static final int PHASE_SHIFT_DURATION = 40;
   private static final int PHASE_SHIFT_COOLDOWN = 100;
   private final ServerBossEvent bossEvent;
   private int phaseShiftTicks = 0;
   private int phaseShiftCooldownTicks = 0;
   private int cloneSummonCooldown = 0;
   private int blinkStrikeCooldown = 0;
   private int rangedAttackCooldown = 0;

   public TemporalPhantomEntity(EntityType<? extends TemporalPhantomEntity> entityType, Level level) {
      super(entityType, level);
      this.bossEvent = new ServerBossEvent(this.getDisplayName(), BossBarColor.PURPLE, BossBarOverlay.PROGRESS);
      this.xpReward = 80;
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 150.0)
         .add(Attributes.ATTACK_DAMAGE, 8.0)
         .add(Attributes.ARMOR, 5.0)
         .add(Attributes.MOVEMENT_SPEED, 0.3)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.3)
         .add(Attributes.FOLLOW_RANGE, 32.0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0, 40, 20.0F));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(PHASE_SHIFT_ACTIVE, false);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
         if (this.phaseShiftTicks > 0) {
            this.phaseShiftTicks--;
            if (this.phaseShiftTicks == 0) {
               this.entityData.set(PHASE_SHIFT_ACTIVE, false);
            }
         }

         if (this.phaseShiftCooldownTicks > 0) {
            this.phaseShiftCooldownTicks--;
         }

         if (this.getCurrentPhase() == 2) {
            if (this.cloneSummonCooldown > 0) {
               this.cloneSummonCooldown--;
            } else {
               this.summonPhantomClones();
               this.cloneSummonCooldown = 400;
            }

            if (this.blinkStrikeCooldown > 0) {
               this.blinkStrikeCooldown--;
            } else if (this.getTarget() instanceof Player player) {
               this.performBlinkStrike(player);
               this.blinkStrikeCooldown = 240;
            }
         }

         if (this.tickCount % 10 == 0 && this.level() instanceof ServerLevel serverLevel) {
            double offsetX = (this.random.nextDouble() - 0.5) * 2.0;
            double offsetY = this.random.nextDouble() * 2.0;
            double offsetZ = (this.random.nextDouble() - 0.5) * 2.0;
            serverLevel.sendParticles(ParticleTypes.PORTAL, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 1, 0.0, 0.0, 0.0, 0.0);
         }
      }
   }

   private int getCurrentPhase() {
      float healthRatio = this.getHealth() / this.getMaxHealth();
      return healthRatio <= 0.5F ? 2 : 1;
   }

   public boolean hurt(DamageSource source, float amount) {
      if (!this.level().isClientSide && source.getDirectEntity() instanceof LivingEntity && this.phaseShiftCooldownTicks == 0 && this.random.nextFloat() < 0.3F
         )
       {
         this.entityData.set(PHASE_SHIFT_ACTIVE, true);
         this.phaseShiftTicks = 40;
         this.phaseShiftCooldownTicks = 100;
         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)ModSounds.BOSS_TELEPORT.get(), SoundSource.HOSTILE, 1.0F, 1.5F);
         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.PORTAL, this.getX(), this.getY() + 1.0, this.getZ(), 30, 0.5, 0.5, 0.5, 0.1);
         }

         return false;
      } else {
         return super.hurt(source, amount);
      }
   }

   public void performRangedAttack(LivingEntity target, float velocity) {
      if (this.level() instanceof ServerLevel serverLevel) {
         TimeBlastEntity blast = new TimeBlastEntity(this.level(), this);
         double targetX = target.getX();
         double targetY = target.getY() + target.getEyeHeight() * 0.5;
         double targetZ = target.getZ();
         double shootX = this.getX();
         double shootY = this.getY() + this.getEyeHeight();
         double shootZ = this.getZ();
         double deltaX = targetX - shootX;
         double deltaY = targetY - shootY;
         double deltaZ = targetZ - shootZ;
         double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
         deltaX /= distance;
         deltaY /= distance;
         deltaZ /= distance;
         blast.setDeltaMovement(deltaX * 1.5, deltaY * 1.5, deltaZ * 1.5);
         blast.setPos(shootX, shootY, shootZ);
         this.level().addFreshEntity(blast);
         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)ModSounds.BOSS_CAST_SPELL.get(), SoundSource.HOSTILE, 1.0F, 1.2F);
      }
   }

   private void summonPhantomClones() {
      if (this.level() instanceof ServerLevel serverLevel) {
         for (int var9 = 0; var9 < 2; var9++) {
            double angle = (Math.PI * 2) * var9 / 2.0 + this.random.nextDouble() * Math.PI;
            double offsetX = Math.cos(angle) * 3.0;
            double offsetZ = Math.sin(angle) * 3.0;
            serverLevel.sendParticles(ParticleTypes.PORTAL, this.getX() + offsetX, this.getY(), this.getZ() + offsetZ, 20, 0.5, 0.5, 0.5, 0.1);
         }

         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)ModSounds.BOSS_PREPARE_SUMMON.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
      }
   }

   private void performBlinkStrike(Player target) {
      Vec3 lookDir = target.getLookAngle();
      double distance = 5.0 + this.random.nextDouble() * 3.0;
      Vec3 targetPos = target.position().subtract(lookDir.scale(distance));
      BlockPos targetBlockPos = BlockPos.containing(targetPos);
      BlockPos safePos = this.findSafeGroundPosition(targetBlockPos);
      if (safePos != null) {
         if (this.teleportToPosition(Vec3.atBottomCenterOf(safePos))) {
            this.doHurtTarget(target);
         }
      }
   }

   private BlockPos findSafeGroundPosition(BlockPos startPos) {
      if (this.isSafeTeleportPosition(startPos)) {
         return startPos;
      }

      for (int offset = 1; offset <= 3; offset++) {
         BlockPos testPos = startPos.above(offset);
         if (this.isSafeTeleportPosition(testPos)) {
            return testPos;
         }
      }

      for (int offset = 1; offset <= 3; offset++) {
         BlockPos testPos = startPos.below(offset);
         if (this.isSafeTeleportPosition(testPos)) {
            return testPos;
         }
      }

      return null;
   }

   private boolean isSafeTeleportPosition(BlockPos pos) {
      if (!this.level().getBlockState(pos.below()).isSolid()) {
         return false;
      }

      for (int i = 0; i < 3; i++) {
         BlockState state = this.level().getBlockState(pos.above(i));
         if (state.isSolid()) {
            return false;
         }
      }

      return true;
   }

   private boolean teleportToPosition(Vec3 pos) {
      Vec3 originalPos = this.position();
      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(ParticleTypes.PORTAL, this.getX(), this.getY() + 1.0, this.getZ(), 30, 0.5, 0.5, 0.5, 0.1);
      }

      this.teleportTo(pos.x, pos.y, pos.z);
      if (this.isStuckInBlocks()) {
         this.teleportTo(originalPos.x, originalPos.y, originalPos.z);
         return false;
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(ParticleTypes.PORTAL, this.getX(), this.getY() + 1.0, this.getZ(), 30, 0.5, 0.5, 0.5, 0.1);
      }

      this.level().playSound(null, this.blockPosition(), (SoundEvent)ModSounds.PHANTOM_TELEPORT.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
      return true;
   }

   private boolean isStuckInBlocks() {
      BlockPos feetPos = this.blockPosition();

      for (int dx = -1; dx <= 1; dx++) {
         for (int dz = -1; dz <= 1; dz++) {
            for (int dy = 0; dy < 3; dy++) {
               BlockPos checkPos = feetPos.offset(dx, dy, dz);
               BlockState state = this.level().getBlockState(checkPos);
               if (state.isSolid() || state.isSuffocating(this.level(), checkPos)) {
                  return true;
               }
            }
         }
      }

      return false;
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
   }

   public void setCustomName(Component name) {
      super.setCustomName(name);
      this.bossEvent.setName(this.getDisplayName());
   }

   public boolean isPhaseShiftActive() {
      return (Boolean)this.entityData.get(PHASE_SHIFT_ACTIVE);
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
