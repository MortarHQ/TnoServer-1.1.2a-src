package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.AbstractIllager.IllagerArmPose;
import net.minecraft.world.entity.monster.SpellcasterIllager.IllagerSpell;
import net.minecraft.world.entity.monster.SpellcasterIllager.SpellcasterCastingSpellGoal;
import net.minecraft.world.entity.monster.SpellcasterIllager.SpellcasterUseSpellGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class IllusionerEntity extends SpellcasterIllager implements RangedAttackMob {
   private static final int MAX_ILLUSIONS_COUNT = FriendsAndFoes.getConfig().illusionerMaxIllusionsCount;
   private static final int ILLUSION_LIFETIME_TICKS = FriendsAndFoes.getConfig().illusionerIllusionLifetimeTicks;
   private static final int INVISIBILITY_TICKS = FriendsAndFoes.getConfig().illusionerInvisibilityTicks;
   private static final String IS_ILLUSION_NBT_NAME = "IsIllusion";
   private static final String WAS_ATTACKED_NBT_NAME = "WasAttacked";
   private static final String TICKS_UNTIL_DESPAWN_NBT_NAME = "TicksUntilDespawn";
   private static final String TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME = "TicksUntilCanCreateIllusions";
   private IllusionerEntity illusioner;
   private boolean isIllusion;
   private boolean wasAttacked;
   private int ticksUntilDespawn;
   private int ticksUntilCanCreateIllusion;

   public IllusionerEntity(EntityType<? extends IllusionerEntity> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 5;
      this.illusioner = null;
      this.isIllusion = false;
      this.wasAttacked = false;
      this.ticksUntilDespawn = 0;
      this.ticksUntilCanCreateIllusion = 0;
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new SpellcasterCastingSpellGoal(this));
      this.goalSelector.addGoal(5, new IllusionerEntity.IllusionerBlindnessSpellGoal());
      this.goalSelector.addGoal(6, new RangedBowAttackGoal(this, 0.5, 20, 15.0F));
      this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{Raider.class}).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, PlayerIllusionEntity.class, true).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, true).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, IronGolem.class, false).setUnseenMemoryTicks(300));
   }

   public void addAdditionalSaveData(CompoundTag nbt) {
      super.addAdditionalSaveData(nbt);
      nbt.putBoolean("IsIllusion", this.isIllusion());
      nbt.putBoolean("WasAttacked", this.wasAttacked());
      nbt.putInt("TicksUntilDespawn", this.getTicksUntilDespawn());
      nbt.putInt("TicksUntilCanCreateIllusions", this.getTicksUntilCanCreateIllusions());
   }

   public void readAdditionalSaveData(CompoundTag nbt) {
      super.readAdditionalSaveData(nbt);
      this.setIsIllusion(VersionedNbt.getBoolean(nbt, "IsIllusion", false));
      this.setWasAttacked(VersionedNbt.getBoolean(nbt, "WasAttacked", false));
      this.setTicksUntilDespawn(VersionedNbt.getInt(nbt, "TicksUntilDespawn", 0));
      this.setTicksUntilCanCreateIllusions(VersionedNbt.getInt(nbt, "TicksUntilCanCreateIllusions", 0));
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.FOLLOW_RANGE, 18.0).add(Attributes.MAX_HEALTH, 24.0);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
      this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
      return super.finalizeSpawn(level, difficulty, spawnReason, entityData);
   }

   public void tick() {
      if (!FriendsAndFoes.getConfig().enableIllusioner) {
         this.discard();
      }

      super.tick();
   }

   public void aiStep() {
      super.aiStep();
      if (!this.level().isClientSide()) {
         if (this.getTicksUntilCanCreateIllusions() > 0) {
            this.setTicksUntilCanCreateIllusions(this.getTicksUntilCanCreateIllusions() - 1);
         }

         if (this.wasAttacked() && this.getTicksUntilCanCreateIllusions() == 0) {
            this.createIllusions();
         }

         if (this.wasAttacked() && this.getTarget() == null && this.getTicksUntilCanCreateIllusions() < ILLUSION_LIFETIME_TICKS / 3) {
            this.setWasAttacked(false);
            this.setTicksUntilCanCreateIllusions(0);
         }

         if (this.isIllusion()) {
            if (this.getTicksUntilDespawn() > 0) {
               this.setTicksUntilDespawn(this.getTicksUntilDespawn() - 1);
            }

            boolean isIllusionerNonExistingOrDead = this.getIllusioner() != null && !this.getIllusioner().isAlive();
            if (this.getTicksUntilDespawn() == 0 || isIllusionerNonExistingOrDead) {
               this.discardIllusion();
            }
         }
      }
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      Entity attacker = damageSource.getEntity();
      EntityType<?> attackerType = null;
      if (attacker != null) {
         attackerType = attacker.getType();
      }

      if ((attackerType == null || !attackerType.is(EntityTypeTags.ILLAGER_FRIENDS) && !attackerType.is(EntityTypeTags.RAIDERS))
         && (!this.isIllusion() || attacker instanceof LivingEntity)) {
         if (!this.level().isClientSide()
            && attackerType != null
            && !attackerType.is(EntityTypeTags.ILLAGER_FRIENDS)
            && !attackerType.is(EntityTypeTags.RAIDERS)) {
            if (this.isIllusion()) {
               this.discardIllusion();
               return false;
            }

            if (this.getTicksUntilCanCreateIllusions() == 0
               && !attackerType.is(EntityTypeTags.ILLAGER_FRIENDS)
               && !attackerType.is(EntityTypeTags.RAIDERS)
               && !(damageSource.getEntity() instanceof Player player && player.getAbilities().instabuild)) {
               this.createIllusions();
            }
         }

         return super.hurt(damageSource, amount);
      } else {
         return false;
      }
   }

   public SoundEvent getCelebrateSound() {
      return SoundEvents.ILLUSIONER_AMBIENT;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ILLUSIONER_AMBIENT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ILLUSIONER_DEATH;
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return SoundEvents.ILLUSIONER_HURT;
   }

   protected SoundEvent getCastingSoundEvent() {
      return SoundEvents.ILLUSIONER_CAST_SPELL;
   }

   public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
   }

   public void performRangedAttack(LivingEntity target, float velocity) {
      ItemStack itemStack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW));
      ItemStack itemStack2 = this.getProjectile(itemStack);
      AbstractArrow abstractArrow = ProjectileUtil.getMobArrow(this, itemStack2, velocity, itemStack);
      double d = target.getX() - this.getX();
      double e = target.getY(0.3333333333333333) - abstractArrow.getY();
      double f = target.getZ() - this.getZ();
      double g = Math.sqrt(d * d + f * f);
      Level var15 = this.level();
      abstractArrow.shoot(d, e + g * 0.2F, f, 1.6F, 14 - this.level().getDifficulty().getId() * 4);
      this.level().addFreshEntity(abstractArrow);
      this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
   }

   public IllagerArmPose getArmPose() {
      if (this.isCastingSpell()) {
         return IllagerArmPose.SPELLCASTING;
      } else {
         return this.isAggressive() ? IllagerArmPose.BOW_AND_ARROW : IllagerArmPose.CROSSED;
      }
   }

   private void discardIllusion() {
      this.playMirrorSound();
      this.spawnCloudParticles();
      this.discard();
   }

   private void createIllusions() {
      this.setWasAttacked(true);
      this.setTicksUntilCanCreateIllusions(ILLUSION_LIFETIME_TICKS);
      this.playMirrorSound();
      this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, INVISIBILITY_TICKS));
      this.spawnCloudParticles();
      if (MAX_ILLUSIONS_COUNT != 0) {
         Vec3 illusionerPosition = this.position();
         float slice = (float) (Math.PI * 2) / MAX_ILLUSIONS_COUNT;
         int radius = 9;
         int randomPoint = this.getRandom().nextIntBetweenInclusive(0, MAX_ILLUSIONS_COUNT - 1);

         for (int point = 0; point < MAX_ILLUSIONS_COUNT; point++) {
            float angle = slice * point;
            int x = (int)(illusionerPosition.x() + radius * Mth.cos(angle));
            int y = (int)illusionerPosition.y();
            int z = (int)(illusionerPosition.z() + radius * Mth.sin(angle));
            if (randomPoint == point) {
               this.tryToTeleport(x, y, z);
            } else {
               this.createIllusion(x, y, z);
            }
         }
      }
   }

   private void createIllusion(int x, int y, int z) {
      IllusionerEntity illusioner = this;
      IllusionerEntity illusion = (IllusionerEntity)((EntityType)FriendsAndFoesEntityTypes.ILLUSIONER.get()).create(this.level());
      illusion.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
      illusion.setIsIllusion(true);
      illusion.setIllusioner(illusioner);
      illusion.setTicksUntilDespawn(ILLUSION_LIFETIME_TICKS);
      illusion.setHealth(this.getMaxHealth());
      illusion.copyPosition(illusioner);
      boolean teleportResult = this.tryToTeleport(x, y, z);
      if (teleportResult) {
         this.level().addFreshEntity(illusion);
         this.spawnCloudParticles();
      }
   }

   public boolean tryToTeleport(int x, int y, int z) {
      y -= 8;
      int worldBottomY = this.level().getMinBuildHeight();
      double bottomY = Math.max(y, worldBottomY);
      double topY = Math.min(bottomY + 16.0, ((ServerLevel)this.level()).getLogicalHeight() - 1);

      for (int i = 0; i < 16; i++) {
         y = (int)Mth.clamp(y + 1, bottomY, topY);
         boolean teleportResult = this.randomTeleport(x, y, z, false);
         if (teleportResult) {
            return true;
         }
      }

      return false;
   }

   private void playMirrorSound() {
      this.playSound(SoundEvents.ILLUSIONER_MIRROR_MOVE, this.getSoundVolume(), this.getVoicePitch());
   }

   public void spawnCloudParticles() {
      this.spawnParticles(ParticleTypes.CLOUD, 16);
   }

   private <T extends ParticleOptions> void spawnParticles(T particleType, int amount) {
      if (!this.level().isClientSide()) {
         for (int i = 0; i < amount; i++) {
            ((ServerLevel)this.level()).sendParticles(particleType, this.getRandomX(0.5), this.getRandomY() + 0.5, this.getRandomZ(0.5), 1, 0.0, 0.0, 0.0, 0.0);
         }
      }
   }

   public boolean isIllusion() {
      return this.isIllusion;
   }

   public void setIsIllusion(boolean isIllusion) {
      this.isIllusion = isIllusion;
   }

   public boolean wasAttacked() {
      return this.wasAttacked;
   }

   public void setWasAttacked(boolean wasAttacked) {
      this.wasAttacked = wasAttacked;
   }

   @Nullable
   public IllusionerEntity getIllusioner() {
      return this.illusioner;
   }

   public void setIllusioner(IllusionerEntity illusioner) {
      this.illusioner = illusioner;
   }

   public int getTicksUntilDespawn() {
      return this.ticksUntilDespawn;
   }

   public void setTicksUntilDespawn(int ticksUntilDespawn) {
      this.ticksUntilDespawn = ticksUntilDespawn;
   }

   public int getTicksUntilCanCreateIllusions() {
      return this.ticksUntilCanCreateIllusion;
   }

   public void setTicksUntilCanCreateIllusions(int ticksUntilCanCreateIllusions) {
      this.ticksUntilCanCreateIllusion = ticksUntilCanCreateIllusions;
   }

   public class IllusionerBlindnessSpellGoal extends SpellcasterUseSpellGoal {
      private int lastTargetId;

      IllusionerBlindnessSpellGoal() {
         super(IllusionerEntity.this);
      }

      public boolean canUse() {
         if (!super.canUse()) {
            return false;
         } else if (IllusionerEntity.this.getTarget() == null) {
            return false;
         } else {
            return IllusionerEntity.this.getTarget().getId() == this.lastTargetId
               ? false
               : ((ServerLevelAccessor)IllusionerEntity.this.level())
                  .getCurrentDifficultyAt(IllusionerEntity.this.blockPosition())
                  .isHarderThan(Difficulty.NORMAL.ordinal());
         }
      }

      public void start() {
         super.start();
         LivingEntity livingEntity = IllusionerEntity.this.getTarget();
         if (livingEntity != null) {
            this.lastTargetId = livingEntity.getId();
         }
      }

      protected int getCastingTime() {
         return 20;
      }

      protected int getCastingInterval() {
         return 180;
      }

      protected void performSpellCasting() {
         IllusionerEntity.this.getTarget().addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200), IllusionerEntity.this);
      }

      protected SoundEvent getSpellPrepareSound() {
         return SoundEvents.ILLUSIONER_PREPARE_BLINDNESS;
      }

      protected IllagerSpell getSpell() {
         return IllagerSpell.BLINDNESS;
      }
   }
}
