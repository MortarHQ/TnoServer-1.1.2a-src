package twilightforest.compat.jei;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import twilightforest.client.UncraftingScreen;
import twilightforest.compat.RecipeViewerConstants;
import twilightforest.compat.jei.categories.CrumbleHornCategory;
import twilightforest.compat.jei.categories.DryingCategory;
import twilightforest.compat.jei.categories.JEIUncraftingCategory;
import twilightforest.compat.jei.categories.OminousFireCategory;
import twilightforest.compat.jei.categories.TransformationPowderCategory;
import twilightforest.compat.jei.extension.CasketRepairExtension;
import twilightforest.compat.jei.extension.ExanimateEssenceRepairExtension;
import twilightforest.compat.jei.extension.MoonwormQueenExtension;
import twilightforest.compat.jei.extension.NoTemplateSmithingExtension;
import twilightforest.compat.jei.extension.ScepterRepairExtension;
import twilightforest.compat.jei.extension.TravellersGearModifierExtension;
import twilightforest.compat.jei.extension.TravellersVestGlovesMergeExtension;
import twilightforest.compat.jei.renderers.EntityHelper;
import twilightforest.compat.jei.renderers.EntityRenderer;
import twilightforest.compat.jei.renderers.FakeItemEntityHelper;
import twilightforest.compat.jei.renderers.FakeItemEntityRenderer;
import twilightforest.compat.jei.subtype.CasketSubtypeInterpreter;
import twilightforest.compat.jei.util.CrumbleRecipe;
import twilightforest.compat.jei.util.GrindstoneTravellersRecipesGetter;
import twilightforest.compat.jei.util.OminousFireRecipe;
import twilightforest.compat.jei.util.TransformationRecipe;
import twilightforest.config.TFConfig;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.init.TFMenuTypes;
import twilightforest.init.TFRecipes;
import twilightforest.inventory.UncraftingMenu;
import twilightforest.item.recipe.CasketRepairRecipe;
import twilightforest.item.recipe.DryingRecipe;
import twilightforest.item.recipe.EssenceRepairRecipe;
import twilightforest.item.recipe.MoonwormQueenRepairRecipe;
import twilightforest.item.recipe.NoTemplateSmithingRecipe;
import twilightforest.item.recipe.ScepterRepairRecipe;
import twilightforest.item.recipe.travellers.TravellersGearModifierRecipe;
import twilightforest.item.recipe.travellers.TravellersVestGlovesMergeRecipe;

@JeiPlugin
public class JEICompat implements IModPlugin {
   public static final IIngredientType<FakeEntityType> ENTITY_TYPE = () -> FakeEntityType.class;
   public static final IIngredientType<FakeItemEntity> FAKE_ITEM_ENTITY = () -> FakeItemEntity.class;

   public static boolean isEmiInstalled() {
      return ModList.get().isLoaded("emi");
   }

   public ResourceLocation getPluginUid() {
      return ResourceLocation.fromNamespaceAndPath("twilightforest", "jei_plugin");
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
      if (!isEmiInstalled()) {
         if (!TFConfig.disableEntireTable) {
            registration.addRecipeCatalyst(new ItemStack((ItemLike)TFBlocks.UNCRAFTING_TABLE.get()), new RecipeType[]{RecipeTypes.CRAFTING});
            registration.addRecipeCatalyst(new ItemStack((ItemLike)TFBlocks.UNCRAFTING_TABLE.get()), new RecipeType[]{JEIUncraftingCategory.UNCRAFTING});
         }

         registration.addRecipeCatalyst(
            new ItemStack((ItemLike)TFItems.TRANSFORMATION_POWDER.get()), new RecipeType[]{TransformationPowderCategory.TRANSFORMATION}
         );
         registration.addRecipeCatalyst(new ItemStack((ItemLike)TFItems.EXANIMATE_ESSENCE.get()), new RecipeType[]{OminousFireCategory.OMINOUS_FIRE});
         registration.addRecipeCatalyst(new ItemStack((ItemLike)TFItems.CRUMBLE_HORN.get()), new RecipeType[]{CrumbleHornCategory.CRUMBLE_HORN});

         for (Holder<Block> block : BuiltInRegistries.BLOCK.getTagOrEmpty(BlockTagGenerator.DRYING_RACKS)) {
            registration.addRecipeCatalyst(new ItemStack((ItemLike)block.value()), new RecipeType[]{DryingCategory.DRYING});
         }
      }
   }

   public void registerItemSubtypes(ISubtypeRegistration registration) {
      if (!isEmiInstalled()) {
         registration.registerSubtypeInterpreter(TFItems.KEEPSAKE_CASKET.asItem(), CasketSubtypeInterpreter.INSTANCE);
      }
   }

