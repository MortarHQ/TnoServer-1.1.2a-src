package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.everlasting;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EverlastingBackpackItemEntity extends ItemEntity {
   private boolean wasFloatingUp = false;

   public EverlastingBackpackItemEntity(EntityType<? extends ItemEntity> type, Level level) {
      super(type, level);
      this.lifespan = Integer.MAX_VALUE;
   }

   public void tick() {
      if (!this.level().isClientSide) {
         double d0 = this.getX() + 0.5 - this.random.nextFloat();
         double d1 = this.getY() + this.random.nextFloat() * 0.5F;
         double d2 = this.getZ() + 0.5 - this.random.nextFloat();
         ServerLevel serverLevel = (ServerLevel)this.level();
         if (this.random.nextInt(20) == 0) {
            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, d0, d1, d2, 0, 0.0, 0.1, 0.0, 1.0);
         }
      }

      if (!this.isNoGravity()) {
         if (this.isInWater() || this.isInLava()) {
            this.onInsideBubbleColumn(false);
            this.wasFloatingUp = true;
         } else if (this.wasFloatingUp) {
            this.setNoGravity(true);
            this.setDeltaMovement(Vec3.ZERO);
         }
      }

      super.tick();
   }

   public boolean isInWater() {
      return this.getY() < this.level().getMinBuildHeight() + 1 || super.isInWater();
   }

   public boolean fireImmune() {
      return true;
   }

   public boolean ignoreExplosion(Explosion explosion) {
      return true;
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return true;
   }

   protected void onBelowWorld() {
   }
}
