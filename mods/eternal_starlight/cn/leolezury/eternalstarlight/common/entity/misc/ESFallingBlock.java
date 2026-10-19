package cn.leolezury.eternalstarlight.common.entity.misc;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ESFallingBlock extends Entity {
   private static final String TAG_BLOCK_STATE = "block_state";
   private static final String TAG_DURATION = "duration";
   private static final String TAG_DAMAGE = "damage";
   protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(ESFallingBlock.class, EntityDataSerializers.BLOCK_POS);
   private static final EntityDataAccessor<Optional<BlockState>> BLOCK_STATE = SynchedEntityData.defineId(
      ESFallingBlock.class, EntityDataSerializers.OPTIONAL_BLOCK_STATE
   );
   private int duration = 20;
   private boolean damage;

   public ESFallingBlock(EntityType<? extends ESFallingBlock> type, Level level) {
      super(type, level);
   }

   public ESFallingBlock(Level level, double x, double y, double z, BlockState state, int duration) {
      this(level, x, y, z, state, duration, true);
   }

   public ESFallingBlock(Level level, double x, double y, double z, BlockState state, int duration, boolean damage) {
      this(ESEntities.FALLING_BLOCK.get(), level);
      this.setBlock(state);
      this.setPos(x, y, z);
      this.setDeltaMovement(Vec3.ZERO);
      this.duration = duration;
      this.xo = x;
      this.yo = y;
      this.zo = z;
      this.setStartPos(this.blockPosition());
      this.damage = damage;
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DATA_START_POS, BlockPos.ZERO).define(BLOCK_STATE, Optional.of(Blocks.DIRT.defaultBlockState()));
   }

   public BlockState getBlock() {
      Optional<BlockState> state = (Optional<BlockState>)this.getEntityData().get(BLOCK_STATE);
      return state.orElse(null);
   }

   public void setBlock(BlockState block) {
      this.getEntityData().set(BLOCK_STATE, Optional.of(block));
   }

   public void setStartPos(BlockPos pos) {
      this.getEntityData().set(DATA_START_POS, pos);
   }

   public BlockPos getStartPos() {
      return (BlockPos)this.getEntityData().get(DATA_START_POS);
   }

   public void tick() {
      if (!this.isNoGravity()) {
         this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
      }

      this.setPos(this.getX() + this.getDeltaMovement().x, this.getY() + this.getDeltaMovement().y, this.getZ() + this.getDeltaMovement().z);
      this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
      if (this.damage) {
         for (LivingEntity living : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox())) {
            living.hurt(this.damageSources().fallingBlock(this), 3.0F);
         }
      }

      if (this.onGround() && this.tickCount > this.duration) {
         this.discard();
      }

      if (this.tickCount > 300) {
         this.discard();
      }
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         this.discard();
         return true;
      } else {
         return false;
      }
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      BlockState blockState = this.getBlock();
      if (blockState != null) {
         tag.put("block_state", NbtUtils.writeBlockState(blockState));
      }

      tag.putInt("duration", this.duration);
      tag.putBoolean("damage", this.damage);
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      Tag blockStateCompound = tag.get("block_state");
      if (blockStateCompound != null) {
         BlockState blockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), (CompoundTag)blockStateCompound);
         this.setBlock(blockState);
      }

      this.duration = tag.getInt("duration");
      this.damage = tag.getBoolean("damage");
   }

   public boolean displayFireAnimation() {
      return false;
   }

   public BlockState getBlockState() {
      Optional<BlockState> optionalBlockState = (Optional<BlockState>)this.getEntityData().get(BLOCK_STATE);
      return optionalBlockState.orElse(null);
   }
}
