package toni.chunkactivitytracker.foundation.config;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.config.ModConfigEvent.Loading;
import net.neoforged.fml.event.config.ModConfigEvent.Reloading;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import org.apache.commons.lang3.tuple.Pair;
import toni.lib.config.ConfigBase;

@EventBusSubscriber(bus = Bus.MOD)
public class AllConfigs {
   private static final Map<Type, ConfigBase> CONFIGS = new EnumMap<>(Type.class);
   private static CServer server;

   public static CServer server() {
      return server;
   }

   public static ConfigBase byType(Type type) {
      return CONFIGS.get(type);
   }

   private static <T extends ConfigBase> T register(Supplier<T> factory, Type side) {
      Pair<T, ModConfigSpec> specPair = new Builder().configure(builder -> {
         T configx = factory.get();
         configx.registerAll(builder);
         return configx;
      });
      T config = (T)specPair.getLeft();
      config.specification = (ModConfigSpec)specPair.getRight();
      CONFIGS.put(side, config);
      return config;
   }

   public static void register(BiConsumer<Type, ModConfigSpec> registration) {
      server = register(CServer::new, Type.SERVER);

      for (Entry<Type, ConfigBase> pair : CONFIGS.entrySet()) {
         registration.accept(pair.getKey(), pair.getValue().specification);
      }
   }

   @SubscribeEvent
   public static void onLoad(Loading event) {
      for (ConfigBase config : CONFIGS.values()) {
         if (config.specification == event.getConfig().getSpec()) {
            config.onLoad();
         }
      }
   }

   @SubscribeEvent
   public static void onReload(Reloading event) {
      for (ConfigBase config : CONFIGS.values()) {
         if (config.specification == event.getConfig().getSpec()) {
            config.onReload();
         }
      }
   }
}
