package nadiendev.constructionwand.data;

import java.util.concurrent.CompletableFuture;
import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.crafting.ModRecipes;
import nadiendev.constructionwand.crafting.RecipeWandUpgrade;
import nadiendev.constructionwand.items.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags.Items;

public class RecipeGenerator extends RecipeProvider {
   public RecipeGenerator(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
      super(packOutput, lookupProvider);
   }

   protected void buildRecipes(RecipeOutput output, Provider provider) {
      this.wandRecipe(output, (ItemLike)ModItems.WAND_STONE.get(), Inp.fromTag(ItemTags.STONE_TOOL_MATERIALS));
      this.wandRecipe(output, (ItemLike)ModItems.WAND_IRON.get(), Inp.fromTag(Items.INGOTS_IRON));
      this.wandRecipe(output, (ItemLike)ModItems.WAND_DIAMOND.get(), Inp.fromTag(Items.GEMS_DIAMOND));
      this.wandRecipe(output, (ItemLike)ModItems.WAND_NETHERITE.get(), Inp.fromTag(Items.INGOTS_NETHERITE));
      this.wandRecipe(output, (ItemLike)ModItems.WAND_INFINITY.get(), Inp.fromTag(Items.NETHER_STARS));
      this.coreRecipe(output, (ItemLike)ModItems.CORE_ANGEL.get(), Inp.fromTag(Items.FEATHERS), Inp.fromTag(Items.INGOTS_GOLD));
      this.coreRecipe(
         output,
         (ItemLike)ModItems.CORE_DESTRUCTION.get(),
         Inp.fromTag(Items.STORAGE_BLOCKS_DIAMOND),
         Inp.fromItem(net.minecraft.world.item.Items.DIAMOND_PICKAXE)
      );
      this.voidSackRecipe(output);
      this.specialRecipe(output, ModRecipes.WAND_UPGRADE.get());
   }

   private void wandRecipe(RecipeOutput output, ItemLike wand, Inp material) {
      ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, wand)
         .define('X', material.ingredient)
         .define('#', Items.RODS_WOODEN)
         .pattern("  X")
         .pattern(" # ")
         .pattern("#  ")
         .unlockedBy("has_item", inventoryTrigger(new ItemPredicate[]{material.predicate}))
         .save(output);
   }

   private void coreRecipe(RecipeOutput output, ItemLike core, Inp item1, Inp item2) {
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, core)
         .define('O', item1.ingredient)
         .define('X', item2.ingredient)
         .define('#', Items.GLASS_PANES)
         .pattern(" #X")
         .pattern("#O#")
         .pattern("X# ")
         .unlockedBy("has_item", inventoryTrigger(new ItemPredicate[]{item1.predicate}))
         .save(output);
   }

   private void voidSackRecipe(RecipeOutput output) {
      Inp coreDestruction = Inp.fromItem((ItemLike)ModItems.CORE_DESTRUCTION.get());
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike)ModItems.VOID_SACK.get())
         .define('C', coreDestruction.ingredient)
         .define('E', net.minecraft.world.item.Items.DIAMOND)
         .define('O', net.minecraft.world.item.Items.NETHERITE_INGOT)
         .define('D', net.minecraft.world.item.Items.EMERALD)
         .pattern("EDE")
         .pattern("OCO")
         .pattern("EDE")
         .unlockedBy("has_core_destruction", inventoryTrigger(new ItemPredicate[]{coreDestruction.predicate}))
         .save(output);
   }

   private void specialRecipe(RecipeOutput output, SimpleCraftingRecipeSerializer<?> serializer) {
      ResourceLocation name = BuiltInRegistries.RECIPE_SERIALIZER.getKey(serializer);
      SpecialRecipeBuilder.special(RecipeWandUpgrade::new).save(output, ConstructionWand.loc("dynamic/" + name.getPath()).toString());
   }
}
