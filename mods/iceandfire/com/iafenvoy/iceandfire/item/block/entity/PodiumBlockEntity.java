package com.iafenvoy.iceandfire.item.block.entity;

import com.iafenvoy.iceandfire.item.DragonEggItem;
import com.iafenvoy.iceandfire.network.payload.UpdatePodiumS2CPayload;
import com.iafenvoy.iceandfire.registry.IafBlockEntities;
import com.iafenvoy.iceandfire.screen.handler.PodiumScreenHandler;
import com.iafenvoy.uranus.ServerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PodiumBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
   private static final int[] slotsTop = new int[]{0};
   public int ticksExisted;
   public int prevTicksExisted;
   private NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);

   public PodiumBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafBlockEntities.PODIUM.get(), pos, state);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, PodiumBlockEntity entityPodium) {
      entityPodium.prevTicksExisted = entityPodium.ticksExisted++;
   }

   public int getContainerSize() {
      return this.stacks.size();
   }

   public ItemStack getItem(int index) {
      return (ItemStack)this.stacks.get(index);
   }

   public ItemStack removeItem(int index, int count) {
      if (!((ItemStack)this.stacks.get(index)).isEmpty()) {
         ItemStack itemstack;
         if (((ItemStack)this.stacks.get(index)).getCount() <= count) {
            itemstack = (ItemStack)this.stacks.get(index);
            this.stacks.set(index, ItemStack.EMPTY);
         } else {
            itemstack = ((ItemStack)this.stacks.get(index)).split(count);
            if (((ItemStack)this.stacks.get(index)).isEmpty()) {
               this.stacks.set(index, ItemStack.EMPTY);
            }
         }

         return itemstack;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public ItemStack getStackInSlotOnClosing(int index) {
      if (!((ItemStack)this.stacks.get(index)).isEmpty()) {
         ItemStack itemstack = (ItemStack)this.stacks.get(index);
         this.stacks.set(index, itemstack);
         return itemstack;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public void setItem(int index, ItemStack stack) {
      this.stacks.set(index, stack);
      if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
         stack.setCount(this.getMaxStackSize());
      }

      assert this.level != null;
      if (!this.level.isClientSide) {
         ServerHelper.sendToAll(new UpdatePodiumS2CPayload(this.getBlockPos(), (ItemStack)this.stacks.getFirst()));
      }
   }

   public void loadAdditional(CompoundTag nbt, Provider registryLookup) {
      super.loadAdditional(nbt, registryLookup);
      this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
      ContainerHelper.loadAllItems(nbt, this.stacks, registryLookup);
   }

   public void saveAdditional(CompoundTag nbt, Provider registryLookup) {
      ContainerHelper.saveAllItems(nbt, this.stacks, registryLookup);
   }

   public void startOpen(Player player) {
   }

   public void stopOpen(Player player) {
   }

   public boolean canPlaceItemThroughFace(int index, ItemStack stack, Direction direction) {
      return index != 0 || stack.getItem() instanceof DragonEggItem;
   }

   public int getMaxStackSize() {
      return 64;
   }

   public boolean stillValid(Player player) {
      return true;
   }

   public void clearContent() {
      this.stacks.clear();
   }

   public int[] getSlotsForFace(Direction side) {
      return slotsTop;
   }

   public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
      return false;
   }

   public boolean hasCustomName() {
      return false;
   }

   public boolean canPlaceItem(int index, ItemStack stack) {
      return false;
   }

   public ClientboundBlockEntityDataPacket toUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider registryLookup) {
      return this.saveWithFullMetadata(registryLookup);
   }

   public ItemStack removeItemNoUpdate(int index) {
      return ItemStack.EMPTY;
   }

   public Component getDisplayName() {
      return this.getDefaultName();
   }

   protected Component getDefaultName() {
      return Component.translatable("block.iceandfire.podium");
   }

   protected NonNullList<ItemStack> getItems() {
      return this.stacks;
   }

   protected void setItems(NonNullList<ItemStack> inventory) {
      this.stacks = inventory;
   }

   protected AbstractContainerMenu createMenu(int id, Inventory player) {
      return null;
   }

   public boolean isEmpty() {
      for (int i = 0; i < this.getContainerSize(); i++) {
         if (!this.getItem(i).isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
      return new PodiumScreenHandler(id, this, playerInventory);
   }
}
