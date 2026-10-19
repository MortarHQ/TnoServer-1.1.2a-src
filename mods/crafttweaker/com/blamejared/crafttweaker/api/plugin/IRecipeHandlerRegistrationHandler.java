package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import net.minecraft.world.item.crafting.Recipe;

public interface IRecipeHandlerRegistrationHandler {
   <T extends Recipe<?>> void registerRecipeHandler(Class<? extends T> var1, IRecipeHandler<T> var2);
}
