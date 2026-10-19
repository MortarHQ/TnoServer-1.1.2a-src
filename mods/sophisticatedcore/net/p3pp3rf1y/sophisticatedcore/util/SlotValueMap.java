package net.p3pp3rf1y.sophisticatedcore.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SlotValueMap<T> {
   private final Map<Integer, T> slotToValue = new HashMap<>();
   private final Map<T, Set<Integer>> valueToSlots = new HashMap<>();

   public SlotValueMap() {
   }

   public SlotValueMap(int slot, T value) {
      this.add(slot, value);
   }

   private SlotValueMap(Object... input) {
      if (input.length % 2 != 0) {
         throw new IllegalArgumentException("Input must be a multiple of 2");
      }

      for (int i = 0; i < input.length; i += 2) {
         this.add((Integer)input[i], (T)input[i + 1]);
      }
   }

   public static <T> SlotValueMap<T> of() {
      return new SlotValueMap<>();
   }

   public static <T> SlotValueMap<T> of(int slot, T value) {
      return new SlotValueMap<>(slot, value);
   }

   public static <T> SlotValueMap<T> of(int slot1, T value1, int slot2, T value2) {
      return new SlotValueMap<>(slot1, value1, slot2, value2);
   }

   public void add(int slot, T value) {
      this.remove(slot);
      this.slotToValue.put(slot, value);
      this.valueToSlots.computeIfAbsent(value, k -> new HashSet<>()).add(slot);
   }

   public boolean containsSlotAndDoesNotMatch(int slot, T value) {
      return this.containsSlot(slot) && !this.slotToValue.get(slot).equals(value);
   }

   public boolean containsSlot(int slot) {
      return this.slotToValue.containsKey(slot);
   }

   public boolean containsValue(T value) {
      return this.valueToSlots.containsKey(value);
   }

   public void remove(int slot) {
      T value = this.slotToValue.remove(slot);
      if (value != null) {
         Set<Integer> slots = this.valueToSlots.get(value);
         slots.remove(slot);
         if (slots.isEmpty()) {
            this.valueToSlots.remove(value);
         }
      }
   }

   public Set<Integer> getSlots(T value) {
      return this.valueToSlots.getOrDefault(value, Collections.emptySet());
   }

   public void clear() {
      this.slotToValue.clear();
      this.valueToSlots.clear();
   }

   public Set<T> keySet() {
      return this.valueToSlots.keySet();
   }
}
