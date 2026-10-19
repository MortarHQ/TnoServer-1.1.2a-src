package com.github.L_Ender.cataclysm.entity.AnimationMonster;

import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Poison_Dart_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class Koboleton_Entity extends Animation_Monster {
   public static final Animation COBOLETON_ATTACK = Animation.create(19);
   public float angryProgress;
   public float prevangryProgress;

   public Koboleton_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.xpReward = 8;
      this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, COBOLETON_ATTACK};
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(2, new Koboleton_Entity.AnimationMeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0, 80));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, false));
   }

   public static Builder koboleton() {
      return Monster.createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 15.0)
         .add(Attributes.MOVEMENT_SPEED, 0.4F)
         .add(Attributes.ATTACK_DAMAGE, 3.0)
         .add(Attributes.MAX_HEALTH, 25.0)
         .add(Attributes.ARMOR, 0.0)
         .add(Attributes.STEP_HEIGHT, 1.25)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.25);
   }

   public boolean hurt(DamageSource source, float damage) {
      Entity entity = source.getDirectEntity();
      return entity instanceof Poison_Dart_Entity ? false : super.hurt(source, damage);
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)ModSounds.KOBOLETON_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.KOBOLETON_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.KOBOLETON_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound((SoundEvent)ModSounds.KOBOLETON_STEP.get(), 0.15F, 0.6F);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
   }

   protected void populateDefaultEquipmentSlots(RandomSource p_219154_, DifficultyInstance p_219155_) {
      this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModItems.KHOPESH.get()));
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34088_, DifficultyInstance p_34089_, MobSpawnType p_34090_, @Nullable SpawnGroupData p_34091_) {
      SpawnGroupData spawngroupdata = super.finalizeSpawn(p_34088_, p_34089_, p_34090_, p_34091_);
      RandomSource randomsource = p_34088_.getRandom();
      this.populateDefaultEquipmentSlots(randomsource, p_34089_);
      return spawngroupdata;
   }

   public void tick() {
      super.tick();
      AnimationHandler.INSTANCE.updateAnimations(this);
      this.prevangryProgress = this.angryProgress;
      if (this.isAggressive() && this.angryProgress < 10.0F) {
         this.angryProgress++;
      }

      if (!this.isAggressive() && this.angryProgress > 0.0F) {
         this.angryProgress--;
      }

      LivingEntity target = this.getTarget();
      if (this.isAlive() && this.getAnimation() == COBOLETON_ATTACK && this.getAnimationTick() == 11) {
         this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
         if (target != null && this.distanceTo(target) < this.getBbWidth() * 2.5F * this.getBbWidth() * 2.5F + target.getBbWidth()) {
            float damage = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
            target.hurt(this.damageSources().mobAttack(this), damage);
            ItemStack offhand = target.getOffhandItem();
            ItemStack mainhand = target.getMainHandItem();
            Optional<SlotResult> slot = CuriosApi.getCuriosHelper().findFirstCurio(target, stack -> stack.is((Item)ModItems.STICKY_GLOVES.get()));
            if (this.random.nextFloat() * 100.0F <= (float)CMCommonConfig.Koboleton.CauseKoboletontoDropItemInHandPercent && slot.isEmpty()) {
               if (!offhand.isEmpty()) {
                  if (!offhand.is(ModTag.STICKY_ITEM)) {
                     int i = offhand.getCount();
                     this.koboletonstealdrop(offhand.copyWithCount(1), target);
                     target.setItemSlot(EquipmentSlot.OFFHAND, offhand.split(i - 1));
                  }
               } else if (!mainhand.isEmpty() && !mainhand.is(ModTag.STICKY_ITEM)) {
                  int i = mainhand.getCount();
                  this.koboletonstealdrop(mainhand.copyWithCount(1), target);
                  target.setItemSlot(EquipmentSlot.MAINHAND, mainhand.split(i - 1));
               }
            }
         }
      }
   }

   private ItemEntity koboletonstealdrop(ItemStack p_36179_, LivingEntity target) {
      if (p_36179_.isEmpty()) {
         return null;
      }

      if (this.level().isClientSide) {
         return null;
      }

      double d0 = target.getEyeY() - 0.3F;
      ItemEntity itementity = new ItemEntity(target.level(), target.getX(), d0, target.getZ(), p_36179_);
      itementity.setDefaultPickUpDelay();
      itementity.setExtendedLifetime();
      float f8 = Mth.sin(target.getXRot() * (float) (Math.PI / 180.0));
      float f2 = Mth.cos(target.getXRot() * (float) (Math.PI / 180.0));
      float f3 = Mth.sin(target.getYRot() * (float) (Math.PI / 180.0));
      float f4 = Mth.cos(target.getYRot() * (float) (Math.PI / 180.0));
      float f5 = target.getRandom().nextFloat() * (float) (Math.PI * 2);
      float f6 = 0.02F * target.getRandom().nextFloat();
      itementity.setDeltaMovement(
         -f3 * f2 * 0.3F + Math.cos(f5) * f6,
         -f8 * 0.3F + 0.1F + (target.getRandom().nextFloat() - target.getRandom().nextFloat()) * 0.1F,
         f4 * f2 * 0.3F + Math.sin(f5) * f6
      );
      this.level().addFreshEntity(itementity);
      return itementity;
   }

   public boolean checkSpawnRules(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
      return ModEntities.rollSpawn(CMCommonConfig.Spawning.KoboletonSpawnRolls, this.getRandom(), spawnReasonIn);
   }

   public static boolean checkKoboletonSpawnRules(
      EntityType<Koboleton_Entity> husk, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return checkMonsterSpawnRules(husk, level, spawnType, pos, random) && (MobSpawnType.isSpawner(spawnType) || level.canSeeSky(pos));
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.isAlliedTo(entityIn)) {
         return true;
      } else {
         return !entityIn.getType().is(ModTag.TEAM_ANCIENT_REMNANT) ? false : this.getTeam() == null && entityIn.getTeam() == null;
      }
   }

   static class AnimationMeleeAttackGoal extends MeleeAttackGoal {
      protected final Koboleton_Entity mob;

      public AnimationMeleeAttackGoal(Koboleton_Entity p_25552_, double p_25553_, boolean p_25554_) {
         super(p_25552_, p_25553_, p_25554_);
         this.mob = p_25552_;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      protected double getAttackReachSqr(LivingEntity p_25556_) {
         return this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 2.5F + p_25556_.getBbWidth();
      }

      protected void checkAndPerformAttack(LivingEntity p_25557_) {
         if (this.canPerformAttack(p_25557_) && this.mob.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
            this.mob.setAnimation(Koboleton_Entity.COBOLETON_ATTACK);
         }
      }
   }
}
