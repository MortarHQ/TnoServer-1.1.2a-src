package de.ellpeck.naturesaura.compat.jei;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.container.IAuraContainer;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import de.ellpeck.naturesaura.items.ItemAuraBottle;
import de.ellpeck.naturesaura.items.ItemEffectPowder;
import de.ellpeck.naturesaura.items.ModItems;
import de.ellpeck.naturesaura.recipes.AltarRecipe;
import de.ellpeck.naturesaura.recipes.AnimalSpawnerRecipe;
import de.ellpeck.naturesaura.recipes.ModRecipes;
import de.ellpeck.naturesaura.recipes.OfferingRecipe;
import de.ellpeck.naturesaura.recipes.TreeRitualRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.Nullable;

@JeiPlugin
public class JEINaturesAuraPlugin implements IModPlugin {
   public static final RecipeType<TreeRitualRecipe> TREE_RITUAL = RecipeType.create("naturesaura", "tree_ritual", TreeRitualRecipe.class);
   public static final RecipeType<AltarRecipe> ALTAR = RecipeType.create("naturesaura", "altar", AltarRecipe.class);
   public static final RecipeType<OfferingRecipe> OFFERING = RecipeType.create("naturesaura", "offering", OfferingRecipe.class);
   public static final RecipeType<AnimalSpawnerRecipe> SPAWNER = RecipeType.create("naturesaura", "animal_spawner", AnimalSpawnerRecipe.class);

   public ResourceLocation getPluginUid() {
      return ResourceLocation.fromNamespaceAndPath("naturesaura", "jei_plugin");
   }

   public void registerCategories(IRecipeCategoryRegistration registry) {
      IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();
      registry.addRecipeCategories(
         new IRecipeCategory[]{new TreeRitualCategory(helper), new AltarCategory(helper), new OfferingCategory(helper), new AnimalSpawnerCategory(helper)}
      );
   }

   public void registerItemSubtypes(ISubtypeRegistration registration) {
      registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ModItems.EFFECT_POWDER, new ISubtypeInterpreter<ItemStack>() {
         @Nullable
         public Object getSubtypeData(ItemStack ingredient, UidContext context) {
            return ItemEffectPowder.getEffect(ingredient);
         }

         public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
            ResourceLocation effect = ItemEffectPowder.getEffect(ingredient);
            return effect != null ? effect.toString() : "";
         }
      });
      registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ModItems.AURA_BOTTLE, new ISubtypeInterpreter<ItemStack>() {
         @Nullable
         public Object getSubtypeData(ItemStack ingredient, UidContext context) {
            return ItemAuraBottle.getType(ingredient).getName();
         }

         public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
            return ItemAuraBottle.getType(ingredient).getName().toString();
         }
      });
      var auraInterpreter = new ISubtypeInterpreter<ItemStack>() {
         @Nullable
         public Object getSubtypeData(ItemStack ingredient, UidContext context) {
            IAuraContainer container = (IAuraContainer)ingredient.getCapability(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY);
            return container != null ? container.getStoredAura() : null;
         }

         public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
            IAuraContainer container = (IAuraContainer)ingredient.getCapability(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY);
            return container != null ? String.valueOf(container.getStoredAura()) : "";
         }
      };
      registration.registerSubtypeInterpreter(ModItems.AURA_CACHE, auraInterpreter);
      registration.registerSubtypeInterpreter(ModItems.AURA_TROVE, auraInterpreter);
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
      registration.addRecipeCatalyst(new ItemStack(ModBlocks.GOLD_POWDER), new RecipeType[]{TREE_RITUAL});
      registration.addRecipeCatalyst(new ItemStack(ModBlocks.WOOD_STAND), new RecipeType[]{TREE_RITUAL});
      registration.addRecipeCatalyst(new ItemStack(ModBlocks.NATURE_ALTAR), new RecipeType[]{ALTAR});
      registration.addRecipeCatalyst(new ItemStack(ModBlocks.OFFERING_TABLE), new RecipeType[]{OFFERING});
      registration.addRecipeCatalyst(new ItemStack(ModBlocks.ANIMAL_SPAWNER), new RecipeType[]{SPAWNER});
   }

   public void registerRecipes(IRecipeRegistration registration) {
      RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
      registration.addRecipes(TREE_RITUAL, manager.getAllRecipesFor(ModRecipes.TREE_RITUAL_TYPE).stream().map(RecipeHolder::value).toList());
      registration.addRecipes(ALTAR, manager.getAllRecipesFor(ModRecipes.ALTAR_TYPE).stream().map(RecipeHolder::value).toList());
      registration.addRecipes(OFFERING, manager.getAllRecipesFor(ModRecipes.OFFERING_TYPE).stream().map(RecipeHolder::value).toList());
      registration.addRecipes(SPAWNER, manager.getAllRecipesFor(ModRecipes.ANIMAL_SPAWNER_TYPE).stream().map(RecipeHolder::value).toList());
   }
}
