package com.mongoose.royalvariations.compat;

import com.google.common.collect.ImmutableMap;
import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.compat.curios.CuriosIntegration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.neoforged.fml.ModList;

public final class OtherModCompat {
   private static final Map<String, Supplier<ICompatable>> MODULE_TYPES = ImmutableMap.builder().put("curios", CuriosIntegration::new).build();
   private static final Map<String, ICompatable> MODULES = new HashMap<>();

   public static void setup() {
      populateModules(ModList.get()::isLoaded);
      MODULES.values().forEach(ICompatable::setup);
   }

   private static void populateModules(Predicate<String> isLoaded) {
      for (Entry<String, Supplier<ICompatable>> entry : MODULE_TYPES.entrySet()) {
         String id = entry.getKey();
         if (isLoaded.test(id)) {
            MODULES.put(id, entry.getValue().get());
            RoyalVariations.LOGGER.info("Loading compat module for mod " + id);
         }
      }
   }
}
