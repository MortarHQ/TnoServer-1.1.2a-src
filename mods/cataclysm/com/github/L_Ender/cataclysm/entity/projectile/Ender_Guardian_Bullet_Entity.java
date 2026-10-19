package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.event.EventHooks;

public class Ender_Guardian_Bullet_Entity extends AbstractHurtingProjectile {
   private double dirX;
   private double dirY;
   private double dirZ;
   private double startX;
   private double startY;
   private double startZ;
   private int timer;
   private boolean fired;

   public Ender_Guardian_Bullet_Entity(EntityType<? extends Ender_Guardian_Bullet_Entity> type, Level world) {
      super(type, world);
   }

   public Ender_Guardian_Bullet_Entity(Level worldIn, LivingEntity shooter, Vec3 accel) {
      super((EntityType)ModEntities.ENDER_GUARDIAN_BULLET.get(), shooter, accel, worldIn);
   }

   protected void defineSynchedData(Builder p_326229_) {
      super.defineSynchedData(p_326229_);
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      if (this.level() instanceof ServerLevel serverlevel && this.fired) {
         Entity entity = result.getEntity();
         Entity Shooter = this.getOwner();
         LivingEntity livingentity = Shooter instanceof LivingEntity ? (LivingEntity)Shooter : null;
         DamageSource damagesource = this.damageSources().mobProjectile(this, livingentity);
         boolean flag = entity.hurt(damagesource, 6.0F);
         if (flag) {
            EnchantmentHelper.doPostAttackEffects(serverlevel, entity, damagesource);
            if (entity instanceof LivingEntity) {
               ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.LEVITATION, 100));
            }
         }
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      if (this.fired) {
         ((ServerLevel)this.level()).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 2, 0.2, 0.2, 0.2, 0.0);
         this.playSound(SoundEvents.SHULKER_BULLET_HIT, 1.0F, 1.0F);
      }
   }

   protected void onHit(HitResult result) {
      super.onHit(result);
      if (this.fired) {
         this.discard();
      }
   }

   public void setUp(int delay, double dirX, double dirY, double dirZ, double startX, double startY, double startZ) {
      this.fired = false;
      this.timer = delay;
      this.dirX = dirX;
      this.dirY = dirY;
      this.dirZ = dirZ;
      this.startX = startX;
      this.startY = startY;
      this.startZ = startZ;
   }

   public void setUpTowards(int delay, double startX, double startY, double startZ, double endX, double endY, double endZ, double speed) {
      Vec3 vec = new Vec3(endX - startX, endY - startY, endZ - startZ).normalize().scale(speed);
      this.setUp(delay, vec.x, vec.y, vec.z, startX, startY, startZ);
   }

   public void tick() {
      if (!this.level().isClientSide) {
         this.timer--;
         if (this.timer <= 0) {
            if (this.fired) {
               this.discard();
            } else {
               this.fired = true;
               this.setDeltaMovement(new Vec3(0.0, 0.0, 0.0));
               this.timer = 30;
            }
         }

         Vec3 DeltaMovement = this.getDeltaMovement();
         double d0 = this.getX();
         double d1 = this.getY();
         double d2 = this.getZ();
         if (this.fired) {
            if (DeltaMovement.lengthSqr() <= 16.0) {
               this.setDeltaMovement(DeltaMovement.add(this.dirX * 0.1, this.dirY * 0.1, this.dirZ * 0.1));
            }
         } else {
            this.setDeltaMovement(new Vec3(this.startX - d0, this.startY - d1, this.startZ - d2).scale(1.0 / this.timer));
         }
      }

      Entity shooter = this.getOwner();
      if (this.level().isClientSide || (shooter == null || !shooter.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
         HitResult HitResult = ProjectileUtil.getHitResultOnMoveVector(this, x$0 -> this.canHitEntity(x$0));
         if (HitResult.getType() != Type.MISS && !EventHooks.onProjectileImpact(this, HitResult)) {
            this.onHit(HitResult);
         }

         this.checkInsideBlocks();
         Vec3 Vec3 = this.getDeltaMovement();
         double d0 = this.getX() + Vec3.x;
         double d1 = this.getY() + Vec3.y;
         double d2 = this.getZ() + Vec3.z;
         ProjectileUtil.rotateTowardsMovement(this, 0.2F);
         this.level().addParticle(ParticleTypes.END_ROD, this.getX() - Vec3.x, this.getY() - Vec3.y + 0.15, this.getZ() - Vec3.z, 0.0, 0.0, 0.0);
         this.setPos(d0, d1, d2);
      } else {
         this.discard();
      }
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putDouble("DX", this.dirX);
      compound.putDouble("DY", this.dirY);
      compound.putDouble("DZ", this.dirZ);
      compound.putDouble("SX", this.startX);
      compound.putDouble("SY", this.startY);
      compound.putDouble("SZ", this.startZ);
      compound.putInt("Timer", this.timer);
      compound.putBoolean("Fired", this.fired);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.dirX = compound.getDouble("DX");
      this.dirY = compound.getDouble("DY");
      this.dirZ = compound.getDouble("DZ");
      this.startX = compound.getDouble("SX");
      this.startY = compound.getDouble("SY");
      this.startZ = compound.getDouble("SZ");
      this.timer = compound.getInt("Timer");
      this.fired = compound.getBoolean("Fired");
   }

   public SoundSource getSoundSource() {
      return SoundSource.HOSTILE;
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public boolean hurt(DamageSource source, float amount) {
      if (!this.level().isClientSide && this.fired) {
         this.playSound(SoundEvents.SHULKER_BULLET_HURT, 1.0F, 1.0F);
         ((ServerLevel)this.level()).sendParticles(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 15, 0.2, 0.2, 0.2, 0.0);
         this.discard();
      }

      return true;
   }
}
