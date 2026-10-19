package net.p3pp3rf1y.sophisticatedbackpacks.data;

import earth.terrarium.chipped.common.registry.ModBlocks;
import java.util.concurrent.CompletableFuture;
import net.mehvahdjukaar.sawmill.SawmillMod;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemPredicate.Builder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags.Items;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import net.p3pp3rf1y.sophisticatedbackpacks.compat.chipped.ChippedCompat;
import net.p3pp3rf1y.sophisticatedbackpacks.compat.sawmill.SawmillCompat;
import net.p3pp3rf1y.sophisticatedbackpacks.crafting.BackpackDyeRecipe;
import net.p3pp3rf1y.sophisticatedbackpacks.crafting.BackpackUpgradeRecipe;
import net.p3pp3rf1y.sophisticatedbackpacks.crafting.BasicBackpackRecipe;
import net.p3pp3rf1y.sophisticatedbackpacks.crafting.SmithingBackpackUpgradeRecipe;
import net.p3pp3rf1y.sophisticatedbackpacks.crafting.SmithingBackpackUpgradeRecipeBuilder;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.crafting.ShapeBasedRecipeBuilder;
import net.p3pp3rf1y.sophisticatedcore.crafting.UpgradeNextTierRecipe;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.util.RegistryHelper;

public class SBPRecipeProvider extends RecipeProvider {
   private static final String HAS_UPGRADE_BASE = "has_upgrade_base";
   private static final String HAS_SMELTING_UPGRADE = "has_smelting_upgrade";

   public SBPRecipeProvider(PackOutput packOutput, CompletableFuture<Provider> registries) {
      super(packOutput, registries);
   }

