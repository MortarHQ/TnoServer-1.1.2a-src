package cn.star.toms_storage_star_optimized.mixin;

import com.tom.storagemod.inventory.IInventoryAccess;
import com.tom.storagemod.inventory.PlatformMultiInventoryAccess;
import java.util.Arrays;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.EmptyItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlatformMultiInventoryAccess.class)
public abstract class PlatformMultiInventoryAccessMixin {
   @Shadow
   private int[] offsets;
   @Shadow
   private int invSize;
   @Shadow
   private int offsetsSize;
   @Shadow
   private boolean calling;
   @Unique
   private int tsso$lastIdx = -1;
   @Unique
   private int tsso$lastStart = 0;

   @Shadow
   private IItemHandler getHandler(int index, int slot) {
      return EmptyItemHandler.INSTANCE;
   }

   @Unique
   private int tsso$findInventory(int slot) {
      if (this.offsetsSize != 0 && slot >= 0 && slot < this.invSize) {
         int idx = this.tsso$lastIdx;
         if (idx >= 0 && idx < this.offsetsSize) {
            int start = this.offsets[idx];
            IItemHandler current = this.getHandler(idx, 0);
            int end = start + current.getSlots();
            if (slot >= start && slot < end) {
               this.tsso$lastStart = start;
               return idx;
            }

            int nextIdx = idx + 1;
            if (nextIdx < this.offsetsSize) {
               int nextStart = this.offsets[nextIdx];
               IItemHandler next = this.getHandler(nextIdx, 0);
               int nextEnd = nextStart + next.getSlots();
               if (slot >= nextStart && slot < nextEnd) {
                  this.tsso$lastIdx = nextIdx;
                  this.tsso$lastStart = nextStart;
                  return nextIdx;
               }
            }
         }

         idx = Arrays.binarySearch(this.offsets, 0, this.offsetsSize, slot);
         if (idx < 0) {
            idx = -idx - 2;
         }

         if (idx >= 0 && idx < this.offsetsSize) {
            this.tsso$lastIdx = idx;
            this.tsso$lastStart = this.offsets[idx];
            return idx;
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   @Unique
   private int tsso$localSlot(int slot, int idx) {
      return slot - this.offsets[idx];
   }

   @Unique
   private List<IInventoryAccess> tsso$connected() {
      return ((MultiInventoryAccessAccessor)this).tsso$getConnected();
   }

   @Overwrite
   public boolean isItemValid(int slot, ItemStack stack) {
      if (!this.calling && slot >= 0 && slot < this.invSize) {
         this.calling = true;

         try {
            int idx = this.tsso$findInventory(slot);
            if (idx < 0) {
               return false;
            }

            int localSlot = this.tsso$localSlot(slot, idx);
            return this.getHandler(idx, localSlot).isItemValid(localSlot, stack);
         } finally {
            this.calling = false;
         }
      } else {
         return false;
      }
   }

   @Overwrite
   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      if (!this.calling && slot >= 0 && slot < this.invSize) {
         this.calling = true;

         try {
            int idx = this.tsso$findInventory(slot);
            if (idx < 0) {
               return stack;
            }

            int localSlot = this.tsso$localSlot(slot, idx);
            return this.getHandler(idx, localSlot).insertItem(localSlot, stack, simulate);
         } finally {
            this.calling = false;
         }
      } else {
         return stack;
      }
   }

   @Overwrite
   public ItemStack getStackInSlot(int slot) {
      if (!this.calling && slot >= 0 && slot < this.invSize) {
         this.calling = true;

         try {
            int idx = this.tsso$findInventory(slot);
            if (idx < 0) {
               return ItemStack.EMPTY;
            }

            int localSlot = this.tsso$localSlot(slot, idx);
            return this.getHandler(idx, localSlot).getStackInSlot(localSlot);
         } finally {
            this.calling = false;
         }
      } else {
         return ItemStack.EMPTY;
      }
   }

   @Overwrite
   public int getSlotLimit(int slot) {
      if (!this.calling && slot >= 0 && slot < this.invSize) {
         this.calling = true;

         try {
            int idx = this.tsso$findInventory(slot);
            if (idx < 0) {
               return 0;
            }

            int localSlot = this.tsso$localSlot(slot, idx);
            return this.getHandler(idx, localSlot).getSlotLimit(localSlot);
         } finally {
            this.calling = false;
         }
      } else {
         return 0;
      }
   }

   @Overwrite
   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      if (!this.calling && slot >= 0 && slot < this.invSize) {
         this.calling = true;

         try {
            int idx = this.tsso$findInventory(slot);
            if (idx < 0) {
               return ItemStack.EMPTY;
            }

            int localSlot = this.tsso$localSlot(slot, idx);
            return this.getHandler(idx, localSlot).extractItem(localSlot, amount, simulate);
         } finally {
            this.calling = false;
         }
      } else {
         return ItemStack.EMPTY;
      }
   }

   @Overwrite
   public void clear() {
      this.tsso$connected().clear();
      this.invSize = 0;
      this.offsetsSize = 0;
      this.tsso$lastIdx = -1;
      this.tsso$lastStart = 0;
   }

   @Overwrite
   public void refresh() {
      List<IInventoryAccess> connected = this.tsso$connected();
      if (this.offsets.length != connected.size()) {
         this.offsets = new int[connected.size()];
      }

      this.invSize = 0;
      int empty = 0;

      for (int i = 0; i < this.offsets.length; i++) {
         IItemHandler handler = this.getHandler(i, 0);
         int slots = handler.getSlots();
         if (slots == 0) {
            empty++;
         } else {
            this.offsets[i - empty] = this.invSize;
            this.invSize += slots;
         }
      }

      this.offsetsSize = this.offsets.length - empty;
      this.tsso$lastIdx = -1;
      this.tsso$lastStart = 0;
   }
}
