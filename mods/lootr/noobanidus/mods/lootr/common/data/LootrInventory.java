package noobanidus.mods.lootr.common.data;

import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import noobanidus.mods.lootr.common.api.MenuBuilder;
import noobanidus.mods.lootr.common.api.data.ILootrInfo;
import noobanidus.mods.lootr.common.api.data.ILootrSavedData;
import noobanidus.mods.lootr.common.api.data.inventory.ILootrInventory;
import org.jetbrains.annotations.Nullable;

public class LootrInventory implements ILootrInventory {
   private final NonNullList<ItemStack> contents;
   private ILootrSavedData info;
   private MenuBuilder menuBuilder = null;

   public LootrInventory(ILootrSavedData info, NonNullList<ItemStack> contents) {
      this.info = info;
      if (!contents.isEmpty()) {
         this.contents = contents;
      } else {
         this.contents = info.buildInitialInventory();
      }
   }

   @Override
   public void setMenuBuilder(MenuBuilder builder) {
      this.menuBuilder = builder;
   }

   @Override
   public CompoundTag saveToTag(Provider provider) {
      CompoundTag result = new CompoundTag();
      ContainerHelper.saveAllItems(result, this.contents, provider);
      return result;
   }

   public int getContainerSize() {
      return this.info.getInfoContainerSize();
   }

   public boolean isEmpty() {
      for (ItemStack itemstack : this.contents) {
         if (!itemstack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack getItem(int index) {
      return (ItemStack)this.contents.get(index);
   }

   public ItemStack removeItem(int index, int count) {
      ItemStack itemstack = ContainerHelper.removeItem(this.contents, index, count);
      if (!itemstack.isEmpty()) {
         this.setChanged();
      }

      return itemstack;
   }

   public ItemStack removeItemNoUpdate(int index) {
      ItemStack result = ContainerHelper.takeItem(this.contents, index);
      if (!result.isEmpty()) {
         this.setChanged();
      }

      return result;
   }

   public void setItem(int index, ItemStack stack) {
      this.contents.set(index, stack);
      if (stack.getCount() > this.getMaxStackSize()) {
         stack.setCount(this.getMaxStackSize());
      }

      this.setChanged();
   }

   public void setChanged() {
      this.info.markChanged();
   }

   public boolean stillValid(Player player) {
      if (!player.level().dimension().equals(this.info.getInfoDimension())) {
         return false;
      }

      Container container = this.info.getInfoContainer();

      return switch (container) {
         case BlockEntity blockEntity -> Container.stillValidBlockEntity(blockEntity, player);
         case ContainerEntity containerEntity -> containerEntity.isChestVehicleStillValid(player);
         case null, default -> false;
      };
   }

   public void clearContent() {
      this.contents.clear();
      this.setChanged();
   }

   @Override
   public ILootrInfo getInfo() {
      return this.info;
   }

   @Override
   public void setInfo(ILootrSavedData info) {
      this.info = info;
   }

   @Override
   public Component getDisplayName() {
      Component component = this.info.getInfoDisplayName();
      return (Component)(component == null ? Component.empty() : component);
   }

   @Nullable
   public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
      if (this.menuBuilder != null) {
         return this.menuBuilder.build(id, inventory, this, this.getContainerSize() / 9);
      }

      return switch (this.getContainerSize()) {
         case 9 -> new ChestMenu(MenuType.GENERIC_9x1, id, inventory, this, 1);
         case 18 -> new ChestMenu(MenuType.GENERIC_9x2, id, inventory, this, 2);
         case 36 -> new ChestMenu(MenuType.GENERIC_9x4, id, inventory, this, 4);
         case 45 -> new ChestMenu(MenuType.GENERIC_9x5, id, inventory, this, 5);
         case 54 -> ChestMenu.sixRows(id, inventory, this);
         default -> ChestMenu.threeRows(id, inventory, this);
      };
   }

   public void startOpen(Player player) {
      Container container = this.info.getInfoContainer();
      if (container != null) {
         container.startOpen(player);
      }
   }

   public void stopOpen(Player player) {
      this.setChanged();
      Container container = this.info.getInfoContainer();
      if (container != null) {
         container.stopOpen(player);
      }
   }

   @Override
   public NonNullList<ItemStack> getInventoryContents() {
      return this.contents;
   }
}
