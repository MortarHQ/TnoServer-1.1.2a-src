package dev.ftb.mods.ftbultimine.integration.ezcrystals;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.ftb.mods.ftbultimine.integration.ezcrystals.neoforge.EZCrystalsImpl;

public class EZCrystals {
   @ExpectPlatform
   @Transformed
   public static void init() {
      EZCrystalsImpl.init();
   }
}
