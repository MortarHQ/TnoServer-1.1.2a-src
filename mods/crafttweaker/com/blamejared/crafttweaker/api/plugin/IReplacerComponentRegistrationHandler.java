package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingFilter;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingStrategy;
import net.minecraft.resources.ResourceLocation;

public interface IReplacerComponentRegistrationHandler {
   void registerTargetingFilter(ITargetingFilter var1);

   void registerTargetingStrategy(ResourceLocation var1, ITargetingStrategy var2);
}
