package net.p3pp3rf1y.sophisticatedcore.inventory;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.util.SlotRange;

public class InventoryPartitioner {
   public static final String BASE_INDEXES_TAG = "baseIndexes";
   private IInventoryPartHandler[] inventoryPartHandlers;
   private int[] baseIndexes;
   private final InventoryHandler parent;

   public InventoryPartitioner(CompoundTag tag, InventoryHandler parent, Supplier<MemorySettingsCategory> getMemorySettings) {
      this.parent = parent;
      this.deserializeNBT(tag, getMemorySettings);
   }

   private int getIndexForSlot(int slot) {
      if (slot < 0) {
         return -1;
      }

      if (this.baseIndexes.length == 1) {
         return 0;
      }

      int i;
      for (i = 0; i < this.baseIndexes.length; i++) {
         if (slot - this.baseIndexes[i] < 0) {
            return i - 1;
         }
      }

      return i - 1;
   }

   public IInventoryPartHandler getPartBySlot(int slot) {
      if (slot >= 0 && slot < this.parent.getSlots()) {
         int index = this.getIndexForSlot(slot);
         return index >= 0 && index < this.inventoryPartHandlers.length ? this.inventoryPartHandlers[index] : IInventoryPartHandler.EMPTY;
      } else {
         return IInventoryPartHandler.EMPTY;
      }
   }

   @Nullable
   public Pair<ResourceLocation, ResourceLocation> getNoItemIcon(int slot) {
      return this.getPartBySlot(slot).getNoItemIcon(slot);
   }

   public void onSlotLimitChange() {
      for (IInventoryPartHandler inventoryPartHandler : this.inventoryPartHandlers) {
         inventoryPartHandler.onSlotLimitChange();
      }
   }

   public Set<Integer> getNoSortSlots() {
      Set<Integer> noSortSlots = new HashSet<>();

      for (IInventoryPartHandler inventoryPartHandler : this.inventoryPartHandlers) {
         noSortSlots.addAll(inventoryPartHandler.getNoSortSlots());
      }

      return noSortSlots;
   }

   public boolean isFilterItem(Item item) {
      for (IInventoryPartHandler inventoryPartHandler : this.inventoryPartHandlers) {
         if (inventoryPartHandler.isFilterItem(item)) {
            return true;
         }
      }

      return false;
   }

   public Map<Item, Set<Integer>> getFilterItems() {
      Map<Item, Set<Integer>> filterItems = new HashMap<>();

      for (IInventoryPartHandler inventoryPartHandler : this.inventoryPartHandlers) {
         for (Entry<Item, Set<Integer>> entry : inventoryPartHandler.getFilterItems().entrySet()) {
            filterItems.computeIfAbsent(entry.getKey(), k -> new HashSet<>()).addAll(entry.getValue());
         }
      }

      return filterItems;
   }

   public void onInit() {
      for (IInventoryPartHandler inventoryPartHandler : this.inventoryPartHandlers) {
         inventoryPartHandler.onInit();
      }
   }

   public Optional<SlotRange> getFirstSpace(int maxNumberOfSlots) {
      for (int partIndex = 0; partIndex < this.inventoryPartHandlers.length; partIndex++) {
         if (this.inventoryPartHandlers[partIndex].canBeReplaced()) {
            int firstSlot = this.baseIndexes[partIndex];
            int numberOfSlots = this.baseIndexes.length > partIndex + 1 ? this.baseIndexes[partIndex + 1] - firstSlot : this.parent.getSlots() - firstSlot;
            numberOfSlots = Math.min(numberOfSlots, maxNumberOfSlots);
            return numberOfSlots > 0 ? Optional.of(new SlotRange(this.baseIndexes[partIndex], numberOfSlots)) : Optional.empty();
         }
      }

      return Optional.empty();
   }

   public void addInventoryPart(int inventorySlot, int numberOfSlots, IInventoryPartHandler inventoryPartHandler) {
      int index = this.getIndexForSlot(inventorySlot);
      if (index >= 0 && index < this.inventoryPartHandlers.length && this.baseIndexes[index] == inventorySlot) {
         List<IInventoryPartHandler> newParts = new ArrayList<>();
         List<Integer> newBaseIndexes = new ArrayList<>();

         for (int i = 0; i < index; i++) {
            newParts.add(this.inventoryPartHandlers[i]);
            newBaseIndexes.add(this.baseIndexes[i]);
         }

         newParts.add(inventoryPartHandler);
         newBaseIndexes.add(inventorySlot);
         int newNextSlot = inventorySlot + numberOfSlots;
         if (this.inventoryPartHandlers[index].getSlots() > newNextSlot) {
            newParts.add(new IInventoryPartHandler.Default(this.parent, this.parent.getSlots() - newNextSlot));
            newBaseIndexes.add(newNextSlot);
         }

         for (int i = index + 1; i < this.inventoryPartHandlers.length; i++) {
            newParts.add(this.inventoryPartHandlers[i]);
            newBaseIndexes.add(this.baseIndexes[i]);
         }

         this.updatePartsAndIndexesFromLists(newParts, newBaseIndexes);
         inventoryPartHandler.onInit();
         this.parent.onFilterItemsChanged();
      }
   }

