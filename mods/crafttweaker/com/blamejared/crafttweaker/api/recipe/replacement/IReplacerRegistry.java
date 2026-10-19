package com.blamejared.crafttweaker.api.recipe.replacement;

import java.util.Collection;
import net.minecraft.resources.ResourceLocation;

public interface IReplacerRegistry {
   Collection<ITargetingFilter> filters();

   ITargetingStrategy findStrategy(ResourceLocation var1);

   Collection<ResourceLocation> allStrategyNames();
}
