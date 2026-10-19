package it.hurts.sskirillss.relics.init;

import it.hurts.octostudios.octolib.module.config.ConfigManager;
import it.hurts.sskirillss.relics.config.LootConfigData;
import it.hurts.sskirillss.relics.config.RelicsConfigData;
import it.hurts.sskirillss.relics.config.data.RelicConfigData;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import java.util.Map.Entry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ConfigRegistry {
   public static final RelicsConfigData RELICS_CONFIG = new RelicsConfigData();
   public static final LootConfigData LOOT_CONFIG = new LootConfigData();

   public static void register() {
      ConfigManager.registerConfig("relics", RELICS_CONFIG);
      if (RELICS_CONFIG.isEnabledExtendedConfigs()) {
         ConfigManager.registerConfig("relics/loot", LOOT_CONFIG);

         for (Entry<ResourceKey<Item>, Item> entry : BuiltInRegistries.ITEM.entrySet()) {
            if (entry.getValue() instanceof IRelicItem relic) {
               RelicConfigData data = relic.constructDefaultConfigData(new RelicConfigData(relic));
               if (data != null) {
                  ConfigManager.registerConfig(relic.getConfigRoute() + "/relics/" + entry.getKey().location().getPath(), data);
               }
            }
         }
      }
   }
}