   public void removeInventoryPart(int inventorySlot) {
      int index = this.getIndexForSlot(inventorySlot);
      if (index >= 0 && index < this.inventoryPartHandlers.length && this.baseIndexes[index] == inventorySlot) {
         if (this.inventoryPartHandlers.length == 1) {
            this.updatePartsAndIndexesFromLists(List.of(new IInventoryPartHandler.Default(this.parent, this.parent.getSlots())), List.of(0));
            this.parent.onFilterItemsChanged();
         } else {
            int slotsAtPartIndex = (this.baseIndexes.length > index + 1 ? this.baseIndexes[index + 1] : this.parent.getSlots()) - this.baseIndexes[index];
            List<IInventoryPartHandler> newParts = new ArrayList<>();
            List<Integer> newBaseIndexes = new ArrayList<>();
            boolean replacedNext = false;

            for (int i = 0; i < index; i++) {
               if (i == index - 1
                  && this.inventoryPartHandlers[i] instanceof IInventoryPartHandler.Default
                  && this.baseIndexes.length > index + 1
                  && this.inventoryPartHandlers[index + 1] instanceof IInventoryPartHandler.Default) {
                  newParts.add(
                     new IInventoryPartHandler.Default(
                        this.parent, this.inventoryPartHandlers[i].getSlots() + this.inventoryPartHandlers[index + 1].getSlots() + slotsAtPartIndex
                     )
                  );
                  newBaseIndexes.add(this.baseIndexes[i]);
                  replacedNext = true;
               } else {
                  newParts.add(this.inventoryPartHandlers[i]);
                  newBaseIndexes.add(this.baseIndexes[i]);
               }
            }

            if (!replacedNext && this.baseIndexes.length > index + 1) {
               if (this.inventoryPartHandlers[index + 1] instanceof IInventoryPartHandler.Default) {
                  newParts.add(new IInventoryPartHandler.Default(this.parent, this.inventoryPartHandlers[index + 1].getSlots() + slotsAtPartIndex));
                  newBaseIndexes.add(inventorySlot);
               } else {
                  newParts.add(new IInventoryPartHandler.Default(this.parent, slotsAtPartIndex));
                  newBaseIndexes.add(inventorySlot);
                  newParts.add(this.inventoryPartHandlers[index + 1]);
                  newBaseIndexes.add(this.baseIndexes[index + 1]);
               }
            }

            for (int i = index + 2; i < this.inventoryPartHandlers.length; i++) {
               newParts.add(this.inventoryPartHandlers[i]);
               newBaseIndexes.add(this.baseIndexes[i]);
            }

            this.updatePartsAndIndexesFromLists(newParts, newBaseIndexes);
            this.parent.onFilterItemsChanged();
         }
      }
   }

   private void updatePartsAndIndexesFromLists(List<IInventoryPartHandler> newParts, List<Integer> newBaseIndexes) {
      this.inventoryPartHandlers = newParts.toArray(new IInventoryPartHandler[0]);
      this.baseIndexes = new int[newBaseIndexes.size()];

      for (int i = 0; i < newBaseIndexes.size(); i++) {
         this.baseIndexes[i] = newBaseIndexes.get(i);
      }

      this.parent.saveInventory();
   }

   public CompoundTag serializeNBT() {
      CompoundTag ret = new CompoundTag();
      ret.putIntArray("baseIndexes", this.baseIndexes);
      ListTag partNames = new ListTag();

      for (IInventoryPartHandler inventoryPartHandler : this.inventoryPartHandlers) {
         partNames.add(StringTag.valueOf(inventoryPartHandler.getName()));
      }

      ret.put("inventoryPartNames", partNames);
      return ret;
   }

   private void deserializeNBT(CompoundTag tag, Supplier<MemorySettingsCategory> getMemorySettings) {
      if (!tag.contains("baseIndexes")) {
         this.inventoryPartHandlers = new IInventoryPartHandler[]{new IInventoryPartHandler.Default(this.parent, this.parent.getSlots())};
         this.baseIndexes = new int[]{0};
      } else {
         this.baseIndexes = tag.getIntArray("baseIndexes");
         this.inventoryPartHandlers = new IInventoryPartHandler[this.baseIndexes.length];
         ListTag partNamesTag = tag.getList("inventoryPartNames", 8);
         int i = 0;

         for (Tag t : partNamesTag) {
            SlotRange slotRange = new SlotRange(
               this.baseIndexes[i], (i + 1 < this.baseIndexes.length ? this.baseIndexes[i + 1] : this.parent.getSlots()) - this.baseIndexes[i]
            );
            this.inventoryPartHandlers[i] = InventoryPartRegistry.instantiatePart(t.getAsString(), this.parent, slotRange, getMemorySettings);
            i++;
         }
      }
   }

   public boolean isInfinite(int slot) {
      return this.getPartBySlot(slot).isInfinite(slot);
   }
}
