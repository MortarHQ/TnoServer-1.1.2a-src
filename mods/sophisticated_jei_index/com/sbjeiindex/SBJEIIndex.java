package com.sbjeiindex;

import com.sbjeiindex.config.SBJEIIndexConfig;
import com.sbjeiindex.init.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod("sophisticated_jei_index")
public class SBJEIIndex {
   public static final String MOD_ID = "sophisticated_jei_index";
   private static final ResourceKey<CreativeModeTab> SOPHISTICATED_BACKPACKS_TAB = ResourceKey.create(
      Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath("sophisticatedbackpacks", "main")
   );

   public SBJEIIndex(IEventBus modBus, Dist dist, ModContainer container) {
      container.registerConfig(Type.COMMON, SBJEIIndexConfig.SPEC);
      ModItems.ITEMS.register(modBus);
      modBus.addListener(this::addCreative);
   }

   private void addCreative(BuildCreativeModeTabContentsEvent event) {
      if (event.getTabKey() == SOPHISTICATED_BACKPACKS_TAB) {
         event.accept((ItemLike)ModItems.JEI_INDEX_UPGRADE.get());
      }
   }
}
