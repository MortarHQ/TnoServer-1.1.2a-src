package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

public class EnergySpark extends ThrowableProjectile implements TrailOwner {
   private static final String TAG_TARGET = "target";
   private static final String TAG_SPAWNED_TICKS = "spawned_ticks";
   public float oSpin;
   public float spin;
   protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(EnergySpark.class, EntityDataSerializers.INT);
   @Nullable
   private LivingEntity target;
   @Nullable
   private UUID targetId;

   public int getSpawnedTicks() {
      return (Integer)this.getEntityData().get(SPAWNED_TICKS);
   }

   public void setSpawnedTicks(int spawnedTicks) {
      this.getEntityData().set(SPAWNED_TICKS, spawnedTicks);
   }

   public EnergySpark(EntityType<? extends EnergySpark> entityType, Level level) {
      super(entityType, level);
   }

   public EnergySpark(Level level, LivingEntity livingEntity) {
      super(ESEntities.ENERGY_SPARK.get(), livingEntity, level);
   }

   public EnergySpark(Level level, double x, double y, double z) {
      super(ESEntities.ENERGY_SPARK.get(), x, y, z, level);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(SPAWNED_TICKS, 0);
   }

   public LivingEntity getTarget() {
      return this.target;
   }

   public void setTarget(LivingEntity target) {
      this.targetId = target.getUUID();
      this.target = target;
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.target == null && this.targetId != null && this.level() instanceof ServerLevel serverLevel) {
            if (serverLevel.getEntity(this.targetId) instanceof LivingEntity livingEntity) {
               this.target = livingEntity;
            }

            if (this.target == null) {
               this.targetId = null;
            }
         }

         if (this.target != null && !this.target.isAlive()) {
            this.target = null;
            this.targetId = null;
         }

         Vec3 targetPos = this.getOwner() instanceof Player
            ? this.position().add(this.getRandom().nextInt(-10, 11), this.getRandom().nextInt(-10, 11), this.getRandom().nextInt(-10, 11))
            : this.position().add(this.getDeltaMovement().normalize());
         if (this.target != null && (this.getOwner() instanceof Player || this.tickCount < 80)) {
            targetPos = this.target.position().add(0.0, this.target.getBbHeight() / 2.0F, 0.0);
         }

         if (this.getSpawnedTicks() <= 40 && this.tickCount % 2 == 0 && this.getOwner() instanceof Player) {
            Vec3 delta = new Vec3(this.getRandom().nextFloat() - 0.5, this.getRandom().nextFloat() - 0.5, this.getRandom().nextFloat() - 0.5);
            double length = this.getDeltaMovement().length();
            this.setDeltaMovement(this.getDeltaMovement().add(delta.normalize().scale(Math.max(length * 0.05, 0.005))).normalize().scale(length));
         }

         if (this.getSpawnedTicks() > 40) {
            Vec3 delta = targetPos.subtract(this.position());
            double lengthSqr = delta.lengthSqr();
            if (lengthSqr < 100.0) {
               double e = 1.0 - Math.sqrt(lengthSqr) / 10.0;
               this.setDeltaMovement(this.getDeltaMovement().add(delta.normalize().scale(e * e * 0.03)));
            } else {
               this.setDeltaMovement(this.getDeltaMovement().add(delta.normalize().scale(0.05)));
            }

            if (this.target != null && this.target.getBoundingBox().intersects(this.getBoundingBox())) {
               this.hurtTarget(this.target);
            }
         }

         if (this.getSpawnedTicks() > (this.getOwner() instanceof Player ? 600 : 120)) {
            this.discard();
         }

         this.setSpawnedTicks(this.getSpawnedTicks() + 1);
      }

      this.oSpin = this.spin;
      this.spin = this.spin + 0.18849556F * (this.getSpawnedTicks() / 100.0F);
   }

   protected void applyGravity() {
   }

   public boolean isOnFire() {
      return false;
   }

   protected void onHitBlock(BlockHitResult hitResult) {
      super.onHitBlock(hitResult);
      switch (hitResult.getDirection().getAxis()) {
         case X:
            this.setDeltaMovement(this.getDeltaMovement().multiply(-1.0, 1.0, 1.0));
            break;
         case Y:
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, -1.0, 1.0));
            break;
         case Z:
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 1.0, -1.0));
      }
   }

   protected void onHitEntity(EntityHitResult hitResult) {
      super.onHitEntity(hitResult);
      this.hurtTarget(hitResult.getEntity());
   }

   private void hurtTarget(Entity entity) {
      if ((this.getTarget() == null || entity == this.getTarget()) && entity != this.getOwner()) {
         entity.invulnerableTime = 0;
         entity.hurt(ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.ENERGIZED_FLAME, this, this.getOwner()), 3.0F);
      }

      this.discard();
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      if (compoundTag.hasUUID("target")) {
         this.targetId = compoundTag.getUUID("target");
      }

      this.setSpawnedTicks(compoundTag.getInt("spawned_ticks"));
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      if (this.target != null) {
         compoundTag.putUUID("target", this.target.getUUID());
      }

      compoundTag.putInt("spawned_ticks", this.getSpawnedTicks());
   }

   @Override
   public TrailEffect createNewTrail() {
      return new TrailEffect(0.125F, 3.0F);
   }

   @Override
   public void updateTrail(TrailEffect effect) {
      Vec3 oldPos = new Vec3(this.xOld, this.yOld, this.zOld);
      effect.update(oldPos.add(0.0, this.getBbHeight() / 2.0F, 0.0));
      if (this.isRemoved()) {
         effect.setLength(Math.max(effect.getLength() - 0.5F, 0.0F));
      }
   }

   @Override
   public Vector4f getTrailColor() {
      return new Vector4f(0.5019608F, 1.0F, 1.0F, 2.0F);
   }
}
