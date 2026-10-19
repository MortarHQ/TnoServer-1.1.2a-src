package twilightforest.data;

import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.item.crafting.AbstractCookingRecipe.Factory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags.Items;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.crafting.CompoundIngredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.common.crafting.DifferenceIngredient;
import org.jetbrains.annotations.NotNull;
import twilightforest.TwilightForestMod;
import twilightforest.data.custom.CartesianShapedRecipeBuilder;
import twilightforest.data.custom.CartesianShapelessRecipeBuilder;
import twilightforest.data.custom.DryingRecipeBuilder;
import twilightforest.data.custom.NoSmithingTemplateRecipeBuilder;
import twilightforest.data.custom.ScepterRecipeBuilder;
import twilightforest.data.custom.TravellersGearComponentModifierBuilder;
import twilightforest.data.custom.UncraftingGenerator;
import twilightforest.data.helpers.CraftingDataHelper;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFItems;
import twilightforest.init.custom.TravellersModifiersManager;
import twilightforest.item.recipe.CasketRepairRecipe;
import twilightforest.item.recipe.EmperorsClothRecipe;
import twilightforest.item.recipe.EssenceRepairRecipe;
import twilightforest.item.recipe.MagicMapCloningRecipe;
import twilightforest.item.recipe.MazeMapCloningRecipe;
import twilightforest.item.recipe.MoonwormQueenRepairRecipe;
import twilightforest.item.recipe.UncraftingTableCondition;
import twilightforest.item.recipe.travellers.TravellersVestGlovesMergeRecipe;

public class CraftingGenerator extends CraftingDataHelper {
   private static final String[] CORAL_SPECIES = new String[]{"tube", "brain", "bubble", "fire", "horn"};
   private static final String[] CORAL_TYPES = new String[]{"", "_block", "_fan"};
   private final Provider provider;

   public CraftingGenerator(PackOutput output, CompletableFuture<Provider> provider) {
      super(output, provider);
      this.provider = provider.join();
   }

