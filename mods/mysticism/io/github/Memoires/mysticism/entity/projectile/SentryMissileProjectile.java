package io.github.Memoires.mysticism.entity.projectile;

import io.github.Memoires.mysticism.registry.entity.MysticismProjectileEntityTypes;
import io.github.manasmods.tensura.entity.projectile.TensuraFlyingProjectile;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SentryMissileProjectile extends TensuraFlyingProjectile implements GeoEntity {
   private static final EntityDataAccessor<Boolean> EXPLODED = SynchedEntityData.defineId(SentryMissileProjectile.class, EntityDataSerializers.BOOLEAN);
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public SentryMissileProjectile(EntityType<? extends SentryMissileProjectile> entityType, Level level) {
      super(entityType, level);
      this.explosionRadius = 1.0F;
   }

   public SentryMissileProjectile(Level levelIn, LivingEntity shooter) {
      super((EntityType)MysticismProjectileEntityTypes.SENTRY_MISSILE.get(), levelIn);
      this.setOwner(shooter);
      Vec3 direction = shooter.getLookAngle().normalize().scale(1.5);
      this.setDeltaMovement(direction);
   }

   protected boolean canHitEntity(Entity entity) {
      return entity != this.getOwner() && super.canHitEntity(entity);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(EXPLODED, false);
   }

   public boolean isNoGravity() {
      return true;
   }

   protected void onHit(HitResult result) {
      if (!this.level().isClientSide && !(Boolean)this.getEntityData().get(EXPLODED)) {
         this.getEntityData().set(EXPLODED, true);
         this.explosion(this.getX(), this.getY(), this.getZ());
      }

      super.onHit(result);
   }

   protected void onHitEntity(EntityHitResult result) {
      Entity entity = result.getEntity();
      this.getEntityData().set(EXPLODED, true);
      DamageSource damageSource = this.damageSources().indirectMagic(this, this.getOwner());
      this.explosion(this.getX(), this.getY(), this.getZ());
      entity.hurt(damageSource, 305.0F);
   }

   public void explosion(double x, double y, double z) {
      if (!(this.getExplosionRadius() <= 0.0F)) {
         boolean mobGrief = this.shouldGrief();
         boolean fire = mobGrief && this.burnTicks > 0;
         this.level().explode(this.getOwner(), x, y, z, this.getExplosionRadius(), fire, ExplosionInteraction.NONE);
      }
   }

   public void shootFromRot(Vec3 rotation) {
      this.setDeltaMovement(rotation.scale(this.getSpeed()));
      this.setInitialRotation(rotation);
   }

   public void setInitialRotation(Vec3 rotation) {
      double d0 = rotation.horizontalDistance();
      this.setYRot((float)(Mth.atan2(rotation.x, rotation.z) * (180.0 / Math.PI)) + 180.0F);
      this.setXRot((float)(Mth.atan2(-rotation.y, d0) * (180.0 / Math.PI)));
      this.yRotO = this.getYRot();
      this.xRotO = this.getXRot();
   }

   public void tick() {
      super.tick();
      this.tickHandler();
      if (this.level().isClientSide()) {
         this.flyingParticles();
      } else if (this.shouldRemove()) {
         this.discard();
      }
   }

   public void updateRotation() {
      Vec3 vec3 = this.getDeltaMovement();
      if (vec3.x != 0.0 || vec3.y != 0.0 || vec3.z != 0.0) {
         double d0 = vec3.horizontalDistance();
         float targetYRot = (float)(Mth.atan2(vec3.x, vec3.z) * (180.0 / Math.PI)) + 180.0F;
         float targetXRot = (float)(Mth.atan2(-vec3.y, d0) * (180.0 / Math.PI));
         this.setXRot(lerpRotation(this.xRotO, targetXRot));
         this.setYRot(lerpRotation(this.yRotO, targetYRot));
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
