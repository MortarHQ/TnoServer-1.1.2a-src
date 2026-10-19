package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.client.particle.Options.CircleLightningParticleOptions;
import com.github.L_Ender.cataclysm.entity.effect.Lightning_Area_Effect_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Lightning_Storm_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Spark_Entity extends ThrowableProjectile {
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Spark_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> AREA_DAMAGE = SynchedEntityData.defineId(Spark_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> AREA_RADIUS = SynchedEntityData.defineId(Spark_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> HP_DAMAGE = SynchedEntityData.defineId(Spark_Entity.class, EntityDataSerializers.FLOAT);

   public Spark_Entity(EntityType<? extends Spark_Entity> type, Level world) {
      super(type, world);
   }

   public Spark_Entity(Level worldIn, LivingEntity throwerIn) {
      super((EntityType)ModEntities.SPARK.get(), throwerIn, worldIn);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putFloat("Damage", this.getDamage());
      tag.putFloat("AreaDamage", this.getAreaDamage());
      tag.putFloat("HpDamage", this.getHpDamage());
      tag.putFloat("Area_Radius", this.getAreaRadius());
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setDamage(tag.getFloat("Damage"));
      this.setHpDamage(tag.getFloat("HpDamage"));
      this.setAreaDamage(tag.getFloat("HpDamage"));
      this.setAreaRadius(tag.getFloat("Area_Radius"));
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(DAMAGE, 0.0F);
      p_326229_.define(AREA_RADIUS, 0.0F);
      p_326229_.define(HP_DAMAGE, 0.0F);
      p_326229_.define(AREA_DAMAGE, 0.0F);
   }

   public float getDamage() {
      return (Float)this.entityData.get(DAMAGE);
   }

   public void setDamage(float damage) {
      this.entityData.set(DAMAGE, damage);
   }

   public float getAreaDamage() {
      return (Float)this.entityData.get(AREA_DAMAGE);
   }

   public void setAreaDamage(float damage) {
      this.entityData.set(AREA_DAMAGE, damage);
   }

   public float getHpDamage() {
      return (Float)this.entityData.get(HP_DAMAGE);
   }

   public void setHpDamage(float damage) {
      this.entityData.set(HP_DAMAGE, damage);
   }

   public float getAreaRadius() {
      return (Float)this.entityData.get(AREA_RADIUS);
   }

   public void setAreaRadius(float radius) {
      this.entityData.set(AREA_RADIUS, radius);
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         Vec3 center = this.position().add(this.getDeltaMovement());
         this.level().addParticle(new CircleLightningParticleOptions(0.1F, 143, 241, 215), center.x, center.y, center.z, this.xo, this.yo, this.zo);
      }
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      if (!this.level().isClientSide) {
         int standingOnY = Mth.floor(this.getY()) - 3;
         this.spawnArea(this.getX(), this.getZ(), standingOnY, this.getY() + 1.0);
         LivingEntity entity1 = (LivingEntity)this.getOwner();
         this.level()
            .addFreshEntity(
               new Lightning_Storm_Entity(
                  this.level(), this.getX(), this.getY(), this.getZ(), this.getYRot(), -9, this.getDamage(), this.getHpDamage(), entity1, 2.0F
               )
            );
         this.discard();
      }
   }

   protected void spawnArea(double x, double z, double minY, double maxY) {
      BlockPos blockpos = BlockPos.containing(x, maxY, z);
      boolean foundGround = false;
      double spawnY = minY;

      do {
         BlockPos below = blockpos.below();
         BlockState groundState = this.level().getBlockState(below);
         if (groundState.isFaceSturdy(this.level(), below, Direction.UP)) {
            if (!this.level().isEmptyBlock(blockpos)) {
               VoxelShape shape = this.level().getBlockState(blockpos).getCollisionShape(this.level(), blockpos);
               if (!shape.isEmpty()) {
                  spawnY = blockpos.getY() + shape.max(Axis.Y);
               } else {
                  spawnY = blockpos.getY();
               }
            } else {
               spawnY = blockpos.getY();
            }

            foundGround = true;
            break;
         }

         blockpos = below;
      } while (blockpos.getY() >= Mth.floor(minY) - 1);

      if (!foundGround) {
         spawnY = minY;
      }

      Lightning_Area_Effect_Entity areaeffectcloud = new Lightning_Area_Effect_Entity(this.level(), x, spawnY, z);
      areaeffectcloud.setRadius(this.getAreaRadius());
      LivingEntity entity1 = (LivingEntity)this.getOwner();
      areaeffectcloud.setOwner(entity1);
      areaeffectcloud.setRadiusOnUse(-1.0F);
      areaeffectcloud.setDamage(this.getAreaDamage());
      areaeffectcloud.setWaitTime(5);
      areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
      areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() * 2.0F / areaeffectcloud.getDuration());
      this.level().addFreshEntity(areaeffectcloud);
   }

   protected double getDefaultGravity() {
      return 0.07;
   }

   protected void onHit(HitResult result) {
      Type hitresult$type = result.getType();
      if (hitresult$type == Type.ENTITY) {
         EntityHitResult entityhitresult = (EntityHitResult)result;
         Entity entity = entityhitresult.getEntity();
         if (entity.getType().is(EntityTypeTags.REDIRECTABLE_PROJECTILE) && entity instanceof Projectile projectile) {
            projectile.deflect(ProjectileDeflection.AIM_DEFLECT, this.getOwner(), this.getOwner(), true);
         }

         this.onHitEntity(entityhitresult);
         this.level().gameEvent(GameEvent.PROJECTILE_LAND, result.getLocation(), Context.of(this, (BlockState)null));
      } else if (hitresult$type == Type.BLOCK) {
         BlockHitResult blockhitresult = (BlockHitResult)result;
         this.onHitBlock(blockhitresult);
         BlockPos blockpos = blockhitresult.getBlockPos();
         this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, Context.of(this, this.level().getBlockState(blockpos)));
      }
   }
}
