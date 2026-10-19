package net.jadenxgamer.netherexp.core.block.entity;

import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DiscernmentGlassBlockEntity extends BlockEntity {
   private static final String FILTER_ITEM_TAG = "filter_item";
   private ItemStack filterItem = ItemStack.EMPTY;

   public DiscernmentGlassBlockEntity(BlockPos pPos, BlockState pBlockState) {
      super(JNEBlockEntityType.DISCERNMENT_GLASS.get(), pPos, pBlockState);
   }

   protected void saveAdditional(CompoundTag nbt, Provider registries) {
      super.saveAdditional(nbt, registries);
      if (!this.getFilterItem().isEmpty()) {
         nbt.put("filter_item", this.getFilterItem().save(registries));
      }
   }

   protected void loadAdditional(CompoundTag nbt, Provider registries) {
      super.loadAdditional(nbt, registries);
      this.setFilterItem(ItemStack.parse(registries, nbt.getCompound("filter_item")).orElse(ItemStack.EMPTY));
   }

   public CompoundTag getUpdateTag(Provider registries) {
      CompoundTag nbt = super.getUpdateTag(registries);
      if (!this.filterItem.isEmpty()) {
         nbt.put("filter_item", this.filterItem.save(registries));
      }

      return nbt;
   }

   public Packet<ClientGamePacketListener> getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   @NotNull
   public BlockEntityType<?> getType() {
      return JNEBlockEntityType.DISCERNMENT_GLASS.get();
   }

   public ItemStack getFilterItem() {
      return this.filterItem;
   }

   public void setFilterItem(ItemStack stack) {
      if (!stack.isEmpty()) {
         stack = stack.copyWithCount(1);
      }

      this.filterItem = stack;
      this.setChanged();
   }

   public void removeFilterItem() {
      this.filterItem = ItemStack.EMPTY;
      this.setChanged();
   }
}
