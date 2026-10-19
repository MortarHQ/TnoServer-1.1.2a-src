package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModItems;
import fuzs.puzzleslib.api.data.v2.AbstractRecipeProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class ModRecipeProvider extends AbstractRecipeProvider {
   public ModRecipeProvider(DataProviderContext context) {
      super(context);
   }

   public void addRecipes(RecipeOutput recipeOutput) {
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike)ModItems.HALLOWED_GEM_ITEM.value())
         .define('#', Items.AMETHYST_SHARD)
         .define('B', (ItemLike)ModItems.UNUSUAL_DUST_ITEM.value())
         .define('R', (ItemLike)ModItems.ILLUSIONARY_DUST_ITEM.value())
         .define('D', Items.DIAMOND)
         .pattern("#B#")
         .pattern("RDR")
         .pattern("#B#")
         .unlockedBy(
            getHasName(new ItemLike[]{(ItemLike)ModItems.UNUSUAL_DUST_ITEM.value(), (ItemLike)ModItems.ILLUSIONARY_DUST_ITEM.value()}),
            has(new ItemLike[]{(ItemLike)ModItems.UNUSUAL_DUST_ITEM.value(), (ItemLike)ModItems.ILLUSIONARY_DUST_ITEM.value()})
         )
         .save(recipeOutput);
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)ModItems.IMBUING_TABLE_ITEM.value())
         .define('#', Items.COPPER_BLOCK)
         .define('P', Items.PAPER)
         .define('O', Items.DARK_OAK_LOG)
         .define('S', (ItemLike)ModItems.PRIMAL_ESSENCE_ITEM.value())
         .define('E', Items.EXPERIENCE_BOTTLE)
         .pattern("#P#")
         .pattern("OSO")
         .pattern("#E#")
         .unlockedBy(getHasName((ItemLike)ModItems.PRIMAL_ESSENCE_ITEM.value()), has((ItemLike)ModItems.PRIMAL_ESSENCE_ITEM.value()))
         .save(recipeOutput);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)ModItems.PLATINUM_SHEET_ITEM.value())
         .requires((ItemLike)ModItems.PLATINUM_CHUNK_ITEM.value(), 4)
         .unlockedBy(getHasName((ItemLike)ModItems.PLATINUM_CHUNK_ITEM.value()), has((ItemLike)ModItems.PLATINUM_CHUNK_ITEM.value()))
         .save(recipeOutput);
      ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)ModItems.HORN_OF_SIGHT_ITEM.value())
         .define('#', Items.GOLD_INGOT)
         .define('H', Items.GOAT_HORN)
         .define('G', (ItemLike)ModItems.HALLOWED_GEM_ITEM.value())
         .pattern(" G ")
         .pattern("#H#")
         .pattern(" # ")
         .unlockedBy(getHasName((ItemLike)ModItems.HALLOWED_GEM_ITEM.value()), has((ItemLike)ModItems.HALLOWED_GEM_ITEM.value()))
         .save(recipeOutput);
   }
}
