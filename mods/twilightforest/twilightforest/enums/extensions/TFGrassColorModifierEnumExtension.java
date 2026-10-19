package twilightforest.enums.extensions;

import net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier;
import twilightforest.beanification.Component;

@Component
public class TFGrassColorModifierEnumExtension {
   public final GrassColorModifier ENCHANTED_FOREST = GrassColorModifier.valueOf("TWILIGHTFOREST_ENCHANTED_FOREST");
   public final GrassColorModifier SWAMP = GrassColorModifier.valueOf("TWILIGHTFOREST_SWAMP");
   public final GrassColorModifier DARK_FOREST = GrassColorModifier.valueOf("TWILIGHTFOREST_DARK_FOREST");
   public final GrassColorModifier DARK_FOREST_CENTER = GrassColorModifier.valueOf("TWILIGHTFOREST_DARK_FOREST_CENTER");
   public final GrassColorModifier SPOOKY_FOREST = GrassColorModifier.valueOf("TWILIGHTFOREST_SPOOKY_FOREST");
}
