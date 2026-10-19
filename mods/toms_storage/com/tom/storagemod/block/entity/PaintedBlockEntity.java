package com.tom.storagemod.block.entity;

import com.tom.storagemod.Content;
import com.tom.storagemod.platform.PlatformBlockEntity;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;

public class PaintedBlockEntity extends PlatformBlockEntity {
   public static final ModelProperty<Supplier<BlockState>> FACADE_STATE = new ModelProperty();
   private BlockState blockState;

   public PaintedBlockEntity(BlockPos pos, BlockState state) {
      super(Content.paintedBE.get(), pos, state);
   }

   public PaintedBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public boolean setPaintedBlockState(BlockState blockState) {
      BlockState old = this.getPaintedBlockState();
      this.blockState = blockState;
      boolean changed = !this.getPaintedBlockState().equals(old);
      if (changed) {
         this.markDirtyClient();
      }

      return changed;
   }

   public ModelData getModelData() {
      return ModelData.builder().with(FACADE_STATE, this::getPaintedBlockState).build();
   }

   public void loadAdditional(CompoundTag compound, Provider provider) {
      super.loadAdditional(compound, provider);
      this.blockState = NbtUtils.readBlockState(provider.lookupOrThrow(Registries.BLOCK), compound.getCompound("block"));
      this.markDirtyClient();
   }

   public void saveAdditional(CompoundTag compound, Provider provider) {
      super.saveAdditional(compound, provider);
      if (this.blockState != null) {
         compound.put("block", NbtUtils.writeBlockState(this.blockState));
      }
   }

   private void markDirtyClient() {
      this.setChanged();
      if (this.getLevel() != null) {
         BlockState state = this.getLevel().getBlockState(this.getBlockPos());
         this.requestModelDataUpdate();
         this.getLevel().sendBlockUpdated(this.getBlockPos(), state, state, 3);
      }
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider provider) {
      return this.saveWithFullMetadata(provider);
   }

   public BlockState getPaintedBlockState() {
      return this.blockState == null ? Blocks.AIR.defaultBlockState() : this.blockState;
   }

   protected void applyImplicitComponents(DataComponentInput dataComponentInput) {
      super.applyImplicitComponents(dataComponentInput);
      this.blockState = (BlockState)dataComponentInput.get(Content.paintComponent.get());
   }

   protected void collectImplicitComponents(Builder builder) {
      super.collectImplicitComponents(builder);
      if (this.blockState != null) {
         builder.set(Content.paintComponent.get(), this.blockState);
      }
   }

   public void removeComponentsFromTag(CompoundTag compoundTag) {
      super.removeComponentsFromTag(compoundTag);
      compoundTag.remove("block");
   }
}
