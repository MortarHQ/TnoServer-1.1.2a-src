package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.animal.CrystallizedMoth;
import cn.leolezury.eternalstarlight.common.network.ClientDismountPacket;
import cn.leolezury.eternalstarlight.common.network.ClientMountPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity.MovementEmission;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Gleech extends Monster {
   private static final String TAG_LARVAL = "larval";
   private static final String TAG_GROWTH_TICKS = "growth_ticks";
   public static final CompoundTag NOT_LARVAL = (CompoundTag)Util.make(() -> {
      CompoundTag tag = new CompoundTag();
      tag.putBoolean("larval", false);
      return tag;
   });
   protected static final EntityDataAccessor<Boolean> LARVAL = SynchedEntityData.defineId(Gleech.class, EntityDataSerializers.BOOLEAN);
   private int growthTicks;
   private int attachTicks;
   public AnimationState idleAnimationState = new AnimationState();

   public boolean isLarval() {
      return (Boolean)this.getEntityData().get(LARVAL);
   }

   public void setLarval(boolean larval) {
      this.getEntityData().set(LARVAL, larval);
   }

   public Gleech(EntityType<? extends Gleech> entityType, Level level) {
      super(entityType, level);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(LARVAL, false);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Player>(this, Player.class, true) {
         public boolean canUse() {
            return super.canUse() && Gleech.this.getVehicle() == null && Gleech.this.getTarget() == null;
         }
      });
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.gleech.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.gleech.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.gleech.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.gleech.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.25);
   }

   protected MovementEmission getMovementEmission() {
      return MovementEmission.EVENTS;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.SILVERFISH_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return SoundEvents.SILVERFISH_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.SILVERFISH_DEATH;
   }

   protected void playStepSound(BlockPos blockPos, BlockState blockState) {
      this.playSound(SoundEvents.SILVERFISH_STEP, 0.15F, 1.0F);
   }

   public boolean isBaby() {
      return this.isLarval();
   }

   public void tick() {
      this.yBodyRot = this.getYRot();
      super.tick();
      if (!this.level().isClientSide) {
         this.setNoGravity(this.getVehicle() != null);
         if (this.isLarval()) {
            this.growthTicks++;
            if (this.growthTicks > 12000) {
               this.growthTicks = 0;
               this.setLarval(false);
            }
         }
      } else {
         this.idleAnimationState.startIfStopped(this.tickCount);
      }
   }

   public void attachTo(LivingEntity livingEntity) {
      if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
         this.startRiding(livingEntity, true);
         ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ClientMountPacket(this.getId(), livingEntity.getId()));
      }
   }

   public void rideTick() {
      this.setDeltaMovement(Vec3.ZERO);
      this.tick();
      this.oRun = this.run;
      this.run = 0.0F;
      this.resetFallDistance();
      if (!this.level().isClientSide) {
         this.attachTicks++;
         if (this.attachTicks > 80) {
            this.attachTicks = 0;
            this.stopRiding();
            ESPlatform.INSTANCE.sendToAllClients((ServerLevel)this.level(), new ClientDismountPacket(this.getId()));
         }
      }

      if (this.getVehicle() instanceof LivingEntity livingEntity) {
         this.setYRot(livingEntity.getYRot());
         this.setPos(
            ESMathUtil.rotationToPosition(
               this.getVehicle().position().add(0.0, this.getVehicle().getBbHeight() / 2.0F, 0.0),
               this.getVehicle().getBbWidth() / 2.0F * 0.75F,
               0.0F,
               this.getVehicle().getYRot() + 90.0F
            )
         );
      }
   }

   public void setYBodyRot(float f) {
      float rotation = f;
      if (this.getVehicle() instanceof LivingEntity livingEntity) {
         rotation = livingEntity.getYRot();
      }

      this.setYRot(rotation);
      super.setYBodyRot(rotation);
   }

   public void setYRot(float f) {
      float rotation = f;
      if (this.getVehicle() instanceof LivingEntity livingEntity) {
         rotation = livingEntity.getYRot();
      }

      super.setYRot(rotation);
   }

   public boolean doHurtTarget(Entity entity) {
      return super.doHurtTarget(entity);
   }

   protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean bl) {
      super.dropCustomDeathLoot(serverLevel, damageSource, bl);
      if (damageSource.getEntity() instanceof CrystallizedMoth && this.getRandom().nextInt(15) == 0) {
         this.spawnAtLocation((ItemLike)ESItems.MUSIC_DISC_SACRED_DESERT.get());
      }
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.setLarval(compoundTag.getBoolean("larval"));
      this.growthTicks = compoundTag.getInt("growth_ticks");
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("larval", this.isLarval());
      compoundTag.putInt("growth_ticks", this.growthTicks);
   }

   public static boolean checkGleechSpawnRules(
      EntityType<? extends Gleech> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return level.getBlockState(pos.below()).is(BlockTags.SAND) && ESConfig.INSTANCE.mobsConfig.gleech.canSpawn();
   }
}
