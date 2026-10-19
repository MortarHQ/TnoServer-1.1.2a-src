package com.blackgear.vanillabackport.core.neoforge.compat;

import com.blackgear.vanillabackport.common.api.bundle.BundleFeatures;
import com.blackgear.vanillabackport.common.level.crafting.BundleColoring;
import com.blackgear.vanillabackport.core.VanillaBackport;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapelessRecipe;

@JeiPlugin
public class VanillaBackportJeiPlugin implements IModPlugin {
   public void registerRecipes(IRecipeRegistration registration) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level != null) {
         RecipeManager recipeManager = mc.level.getRecipeManager();
         boolean hasBundleColoring = recipeManager.getAllRecipesFor(RecipeType.CRAFTING)
            .stream()
            .<CraftingRecipe>map(RecipeHolder::value)
            .anyMatch(r -> r instanceof BundleColoring);
         if (hasBundleColoring) {
            List<RecipeHolder<CraftingRecipe>> extraRecipes = new ArrayList<>();

            for (DyeColor dyeColor : DyeColor.values()) {
               Item resultBundle = BundleFeatures.getByColor(dyeColor);
               List<Item> dyeItems = getDyesByColor().get(dyeColor);
               if (resultBundle != null && dyeItems != null && !dyeItems.isEmpty()) {
                  List<Item> otherBundles = getBundles().stream().filter(item -> item != resultBundle).toList();
                  if (!otherBundles.isEmpty()) {
                     Ingredient anyOtherBundle = Ingredient.of(otherBundles.stream().map(ItemStack::new).toArray(ItemStack[]::new));
                     Ingredient dyeChoices = Ingredient.of(dyeItems.stream().map(ItemStack::new).toArray(ItemStack[]::new));
                     NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, new Ingredient[]{anyOtherBundle, dyeChoices});
                     ItemStack output = new ItemStack(resultBundle);
                     ResourceLocation baseId = BuiltInRegistries.ITEM.getKey(resultBundle);
                     ResourceLocation displayId = ResourceLocation.fromNamespaceAndPath(baseId.getNamespace(), "/" + baseId.getPath());
                     ShapelessRecipe recipe = new ShapelessRecipe("", CraftingBookCategory.MISC, output, inputs);
                     RecipeHolder<CraftingRecipe> holder = new RecipeHolder(displayId, recipe);
                     extraRecipes.add(holder);
                  }
               }
            }

            registration.addRecipes(RecipeTypes.CRAFTING, extraRecipes);
         }
      }
   }

   private static List<Item> getBundles() {
      return List.copyOf(BundleFeatures.BUNDLES_BY_DYE.values());
   }

   private static Map<DyeColor, List<Item>> getDyesByColor() {
      return BuiltInRegistries.ITEM
         .stream()
         .filter(item -> item instanceof DyeItem)
         .map(item -> (DyeItem)item)
         .collect(Collectors.groupingBy(DyeItem::getDyeColor, () -> new EnumMap(DyeColor.class), Collectors.mapping(d -> (Item)d, Collectors.toList())));
   }

   public ResourceLocation getPluginUid() {
      return VanillaBackport.resource("jei_plugin");
   }
}
