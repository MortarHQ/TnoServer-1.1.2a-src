package com.github.wal_bos.moreskills.world.entity;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.registry.particle.ModParticles;
import com.mojang.datafixers.util.Pair;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.magic.barrier.DisintegrationEntity;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.particle.TensuraParticleType;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
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
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MichaelManasEntity extends PathfinderMob {
   private static final List<Holder<MobEffect>> IMMUNE = buildImmuneList();
   private UUID masterId;
   private int pulseCd;
   private int beamCd;
   private int dashCd;
   private int masterGrace = 40;
   private int sealCd;
   private int lanceCd;
   private int purgeCd;
   private int cageCd;
   private int finalMoveCd;
   private int sunJudgementCd;
   private int capWindowTicks = 20;
   private float capDamageTaken = 0.0F;
   private int capImmuneTicks = 0;
   private int hardIFrameTicks = 0;
   private boolean finalMoveUsed = false;
   private boolean sunFinalUsed = false;
   private final List<UUID> disintegrationIds = new ArrayList<>();
   private int sunJudgementTicks = 0;
   private Vec3 sunJudgementCenter = Vec3.ZERO;
   private float sunJudgementRadius = 0.0F;
   private float sunJudgementDamage = 0.0F;
   private float sunJudgementSpirit = 0.0F;
   private final ServerBossEvent bossBar = new ServerBossEvent(
      Component.literal("MICHAEL").withStyle(new ChatFormatting[]{ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD}),
      BossBarColor.PURPLE,
      BossBarOverlay.NOTCHED_20
   );

   private static EntityConfigs.MichaelConfig config() {
      return EntityConfigs.ENTITY.michael;
   }

   public MichaelManasEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
      if ((Boolean)config().persistent.get()) {
         this.setPersistenceRequired();
      }

      this.noCulling = true;
      this.lookControl = new LookControl(this);
      this.moveControl = new FlyingMoveControl(this, (Integer)config().flyingMoveControlTurn.get(), true);
      this.setNoGravity((Boolean)config().noGravity.get());
      this.bossBar.setDarkenScreen((Boolean)config().bossBarDarkenScreen.get());
      this.bossBar.setPlayBossMusic((Boolean)config().bossBarPlayMusic.get());
      this.bossBar.setCreateWorldFog((Boolean)config().bossBarCreateWorldFog.get());
      this.xpReward = (Integer)config().xpReward.get();
      this.applyConfiguredAttributes();
   }

   private void applyConfiguredAttributes() {
      this.setBase(Attributes.MAX_HEALTH, (Double)config().maxHealth.get());
      this.setBase(Attributes.ARMOR, (Double)config().armor.get());
      this.setBase(Attributes.ARMOR_TOUGHNESS, (Double)config().armorToughness.get());
      this.setBase(Attributes.KNOCKBACK_RESISTANCE, (Double)config().knockbackResistance.get());
      this.setBase(Attributes.MOVEMENT_SPEED, (Double)config().movementSpeed.get());
      this.setBase(Attributes.FLYING_SPEED, (Double)config().flyingSpeed.get());
      this.setBase(Attributes.ATTACK_DAMAGE, (Double)config().attackDamage.get());
      this.setBase(Attributes.FOLLOW_RANGE, (Double)config().followRange.get());
      this.setHealth(this.getMaxHealth());
   }

   private void setBase(Holder<Attribute> attribute, double value) {
      AttributeInstance instance = this.getAttribute(attribute);
      if (instance != null) {
         instance.setBaseValue(value);
      }
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 8500.0)
         .add(Attributes.ARMOR, 155.0)
         .add(Attributes.ARMOR_TOUGHNESS, 520.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.MOVEMENT_SPEED, 0.64)
         .add(Attributes.FLYING_SPEED, 1.02)
         .add(Attributes.ATTACK_DAMAGE, 175.0)
         .add(Attributes.FOLLOW_RANGE, 128.0);
   }

   private static List<Holder<MobEffect>> buildImmuneList() {
      List<Holder<MobEffect>> list = new ArrayList<>();
      addImmune(list, TensuraMobEffects.CHILL);
      addImmune(list, TensuraMobEffects.BLACK_BURN);
      addImmune(list, TensuraMobEffects.MAGIC_INTERFERENCE);
      addImmune(list, TensuraMobEffects.SPATIAL_BLOCKADE);
      addImmune(list, TensuraMobEffects.TRUE_BLINDNESS);
      addImmune(list, TensuraMobEffects.ANTI_SKILL);
      addImmune(list, TensuraMobEffects.MAGICULE_POISON);
      addImmune(list, TensuraMobEffects.DISINTEGRATING);
      addImmune(list, TensuraMobEffects.SOUL_DRAIN);
      addImmune(list, TensuraMobEffects.FEAR);
      addImmune(list, TensuraMobEffects.INFINITE_IMPRISONMENT);
      addImmune(list, TensuraMobEffects.INFECTION);
      addImmune(list, TensuraMobEffects.ENERGY_BLOCKADE);
      addImmune(list, TensuraMobEffects.SILENCE);
      addImmune(list, TensuraMobEffects.REST);
      addImmune(list, TensuraMobEffects.SLEEP);
      addImmune(list, TensuraMobEffects.DROWSINESS);
      addImmune(list, TensuraMobEffects.CURSE);
      addImmune(list, TensuraMobEffects.SEVERANCE_BLADE);
      addImmune(list, TensuraMobEffects.MIND_CONTROL);
      addImmune(list, TensuraMobEffects.MOVEMENT_INTERFERENCE);
      addImmune(list, TensuraMobEffects.OPPRESSION);
      addImmune(list, TensuraMobEffects.PARALYSIS);
      return list;
   }

   private static void addImmune(List<Holder<MobEffect>> out, RegistrySupplier<MobEffect> effect) {
      try {
         out.add(TensuraMobEffects.getReference(effect));
      } catch (Throwable var3) {
      }
   }

   private static ManasSkillInstance noSkill() {
      return null;
   }

   private void showCircle(MagicCircleVariant variant, float radius, int life, float distance, Vec3 offset, boolean spinning) {
      MagicCircle.castMagicCircle(radius, life, variant, spinning, this, new CompoundTag(), distance, offset, noSkill(), 0, Pair.of(0.0, 0.0));
   }

   private void showCircle(MagicCircleVariant variant, float radius, int life, float distance, Vec3 offset) {
      this.showCircle(variant, radius, life, distance, offset, false);
   }

   private void showCircleAt(Vec3 pos, MagicCircleVariant variant, float radius, int life, boolean spinning) {
      MagicCircle.castMagicCircle(radius, life, pos, variant, spinning, this, new CompoundTag(), noSkill(), 0, Pair.of(0.0, 0.0));
   }

   public void setMasterEntity(Entity master) {
      this.masterId = master == null ? null : master.getUUID();
   }

   public void setMaster(FeldwayEntity master) {
      this.setMasterEntity(master);
   }

   private LivingEntity getMasterLiving(ServerLevel sl) {
      if (this.masterId == null) {
         return null;
      } else {
         return sl.getEntity(this.masterId) instanceof LivingEntity living && living.isAlive() ? living : null;
      }
   }

   private FeldwayEntity getFeldwayMaster(ServerLevel sl) {
      return this.getMasterLiving(sl) instanceof FeldwayEntity feldway ? feldway : null;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new MichaelManasEntity.MichaelCombatGoal(this));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, ((Double)config().lookAtPlayerRange.get()).floatValue()));
      this.targetSelector
         .addGoal(
            1, new NearestAttackableTargetGoal(this, Player.class, true, p -> p instanceof Player player && !player.isCreative() && !player.isSpectator())
         );
      this.targetSelector
         .addGoal(
            2,
            new NearestAttackableTargetGoal(
               this,
               LivingEntity.class,
               10,
               true,
               false,
               e -> {
                  if (!(e instanceof LivingEntity le)) {
                     return false;
                  } else if (le.isAlive() && le != this) {
                     if (le instanceof Player p && (p.isCreative() || p.isSpectator())) {
                        return false;
                     } else {
                        return !(le instanceof FeldwayEntity) && !(le instanceof MichaelManasEntity) && !(le instanceof FeldwayAngelEntity)
                           ? !this.isAlliedTo(le) && !le.isAlliedTo(this)
                           : false;
                     }
                  } else {
                     return false;
                  }
               }
            )
         );
   }

   public boolean isAlliedTo(Entity e) {
      if (super.isAlliedTo(e)) {
         return true;
      }

      if (e == null) {
         return false;
      }

      if (this.masterId != null && this.masterId.equals(e.getUUID())) {
         return true;
      }

      if (e instanceof FeldwayAngelEntity) {
         return true;
      }

      if (e instanceof FeldwayEntity) {
         return true;
      }

      if (e instanceof MichaelManasEntity) {
         return true;
      }

      if (this.level() instanceof ServerLevel sl) {
         LivingEntity master = this.getMasterLiving(sl);
         if (master != null) {
            if (e.getUUID().equals(master.getUUID())) {
               return true;
            }

            if (master.isAlliedTo(e)) {
               return true;
            }

            if (e.isAlliedTo(master)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isImmune(Holder<MobEffect> effect) {
      if (effect == null) {
         return false;
      }

      for (Holder<MobEffect> e : IMMUNE) {
         if (e.equals(effect)) {
            return true;
         }
      }

      return false;
   }

   public boolean canBeAffected(MobEffectInstance inst) {
      return inst != null && !this.isImmune(inst.getEffect());
   }

   public boolean addEffect(MobEffectInstance inst, Entity source) {
      if (inst == null) {
         return false;
      } else {
         return this.isImmune(inst.getEffect()) ? false : super.addEffect(inst, source);
      }
   }

   private void cleanseImmuneEffects() {
      for (Holder<MobEffect> e : IMMUNE) {
         try {
            if (this.hasEffect(e)) {
               this.removeEffect(e);
            }
         } catch (Throwable var4) {
         }
      }
   }

   private void sustainSelf() {
      try {
         IExistence ex = TensuraStorages.getExistenceFrom(this);
         ex.setAura(Math.max(ex.getAura(), (Double)config().enforcedAura.get()));
         ex.setMagicule(Math.max(ex.getMagicule(), (Double)config().enforcedMagicule.get()));
         ex.setSpiritualHealth(Math.max(ex.getSpiritualHealth(), (Double)config().enforcedSpiritualHealth.get()));
         ex.markDirty();
      } catch (Throwable var2) {
      }

      if (this.tickCount % Math.max(1, (Integer)config().healIntervalTicks.get()) == 0
         && this.getHealth() < this.getMaxHealth() * ((Double)config().healBelowHealthPercent.get()).floatValue()) {
         this.heal(((Double)config().healAmount.get()).floatValue());
      }
   }

   public boolean removeWhenFarAway(double dist) {
      return false;
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossBar.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossBar.removePlayer(player);
   }

   private float damageCapPerHit() {
      return (float)(this.getMaxHealth() * (Double)config().damageCapPerHitPercent.get());
   }

   private float damageCapPerSecond() {
      return (float)(this.getMaxHealth() * (Double)config().damageCapPerSecondPercent.get());
   }

   public boolean hurt(DamageSource source, float amount) {
      Entity a = source.getEntity();
      Entity d = source.getDirectEntity();
      if (this.level().isClientSide()) {
         return false;
      }

      if (source == null) {
         return false;
      }

      if (this.isRemoved()) {
         return false;
      }

      if (!(amount <= 0.0F) && !Float.isNaN(amount) && !Float.isInfinite(amount)) {
         if (a == null || !this.isAlliedTo(a) && !a.isAlliedTo(this)) {
            if (d == null || !this.isAlliedTo(d) && !d.isAlliedTo(this)) {
               if (this.capImmuneTicks > 0) {
                  return false;
               }

               if (this.hardIFrameTicks > 0) {
                  return false;
               }

               float before = this.getHealth();
               amount = Math.min(amount, this.damageCapPerHit());
               amount *= ((Double)config().incomingDamageMultiplier.get()).floatValue();
               float secCap = this.damageCapPerSecond();
               float remaining = secCap - this.capDamageTaken;
               if (remaining <= 0.0F) {
                  this.capImmuneTicks = (Integer)config().capImmuneTicks.get();
                  this.hardIFrameTicks = (Integer)config().hardIFrameTicks.get();
                  this.invulnerableTime = Math.max(this.invulnerableTime, (Integer)config().hardIFrameTicks.get());
                  return false;
               }

               amount = Math.min(amount, remaining);
               if (amount <= 0.0F) {
                  return false;
               }

               boolean ok = super.hurt(source, amount);
               if (!ok) {
                  return false;
               }

               float actual = Math.max(0.0F, before - this.getHealth());
               this.capDamageTaken += actual;
               if (this.capDamageTaken >= secCap) {
                  this.capImmuneTicks = (Integer)config().capImmuneTicks.get();
                  this.hardIFrameTicks = (Integer)config().hardIFrameTicks.get();
                  this.invulnerableTime = Math.max(this.invulnerableTime, (Integer)config().hardIFrameTicks.get());
               }

               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         this.setNoGravity((Boolean)config().noGravity.get());
         this.cleanseImmuneEffects();
         this.sustainSelf();
         this.cleanupDisintegrations();
         if (this.capWindowTicks > 0) {
            this.capWindowTicks--;
         }

         if (this.capWindowTicks <= 0) {
            this.capWindowTicks = Math.max(1, (Integer)config().capWindowTicks.get());
            this.capDamageTaken = 0.0F;
         }

         if (this.capImmuneTicks > 0) {
            this.capImmuneTicks--;
         }

         if (this.hardIFrameTicks > 0) {
            this.hardIFrameTicks--;
         }

         this.bossBar.setProgress(Mth.clamp(this.getHealth() / this.getMaxHealth(), 0.0F, 1.0F));
         if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
         } else {
            this.bossBar.setName(Component.literal("MICHAEL").withStyle(new ChatFormatting[]{ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD}));
         }

         if (this.pulseCd > 0) {
            this.pulseCd--;
         }

         if (this.beamCd > 0) {
            this.beamCd--;
         }

         if (this.dashCd > 0) {
            this.dashCd--;
         }

         if (this.sealCd > 0) {
            this.sealCd--;
         }

         if (this.lanceCd > 0) {
            this.lanceCd--;
         }

         if (this.purgeCd > 0) {
            this.purgeCd--;
         }

         if (this.cageCd > 0) {
            this.cageCd--;
         }

         if (this.finalMoveCd > 0) {
            this.finalMoveCd--;
         }

         if (this.sunJudgementCd > 0) {
            this.sunJudgementCd--;
         }

         if (this.level() instanceof ServerLevel sl) {
            if (this.masterId == null) {
               CompoundTag pd = this.getPersistentData();
               if (pd.hasUUID("MichaelMaster")) {
                  this.masterId = pd.getUUID("MichaelMaster");
               }
            }

            LivingEntity master = this.getMasterLiving(sl);
            if (master != null) {
               if (this.getTarget() == null || !this.getTarget().isAlive()) {
                  LivingEntity inheritedTarget = null;
                  if (master instanceof Mob mobMaster) {
                     if (mobMaster.getTarget() != null && mobMaster.getTarget().isAlive()) {
                        inheritedTarget = mobMaster.getTarget();
                     }
                  } else if (master instanceof Player playerMaster) {
                     LivingEntity lastHurt = playerMaster.getLastHurtMob();
                     LivingEntity lastBy = playerMaster.getLastHurtByMob();
                     if (lastHurt != null && lastHurt.isAlive()) {
                        inheritedTarget = lastHurt;
                     } else if (lastBy != null && lastBy.isAlive()) {
                        inheritedTarget = lastBy;
                     }
                  }

                  if (inheritedTarget != null && !this.isAlliedTo(inheritedTarget)) {
                     this.setTarget(inheritedTarget);
                  }
               }

               LivingEntity t = this.getTarget();
               if (t != null && t.isAlive() && (this.isAlliedTo(t) || t.isAlliedTo(this) || t instanceof FeldwayEntity || t instanceof MichaelManasEntity)) {
                  this.setTarget(null);
                  t = null;
               }

               if (t != null) {
                  this.getLookControl().setLookAt(t, 85.0F, 85.0F);
               }

               Vec3 follow = master.position().add(0.0, (Double)config().followAboveMasterY.get(), 0.0);
               if (t != null && t.isAlive()) {
                  follow = follow.lerp(t.position().add(0.0, (Double)config().followAboveTargetY.get(), 0.0), (Double)config().followTargetLerp.get());
               }

               double maxY = this.level().getMaxBuildHeight() - (Integer)config().topBuildPadding.get();
               follow = new Vec3(follow.x, Mth.clamp(follow.y, master.getY() + (Double)config().minimumAboveMasterY.get(), maxY), follow.z);
               this.getMoveControl().setWantedPosition(follow.x, follow.y, follow.z, (Double)config().followMoveSpeed.get());
               this.passiveAura(sl);
               if (this.sunJudgementTicks > 0) {
                  this.tickSunJudgement(sl);
               }

               if (t != null && t.isAlive()) {
                  double dist = this.distanceTo(t);
                  if (!this.finalMoveUsed
                     && this.finalMoveCd <= 0
                     && this.getHealth() <= this.getMaxHealth() * ((Double)config().apocalypseHealthPercent.get()).floatValue()
                     && dist < (Double)config().apocalypseRange.get()) {
                     this.finalMoveCd = (Integer)config().finalMoveLockoutTicks.get();
                     this.finalMoveUsed = true;
                     this.apocalypseDisintegrationRing(sl, t);
                     return;
                  }

                  if (!this.sunFinalUsed
                     && this.sunJudgementCd <= 0
                     && this.getHealth() <= this.getMaxHealth() * ((Double)config().sunJudgementHealthPercent.get()).floatValue()
                     && dist < (Double)config().sunJudgementCastRange.get()) {
                     this.sunFinalUsed = true;
                     this.sunJudgementCd = (Integer)config().sunJudgementLockoutTicks.get();
                     this.beginSunJudgement(sl, t);
                     return;
                  }

                  if (dist > (Double)config().dashTriggerDistance.get() && this.dashCd <= 0) {
                     this.dashCd = (Integer)config().dashCooldownTicks.get();
                     this.teleportDash(sl, t);
                  }

                  if (this.purgeCd <= 0 && dist < (Double)config().purgeRange.get() && sl.random.nextFloat() < (Double)config().purgeChance.get()) {
                     this.purgeCd = (Integer)config().purgeCooldownTicks.get();
                     this.absoluteCommandPurge(sl);
                  }

                  if (this.sealCd <= 0 && dist < (Double)config().sealRange.get() && sl.random.nextFloat() < (Double)config().sealChance.get()) {
                     this.sealCd = (Integer)config().sealCooldownTicks.get();
                     this.authoritySeal(sl, t);
                  }

                  if (this.lanceCd <= 0 && dist < (Double)config().lanceRange.get() && sl.random.nextFloat() < (Double)config().lanceChance.get()) {
                     this.lanceCd = (Integer)config().lanceCooldownTicks.get();
                     this.holyLanceBarrage(sl, t);
                  }

                  if (this.pulseCd <= 0) {
                     this.pulseCd = (Integer)config().pulseCooldownTicks.get();
                     this.dominionPulse(sl);
                  }

                  if (this.beamCd <= 0 && dist < (Double)config().beamRange.get()) {
                     this.beamCd = (Integer)config().beamCooldownTicks.get();
                     this.judgementBeam(sl, t);
                  }

                  if (this.cageCd <= 0 && dist < (Double)config().cageRange.get() && sl.random.nextFloat() < (Double)config().cageChance.get()) {
                     this.cageCd = (Integer)config().cageCooldownTicks.get();
                     this.solarCage(sl, t);
                  }
               }
            }
         }
      }
   }

   private DamageSource holyDamage(float barrierBypass, float resistanceBypass) {
      DamageSource src = TensuraDamageTypes.getEntityDamageSource(this.level(), TensuraDamageTypes.HOLY_DAMAGE, this);
      TensuraDamageSource td = (TensuraDamageSource)src;
      if (resistanceBypass > 0.0F) {
         src = td.tensura$setResistanceBypassLevel(resistanceBypass);
      }

      if (barrierBypass > 0.0F) {
         src = ((TensuraDamageSource)src).tensura$setBarrierBypassLevel(barrierBypass);
      }

      return src;
   }

   private DamageSource disintegrationDamage(float barrierBypass, float resistanceBypass) {
      DamageSource src = TensuraDamageTypes.getEntityDamageSource(this.level(), TensuraDamageTypes.DISINTEGRATION, this);
      TensuraDamageSource td = (TensuraDamageSource)src;
      if (resistanceBypass > 0.0F) {
         src = td.tensura$setResistanceBypassLevel(resistanceBypass);
      }

      if (barrierBypass > 0.0F) {
         src = ((TensuraDamageSource)src).tensura$setBarrierBypassLevel(barrierBypass);
      }

      return src;
   }

   private void resetBadEffects(LivingEntity target) {
      try {
         TensuraStorages.resetEffect(target);
      } catch (Throwable var3) {
      }
   }

   private void passiveAura(ServerLevel sl) {
      double x = this.getX();
      double y = this.getY() + this.getBbHeight() * 0.72;
      double z = this.getZ();
      if ((this.tickCount & 1) == 0) {
         sl.sendParticles(ParticleTypes.END_ROD, x, y, z, 2, 0.18, 0.18, 0.18, 0.01);
      }

      if (this.tickCount % 4 == 0) {
         sl.sendParticles(ParticleTypes.ENCHANT, x, y, z, 4, 0.25, 0.12, 0.25, 0.03);
      }

      if (this.tickCount % 8 == 0) {
         sl.sendParticles(ParticleTypes.REVERSE_PORTAL, x, y, z, 2, 0.15, 0.1, 0.15, 0.005);
      }
   }

   private void teleportDash(ServerLevel sl, LivingEntity t) {
      Vec3 to = t.position().add(0.0, 1.0, 0.0);
      Vec3 dir = to.subtract(this.position());
      double len = Math.max(1.0, dir.length());
      Vec3 n = dir.scale(1.0 / len);
      Vec3 dashTo = to.subtract(n.scale((Double)config().dashStopDistance.get()));
      this.showCircle(
         MagicCircleVariant.LIGHT, ((Double)config().dashCircleRadius.get()).floatValue(), (Integer)config().dashCircleLifeTicks.get(), 0.0F, Vec3.ZERO, true
      );
      this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 2.2F, 1.05F);
      this.teleportTo(dashTo.x, dashTo.y + (Double)config().dashTeleportYOffset.get(), dashTo.z);
      sl.sendParticles(ParticleTypes.FLASH, dashTo.x, dashTo.y + 1.2, dashTo.z, 3, 0.4, 0.4, 0.4, 0.0);
      sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), dashTo.x, dashTo.y + 1.2, dashTo.z, 16, 0.55, 0.55, 0.55, 0.0);
      sl.sendParticles((TensuraParticleType)TensuraParticleTypes.SOLAR_FLASH.get(), dashTo.x, dashTo.y + 1.2, dashTo.z, 8, 0.45, 0.45, 0.45, 0.0);
   }

   private void absoluteCommandPurge(ServerLevel sl) {
      this.showCircle(
         MagicCircleVariant.LIGHT, ((Double)config().purgeCircleRadius.get()).floatValue(), (Integer)config().purgeCircleLifeTicks.get(), 0.0F, Vec3.ZERO, true
      );
      this.level().playSound(null, this.blockPosition(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.HOSTILE, 2.8F, 0.55F);
      sl.sendParticles(ParticleTypes.FLASH, this.getX(), this.getY() + 1.0, this.getZ(), 14, 1.0, 1.0, 1.0, 0.0);
      sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), this.getX(), this.getY() + 1.0, this.getZ(), 120, 2.6, 1.0, 2.6, 0.0);
      sl.sendParticles((TensuraParticleType)TensuraParticleTypes.SOLAR_FLASH.get(), this.getX(), this.getY() + 1.0, this.getZ(), 60, 2.4, 0.8, 2.4, 0.0);
      AABB box = this.getBoundingBox().inflate((Double)config().purgeRadius.get());

      for (LivingEntity e : sl.getEntitiesOfClass(LivingEntity.class, box, x -> x != null && x.isAlive())) {
         if (e != this
            && !this.isAlliedTo(e)
            && !e.isAlliedTo(this)
            && !(e instanceof FeldwayEntity)
            && !(e instanceof MichaelManasEntity)
            && !(e instanceof FeldwayAngelEntity)
            && !(e instanceof Player p && (p.isCreative() || p.isSpectator()))) {
            this.resetBadEffects(e);
            e.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL),
                  (Integer)config().purgeAntiSkillTicks.get(),
                  (Integer)config().purgeAntiSkillAmplifier.get(),
                  false,
                  false,
                  true
               )
            );

            try {
               e.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.SILENCE),
                     (Integer)config().purgeSilenceTicks.get(),
                     (Integer)config().purgeSilenceAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var9) {
            }

            try {
               e.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION),
                     (Integer)config().purgeOppressionTicks.get(),
                     (Integer)config().purgeOppressionAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var8) {
            }

            try {
               e.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
                     (Integer)config().purgeMovementTicks.get(),
                     (Integer)config().purgeMovementAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var7) {
            }

            try {
               e.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE),
                     (Integer)config().purgeSpatialTicks.get(),
                     (Integer)config().purgeSpatialAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var6) {
            }

            DamageSource src = this.holyDamage(
               ((Double)config().purgeBarrierBypass.get()).floatValue(), ((Double)config().purgeResistanceBypass.get()).floatValue()
            );
            e.hurt(src, ((Double)config().purgeDamage.get()).floatValue());
            TensuraDamageHelper.directSpiritualHurt(e, this, src, ((Double)config().purgeSpiritualDamage.get()).floatValue());
         }
      }
   }

   private void authoritySeal(ServerLevel sl, LivingEntity t) {
      if (t != null && t.isAlive()) {
         if (!this.isAlliedTo(t) && !t.isAlliedTo(this)) {
            Vec3 pos = t.position().add(0.0, t.getBbHeight() * 0.6, 0.0);
            this.showCircleAt(
               pos, MagicCircleVariant.LIGHT, ((Double)config().sealCircleRadius.get()).floatValue(), (Integer)config().sealCircleLifeTicks.get(), true
            );
            sl.playSound(null, t.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 1.8F, 0.8F);
            sl.sendParticles(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 6, 0.35, 0.35, 0.35, 0.0);
            sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), pos.x, pos.y, pos.z, 60, 1.0, 0.7, 1.0, 0.0);
            sl.sendParticles((TensuraParticleType)TensuraParticleTypes.SOLAR_FLASH.get(), pos.x, pos.y, pos.z, 18, 0.9, 0.6, 0.9, 0.0);
            this.resetBadEffects(t);
            t.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL),
                  (Integer)config().sealAntiSkillTicks.get(),
                  (Integer)config().sealAntiSkillAmplifier.get(),
                  false,
                  false,
                  true
               )
            );

            try {
               t.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.SILENCE),
                     (Integer)config().sealSilenceTicks.get(),
                     (Integer)config().sealSilenceAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var8) {
            }

            try {
               t.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION),
                     (Integer)config().sealOppressionTicks.get(),
                     (Integer)config().sealOppressionAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var7) {
            }

            try {
               t.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
                     (Integer)config().sealMovementTicks.get(),
                     (Integer)config().sealMovementAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var6) {
            }

            try {
               t.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE),
                     (Integer)config().sealSpatialTicks.get(),
                     (Integer)config().sealSpatialAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var5) {
            }

            DamageSource src = this.holyDamage(
               ((Double)config().sealBarrierBypass.get()).floatValue(), ((Double)config().sealResistanceBypass.get()).floatValue()
            );
            t.hurt(src, ((Double)config().sealDamage.get()).floatValue());
            TensuraDamageHelper.directSpiritualHurt(t, this, src, ((Double)config().sealSpiritualDamage.get()).floatValue());
         }
      }
   }

   private void holyLanceBarrage(ServerLevel sl, LivingEntity t) {
      if (t != null && t.isAlive()) {
         if (!this.isAlliedTo(t) && !t.isAlliedTo(this)) {
            this.showCircle(
               MagicCircleVariant.LIGHT,
               ((Double)config().lanceCircleRadius.get()).floatValue(),
               (Integer)config().lanceCircleLifeTicks.get(),
               ((Double)config().lanceCircleDistance.get()).floatValue(),
               Vec3.ZERO,
               true
            );
            this.level().playSound(null, this.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 2.2F, 0.78F);
            int beams = Math.max(1, (Integer)config().lanceBeamCount.get());
            Vec3 targetBase = t.position().add(0.0, t.getBbHeight() * 0.55, 0.0);

            for (int b = 0; b < beams; b++) {
               double ang = (Math.PI * 2) / beams * b;
               Vec3 from = this.position().add(Math.cos(ang) * 1.6, this.getBbHeight() * 0.78 + 0.2 * b, Math.sin(ang) * 1.6);
               Vec3 to = targetBase.add(Math.cos(ang) * 0.8, 0.15 * b, Math.sin(ang) * 0.8);
               Vec3 dir = to.subtract(from);
               double len = Math.max(1.0, dir.length());
               Vec3 n = dir.scale(1.0 / len);

               for (int i = 0; i <= (int)len; i++) {
                  Vec3 p = from.add(n.scale(i));
                  sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0);
                  if ((i & 1) == 0) {
                     sl.sendParticles(ParticleTypes.END_ROD, p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0);
                  }

                  if (i % 3 == 0) {
                     sl.sendParticles((TensuraParticleType)TensuraParticleTypes.SOLAR_FLASH.get(), p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0);
                  }
               }
            }

            sl.sendParticles(ParticleTypes.FLASH, targetBase.x, targetBase.y, targetBase.z, 2, 0.2, 0.2, 0.2, 0.0);
            DamageSource source = this.holyDamage(
               ((Double)config().lanceBarrierBypass.get()).floatValue(), ((Double)config().lanceResistanceBypass.get()).floatValue()
            );
            t.hurt(source, ((Double)config().lanceDamage.get()).floatValue());
            TensuraDamageHelper.directSpiritualHurt(t, this, source, ((Double)config().lanceSpiritualDamage.get()).floatValue());
         }
      }
   }

   private void dominionPulse(ServerLevel sl) {
      this.showCircle(
         MagicCircleVariant.LIGHT, ((Double)config().pulseCircleRadius.get()).floatValue(), (Integer)config().pulseCircleLifeTicks.get(), 0.0F, Vec3.ZERO, true
      );
      this.level().playSound(null, this.blockPosition(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.HOSTILE, 2.4F, 0.65F);
      sl.sendParticles(ParticleTypes.FLASH, this.getX(), this.getY() + 1.1, this.getZ(), 10, 0.9, 0.9, 0.9, 0.0);
      sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), this.getX(), this.getY() + 1.1, this.getZ(), 120, 2.2, 0.9, 2.2, 0.0);
      sl.sendParticles((TensuraParticleType)TensuraParticleTypes.SOLAR_FLASH.get(), this.getX(), this.getY() + 1.1, this.getZ(), 50, 2.0, 0.8, 2.0, 0.0);
      AABB box = this.getBoundingBox().inflate((Double)config().pulseRadius.get());

      for (LivingEntity e : sl.getEntitiesOfClass(LivingEntity.class, box, x -> x != null && x.isAlive())) {
         if (e != this
            && !this.isAlliedTo(e)
            && !e.isAlliedTo(this)
            && !(e instanceof FeldwayEntity)
            && !(e instanceof MichaelManasEntity)
            && !(e instanceof FeldwayAngelEntity)
            && !(e instanceof Player p && (p.isCreative() || p.isSpectator()))) {
            this.resetBadEffects(e);
            e.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL),
                  (Integer)config().pulseAntiSkillTicks.get(),
                  (Integer)config().pulseAntiSkillAmplifier.get(),
                  false,
                  false,
                  true
               )
            );

            try {
               e.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.SILENCE),
                     (Integer)config().pulseSilenceTicks.get(),
                     (Integer)config().pulseSilenceAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var8) {
            }

            try {
               e.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION),
                     (Integer)config().pulseOppressionTicks.get(),
                     (Integer)config().pulseOppressionAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var7) {
            }

            try {
               e.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
                     (Integer)config().pulseMovementTicks.get(),
                     (Integer)config().pulseMovementAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var6) {
            }

            DamageSource src = this.holyDamage(
               ((Double)config().pulseBarrierBypass.get()).floatValue(), ((Double)config().pulseResistanceBypass.get()).floatValue()
            );
            e.hurt(src, ((Double)config().pulseDamage.get()).floatValue());
            TensuraDamageHelper.directSpiritualHurt(e, this, src, ((Double)config().pulseSpiritualDamage.get()).floatValue());
         }
      }
   }

   private void judgementBeam(ServerLevel sl, LivingEntity t) {
      if (t != null && t.isAlive()) {
         if (!this.isAlliedTo(t) && !t.isAlliedTo(this)) {
            Vec3 pos = t.position().add(0.0, t.getBbHeight() * 0.55, 0.0);
            this.showCircleAt(
               pos.add(0.0, (Double)config().beamCircleYOffset.get(), 0.0),
               MagicCircleVariant.LIGHT,
               ((Double)config().beamCircleRadius.get()).floatValue(),
               (Integer)config().beamCircleLifeTicks.get(),
               true
            );
            sl.playSound(null, t.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 2.2F, 0.75F);
            sl.sendParticles(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 6, 0.25, 0.25, 0.25, 0.0);
            sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), pos.x, pos.y, pos.z, 40, 0.7, 0.7, 0.7, 0.0);
            sl.sendParticles((TensuraParticleType)TensuraParticleTypes.SOLAR_FLASH.get(), pos.x, pos.y, pos.z, 12, 0.6, 0.6, 0.6, 0.0);

            try {
               t.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
                     (Integer)config().beamMovementTicks.get(),
                     (Integer)config().beamMovementAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var6) {
            }

            try {
               t.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION),
                     (Integer)config().beamOppressionTicks.get(),
                     (Integer)config().beamOppressionAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var5) {
            }

            DamageSource source = this.holyDamage(
               ((Double)config().beamBarrierBypass.get()).floatValue(), ((Double)config().beamResistanceBypass.get()).floatValue()
            );
            t.hurt(source, ((Double)config().beamDamage.get()).floatValue());
            TensuraDamageHelper.directSpiritualHurt(t, this, source, ((Double)config().beamSpiritualDamage.get()).floatValue());
         }
      }
   }

   private void solarCage(ServerLevel sl, LivingEntity t) {
      if (t != null && t.isAlive()) {
         if (!this.isAlliedTo(t) && !t.isAlliedTo(this)) {
            Vec3 c = t.position().add(0.0, t.getBbHeight() * 0.5, 0.0);
            float r = ((Double)config().cageVisualRadius.get()).floatValue();
            this.level().playSound(null, t.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.HOSTILE, 2.2F, 0.7F);

            for (int ring = 0; ring < Math.max(1, (Integer)config().cageVisualRings.get()); ring++) {
               double y = c.y - 1.2 + ring * 1.2;

               for (int deg = 0; deg < 360; deg += Math.max(1, (Integer)config().cageVisualStepDegrees.get())) {
                  double a = Math.toRadians(deg + ring * 8.0);
                  double px = c.x + Math.cos(a) * r;
                  double pz = c.z + Math.sin(a) * r;
                  sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), px, y, pz, 1, 0.0, 0.0, 0.0, 0.0);
                  if (deg % 24 == 0) {
                     sl.sendParticles((TensuraParticleType)TensuraParticleTypes.SOLAR_FLASH.get(), px, y, pz, 1, 0.0, 0.0, 0.0, 0.0);
                  }
               }
            }

            try {
               t.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE),
                     (Integer)config().cageSpatialTicks.get(),
                     (Integer)config().cageSpatialAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var17) {
            }

            try {
               t.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
                     (Integer)config().cageMovementTicks.get(),
                     (Integer)config().cageMovementAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var16) {
            }

            try {
               t.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL),
                     (Integer)config().cageAntiSkillTicks.get(),
                     (Integer)config().cageAntiSkillAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var15) {
            }

            DamageSource src = this.holyDamage(
               ((Double)config().cageBarrierBypass.get()).floatValue(), ((Double)config().cageResistanceBypass.get()).floatValue()
            );
            t.hurt(src, ((Double)config().cageDamage.get()).floatValue());
            TensuraDamageHelper.directSpiritualHurt(t, this, src, ((Double)config().cageSpiritualDamage.get()).floatValue());
         }
      }
   }

   private void apocalypseDisintegrationRing(ServerLevel sl, LivingEntity target) {
      if (target != null && target.isAlive()) {
         if (!this.isAlliedTo(target) && !target.isAlliedTo(this)) {
            Vec3 center = target.position();
            int count = (Integer)config().apocalypseMinPillars.get()
               + sl.random.nextInt(Math.max(1, (Integer)config().apocalypseMaxPillars.get() - (Integer)config().apocalypseMinPillars.get() + 1));
            double ringRadius = (Double)config().apocalypseRingRadius.get();
            this.showCircle(
               MagicCircleVariant.LIGHT,
               ((Double)config().apocalypseCircleRadius.get()).floatValue(),
               (Integer)config().apocalypseCircleLifeTicks.get(),
               0.0F,
               Vec3.ZERO,
               true
            );
            this.level().playSound(null, this.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.HOSTILE, 3.0F, 0.55F);
            this.level().playSound(null, this.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 2.8F, 0.72F);
            sl.sendParticles((SimpleParticleType)ModParticles.SUN.get(), center.x, center.y + 1.0, center.z, 8, 0.0, 0.0, 0.0, 0.0);
            sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), center.x, center.y + 1.0, center.z, 110, 3.2, 1.0, 3.2, 0.0);
            sl.sendParticles((TensuraParticleType)TensuraParticleTypes.SOLAR_FLASH.get(), center.x, center.y + 1.0, center.z, 60, 2.8, 0.9, 2.8, 0.0);

            for (int i = 0; i < count; i++) {
               double ang = (Math.PI * 2) / count * i;
               double px = center.x + Math.cos(ang) * ringRadius;
               double pz = center.z + Math.sin(ang) * ringRadius;
               Vec3 pos = new Vec3(px, center.y, pz);
               this.spawnMichaelDisintegration(
                  sl,
                  pos,
                  ((Double)config().apocalypsePillarSize.get()).floatValue(),
                  ((Double)config().apocalypsePillarHeight.get()).floatValue(),
                  (Integer)config().apocalypsePillarLifeTicks.get(),
                  ((Double)config().apocalypsePillarDamage.get()).floatValue()
               );
               sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), px, center.y + 0.5, pz, 14, 0.55, 0.8, 0.55, 0.0);
            }

            this.spawnMichaelDisintegration(
               sl,
               center,
               ((Double)config().apocalypseCenterSize.get()).floatValue(),
               ((Double)config().apocalypseCenterHeight.get()).floatValue(),
               (Integer)config().apocalypsePillarLifeTicks.get(),
               ((Double)config().apocalypseCenterDamage.get()).floatValue()
            );

            try {
               target.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
                     (Integer)config().apocalypseTargetMovementTicks.get(),
                     (Integer)config().apocalypseTargetMovementAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var16) {
            }

            try {
               target.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE),
                     (Integer)config().apocalypseTargetSpatialTicks.get(),
                     (Integer)config().apocalypseTargetSpatialAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            } catch (Throwable var15) {
            }

            target.setDeltaMovement(Vec3.ZERO);
            target.hurtMarked = true;
         }
      }
   }

   private void beginSunJudgement(ServerLevel sl, LivingEntity target) {
      if (target != null && target.isAlive()) {
         if (!this.isAlliedTo(target) && !target.isAlliedTo(this)) {
            this.sunJudgementCenter = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
            this.sunJudgementRadius = ((Double)config().sunJudgementRadius.get()).floatValue();
            this.sunJudgementDamage = ((Double)config().sunJudgementDamage.get()).floatValue();
            this.sunJudgementSpirit = ((Double)config().sunJudgementSpiritualDamage.get()).floatValue();
            this.sunJudgementTicks = (Integer)config().sunJudgementDurationTicks.get();
            this.showCircleAt(
               this.sunJudgementCenter,
               MagicCircleVariant.LIGHT,
               ((Double)config().sunJudgementCircleRadius.get()).floatValue(),
               (Integer)config().sunJudgementCircleLifeTicks.get(),
               true
            );
            sl.playSound(null, this.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.HOSTILE, 3.4F, 0.55F);
            sl.playSound(null, this.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.HOSTILE, 3.2F, 0.72F);
         }
      }
   }

   private void tickSunJudgement(ServerLevel sl) {
      if (this.sunJudgementTicks > 0) {
         this.sunJudgementTicks--;
         Vec3 center = this.sunJudgementCenter;
         double sunY = center.y + (Double)config().sunJudgementSunYOffset.get();
         sl.sendParticles((SimpleParticleType)ModParticles.SUN.get(), center.x, sunY, center.z, 1, 0.0, 0.0, 0.0, 0.0);
         if (this.sunJudgementTicks % 2 == 0) {
            sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), center.x, sunY, center.z, 10, 1.2, 0.8, 1.2, 0.0);
            sl.sendParticles((TensuraParticleType)TensuraParticleTypes.SOLAR_FLASH.get(), center.x, sunY, center.z, 5, 1.0, 0.7, 1.0, 0.0);
         }

         int rays = (Integer)config().sunJudgementInitialRays.get();
         if (this.sunJudgementTicks < (Integer)config().sunJudgementSecondStageTicks.get()) {
            rays = (Integer)config().sunJudgementSecondStageRays.get();
         }

         if (this.sunJudgementTicks < (Integer)config().sunJudgementFinalStageTicks.get()) {
            rays = (Integer)config().sunJudgementFinalStageRays.get();
         }

         for (int i = 0; i < rays; i++) {
            double ang = sl.random.nextDouble() * Math.PI * 2.0;
            double rr = Math.sqrt(sl.random.nextDouble()) * this.sunJudgementRadius;
            double sx = center.x + Math.cos(ang) * rr;
            double sz = center.z + Math.sin(ang) * rr;
            double topY = sunY - 1.0;

            for (int p = 0; p < Math.max(1, (Integer)config().sunJudgementRayVisualHeight.get()); p++) {
               double py = topY - p * 1.0;
               sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), sx, py, sz, 1, 0.0, 0.0, 0.0, 0.0);
            }

            sl.sendParticles(ParticleTypes.FLASH, sx, center.y + 0.5, sz, 1, 0.0, 0.0, 0.0, 0.0);
            sl.sendParticles((TensuraParticleType)TensuraParticleTypes.SOLAR_FLASH.get(), sx, center.y + 2.5, sz, 6, 0.15, 1.0, 0.15, 0.0);
            AABB hit = new AABB(
               sx - (Double)config().sunJudgementRayHitRadius.get(),
               center.y - (Double)config().sunJudgementRayHitDepth.get(),
               sz - (Double)config().sunJudgementRayHitRadius.get(),
               sx + (Double)config().sunJudgementRayHitRadius.get(),
               center.y + (Double)config().sunJudgementRayHitHeight.get(),
               sz + (Double)config().sunJudgementRayHitRadius.get()
            );

            for (LivingEntity e : sl.getEntitiesOfClass(LivingEntity.class, hit, LivingEntity::isAlive)) {
               if (e != this
                  && !this.isAlliedTo(e)
                  && !e.isAlliedTo(this)
                  && !(e instanceof FeldwayEntity)
                  && !(e instanceof MichaelManasEntity)
                  && !(e instanceof FeldwayAngelEntity)
                  && !(e instanceof Player p && (p.isCreative() || p.isSpectator()))) {
                  CompoundTag tag = e.getPersistentData();
                  int last = tag.getInt("MichaelSunJudgementHitTick");
                  if (last != this.tickCount) {
                     tag.putInt("MichaelSunJudgementHitTick", this.tickCount);
                     DamageSource src = this.disintegrationDamage(
                        ((Double)config().sunJudgementBarrierBypass.get()).floatValue(), ((Double)config().sunJudgementResistanceBypass.get()).floatValue()
                     );
                     e.hurt(src, this.sunJudgementDamage);
                     TensuraDamageHelper.directSpiritualHurt(e, this, src, this.sunJudgementSpirit);

                     try {
                        e.addEffect(
                           new MobEffectInstance(
                              TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL),
                              (Integer)config().sunJudgementAntiSkillTicks.get(),
                              (Integer)config().sunJudgementAntiSkillAmplifier.get(),
                              false,
                              false,
                              true
                           )
                        );
                     } catch (Throwable var25) {
                     }

                     try {
                        e.addEffect(
                           new MobEffectInstance(
                              TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION),
                              (Integer)config().sunJudgementOppressionTicks.get(),
                              (Integer)config().sunJudgementOppressionAmplifier.get(),
                              false,
                              false,
                              true
                           )
                        );
                     } catch (Throwable var24) {
                     }
                  }
               }
            }
         }

         if (this.sunJudgementTicks == 0) {
            sl.playSound(null, center.x, center.y, center.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 3.2F, 0.8F);
            sl.sendParticles((SimpleParticleType)ModParticles.SUN.get(), center.x, sunY, center.z, 10, 0.0, 0.0, 0.0, 0.0);
            sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), center.x, center.y + 1.0, center.z, 180, 4.0, 1.2, 4.0, 0.0);
            sl.sendParticles((TensuraParticleType)TensuraParticleTypes.SOLAR_FLASH.get(), center.x, center.y + 1.0, center.z, 100, 3.4, 1.0, 3.4, 0.0);
         }
      }
   }

   private void spawnMichaelDisintegration(ServerLevel sl, Vec3 pos, float size, float height, int life, float damage) {
      DisintegrationEntity disintegration = new DisintegrationEntity(this.level(), this);
      disintegration.setSize(size);
      disintegration.setHeight(height);
      disintegration.setLife(life);
      disintegration.setDamage(damage);
      disintegration.setPos(pos);
      this.level().addFreshEntity(disintegration);
      this.disintegrationIds.add(disintegration.getUUID());
   }

   private void cleanupDisintegrations() {
      if (this.level() instanceof ServerLevel sl) {
         for (UUID id : List.copyOf(this.disintegrationIds)) {
            Entity e = sl.getEntity(id);
            if (e == null || !e.isAlive()) {
               this.disintegrationIds.remove(id);
            }
         }
      }
   }

   public void die(DamageSource src) {
      super.die(src);
      if (this.level() instanceof ServerLevel sl) {
         this.bossBar.removeAllPlayers();
         FeldwayEntity feldwayMaster = this.getFeldwayMaster(sl);
         if (feldwayMaster != null) {
            feldwayMaster.onMichaelKilled();
         }

         sl.playSound(null, this.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 3.0F, 0.55F);
         sl.sendParticles(ParticleTypes.FLASH, this.getX(), this.getY() + 1.2, this.getZ(), 18, 1.2, 1.2, 1.2, 0.0);
         sl.sendParticles((SimpleParticleType)ModParticles.SUN.get(), this.getX(), this.getY() + 1.2, this.getZ(), 6, 0.0, 0.0, 0.0, 0.0);
         sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), this.getX(), this.getY() + 1.2, this.getZ(), 100, 1.3, 1.0, 1.3, 0.0);
         sl.sendParticles((TensuraParticleType)TensuraParticleTypes.SOLAR_FLASH.get(), this.getX(), this.getY() + 1.2, this.getZ(), 70, 1.2, 0.9, 1.2, 0.0);
      }
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      if (this.masterId != null) {
         tag.putUUID("MichaelMaster", this.masterId);
      }

      tag.putInt("MichaelPulseCd", this.pulseCd);
      tag.putInt("MichaelBeamCd", this.beamCd);
      tag.putInt("MichaelDashCd", this.dashCd);
      tag.putInt("MichaelSealCd", this.sealCd);
      tag.putInt("MichaelLanceCd", this.lanceCd);
      tag.putInt("MichaelPurgeCd", this.purgeCd);
      tag.putInt("MichaelCageCd", this.cageCd);
      tag.putInt("MichaelFinalCd", this.finalMoveCd);
      tag.putInt("MichaelSunCd", this.sunJudgementCd);
      tag.putInt("MichaelCapWindow", this.capWindowTicks);
      tag.putFloat("MichaelCapTaken", this.capDamageTaken);
      tag.putInt("MichaelCapImmune", this.capImmuneTicks);
      tag.putInt("MichaelHardI", this.hardIFrameTicks);
      tag.putBoolean("MichaelFinalUsed", this.finalMoveUsed);
      tag.putBoolean("MichaelSunUsed", this.sunFinalUsed);
      tag.putInt("MichaelSunTicks", this.sunJudgementTicks);
      tag.putDouble("MichaelSunX", this.sunJudgementCenter.x);
      tag.putDouble("MichaelSunY", this.sunJudgementCenter.y);
      tag.putDouble("MichaelSunZ", this.sunJudgementCenter.z);
      tag.putFloat("MichaelSunRadius", this.sunJudgementRadius);
      tag.putFloat("MichaelSunDamage", this.sunJudgementDamage);
      tag.putFloat("MichaelSunSpirit", this.sunJudgementSpirit);
      int n = Math.min(32, this.disintegrationIds.size());
      tag.putInt("MichaelDisintegrationCount", n);

      for (int i = 0; i < n; i++) {
         tag.putUUID("MichaelDisintegration_" + i, this.disintegrationIds.get(i));
      }
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.hasUUID("MichaelMaster")) {
         this.masterId = tag.getUUID("MichaelMaster");
      }

      this.pulseCd = tag.getInt("MichaelPulseCd");
      this.beamCd = tag.getInt("MichaelBeamCd");
      this.dashCd = tag.getInt("MichaelDashCd");
      this.sealCd = tag.getInt("MichaelSealCd");
      this.lanceCd = tag.getInt("MichaelLanceCd");
      this.purgeCd = tag.getInt("MichaelPurgeCd");
      this.cageCd = tag.getInt("MichaelCageCd");
      this.finalMoveCd = tag.getInt("MichaelFinalCd");
      this.sunJudgementCd = tag.getInt("MichaelSunCd");
      this.capWindowTicks = tag.getInt("MichaelCapWindow");
      this.capDamageTaken = tag.getFloat("MichaelCapTaken");
      this.capImmuneTicks = tag.getInt("MichaelCapImmune");
      this.hardIFrameTicks = tag.getInt("MichaelHardI");
      this.finalMoveUsed = tag.getBoolean("MichaelFinalUsed");
      this.sunFinalUsed = tag.getBoolean("MichaelSunUsed");
      this.sunJudgementTicks = tag.getInt("MichaelSunTicks");
      this.sunJudgementCenter = new Vec3(tag.getDouble("MichaelSunX"), tag.getDouble("MichaelSunY"), tag.getDouble("MichaelSunZ"));
      this.sunJudgementRadius = tag.getFloat("MichaelSunRadius");
      this.sunJudgementDamage = tag.getFloat("MichaelSunDamage");
      this.sunJudgementSpirit = tag.getFloat("MichaelSunSpirit");
      this.disintegrationIds.clear();
      int n = tag.getInt("MichaelDisintegrationCount");

      for (int i = 0; i < n; i++) {
         String key = "MichaelDisintegration_" + i;
         if (tag.hasUUID(key)) {
            this.disintegrationIds.add(tag.getUUID(key));
         }
      }
   }

   private static class MichaelCombatGoal extends Goal {
      private final MichaelManasEntity mob;

      MichaelCombatGoal(MichaelManasEntity mob) {
         this.mob = mob;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         return true;
      }

      public void tick() {
         if (this.mob.getTarget() != null && this.mob.getTarget().isAlive()) {
            this.mob.getLookControl().setLookAt(this.mob.getTarget(), 85.0F, 85.0F);
         }
      }
   }
}
