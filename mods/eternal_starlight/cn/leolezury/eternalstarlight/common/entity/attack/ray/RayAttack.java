package cn.leolezury.eternalstarlight.common.entity.attack.ray;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.RayAttackUser;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

public class RayAttack extends Entity implements TraceableEntity {
   protected static final EntityDataAccessor<Integer> CASTER = SynchedEntityData.defineId(RayAttack.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Float> PITCH = SynchedEntityData.defineId(RayAttack.class, EntityDataSerializers.FLOAT);
   protected static final EntityDataAccessor<Float> YAW = SynchedEntityData.defineId(RayAttack.class, EntityDataSerializers.FLOAT);
   protected static final EntityDataAccessor<Float> LENGTH = SynchedEntityData.defineId(RayAttack.class, EntityDataSerializers.FLOAT);
   public float renderPitch;
   public float renderYaw;
   public float prevPitch;
   public float prevYaw;
   private Entity caster;

   public int getCasterId() {
      return (Integer)this.getEntityData().get(CASTER);
   }

   public Optional<Entity> getCaster() {
      if (this.level().isClientSide) {
         int casterId = this.getCasterId();
         if (this.caster == null || casterId != this.caster.getId()) {
            this.caster = this.level().getEntity(casterId);
         }
      }

      return Optional.ofNullable(this.caster);
   }

   public void setCaster(Entity caster) {
      this.getEntityData().set(CASTER, caster != null ? caster.getId() : -1);
      this.caster = caster;
   }

   public float getPitch() {
      return (Float)this.getEntityData().get(PITCH);
   }

   public void setPitch(float pitch) {
      this.getEntityData().set(PITCH, pitch);
   }

   public float getYaw() {
      return (Float)this.getEntityData().get(YAW);
   }

   public void setYaw(float yaw) {
      this.getEntityData().set(YAW, yaw);
   }

   public float getLength() {
      return (Float)this.getEntityData().get(LENGTH);
   }

   public void setLength(float length) {
      this.getEntityData().set(LENGTH, length);
   }

   public RayAttack(EntityType<? extends RayAttack> type, Level world) {
      super(type, world);
      this.noCulling = true;
   }

   public RayAttack(EntityType<? extends RayAttack> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch) {
      this(type, world);
      this.setCaster(caster);
      this.setYaw(yaw);
      this.setPitch(pitch);
      this.setLength(this.getRadius());
      this.setPos(x, y, z);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(CASTER, -1).define(PITCH, 0.0F).define(YAW, 0.0F).define(LENGTH, 0.0F);
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         this.update();
         Vec3 idealEndPos = ESMathUtil.rotationToPosition(this.position(), this.getRadius(), this.getPitch(), this.getYaw());
         Vec3 endPos = idealEndPos;
         ESEntityUtil.RaytraceResult result = ESEntityUtil.raytrace(this.level(), CollisionContext.of(this), this.position(), idealEndPos);
         boolean hasBlock = result.blockHitResult() != null && result.blockHitResult().getType() != Type.MISS;
         if (hasBlock) {
            endPos = result.blockHitResult().getLocation();
         }

         this.setLength((float)endPos.distanceTo(this.position()));
         result = ESEntityUtil.raytrace(this.level(), CollisionContext.of(this), this.position(), endPos);
         this.onHit(result);
         if (this.caster != null && this.caster.getId() != this.getCasterId() || this.tickCount % 20 == 0) {
            this.setCaster(this.caster);
         }
      } else {
         this.prevYaw = this.renderYaw;
         this.prevPitch = this.renderPitch;
         this.renderYaw = this.getYaw();
         this.renderPitch = this.getPitch();
         this.xo = this.getX();
         this.yo = this.getY();
         this.zo = this.getZ();
         Vec3 endPos = ESMathUtil.rotationToPosition(this.position(), this.getLength(), this.getPitch(), this.getYaw());
         this.addEndParticles(endPos);
      }
   }

   public boolean isOnFire() {
      return false;
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         this.discard();
         return true;
      } else {
         return false;
      }
   }

   public int getRadius() {
      return 20;
   }

   public void onHit(ESEntityUtil.RaytraceResult result) {
      for (Entity target : result.entities()) {
         if (target instanceof LivingEntity living && (this.getCaster().isEmpty() || living != this.getCaster().get())) {
            this.doHurtTarget(living);
         }
      }
   }

   public void doHurtTarget(LivingEntity target) {
      this.getCaster()
         .ifPresent(
            caster -> {
               if (ESEntityUtil.shouldHarm(caster, target)
                  && target.hurt(ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.LASER, this, caster), this.getAttackDamage())) {
                  target.setRemainingFireTicks(Math.max(target.getRemainingFireTicks(), 100));
               }
            }
         );
   }

   public void addEndParticles(Vec3 endPos) {
   }

   public float getRotationSpeed() {
      return 1.0F;
   }

   public float getAttackDamage() {
      return 8.0F;
   }

   public void push(Entity entity) {
   }

   public boolean isPickable() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      return distance < 1024.0;
   }

   protected void readAdditionalSaveData(CompoundTag compoundTag) {
   }

   protected void addAdditionalSaveData(CompoundTag compoundTag) {
   }

   private void update() {
      this.updateRotations();
      this.updatePosition();
   }

   protected float rotateTowards(float from, float to, float maxAngle) {
      float f = Mth.degreesDifference(from, to);
      float f1 = Mth.clamp(f, -maxAngle, maxAngle);
      return from + f1;
   }

   public void updateRotations() {
      this.getCaster().ifPresent(caster -> {
         if (caster instanceof LivingEntity living) {
            if (caster instanceof RayAttackUser shooter && !shooter.isRayFollowingHeadRotation()) {
               Vec3 idealEndPos = ESMathUtil.rotationToPosition(this.position(), this.getRadius(), this.getPitch(), this.getYaw());
               shooter.updateRayEnd(idealEndPos);
               Vec3 wantedPos = shooter.getRayRotationTarget();
               float wantedYaw = ESMathUtil.positionToYaw(this.position(), wantedPos);
               float wantedPitch = ESMathUtil.positionToPitch(this.position(), wantedPos);
               float currentYaw = this.getYaw();
               float currentPitch = this.getPitch();
               this.setYaw(this.rotateTowards(currentYaw, wantedYaw, this.getRotationSpeed()));
               this.setPitch(this.rotateTowards(currentPitch, wantedPitch, this.getRotationSpeed()));
            } else {
               this.setYaw(living.yHeadRot + 90.0F);
               this.setPitch(-caster.getXRot());
            }
         }
      });
   }

   public void updatePosition() {
      this.getCaster().ifPresent(caster -> this.setPos(this.getPositionForCaster(caster, caster.position())));
   }

   public Vec3 getPositionForCaster(Entity caster, Vec3 casterPos) {
      return casterPos;
   }

   @Nullable
   public Entity getOwner() {
      return this.getCaster().orElse(null);
   }
}
