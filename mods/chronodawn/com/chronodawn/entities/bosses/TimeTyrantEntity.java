package com.chronodawn.entities.bosses;

import com.chronodawn.core.dimension.DimensionStabilizer;
import com.chronodawn.data.DimensionStateData;
import com.chronodawn.registry.ModEffects;
import com.chronodawn.registry.ModSounds;
import com.chronodawn.worldgen.protection.BlockProtectionHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TimeTyrantEntity extends Monster {
   private final ServerBossEvent bossEvent = new ServerBossEvent(this.getDisplayName(), BossBarColor.PURPLE, BossBarOverlay.PROGRESS);
   private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(TimeTyrantEntity.class, EntityDataSerializers.INT);
   public static final int PHASE_1 = 1;
   public static final int PHASE_2 = 2;
   public static final int PHASE_3 = 3;
   private int timeStopCooldown = 0;
   private int teleportCooldown = 0;
   private int timeAccelerationCooldown = 0;
   private int aoeCooldown = 0;
   private int postTeleportDelay = 0;
   private boolean hasUsedTimeReversal = false;
   private boolean chronoAegisAnchorActive = false;
   private boolean chronoAegisDisruptionActive = false;
   private int chronoAegisTeleportBlockTicks = 0;
   private int timeClockWeakeningTicks = 0;
   private int timeClockUsesInPhase1 = 0;
   private int timeClockUsesInPhase2 = 0;
   private int timeClockUsesInPhase3 = 0;
   private int previousPhase = 1;
   public static final int TIME_CLOCK_WEAKENING_DURATION = 200;
   public static final double WEAKENING_ARMOR_REDUCTION = 10.0;
   public static final double WEAKENING_SPEED_MULTIPLIER = 0.5;
   public static final int TIME_STOP_COOLDOWN_TICKS = 100;
   public static final int TELEPORT_COOLDOWN_TICKS = 100;
   public static final int TIME_ACCELERATION_COOLDOWN_TICKS = 160;
   public static final int AOE_COOLDOWN_TICKS = 120;
   public static final int POST_TELEPORT_DELAY_TICKS = 15;
   public static final double AOE_RANGE = 5.0;
   public static final float AOE_DAMAGE = 12.0F;
   public static final float TIME_REVERSAL_HP_PERCENT = 0.1F;
   public static final float TIME_REVERSAL_TRIGGER_THRESHOLD = 0.2F;

   public TimeTyrantEntity(EntityType<? extends TimeTyrantEntity> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 200;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(PHASE, 1);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 500.0)
         .add(Attributes.ARMOR, 15.0)
         .add(Attributes.ATTACK_DAMAGE, 18.0)
         .add(Attributes.ATTACK_KNOCKBACK, 1.5)
         .add(Attributes.MOVEMENT_SPEED, 0.25)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   public int getPhase() {
      return (Integer)this.entityData.get(PHASE);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new ExtendedMeleeAttackGoal(this, 1.0, false, 3.5));
      this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
      this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         int currentPhase = (Integer)this.entityData.get(PHASE);
         if (currentPhase != this.previousPhase) {
            this.previousPhase = currentPhase;
         }

         this.updatePhase();
         this.handleTimeClockWeakening();
         this.handlePhaseAbilities();
         this.decrementCooldowns();
      }
   }

   private void updatePhase() {
      float healthPercent = this.getHealth() / this.getMaxHealth();
      int currentPhase = (Integer)this.entityData.get(PHASE);
      int newPhase;
      if (healthPercent > 0.66F) {
         newPhase = 1;
      } else if (healthPercent > 0.33F) {
         newPhase = 2;
      } else {
         newPhase = 3;
      }

      if (newPhase != currentPhase) {
         this.entityData.set(PHASE, newPhase);
         this.onPhaseTransition(newPhase);
      }
   }

   private void onPhaseTransition(int newPhase) {
      this.level().playSound(null, this.blockPosition(), (SoundEvent)ModSounds.BOSS_POWER_UP.get(), SoundSource.HOSTILE, 2.0F, 1.0F);
      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(ParticleTypes.PORTAL, this.getX(), this.getY() + 1.0, this.getZ(), 100, 0.5, 1.0, 0.5, 0.1);
      }
   }

   private void handlePhaseAbilities() {
      int currentPhase = (Integer)this.entityData.get(PHASE);
      LivingEntity target = this.getTarget();
      if (target != null) {
         if (currentPhase >= 1) {
            this.handleTimeStopAbility(target);
         }

         if (currentPhase >= 2) {
            this.handleTeleportAbility(target);
            this.handleTimeAccelerationAbility();
         }

         if (currentPhase >= 3) {
            this.handleAoEAbility();
            this.handleTimeReversalAbility();
         }
      }
   }

   private void handleTimeStopAbility(LivingEntity target) {
      if (this.timeStopCooldown <= 0 && this.postTeleportDelay <= 0) {
         double distance = this.distanceToSqr(target);
         if (!(distance > 16.0)) {
            boolean hasChronoAegis = false;
            if (target instanceof Player player) {
               Holder<MobEffect> aegisEffect = BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)ModEffects.CHRONO_AEGIS_BUFF.get());
               hasChronoAegis = player.hasEffect(aegisEffect);
            }

            int slownessLevel = hasChronoAegis ? 1 : 4;
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, slownessLevel));
            if (this.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(ParticleTypes.WITCH, target.getX(), target.getY() + 1.0, target.getZ(), 30, 0.5, 0.5, 0.5, 0.05);
            }

            this.level().playSound(null, target.blockPosition(), (SoundEvent)ModSounds.BOSS_POWER_UP.get(), SoundSource.HOSTILE, 1.0F, 1.5F);
            this.timeStopCooldown = 100;
         }
      }
   }

   private void handleTeleportAbility(LivingEntity target) {
      if (this.teleportCooldown <= 0 && this.postTeleportDelay <= 0) {
         if (this.chronoAegisTeleportBlockTicks <= 0) {
            Vec3 targetPos = target.position();
            Vec3 teleportPos = this.findTeleportPosition(targetPos);
            if (teleportPos != null && this.teleportToPosition(teleportPos)) {
               this.teleportCooldown = 100;
               this.postTeleportDelay = 15;
               if (!this.chronoAegisAnchorActive && this.hasNearbyChronoAegisPlayer()) {
                  this.chronoAegisAnchorActive = true;
                  this.chronoAegisTeleportBlockTicks = 60;
               }
            }
         }
      }
   }

   private Vec3 findTeleportPosition(Vec3 targetPos) {
      for (int attempt = 0; attempt < 16; attempt++) {
         double angle = this.random.nextDouble() * Math.PI * 2.0;
         double distance = 2.0 + this.random.nextDouble() * 2.0;
         double x = targetPos.x + Math.cos(angle) * distance;
         double z = targetPos.z + Math.sin(angle) * distance;
         BlockPos candidatePos = BlockPos.containing(x, targetPos.y, z);
         if (!this.hasWallBetween(this.blockPosition(), candidatePos)) {
            BlockPos safePos = this.findSafeGroundPosition(candidatePos);
            if (safePos != null && this.isAreaSafe(safePos)) {
               return new Vec3(safePos.getX() + 0.5, safePos.getY(), safePos.getZ() + 0.5);
            }
         }
      }

      return null;
   }

   private boolean hasWallBetween(BlockPos from, BlockPos to) {
      double dx = to.getX() - from.getX();
      double dz = to.getZ() - from.getZ();
      double distance = Math.sqrt(dx * dx + dz * dz);
      if (distance < 1.0) {
         return false;
      }

      int steps = (int)Math.ceil(distance);

      for (int i = 1; i < steps; i++) {
         double t = (double)i / steps;
         int x = (int)(from.getX() + dx * t);
         int z = (int)(from.getZ() + dz * t);

         for (int y = 0; y < 5; y++) {
            BlockPos checkPos = new BlockPos(x, from.getY() + y, z);
            if (this.level().getBlockState(checkPos).isSolid()) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isAreaSafe(BlockPos centerPos) {
      for (int dx = -1; dx <= 1; dx++) {
         for (int dz = -1; dz <= 1; dz++) {
            BlockPos checkPos = centerPos.offset(dx, 0, dz);

            for (int dy = 0; dy < 5; dy++) {
               BlockState state = this.level().getBlockState(checkPos.above(dy));
               if (state.isSolid()) {
                  return false;
               }
            }
         }
      }

      return true;
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

      for (int i = 0; i < 5; i++) {
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
         serverLevel.sendParticles(ParticleTypes.PORTAL, this.getX(), this.getY() + 1.0, this.getZ(), 50, 0.5, 1.0, 0.5, 0.1);
      }

      this.teleportTo(pos.x, pos.y, pos.z);
      if (this.isStuckInBlocks()) {
         this.teleportTo(originalPos.x, originalPos.y, originalPos.z);
         return false;
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(ParticleTypes.PORTAL, this.getX(), this.getY() + 1.0, this.getZ(), 50, 0.5, 1.0, 0.5, 0.1);
      }

      this.level().playSound(null, this.blockPosition(), (SoundEvent)ModSounds.BOSS_TELEPORT.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
      return true;
   }

   private boolean isStuckInBlocks() {
      BlockPos feetPos = this.blockPosition();

      for (int dx = -1; dx <= 1; dx++) {
         for (int dz = -1; dz <= 1; dz++) {
            for (int dy = 0; dy < 5; dy++) {
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

   private void handleTimeAccelerationAbility() {
      if (this.timeAccelerationCooldown <= 0 && this.postTeleportDelay <= 0) {
         this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 1));
         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.ENCHANT, this.getX(), this.getY() + 1.0, this.getZ(), 50, 0.5, 1.0, 0.5, 0.1);
         }

         this.timeAccelerationCooldown = 160;
      }
   }

   private void handleAoEAbility() {
      if (this.aoeCooldown <= 0 && this.postTeleportDelay <= 0) {
         AABB aoeBox = new AABB(this.blockPosition()).inflate(5.0);
         boolean hitAnyEntity = false;

         for (Player player : this.level().getEntitiesOfClass(Player.class, aoeBox)) {
            if (player.isAlive()) {
               float damage = 12.0F;
               Holder<MobEffect> aegisEffect = BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)ModEffects.CHRONO_AEGIS_BUFF.get());
               if (player.hasEffect(aegisEffect)) {
                  damage *= 0.5F;
               }

               player.hurt(this.damageSources().mobAttack(this), damage);
               player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
               player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 1));
               hitAnyEntity = true;
            }
         }

         if (hitAnyEntity) {
            if (this.level() instanceof ServerLevel serverLevel) {
               for (double angle = 0.0; angle < Math.PI * 2; angle += Math.PI / 16) {
                  double x = this.getX() + Math.cos(angle) * 5.0;
                  double z = this.getZ() + Math.sin(angle) * 5.0;
                  serverLevel.sendParticles(ParticleTypes.CRIMSON_SPORE, x, this.getY() + 0.1, z, 1, 0.0, 0.0, 0.0, 0.0);
               }
            }

            this.level().playSound(null, this.blockPosition(), (SoundEvent)ModSounds.BOSS_CAST_SPELL.get(), SoundSource.HOSTILE, 2.0F, 0.8F);
            this.aoeCooldown = 120;
         }
      }
   }

   private void handleTimeReversalAbility() {
      if (!this.hasUsedTimeReversal) {
         float healthPercent = this.getHealth() / this.getMaxHealth();
         if (!(healthPercent > 0.2F)) {
            float healPercent = 0.1F;
            if (!this.chronoAegisDisruptionActive && this.hasNearbyChronoAegisPlayer()) {
               this.chronoAegisDisruptionActive = true;
               healPercent *= 0.5F;
            }

            float healAmount = this.getMaxHealth() * healPercent;
            this.heal(healAmount);
            this.hasUsedTimeReversal = true;
            if (this.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + 1.0, this.getZ(), 100, 0.5, 1.0, 0.5, 0.2);
               serverLevel.sendParticles(ParticleTypes.END_ROD, this.getX(), this.getY() + 1.0, this.getZ(), 50, 0.5, 1.0, 0.5, 0.1);
            }

            this.level().playSound(null, this.blockPosition(), (SoundEvent)ModSounds.TYRANT_PHASE_CHANGE.get(), SoundSource.HOSTILE, 2.0F, 1.0F);
            Component message = Component.literal("Time Tyrant reverses time!").withStyle(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.BOLD});

            for (Player player : this.level().getEntitiesOfClass(Player.class, new AABB(this.blockPosition()).inflate(32.0))) {
               player.displayClientMessage(message, true);
            }
         }
      }
   }

   private void decrementCooldowns() {
      if (this.timeStopCooldown > 0) {
         this.timeStopCooldown--;
      }

      if (this.teleportCooldown > 0) {
         this.teleportCooldown--;
      }

      if (this.timeAccelerationCooldown > 0) {
         this.timeAccelerationCooldown--;
      }

      if (this.aoeCooldown > 0) {
         this.aoeCooldown--;
      }

      if (this.postTeleportDelay > 0) {
         this.postTeleportDelay--;
      }

      if (this.chronoAegisTeleportBlockTicks > 0) {
         this.chronoAegisTeleportBlockTicks--;
         if (this.chronoAegisTeleportBlockTicks == 0) {
            this.chronoAegisAnchorActive = false;
         }
      }

      if (!this.hasNearbyChronoAegisPlayer()) {
         this.chronoAegisDisruptionActive = false;
      }
   }

   public boolean applyTimeClockWeakening() {
      int currentPhase = (Integer)this.entityData.get(PHASE);

      int usesInCurrentPhase = switch (currentPhase) {
         case 1 -> this.timeClockUsesInPhase1;
         case 2 -> this.timeClockUsesInPhase2;
         case 3 -> this.timeClockUsesInPhase3;
         default -> 1;
      };
      if (usesInCurrentPhase > 0) {
         return false;
      }

      switch (currentPhase) {
         case 1:
            this.timeClockUsesInPhase1++;
            break;
         case 2:
            this.timeClockUsesInPhase2++;
            break;
         case 3:
            this.timeClockUsesInPhase3++;
      }

      this.timeClockWeakeningTicks = 200;
      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(ParticleTypes.WITCH, this.getX(), this.getY() + 1.5, this.getZ(), 50, 0.5, 1.0, 0.5, 0.1);
      }

      this.level().playSound(null, this.blockPosition(), (SoundEvent)ModSounds.TYRANT_BELL.get(), SoundSource.HOSTILE, 1.0F, 0.5F);
      return true;
   }

   private void handleTimeClockWeakening() {
      if (this.timeClockWeakeningTicks > 0) {
         AttributeInstance armorAttribute = this.getAttribute(Attributes.ARMOR);
         if (armorAttribute != null) {
            double baseArmor = 15.0;
            double targetArmor = 5.0;
            double currentArmor = armorAttribute.getBaseValue();
            if (currentArmor > targetArmor) {
               armorAttribute.setBaseValue(targetArmor);
            }
         }

         AttributeInstance speedAttribute = this.getAttribute(Attributes.MOVEMENT_SPEED);
         if (speedAttribute != null) {
            double baseSpeed = 0.25;
            double targetSpeed = baseSpeed * 0.5;
            speedAttribute.setBaseValue(targetSpeed);
         }

         this.timeClockWeakeningTicks--;
         if (this.timeClockWeakeningTicks == 0) {
            armorAttribute = this.getAttribute(Attributes.ARMOR);
            if (armorAttribute != null) {
               armorAttribute.setBaseValue(15.0);
            }

            speedAttribute = this.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttribute != null) {
               speedAttribute.setBaseValue(0.25);
            }

            if (this.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(ParticleTypes.POOF, this.getX(), this.getY() + 1.5, this.getZ(), 20, 0.5, 1.0, 0.5, 0.05);
            }
         }
      } else {
         AttributeInstance armorAttribute = this.getAttribute(Attributes.ARMOR);
         if (armorAttribute != null && armorAttribute.getBaseValue() < 15.0) {
            armorAttribute.setBaseValue(15.0);
         }

         AttributeInstance speedAttribute = this.getAttribute(Attributes.MOVEMENT_SPEED);
         if (speedAttribute != null && speedAttribute.getBaseValue() < 0.25) {
            speedAttribute.setBaseValue(0.25);
         }
      }
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossEvent.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   public void customServerAiStep() {
      super.customServerAiStep();
      this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (this.hasCustomName()) {
         this.bossEvent.setName(this.getDisplayName());
      }

      this.entityData.set(PHASE, tag.getInt("Phase"));
      this.hasUsedTimeReversal = tag.getBoolean("HasUsedTimeReversal");
      this.chronoAegisAnchorActive = tag.getBoolean("ChronoAegisAnchorActive");
      this.chronoAegisDisruptionActive = tag.getBoolean("ChronoAegisDisruptionActive");
      this.chronoAegisTeleportBlockTicks = tag.getInt("ChronoAegisTeleportBlockTicks");
      this.timeClockWeakeningTicks = tag.getInt("TimeClockWeakeningTicks");
      this.timeClockUsesInPhase1 = tag.getInt("TimeClockUsesInPhase1");
      this.timeClockUsesInPhase2 = tag.getInt("TimeClockUsesInPhase2");
      this.timeClockUsesInPhase3 = tag.getInt("TimeClockUsesInPhase3");
      this.previousPhase = tag.getInt("PreviousPhase");
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("Phase", (Integer)this.entityData.get(PHASE));
      tag.putBoolean("HasUsedTimeReversal", this.hasUsedTimeReversal);
      tag.putBoolean("ChronoAegisAnchorActive", this.chronoAegisAnchorActive);
      tag.putBoolean("ChronoAegisDisruptionActive", this.chronoAegisDisruptionActive);
      tag.putInt("ChronoAegisTeleportBlockTicks", this.chronoAegisTeleportBlockTicks);
      tag.putInt("TimeClockWeakeningTicks", this.timeClockWeakeningTicks);
      tag.putInt("TimeClockUsesInPhase1", this.timeClockUsesInPhase1);
      tag.putInt("TimeClockUsesInPhase2", this.timeClockUsesInPhase2);
      tag.putInt("TimeClockUsesInPhase3", this.timeClockUsesInPhase3);
      tag.putInt("PreviousPhase", this.previousPhase);
   }

   private boolean hasNearbyChronoAegisPlayer() {
      AABB searchBox = new AABB(this.blockPosition()).inflate(32.0);
      Holder<MobEffect> aegisEffect = BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)ModEffects.CHRONO_AEGIS_BUFF.get());
      return this.level().getEntitiesOfClass(Player.class, searchBox).stream().anyMatch(player -> player.hasEffect(aegisEffect));
   }

   public boolean isPersistenceRequired() {
      return true;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public void die(DamageSource cause) {
      super.die(cause);
      if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
         BlockProtectionHandler.onBossDefeatedAt(serverLevel, this.blockPosition());
         DimensionStabilizer.stabilizeDimension(serverLevel);
         DimensionStateData dimensionState = DimensionStateData.get(serverLevel);
         dimensionState.enhanceTimeDistortion();
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      if (source.getDirectEntity() instanceof Projectile) {
         amount *= 0.3F;
      }

      return super.hurt(source, amount);
   }
}
