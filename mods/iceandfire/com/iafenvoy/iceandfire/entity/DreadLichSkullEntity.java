package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.registry.IafParticles;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;

public class DreadLichSkullEntity extends AbstractArrow {
   public DreadLichSkullEntity(EntityType<? extends AbstractArrow> type, Level worldIn) {
      super(type, worldIn);
      this.setBaseDamage(6.0);
   }

   public DreadLichSkullEntity(EntityType<? extends AbstractArrow> type, Level worldIn, LivingEntity shooter, double dmg) {
      super(type, worldIn);
      this.setOwner(shooter);
      this.setBaseDamage(dmg);
   }

   public boolean isInWater() {
      return false;
   }

   public void tick() {
      float sqrt = Mth.sqrt((float)(this.getDeltaMovement().x * this.getDeltaMovement().x + this.getDeltaMovement().z * this.getDeltaMovement().z));
      if ((sqrt < 0.1F || this.horizontalCollision || this.verticalCollision || this.inGround) && this.tickCount > 5) {
         this.remove(RemovalReason.DISCARDED);
      }

      Entity shootingEntity = this.getOwner();
      if (shootingEntity instanceof Mob mob && mob.getTarget() != null) {
         LivingEntity target = mob.getTarget();
         double minusX = target.getX() - this.getX();
         double minusY = target.getY() - this.getY();
         double minusZ = target.getZ() - this.getZ();
         double speed = 0.15;
         this.setDeltaMovement(this.getDeltaMovement().add(minusX * speed * 0.1, minusY * speed * 0.1, minusZ * speed * 0.1));
      }

      if (shootingEntity instanceof Player player) {
         LivingEntity target = player.getKillCredit();
         if (target == null || !target.isAlive()) {
            double d0 = 10.0;
            List<Entity> list = this.level()
               .getEntities(
                  shootingEntity,
                  new AABB(this.getX(), this.getY(), this.getZ(), this.getX() + 1.0, this.getY() + 1.0, this.getZ() + 1.0).inflate(d0, 10.0, d0),
                  EntitySelector.ENTITY_STILL_ALIVE
               );
            LivingEntity closest = null;
            if (!list.isEmpty()) {
               for (Entity e : list) {
                  if (e instanceof LivingEntity living
                     && !e.getUUID().equals(shootingEntity.getUUID())
                     && e instanceof Enemy
                     && (closest == null || closest.distanceTo(shootingEntity) > e.distanceTo(shootingEntity))) {
                     closest = living;
                  }
               }
            }

            target = closest;
         }

         if (target != null && target.isAlive()) {
            double minusX = target.getX() - this.getX();
            double minusY = target.getY() + target.getEyeHeight() - this.getY();
            double minusZ = target.getZ() - this.getZ();
            double speed = 0.25 * Math.min(this.distanceTo(target), 10.0) / 10.0;
            this.setDeltaMovement(
               this.getDeltaMovement()
                  .add(
                     (Math.signum(minusX) * 0.5 - this.getDeltaMovement().x) * 0.1F,
                     (Math.signum(minusY) * 0.5 - this.getDeltaMovement().y) * 0.1F,
                     (Math.signum(minusZ) * 0.5 - this.getDeltaMovement().z) * 0.1F
                  )
            );
            this.setYRot((float)(Mth.atan2(this.getDeltaMovement().x, this.getDeltaMovement().z) * (180.0 / Math.PI)));
            this.setXRot((float)(Mth.atan2(this.getDeltaMovement().y, sqrt) * (180.0 / Math.PI)));
         }
      }

      double d0 = 0.0;
      double d1 = 0.01;
      double d2 = 0.0;
      double x = this.getX() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth();
      double y = this.getY() + this.random.nextFloat() * this.getBbHeight() - this.getBbHeight();
      double z = this.getZ() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth();
      float f = (this.getBbWidth() + this.getBbHeight() + this.getBbWidth()) * 0.333F + 0.5F;
      if (this.particleDistSq(x, y, z) < f * f) {
         this.level().addParticle((ParticleOptions)IafParticles.DREAD_TORCH.get(), x, y + 0.5, z, d0, d1, d2);
      }

      super.tick();
   }

   public double particleDistSq(double toX, double toY, double toZ) {
      double d0 = this.getX() - toX;
      double d1 = this.getY() - toY;
      double d2 = this.getZ() - toZ;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public void playSound(SoundEvent soundIn, float volume, float pitch) {
      if (!this.isSilent() && soundIn != SoundEvents.ARROW_HIT && soundIn != SoundEvents.ARROW_HIT_PLAYER) {
         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), soundIn, this.getSoundSource(), volume, pitch);
      }
   }

   protected void onHitEntity(EntityHitResult raytraceResultIn) {
      Entity entity = raytraceResultIn.getEntity();
      Entity shootingEntity = this.getOwner();
      if (entity == null || shootingEntity == null || !entity.isAlliedTo(shootingEntity)) {
         super.onHitEntity(raytraceResultIn);
      }
   }

   protected void doPostHurtEffects(LivingEntity living) {
      super.doPostHurtEffects(living);
      Entity shootingEntity = this.getOwner();
      if (living != null && (shootingEntity == null || !living.is(shootingEntity)) && living instanceof Player player) {
         this.damageShield(player, (float)this.getBaseDamage());
      }
   }

   protected ItemStack getDefaultPickupItem() {
      return new ItemStack(Items.ARROW);
   }

   protected void damageShield(Player player, float damage) {
      if (damage >= 3.0F && player.getUseItem().getItem() instanceof ShieldItem) {
         int i = 1 + Mth.floor(damage);
         player.getUseItem().hurtAndBreak(i, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
         if (player.getUseItem().isEmpty()) {
            player.stopUsingItem();
            this.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + this.level().random.nextFloat() * 0.4F);
         }
      }
   }

   public boolean isNoGravity() {
      return true;
   }
}
