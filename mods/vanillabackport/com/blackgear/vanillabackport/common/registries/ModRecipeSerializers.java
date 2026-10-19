package com.blackgear.vanillabackport.common.registries;

import com.blackgear.platform.core.CoreRegistry;
import com.blackgear.vanillabackport.common.level.crafting.BundleColoring;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class ModRecipeSerializers {
   public static final CoreRegistry<RecipeSerializer<?>> SERIALIZERS = CoreRegistry.create(BuiltInRegistries.RECIPE_SERIALIZER, "minecraft");
   public static final Supplier<RecipeSerializer<BundleColoring>> BUNDLE_COLORING = SERIALIZERS.register(
      "crafting_special_bundlecoloring", () -> new SimpleCraftingRecipeSerializer(BundleColoring::new)
   );
}
