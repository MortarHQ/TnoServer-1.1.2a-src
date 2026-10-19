package org.crypticdev.stextras.event;

public final class MenuOutputTakeTracker {
   private static final ThreadLocal<Boolean> SUPPRESS_SLOT_OUTPUT_EVENTS = ThreadLocal.withInitial(() -> false);

   public static void beginSuppressingSlotEvents() {
      SUPPRESS_SLOT_OUTPUT_EVENTS.set(true);
   }

   public static void endSuppressingSlotEvents() {
      SUPPRESS_SLOT_OUTPUT_EVENTS.remove();
   }

   public static boolean isSuppressingSlotEvents() {
      return SUPPRESS_SLOT_OUTPUT_EVENTS.get();
   }

   private MenuOutputTakeTracker() {
   }
}
