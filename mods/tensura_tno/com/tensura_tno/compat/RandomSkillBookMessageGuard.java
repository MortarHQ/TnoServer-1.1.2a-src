package com.tensura_tno.compat;

public final class RandomSkillBookMessageGuard {
   private static final ThreadLocal<RandomSkillBookMessageGuard.Attempt> ACTIVE = new ThreadLocal<>();

   private RandomSkillBookMessageGuard() {
   }

   public static void duringRandomUse(Object player, Runnable use) {
      RandomSkillBookMessageGuard.Attempt previous = ACTIVE.get();
      ACTIVE.set(new RandomSkillBookMessageGuard.Attempt(player));

      try {
         use.run();
      } finally {
         if (previous == null) {
            ACTIVE.remove();
         } else {
            ACTIVE.set(previous);
         }
      }
   }

   public static boolean deferSoulEnergyFailure(Object player, String key, Runnable display) {
      RandomSkillBookMessageGuard.Attempt attempt = ACTIVE.get();
      if (attempt != null && attempt.player == player && isSoulEnergyFailure(key)) {
         if (attempt.firstFailure == null) {
            attempt.firstFailure = display;
         }

         return true;
      } else {
         return false;
      }
   }

   public static void displayRandomResult(Object player, String key, Runnable display) {
      RandomSkillBookMessageGuard.Attempt attempt = ACTIVE.get();
      if (attempt != null && attempt.player == player && "message.tensura_skill_books.invalid_skill".equals(key) && attempt.firstFailure != null) {
         Runnable failure = attempt.firstFailure;
         attempt.firstFailure = null;
         failure.run();
      } else {
         display.run();
      }
   }

   private static boolean isSoulEnergyFailure(String key) {
      return "mysticism.soulEnergy.no_skill".equals(key) || "mysticism.skill.acquire_failed.soul_energy".equals(key);
   }

   private static final class Attempt {
      private final Object player;
      private Runnable firstFailure;

      private Attempt(Object player) {
         this.player = player;
      }
   }
}
