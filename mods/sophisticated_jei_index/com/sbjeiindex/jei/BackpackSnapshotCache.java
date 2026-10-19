package com.sbjeiindex.jei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.SlotItemHandler;

public final class BackpackSnapshotCache {
   private static final Map<AbstractContainerMenu, BackpackSnapshotCache.BackpackSnapshot> BY_CONTAINER = new WeakHashMap<>();

   private BackpackSnapshotCache() {
   }

   public static BackpackSnapshotCache.BackpackSnapshot getOrCreate(AbstractContainerMenu container, List<IItemHandlerModifiable> handlers) {
      BackpackSnapshotCache.BackpackSnapshot existing = BY_CONTAINER.get(container);
      if (existing != null && sameHandlers(existing.handlers, handlers)) {
         return existing;
      }

      BackpackSnapshotCache.BackpackSnapshot snapshot = BackpackSnapshotCache.BackpackSnapshot.build(handlers);
      BY_CONTAINER.put(container, snapshot);
      return snapshot;
   }

   private static boolean sameHandlers(List<IItemHandlerModifiable> a, List<IItemHandlerModifiable> b) {
      if (a == b) {
         return true;
      }

      if (a != null && b != null && a.size() == b.size()) {
         for (int i = 0; i < a.size(); i++) {
            if (a.get(i) != b.get(i)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static final class BackpackSnapshot {
      private final List<IItemHandlerModifiable> handlers;
      private final List<Slot> backpackSlots;
      private final Map<Integer, Slot> extraSlots;
      private final Map<Integer, ItemStack> nonEmptyStacks;
      private final int totalBackpackSlots;
      private final int emptyBackpackSlots;

      private BackpackSnapshot(
         List<IItemHandlerModifiable> handlers,
         List<Slot> backpackSlots,
         Map<Integer, Slot> extraSlots,
         Map<Integer, ItemStack> nonEmptyStacks,
         int totalBackpackSlots,
         int emptyBackpackSlots
      ) {
         this.handlers = handlers;
         this.backpackSlots = backpackSlots;
         this.extraSlots = extraSlots;
         this.nonEmptyStacks = nonEmptyStacks;
         this.totalBackpackSlots = totalBackpackSlots;
         this.emptyBackpackSlots = emptyBackpackSlots;
      }

      public List<Slot> backpackSlots() {
         return this.backpackSlots;
      }

      public Map<Integer, Slot> extraSlots() {
         return this.extraSlots;
      }

      public Map<Integer, ItemStack> nonEmptyStacks() {
         return this.nonEmptyStacks;
      }

      public int totalBackpackSlots() {
         return this.totalBackpackSlots;
      }

      public int emptyBackpackSlots() {
         return this.emptyBackpackSlots;
      }

      private static BackpackSnapshotCache.BackpackSnapshot build(List<IItemHandlerModifiable> handlers) {
         int totalSlots = 0;

         for (IItemHandlerModifiable handler : handlers) {
            totalSlots += handler.getSlots();
         }

         List<Slot> backpackSlots = new ArrayList<>(totalSlots);
         Map<Integer, Slot> extraSlots = new HashMap<>(Math.max(16, totalSlots * 2));
         Map<Integer, ItemStack> nonEmptyStacks = new HashMap<>();
         int nonEmptyCount = 0;

         for (int backpackIndex = 0; backpackIndex < handlers.size(); backpackIndex++) {
            IItemHandlerModifiable backpackHandler = handlers.get(backpackIndex);
            int baseOffset = 10000 + backpackIndex * 10000;
            OffsetItemHandlerModifiable offsetHandler = new OffsetItemHandlerModifiable(backpackHandler, baseOffset);
            int slots = backpackHandler.getSlots();

            for (int i = 0; i < slots; i++) {
               int slotId = baseOffset + i;
               Slot slot = new SlotItemHandler(offsetHandler, slotId, 0, 0);
               slot.index = slotId;
               backpackSlots.add(slot);
               extraSlots.put(slotId, slot);
               ItemStack stack = backpackHandler.getStackInSlot(i);
               if (!stack.isEmpty()) {
                  nonEmptyStacks.put(slotId, stack.copy());
                  nonEmptyCount++;
               }
            }
         }

         int emptySlots = totalSlots - nonEmptyCount;
         return new BackpackSnapshotCache.BackpackSnapshot(
            List.copyOf(handlers), List.copyOf(backpackSlots), Map.copyOf(extraSlots), Map.copyOf(nonEmptyStacks), totalSlots, emptySlots
         );
      }
   }
}