   protected void buildRecipes(RecipeOutput recipeOutput) {
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.BACKPACK.get(), BasicBackpackRecipe::new)
         .pattern("SLS")
         .pattern("SCS")
         .pattern("LLL")
         .define('L', Items.LEATHERS)
         .define('C', Items.CHESTS_WOODEN)
         .define('S', Items.STRINGS)
         .unlockedBy("has_leather", hasLeather())
         .save(recipeOutput);
      SpecialRecipeBuilder.special(BackpackDyeRecipe::new).save(recipeOutput, SophisticatedBackpacks.getRegistryName("backpack_dye"));
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.DIAMOND_BACKPACK.get(), BackpackUpgradeRecipe::new)
         .pattern("DDD")
         .pattern("DBD")
         .pattern("DDD")
         .define('D', Items.GEMS_DIAMOND)
         .define('B', (ItemLike)ModItems.GOLD_BACKPACK.get())
         .unlockedBy("has_gold_backpack", has((ItemLike)ModItems.GOLD_BACKPACK.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.GOLD_BACKPACK.get(), BackpackUpgradeRecipe::new)
         .pattern("GGG")
         .pattern("GBG")
         .pattern("GGG")
         .define('G', Items.INGOTS_GOLD)
         .define('B', (ItemLike)ModItems.IRON_BACKPACK.get())
         .unlockedBy("has_iron_backpack", has((ItemLike)ModItems.IRON_BACKPACK.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.IRON_BACKPACK.get(), BackpackUpgradeRecipe::new)
         .pattern("III")
         .pattern("IBI")
         .pattern("III")
         .define('I', Items.INGOTS_IRON)
         .define('B', (ItemLike)ModItems.BACKPACK.get())
         .unlockedBy("has_backpack", has((ItemLike)ModItems.BACKPACK.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.IRON_BACKPACK.get(), BackpackUpgradeRecipe::new)
         .pattern(" I ")
         .pattern("IBI")
         .pattern(" I ")
         .define('I', Items.INGOTS_IRON)
         .define('B', (ItemLike)ModItems.COPPER_BACKPACK.get())
         .unlockedBy("has_copper_backpack", has((ItemLike)ModItems.COPPER_BACKPACK.get()))
         .save(recipeOutput, SophisticatedBackpacks.getRL("iron_backpack_from_copper"));
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.COPPER_BACKPACK.get(), BackpackUpgradeRecipe::new)
         .pattern("CCC")
         .pattern("CBC")
         .pattern("CCC")
         .define('C', Items.INGOTS_COPPER)
         .define('B', (ItemLike)ModItems.BACKPACK.get())
         .unlockedBy("has_backpack", has((ItemLike)ModItems.BACKPACK.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.PICKUP_UPGRADE.get())
         .pattern(" P ")
         .pattern("SBS")
         .pattern("RRR")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('R', Items.DUSTS_REDSTONE)
         .define('S', Items.STRINGS)
         .define('P', Blocks.STICKY_PISTON)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.UPGRADE_BASE.get())
         .pattern("SIS")
         .pattern("ILI")
         .pattern("SIS")
         .define('L', Items.LEATHERS)
         .define('I', Items.INGOTS_IRON)
         .define('S', Items.STRINGS)
         .unlockedBy("has_leather", hasLeather())
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ADVANCED_PICKUP_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern(" D ")
         .pattern("GPG")
         .pattern("RRR")
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('P', (ItemLike)ModItems.PICKUP_UPGRADE.get())
         .unlockedBy("has_pickup_upgrade", has((ItemLike)ModItems.PICKUP_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.FILTER_UPGRADE.get())
         .pattern("RSR")
         .pattern("SBS")
         .pattern("RSR")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('R', Items.DUSTS_REDSTONE)
         .define('S', Items.STRINGS)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ADVANCED_FILTER_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern("GPG")
         .pattern("RRR")
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('P', (ItemLike)ModItems.FILTER_UPGRADE.get())
         .unlockedBy("has_filter_upgrade", has((ItemLike)ModItems.FILTER_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.MAGNET_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern("EIE")
         .pattern("IPI")
         .pattern("R L")
         .define('E', Items.ENDER_PEARLS)
         .define('I', Items.INGOTS_IRON)
         .define('R', Items.DUSTS_REDSTONE)
         .define('L', Items.GEMS_LAPIS)
         .define('P', (ItemLike)ModItems.PICKUP_UPGRADE.get())
         .unlockedBy("has_pickup_upgrade", has((ItemLike)ModItems.PICKUP_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ADVANCED_MAGNET_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern("EIE")
         .pattern("IPI")
         .pattern("R L")
         .define('E', Items.ENDER_PEARLS)
         .define('I', Items.INGOTS_IRON)
         .define('R', Items.DUSTS_REDSTONE)
         .define('L', Items.GEMS_LAPIS)
         .define('P', (ItemLike)ModItems.ADVANCED_PICKUP_UPGRADE.get())
         .unlockedBy("has_advanced_pickup_upgrade", has((ItemLike)ModItems.ADVANCED_PICKUP_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ADVANCED_MAGNET_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern(" D ")
         .pattern("GMG")
         .pattern("RRR")
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('M', (ItemLike)ModItems.MAGNET_UPGRADE.get())
         .unlockedBy("has_magnet_upgrade", has((ItemLike)ModItems.MAGNET_UPGRADE.get()))
         .save(recipeOutput, SophisticatedBackpacks.getRL("advanced_magnet_upgrade_from_basic"));
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.FEEDING_UPGRADE.get())
         .pattern(" C ")
         .pattern("ABM")
         .pattern(" E ")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('C', net.minecraft.world.item.Items.GOLDEN_CARROT)
         .define('A', net.minecraft.world.item.Items.GOLDEN_APPLE)
         .define('M', net.minecraft.world.item.Items.GLISTERING_MELON_SLICE)
         .define('E', Items.ENDER_PEARLS)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.COMPACTING_UPGRADE.get())
         .pattern("IPI")
         .pattern("PBP")
         .pattern("RPR")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('I', Items.INGOTS_IRON)
         .define('P', net.minecraft.world.item.Items.PISTON)
         .define('R', Items.DUSTS_REDSTONE)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ADVANCED_COMPACTING_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern(" D ")
         .pattern("GCG")
         .pattern("RRR")
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('C', (ItemLike)ModItems.COMPACTING_UPGRADE.get())
         .unlockedBy("has_compacting_upgrade", has((ItemLike)ModItems.COMPACTING_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.VOID_UPGRADE.get())
         .pattern(" E ")
         .pattern("OBO")
         .pattern("ROR")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('E', Items.ENDER_PEARLS)
         .define('O', Items.OBSIDIANS)
         .define('R', Items.DUSTS_REDSTONE)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ADVANCED_VOID_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern(" D ")
         .pattern("GVG")
         .pattern("RRR")
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('V', (ItemLike)ModItems.VOID_UPGRADE.get())
         .unlockedBy("has_void_upgrade", has((ItemLike)ModItems.VOID_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.RESTOCK_UPGRADE.get())
         .pattern(" P ")
         .pattern("IBI")
         .pattern("RCR")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('C', Items.CHESTS_WOODEN)
         .define('I', Items.INGOTS_IRON)
         .define('R', Items.DUSTS_REDSTONE)
         .define('P', net.minecraft.world.item.Items.STICKY_PISTON)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ADVANCED_RESTOCK_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern(" D ")
         .pattern("GVG")
         .pattern("RRR")
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('V', (ItemLike)ModItems.RESTOCK_UPGRADE.get())
         .unlockedBy("has_restock_upgrade", has((ItemLike)ModItems.RESTOCK_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.DEPOSIT_UPGRADE.get())
         .pattern(" P ")
         .pattern("IBI")
         .pattern("RCR")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('C', Items.CHESTS_WOODEN)
         .define('I', Items.INGOTS_IRON)
         .define('R', Items.DUSTS_REDSTONE)
         .define('P', net.minecraft.world.item.Items.PISTON)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ADVANCED_DEPOSIT_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern(" D ")
         .pattern("GVG")
         .pattern("RRR")
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('V', (ItemLike)ModItems.DEPOSIT_UPGRADE.get())
         .unlockedBy("has_deposit_upgrade", has((ItemLike)ModItems.DEPOSIT_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.REFILL_UPGRADE.get())
         .pattern(" E ")
         .pattern("IBI")
         .pattern("RCR")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('C', Items.CHESTS_WOODEN)
         .define('I', Items.INGOTS_IRON)
         .define('R', Items.DUSTS_REDSTONE)
         .define('E', Items.ENDER_PEARLS)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ADVANCED_REFILL_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern(" D ")
         .pattern("GFG")
         .pattern("RRR")
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('F', (ItemLike)ModItems.REFILL_UPGRADE.get())
         .unlockedBy("has_refill_upgrade", has((ItemLike)ModItems.REFILL_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.INCEPTION_UPGRADE.get())
         .pattern("ESE")
         .pattern("DBD")
         .pattern("EDE")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('S', Items.NETHER_STARS)
         .define('D', Items.GEMS_DIAMOND)
         .define('E', net.minecraft.world.item.Items.ENDER_EYE)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.EVERLASTING_UPGRADE.get())
         .pattern("CSC")
         .pattern("SBS")
         .pattern("CSC")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('S', Items.NETHER_STARS)
         .define('C', net.minecraft.world.item.Items.END_CRYSTAL)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.SMELTING_UPGRADE.get())
         .pattern("RIR")
         .pattern("IBI")
         .pattern("RFR")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('R', Items.DUSTS_REDSTONE)
         .define('I', Items.INGOTS_IRON)
         .define('F', net.minecraft.world.item.Items.FURNACE)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.AUTO_SMELTING_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern("DHD")
         .pattern("RSH")
         .pattern("GHG")
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('H', net.minecraft.world.item.Items.HOPPER)
         .define('S', (ItemLike)ModItems.SMELTING_UPGRADE.get())
         .unlockedBy("has_smelting_upgrade", has((ItemLike)ModItems.SMELTING_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.CRAFTING_UPGRADE.get())
         .pattern(" T ")
         .pattern("IBI")
         .pattern(" C ")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('C', Items.CHESTS)
         .define('I', Items.INGOTS_IRON)
         .define('T', net.minecraft.world.item.Items.CRAFTING_TABLE)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.STONECUTTER_UPGRADE.get())
         .pattern(" S ")
         .pattern("IBI")
         .pattern(" R ")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('R', Items.DUSTS_REDSTONE)
         .define('I', Items.INGOTS_IRON)
         .define('S', net.minecraft.world.item.Items.STONECUTTER)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.STACK_UPGRADE_STARTER_TIER.get())
         .pattern("CCC")
         .pattern("CBC")
         .pattern("CCC")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('C', Items.STORAGE_BLOCKS_COPPER)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.STACK_UPGRADE_TIER_1.get())
         .pattern("III")
         .pattern("IBI")
         .pattern("III")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('I', Items.STORAGE_BLOCKS_IRON)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.STACK_UPGRADE_TIER_1.get())
         .pattern(" I ")
         .pattern("ISI")
         .pattern(" I ")
         .define('S', (ItemLike)ModItems.STACK_UPGRADE_STARTER_TIER.get())
         .define('I', Items.STORAGE_BLOCKS_IRON)
         .unlockedBy("has_stack_upgrade_starter_tier", has((ItemLike)ModItems.STACK_UPGRADE_STARTER_TIER.get()))
         .save(recipeOutput, SophisticatedBackpacks.getRL("stack_upgrade_tier_1_from_starter"));
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.STACK_UPGRADE_TIER_2.get())
         .pattern("GGG")
         .pattern("GSG")
         .pattern("GGG")
         .define('S', (ItemLike)ModItems.STACK_UPGRADE_TIER_1.get())
         .define('G', Items.STORAGE_BLOCKS_GOLD)
         .unlockedBy("has_stack_upgrade_tier_1", has((ItemLike)ModItems.STACK_UPGRADE_TIER_1.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.STACK_UPGRADE_TIER_3.get())
         .pattern("DDD")
         .pattern("DSD")
         .pattern("DDD")
         .define('S', (ItemLike)ModItems.STACK_UPGRADE_TIER_2.get())
         .define('D', Items.STORAGE_BLOCKS_DIAMOND)
         .unlockedBy("has_stack_upgrade_tier_2", has((ItemLike)ModItems.STACK_UPGRADE_TIER_2.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.STACK_UPGRADE_TIER_4.get())
         .pattern("NNN")
         .pattern("NSN")
         .pattern("NNN")
         .define('S', (ItemLike)ModItems.STACK_UPGRADE_TIER_3.get())
         .define('N', Items.STORAGE_BLOCKS_NETHERITE)
         .unlockedBy("has_stack_upgrade_tier_3", has((ItemLike)ModItems.STACK_UPGRADE_TIER_3.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.STACK_UPGRADE_OMEGA_TIER.get())
         .pattern("SSS")
         .pattern("SSS")
         .pattern("SSS")
         .define('S', (ItemLike)ModItems.STACK_UPGRADE_TIER_4.get())
         .unlockedBy("has_stack_upgrade_tier_4", has((ItemLike)ModItems.STACK_UPGRADE_TIER_4.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.STACK_DOWNGRADE_TIER_1.get())
         .pattern("SFS")
         .pattern("SBS")
         .pattern("FSF")
         .define('S', Items.RODS_WOODEN)
         .define('F', net.minecraft.world.item.Items.FLINT)
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.STACK_DOWNGRADE_TIER_2.get())
         .pattern("FSF")
         .pattern("SBS")
         .pattern("FSF")
         .define('S', Items.RODS_WOODEN)
         .define('F', net.minecraft.world.item.Items.FLINT)
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.STACK_DOWNGRADE_TIER_3.get())
         .pattern("SFS")
         .pattern("FBF")
         .pattern("FSF")
         .define('S', Items.RODS_WOODEN)
         .define('F', net.minecraft.world.item.Items.FLINT)
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.JUKEBOX_UPGRADE.get())
         .pattern(" J ")
         .pattern("IBI")
         .pattern(" R ")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('R', Items.DUSTS_REDSTONE)
         .define('I', Items.INGOTS_IRON)
         .define('J', net.minecraft.world.item.Items.JUKEBOX)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ADVANCED_JUKEBOX_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern(" D ")
         .pattern("GJG")
         .pattern("RRR")
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('J', (ItemLike)ModItems.JUKEBOX_UPGRADE.get())
         .unlockedBy("has_jukebox_upgrade", has((ItemLike)ModItems.JUKEBOX_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.TOOL_SWAPPER_UPGRADE.get())
         .pattern("RWR")
         .pattern("PBA")
         .pattern("ISI")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('S', net.minecraft.world.item.Items.WOODEN_SHOVEL)
         .define('P', net.minecraft.world.item.Items.WOODEN_PICKAXE)
         .define('A', net.minecraft.world.item.Items.WOODEN_AXE)
         .define('W', net.minecraft.world.item.Items.WOODEN_SWORD)
         .define('I', Items.INGOTS_IRON)
         .define('R', Items.DUSTS_REDSTONE)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ADVANCED_TOOL_SWAPPER_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern(" D ")
         .pattern("GVG")
         .pattern("RRR")
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('V', (ItemLike)ModItems.TOOL_SWAPPER_UPGRADE.get())
         .unlockedBy("has_tool_swapper_upgrade", has((ItemLike)ModItems.TOOL_SWAPPER_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.TANK_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern("GGG")
         .pattern("GBG")
         .pattern("GGG")
         .define('G', Items.GLASS_BLOCKS)
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ADVANCED_FEEDING_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern(" D ")
         .pattern("GVG")
         .pattern("RRR")
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('V', (ItemLike)ModItems.FEEDING_UPGRADE.get())
         .unlockedBy("has_feeding_upgrade", has((ItemLike)ModItems.FEEDING_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.BATTERY_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern("GRG")
         .pattern("RBR")
         .pattern("GRG")
         .define('R', Items.STORAGE_BLOCKS_REDSTONE)
         .define('G', Items.INGOTS_GOLD)
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.PUMP_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern("GUG")
         .pattern("PBS")
         .pattern("GUG")
         .define('U', net.minecraft.world.item.Items.BUCKET)
         .define('G', Items.GLASS_BLOCKS)
         .define('P', net.minecraft.world.item.Items.PISTON)
         .define('S', net.minecraft.world.item.Items.STICKY_PISTON)
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ADVANCED_PUMP_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern("DID")
         .pattern("GPG")
         .pattern("RRR")
         .define('I', net.minecraft.world.item.Items.DISPENSER)
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('P', (ItemLike)ModItems.PUMP_UPGRADE.get())
         .unlockedBy("has_pump_upgrade", has((ItemLike)ModItems.PUMP_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.XP_PUMP_UPGRADE.get())
         .pattern("RER")
         .pattern("CPC")
         .pattern("RER")
         .define('R', Items.DUSTS_REDSTONE)
         .define('E', net.minecraft.world.item.Items.ENDER_EYE)
         .define('C', net.minecraft.world.item.Items.EXPERIENCE_BOTTLE)
         .define('P', (ItemLike)ModItems.ADVANCED_PUMP_UPGRADE.get())
         .unlockedBy("has_advanced_pump_upgrade", has((ItemLike)ModItems.ADVANCED_PUMP_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.SMOKING_UPGRADE.get())
         .pattern("RIR")
         .pattern("IBI")
         .pattern("RSR")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('R', Items.DUSTS_REDSTONE)
         .define('I', Items.INGOTS_IRON)
         .define('S', net.minecraft.world.item.Items.SMOKER)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.SMOKING_UPGRADE.get())
         .pattern(" L ")
         .pattern("LSL")
         .pattern(" L ")
         .define('S', (ItemLike)ModItems.SMELTING_UPGRADE.get())
         .define('L', ItemTags.LOGS)
         .unlockedBy("has_smelting_upgrade", has((ItemLike)ModItems.SMELTING_UPGRADE.get()))
         .save(recipeOutput, SophisticatedBackpacks.getRL("smoking_upgrade_from_smelting_upgrade"));
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.AUTO_SMOKING_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern("DHD")
         .pattern("RSH")
         .pattern("GHG")
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('H', net.minecraft.world.item.Items.HOPPER)
         .define('S', (ItemLike)ModItems.SMOKING_UPGRADE.get())
         .unlockedBy("has_smoking_upgrade", has((ItemLike)ModItems.SMOKING_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.AUTO_SMOKING_UPGRADE.get())
         .pattern(" L ")
         .pattern("LSL")
         .pattern(" L ")
         .define('S', (ItemLike)ModItems.AUTO_SMELTING_UPGRADE.get())
         .define('L', ItemTags.LOGS)
         .unlockedBy("has_auto_smelting_upgrade", has((ItemLike)ModItems.AUTO_SMELTING_UPGRADE.get()))
         .save(recipeOutput, SophisticatedBackpacks.getRL("auto_smoking_upgrade_from_auto_smelting_upgrade"));
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.BLASTING_UPGRADE.get())
         .pattern("RIR")
         .pattern("IBI")
         .pattern("RFR")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('R', Items.DUSTS_REDSTONE)
         .define('I', Items.INGOTS_IRON)
         .define('F', net.minecraft.world.item.Items.BLAST_FURNACE)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.BLASTING_UPGRADE.get())
         .pattern("III")
         .pattern("ISI")
         .pattern("TTT")
         .define('S', (ItemLike)ModItems.SMELTING_UPGRADE.get())
         .define('I', Items.INGOTS_IRON)
         .define('T', net.minecraft.world.item.Items.SMOOTH_STONE)
         .unlockedBy("has_smelting_upgrade", has((ItemLike)ModItems.SMELTING_UPGRADE.get()))
         .save(recipeOutput, SophisticatedBackpacks.getRL("blasting_upgrade_from_smelting_upgrade"));
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.AUTO_BLASTING_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern("DHD")
         .pattern("RSH")
         .pattern("GHG")
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('H', net.minecraft.world.item.Items.HOPPER)
         .define('S', (ItemLike)ModItems.BLASTING_UPGRADE.get())
         .unlockedBy("has_blasting_upgrade", has((ItemLike)ModItems.BLASTING_UPGRADE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.AUTO_BLASTING_UPGRADE.get())
         .pattern("III")
         .pattern("ISI")
         .pattern("TTT")
         .define('S', (ItemLike)ModItems.AUTO_SMELTING_UPGRADE.get())
         .define('I', Items.INGOTS_IRON)
         .define('T', net.minecraft.world.item.Items.SMOOTH_STONE)
         .unlockedBy("has_auto_smelting_upgrade", has((ItemLike)ModItems.AUTO_SMELTING_UPGRADE.get()))
         .save(recipeOutput, SophisticatedBackpacks.getRL("auto_blasting_upgrade_from_auto_smelting_upgrade"));
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ANVIL_UPGRADE.get())
         .pattern("ADA")
         .pattern("IBI")
         .pattern(" C ")
         .define('A', net.minecraft.world.item.Items.ANVIL)
         .define('D', Items.GEMS_DIAMOND)
         .define('I', Items.INGOTS_IRON)
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('C', Items.CHESTS_WOODEN)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.SMITHING_UPGRADE.get())
         .pattern(" S ")
         .pattern("IBI")
         .pattern(" C ")
         .define('S', net.minecraft.world.item.Items.SMITHING_TABLE)
         .define('I', Items.INGOTS_IRON)
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('C', Items.CHESTS_WOODEN)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ALCHEMY_UPGRADE.get())
         .pattern("TGF")
         .pattern("IBI")
         .pattern("RPR")
         .define('T', net.minecraft.world.item.Items.GHAST_TEAR)
         .define('G', net.minecraft.world.item.Items.GLASS_BOTTLE)
         .define('F', net.minecraft.world.item.Items.FERMENTED_SPIDER_EYE)
         .define('R', net.minecraft.world.item.Items.BLAZE_ROD)
         .define('P', net.minecraft.world.item.Items.ENDER_PEARL)
         .define('I', Items.INGOTS_IRON)
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput);
      ShapeBasedRecipeBuilder.shaped((ItemLike)ModItems.ADVANCED_ALCHEMY_UPGRADE.get(), UpgradeNextTierRecipe::new)
         .pattern(" D ")
         .pattern("GAG")
         .pattern("RRR")
         .define('D', Items.GEMS_DIAMOND)
         .define('G', Items.INGOTS_GOLD)
         .define('R', Items.DUSTS_REDSTONE)
         .define('A', (ItemLike)ModItems.ALCHEMY_UPGRADE.get())
         .unlockedBy("has_alchemy_upgrade", has((ItemLike)ModItems.ALCHEMY_UPGRADE.get()))
         .save(recipeOutput);
      new SmithingBackpackUpgradeRecipeBuilder(
            SmithingBackpackUpgradeRecipe::new,
            Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE}),
            Ingredient.of(new ItemLike[]{(ItemLike)ModItems.DIAMOND_BACKPACK.get()}),
            Ingredient.of(new ItemLike[]{net.minecraft.world.item.Items.NETHERITE_INGOT}),
            (Item)ModItems.NETHERITE_BACKPACK.get()
         )
         .unlocks("has_diamond_backpack", has((ItemLike)ModItems.DIAMOND_BACKPACK.get()))
         .save(recipeOutput, RegistryHelper.getItemKey((Item)ModItems.NETHERITE_BACKPACK.get()));
      addCompatUpgradeRecipes(recipeOutput);
   }

   private static void addCompatUpgradeRecipes(RecipeOutput recipeOutput) {
      addCompatUpgradeRecipe(
         recipeOutput, (UpgradeItemBase<?>)ChippedCompat.BOTANIST_WORKBENCH_UPGRADE.get(), (Block)ModBlocks.BOTANIST_WORKBENCH.get(), "chipped"
      );
      addCompatUpgradeRecipe(recipeOutput, (UpgradeItemBase<?>)ChippedCompat.GLASSBLOWER_UPGRADE.get(), (Block)ModBlocks.GLASSBLOWER.get(), "chipped");
      addCompatUpgradeRecipe(recipeOutput, (UpgradeItemBase<?>)ChippedCompat.CARPENTERS_TABLE_UPGRADE.get(), (Block)ModBlocks.CARPENTERS_TABLE.get(), "chipped");
      addCompatUpgradeRecipe(recipeOutput, (UpgradeItemBase<?>)ChippedCompat.LOOM_TABLE_UPGRADE.get(), (Block)ModBlocks.LOOM_TABLE.get(), "chipped");
      addCompatUpgradeRecipe(recipeOutput, (UpgradeItemBase<?>)ChippedCompat.MASON_TABLE_UPGRADE.get(), (Block)ModBlocks.MASON_TABLE.get(), "chipped");
      addCompatUpgradeRecipe(recipeOutput, (UpgradeItemBase<?>)ChippedCompat.ALCHEMY_BENCH_UPGRADE.get(), (Block)ModBlocks.ALCHEMY_BENCH.get(), "chipped");
      addCompatUpgradeRecipe(recipeOutput, (UpgradeItemBase<?>)ChippedCompat.TINKERING_TABLE_UPGRADE.get(), (Block)ModBlocks.TINKERING_TABLE.get(), "chipped");
      addCompatUpgradeRecipe(recipeOutput, (UpgradeItemBase<?>)SawmillCompat.SAWMILL_UPGRADE.get(), (Block)SawmillMod.SAWMILL_BLOCK.get(), "sawmill");
   }

   private static void addCompatUpgradeRecipe(RecipeOutput recipeOutput, UpgradeItemBase<?> upgrade, Block workbench, String modId) {
      ShapeBasedRecipeBuilder.shaped(upgrade)
         .pattern(" W ")
         .pattern("IBI")
         .pattern(" R ")
         .define('B', (ItemLike)ModItems.UPGRADE_BASE.get())
         .define('R', Items.DUSTS_REDSTONE)
         .define('I', Items.INGOTS_IRON)
         .define('W', workbench)
         .unlockedBy("has_upgrade_base", has((ItemLike)ModItems.UPGRADE_BASE.get()))
         .save(recipeOutput.withConditions(new ICondition[]{new ModLoadedCondition(modId)}));
   }

   private static Criterion<?> hasLeather() {
      return inventoryTrigger(new ItemPredicate[]{Builder.item().of(Items.LEATHERS).build()});
   }
}
