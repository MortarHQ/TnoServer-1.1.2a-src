package net.unusual.block_factorys_bosses.entity;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.unusual.block_factorys_bosses.entity.projectile.BigRiftProjectileEntity;
import net.unusual.block_factorys_bosses.entity.projectile.RiftProjectileEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RiftEntity extends Entity {
   private static final String TAG_TIMER = "Timer";
   private int timer = 120;
   private Entity owner;
   private Entity target;

   public RiftEntity(EntityType<RiftEntity> type, Level world) {
      super(type, world);
      this.setNoGravity(true);
      this.noPhysics = true;
   }

   public void setOwnerAndTarget(Entity owner, Entity target) {
      this.owner = owner;
      this.target = target;
   }

   protected void defineSynchedData(Builder builder) {
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("Timer", this.timer);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      if (compound.contains("Timer")) {
         this.timer = compound.getInt("Timer");
      }
   }

   public void baseTick() {
      super.baseTick();
      this.level().addParticle(ColorParticleOption.create((ParticleType)BossesRiseParticleTypes.PIXEL.get(), 62969), true, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      if (this.timer == 120) {
         this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.RIFT_OPEN.get(), true, this.getX(), this.getY(), this.getZ(), 10.0, 0.0, 0.0);
         this.playSound((SoundEvent)BossesRiseSounds.RIFT_OPEN.value(), 8.0F, 1.0F);
         this.playSound((SoundEvent)BossesRiseSounds.RIFT_IDLE.value(), 4.0F, 1.0F);
      }

      if (this.timer == 112) {
         this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.BIG_STAR.get(), true, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
      }

      if (this.timer == 110) {
         this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.RIFT.get(), true, this.getX(), this.getY(), this.getZ(), 100.0, 0.0, 0.0);
      }

      if (this.timer == 30) {
         this.playSound((SoundEvent)BossesRiseSounds.RIFT_CLOSE.value(), 8.0F, 1.0F);
      }

      if (this.timer == 10) {
         this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.RIFT_CLOSE.get(), true, this.getX(), this.getY(), this.getZ(), 10.0, 0.0, 0.0);
      }

      if (this.timer > 20 && this.timer < 110) {
         if (this.timer % 2 == 0) {
            SimpleParticleType particle = this.random.nextFloat() > 0.5
               ? (SimpleParticleType)BossesRiseParticleTypes.SOUL.get()
               : (SimpleParticleType)BossesRiseParticleTypes.SOUL_FLIP.get();
            double x = this.getX() + this.random.nextFloat() * 4.5 - 2.25;
            double y = this.getY() + this.random.nextFloat() * 9.0F - 4.5;
            double z = this.getZ() + this.random.nextFloat() * 4.5 - 2.25;
            this.level().addParticle(particle, true, x, y, z, 0.0, 0.0, 0.0);
         }

         double x = this.getX() + this.random.nextFloat() * 4.0F - 2.0;
         double y = this.getY() + this.random.nextFloat() * 8.0F - 4.0;
         double z = this.getZ() + this.random.nextFloat() * 4.0F - 2.0;
         this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.WEIRD_SMOKE.get(), true, x, y, z, this.getX(), this.getY(), this.getZ());

         for (int i = 0; i < 2; i++) {
            double xx = this.getX() + this.random.nextFloat() * 3.0F - 1.5;
            double yx = this.getY() + this.random.nextFloat() * 6.0F - 3.0;
            double zx = this.getZ() + this.random.nextFloat() * 3.0F - 1.5;
            this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.STAR.get(), true, xx, yx, zx, 0.0, 0.0, 0.0);
         }

         if (!this.level().isClientSide() && this.timer % 2 == 0 && this.owner != null && this.target != null) {
            RiftProjectileEntity proj;
            if (this.timer % 20 == 0) {
               proj = new BigRiftProjectileEntity((EntityType<? extends BigRiftProjectileEntity>)BossesRiseEntities.BIG_RIFT_PROJECTILE.get(), this.level());
            } else {
               proj = new RiftProjectileEntity((EntityType<? extends RiftProjectileEntity>)BossesRiseEntities.RIFT_PROJECTILE.get(), this.level());
            }

            proj.setOwner(this.owner);
            proj.setTarget(this.target);
            proj.setPos(this.getX(), this.getY(), this.getZ());
            proj.shoot(0.0, 1.0, 0.0, 1.2F, 25.0F);
            this.level().addFreshEntity(proj);
         }
      }

      if (--this.timer <= 0) {
         this.discard();
      }
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }
}
