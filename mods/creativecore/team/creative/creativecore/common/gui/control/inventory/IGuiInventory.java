package team.creative.creativecore.common.gui.control.inventory;

import java.util.BitSet;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.sync.GuiSyncGlobal;
import team.creative.creativecore.common.gui.sync.GuiSyncHolder;

public interface IGuiInventory {
   GuiSyncGlobal<GuiControl, CompoundTag> SYNC = GuiSyncHolder.GLOBAL.register("inv_one", (control, nbt) -> {
      IGuiInventory inventory = (IGuiInventory)control;

      for (String name : nbt.getAllKeys()) {
         int id = Integer.parseInt(name);
         inventory.getSlot(id).slot.set(ItemStack.parseOptional(control.provider(), nbt.getCompound(name)));
         inventory.setChanged(id);
      }
   });
   GuiSyncGlobal<GuiControl, ListTag> SYNC_ALL = GuiSyncHolder.GLOBAL.register("inv_all", (control, list) -> {
      IGuiInventory inventory = (IGuiInventory)control;

      for (int i = 0; i < inventory.inventorySize(); i++) {
         inventory.getSlot(i).slot.set(ItemStack.parseOptional(control.provider(), list.getCompound(i)));
      }

      inventory.setChanged();
   });

   GuiSlot getSlot(int var1);

   int inventorySize();

   String name();

   void setChanged();

   void setChanged(int var1);

   default ItemStack moveInside(ItemStack toAdd, int slot) {
      return toAdd;
   }

   default void insertClever(ItemStack toAdd) {
      this.insertClever(toAdd, 0, this.inventorySize());
   }

   default void insertClever(ItemStack toAdd, int start, int endExclusive) {
      this.insert(toAdd, false, start, endExclusive);
      if (!toAdd.isEmpty()) {
         this.insert(toAdd, true, start, endExclusive);
      }
   }

   default void insert(ItemStack toAdd, boolean useEmptySlot) {
      this.insert(toAdd, useEmptySlot, 0, this.inventorySize());
   }

   default void insert(ItemStack toAdd, boolean useEmptySlot, int start, int endExclusive) {
      for (int i = start; i < endExclusive; i++) {
         GuiSlot slot = this.getSlot(i);
         if (useEmptySlot || slot.slot.hasItem()) {
            toAdd = slot.insert(toAdd);
            if (toAdd.isEmpty()) {
               return;
            }
         }
      }
   }

   default void extract(ItemStack toDrain) {
      Player player = ((GuiControl)this).getPlayer();

      for (int i = 0; i < this.inventorySize(); i++) {
         GuiSlot slot = this.getSlot(i);
         if (slot.slot.mayPickup(player) && ItemStack.isSameItemSameComponents(toDrain, slot.getStack())) {
            int transfer = Math.min(toDrain.getCount(), slot.getStack().getCount());
            if (transfer > 0) {
               ItemStack drained = slot.slot.remove(transfer);
               slot.slot.onTake(player, drained);
               toDrain.shrink(transfer);
               if (toDrain.isEmpty()) {
                  return;
               }
            }
         }
      }
   }

   default void sync(BitSet set) {
      GuiControl control = (GuiControl)this;
      if (!control.isClient()) {
         Provider provider = control.provider();
         CompoundTag nbt = new CompoundTag();

         for (int i = set.nextSetBit(0); i >= 0; i = set.nextSetBit(i + 1)) {
            GuiSlot slot = this.getSlot(i);
            slot.onSendUpdate();
            nbt.put(i + "", slot.slot.getItem().saveOptional(provider));
         }

         SYNC.send(control, nbt);
      }
   }

   default void syncAll() {
      GuiControl control = (GuiControl)this;
      if (!control.isClient()) {
         Provider provider = control.provider();
         ListTag list = new ListTag();

         for (int i = 0; i < this.inventorySize(); i++) {
            list.add(this.getSlot(i).slot.getItem().saveOptional(provider));
         }

         SYNC_ALL.send(control, list);
      }
   }
}
