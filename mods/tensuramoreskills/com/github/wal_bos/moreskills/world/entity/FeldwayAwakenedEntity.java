package com.github.wal_bos.moreskills.world.entity;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.handler.AsmodayBlackHoleHandler;
import com.github.wal_bos.moreskills.handler.FeldwayRewardHandler;
import com.github.wal_bos.moreskills.registry.TenaddexItems;
import com.github.wal_bos.moreskills.world.entity.feldway.AwakenedFeldwayCombat;
import com.github.wal_bos.moreskills.world.entity.feldway.AwakenedFeldwayDefense;
import com.github.wal_bos.moreskills.world.entity.feldway.AwakenedFeldwayTargeting;
import com.github.wal_bos.moreskills.world.entity.feldway.AwakenedImperialHaloShot;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.EnumSet;
import java.util.UUID;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FeldwayAwakenedEntity extends PathfinderMob {
   private static final Component AWAKENED_BOSS_NAME = Component.literal("Feldway, Lord of the Four Shades");
   private final ServerBossEvent bossEvent = new ServerBossEvent(AWAKENED_BOSS_NAME, BossBarColor.RED, BossBarOverlay.NOTCHED_20);
   private UUID lockedTargetId;
   private int globalCooldown;
   private int sideAttackCooldown;
   private int attackId;
   private int attackTicks;
   private boolean awakenedBlackHoleUsed;
   private int awakenedBlackHoleCombatTicks;
   private int forcedAttackMask;

   private static EntityConfigs.AwakenedFeldwayConfig cfg() {
      return EntityConfigs.ENTITY.awakenedFeldway;
   }

   private static int blackHoleForceCombatTicks() {
      return (Integer)cfg().blackHoleForceCombatTicks.get();
   }

   private static int weightImperialHaloShot() {
      return (Integer)cfg().weightImperialHaloShot.get();
   }

   private static int weightLightningShot() {
      return (Integer)cfg().weightLightningShot.get();
   }

   private static int weightMirageExecution() {
      return (Integer)cfg().weightMirageExecution.get();
   }

   private static int weightChronoJudgementClock() {
      return (Integer)cfg().weightChronoJudgementClock.get();
   }

   private static int weightSeraphicGuillotine() {
      return (Integer)cfg().weightSeraphicGuillotine.get();
   }

   private static int weightAvatarOblivion() {
      return (Integer)cfg().weightAvatarOblivion.get();
   }

   private static int weightAxisJudgment() {
      return (Integer)cfg().weightAxisJudgment.get();
   }

   private static float blackHoleRareChance() {
      return ((Double)cfg().blackHoleRareChance.get()).floatValue();
   }

   public FeldwayAwakenedEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
      this.bossEvent.setDarkenScreen((Boolean)cfg().awakenedBossBarDarkenScreen.get());
      this.bossEvent.setPlayBossMusic((Boolean)cfg().awakenedBossBarPlayMusic.get());
      this.moveControl = new FlyingMoveControl(this, (Integer)cfg().awakenedFlyingMoveControlTurn.get(), true);
      this.setNoGravity((Boolean)cfg().awakenedNoGravity.get());
      if ((Boolean)cfg().awakenedPersistent.get()) {
         this.setPersistenceRequired();
      }

      this.xpReward = (Integer)cfg().awakenedXpReward.get();
      this.applyConfiguredAttributes();
   }

   private void applyConfiguredAttributes() {
      this.setBase(Attributes.MAX_HEALTH, (Double)cfg().awakenedMaxHealth.get());
      this.setBase(Attributes.ARMOR, (Double)cfg().awakenedArmor.get());
      this.setBase(Attributes.ARMOR_TOUGHNESS, (Double)cfg().awakenedArmorToughness.get());
      this.setBase(Attributes.KNOCKBACK_RESISTANCE, (Double)cfg().awakenedKnockbackResistance.get());
      this.setBase(Attributes.MOVEMENT_SPEED, (Double)cfg().awakenedMovementSpeed.get());
      this.setBase(Attributes.FLYING_SPEED, (Double)cfg().awakenedFlyingSpeed.get());
      this.setBase(Attributes.ATTACK_DAMAGE, (Double)cfg().awakenedAttackDamage.get());
      this.setBase(Attributes.FOLLOW_RANGE, (Double)cfg().awakenedFollowRange.get());
      this.setBase(TensuraAttributes.PRESENCE_SENSE, (Double)cfg().awakenedPresenceSense.get());
      this.setBase(TensuraAttributes.PRESENCE_SENSE_RADIUS, (Double)cfg().awakenedPresenceSenseRadius.get());
      this.setBase(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, (Double)cfg().awakenedSpiritualHealthRegeneration.get());
      this.setBase(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, (Double)cfg().awakenedAuraRegenMultiplier.get());
      this.setBase(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, (Double)cfg().awakenedMagiculeRegenMultiplier.get());
      this.setHealth(this.getMaxHealth());
   }

   private void setBase(Holder<Attribute> attribute, double value) {
      AttributeInstance instance = this.getAttribute(attribute);
      if (instance != null) {
         instance.setBaseValue(value);
      }
   }

   public Component getDisplayName() {
      return this.hasCustomName() ? super.getDisplayName() : AWAKENED_BOSS_NAME;
   }

   public void setCustomName(Component name) {
      super.setCustomName(name);
      this.bossEvent.setName(name == null ? AWAKENED_BOSS_NAME : name);
   }

   protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
      super.dropCustomDeathLoot(level, damageSource, recentlyHit);
      this.spawnAtLocation((ItemLike)TenaddexItems.FELDWAY_ANGEL_SUMMONER.get());
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 32000.0)
         .add(Attributes.ARMOR, 120.0)
         .add(Attributes.ARMOR_TOUGHNESS, 80.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.MOVEMENT_SPEED, 0.42)
         .add(Attributes.FLYING_SPEED, 1.0)
         .add(Attributes.ATTACK_DAMAGE, 420.0)
         .add(Attributes.FOLLOW_RANGE, 160.0)
         .add(TensuraAttributes.PRESENCE_SENSE, 10.0)
         .add(TensuraAttributes.PRESENCE_SENSE_RADIUS, 160.0)
         .add(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, 250.0)
         .add(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, 20.0)
         .add(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, 20.0);
   }

   public void setLockedTarget(UUID targetId) {
      this.lockedTargetId = targetId;
   }

   public UUID getLockedTargetId() {
      return this.lockedTargetId;
   }

   public int getGlobalCooldown() {
      return this.globalCooldown;
   }

   public void setGlobalCooldown(int globalCooldown) {
      this.globalCooldown = Math.max(this.globalCooldown, globalCooldown);
   }

   public int getSideAttackCooldown() {
      return this.sideAttackCooldown;
   }

   public void setSideAttackCooldown(int sideAttackCooldown) {
      this.sideAttackCooldown = Math.max(this.sideAttackCooldown, sideAttackCooldown);
   }

   public int getAttackId() {
      return this.attackId;
   }

   public void setAttack(int attackId) {
      this.attackId = attackId;
      this.attackTicks = 0;
   }

   public int getAttackTicks() {
      return this.attackTicks;
   }

   public void clearAttack() {
      this.attackId = 0;
      this.attackTicks = 0;
   }

   public boolean hasUsedAwakenedBlackHole() {
      return this.awakenedBlackHoleUsed;
   }

   public void markAwakenedBlackHoleUsed() {
      this.awakenedBlackHoleUsed = true;
      this.awakenedBlackHoleCombatTicks = blackHoleForceCombatTicks();
      this.markForcedAwakenedAttack(1);
   }

   public boolean shouldForceAwakenedBlackHole() {
      return !this.awakenedBlackHoleUsed && this.awakenedBlackHoleCombatTicks >= blackHoleForceCombatTicks();
   }

   public void tickAwakenedBlackHoleTimer(boolean inCombat) {
      if (!this.awakenedBlackHoleUsed) {
         if (inCombat) {
            this.awakenedBlackHoleCombatTicks++;
         } else if (this.awakenedBlackHoleCombatTicks > 0) {
            this.awakenedBlackHoleCombatTicks--;
         }
      }
   }

   public int nextForcedAwakenedAttack(boolean canBlackHole) {
      int[] order = new int[]{80, 2, 3, 7, 6, 5, 4, 1};

      for (int id : order) {
         if (id != 1 || canBlackHole) {
            int bit = attackBit(id);
            if (bit != 0 && (this.forcedAttackMask & bit) == 0) {
               return id;
            }
         }
      }

      return 0;
   }

   public void markForcedAwakenedAttack(int id) {
      int bit = attackBit(id);
      if (bit != 0) {
         this.forcedAttackMask |= bit;
      }
   }

   private static int attackBit(int id) {
      return switch (id) {
         case 1 -> 1;
         case 2 -> 2;
         case 3 -> 4;
         case 4 -> 8;
         case 5 -> 16;
         case 6 -> 32;
         case 7 -> 64;
         case 80 -> 128;
         default -> 0;
      };
   }

   public DamageSource holySource(boolean barrierBypass, boolean resistanceBypass) {
      DamageSource src = TensuraDamageTypes.getEntityDamageSource(this.level(), TensuraDamageTypes.HOLY_DAMAGE, this);
      TensuraDamageSource tSrc = (TensuraDamageSource)src;
      if (resistanceBypass) {
         src = tSrc.tensura$setResistanceBypassLevel(1.0F);
         tSrc = (TensuraDamageSource)src;
      }

      if (barrierBypass) {
         src = tSrc.tensura$setBarrierBypassLevel(1.0F);
      }

      return src;
   }

   public void hurtSpiritual(LivingEntity target, float amount) {
      try {
         IExistence ex = TensuraStorages.getExistenceFrom(target);
         ex.setSpiritualHealth(Math.max(0.0, ex.getSpiritualHealth() - amount));
         ex.markDirty();
         if (ex.getSpiritualHealth() <= 0.0 && target.isAlive()) {
            target.setHealth(0.0F);
            target.die(((TensuraDamageSource)this.holySource(true, true)).tensura$setSpiritual());
         }
      } catch (Throwable var4) {
      }
   }

   public boolean isValidEnemy(Entity entity) {
      if (!(entity instanceof LivingEntity living)) {
         return false;
      } else if (!living.isAlive()) {
         return false;
      } else if (living == this) {
         return false;
      } else {
         return entity instanceof Player player && (player.isCreative() || player.isSpectator()) ? false : !entity.isAlliedTo(this) && !this.isAlliedTo(entity);
      }
   }

   public LivingEntity resolveLockedTarget() {
      if (this.level() instanceof ServerLevel sl) {
         if (this.lockedTargetId == null) {
            return null;
         } else {
            return sl.getEntity(this.lockedTargetId) instanceof LivingEntity living && this.isValidEnemy(living) ? living : null;
         }
      } else {
         return null;
      }
   }

   public void setTarget(LivingEntity target) {
      if (target != null && this.isValidEnemy(target)) {
         this.lockedTargetId = target.getUUID();
         super.setTarget(target);
      } else {
         LivingEntity locked = this.resolveLockedTarget();
         if (locked != null) {
            super.setTarget(locked);
         } else {
            super.setTarget(null);
         }
      }
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
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

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FeldwayAwakenedEntity.AwakenedCombatGoal(this));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, ((Double)cfg().awakenedLookAtPlayerRange.get()).floatValue()));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
   }

   public void tick() {
      if (!this.level().isClientSide()) {
         AwakenedFeldwayDefense.tick(this);
      }

      super.tick();
      if (!this.level().isClientSide()) {
         if (!this.isRemoved() && this.deathTime <= 0 && this.isAlive()) {
            this.tickTimers();
            this.setNoGravity((Boolean)cfg().awakenedNoGravity.get());
            this.invulnerableTime = 0;
            this.hurtTime = 0;
            this.bossEvent.setProgress(Mth.clamp(this.getHealth() / this.getMaxHealth(), 0.0F, 1.0F));
            AwakenedFeldwayTargeting.tick(this);
            LivingEntity target = this.resolveLockedTarget();
            if (target == null) {
               target = this.getTarget();
            }

            this.tickAwakenedBlackHoleTimer(target != null && target.isAlive() && this.isValidEnemy(target));
            AwakenedFeldwayCombat.tickAttack(this);
            AwakenedFeldwayCombat.tickSideAttacks(this, target);
            if (this.attackId == 0 || this.attackId == 2 || this.attackId == 80) {
               this.tickMovement(target);
            }
         } else {
            this.bossEvent.setProgress(Mth.clamp(this.getHealth() / this.getMaxHealth(), 0.0F, 1.0F));
         }
      }
   }

   private void tickTimers() {
      if (this.globalCooldown > 0) {
         this.globalCooldown--;
      }

      if (this.sideAttackCooldown > 0) {
         this.sideAttackCooldown--;
      }

      if (this.attackId != 0) {
         this.attackTicks++;
      }
   }

   private void tickMovement(LivingEntity target) {
      if (target != null && target.isAlive()) {
         this.getLookControl().setLookAt(target, 90.0F, 90.0F);
         double horizontalSqr = this.distanceToSqr(target.getX(), this.getY(), target.getZ());
         double horizontal = Math.sqrt(horizontalSqr);
         double preferredRadius = horizontal > 34.0 ? 8.0 : (horizontal < 8.0 ? 15.0 : 12.0);
         double angle = this.tickCount * 0.042;
         Vec3 away = this.position().subtract(target.position());
         away = new Vec3(away.x, 0.0, away.z);
         if (away.lengthSqr() < 0.001) {
            away = new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
         } else {
            away = away.normalize();
         }

         Vec3 side = new Vec3(-away.z, 0.0, away.x);
         double orbitStrength = horizontal > 28.0 ? 0.25 : 0.72;
         Vec3 offset = away.scale(preferredRadius * (1.0 - orbitStrength)).add(side.scale(preferredRadius * orbitStrength));
         double wantedX = target.getX() + offset.x;
         double wantedZ = target.getZ() + offset.z;
         double groundY = this.findGroundYBelow(wantedX, Math.max(this.getY(), target.getY()) + 48.0, wantedZ);
         double targetGroundY = this.findGroundYBelow(target.getX(), target.getY() + 48.0, target.getZ());
         double selfGroundY = this.findGroundYBelow(this.getX(), this.getY() + 48.0, this.getZ());
         if (Double.isNaN(groundY)) {
            groundY = targetGroundY;
         }

         if (Double.isNaN(groundY)) {
            groundY = selfGroundY;
         }

         double wantedY;
         if (!Double.isNaN(groundY)) {
            double baseHoverY = groundY + (Double)cfg().hoverHeight.get();
            double targetHighBreakY = groundY + (Double)cfg().hoverTargetHighBreakHeight.get();
            if (target.getY() > targetHighBreakY) {
               wantedY = target.getY() + target.getBbHeight() * 0.5 + (Double)cfg().hoverAboveTargetWhenHigh.get();
            } else {
               wantedY = baseHoverY;
            }

            double lowLimit = groundY + (Double)cfg().hoverLowLimit.get();
            double highLimit = target.getY() > targetHighBreakY
               ? target.getY() + (Double)cfg().hoverHighTargetBonus.get()
               : groundY + (Double)cfg().hoverHighLimit.get();
            wantedY = Mth.clamp(wantedY, lowLimit, highLimit);
            if (this.getY() > groundY + (Double)cfg().hoverTooHighDescentStart.get() && target.getY() <= targetHighBreakY) {
               wantedY = Math.min(wantedY, this.getY() - 1.2);
            }

            if (this.getY() < groundY + (Double)cfg().hoverTooLowRiseStart.get()) {
               wantedY = Math.max(wantedY, this.getY() + 1.4);
            }
         } else {
            double fallbackY = target.getY() + (Double)cfg().hoverHeight.get();
            if (target.getY() > this.getY() + 24.0) {
               fallbackY = target.getY() + (Double)cfg().hoverAboveTargetWhenHigh.get();
            }

            wantedY = fallbackY;
         }

         double maxUpStep = target.getY() > wantedY + 12.0 ? (Double)cfg().hoverHighTargetMaxUpStep.get() : (Double)cfg().hoverMaxUpStep.get();
         double yStep = Mth.clamp(wantedY - this.getY(), -(Double)cfg().hoverMaxDownStep.get(), maxUpStep);
         wantedY = this.getY() + yStep;
         double speed = horizontal > 42.0
            ? (Double)cfg().movementFarSpeed.get()
            : (horizontal > 24.0 ? (Double)cfg().movementMidSpeed.get() : (Double)cfg().movementNearSpeed.get());
         this.getMoveControl().setWantedPosition(wantedX, wantedY, wantedZ, speed);
         Vec3 motion = this.getDeltaMovement();
         if (!Double.isNaN(groundY) && target.getY() <= groundY + 42.0 && this.getY() > groundY + 39.0 && motion.y > -0.28) {
            this.setDeltaMovement(motion.x, Math.min(motion.y * 0.35 - 0.05, -0.08), motion.z);
            this.hurtMarked = true;
         } else if (!Double.isNaN(groundY) && this.getY() < groundY + 24.0 && motion.y < 0.2) {
            this.setDeltaMovement(motion.x, Math.max(motion.y * 0.35 + 0.08, 0.12), motion.z);
            this.hurtMarked = true;
         } else if (Math.abs(this.getY() - wantedY) < 0.9 && Math.abs(motion.y) > 0.18) {
            this.setDeltaMovement(motion.x, motion.y * 0.65, motion.z);
            this.hurtMarked = true;
         }
      } else {
         this.getNavigation().stop();
      }
   }

   private double findGroundYBelow(double x, double startY, double z) {
      int y = Mth.floor(Mth.clamp(startY, this.level().getMinBuildHeight() + 1.0, this.level().getMaxBuildHeight() - 1.0));
      int minY = Math.max(this.level().getMinBuildHeight(), y - 160);
      MutableBlockPos pos = new MutableBlockPos(Mth.floor(x), y, Mth.floor(z));

      while (pos.getY() >= minY) {
         if (this.level().isLoaded(pos) && this.level().getBlockState(pos).isFaceSturdy(this.level(), pos, Direction.UP)) {
            return pos.getY() + 1.0;
         }

         pos.move(0, -1, 0);
      }

      return Double.NaN;
   }

   public boolean canBeAffected(MobEffectInstance instance) {
      return AwakenedFeldwayDefense.canBeAffected(this, instance);
   }

   public boolean addEffect(MobEffectInstance effectInstance, Entity entity) {
      return false;
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return false;
   }

   public void setHealth(float value) {
      super.setHealth(AwakenedFeldwayDefense.guardSetHealth(this, value));
   }

   public boolean hurt(DamageSource source, float amount) {
      if (AwakenedFeldwayDefense.shouldCancelDamage(this, source, amount)) {
         return false;
      } else {
         Entity attacker = source.getEntity();
         Entity direct = source.getDirectEntity();
         if (attacker instanceof LivingEntity livingAttacker && !this.isValidEnemy(livingAttacker)) {
            return false;
         } else if (direct instanceof LivingEntity livingDirect && direct != attacker && !this.isValidEnemy(livingDirect)) {
            return false;
         } else {
            this.invulnerableTime = 0;
            this.hurtTime = 0;
            float before = this.getHealth();
            float guarded = AwakenedFeldwayDefense.modifyIncomingDamage(this, source, amount);
            if (guarded <= 0.0F) {
               return false;
            }

            boolean ok = super.hurt(source, guarded);
            this.invulnerableTime = 0;
            this.hurtTime = 0;
            AwakenedFeldwayDefense.afterDamage(this, before, guarded);
            return ok;
         }
      }
   }

   public void die(DamageSource source) {
      if (!AwakenedFeldwayDefense.shouldStopDeath(this, source)) {
         if (!this.level().isClientSide()) {
            FeldwayRewardHandler.tryOpenReward(this, source);
         }

         super.die(source);
      }
   }

   public void remove(RemovalReason reason) {
      if (!AwakenedFeldwayDefense.shouldBlockRemoval(this, reason)) {
         super.remove(reason);
      }
   }

   public boolean doHurtTarget(Entity target) {
      if (target instanceof LivingEntity living) {
         if (!this.isValidEnemy(living)) {
            return false;
         }

         boolean ok = living.hurt(this.holySource(true, true), 1200.0F);
         this.hurtSpiritual(living, 650.0F);
         return ok;
      } else {
         return false;
      }
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      if (this.lockedTargetId != null) {
         tag.putUUID("AwakenedLockedTarget", this.lockedTargetId);
      }

      tag.putInt("AwakenedGlobalCooldown", this.globalCooldown);
      tag.putInt("AwakenedSideAttackCooldown", this.sideAttackCooldown);
      tag.putInt("AwakenedAttackId", this.attackId);
      tag.putInt("AwakenedAttackTicks", this.attackTicks);
      tag.putBoolean("AwakenedBlackHoleUsed", this.awakenedBlackHoleUsed);
      tag.putInt("AwakenedBlackHoleCombatTicks", this.awakenedBlackHoleCombatTicks);
      tag.putInt("AwakenedForcedAttackMask", this.forcedAttackMask);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.hasUUID("AwakenedLockedTarget")) {
         this.lockedTargetId = tag.getUUID("AwakenedLockedTarget");
      }

      this.globalCooldown = tag.getInt("AwakenedGlobalCooldown");
      this.sideAttackCooldown = tag.getInt("AwakenedSideAttackCooldown");
      this.attackId = tag.getInt("AwakenedAttackId");
      this.attackTicks = tag.getInt("AwakenedAttackTicks");
      this.awakenedBlackHoleUsed = tag.getBoolean("AwakenedBlackHoleUsed");
      this.awakenedBlackHoleCombatTicks = tag.getInt("AwakenedBlackHoleCombatTicks");
      this.forcedAttackMask = tag.getInt("AwakenedForcedAttackMask");
      this.bossEvent.setName(this.hasCustomName() ? this.getDisplayName() : AWAKENED_BOSS_NAME);
   }

   private static class AwakenedCombatGoal extends Goal {
      private final FeldwayAwakenedEntity boss;

      private AwakenedCombatGoal(FeldwayAwakenedEntity boss) {
         this.boss = boss;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         LivingEntity target = this.boss.resolveLockedTarget();
         if (target == null) {
            target = this.boss.getTarget();
         }

         return target != null && target.isAlive() && this.boss.isValidEnemy(target);
      }

      public void tick() {
         if (this.boss.getAttackId() == 0) {
            if (this.boss.getGlobalCooldown() <= 0) {
               LivingEntity target = this.boss.resolveLockedTarget();
               if (target == null) {
                  target = this.boss.getTarget();
               }

               if (target != null && target.isAlive()) {
                  if (this.boss.isValidEnemy(target)) {
                     this.boss.getLookControl().setLookAt(target, 90.0F, 90.0F);
                     boolean canBlackHole = !this.boss.hasUsedAwakenedBlackHole() && !AsmodayBlackHoleHandler.hasActiveBlackHole(this.boss);
                     if (canBlackHole && this.boss.shouldForceAwakenedBlackHole()) {
                        this.boss.markForcedAwakenedAttack(1);
                        this.boss.setAttack(1);
                     } else {
                        int forced = this.boss.nextForcedAwakenedAttack(canBlackHole);
                        if (forced != 0) {
                           this.boss.markForcedAwakenedAttack(forced);
                           if (forced == 80) {
                              AwakenedImperialHaloShot.begin(this.boss);
                           } else {
                              this.boss.setAttack(forced);
                           }
                        } else if (canBlackHole && this.boss.getRandom().nextFloat() < FeldwayAwakenedEntity.blackHoleRareChance()) {
                           this.boss.markForcedAwakenedAttack(1);
                           this.boss.setAttack(1);
                        } else {
                           int total = FeldwayAwakenedEntity.weightImperialHaloShot()
                              + FeldwayAwakenedEntity.weightLightningShot()
                              + FeldwayAwakenedEntity.weightMirageExecution()
                              + FeldwayAwakenedEntity.weightChronoJudgementClock()
                              + FeldwayAwakenedEntity.weightSeraphicGuillotine()
                              + FeldwayAwakenedEntity.weightAvatarOblivion()
                              + FeldwayAwakenedEntity.weightAxisJudgment();
                           int roll = this.boss.getRandom().nextInt(total);
                           if (roll < FeldwayAwakenedEntity.weightImperialHaloShot()) {
                              AwakenedImperialHaloShot.begin(this.boss);
                           } else {
                              roll -= FeldwayAwakenedEntity.weightImperialHaloShot();
                              if (roll < FeldwayAwakenedEntity.weightLightningShot()) {
                                 this.boss.setAttack(2);
                              } else {
                                 roll -= FeldwayAwakenedEntity.weightLightningShot();
                                 if (roll < FeldwayAwakenedEntity.weightMirageExecution()) {
                                    this.boss.setAttack(3);
                                 } else {
                                    roll -= FeldwayAwakenedEntity.weightMirageExecution();
                                    if (roll < FeldwayAwakenedEntity.weightChronoJudgementClock()) {
                                       this.boss.setAttack(7);
                                    } else {
                                       roll -= FeldwayAwakenedEntity.weightChronoJudgementClock();
                                       if (roll < FeldwayAwakenedEntity.weightSeraphicGuillotine()) {
                                          this.boss.setAttack(6);
                                       } else {
                                          roll -= FeldwayAwakenedEntity.weightSeraphicGuillotine();
                                          if (roll < FeldwayAwakenedEntity.weightAvatarOblivion()) {
                                             this.boss.setAttack(5);
                                          } else {
                                             this.boss.setAttack(4);
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
