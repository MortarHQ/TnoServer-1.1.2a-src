package twilightforest.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SlimeProjectile extends TFThrowable implements ItemSupplier {
   public SlimeProjectile(EntityType<? extends SlimeProjectile> type, Level world) {
      super(type, world);
   }

   public SlimeProjectile(EntityType<? extends SlimeProjectile> type, Level world, LivingEntity thrower) {
      super(type, world, thrower);
   }

   public void tick() {
      super.tick();
      this.makeTrail(ParticleTypes.ITEM_SLIME, 2);
   }

   protected double getDefaultGravity() {
      return 0.006F;
   }

   public boolean hurt(DamageSource source, float amount) {
      super.hurt(source, amount);
      this.die();
      return true;
   }

   public void handleEntityEvent(byte id) {
      if (id == 3) {
         for (int i = 0; i < 8; i++) {
            this.level()
               .addParticle(
                  ParticleTypes.ITEM_SLIME,
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
      Entity target = result.getEntity();
      if (!this.level().isClientSide() && target instanceof LivingEntity) {
         target.hurt(this.damageSources().thrown(this, this.getOwner()), 4.0F);
      }
   }

   protected void onHit(HitResult result) {
      super.onHit(result);
      this.die();
   }

   private void die() {
      if (!this.level().isClientSide()) {
         this.playSound(SoundEvents.SLIME_SQUISH, 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
         this.discard();
         this.level().broadcastEntityEvent(this, (byte)3);
      }
   }

   public ItemStack getItem() {
      return new ItemStack(Items.SLIME_BALL);
   }
}
