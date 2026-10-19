package com.chronodawn.registry;

import com.chronodawn.ChronoDawn;
import com.chronodawn.entities.boats.ChronoDawnBoatType;
import com.chronodawn.items.ChronicleBookItem;
import com.chronodawn.items.ChronoAegisItem;
import com.chronodawn.items.ClockstoneShieldItem;
import com.chronodawn.items.DecorativeWaterBucketItem;
import com.chronodawn.items.DeferredSpawnEggItem;
import com.chronodawn.items.EnhancedClockstoneShieldItem;
import com.chronodawn.items.EntropyCoreItem;
import com.chronodawn.items.EntropyCrystalShieldItem;
import com.chronodawn.items.KeyToMasterClockItem;
import com.chronodawn.items.PhantomEssenceItem;
import com.chronodawn.items.PortalStabilizerItem;
import com.chronodawn.items.TimeCompassItem;
import com.chronodawn.items.TimeHourglassItem;
import com.chronodawn.items.TimeTorchItem;
import com.chronodawn.items.UnstableHourglassItem;
import com.chronodawn.items.artifacts.ChronobladeItem;
import com.chronodawn.items.artifacts.EchoingTimeBootsItem;
import com.chronodawn.items.artifacts.EyeOfChronosItem;
import com.chronodawn.items.artifacts.TimeTyrantMailItem;
import com.chronodawn.items.artifacts.UnstablePocketWatchItem;
import com.chronodawn.items.base.ClockstoneItem;
import com.chronodawn.items.base.EnhancedClockstoneItem;
import com.chronodawn.items.base.FragmentOfStasisCoreItem;
import com.chronodawn.items.base.TimeCrystalItem;
import com.chronodawn.items.boats.ChronoDawnBoatItem;
import com.chronodawn.items.boss.ColossusGearItem;
import com.chronodawn.items.boss.GuardianStoneItem;
import com.chronodawn.items.combat.TimeArrowItem;
import com.chronodawn.items.consumables.BakedTemporalRootItem;
import com.chronodawn.items.consumables.ChronoMelonJuiceItem;
import com.chronodawn.items.consumables.ChronoMelonSeedsItem;
import com.chronodawn.items.consumables.ChronoMelonSliceItem;
import com.chronodawn.items.consumables.ClockworkCookieItem;
import com.chronodawn.items.consumables.EnhancedTimeBreadItem;
import com.chronodawn.items.consumables.FruitOfTimeItem;
import com.chronodawn.items.consumables.GlisteningChronoMelonItem;
import com.chronodawn.items.consumables.GoldenTimeWheatItem;
import com.chronodawn.items.consumables.TemporalRootItem;
import com.chronodawn.items.consumables.TemporalRootStewItem;
import com.chronodawn.items.consumables.TimeBreadItem;
import com.chronodawn.items.consumables.TimeFruitPieItem;
import com.chronodawn.items.consumables.TimeJamItem;
import com.chronodawn.items.consumables.TimeWheatCookieItem;
import com.chronodawn.items.consumables.TimeWheatItem;
import com.chronodawn.items.consumables.TimeWheatSeedsItem;
import com.chronodawn.items.consumables.TimelessMushroomItem;
import com.chronodawn.items.consumables.TimelessMushroomSoupItem;
import com.chronodawn.items.equipment.ClockstoneArmorItem;
import com.chronodawn.items.equipment.ClockstoneAxeItem;
import com.chronodawn.items.equipment.ClockstoneHoeItem;
import com.chronodawn.items.equipment.ClockstonePickaxeItem;
import com.chronodawn.items.equipment.ClockstoneShovelItem;
import com.chronodawn.items.equipment.ClockstoneSwordItem;
import com.chronodawn.items.equipment.EnhancedClockstoneArmorItem;
import com.chronodawn.items.equipment.EnhancedClockstoneAxeItem;
import com.chronodawn.items.equipment.EnhancedClockstoneHoeItem;
import com.chronodawn.items.equipment.EnhancedClockstonePickaxeItem;
import com.chronodawn.items.equipment.EnhancedClockstoneShovelItem;
import com.chronodawn.items.equipment.EnhancedClockstoneSwordItem;
import com.chronodawn.items.equipment.EntropyCrystalSwordItem;
import com.chronodawn.items.equipment.TemporalAmberArmorItem;
import com.chronodawn.items.quest.AncientGearItem;
import com.chronodawn.items.tools.SpatiallyLinkedPickaxeItem;
import com.chronodawn.items.tools.TimeClockItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class ModItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create("chronodawn", Registries.ITEM);
   public static final RegistrySupplier<Item> CLOCKSTONE_ORE = ITEMS.register(
      ModItemId.CLOCKSTONE_ORE.id(), () -> new BlockItem((Block)ModBlocks.CLOCKSTONE_ORE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_CRYSTAL_ORE = ITEMS.register(
      ModItemId.TIME_CRYSTAL_ORE.id(), () -> new BlockItem((Block)ModBlocks.TIME_CRYSTAL_ORE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_COAL_ORE = ITEMS.register(
      ModItemId.TEMPORAL_COAL_ORE.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_COAL_ORE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_GOLD_ORE = ITEMS.register(
      ModItemId.TEMPORAL_GOLD_ORE.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_GOLD_ORE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_IRON_ORE = ITEMS.register(
      ModItemId.TEMPORAL_IRON_ORE.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_IRON_ORE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_REDSTONE_ORE = ITEMS.register(
      ModItemId.TEMPORAL_REDSTONE_ORE.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_REDSTONE_ORE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ENTROPY_CRYSTAL_ORE = ITEMS.register(
      ModItemId.ENTROPY_CRYSTAL_ORE.id(), () -> new BlockItem((Block)ModBlocks.ENTROPY_CRYSTAL_ORE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_AMBER_ORE = ITEMS.register(
      ModItemId.TEMPORAL_AMBER_ORE.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_AMBER_ORE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DEEPSLATE_TEMPORAL_AMBER_ORE = ITEMS.register(
      ModItemId.DEEPSLATE_TEMPORAL_AMBER_ORE.id(), () -> new BlockItem((Block)ModBlocks.DEEPSLATE_TEMPORAL_AMBER_ORE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DEEPSLATE_TEMPORAL_GOLD_ORE = ITEMS.register(
      ModItemId.DEEPSLATE_TEMPORAL_GOLD_ORE.id(), () -> new BlockItem((Block)ModBlocks.DEEPSLATE_TEMPORAL_GOLD_ORE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DEEPSLATE_TEMPORAL_REDSTONE_ORE = ITEMS.register(
      ModItemId.DEEPSLATE_TEMPORAL_REDSTONE_ORE.id(), () -> new BlockItem((Block)ModBlocks.DEEPSLATE_TEMPORAL_REDSTONE_ORE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DEEPSLATE_CLOCKSTONE_ORE = ITEMS.register(
      ModItemId.DEEPSLATE_CLOCKSTONE_ORE.id(), () -> new BlockItem((Block)ModBlocks.DEEPSLATE_CLOCKSTONE_ORE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> CLOCKSTONE_BLOCK = ITEMS.register(
      ModItemId.CLOCKSTONE_BLOCK.id(), () -> new BlockItem((Block)ModBlocks.CLOCKSTONE_BLOCK.get(), new Properties())
   );
   public static final RegistrySupplier<Item> CLOCKSTONE_STAIRS = ITEMS.register(
      ModItemId.CLOCKSTONE_STAIRS.id(), () -> new BlockItem((Block)ModBlocks.CLOCKSTONE_STAIRS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> CLOCKSTONE_SLAB = ITEMS.register(
      ModItemId.CLOCKSTONE_SLAB.id(), () -> new BlockItem((Block)ModBlocks.CLOCKSTONE_SLAB.get(), new Properties())
   );
   public static final RegistrySupplier<Item> CLOCKSTONE_WALL = ITEMS.register(
      ModItemId.CLOCKSTONE_WALL.id(), () -> new BlockItem((Block)ModBlocks.CLOCKSTONE_WALL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> REVERSING_TIME_SANDSTONE = ITEMS.register(
      ModItemId.REVERSING_TIME_SANDSTONE.id(), () -> new BlockItem((Block)ModBlocks.REVERSING_TIME_SANDSTONE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> UNSTABLE_FUNGUS = ITEMS.register(
      ModItemId.UNSTABLE_FUNGUS.id(), () -> new BlockItem((Block)ModBlocks.UNSTABLE_FUNGUS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD_LOG = ITEMS.register(
      ModItemId.TIME_WOOD_LOG.id(), () -> new BlockItem((Block)ModBlocks.TIME_WOOD_LOG.get(), new Properties())
   );
   public static final RegistrySupplier<Item> STRIPPED_TIME_WOOD_LOG = ITEMS.register(
      ModItemId.STRIPPED_TIME_WOOD_LOG.id(), () -> new BlockItem((Block)ModBlocks.STRIPPED_TIME_WOOD_LOG.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD = ITEMS.register(
      ModItemId.TIME_WOOD.id(), () -> new BlockItem((Block)ModBlocks.TIME_WOOD.get(), new Properties())
   );
   public static final RegistrySupplier<Item> STRIPPED_TIME_WOOD = ITEMS.register(
      ModItemId.STRIPPED_TIME_WOOD.id(), () -> new BlockItem((Block)ModBlocks.STRIPPED_TIME_WOOD.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD_LEAVES = ITEMS.register(
      ModItemId.TIME_WOOD_LEAVES.id(), () -> new BlockItem((Block)ModBlocks.TIME_WOOD_LEAVES.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD_PLANKS = ITEMS.register(
      ModItemId.TIME_WOOD_PLANKS.id(), () -> new BlockItem((Block)ModBlocks.TIME_WOOD_PLANKS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD_SAPLING = ITEMS.register(
      ModItemId.TIME_WOOD_SAPLING.id(), () -> new BlockItem((Block)ModBlocks.TIME_WOOD_SAPLING.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD_LOG = ITEMS.register(
      ModItemId.DARK_TIME_WOOD_LOG.id(), () -> new BlockItem((Block)ModBlocks.DARK_TIME_WOOD_LOG.get(), new Properties())
   );
   public static final RegistrySupplier<Item> STRIPPED_DARK_TIME_WOOD_LOG = ITEMS.register(
      ModItemId.STRIPPED_DARK_TIME_WOOD_LOG.id(), () -> new BlockItem((Block)ModBlocks.STRIPPED_DARK_TIME_WOOD_LOG.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD = ITEMS.register(
      ModItemId.DARK_TIME_WOOD.id(), () -> new BlockItem((Block)ModBlocks.DARK_TIME_WOOD.get(), new Properties())
   );
   public static final RegistrySupplier<Item> STRIPPED_DARK_TIME_WOOD = ITEMS.register(
      ModItemId.STRIPPED_DARK_TIME_WOOD.id(), () -> new BlockItem((Block)ModBlocks.STRIPPED_DARK_TIME_WOOD.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD_LEAVES = ITEMS.register(
      ModItemId.DARK_TIME_WOOD_LEAVES.id(), () -> new BlockItem((Block)ModBlocks.DARK_TIME_WOOD_LEAVES.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD_PLANKS = ITEMS.register(
      ModItemId.DARK_TIME_WOOD_PLANKS.id(), () -> new BlockItem((Block)ModBlocks.DARK_TIME_WOOD_PLANKS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD_STAIRS = ITEMS.register(
      ModItemId.DARK_TIME_WOOD_STAIRS.id(), () -> new BlockItem((Block)ModBlocks.DARK_TIME_WOOD_STAIRS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD_SLAB = ITEMS.register(
      ModItemId.DARK_TIME_WOOD_SLAB.id(), () -> new BlockItem((Block)ModBlocks.DARK_TIME_WOOD_SLAB.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD_FENCE = ITEMS.register(
      ModItemId.DARK_TIME_WOOD_FENCE.id(), () -> new BlockItem((Block)ModBlocks.DARK_TIME_WOOD_FENCE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD_DOOR = ITEMS.register(
      ModItemId.DARK_TIME_WOOD_DOOR.id(), () -> new BlockItem((Block)ModBlocks.DARK_TIME_WOOD_DOOR.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD_TRAPDOOR = ITEMS.register(
      ModItemId.DARK_TIME_WOOD_TRAPDOOR.id(), () -> new BlockItem((Block)ModBlocks.DARK_TIME_WOOD_TRAPDOOR.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD_FENCE_GATE = ITEMS.register(
      ModItemId.DARK_TIME_WOOD_FENCE_GATE.id(), () -> new BlockItem((Block)ModBlocks.DARK_TIME_WOOD_FENCE_GATE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD_BUTTON = ITEMS.register(
      ModItemId.DARK_TIME_WOOD_BUTTON.id(), () -> new BlockItem((Block)ModBlocks.DARK_TIME_WOOD_BUTTON.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD_PRESSURE_PLATE = ITEMS.register(
      ModItemId.DARK_TIME_WOOD_PRESSURE_PLATE.id(), () -> new BlockItem((Block)ModBlocks.DARK_TIME_WOOD_PRESSURE_PLATE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD_SAPLING = ITEMS.register(
      ModItemId.DARK_TIME_WOOD_SAPLING.id(), () -> new BlockItem((Block)ModBlocks.DARK_TIME_WOOD_SAPLING.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD_LOG = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD_LOG.id(), () -> new BlockItem((Block)ModBlocks.ANCIENT_TIME_WOOD_LOG.get(), new Properties())
   );
   public static final RegistrySupplier<Item> STRIPPED_ANCIENT_TIME_WOOD_LOG = ITEMS.register(
      ModItemId.STRIPPED_ANCIENT_TIME_WOOD_LOG.id(), () -> new BlockItem((Block)ModBlocks.STRIPPED_ANCIENT_TIME_WOOD_LOG.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD.id(), () -> new BlockItem((Block)ModBlocks.ANCIENT_TIME_WOOD.get(), new Properties())
   );
   public static final RegistrySupplier<Item> STRIPPED_ANCIENT_TIME_WOOD = ITEMS.register(
      ModItemId.STRIPPED_ANCIENT_TIME_WOOD.id(), () -> new BlockItem((Block)ModBlocks.STRIPPED_ANCIENT_TIME_WOOD.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD_LEAVES = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD_LEAVES.id(), () -> new BlockItem((Block)ModBlocks.ANCIENT_TIME_WOOD_LEAVES.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD_PLANKS = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD_PLANKS.id(), () -> new BlockItem((Block)ModBlocks.ANCIENT_TIME_WOOD_PLANKS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD_STAIRS = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD_STAIRS.id(), () -> new BlockItem((Block)ModBlocks.ANCIENT_TIME_WOOD_STAIRS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD_SLAB = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD_SLAB.id(), () -> new BlockItem((Block)ModBlocks.ANCIENT_TIME_WOOD_SLAB.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD_FENCE = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD_FENCE.id(), () -> new BlockItem((Block)ModBlocks.ANCIENT_TIME_WOOD_FENCE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD_DOOR = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD_DOOR.id(), () -> new BlockItem((Block)ModBlocks.ANCIENT_TIME_WOOD_DOOR.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD_TRAPDOOR = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD_TRAPDOOR.id(), () -> new BlockItem((Block)ModBlocks.ANCIENT_TIME_WOOD_TRAPDOOR.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD_FENCE_GATE = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD_FENCE_GATE.id(), () -> new BlockItem((Block)ModBlocks.ANCIENT_TIME_WOOD_FENCE_GATE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD_BUTTON = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD_BUTTON.id(), () -> new BlockItem((Block)ModBlocks.ANCIENT_TIME_WOOD_BUTTON.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD_PRESSURE_PLATE = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD_PRESSURE_PLATE.id(), () -> new BlockItem((Block)ModBlocks.ANCIENT_TIME_WOOD_PRESSURE_PLATE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD_SAPLING = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD_SAPLING.id(), () -> new BlockItem((Block)ModBlocks.ANCIENT_TIME_WOOD_SAPLING.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WHEAT_BALE = ITEMS.register(
      ModItemId.TIME_WHEAT_BALE.id(), () -> new BlockItem((Block)ModBlocks.TIME_WHEAT_BALE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> CLOCK_TOWER_TELEPORTER = ITEMS.register(
      ModItemId.CLOCK_TOWER_TELEPORTER.id(), () -> new BlockItem((Block)ModBlocks.CLOCK_TOWER_TELEPORTER.get(), new Properties())
   );
   public static final RegistrySupplier<Item> CLOCKWORK_BLOCK = ITEMS.register(
      ModItemId.CLOCKWORK_BLOCK.id(), () -> new BlockItem((Block)ModBlocks.CLOCKWORK_BLOCK.get(), new Properties())
   );
   public static final RegistrySupplier<Item> CLOCKWORK_DIAL = ITEMS.register(
      ModItemId.CLOCKWORK_DIAL.id(), () -> new BlockItem((Block)ModBlocks.CLOCKWORK_DIAL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_CRYSTAL_BLOCK = ITEMS.register(
      ModItemId.TIME_CRYSTAL_BLOCK.id(), () -> new BlockItem((Block)ModBlocks.TIME_CRYSTAL_BLOCK.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_BRICKS = ITEMS.register(
      ModItemId.TEMPORAL_BRICKS.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_BRICKS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_BRICKS_STAIRS = ITEMS.register(
      ModItemId.TEMPORAL_BRICKS_STAIRS.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_BRICKS_STAIRS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_BRICKS_SLAB = ITEMS.register(
      ModItemId.TEMPORAL_BRICKS_SLAB.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_BRICKS_SLAB.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_BRICKS_WALL = ITEMS.register(
      ModItemId.TEMPORAL_BRICKS_WALL.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_BRICKS_WALL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD_STAIRS = ITEMS.register(
      ModItemId.TIME_WOOD_STAIRS.id(), () -> new BlockItem((Block)ModBlocks.TIME_WOOD_STAIRS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD_SLAB = ITEMS.register(
      ModItemId.TIME_WOOD_SLAB.id(), () -> new BlockItem((Block)ModBlocks.TIME_WOOD_SLAB.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD_FENCE = ITEMS.register(
      ModItemId.TIME_WOOD_FENCE.id(), () -> new BlockItem((Block)ModBlocks.TIME_WOOD_FENCE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD_DOOR = ITEMS.register(
      ModItemId.TIME_WOOD_DOOR.id(), () -> new BlockItem((Block)ModBlocks.TIME_WOOD_DOOR.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD_TRAPDOOR = ITEMS.register(
      ModItemId.TIME_WOOD_TRAPDOOR.id(), () -> new BlockItem((Block)ModBlocks.TIME_WOOD_TRAPDOOR.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD_FENCE_GATE = ITEMS.register(
      ModItemId.TIME_WOOD_FENCE_GATE.id(), () -> new BlockItem((Block)ModBlocks.TIME_WOOD_FENCE_GATE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD_BUTTON = ITEMS.register(
      ModItemId.TIME_WOOD_BUTTON.id(), () -> new BlockItem((Block)ModBlocks.TIME_WOOD_BUTTON.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD_PRESSURE_PLATE = ITEMS.register(
      ModItemId.TIME_WOOD_PRESSURE_PLATE.id(), () -> new BlockItem((Block)ModBlocks.TIME_WOOD_PRESSURE_PLATE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_MOSS = ITEMS.register(
      ModItemId.TEMPORAL_MOSS.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_MOSS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_DIRT = ITEMS.register(
      ModItemId.TEMPORAL_DIRT.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_DIRT.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_GRASS_BLOCK = ITEMS.register(
      ModItemId.TEMPORAL_GRASS_BLOCK.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_GRASS_BLOCK.get(), new Properties())
   );
   public static final RegistrySupplier<Item> COARSE_TEMPORAL_DIRT = ITEMS.register(
      ModItemId.COARSE_TEMPORAL_DIRT.id(), () -> new BlockItem((Block)ModBlocks.COARSE_TEMPORAL_DIRT.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_STONE = ITEMS.register(
      ModItemId.TEMPORAL_STONE.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_STONE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_COBBLESTONE = ITEMS.register(
      ModItemId.TEMPORAL_COBBLESTONE.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_COBBLESTONE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_STONE_STAIRS = ITEMS.register(
      ModItemId.TEMPORAL_STONE_STAIRS.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_STONE_STAIRS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_STONE_SLAB = ITEMS.register(
      ModItemId.TEMPORAL_STONE_SLAB.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_STONE_SLAB.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_STONE_WALL = ITEMS.register(
      ModItemId.TEMPORAL_STONE_WALL.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_STONE_WALL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_SAND = ITEMS.register(
      ModItemId.TEMPORAL_SAND.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_SAND.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_GRAVEL = ITEMS.register(
      ModItemId.TEMPORAL_GRAVEL.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_GRAVEL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_SANDSTONE = ITEMS.register(
      ModItemId.TEMPORAL_SANDSTONE.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_SANDSTONE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_SANDSTONE_STAIRS = ITEMS.register(
      ModItemId.TEMPORAL_SANDSTONE_STAIRS.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_SANDSTONE_STAIRS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_SANDSTONE_SLAB = ITEMS.register(
      ModItemId.TEMPORAL_SANDSTONE_SLAB.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_SANDSTONE_SLAB.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_SANDSTONE_WALL = ITEMS.register(
      ModItemId.TEMPORAL_SANDSTONE_WALL.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_SANDSTONE_WALL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_COBBLESTONE_STAIRS = ITEMS.register(
      ModItemId.TEMPORAL_COBBLESTONE_STAIRS.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_COBBLESTONE_STAIRS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_COBBLESTONE_SLAB = ITEMS.register(
      ModItemId.TEMPORAL_COBBLESTONE_SLAB.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_COBBLESTONE_SLAB.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_COBBLESTONE_WALL = ITEMS.register(
      ModItemId.TEMPORAL_COBBLESTONE_WALL.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_COBBLESTONE_WALL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> MOSSY_TEMPORAL_COBBLESTONE = ITEMS.register(
      ModItemId.MOSSY_TEMPORAL_COBBLESTONE.id(), () -> new BlockItem((Block)ModBlocks.MOSSY_TEMPORAL_COBBLESTONE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> MOSSY_TEMPORAL_COBBLESTONE_STAIRS = ITEMS.register(
      ModItemId.MOSSY_TEMPORAL_COBBLESTONE_STAIRS.id(), () -> new BlockItem((Block)ModBlocks.MOSSY_TEMPORAL_COBBLESTONE_STAIRS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> MOSSY_TEMPORAL_COBBLESTONE_SLAB = ITEMS.register(
      ModItemId.MOSSY_TEMPORAL_COBBLESTONE_SLAB.id(), () -> new BlockItem((Block)ModBlocks.MOSSY_TEMPORAL_COBBLESTONE_SLAB.get(), new Properties())
   );
   public static final RegistrySupplier<Item> MOSSY_TEMPORAL_COBBLESTONE_WALL = ITEMS.register(
      ModItemId.MOSSY_TEMPORAL_COBBLESTONE_WALL.id(), () -> new BlockItem((Block)ModBlocks.MOSSY_TEMPORAL_COBBLESTONE_WALL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_STONE_BRICKS = ITEMS.register(
      ModItemId.TEMPORAL_STONE_BRICKS.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_STONE_BRICKS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_STONE_BRICKS_STAIRS = ITEMS.register(
      ModItemId.TEMPORAL_STONE_BRICKS_STAIRS.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_STONE_BRICKS_STAIRS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_STONE_BRICKS_SLAB = ITEMS.register(
      ModItemId.TEMPORAL_STONE_BRICKS_SLAB.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_STONE_BRICKS_SLAB.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_STONE_BRICKS_WALL = ITEMS.register(
      ModItemId.TEMPORAL_STONE_BRICKS_WALL.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_STONE_BRICKS_WALL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> MOSSY_TEMPORAL_STONE_BRICKS = ITEMS.register(
      ModItemId.MOSSY_TEMPORAL_STONE_BRICKS.id(), () -> new BlockItem((Block)ModBlocks.MOSSY_TEMPORAL_STONE_BRICKS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> MOSSY_TEMPORAL_STONE_BRICKS_STAIRS = ITEMS.register(
      ModItemId.MOSSY_TEMPORAL_STONE_BRICKS_STAIRS.id(), () -> new BlockItem((Block)ModBlocks.MOSSY_TEMPORAL_STONE_BRICKS_STAIRS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> MOSSY_TEMPORAL_STONE_BRICKS_SLAB = ITEMS.register(
      ModItemId.MOSSY_TEMPORAL_STONE_BRICKS_SLAB.id(), () -> new BlockItem((Block)ModBlocks.MOSSY_TEMPORAL_STONE_BRICKS_SLAB.get(), new Properties())
   );
   public static final RegistrySupplier<Item> MOSSY_TEMPORAL_STONE_BRICKS_WALL = ITEMS.register(
      ModItemId.MOSSY_TEMPORAL_STONE_BRICKS_WALL.id(), () -> new BlockItem((Block)ModBlocks.MOSSY_TEMPORAL_STONE_BRICKS_WALL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_STONE_BUTTON = ITEMS.register(
      ModItemId.TEMPORAL_STONE_BUTTON.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_STONE_BUTTON.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_STONE_PRESSURE_PLATE = ITEMS.register(
      ModItemId.TEMPORAL_STONE_PRESSURE_PLATE.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_STONE_PRESSURE_PLATE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DEEPSLATE_TEMPORAL_STONE = ITEMS.register(
      ModItemId.DEEPSLATE_TEMPORAL_STONE.id(), () -> new BlockItem((Block)ModBlocks.DEEPSLATE_TEMPORAL_STONE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> COBBLED_DEEPSLATE_TEMPORAL_STONE = ITEMS.register(
      ModItemId.COBBLED_DEEPSLATE_TEMPORAL_STONE.id(), () -> new BlockItem((Block)ModBlocks.COBBLED_DEEPSLATE_TEMPORAL_STONE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> COBBLED_DEEPSLATE_TEMPORAL_STONE_STAIRS = ITEMS.register(
      ModItemId.COBBLED_DEEPSLATE_TEMPORAL_STONE_STAIRS.id(),
      () -> new BlockItem((Block)ModBlocks.COBBLED_DEEPSLATE_TEMPORAL_STONE_STAIRS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> COBBLED_DEEPSLATE_TEMPORAL_STONE_SLAB = ITEMS.register(
      ModItemId.COBBLED_DEEPSLATE_TEMPORAL_STONE_SLAB.id(), () -> new BlockItem((Block)ModBlocks.COBBLED_DEEPSLATE_TEMPORAL_STONE_SLAB.get(), new Properties())
   );
   public static final RegistrySupplier<Item> COBBLED_DEEPSLATE_TEMPORAL_STONE_WALL = ITEMS.register(
      ModItemId.COBBLED_DEEPSLATE_TEMPORAL_STONE_WALL.id(), () -> new BlockItem((Block)ModBlocks.COBBLED_DEEPSLATE_TEMPORAL_STONE_WALL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> POLISHED_DEEPSLATE_TEMPORAL_STONE = ITEMS.register(
      ModItemId.POLISHED_DEEPSLATE_TEMPORAL_STONE.id(), () -> new BlockItem((Block)ModBlocks.POLISHED_DEEPSLATE_TEMPORAL_STONE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> POLISHED_DEEPSLATE_TEMPORAL_STONE_STAIRS = ITEMS.register(
      ModItemId.POLISHED_DEEPSLATE_TEMPORAL_STONE_STAIRS.id(),
      () -> new BlockItem((Block)ModBlocks.POLISHED_DEEPSLATE_TEMPORAL_STONE_STAIRS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> POLISHED_DEEPSLATE_TEMPORAL_STONE_SLAB = ITEMS.register(
      ModItemId.POLISHED_DEEPSLATE_TEMPORAL_STONE_SLAB.id(),
      () -> new BlockItem((Block)ModBlocks.POLISHED_DEEPSLATE_TEMPORAL_STONE_SLAB.get(), new Properties())
   );
   public static final RegistrySupplier<Item> POLISHED_DEEPSLATE_TEMPORAL_STONE_WALL = ITEMS.register(
      ModItemId.POLISHED_DEEPSLATE_TEMPORAL_STONE_WALL.id(),
      () -> new BlockItem((Block)ModBlocks.POLISHED_DEEPSLATE_TEMPORAL_STONE_WALL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_POINTED_DRIPSTONE = ITEMS.register(
      ModItemId.TEMPORAL_POINTED_DRIPSTONE.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_POINTED_DRIPSTONE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> FROZEN_TIME_ICE = ITEMS.register(
      ModItemId.FROZEN_TIME_ICE.id(), () -> new BlockItem((Block)ModBlocks.FROZEN_TIME_ICE.get(), new Properties())
   );
   public static final RegistrySupplier<Item> BOSS_ROOM_DOOR = ITEMS.register(
      ModItemId.BOSS_ROOM_DOOR.id(), () -> new BlockItem((Block)ModBlocks.BOSS_ROOM_DOOR.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ENTROPY_CRYPT_TRAPDOOR = ITEMS.register(
      ModItemId.ENTROPY_CRYPT_TRAPDOOR.id(), () -> new BlockItem((Block)ModBlocks.ENTROPY_CRYPT_TRAPDOOR.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_PARTICLE_EMITTER = ITEMS.register(
      ModItemId.TEMPORAL_PARTICLE_EMITTER.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_PARTICLE_EMITTER.get(), new Properties())
   );
   public static final RegistrySupplier<Item> BOSS_ROOM_BOUNDARY_MARKER = ITEMS.register(
      ModItemId.BOSS_ROOM_BOUNDARY_MARKER.id(), () -> new BlockItem((Block)ModBlocks.BOSS_ROOM_BOUNDARY_MARKER.get(), new Properties())
   );
   public static final RegistrySupplier<Item> CLOCKSTONE = ITEMS.register(
      ModItemId.CLOCKSTONE.id(), () -> new ClockstoneItem(ClockstoneItem.createProperties())
   );
   public static final RegistrySupplier<Item> TIME_CRYSTAL = ITEMS.register(
      ModItemId.TIME_CRYSTAL.id(), () -> new TimeCrystalItem(TimeCrystalItem.createProperties())
   );
   public static final RegistrySupplier<Item> ENHANCED_CLOCKSTONE = ITEMS.register(
      ModItemId.ENHANCED_CLOCKSTONE.id(), () -> new EnhancedClockstoneItem(EnhancedClockstoneItem.createProperties())
   );
   public static final RegistrySupplier<Item> ENTROPY_CRYSTAL = ITEMS.register(ModItemId.ENTROPY_CRYSTAL.id(), () -> new Item(new Properties()));
   public static final RegistrySupplier<Item> RAW_TEMPORAL_AMBER = ITEMS.register(ModItemId.RAW_TEMPORAL_AMBER.id(), () -> new Item(new Properties()));
   public static final RegistrySupplier<Item> TEMPORAL_AMBER_DUST = ITEMS.register(ModItemId.TEMPORAL_AMBER_DUST.id(), () -> new Item(new Properties()));
   public static final RegistrySupplier<Item> UNSTABLE_HOURGLASS = ITEMS.register(
      ModItemId.UNSTABLE_HOURGLASS.id(), () -> new UnstableHourglassItem(UnstableHourglassItem.createProperties())
   );
   public static final RegistrySupplier<Item> FRAGMENT_OF_STASIS_CORE = ITEMS.register(
      ModItemId.FRAGMENT_OF_STASIS_CORE.id(), () -> new FragmentOfStasisCoreItem(FragmentOfStasisCoreItem.createProperties())
   );
   public static final RegistrySupplier<Item> EYE_OF_CHRONOS = ITEMS.register(
      ModItemId.EYE_OF_CHRONOS.id(), () -> new EyeOfChronosItem(EyeOfChronosItem.createProperties())
   );
   public static final RegistrySupplier<Item> GUARDIAN_STONE = ITEMS.register(
      ModItemId.GUARDIAN_STONE.id(), () -> new GuardianStoneItem(GuardianStoneItem.createProperties())
   );
   public static final RegistrySupplier<Item> COLOSSUS_GEAR = ITEMS.register(
      ModItemId.COLOSSUS_GEAR.id(), () -> new ColossusGearItem(ColossusGearItem.createProperties())
   );
   public static final RegistrySupplier<Item> PHANTOM_ESSENCE = ITEMS.register(
      ModItemId.PHANTOM_ESSENCE.id(), () -> new PhantomEssenceItem(PhantomEssenceItem.createProperties())
   );
   public static final RegistrySupplier<Item> ENTROPY_CORE = ITEMS.register(
      ModItemId.ENTROPY_CORE.id(), () -> new EntropyCoreItem(EntropyCoreItem.createProperties())
   );
   public static final RegistrySupplier<Item> CHRONO_AEGIS = ITEMS.register(ModItemId.CHRONO_AEGIS.id(), () -> new ChronoAegisItem(new Properties()));
   public static final RegistrySupplier<Item> CLOCKSTONE_SHIELD = ITEMS.register(
      ModItemId.CLOCKSTONE_SHIELD.id(), () -> new ClockstoneShieldItem(new Properties())
   );
   public static final RegistrySupplier<Item> ENHANCED_CLOCKSTONE_SHIELD = ITEMS.register(
      ModItemId.ENHANCED_CLOCKSTONE_SHIELD.id(), () -> new EnhancedClockstoneShieldItem(new Properties())
   );
   public static final RegistrySupplier<Item> ENTROPY_CRYSTAL_SHIELD = ITEMS.register(
      ModItemId.ENTROPY_CRYSTAL_SHIELD.id(), () -> new EntropyCrystalShieldItem(new Properties())
   );
   public static final RegistrySupplier<Item> TIME_HOURGLASS = ITEMS.register(
      ModItemId.TIME_HOURGLASS.id(), () -> new TimeHourglassItem(TimeHourglassItem.createProperties())
   );
   public static final RegistrySupplier<Item> PORTAL_STABILIZER = ITEMS.register(
      ModItemId.PORTAL_STABILIZER.id(), () -> new PortalStabilizerItem(PortalStabilizerItem.createProperties())
   );
   public static final RegistrySupplier<Item> TIME_COMPASS = ITEMS.register(
      ModItemId.TIME_COMPASS.id(), () -> new TimeCompassItem(TimeCompassItem.createProperties())
   );
   public static final RegistrySupplier<Item> DECORATIVE_WATER_BUCKET = ITEMS.register(
      ModItemId.DECORATIVE_WATER_BUCKET.id(), () -> new DecorativeWaterBucketItem((Fluid)ModFluids.DECORATIVE_WATER.get(), new Properties().stacksTo(1))
   );
   public static final RegistrySupplier<Item> CHRONICLE_BOOK = ITEMS.register(
      ModItemId.CHRONICLE_BOOK.id(), () -> new ChronicleBookItem(ChronicleBookItem.createProperties())
   );
   public static final RegistrySupplier<Item> FRUIT_OF_TIME = ITEMS.register(
      ModItemId.FRUIT_OF_TIME.id(), () -> new FruitOfTimeItem(FruitOfTimeItem.createProperties())
   );
   public static final RegistrySupplier<Item> TIME_FRUIT_PIE = ITEMS.register(
      ModItemId.TIME_FRUIT_PIE.id(), () -> new TimeFruitPieItem(TimeFruitPieItem.createProperties())
   );
   public static final RegistrySupplier<Item> TIME_JAM = ITEMS.register(ModItemId.TIME_JAM.id(), () -> new TimeJamItem(TimeJamItem.createProperties()));
   public static final RegistrySupplier<Item> TIME_WHEAT_SEEDS = ITEMS.register(
      ModItemId.TIME_WHEAT_SEEDS.id(), () -> new TimeWheatSeedsItem(TimeWheatSeedsItem.createProperties())
   );
   public static final RegistrySupplier<Item> TIME_WHEAT = ITEMS.register(ModItemId.TIME_WHEAT.id(), () -> new TimeWheatItem(TimeWheatItem.createProperties()));
   public static final RegistrySupplier<Item> TIME_BREAD = ITEMS.register(ModItemId.TIME_BREAD.id(), () -> new TimeBreadItem(TimeBreadItem.createProperties()));
   public static final RegistrySupplier<Item> TEMPORAL_ROOT = ITEMS.register(
      ModItemId.TEMPORAL_ROOT.id(), () -> new TemporalRootItem(TemporalRootItem.createProperties())
   );
   public static final RegistrySupplier<Item> BAKED_TEMPORAL_ROOT = ITEMS.register(
      ModItemId.BAKED_TEMPORAL_ROOT.id(), () -> new BakedTemporalRootItem(BakedTemporalRootItem.createProperties())
   );
   public static final RegistrySupplier<Item> CHRONO_MELON_SLICE = ITEMS.register(
      ModItemId.CHRONO_MELON_SLICE.id(), () -> new ChronoMelonSliceItem(ChronoMelonSliceItem.createProperties())
   );
   public static final RegistrySupplier<Item> CHRONO_MELON_SEEDS = ITEMS.register(
      ModItemId.CHRONO_MELON_SEEDS.id(), () -> new ChronoMelonSeedsItem(ChronoMelonSeedsItem.createProperties())
   );
   public static final RegistrySupplier<Item> CHRONO_MELON = ITEMS.register(
      ModItemId.CHRONO_MELON.id(), () -> new BlockItem((Block)ModBlocks.CHRONO_MELON.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TIMELESS_MUSHROOM = ITEMS.register(
      ModItemId.TIMELESS_MUSHROOM.id(), () -> new TimelessMushroomItem(TimelessMushroomItem.createProperties())
   );
   public static final RegistrySupplier<Item> PURPLE_TIME_BLOSSOM = ITEMS.register(
      ModItemId.PURPLE_TIME_BLOSSOM.id(), () -> new BlockItem((Block)ModBlocks.PURPLE_TIME_BLOSSOM.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ORANGE_TIME_BLOSSOM = ITEMS.register(
      ModItemId.ORANGE_TIME_BLOSSOM.id(), () -> new BlockItem((Block)ModBlocks.ORANGE_TIME_BLOSSOM.get(), new Properties())
   );
   public static final RegistrySupplier<Item> PINK_TIME_BLOSSOM = ITEMS.register(
      ModItemId.PINK_TIME_BLOSSOM.id(), () -> new BlockItem((Block)ModBlocks.PINK_TIME_BLOSSOM.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DAWN_BELL = ITEMS.register(
      ModItemId.DAWN_BELL.id(), () -> new BlockItem((Block)ModBlocks.DAWN_BELL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DUSK_BELL = ITEMS.register(
      ModItemId.DUSK_BELL.id(), () -> new BlockItem((Block)ModBlocks.DUSK_BELL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_TALL_GRASS = ITEMS.register(
      ModItemId.TEMPORAL_TALL_GRASS.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_TALL_GRASS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_FERN = ITEMS.register(
      ModItemId.TEMPORAL_FERN.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_FERN.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_GRASS = ITEMS.register(
      ModItemId.TEMPORAL_GRASS.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_GRASS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> PARCHED_TEMPORAL_DIRT = ITEMS.register(
      ModItemId.PARCHED_TEMPORAL_DIRT.id(), () -> new BlockItem((Block)ModBlocks.PARCHED_TEMPORAL_DIRT.get(), new Properties())
   );
   public static final RegistrySupplier<Item> FADED_TEMPORAL_GRASS = ITEMS.register(
      ModItemId.FADED_TEMPORAL_GRASS.id(), () -> new BlockItem((Block)ModBlocks.FADED_TEMPORAL_GRASS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_DEAD_BUSH = ITEMS.register(
      ModItemId.TEMPORAL_DEAD_BUSH.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_DEAD_BUSH.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_KELP = ITEMS.register(
      ModItemId.TEMPORAL_KELP.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_KELP.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_SEAGRASS = ITEMS.register(
      ModItemId.TEMPORAL_SEAGRASS.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_SEAGRASS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TALL_TEMPORAL_SEAGRASS = ITEMS.register(
      ModItemId.TALL_TEMPORAL_SEAGRASS.id(), () -> new BlockItem((Block)ModBlocks.TALL_TEMPORAL_SEAGRASS.get(), new Properties())
   );
   public static final RegistrySupplier<Item> LUMEN_POLYP = ITEMS.register(
      ModItemId.LUMEN_POLYP.id(), () -> new BlockItem((Block)ModBlocks.LUMEN_POLYP.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DAWN_CHRONO_CORAL = ITEMS.register(
      ModItemId.DAWN_CHRONO_CORAL.id(), () -> new BlockItem((Block)ModBlocks.DAWN_CHRONO_CORAL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DUSK_CHRONO_CORAL = ITEMS.register(
      ModItemId.DUSK_CHRONO_CORAL.id(), () -> new BlockItem((Block)ModBlocks.DUSK_CHRONO_CORAL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TWILIGHT_CHRONO_CORAL = ITEMS.register(
      ModItemId.TWILIGHT_CHRONO_CORAL.id(), () -> new BlockItem((Block)ModBlocks.TWILIGHT_CHRONO_CORAL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> ETERNAL_CHRONO_CORAL = ITEMS.register(
      ModItemId.ETERNAL_CHRONO_CORAL.id(), () -> new BlockItem((Block)ModBlocks.ETERNAL_CHRONO_CORAL.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DRIED_TEMPORAL_KELP = ITEMS.register(
      ModItemId.DRIED_TEMPORAL_KELP.id(), () -> new Item(new Properties().food(new Builder().nutrition(1).saturationModifier(0.6F).fast().build()))
   );
   public static final RegistrySupplier<Item> PURPLE_TIME_TORCH = ITEMS.register(
      ModItemId.PURPLE_TIME_TORCH.id(), () -> new TimeTorchItem((Block)ModBlocks.PURPLE_TIME_TORCH.get(), ModBlocks.WALL_PURPLE_TIME_TORCH, new Properties())
   );
   public static final RegistrySupplier<Item> ORANGE_TIME_TORCH = ITEMS.register(
      ModItemId.ORANGE_TIME_TORCH.id(), () -> new TimeTorchItem((Block)ModBlocks.ORANGE_TIME_TORCH.get(), ModBlocks.WALL_ORANGE_TIME_TORCH, new Properties())
   );
   public static final RegistrySupplier<Item> PINK_TIME_TORCH = ITEMS.register(
      ModItemId.PINK_TIME_TORCH.id(), () -> new TimeTorchItem((Block)ModBlocks.PINK_TIME_TORCH.get(), ModBlocks.WALL_PINK_TIME_TORCH, new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_LANTERN = ITEMS.register(
      ModItemId.TEMPORAL_LANTERN.id(), () -> new BlockItem((Block)ModBlocks.TEMPORAL_LANTERN.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DAWN_LANTERN = ITEMS.register(
      ModItemId.DAWN_LANTERN.id(), () -> new BlockItem((Block)ModBlocks.DAWN_LANTERN.get(), new Properties())
   );
   public static final RegistrySupplier<Item> DUSK_LANTERN = ITEMS.register(
      ModItemId.DUSK_LANTERN.id(), () -> new BlockItem((Block)ModBlocks.DUSK_LANTERN.get(), new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_ROOT_STEW = ITEMS.register(
      ModItemId.TEMPORAL_ROOT_STEW.id(), () -> new TemporalRootStewItem(TemporalRootStewItem.createProperties())
   );
   public static final RegistrySupplier<Item> GLISTENING_CHRONO_MELON = ITEMS.register(
      ModItemId.GLISTENING_CHRONO_MELON.id(), () -> new GlisteningChronoMelonItem(GlisteningChronoMelonItem.createProperties())
   );
   public static final RegistrySupplier<Item> CHRONO_MELON_JUICE = ITEMS.register(
      ModItemId.CHRONO_MELON_JUICE.id(), () -> new ChronoMelonJuiceItem(ChronoMelonJuiceItem.createProperties())
   );
   public static final RegistrySupplier<Item> TIMELESS_MUSHROOM_SOUP = ITEMS.register(
      ModItemId.TIMELESS_MUSHROOM_SOUP.id(), () -> new TimelessMushroomSoupItem(TimelessMushroomSoupItem.createProperties())
   );
   public static final RegistrySupplier<Item> ENHANCED_TIME_BREAD = ITEMS.register(
      ModItemId.ENHANCED_TIME_BREAD.id(), () -> new EnhancedTimeBreadItem(EnhancedTimeBreadItem.createProperties())
   );
   public static final RegistrySupplier<Item> TIME_WHEAT_COOKIE = ITEMS.register(
      ModItemId.TIME_WHEAT_COOKIE.id(), () -> new TimeWheatCookieItem(TimeWheatCookieItem.createProperties())
   );
   public static final RegistrySupplier<Item> CLOCKWORK_COOKIE = ITEMS.register(
      ModItemId.CLOCKWORK_COOKIE.id(), () -> new ClockworkCookieItem(ClockworkCookieItem.createProperties())
   );
   public static final RegistrySupplier<Item> GOLDEN_TIME_WHEAT = ITEMS.register(
      ModItemId.GOLDEN_TIME_WHEAT.id(), () -> new GoldenTimeWheatItem(GoldenTimeWheatItem.createProperties())
   );
   public static final RegistrySupplier<Item> CLOCKSTONE_SWORD = ITEMS.register(
      ModItemId.CLOCKSTONE_SWORD.id(), () -> new ClockstoneSwordItem(ClockstoneSwordItem.createProperties())
   );
   public static final RegistrySupplier<Item> CLOCKSTONE_PICKAXE = ITEMS.register(
      ModItemId.CLOCKSTONE_PICKAXE.id(), () -> new ClockstonePickaxeItem(ClockstonePickaxeItem.createProperties())
   );
   public static final RegistrySupplier<Item> CLOCKSTONE_AXE = ITEMS.register(
      ModItemId.CLOCKSTONE_AXE.id(), () -> new ClockstoneAxeItem(ClockstoneAxeItem.createProperties())
   );
   public static final RegistrySupplier<Item> CLOCKSTONE_SHOVEL = ITEMS.register(
      ModItemId.CLOCKSTONE_SHOVEL.id(), () -> new ClockstoneShovelItem(ClockstoneShovelItem.createProperties())
   );
   public static final RegistrySupplier<Item> CLOCKSTONE_HOE = ITEMS.register(
      ModItemId.CLOCKSTONE_HOE.id(), () -> new ClockstoneHoeItem(ClockstoneHoeItem.createProperties())
   );
   public static final RegistrySupplier<Item> CLOCKSTONE_HELMET = ITEMS.register(
      ModItemId.CLOCKSTONE_HELMET.id(), () -> new ClockstoneArmorItem(Type.HELMET, ClockstoneArmorItem.createProperties(Type.HELMET))
   );
   public static final RegistrySupplier<Item> CLOCKSTONE_CHESTPLATE = ITEMS.register(
      ModItemId.CLOCKSTONE_CHESTPLATE.id(), () -> new ClockstoneArmorItem(Type.CHESTPLATE, ClockstoneArmorItem.createProperties(Type.CHESTPLATE))
   );
   public static final RegistrySupplier<Item> CLOCKSTONE_LEGGINGS = ITEMS.register(
      ModItemId.CLOCKSTONE_LEGGINGS.id(), () -> new ClockstoneArmorItem(Type.LEGGINGS, ClockstoneArmorItem.createProperties(Type.LEGGINGS))
   );
   public static final RegistrySupplier<Item> CLOCKSTONE_BOOTS = ITEMS.register(
      ModItemId.CLOCKSTONE_BOOTS.id(), () -> new ClockstoneArmorItem(Type.BOOTS, ClockstoneArmorItem.createProperties(Type.BOOTS))
   );
   public static final RegistrySupplier<Item> ENHANCED_CLOCKSTONE_SWORD = ITEMS.register(
      ModItemId.ENHANCED_CLOCKSTONE_SWORD.id(), () -> new EnhancedClockstoneSwordItem(EnhancedClockstoneSwordItem.createProperties())
   );
   public static final RegistrySupplier<Item> ENTROPY_CRYSTAL_SWORD = ITEMS.register(
      ModItemId.ENTROPY_CRYSTAL_SWORD.id(), () -> new EntropyCrystalSwordItem(EntropyCrystalSwordItem.createProperties())
   );
   public static final RegistrySupplier<Item> ENHANCED_CLOCKSTONE_PICKAXE = ITEMS.register(
      ModItemId.ENHANCED_CLOCKSTONE_PICKAXE.id(), () -> new EnhancedClockstonePickaxeItem(EnhancedClockstonePickaxeItem.createProperties())
   );
   public static final RegistrySupplier<Item> ENHANCED_CLOCKSTONE_AXE = ITEMS.register(
      ModItemId.ENHANCED_CLOCKSTONE_AXE.id(), () -> new EnhancedClockstoneAxeItem(EnhancedClockstoneAxeItem.createProperties())
   );
   public static final RegistrySupplier<Item> ENHANCED_CLOCKSTONE_SHOVEL = ITEMS.register(
      ModItemId.ENHANCED_CLOCKSTONE_SHOVEL.id(), () -> new EnhancedClockstoneShovelItem(EnhancedClockstoneShovelItem.createProperties())
   );
   public static final RegistrySupplier<Item> ENHANCED_CLOCKSTONE_HOE = ITEMS.register(
      ModItemId.ENHANCED_CLOCKSTONE_HOE.id(), () -> new EnhancedClockstoneHoeItem(EnhancedClockstoneHoeItem.createProperties())
   );
   public static final RegistrySupplier<Item> CHRONOBLADE = ITEMS.register(
      ModItemId.CHRONOBLADE.id(), () -> new ChronobladeItem(ChronobladeItem.createProperties())
   );
   public static final RegistrySupplier<Item> TIME_TYRANT_MAIL = ITEMS.register(
      ModItemId.TIME_TYRANT_MAIL.id(), () -> new TimeTyrantMailItem(TimeTyrantMailItem.createProperties())
   );
   public static final RegistrySupplier<Item> ECHOING_TIME_BOOTS = ITEMS.register(
      ModItemId.ECHOING_TIME_BOOTS.id(), () -> new EchoingTimeBootsItem(EchoingTimeBootsItem.createProperties())
   );
   public static final RegistrySupplier<Item> UNSTABLE_POCKET_WATCH = ITEMS.register(
      ModItemId.UNSTABLE_POCKET_WATCH.id(), () -> new UnstablePocketWatchItem(UnstablePocketWatchItem.createProperties())
   );
   public static final RegistrySupplier<Item> ENHANCED_CLOCKSTONE_HELMET = ITEMS.register(
      ModItemId.ENHANCED_CLOCKSTONE_HELMET.id(), () -> new EnhancedClockstoneArmorItem(Type.HELMET, EnhancedClockstoneArmorItem.createProperties(Type.HELMET))
   );
   public static final RegistrySupplier<Item> ENHANCED_CLOCKSTONE_CHESTPLATE = ITEMS.register(
      ModItemId.ENHANCED_CLOCKSTONE_CHESTPLATE.id(),
      () -> new EnhancedClockstoneArmorItem(Type.CHESTPLATE, EnhancedClockstoneArmorItem.createProperties(Type.CHESTPLATE))
   );
   public static final RegistrySupplier<Item> ENHANCED_CLOCKSTONE_LEGGINGS = ITEMS.register(
      ModItemId.ENHANCED_CLOCKSTONE_LEGGINGS.id(),
      () -> new EnhancedClockstoneArmorItem(Type.LEGGINGS, EnhancedClockstoneArmorItem.createProperties(Type.LEGGINGS))
   );
   public static final RegistrySupplier<Item> ENHANCED_CLOCKSTONE_BOOTS = ITEMS.register(
      ModItemId.ENHANCED_CLOCKSTONE_BOOTS.id(), () -> new EnhancedClockstoneArmorItem(Type.BOOTS, EnhancedClockstoneArmorItem.createProperties(Type.BOOTS))
   );
   public static final RegistrySupplier<Item> TEMPORAL_AMBER_HELMET = ITEMS.register(
      ModItemId.TEMPORAL_AMBER_HELMET.id(), () -> new TemporalAmberArmorItem(Type.HELMET, TemporalAmberArmorItem.createProperties(Type.HELMET))
   );
   public static final RegistrySupplier<Item> TEMPORAL_AMBER_CHESTPLATE = ITEMS.register(
      ModItemId.TEMPORAL_AMBER_CHESTPLATE.id(), () -> new TemporalAmberArmorItem(Type.CHESTPLATE, TemporalAmberArmorItem.createProperties(Type.CHESTPLATE))
   );
   public static final RegistrySupplier<Item> TEMPORAL_AMBER_LEGGINGS = ITEMS.register(
      ModItemId.TEMPORAL_AMBER_LEGGINGS.id(), () -> new TemporalAmberArmorItem(Type.LEGGINGS, TemporalAmberArmorItem.createProperties(Type.LEGGINGS))
   );
   public static final RegistrySupplier<Item> TEMPORAL_AMBER_BOOTS = ITEMS.register(
      ModItemId.TEMPORAL_AMBER_BOOTS.id(), () -> new TemporalAmberArmorItem(Type.BOOTS, TemporalAmberArmorItem.createProperties(Type.BOOTS))
   );
   public static final RegistrySupplier<Item> TIME_CLOCK = ITEMS.register(ModItemId.TIME_CLOCK.id(), () -> new TimeClockItem(TimeClockItem.createProperties()));
   public static final RegistrySupplier<Item> SPATIALLY_LINKED_PICKAXE = ITEMS.register(
      ModItemId.SPATIALLY_LINKED_PICKAXE.id(), () -> new SpatiallyLinkedPickaxeItem(SpatiallyLinkedPickaxeItem.createProperties())
   );
   public static final RegistrySupplier<Item> TIME_ARROW = ITEMS.register(ModItemId.TIME_ARROW.id(), () -> new TimeArrowItem(TimeArrowItem.createProperties()));
   public static final RegistrySupplier<Item> KEY_TO_MASTER_CLOCK = ITEMS.register(
      ModItemId.KEY_TO_MASTER_CLOCK.id(), () -> new KeyToMasterClockItem(KeyToMasterClockItem.createProperties())
   );
   public static final RegistrySupplier<Item> ANCIENT_GEAR = ITEMS.register(
      ModItemId.ANCIENT_GEAR.id(), () -> new AncientGearItem(AncientGearItem.createProperties())
   );
   public static final RegistrySupplier<Item> CHRONO_BOVINE_MEAT = ITEMS.register(
      ModItemId.CHRONO_BOVINE_MEAT.id(), () -> new Item(new Properties().food(new Builder().nutrition(3).saturationModifier(0.3F).build()))
   );
   public static final RegistrySupplier<Item> COOKED_CHRONO_BOVINE_MEAT = ITEMS.register(
      ModItemId.COOKED_CHRONO_BOVINE_MEAT.id(), () -> new Item(new Properties().food(new Builder().nutrition(8).saturationModifier(0.8F).build()))
   );
   public static final RegistrySupplier<Item> GLIDE_FISH = ITEMS.register(
      ModItemId.GLIDE_FISH.id(), () -> new Item(new Properties().food(new Builder().nutrition(2).saturationModifier(0.1F).build()))
   );
   public static final RegistrySupplier<Item> COOKED_GLIDE_FISH = ITEMS.register(
      ModItemId.COOKED_GLIDE_FISH.id(), () -> new Item(new Properties().food(new Builder().nutrition(5).saturationModifier(0.6F).build()))
   );
   public static final RegistrySupplier<Item> TEMPORAL_WRAITH_SPAWN_EGG = ITEMS.register(
      ModItemId.TEMPORAL_WRAITH_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.TEMPORAL_WRAITH, 4915330, 11393254, new Properties())
   );
   public static final RegistrySupplier<Item> CLOCKWORK_SENTINEL_SPAWN_EGG = ITEMS.register(
      ModItemId.CLOCKWORK_SENTINEL_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.CLOCKWORK_SENTINEL, 4286945, 16766720, new Properties())
   );
   public static final RegistrySupplier<Item> TIME_KEEPER_SPAWN_EGG = ITEMS.register(
      ModItemId.TIME_KEEPER_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.TIME_KEEPER, 4734347, 16119285, new Properties())
   );
   public static final RegistrySupplier<Item> TIME_GUARDIAN_SPAWN_EGG = ITEMS.register(
      ModItemId.TIME_GUARDIAN_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.TIME_GUARDIAN, 16766720, 4286945, new Properties())
   );
   public static final RegistrySupplier<Item> TIME_TYRANT_SPAWN_EGG = ITEMS.register(
      ModItemId.TIME_TYRANT_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.TIME_TYRANT, 9109504, 16766720, new Properties())
   );
   public static final RegistrySupplier<Item> CHRONOS_WARDEN_SPAWN_EGG = ITEMS.register(
      ModItemId.CHRONOS_WARDEN_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.CHRONOS_WARDEN, 3100495, 52945, new Properties())
   );
   public static final RegistrySupplier<Item> CLOCKWORK_COLOSSUS_SPAWN_EGG = ITEMS.register(
      ModItemId.CLOCKWORK_COLOSSUS_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.CLOCKWORK_COLOSSUS, 7372944, 16747520, new Properties())
   );
   public static final RegistrySupplier<Item> ENTROPY_KEEPER_SPAWN_EGG = ITEMS.register(
      ModItemId.ENTROPY_KEEPER_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.ENTROPY_KEEPER, 1644912, 9662683, new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_PHANTOM_SPAWN_EGG = ITEMS.register(
      ModItemId.TEMPORAL_PHANTOM_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.TEMPORAL_PHANTOM, 3092271, 14737632, new Properties())
   );
   public static final RegistrySupplier<Item> FLOQ_SPAWN_EGG = ITEMS.register(
      ModItemId.FLOQ_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.FLOQ, 32896, 9498256, new Properties())
   );
   public static final RegistrySupplier<Item> EPOCH_HUSK_SPAWN_EGG = ITEMS.register(
      ModItemId.EPOCH_HUSK_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.EPOCH_HUSK, 13219722, 9139029, new Properties())
   );
   public static final RegistrySupplier<Item> FORGOTTEN_MINUTE_SPAWN_EGG = ITEMS.register(
      ModItemId.FORGOTTEN_MINUTE_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.FORGOTTEN_MINUTE, 10526928, 6316192, new Properties())
   );
   public static final RegistrySupplier<Item> CHRONAL_LEECH_SPAWN_EGG = ITEMS.register(
      ModItemId.CHRONAL_LEECH_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.CHRONAL_LEECH, 4864570, 9109504, new Properties())
   );
   public static final RegistrySupplier<Item> MOMENT_CREEPER_SPAWN_EGG = ITEMS.register(
      ModItemId.MOMENT_CREEPER_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.MOMENT_CREEPER, 894731, 0, new Properties())
   );
   public static final RegistrySupplier<Item> GLIDE_FISH_SPAWN_EGG = ITEMS.register(
      ModItemId.GLIDE_FISH_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.GLIDE_FISH, 8900331, 12632256, new Properties())
   );
   public static final RegistrySupplier<Item> TIMELINE_STRIDER_SPAWN_EGG = ITEMS.register(
      ModItemId.TIMELINE_STRIDER_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.TIMELINE_STRIDER, 1776442, 16766720, new Properties())
   );
   public static final RegistrySupplier<Item> HOURGLASS_GOLEM_SPAWN_EGG = ITEMS.register(
      ModItemId.HOURGLASS_GOLEM_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.HOURGLASS_GOLEM, 10193774, 14329120, new Properties())
   );
   public static final RegistrySupplier<Item> SECONDHAND_ARCHER_SPAWN_EGG = ITEMS.register(
      ModItemId.SECONDHAND_ARCHER_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.SECONDHAND_ARCHER, 8022874, 13943976, new Properties())
   );
   public static final RegistrySupplier<Item> PARADOX_CRAWLER_SPAWN_EGG = ITEMS.register(
      ModItemId.PARADOX_CRAWLER_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.PARADOX_CRAWLER, 2829130, 5227511, new Properties())
   );
   public static final RegistrySupplier<Item> CHRONO_TURTLE_SPAWN_EGG = ITEMS.register(
      ModItemId.CHRONO_TURTLE_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.CHRONO_TURTLE, 4029018, 9159844, new Properties())
   );
   public static final RegistrySupplier<Item> TIMEBOUND_RABBIT_SPAWN_EGG = ITEMS.register(
      ModItemId.TIMEBOUND_RABBIT_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.TIMEBOUND_RABBIT, 6005720, 3042972, new Properties())
   );
   public static final RegistrySupplier<Item> PULSE_HOG_SPAWN_EGG = ITEMS.register(
      ModItemId.PULSE_HOG_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.PULSE_HOG, 8087790, 4734347, new Properties())
   );
   public static final RegistrySupplier<Item> SECONDWING_FOWL_SPAWN_EGG = ITEMS.register(
      ModItemId.SECONDWING_FOWL_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.SECONDWING_FOWL, 9662683, 16766720, new Properties())
   );
   public static final RegistrySupplier<Item> TICKING_SHEEP_SPAWN_EGG = ITEMS.register(
      ModItemId.TICKING_SHEEP_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.TICKING_SHEEP, 8313048, 9109504, new Properties())
   );
   public static final RegistrySupplier<Item> CHRONO_BOVINE_SPAWN_EGG = ITEMS.register(
      ModItemId.CHRONO_BOVINE_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.CHRONO_BOVINE, 1714762, 13938487, new Properties())
   );
   public static final RegistrySupplier<Item> TEMPORAL_CAPRID_SPAWN_EGG = ITEMS.register(
      ModItemId.TEMPORAL_CAPRID_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.TEMPORAL_CAPRID, 5987286, 15127418, new Properties())
   );
   public static final RegistrySupplier<Item> CHRONO_URSID_SPAWN_EGG = ITEMS.register(
      ModItemId.CHRONO_URSID_SPAWN_EGG.id(), () -> new DeferredSpawnEggItem(ModEntities.CHRONO_URSID, 15263976, 12095560, new Properties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD_BOAT = ITEMS.register(
      ModItemId.TIME_WOOD_BOAT.id(), () -> new ChronoDawnBoatItem(ChronoDawnBoatType.TIME_WOOD, false, ChronoDawnBoatItem.createProperties())
   );
   public static final RegistrySupplier<Item> TIME_WOOD_CHEST_BOAT = ITEMS.register(
      ModItemId.TIME_WOOD_CHEST_BOAT.id(), () -> new ChronoDawnBoatItem(ChronoDawnBoatType.TIME_WOOD, true, ChronoDawnBoatItem.createProperties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD_BOAT = ITEMS.register(
      ModItemId.DARK_TIME_WOOD_BOAT.id(), () -> new ChronoDawnBoatItem(ChronoDawnBoatType.DARK_TIME_WOOD, false, ChronoDawnBoatItem.createProperties())
   );
   public static final RegistrySupplier<Item> DARK_TIME_WOOD_CHEST_BOAT = ITEMS.register(
      ModItemId.DARK_TIME_WOOD_CHEST_BOAT.id(), () -> new ChronoDawnBoatItem(ChronoDawnBoatType.DARK_TIME_WOOD, true, ChronoDawnBoatItem.createProperties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD_BOAT = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD_BOAT.id(), () -> new ChronoDawnBoatItem(ChronoDawnBoatType.ANCIENT_TIME_WOOD, false, ChronoDawnBoatItem.createProperties())
   );
   public static final RegistrySupplier<Item> ANCIENT_TIME_WOOD_CHEST_BOAT = ITEMS.register(
      ModItemId.ANCIENT_TIME_WOOD_CHEST_BOAT.id(),
      () -> new ChronoDawnBoatItem(ChronoDawnBoatType.ANCIENT_TIME_WOOD, true, ChronoDawnBoatItem.createProperties())
   );

   public static void register() {
      ITEMS.register();
      ChronoDawn.LOGGER.debug("Registered ModItems");
   }

   public static void initializeSpawnEggs() {
      ChronoDawn.LOGGER.debug("Initializing spawn eggs...");
      if (TEMPORAL_WRAITH_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)TEMPORAL_WRAITH_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (CLOCKWORK_SENTINEL_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)CLOCKWORK_SENTINEL_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (TIME_KEEPER_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)TIME_KEEPER_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (TIME_GUARDIAN_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)TIME_GUARDIAN_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (TIME_TYRANT_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)TIME_TYRANT_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (CHRONOS_WARDEN_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)CHRONOS_WARDEN_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (CLOCKWORK_COLOSSUS_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)CLOCKWORK_COLOSSUS_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (ENTROPY_KEEPER_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)ENTROPY_KEEPER_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (TEMPORAL_PHANTOM_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)TEMPORAL_PHANTOM_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (FLOQ_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)FLOQ_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (EPOCH_HUSK_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)EPOCH_HUSK_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (FORGOTTEN_MINUTE_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)FORGOTTEN_MINUTE_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (CHRONAL_LEECH_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)CHRONAL_LEECH_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (MOMENT_CREEPER_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)MOMENT_CREEPER_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (GLIDE_FISH_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)GLIDE_FISH_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (TIMELINE_STRIDER_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)TIMELINE_STRIDER_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (HOURGLASS_GOLEM_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)HOURGLASS_GOLEM_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (SECONDHAND_ARCHER_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)SECONDHAND_ARCHER_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (PARADOX_CRAWLER_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)PARADOX_CRAWLER_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (CHRONO_TURTLE_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)CHRONO_TURTLE_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (TIMEBOUND_RABBIT_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)TIMEBOUND_RABBIT_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (PULSE_HOG_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)PULSE_HOG_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (SECONDWING_FOWL_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)SECONDWING_FOWL_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (TICKING_SHEEP_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)TICKING_SHEEP_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (CHRONO_BOVINE_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)CHRONO_BOVINE_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (TEMPORAL_CAPRID_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)TEMPORAL_CAPRID_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      if (CHRONO_URSID_SPAWN_EGG.get() instanceof DeferredSpawnEggItem) {
         ((DeferredSpawnEggItem)CHRONO_URSID_SPAWN_EGG.get()).initializeSpawnEgg();
      }

      ChronoDawn.LOGGER.debug("Spawn eggs initialized");
   }

   public static void populateCreativeTab(ItemDisplayParameters params, Output output) {
      output.accept((ItemLike)CLOCKSTONE_ORE.get());
      output.accept((ItemLike)TIME_CRYSTAL_ORE.get());
      output.accept((ItemLike)TEMPORAL_COAL_ORE.get());
      output.accept((ItemLike)TEMPORAL_GOLD_ORE.get());
      output.accept((ItemLike)TEMPORAL_IRON_ORE.get());
      output.accept((ItemLike)TEMPORAL_REDSTONE_ORE.get());
      output.accept((ItemLike)ENTROPY_CRYSTAL_ORE.get());
      output.accept((ItemLike)TEMPORAL_AMBER_ORE.get());
      output.accept((ItemLike)DEEPSLATE_TEMPORAL_AMBER_ORE.get());
      output.accept((ItemLike)DEEPSLATE_TEMPORAL_GOLD_ORE.get());
      output.accept((ItemLike)DEEPSLATE_TEMPORAL_REDSTONE_ORE.get());
      output.accept((ItemLike)DEEPSLATE_CLOCKSTONE_ORE.get());
      output.accept((ItemLike)CLOCKSTONE_BLOCK.get());
      output.accept((ItemLike)CLOCKSTONE_STAIRS.get());
      output.accept((ItemLike)CLOCKSTONE_SLAB.get());
      output.accept((ItemLike)CLOCKSTONE_WALL.get());
      output.accept((ItemLike)REVERSING_TIME_SANDSTONE.get());
      output.accept((ItemLike)UNSTABLE_FUNGUS.get());
      output.accept((ItemLike)TIME_WOOD_LOG.get());
      output.accept((ItemLike)STRIPPED_TIME_WOOD_LOG.get());
      output.accept((ItemLike)TIME_WOOD.get());
      output.accept((ItemLike)STRIPPED_TIME_WOOD.get());
      output.accept((ItemLike)TIME_WOOD_LEAVES.get());
      output.accept((ItemLike)TIME_WOOD_PLANKS.get());
      output.accept((ItemLike)TIME_WOOD_STAIRS.get());
      output.accept((ItemLike)TIME_WOOD_SLAB.get());
      output.accept((ItemLike)TIME_WOOD_FENCE.get());
      output.accept((ItemLike)TIME_WOOD_DOOR.get());
      output.accept((ItemLike)TIME_WOOD_TRAPDOOR.get());
      output.accept((ItemLike)TIME_WOOD_FENCE_GATE.get());
      output.accept((ItemLike)TIME_WOOD_BUTTON.get());
      output.accept((ItemLike)TIME_WOOD_PRESSURE_PLATE.get());
      output.accept((ItemLike)TIME_WOOD_SAPLING.get());
      output.accept((ItemLike)DARK_TIME_WOOD_LOG.get());
      output.accept((ItemLike)STRIPPED_DARK_TIME_WOOD_LOG.get());
      output.accept((ItemLike)DARK_TIME_WOOD.get());
      output.accept((ItemLike)STRIPPED_DARK_TIME_WOOD.get());
      output.accept((ItemLike)DARK_TIME_WOOD_LEAVES.get());
      output.accept((ItemLike)DARK_TIME_WOOD_PLANKS.get());
      output.accept((ItemLike)DARK_TIME_WOOD_STAIRS.get());
      output.accept((ItemLike)DARK_TIME_WOOD_SLAB.get());
      output.accept((ItemLike)DARK_TIME_WOOD_FENCE.get());
      output.accept((ItemLike)DARK_TIME_WOOD_DOOR.get());
      output.accept((ItemLike)DARK_TIME_WOOD_TRAPDOOR.get());
      output.accept((ItemLike)DARK_TIME_WOOD_FENCE_GATE.get());
      output.accept((ItemLike)DARK_TIME_WOOD_BUTTON.get());
      output.accept((ItemLike)DARK_TIME_WOOD_PRESSURE_PLATE.get());
      output.accept((ItemLike)DARK_TIME_WOOD_SAPLING.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD_LOG.get());
      output.accept((ItemLike)STRIPPED_ANCIENT_TIME_WOOD_LOG.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD.get());
      output.accept((ItemLike)STRIPPED_ANCIENT_TIME_WOOD.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD_LEAVES.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD_PLANKS.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD_STAIRS.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD_SLAB.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD_FENCE.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD_DOOR.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD_TRAPDOOR.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD_FENCE_GATE.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD_BUTTON.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD_PRESSURE_PLATE.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD_SAPLING.get());
      output.accept((ItemLike)TIME_WHEAT_BALE.get());
      output.accept((ItemLike)CLOCK_TOWER_TELEPORTER.get());
      output.accept((ItemLike)CLOCKWORK_BLOCK.get());
      output.accept((ItemLike)CLOCKWORK_DIAL.get());
      output.accept((ItemLike)TIME_CRYSTAL_BLOCK.get());
      output.accept((ItemLike)TEMPORAL_BRICKS.get());
      output.accept((ItemLike)TEMPORAL_BRICKS_STAIRS.get());
      output.accept((ItemLike)TEMPORAL_BRICKS_SLAB.get());
      output.accept((ItemLike)TEMPORAL_BRICKS_WALL.get());
      output.accept((ItemLike)TEMPORAL_MOSS.get());
      output.accept((ItemLike)TEMPORAL_DIRT.get());
      output.accept((ItemLike)TEMPORAL_GRASS_BLOCK.get());
      output.accept((ItemLike)COARSE_TEMPORAL_DIRT.get());
      output.accept((ItemLike)PARCHED_TEMPORAL_DIRT.get());
      output.accept((ItemLike)TEMPORAL_STONE.get());
      output.accept((ItemLike)TEMPORAL_STONE_STAIRS.get());
      output.accept((ItemLike)TEMPORAL_STONE_SLAB.get());
      output.accept((ItemLike)TEMPORAL_STONE_WALL.get());
      output.accept((ItemLike)TEMPORAL_SAND.get());
      output.accept((ItemLike)TEMPORAL_GRAVEL.get());
      output.accept((ItemLike)TEMPORAL_SANDSTONE.get());
      output.accept((ItemLike)TEMPORAL_SANDSTONE_STAIRS.get());
      output.accept((ItemLike)TEMPORAL_SANDSTONE_SLAB.get());
      output.accept((ItemLike)TEMPORAL_SANDSTONE_WALL.get());
      output.accept((ItemLike)TEMPORAL_COBBLESTONE.get());
      output.accept((ItemLike)TEMPORAL_COBBLESTONE_STAIRS.get());
      output.accept((ItemLike)TEMPORAL_COBBLESTONE_SLAB.get());
      output.accept((ItemLike)TEMPORAL_COBBLESTONE_WALL.get());
      output.accept((ItemLike)MOSSY_TEMPORAL_COBBLESTONE.get());
      output.accept((ItemLike)MOSSY_TEMPORAL_COBBLESTONE_STAIRS.get());
      output.accept((ItemLike)MOSSY_TEMPORAL_COBBLESTONE_SLAB.get());
      output.accept((ItemLike)MOSSY_TEMPORAL_COBBLESTONE_WALL.get());
      output.accept((ItemLike)TEMPORAL_STONE_BRICKS.get());
      output.accept((ItemLike)TEMPORAL_STONE_BRICKS_STAIRS.get());
      output.accept((ItemLike)TEMPORAL_STONE_BRICKS_SLAB.get());
      output.accept((ItemLike)TEMPORAL_STONE_BRICKS_WALL.get());
      output.accept((ItemLike)MOSSY_TEMPORAL_STONE_BRICKS.get());
      output.accept((ItemLike)MOSSY_TEMPORAL_STONE_BRICKS_STAIRS.get());
      output.accept((ItemLike)MOSSY_TEMPORAL_STONE_BRICKS_SLAB.get());
      output.accept((ItemLike)MOSSY_TEMPORAL_STONE_BRICKS_WALL.get());
      output.accept((ItemLike)TEMPORAL_STONE_BUTTON.get());
      output.accept((ItemLike)TEMPORAL_STONE_PRESSURE_PLATE.get());
      output.accept((ItemLike)DEEPSLATE_TEMPORAL_STONE.get());
      output.accept((ItemLike)COBBLED_DEEPSLATE_TEMPORAL_STONE.get());
      output.accept((ItemLike)COBBLED_DEEPSLATE_TEMPORAL_STONE_STAIRS.get());
      output.accept((ItemLike)COBBLED_DEEPSLATE_TEMPORAL_STONE_SLAB.get());
      output.accept((ItemLike)COBBLED_DEEPSLATE_TEMPORAL_STONE_WALL.get());
      output.accept((ItemLike)POLISHED_DEEPSLATE_TEMPORAL_STONE.get());
      output.accept((ItemLike)POLISHED_DEEPSLATE_TEMPORAL_STONE_STAIRS.get());
      output.accept((ItemLike)POLISHED_DEEPSLATE_TEMPORAL_STONE_SLAB.get());
      output.accept((ItemLike)POLISHED_DEEPSLATE_TEMPORAL_STONE_WALL.get());
      output.accept((ItemLike)TEMPORAL_POINTED_DRIPSTONE.get());
      output.accept((ItemLike)FROZEN_TIME_ICE.get());
      output.accept((ItemLike)CLOCKSTONE.get());
      output.accept((ItemLike)TIME_CRYSTAL.get());
      output.accept((ItemLike)ENHANCED_CLOCKSTONE.get());
      output.accept((ItemLike)ENTROPY_CRYSTAL.get());
      output.accept((ItemLike)RAW_TEMPORAL_AMBER.get());
      output.accept((ItemLike)TEMPORAL_AMBER_DUST.get());
      output.accept((ItemLike)UNSTABLE_HOURGLASS.get());
      output.accept((ItemLike)FRAGMENT_OF_STASIS_CORE.get());
      output.accept((ItemLike)EYE_OF_CHRONOS.get());
      output.accept((ItemLike)TIME_HOURGLASS.get());
      output.accept((ItemLike)PORTAL_STABILIZER.get());
      output.accept((ItemLike)CHRONICLE_BOOK.get());
      output.accept(TimeCompassItem.createCompass("desert_clock_tower"));
      output.accept((ItemLike)FRUIT_OF_TIME.get());
      output.accept((ItemLike)TIME_FRUIT_PIE.get());
      output.accept((ItemLike)TIME_JAM.get());
      output.accept((ItemLike)TIME_WHEAT_SEEDS.get());
      output.accept((ItemLike)TIME_WHEAT.get());
      output.accept((ItemLike)TIME_BREAD.get());
      output.accept((ItemLike)TEMPORAL_ROOT.get());
      output.accept((ItemLike)BAKED_TEMPORAL_ROOT.get());
      output.accept((ItemLike)CHRONO_MELON_SEEDS.get());
      output.accept((ItemLike)CHRONO_MELON_SLICE.get());
      output.accept((ItemLike)CHRONO_MELON.get());
      output.accept((ItemLike)TIMELESS_MUSHROOM.get());
      output.accept((ItemLike)PURPLE_TIME_BLOSSOM.get());
      output.accept((ItemLike)ORANGE_TIME_BLOSSOM.get());
      output.accept((ItemLike)PINK_TIME_BLOSSOM.get());
      output.accept((ItemLike)DAWN_BELL.get());
      output.accept((ItemLike)DUSK_BELL.get());
      output.accept((ItemLike)TEMPORAL_TALL_GRASS.get());
      output.accept((ItemLike)TEMPORAL_FERN.get());
      output.accept((ItemLike)TEMPORAL_GRASS.get());
      output.accept((ItemLike)FADED_TEMPORAL_GRASS.get());
      output.accept((ItemLike)TEMPORAL_DEAD_BUSH.get());
      output.accept((ItemLike)TEMPORAL_KELP.get());
      output.accept((ItemLike)TEMPORAL_SEAGRASS.get());
      output.accept((ItemLike)TALL_TEMPORAL_SEAGRASS.get());
      output.accept((ItemLike)LUMEN_POLYP.get());
      output.accept((ItemLike)DAWN_CHRONO_CORAL.get());
      output.accept((ItemLike)DUSK_CHRONO_CORAL.get());
      output.accept((ItemLike)TWILIGHT_CHRONO_CORAL.get());
      output.accept((ItemLike)ETERNAL_CHRONO_CORAL.get());
      output.accept((ItemLike)DRIED_TEMPORAL_KELP.get());
      output.accept((ItemLike)PURPLE_TIME_TORCH.get());
      output.accept((ItemLike)ORANGE_TIME_TORCH.get());
      output.accept((ItemLike)PINK_TIME_TORCH.get());
      output.accept((ItemLike)TEMPORAL_LANTERN.get());
      output.accept((ItemLike)DAWN_LANTERN.get());
      output.accept((ItemLike)DUSK_LANTERN.get());
      output.accept((ItemLike)TEMPORAL_ROOT_STEW.get());
      output.accept((ItemLike)TIMELESS_MUSHROOM_SOUP.get());
      output.accept((ItemLike)GLISTENING_CHRONO_MELON.get());
      output.accept((ItemLike)CHRONO_MELON_JUICE.get());
      output.accept((ItemLike)ENHANCED_TIME_BREAD.get());
      output.accept((ItemLike)TIME_WHEAT_COOKIE.get());
      output.accept((ItemLike)CLOCKWORK_COOKIE.get());
      output.accept((ItemLike)GOLDEN_TIME_WHEAT.get());
      output.accept((ItemLike)CHRONO_BOVINE_MEAT.get());
      output.accept((ItemLike)COOKED_CHRONO_BOVINE_MEAT.get());
      output.accept((ItemLike)GLIDE_FISH.get());
      output.accept((ItemLike)COOKED_GLIDE_FISH.get());
      output.accept((ItemLike)CLOCKSTONE_SWORD.get());
      output.accept((ItemLike)CLOCKSTONE_PICKAXE.get());
      output.accept((ItemLike)CLOCKSTONE_AXE.get());
      output.accept((ItemLike)CLOCKSTONE_SHOVEL.get());
      output.accept((ItemLike)CLOCKSTONE_HOE.get());
      output.accept((ItemLike)CLOCKSTONE_HELMET.get());
      output.accept((ItemLike)CLOCKSTONE_CHESTPLATE.get());
      output.accept((ItemLike)CLOCKSTONE_LEGGINGS.get());
      output.accept((ItemLike)CLOCKSTONE_BOOTS.get());
      output.accept((ItemLike)ENHANCED_CLOCKSTONE_SWORD.get());
      output.accept((ItemLike)ENHANCED_CLOCKSTONE_PICKAXE.get());
      output.accept((ItemLike)ENHANCED_CLOCKSTONE_AXE.get());
      output.accept((ItemLike)ENHANCED_CLOCKSTONE_SHOVEL.get());
      output.accept((ItemLike)ENHANCED_CLOCKSTONE_HOE.get());
      output.accept((ItemLike)ENHANCED_CLOCKSTONE_HELMET.get());
      output.accept((ItemLike)ENHANCED_CLOCKSTONE_CHESTPLATE.get());
      output.accept((ItemLike)ENHANCED_CLOCKSTONE_LEGGINGS.get());
      output.accept((ItemLike)ENHANCED_CLOCKSTONE_BOOTS.get());
      output.accept((ItemLike)TEMPORAL_AMBER_HELMET.get());
      output.accept((ItemLike)TEMPORAL_AMBER_CHESTPLATE.get());
      output.accept((ItemLike)TEMPORAL_AMBER_LEGGINGS.get());
      output.accept((ItemLike)TEMPORAL_AMBER_BOOTS.get());
      output.accept((ItemLike)ENTROPY_CRYSTAL_SWORD.get());
      output.accept((ItemLike)CHRONOBLADE.get());
      output.accept((ItemLike)TIME_TYRANT_MAIL.get());
      output.accept((ItemLike)ECHOING_TIME_BOOTS.get());
      output.accept((ItemLike)UNSTABLE_POCKET_WATCH.get());
      output.accept((ItemLike)TIME_CLOCK.get());
      output.accept((ItemLike)SPATIALLY_LINKED_PICKAXE.get());
      output.accept((ItemLike)TIME_ARROW.get());
      output.accept((ItemLike)KEY_TO_MASTER_CLOCK.get());
      output.accept((ItemLike)ANCIENT_GEAR.get());
      output.accept((ItemLike)GUARDIAN_STONE.get());
      output.accept((ItemLike)COLOSSUS_GEAR.get());
      output.accept((ItemLike)PHANTOM_ESSENCE.get());
      output.accept((ItemLike)ENTROPY_CORE.get());
      output.accept((ItemLike)CHRONO_AEGIS.get());
      output.accept((ItemLike)CLOCKSTONE_SHIELD.get());
      output.accept((ItemLike)ENHANCED_CLOCKSTONE_SHIELD.get());
      output.accept((ItemLike)ENTROPY_CRYSTAL_SHIELD.get());
      output.accept((ItemLike)TIME_WOOD_BOAT.get());
      output.accept((ItemLike)TIME_WOOD_CHEST_BOAT.get());
      output.accept((ItemLike)DARK_TIME_WOOD_BOAT.get());
      output.accept((ItemLike)DARK_TIME_WOOD_CHEST_BOAT.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD_BOAT.get());
      output.accept((ItemLike)ANCIENT_TIME_WOOD_CHEST_BOAT.get());
      if (TEMPORAL_WRAITH_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)TEMPORAL_WRAITH_SPAWN_EGG.get());
      }

      if (CLOCKWORK_SENTINEL_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)CLOCKWORK_SENTINEL_SPAWN_EGG.get());
      }

      if (TIME_KEEPER_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)TIME_KEEPER_SPAWN_EGG.get());
      }

      if (FLOQ_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)FLOQ_SPAWN_EGG.get());
      }

      if (EPOCH_HUSK_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)EPOCH_HUSK_SPAWN_EGG.get());
      }

      if (FORGOTTEN_MINUTE_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)FORGOTTEN_MINUTE_SPAWN_EGG.get());
      }

      if (CHRONAL_LEECH_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)CHRONAL_LEECH_SPAWN_EGG.get());
      }

      if (MOMENT_CREEPER_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)MOMENT_CREEPER_SPAWN_EGG.get());
      }

      if (GLIDE_FISH_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)GLIDE_FISH_SPAWN_EGG.get());
      }

      if (TIMELINE_STRIDER_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)TIMELINE_STRIDER_SPAWN_EGG.get());
      }

      if (HOURGLASS_GOLEM_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)HOURGLASS_GOLEM_SPAWN_EGG.get());
      }

      if (SECONDHAND_ARCHER_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)SECONDHAND_ARCHER_SPAWN_EGG.get());
      }

      if (PARADOX_CRAWLER_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)PARADOX_CRAWLER_SPAWN_EGG.get());
      }

      if (CHRONO_TURTLE_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)CHRONO_TURTLE_SPAWN_EGG.get());
      }

      if (TIMEBOUND_RABBIT_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)TIMEBOUND_RABBIT_SPAWN_EGG.get());
      }

      if (PULSE_HOG_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)PULSE_HOG_SPAWN_EGG.get());
      }

      if (SECONDWING_FOWL_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)SECONDWING_FOWL_SPAWN_EGG.get());
      }

      if (TICKING_SHEEP_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)TICKING_SHEEP_SPAWN_EGG.get());
      }

      if (CHRONO_BOVINE_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)CHRONO_BOVINE_SPAWN_EGG.get());
      }

      if (TEMPORAL_CAPRID_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)TEMPORAL_CAPRID_SPAWN_EGG.get());
      }

      if (CHRONO_URSID_SPAWN_EGG.isPresent()) {
         output.accept((ItemLike)CHRONO_URSID_SPAWN_EGG.get());
      }
   }
}
