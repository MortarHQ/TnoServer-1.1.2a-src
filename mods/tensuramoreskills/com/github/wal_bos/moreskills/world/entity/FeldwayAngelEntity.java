package com.github.wal_bos.moreskills.world.entity;

import com.github.wal_bos.moreskills.registry.entity.ModEntityTypes;
import com.mojang.datafixers.util.Pair;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.enchantment.TensuraEnchantmentHelper;
import io.github.manasmods.tensura.enchantment.TensuraEnchantments;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.magic.barrier.HolyFieldEntity;
import io.github.manasmods.tensura.entity.magic.beam.SolarBeamProjectile;
import io.github.manasmods.tensura.entity.magic.beam.SpatialRayProjectile;
import io.github.manasmods.tensura.entity.projectile.LightArrowProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.DimensionCutProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.SolarGrenadeProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.WindBladeProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.WindSphereProjectile;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.item.TensuraArmorItems;
import io.github.manasmods.tensura.registry.item.TensuraToolItems;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class FeldwayAngelEntity extends PathfinderMob {
   private static final String TAG_INIT = "FeldAngelInit";
   private static final String TAG_FORCED_TARGET = "ForcedTarget";
   private static final List<Holder<MobEffect>> IMMUNE = buildImmuneList();
   private UUID masterUUID;
   private int solarRainCd;
   private int solarWaveCd;
   private int healCd;
   private int blinkCd;
   private int meleeCd;
   private int holyFieldCd;
   private int solarBeamCd;
   private int flashBurstCd;
   private int windBladeCd;
   private int windSphereCd;
   private int spatialRayCd;
   private int dimensionCutCd;
   private int ownerRegenTick;
   private int ownerMagiculeTick;
   private int forcedTargetTicks;

   public FeldwayAngelEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
      this.setPersistenceRequired();
      this.noCulling = true;
      this.setNoGravity(true);
      this.moveControl = new FeldwayAngelEntity.AngelMoveControl(this);
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 800.0)
         .add(Attributes.ARMOR, 80.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.95)
         .add(Attributes.MOVEMENT_SPEED, 0.3)
         .add(Attributes.FLYING_SPEED, 0.62)
         .add(Attributes.ATTACK_DAMAGE, 120.0)
         .add(Attributes.FOLLOW_RANGE, 96.0);
   }

   public void setMaster(Mob master) {
      this.masterUUID = master == null ? null : master.getUUID();
   }

   public void setMasterEntity(Entity master) {
      this.masterUUID = master == null ? null : master.getUUID();
   }

   public Entity getMasterEntity() {
      if (this.level() instanceof ServerLevel sl) {
         return this.masterUUID == null ? null : sl.getEntity(this.masterUUID);
      } else {
         return null;
      }
   }

   public Mob getMaster() {
      return this.getMasterEntity() instanceof Mob m ? m : null;
   }

   public boolean isMaster(Entity entity) {
      return entity != null && this.masterUUID != null && entity.getUUID().equals(this.masterUUID);
   }

   public void applySummonedLoadout() {
      this.equipAngelLoadout();
   }

   public void commandAttackTarget(LivingEntity target) {
      if (target != null && target.isAlive()) {
         if (this.shouldAttack(target)) {
            this.getPersistentData().putUUID("ForcedTarget", target.getUUID());
            this.forcedTargetTicks = 600;
            this.setTarget(target);
         }
      }
   }

   private LivingEntity getForcedTarget() {
      if (this.level() instanceof ServerLevel sl) {
         CompoundTag tag = this.getPersistentData();
         if (!tag.hasUUID("ForcedTarget")) {
            return null;
         } else {
            return sl.getEntity(tag.getUUID("ForcedTarget")) instanceof LivingEntity living && living.isAlive() ? living : null;
         }
      } else {
         return null;
      }
   }

   private void clearForcedTarget() {
      this.getPersistentData().remove("ForcedTarget");
      this.forcedTargetTicks = 0;
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData) {
      SpawnGroupData data = super.finalizeSpawn(level, difficulty, reason, spawnData);
      this.equipAngelLoadout();
      return data;
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

   private void applyTensuraEngravingsNoTsukumogami(ItemStack stack) {
      if (stack != null && !stack.isEmpty()) {
         stack.enchant(TensuraEnchantmentHelper.getEnchantment(this.level(), TensuraEnchantments.BARRIER_PIERCING), 5);
         stack.enchant(TensuraEnchantmentHelper.getEnchantment(this.level(), TensuraEnchantments.BREATHING_SUPPORT), 1);
         stack.enchant(TensuraEnchantmentHelper.getEnchantment(this.level(), TensuraEnchantments.CRUSHING), 1);
         stack.enchant(TensuraEnchantmentHelper.getEnchantment(this.level(), TensuraEnchantments.ELEMENTAL_BOOST), 1);
         stack.enchant(TensuraEnchantmentHelper.getEnchantment(this.level(), TensuraEnchantments.ELEMENTAL_RESISTANCE), 1);
         stack.enchant(TensuraEnchantmentHelper.getEnchantment(this.level(), TensuraEnchantments.ENERGY_STEAL), 2);
         stack.enchant(TensuraEnchantmentHelper.getEnchantment(this.level(), TensuraEnchantments.SEVERANCE), 1);
         stack.enchant(TensuraEnchantmentHelper.getEnchantment(this.level(), TensuraEnchantments.SLOTTING), 1);
         stack.enchant(TensuraEnchantmentHelper.getEnchantment(this.level(), TensuraEnchantments.SOUL_EATER), 1);
         stack.enchant(TensuraEnchantmentHelper.getEnchantment(this.level(), TensuraEnchantments.STURDY), 1);
         stack.enchant(TensuraEnchantmentHelper.getEnchantment(this.level(), TensuraEnchantments.SWIFT), 1);
         stack.enchant(TensuraEnchantmentHelper.getEnchantment(this.level(), TensuraEnchantments.MAGIC_INTERFERENCE), 1);
      }
   }

   private void equipAngelLoadout() {
      ItemStack sword = new ItemStack((ItemLike)TensuraToolItems.HIHIIROKANE_SWORD.get());
      this.applyTensuraEngravingsNoTsukumogami(sword);
      this.setItemSlot(EquipmentSlot.MAINHAND, sword);
      this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
      ItemStack chest = new ItemStack((ItemLike)TensuraArmorItems.HOLY_ARMAMENTS_CHESTPLATE.get());
      ItemStack legs = new ItemStack((ItemLike)TensuraArmorItems.HOLY_ARMAMENTS_LEGGINGS.get());
      ItemStack boots = new ItemStack((ItemLike)TensuraArmorItems.HOLY_ARMAMENTS_BOOTS.get());
      this.applyTensuraEngravingsNoTsukumogami(chest);
      this.applyTensuraEngravingsNoTsukumogami(legs);
      this.applyTensuraEngravingsNoTsukumogami(boots);
      this.setItemSlot(EquipmentSlot.CHEST, chest);
      this.setItemSlot(EquipmentSlot.LEGS, legs);
      this.setItemSlot(EquipmentSlot.FEET, boots);
      this.setDropChance(EquipmentSlot.CHEST, 0.0F);
      this.setDropChance(EquipmentSlot.LEGS, 0.0F);
      this.setDropChance(EquipmentSlot.FEET, 0.0F);
      this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
      this.setDropChance(EquipmentSlot.HEAD, 0.0F);
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
      return list;
   }

   private static void addImmune(List<Holder<MobEffect>> out, RegistrySupplier<MobEffect> effect) {
      try {
         out.add(TensuraMobEffects.getReference(effect));
      } catch (Throwable var3) {
      }
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

   public boolean canBeAffected(MobEffectInstance instance) {
      return instance != null && !this.isImmune(instance.getEffect());
   }

   public boolean addEffect(MobEffectInstance instance, Entity source) {
      if (instance == null) {
         return false;
      } else {
         return this.isImmune(instance.getEffect()) ? false : super.addEffect(instance, source);
      }
   }

   private void cleanseImmuneEffects() {
      if (!this.level().isClientSide()) {
         for (Holder<MobEffect> effect : IMMUNE) {
            try {
               if (this.hasEffect(effect)) {
                  this.removeEffect(effect);
               }
            } catch (Throwable var4) {
            }
         }
      }
   }

   private boolean isMichael(Entity e) {
      return e instanceof MichaelManasEntity || e != null && e.getType() == ModEntityTypes.MICHAEL_MANAS.get();
   }

   private boolean isFeldway(Entity e) {
      return e instanceof FeldwayEntity || e != null && e.getType() == ModEntityTypes.FELDWAY.get();
   }

   private boolean isHealTarget(Entity e) {
      return this.isMichael(e) || this.isFeldway(e);
   }

   private boolean hasMobMaster() {
      return this.getMasterEntity() instanceof Mob;
   }

   private boolean isPlayerControlledPassive() {
      return this.getMasterEntity() instanceof Player;
   }

   private boolean canUseAutonomousAggro() {
      return this.hasMobMaster();
   }

   private boolean shouldAttack(LivingEntity target) {
      if (target == null || !target.isAlive()) {
         return false;
      } else if (target == this) {
         return false;
      } else if (this.isAlliedTo(target) || target.isAlliedTo(this)) {
         return false;
      } else {
         return !this.isMichael(target) && !this.isFeldway(target) ? !(target instanceof Player p && (p.getAbilities().instabuild || p.isSpectator())) : false;
      }
   }

   private LivingEntity getPlayerMasterPriorityTarget(Player player) {
      LivingEntity target = player.getLastHurtMob();
      if (this.shouldAttack(target)) {
         return target;
      }

      target = player.getLastHurtByMob();
      return this.shouldAttack(target) ? target : null;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new FeldwayAngelEntity.AngelSupportGoal(this));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 10.0F));
      this.targetSelector.addGoal(1, new FeldwayAngelEntity.AngelOwnerHurtByTargetGoal(this));
      this.targetSelector.addGoal(2, new FeldwayAngelEntity.AngelOwnerHurtTargetGoal(this));
      this.targetSelector.addGoal(3, new FeldwayAngelEntity.AngelSelfDefendGoal(this));
      this.targetSelector.addGoal(4, new FeldwayAngelEntity.AngelMobMasterTargetGoal(this));
   }

   public boolean isAlliedTo(Entity e) {
      if (super.isAlliedTo(e)) {
         return true;
      }

      if (e == null) {
         return false;
      }

      if (e instanceof FeldwayAngelEntity) {
         return true;
      }

      if (this.isFeldway(e)) {
         return true;
      }

      if (this.isMichael(e)) {
         return true;
      }

      if (this.isMaster(e)) {
         return true;
      }

      Entity masterEntity = this.getMasterEntity();
      if (masterEntity != null) {
         if (e.getUUID().equals(masterEntity.getUUID())) {
            return true;
         }

         if (e.isAlliedTo(masterEntity)) {
            return true;
         }

         if (masterEntity.isAlliedTo(e)) {
            return true;
         }
      }

      return false;
   }

   public boolean removeWhenFarAway(double distance) {
      return false;
   }

   public boolean hurt(DamageSource source, float amount) {
      try {
         Entity a = source.getEntity();
         Entity d = source.getDirectEntity();
         Entity master = this.getMasterEntity();
         if (master != null) {
            if (a == master || d == master) {
               return false;
            }

            if (a != null && a.getUUID().equals(master.getUUID())) {
               return false;
            }

            if (d != null && d.getUUID().equals(master.getUUID())) {
               return false;
            }
         }

         if (a != null && (this.isAlliedTo(a) || a.isAlliedTo(this))) {
            return false;
         }

         if (d != null && (this.isAlliedTo(d) || d.isAlliedTo(this))) {
            return false;
         }
      } catch (Throwable var6) {
      }

      float cap = Math.max(1.0F, this.getMaxHealth() * 0.2F);
      return super.hurt(source, Math.min(amount, cap));
   }

   public boolean doHurtTarget(Entity target) {
      if (target instanceof LivingEntity living) {
         if (!living.isAlive()) {
            return false;
         }

         if (this.isAlliedTo(living) || living.isAlliedTo(this)) {
            return false;
         }

         if (this.isMichael(living) || this.isFeldway(living)) {
            return false;
         }

         if (this.meleeCd > 0) {
            return false;
         }

         this.meleeCd = 12;
         boolean ok = super.doHurtTarget(target);
         if (ok) {
            living.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SOUL_DRAIN), 50, 0, false, false, true), this);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.HOSTILE, 1.0F, 1.0F);
         }

         return ok;
      } else {
         return false;
      }
   }

   private void initOnce() {
      CompoundTag tag = this.getPersistentData();
      if (!tag.getBoolean("FeldAngelInit")) {
         this.equipAngelLoadout();
         tag.putBoolean("FeldAngelInit", true);
      }
   }

   private void supportSummonerRegen() {
      if (!this.level().isClientSide()) {
         if (this.getMasterEntity() instanceof Player owner && owner.isAlive()) {
            if (this.ownerRegenTick > 0) {
               this.ownerRegenTick--;
            } else {
               this.ownerRegenTick = 20;

               try {
                  owner.addEffect(
                     new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION), 40, 1, false, false, false), this
                  );
               } catch (Throwable var3) {
               }
            }
         }
      }
   }

   private void supportSummonerMagicule() {
      if (!this.level().isClientSide()) {
         if (this.getMasterEntity() instanceof Player owner && owner.isAlive()) {
            if (this.ownerMagiculeTick > 0) {
               this.ownerMagiculeTick--;
            } else {
               this.ownerMagiculeTick = 20;

               try {
                  IExistence existence = TensuraStorages.getExistenceFrom(owner);
                  existence.setMagicule(existence.getMagicule() + 3.5E7);
                  existence.markDirty();
               } catch (Throwable var3) {
               }
            }
         }
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         this.initOnce();
         this.cleanseImmuneEffects();
         this.setNoGravity(true);
         this.supportSummonerRegen();
         this.supportSummonerMagicule();
         if (this.solarRainCd > 0) {
            this.solarRainCd--;
         }

         if (this.solarWaveCd > 0) {
            this.solarWaveCd--;
         }

         if (this.healCd > 0) {
            this.healCd--;
         }

         if (this.blinkCd > 0) {
            this.blinkCd--;
         }

         if (this.meleeCd > 0) {
            this.meleeCd--;
         }

         if (this.holyFieldCd > 0) {
            this.holyFieldCd--;
         }

         if (this.solarBeamCd > 0) {
            this.solarBeamCd--;
         }

         if (this.flashBurstCd > 0) {
            this.flashBurstCd--;
         }

         if (this.windBladeCd > 0) {
            this.windBladeCd--;
         }

         if (this.windSphereCd > 0) {
            this.windSphereCd--;
         }

         if (this.spatialRayCd > 0) {
            this.spatialRayCd--;
         }

         if (this.dimensionCutCd > 0) {
            this.dimensionCutCd--;
         }

         if (this.forcedTargetTicks > 0) {
            this.forcedTargetTicks--;
         } else if (this.forcedTargetTicks == 0) {
            this.clearForcedTarget();
         }

         LivingEntity forcedTarget = this.getForcedTarget();
         if (forcedTarget != null && this.shouldAttack(forcedTarget)) {
            this.setTarget(forcedTarget);
         } else if (forcedTarget != null) {
            this.clearForcedTarget();
         }

         Entity masterEntity = this.getMasterEntity();
         if (forcedTarget == null) {
            if (masterEntity instanceof Mob master && master.isAlive()) {
               LivingEntity mt = master.getTarget();
               if (this.shouldAttack(mt)) {
                  this.setTarget(mt);
               } else {
                  LivingEntity current = this.getTarget();
                  if (current != null && (!current.isAlive() || this.isAlliedTo(current) || this.isMichael(current) || this.isFeldway(current))) {
                     this.setTarget(null);
                  }
               }
            } else if (masterEntity instanceof Player player && player.isAlive()) {
               LivingEntity preferred = this.getPlayerMasterPriorityTarget(player);
               if (preferred != null) {
                  this.setTarget(preferred);
               } else {
                  LivingEntity current = this.getTarget();
                  if (current != null) {
                     boolean keep = current.isAlive() && this.shouldAttack(current);
                     if (!keep) {
                        this.setTarget(null);
                     }
                  }
               }
            } else {
               this.setTarget(null);
            }
         }

         if (this.level() instanceof ServerLevel sl && (this.tickCount & 1) == 0) {
            sl.sendParticles(ParticleTypes.END_ROD, this.getX(), this.getY() + this.getBbHeight() * 0.6, this.getZ(), 2, 0.22, 0.22, 0.22, 0.01);
            if (this.tickCount % 8 == 0) {
               sl.sendParticles(ParticleTypes.ENCHANT, this.getX(), this.getY() + 0.9, this.getZ(), 4, 0.35, 0.18, 0.35, 0.04);
            }
         }
      }
   }

   private LivingEntity findBestHealTarget() {
      AABB box = this.getBoundingBox().inflate(26.0);
      List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, box, e -> e.isAlive() && this.isAlliedTo(e) && this.isHealTarget(e));
      LivingEntity best = null;
      double bestMissing = 0.0;

      for (LivingEntity ally : list) {
         double missing = ally.getMaxHealth() - ally.getHealth();
         if (ally.getHealth() < ally.getMaxHealth() * 0.95F && missing > bestMissing) {
            bestMissing = missing;
            best = ally;
         }
      }

      return best;
   }

   private void blinkNear(LivingEntity target) {
      if (this.level() instanceof ServerLevel sl) {
         if (target != null && target.isAlive()) {
            if (this.blinkCd <= 0) {
               this.blinkCd = 28;
               double r = 3.0;
               double ox = (sl.random.nextDouble() - 0.5) * r * 2.0;
               double oz = (sl.random.nextDouble() - 0.5) * r * 2.0;
               double oy = target.getBbHeight() * (0.25 + sl.random.nextDouble() * 0.25);
               Vec3 pos = target.position().add(ox, oy, oz);
               this.teleportTo(pos.x, pos.y, pos.z);
               sl.sendParticles(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 1, 0.0, 0.0, 0.0, 0.0);
               sl.sendParticles(ParticleTypes.END_ROD, pos.x, pos.y, pos.z, 10, 0.25, 0.25, 0.25, 0.02);
               sl.playSound(null, this.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 0.7F, 1.45F);
            }
         }
      }
   }

   private void castLowHeal(LivingEntity ally) {
      if (this.level() instanceof ServerLevel sl) {
         if (ally != null && ally.isAlive()) {
            if (this.isMichael(ally) || this.isFeldway(ally)) {
               if (this.healCd <= 0) {
                  this.healCd = 120;
                  this.showCircle(MagicCircleVariant.RECOVERY, 0.5F, 25, 0.75F, Vec3.ZERO);
                  ally.heal(50.0F);
                  ally.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.REINFORCEMENT), 80, 0, false, false, true), this);
                  sl.sendParticles(
                     ParticleTypes.END_ROD,
                     ally.getX(),
                     ally.getY() + ally.getBbHeight() * 0.6,
                     ally.getZ(),
                     12,
                     ally.getBbWidth() * 0.3,
                     ally.getBbHeight() * 0.25,
                     ally.getBbWidth() * 0.3,
                     0.02
                  );
                  sl.sendParticles(
                     ParticleTypes.ENCHANT,
                     ally.getX(),
                     ally.getY() + ally.getBbHeight() * 0.7,
                     ally.getZ(),
                     18,
                     ally.getBbWidth() * 0.35,
                     ally.getBbHeight() * 0.3,
                     ally.getBbWidth() * 0.35,
                     0.05
                  );
                  this.level()
                     .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_HEAL.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
               }
            }
         }
      }
   }

   private void castSolarRain(LivingEntity target) {
      if (target != null && target.isAlive()) {
         if (!this.isAlliedTo(target) && !target.isAlliedTo(this)) {
            if (!this.isMichael(target) && !this.isFeldway(target)) {
               if (this.solarRainCd <= 0) {
                  this.solarRainCd = 52;
                  this.showCircle(MagicCircleVariant.LIGHT, 0.95F, 25, 0.75F, Vec3.ZERO);
                  int count = 6;
                  int step = 360 / count;
                  Vec3 center = target.getEyePosition();

                  for (int i = 0; i < count; i++) {
                     double angle = Math.toRadians(i * step);
                     Vec3 spawn = this.getEyePosition().add(Math.cos(angle) * 2.1, 1.9, Math.sin(angle) * 2.1);
                     LightArrowProjectile arrow = new LightArrowProjectile(this.level(), this);
                     arrow.setSpeed(1.6F);
                     arrow.setPos(spawn.x, spawn.y, spawn.z);
                     arrow.setLife(45);
                     arrow.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.35F);
                     arrow.setPiercingBlock(true);
                     arrow.setIgnoreInvulnerabilityOnHit(true);
                     arrow.shootFromRot(center.subtract(spawn).normalize());
                     this.level().addFreshEntity(arrow);
                  }

                  this.level()
                     .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.CAST_LIGHT.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
               }
            }
         }
      }
   }

   private void castSolarWave(LivingEntity target) {
      if (target != null && target.isAlive()) {
         if (!this.isAlliedTo(target) && !target.isAlliedTo(this)) {
            if (!this.isMichael(target) && !this.isFeldway(target)) {
               if (this.solarWaveCd <= 0) {
                  this.solarWaveCd = 85;
                  this.showCircle(MagicCircleVariant.LIGHT, 1.0F, 25, 0.75F, Vec3.ZERO);
                  SolarGrenadeProjectile grenade = new SolarGrenadeProjectile(this.level(), this);
                  grenade.setSpeed(0.9F);
                  grenade.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0F);
                  grenade.setEffectRange(4.0F);
                  Vec3 spawn = this.getEyePosition().add(this.getLookAngle().normalize());
                  grenade.setPos(spawn.x, spawn.y, spawn.z);
                  grenade.shootFromRot(target.getEyePosition().subtract(this.getEyePosition()).normalize());
                  this.level().addFreshEntity(grenade);
                  this.level()
                     .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.CAST_LIGHT.get(), SoundSource.HOSTILE, 1.0F, 0.95F);
               }
            }
         }
      }
   }

   private void castSolarBeam(LivingEntity target) {
      if (target != null && target.isAlive()) {
         if (!this.isAlliedTo(target) && !target.isAlliedTo(this)) {
            if (!this.isMichael(target) && !this.isFeldway(target)) {
               if (this.solarBeamCd <= 0) {
                  this.solarBeamCd = 120;
                  this.showCircle(MagicCircleVariant.LIGHT, 1.15F, 30, 0.6F, new Vec3(0.0, this.getBbHeight() * 0.15 - this.getEyeHeight(), 0.0));
                  SolarBeamProjectile beam = new SolarBeamProjectile(this.level(), this);
                  beam.setPos(this.getEyePosition());
                  beam.setLife(18);
                  beam.setSize(0.85F);
                  beam.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.3F);
                  beam.setRange(28.0F);
                  beam.setFollowingOwner(true);
                  this.level().addFreshEntity(beam);
                  this.level()
                     .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.CAST_LIGHT.get(), SoundSource.HOSTILE, 1.0F, 0.9F);
               }
            }
         }
      }
   }

   private void castHolyField(LivingEntity target) {
      if (target != null && target.isAlive()) {
         if (!this.isAlliedTo(target) && !target.isAlliedTo(this)) {
            if (!this.isMichael(target) && !this.isFeldway(target)) {
               if (this.holyFieldCd <= 0) {
                  this.holyFieldCd = 140;
                  this.showCircle(MagicCircleVariant.LIGHT, 1.0F, 30, 0.75F, Vec3.ZERO);
                  HolyFieldEntity field = new HolyFieldEntity(this.level(), this);
                  field.setLife(80);
                  field.setSize(4.0F);
                  field.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.25F);
                  field.setPos(target.getX(), target.getY() + 0.1, target.getZ());
                  this.level().addFreshEntity(field);
                  this.level()
                     .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.CAST_LIGHT.get(), SoundSource.HOSTILE, 1.0F, 1.05F);
               }
            }
         }
      }
   }

   private void castFlashBurst(LivingEntity target) {
      if (this.level() instanceof ServerLevel sl) {
         if (target != null && target.isAlive()) {
            if (!this.isAlliedTo(target) && !target.isAlliedTo(this)) {
               if (!this.isMichael(target) && !this.isFeldway(target)) {
                  if (this.flashBurstCd <= 0) {
                     this.flashBurstCd = 95;
                     this.showCircle(MagicCircleVariant.LIGHT, 0.8F, 20, 0.45F, Vec3.ZERO);
                     AABB box = target.getBoundingBox().inflate(3.5);

                     for (LivingEntity hit : this.level()
                        .getEntitiesOfClass(
                           LivingEntity.class, box, e -> e.isAlive() && !this.isAlliedTo(e) && !e.isAlliedTo(this) && !this.isMichael(e) && !this.isFeldway(e)
                        )) {
                        hit.hurt(this.damageSources().mobAttack(this), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.8F);
                        SkillHelper.knockBack(this, hit, 1.4F);
                     }

                     sl.sendParticles(ParticleTypes.FLASH, target.getX(), target.getY() + target.getBbHeight() * 0.55, target.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                     sl.sendParticles(
                        ParticleTypes.END_ROD, target.getX(), target.getY() + target.getBbHeight() * 0.55, target.getZ(), 18, 0.9, 0.55, 0.9, 0.05
                     );
                     this.level()
                        .playSound(
                           null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.CAST_LIGHT.get(), SoundSource.HOSTILE, 1.0F, 1.1F
                        );
                  }
               }
            }
         }
      }
   }

   private void castWindBlade(LivingEntity target) {
      if (target != null && target.isAlive()) {
         if (!this.isAlliedTo(target) && !target.isAlliedTo(this)) {
            if (!this.isMichael(target) && !this.isFeldway(target)) {
               if (this.windBladeCd <= 0) {
                  this.windBladeCd = 70;
                  this.showCircle(MagicCircleVariant.WIND, 0.8F, 22, 0.7F, Vec3.ZERO);
                  WindBladeProjectile blade = new WindBladeProjectile(this.level(), this);
                  float angle = (float) (Math.PI / 180.0) * this.yBodyRot;
                  double xOffset = Mth.sin((float)(Math.PI + angle));
                  double zOffset = Mth.cos(angle);
                  blade.moveTo(this.getX() + xOffset, this.getEyeY() - 0.2, this.getZ() + zOffset, this.getYRot(), this.getXRot());
                  blade.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.45F);
                  blade.setSpeed(1.8F);
                  blade.setNoGravity(true);
                  blade.shootToward(target, 1.35F, 0.0F);
                  this.level().addFreshEntity(blade);
                  this.level()
                     .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.CAST_WIND.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
               }
            }
         }
      }
   }

   private void castWindSphere(LivingEntity target) {
      if (target != null && target.isAlive()) {
         if (!this.isAlliedTo(target) && !target.isAlliedTo(this)) {
            if (!this.isMichael(target) && !this.isFeldway(target)) {
               if (this.windSphereCd <= 0) {
                  this.windSphereCd = 92;
                  this.showCircle(MagicCircleVariant.WIND, 0.9F, 24, 0.7F, Vec3.ZERO);
                  WindSphereProjectile sphere = new WindSphereProjectile(this.level(), this);
                  sphere.setSize(0.75F);
                  float angle = (float) (Math.PI / 180.0) * this.yBodyRot;
                  double xOffset = Mth.sin((float)(Math.PI + angle));
                  double zOffset = Mth.cos(angle);
                  sphere.moveTo(this.getX() + xOffset, this.getEyeY() - 0.2, this.getZ() + zOffset, this.getYRot(), this.getXRot());
                  sphere.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.2F);
                  sphere.setSpeed(1.25F);
                  sphere.setNoGravity(true);
                  sphere.shootToward(target, 1.0F, 0.0F);
                  this.level().addFreshEntity(sphere);
                  this.level()
                     .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.CAST_WIND.get(), SoundSource.HOSTILE, 1.0F, 0.95F);
               }
            }
         }
      }
   }

   private void castSpatialRay(LivingEntity target) {
      if (target != null && target.isAlive()) {
         if (!this.isAlliedTo(target) && !target.isAlliedTo(this)) {
            if (!this.isMichael(target) && !this.isFeldway(target)) {
               if (this.spatialRayCd <= 0) {
                  this.spatialRayCd = 125;
                  this.showCircle(MagicCircleVariant.SPACE, 0.85F, 28, 0.5F, new Vec3(0.0, this.getBbHeight() * 0.15 - this.getEyeHeight(), 0.0));
                  this.getLookControl().setLookAt(target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ());
                  SpatialRayProjectile ray = new SpatialRayProjectile(this.level(), this);
                  ray.setFollowingOwner(true);
                  ray.setSize(1.0F);
                  ray.setLife(22);
                  ray.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.1F);
                  ray.setRange(28.0F);
                  ray.setPos(this.getEyePosition());
                  this.level().addFreshEntity(ray);
                  this.level()
                     .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.CAST_SPACE.get(), SoundSource.HOSTILE, 1.0F, 0.9F);
               }
            }
         }
      }
   }

   private void castDimensionCut(LivingEntity target) {
      if (target != null && target.isAlive()) {
         if (!this.isAlliedTo(target) && !target.isAlliedTo(this)) {
            if (!this.isMichael(target) && !this.isFeldway(target)) {
               if (this.dimensionCutCd <= 0) {
                  this.dimensionCutCd = 100;
                  this.showCircle(MagicCircleVariant.SPACE, 0.7F, 22, 0.7F, Vec3.ZERO);
                  DimensionCutProjectile cut = new DimensionCutProjectile(this.level(), this);
                  float angle = (float) (Math.PI / 180.0) * this.yBodyRot;
                  double xOffset = Mth.sin((float)(Math.PI + angle));
                  double zOffset = Mth.cos(angle);
                  cut.moveTo(this.getX() + xOffset, this.getEyeY() - 0.2, this.getZ() + zOffset, this.getYRot(), this.getXRot());
                  cut.setDamage((float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.8F);
                  cut.setSpeed(1.8F);
                  cut.setNoGravity(true);
                  cut.shootToward(target, 1.0F, 0.0F);
                  this.level().addFreshEntity(cut);
                  this.level()
                     .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.CAST_SPACE.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
               }
            }
         }
      }
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      if (this.masterUUID != null) {
         tag.putUUID("Master", this.masterUUID);
      }

      tag.putInt("SolarRainCd", this.solarRainCd);
      tag.putInt("SolarWaveCd", this.solarWaveCd);
      tag.putInt("HealCd", this.healCd);
      tag.putInt("BlinkCd", this.blinkCd);
      tag.putInt("MeleeCd", this.meleeCd);
      tag.putInt("HolyFieldCd", this.holyFieldCd);
      tag.putInt("SolarBeamCd", this.solarBeamCd);
      tag.putInt("FlashBurstCd", this.flashBurstCd);
      tag.putInt("WindBladeCd", this.windBladeCd);
      tag.putInt("WindSphereCd", this.windSphereCd);
      tag.putInt("SpatialRayCd", this.spatialRayCd);
      tag.putInt("DimensionCutCd", this.dimensionCutCd);
      tag.putInt("OwnerRegenTick", this.ownerRegenTick);
      tag.putInt("OwnerMagiculeTick", this.ownerMagiculeTick);
      tag.putInt("ForcedTargetTicks", this.forcedTargetTicks);
      if (this.getPersistentData().hasUUID("ForcedTarget")) {
         tag.putUUID("ForcedTarget", this.getPersistentData().getUUID("ForcedTarget"));
      }

      tag.putBoolean("FeldAngelInit", this.getPersistentData().getBoolean("FeldAngelInit"));
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.hasUUID("Master")) {
         this.masterUUID = tag.getUUID("Master");
      }

      this.solarRainCd = tag.getInt("SolarRainCd");
      this.solarWaveCd = tag.getInt("SolarWaveCd");
      this.healCd = tag.getInt("HealCd");
      this.blinkCd = tag.getInt("BlinkCd");
      this.meleeCd = tag.getInt("MeleeCd");
      this.holyFieldCd = tag.getInt("HolyFieldCd");
      this.solarBeamCd = tag.getInt("SolarBeamCd");
      this.flashBurstCd = tag.getInt("FlashBurstCd");
      this.windBladeCd = tag.getInt("WindBladeCd");
      this.windSphereCd = tag.getInt("WindSphereCd");
      this.spatialRayCd = tag.getInt("SpatialRayCd");
      this.dimensionCutCd = tag.getInt("DimensionCutCd");
      this.ownerRegenTick = tag.getInt("OwnerRegenTick");
      this.ownerMagiculeTick = tag.getInt("OwnerMagiculeTick");
      this.forcedTargetTicks = tag.getInt("ForcedTargetTicks");
      if (tag.hasUUID("ForcedTarget")) {
         this.getPersistentData().putUUID("ForcedTarget", tag.getUUID("ForcedTarget"));
      }

      if (tag.contains("FeldAngelInit")) {
         this.getPersistentData().putBoolean("FeldAngelInit", tag.getBoolean("FeldAngelInit"));
      }
   }

   private static class AngelMobMasterTargetGoal extends Goal {
      private final FeldwayAngelEntity angel;

      private AngelMobMasterTargetGoal(FeldwayAngelEntity angel) {
         this.angel = angel;
         this.setFlags(EnumSet.of(Flag.TARGET));
      }

      public boolean canUse() {
         if (!this.angel.canUseAutonomousAggro()) {
            return false;
         }

         if (this.angel.getMasterEntity() instanceof Mob mobMaster && mobMaster.isAlive()) {
            LivingEntity target = mobMaster.getTarget();
            if (this.angel.shouldAttack(target)) {
               return true;
            }
         }

         return this.angel.getTarget() == null || !this.angel.getTarget().isAlive();
      }

      public void start() {
         if (this.angel.canUseAutonomousAggro()) {
            if (this.angel.getMasterEntity() instanceof Mob mobMaster && mobMaster.isAlive()) {
               LivingEntity masterTarget = mobMaster.getTarget();
               if (this.angel.shouldAttack(masterTarget)) {
                  this.angel.setTarget(masterTarget);
                  return;
               }
            }

            AABB box = this.angel.getBoundingBox().inflate(32.0);
            List<LivingEntity> nearby = this.angel.level().getEntitiesOfClass(LivingEntity.class, box, this.angel::shouldAttack);
            LivingEntity best = null;
            double bestDist = Double.MAX_VALUE;

            for (LivingEntity living : nearby) {
               double dist = this.angel.distanceToSqr(living);
               if (dist < bestDist) {
                  bestDist = dist;
                  best = living;
               }
            }

            if (best != null) {
               this.angel.setTarget(best);
            }
         }
      }
   }

   private static class AngelMoveControl extends MoveControl {
      private final FeldwayAngelEntity angel;

      private AngelMoveControl(FeldwayAngelEntity angel) {
         super(angel);
         this.angel = angel;
      }

      public void tick() {
         if (this.operation != Operation.MOVE_TO) {
            this.angel.setDeltaMovement(this.angel.getDeltaMovement().scale(0.92));
         } else {
            Vec3 want = new Vec3(this.wantedX - this.angel.getX(), this.wantedY - this.angel.getY(), this.wantedZ - this.angel.getZ());
            double dist = want.length();
            if (dist < 0.35) {
               this.operation = Operation.WAIT;
               this.angel.setDeltaMovement(this.angel.getDeltaMovement().scale(0.75));
            } else {
               Vec3 add = want.normalize().scale(this.speedModifier * 0.045);
               this.angel.setDeltaMovement(this.angel.getDeltaMovement().add(add));
               Vec3 delta = this.angel.getDeltaMovement();
               if (delta.lengthSqr() > 8.0E-4) {
                  float yRot = (float)(Mth.atan2(delta.z, delta.x) * (180.0 / Math.PI)) - 90.0F;
                  this.angel.setYRot(this.rotlerp(this.angel.getYRot(), yRot, 3.5F));
                  this.angel.yBodyRot = this.angel.getYRot();
                  this.angel.yHeadRot = this.angel.getYRot();
               }
            }
         }
      }
   }

   private static class AngelOwnerHurtByTargetGoal extends Goal {
      private final FeldwayAngelEntity angel;

      private AngelOwnerHurtByTargetGoal(FeldwayAngelEntity angel) {
         this.angel = angel;
         this.setFlags(EnumSet.of(Flag.TARGET));
      }

      public boolean canUse() {
         if (this.angel.getMasterEntity() instanceof Player player && player.isAlive()) {
            LivingEntity attacker = player.getLastHurtByMob();
            return this.angel.shouldAttack(attacker);
         } else {
            return false;
         }
      }

      public void start() {
         if (this.angel.getMasterEntity() instanceof Player player) {
            LivingEntity attacker = player.getLastHurtByMob();
            if (this.angel.shouldAttack(attacker)) {
               this.angel.setTarget(attacker);
            }
         }
      }
   }

   private static class AngelOwnerHurtTargetGoal extends Goal {
      private final FeldwayAngelEntity angel;

      private AngelOwnerHurtTargetGoal(FeldwayAngelEntity angel) {
         this.angel = angel;
         this.setFlags(EnumSet.of(Flag.TARGET));
      }

      public boolean canUse() {
         if (this.angel.getMasterEntity() instanceof Player player && player.isAlive()) {
            LivingEntity victim = player.getLastHurtMob();
            return this.angel.shouldAttack(victim);
         } else {
            return false;
         }
      }

      public void start() {
         if (this.angel.getMasterEntity() instanceof Player player) {
            LivingEntity victim = player.getLastHurtMob();
            if (this.angel.shouldAttack(victim)) {
               this.angel.setTarget(victim);
            }
         }
      }
   }

   private static class AngelSelfDefendGoal extends HurtByTargetGoal {
      private final FeldwayAngelEntity angel;

      private AngelSelfDefendGoal(FeldwayAngelEntity angel) {
         super(angel, new Class[0]);
         this.angel = angel;
      }

      public boolean canUse() {
         LivingEntity attacker = this.angel.getLastHurtByMob();
         if (!this.angel.shouldAttack(attacker)) {
            return false;
         }

         if (!this.angel.isPlayerControlledPassive()) {
            return true;
         }

         Entity master = this.angel.getMasterEntity();
         return master != null && attacker != master;
      }

      public void start() {
         LivingEntity attacker = this.angel.getLastHurtByMob();
         if (this.angel.shouldAttack(attacker)) {
            this.angel.setTarget(attacker);
         }

         super.start();
      }
   }

   private static class AngelSupportGoal extends Goal {
      private final FeldwayAngelEntity angel;

      private AngelSupportGoal(FeldwayAngelEntity angel) {
         this.angel = angel;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         return this.angel.getMasterEntity() instanceof LivingEntity living && living.isAlive();
      }

      public void tick() {
         if (this.angel.getMasterEntity() instanceof LivingEntity master && master.isAlive()) {
            LivingEntity forced = this.angel.getForcedTarget();
            if (forced != null && this.angel.shouldAttack(forced)) {
               this.angel.setTarget(forced);
            }

            if (master instanceof Mob mobMaster && forced == null) {
               LivingEntity healTarget = this.angel.findBestHealTarget();
               if (healTarget != null) {
                  this.angel.getLookControl().setLookAt(healTarget, 22.0F, 18.0F);
                  double y = Mth.clamp(healTarget.getY() + 5.0, this.angel.getY() - 2.0, this.angel.level().getMaxBuildHeight() - 8.0);
                  this.angel.getMoveControl().setWantedPosition(healTarget.getX(), y, healTarget.getZ(), 0.75);
                  if (this.angel.distanceTo(healTarget) < 18.0) {
                     this.angel.castLowHeal(healTarget);
                  }
               }

               LivingEntity mt = mobMaster.getTarget();
               if (mt != null && mt.isAlive() && !this.angel.isAlliedTo(mt) && !this.angel.isMichael(mt) && !this.angel.isFeldway(mt)) {
                  this.angel.setTarget(mt);
               }
            } else if (master instanceof Player playerMaster && forced == null) {
               LivingEntity preferred = this.angel.getPlayerMasterPriorityTarget(playerMaster);
               LivingEntity current = this.angel.getTarget();
               if (preferred != null) {
                  this.angel.setTarget(preferred);
                  current = preferred;
               } else if (current != null) {
                  boolean keep = current.isAlive() && this.angel.shouldAttack(current);
                  if (!keep) {
                     this.angel.setTarget(null);
                     current = null;
                  }
               }

               if (current == null) {
                  double px = playerMaster.getX() + (playerMaster.getRandom().nextDouble() - 0.5) * 3.0;
                  double pz = playerMaster.getZ() + (playerMaster.getRandom().nextDouble() - 0.5) * 3.0;
                  double py = Mth.clamp(playerMaster.getY() + 3.0, this.angel.getY() - 3.0, this.angel.level().getMaxBuildHeight() - 8.0);
                  this.angel.getLookControl().setLookAt(playerMaster, 20.0F, 20.0F);
                  this.angel.getMoveControl().setWantedPosition(px, py, pz, 0.82);
                  return;
               }
            }

            LivingEntity target = this.angel.getTarget();
            if (target != null && target.isAlive()) {
               if (!this.angel.isAlliedTo(target) && !target.isAlliedTo(this.angel)) {
                  if (!this.angel.isMichael(target) && !this.angel.isFeldway(target)) {
                     this.angel.getLookControl().setLookAt(target, 22.0F, 18.0F);
                     double dist = this.angel.distanceTo(target);
                     double hoverY = Mth.clamp(target.getY() + 4.5, this.angel.getY() - 2.0, this.angel.level().getMaxBuildHeight() - 8.0);
                     this.angel.getMoveControl().setWantedPosition(target.getX(), hoverY, target.getZ(), 0.92);
                     if (dist >= 10.0 && dist <= 26.0 && this.angel.blinkCd <= 0 && this.angel.getRandom().nextFloat() < 0.1F) {
                        this.angel.blinkNear(target);
                     }

                     if (dist <= 34.0 && this.angel.solarRainCd <= 0) {
                        this.angel.castSolarRain(target);
                     } else if (dist <= 18.0 && this.angel.solarWaveCd <= 0 && this.angel.getRandom().nextFloat() < 0.2F) {
                        this.angel.castSolarWave(target);
                     } else if (dist <= 24.0 && this.angel.solarBeamCd <= 0 && this.angel.getRandom().nextFloat() < 0.14F) {
                        this.angel.castSolarBeam(target);
                     } else if (dist <= 14.0 && this.angel.holyFieldCd <= 0 && this.angel.getRandom().nextFloat() < 0.14F) {
                        this.angel.castHolyField(target);
                     } else if (dist <= 8.0 && this.angel.flashBurstCd <= 0 && this.angel.getRandom().nextFloat() < 0.16F) {
                        this.angel.castFlashBurst(target);
                     } else if (dist <= 24.0 && this.angel.windBladeCd <= 0 && this.angel.getRandom().nextFloat() < 0.18F) {
                        this.angel.castWindBlade(target);
                     } else if (dist <= 30.0 && this.angel.windSphereCd <= 0 && this.angel.getRandom().nextFloat() < 0.16F) {
                        this.angel.castWindSphere(target);
                     } else if (dist <= 28.0 && this.angel.spatialRayCd <= 0 && this.angel.getRandom().nextFloat() < 0.12F) {
                        this.angel.castSpatialRay(target);
                     } else if (dist <= 30.0 && this.angel.dimensionCutCd <= 0 && this.angel.getRandom().nextFloat() < 0.14F) {
                        this.angel.castDimensionCut(target);
                     } else {
                        if (dist <= 3.0) {
                           this.angel.doHurtTarget(target);
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
