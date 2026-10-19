package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity.Netherite_Monstrosity_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity.Netherite_Monstrosity_Part;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.common.NeoForgeMod;

public class Lava_Bomb_Entity extends ThrowableProjectile {
   private static final EntityDataAccessor<Boolean> ON_GROUND = SynchedEntityData.defineId(Lava_Bomb_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> LAVA_TIME = SynchedEntityData.defineId(Lava_Bomb_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> MAX_LAVA_TIME = SynchedEntityData.defineId(Lava_Bomb_Entity.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<BlockPos> LAVA_POS = SynchedEntityData.defineId(Lava_Bomb_Entity.class, EntityDataSerializers.BLOCK_POS);

   public Lava_Bomb_Entity(EntityType<Lava_Bomb_Entity> type, Level world) {
      super(type, world);
   }

   public Lava_Bomb_Entity(EntityType<Lava_Bomb_Entity> type, Level world, LivingEntity thrower) {
      super(type, thrower, world);
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(ON_GROUND, false);
      p_326229_.define(LAVA_TIME, 0);
      p_326229_.define(MAX_LAVA_TIME, 200);
      p_326229_.define(LAVA_POS, BlockPos.ZERO);
   }

   protected void onHit(HitResult ray) {
      Type raytraceresult$type = ray.getType();
      if (raytraceresult$type == Type.ENTITY) {
         this.onHitEntity((EntityHitResult)ray);
      } else if (raytraceresult$type == Type.BLOCK) {
         this.onHitBlock((BlockHitResult)ray);
      }
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      Entity shooter = this.getOwner();
      if (!this.getGround()
         && !this.level().isClientSide
         && !(result.getEntity() instanceof Lava_Bomb_Entity)
         && !(result.getEntity() instanceof Netherite_Monstrosity_Part)
         && !(result.getEntity() instanceof Netherite_Monstrosity_Entity)) {
         this.playSound(SoundEvents.GENERIC_BURN, 1.5F, 0.75F);
         this.level().explode(shooter, this.getX(), this.getY(), this.getZ(), 2.0F, ExplosionInteraction.NONE);
         this.doTerrainEffects();
         this.setGround(true);
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      if (!this.level().isClientSide() && !this.getGround()) {
         this.playSound(SoundEvents.GENERIC_BURN, 1.5F, 0.75F);
         this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.0F, ExplosionInteraction.NONE);
         this.doTerrainEffects();
         this.setGround(true);
      }
   }

   protected void doTerrainEffects() {
      BlockPos landed = this.blockPosition();

      while (
         landed.getY() < this.level().getMaxBuildHeight()
            && (
               !this.level().getBlockState(landed).isAir()
                  || !this.level().getBlockState(landed).getFluidState().isEmpty()
                     && this.level().getBlockState(landed).getFluidState().getFluidType() != NeoForgeMod.LAVA_TYPE.value()
            )
      ) {
         landed = landed.above();
      }

      this.setLavaPos(landed);
      if (this.level().getBlockState(this.getLavaPos()).isAir()) {
         BlockState fluid = Blocks.LAVA.defaultBlockState();
         this.level().setBlockAndUpdate(this.getLavaPos(), fluid);
      }
   }

   public void tick() {
      super.tick();
      if (this.getGround()) {
         this.setLavaTime(this.getLavaTime() + 1);
         this.setDeltaMovement(Vec3.ZERO);
         if (!this.level().isClientSide && this.getLavaTime() >= this.getMaxLavaTime() && this.getLavaPos() != BlockPos.ZERO) {
            this.discard();
         }
      } else {
         this.makeTrail();
      }
   }

   public void remove(RemovalReason reason) {
      super.remove(reason);
      if (!this.level().isClientSide
         && this.getLavaPos() != BlockPos.ZERO
         && this.level().getFluidState(this.getLavaPos()).getFluidType() == NeoForgeMod.LAVA_TYPE.value()) {
         this.level().setBlockAndUpdate(this.getLavaPos(), Blocks.AIR.defaultBlockState());
      }
   }

   protected void applyGravity() {
      double d0 = this.getGravity();
      if (!this.getGround()) {
         if (d0 != 0.0) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -d0, 0.0));
         }
      } else {
         this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.0, 0.0));
      }
   }

   protected void makeTrail() {
      if (this.level().isClientSide) {
         for (int i = 0; i < 5; i++) {
            double dx = this.getX() + 1.5F * (this.random.nextFloat() - 0.5F);
            double dy = this.getY() + 1.5F * (this.random.nextFloat() - 0.5F);
            double dz = this.getZ() + 1.5F * (this.random.nextFloat() - 0.5F);
            this.level().addParticle(ParticleTypes.FLAME, dx, dy, dz, -this.getDeltaMovement().x(), -this.getDeltaMovement().y(), -this.getDeltaMovement().z());
         }
      }
   }

   public void setLavaPos(BlockPos p_31960_) {
      this.entityData.set(LAVA_POS, p_31960_);
   }

   public BlockPos getLavaPos() {
      return (BlockPos)this.entityData.get(LAVA_POS);
   }

   public boolean getGround() {
      return (Boolean)this.entityData.get(ON_GROUND);
   }

   public void setGround(boolean weapon) {
      this.entityData.set(ON_GROUND, weapon);
   }

   public int getLavaTime() {
      return (Integer)this.entityData.get(LAVA_TIME);
   }

   public void setLavaTime(int time) {
      this.entityData.set(LAVA_TIME, time);
   }

   public int getMaxLavaTime() {
      return (Integer)this.entityData.get(MAX_LAVA_TIME);
   }

   public void setMaxLavaTime(int time) {
      this.entityData.set(MAX_LAVA_TIME, time);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setGround(compound.getBoolean("bomb_ground"));
      this.setLavaTime(compound.getInt("lava_time"));
      this.setMaxLavaTime(compound.getInt("max_lava_time"));
      int i = compound.getInt("LavaPosX");
      int j = compound.getInt("LavaPosY");
      int k = compound.getInt("LavaPosZ");
      this.setLavaPos(new BlockPos(i, j, k));
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("LavaPosX", this.getLavaPos().getX());
      compound.putInt("LavaPosY", this.getLavaPos().getY());
      compound.putInt("LavaPosZ", this.getLavaPos().getZ());
      compound.putInt("lava_time", this.getLavaTime());
      compound.putInt("max_lava_time", this.getMaxLavaTime());
      compound.putBoolean("bomb_ground", this.getGround());
   }

   public float getLightLevelDependentMagicValue() {
      return 1.0F;
   }

   protected double getDefaultGravity() {
      return 0.025F;
   }
}
