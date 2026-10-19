package com.github.ysbbbbbb.kaleidoscopecookery.compat.ponder.init;

import com.github.ysbbbbbb.kaleidoscopecookery.compat.ponder.scenes.EnamelBasinScenes;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.ponder.scenes.MillstoneScenes;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.ponder.scenes.PotScenes;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.ponder.scenes.RecipeItemScenes;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.ponder.scenes.ShawarmaSpitScenes;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.ponder.scenes.SteamerScenes;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.ponder.scenes.StockpotScenes;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class KitchenBlockPonderScreen {
   public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
      helper.forComponents(new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "stockpot")})
         .addStoryBoard("stockpot/stockpot_introduction", StockpotScenes::introduction);
      helper.forComponents(new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "pot")})
         .addStoryBoard("pot/pot_introduction", PotScenes::introduction);
      helper.forComponents(new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "steamer")})
         .addStoryBoard("steamer/steamer_introduction", SteamerScenes::introduction)
         .addStoryBoard("steamer/steamer_introduction", SteamerScenes::cooking);
      helper.forComponents(new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "enamel_basin")})
         .addStoryBoard("enamel_basin/enamel_basin_introduction", EnamelBasinScenes::introduction);
      helper.forComponents(new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "millstone")})
         .addStoryBoard("millstone/millstone_introduction", MillstoneScenes::introduction);
      helper.forComponents(new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "shawarma_spit")})
         .addStoryBoard("shawarma_spit/shawarma_spit_introduction", ShawarmaSpitScenes::introduction);
      helper.forComponents(new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "recipe_item")})
         .addStoryBoard("stockpot/stockpot_introduction", RecipeItemScenes::introduction);
   }
}
