package com.finderfeed.raids_enhanced;

import com.finderfeed.raids_enhanced.init.REAnimations;
import com.finderfeed.raids_enhanced.init.REConfigs;
import com.finderfeed.raids_enhanced.init.RECreativeTabs;
import com.finderfeed.raids_enhanced.init.REEntities;
import com.finderfeed.raids_enhanced.init.REItems;
import com.finderfeed.raids_enhanced.init.REModels;
import com.finderfeed.raids_enhanced.init.REParticles;
import com.finderfeed.raids_enhanced.init.RESounds;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod("raidsenhanced")
public class RaidsEnhanced {
   public static final String MOD_ID = "raidsenhanced";
   public static final Logger LOGGER = LogUtils.getLogger();

   public static ResourceLocation location(String loc) {
      return ResourceLocation.tryBuild("raidsenhanced", loc);
   }

   public RaidsEnhanced(IEventBus modEventBus, ModContainer modContainer) {
      REAnimations.ANIMATIONS.register(modEventBus);
      REParticles.PARTICLES.register(modEventBus);
      RECreativeTabs.TABS.register(modEventBus);
      REEntities.ENTITIES.register(modEventBus);
      REConfigs.CONFIGS.register(modEventBus);
      REModels.MODELS.register(modEventBus);
      RESounds.SOUNDS.register(modEventBus);
      REItems.ITEMS.register(modEventBus);
   }
}
