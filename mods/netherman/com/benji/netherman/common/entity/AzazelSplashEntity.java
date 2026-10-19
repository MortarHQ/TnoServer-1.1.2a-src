package com.benji.netherman.common.entity;

import com.benji.netherman.config.AzazelConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AzazelSplashEntity extends Projectile implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private int lifeTicks = 0;

   public AzazelSplashEntity(EntityType<? extends Projectile> type, Level level) {
      super(type, level);
      this.setNoGravity(true);
   }

   protected void defineSynchedData(Builder builder) {
   }

   public void tick() {
      super.tick();
      this.lifeTicks++;
      if (this.lifeTicks > 200) {
         this.discard();
      } else {
         HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, x$0 -> this.canHitEntity(x$0));
         if (hitresult.getType() != Type.MISS) {
            this.onHit(hitresult);
         }

         this.checkInsideBlocks();
         Vec3 move = this.getDeltaMovement();
         this.setPos(this.getX() + move.x, this.getY() + move.y, this.getZ() + move.z);
         if (move.lengthSqr() > 0.001) {
            double d0 = move.horizontalDistance();
            this.setYRot((float)(Mth.atan2(move.x, move.z) * 180.0F / (float)Math.PI));
            this.setXRot((float)(Mth.atan2(move.y, d0) * 180.0F / (float)Math.PI));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
         }

         if (this.level() instanceof ServerLevel sl) {
            sl.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY(), this.getZ(), 2, 0.5, 0.5, 0.5, 0.0);
         }
      }
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      if (!this.level().isClientSide() && result.getEntity() instanceof LivingEntity target) {
         if (target == this.getOwner() || target instanceof AzazelHumanEntity) {
            return;
         }

         float damage = ((Double)AzazelConfig.HUMAN_SPLASH_DAMAGE.get()).floatValue();
         target.hurt(this.damageSources().mobProjectile(this, (LivingEntity)this.getOwner()), damage);
         ((ServerLevel)this.level()).sendParticles(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 30, 1.0, 1.0, 1.0, 0.2);
         this.discard();
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      if (!this.level().isClientSide()) {
         this.discard();
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