   public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
      if (!isEmiInstalled()) {
         registration.addRecipeTransferHandler(UncraftingMenu.class, (MenuType)TFMenuTypes.UNCRAFTING.get(), RecipeTypes.CRAFTING, 11, 9, 20, 36);
      }
   }

   public void registerIngredients(IModIngredientRegistration registration) {
      if (!isEmiInstalled()) {
         registration.register(ENTITY_TYPE, Collections.emptyList(), new EntityHelper(), new EntityRenderer(16), FakeEntityType.CODEC);
         registration.register(FAKE_ITEM_ENTITY, Collections.emptyList(), new FakeItemEntityHelper(), new FakeItemEntityRenderer(16), FakeItemEntity.CODEC);
      }
   }

   public void registerCategories(IRecipeCategoryRegistration registration) {
      if (!isEmiInstalled()) {
         registration.addRecipeCategories(new IRecipeCategory[]{new JEIUncraftingCategory(registration.getJeiHelpers().getGuiHelper())});
         registration.addRecipeCategories(new IRecipeCategory[]{new TransformationPowderCategory(registration.getJeiHelpers().getGuiHelper())});
         registration.addRecipeCategories(new IRecipeCategory[]{new OminousFireCategory(registration.getJeiHelpers().getGuiHelper())});
         registration.addRecipeCategories(new IRecipeCategory[]{new CrumbleHornCategory(registration.getJeiHelpers().getGuiHelper())});
         registration.addRecipeCategories(new IRecipeCategory[]{new DryingCategory(registration.getJeiHelpers().getGuiHelper())});
         RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
      }
   }

   public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
      if (!isEmiInstalled()) {
         registration.getSmithingCategory().addExtension(NoTemplateSmithingRecipe.class, new NoTemplateSmithingExtension());
         registration.getCraftingCategory().addExtension(ScepterRepairRecipe.class, new ScepterRepairExtension());
         registration.getCraftingCategory().addExtension(TravellersGearModifierRecipe.class, new TravellersGearModifierExtension());
         registration.getCraftingCategory().addExtension(MoonwormQueenRepairRecipe.class, new MoonwormQueenExtension());
         registration.getCraftingCategory().addExtension(EssenceRepairRecipe.class, new ExanimateEssenceRepairExtension());
         registration.getCraftingCategory().addExtension(CasketRepairRecipe.class, new CasketRepairExtension());
         registration.getCraftingCategory().addExtension(TravellersVestGlovesMergeRecipe.class, new TravellersVestGlovesMergeExtension());
      }
   }

   public void registerRecipes(IRecipeRegistration registration) {
      if (!isEmiInstalled()) {
         RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
         if (!TFConfig.disableEntireTable) {
            List<RecipeHolder<? extends CraftingRecipe>> recipes = RecipeViewerConstants.getAllUncraftingRecipes(manager);
            registration.addRecipes(JEIUncraftingCategory.UNCRAFTING, recipes.stream().map(RecipeHolder::value).toList());
         }

         registration.addRecipes(
            TransformationPowderCategory.TRANSFORMATION,
            RecipeViewerConstants.getTransformationPowderRecipes()
               .stream()
               .map(info -> new TransformationRecipe(new FakeEntityType(info.input()), new FakeEntityType(info.output()), info.reversible()))
               .toList()
         );
         registration.addRecipes(
            OminousFireCategory.OMINOUS_FIRE,
            RecipeViewerConstants.getOminousFireRecipes()
               .stream()
               .map(info -> new OminousFireRecipe(new FakeEntityType(info.input()), new FakeEntityType(info.output())))
               .toList()
         );
         registration.addRecipes(
            CrumbleHornCategory.CRUMBLE_HORN,
            RecipeViewerConstants.getCrumbleHornRecipes().stream().map(info -> new CrumbleRecipe((Block)info.getFirst(), (Block)info.getSecond())).toList()
         );
         registration.addRecipes(
            DryingCategory.DRYING,
            manager.getAllRecipesFor((net.minecraft.world.item.crafting.RecipeType)TFRecipes.DRYING_RECIPE.get())
               .stream()
               .filter(holder -> !((DryingRecipe)holder.value()).getResult().is(TFItems.STALE_BREAD))
               .map(RecipeHolder::value)
               .toList()
         );
         registration.addRecipes(RecipeTypes.GRINDSTONE, GrindstoneTravellersRecipesGetter.getRecipes());
      }
   }

   public void registerGuiHandlers(IGuiHandlerRegistration registration) {
      if (!isEmiInstalled()) {
         registration.addRecipeClickArea(UncraftingScreen.class, 34, 33, 27, 20, new RecipeType[]{JEIUncraftingCategory.UNCRAFTING});
         registration.addRecipeClickArea(UncraftingScreen.class, 115, 33, 27, 20, new RecipeType[]{RecipeTypes.CRAFTING});
      }
   }

   public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
      jeiRuntime.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, List.of(TFItems.MAGIC_PAINTING.toStack()));
   }
}
