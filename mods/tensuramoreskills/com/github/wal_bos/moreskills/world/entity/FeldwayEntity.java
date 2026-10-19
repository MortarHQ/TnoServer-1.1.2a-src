package com.github.wal_bos.moreskills.world.entity;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.registry.entity.ModEntityTypes;
import com.github.wal_bos.moreskills.registry.particle.ModParticles;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import org.joml.Vector3f;

public class FeldwayEntity extends PathfinderMob {
   private final ServerBossEvent bossEvent;
   private int phase = 0;
   private int atk = 0;
   private int animT = 0;
   private int gcd = 0;
   private int antiOneShotTicks = 0;
   private int hardIFrameTicks = 0;
   private int angelCooldown = 0;
   private int dashCooldown = 0;
   private int commandCooldown = 0;
   private int skyBeamCooldown = 0;
   private int spatialCooldown = 0;
   private int fieldCooldown = 0;
   private int lanceRainCooldown = 0;
   private int prisonCooldown = 0;
   private int heavenfallCooldown = 0;
   private boolean pressureFieldActive = false;
   private int pressureFieldTicks = 0;
   private int pressureFieldDuration = 0;
   private Vec3 pressureFieldCenter = Vec3.ZERO;
   private double pressureFieldStartY = 0.0;
   private double pressureFieldBottomY = 0.0;
   private boolean pressureFieldImpactDone = false;
   private boolean awakenedHandOffDone = false;
   private int skyJudgementTicks = 0;
   private Vec3 skyJudgementCenter = Vec3.ZERO;
   private float skyJudgementRadius = 0.0F;
   private float skyJudgementDamage = 0.0F;
   private float skyJudgementSpirit = 0.0F;
   private int trueSightCd = 0;
   private final List<UUID> angelIds = new ArrayList<>();
   private UUID lockedTargetId = null;
   private static final double TARGET_LEASH_DISTANCE = 160.0;
   private static final int A_NONE = 0;
   private static final int A_DASH = 1;
   private static final int A_COMMAND = 2;
   private static final int A_FIELD = 3;
   private static final int A_SPATIAL = 4;
   private static final int A_SKY_BEAM = 5;
   private static final int A_REGALIA = 6;
   private static final int A_LANCE_RAIN = 7;
   private static final int A_PRISON = 8;
   private static final int A_HEAVENFALL = 9;
   private static final float TRUE_DEATH_HP_PERCENT = 0.15F;
   private static final float MICHAEL_RELEASE_HP_PERCENT = 0.2F;
   private static final float MAX_DAMAGE_PER_SECOND_PERCENT = 0.04F;
   private static final float MAX_DAMAGE_PER_HIT_PERCENT = 0.01F;
   private static final float MAX_EMERGENCY_DAMAGE_PERCENT = 0.01F;
   private static final float SAFEGUARD_RECOVER_PERCENT = 0.22F;
   private static final float BASE_HIT = 500.0F;
   private static final float DASH_HIT = 650.0F;
   private boolean regaliaDone = false;
   private Vec3 regaliaCenter = Vec3.ZERO;
   private boolean michaelReleased = false;
   private UUID michaelId = null;
   private boolean feldwaySkillsGiven = false;
   private int capWindowTicks = (Integer)cfg().capWindowTicks.get();
   private float capDamageTaken = 0.0F;
   private int capImmuneTicks = 0;
   private boolean applyingGuardedDamage = false;
   private float lastSafeHealth = 1.0F;
   private int lastSafeHealthTick = 0;
   private int flyWanderCd = 0;
   private float flyOrbitAngle = 0.0F;
   private float flyOrbitRadius = 9.0F;
   private int flyOrbitDir = 1;
   private boolean flyingMode = false;
   private boolean allowTrueDeath = false;
   private static final ResourceLocation FELDWAY_RESISTANCE_DEGRADATION = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "feldway_resistance_degradation"
   );

   private static EntityConfigs.FeldwayConfig cfg() {
      return EntityConfigs.ENTITY.feldway;
   }

   private static float cfgFloat(DoubleValue value) {
      return ((Double)value.get()).floatValue();
   }

   public FeldwayEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
      this.bossEvent = new ServerBossEvent(this.getDisplayName(), BossBarColor.PURPLE, BossBarOverlay.NOTCHED_20);
      this.bossEvent.setPlayBossMusic(true).setDarkenScreen(true);
      if ((Boolean)cfg().persistent.get()) {
         this.setPersistenceRequired();
      }

      this.lookControl = new LookControl(this);
      this.xpReward = (Integer)cfg().xpReward.get();
      this.switchNavigator(true);
      this.applyConfiguredAttributes();
   }

   private void applyConfiguredAttributes() {
      this.setBase(Attributes.MAX_HEALTH, (Double)cfg().maxHealth.get());
      this.setBase(Attributes.ARMOR, (Double)cfg().armor.get());
      this.setBase(Attributes.ARMOR_TOUGHNESS, (Double)cfg().armorToughness.get());
      this.setBase(Attributes.KNOCKBACK_RESISTANCE, (Double)cfg().knockbackResistance.get());
      this.setBase(Attributes.MOVEMENT_SPEED, (Double)cfg().movementSpeed.get());
      this.setBase(Attributes.FLYING_SPEED, (Double)cfg().flyingSpeed.get());
      this.setBase(Attributes.ATTACK_DAMAGE, (Double)cfg().attackDamage.get());
      this.setBase(Attributes.FOLLOW_RANGE, (Double)cfg().followRange.get());
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
         .add(Attributes.MAX_HEALTH, 12000.0)
         .add(Attributes.ARMOR, 80.0)
         .add(Attributes.ARMOR_TOUGHNESS, 30.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.MOVEMENT_SPEED, 0.34)
         .add(Attributes.FLYING_SPEED, 0.65)
         .add(Attributes.ATTACK_DAMAGE, 220.0)
         .add(Attributes.FOLLOW_RANGE, 96.0);
   }

   public boolean removeWhenFarAway(double dist) {
      return false;
   }

   public void startSeenByPlayer(ServerPlayer p) {
      super.startSeenByPlayer(p);
      this.bossEvent.addPlayer(p);
   }

   public void stopSeenByPlayer(ServerPlayer p) {
      super.stopSeenByPlayer(p);
      this.bossEvent.removePlayer(p);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new FeldwayEntity.FeldwayCombatGoal(this));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 12.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector
         .addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true, e -> e instanceof Player p && !p.isCreative() && !p.isSpectator()));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, LivingEntity.class, 10, true, false, e -> {
         if (!(e instanceof LivingEntity le)) {
            return false;
         } else if (le.isAlive() && le != this) {
            if (le instanceof Player p && (p.isCreative() || p.isSpectator())) {
               return false;
            } else if (this.isAngelAlly(le)) {
               return false;
            } else {
               return le instanceof MichaelManasEntity ? false : !this.isAlliedTo(le) && !le.isAlliedTo(this);
            }
         } else {
            return false;
         }
      }));
   }

   private void switchNavigator(boolean onLand) {
      if (onLand) {
         this.moveControl = new MoveControl(this);
         this.navigation = new GroundPathNavigation(this, this.level());
         this.flyingMode = false;
      } else {
         this.moveControl = new FlyingMoveControl(this, 14, true);
         this.navigation = new GroundPathNavigation(this, this.level());
         this.flyingMode = true;
      }
   }

   public boolean isFlyingMode() {
      return this.flyingMode;
   }

   private boolean isAngelAlly(Entity e) {
      if (e == null) {
         return false;
      } else {
         return e instanceof FeldwayAngelEntity ? true : this.angelIds.contains(e.getUUID());
      }
   }

   private int angelCountClamped() {
      this.cleanupAngels();
      return Math.min(3, this.angelIds.size());
   }

   private void enforceResistanceBypass() {
      try {
         AttributeHelper.addPermanentAttributeIfHigher(
            this, TensuraAttributes.RESISTANCE_DEGRADATION, FELDWAY_RESISTANCE_DEGRADATION, (Double)cfg().resistanceDegradation.get(), Operation.ADD_VALUE
         );
      } catch (Throwable var2) {
      }
   }

   public boolean isAlliedTo(Entity e) {
      if (super.isAlliedTo(e)) {
         return true;
      } else if (e == null) {
         return false;
      } else if (e instanceof FeldwayAngelEntity) {
         return true;
      } else {
         return e instanceof MichaelManasEntity ? true : this.angelIds.contains(e.getUUID());
      }
   }

   public void setTarget(LivingEntity target) {
      if (target == null) {
         LivingEntity locked = this.resolveLockedTarget();
         if (locked != null
            && locked.isAlive()
            && this.distanceToSqr(locked) <= (Double)cfg().targetLeashDistance.get() * (Double)cfg().targetLeashDistance.get()) {
            super.setTarget(locked);
         } else {
            this.lockedTargetId = null;
            super.setTarget(null);
         }
      } else if (this.isValidTrueSightTarget(target)) {
         this.lockedTargetId = target.getUUID();
         super.setTarget(target);
      }
   }

   private LivingEntity resolveLockedTarget() {
      if (this.level() instanceof ServerLevel sl) {
         if (this.lockedTargetId == null) {
            return null;
         } else {
            return sl.getEntity(this.lockedTargetId) instanceof LivingEntity le ? le : null;
         }
      } else {
         return null;
      }
   }

   private boolean isTargetTooFar(LivingEntity target) {
      return this.distanceToSqr(target) > (Double)cfg().targetLeashDistance.get() * (Double)cfg().targetLeashDistance.get();
   }

   private void updateLockedTarget() {
      if (!this.level().isClientSide()) {
         LivingEntity locked = this.resolveLockedTarget();
         if (locked != null) {
            if (this.isValidTrueSightTarget(locked) && !this.isTargetTooFar(locked)) {
               if (this.getTarget() != locked) {
                  super.setTarget(locked);
               }
            } else {
               if (this.getTarget() == locked) {
                  super.setTarget(null);
               }

               this.lockedTargetId = null;
            }
         } else {
            LivingEntity cur = this.getTarget();
            if (cur != null) {
               if (this.isValidTrueSightTarget(cur) && !this.isTargetTooFar(cur)) {
                  this.lockedTargetId = cur.getUUID();
               } else {
                  super.setTarget(null);
                  this.lockedTargetId = null;
               }
            }
         }
      }
   }

   private float dmg(float amount) {
      return Math.max(cfgFloat(cfg().minimumDamage), amount);
   }

   private void equipFeldwayLoadout() {
      try {
         ItemStack sword = new ItemStack((ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.parse("tensura:dead_end_rainbow")));
         if (!sword.isEmpty()) {
            this.setItemSlot(EquipmentSlot.MAINHAND, sword);
            this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
         }
      } catch (Throwable var2) {
      }
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData) {
      SpawnGroupData data = super.finalizeSpawn(level, difficulty, reason, spawnData);
      this.equipFeldwayLoadout();
      return data;
   }

   private void giveFeldwaySkills() {
      if (!this.level().isClientSide()) {
         if (!this.feldwaySkillsGiven) {
            this.feldwaySkillsGiven = true;
            this.learnSkillSafe((ManasSkill)ResistanceSkills.ABNORMAL_CONDITION_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.COLD_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.CORROSION_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.DARKNESS_ATTACK_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.EARTH_ATTACK_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.ELECTRICITY_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.FLAME_ATTACK_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.GRAVITY_ATTACK_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.HEAT_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.HOLY_ATTACK_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.LIGHT_ATTACK_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.MAGIC_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.PAIN_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.PARALYSIS_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.PIERCE_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.POISON_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.SPATIAL_ATTACK_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.THERMAL_FLUCTUATION_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.WATER_ATTACK_NULLIFICATION.get());
            this.learnSkillSafe((ManasSkill)ResistanceSkills.WIND_ATTACK_NULLIFICATION.get());
         }
      }
   }

   private void learnSkillSafe(ManasSkill skill) {
      if (skill != null) {
         try {
            SkillHelper.learnSkill(this, skill);
         } catch (Throwable var3) {
         }
      }
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      this.bossEvent.setProgress(Mth.clamp(this.getHealth() / this.getMaxHealth(), 0.0F, 1.0F));
      int a = this.angelCountClamped();
      this.bossEvent.setOverlay(a > 0 ? BossBarOverlay.NOTCHED_12 : BossBarOverlay.NOTCHED_20);
      if (this.phase >= 3) {
         this.bossEvent.setColor(BossBarColor.RED);
      } else if (this.phase == 2) {
         this.bossEvent.setColor(BossBarColor.BLUE);
      } else {
         this.bossEvent.setColor(BossBarColor.PURPLE);
      }
   }

   public boolean canBeAffected(MobEffectInstance inst) {
      return false;
   }

   private void applyInfiniteRegenSelf() {
      try {
         this.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION), 240, 4, false, false, false));
      } catch (Throwable var3) {
      }

      if (!this.allowTrueDeath && this.getHealth() > this.trueDeathThresholdHealth() && !this.michaelReleased) {
         try {
            this.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION), 80, 0, false, false, false));
         } catch (Throwable var2) {
         }
      } else {
         this.removeDeathBlockingEffects();
      }
   }

   private void enforceMegaEPMP() {
      try {
         IExistence ex = TensuraStorages.getExistenceFrom(this);
         ex.setAura(Math.max(ex.getAura(), (Double)cfg().enforcedAura.get()));
         ex.setMagicule(Math.max(ex.getMagicule(), (Double)cfg().enforcedMagicule.get()));
         ex.setSpiritualHealth(Math.max(ex.getSpiritualHealth(), (Double)cfg().enforcedSpiritualHealth.get()));
         ex.markDirty();
      } catch (Throwable var2) {
      }
   }

   private void enforceAbsoluteSpiritualImmunity() {
      if (!this.allowTrueDeath || !this.michaelReleased || this.isMichaelAlive()) {
         try {
            IExistence ex = TensuraStorages.getExistenceFrom(this);
            double want = Math.max(
               (Double)cfg().absoluteSpiritualHealthMinimum.get(), this.getMaxHealth() * (Double)cfg().absoluteSpiritualHealthMaxHealthMultiplier.get()
            );
            if (Double.isNaN(ex.getSpiritualHealth()) || Double.isInfinite(ex.getSpiritualHealth()) || ex.getSpiritualHealth() < want) {
               ex.setSpiritualHealth(want);
               ex.markDirty();
            }
         } catch (Throwable var4) {
         }
      }
   }

   private void cleanseHard() {
      try {
         TensuraStorages.resetEffect(this);
      } catch (Throwable var3) {
      }

      try {
         this.clearFire();
      } catch (Throwable var2) {
      }

      this.applyInfiniteRegenSelf();
   }

   private boolean isInvisCheeseTarget(Entity e) {
      if (e instanceof LivingEntity le) {
         if (!le.isAlive()) {
            return false;
         } else {
            return !(le instanceof Player p && (p.isCreative() || p.isSpectator())) ? le.isInvisible() : false;
         }
      } else {
         return false;
      }
   }

   private boolean isValidTrueSightTarget(Entity e) {
      if (!(e instanceof LivingEntity le)) {
         return false;
      } else if (le.isAlive() && le != this) {
         if (le instanceof Player p && (p.isCreative() || p.isSpectator())) {
            return false;
         } else if (le instanceof MichaelManasEntity) {
            return false;
         } else {
            return this.isAngelAlly(le) ? false : !le.isAlliedTo(this) && !this.isAlliedTo(le);
         }
      } else {
         return false;
      }
   }

   private void applyReveal(LivingEntity le) {
      try {
         le.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT));
      } catch (Throwable var4) {
      }

      try {
         le.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.SHADOW_STEP));
      } catch (Throwable var3) {
      }
   }

   private void trueSightRetarget() {
      if (this.level() instanceof ServerLevel sl) {
         if (this.trueSightCd <= 0) {
            this.trueSightCd = 8;
            LivingEntity locked = this.resolveLockedTarget();
            if (locked != null) {
               if (this.isValidTrueSightTarget(locked) && !this.isTargetTooFar(locked)) {
                  if (this.getTarget() != locked) {
                     super.setTarget(locked);
                  }

                  if (this.isInvisCheeseTarget(locked)) {
                     this.applyReveal(locked);
                  }

                  return;
               }

               this.lockedTargetId = null;
               super.setTarget(null);
            }

            LivingEntity cur = this.getTarget();
            if (cur != null && cur.isAlive() && (cur instanceof MichaelManasEntity || this.isAlliedTo(cur) || cur.isAlliedTo(this) || this.isTargetTooFar(cur))
               )
             {
               super.setTarget(null);
               cur = null;
            }

            if (cur != null && cur.isAlive()) {
               this.lockedTargetId = cur.getUUID();
               if (this.isInvisCheeseTarget(cur)) {
                  this.applyReveal(cur);
               }
            } else {
               double r = Mth.clamp(this.getAttributeValue(Attributes.FOLLOW_RANGE), 24.0, 128.0);
               AABB box = this.getBoundingBox().inflate(r, 32.0, r);
               List<LivingEntity> list = sl.getEntitiesOfClass(LivingEntity.class, box, this::isValidTrueSightTarget);
               if (!list.isEmpty()) {
                  LivingEntity best = null;
                  double bestD = Double.MAX_VALUE;

                  for (LivingEntity e : list) {
                     double d = this.distanceToSqr(e);
                     if (d < bestD) {
                        bestD = d;
                        best = e;
                     }
                  }

                  if (best != null) {
                     this.lockedTargetId = best.getUUID();
                     super.setTarget(best);
                     if (this.isInvisCheeseTarget(best)) {
                        this.applyReveal(best);
                     }
                  }
               }
            }
         }
      }
   }

   private boolean isOwnOrAllyDamage(DamageSource src) {
      try {
         Entity a = src.getEntity();
         Entity d = src.getDirectEntity();
         if (a == this || d == this) {
            return true;
         }

         if (a != null && (a.isAlliedTo(this) || this.isAlliedTo(a) || this.isAngelAlly(a))) {
            return true;
         }

         if (d != null && (d.isAlliedTo(this) || this.isAlliedTo(d) || this.isAngelAlly(d))) {
            return true;
         }
      } catch (Throwable var4) {
      }

      return false;
   }

   private boolean isMichaelAlive() {
      if (this.level() instanceof ServerLevel sl) {
         if (this.michaelId == null) {
            return false;
         }

         Entity e = sl.getEntity(this.michaelId);
         return e != null && e.isAlive();
      } else {
         return false;
      }
   }

   public void onMichaelKilled() {
      if (this.level() instanceof ServerLevel sl) {
         sl.playSound(null, this.blockPosition(), SoundEvents.WARDEN_ROAR, SoundSource.HOSTILE, 4.0F, 0.75F);
         sl.sendParticles(ParticleTypes.FLASH, this.getX(), this.getY() + this.getBbHeight() * 0.55, this.getZ(), 12, 1.0, 1.0, 1.0, 0.0);
         sl.sendParticles(
            (SimpleParticleType)ModParticles.DEATHSHINE.get(), this.getX(), this.getY() + this.getBbHeight() * 0.55, this.getZ(), 40, 1.2, 1.0, 1.2, 0.0
         );
         sl.sendParticles(
            (SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(),
            this.getX(),
            this.getY() + this.getBbHeight() * 0.55,
            this.getZ(),
            80,
            1.4,
            1.1,
            1.4,
            0.0
         );
      }

      this.michaelId = null;
      this.allowTrueDeath = this.getHealth() <= this.trueDeathThresholdHealth();
      this.capImmuneTicks = 0;
      this.hardIFrameTicks = 0;
      this.antiOneShotTicks = 0;
      this.invulnerableTime = 0;
      this.hurtTime = 0;
      this.capWindowTicks = (Integer)cfg().capWindowTicks.get();
      this.capDamageTaken = 0.0F;
      this.lastSafeHealth = this.getHealth();
      this.lastSafeHealthTick = this.tickCount;
      this.removeDeathBlockingEffects();
      this.gcd = Math.max(this.gcd, 20);
   }

   private void releaseMichaelManas() {
      if (this.level() instanceof ServerLevel sl) {
         if (!this.michaelReleased) {
            MichaelManasEntity m = (MichaelManasEntity)((EntityType)ModEntityTypes.MICHAEL_MANAS.get()).create(sl);
            if (m != null) {
               Vec3 spawn = this.position().add(0.0, 7.0, 0.0);
               m.moveTo(spawn.x, spawn.y, spawn.z, this.getYRot(), 0.0F);
               m.setMaster(this);
               m.getPersistentData().putUUID("MichaelMaster", this.getUUID());
               LivingEntity locked = this.resolveLockedTarget();
               if (locked != null && locked.isAlive()) {
                  m.setTarget(locked);
               } else if (this.getTarget() != null && this.getTarget().isAlive()) {
                  m.setTarget(this.getTarget());
               }

               boolean added = sl.addFreshEntity(m);
               if (added) {
                  this.michaelReleased = true;
                  this.michaelId = m.getUUID();
                  this.roar();
                  sl.playSound(null, this.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 4.2F, 0.55F);
                  sl.sendParticles(ParticleTypes.FLASH, this.getX(), this.getY() + 1.2, this.getZ(), 18, 1.2, 1.2, 1.2, 0.0);
                  sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), this.getX(), this.getY() + 1.2, this.getZ(), 60, 1.3, 1.1, 1.3, 0.0);
                  sl.sendParticles(
                     (SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(), this.getX(), this.getY() + 1.2, this.getZ(), 90, 1.5, 1.2, 1.5, 0.0
                  );
                  this.capImmuneTicks = Math.max(this.capImmuneTicks, (Integer)cfg().michaelReleaseCapImmuneTicks.get());
                  this.hardIFrameTicks = Math.max(this.hardIFrameTicks, (Integer)cfg().michaelReleaseHardIFrameTicks.get());
                  this.antiOneShotTicks = Math.max(this.antiOneShotTicks, (Integer)cfg().michaelReleaseAntiOneShotTicks.get());
                  this.invulnerableTime = Math.max(this.invulnerableTime, 40);
               }
            }
         }
      }
   }

   private float damageCapPerHit() {
      return this.maxDamagePerHit();
   }

   private float perSecondCap() {
      return Math.max(1.0F, this.getMaxHealth() * cfgFloat(cfg().maxDamagePerSecondPercent));
   }

   private float oneShotGuardThreshold() {
      return this.trueDeathThresholdHealth();
   }

   private float oneShotGuardRecoverTo() {
      return Math.max(this.trueDeathThresholdHealth() + 1.0F, this.getMaxHealth() * cfgFloat(cfg().safeguardRecoverPercent));
   }

   private boolean canUseLethalSafeguard(float beforeHealth) {
      return beforeHealth > this.oneShotGuardThreshold();
   }

   private void triggerLethalSafeguard() {
      float restore = Math.max(this.oneShotGuardRecoverTo(), this.trueDeathFloor() + 1.0F);
      super.setHealth(Math.min(this.getMaxHealth(), restore));
      this.deathTime = 0;
      this.dead = false;
      this.hurtTime = 0;
      this.invulnerableTime = Math.max(this.invulnerableTime, 50);
      this.capImmuneTicks = Math.max(this.capImmuneTicks, 45);
      this.antiOneShotTicks = Math.max(this.antiOneShotTicks, (Integer)cfg().michaelReleaseAntiOneShotTicks.get());
      this.hardIFrameTicks = Math.max(this.hardIFrameTicks, 28);
      this.lastSafeHealth = this.getHealth();
      this.lastSafeHealthTick = this.tickCount;
      this.allowTrueDeath = false;
   }

   public void setHealth(float value) {
      if (!this.level().isClientSide()) {
         if (Float.isNaN(value) || Float.isInfinite(value)) {
            value = Math.max(1.0F, this.getHealth());
         }

         float before = super.getHealth();
         if (value < 0.0F) {
            value = 0.0F;
         }

         if (this.isMichaelAlive() && value <= this.trueDeathThresholdHealth()) {
            value = Math.max(1.0F, this.trueDeathThresholdHealth() + 1.0F);
         }

         if (!this.isMichaelAlive() && this.michaelReleased && value <= this.trueDeathThresholdHealth()) {
            this.allowTrueDeath = true;
            this.capImmuneTicks = 0;
            this.hardIFrameTicks = 0;
            this.antiOneShotTicks = 0;
            this.invulnerableTime = 0;
            this.hurtTime = 0;
            this.removeDeathBlockingEffects();
         }

         if (!this.isMichaelAlive() && !this.allowTrueDeath && !this.applyingGuardedDamage && before > this.trueDeathThresholdHealth() && value < before) {
            float maxDrop = Math.min(this.emergencyDamageCap(), this.perSecondCap());
            float lowestAllowed = Math.max(this.trueDeathThresholdHealth() + 1.0F, before - maxDrop);
            if (value <= this.trueDeathThresholdHealth() || before - value > maxDrop) {
               value = lowestAllowed;
               this.capImmuneTicks = Math.max(this.capImmuneTicks, 20);
               this.antiOneShotTicks = Math.max(this.antiOneShotTicks, 80);
               this.hardIFrameTicks = Math.max(this.hardIFrameTicks, 12);
               this.invulnerableTime = Math.max(this.invulnerableTime, 20);
            }
         }

         if (!this.isMichaelAlive() && !this.allowTrueDeath && before > this.trueDeathThresholdHealth() && value <= 0.0F) {
            value = Math.max(1.0F, this.trueDeathThresholdHealth() + 1.0F);
            this.capImmuneTicks = Math.max(this.capImmuneTicks, 35);
            this.antiOneShotTicks = Math.max(this.antiOneShotTicks, (Integer)cfg().michaelReleaseAntiOneShotTicks.get());
            this.hardIFrameTicks = Math.max(this.hardIFrameTicks, 24);
            this.invulnerableTime = Math.max(this.invulnerableTime, 35);
         }

         if (!this.isMichaelAlive() && value <= 0.0F && before <= this.trueDeathThresholdHealth()) {
            this.allowTrueDeath = true;
            this.removeDeathBlockingEffects();
         }
      }

      super.setHealth(value);
   }

   public boolean isInvulnerableTo(DamageSource src) {
      if (src == null) {
         return true;
      }

      if (this.isMichaelAlive() && this.getHealth() <= this.trueDeathThresholdHealth()) {
         return true;
      }

      try {
         String id = src.getMsgId();
         if (id != null) {
            String s = id.toLowerCase();
            if (s.contains("delete") || s.contains("erase") || s.contains("kill") || s.contains("execute") || s.contains("remove") || s.contains("despawn")) {
               return true;
            }
         }
      } catch (Throwable var4) {
      }

      return super.isInvulnerableTo(src);
   }

   public boolean hurt(DamageSource src, float amt) {
      if (this.level().isClientSide()) {
         return false;
      }

      if (src == null) {
         return false;
      }

      if (this.isRemoved()) {
         return false;
      }

      if (this.isOwnOrAllyDamage(src)) {
         return false;
      }

      if (!(amt <= 0.0F) && !Float.isNaN(amt) && !Float.isInfinite(amt)) {
         this.refreshLastSafeHealth();
         if (this.isMichaelAlive()) {
            this.allowTrueDeath = false;
            if (this.getHealth() <= this.trueDeathThresholdHealth()) {
               super.setHealth(Math.max(1.0F, this.trueDeathThresholdHealth() + 1.0F));
               this.deathTime = 0;
               this.dead = false;
               this.hurtTime = 0;
               this.invulnerableTime = Math.max(this.invulnerableTime, 20);
               return false;
            }

            amt *= 0.35F;
         }

         float before = this.getHealth();
         boolean trueDeathRange = before <= this.trueDeathThresholdHealth();
         this.lastSafeHealth = before;
         this.lastSafeHealthTick = this.tickCount;
         if (trueDeathRange) {
            if (this.isMichaelAlive()) {
               this.allowTrueDeath = false;
               super.setHealth(Math.max(1.0F, this.trueDeathThresholdHealth() + 1.0F));
               this.deathTime = 0;
               this.dead = false;
               this.hurtTime = 0;
               this.invulnerableTime = Math.max(this.invulnerableTime, 20);
               return false;
            }

            this.removeDeathBlockingEffects();
            this.allowTrueDeath = true;
            this.capImmuneTicks = 0;
            this.hardIFrameTicks = 0;
            this.antiOneShotTicks = 0;
            this.invulnerableTime = 0;
            this.hurtTime = 0;
            this.applyingGuardedDamage = true;

            boolean ok;
            try {
               ok = super.hurt(src, amt);
            } finally {
               this.applyingGuardedDamage = false;
            }

            if (this.getHealth() <= 0.0F) {
               this.allowTrueDeath = true;
               this.removeDeathBlockingEffects();
            }

            return ok;
         } else {
            if (this.capImmuneTicks > 0) {
               return false;
            }

            if (this.hardIFrameTicks > 0) {
               return false;
            }

            this.allowTrueDeath = false;
            if (this.antiOneShotTicks > 0) {
               amt *= 0.2F;
            }

            float perHit = Math.min(this.maxDamagePerHit(), this.emergencyDamageCap());
            amt = Math.min(amt, perHit);
            float capSec = this.perSecondCap();
            float remaining = capSec - this.capDamageTaken;
            if (remaining <= 0.0F) {
               this.capImmuneTicks = Math.max(this.capImmuneTicks, 20);
               this.antiOneShotTicks = Math.max(this.antiOneShotTicks, 80);
               this.hardIFrameTicks = Math.max(this.hardIFrameTicks, 12);
               this.invulnerableTime = Math.max(this.invulnerableTime, 20);
               return false;
            }

            amt = Math.min(amt, remaining);
            if (before - amt <= this.trueDeathThresholdHealth()) {
               if (this.michaelReleased && !this.isMichaelAlive()) {
                  this.allowTrueDeath = true;
                  this.capImmuneTicks = 0;
                  this.hardIFrameTicks = 0;
                  this.antiOneShotTicks = 0;
                  this.invulnerableTime = 0;
                  this.hurtTime = 0;
                  this.removeDeathBlockingEffects();
               } else {
                  amt = Math.max(0.0F, before - this.trueDeathThresholdHealth() - 1.0F);
                  this.capImmuneTicks = Math.max(this.capImmuneTicks, 25);
                  this.antiOneShotTicks = Math.max(this.antiOneShotTicks, 100);
                  this.hardIFrameTicks = Math.max(this.hardIFrameTicks, 16);
                  this.invulnerableTime = Math.max(this.invulnerableTime, 25);
               }
            }

            if (amt <= 0.0F) {
               return false;
            }

            this.applyingGuardedDamage = true;

            boolean ok;
            try {
               ok = super.hurt(src, amt);
            } finally {
               this.applyingGuardedDamage = false;
            }

            float after = this.getHealth();
            float actualLoss = before - after;
            float allowedLoss = Math.min(amt, perHit);
            if (after <= 0.0F || after <= this.trueDeathThresholdHealth()) {
               if (before > this.trueDeathThresholdHealth() && (!this.michaelReleased || this.isMichaelAlive())) {
                  super.setHealth(Math.max(1.0F, this.trueDeathThresholdHealth() + 1.0F));
                  this.deathTime = 0;
                  this.dead = false;
                  this.hurtTime = 0;
                  this.allowTrueDeath = false;
                  this.capImmuneTicks = Math.max(this.capImmuneTicks, 35);
                  this.antiOneShotTicks = Math.max(this.antiOneShotTicks, (Integer)cfg().michaelReleaseAntiOneShotTicks.get());
                  this.hardIFrameTicks = Math.max(this.hardIFrameTicks, 24);
                  this.invulnerableTime = Math.max(this.invulnerableTime, 35);
                  return true;
               }

               if (this.michaelReleased && !this.isMichaelAlive()) {
                  this.allowTrueDeath = true;
                  this.capImmuneTicks = 0;
                  this.hardIFrameTicks = 0;
                  this.antiOneShotTicks = 0;
                  this.invulnerableTime = 0;
                  this.hurtTime = 0;
                  this.removeDeathBlockingEffects();
               }
            }

            if (actualLoss > allowedLoss && (!this.michaelReleased || this.isMichaelAlive() || !this.allowTrueDeath)) {
               super.setHealth(Math.max(this.trueDeathThresholdHealth() + 1.0F, before - allowedLoss));
               actualLoss = allowedLoss;
            }

            this.capDamageTaken = this.capDamageTaken + Math.max(0.0F, actualLoss);
            if (this.capDamageTaken >= capSec && before > this.trueDeathThresholdHealth() && (!this.michaelReleased || this.isMichaelAlive())) {
               this.capImmuneTicks = Math.max(this.capImmuneTicks, 25);
               this.antiOneShotTicks = Math.max(this.antiOneShotTicks, 100);
               this.hardIFrameTicks = Math.max(this.hardIFrameTicks, 18);
               this.invulnerableTime = Math.max(this.invulnerableTime, 25);
            }

            return ok;
         }
      } else {
         return false;
      }
   }

   public void die(DamageSource src) {
      if (!this.level().isClientSide() && this.michaelReleased && !this.isMichaelAlive() && !this.awakenedHandOffDone) {
         this.awakenIntoSecondBoss(src);
      } else {
         if (!this.level().isClientSide()) {
            if (this.isMichaelAlive()) {
               this.allowTrueDeath = false;
               super.setHealth(Math.max(1.0F, this.trueDeathThresholdHealth() + 1.0F));
               this.deathTime = 0;
               this.dead = false;
               this.hurtTime = 0;
               this.invulnerableTime = Math.max(this.invulnerableTime, 40);
               this.capImmuneTicks = Math.max(this.capImmuneTicks, (Integer)cfg().michaelReleaseCapImmuneTicks.get());
               this.antiOneShotTicks = Math.max(this.antiOneShotTicks, (Integer)cfg().michaelReleaseAntiOneShotTicks.get());
               this.hardIFrameTicks = Math.max(this.hardIFrameTicks, (Integer)cfg().michaelReleaseHardIFrameTicks.get());
               return;
            }

            if (this.michaelReleased && this.getHealth() <= this.trueDeathThresholdHealth()) {
               this.allowTrueDeath = true;
               this.capImmuneTicks = 0;
               this.hardIFrameTicks = 0;
               this.antiOneShotTicks = 0;
               this.invulnerableTime = 0;
               this.hurtTime = 0;
               this.removeDeathBlockingEffects();
               super.die(src);
               return;
            }

            if (this.getHealth() <= this.trueDeathThresholdHealth()) {
               this.allowTrueDeath = true;
               this.capImmuneTicks = 0;
               this.hardIFrameTicks = 0;
               this.antiOneShotTicks = 0;
               this.invulnerableTime = 0;
               this.hurtTime = 0;
               this.removeDeathBlockingEffects();
               super.die(src);
               return;
            }

            if (!this.allowTrueDeath) {
               try {
                  IExistence ex = TensuraStorages.getExistenceFrom(this);
                  double want = Math.max(
                     (Double)cfg().absoluteSpiritualHealthMinimum.get(), this.getMaxHealth() * (Double)cfg().absoluteSpiritualHealthMaxHealthMultiplier.get()
                  );
                  ex.setSpiritualHealth(want);
                  ex.markDirty();
               } catch (Throwable var5) {
               }

               this.triggerLethalSafeguard();
               return;
            }
         }

         this.allowTrueDeath = true;
         this.removeDeathBlockingEffects();
         super.die(src);
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         if (this.capWindowTicks > 0) {
            this.capWindowTicks--;
         }

         if (this.capWindowTicks <= 0) {
            this.capWindowTicks = (Integer)cfg().capWindowTicks.get();
            this.capDamageTaken = 0.0F;
         }

         if (this.capImmuneTicks > 0) {
            this.capImmuneTicks--;
         }

         this.cleanseHard();
         this.enforceAbsoluteSpiritualImmunity();
         this.enforceMegaEPMP();
         this.giveFeldwaySkills();
         this.enforceResistanceBypass();
         this.updateLockedTarget();
         this.trueSightRetarget();
         this.phaseHandler();
         if (!this.isMichaelAlive() && this.getHealth() <= this.trueDeathThresholdHealth()) {
            this.removeDeathBlockingEffects();
         }

         if (this.isMichaelAlive() && this.getHealth() <= 0.0F) {
            this.allowTrueDeath = false;
            super.setHealth(Math.max(1.0F, this.trueDeathThresholdHealth() + 1.0F));
            this.deathTime = 0;
            this.dead = false;
         }

         if (this.phase >= 3 && !this.michaelReleased && this.getHealth() <= this.michaelReleaseThresholdHealth()) {
            this.releaseMichaelManas();
         }

         if (this.michaelReleased && this.michaelId != null && !this.isMichaelAlive()) {
            this.onMichaelKilled();
         }

         if (this.michaelReleased && !this.isMichaelAlive() && this.getHealth() <= this.trueDeathThresholdHealth() && !this.awakenedHandOffDone) {
            this.awakenIntoSecondBoss(this.damageSources().generic());
         } else {
            if (this.gcd > 0) {
               this.gcd--;
            }

            if (this.antiOneShotTicks > 0) {
               this.antiOneShotTicks--;
            }

            if (this.hardIFrameTicks > 0) {
               this.hardIFrameTicks--;
            }

            if (this.angelCooldown > 0) {
               this.angelCooldown--;
            }

            if (this.dashCooldown > 0) {
               this.dashCooldown--;
            }

            if (this.commandCooldown > 0) {
               this.commandCooldown--;
            }

            if (this.skyBeamCooldown > 0) {
               this.skyBeamCooldown--;
            }

            if (this.spatialCooldown > 0) {
               this.spatialCooldown--;
            }

            if (this.fieldCooldown > 0) {
               this.fieldCooldown--;
            }

            if (this.lanceRainCooldown > 0) {
               this.lanceRainCooldown--;
            }

            if (this.prisonCooldown > 0) {
               this.prisonCooldown--;
            }

            if (this.heavenfallCooldown > 0) {
               this.heavenfallCooldown--;
            }

            if (this.trueSightCd > 0) {
               this.trueSightCd--;
            }

            this.cleanupAngels();
            this.protectAngelsFromBoss();
            LivingEntity cur = this.getTarget();
            if (cur != null && cur.isAlive() && this.isInvisCheeseTarget(cur)) {
               this.applyReveal(cur);
            }

            this.handleFlyingAggro();
            this.animHandler();
            this.tickPressureField();
            if (this.level() instanceof ServerLevel sl) {
               double y = this.getY() + this.getBbHeight() * 0.72;
               sl.sendParticles(ParticleTypes.ENCHANT, this.getX(), y, this.getZ(), 3, 0.32, 0.16, 0.32, 0.0);
               if (this.tickCount % 4 == 0) {
                  sl.sendParticles(ParticleTypes.PORTAL, this.getX(), y, this.getZ(), 2, 0.4, 0.18, 0.4, 0.02);
               }

               if (this.tickCount % 10 == 0) {
                  sl.sendParticles(ParticleTypes.CLOUD, this.getX(), y - 0.15, this.getZ(), 2, 0.25, 0.08, 0.25, 0.0);
               }
            }
         }
      }
   }

   private void protectAngelsFromBoss() {
      if (this.level() instanceof ServerLevel sl) {
         for (UUID id : List.copyOf(this.angelIds)) {
            if (sl.getEntity(id) instanceof LivingEntity le && le.isAlive()) {
               le.invulnerableTime = 6;
               le.hurtTime = 0;
            }
         }

         if (this.michaelReleased && this.michaelId != null && !this.isMichaelAlive()) {
            this.onMichaelKilled();
         }

         if (this.michaelReleased && !this.isMichaelAlive() && this.getHealth() <= this.trueDeathThresholdHealth() && !this.awakenedHandOffDone) {
            this.awakenIntoSecondBoss(this.damageSources().generic());
         }
      }
   }

   private DamageSource holySource(boolean barrierBypass, boolean resistanceBypass) {
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

   private void hurtSpiritual(LivingEntity target, float amount) {
      try {
         amount = this.dmg(amount);
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

   private void burstNoAllies(Vec3 center, double radius, float dmg) {
      AABB box = new AABB(center.x - radius, center.y - radius, center.z - radius, center.x + radius, center.y + radius, center.z + radius);

      for (LivingEntity e : this.level().getEntitiesOfClass(LivingEntity.class, box, LivingEntity::isAlive)) {
         if (e != this
            && !this.isAngelAlly(e)
            && !(e instanceof MichaelManasEntity)
            && !e.isAlliedTo(this)
            && !this.isAlliedTo(e)
            && !(e instanceof Player p && (p.isCreative() || p.isSpectator()))
            && !(e.position().distanceTo(center) > radius)) {
            e.hurt(this.holySource(false, true), this.dmg(dmg));
            this.hurtSpiritual(e, dmg * 0.25F);
         }
      }
   }

   private void phaseHandler() {
      if (this.phase == 0) {
         LivingEntity t = this.resolveLockedTarget();
         if (t == null) {
            t = this.getTarget();
         }

         if (t != null && t.isAlive()) {
            this.phase = 1;
            this.roar();
            this.summonAngels(1);
         }
      } else {
         if (this.phase == 1 && this.getHealth() <= this.getMaxHealth() * 0.66F) {
            if (!this.regaliaDone) {
               this.regaliaDone = true;
               this.startRegaliaDominion();
               return;
            }

            if (this.atk != 6) {
               this.phase = 2;
               this.roar();
               this.summonAngels(1);
            }
         }

         if (this.phase == 2 && this.getHealth() <= this.getMaxHealth() * 0.33F) {
            this.phase = 3;
            this.roar();
            this.summonAngels(1);
         }
      }
   }

   private void roar() {
      this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WARDEN_ROAR, SoundSource.HOSTILE, 5.5F, 0.8F);
      if (this.level() instanceof ServerLevel sl) {
         double y = this.getY() + this.getBbHeight() * 0.55;
         sl.sendParticles(ParticleTypes.FLASH, this.getX(), y, this.getZ(), 8, 1.0, 1.0, 1.0, 0.0);
         sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), this.getX(), y, this.getZ(), 70, 1.6, 1.0, 1.6, 0.0);
         sl.sendParticles((SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(), this.getX(), y, this.getZ(), 90, 1.8, 1.2, 1.8, 0.0);

         for (int ring = 0; ring < 3; ring++) {
            double r = 1.8 + ring * 1.4;

            for (int deg = 0; deg < 360; deg += 18) {
               double a = Math.toRadians(deg + ring * 9.0);
               double px = this.getX() + Math.cos(a) * r;
               double pz = this.getZ() + Math.sin(a) * r;
               sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), px, y + ring * 0.2, pz, 1, 0.0, 0.12, 0.0, 0.0);
            }
         }
      }
   }

   private void startRegaliaDominion() {
      if (this.level() instanceof ServerLevel sl) {
         this.atk = 6;
         this.animT = 0;
         this.gcd = 0;
         this.hardIFrameTicks = Math.max(this.hardIFrameTicks, 10);
         this.regaliaCenter = this.position().add(0.0, this.getBbHeight() * 0.5, 0.0);
         sl.playSound(null, this.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.HOSTILE, 3.2F, 0.55F);
         sl.sendParticles(ParticleTypes.FLASH, this.getX(), this.getY() + 1.2, this.getZ(), 10, 1.0, 1.0, 1.0, 0.0);
         sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), this.getX(), this.getY() + 1.2, this.getZ(), 28, 1.0, 1.0, 1.0, 0.0);
      }
   }

   private void regaliaWindupParticles(ServerLevel sl) {
      double cx = this.regaliaCenter.x;
      double cy = this.regaliaCenter.y;
      double cz = this.regaliaCenter.z;
      int rings = 4;
      double baseR = 3.0 + this.animT * 0.12;

      for (int r = 0; r < rings; r++) {
         double rr = baseR + r * 1.6;

         for (int deg = 0; deg < 360; deg += 14) {
            double a = Math.toRadians(deg + sl.getGameTime() % 360L);
            double px = cx + Math.cos(a) * rr;
            double pz = cz + Math.sin(a) * rr;
            double py = cy + Mth.sin((float)sl.getGameTime() * 0.1F + r) * 0.6F;
            sl.sendParticles(new DustParticleOptions(new Vector3f(1.0F, 0.95F, 0.2F), 1.9F), px, py, pz, 1, 0.0, 0.02, 0.0, 0.0);
            sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), px, py, pz, 1, 0.0, 0.0, 0.0, 0.0);
            if ((deg + r) % 28 == 0) {
               sl.sendParticles((SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(), px, py + 0.15, pz, 1, 0.0, 0.0, 0.0, 0.0);
            }
         }
      }

      for (int i = 0; i < 14; i++) {
         double ang = sl.random.nextDouble() * Math.PI * 2.0;
         double rr = 1.0 + sl.random.nextDouble() * (baseR + 1.8);
         double px = cx + Math.cos(ang) * rr;
         double pz = cz + Math.sin(ang) * rr;
         double py = cy + 0.4 + sl.random.nextDouble() * 1.5;
         sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), px, py, pz, 1, 0.0, 0.24, 0.0, 0.0);
      }
   }

   private static void tryAdd(List<Holder<MobEffect>> out, RegistrySupplier<MobEffect> ro) {
      try {
         if (ro != null) {
            out.add(TensuraMobEffects.getReference(ro));
         }
      } catch (Throwable var3) {
      }
   }

   private static List<Holder<MobEffect>> regaliaBadEffects() {
      List<Holder<MobEffect>> out = new ArrayList<>();
      tryAdd(out, TensuraMobEffects.MAGIC_INTERFERENCE);
      tryAdd(out, TensuraMobEffects.MOVEMENT_INTERFERENCE);
      tryAdd(out, TensuraMobEffects.OPPRESSION);
      tryAdd(out, TensuraMobEffects.SPATIAL_BLOCKADE);
      tryAdd(out, TensuraMobEffects.CURSE);
      tryAdd(out, TensuraMobEffects.FEAR);
      tryAdd(out, TensuraMobEffects.PARALYSIS);
      return out;
   }

   private void applyRegaliaAll(LivingEntity e, int dur, int amp) {
      try {
         TensuraStorages.resetEffect(e);
      } catch (Throwable var9) {
      }

      for (Holder<MobEffect> eff : regaliaBadEffects()) {
         if (eff != null) {
            try {
               e.addEffect(new MobEffectInstance(eff, dur, amp, false, false, true));
            } catch (Throwable var8) {
            }
         }
      }

      try {
         e.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), dur, 2, false, false, true));
      } catch (Throwable var7) {
      }
   }

   private void regaliaImpact(ServerLevel sl) {
      float radius = 30.0F;
      sl.playSound(null, this.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 3.6F, 0.55F);
      sl.sendParticles(ParticleTypes.FLASH, this.regaliaCenter.x, this.regaliaCenter.y, this.regaliaCenter.z, 10, 1.2, 1.2, 1.2, 0.0);
      sl.sendParticles(
         (SimpleParticleType)ModParticles.DEATHSHINE.get(), this.regaliaCenter.x, this.regaliaCenter.y, this.regaliaCenter.z, 210, 3.0, 1.5, 3.0, 0.0
      );
      sl.sendParticles(
         (SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(),
         this.regaliaCenter.x,
         this.regaliaCenter.y,
         this.regaliaCenter.z,
         140,
         3.2,
         1.6,
         3.2,
         0.0
      );
      sl.sendParticles(
         (SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(),
         this.regaliaCenter.x,
         this.regaliaCenter.y + 0.4,
         this.regaliaCenter.z,
         110,
         0.6,
         0.2,
         0.6,
         0.0
      );

      for (int ring = 0; ring < 7; ring++) {
         double r = 2.2 + ring * 2.7;

         for (int deg = 0; deg < 360; deg += 9) {
            double a = Math.toRadians(deg + ring * 4.0);
            double px = this.regaliaCenter.x + Math.cos(a) * r;
            double pz = this.regaliaCenter.z + Math.sin(a) * r;
            double py = this.regaliaCenter.y + 0.15 + ring * 0.06;
            sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), px, py, pz, 1, 0.0, 0.0, 0.0, 0.0);
         }
      }

      AABB box = new AABB(
         this.regaliaCenter.x - radius,
         this.regaliaCenter.y - radius,
         this.regaliaCenter.z - radius,
         this.regaliaCenter.x + radius,
         this.regaliaCenter.y + radius,
         this.regaliaCenter.z + radius
      );

      for (LivingEntity e : sl.getEntitiesOfClass(LivingEntity.class, box, x -> x != null && x.isAlive())) {
         if (e != this
            && !this.isAngelAlly(e)
            && !(e instanceof MichaelManasEntity)
            && !this.isAlliedTo(e)
            && !e.isAlliedTo(this)
            && !(e instanceof Player p && (p.isCreative() || p.isSpectator()))
            && !(e.position().distanceTo(this.regaliaCenter) > radius)) {
            this.applyRegaliaAll(e, 100, 1);
            e.setDeltaMovement(Vec3.ZERO);
            e.hurtMarked = true;
         }
      }

      this.burstNoAllies(this.regaliaCenter, radius, 950.0F);
   }

   private void animHandler() {
      if (this.atk != 0) {
         this.animT++;
         LivingEntity t = this.getTarget();
         if (t != null && t.isAlive()) {
            this.getLookControl().setLookAt(t, 85.0F, 85.0F);
         }

         if (this.atk == 6) {
            if (this.level() instanceof ServerLevel sl) {
               this.regaliaWindupParticles(sl);
               if (this.animT == 50) {
                  this.regaliaImpact(sl);
               }
            }

            if (this.animT >= 72) {
               this.atk = 0;
               this.animT = 0;
               this.gcd = 34;
               this.phase = 2;
               this.roar();
               this.summonAngels(1);
            }
         } else if (this.atk == 1) {
            if (this.animT == 6) {
               this.dashStrike();
            }

            if (this.animT == 10 && this.phase >= 2) {
               this.dashStrike();
            }

            if (this.animT >= 16) {
               this.endAtk();
            }
         } else if (this.atk == 2) {
            if (this.animT == 8) {
               this.commandPulse();
            }

            if (this.animT >= 18) {
               this.endAtk();
            }
         } else if (this.atk == 3) {
            if (this.animT == 8) {
               this.pressureField();
            }

            if (this.animT >= 18) {
               this.endAtk();
            }
         } else if (this.atk == 4) {
            if (this.animT >= 8 && this.animT <= 28) {
               this.castSpatialSlasher();
            }

            if (this.animT >= 32) {
               this.endAtk();
            }
         } else if (this.atk != 5) {
            if (this.atk == 7) {
               if (this.animT == 14) {
                  this.castCelestialLanceRain();
               }

               if (this.animT >= 32) {
                  this.endAtk();
               }
            } else if (this.atk == 8) {
               if (this.animT == 10) {
                  this.castSolarPrison();
               }

               if (this.animT >= 26) {
                  this.endAtk();
               }
            } else {
               if (this.atk == 9) {
                  if (this.animT == 12) {
                     this.castHeavenfallBurst();
                  }

                  if (this.animT >= 28) {
                     this.endAtk();
                  }
               }
            }
         } else if (this.animT == 8 && !this.castSkyJudgementBeam()) {
            this.atk = 0;
            this.animT = 0;
            this.gcd = 10;
         } else {
            if (this.animT >= 9 && this.skyJudgementTicks > 0) {
               this.tickSkyJudgementBeam();
            }

            if (this.animT >= 120 || this.animT >= 9 && this.skyJudgementTicks <= 0) {
               this.endAtk();
            }
         }
      }
   }

   private void endAtk() {
      this.atk = 0;
      this.animT = 0;
      this.gcd = this.phase >= 3 ? 8 : (this.phase == 2 ? 12 : 16);
   }

   private float hit() {
      float mult = this.phase >= 3 ? 1.6F : (this.phase == 2 ? 1.25F : 1.0F);
      return this.dmg(cfgFloat(cfg().baseHitDamage) * mult);
   }

   private void dashStrike() {
      LivingEntity t = this.getTarget();
      if (t != null && t.isAlive()) {
         if (this.dashCooldown <= 0) {
            this.dashCooldown = this.phase >= 3 ? (Integer)cfg().dashCooldownPhaseThree.get() : (Integer)cfg().dashCooldownPhaseOneTwo.get();
            Vec3 from = this.position();
            Vec3 to = t.position();
            Vec3 dir = to.subtract(from);
            double len = Math.max(1.0, dir.length());
            Vec3 n = dir.scale(1.0 / len);
            Vec3 dashTo = to.subtract(n.scale(2.0));
            double y = this.level().getHeight(Types.MOTION_BLOCKING_NO_LEAVES, (int)dashTo.x, (int)dashTo.z);
            dashTo = new Vec3(dashTo.x, Math.max(y + 1.0, dashTo.y), dashTo.z);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 2.8F, 0.85F);
            this.teleportTo(dashTo.x, dashTo.y, dashTo.z);
            if (this.level() instanceof ServerLevel sl) {
               sl.sendParticles(ParticleTypes.FLASH, dashTo.x, dashTo.y + 1.0, dashTo.z, 3, 0.4, 0.4, 0.4, 0.0);
               sl.sendParticles((SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(), dashTo.x, dashTo.y + 1.0, dashTo.z, 30, 1.0, 0.7, 1.0, 0.0);
               sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), dashTo.x, dashTo.y + 1.0, dashTo.z, 18, 0.9, 0.6, 0.9, 0.0);

               for (int deg = 0; deg < 360; deg += 20) {
                  double a = Math.toRadians(deg);
                  double px = dashTo.x + Math.cos(a) * 1.8;
                  double pz = dashTo.z + Math.sin(a) * 1.8;
                  sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), px, dashTo.y + 0.25, pz, 1, 0.0, 0.2, 0.0, 0.0);
               }
            }

            AABB hitBox = this.getBoundingBox().inflate(4.3, 2.7, 4.3);

            for (LivingEntity e : this.level().getEntitiesOfClass(LivingEntity.class, hitBox, ent -> ent.isAlive() && !ent.isAlliedTo(this))) {
               if (e != this
                  && !(e instanceof Player p && (p.isCreative() || p.isSpectator()))
                  && !this.isAngelAlly(e)
                  && !(e instanceof MichaelManasEntity)
                  && !this.isAlliedTo(e)
                  && !e.isAlliedTo(this)) {
                  e.hurt(this.holySource(false, true), this.dmg(cfgFloat(cfg().dashHitDamage) * (this.phase >= 3 ? 1.55F : 1.1F)));
                  this.hurtSpiritual(e, cfgFloat(cfg().dashHitDamage) * 0.2F);
                  SkillHelper.knockBack(this, e, 2.0F);
               }
            }
         }
      }
   }

   private void pressureField() {
      if (this.level() instanceof ServerLevel sl) {
         if (this.fieldCooldown <= 0) {
            if (!this.pressureFieldActive) {
               this.fieldCooldown = (Integer)cfg().pressureFieldCooldownTicks.get();
               this.pressureFieldCenter = this.position().add(0.0, this.getBbHeight() * 0.56, 0.0);
               this.pressureFieldStartY = this.pressureFieldCenter.y + 218.0;
               this.pressureFieldBottomY = this.pressureFieldCenter.y - 3.0;
               this.pressureFieldTicks = 0;
               this.pressureFieldDuration = (Integer)cfg().pressureFieldDurationTicks.get();
               this.pressureFieldImpactDone = false;
               this.pressureFieldActive = true;
               sl.playSound(null, this.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.HOSTILE, 4.8F, 0.42F);
               sl.playSound(null, this.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.HOSTILE, 3.8F, 0.56F);

               for (int i = 0; i < 220; i++) {
                  double ang = sl.random.nextDouble() * Math.PI * 2.0;
                  double rr = Math.sqrt(sl.random.nextDouble()) * 3.2;
                  double px = this.pressureFieldCenter.x + Math.cos(ang) * rr;
                  double pz = this.pressureFieldCenter.z + Math.sin(ang) * rr;
                  sl.sendParticles(
                     (SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(),
                     px,
                     this.pressureFieldStartY + sl.random.nextDouble() * 4.0,
                     pz,
                     1,
                     0.0,
                     0.95,
                     0.0,
                     0.0
                  );
                  if (i % 4 == 0) {
                     sl.sendParticles(
                        (SimpleParticleType)ModParticles.DEATHSHINE.get(),
                        px,
                        this.pressureFieldStartY + sl.random.nextDouble() * 4.0,
                        pz,
                        1,
                        0.0,
                        0.0,
                        0.0,
                        0.0
                     );
                  }
               }
            }
         }
      }
   }

   private void commandPulse() {
      if (this.level() instanceof ServerLevel sl) {
         if (this.commandCooldown <= 0) {
            this.commandCooldown = (Integer)cfg().commandCooldownTicks.get();
            AABB box = this.getBoundingBox().inflate(24.0);
            sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), this.getX(), this.getY() + 1.0, this.getZ(), 160, 3.0, 1.2, 3.0, 0.0);
            sl.sendParticles((SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(), this.getX(), this.getY() + 1.0, this.getZ(), 90, 3.2, 1.1, 3.2, 0.0);
            sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), this.getX(), this.getY() + 1.0, this.getZ(), 80, 1.0, 0.4, 1.0, 0.0);

            for (int ring = 0; ring < 5; ring++) {
               double r = 2.2 + ring * 3.0;
               double y = this.getY() + 0.4 + ring * 0.2;

               for (int deg = 0; deg < 360; deg += 12) {
                  double a = Math.toRadians(deg + ring * 8.0);
                  double px = this.getX() + Math.cos(a) * r;
                  double pz = this.getZ() + Math.sin(a) * r;
                  sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), px, y, pz, 1, 0.0, 0.0, 0.0, 0.0);
               }
            }

            for (LivingEntity e : this.level().getEntitiesOfClass(LivingEntity.class, box, ent -> ent.isAlive())) {
               if (e != this
                  && !(e instanceof Player p && (p.isCreative() || p.isSpectator()))
                  && !(e instanceof MichaelManasEntity)
                  && !this.isAngelAlly(e)
                  && !this.isAlliedTo(e)
                  && !e.isAlliedTo(this)) {
                  try {
                     TensuraStorages.resetEffect(e);
                  } catch (Throwable var18) {
                  }

                  try {
                     e.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), 85, 2, false, false, true));
                  } catch (Throwable var17) {
                  }

                  try {
                     e.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SILENCE), 85, 1, false, false, true));
                  } catch (Throwable var16) {
                  }

                  try {
                     e.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), 70, 1, false, false, true));
                  } catch (Throwable var15) {
                  }

                  e.hurt(this.holySource(false, true), this.dmg(cfgFloat(cfg().commandBurstDamage)));
                  this.hurtSpiritual(e, cfgFloat(cfg().commandBurstSpiritualDamage));
               }
            }
         }
      }
   }

   private void cleanupAngels() {
      if (this.level() instanceof ServerLevel sl) {
         for (UUID id : List.copyOf(this.angelIds)) {
            Entity e = sl.getEntity(id);
            if (e == null || !e.isAlive()) {
               this.angelIds.remove(id);
            }
         }
      }
   }

   private void summonAngels(int requested) {
      if (this.level() instanceof ServerLevel sl) {
         if (this.angelCooldown <= 0) {
            this.cleanupAngels();
            int alive = this.angelIds.size();
            if (alive < (Integer)cfg().maxAngels.get()) {
               LivingEntity target = this.resolveLockedTarget();
               if (target == null) {
                  target = this.getTarget();
               }

               if (target != null && target.isAlive()) {
                  int want = Math.min(requested, (Integer)cfg().maxAngels.get() - alive);
                  if (want > 0) {
                     int count = 0;
                     if (alive == 0) {
                        count = this.random.nextFloat() < cfg().summonAngelChanceWhenNone.get() ? 1 : 2;
                     } else if (alive == 1) {
                        count = this.random.nextFloat() < cfg().summonAngelChanceWhenOne.get() ? 1 : 2;
                     } else if (alive == 2) {
                        count = this.random.nextFloat() < 0.18F ? 1 : 0;
                     }

                     count = Math.min(count, want);
                     if (count > 0) {
                        this.angelCooldown = this.phase >= 3 ? (Integer)cfg().angelCooldownPhaseThree.get() : (Integer)cfg().angelCooldownPhaseOneTwo.get();

                        for (int i = 0; i < count; i++) {
                           FeldwayAngelEntity a = (FeldwayAngelEntity)((EntityType)ModEntityTypes.FELDWAY_ANGEL.get()).create(sl);
                           if (a != null) {
                              double ang = (Math.PI * 2) * ((alive + i) / 3.0);
                              double r = 8.0 + i;
                              double px = this.getX() + Math.cos(ang) * r;
                              double pz = this.getZ() + Math.sin(ang) * r;
                              double py = this.getY() + 6.0 + i;
                              a.moveTo(px, py, pz, this.getYRot(), 0.0F);
                              a.setMaster(this);
                              a.setTarget(target);
                              sl.addFreshEntity(a);
                              this.angelIds.add(a.getUUID());
                              sl.sendParticles(ParticleTypes.FLASH, px, py + 0.5, pz, 1, 0.0, 0.0, 0.0, 0.0);
                              sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), px, py + 0.5, pz, 18, 0.7, 0.7, 0.7, 0.0);
                              sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), px, py + 0.5, pz, 12, 0.35, 0.22, 0.35, 0.0);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void castSpatialSlasher() {
      if (this.level() instanceof ServerLevel sl) {
         boolean mastered = this.phase >= 3;
         int pulseAge = this.animT - 8;
         if (pulseAge >= 0 && pulseAge <= 20) {
            if (pulseAge == 0) {
               if (this.spatialCooldown > 0) {
                  return;
               }

               this.spatialCooldown = (Integer)cfg().spatialCooldownTicks.get();
               sl.playSound(null, this.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.HOSTILE, 3.3F, 0.65F);
               sl.playSound(null, this.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 2.8F, 1.2F);
               sl.playSound(null, this.blockPosition(), (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 2.4F, 0.85F);
               sl.sendParticles(ParticleTypes.FLASH, this.getX(), this.getY() + this.getBbHeight() * 0.45, this.getZ(), 8, 0.9, 0.5, 0.9, 0.0);
               sl.sendParticles(
                  (SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(),
                  this.getX(),
                  this.getY() + this.getBbHeight() * 0.45,
                  this.getZ(),
                  44,
                  1.2,
                  0.7,
                  1.2,
                  0.0
               );
            }

            float maxRadius = mastered ? cfgFloat(cfg().spatialRadiusMastered) : cfgFloat(cfg().spatialRadius);
            float damage = mastered ? cfgFloat(cfg().spatialDamageMastered) : cfgFloat(cfg().spatialDamage);
            float spirit = mastered ? cfgFloat(cfg().spatialSpiritualDamageMastered) : cfgFloat(cfg().spatialSpiritualDamage);
            Vec3 center = this.position().add(0.0, this.getBbHeight() * 0.45, 0.0);
            float progress = pulseAge / 20.0F;
            float eased = 1.0F - (1.0F - progress) * (1.0F - progress);
            float currentRadius = Math.max(1.5F, maxRadius * eased);
            this.spawnDeathEnergyLightningBurst(sl, center, currentRadius, Math.max(18, (int)(currentRadius * 5.0F)), center.y + currentRadius * 0.1);
            this.spawnDeathEnergyLightningBurst(sl, center, currentRadius * 0.78F, Math.max(14, (int)(currentRadius * 4.0F)), center.y + currentRadius * 0.18);
            this.spawnDeathEnergyLightningBurst(sl, center, currentRadius * 0.56F, Math.max(10, (int)(currentRadius * 3.0F)), center.y + currentRadius * 0.24);
            if (pulseAge % 2 == 0) {
               this.spawnDeathEnergyLightningCracks(sl, center, currentRadius, mastered ? 8 : 6);
            }

            int smokePoints = mastered ? 16 : 12;

            for (int i = 0; i < smokePoints; i++) {
               double ang = (Math.PI * 2) / smokePoints * i + pulseAge * 0.12;
               double px = center.x + Math.cos(ang) * Math.max(0.9, currentRadius * 0.86);
               double pz = center.z + Math.sin(ang) * Math.max(0.9, currentRadius * 0.86);
               sl.sendParticles(
                  (SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), px, center.y + 0.15 + sl.random.nextDouble() * 0.5, pz, 1, 0.0, 0.14, 0.0, 0.0
               );
            }

            sl.sendParticles(
               (SimpleParticleType)ModParticles.DEATHSHINE.get(),
               center.x,
               center.y + 0.8,
               center.z,
               mastered ? 20 : 12,
               currentRadius * 0.15,
               0.45,
               currentRadius * 0.15,
               0.0
            );
            float shellThickness = 1.85F;
            AABB box = new AABB(
               center.x - currentRadius - 2.5,
               center.y - 3.0,
               center.z - currentRadius - 2.5,
               center.x + currentRadius + 2.5,
               center.y + currentRadius * 0.6 + 3.0,
               center.z + currentRadius + 2.5
            );

            for (Entity e : this.level().getEntities(this, box, ex -> ex instanceof LivingEntity le && le.isAlive() && le != this)) {
               LivingEntity target = (LivingEntity)e;
               if (!target.isAlliedTo(this)
                  && !this.isAlliedTo(target)
                  && !(target instanceof MichaelManasEntity)
                  && !this.isAngelAlly(target)
                  && !(target instanceof Player p && (p.isCreative() || p.isSpectator()))) {
                  Vec3 tCenter = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
                  double dist = tCenter.distanceTo(center);
                  if (!(dist > currentRadius) && !(dist < currentRadius - shellThickness)) {
                     CompoundTag tag = target.getPersistentData();
                     int lastHitTick = tag.getInt("FeldwayDeathEnergyLightningTick");
                     int now = this.tickCount;
                     if (lastHitTick != now) {
                        tag.putInt("FeldwayDeathEnergyLightningTick", now);

                        try {
                           target.addEffect(
                              new MobEffectInstance(
                                 TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), mastered ? 90 : 70, mastered ? 2 : 1, false, false, true
                              )
                           );
                        } catch (Throwable var29) {
                        }

                        try {
                           target.addEffect(
                              new MobEffectInstance(
                                 TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), mastered ? 90 : 70, 1, false, false, true
                              )
                           );
                        } catch (Throwable var28) {
                        }

                        try {
                           target.addEffect(
                              new MobEffectInstance(
                                 TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE), mastered ? 80 : 60, 0, false, false, true
                              )
                           );
                        } catch (Throwable var27) {
                        }

                        try {
                           target.addEffect(
                              new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION), mastered ? 80 : 55, 1, false, false, true)
                           );
                        } catch (Throwable var26) {
                        }

                        target.hurt(this.holySource(true, true), this.dmg(damage));
                        this.hurtSpiritual(target, spirit);
                        Vec3 knock = target.position().subtract(center);
                        if (knock.lengthSqr() > 0.001) {
                           Vec3 n = knock.normalize();
                           target.push(n.x * 1.35, 0.22, n.z * 1.35);
                           target.hurtMarked = true;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean castSkyJudgementBeam() {
      if (this.level() instanceof ServerLevel sl) {
         if (this.skyBeamCooldown > 0) {
            return false;
         } else {
            LivingEntity t = this.getTarget();
            if (t != null && t.isAlive()) {
               this.skyBeamCooldown = (Integer)cfg().skyBeamCooldownTicks.get();
               this.skyJudgementCenter = t.position().add(0.0, t.getBbHeight() * 0.5, 0.0);
               this.skyJudgementRadius = this.phase >= 3 ? cfgFloat(cfg().skyJudgementRadiusPhaseThree) : cfgFloat(cfg().skyJudgementRadius);
               this.skyJudgementDamage = this.phase >= 3 ? cfgFloat(cfg().skyJudgementDamagePhaseThree) : cfgFloat(cfg().skyJudgementDamage);
               this.skyJudgementSpirit = this.phase >= 3 ? cfgFloat(cfg().skyJudgementSpiritualDamagePhaseThree) : cfgFloat(cfg().skyJudgementSpiritualDamage);
               this.skyJudgementTicks = (Integer)cfg().skyBeamChargeTicks.get();
               sl.playSound(null, this.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.HOSTILE, 4.5F, 0.55F);
               sl.playSound(null, this.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.HOSTILE, 4.0F, 0.65F);
               sl.sendParticles(
                  ParticleTypes.FLASH, this.skyJudgementCenter.x, this.skyJudgementCenter.y + 1.0, this.skyJudgementCenter.z, 18, 3.0, 1.8, 3.0, 0.0
               );
               sl.sendParticles(
                  (SimpleParticleType)ModParticles.DEATHSHINE.get(),
                  this.skyJudgementCenter.x,
                  this.skyJudgementCenter.y + 12.0,
                  this.skyJudgementCenter.z,
                  210,
                  this.skyJudgementRadius * 0.28,
                  12.0,
                  this.skyJudgementRadius * 0.28,
                  0.0
               );
               sl.sendParticles(
                  (SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(),
                  this.skyJudgementCenter.x,
                  this.skyJudgementCenter.y + 12.0,
                  this.skyJudgementCenter.z,
                  150,
                  this.skyJudgementRadius * 0.24,
                  12.0,
                  this.skyJudgementRadius * 0.24,
                  0.0
               );
               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private void castCelestialLanceRain() {
      if (this.level() instanceof ServerLevel sl) {
         if (this.lanceRainCooldown <= 0) {
            LivingEntity t = this.getTarget();
            if (t != null && t.isAlive()) {
               this.lanceRainCooldown = (Integer)cfg().lanceRainCooldownTicks.get();
               Vec3 center = t.position();
               float radius = this.phase >= 3 ? cfgFloat(cfg().lanceRainRadiusPhaseThree) : cfgFloat(cfg().lanceRainRadius);
               int strikes = this.phase >= 3 ? (Integer)cfg().lanceRainStrikesPhaseThree.get() : (Integer)cfg().lanceRainStrikes.get();
               sl.playSound(null, this.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.HOSTILE, 3.2F, 1.25F);
               sl.playSound(null, this.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.HOSTILE, 2.7F, 0.9F);

               for (int i = 0; i < strikes; i++) {
                  double ang = sl.random.nextDouble() * Math.PI * 2.0;
                  double rr = Math.sqrt(sl.random.nextDouble()) * radius;
                  double sx = center.x + Math.cos(ang) * rr;
                  double sz = center.z + Math.sin(ang) * rr;
                  double topY = center.y + 34.0 + sl.random.nextDouble() * 8.0;
                  this.spawnDeathshineColumn(sl, sx, topY, sz, this.phase >= 3 ? 20 : 16, 1.2);

                  for (int j = 0; j < 5; j++) {
                     sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), sx, center.y + 0.5 + j * 0.8, sz, 2, 0.12, 0.06, 0.12, 0.0);
                  }

                  sl.sendParticles(ParticleTypes.FLASH, sx, center.y + 0.3, sz, 1, 0.0, 0.0, 0.0, 0.0);
                  sl.sendParticles((SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(), sx, center.y + 6.0, sz, 10, 0.18, 3.2, 0.18, 0.0);
                  AABB hit = new AABB(sx - 1.2, center.y - 2.0, sz - 1.2, sx + 1.2, center.y + 9.0, sz + 1.2);

                  for (LivingEntity e : sl.getEntitiesOfClass(LivingEntity.class, hit, LivingEntity::isAlive)) {
                     if (e != this
                        && !(e instanceof Player p && (p.isCreative() || p.isSpectator()))
                        && !(e instanceof MichaelManasEntity)
                        && !this.isAngelAlly(e)
                        && !this.isAlliedTo(e)
                        && !e.isAlliedTo(this)) {
                        e.hurt(
                           this.holySource(true, true), this.dmg(this.phase >= 3 ? cfgFloat(cfg().lanceRainDamagePhaseThree) : cfgFloat(cfg().lanceRainDamage))
                        );
                        this.hurtSpiritual(e, this.phase >= 3 ? cfgFloat(cfg().lanceRainSpiritualDamagePhaseThree) : cfgFloat(cfg().lanceRainSpiritualDamage));
                     }
                  }
               }
            }
         }
      }
   }

   private void castSolarPrison() {
      if (this.level() instanceof ServerLevel sl) {
         if (this.prisonCooldown <= 0) {
            LivingEntity t = this.getTarget();
            if (t != null && t.isAlive()) {
               this.prisonCooldown = (Integer)cfg().prisonCooldownTicks.get();
               Vec3 center = t.position().add(0.0, t.getBbHeight() * 0.5, 0.0);
               float radius = cfgFloat(cfg().solarPrisonRadius);

               for (int ring = 0; ring < 3; ring++) {
                  double y = center.y - 1.0 + ring * 2.0;

                  for (int deg = 0; deg < 360; deg += 10) {
                     double a = Math.toRadians(deg);
                     double px = center.x + Math.cos(a) * radius;
                     double pz = center.z + Math.sin(a) * radius;
                     sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), px, y, pz, 1, 0.0, 0.0, 0.0, 0.0);
                     sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), px, y, pz, 1, 0.0, 0.08, 0.0, 0.0);
                  }
               }

               for (int deg = 0; deg < 360; deg += 18) {
                  double a = Math.toRadians(deg + this.tickCount * 8.0);
                  double px = center.x + Math.cos(a) * 2.1;
                  double pz = center.z + Math.sin(a) * 2.1;
                  sl.sendParticles((SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(), px, center.y + 0.8, pz, 1, 0.0, 0.0, 0.0, 0.0);
               }

               AABB box = new AABB(center.x - radius, center.y - 3.0, center.z - radius, center.x + radius, center.y + 4.0, center.z + radius);

               for (LivingEntity e : sl.getEntitiesOfClass(LivingEntity.class, box, LivingEntity::isAlive)) {
                  if (e != this
                     && !(e instanceof Player p && (p.isCreative() || p.isSpectator()))
                     && !(e instanceof MichaelManasEntity)
                     && !this.isAngelAlly(e)
                     && !this.isAlliedTo(e)
                     && !e.isAlliedTo(this)) {
                     try {
                        e.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE), 90, 1, false, false, true));
                     } catch (Throwable var16) {
                     }

                     try {
                        e.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), 90, 1, false, false, true));
                     } catch (Throwable var15) {
                     }

                     e.hurt(
                        this.holySource(false, true),
                        this.dmg(this.phase >= 3 ? cfgFloat(cfg().solarPrisonDamagePhaseThree) : cfgFloat(cfg().solarPrisonDamage))
                     );
                     this.hurtSpiritual(e, cfgFloat(cfg().solarPrisonSpiritualDamage));
                  }
               }
            }
         }
      }
   }

   private void castHeavenfallBurst() {
      if (this.level() instanceof ServerLevel sl) {
         if (this.heavenfallCooldown <= 0) {
            this.heavenfallCooldown = (Integer)cfg().heavenfallCooldownTicks.get();
            Vec3 center = this.position().add(0.0, this.getBbHeight() * 0.5, 0.0);
            float radius = this.phase >= 3 ? 15.0F : 11.0F;
            sl.playSound(null, this.blockPosition(), (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 3.8F, 0.65F);
            sl.playSound(null, this.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.HOSTILE, 2.8F, 0.7F);
            sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), center.x, center.y, center.z, 180, 2.8, 1.7, 2.8, 0.0);
            sl.sendParticles((SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(), center.x, center.y, center.z, 130, 2.4, 1.5, 2.4, 0.0);
            sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), center.x, center.y + 0.2, center.z, 90, 0.7, 0.12, 0.7, 0.0);
            sl.sendParticles(ParticleTypes.FLASH, center.x, center.y, center.z, 6, 0.8, 0.8, 0.8, 0.0);

            for (int ring = 0; ring < 6; ring++) {
               double r = 1.6 + ring * (radius / 6.0);
               double y = center.y + 0.08 * ring;

               for (int deg = 0; deg < 360; deg += 10) {
                  double a = Math.toRadians(deg + ring * 5.0);
                  double px = center.x + Math.cos(a) * r;
                  double pz = center.z + Math.sin(a) * r;
                  sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), px, y, pz, 1, 0.0, 0.0, 0.0, 0.0);
               }
            }

            this.burstNoAllies(center, radius, this.phase >= 3 ? 1600.0F : 1050.0F);
            AABB box = new AABB(center.x - radius, center.y - radius, center.z - radius, center.x + radius, center.y + radius, center.z + radius);

            for (LivingEntity e : sl.getEntitiesOfClass(LivingEntity.class, box, LivingEntity::isAlive)) {
               if (e != this
                  && !(e instanceof Player p && (p.isCreative() || p.isSpectator()))
                  && !(e instanceof MichaelManasEntity)
                  && !this.isAngelAlly(e)
                  && !this.isAlliedTo(e)
                  && !e.isAlliedTo(this)
                  && !(e.position().distanceTo(center) > radius)) {
                  try {
                     e.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION), 50, 1, false, false, true));
                  } catch (Throwable var16) {
                  }

                  SkillHelper.knockBack(this, e, 2.4F);
               }
            }
         }
      }
   }

   public boolean doHurtTarget(Entity target) {
      if (target instanceof LivingEntity le) {
         if (le == this) {
            return false;
         } else if (le instanceof Player p && (p.isCreative() || p.isSpectator())) {
            return false;
         } else if (le instanceof MichaelManasEntity) {
            return false;
         } else if (this.isAngelAlly(le)) {
            return false;
         } else if (!this.isAlliedTo(le) && !le.isAlliedTo(this)) {
            float dmg = this.hit();
            boolean ok = le.hurt(this.holySource(false, true), dmg);
            this.hurtSpiritual(le, dmg * 0.2F);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.HOSTILE, 2.0F, 0.9F);
            return ok;
         } else {
            return false;
         }
      } else {
         return super.doHurtTarget(target);
      }
   }

   private void handleFlyingAggro() {
      LivingEntity t = this.resolveLockedTarget();
      if (t == null) {
         t = this.getTarget();
      }

      if (t != null && t.isAlive()) {
         double dist = this.distanceTo(t);
         boolean shouldFly = dist > 4.0 || t.getY() > this.getY() + 1.5 || this.phase >= 2;
         if (!shouldFly) {
            if (this.isFlyingMode() && this.onGround()) {
               this.switchNavigator(true);
            }

            this.setNoGravity(false);
            if (!this.isFlyingMode()) {
               this.getNavigation().moveTo(t, this.phase >= 3 ? 1.25 : 1.1);
            }
         } else {
            if (!this.isFlyingMode()) {
               this.switchNavigator(false);
            }

            this.setNoGravity(true);
            this.navigation.stop();
            if (this.flyWanderCd > 0) {
               this.flyWanderCd--;
            }

            if (this.flyWanderCd <= 0) {
               this.flyWanderCd = this.phase >= 3 ? 16 : (this.phase == 2 ? 22 : 30);
               this.flyOrbitDir = this.random.nextBoolean() ? 1 : -1;
               float minR = this.phase >= 3 ? 4.5F : 6.0F;
               float maxR = this.phase >= 3 ? 8.0F : 10.0F;
               this.flyOrbitRadius = Mth.clamp(minR + this.random.nextFloat() * (maxR - minR), 4.0F, 12.0F);
            }

            boolean dive = dist > 14.0 || this.phase >= 3 && dist > 9.0;
            Vec3 wanted;
            if (dive) {
               Vec3 eye = t.getEyePosition();
               Vec3 back = t.getLookAngle().normalize().scale(-(this.phase >= 3 ? 2.0 : 3.5));
               double hoverY = t.getY() + (this.phase >= 3 ? 2.8 : 4.5);
               wanted = new Vec3(eye.x + back.x, hoverY, eye.z + back.z);
            } else {
               this.flyOrbitAngle = this.flyOrbitAngle + (this.phase >= 3 ? 0.23F : (this.phase == 2 ? 0.18F : 0.13F)) * this.flyOrbitDir;
               double ox = Math.cos(this.flyOrbitAngle) * this.flyOrbitRadius;
               double oz = Math.sin(this.flyOrbitAngle) * this.flyOrbitRadius;
               double hoverY = t.getY() + (this.phase >= 3 ? 2.5 : 4.0) + Math.sin(this.tickCount * 0.16) * (this.phase >= 3 ? 0.8 : 1.2);
               wanted = new Vec3(t.getX() + ox, hoverY, t.getZ() + oz);
            }

            double maxY = this.level().getMaxBuildHeight() - 8;
            wanted = new Vec3(wanted.x, Mth.clamp(wanted.y, this.getY() - 4.0, maxY), wanted.z);
            double speed = this.phase >= 3 ? 1.75 : (this.phase == 2 ? 1.45 : 1.2);
            this.getMoveControl().setWantedPosition(wanted.x, wanted.y, wanted.z, speed);
            this.getLookControl().setLookAt(t, 90.0F, 90.0F);
            if (dist <= (this.phase >= 3 ? 3.6 : 4.4) && this.atk == 0 && this.gcd <= 0) {
               this.doHurtTarget(t);
               this.gcd = this.phase >= 3 ? 6 : 10;
            }
         }
      } else {
         if (this.isFlyingMode() && this.onGround()) {
            this.switchNavigator(true);
         }

         this.setNoGravity(false);
      }
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("FeldPhase", this.phase);
      tag.putInt("FeldAtk", this.atk);
      tag.putInt("FeldAnimT", this.animT);
      tag.putInt("FeldGcd", this.gcd);
      tag.putInt("FeldAOS", this.antiOneShotTicks);
      tag.putInt("FeldIFr", this.hardIFrameTicks);
      tag.putInt("FeldAngelCd", this.angelCooldown);
      tag.putInt("FeldDashCd", this.dashCooldown);
      tag.putInt("FeldCommandCd", this.commandCooldown);
      tag.putInt("FeldSkyBeamCd", this.skyBeamCooldown);
      tag.putInt("FeldSpatialCd", this.spatialCooldown);
      tag.putInt("FeldFieldCd", this.fieldCooldown);
      tag.putInt("FeldLanceCd", this.lanceRainCooldown);
      tag.putInt("FeldPrisonCd", this.prisonCooldown);
      tag.putInt("FeldHeavenfallCd", this.heavenfallCooldown);
      int n = Math.min(16, this.angelIds.size());
      tag.putInt("FeldAngelN", n);

      for (int i = 0; i < n; i++) {
         tag.putUUID("FeldAngel_" + i, this.angelIds.get(i));
      }

      tag.putBoolean("FeldRegaliaDone", this.regaliaDone);
      tag.putBoolean("FeldMichaelRel", this.michaelReleased);
      if (this.michaelId != null) {
         tag.putUUID("FeldMichaelId", this.michaelId);
      }

      if (this.lockedTargetId != null) {
         tag.putUUID("FeldLockedTarget", this.lockedTargetId);
      }

      tag.putInt("FeldCapW", this.capWindowTicks);
      tag.putFloat("FeldCapD", this.capDamageTaken);
      tag.putInt("FeldCapI", this.capImmuneTicks);
      tag.putInt("FeldFlyCd", this.flyWanderCd);
      tag.putFloat("FeldFlyAng", this.flyOrbitAngle);
      tag.putFloat("FeldFlyRad", this.flyOrbitRadius);
      tag.putInt("FeldFlyDir", this.flyOrbitDir);
      tag.putBoolean("FeldFlyingMode", this.flyingMode);
      tag.putBoolean("FeldAwakenedHandOffDone", this.awakenedHandOffDone);
      tag.putBoolean("FeldPressureActive", this.pressureFieldActive);
      tag.putInt("FeldPressureTicks", this.pressureFieldTicks);
      tag.putInt("FeldPressureDuration", this.pressureFieldDuration);
      tag.putDouble("FeldPressureCX", this.pressureFieldCenter.x);
      tag.putDouble("FeldPressureCY", this.pressureFieldCenter.y);
      tag.putDouble("FeldPressureCZ", this.pressureFieldCenter.z);
      tag.putDouble("FeldPressureStartY", this.pressureFieldStartY);
      tag.putDouble("FeldPressureBottomY", this.pressureFieldBottomY);
      tag.putBoolean("FeldPressureImpact", this.pressureFieldImpactDone);
      tag.putBoolean("FeldAllowTrueDeath", this.allowTrueDeath);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.phase = tag.getInt("FeldPhase");
      this.atk = tag.getInt("FeldAtk");
      this.animT = tag.getInt("FeldAnimT");
      this.gcd = tag.getInt("FeldGcd");
      this.antiOneShotTicks = tag.getInt("FeldAOS");
      this.hardIFrameTicks = tag.getInt("FeldIFr");
      this.angelCooldown = tag.getInt("FeldAngelCd");
      this.dashCooldown = tag.getInt("FeldDashCd");
      this.commandCooldown = tag.getInt("FeldCommandCd");
      this.skyBeamCooldown = tag.getInt("FeldSkyBeamCd");
      this.spatialCooldown = tag.getInt("FeldSpatialCd");
      this.fieldCooldown = tag.getInt("FeldFieldCd");
      this.lanceRainCooldown = tag.getInt("FeldLanceCd");
      this.prisonCooldown = tag.getInt("FeldPrisonCd");
      this.heavenfallCooldown = tag.getInt("FeldHeavenfallCd");
      if (tag.contains("FeldAwakenedHandOffDone")) {
         this.awakenedHandOffDone = tag.getBoolean("FeldAwakenedHandOffDone");
      }

      this.angelIds.clear();
      int n = tag.getInt("FeldAngelN");

      for (int i = 0; i < n; i++) {
         String k = "FeldAngel_" + i;
         if (tag.hasUUID(k)) {
            this.angelIds.add(tag.getUUID(k));
         }
      }

      this.regaliaDone = tag.getBoolean("FeldRegaliaDone");
      this.michaelReleased = tag.getBoolean("FeldMichaelRel");
      if (tag.hasUUID("FeldMichaelId")) {
         this.michaelId = tag.getUUID("FeldMichaelId");
      }

      if (tag.hasUUID("FeldLockedTarget")) {
         this.lockedTargetId = tag.getUUID("FeldLockedTarget");
      }

      if (tag.contains("FeldCapW")) {
         this.capWindowTicks = tag.getInt("FeldCapW");
      }

      if (tag.contains("FeldCapD")) {
         this.capDamageTaken = tag.getFloat("FeldCapD");
      }

      if (tag.contains("FeldCapI")) {
         this.capImmuneTicks = tag.getInt("FeldCapI");
      }

      if (tag.contains("FeldFlyCd")) {
         this.flyWanderCd = tag.getInt("FeldFlyCd");
      }

      if (tag.contains("FeldFlyAng")) {
         this.flyOrbitAngle = tag.getFloat("FeldFlyAng");
      }

      if (tag.contains("FeldFlyRad")) {
         this.flyOrbitRadius = tag.getFloat("FeldFlyRad");
      }

      if (tag.contains("FeldFlyDir")) {
         this.flyOrbitDir = tag.getInt("FeldFlyDir");
      }

      if (tag.contains("FeldFlyingMode")) {
         this.switchNavigator(!tag.getBoolean("FeldFlyingMode"));
      }

      if (tag.contains("FeldPressureActive")) {
         this.pressureFieldActive = tag.getBoolean("FeldPressureActive");
      }

      if (tag.contains("FeldPressureTicks")) {
         this.pressureFieldTicks = tag.getInt("FeldPressureTicks");
      }

      if (tag.contains("FeldPressureDuration")) {
         this.pressureFieldDuration = tag.getInt("FeldPressureDuration");
      }

      if (tag.contains("FeldPressureCX")) {
         this.pressureFieldCenter = new Vec3(tag.getDouble("FeldPressureCX"), tag.getDouble("FeldPressureCY"), tag.getDouble("FeldPressureCZ"));
      }

      if (tag.contains("FeldPressureStartY")) {
         this.pressureFieldStartY = tag.getDouble("FeldPressureStartY");
      }

      if (tag.contains("FeldPressureBottomY")) {
         this.pressureFieldBottomY = tag.getDouble("FeldPressureBottomY");
      }

      if (tag.contains("FeldPressureImpact")) {
         this.pressureFieldImpactDone = tag.getBoolean("FeldPressureImpact");
      }

      if (tag.contains("FeldAllowTrueDeath")) {
         this.allowTrueDeath = tag.getBoolean("FeldAllowTrueDeath");
      }

      if (this.hasCustomName()) {
         this.bossEvent.setName(this.getDisplayName());
      }
   }

   private void spawnDeathshineColumn(ServerLevel sl, double x, double topY, double z, int layers, double spacing) {
      for (int i = 0; i < layers; i++) {
         double py = topY - i * spacing;
         sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), x, py, z, 1, 0.0, 0.0, 0.0, 0.0);
         if (i % 3 == 0) {
            sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), x, py, z, 1, 0.0, 0.1, 0.0, 0.0);
         }
      }
   }

   private void spawnDeathEnergyLightningBurst(ServerLevel sl, Vec3 center, float radius, int points, double yLevel) {
      for (int i = 0; i < points; i++) {
         double ang = (Math.PI * 2) / points * i;
         double x = center.x + Math.cos(ang) * radius;
         double z = center.z + Math.sin(ang) * radius;
         sl.sendParticles((SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(), x, yLevel, z, 1, 0.0, 0.0, 0.0, 0.0);
      }
   }

   private void spawnJudgementSmokeBeam(ServerLevel sl, double x, double topY, double bottomY, double z) {
      double step = 0.95;
      int layers = Math.max(1, Mth.ceil((topY - bottomY) / step));

      for (int i = 0; i <= layers; i++) {
         double py = topY - i * step;
         if (py < bottomY) {
            py = bottomY;
         }

         int count;
         double spread;
         double ySpeed;
         if (i == 0) {
            count = 14;
            spread = 1.0;
            ySpeed = 0.75;
         } else if (i < 4) {
            count = 10;
            spread = 0.7;
            ySpeed = 0.0;
         } else if (py - bottomY < 5.0) {
            count = 8;
            spread = 0.44;
            ySpeed = 0.0;
         } else {
            count = 6;
            spread = 0.3;
            ySpeed = 0.0;
         }

         for (int j = 0; j < count; j++) {
            double ox = (sl.random.nextDouble() - 0.5) * spread;
            double oz = (sl.random.nextDouble() - 0.5) * spread;
            sl.sendParticles((SimpleParticleType)ModParticles.JUDGEMENT_SMOKE.get(), x + ox, py, z + oz, 1, 0.0, ySpeed, 0.0, 0.0);
            if (j % 3 == 0) {
               sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), x + ox * 0.35, py, z + oz * 0.35, 1, 0.0, 0.05, 0.0, 0.0);
            }
         }

         if (py <= bottomY) {
            break;
         }
      }

      sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), x, bottomY + 5.0, z, 12, 0.2, 1.6, 0.2, 0.0);
      sl.sendParticles(ParticleTypes.FLASH, x, bottomY + 0.35, z, 2, 0.0, 0.0, 0.0, 0.0);
   }

   private void spawnDeathEnergyLightningCracks(ServerLevel sl, Vec3 center, float radius, int lines) {
      for (int i = 0; i < lines; i++) {
         double ang = sl.random.nextDouble() * Math.PI * 2.0;
         Vec3 dir = new Vec3(Math.cos(ang), 0.0, Math.sin(ang));
         Vec3 start = center.add(dir.scale(1.5));
         Vec3 end = center.add(dir.scale(radius));
         Vec3 diff = end.subtract(start);
         double len = diff.length();
         if (!(len <= 0.001)) {
            Vec3 norm = diff.normalize();

            for (double d = 0.0; d <= len; d += 1.15) {
               Vec3 p = start.add(norm.scale(d));
               double wobbleX = (sl.random.nextDouble() - 0.5) * 0.7;
               double wobbleZ = (sl.random.nextDouble() - 0.5) * 0.7;
               double py = center.y + 0.25 + sl.random.nextDouble() * (radius * 0.08);
               sl.sendParticles((SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(), p.x + wobbleX, py, p.z + wobbleZ, 1, 0.0, 0.0, 0.0, 0.0);
               if ((int)d % 2 == 0) {
                  sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), p.x + wobbleX * 0.5, py, p.z + wobbleZ * 0.5, 1, 0.0, 0.0, 0.0, 0.0);
               }
            }
         }
      }
   }

   private void tickSkyJudgementBeam() {
      if (this.level() instanceof ServerLevel sl) {
         if (this.skyJudgementTicks > 0) {
            this.skyJudgementTicks--;
            Vec3 center = this.skyJudgementCenter;
            float radius = this.skyJudgementRadius;
            float damage = this.skyJudgementDamage;
            float spirit = this.skyJudgementSpirit;
            if (this.skyJudgementTicks % 12 == 0) {
               sl.playSound(null, this.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.HOSTILE, 2.6F, 0.8F);
            }

            int strikesThisTick = this.phase >= 3 ? 3 : 2;

            for (int s = 0; s < strikesThisTick; s++) {
               double ang = sl.random.nextDouble() * Math.PI * 2.0;
               double rr = Math.sqrt(sl.random.nextDouble()) * radius;
               double sx = center.x + Math.cos(ang) * rr;
               double sz = center.z + Math.sin(ang) * rr;
               double topY = center.y + 58.0 + sl.random.nextDouble() * 12.0;
               double bottomY = center.y + 0.35;
               this.spawnJudgementSmokeBeam(sl, sx, topY, bottomY, sz);
               sl.sendParticles(ParticleTypes.FLASH, sx, center.y + 0.4, sz, 1, 0.15, 0.15, 0.15, 0.0);
               sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), sx, center.y + 6.0, sz, 8, 0.24, 3.0, 0.24, 0.0);
               AABB hit = new AABB(sx - 3.0, center.y - 3.0, sz - 3.0, sx + 3.0, center.y + 12.0, sz + 3.0);

               for (LivingEntity v : sl.getEntitiesOfClass(LivingEntity.class, hit, LivingEntity::isAlive)) {
                  if (v != this
                     && !(v instanceof Player p && (p.isCreative() || p.isSpectator()))
                     && !(v instanceof MichaelManasEntity)
                     && !this.isAngelAlly(v)
                     && !this.isAlliedTo(v)
                     && !v.isAlliedTo(this)) {
                     CompoundTag tag = v.getPersistentData();
                     int last = tag.getInt("FeldwaySkyJudgementHitTick");
                     if (last != this.tickCount) {
                        tag.putInt("FeldwaySkyJudgementHitTick", this.tickCount);
                        v.hurt(this.holySource(true, true), this.dmg(damage));
                        this.hurtSpiritual(v, spirit);

                        try {
                           v.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), 65, 2, false, false, true));
                        } catch (Throwable var27) {
                        }

                        try {
                           v.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION), 65, 1, false, false, true));
                        } catch (Throwable var26) {
                        }

                        v.setDeltaMovement(0.0, -0.55, 0.0);
                        v.hurtMarked = true;
                     }
                  }
               }
            }

            if (this.skyJudgementTicks % 10 == 0) {
               sl.sendParticles(
                  (SimpleParticleType)ModParticles.DEATHSHINE.get(), center.x, center.y + 10.0, center.z, 48, radius * 0.28, 10.0, radius * 0.28, 0.0
               );
               sl.sendParticles(
                  (SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(),
                  center.x,
                  center.y + 10.0,
                  center.z,
                  32,
                  radius * 0.24,
                  10.0,
                  radius * 0.24,
                  0.0
               );
            }
         }
      }
   }

   private void awakenIntoSecondBoss(DamageSource source) {
      if (this.level() instanceof ServerLevel sl) {
         if (!this.awakenedHandOffDone) {
            this.awakenedHandOffDone = true;
            FeldwayAwakeningEntity awakening = (FeldwayAwakeningEntity)((EntityType)ModEntityTypes.FELDWAY_AWAKENING.get()).create(sl);
            if (awakening != null) {
               awakening.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
               LivingEntity target = this.resolveLockedTarget();
               if (target == null) {
                  target = this.getTarget();
               }

               if (target != null && target.isAlive()) {
                  awakening.setAwakeningTarget(target.getUUID());
               } else if (source != null && source.getEntity() instanceof LivingEntity attacker && this.isValidTrueSightTarget(attacker)) {
                  awakening.setAwakeningTarget(attacker.getUUID());
               }

               sl.addFreshEntity(awakening);
               sl.playSound(null, this.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 5.0F, 0.55F);
               sl.playSound(null, this.blockPosition(), SoundEvents.WARDEN_ROAR, SoundSource.HOSTILE, 5.5F, 0.65F);
               sl.sendParticles(ParticleTypes.FLASH, this.getX(), this.getY() + this.getBbHeight() * 0.55, this.getZ(), 18, 1.4, 1.4, 1.4, 0.0);
               sl.sendParticles(
                  (SimpleParticleType)ModParticles.DEATHSHINE.get(), this.getX(), this.getY() + this.getBbHeight() * 0.55, this.getZ(), 120, 1.8, 1.2, 1.8, 0.0
               );
               sl.sendParticles(
                  (SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(),
                  this.getX(),
                  this.getY() + this.getBbHeight() * 0.55,
                  this.getZ(),
                  140,
                  2.0,
                  1.4,
                  2.0,
                  0.0
               );
               this.bossEvent.removeAllPlayers();
               this.discard();
            }
         }
      }
   }

   private void tickPressureField() {
      if (this.level() instanceof ServerLevel sl) {
         if (this.pressureFieldActive) {
            this.pressureFieldTicks++;
            Vec3 center = this.pressureFieldCenter;
            double startY = this.pressureFieldStartY;
            double bottomY = this.pressureFieldBottomY;
            double progress = Math.min(1.0, (double)this.pressureFieldTicks / Math.max(1, this.pressureFieldDuration));
            double eased = progress * progress * (3.0 - 2.0 * progress);
            double headY = startY - (startY - bottomY) * eased;
            if (headY < bottomY) {
               headY = bottomY;
            }

            double trailLength = 36.0;
            double trailBottom = Math.max(bottomY, headY - trailLength);

            for (double py = headY; py >= trailBottom; py -= 0.34) {
               double t = (headY - py) / Math.max(0.001, headY - trailBottom);
               double outerRadius = 1.95 - t * 0.18;
               double midRadius = 1.05 - t * 0.1;
               double coreRadius = 0.3;
               int outerCount = py >= headY - 1.1 ? 26 : 14;
               int midCount = py >= headY - 1.1 ? 16 : 9;
               int coreCount = py >= headY - 1.1 ? 12 : 7;

               for (int j = 0; j < outerCount; j++) {
                  double ang = sl.random.nextDouble() * Math.PI * 2.0;
                  double rr = Math.sqrt(sl.random.nextDouble()) * outerRadius;
                  double px = center.x + Math.cos(ang) * rr;
                  double pz = center.z + Math.sin(ang) * rr;
                  sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), px, py, pz, 1, 0.0, py >= headY - 0.7 ? 0.96 : 0.0, 0.0, 0.0);
               }

               for (int j = 0; j < midCount; j++) {
                  double ang = sl.random.nextDouble() * Math.PI * 2.0;
                  double rr = Math.sqrt(sl.random.nextDouble()) * midRadius;
                  double px = center.x + Math.cos(ang) * rr;
                  double pz = center.z + Math.sin(ang) * rr;
                  sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), px, py, pz, 1, 0.0, py >= headY - 0.7 ? 0.82 : 0.0, 0.0, 0.0);
                  if (j % 3 == 0) {
                     sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), px, py, pz, 1, 0.0, 0.0, 0.0, 0.0);
                  }
               }

               for (int j = 0; j < coreCount; j++) {
                  double ang = sl.random.nextDouble() * Math.PI * 2.0;
                  double rr = Math.sqrt(sl.random.nextDouble()) * coreRadius;
                  double px = center.x + Math.cos(ang) * rr;
                  double pz = center.z + Math.sin(ang) * rr;
                  sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), px, py, pz, 1, 0.0, py >= headY - 0.7 ? 1.08 : 0.0, 0.0, 0.0);
               }

               if (py >= headY - 1.0) {
                  double ringRadius = 0.55;

                  for (int deg = 0; deg < 360; deg += 24) {
                     double a = Math.toRadians(deg + this.pressureFieldTicks * 9.0);
                     double px = center.x + Math.cos(a) * ringRadius;
                     double pz = center.z + Math.sin(a) * ringRadius;
                     sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), px, py, pz, 1, 0.0, 1.0, 0.0, 0.0);
                     if (deg % 48 == 0) {
                        sl.sendParticles((SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(), px, py + 0.05, pz, 1, 0.0, 0.0, 0.0, 0.0);
                     }
                  }
               }
            }

            if (this.pressureFieldTicks % 2 == 0) {
               for (int i = 0; i < 28; i++) {
                  double ang = sl.random.nextDouble() * Math.PI * 2.0;
                  double rr = Math.sqrt(sl.random.nextDouble()) * 1.8;
                  double px = center.x + Math.cos(ang) * rr;
                  double pz = center.z + Math.sin(ang) * rr;
                  sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), px, headY + 0.45, pz, 1, 0.0, 1.08, 0.0, 0.0);
               }
            }

            if (this.pressureFieldTicks % 6 == 0) {
               sl.playSound(null, this.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.HOSTILE, 1.9F, 0.72F);
            }

            if (!this.pressureFieldImpactDone && headY <= bottomY + 0.4) {
               this.pressureFieldImpactDone = true;

               for (int ring = 0; ring < 6; ring++) {
                  double r = 0.9 + ring * 0.7;
                  double y = bottomY + 0.12 + ring * 0.05;

                  for (int deg = 0; deg < 360; deg += 8) {
                     double a = Math.toRadians(deg + ring * 6.0);
                     double px = center.x + Math.cos(a) * r;
                     double pz = center.z + Math.sin(a) * r;
                     sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), px, y, pz, 1, 0.0, 0.08, 0.0, 0.0);
                     if (deg % 24 == 0) {
                        sl.sendParticles((SimpleParticleType)ModParticles.DEATHSHINE.get(), px, y, pz, 1, 0.0, 0.0, 0.0, 0.0);
                     }
                  }
               }

               sl.sendParticles((SimpleParticleType)ModParticles.HOLY_SMOKE_BEAM.get(), center.x, bottomY + 0.3, center.z, 90, 0.28, 0.1, 0.28, 0.012);
               sl.sendParticles((SimpleParticleType)ModParticles.DEATH_ENERGY_LIGHTNING.get(), center.x, bottomY + 0.35, center.z, 70, 0.8, 0.16, 0.8, 0.0);
               sl.sendParticles(ParticleTypes.FLASH, center.x, bottomY + 0.7, center.z, 3, 0.06, 0.05, 0.06, 0.0);
               sl.playSound(null, this.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 3.1F, 0.68F);
               sl.playSound(null, this.blockPosition(), (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 2.0F, 0.9F);
               AABB box = new AABB(center.x - 22.0, center.y - 5.0, center.z - 22.0, center.x + 22.0, center.y + 16.0, center.z + 22.0);

               for (LivingEntity e : this.level().getEntitiesOfClass(LivingEntity.class, box, LivingEntity::isAlive)) {
                  if (e != this
                     && !(e instanceof Player p && (p.isCreative() || p.isSpectator()))
                     && !(e instanceof MichaelManasEntity)
                     && !this.isAngelAlly(e)
                     && !this.isAlliedTo(e)
                     && !e.isAlliedTo(this)) {
                     try {
                        e.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), 80, 2, false, false, true));
                     } catch (Throwable var41) {
                     }

                     try {
                        e.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION), 80, 1, false, false, true));
                     } catch (Throwable var40) {
                     }

                     try {
                        e.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), 70, 2, false, false, true));
                     } catch (Throwable var39) {
                     }

                     Vec3 push = e.position().subtract(center);
                     double len = Math.max(0.001, Math.sqrt(push.x * push.x + push.z * push.z));
                     double var10002 = push.x / len;
                     double var10001 = e.getDeltaMovement().x + var10002 * 1.05;
                     var10002 = Math.max(e.getDeltaMovement().y, 0.55);
                     double var10004 = push.z / len;
                     e.setDeltaMovement(var10001, var10002, e.getDeltaMovement().z + var10004 * 1.05);
                     e.hurtMarked = true;
                     e.hurt(
                        this.holySource(true, true), this.dmg(this.phase >= 3 ? cfgFloat(cfg().heavenfallDamagePhaseThree) : cfgFloat(cfg().heavenfallDamage))
                     );
                     this.hurtSpiritual(e, this.phase >= 3 ? cfgFloat(cfg().heavenfallSpiritualDamagePhaseThree) : cfgFloat(cfg().heavenfallSpiritualDamage));
                  }
               }
            }

            if (this.pressureFieldTicks >= this.pressureFieldDuration + 6) {
               this.pressureFieldActive = false;
               this.pressureFieldTicks = 0;
               this.pressureFieldDuration = 0;
               this.pressureFieldImpactDone = false;
               this.pressureFieldCenter = Vec3.ZERO;
               this.pressureFieldStartY = 0.0;
               this.pressureFieldBottomY = 0.0;
            }
         }
      }
   }

   private float trueDeathThresholdHealth() {
      return this.getMaxHealth() * cfgFloat(cfg().trueDeathHpPercent);
   }

   private float michaelReleaseThresholdHealth() {
      return this.getMaxHealth() * cfgFloat(cfg().michaelReleaseHpPercent);
   }

   private boolean isAtTrueDeathThreshold(float health) {
      return health <= this.trueDeathThresholdHealth();
   }

   private void removeDeathBlockingEffects() {
      try {
         this.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION));
      } catch (Throwable var2) {
      }
   }

   private float maxDamagePerHit() {
      int a = this.angelCountClamped();
      float cap = this.getMaxHealth() * cfgFloat(cfg().maxDamagePerHitPercent);
      float mult = 1.0F - 0.06F * a;
      if (mult < 0.55F) {
         mult = 0.55F;
      }

      return Math.max(1.0F, cap * mult);
   }

   private float emergencyDamageCap() {
      return Math.max(1.0F, this.getMaxHealth() * cfgFloat(cfg().maxEmergencyDamagePercent));
   }

   private float trueDeathFloor() {
      return Math.max(1.0F, this.trueDeathThresholdHealth());
   }

   private void refreshLastSafeHealth() {
      if (this.tickCount != this.lastSafeHealthTick) {
         this.lastSafeHealthTick = this.tickCount;
         float hp = this.getHealth();
         if (hp > 0.0F && !Float.isNaN(hp) && !Float.isInfinite(hp)) {
            this.lastSafeHealth = hp;
         }
      }
   }

   private static class FeldwayCombatGoal extends Goal {
      private final FeldwayEntity b;

      FeldwayCombatGoal(FeldwayEntity b) {
         this.b = b;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         LivingEntity t = this.b.resolveLockedTarget();
         return t != null && t.isAlive() ? true : this.b.getTarget() != null && this.b.getTarget().isAlive();
      }

      public void tick() {
         LivingEntity t = this.b.resolveLockedTarget();
         if (t == null || !t.isAlive()) {
            t = this.b.getTarget();
         }

         if (t != null && t.isAlive()) {
            this.b.getLookControl().setLookAt(t, 85.0F, 85.0F);
            double dist = this.b.distanceTo(t);
            if (this.b.atk == 0) {
               if (this.b.gcd <= 0) {
                  if (this.b.phase == 1 && !this.b.regaliaDone && this.b.getHealth() <= this.b.getMaxHealth() * 0.7F && this.b.random.nextFloat() < 0.05F) {
                     this.b.regaliaDone = true;
                     this.b.startRegaliaDominion();
                  } else if (this.b.angelCooldown <= 0 && this.b.angelCountClamped() < 2 && this.b.random.nextFloat() < 0.035F) {
                     this.b.summonAngels(1);
                     this.b.gcd = 10;
                  } else if (this.b.phase >= 3 && this.b.skyBeamCooldown <= 0 && this.b.random.nextFloat() < 0.22F) {
                     this.b.atk = 5;
                     this.b.animT = 0;
                  } else if (this.b.phase >= 2 && this.b.heavenfallCooldown <= 0 && this.b.random.nextFloat() < 0.1F) {
                     this.b.atk = 9;
                     this.b.animT = 0;
                  } else if (this.b.phase >= 2 && this.b.lanceRainCooldown <= 0 && this.b.random.nextFloat() < 0.12F) {
                     this.b.atk = 7;
                     this.b.animT = 0;
                  } else if (this.b.phase >= 2 && this.b.spatialCooldown <= 0 && this.b.random.nextFloat() < 0.13F) {
                     this.b.atk = 4;
                     this.b.animT = 0;
                  } else if (this.b.prisonCooldown <= 0 && dist <= 20.0 && this.b.random.nextFloat() < 0.11F) {
                     this.b.atk = 8;
                     this.b.animT = 0;
                  } else {
                     float r = this.b.random.nextFloat();
                     if (dist < 12.0 && this.b.dashCooldown <= 0 && r < 0.24F) {
                        this.b.atk = 1;
                        this.b.animT = 0;
                     } else if (this.b.commandCooldown <= 0 && r < 0.42F) {
                        this.b.atk = 2;
                        this.b.animT = 0;
                     } else if (this.b.fieldCooldown <= 0 && r < 0.64F) {
                        this.b.atk = 3;
                        this.b.animT = 0;
                     } else {
                        this.b.getNavigation().moveTo(t, 1.1);
                        if (dist <= 5.0) {
                           this.b.doHurtTarget(t);
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