   protected void buildRecipes(RecipeOutput output) {
      StonecuttingGenerator.buildRecipes(output);
      UncraftingGenerator.buildRecipes(output);
      this.blockCompressionRecipes(output);
      this.equipmentRecipes(this.provider, output);
      this.emptyMapRecipes(output);
      this.woodRecipes(output);
      this.fieryConversions(output);
      this.nagastoneRecipes(output);
      this.darkTowerRecipes(output);
      this.castleRecipes(output);
      this.bannerPattern(output, "naga_banner_pattern", TFBlocks.NAGA_TROPHY, TFItems.NAGA_BANNER_PATTERN);
      this.bannerPattern(output, "lich_banner_pattern", TFBlocks.LICH_TROPHY, TFItems.LICH_BANNER_PATTERN);
      this.bannerPattern(output, "minoshroom_banner_pattern", TFBlocks.MINOSHROOM_TROPHY, TFItems.MINOSHROOM_BANNER_PATTERN);
      this.bannerPattern(output, "hydra_banner_pattern", TFBlocks.HYDRA_TROPHY, TFItems.HYDRA_BANNER_PATTERN);
      this.bannerPattern(output, "knight_phantom_banner_pattern", TFBlocks.KNIGHT_PHANTOM_TROPHY, TFItems.KNIGHT_PHANTOM_BANNER_PATTERN);
      this.bannerPattern(output, "ur_ghast_banner_pattern", TFBlocks.UR_GHAST_TROPHY, TFItems.UR_GHAST_BANNER_PATTERN);
      this.bannerPattern(output, "alpha_yeti_banner_pattern", TFBlocks.ALPHA_YETI_TROPHY, TFItems.ALPHA_YETI_BANNER_PATTERN);
      this.bannerPattern(output, "snow_queen_banner_pattern", TFBlocks.SNOW_QUEEN_TROPHY, TFItems.SNOW_QUEEN_BANNER_PATTERN);
      this.slabBlock(output, "aurora_slab", TFBlocks.AURORA_SLAB, TFBlocks.AURORA_BLOCK);
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)TFBlocks.AURORA_PILLAR.get(), 2)
         .pattern("#")
         .pattern("#")
         .define('#', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.AURORA_BLOCK.get()}))
         .unlockedBy("has_slab", has((ItemLike)TFBlocks.AURORA_SLAB.get()))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)TFBlocks.IRON_LADDER.get(), 3)
         .pattern("-#-")
         .pattern("-#-")
         .define('#', Ingredient.of(new ItemLike[]{Blocks.IRON_BARS}))
         .define('-', Items.NUGGETS_IRON)
         .unlockedBy("has_iron_bars", has(Blocks.IRON_BARS))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)TFBlocks.WROUGHT_IRON_FENCE.get(), 3)
         .pattern("###")
         .pattern("###")
         .define('#', ItemTagGenerator.WROUGHT_IRON_INGOTS)
         .unlockedBy("has_wrought_iron", has(ItemTagGenerator.WROUGHT_IRON_INGOTS))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)TFBlocks.CANDELABRA.get(), 2)
         .pattern("###")
         .pattern(" # ")
         .define('#', ItemTagGenerator.WROUGHT_IRON_INGOTS)
         .unlockedBy("has_wrought_iron", has(ItemTagGenerator.WROUGHT_IRON_INGOTS))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)TFBlocks.ROPE.get(), 8)
         .pattern("#")
         .pattern("#")
         .pattern("#")
         .define('#', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.ROOT_STRAND.get()}))
         .unlockedBy("has_root_strand", has((ItemLike)TFBlocks.ROOT_STRAND.get()))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)TFBlocks.CANOPY_WINDOW.get(), 4)
         .pattern("GPG")
         .pattern("PPP")
         .pattern("GPG")
         .define('G', Ingredient.of(Items.GLASS_BLOCKS))
         .define('P', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.CANOPY_PLANKS.value()}))
         .unlockedBy("has_planks", has((ItemLike)TFBlocks.CANOPY_PLANKS.value()))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)TFBlocks.CANOPY_WINDOW_PANE.get(), 16)
         .pattern("GGG")
         .pattern("GGG")
         .define('G', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.CANOPY_WINDOW.value()}))
         .unlockedBy("has_windows", has((ItemLike)TFBlocks.CANOPY_WINDOW.value()))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)TFItems.MASON_JAR.get(), 4)
         .pattern("GLG")
         .pattern("G G")
         .pattern("GGG")
         .define('G', Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.GLASS}))
         .define('L', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.TWILIGHT_OAK_LOG.get()}))
         .unlockedBy("has_tf_oak", has((ItemLike)TFBlocks.TWILIGHT_OAK_LOG.value()))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, (ItemLike)TFBlocks.FIREFLY_JAR.get())
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.FIREFLY.get()}))
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFItems.MASON_JAR.get()}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.FIREFLY.get()))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, (ItemLike)TFBlocks.FIREFLY_SPAWNER.get())
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.FIREFLY_JAR.get()}))
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.FIREFLY.get()}))
         .requires(Ingredient.of(new ItemLike[]{Blocks.POPPY}))
         .unlockedBy("has_jar", has((ItemLike)TFBlocks.FIREFLY_JAR.get()))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, (ItemLike)TFBlocks.CICADA_JAR.get())
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.CICADA.get()}))
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFItems.MASON_JAR.get()}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.CICADA.get()))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, net.minecraft.world.item.Items.MAGENTA_DYE)
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.HUGE_WATER_LILY.get()}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.HUGE_WATER_LILY.get()))
         .save(output, TwilightForestMod.prefix("waterlily_to_magenta"));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, net.minecraft.world.item.Items.RED_DYE)
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.THORN_ROSE.get()}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.THORN_ROSE.get()))
         .save(output, TwilightForestMod.prefix("thorn_rose_to_red"));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, net.minecraft.world.item.Items.STICK)
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.ROOT_STRAND.get()}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.ROOT_STRAND.get()))
         .group("sticks")
         .save(output, TwilightForestMod.prefix("root_stick"));
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.TORCH, 5)
         .pattern("∴")
         .pattern("|")
         .define('∴', Ingredient.of(new ItemLike[]{(ItemLike)TFItems.TORCHBERRIES.get()}))
         .define('|', Items.RODS_WOODEN)
         .unlockedBy("has_item", has((ItemLike)TFItems.TORCHBERRIES.get()))
         .save(output, TwilightForestMod.prefix("berry_torch"));
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)TFBlocks.UNCRAFTING_TABLE.get())
         .pattern("###")
         .pattern("#X#")
         .pattern("###")
         .define('#', Blocks.CRAFTING_TABLE)
         .define('X', (ItemLike)TFItems.MAZE_MAP_FOCUS.get())
         .unlockedBy("has_uncrafting_table", has((ItemLike)TFBlocks.UNCRAFTING_TABLE.get()))
         .save(output.withConditions(new ICondition[]{UncraftingTableCondition.INSTANCE}), TwilightForestMod.prefix("uncrafting_table"));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TFItems.MOSS_SOUP)
         .requires(TFBlocks.MOSS_PATCH)
         .requires(net.minecraft.world.item.Items.BOWL)
         .requires(Ingredient.of(new ItemStack[]{PotionContents.createItemStack(net.minecraft.world.item.Items.POTION, Potions.WATER)}))
         .unlockedBy("has_moss", has(TFBlocks.MOSS_PATCH))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TFItems.BERRY_MEDLEY)
         .requires(net.minecraft.world.item.Items.BOWL)
         .requires(TFItems.RASPBERRY)
         .requires(TFItems.BLUEBERRY)
         .requires(TFItems.BLACKBERRY)
         .requires(TFItems.MALOBERRY)
         .unlockedBy("has_raspberry", has(TFItems.RASPBERRY))
         .unlockedBy("has_blueberry", has(TFItems.BLUEBERRY))
         .unlockedBy("has_blackberry", has(TFItems.BLACKBERRY))
         .unlockedBy("has_maloberry", has(TFItems.MALOBERRY))
         .save(output);
      this.cookingRecipes(output, "smelted", RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, 200);
      this.cookingRecipes(output, "smoked", RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, 100);
      this.cookingRecipes(output, "campfired", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 600);
      this.ingotRecipes(output, "smelted", RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, 200);
      this.ingotRecipes(output, "blasted", RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, 100);
      this.oreberryRecipes(output, "smelted", RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, 200);
      this.oreberryRecipes(output, "blasted", RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, 100);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, net.minecraft.world.item.Items.COPPER_INGOT)
         .requires(Ingredient.of(ItemTagGenerator.COPPER_NUGGETS), 9)
         .unlockedBy("has_item", has(ItemTagGenerator.COPPER_NUGGETS))
         .save(output, TwilightForestMod.prefix("copper_nuggets_to_ingot"));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TFItems.COPPER_NUGGET, 9)
         .requires(Ingredient.of(Items.INGOTS_COPPER))
         .unlockedBy("has_item", has(Items.INGOTS_COPPER))
         .save(output, TwilightForestMod.prefix("copper_ingot_to_nuggets"));
      this.crackedWoodRecipes(output);
      this.crackedStoneRecipes(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)TFBlocks.CANOPY_BOOKSHELF.get())
         .pattern("---")
         .pattern("B B")
         .pattern("---")
         .define('-', (ItemLike)TFBlocks.CANOPY_PLANKS.get())
         .define('B', net.minecraft.world.item.Items.BOOK)
         .unlockedBy("has_item", has((ItemLike)TFBlocks.CANOPY_PLANKS.get()))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)TFItems.ARMOR_SHARD_CLUSTER.get())
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFItems.ARMOR_SHARD.get()}), 9)
         .unlockedBy("has_item", has((ItemLike)TFItems.ARMOR_SHARD.get()))
         .save(output, TwilightForestMod.prefix("material/" + TFItems.ARMOR_SHARD_CLUSTER.getId().getPath()));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)TFBlocks.MOSSY_UNDERBRICK.get(), 1)
         .requires(Ingredient.of(new ItemLike[]{Blocks.VINE, Blocks.MOSS_BLOCK}))
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.UNDERBRICK.get()}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.UNDERBRICK.get()))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)TFBlocks.MOSSY_MAZESTONE.get(), 1)
         .requires(Ingredient.of(new ItemLike[]{Blocks.VINE, Blocks.MOSS_BLOCK}))
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.MAZESTONE_BRICK.get()}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.MAZESTONE_BRICK.get()))
         .save(output, TwilightForestMod.prefix("maze_stone/mossy_mazestone"));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)TFItems.CARMINITE.get())
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFItems.BORER_ESSENCE.get()}))
         .requires(Items.DUSTS_REDSTONE)
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFItems.BORER_ESSENCE.get()}))
         .requires(Items.DUSTS_REDSTONE)
         .requires(Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.GHAST_TEAR}))
         .requires(Items.DUSTS_REDSTONE)
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFItems.BORER_ESSENCE.get()}))
         .requires(Items.DUSTS_REDSTONE)
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFItems.BORER_ESSENCE.get()}))
         .unlockedBy("has_item", has((ItemLike)TFItems.BORER_ESSENCE.get()))
         .save(output, TwilightForestMod.prefix("material/" + TFItems.CARMINITE.getId().getPath()));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)TFItems.RAW_IRONWOOD.get(), 2)
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFItems.LIVEROOT.get()}))
         .requires(Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.RAW_IRON}))
         .requires(Items.NUGGETS_GOLD)
         .unlockedBy("has_item", has((ItemLike)TFItems.LIVEROOT.get()))
         .save(output, TwilightForestMod.prefix("material/" + TFItems.RAW_IRONWOOD.getId().getPath()));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)TFBlocks.RAINY_CLOUD.get(), 8)
         .pattern("ccc")
         .pattern("cbc")
         .pattern("ccc")
         .define('c', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.FLUFFY_CLOUD.get()}))
         .define('b', Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.WATER_BUCKET}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.FLUFFY_CLOUD.get()))
         .save(output, TwilightForestMod.prefix("rainy_cloud"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)TFBlocks.SNOWY_CLOUD.get(), 8)
         .pattern("ccc")
         .pattern("cbc")
         .pattern("ccc")
         .define('c', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.FLUFFY_CLOUD.get()}))
         .define('b', Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.POWDER_SNOW_BUCKET}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.FLUFFY_CLOUD.get()))
         .save(output, TwilightForestMod.prefix("snowy_cloud"));
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TFItems.MYSTIC_CROWN, 1)
         .pattern("ttt")
         .pattern("t t")
         .pattern("ttt")
         .define('t', Ingredient.of(new ItemLike[]{TFItems.CROWN_SPLINTER}))
         .unlockedBy("has_item", has(TFItems.CROWN_SPLINTER))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, net.minecraft.world.item.Items.LEAD, 2)
         .define('~', net.minecraft.world.item.Items.STRING)
         .define('O', TFItems.MAZE_SLIME_BALL)
         .pattern("~~ ")
         .pattern("~O ")
         .pattern("  ~")
         .unlockedBy("has_slime_ball", has(TFItems.MAZE_SLIME_BALL))
         .save(output, TwilightForestMod.prefix("lead_maze_ver"));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, net.minecraft.world.item.Items.MAGMA_CREAM)
         .requires(net.minecraft.world.item.Items.BLAZE_POWDER)
         .requires(TFItems.MAZE_SLIME_BALL)
         .unlockedBy("has_blaze_powder", has(net.minecraft.world.item.Items.BLAZE_POWDER))
         .save(output, TwilightForestMod.prefix("magma_cream_maze_ver"));
      ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Blocks.STICKY_PISTON)
         .define('P', Blocks.PISTON)
         .define('S', TFItems.MAZE_SLIME_BALL)
         .pattern("S")
         .pattern("P")
         .unlockedBy("has_slime_ball", has(TFItems.MAZE_SLIME_BALL))
         .save(output, TwilightForestMod.prefix("sticky_piston_maze_ver"));
   }

   private void darkTowerRecipes(RecipeOutput output) {
      ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)TFBlocks.ENCASED_FIRE_JET.get())
         .pattern("#∴#")
         .pattern("∴^∴")
         .pattern("uuu")
         .define('∴', Items.DUSTS_REDSTONE)
         .define('#', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.ENCASED_TOWERWOOD.get()}))
         .define('^', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.FIRE_JET.get()}))
         .define('u', Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.LAVA_BUCKET}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.FIRE_JET.get()))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)TFBlocks.ENCASED_SMOKER.get())
         .pattern("#∴#")
         .pattern("∴^∴")
         .pattern("#∴#")
         .define('∴', Items.DUSTS_REDSTONE)
         .define('#', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.ENCASED_TOWERWOOD.get()}))
         .define('^', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.SMOKER.get()}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.SMOKER.get()))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)TFBlocks.CARMINITE_BUILDER.get())
         .pattern("#6#")
         .pattern("6o6")
         .pattern("#6#")
         .define('6', ItemTagGenerator.CARMINITE_GEMS)
         .define('#', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.ENCASED_TOWERWOOD.get()}))
         .define('o', Ingredient.of(new ItemLike[]{Blocks.DISPENSER}))
         .unlockedBy("has_item", has(ItemTagGenerator.CARMINITE_GEMS))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)TFBlocks.CARMINITE_REACTOR.get())
         .pattern("#6#")
         .pattern("6%6")
         .pattern("#6#")
         .define('6', ItemTagGenerator.CARMINITE_GEMS)
         .define('#', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.ENCASED_TOWERWOOD.get()}))
         .define('%', Items.ORES_REDSTONE)
         .unlockedBy("has_item", has(ItemTagGenerator.CARMINITE_GEMS))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)TFBlocks.REAPPEARING_BLOCK.get(), 2)
         .pattern("#∴#")
         .pattern("∴6∴")
         .pattern("#∴#")
         .define('∴', Items.DUSTS_REDSTONE)
         .define('#', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.ENCASED_TOWERWOOD.get()}))
         .define('6', ItemTagGenerator.CARMINITE_GEMS)
         .unlockedBy("has_item", has((ItemLike)TFBlocks.REAPPEARING_BLOCK.get()))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)TFBlocks.VANISHING_BLOCK.get(), 8)
         .pattern("#w#")
         .pattern("w6w")
         .pattern("#w#")
         .define('w', ItemTagGenerator.TOWERWOOD)
         .define('#', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.ENCASED_TOWERWOOD.get()}))
         .define('6', ItemTagGenerator.CARMINITE_GEMS)
         .unlockedBy("has_item", has((ItemLike)TFBlocks.REAPPEARING_BLOCK.get()))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)TFBlocks.MOSSY_TOWERWOOD.get())
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.TOWERWOOD.get()}))
         .requires(Ingredient.of(new ItemLike[]{Blocks.VINE, Blocks.MOSS_BLOCK}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.TOWERWOOD.get()))
         .save(output, TwilightForestMod.prefix("wood/" + TFBlocks.MOSSY_TOWERWOOD.getId().getPath()));
   }

   private void equipmentRecipes(Provider provider, RecipeOutput output) {
      this.bootsItem(output, TFItems.IRONWOOD_BOOTS, ItemTagGenerator.IRONWOOD_INGOTS, this.buildEnchants(provider, Pair.of(Enchantments.FEATHER_FALLING, 1)));
      this.leggingsItem(output, TFItems.IRONWOOD_LEGGINGS, ItemTagGenerator.IRONWOOD_INGOTS, this.buildEnchants(provider, Pair.of(Enchantments.PROTECTION, 1)));
      this.chestplateItem(
         output, TFItems.IRONWOOD_CHESTPLATE, ItemTagGenerator.IRONWOOD_INGOTS, this.buildEnchants(provider, Pair.of(Enchantments.PROTECTION, 1))
      );
      this.helmetItem(output, TFItems.IRONWOOD_HELMET, ItemTagGenerator.IRONWOOD_INGOTS, this.buildEnchants(provider, Pair.of(Enchantments.AQUA_AFFINITY, 1)));
      this.swordItem(
         output, TFItems.IRONWOOD_SWORD, ItemTagGenerator.IRONWOOD_INGOTS, Items.RODS_WOODEN, this.buildEnchants(provider, Pair.of(Enchantments.KNOCKBACK, 1))
      );
      this.pickaxeItem(
         output,
         TFItems.IRONWOOD_PICKAXE,
         ItemTagGenerator.IRONWOOD_INGOTS,
         Items.RODS_WOODEN,
         this.buildEnchants(provider, Pair.of(Enchantments.EFFICIENCY, 1))
      );
      this.axeItem(
         output, TFItems.IRONWOOD_AXE, ItemTagGenerator.IRONWOOD_INGOTS, Items.RODS_WOODEN, this.buildEnchants(provider, Pair.of(Enchantments.FORTUNE, 1))
      );
      this.shovelItem(
         output,
         TFItems.IRONWOOD_SHOVEL,
         ItemTagGenerator.IRONWOOD_INGOTS,
         Items.RODS_WOODEN,
         this.buildEnchants(provider, Pair.of(Enchantments.UNBREAKING, 1))
      );
      this.hoeItem(
         output, TFItems.IRONWOOD_HOE, ItemTagGenerator.IRONWOOD_INGOTS, Items.RODS_WOODEN, this.buildEnchants(provider, Pair.of(Enchantments.EFFICIENCY, 1))
      );
      this.bootsItem(output, TFItems.STEELEAF_BOOTS, ItemTagGenerator.STEELEAF_INGOTS, this.buildEnchants(provider, Pair.of(Enchantments.FEATHER_FALLING, 2)));
      this.leggingsItem(
         output, TFItems.STEELEAF_LEGGINGS, ItemTagGenerator.STEELEAF_INGOTS, this.buildEnchants(provider, Pair.of(Enchantments.FIRE_PROTECTION, 2))
      );
      this.chestplateItem(
         output, TFItems.STEELEAF_CHESTPLATE, ItemTagGenerator.STEELEAF_INGOTS, this.buildEnchants(provider, Pair.of(Enchantments.BLAST_PROTECTION, 2))
      );
      this.helmetItem(
         output, TFItems.STEELEAF_HELMET, ItemTagGenerator.STEELEAF_INGOTS, this.buildEnchants(provider, Pair.of(Enchantments.PROJECTILE_PROTECTION, 2))
      );
      this.swordItem(
         output, TFItems.STEELEAF_SWORD, ItemTagGenerator.STEELEAF_INGOTS, Items.RODS_WOODEN, this.buildEnchants(provider, Pair.of(Enchantments.LOOTING, 2))
      );
      this.pickaxeItem(
         output, TFItems.STEELEAF_PICKAXE, ItemTagGenerator.STEELEAF_INGOTS, Items.RODS_WOODEN, this.buildEnchants(provider, Pair.of(Enchantments.FORTUNE, 2))
      );
      this.axeItem(
         output, TFItems.STEELEAF_AXE, ItemTagGenerator.STEELEAF_INGOTS, Items.RODS_WOODEN, this.buildEnchants(provider, Pair.of(Enchantments.EFFICIENCY, 2))
      );
      this.shovelItem(
         output,
         TFItems.STEELEAF_SHOVEL,
         ItemTagGenerator.STEELEAF_INGOTS,
         Items.RODS_WOODEN,
         this.buildEnchants(provider, Pair.of(Enchantments.EFFICIENCY, 2))
      );
      this.hoeItem(
         output, TFItems.STEELEAF_HOE, ItemTagGenerator.STEELEAF_INGOTS, Items.RODS_WOODEN, this.buildEnchants(provider, Pair.of(Enchantments.FORTUNE, 2))
      );
      this.bootsItem(output, TFItems.ARCTIC_BOOTS, ItemTagGenerator.ARCTIC_FUR);
      this.chestplateItem(output, TFItems.ARCTIC_CHESTPLATE, ItemTagGenerator.ARCTIC_FUR);
      this.helmetItem(output, TFItems.ARCTIC_HELMET, ItemTagGenerator.ARCTIC_FUR);
      this.leggingsItem(output, TFItems.ARCTIC_LEGGINGS, ItemTagGenerator.ARCTIC_FUR);
      this.bootsItem(output, TFItems.KNIGHTMETAL_BOOTS, ItemTagGenerator.KNIGHTMETAL_INGOTS);
      this.chestplateItem(output, TFItems.KNIGHTMETAL_CHESTPLATE, ItemTagGenerator.KNIGHTMETAL_INGOTS);
      this.helmetItem(output, TFItems.KNIGHTMETAL_HELMET, ItemTagGenerator.KNIGHTMETAL_INGOTS);
      this.leggingsItem(output, TFItems.KNIGHTMETAL_LEGGINGS, ItemTagGenerator.KNIGHTMETAL_INGOTS);
      this.pickaxeItem(output, TFItems.KNIGHTMETAL_PICKAXE, ItemTagGenerator.KNIGHTMETAL_INGOTS, Items.RODS_WOODEN);
      this.swordItem(output, TFItems.KNIGHTMETAL_SWORD, ItemTagGenerator.KNIGHTMETAL_INGOTS, Items.RODS_WOODEN);
      this.axeItem(output, TFItems.KNIGHTMETAL_AXE, ItemTagGenerator.KNIGHTMETAL_INGOTS, Items.RODS_WOODEN);
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TFItems.FIERY_BOOTS)
         .pattern("# #")
         .pattern("# #")
         .define('#', ItemTagGenerator.FIERY_INGOTS)
         .unlockedBy("has_item", has(ItemTagGenerator.FIERY_INGOTS))
         .group("fiery_boots")
         .save(output, this.locEquip(TFItems.FIERY_BOOTS.getKey().location().getPath()));
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TFItems.FIERY_LEGGINGS)
         .pattern("###")
         .pattern("# #")
         .pattern("# #")
         .define('#', ItemTagGenerator.FIERY_INGOTS)
         .unlockedBy("has_item", has(ItemTagGenerator.FIERY_INGOTS))
         .group("fiery_leggings")
         .save(output, this.locEquip(TFItems.FIERY_LEGGINGS.getKey().location().getPath()));
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TFItems.FIERY_CHESTPLATE)
         .pattern("# #")
         .pattern("###")
         .pattern("###")
         .define('#', ItemTagGenerator.FIERY_INGOTS)
         .unlockedBy("has_item", has(ItemTagGenerator.FIERY_INGOTS))
         .group("fiery_chestplate")
         .save(output, this.locEquip(TFItems.FIERY_CHESTPLATE.getKey().location().getPath()));
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TFItems.FIERY_HELMET)
         .pattern("###")
         .pattern("# #")
         .define('#', ItemTagGenerator.FIERY_INGOTS)
         .unlockedBy("has_item", has(ItemTagGenerator.FIERY_INGOTS))
         .group("fiery_helmet")
         .save(output, this.locEquip(TFItems.FIERY_HELMET.getKey().location().getPath()));
      ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TFItems.FIERY_PICKAXE)
         .pattern("###")
         .pattern(" X ")
         .pattern(" X ")
         .define('#', ItemTagGenerator.FIERY_INGOTS)
         .define('X', Items.RODS_BLAZE)
         .unlockedBy("has_item", has(ItemTagGenerator.FIERY_INGOTS))
         .group("fiery_pickaxe")
         .save(output, this.locEquip(TFItems.FIERY_PICKAXE.getKey().location().getPath()));
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TFItems.FIERY_SWORD)
         .pattern("#")
         .pattern("#")
         .pattern("X")
         .define('#', ItemTagGenerator.FIERY_INGOTS)
         .define('X', Items.RODS_BLAZE)
         .unlockedBy("has_item", has(ItemTagGenerator.FIERY_INGOTS))
         .group("fiery_sword")
         .save(output, this.locEquip(TFItems.FIERY_SWORD.getKey().location().getPath()));
      ShapedRecipeBuilder.shaped(
            RecipeCategory.COMBAT, new ItemStack(TFItems.NAGA_CHESTPLATE, 1, this.buildEnchants(provider, Pair.of(Enchantments.FIRE_PROTECTION, 3)).build())
         )
         .pattern("# #")
         .pattern("###")
         .pattern("###")
         .define('#', TFItems.NAGA_SCALE)
         .unlockedBy("has_item", has(TFItems.NAGA_SCALE))
         .save(output, this.locEquip(TFItems.NAGA_CHESTPLATE.getKey().location().getPath()));
      ShapedRecipeBuilder.shaped(
            RecipeCategory.COMBAT, new ItemStack(TFItems.NAGA_LEGGINGS, 1, this.buildEnchants(provider, Pair.of(Enchantments.PROTECTION, 3)).build())
         )
         .pattern("###")
         .pattern("# #")
         .pattern("# #")
         .define('#', TFItems.NAGA_SCALE)
         .unlockedBy("has_item", has(TFItems.NAGA_SCALE))
         .save(output, this.locEquip(TFItems.NAGA_LEGGINGS.getKey().location().getPath()));
      ShapedRecipeBuilder.shaped(
            RecipeCategory.COMBAT, new ItemStack(TFItems.YETI_HELMET, 1, this.buildEnchants(provider, Pair.of(Enchantments.PROTECTION, 2)).build())
         )
         .pattern("###")
         .pattern("# #")
         .define('#', TFItems.ALPHA_YETI_FUR)
         .unlockedBy("has_item", has(TFItems.ALPHA_YETI_FUR))
         .save(output, this.locEquip(TFItems.YETI_HELMET.getKey().location().getPath()));
      ShapedRecipeBuilder.shaped(
            RecipeCategory.COMBAT, new ItemStack(TFItems.YETI_CHESTPLATE, 1, this.buildEnchants(provider, Pair.of(Enchantments.PROTECTION, 2)).build())
         )
         .pattern("# #")
         .pattern("###")
         .pattern("###")
         .define('#', TFItems.ALPHA_YETI_FUR)
         .unlockedBy("has_item", has(TFItems.ALPHA_YETI_FUR))
         .save(output, this.locEquip(TFItems.YETI_CHESTPLATE.getKey().location().getPath()));
      ShapedRecipeBuilder.shaped(
            RecipeCategory.COMBAT, new ItemStack(TFItems.YETI_LEGGINGS, 1, this.buildEnchants(provider, Pair.of(Enchantments.PROTECTION, 2)).build())
         )
         .pattern("###")
         .pattern("# #")
         .pattern("# #")
         .define('#', TFItems.ALPHA_YETI_FUR)
         .unlockedBy("has_item", has(TFItems.ALPHA_YETI_FUR))
         .save(output, this.locEquip(TFItems.YETI_LEGGINGS.getKey().location().getPath()));
      ShapedRecipeBuilder.shaped(
            RecipeCategory.COMBAT,
            new ItemStack(
               TFItems.YETI_BOOTS, 1, this.buildEnchants(provider, Pair.of(Enchantments.PROTECTION, 2), Pair.of(Enchantments.FEATHER_FALLING, 4)).build()
            )
         )
         .pattern("# #")
         .pattern("# #")
         .define('#', TFItems.ALPHA_YETI_FUR)
         .unlockedBy("has_item", has(TFItems.ALPHA_YETI_FUR))
         .save(output, this.locEquip(TFItems.YETI_BOOTS.getKey().location().getPath()));
      ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)TFItems.GIANT_PICKAXE.get())
         .pattern("###")
         .pattern(" X ")
         .pattern(" X ")
         .define('#', (ItemLike)TFBlocks.GIANT_COBBLESTONE.get())
         .define('X', (ItemLike)TFBlocks.GIANT_LOG.get())
         .unlockedBy("has_item", has((ItemLike)TFBlocks.GIANT_COBBLESTONE.get()))
         .save(output, this.locEquip(TFItems.GIANT_PICKAXE.getId().getPath()));
      ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)TFItems.GIANT_SWORD.get())
         .pattern("#")
         .pattern("#")
         .pattern("X")
         .define('#', (ItemLike)TFBlocks.GIANT_COBBLESTONE.get())
         .define('X', (ItemLike)TFBlocks.GIANT_LOG.get())
         .unlockedBy("has_item", has((ItemLike)TFBlocks.GIANT_COBBLESTONE.get()))
         .save(output, this.locEquip(TFItems.GIANT_SWORD.getId().getPath()));
      this.travellersGearRecipes(output);
      this.charmRecipe(output, "charm_of_keeping_2", TFItems.CHARM_OF_KEEPING_2, TFItems.CHARM_OF_KEEPING_1);
      this.charmRecipe(output, "charm_of_keeping_3", TFItems.CHARM_OF_KEEPING_3, TFItems.CHARM_OF_KEEPING_2);
      this.charmRecipe(output, "charm_of_life_2", TFItems.CHARM_OF_LIFE_2, TFItems.CHARM_OF_LIFE_1);
      SpecialRecipeBuilder.special(MoonwormQueenRepairRecipe::new).save(output, TwilightForestMod.prefix("moonworm_queen_repair_recipe").toString());
      SpecialRecipeBuilder.special(MagicMapCloningRecipe::new).save(output, TwilightForestMod.prefix("magic_map_cloning_recipe").toString());
      SpecialRecipeBuilder.special(MazeMapCloningRecipe::new).save(output, TwilightForestMod.prefix("maze_map_cloning_recipe").toString());
      SpecialRecipeBuilder.special(EmperorsClothRecipe::new).save(output, TwilightForestMod.prefix("emperors_cloth_recipe").toString());
      SpecialRecipeBuilder.special(CasketRepairRecipe::new).save(output, TwilightForestMod.prefix("casket_repair_recipe").toString());
      SpecialRecipeBuilder.special(EssenceRepairRecipe::new).save(output, TwilightForestMod.prefix("essence_repair_recipe").toString());
      SpecialRecipeBuilder.special(TravellersVestGlovesMergeRecipe::new)
         .save(output, TwilightForestMod.prefix("travellers_vest_gloves_merge_recipe").toString());
      NoSmithingTemplateRecipeBuilder.noTemplate(
            Ingredient.of(ItemTagGenerator.EMPERORS_CLOTH_APPLICABLE),
            Ingredient.of(new ItemLike[]{(ItemLike)TFItems.EMPERORS_CLOTH.get()}),
            RecipeCategory.MISC
         )
         .attachData(TFDataComponents.EMPERORS_CLOTH::value, Unit.INSTANCE)
         .unlocks("has_cloth", has(TFItems.EMPERORS_CLOTH))
         .save(output, TwilightForestMod.prefix("emperors_cloth_smithing"));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.COBBLESTONE, 64)
         .requires((ItemLike)TFBlocks.GIANT_COBBLESTONE.get())
         .unlockedBy("has_item", has((ItemLike)TFBlocks.GIANT_COBBLESTONE.get()))
         .save(
            output,
            TwilightForestMod.prefix(
               TFBlocks.GIANT_COBBLESTONE.getId().getPath() + "_to_" + BuiltInRegistries.ITEM.getKey(net.minecraft.world.item.Items.COBBLESTONE).getPath()
            )
         );
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.OAK_LOG, 64)
         .requires((ItemLike)TFBlocks.GIANT_LOG.get())
         .unlockedBy("has_item", has((ItemLike)TFBlocks.GIANT_LOG.get()))
         .save(
            output,
            TwilightForestMod.prefix(
               TFBlocks.GIANT_LOG.getId().getPath() + "_to_" + BuiltInRegistries.ITEM.getKey(net.minecraft.world.item.Items.OAK_LOG).getPath()
            )
         );
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.OAK_LEAVES, 64)
         .requires((ItemLike)TFBlocks.GIANT_LEAVES.get())
         .unlockedBy("has_item", has((ItemLike)TFBlocks.GIANT_LEAVES.get()))
         .save(
            output,
            TwilightForestMod.prefix(
               TFBlocks.GIANT_LEAVES.getId().getPath() + "_to_" + BuiltInRegistries.ITEM.getKey(net.minecraft.world.item.Items.OAK_LEAVES).getPath()
            )
         );
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.OBSIDIAN, 64)
         .requires((ItemLike)TFBlocks.GIANT_OBSIDIAN.get())
         .unlockedBy("has_item", has((ItemLike)TFBlocks.GIANT_OBSIDIAN.get()))
         .save(
            output,
            TwilightForestMod.prefix(
               TFBlocks.GIANT_OBSIDIAN.getId().getPath() + "_to_" + BuiltInRegistries.ITEM.getKey(net.minecraft.world.item.Items.OBSIDIAN).getPath()
            )
         );
      ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, (ItemLike)TFItems.BLOCK_AND_CHAIN.get())
         .requires(Ingredient.of(ItemTagGenerator.STORAGE_BLOCKS_KNIGHTMETAL))
         .requires(Ingredient.of(ItemTagGenerator.KNIGHTMETAL_INGOTS), 3)
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFItems.KNIGHTMETAL_RING.get()}))
         .unlockedBy("has_block", has(ItemTagGenerator.STORAGE_BLOCKS_KNIGHTMETAL))
         .unlockedBy("has_ingot", has(ItemTagGenerator.KNIGHTMETAL_INGOTS))
         .unlockedBy("has_ring", has((ItemLike)TFItems.KNIGHTMETAL_RING.get()))
         .save(output, this.locEquip(TFItems.BLOCK_AND_CHAIN.getId().getPath()));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TFItems.TANNIN)
         .requires(potionIngredient(Potions.WATER))
         .requires(TFBlocks.TWILIGHT_OAK_SAPLING)
         .requires(TFBlocks.ROOT_STRAND)
         .requires(TFBlocks.TWILIGHT_OAK_LEAVES)
         .unlockedBy("has_block", has(TFBlocks.TWILIGHT_OAK_SAPLING))
         .unlockedBy("has_block", has(TFBlocks.ROOT_STRAND))
         .unlockedBy("has_block", has(TFBlocks.TWILIGHT_OAK_LEAVES))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TFItems.TREATED_LEATHER)
         .requires(TFItems.TANNIN)
         .requires(Items.LEATHERS)
         .unlockedBy("has_tannin", has(TFItems.TANNIN))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike)TFItems.KNIGHTMETAL_RING.get())
         .pattern(" - ")
         .pattern("- -")
         .pattern(" - ")
         .define('-', ItemTagGenerator.KNIGHTMETAL_INGOTS)
         .unlockedBy("has_item", has(ItemTagGenerator.KNIGHTMETAL_INGOTS))
         .save(output, this.locEquip(TFItems.KNIGHTMETAL_RING.getId().getPath()));
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)TFItems.KNIGHTMETAL_SHIELD.get())
         .pattern("-#")
         .pattern("-o")
         .pattern("-#")
         .define('-', ItemTagGenerator.KNIGHTMETAL_INGOTS)
         .define('#', ItemTagGenerator.TOWERWOOD)
         .define('o', Ingredient.of(new ItemLike[]{(ItemLike)TFItems.KNIGHTMETAL_RING.get()}))
         .unlockedBy("has_ingot", has(ItemTagGenerator.KNIGHTMETAL_INGOTS))
         .unlockedBy("has_ring", has((ItemLike)TFItems.KNIGHTMETAL_RING.get()))
         .save(output, this.locEquip(TFItems.KNIGHTMETAL_SHIELD.getId().getPath()));
      ScepterRecipeBuilder.repairFor((Item)TFItems.LIFEDRAIN_SCEPTER.get(), 9)
         .addRepairIngredient(net.minecraft.world.item.Items.FERMENTED_SPIDER_EYE)
         .save(output, this.locEquip(TFItems.LIFEDRAIN_SCEPTER.getId().getPath()));
      ScepterRecipeBuilder.repairFor((Item)TFItems.FORTIFICATION_SCEPTER.get(), 9)
         .addRepairIngredient(Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.GOLDEN_APPLE}))
         .save(output, this.locEquip(TFItems.FORTIFICATION_SCEPTER.getId().getPath()));
      ScepterRecipeBuilder.repairFor((Item)TFItems.TWILIGHT_SCEPTER.get(), 9)
         .addRepairIngredient(Items.ENDER_PEARLS)
         .save(output, this.locEquip(TFItems.TWILIGHT_SCEPTER.getId().getPath()));
      ScepterRecipeBuilder.repairFor((Item)TFItems.ZOMBIE_SCEPTER.get(), 9)
         .addRepairIngredient(potionsIngredient(Potions.STRENGTH, Potions.LONG_STRENGTH, Potions.STRONG_STRENGTH))
         .addRepairIngredient(net.minecraft.world.item.Items.ROTTEN_FLESH)
         .save(output, this.locEquip(TFItems.ZOMBIE_SCEPTER.getId().getPath()));
      Predicate<Ingredient> splitTravellersModifiersRecipes = ingredient -> Arrays.stream(ingredient.getItems())
         .allMatch(stack -> stack.has(TFDataComponents.IS_TRAVELLERS_GEAR) && !stack.is(TFItems.TRAVELLERS_BELT));
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern(" R ")
               .pattern("RGR")
               .define('R', TFBlocks.RED_THREAD)
               .define('G', TFItems.TRAVELLERS_GOGGLES)
               .build(),
            TravellersModifiersManager.RED_THREAD_VISION_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern(" E ")
               .pattern("PVP")
               .pattern(" S ")
               .define('E', net.minecraft.world.item.Items.ENDER_EYE)
               .define('P', net.minecraft.world.item.Items.ENDER_PEARL)
               .define('V', TFItems.TRAVELLERS_VEST)
               .define('S', net.minecraft.world.item.Items.SUGAR)
               .build(),
            TravellersModifiersManager.PERFECT_DODGE_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShapeless(
            CartesianShapelessRecipeBuilder.create(splitTravellersModifiersRecipes)
               .ingredient(potionsIngredient(Potions.INVISIBILITY, Potions.LONG_INVISIBILITY))
               .ingredient(net.minecraft.world.item.Items.ENDER_EYE)
               .ingredient(net.minecraft.world.item.Items.SPIDER_EYE)
               .ingredient(net.minecraft.world.item.Items.GOLDEN_CARROT)
               .ingredient(TFItems.TRAVELLERS_VEST)
               .build(),
            TravellersModifiersManager.STEALTH_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern("RRR")
               .pattern("RVR")
               .pattern("OOO")
               .define('R', net.minecraft.world.item.Items.REDSTONE_BLOCK)
               .define('V', TFItems.TRAVELLERS_VEST)
               .define('O', net.minecraft.world.item.Items.OBSIDIAN)
               .build(),
            TravellersModifiersManager.HASTE_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShapeless(
            CartesianShapelessRecipeBuilder.create(splitTravellersModifiersRecipes)
               .ingredient(TFItems.EXANIMATE_ESSENCE)
               .ingredient(TFItems.ORE_MAGNET)
               .ingredient(TFItems.LIVEROOT)
               .ingredient(net.minecraft.world.item.Items.CHICKEN)
               .ingredient(TFItems.TRAVELLERS_VEST)
               .build(),
            TravellersModifiersManager.ARROW_MAGNETISM_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShapeless(
            CartesianShapelessRecipeBuilder.create(splitTravellersModifiersRecipes)
               .ingredient(TFItems.HYDRA_CHOP)
               .ingredient(TFItems.HYDRA_CHOP)
               .ingredient(TFItems.HYDRA_CHOP)
               .ingredient(net.minecraft.world.item.Items.BUNDLE)
               .ingredient(TFItems.TRAVELLERS_VEST)
               .build(),
            TravellersModifiersManager.EFFICIENT_EATER_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShapeless(
            CartesianShapelessRecipeBuilder.create(splitTravellersModifiersRecipes)
               .ingredient(TFItems.TRAVELLERS_WINGS)
               .ingredient(TFItems.TRAVELLERS_BELT)
               .build(),
            TravellersModifiersManager.SWAP_HOTBAR_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern("FCF")
               .pattern("FWF")
               .pattern("FEF")
               .define('F', net.minecraft.world.item.Items.FEATHER)
               .define('C', net.minecraft.world.item.Items.COBWEB)
               .define('W', TFItems.TRAVELLERS_WINGS)
               .define('E', TFItems.BORER_ESSENCE)
               .build(),
            TravellersModifiersManager.GRADUAL_GLIDE_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern(" L ")
               .pattern("SWS")
               .pattern("P P")
               .define('L', potionsIngredient(Potions.LEAPING, Potions.LONG_LEAPING, Potions.STRONG_LEAPING))
               .define('S', Items.STRINGS)
               .define('W', TFItems.TRAVELLERS_WINGS)
               .define('P', net.minecraft.world.item.Items.PISTON)
               .build(),
            TravellersModifiersManager.DOUBLE_JUMP_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern("FPF")
               .pattern("HWH")
               .define('P', potionsIngredient(Potions.SWIFTNESS, Potions.LONG_SWIFTNESS, Potions.STRONG_SWIFTNESS))
               .define('H', net.minecraft.world.item.Items.RABBIT_HIDE)
               .define('W', TFItems.TRAVELLERS_WINGS)
               .define('F', TFItems.RAVEN_FEATHER)
               .build(),
            TravellersModifiersManager.AGILE_RANGER_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern("SBS")
               .pattern("PWP")
               .pattern("SBS")
               .define('S', Items.STRINGS)
               .define('B', Items.BONES)
               .define('P', net.minecraft.world.item.Items.PISTON)
               .define('W', TFItems.TRAVELLERS_WINGS)
               .build(),
            TravellersModifiersManager.SIDESTEP_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern("SBS")
               .pattern("L L")
               .define('B', TFItems.TRAVELLERS_BOOTS)
               .define('S', TFItems.MAZE_SLIME_BALL)
               .define('L', net.minecraft.world.item.Items.LILY_PAD)
               .build(),
            TravellersModifiersManager.WATER_WALK_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern("n n")
               .pattern("kbk")
               .pattern("i i")
               .define('b', TFItems.TRAVELLERS_BOOTS)
               .define('k', ItemTagGenerator.KNIGHTMETAL_INGOTS)
               .define('n', Items.NUGGETS_IRON)
               .define('i', Items.INGOTS_IRON)
               .build(),
            TravellersModifiersManager.UNRESTRAINED_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern("WBW")
               .pattern("S S")
               .define('W', Items.STRINGS)
               .define('B', TFItems.TRAVELLERS_BOOTS)
               .define('S', net.minecraft.world.item.Items.SLIME_BLOCK)
               .build(),
            TravellersModifiersManager.SLIMY_SOLES_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern("MPM")
               .pattern("HBH")
               .define('M', TFItems.RAW_MEEF)
               .define('P', potionsIngredient(Potions.SWIFTNESS, Potions.LONG_SWIFTNESS, Potions.STRONG_SWIFTNESS))
               .define('B', TFItems.TRAVELLERS_BOOTS)
               .define('H', net.minecraft.world.item.Items.RABBIT_HIDE)
               .build(),
            TravellersModifiersManager.STRAIGHT_AHEAD_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern(" E ")
               .pattern("MTM")
               .pattern(" E ")
               .define('E', net.minecraft.world.item.Items.EXPERIENCE_BOTTLE)
               .define('M', TFBlocks.MOSS_PATCH)
               .define(
                  'T', Ingredient.of(new ItemLike[]{TFItems.TRAVELLERS_GOGGLES, TFItems.TRAVELLERS_VEST, TFItems.TRAVELLERS_WINGS, TFItems.TRAVELLERS_BOOTS})
               )
               .build(),
            TravellersModifiersManager.AUTO_REPAIR_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern(" M ")
               .pattern("ETE")
               .pattern(" M ")
               .define('M', TFBlocks.MOSS_PATCH)
               .define('E', net.minecraft.world.item.Items.EXPERIENCE_BOTTLE)
               .define(
                  'T', Ingredient.of(new ItemLike[]{TFItems.TRAVELLERS_GOGGLES, TFItems.TRAVELLERS_VEST, TFItems.TRAVELLERS_WINGS, TFItems.TRAVELLERS_BOOTS})
               )
               .build(),
            TravellersModifiersManager.AUTO_REPAIR_MODIFIER,
            true
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern("M M")
               .pattern("YGY")
               .pattern("M M")
               .define('M', net.minecraft.world.item.Items.PHANTOM_MEMBRANE)
               .define('Y', net.minecraft.world.item.Items.YELLOW_STAINED_GLASS_PANE)
               .define('G', TFItems.TRAVELLERS_GOGGLES)
               .build(),
            TravellersModifiersManager.ALL_NIGHT_GOGGLES_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern("sss")
               .pattern("igi")
               .pattern("sss")
               .define('i', net.minecraft.world.item.Items.ITEM_FRAME)
               .define('s', Items.RODS_WOODEN)
               .define('g', TFItems.TRAVELLERS_GOGGLES)
               .build(),
            TravellersModifiersManager.ITEM_DISPLAY_MODIFIER
         )
         .save(output);
      TravellersGearComponentModifierBuilder.buildShaped(
            CartesianShapedRecipeBuilder.create(splitTravellersModifiersRecipes)
               .pattern(" b")
               .pattern("gb")
               .pattern("sw")
               .define('b', net.minecraft.world.item.Items.BAMBOO)
               .define('w', potionsIngredient(Potions.WATER_BREATHING, Potions.LONG_WATER_BREATHING))
               .define('s', Items.SLIME_BALLS)
               .define('g', TFItems.TRAVELLERS_GOGGLES)
               .build(),
            TravellersModifiersManager.AQUATIC_AGILITY_MODIFIER
         )
         .save(output);
      DryingRecipeBuilder.drying(Ingredient.of(ItemTags.SAPLINGS), new ItemStack(net.minecraft.world.item.Items.DEAD_BUSH), 6.0F)
         .unlockedBy("has_sapling", has(ItemTags.SAPLINGS))
         .save(output, TwilightForestMod.prefix("drying/sapling_to_dead_bush"));
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.MUD, net.minecraft.world.item.Items.CLAY, 6.0F)
         .unlockedBy("has_mud", has(net.minecraft.world.item.Items.MUD))
         .save(output, TwilightForestMod.prefix("drying/mud_to_clay"));
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.WET_SPONGE, net.minecraft.world.item.Items.SPONGE, 2.0F)
         .unlockedBy("has_wet_sponge", has(net.minecraft.world.item.Items.WET_SPONGE))
         .save(output, TwilightForestMod.prefix("drying/sponge"));
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.KELP, net.minecraft.world.item.Items.DRIED_KELP, 3.0F)
         .unlockedBy("has_kelp", has(net.minecraft.world.item.Items.KELP))
         .save(output, TwilightForestMod.prefix("drying/kelp"));
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.ROTTEN_FLESH, TFItems.MONSTER_JERKY)
         .unlockedBy("has_meat", has(net.minecraft.world.item.Items.ROTTEN_FLESH))
         .save(output);
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.BEEF, TFItems.BEEF_JERKY)
         .unlockedBy("has_meat", has(net.minecraft.world.item.Items.BEEF))
         .save(output);
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.CHICKEN, TFItems.CHICKEN_JERKY)
         .unlockedBy("has_meat", has(net.minecraft.world.item.Items.CHICKEN))
         .save(output);
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.PORKCHOP, TFItems.PORK_JERKY)
         .unlockedBy("has_meat", has(net.minecraft.world.item.Items.PORKCHOP))
         .save(output);
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.MUTTON, TFItems.MUTTON_JERKY)
         .unlockedBy("has_meat", has(net.minecraft.world.item.Items.MUTTON))
         .save(output);
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.RABBIT, TFItems.RABBIT_JERKY)
         .unlockedBy("has_meat", has(net.minecraft.world.item.Items.RABBIT))
         .save(output);
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.COD, TFItems.COD_JERKY)
         .unlockedBy("has_meat", has(net.minecraft.world.item.Items.COD))
         .save(output);
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.SALMON, TFItems.SALMON_JERKY)
         .unlockedBy("has_meat", has(net.minecraft.world.item.Items.SALMON))
         .save(output);
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.TROPICAL_FISH, TFItems.TROPICAL_FISH_JERKY)
         .unlockedBy("has_meat", has(net.minecraft.world.item.Items.TROPICAL_FISH))
         .save(output);
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.PUFFERFISH, TFItems.FUGU_JERKY)
         .unlockedBy("has_meat", has(net.minecraft.world.item.Items.PUFFERFISH))
         .save(output);
      DryingRecipeBuilder.drying(TFItems.RAW_VENISON, TFItems.VENISON_JERKY).unlockedBy("has_meat", has(TFItems.RAW_VENISON)).save(output);
      DryingRecipeBuilder.drying(TFItems.RAW_MEEF, TFItems.MEEF_JERKY).unlockedBy("has_meat", has(TFItems.RAW_MEEF)).save(output);
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.SLIME_BALL, TFItems.GELATINOUS_SLIME_DROP)
         .unlockedBy("has_slime_ball", has(net.minecraft.world.item.Items.SLIME_BALL))
         .save(output);
      DryingRecipeBuilder.drying(TFItems.MAZE_SLIME_BALL, TFItems.GELATINOUS_MAZE_SLIME_DROP)
         .unlockedBy("has_maze_slime_ball", has(TFItems.MAZE_SLIME_BALL))
         .save(output);
      DryingRecipeBuilder.drying(TFItems.TREATED_LEATHER, TFItems.TANNED_LEATHER).unlockedBy("has_treated", has(TFItems.TREATED_LEATHER)).save(output);
      this.DryingRackCoralRecipes(output);
      DryingRecipeBuilder.drying(net.minecraft.world.item.Items.BREAD, TFItems.STALE_BREAD).save(output);
   }

   private void blockCompressionRecipes(RecipeOutput output) {
      this.reverseCompressBlock(output, TFItems.ARCTIC_FUR, TFBlocks.ARCTIC_FUR_BLOCK);
      this.reverseCompressBlock(output, TFItems.CARMINITE, TFBlocks.CARMINITE_BLOCK);
      this.reverseCompressBlock(output, TFItems.IRONWOOD_INGOT, TFBlocks.IRONWOOD_BLOCK);
      this.reverseCompressBlock(output, TFItems.KNIGHTMETAL_INGOT, TFBlocks.KNIGHTMETAL_BLOCK);
      this.reverseCompressBlock(output, TFItems.STEELEAF_INGOT, TFBlocks.STEELEAF_BLOCK);
      this.reverseCompressBlock(output, TFItems.FIERY_INGOT, TFBlocks.FIERY_BLOCK);
      this.reverseCompressBlock(output, TFItems.MAZE_SLIME_BALL, TFBlocks.MAZE_SLIME_BLOCK);
      this.compressedBlock(output, TFBlocks.ARCTIC_FUR_BLOCK, ItemTagGenerator.ARCTIC_FUR, TFItems.ARCTIC_FUR);
      this.compressedBlock(output, TFBlocks.CARMINITE_BLOCK, ItemTagGenerator.CARMINITE_GEMS, TFItems.CARMINITE);
      this.compressedBlock(output, TFBlocks.FIERY_BLOCK, ItemTagGenerator.FIERY_INGOTS, TFItems.FIERY_INGOT);
      this.compressedBlock(output, TFBlocks.IRONWOOD_BLOCK, ItemTagGenerator.IRONWOOD_INGOTS, TFItems.IRONWOOD_INGOT);
      this.compressedBlock(output, TFBlocks.KNIGHTMETAL_BLOCK, ItemTagGenerator.KNIGHTMETAL_INGOTS, TFItems.KNIGHTMETAL_INGOT);
      this.compressedBlock(output, TFBlocks.STEELEAF_BLOCK, ItemTagGenerator.STEELEAF_INGOTS, TFItems.STEELEAF_INGOT);
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TFBlocks.MAZE_SLIME_BLOCK)
         .pattern("###")
         .pattern("###")
         .pattern("###")
         .define('#', TFItems.MAZE_SLIME_BALL)
         .unlockedBy("has_item", has(TFItems.MAZE_SLIME_BALL))
         .save(output, TwilightForestMod.prefix("compressed_blocks/maze_slime"));
   }

   private void emptyMapRecipes(RecipeOutput output) {
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)TFItems.MAGIC_MAP_FOCUS.get())
         .requires((ItemLike)TFItems.RAVEN_FEATHER.get())
         .requires((ItemLike)TFItems.TORCHBERRIES.get())
         .requires(Items.DUSTS_GLOWSTONE)
         .unlockedBy("has_berries", has((ItemLike)TFItems.TORCHBERRIES.get()))
         .unlockedBy("has_feather", has((ItemLike)TFItems.RAVEN_FEATHER.get()))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike)TFItems.MAGIC_MAP.get())
         .pattern("###")
         .pattern("#•#")
         .pattern("###")
         .define('#', ItemTagGenerator.PAPER)
         .define('•', Ingredient.of(new ItemLike[]{(ItemLike)TFItems.MAGIC_MAP_FOCUS.get()}))
         .unlockedBy("has_item", has((ItemLike)TFItems.MAGIC_MAP_FOCUS.get()))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike)TFItems.MAZE_MAP.get())
         .pattern("###")
         .pattern("#•#")
         .pattern("###")
         .define('#', ItemTagGenerator.PAPER)
         .define('•', Ingredient.of(new ItemLike[]{(ItemLike)TFItems.MAZE_MAP_FOCUS.get()}))
         .unlockedBy("has_item", has((ItemLike)TFItems.MAZE_MAP_FOCUS.get()))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)TFItems.ORE_MAP.get())
         .requires((ItemLike)TFItems.MAZE_MAP.get())
         .requires(Items.STORAGE_BLOCKS_DIAMOND)
         .requires(Items.STORAGE_BLOCKS_GOLD)
         .requires(Items.STORAGE_BLOCKS_IRON)
         .unlockedBy("has_item", has((ItemLike)TFItems.MAZE_MAP.get()))
         .save(output);
   }

   private void woodRecipes(RecipeOutput output) {
      this.buttonBlock(output, "canopy", TFBlocks.CANOPY_BUTTON, TFBlocks.CANOPY_PLANKS);
      this.buttonBlock(output, "dark", TFBlocks.DARK_BUTTON, TFBlocks.DARK_PLANKS);
      this.buttonBlock(output, "mangrove", TFBlocks.MANGROVE_BUTTON, TFBlocks.MANGROVE_PLANKS);
      this.buttonBlock(output, "mining", TFBlocks.MINING_BUTTON, TFBlocks.MINING_PLANKS);
      this.buttonBlock(output, "sorting", TFBlocks.SORTING_BUTTON, TFBlocks.SORTING_PLANKS);
      this.buttonBlock(output, "time", TFBlocks.TIME_BUTTON, TFBlocks.TIME_PLANKS);
      this.buttonBlock(output, "transformation", TFBlocks.TRANSFORMATION_BUTTON, TFBlocks.TRANSFORMATION_PLANKS);
      this.buttonBlock(output, "twilight_oak", TFBlocks.TWILIGHT_OAK_BUTTON, TFBlocks.TWILIGHT_OAK_PLANKS);
      this.doorBlock(output, "canopy", TFBlocks.CANOPY_DOOR, TFBlocks.CANOPY_PLANKS);
      this.doorBlock(output, "dark", TFBlocks.DARK_DOOR, TFBlocks.DARK_PLANKS);
      this.doorBlock(output, "mangrove", TFBlocks.MANGROVE_DOOR, TFBlocks.MANGROVE_PLANKS);
      this.doorBlock(output, "mining", TFBlocks.MINING_DOOR, TFBlocks.MINING_PLANKS);
      this.doorBlock(output, "sorting", TFBlocks.SORTING_DOOR, TFBlocks.SORTING_PLANKS);
      this.doorBlock(output, "time", TFBlocks.TIME_DOOR, TFBlocks.TIME_PLANKS);
      this.doorBlock(output, "transformation", TFBlocks.TRANSFORMATION_DOOR, TFBlocks.TRANSFORMATION_PLANKS);
      this.doorBlock(output, "twilight_oak", TFBlocks.TWILIGHT_OAK_DOOR, TFBlocks.TWILIGHT_OAK_PLANKS);
      this.fenceBlock(output, "canopy", TFBlocks.CANOPY_FENCE, TFBlocks.CANOPY_PLANKS);
      this.fenceBlock(output, "dark", TFBlocks.DARK_FENCE, TFBlocks.DARK_PLANKS);
      this.fenceBlock(output, "mangrove", TFBlocks.MANGROVE_FENCE, TFBlocks.MANGROVE_PLANKS);
      this.fenceBlock(output, "mining", TFBlocks.MINING_FENCE, TFBlocks.MINING_PLANKS);
      this.fenceBlock(output, "sorting", TFBlocks.SORTING_FENCE, TFBlocks.SORTING_PLANKS);
      this.fenceBlock(output, "time", TFBlocks.TIME_FENCE, TFBlocks.TIME_PLANKS);
      this.fenceBlock(output, "transformation", TFBlocks.TRANSFORMATION_FENCE, TFBlocks.TRANSFORMATION_PLANKS);
      this.fenceBlock(output, "twilight_oak", TFBlocks.TWILIGHT_OAK_FENCE, TFBlocks.TWILIGHT_OAK_PLANKS);
      this.gateBlock(output, "canopy", TFBlocks.CANOPY_GATE, TFBlocks.CANOPY_PLANKS);
      this.gateBlock(output, "dark", TFBlocks.DARK_GATE, TFBlocks.DARK_PLANKS);
      this.gateBlock(output, "mangrove", TFBlocks.MANGROVE_GATE, TFBlocks.MANGROVE_PLANKS);
      this.gateBlock(output, "mining", TFBlocks.MINING_GATE, TFBlocks.MINING_PLANKS);
      this.gateBlock(output, "sorting", TFBlocks.SORTING_GATE, TFBlocks.SORTING_PLANKS);
      this.gateBlock(output, "time", TFBlocks.TIME_GATE, TFBlocks.TIME_PLANKS);
      this.gateBlock(output, "transformation", TFBlocks.TRANSFORMATION_GATE, TFBlocks.TRANSFORMATION_PLANKS);
      this.gateBlock(output, "twilight_oak", TFBlocks.TWILIGHT_OAK_GATE, TFBlocks.TWILIGHT_OAK_PLANKS);
      this.planksBlock(output, "canopy", TFBlocks.CANOPY_PLANKS, ItemTagGenerator.CANOPY_LOGS);
      this.planksBlock(output, "dark", TFBlocks.DARK_PLANKS, ItemTagGenerator.DARKWOOD_LOGS);
      this.planksBlock(output, "mangrove", TFBlocks.MANGROVE_PLANKS, ItemTagGenerator.MANGROVE_LOGS);
      this.planksBlock(output, "mining", TFBlocks.MINING_PLANKS, ItemTagGenerator.MINING_LOGS);
      this.planksBlock(output, "sorting", TFBlocks.SORTING_PLANKS, ItemTagGenerator.SORTING_LOGS);
      this.planksBlock(output, "time", TFBlocks.TIME_PLANKS, ItemTagGenerator.TIME_LOGS);
      this.planksBlock(output, "transformation", TFBlocks.TRANSFORMATION_PLANKS, ItemTagGenerator.TRANSFORMATION_LOGS);
      this.planksBlock(output, "twilight_oak", TFBlocks.TWILIGHT_OAK_PLANKS, ItemTagGenerator.TWILIGHT_OAK_LOGS);
      this.woodBlock(output, "canopy", TFBlocks.CANOPY_WOOD, TFBlocks.CANOPY_LOG);
      this.woodBlock(output, "dark", TFBlocks.DARK_WOOD, TFBlocks.DARK_LOG);
      this.woodBlock(output, "mangrove", TFBlocks.MANGROVE_WOOD, TFBlocks.MANGROVE_LOG);
      this.woodBlock(output, "mining", TFBlocks.MINING_WOOD, TFBlocks.MINING_LOG);
      this.woodBlock(output, "sorting", TFBlocks.SORTING_WOOD, TFBlocks.SORTING_LOG);
      this.woodBlock(output, "time", TFBlocks.TIME_WOOD, TFBlocks.TIME_LOG);
      this.woodBlock(output, "transformation", TFBlocks.TRANSFORMATION_WOOD, TFBlocks.TRANSFORMATION_LOG);
      this.woodBlock(output, "twilight_oak", TFBlocks.TWILIGHT_OAK_WOOD, TFBlocks.TWILIGHT_OAK_LOG);
      this.strippedWoodBlock(output, "canopy", TFBlocks.STRIPPED_CANOPY_WOOD, TFBlocks.STRIPPED_CANOPY_LOG);
      this.strippedWoodBlock(output, "dark", TFBlocks.STRIPPED_DARK_WOOD, TFBlocks.STRIPPED_DARK_LOG);
      this.strippedWoodBlock(output, "mangrove", TFBlocks.STRIPPED_MANGROVE_WOOD, TFBlocks.STRIPPED_MANGROVE_LOG);
      this.strippedWoodBlock(output, "mining", TFBlocks.STRIPPED_MINING_WOOD, TFBlocks.STRIPPED_MINING_LOG);
      this.strippedWoodBlock(output, "sorting", TFBlocks.STRIPPED_SORTING_WOOD, TFBlocks.STRIPPED_SORTING_LOG);
      this.strippedWoodBlock(output, "time", TFBlocks.STRIPPED_TIME_WOOD, TFBlocks.STRIPPED_TIME_LOG);
      this.strippedWoodBlock(output, "transformation", TFBlocks.STRIPPED_TRANSFORMATION_WOOD, TFBlocks.STRIPPED_TRANSFORMATION_LOG);
      this.strippedWoodBlock(output, "twilight_oak", TFBlocks.STRIPPED_TWILIGHT_OAK_WOOD, TFBlocks.STRIPPED_TWILIGHT_OAK_LOG);
      this.plateBlock(output, "canopy", TFBlocks.CANOPY_PLATE, TFBlocks.CANOPY_PLANKS);
      this.plateBlock(output, "dark", TFBlocks.DARK_PLATE, TFBlocks.DARK_PLANKS);
      this.plateBlock(output, "mangrove", TFBlocks.MANGROVE_PLATE, TFBlocks.MANGROVE_PLANKS);
      this.plateBlock(output, "mining", TFBlocks.MINING_PLATE, TFBlocks.MINING_PLANKS);
      this.plateBlock(output, "sorting", TFBlocks.SORTING_PLATE, TFBlocks.SORTING_PLANKS);
      this.plateBlock(output, "time", TFBlocks.TIME_PLATE, TFBlocks.TIME_PLANKS);
      this.plateBlock(output, "transformation", TFBlocks.TRANSFORMATION_PLATE, TFBlocks.TRANSFORMATION_PLANKS);
      this.plateBlock(output, "twilight_oak", TFBlocks.TWILIGHT_OAK_PLATE, TFBlocks.TWILIGHT_OAK_PLANKS);
      this.woodenSlabBlock(output, "canopy", TFBlocks.CANOPY_SLAB, TFBlocks.CANOPY_PLANKS);
      this.woodenSlabBlock(output, "dark", TFBlocks.DARK_SLAB, TFBlocks.DARK_PLANKS);
      this.woodenSlabBlock(output, "mangrove", TFBlocks.MANGROVE_SLAB, TFBlocks.MANGROVE_PLANKS);
      this.woodenSlabBlock(output, "mining", TFBlocks.MINING_SLAB, TFBlocks.MINING_PLANKS);
      this.woodenSlabBlock(output, "sorting", TFBlocks.SORTING_SLAB, TFBlocks.SORTING_PLANKS);
      this.woodenSlabBlock(output, "time", TFBlocks.TIME_SLAB, TFBlocks.TIME_PLANKS);
      this.woodenSlabBlock(output, "transformation", TFBlocks.TRANSFORMATION_SLAB, TFBlocks.TRANSFORMATION_PLANKS);
      this.woodenSlabBlock(output, "twilight_oak", TFBlocks.TWILIGHT_OAK_SLAB, TFBlocks.TWILIGHT_OAK_PLANKS);
      this.woodenStairsBlock(output, this.locWood("canopy_stairs"), TFBlocks.CANOPY_STAIRS, TFBlocks.CANOPY_PLANKS, (ItemLike)TFBlocks.CANOPY_PLANKS.get());
      this.woodenStairsBlock(output, this.locWood("dark_stairs"), TFBlocks.DARK_STAIRS, TFBlocks.DARK_PLANKS, (ItemLike)TFBlocks.DARK_PLANKS.get());
      this.woodenStairsBlock(
         output, this.locWood("mangrove_stairs"), TFBlocks.MANGROVE_STAIRS, TFBlocks.MANGROVE_PLANKS, (ItemLike)TFBlocks.MANGROVE_PLANKS.get()
      );
      this.woodenStairsBlock(output, this.locWood("mining_stairs"), TFBlocks.MINING_STAIRS, TFBlocks.MINING_PLANKS, (ItemLike)TFBlocks.MINING_PLANKS.get());
      this.woodenStairsBlock(output, this.locWood("sorting_stairs"), TFBlocks.SORTING_STAIRS, TFBlocks.SORTING_PLANKS, (ItemLike)TFBlocks.SORTING_PLANKS.get());
      this.woodenStairsBlock(output, this.locWood("time_stairs"), TFBlocks.TIME_STAIRS, TFBlocks.TIME_PLANKS, (ItemLike)TFBlocks.TIME_PLANKS.get());
      this.woodenStairsBlock(
         output,
         this.locWood("transformation_stairs"),
         TFBlocks.TRANSFORMATION_STAIRS,
         TFBlocks.TRANSFORMATION_PLANKS,
         (ItemLike)TFBlocks.TRANSFORMATION_PLANKS.get()
      );
      this.woodenStairsBlock(
         output, this.locWood("twilight_oak_stairs"), TFBlocks.TWILIGHT_OAK_STAIRS, TFBlocks.TWILIGHT_OAK_PLANKS, (ItemLike)TFBlocks.TWILIGHT_OAK_PLANKS.get()
      );
      this.trapdoorBlock(output, "canopy", TFBlocks.CANOPY_TRAPDOOR, TFBlocks.CANOPY_PLANKS);
      this.trapdoorBlock(output, "dark", TFBlocks.DARK_TRAPDOOR, TFBlocks.DARK_PLANKS);
      this.trapdoorBlock(output, "mangrove", TFBlocks.MANGROVE_TRAPDOOR, TFBlocks.MANGROVE_PLANKS);
      this.trapdoorBlock(output, "mining", TFBlocks.MINING_TRAPDOOR, TFBlocks.MINING_PLANKS);
      this.trapdoorBlock(output, "sorting", TFBlocks.SORTING_TRAPDOOR, TFBlocks.SORTING_PLANKS);
      this.trapdoorBlock(output, "time", TFBlocks.TIME_TRAPDOOR, TFBlocks.TIME_PLANKS);
      this.trapdoorBlock(output, "transformation", TFBlocks.TRANSFORMATION_TRAPDOOR, TFBlocks.TRANSFORMATION_PLANKS);
      this.trapdoorBlock(output, "twilight_oak", TFBlocks.TWILIGHT_OAK_TRAPDOOR, TFBlocks.TWILIGHT_OAK_PLANKS);
      this.signBlock(output, "canopy", TFItems.CANOPY_SIGN, TFBlocks.CANOPY_PLANKS);
      this.signBlock(output, "dark", TFItems.DARK_SIGN, TFBlocks.DARK_PLANKS);
      this.signBlock(output, "mangrove", TFItems.MANGROVE_SIGN, TFBlocks.MANGROVE_PLANKS);
      this.signBlock(output, "mining", TFItems.MINING_SIGN, TFBlocks.MINING_PLANKS);
      this.signBlock(output, "sorting", TFItems.SORTING_SIGN, TFBlocks.SORTING_PLANKS);
      this.signBlock(output, "time", TFItems.TIME_SIGN, TFBlocks.TIME_PLANKS);
      this.signBlock(output, "transformation", TFItems.TRANSFORMATION_SIGN, TFBlocks.TRANSFORMATION_PLANKS);
      this.signBlock(output, "twilight_oak", TFItems.TWILIGHT_OAK_SIGN, TFBlocks.TWILIGHT_OAK_PLANKS);
      this.hangingSignBlock(output, "canopy", TFItems.CANOPY_HANGING_SIGN, TFBlocks.STRIPPED_CANOPY_LOG);
      this.hangingSignBlock(output, "dark", TFItems.DARK_HANGING_SIGN, TFBlocks.STRIPPED_DARK_LOG);
      this.hangingSignBlock(output, "mangrove", TFItems.MANGROVE_HANGING_SIGN, TFBlocks.STRIPPED_MANGROVE_LOG);
      this.hangingSignBlock(output, "mining", TFItems.MINING_HANGING_SIGN, TFBlocks.STRIPPED_MINING_LOG);
      this.hangingSignBlock(output, "sorting", TFItems.SORTING_HANGING_SIGN, TFBlocks.STRIPPED_SORTING_LOG);
      this.hangingSignBlock(output, "time", TFItems.TIME_HANGING_SIGN, TFBlocks.STRIPPED_TIME_LOG);
      this.hangingSignBlock(output, "transformation", TFItems.TRANSFORMATION_HANGING_SIGN, TFBlocks.STRIPPED_TRANSFORMATION_LOG);
      this.hangingSignBlock(output, "twilight_oak", TFItems.TWILIGHT_OAK_HANGING_SIGN, TFBlocks.STRIPPED_TWILIGHT_OAK_LOG);
      this.banisterBlock(output, "canopy", TFBlocks.CANOPY_BANISTER, TFBlocks.CANOPY_SLAB);
      this.banisterBlock(output, "dark", TFBlocks.DARK_BANISTER, TFBlocks.DARK_SLAB);
      this.banisterBlock(output, "mangrove", TFBlocks.MANGROVE_BANISTER, TFBlocks.MANGROVE_SLAB);
      this.banisterBlock(output, "mining", TFBlocks.MINING_BANISTER, TFBlocks.MINING_SLAB);
      this.banisterBlock(output, "sorting", TFBlocks.SORTING_BANISTER, TFBlocks.SORTING_SLAB);
      this.banisterBlock(output, "time", TFBlocks.TIME_BANISTER, TFBlocks.TIME_SLAB);
      this.banisterBlock(output, "transformation", TFBlocks.TRANSFORMATION_BANISTER, TFBlocks.TRANSFORMATION_SLAB);
      this.banisterBlock(output, "twilight_oak", TFBlocks.TWILIGHT_OAK_BANISTER, TFBlocks.TWILIGHT_OAK_SLAB);
      this.banisterBlock(output, "oak", TFBlocks.OAK_BANISTER, Blocks.OAK_SLAB);
      this.banisterBlock(output, "spruce", TFBlocks.SPRUCE_BANISTER, Blocks.SPRUCE_SLAB);
      this.banisterBlock(output, "birch", TFBlocks.BIRCH_BANISTER, Blocks.BIRCH_SLAB);
      this.banisterBlock(output, "jungle", TFBlocks.JUNGLE_BANISTER, Blocks.JUNGLE_SLAB);
      this.banisterBlock(output, "acacia", TFBlocks.ACACIA_BANISTER, Blocks.ACACIA_SLAB);
      this.banisterBlock(output, "dark_oak", TFBlocks.DARK_OAK_BANISTER, Blocks.DARK_OAK_SLAB);
      this.banisterBlock(output, "crimson", TFBlocks.CRIMSON_BANISTER, Blocks.CRIMSON_SLAB);
      this.banisterBlock(output, "warped", TFBlocks.WARPED_BANISTER, Blocks.WARPED_SLAB);
      this.banisterBlock(output, "vangrove", TFBlocks.VANGROVE_BANISTER, Blocks.MANGROVE_SLAB);
      this.banisterBlock(output, "bamboo", TFBlocks.BAMBOO_BANISTER, Blocks.BAMBOO_SLAB);
      this.banisterBlock(output, "cherry", TFBlocks.CHERRY_BANISTER, Blocks.CHERRY_SLAB);
      this.dryingRackBlock(output, "canopy", TFBlocks.CANOPY_DRYING_RACK, TFBlocks.CANOPY_SLAB);
      this.dryingRackBlock(output, "dark", TFBlocks.DARK_DRYING_RACK, TFBlocks.DARK_SLAB);
      this.dryingRackBlock(output, "mangrove", TFBlocks.MANGROVE_DRYING_RACK, TFBlocks.MANGROVE_SLAB);
      this.dryingRackBlock(output, "mining", TFBlocks.MINING_DRYING_RACK, TFBlocks.MINING_SLAB);
      this.dryingRackBlock(output, "sorting", TFBlocks.SORTING_DRYING_RACK, TFBlocks.SORTING_SLAB);
      this.dryingRackBlock(output, "time", TFBlocks.TIME_DRYING_RACK, TFBlocks.TIME_SLAB);
      this.dryingRackBlock(output, "transformation", TFBlocks.TRANSFORMATION_DRYING_RACK, TFBlocks.TRANSFORMATION_SLAB);
      this.dryingRackBlock(output, "twilight_oak", TFBlocks.TWILIGHT_OAK_DRYING_RACK, TFBlocks.TWILIGHT_OAK_SLAB);
      this.dryingRackBlock(output, "oak", TFBlocks.OAK_DRYING_RACK, Blocks.OAK_SLAB);
      this.dryingRackBlock(output, "spruce", TFBlocks.SPRUCE_DRYING_RACK, Blocks.SPRUCE_SLAB);
      this.dryingRackBlock(output, "birch", TFBlocks.BIRCH_DRYING_RACK, Blocks.BIRCH_SLAB);
      this.dryingRackBlock(output, "jungle", TFBlocks.JUNGLE_DRYING_RACK, Blocks.JUNGLE_SLAB);
      this.dryingRackBlock(output, "acacia", TFBlocks.ACACIA_DRYING_RACK, Blocks.ACACIA_SLAB);
      this.dryingRackBlock(output, "dark_oak", TFBlocks.DARK_OAK_DRYING_RACK, Blocks.DARK_OAK_SLAB);
      this.dryingRackBlock(output, "crimson", TFBlocks.CRIMSON_DRYING_RACK, Blocks.CRIMSON_SLAB);
      this.dryingRackBlock(output, "warped", TFBlocks.WARPED_DRYING_RACK, Blocks.WARPED_SLAB);
      this.dryingRackBlock(output, "vangrove", TFBlocks.VANGROVE_DRYING_RACK, Blocks.MANGROVE_SLAB);
      this.dryingRackBlock(output, "bamboo", TFBlocks.BAMBOO_DRYING_RACK, Blocks.BAMBOO_SLAB);
      this.dryingRackBlock(output, "cherry", TFBlocks.CHERRY_DRYING_RACK, Blocks.CHERRY_SLAB);
      this.chestBlock(output, "twilight_oak", TFBlocks.TWILIGHT_OAK_CHEST, TFBlocks.TWILIGHT_OAK_TRAPPED_CHEST, TFBlocks.TWILIGHT_OAK_PLANKS);
      this.chestBlock(output, "canopy", TFBlocks.CANOPY_CHEST, TFBlocks.CANOPY_TRAPPED_CHEST, TFBlocks.CANOPY_PLANKS);
      this.chestBlock(output, "mangrove", TFBlocks.MANGROVE_CHEST, TFBlocks.MANGROVE_TRAPPED_CHEST, TFBlocks.MANGROVE_PLANKS);
      this.chestBlock(output, "dark", TFBlocks.DARK_CHEST, TFBlocks.DARK_TRAPPED_CHEST, TFBlocks.DARK_PLANKS);
      this.chestBlock(output, "time", TFBlocks.TIME_CHEST, TFBlocks.TIME_TRAPPED_CHEST, TFBlocks.TIME_PLANKS);
      this.chestBlock(output, "transformation", TFBlocks.TRANSFORMATION_CHEST, TFBlocks.TRANSFORMATION_TRAPPED_CHEST, TFBlocks.TRANSFORMATION_PLANKS);
      this.chestBlock(output, "mining", TFBlocks.MINING_CHEST, TFBlocks.MINING_TRAPPED_CHEST, TFBlocks.MINING_PLANKS);
      this.chestBlock(output, "sorting", TFBlocks.SORTING_CHEST, TFBlocks.SORTING_TRAPPED_CHEST, TFBlocks.SORTING_PLANKS);
      this.buildBoats(output, TFItems.TWILIGHT_OAK_BOAT, TFItems.TWILIGHT_OAK_CHEST_BOAT, TFBlocks.TWILIGHT_OAK_PLANKS);
      this.buildBoats(output, TFItems.CANOPY_BOAT, TFItems.CANOPY_CHEST_BOAT, TFBlocks.CANOPY_PLANKS);
      this.buildBoats(output, TFItems.MANGROVE_BOAT, TFItems.MANGROVE_CHEST_BOAT, TFBlocks.MANGROVE_PLANKS);
      this.buildBoats(output, TFItems.DARK_BOAT, TFItems.DARK_CHEST_BOAT, TFBlocks.DARK_PLANKS);
      this.buildBoats(output, TFItems.TIME_BOAT, TFItems.TIME_CHEST_BOAT, TFBlocks.TIME_PLANKS);
      this.buildBoats(output, TFItems.TRANSFORMATION_BOAT, TFItems.TRANSFORMATION_CHEST_BOAT, TFBlocks.TRANSFORMATION_PLANKS);
      this.buildBoats(output, TFItems.MINING_BOAT, TFItems.MINING_CHEST_BOAT, TFBlocks.MINING_PLANKS);
      this.buildBoats(output, TFItems.SORTING_BOAT, TFItems.SORTING_CHEST_BOAT, TFBlocks.SORTING_PLANKS);
   }

   private void nagastoneRecipes(RecipeOutput output) {
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)TFBlocks.SPIRAL_BRICKS.get(), 8)
         .pattern("BSS")
         .pattern("BSS")
         .pattern("BBB")
         .define('B', Ingredient.of(new ItemLike[]{Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS}))
         .define('S', Ingredient.of(new ItemLike[]{Blocks.STONE_SLAB, Blocks.STONE_BRICK_SLAB}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.SPIRAL_BRICKS.get()))
         .save(output, this.locNaga("nagastone_spiral"));
      this.stairsBlock(
         output, this.locNaga("nagastone_stairs_left"), TFBlocks.NAGASTONE_STAIRS_LEFT, TFBlocks.ETCHED_NAGASTONE, (ItemLike)TFBlocks.ETCHED_NAGASTONE.get()
      );
      this.stairsRightBlock(
         output, this.locNaga("nagastone_stairs_right"), TFBlocks.NAGASTONE_STAIRS_RIGHT, TFBlocks.ETCHED_NAGASTONE, (ItemLike)TFBlocks.ETCHED_NAGASTONE.get()
      );
      this.stairsBlock(
         output,
         this.locNaga("mossy_nagastone_stairs_left"),
         TFBlocks.MOSSY_NAGASTONE_STAIRS_LEFT,
         TFBlocks.MOSSY_ETCHED_NAGASTONE,
         (ItemLike)TFBlocks.MOSSY_ETCHED_NAGASTONE.get()
      );
      this.stairsRightBlock(
         output,
         this.locNaga("mossy_nagastone_stairs_right"),
         TFBlocks.MOSSY_NAGASTONE_STAIRS_RIGHT,
         TFBlocks.MOSSY_ETCHED_NAGASTONE,
         (ItemLike)TFBlocks.MOSSY_ETCHED_NAGASTONE.get()
      );
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)TFBlocks.MOSSY_ETCHED_NAGASTONE.get(), 1)
         .requires(Ingredient.of(new ItemLike[]{Blocks.VINE, Blocks.MOSS_BLOCK}))
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.ETCHED_NAGASTONE.get()}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.ETCHED_NAGASTONE.get()))
         .save(output, this.locNaga("mossy_etched_nagastone"));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)TFBlocks.MOSSY_NAGASTONE_PILLAR.get(), 1)
         .requires(Ingredient.of(new ItemLike[]{Blocks.VINE, Blocks.MOSS_BLOCK}))
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.NAGASTONE_PILLAR.get()}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.NAGASTONE_PILLAR.get()))
         .save(output, this.locNaga("mossy_nagastone_pillar"));
      this.stairsBlock(
         output,
         this.locNaga("cracked_nagastone_stairs_left"),
         TFBlocks.CRACKED_NAGASTONE_STAIRS_LEFT,
         TFBlocks.CRACKED_ETCHED_NAGASTONE,
         (ItemLike)TFBlocks.CRACKED_ETCHED_NAGASTONE.get()
      );
      this.stairsRightBlock(
         output,
         this.locNaga("cracked_nagastone_stairs_right"),
         TFBlocks.CRACKED_NAGASTONE_STAIRS_RIGHT,
         TFBlocks.CRACKED_ETCHED_NAGASTONE,
         (ItemLike)TFBlocks.CRACKED_ETCHED_NAGASTONE.get()
      );
   }

   private void castleRecipes(RecipeOutput output) {
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)TFBlocks.MOSSY_CASTLE_BRICK.get(), 1)
         .requires(Ingredient.of(new ItemLike[]{Blocks.VINE, Blocks.MOSS_BLOCK}))
         .requires(Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.CASTLE_BRICK.get()}))
         .unlockedBy("has_item", has((ItemLike)TFBlocks.CASTLE_BRICK.get()))
         .save(output, this.locCastle("mossy_castle_brick"));
      this.castleBlock(
         output,
         TFBlocks.THICK_CASTLE_BRICK,
         (ItemLike)TFBlocks.CASTLE_BRICK.get(),
         (ItemLike)TFBlocks.WORN_CASTLE_BRICK.get(),
         (ItemLike)TFBlocks.CRACKED_CASTLE_BRICK.get(),
         (ItemLike)TFBlocks.MOSSY_CASTLE_BRICK.get()
      );
      this.castleBlock(output, TFBlocks.BOLD_CASTLE_BRICK_PILLAR, (ItemLike)TFBlocks.THICK_CASTLE_BRICK.get());
      this.castleBlock(output, TFBlocks.BOLD_CASTLE_BRICK_TILE, (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get());
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get(), 4)
         .pattern("##")
         .pattern("##")
         .define('#', Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.BOLD_CASTLE_BRICK_TILE.get()}))
         .unlockedBy("has_castle_brick", has((ItemLike)TFBlocks.CASTLE_BRICK.get()))
         .save(output, this.locCastle("bold_castle_pillar_from_tile"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.get(), 6)
         .pattern("#H#")
         .pattern("#H#")
         .define(
            '#',
            Ingredient.of(
               new ItemLike[]{
                  (ItemLike)TFBlocks.CASTLE_BRICK.get(),
                  (ItemLike)TFBlocks.WORN_CASTLE_BRICK.get(),
                  (ItemLike)TFBlocks.CRACKED_CASTLE_BRICK.get(),
                  (ItemLike)TFBlocks.MOSSY_CASTLE_BRICK.get(),
                  (ItemLike)TFBlocks.THICK_CASTLE_BRICK.get()
               }
            )
         )
         .define(
            'H',
            Ingredient.of(
               new ItemLike[]{
                  (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.get(),
                  (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_TILE.get(),
                  (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get(),
                  (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_TILE.get()
               }
            )
         )
         .unlockedBy("has_castle_brick", has((ItemLike)TFBlocks.CASTLE_BRICK.get()))
         .save(output, this.locCastle("encased_castle_pillar"));
      this.stairsBlock(
         output,
         this.locCastle("bold_castle_brick_stairs"),
         TFBlocks.BOLD_CASTLE_BRICK_STAIRS,
         TFBlocks.BOLD_CASTLE_BRICK_PILLAR,
         (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get(),
         (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_TILE.get()
      );
      this.stairsBlock(
         output, this.locCastle("castle_brick_stairs"), TFBlocks.CASTLE_BRICK_STAIRS, TFBlocks.CASTLE_BRICK, (ItemLike)TFBlocks.CASTLE_BRICK.get()
      );
      this.stairsBlock(
         output,
         this.locCastle("cracked_castle_brick_stairs"),
         TFBlocks.CRACKED_CASTLE_BRICK_STAIRS,
         TFBlocks.CRACKED_CASTLE_BRICK,
         (ItemLike)TFBlocks.CRACKED_CASTLE_BRICK.get()
      );
      this.stairsBlock(
         output,
         this.locCastle("encased_castle_brick_stairs"),
         TFBlocks.ENCASED_CASTLE_BRICK_STAIRS,
         TFBlocks.ENCASED_CASTLE_BRICK_PILLAR,
         (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.get(),
         (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_TILE.get()
      );
      this.stairsBlock(
         output,
         this.locCastle("mossy_castle_brick_stairs"),
         TFBlocks.MOSSY_CASTLE_BRICK_STAIRS,
         TFBlocks.MOSSY_CASTLE_BRICK,
         (ItemLike)TFBlocks.MOSSY_CASTLE_BRICK.get()
      );
      this.stairsBlock(
         output,
         this.locCastle("worn_castle_brick_stairs"),
         TFBlocks.WORN_CASTLE_BRICK_STAIRS,
         TFBlocks.WORN_CASTLE_BRICK,
         (ItemLike)TFBlocks.WORN_CASTLE_BRICK.get()
      );
   }

   private void fieryConversions(RecipeOutput output) {
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)TFItems.FIERY_INGOT.get())
         .requires(Ingredient.of(ItemTagGenerator.FIERY_VIAL))
         .requires(Ingredient.of(Items.INGOTS_IRON))
         .unlockedBy("has_item", has(ItemTagGenerator.FIERY_VIAL))
         .group("fiery_ingot")
         .save(output, this.locEquip("fiery_ingot_crafting"));
      this.fieryConversion(output, TFItems.FIERY_HELMET, net.minecraft.world.item.Items.IRON_HELMET, 5);
      this.fieryConversion(output, TFItems.FIERY_CHESTPLATE, net.minecraft.world.item.Items.IRON_CHESTPLATE, 8);
      this.fieryConversion(output, TFItems.FIERY_LEGGINGS, net.minecraft.world.item.Items.IRON_LEGGINGS, 7);
      this.fieryConversion(output, TFItems.FIERY_BOOTS, net.minecraft.world.item.Items.IRON_BOOTS, 4);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, (ItemLike)TFItems.FIERY_SWORD.get())
         .requires(net.minecraft.world.item.Items.IRON_SWORD)
         .requires(Ingredient.of(ItemTagGenerator.FIERY_VIAL), 2)
         .requires(Ingredient.of(Items.RODS_BLAZE))
         .unlockedBy("has_item", has(ItemTagGenerator.FIERY_VIAL))
         .group("fiery_sword")
         .save(output, this.locEquip("fiery_" + BuiltInRegistries.ITEM.getKey(net.minecraft.world.item.Items.IRON_SWORD).getPath()));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, (ItemLike)TFItems.FIERY_PICKAXE.get())
         .requires(net.minecraft.world.item.Items.IRON_PICKAXE)
         .requires(Ingredient.of(ItemTagGenerator.FIERY_VIAL), 3)
         .requires(Ingredient.of(Items.RODS_BLAZE), 2)
         .unlockedBy("has_item", has(ItemTagGenerator.FIERY_VIAL))
         .group("fiery_pickaxe")
         .save(output, this.locEquip("fiery_" + BuiltInRegistries.ITEM.getKey(net.minecraft.world.item.Items.IRON_PICKAXE).getPath()));
   }

   private <T extends AbstractCookingRecipe> void cookingRecipes(
      RecipeOutput output, String processName, RecipeSerializer<T> process, Factory<T> factory, int smeltingTime
   ) {
      SimpleCookingRecipeBuilder.generic(
            Ingredient.of(new ItemLike[]{(ItemLike)TFItems.RAW_MEEF.get()}),
            RecipeCategory.FOOD,
            (ItemLike)TFItems.COOKED_MEEF.get(),
            0.35F,
            smeltingTime,
            process,
            factory
         )
         .unlockedBy("has_food", has((ItemLike)TFItems.RAW_MEEF.get()))
         .save(output, TwilightForestMod.prefix("food/" + processName + "_meef").toString());
      SimpleCookingRecipeBuilder.generic(
            Ingredient.of(new ItemLike[]{(ItemLike)TFItems.RAW_VENISON.get()}),
            RecipeCategory.FOOD,
            (ItemLike)TFItems.COOKED_VENISON.get(),
            0.35F,
            smeltingTime,
            process,
            factory
         )
         .unlockedBy("has_food", has((ItemLike)TFItems.RAW_VENISON.get()))
         .save(output, TwilightForestMod.prefix("food/" + processName + "_venison").toString());
   }

   private <T extends AbstractCookingRecipe> void ingotRecipes(
      RecipeOutput output, String processName, RecipeSerializer<T> process, Factory<T> factory, int smeltingTime
   ) {
      SimpleCookingRecipeBuilder.generic(
            Ingredient.of(new ItemLike[]{(ItemLike)TFItems.ARMOR_SHARD_CLUSTER.get()}),
            RecipeCategory.MISC,
            (ItemLike)TFItems.KNIGHTMETAL_INGOT.get(),
            1.0F,
            smeltingTime,
            process,
            factory
         )
         .unlockedBy("has_item", has((ItemLike)TFItems.ARMOR_SHARD_CLUSTER.get()))
         .group("knightmetal_ingot")
         .save(output, TwilightForestMod.prefix("material/" + processName + "_knightmetal_ingot").toString());
      SimpleCookingRecipeBuilder.generic(
            Ingredient.of(new ItemLike[]{(ItemLike)TFItems.RAW_IRONWOOD.get()}),
            RecipeCategory.MISC,
            (ItemLike)TFItems.IRONWOOD_INGOT.get(),
            1.0F,
            smeltingTime,
            process,
            factory
         )
         .unlockedBy("has_item", has((ItemLike)TFItems.RAW_IRONWOOD.get()))
         .group("ironwood_ingot")
         .save(output, TwilightForestMod.prefix("material/" + processName + "_ironwood_ingot").toString());
   }

   private <T extends AbstractCookingRecipe> void oreberryRecipes(
      RecipeOutput output, String processName, RecipeSerializer<T> process, Factory<T> factory, int smeltingTime
   ) {
      SimpleCookingRecipeBuilder.generic(
            Ingredient.of(new ItemLike[]{(ItemLike)TFItems.IRON_BERRY.get()}),
            RecipeCategory.MISC,
            net.minecraft.world.item.Items.IRON_NUGGET,
            1.0F,
            smeltingTime,
            process,
            factory
         )
         .unlockedBy("has_item", has((ItemLike)TFItems.IRON_BERRY.get()))
         .group("iron_nugget")
         .save(output, TwilightForestMod.prefix("material/" + processName + "_iron_nugget").toString());
      SimpleCookingRecipeBuilder.generic(
            Ingredient.of(new ItemLike[]{(ItemLike)TFItems.GOLD_BERRY.get()}),
            RecipeCategory.MISC,
            net.minecraft.world.item.Items.GOLD_NUGGET,
            1.0F,
            smeltingTime,
            process,
            factory
         )
         .unlockedBy("has_item", has((ItemLike)TFItems.GOLD_BERRY.get()))
         .group("gold_nugget")
         .save(output, TwilightForestMod.prefix("material/" + processName + "_gold_nugget").toString());
      SimpleCookingRecipeBuilder.generic(
            Ingredient.of(new ItemLike[]{(ItemLike)TFItems.COPPER_BERRY.get()}),
            RecipeCategory.MISC,
            TFItems.COPPER_NUGGET,
            1.0F,
            smeltingTime,
            process,
            factory
         )
         .unlockedBy("has_item", has((ItemLike)TFItems.COPPER_BERRY.get()))
         .group("copper_nugget")
         .save(output, TwilightForestMod.prefix("material/" + processName + "_copper_nugget").toString());
   }

   private void DryingRackCoralRecipes(RecipeOutput output) {
      for (String name : CORAL_SPECIES) {
         for (String type : CORAL_TYPES) {
            this.registerCoral(output, name + "_coral" + type);
         }
      }
   }

   private void registerCoral(RecipeOutput output, String coral) {
      DryingRecipeBuilder.drying(
            (ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(coral)),
            (ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(coral).withPrefix("dead_")),
            0.033333335F
         )
         .unlockedBy("has_coral", has((ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(coral))))
         .save(output);
   }

   private void travellersGearRecipes(RecipeOutput output) {
      ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, TFItems.TRAVELLERS_GOGGLES)
         .pattern("l l")
         .pattern("plp")
         .pattern("g g")
         .define('l', TFItems.TANNED_LEATHER)
         .define('p', Items.GLASS_PANES_COLORLESS)
         .define('g', Items.INGOTS_GOLD)
         .unlockedBy("has_leather", has(TFItems.TANNED_LEATHER))
         .save(output, this.locEquip(TFItems.TRAVELLERS_GOGGLES.getId().getPath()));
      ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, TFItems.TRAVELLERS_VEST)
         .pattern("l l")
         .pattern("lwl")
         .pattern("lwl")
         .define('l', TFItems.TANNED_LEATHER)
         .define('w', ItemTags.WOOL)
         .unlockedBy("has_leather", has(TFItems.TANNED_LEATHER))
         .save(output, this.locEquip(TFItems.TRAVELLERS_VEST.getId().getPath()));
      ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, TFItems.TRAVELLERS_WINGS)
         .pattern("g g")
         .pattern("ili")
         .pattern("ici")
         .define('l', TFItems.TANNED_LEATHER)
         .define('g', Items.INGOTS_GOLD)
         .define('i', Items.INGOTS_COPPER)
         .define('c', ItemTagGenerator.CARMINITE_GEMS)
         .unlockedBy("has_leather", has(TFItems.TANNED_LEATHER))
         .save(output, this.locEquip(TFItems.TRAVELLERS_WINGS.getId().getPath()));
      ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, TFItems.TRAVELLERS_BOOTS)
         .pattern("s s")
         .pattern("l l")
         .pattern("l l")
         .define('l', TFItems.TANNED_LEATHER)
         .define('s', Items.STRINGS)
         .unlockedBy("has_leather", has(TFItems.TANNED_LEATHER))
         .save(output, this.locEquip(TFItems.TRAVELLERS_BOOTS.getId().getPath()));
      ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, TFItems.TRAVELLERS_BELT)
         .pattern("lll")
         .pattern("ixi")
         .pattern("lll")
         .define('l', TFItems.TANNED_LEATHER)
         .define('x', DifferenceIngredient.of(Ingredient.of(Items.CHESTS_WOODEN), Ingredient.of(Items.CHESTS_TRAPPED)))
         .define('i', Items.NUGGETS_IRON)
         .unlockedBy("has_leather", has(TFItems.TANNED_LEATHER))
         .save(output, this.locEquip(TFItems.TRAVELLERS_BELT.getId().getPath()));
      ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, TFItems.TRAVELLERS_GLOVES)
         .pattern("s s")
         .pattern("l l")
         .define('l', TFItems.TANNED_LEATHER)
         .define('s', Items.STRINGS)
         .unlockedBy("has_leather", has(TFItems.TANNED_LEATHER))
         .save(output, this.locEquip(TFItems.TRAVELLERS_GLOVES.getId().getPath()));
   }

   private void crackedWoodRecipes(RecipeOutput output) {
      SimpleCookingRecipeBuilder.smoking(
            Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.TOWERWOOD.get()}),
            RecipeCategory.BUILDING_BLOCKS,
            (ItemLike)TFBlocks.CRACKED_TOWERWOOD.get(),
            0.1F,
            100
         )
         .unlockedBy("has_item", has((ItemLike)TFBlocks.TOWERWOOD.get()))
         .save(output, TwilightForestMod.prefix("wood/smoked_cracked_towerwood").toString());
   }

   private void crackedStoneRecipes(RecipeOutput output) {
      SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.NAGASTONE_PILLAR.get()}),
            RecipeCategory.BUILDING_BLOCKS,
            (ItemLike)TFBlocks.CRACKED_NAGASTONE_PILLAR.get(),
            0.1F,
            200
         )
         .unlockedBy("has_item", has((ItemLike)TFBlocks.NAGASTONE_PILLAR.get()))
         .save(output, TwilightForestMod.prefix("nagastone/smelted_cracked_nagastone_pillar").toString());
      SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.ETCHED_NAGASTONE.get()}),
            RecipeCategory.BUILDING_BLOCKS,
            (ItemLike)TFBlocks.CRACKED_ETCHED_NAGASTONE.get(),
            0.1F,
            200
         )
         .unlockedBy("has_item", has((ItemLike)TFBlocks.ETCHED_NAGASTONE.get()))
         .save(output, TwilightForestMod.prefix("nagastone/smelted_cracked_etched_nagastone").toString());
      SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.MAZESTONE_BRICK.get()}),
            RecipeCategory.BUILDING_BLOCKS,
            (ItemLike)TFBlocks.CRACKED_MAZESTONE.get(),
            0.1F,
            200
         )
         .unlockedBy("has_item", has((ItemLike)TFBlocks.MAZESTONE_BRICK.get()))
         .save(output, TwilightForestMod.prefix("maze_stone/smelted_maze_stone_cracked").toString());
      SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.CASTLE_BRICK.get()}),
            RecipeCategory.BUILDING_BLOCKS,
            (ItemLike)TFBlocks.CRACKED_CASTLE_BRICK.get(),
            0.1F,
            200
         )
         .unlockedBy("has_item", has((ItemLike)TFBlocks.CASTLE_BRICK.get()))
         .save(output, TwilightForestMod.prefix("castleblock/smelted_cracked_castle_brick").toString());
      SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(new ItemLike[]{(ItemLike)TFBlocks.UNDERBRICK.get()}),
            RecipeCategory.BUILDING_BLOCKS,
            (ItemLike)TFBlocks.CRACKED_UNDERBRICK.get(),
            0.1F,
            200
         )
         .unlockedBy("has_item", has((ItemLike)TFBlocks.UNDERBRICK.get()))
         .save(output, TwilightForestMod.prefix("smelted_cracked_underbrick").toString());
   }

   @SafeVarargs
   @NotNull
   private static Ingredient potionsIngredient(Holder<Potion> @NotNull ... potions) {
      Ingredient[] ingredients = new Ingredient[potions.length];

      for (int i = 0; i < potions.length; i++) {
         ingredients[i] = potionIngredient(potions[i]);
      }

      return CompoundIngredient.of(ingredients);
   }

   @NotNull
   private static Ingredient potionIngredient(@NotNull Holder<Potion> potion) {
      return DataComponentIngredient.of(
         false, DataComponents.POTION_CONTENTS, new PotionContents(potion), new ItemLike[]{net.minecraft.world.item.Items.POTION}
      );
   }
}
