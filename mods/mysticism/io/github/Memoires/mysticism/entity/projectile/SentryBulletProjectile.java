package io.github.Memoires.mysticism.entity.projectile;

import io.github.Memoires.mysticism.registry.entity.MysticismProjectileEntityTypes;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.projectile.TensuraFlyingProjectile;
import java.util.Optional;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SentryBulletProjectile extends TensuraFlyingProjectile {
   protected boolean magic = false;

   public SentryBulletProjectile(EntityType<? extends SentryBulletProjectile> entityType, Level level) {
      super(entityType, level);
      this.setSize(1.0F);
   }

   public SentryBulletProjectile(Level worldIn, LivingEntity shooter, boolean right) {
      super((EntityType)MysticismProjectileEntityTypes.SENTRY_BULLET.get(), worldIn);
      this.setOwner(shooter);
      this.setSize(1.0F);
      float rot = shooter.yHeadRot + (right ? 60 : -60);
      this.setPos(
         shooter.getX() - shooter.getBbWidth() * 0.5 * Mth.sin(rot * (float) (Math.PI / 180.0)),
         shooter.getEyeY() - 0.2F,
         shooter.getZ() + shooter.getBbWidth() * 0.5 * Mth.cos(rot * (float) (Math.PI / 180.0))
      );
   }

   public boolean shouldDiscardInWater() {
      return false;
   }

   public boolean shouldDiscardInLava() {
      return false;
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("Magic", this.isMagic());
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setMagic(compound.getBoolean("Magic"));
   }

   public ResourceKey<DamageType> getDamageType() {
      return this.isMagic() ? TensuraDamageTypes.BULLET_MAGIC : TensuraDamageTypes.BULLET;
   }

   public Optional<SoundEvent> hitSound() {
      return Optional.of(SoundEvents.ITEM_BREAK);
   }

   public void hitParticles(double x, double y, double z) {
   }

   public void flyingParticles() {
      Vec3 vec3 = this.position().subtract(this.getDeltaMovement().scale(2.0));
      this.level().addParticle(ParticleTypes.SMOKE, vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
   }

   public void setMagic(boolean magic) {
      this.magic = magic;
   }

   public boolean isMagic() {
      return this.magic;
   }
}
