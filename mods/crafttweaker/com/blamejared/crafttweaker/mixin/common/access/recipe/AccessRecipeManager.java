package com.blamejared.crafttweaker.mixin.common.access.recipe;

import com.google.common.collect.Multimap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RecipeManager.class)
public interface AccessRecipeManager {
   @Accessor("byType")
   Multimap<RecipeType<?>, RecipeHolder<?>> crafttweaker$getByType();

   @Accessor("byType")
   void crafttweaker$setByType(Multimap<RecipeType<?>, RecipeHolder<?>> var1);

   @Accessor("byName")
   Map<ResourceLocation, RecipeHolder<?>> crafttweaker$getByName();

   @Accessor("byName")
   void crafttweaker$setByName(Map<ResourceLocation, RecipeHolder<?>> var1);
}
