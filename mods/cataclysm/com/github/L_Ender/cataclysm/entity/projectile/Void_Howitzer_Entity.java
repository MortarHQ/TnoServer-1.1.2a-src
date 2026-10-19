package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Void_Howitzer_Entity extends ThrowableProjectile {
   public Void_Howitzer_Entity(EntityType<Void_Howitzer_Entity> type, Level world) {
      super(type, world);
   }

   public Void_Howitzer_Entity(EntityType<Void_Howitzer_Entity> type, Level world, LivingEntity thrower) {
      super(type, thrower, world);
   }

   protected void defineSynchedData(Builder p_326229_) {
   }

   protected void onHitEntity(EntityHitResult p_37626_) {
      super.onHitEntity(p_37626_);
      if (this.level() instanceof ServerLevel serverlevel) {
         Entity entity = p_37626_.getEntity();
         if (this.getOwner() instanceof LivingEntity livingentity) {
            DamageSource damagesource = this.damageSources().indirectMagic(this, livingentity);
            boolean flag = entity.hurt(damagesource, 8.0F);
            if (flag) {
               if (entity.isAlive()) {
                  EnchantmentHelper.doPostAttackEffects(serverlevel, entity, damagesource);
               } else {
                  livingentity.heal(5.0F);
               }
            }
         } else {
            entity.hurt(this.damageSources().magic(), 5.0F);
         }
      }
   }

   protected void onHit(HitResult p_37628_) {
      super.onHit(p_37628_);
      if (!this.level().isClientSide) {
         int standingOnY = Mth.floor(this.getY()) - 3;
         this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0F, false, ExplosionInteraction.NONE);

         for (int k = 0; k < 6; k++) {
            float f2 = k * (float) Math.PI * 2.0F / 6.0F + (float) (Math.PI * 2.0 / 5.0);
            this.spawnFangs(this.getX() + Mth.cos(f2) * 2.5, this.getZ() + Mth.sin(f2) * 2.5, standingOnY, this.getY() + 1.0, f2, 0);
         }

         for (int k = 0; k < 11; k++) {
            float f3 = k * (float) Math.PI * 2.0F / 11.0F + (float) (Math.PI / 5);
            this.spawnFangs(this.getX() + Mth.cos(f3) * 3.5, this.getZ() + Mth.sin(f3) * 3.5, standingOnY, this.getY() + 1.0, f3, 2);
         }

         for (int k = 0; k < 14; k++) {
            float f4 = k * (float) Math.PI * 2.0F / 14.0F + (float) (Math.PI / 10);
            this.spawnFangs(this.getX() + Mth.cos(f4) * 4.5, this.getZ() + Mth.sin(f4) * 4.5, standingOnY, this.getY() + 1.0, f4, 4);
         }

         for (int k = 0; k < 19; k++) {
            float f5 = k * (float) Math.PI * 2.0F / 19.0F + 0.25132743F;
            this.spawnFangs(this.getX() + Mth.cos(f5) * 5.5, this.getZ() + Mth.sin(f5) * 5.5, standingOnY, this.getY() + 1.0, f5, 6);
         }

         for (int k = 0; k < 26; k++) {
            float f5 = k * (float) Math.PI * 2.0F / 26.0F + 0.17951958F;
            this.spawnFangs(this.getX() + Mth.cos(f5) * 6.5, this.getZ() + Mth.sin(f5) * 6.5, standingOnY, this.getY() + 1.0, f5, 8);
         }

         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 40.0F, 0.3F, 0, 20);
         this.discard();
      }
   }

   protected void spawnFangs(double x, double z, double minY, double maxY, float rotation, int delay) {
      BlockPos blockpos = BlockPos.containing(x, maxY, z);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.below();
         BlockState blockstate = this.level().getBlockState(blockpos1);
         if (blockstate.isFaceSturdy(this.level(), blockpos1, Direction.UP)) {
            if (!this.level().isEmptyBlock(blockpos)) {
               BlockState blockstate1 = this.level().getBlockState(blockpos);
               VoxelShape voxelshape = blockstate1.getCollisionShape(this.level(), blockpos);
               if (!voxelshape.isEmpty()) {
                  d0 = voxelshape.max(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.below();
      } while (blockpos.getY() >= Mth.floor(minY) - 1);

      if (flag) {
         LivingEntity entity1 = (LivingEntity)this.getOwner();
         this.level().addFreshEntity(new Void_Rune_Entity(this.level(), x, blockpos.getY() + d0, z, rotation, delay, 7.0F, entity1));
      }
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         Vec3 vec3 = this.getDeltaMovement();
         this.level().addParticle(ParticleTypes.REVERSE_PORTAL, this.getX() - vec3.x, this.getY() - vec3.y, this.getZ() - vec3.z, 0.0, 0.0, 0.0);
         this.level().addParticle(ParticleTypes.SMOKE, this.getX() - vec3.x, this.getY() - vec3.y, this.getZ() - vec3.z, 0.0, 0.0, 0.0);
      }
   }

   protected double getDefaultGravity() {
      return 0.03F;
   }
}
