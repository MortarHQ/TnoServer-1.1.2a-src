package com.github.wal_bos.moreskills.world.entity;

import com.github.wal_bos.moreskills.registry.entity.ModEntityTypes;
import com.github.wal_bos.moreskills.world.entity.avalon.AvalonNovariAntiFlight;
import com.github.wal_bos.moreskills.world.entity.avalon.AvalonNovariCombat;
import com.github.wal_bos.moreskills.world.entity.avalon.AvalonNovariPhaseData;
import com.github.wal_bos.moreskills.world.entity.avalon.AvalonNovariSkillLoadout;
import com.github.wal_bos.moreskills.world.entity.avalon.AvalonNovariTargeting;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AvalonNovariEntity extends PathfinderMob {
   private static final EntityDataAccessor<Integer> DATA_PHASE = SynchedEntityData.defineId(AvalonNovariEntity.class, EntityDataSerializers.INT);
   private final ServerBossEvent bossEvent = new ServerBossEvent(AvalonNovariPhaseData.name(1), AvalonNovariPhaseData.barColor(1), BossBarOverlay.NOTCHED_20);
   private UUID lockedTargetId;
   private int attackId;
   private int attackTicks;
   private int globalCooldown;
   private int forcedAttackMask;
   private int reflectTicks;
   private float reflectMultiplier;
   private int phenomenonTheftTicks;
   private int burdenedArmTicks;
   private boolean phaseStarted;

   public AvalonNovariEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
      this.bossEvent.setDarkenScreen(true);
      this.bossEvent.setPlayBossMusic(true);
      this.setPersistenceRequired();
      this.xpReward = 0;
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 100000.0)
         .add(Attributes.ARMOR, 255.0)
         .add(Attributes.ARMOR_TOUGHNESS, 100.0)
         .add(Attributes.ATTACK_DAMAGE, 150.0)
         .add(Attributes.MOVEMENT_SPEED, 0.34)
         .add(Attributes.FOLLOW_RANGE, 160.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.STEP_HEIGHT, 1.2)
         .add(TensuraAttributes.MAX_SPIRITUAL_HEALTH, 165000.0)
         .add(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, 300.0)
         .add(TensuraAttributes.MAX_AURA, 4.0E7)
         .add(TensuraAttributes.MAX_MAGICULE, 4.0E7)
         .add(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, 60.0)
         .add(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, 60.0)
         .add(TensuraAttributes.PRESENCE_SENSE, 10.0)
         .add(TensuraAttributes.PRESENCE_SENSE_RADIUS, 160.0)
         .add(TensuraAttributes.DODGE_NEGATE_CHANCE, 100.0)
         .add(TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, 100.0)
         .add(TensuraAttributes.RESISTANCE_DEGRADATION, 1.0)
         .add(TensuraAttributes.MULTILAYER_BARRIER, 1000000.0)
         .add(TensuraAttributes.CHANT_SPEED, 1000000.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_PHASE, 1);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("Phase", this.getPhase());
      compound.putInt("AttackId", this.attackId);
      compound.putInt("AttackTicks", this.attackTicks);
      compound.putInt("GlobalCooldown", this.globalCooldown);
      compound.putInt("ForcedAttackMask", this.forcedAttackMask);
      compound.putInt("ReflectTicks", this.reflectTicks);
      compound.putFloat("ReflectMultiplier", this.reflectMultiplier);
      compound.putInt("PhenomenonTheftTicks", this.phenomenonTheftTicks);
      compound.putInt("BurdenedArmTicks", this.burdenedArmTicks);
      compound.putBoolean("PhaseStarted", this.phaseStarted);
      if (this.lockedTargetId != null) {
         compound.putUUID("LockedTarget", this.lockedTargetId);
      }
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setPhase(compound.getInt("Phase") <= 0 ? 1 : compound.getInt("Phase"));
      this.attackId = compound.getInt("AttackId");
      this.attackTicks = compound.getInt("AttackTicks");
      this.globalCooldown = compound.getInt("GlobalCooldown");
      this.forcedAttackMask = compound.getInt("ForcedAttackMask");
      this.reflectTicks = compound.getInt("ReflectTicks");
      this.reflectMultiplier = compound.getFloat("ReflectMultiplier");
      this.phenomenonTheftTicks = compound.getInt("PhenomenonTheftTicks");
      this.burdenedArmTicks = compound.getInt("BurdenedArmTicks");
      this.phaseStarted = compound.getBoolean("PhaseStarted");
      if (compound.contains("LockedTarget")) {
         this.lockedTargetId = compound.getUUID("LockedTarget");
      }

      this.applyPhaseStats(false);
   }

   public int getPhase() {
      return (Integer)this.entityData.get(DATA_PHASE);
   }

   public void setPhase(int phase) {
      this.entityData.set(DATA_PHASE, Mth.clamp(phase, 1, AvalonNovariPhaseData.maxPhase()));
      this.bossEvent.setName(AvalonNovariPhaseData.name(this.getPhase()));
      this.bossEvent.setColor(AvalonNovariPhaseData.barColor(this.getPhase()));
   }

   public void setupPhase(int phase, UUID targetId) {
      this.setPhase(phase);
      this.lockedTargetId = targetId;
      this.phaseStarted = false;
      this.attackId = 0;
      this.attackTicks = 0;
      this.globalCooldown = 30;
      this.forcedAttackMask = 0;
      this.reflectTicks = 0;
      this.reflectMultiplier = 0.0F;
      this.phenomenonTheftTicks = 0;
      this.burdenedArmTicks = 0;
      this.applyPhaseStats(true);
      AvalonNovariSkillLoadout.apply(this);
   }

   private void applyPhaseStats(boolean fullHeal) {
      this.setBase(Attributes.MAX_HEALTH, AvalonNovariPhaseData.health(this.getPhase()));
      this.setBase(Attributes.ARMOR, AvalonNovariPhaseData.armor(this.getPhase()));
      this.setBase(Attributes.ATTACK_DAMAGE, AvalonNovariPhaseData.attack(this.getPhase()));
      this.setBase(TensuraAttributes.MAX_SPIRITUAL_HEALTH, AvalonNovariPhaseData.spiritualHealth(this.getPhase()));
      double maxEp = AvalonNovariPhaseData.maxEp(this.getPhase());
      this.setBase(TensuraAttributes.MAX_AURA, maxEp * 0.5);
      this.setBase(TensuraAttributes.MAX_MAGICULE, maxEp * 0.5);
      if (fullHeal) {
         this.setHealth(this.getMaxHealth());
         this.enforceExistence(true);
         AvalonNovariSkillLoadout.apply(this);
      }
   }

   private void setBase(Holder<Attribute> attribute, double value) {
      AttributeInstance instance = this.getAttribute(attribute);
      if (instance != null) {
         instance.setBaseValue(value);
      }
   }

   public Component getDisplayName() {
      return this.hasCustomName() ? super.getDisplayName() : AvalonNovariPhaseData.name(this.getPhase());
   }

   public void setCustomName(Component name) {
      super.setCustomName(name);
      this.bossEvent.setName(name == null ? AvalonNovariPhaseData.name(this.getPhase()) : name);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.85));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 24.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         if (!this.phaseStarted) {
            this.phaseStarted = true;
            this.message(AvalonNovariPhaseData.startQuote(this.getPhase()));
            this.applyPhaseStats(false);
            this.enforceExistence(true);
         }

         if (!this.isRemoved() && this.isAlive()) {
            this.tickTimers();
            if (this.tickCount % 20 == 0) {
               AvalonNovariSkillLoadout.apply(this);
            }

            this.bossEvent.setProgress(Mth.clamp(this.getHealth() / this.getMaxHealth(), 0.0F, 1.0F));
            this.invulnerableTime = 0;
            this.hurtTime = 0;
            this.setNoGravity(false);
            this.enforceExistence(false);
            AvalonNovariTargeting.tick(this);
            AvalonNovariAntiFlight.tick(this);
            this.tickBurdenedArm();
            AvalonNovariCombat.tickAttack(this);
            AvalonNovariCombat.tryStartAttack(this);
            this.tickMovement(this.resolveTarget());
         } else {
            this.bossEvent.setProgress(0.0F);
         }
      }
   }

   private void tickTimers() {
      if (this.globalCooldown > 0) {
         this.globalCooldown--;
      }

      if (this.attackId != 0) {
         this.attackTicks++;
      }

      if (this.reflectTicks > 0) {
         this.reflectTicks--;
      }

      if (this.reflectTicks <= 0) {
         this.reflectMultiplier = 0.0F;
      }

      if (this.phenomenonTheftTicks > 0) {
         this.phenomenonTheftTicks--;
      }

      if (this.burdenedArmTicks > 0) {
         this.burdenedArmTicks--;
      }
   }

   private void tickBurdenedArm() {
      if (this.getPhase() == 1) {
         if (this.burdenedArmTicks <= 0 && this.tickCount % 900 == 0) {
            this.burdenedArmTicks = 100;
            this.setBase(Attributes.ARMOR, 0.0);
            this.clearAttack();
            this.setGlobalCooldown(100);
            this.message(Component.literal("<Avalon> Ghh... the burdened arm...").withStyle(ChatFormatting.DARK_AQUA));
            this.level().playSound(null, this.blockPosition(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.HOSTILE, 4.0F, 0.25F);
         } else {
            if (this.burdenedArmTicks == 1) {
               this.setBase(Attributes.ARMOR, AvalonNovariPhaseData.armor(this.getPhase()));
            }
         }
      }
   }

   private void tickMovement(LivingEntity target) {
      if (this.attackId != 0 || this.burdenedArmTicks > 0) {
         this.getNavigation().stop();
      } else if (target != null && target.isAlive()) {
         this.getLookControl().setLookAt(target, 90.0F, 90.0F);
         double dist = this.distanceTo(target);
         if (dist > 8.0) {
            this.getNavigation().moveTo(target, 1.18);
         } else if (dist < 4.0) {
            Vec3 away = this.position().subtract(target.position());
            if (away.lengthSqr() < 0.001) {
               away = new Vec3(1.0, 0.0, 0.0);
            }

            away = new Vec3(away.x, 0.0, away.z).normalize();
            this.getMoveControl().setWantedPosition(this.getX() + away.x * 4.0, this.getY(), this.getZ() + away.z * 4.0, 1.05);
         } else {
            this.getNavigation().stop();
         }
      } else {
         this.getNavigation().stop();
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      if (this.level().isClientSide()) {
         return false;
      }

      if (!(amount <= 0.0F) && !Float.isNaN(amount) && !Float.isInfinite(amount)) {
         if (source.getEntity() != this && source.getDirectEntity() != this) {
            if (this.reflectTicks > 0 && source.getEntity() instanceof LivingEntity attacker && attacker != this) {
               DamageSource reflect = TensuraDamageTypes.getEntityDamageSource(this.level(), TensuraDamageTypes.SPACE_ELEMENTAL, this);
               attacker.invulnerableTime = 0;
               attacker.hurt(reflect, amount * this.reflectMultiplier);
               attacker.invulnerableTime = 0;
               return false;
            }

            if (this.getPhase() == 1 && this.burdenedArmTicks <= 0) {
               float converted = amount * 0.5F;
               amount *= 0.5F;

               try {
                  IExistence ex = TensuraStorages.getExistenceFrom(this);
                  ex.setMagicule(ex.getMagicule() + converted * 100.0);
                  ex.markDirty();
               } catch (Throwable var5) {
               }
            }

            if (this.getHealth() - amount <= 0.0F && this.getPhase() < AvalonNovariPhaseData.maxPhase()) {
               this.beginTransition(this.getPhase() + 1);
               return false;
            } else {
               boolean result = super.hurt(source, amount);
               this.invulnerableTime = 0;
               this.hurtTime = 0;
               return result;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void beginTransition(int nextPhase) {
      if (this.level() instanceof ServerLevel level) {
         this.message(AvalonNovariPhaseData.endQuote(this.getPhase()));
         AvalonNovariTransitionEntity transition = (AvalonNovariTransitionEntity)((EntityType)ModEntityTypes.AVALON_NOVARI_TRANSITION.get()).create(level);
         if (transition != null) {
            transition.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
            transition.setup(nextPhase, this.lockedTargetId, this.getUUID());
            level.addFreshEntity(transition);
         }

         this.bossEvent.removeAllPlayers();
         this.discard();
      }
   }

   public void die(DamageSource damageSource) {
      if (!this.level().isClientSide() && this.getPhase() < AvalonNovariPhaseData.maxPhase()) {
         this.beginTransition(this.getPhase() + 1);
      } else {
         this.message(AvalonNovariPhaseData.endQuote(this.getPhase()));
         super.die(damageSource);
      }
   }

   public boolean canBeAffected(MobEffectInstance effectInstance) {
      return false;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
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
      if (this.level() instanceof ServerLevel level) {
         if (this.lockedTargetId == null) {
            return null;
         } else {
            return level.getEntity(this.lockedTargetId) instanceof LivingEntity living && this.isValidEnemy(living) ? living : null;
         }
      } else {
         return null;
      }
   }

   public LivingEntity resolveTarget() {
      LivingEntity locked = this.resolveLockedTarget();
      if (locked != null) {
         return locked;
      }

      LivingEntity target = this.getTarget();
      return target != null && this.isValidEnemy(target) ? target : null;
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

   private void enforceExistence(boolean full) {
      try {
         IExistence ex = TensuraStorages.getExistenceFrom(this);
         double maxEp = AvalonNovariPhaseData.maxEp(this.getPhase());
         double minEp = AvalonNovariPhaseData.minEp(this.getPhase());
         if (full || ex.getAura() < minEp * 0.5) {
            ex.setAura(maxEp * 0.5);
         }

         if (full || ex.getMagicule() < minEp * 0.5) {
            ex.setMagicule(maxEp * 0.5);
         }

         if (full || ex.getSpiritualHealth() < AvalonNovariPhaseData.spiritualHealth(this.getPhase())) {
            ex.setSpiritualHealth(AvalonNovariPhaseData.spiritualHealth(this.getPhase()));
         }

         ex.markDirty();
      } catch (Throwable var7) {
      }
   }

   private void message(Component component) {
      if (this.level() instanceof ServerLevel level) {
         double var7 = 32400.0;

         for (ServerPlayer player : level.players()) {
            if (player.distanceToSqr(this) <= var7) {
               player.sendSystemMessage(component);
            }
         }
      }
   }

   public UUID getLockedTargetId() {
      return this.lockedTargetId;
   }

   public void setLockedTarget(UUID lockedTargetId) {
      this.lockedTargetId = lockedTargetId;
   }

   public int getAttackId() {
      return this.attackId;
   }

   public void setAttack(int attackId) {
      this.attackId = attackId;
      this.attackTicks = 0;
   }

   public void clearAttack() {
      this.attackId = 0;
      this.attackTicks = 0;
   }

   public int getAttackTicks() {
      return this.attackTicks;
   }

   public int getGlobalCooldown() {
      return this.globalCooldown;
   }

   public void setGlobalCooldown(int globalCooldown) {
      this.globalCooldown = Math.max(this.globalCooldown, globalCooldown);
   }

   public void setReflectTicks(int ticks, float multiplier) {
      this.reflectTicks = Math.max(this.reflectTicks, ticks);
      this.reflectMultiplier = Math.max(this.reflectMultiplier, multiplier);
   }

   public void setPhenomenonTheftTicks(int ticks) {
      this.phenomenonTheftTicks = Math.max(this.phenomenonTheftTicks, ticks);
   }

   public int nextForcedAttack() {
      int[] order = this.getPhase() <= 1
         ? new int[]{1, 2, 3, 11, 12, 13, 14, 9, 15, 16, 17, 18, 4, 5, 6, 8}
         : (this.getPhase() == 2 ? new int[]{10, 2, 3, 11, 12, 13, 14, 9, 15, 16, 17, 18, 4, 5, 6, 8} : new int[]{30, 31, 32, 33, 34, 35, 36, 37});

      for (int id : order) {
         int bit = AvalonNovariCombat.attackBit(id);
         if (bit != 0 && (this.forcedAttackMask & bit) == 0) {
            return id;
         }
      }

      this.forcedAttackMask = 0;
      return 0;
   }

   public void markForcedAttack(int id) {
      int bit = AvalonNovariCombat.attackBit(id);
      if (bit != 0) {
         this.forcedAttackMask |= bit;
      }
   }
}
