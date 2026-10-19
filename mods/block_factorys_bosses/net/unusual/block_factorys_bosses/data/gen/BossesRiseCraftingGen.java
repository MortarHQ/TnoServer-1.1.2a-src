package net.unusual.block_factorys_bosses.data.gen;

import java.util.concurrent.CompletableFuture;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags.Items;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.data.util.BossesRiseCraftingProvider;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseCraftingGen extends BossesRiseCraftingProvider {
   public BossesRiseCraftingGen(PackOutput output, CompletableFuture<Provider> registries) {
      super(output, registries);
   }

   @Override
   protected void buildRecipes(RecipeOutput output, Provider registries) {
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseBlocks.SHIP_LANTERN, 1)
         .pattern("OOO")
         .pattern(".#.")
         .pattern("...")
         .define('O', Ingredient.of(Items.INGOTS_IRON))
         .define('.', Ingredient.of(Items.NUGGETS_IRON))
         .define('#', Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.BLAZE_POWDER}))
         .unlockedBy("has_item", has(net.minecraft.world.item.Items.BLAZE_POWDER))
         .save(output, BossesRise.prefix("lighting/ship_lantern"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseBlocks.TALL_CANDLES_CROSS, 1)
         .pattern("OOO")
         .pattern("O#O")
         .pattern(".#.")
         .define('O', Ingredient.of(ItemTags.CANDLES))
         .define('.', Ingredient.of(Items.NUGGETS_GOLD))
         .define('#', Ingredient.of(Items.INGOTS_GOLD))
         .unlockedBy("has_item", has(Items.INGOTS_GOLD))
         .save(output, BossesRise.prefix("lighting/candles_extra"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseBlocks.CANDLES, 1)
         .pattern("OOO")
         .pattern(".#.")
         .define('O', Ingredient.of(ItemTags.CANDLES))
         .define('.', Ingredient.of(Items.NUGGETS_GOLD))
         .define('#', Ingredient.of(Items.INGOTS_GOLD))
         .unlockedBy("has_item", has(Items.INGOTS_GOLD))
         .save(output, BossesRise.prefix("lighting/small_candles"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseBlocks.UNDER_WORLD_TALL_CANDLE_CROSS, 1)
         .pattern("OOO")
         .pattern("O#O")
         .pattern(".#.")
         .define('O', Ingredient.of(ItemTags.CANDLES))
         .define('.', Ingredient.of(ItemTags.SOUL_FIRE_BASE_BLOCKS))
         .define('#', Ingredient.of(Items.INGOTS_GOLD))
         .unlockedBy("has_item", has(ItemTags.SOUL_FIRE_BASE_BLOCKS))
         .save(output, BossesRise.prefix("lighting/underworld_candles_extra"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseBlocks.UNDERWOLD_CANDLES, 1)
         .pattern("OOO")
         .pattern(".#.")
         .define('O', Ingredient.of(ItemTags.CANDLES))
         .define('.', Ingredient.of(ItemTags.SOUL_FIRE_BASE_BLOCKS))
         .define('#', Ingredient.of(Items.INGOTS_GOLD))
         .unlockedBy("has_item", has(ItemTags.SOUL_FIRE_BASE_BLOCKS))
         .save(output, BossesRise.prefix("lighting/underworld_small_candles"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseBlocks.UNDERWOLD_WALL_TORCH, 1)
         .pattern(" O ")
         .pattern(".#.")
         .pattern(" I.")
         .define('O', Ingredient.of(ItemTags.COALS))
         .define('.', Ingredient.of(ItemTags.SOUL_FIRE_BASE_BLOCKS))
         .define('#', Ingredient.of(Items.INGOTS_IRON))
         .define('I', Ingredient.of(Items.RODS_WOODEN))
         .unlockedBy("has_item", has(ItemTags.SOUL_FIRE_BASE_BLOCKS))
         .save(output, BossesRise.prefix("lighting/underworld_wall_torch"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseBlocks.WALL_TORCH, 1)
         .pattern(" O ")
         .pattern(".#.")
         .pattern(" I.")
         .define('O', Ingredient.of(ItemTags.COALS))
         .define('.', Ingredient.of(Items.NUGGETS_IRON))
         .define('#', Ingredient.of(Items.INGOTS_IRON))
         .define('I', Ingredient.of(Items.RODS_WOODEN))
         .unlockedBy("has_item", has(Items.NUGGETS_IRON))
         .save(output, BossesRise.prefix("lighting/wall_torch"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseBlocks.PLANK, 12)
         .pattern("  O")
         .pattern(" O ")
         .pattern("O  ")
         .define('O', Ingredient.of(ItemTags.PLANKS))
         .unlockedBy("has_item", has(ItemTags.PLANKS))
         .save(output, BossesRise.prefix("plank/plank"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.BIG_OAK_PLANKS, 1)
         .pattern("OO")
         .pattern("OO")
         .define('O', Ingredient.of(new ItemLike[]{BossesRiseBlocks.PLANK}))
         .unlockedBy("has_item", has(BossesRiseBlocks.PLANK))
         .save(output, BossesRise.prefix("plank/plank_block"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.BIG_OAK_PLANKS_WET, 8)
         .pattern("OOO")
         .pattern("OVO")
         .pattern("OOO")
         .define('O', Ingredient.of(new ItemLike[]{BossesRiseItems.BIG_OAK_PLANKS}))
         .define('V', Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.WATER_BUCKET}))
         .unlockedBy("has_item", has(BossesRiseItems.BIG_OAK_PLANKS))
         .save(output, BossesRise.prefix("plank/wet_plank_block"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.BIG_OAK_PLANKS_WET_BROKEN, 8)
         .pattern("OOO")
         .pattern("OVO")
         .pattern("OOO")
         .define('O', Ingredient.of(new ItemLike[]{BossesRiseItems.BIG_OAK_PLANKS_BROKEN}))
         .define('V', Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.WATER_BUCKET}))
         .unlockedBy("has_item", has(BossesRiseItems.BIG_OAK_PLANKS_BROKEN))
         .save(output, BossesRise.prefix("plank/wet_plank_block_broken"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.BIG_OAK_PLANKS_WET_CRACKED, 8)
         .pattern("OOO")
         .pattern("OVO")
         .pattern("OOO")
         .define('O', Ingredient.of(new ItemLike[]{BossesRiseItems.BIG_OAK_PLANKS_CRACKED}))
         .define('V', Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.WATER_BUCKET}))
         .unlockedBy("has_item", has(BossesRiseItems.BIG_OAK_PLANKS_CRACKED))
         .save(output, BossesRise.prefix("plank/wet_plank_block_cracked"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.BIG_OAK_PLANKS_WET_STRAIGHT, 8)
         .pattern("OOO")
         .pattern("OVO")
         .pattern("OOO")
         .define('O', Ingredient.of(new ItemLike[]{BossesRiseItems.BIG_OAK_PLANKS_STRAIGHT}))
         .define('V', Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.WATER_BUCKET}))
         .unlockedBy("has_item", has(BossesRiseItems.BIG_OAK_PLANKS_STRAIGHT))
         .save(output, BossesRise.prefix("plank/wet_plank_block_straight"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.BIG_OAK_PLANKS_WET_VARIATION, 8)
         .pattern("OOO")
         .pattern("OVO")
         .pattern("OOO")
         .define('O', Ingredient.of(new ItemLike[]{BossesRiseItems.BIG_OAK_PLANKS_VARIATION}))
         .define('V', Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.WATER_BUCKET}))
         .unlockedBy("has_item", has(BossesRiseItems.BIG_OAK_PLANKS_VARIATION))
         .save(output, BossesRise.prefix("plank/wet_plank_block_variation"));
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BossesRiseBlocks.CANNONBALL, 1)
         .pattern(" O ")
         .pattern("OOO")
         .pattern(" O ")
         .define('O', Ingredient.of(Items.INGOTS_IRON))
         .unlockedBy("has_item", has(Items.INGOTS_IRON))
         .save(output, BossesRise.prefix("cannonball"));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.COIN_PILE, 1)
         .requires(Ingredient.of(Items.NUGGETS_GOLD), 8)
         .unlockedBy("has_item", has(Items.NUGGETS_GOLD))
         .save(output, BossesRise.prefix("coin_pile"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseBlocks.NET, 3)
         .pattern("O O")
         .pattern(" O ")
         .pattern("O O")
         .define('O', Ingredient.of(Items.STRINGS))
         .unlockedBy("has_item", has(Items.STRINGS))
         .save(output, BossesRise.prefix("net"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseBlocks.ROPE_ROLL, 1)
         .pattern("OOO")
         .pattern("O O")
         .pattern("OOO")
         .define('O', Ingredient.of(Items.STRINGS))
         .unlockedBy("has_item", has(Items.STRINGS))
         .save(output, BossesRise.prefix("rope_roll"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseBlocks.GUARDRAIL, 3)
         .pattern("SSS")
         .pattern(" O ")
         .define('O', Ingredient.of(Items.RODS_WOODEN))
         .define('S', Ingredient.of(ItemTags.PLANKS))
         .unlockedBy("has_item", has(ItemTags.PLANKS))
         .save(output, BossesRise.prefix("ship_rail"));
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, this.enchantedStack(BossesRiseItems.KRAKEN_TRIDENT, Enchantments.LOYALTY, 1, registries))
         .pattern(" aa")
         .pattern(" /a")
         .pattern("/  ")
         .define('a', Ingredient.of(new ItemLike[]{BossesRiseItems.KRAKEN_TOOTH}))
         .define('/', Ingredient.of(Items.RODS_WOODEN))
         .unlockedBy("has_item", has(BossesRiseItems.KRAKEN_TOOTH))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BossesRiseItems.UNDYING_TENTACLE)
         .pattern("  a")
         .pattern(" a ")
         .pattern("a  ")
         .define('a', Ingredient.of(new ItemLike[]{BossesRiseItems.KRAKEN_TOOTH}))
         .unlockedBy("has_item", has(BossesRiseItems.KRAKEN_TOOTH))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BossesRiseBlocks.BIG_CHAIN, 2)
         .pattern("a")
         .pattern("b")
         .pattern("a")
         .define('a', Ingredient.of(Items.INGOTS_IRON))
         .define('b', Ingredient.of(Items.NUGGETS_IRON))
         .unlockedBy("has_item", has(Items.INGOTS_IRON))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BossesRiseItems.DRAGON_BONES_CHESTPLATE, 1)
         .pattern("a a")
         .pattern("aaa")
         .pattern("aaa")
         .define('a', Ingredient.of(new ItemLike[]{BossesRiseItems.DRAGON_BONE}))
         .unlockedBy("has_item", has(BossesRiseItems.DRAGON_BONE))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BossesRiseItems.DRAGON_BONES_LEGGINGS, 1)
         .pattern("aaa")
         .pattern("a a")
         .pattern("a a")
         .define('a', Ingredient.of(new ItemLike[]{BossesRiseItems.DRAGON_BONE}))
         .unlockedBy("has_item", has(BossesRiseItems.DRAGON_BONE))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BossesRiseItems.DRAGON_BONES_BOOTS, 1)
         .pattern("a a")
         .pattern("a a")
         .define('a', Ingredient.of(new ItemLike[]{BossesRiseItems.DRAGON_BONE}))
         .unlockedBy("has_item", has(BossesRiseItems.DRAGON_BONE))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseBlocks.PRISON_DOOR, 1)
         .pattern("aa")
         .pattern("ab")
         .pattern("aa")
         .define('a', Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.IRON_BARS}))
         .define('b', Ingredient.of(Items.NUGGETS_IRON))
         .unlockedBy("has_item", has(net.minecraft.world.item.Items.IRON_BARS))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BossesRiseBlocks.RUSTY_PRISON_DOOR, 1)
         .pattern("aa")
         .pattern("ab")
         .pattern("aa")
         .define('a', Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.IRON_BARS}))
         .define('b', Ingredient.of(Items.NUGGETS_GOLD))
         .unlockedBy("has_item", has(net.minecraft.world.item.Items.IRON_BARS))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.TALL_CANDLES, 1)
         .requires(Ingredient.of(new ItemLike[]{BossesRiseItems.TALL_CANDLES_CROSS}))
         .unlockedBy("has_item", has(BossesRiseItems.TALL_CANDLES_CROSS))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.UNDERWORLD_TALL_CANDLES, 1)
         .requires(Ingredient.of(new ItemLike[]{BossesRiseItems.UNDER_WORLD_TALL_CANDLE_CROSS}))
         .unlockedBy("has_item", has(BossesRiseItems.UNDER_WORLD_TALL_CANDLE_CROSS))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, net.minecraft.world.item.Items.BONE, 3)
         .requires(Ingredient.of(BossesRiseTags.Items.BONE_REMAINS))
         .unlockedBy("has_item", has(BossesRiseTags.Items.BONE_REMAINS))
         .save(output, BossesRise.prefix("remains_to_bones"));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, net.minecraft.world.item.Items.BONE, 5)
         .requires(Ingredient.of(new ItemLike[]{BossesRiseItems.CORPSE}))
         .unlockedBy("has_item", has(BossesRiseItems.CORPSE))
         .save(output, BossesRise.prefix("corpse_to_bones"));
      SingleItemRecipeBuilder.stonecutting(
            Ingredient.of(new ItemLike[]{BossesRiseItems.BIG_OAK_PLANKS}), RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.BIG_OAK_PLANKS_BROKEN
         )
         .unlockedBy("has_item", has(BossesRiseItems.BIG_OAK_PLANKS))
         .save(output, BossesRise.prefix("plank/sawmill/oak_planks_broken"));
      SingleItemRecipeBuilder.stonecutting(
            Ingredient.of(new ItemLike[]{BossesRiseItems.BIG_OAK_PLANKS}), RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.BIG_OAK_PLANKS_CRACKED
         )
         .unlockedBy("has_item", has(BossesRiseItems.BIG_OAK_PLANKS))
         .save(output, BossesRise.prefix("plank/sawmill/oak_planks_cracked"));
      SingleItemRecipeBuilder.stonecutting(
            Ingredient.of(new ItemLike[]{BossesRiseItems.BIG_OAK_PLANKS}), RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.BIG_OAK_PLANKS_STRAIGHT
         )
         .unlockedBy("has_item", has(BossesRiseItems.BIG_OAK_PLANKS))
         .save(output, BossesRise.prefix("plank/sawmill/oak_planks_straight"));
      SingleItemRecipeBuilder.stonecutting(
            Ingredient.of(new ItemLike[]{BossesRiseItems.BIG_OAK_PLANKS}), RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.BIG_OAK_PLANKS_VARIATION
         )
         .unlockedBy("has_item", has(BossesRiseItems.BIG_OAK_PLANKS))
         .save(output, BossesRise.prefix("plank/sawmill/oak_planks_variation"));
      SingleItemRecipeBuilder.stonecutting(
            Ingredient.of(new ItemLike[]{BossesRiseItems.BIG_OAK_PLANKS_WET}), RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.BIG_OAK_PLANKS_WET_BROKEN
         )
         .unlockedBy("has_item", has(BossesRiseItems.BIG_OAK_PLANKS_WET))
         .save(output, BossesRise.prefix("plank/sawmill/wet_oak_planks_broken"));
      SingleItemRecipeBuilder.stonecutting(
            Ingredient.of(new ItemLike[]{BossesRiseItems.BIG_OAK_PLANKS_WET}), RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.BIG_OAK_PLANKS_WET_CRACKED
         )
         .unlockedBy("has_item", has(BossesRiseItems.BIG_OAK_PLANKS_WET))
         .save(output, BossesRise.prefix("plank/sawmill/wet_oak_planks_cracked"));
      SingleItemRecipeBuilder.stonecutting(
            Ingredient.of(new ItemLike[]{BossesRiseItems.BIG_OAK_PLANKS_WET}), RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.BIG_OAK_PLANKS_WET_STRAIGHT
         )
         .unlockedBy("has_item", has(BossesRiseItems.BIG_OAK_PLANKS_WET))
         .save(output, BossesRise.prefix("plank/sawmill/wet_oak_planks_straight"));
      SingleItemRecipeBuilder.stonecutting(
            Ingredient.of(new ItemLike[]{BossesRiseItems.BIG_OAK_PLANKS_WET}), RecipeCategory.BUILDING_BLOCKS, BossesRiseItems.BIG_OAK_PLANKS_WET_VARIATION
         )
         .unlockedBy("has_item", has(BossesRiseItems.BIG_OAK_PLANKS_WET))
         .save(output, BossesRise.prefix("plank/sawmill/wet_oak_planks_variation"));
   }
}
