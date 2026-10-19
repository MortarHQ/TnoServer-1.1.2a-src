package com.chaosthedude.explorerscompass.registry;

import com.chaosthedude.explorerscompass.ExplorersCompass;
import com.chaosthedude.explorerscompass.items.ExplorersCompassItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid = "explorerscompass", bus = Bus.MOD)
public class ExplorersCompassRegistry {
   @SubscribeEvent
   public static void registerItems(RegisterEvent e) {
      e.register(BuiltInRegistries.ITEM.key(), helper -> {
         ExplorersCompass.explorersCompass = new ExplorersCompassItem();
         helper.register(ResourceLocation.fromNamespaceAndPath("explorerscompass", "explorerscompass"), ExplorersCompass.explorersCompass);
      });
      e.register(BuiltInRegistries.DATA_COMPONENT_TYPE.key(), registry -> {
         registry.register(ResourceLocation.fromNamespaceAndPath("explorerscompass", "structure_id"), ExplorersCompass.STRUCTURE_ID_COMPONENT);
         registry.register(ResourceLocation.fromNamespaceAndPath("explorerscompass", "compass_state"), ExplorersCompass.COMPASS_STATE_COMPONENT);
         registry.register(ResourceLocation.fromNamespaceAndPath("explorerscompass", "found_x"), ExplorersCompass.FOUND_X_COMPONENT);
         registry.register(ResourceLocation.fromNamespaceAndPath("explorerscompass", "found_z"), ExplorersCompass.FOUND_Z_COMPONENT);
         registry.register(ResourceLocation.fromNamespaceAndPath("explorerscompass", "search_radius"), ExplorersCompass.SEARCH_RADIUS_COMPONENT);
         registry.register(ResourceLocation.fromNamespaceAndPath("explorerscompass", "samples"), ExplorersCompass.SAMPLES_COMPONENT);
         registry.register(ResourceLocation.fromNamespaceAndPath("explorerscompass", "display_coords"), ExplorersCompass.DISPLAY_COORDS_COMPONENT);
         registry.register(ResourceLocation.fromNamespaceAndPath("explorerscompass", "is_group"), ExplorersCompass.IS_GROUP_COMPONENT);
         registry.register(ResourceLocation.fromNamespaceAndPath("explorerscompass", "prev_pos"), ExplorersCompass.PREV_POS_COMPONENT);
         registry.register(ResourceLocation.fromNamespaceAndPath("explorerscompass", "damage"), ExplorersCompass.DAMAGE_COMPONENT);
      });
   }
}
