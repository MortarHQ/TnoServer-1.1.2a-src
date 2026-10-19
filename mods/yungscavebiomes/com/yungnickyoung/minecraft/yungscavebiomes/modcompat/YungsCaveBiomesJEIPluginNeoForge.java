package com.yungnickyoung.minecraft.yungscavebiomes.modcompat;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.PotionModule;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class YungsCaveBiomesJEIPluginNeoForge implements IModPlugin {
   private static final ResourceLocation PLUGIN_UID = YungsCaveBiomesCommon.id("jei_plugin");

   @NotNull
   public ResourceLocation getPluginUid() {
      return PLUGIN_UID;
   }

   public void registerRecipes(IRecipeRegistration registration) {
      List<IJeiBrewingRecipe> recipes = List.of(
         registration.getVanillaRecipeFactory()
            .createBrewingRecipe(
               List.of(((Block)BlockModule.FROST_LILY.get()).asItem().getDefaultInstance()),
               PotionContents.createItemStack(Items.POTION, Potions.AWKWARD),
               PotionContents.createItemStack(Items.POTION, PotionModule.FROST_POTION.getHolder()),
               YungsCaveBiomesCommon.id("frost_potion_from_frost_lily")
            ),
         registration.getVanillaRecipeFactory()
            .createBrewingRecipe(
               List.of(((Block)BlockModule.FROST_LILY.get()).asItem().getDefaultInstance()),
               PotionContents.createItemStack(Items.SPLASH_POTION, Potions.AWKWARD),
               PotionContents.createItemStack(Items.SPLASH_POTION, PotionModule.FROST_POTION.getHolder()),
               YungsCaveBiomesCommon.id("frost_splash_potion_from_awkward_splash_potion")
            ),
         registration.getVanillaRecipeFactory()
            .createBrewingRecipe(
               List.of(((Block)BlockModule.FROST_LILY.get()).asItem().getDefaultInstance()),
               PotionContents.createItemStack(Items.LINGERING_POTION, Potions.AWKWARD),
               PotionContents.createItemStack(Items.LINGERING_POTION, PotionModule.FROST_POTION.getHolder()),
               YungsCaveBiomesCommon.id("frost_lingering_potion_from_awkward_lingering_potion")
            )
      );
      registration.addRecipes(RecipeTypes.BREWING, recipes);
   }
}
