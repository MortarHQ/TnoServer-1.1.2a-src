package it.hurts.sskirillss.relics.entities;

import it.hurts.sskirillss.relics.init.EntityRegistry;
import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlockSimulationEntity extends Entity {
   private static final EntityDataAccessor<BlockState> BLOCK_STATE = SynchedEntityData.defineId(BlockSimulationEntity.class, EntityDataSerializers.BLOCK_STATE);

   public BlockSimulationEntity(EntityType<?> pEntityType, Level level) {
      super(pEntityType, level);
   }

   public BlockSimulationEntity(Level level, BlockState state) {
      super((EntityType)EntityRegistry.BLOCK_SIMULATION.get(), level);
      this.setBlockState(state);
      this.noPhysics = true;
   }

   public void tick() {
      super.tick();
      this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.075, 0.0));
      this.move(MoverType.SELF, this.getDeltaMovement());
      if (this.tickCount % 100 == 0 || this.tickCount > 10 && this.getCommandSenderWorld().getBlockState(this.blockPosition().above()).blocksMotion()) {
         this.remove(RemovalReason.KILLED);
      }
   }

   public void setBlockState(@Nullable BlockState state) {
      this.entityData.set(BLOCK_STATE, state);
   }

   @Nullable
   public BlockState getBlockState() {
      return (BlockState)this.entityData.get(BLOCK_STATE);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(BLOCK_STATE, Blocks.AIR.defaultBlockState());
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.setBlockState(NbtUtils.readBlockState(this.getCommandSenderWorld().holderLookup(Registries.BLOCK), compound.getCompound("BlockState")));
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      BlockState state = this.getBlockState();
      if (state != null) {
         compound.put("BlockState", NbtUtils.writeBlockState(state));
      }
   }

   public boolean isPushedByFluid() {
      return false;
   }
}
