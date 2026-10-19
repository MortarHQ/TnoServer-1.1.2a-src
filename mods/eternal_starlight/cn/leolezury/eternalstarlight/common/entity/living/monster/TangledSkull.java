package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class TangledSkull extends Monster {
   private static final String TAG_SHOT = "shot";
   private static final String TAG_SHOT_FROM_MONSTROSITY = "shot_from_monstrosity";
   private static final String TAG_SHOT_MOVEMENT_X = "shot_movement_x";
   private static final String TAG_SHOT_MOVEMENT_Y = "shot_movement_y";
   private static final String TAG_SHOT_MOVEMENT_Z = "shot_movement_z";
   public int skullDeathTime;
   public final List<Vec3> trailPositions = new ArrayList<>();
   protected static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(TangledSkull.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Boolean> SHOT = SynchedEntityData.defineId(TangledSkull.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Boolean> SHOT_FROM_MONSTROSITY = SynchedEntityData.defineId(TangledSkull.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Vector3f> SHOT_MOVEMENT = SynchedEntityData.defineId(TangledSkull.class, EntityDataSerializers.VECTOR3);

   public boolean isCharging() {
      return (Boolean)this.getEntityData().get(CHARGING);
   }

   public void setCharging(boolean charging) {
      this.getEntityData().set(CHARGING, charging);
   }

   public boolean isShot() {
      return (Boolean)this.getEntityData().get(SHOT);
   }

   public void setShot(boolean shot) {
      this.getEntityData().set(SHOT, shot);
   }

   public boolean isShotFromMonstrosity() {
      return (Boolean)this.getEntityData().get(SHOT_FROM_MONSTROSITY);
   }

   public void setShotFromMonstrosity(boolean shotFromMonstrosity) {
      this.getEntityData().set(SHOT_FROM_MONSTROSITY, shotFromMonstrosity);
   }

   public Vec3 getShotMovement() {
      return new Vec3((Vector3f)this.getEntityData().get(SHOT_MOVEMENT));
   }

   public void setShotMovement(Vec3 shotMovement) {
      this.getEntityData().set(SHOT_MOVEMENT, shotMovement.toVector3f());
   }

   public TangledSkull(EntityType<? extends TangledSkull> entityType, Level level) {
      super(entityType, level);
      this.moveControl = new TangledSkull.TangledSkullMoveControl(this);
   }

   protected BodyRotationControl createBodyControl() {
      return new TangledSkull.TangledSkullRotationControl(this);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new TangledSkull.TangledSkullChargeAttackGoal());
      this.goalSelector.addGoal(2, new TangledSkull.TangledSkullRandomMoveGoal());
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.tangledSkull.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.tangledSkull.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.tangledSkull.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.tangledSkull.followRange());
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(CHARGING, false).define(SHOT, false).define(SHOT_FROM_MONSTROSITY, false).define(SHOT_MOVEMENT, Vec3.ZERO.toVector3f());
   }

   public void tick() {
      this.noPhysics = true;
      super.tick();
      this.noPhysics = false;
      this.setNoGravity(true);
      if (!this.level().isClientSide) {
         if (this.isShotFromMonstrosity() && this.tickCount == 30) {
            this.playSound(ESSoundEvents.TANGLED_SKULL_ROAR.get(), 5.0F, 1.0F);
         }

         if (this.isShot()) {
            this.setDeltaMovement(this.getShotMovement());
            HitResult result = ProjectileUtil.getHitResultOnMoveVector(this, entity -> true);
            if (this.isShotFromMonstrosity()
               && result.getType() == Type.ENTITY
               && ((EntityHitResult)result).getEntity() instanceof LivingEntity living
               && !this.isAlliedTo(living)) {
               living.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60));
               living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60));
               living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60));
               living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60));
            }

            boolean ally = this.isShotFromMonstrosity() && result.getType() == Type.ENTITY && this.isAlliedTo(((EntityHitResult)result).getEntity());
            if (result.getType() != Type.MISS && !ally) {
               this.level().explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, ExplosionInteraction.NONE);
               if (this.isShotFromMonstrosity() && this.level() instanceof ServerLevel serverLevel) {
                  ESPlatform.INSTANCE
                     .sendToAllClients(serverLevel, new ParticlePacket(RingExplosionParticleOptions.SOUL, this.getX(), this.getY(), this.getZ(), 0.0, 0.2, 0.0));
               }

               this.discard();
            }
         }
      } else if (!this.isShotFromMonstrosity()) {
         this.level().addParticle((ParticleOptions)ESParticles.SOUL_TRAIL.get(), this.xo, this.yo + this.getBbHeight() / 2.0F, this.zo, 0.0, 0.0, 0.0);
      }
   }

   protected void tickDeath() {
      this.setDeltaMovement(Vec3.ZERO);
      this.skullDeathTime++;
      if (this.skullDeathTime >= 80 && !this.level().isClientSide() && !this.isRemoved()) {
         this.level().explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, ExplosionInteraction.NONE);
         this.level().broadcastEntityEvent(this, (byte)60);
         this.remove(RemovalReason.KILLED);
      }
   }

   public void die(DamageSource source) {
      super.die(source);
      if (!this.level().isClientSide() && source.getEntity() instanceof TangledSkull killer && killer.getKillCredit() instanceof ServerPlayer player) {
         ((PlayerTrigger)ESCriteriaTriggers.CHAIN_TANGLED_SKULL_EXPLOSION.get()).trigger(player);
      }
   }

   @Nullable
   public LivingEntity getTarget() {
      return this.isShot() ? null : super.getTarget();
   }

   public void setDeltaMovement(Vec3 vec3) {
      if (this.isShot()) {
         super.setDeltaMovement(this.isShotFromMonstrosity() && this.tickCount < 30 ? Vec3.ZERO : this.getShotMovement());
      } else {
         super.setDeltaMovement(vec3);
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      return this.isShot() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) ? false : super.hurt(source, amount);
   }

   public boolean isAlliedTo(Entity entity) {
      return super.isAlliedTo(entity) || entity.getType().is(ESTags.EntityTypes.LUNAR_MONSTROSITY_ALLIES);
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("shot", this.isShot());
      compoundTag.putBoolean("shot_from_monstrosity", this.isShotFromMonstrosity());
      if (this.isShot()) {
         compoundTag.putDouble("shot_movement_x", this.getShotMovement().x);
         compoundTag.putDouble("shot_movement_y", this.getShotMovement().y);
         compoundTag.putDouble("shot_movement_z", this.getShotMovement().z);
      }
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      if (compoundTag.contains("shot", 1)) {
         this.setShot(compoundTag.getBoolean("shot"));
      }

      if (compoundTag.contains("shot_from_monstrosity", 1)) {
         this.setShotFromMonstrosity(compoundTag.getBoolean("shot_from_monstrosity"));
      }

      if (compoundTag.contains("shot_movement_x", 99) && compoundTag.contains("shot_movement_y", 99) && compoundTag.contains("shot_movement_z", 99)) {
         this.setShotMovement(
            new Vec3(compoundTag.getDouble("shot_movement_x"), compoundTag.getDouble("shot_movement_y"), compoundTag.getDouble("shot_movement_z"))
         );
      }
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return ESSoundEvents.TANGLED_SKULL_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return SoundEvents.SKELETON_HURT;
   }

   private class TangledSkullChargeAttackGoal extends Goal {
      public TangledSkullChargeAttackGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         LivingEntity livingEntity = TangledSkull.this.getTarget();
         return livingEntity != null
               && livingEntity.isAlive()
               && !TangledSkull.this.getMoveControl().hasWanted()
               && TangledSkull.this.random.nextInt(reducedTickDelay(7)) == 0
            ? TangledSkull.this.distanceToSqr(livingEntity) > 4.0
            : false;
      }

      public boolean canContinueToUse() {
         return TangledSkull.this.getMoveControl().hasWanted()
            && TangledSkull.this.isCharging()
            && TangledSkull.this.getTarget() != null
            && TangledSkull.this.getTarget().isAlive();
      }

      public void start() {
         LivingEntity livingEntity = TangledSkull.this.getTarget();
         if (livingEntity != null) {
            Vec3 vec3 = livingEntity.getEyePosition();
            TangledSkull.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0);
         }

         TangledSkull.this.setCharging(true);
      }

      public void stop() {
         TangledSkull.this.setCharging(false);
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         LivingEntity livingEntity = TangledSkull.this.getTarget();
         if (livingEntity != null) {
            if (TangledSkull.this.getBoundingBox().intersects(livingEntity.getBoundingBox())) {
               TangledSkull.this.doHurtTarget(livingEntity);
               TangledSkull.this.setCharging(false);
            } else {
               double d = TangledSkull.this.distanceToSqr(livingEntity);
               if (d < 9.0) {
                  Vec3 vec3 = livingEntity.getEyePosition();
                  TangledSkull.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0);
               }
            }
         }
      }
   }

   private class TangledSkullMoveControl extends MoveControl {
      public TangledSkullMoveControl(final TangledSkull skull) {
         super(skull);
      }

      public void tick() {
         if (this.operation == Operation.MOVE_TO) {
            Vec3 vec3 = new Vec3(this.wantedX - TangledSkull.this.getX(), this.wantedY - TangledSkull.this.getY(), this.wantedZ - TangledSkull.this.getZ());
            double d = vec3.length();
            if (d < TangledSkull.this.getBoundingBox().getSize()) {
               this.operation = Operation.WAIT;
               TangledSkull.this.setDeltaMovement(TangledSkull.this.getDeltaMovement().scale(0.5));
            } else {
               TangledSkull.this.setDeltaMovement(TangledSkull.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05 / d)));
               if (TangledSkull.this.getTarget() == null) {
                  Vec3 vec32 = TangledSkull.this.getDeltaMovement();
                  TangledSkull.this.setYRot(-((float)Mth.atan2(vec32.x, vec32.z)) * (180.0F / (float)Math.PI));
               } else {
                  double e = TangledSkull.this.getTarget().getX() - TangledSkull.this.getX();
                  double f = TangledSkull.this.getTarget().getZ() - TangledSkull.this.getZ();
                  TangledSkull.this.setYRot(-((float)Mth.atan2(e, f)) * (180.0F / (float)Math.PI));
               }

               TangledSkull.this.yBodyRot = TangledSkull.this.getYRot();
            }
         }
      }
   }

   private class TangledSkullRandomMoveGoal extends Goal {
      public TangledSkullRandomMoveGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         return !TangledSkull.this.getMoveControl().hasWanted() && TangledSkull.this.random.nextInt(reducedTickDelay(7)) == 0;
      }

      public boolean canContinueToUse() {
         return false;
      }

      public void tick() {
         BlockPos blockPos = TangledSkull.this.blockPosition();

         for (int i = 0; i < 3; i++) {
            BlockPos blockPos2 = blockPos.offset(
               TangledSkull.this.random.nextInt(15) - 7, TangledSkull.this.random.nextInt(11) - 5, TangledSkull.this.random.nextInt(15) - 7
            );
            if (TangledSkull.this.level().isEmptyBlock(blockPos2)) {
               TangledSkull.this.moveControl.setWantedPosition(blockPos2.getX() + 0.5, blockPos2.getY() + 0.5, blockPos2.getZ() + 0.5, 0.25);
               if (TangledSkull.this.getTarget() == null) {
                  TangledSkull.this.getLookControl().setLookAt(blockPos2.getX() + 0.5, blockPos2.getY() + 0.5, blockPos2.getZ() + 0.5, 180.0F, 20.0F);
               }
               break;
            }
         }
      }
   }

   private class TangledSkullRotationControl extends BodyRotationControl {
      public TangledSkullRotationControl(final TangledSkull skull) {
         super(skull);
      }

      public void clientTick() {
         if (TangledSkull.this.isShot()) {
            Vec3 movement = TangledSkull.this.getShotMovement();
            float pitch = ESMathUtil.positionToPitch(movement);
            float yaw = ESMathUtil.positionToYaw(movement);
            TangledSkull.this.setYRot(yaw - 90.0F);
            TangledSkull.this.yHeadRot = yaw - 90.0F;
            TangledSkull.this.yBodyRot = yaw - 90.0F;
            TangledSkull.this.setXRot(-pitch);
         } else {
            super.clientTick();
         }
      }
   }
}
