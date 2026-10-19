package team.lodestar.lodestone.compability;

import net.neoforged.fml.ModList;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.handlers.ItemEventHandler;
import team.lodestar.lodestone.helpers.CurioHelper;

public class CuriosCompat {
   public static boolean LOADED;

   public static void init() {
      LOADED = ModList.get().isLoaded("curios");
      if (LOADED) {
         CuriosCompat.LoadedOnly.init();
      }
   }

   public static class LoadedOnly {
      public static final ItemEventHandler.EventResponderSource CURIOS = new ItemEventHandler.EventResponderSource(
         LodestoneLib.lodestonePath("curios"), CurioHelper::getEquippedCurios
      );

      public static void init() {
         ItemEventHandler.registerLookup(CURIOS);
      }
   }
}
