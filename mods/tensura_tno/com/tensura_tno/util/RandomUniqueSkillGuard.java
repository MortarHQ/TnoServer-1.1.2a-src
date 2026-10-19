package com.tensura_tno.util;

public final class RandomUniqueSkillGuard {
   public static final ThreadLocal<Boolean> ACTIVE = ThreadLocal.withInitial(() -> false);

   private RandomUniqueSkillGuard() {
   }

   public static void runGuarded(Runnable action) {
      boolean previouslyActive = ACTIVE.get();
      ACTIVE.set(true);

      try {
         action.run();
      } finally {
         if (previouslyActive) {
            ACTIVE.set(true);
         } else {
            ACTIVE.remove();
         }
      }
   }
}
