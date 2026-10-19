package com.sbjeiindex.jei;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.inventory.Slot;

public final class JeiSlotResolver {
   private static final ThreadLocal<Map<Integer, Slot>> EXTRA_SLOTS = new ThreadLocal<>();

   private JeiSlotResolver() {
   }

   public static void set(@Nullable Map<Integer, Slot> slots) {
      if (slots != null && !slots.isEmpty()) {
         EXTRA_SLOTS.set(slots);
      } else {
         EXTRA_SLOTS.remove();
      }
   }

   @Nullable
   public static Slot resolve(int slotId) {
      Map<Integer, Slot> slots = EXTRA_SLOTS.get();
      return slots == null ? null : slots.get(slotId);
   }

   public static void clear() {
      EXTRA_SLOTS.remove();
   }
}
