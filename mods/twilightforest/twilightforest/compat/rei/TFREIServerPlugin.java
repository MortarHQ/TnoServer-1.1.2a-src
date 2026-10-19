package twilightforest.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.forge.REIPluginDedicatedServer;
import twilightforest.compat.rei.categories.REICrumbleHornCategory;
import twilightforest.compat.rei.categories.REIDryingCategory;
import twilightforest.compat.rei.categories.REIOminousFireCategory;
import twilightforest.compat.rei.categories.REITransformationPowderCategory;
import twilightforest.compat.rei.displays.REICrumbleHornDisplay;
import twilightforest.compat.rei.displays.REIDryingDisplay;
import twilightforest.compat.rei.displays.REIOminousFireDisplay;
import twilightforest.compat.rei.displays.REITransformationPowderDisplay;
import twilightforest.compat.rei.displays.REIUncraftingDisplay;

@REIPluginDedicatedServer
public class TFREIServerPlugin implements REIServerPlugin {
   public static final CategoryIdentifier<REIUncraftingDisplay> UNCRAFTING = CategoryIdentifier.of("twilightforest", "uncrafting");

   public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
      registry.register(REICrumbleHornCategory.CRUMBLE_HORN, REICrumbleHornDisplay.serializer());
      registry.register(REIDryingCategory.DRYING, REIDryingDisplay.serializer());
      registry.register(REITransformationPowderCategory.TRANSFORMATION, REITransformationPowderDisplay.serializer());
      registry.register(REIOminousFireCategory.OMINOUS_FIRE, REIOminousFireDisplay.serializer());
      registry.register(UNCRAFTING, REIUncraftingDisplay.Serializer.INSTANCE);
   }
}
