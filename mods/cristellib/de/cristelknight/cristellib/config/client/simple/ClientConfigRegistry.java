package de.cristelknight.cristellib.config.client.simple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class ClientConfigRegistry {
   private static final Map<String, Set<SimpleConfigScreen>> CONFIGS_WITH_SCREEN = new HashMap<>();

   public static Map<String, Set<SimpleConfigScreen>> getAllConfigsWithScreen() {
      return CONFIGS_WITH_SCREEN;
   }

   public static boolean hasScreens(String modID) {
      return CONFIGS_WITH_SCREEN.containsKey(modID);
   }

   public static Set<SimpleConfigScreen> getScreens(String modID) {
      return hasScreens(modID) ? CONFIGS_WITH_SCREEN.get(modID) : Set.of();
   }

   public static <T> void registerScreen(String modIdForScreen, String screenName, Runnable onScreenSave, Class<T> simpleConfig) {
      CONFIGS_WITH_SCREEN.computeIfAbsent(modIdForScreen, k -> new HashSet<>()).add(new SimpleConfigScreen(simpleConfig, screenName, onScreenSave));
   }

   @Nullable
   public static SimpleConfigScreen getScreen(String modID, Class<?> simpleConfig) {
      for (SimpleConfigScreen screen : getScreens(modID)) {
         if (screen.simpleConfig() == simpleConfig) {
            return screen;
         }
      }

      return null;
   }
}
