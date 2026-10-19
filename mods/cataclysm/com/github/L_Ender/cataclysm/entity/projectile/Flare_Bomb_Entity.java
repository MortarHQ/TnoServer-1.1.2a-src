package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity.Netherite_Monstrosity_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity.Netherite_Monstrosity_Part;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Flare_Bomb_Entity extends ThrowableProjectile {
   public double prevDeltaMovementX;
   public double prevDeltaMovementY;
   public double prevDeltaMovementZ;
   private Vec3[] trailPositions = new Vec3[64];
   private int trailPointer = -1;

   public Flare_Bomb_Entity(EntityType<Flare_Bomb_Entity> type, Level world) {
      super(type, world);
   }

   public Flare_Bomb_Entity(EntityType<Flare_Bomb_Entity> type, Level world, LivingEntity thrower) {
      super(type, thrower, world);
   }

   protected void defineSynchedData(Builder p_326229_) {
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      Entity shooter = this.getOwner();
      if (this.level() instanceof ServerLevel serverlevel
         && !(result.getEntity() instanceof Flare_Bomb_Entity)
         && (
            !(shooter instanceof Netherite_Monstrosity_Entity)
               || !(result.getEntity() instanceof Netherite_Monstrosity_Part) && !(result.getEntity() instanceof Netherite_Monstrosity_Entity)
         )) {
         Entity entity = result.getEntity();
         if (this.getOwner() instanceof LivingEntity livingentity) {
            DamageSource damagesource = this.damageSources().mobProjectile(this, livingentity);
            boolean flag = entity.hurt(damagesource, 7.0F);
            if (flag && entity.isAlive()) {
               entity.igniteForSeconds(5.0F);
               EnchantmentHelper.doPostAttackEffects(serverlevel, entity, damagesource);
            }
         } else {
            entity.hurt(this.damageSources().magic(), 7.0F);
         }
      }
   }

   protected void onHit(HitResult p_37628_) {
      super.onHit(p_37628_);
      if (!this.level().isClientSide) {
         this.playSound(SoundEvents.GENERIC_BURN, 1.5F, 0.75F);
         this.level().broadcastEntityEvent(this, (byte)4);
         if (this.random.nextBoolean()) {
            this.XStrikeRune(10, 2.0);
         } else {
            this.PlusStrikeRune(10, 2.0);
         }

         this.discard();
      }
   }

   protected void PlusStrikeRune(int rune, double time) {
      for (int i = 0; i < 4; i++) {
         float yawRadians = (float)Math.toRadians(90.0F + this.getYRot());
         float throwAngle = yawRadians + i * (float) Math.PI / 2.0F;

         for (int k = 0; k < rune; k++) {
            double d2 = 0.8 * (k + 1);
            int d3 = (int)(time * (k + 1));
            this.spawnJet(
               this.getX() + Mth.cos(throwAngle) * 1.25 * d2,
               this.getZ() + Mth.sin(throwAngle) * 1.25 * d2,
               this.getY() - 2.0,
               this.getY() + 2.0,
               throwAngle,
               d3
            );
         }
      }
   }

   protected void XStrikeRune(int rune, double time) {
      for (int i = 0; i < 4; i++) {
         float yawRadians = (float)Math.toRadians(45.0F + this.getYRot());
         float throwAngle = yawRadians + i * (float) Math.PI / 2.0F;

         for (int k = 0; k < rune; k++) {
            double d2 = 0.8 * (k + 1);
            int d3 = (int)(time * (k + 1));
            this.spawnJet(
               this.getX() + Mth.cos(throwAngle) * 1.25 * d2,
               this.getZ() + Mth.sin(throwAngle) * 1.25 * d2,
               this.getY() - 2.0,
               this.getY() + 2.0,
               throwAngle,
               d3
            );
         }
      }
   }

   protected void spawnJet(double x, double z, double minY, double maxY, float rotation, int delay) {
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
         if (this.getOwner() != null && this.getOwner() instanceof LivingEntity living) {
            this.level().addFreshEntity(new Flame_Jet_Entity(this.level(), x, blockpos.getY() + d0, z, rotation, delay, 7.0F, living));
         } else {
            this.level().addFreshEntity(new Flame_Jet_Entity(this.level(), x, blockpos.getY() + d0, z, rotation, delay, 7.0F, null));
         }
      }
   }

   public void tick() {
      super.tick();
      this.prevDeltaMovementX = this.getDeltaMovement().x;
      this.prevDeltaMovementY = this.getDeltaMovement().y;
      this.prevDeltaMovementZ = this.getDeltaMovement().z;
      this.setYRot(-((float)Mth.atan2(this.getDeltaMovement().x, this.getDeltaMovement().z)) * (180.0F / (float)Math.PI));
      Vec3 trailAt = this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0);
      if (this.trailPointer == -1) {
         Vec3 backAt = trailAt;

         for (int i = 0; i < this.trailPositions.length; i++) {
            this.trailPositions[i] = backAt;
         }
      }

      if (++this.trailPointer == this.trailPositions.length) {
         this.trailPointer = 0;
      }

      this.trailPositions[this.trailPointer] = trailAt;
   }

   public Vec3 getTrailPosition(int pointer, float partialTick) {
      if (this.isRemoved()) {
         partialTick = 1.0F;
      }

      int i = this.trailPointer - pointer & 63;
      int j = this.trailPointer - pointer - 1 & 63;
      Vec3 d0 = this.trailPositions[j];
      Vec3 d1 = this.trailPositions[i].subtract(d0);
      return d0.add(d1.scale(partialTick));
   }

   public void handleEntityEvent(byte id) {
      super.handleEntityEvent(id);
      if (id == 4) {
         this.level()
            .playLocalSound(
               this.getX(),
               this.getY(),
               this.getZ(),
               (SoundEvent)ModSounds.EXPLOSION.get(),
               SoundSource.BLOCKS,
               4.0F,
               (1.0F + (this.level().random.nextFloat() - this.level().random.nextFloat()) * 0.2F) * 0.7F,
               false
            );
         this.level().addParticle((ParticleOptions)ModParticle.FLARE_EXPLODE.get(), this.getX(), this.getY(), this.getZ(), 0.1, 0.0, 0.0);
      }
   }

   public float getLightLevelDependentMagicValue() {
      return 1.0F;
   }

   protected double getDefaultGravity() {
      return 0.025;
   }

   public boolean hasTrail() {
      return this.trailPointer != -1;
   }
}
