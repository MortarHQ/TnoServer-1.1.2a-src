package net.p3pp3rf1y.sophisticatedcore.compat.create;

import com.simibubi.create.api.contraption.storage.SyncedMountedStorage;
import com.simibubi.create.api.contraption.storage.item.MountedItemStorage;
import com.simibubi.create.api.contraption.storage.item.MountedItemStorageType;
import com.simibubi.create.content.contraptions.Contraption;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;

public abstract class MountedStorageBase extends MountedItemStorage implements SyncedMountedStorage {
   private ItemStack storageStack;

   public MountedStorageBase(MountedItemStorageType<?> type, ItemStack storageStack) {
      super(type);
      this.storageStack = storageStack;
   }

   public ItemStack getStorageStack() {
      return this.storageStack;
   }

   public void setStorageStack(ItemStack stack) {
      this.storageStack = stack;
   }

   public void afterSync(Contraption contraption, BlockPos localPos) {
      this.updateWithSyncedStorageStack(this.storageStack, true);
   }

   public void markClean() {
   }

   public boolean isDirty() {
      return false;
   }

   public abstract void updateWithSyncedStorageStack(ItemStack var1, boolean var2);

   public abstract IStorageWrapper getStorageWrapper();

   protected IItemHandlerModifiable getExternalItemHandler() {
      return this.getStorageWrapper().getInventoryForInputOutput();
   }

   public void setStackInSlot(int i, ItemStack itemStack) {
      this.getExternalItemHandler().setStackInSlot(i, itemStack);
   }

   public int getSlots() {
      return this.getExternalItemHandler().getSlots();
   }

   public ItemStack getStackInSlot(int i) {
      return this.getExternalItemHandler().getStackInSlot(i);
   }

   public ItemStack insertItem(int i, ItemStack itemStack, boolean b) {
      return this.getExternalItemHandler().insertItem(i, itemStack, b);
   }

   public ItemStack extractItem(int i, int i1, boolean b) {
      return this.getExternalItemHandler().extractItem(i, i1, b);
   }

   public int getSlotLimit(int i) {
      return this.getExternalItemHandler().getSlotLimit(i);
   }

   public boolean isItemValid(int i, ItemStack itemStack) {
      return this.getExternalItemHandler().isItemValid(i, itemStack);
   }

   public void onClose(Player player, Vec3 pos) {
   }

   protected void onOpen(ServerLevel level, Vec3 pos) {
   }

   public void onContraptionDestroyed() {
   }
}
