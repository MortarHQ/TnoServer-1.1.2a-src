package net.unusual.block_factorys_bosses.entity.projectile;

import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.event.EventHooks;
import net.unusual.block_factorys_bosses.client.ClientParticleHandler;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

public class RiftProjectileEntity extends Projectile {
   private Entity target;
   private int timer = 0;

   public RiftProjectileEntity(EntityType<? extends RiftProjectileEntity> type, Level world) {
      super(type, world);
   }

   protected double getDefaultGravity() {
      return 0.0;
   }

   public void setTarget(Entity target) {
      this.target = target;
   }

   public void tick() {
      super.tick();
      Vec3 vel = this.getDeltaMovement();
      HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, x$0 -> this.canHitEntity(x$0));
      if (hitresult.getType() != Type.MISS && !EventHooks.onProjectileImpact(this, hitresult)) {
         this.hitTargetOrDeflectSelf(hitresult);
      }

      double d0 = this.getX() + vel.x;
      double d1 = this.getY() + vel.y;
      double d2 = this.getZ() + vel.z;
      this.updateRotation();
      if (++this.timer >= 60) {
         this.discard();
      } else if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockStateBase::isAir)) {
         this.discard();
      } else if (this.isInWaterOrBubble()) {
         this.discard();
      } else {
         double speed = vel.length();
         vel = vel.normalize();
         if (this.timer < 20 && this.target != null) {
            Vec3 targetDir = this.target.position().subtract(this.position()).normalize();
            double dot = vel.dot(targetDir);
            double theta = Math.min(Math.acos(Math.clamp(dot, -1.0, 1.0)), Math.toRadians(281.25 * speed) / 20.0);
            Vec3 rotationAxis = vel.cross(targetDir).normalize();
            double cosTheta = Math.cos(theta);
            double sinTheta = Math.sin(theta);
            Vec3 comp1 = vel.scale(cosTheta);
            Vec3 comp2 = rotationAxis.cross(vel).scale(sinTheta);
            Vec3 comp3 = rotationAxis.scale(dot * (1.0 - cosTheta));
            vel = comp1.add(comp2).add(comp3);
         }

         this.setDeltaMovement(vel.normalize().scale(speed));
         this.applyGravity();
         this.setPos(d0, d1, d2);
      }

      if (this.level().isClientSide()) {
         for (int i = 0; i < 2; i++) {
            Vec3 pos = new Vec3(
               this.getX() + this.random.nextFloat() - 0.5, this.getY() + this.random.nextFloat() - 0.5, this.getZ() + this.random.nextFloat() - 0.5
            );
            ClientParticleHandler.addParticle(ColorParticleOption.create((ParticleType)BossesRiseParticleTypes.PIXEL.get(), 62969), pos, Vec3.ZERO);
         }
      }
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      Entity entity = result.getEntity();
      if (this.getOwner() != entity) {
         DamageSource damagesource = this.damageSources().magic();
         if (entity.hurt(damagesource, 2.0F) && this.level() instanceof ServerLevel serverlevel) {
            EnchantmentHelper.doPostAttackEffects(serverlevel, entity, damagesource);
         }
      }

      if (this.level() instanceof ServerLevel level) {
         level.playSound(null, this.getX(), this.getY(), this.getZ(), BossesRiseSounds.RIFT_FIREBALL, SoundSource.HOSTILE, 0.5F, 1.0F);
         this.discard();
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      if (this.level() instanceof ServerLevel level) {
         level.sendParticles((SimpleParticleType)BossesRiseParticleTypes.RING.get(), this.getX(), this.getY(), this.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
         level.playSound(null, this.getX(), this.getY(), this.getZ(), BossesRiseSounds.RIFT_FIREBALL, SoundSource.HOSTILE, 0.25F, 1.0F);
         this.discard();
      }
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("Timer", this.timer);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.timer = compound.getInt("Timer");
   }

   protected void defineSynchedData(Builder builder) {
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      double d0 = this.getBoundingBox().getSize() * 10.0;
      if (Double.isNaN(d0)) {
         d0 = 0.0;
      }

      d0 *= 64.0 * getViewScale();
      return distance < d0 * d0;
   }
}
