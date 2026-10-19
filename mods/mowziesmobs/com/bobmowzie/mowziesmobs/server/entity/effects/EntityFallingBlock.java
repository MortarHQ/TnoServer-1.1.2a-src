package com.bobmowzie.mowziesmobs.server.entity.effects;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EntityFallingBlock extends Entity {
   public static float GRAVITY = 0.1F;
   public double prevMotionX;
   public double prevMotionY;
   public double prevMotionZ;
   private static final EntityDataAccessor<BlockState> BLOCK_STATE = SynchedEntityData.defineId(EntityFallingBlock.class, EntityDataSerializers.BLOCK_STATE);
   private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(EntityFallingBlock.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> TICKS_EXISTED = SynchedEntityData.defineId(EntityFallingBlock.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<String> MODE = SynchedEntityData.defineId(EntityFallingBlock.class, EntityDataSerializers.STRING);
   private static final EntityDataAccessor<Float> ANIM_V_Y = SynchedEntityData.defineId(EntityFallingBlock.class, EntityDataSerializers.FLOAT);
   public float animY = 0.0F;
   public float prevAnimY = 0.0F;

   public EntityFallingBlock(EntityType<?> entityTypeIn, Level worldIn) {
      super(entityTypeIn, worldIn);
      this.setBlock(Blocks.DIRT.defaultBlockState());
      this.setDuration(70);
   }

   public EntityFallingBlock(EntityType<?> entityTypeIn, Level worldIn, int duration, BlockState blockState) {
      super(entityTypeIn, worldIn);
      this.setBlock(blockState);
      this.setDuration(duration);
   }

   public EntityFallingBlock(EntityType<?> entityTypeIn, Level worldIn, BlockState blockState, float vy) {
      super(entityTypeIn, worldIn);
      this.setBlock(blockState);
      this.setMode(EntityFallingBlock.EnumFallingBlockMode.POPUP_ANIM);
      this.setAnimVY(vy);
   }

   public void onAddedToLevel() {
      if (this.getDeltaMovement().x() > 0.0 || this.getDeltaMovement().z() > 0.0) {
         this.setYRot((float)((180.0 / Math.PI) * Math.atan2(this.getDeltaMovement().x(), this.getDeltaMovement().z())));
      }

      this.setXRot(this.getXRot() + this.random.nextFloat() * 360.0F);
      super.onAddedToLevel();
   }

   public void tick() {
      if (this.getMode() == EntityFallingBlock.EnumFallingBlockMode.POPUP_ANIM) {
         this.setDeltaMovement(0.0, 0.0, 0.0);
      }

      this.prevMotionX = this.getDeltaMovement().x;
      this.prevMotionY = this.getDeltaMovement().y;
      this.prevMotionZ = this.getDeltaMovement().z;
      super.tick();
      if (this.getMode() == EntityFallingBlock.EnumFallingBlockMode.MOBILE) {
         this.setDeltaMovement(this.getDeltaMovement().subtract(0.0, GRAVITY, 0.0));
         if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().scale(0.7));
         } else {
            this.setXRot(this.getXRot() + 15.0F);
         }

         this.move(MoverType.SELF, this.getDeltaMovement());
         if (this.tickCount > this.getDuration()) {
            this.discard();
         }
      } else {
         float animVY = this.getAnimVY();
         this.prevAnimY = this.animY;
         this.animY += animVY;
         this.setAnimVY(animVY - GRAVITY);
         if (this.animY < -0.5) {
            this.discard();
         }
      }
   }

   protected void defineSynchedData(@NotNull Builder builder) {
      builder.define(BLOCK_STATE, Blocks.DIRT.defaultBlockState());
      builder.define(DURATION, 70);
      builder.define(TICKS_EXISTED, 0);
      builder.define(MODE, EntityFallingBlock.EnumFallingBlockMode.MOBILE.toString());
      builder.define(ANIM_V_Y, 1.0F);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      Tag blockStateCompound = compound.get("block");
      if (blockStateCompound != null) {
         BlockState blockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), (CompoundTag)blockStateCompound);
         this.setBlock(blockState);
      }

      this.setDuration(compound.getInt("duration"));
      this.tickCount = compound.getInt("ticksExisted");
      this.getEntityData().set(MODE, compound.getString("mode"));
      this.setAnimVY(compound.getFloat("vy"));
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      BlockState blockState = this.getBlock();
      if (blockState != null) {
         compound.put("block", NbtUtils.writeBlockState(blockState));
      }

      compound.putInt("duration", this.getDuration());
      compound.putInt("ticksExisted", this.tickCount);
      compound.putString("mode", (String)this.getEntityData().get(MODE));
      compound.putFloat("vy", (Float)this.getEntityData().get(ANIM_V_Y));
   }

   public BlockState getBlock() {
      return (BlockState)this.getEntityData().get(BLOCK_STATE);
   }

   public void setBlock(BlockState block) {
      this.getEntityData().set(BLOCK_STATE, block);
   }

   public int getDuration() {
      return (Integer)this.getEntityData().get(DURATION);
   }

   public void setDuration(int duration) {
      this.getEntityData().set(DURATION, duration);
   }

   public int getTicksExisted() {
      return (Integer)this.getEntityData().get(TICKS_EXISTED);
   }

   public void setTicksExisted(int ticksExisted) {
      this.getEntityData().set(TICKS_EXISTED, ticksExisted);
   }

   public EntityFallingBlock.EnumFallingBlockMode getMode() {
      String mode = (String)this.getEntityData().get(MODE);
      return mode.isEmpty()
         ? EntityFallingBlock.EnumFallingBlockMode.MOBILE
         : EntityFallingBlock.EnumFallingBlockMode.valueOf((String)this.getEntityData().get(MODE));
   }

   private void setMode(EntityFallingBlock.EnumFallingBlockMode mode) {
      this.getEntityData().set(MODE, mode.toString());
   }

   public float getAnimVY() {
      return (Float)this.getEntityData().get(ANIM_V_Y);
   }

   private void setAnimVY(float vy) {
      this.getEntityData().set(ANIM_V_Y, vy);
   }

   public enum EnumFallingBlockMode {
      MOBILE,
      POPUP_ANIM;
   }
}
