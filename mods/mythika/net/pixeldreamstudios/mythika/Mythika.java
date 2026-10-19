package net.pixeldreamstudios.mythika;

import java.util.List;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mythika.registry.EntityRegistry;
import net.pixeldreamstudios.mythika.registry.ItemRegistry;
import net.pixeldreamstudios.mythika.registry.StructureRegistry;
import net.pixeldreamstudios.mythika.registry.TabRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Mythika {
   public static final String MOD_ID = "mythika";
   public static final Logger LOGGER = LoggerFactory.getLogger("mythika");
   public static MythikaConfig config;

   private Mythika() {
   }

   public static void init() {
      ConfigHolder<MythikaConfig> holder = AutoConfig.register(MythikaConfig.class, JanksonConfigSerializer::new);
      config = (MythikaConfig)holder.getConfig();
      List<String> moved = config.migrate();
      if (!moved.isEmpty()) {
         LOGGER.info("Config was written against older defaults - retuned {}: {}", moved.size(), String.join(", ", moved));
      }

      holder.save();
      EntityRegistry.init();
      ItemRegistry.init();
      TabRegistry.init();
      StructureRegistry.init();
   }

   public static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("mythika", path);
   }
}
