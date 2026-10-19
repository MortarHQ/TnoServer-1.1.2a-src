package de.ellpeck.naturesaura.compat;

import com.google.common.collect.ImmutableMap;
import de.ellpeck.naturesaura.NaturesAura;
import de.ellpeck.naturesaura.compat.patchouli.PatchouliCompat;
import de.ellpeck.naturesaura.data.ItemTagProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class Compat {
   private static final Map<String, Supplier<ICompat>> MODULE_TYPES = ImmutableMap.builder()
      .put("patchouli", (Supplier<ICompat>)() -> new PatchouliCompat())
      .put("curios", (Supplier<ICompat>)() -> new CuriosCompat())
      .build();
   private static final Map<String, ICompat> MODULES = new HashMap<>();

   public static void setup(FMLCommonSetupEvent event) {
      populateModules(ModList.get()::isLoaded);
      MODULES.values().forEach(c -> c.setup(event));
   }

   public static void setupClient() {
      MODULES.values().forEach(ICompat::setupClient);
   }

   public static boolean hasCompat(String mod) {
      return MODULES.containsKey(mod);
   }

   public static void gatherData(GatherDataEvent event) {
      populateModules(s -> true);
      MODULES.values().forEach(m -> m.gatherData(event));
   }

   public static void addItemTags(ItemTagProvider provider) {
      MODULES.values().forEach(m -> m.addItemTags(provider));
   }

   public static void addCapabilities(RegisterCapabilitiesEvent event) {
      MODULES.values().forEach(c -> c.addCapabilities(event));
   }

   private static void populateModules(Predicate<String> isLoaded) {
      for (Entry<String, Supplier<ICompat>> entry : MODULE_TYPES.entrySet()) {
         String id = entry.getKey();
         if (isLoaded.test(id)) {
            MODULES.put(id, entry.getValue().get());
            NaturesAura.LOGGER.info("Loading compat module for mod " + id);
         }
      }
   }
}
