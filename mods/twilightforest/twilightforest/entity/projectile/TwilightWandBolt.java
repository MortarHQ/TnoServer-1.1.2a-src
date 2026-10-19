package twilightforest.entity.projectile;

import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFEntities;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;

public class TwilightWandBolt extends TFThrowable {
   public TwilightWandBolt(EntityType<? extends TwilightWandBolt> type, Level world) {
      super(type, world);
   }

   public TwilightWandBolt(Level world, LivingEntity thrower) {
      super((EntityType<? extends TFThrowable>)TFEntities.WAND_BOLT.get(), world, thrower);
      this.shootFromRotation(thrower, thrower.getXRot(), thrower.getYRot(), 0.0F, 1.5F, 1.0F);
   }

   public TwilightWandBolt(Level worldIn, double x, double y, double z) {
      super((EntityType<? extends TFThrowable>)TFEntities.WAND_BOLT.get(), worldIn, x, y, z);
   }

   public void tick() {
      super.tick();
      this.makeTrail();
   }

   private void makeTrail() {
      for (int i = 0; i < 5; i++) {
         double dx = this.getX() + 0.25 * (this.random.nextDouble() - this.random.nextDouble());
         double dy = this.getY() + 0.25 * (this.random.nextDouble() - this.random.nextDouble());
         double dz = this.getZ() + 0.25 * (this.random.nextDouble() - this.random.nextDouble());
         float s1 = (this.random.nextFloat() * 0.5F + 0.5F) * 0.17F;
         float s2 = (this.random.nextFloat() * 0.5F + 0.5F) * 0.8F;
         float s3 = (this.random.nextFloat() * 0.5F + 0.5F) * 0.69F;
         this.level().addParticle(ColorParticleOption.create((ParticleType)TFParticleType.MAGIC_EFFECT.get(), s1, s2, s3), dx, dy, dz, 0.0, 0.0, 0.0);
      }
   }

   protected double getDefaultGravity() {
      return 0.003F;
   }

   protected boolean canHitEntity(Entity target) {
      return super.canHitEntity(target) && !(target instanceof LichBolt) && !(target instanceof LichBomb);
   }

   public void handleEntityEvent(byte id) {
      if (id == 3) {
         for (int i = 0; i < 8; i++) {
            this.level()
               .addParticle(
                  (ParticleOptions)TFParticleType.TWILIGHT_ORB.get(),
                  false,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  this.random.nextGaussian() * 0.05,
                  this.random.nextDouble() * 0.2,
                  this.random.nextGaussian() * 0.05
               );
         }
      } else {
         super.handleEntityEvent(id);
      }
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      if (!this.level().isClientSide()) {
         Entity hit = result.getEntity();
         if (hit instanceof LivingEntity) {
            hit.hurt(TFDamageTypes.getIndirectEntityDamageSource(this.level(), TFDamageTypes.TWILIGHT_SCEPTER, this, this.getOwner()), 6.0F);
         }

         this.level()
            .playSound(
               null,
               hit.blockPosition(),
               (SoundEvent)TFSounds.TWILIGHT_SCEPTER_HIT.get(),
               this.getOwner() != null ? this.getOwner().getSoundSource() : SoundSource.PLAYERS
            );
         this.level().broadcastEntityEvent(this, (byte)3);
         this.discard();
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      if (!this.level().isClientSide()) {
         this.level().broadcastEntityEvent(this, (byte)3);
         this.discard();
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      super.hurt(source, amount);
      if (!this.level().isClientSide() && source.getEntity() != null) {
         Vec3 vec3d = source.getEntity().getLookAngle();
         this.shoot(vec3d.x(), vec3d.y(), vec3d.z(), 1.5F, 0.1F);
         if (source.getDirectEntity() instanceof LivingEntity) {
            this.setOwner(source.getDirectEntity());
         }

         return true;
      } else {
         return false;
      }
   }
}
