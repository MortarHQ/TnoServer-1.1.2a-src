package cc.sighs.oelib.config;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.config.model.ConfigMeta;
import cc.sighs.oelib.config.model.ConfigSide;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class ClientConfigManager implements ResourceManagerReloadListener {
   private static final Map<ResourceLocation, ConfigUnit<?>> CONFIGS = new ConcurrentHashMap<>();

   static void registerUnit(ConfigUnit<?> unit) {
      ConfigMeta meta = unit.meta();
      if (meta.side() != ConfigSide.CLIENT) {
         OELib.LOGGER.warn("Registering non-client config {} into ClientConfigManager (side={})", meta.id(), meta.side());
      }

      ConfigUnit<?> existing = CONFIGS.put(unit.id(), unit);
      if (existing != null) {
         OELib.LOGGER.warn("Duplicate client config registration for {}, replacing previous unit", unit.id());
      }

      unit.applyAutoMigrationOnRegister();
   }

   static Optional<ConfigUnit<?>> get(ResourceLocation id) {
      return Optional.ofNullable(CONFIGS.get(id));
   }

   public static Map<ResourceLocation, ConfigUnit<?>> all() {
      return Map.copyOf(CONFIGS);
   }

   static void reloadAll() {
      for (ConfigUnit<?> unit : CONFIGS.values()) {
         unit.reload();
      }
   }

   public void onResourceManagerReload(ResourceManager resourceManager) {
      reloadAll();
      OELib.LOGGER.info("Successfully reload {} client config(s).", CONFIGS.size());
   }
}
