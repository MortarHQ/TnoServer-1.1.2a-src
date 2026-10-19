package com.blackgear.vanillabackport.client.api.renderer;

import com.blackgear.vanillabackport.core.ModChecker;
import com.blackgear.vanillabackport.core.VanillaBackport;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@FunctionalInterface
@OnlyIn(Dist.CLIENT)
public interface RenderConditions {
   RenderConditions DEFAULT = () -> true;
   RenderConditions FARM_ANIMALS = () -> (Boolean)VanillaBackport.COMMON_CONFIG.hasFarmAnimalVariants.get() && !ModChecker.MIXED_LITTER_LOADED;
   RenderConditions SHEEP_UNDERCOAT = () -> (Boolean)VanillaBackport.CLIENT_CONFIG.useSheepWoolUndercoat.get() && !ModChecker.MIXED_LITTER_LOADED;

   boolean apply();
}
