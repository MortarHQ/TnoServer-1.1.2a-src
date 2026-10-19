package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.world.entity.ai.goal.PotionBowAttackGoal;
import java.util.stream.StreamSupport;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.AbstractIllager.IllagerArmPose;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Alchemist extends AbstractIllager implements RangedAttackMob {
   private static final EntityDataAccessor<Boolean> DATA_HOLDING_POTION = SynchedEntityData.defineId(Alchemist.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DATA_HOLDING_BOW = SynchedEntityData.defineId(Alchemist.class, EntityDataSerializers.BOOLEAN);
   public int potionCooldown = 160;

   public Alchemist(EntityType<? extends Alchemist> entityType, Level world) {
      super(entityType, world);
      this.xpReward = 10;
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(4, new PotionBowAttackGoal(this, 0.5, 20, 15.0F));
      this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{Raider.class}).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, false));
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
      this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
      return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
   }

   public void performRangedAttack(LivingEntity target, float pullProgress) {
      ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
      PotionContents potionContents = (PotionContents)itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
      if (itemStack.is(Items.LINGERING_POTION) && potionContents.potion().isPresent()) {
         Vec3 deltaMovement = target.getDeltaMovement();
         double d = target.getX() + deltaMovement.x - this.getX();
         double e = target.getEyeY() - 1.1 - this.getY();
         double f = target.getZ() + deltaMovement.z - this.getZ();
         double g = Math.sqrt(d * d + f * f);
         ThrownPotion potionEntity = new ThrownPotion(this.level(), this);
         Holder<Potion> potion = (Holder<Potion>)potionContents.potion().get();
         potionEntity.setItem(PotionContents.createItemStack(Items.LINGERING_POTION, potion));
         potionEntity.setXRot(potionEntity.getXRot() + 20.0F);
         potionEntity.shoot(d, e + g * 0.2, f, 0.75F, 8.0F);
         if (!this.isSilent()) {
            this.level()
               .playSound(
                  null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_THROW, this.getSoundSource(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F
               );
         }

         this.level().addFreshEntity(potionEntity);
         this.setBowState(true);
      } else {
         ItemStack itemInHand = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW));
         ItemStack projectileItem = this.getProjectile(itemInHand);
         AbstractArrow persistentProjectileEntity = ProjectileUtil.getMobArrow(this, projectileItem, pullProgress, itemInHand);
         double d = target.getX() - this.getX();
         double e = target.getY(0.33) - persistentProjectileEntity.getY();
         double f = target.getZ() - this.getZ();
         double g = Math.sqrt(d * d + f * f);
         persistentProjectileEntity.shoot(d, e + g * 0.2, f, 1.6F, 14 - this.level().getDifficulty().getId() * 4);
         this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
         this.level().addFreshEntity(persistentProjectileEntity);
      }
   }

   public void addAdditionalSaveData(CompoundTag nbt) {
      nbt.putBoolean("BowState", this.getBowState());
      nbt.putBoolean("PotionState", this.getPotionState());
      super.addAdditionalSaveData(nbt);
   }

   private void removeEffectsInCloud(AreaEffectCloud cloudEntity) {
      for (LivingEntity entity : this.level().getEntitiesOfClass(AbstractIllager.class, cloudEntity.getBoundingBox().inflate(0.3), Entity::isAlive)) {
         StreamSupport.stream(cloudEntity.potionContents.getAllEffects().spliterator(), false)
            .findAny()
            .map(MobEffectInstance::getEffect)
            .ifPresent(entity::removeEffect);
      }
   }

   public void readAdditionalSaveData(CompoundTag nbt) {
      super.readAdditionalSaveData(nbt);
      this.setPotionState(nbt.getBoolean("PotionState"));
      this.setBowState(nbt.getBoolean("BowState"));
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_HOLDING_POTION, false);
      builder.define(DATA_HOLDING_BOW, true);
   }

   public boolean getPotionState() {
      return (Boolean)this.entityData.get(DATA_HOLDING_POTION);
   }

   public void setPotionState(boolean potionState) {
      this.entityData.set(DATA_HOLDING_POTION, potionState);
   }

   public boolean getBowState() {
      return (Boolean)this.entityData.get(DATA_HOLDING_BOW);
   }

   public void setBowState(boolean bowState) {
      this.entityData.set(DATA_HOLDING_BOW, bowState);
   }

   public SoundEvent getCelebrateSound() {
      return SoundEvents.EVOKER_CELEBRATE;
   }

   protected void customServerAiStep() {
      for (AreaEffectCloud areaEffectCloud : this.level().getEntitiesOfClass(AreaEffectCloud.class, this.getBoundingBox().inflate(30.0), Entity::isAlive)) {
         this.removeEffectsInCloud(areaEffectCloud);
      }

      this.potionCooldown--;
      if (this.potionCooldown <= 0) {
         this.setPotionState(true);
         this.potionCooldown = 160;
      }

      ItemStack mainHandItem = this.getItemInHand(InteractionHand.MAIN_HAND);
      if (this.getBowState() && mainHandItem.is(Items.LINGERING_POTION)) {
         this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
         this.setPotionState(false);
      }

      if (this.getPotionState() && mainHandItem.is(Items.BOW)) {
         Holder<Potion> potion = switch (this.random.nextInt(3)) {
            case 0 -> Potions.POISON;
            case 1 -> Potions.SLOWNESS;
            case 2 -> Potions.WEAKNESS;
            default -> throw new RuntimeException();
         };
         this.setItemSlot(EquipmentSlot.MAINHAND, PotionContents.createItemStack(Items.LINGERING_POTION, potion));
         this.setBowState(false);
      }

      super.customServerAiStep();
   }

   public boolean canBeAffected(MobEffectInstance effectInstance) {
      return effectInstance.getEffect() != MobEffects.POISON && super.canBeAffected(effectInstance);
   }

   public boolean isAlliedTo(Entity other) {
      if (other == null) {
         return false;
      } else if (other == this) {
         return true;
      } else if (super.isAlliedTo(other)) {
         return true;
      } else {
         return other instanceof Vex
            ? this.isAlliedTo(((Vex)other).getOwner())
            : other instanceof LivingEntity livingEntity
               && livingEntity.getType().is(EntityTypeTags.ILLAGER_FRIENDS)
               && this.getTeam() == null
               && other.getTeam() == null;
      }
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ILLUSIONER_AMBIENT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ILLUSIONER_DEATH;
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ILLUSIONER_HURT;
   }

   public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
   }

   public IllagerArmPose getArmPose() {
      if (this.isAggressive() && this.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.BOW)) {
         return IllagerArmPose.BOW_AND_ARROW;
      } else {
         return this.isAggressive() && this.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.LINGERING_POTION)
            ? IllagerArmPose.ATTACKING
            : IllagerArmPose.CROSSED;
      }
   }
}
