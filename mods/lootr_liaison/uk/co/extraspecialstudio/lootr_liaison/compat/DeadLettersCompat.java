package uk.co.extraspecialstudio.lootr_liaison.compat;

import net.neoforged.fml.ModList;

public final class DeadLettersCompat {
   private static final String DEAD_LETTERS_MODID = "dead_letters";

   private DeadLettersCompat() {
   }

   public static boolean isPresent() {
      return ModList.get().isLoaded("dead_letters");
   }
}
