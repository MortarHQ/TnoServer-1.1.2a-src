package com.finderfeed.raids_enhanced.content.entities.falling_block;

import com.finderfeed.fdlib.nbt.AutoSerializable;
import com.finderfeed.fdlib.nbt.SerializableField;
import com.finderfeed.fdlib.util.FDProjectile;
import com.finderfeed.fdlib.util.math.FDMathUtil;
import com.finderfeed.raids_enhanced.init.REEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class REFallingBlock extends FDProjectile implements AutoSerializable {
   public static final EntityDataAccessor<BlockState> STATE = SynchedEntityData.defineId(REFallingBlock.class, EntityDataSerializers.BLOCK_STATE);
   public static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(REFallingBlock.class, EntityDataSerializers.FLOAT);
   @SerializableField
   private int removingTicker = -1;

   public REFallingBlock(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
      super(type, level);
   }

   public static REFallingBlock summon(Level level, BlockState state, Vec3 pos, Vec3 speed, float gravity) {
      REFallingBlock block = new REFallingBlock(REEntities.FALLING_BLOCK.get(), level);
      block.setPos(pos);
      block.setDeltaMovement(speed);
      block.setBlockState(state);
      block.getEntityData().set(GRAVITY, gravity);
      level.addFreshEntity(block);
      return block;
   }

   public static REFallingBlock summon(Level level, BlockState state, Vec3 pos) {
      return summon(level, state, pos, Vec3.ZERO);
   }

   public static REFallingBlock summon(Level level, BlockState state, Vec3 pos, Vec3 speed) {
      return summon(level, state, pos, speed, 0.025F);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide && this.removingTicker != -1) {
         this.removingTicker = Mth.clamp(this.removingTicker - 1, 0, Integer.MAX_VALUE);
         if (this.removingTicker == 0) {
            this.remove(RemovalReason.DISCARDED);
         }
      }

      this.applyGravity();
   }

   public Vec3 getLightProbePosition(float p_20309_) {
      return this.getPosition(p_20309_).add(0.0, 1.5, 0.0);
   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      if (!this.level().isClientSide && this.removingTicker == -1) {
         double speed = Math.abs(this.getDeltaMovement().y);
         if (speed > 0.5) {
            this.remove(RemovalReason.DISCARDED);
         } else {
            int lifetime = (int)Math.floor(FDMathUtil.lerp(1.0, 30.0, 1.0 - speed / 0.5));
            this.removingTicker = lifetime;
         }
      }
   }

   public BlockState getBlockState() {
      return (BlockState)this.entityData.get(STATE);
   }

   public void setBlockState(BlockState state) {
      this.entityData.set(STATE, state);
   }

   protected void defineSynchedData(Builder data) {
      data.define(GRAVITY, 0.025F).define(STATE, Blocks.STONE.defaultBlockState());
   }

   public boolean save(CompoundTag tag) {
      tag.put("state", NbtUtils.writeBlockState(this.getBlockState()));
      tag.putFloat("gravity", (Float)this.getEntityData().get(GRAVITY));
      this.autoSave(tag);
      return super.save(tag);
   }

   public void load(CompoundTag tag) {
      this.setBlockState(NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), tag.getCompound("state")));
      this.getEntityData().set(GRAVITY, tag.getFloat("gravity"));
      this.autoLoad(tag);
      super.load(tag);
   }

   protected double getDefaultGravity() {
      return ((Float)this.getEntityData().get(GRAVITY)).floatValue();
   }

   public boolean shouldRenderAtSqrDistance(double dist) {
      return dist < 14400.0;
   }
}
