package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.gen.feature.placement.AvoidStructureFilter;
import com.google.common.collect.ImmutableList.Builder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class ESPlacedFeatures {
   public static final ResourceKey<PlacedFeature> FINAL_MODIFICATION = create("final_modification");
   public static final ResourceKey<PlacedFeature> STONE_SPIKE = create("stone_spike");
   public static final ResourceKey<PlacedFeature> MONOLITH = create("monolith");
   public static final ResourceKey<PlacedFeature> LUSH_MONOLITH = create("lush_monolith");
   public static final ResourceKey<PlacedFeature> GLACITE = create("glacite");
   public static final ResourceKey<PlacedFeature> ICICLE = create("icicle");
   public static final ResourceKey<PlacedFeature> HANGING_ICICLE = create("hanging_icicle");
   public static final ResourceKey<PlacedFeature> STONE_ORE = create("stone_ore");
   public static final ResourceKey<PlacedFeature> DEEPSLATE_ORE = create("deepslate_ore");
   public static final ResourceKey<PlacedFeature> GLOWING_GRIMSTONE_ORE = create("glowing_grimstone_ore");
   public static final ResourceKey<PlacedFeature> GLOWING_VOIDSTONE_ORE = create("glowing_voidstone_ore");
   public static final ResourceKey<PlacedFeature> GLOWING_NIGHTFALL_MUD_ORE = create("glowing_nightfall_mud_ore");
   public static final ResourceKey<PlacedFeature> NIGHTFALL_DIRT_ORE = create("nightfall_dirt_ore");
   public static final ResourceKey<PlacedFeature> DIMSLAG_ORE = create("dimslag_ore");
   public static final ResourceKey<PlacedFeature> SAND_ORE = create("sand_ore");
   public static final ResourceKey<PlacedFeature> UPPER_STARLIT_DIAMOND_ORE = create("upper_starlit_diamond_ore");
   public static final ResourceKey<PlacedFeature> LOWER_STARLIT_DIAMOND_ORE = create("lower_starlit_diamond_ore");
   public static final ResourceKey<PlacedFeature> UPPER_DEEPSILVER_ORE = create("upper_deepsilver_ore");
   public static final ResourceKey<PlacedFeature> LOWER_DEEPSILVER_ORE = create("lower_deepsilver_ore");
   public static final ResourceKey<PlacedFeature> UPPER_MALARITE_ORE = create("upper_malarite_ore");
   public static final ResourceKey<PlacedFeature> LOWER_MALARITE_ORE = create("lower_malarite_ore");
   public static final ResourceKey<PlacedFeature> UPPER_REDSTONE_ORE = create("upper_redstone_ore");
   public static final ResourceKey<PlacedFeature> LOWER_REDSTONE_ORE = create("lower_redstone_ore");
   public static final ResourceKey<PlacedFeature> UPPER_SALTPETER_ORE = create("upper_saltpeter_ore");
   public static final ResourceKey<PlacedFeature> LOWER_SALTPETER_ORE = create("lower_saltpeter_ore");
   public static final ResourceKey<PlacedFeature> UPPER_STARCORE_ORE = create("upper_starcore_ore");
   public static final ResourceKey<PlacedFeature> LOWER_STARCORE_ORE = create("lower_starcore_ore");
   public static final ResourceKey<PlacedFeature> FALLEN_LUNAR_LOG = create("fallen_lunar_log");
   public static final ResourceKey<PlacedFeature> FALLEN_NORTHLAND_LOG = create("fallen_northland_log");
   public static final ResourceKey<PlacedFeature> FALLEN_BANYIN_LOG = create("fallen_banyin_log");
   public static final ResourceKey<PlacedFeature> FALLEN_SCARLET_LOG = create("fallen_scarlet_log");
   public static final ResourceKey<PlacedFeature> SCARLET_LEAVES_PILE = create("scarlet_leaves_pile");
   public static final ResourceKey<PlacedFeature> ASHEN_SNOW = create("ashen_snow");
   public static final ResourceKey<PlacedFeature> STARLIGHT_CRYSTAL = create("starlight_crystal");
   public static final ResourceKey<PlacedFeature> STARLIGHT_CRYSTAL_SURFACE = create("starlight_crystal_surface");
   public static final ResourceKey<PlacedFeature> ICE_SPIKE = create("ice_spike");
   public static final ResourceKey<PlacedFeature> STELLAGMITE = create("stellagmite");
   public static final ResourceKey<PlacedFeature> THIOQUARTZ_GEODE = create("thioquartz_geode");
   public static final ResourceKey<PlacedFeature> CAVE_VINE = create("cave_vine");
   public static final ResourceKey<PlacedFeature> CAVE_MOSS = create("cave_moss");
   public static final ResourceKey<PlacedFeature> CAVE_MOSS_VEIN = create("cave_moss_vein");
   public static final ResourceKey<PlacedFeature> BOULDERSHROOM = create("bouldershroom");
   public static final ResourceKey<PlacedFeature> HANGING_FANTAGRASS = create("hanging_fantagrass");
   public static final ResourceKey<PlacedFeature> ABYSSAL_KELP = create("abyssal_kelp");
   public static final ResourceKey<PlacedFeature> ORBFLORA = create("orbflora");
   public static final ResourceKey<PlacedFeature> SPIRAL_KELP = create("spiral_kelp");
   public static final ResourceKey<PlacedFeature> SEA_ROSA = create("sea_rosa");
   public static final ResourceKey<PlacedFeature> LUMENSTEM = create("lumenstem");
   public static final ResourceKey<PlacedFeature> OCEAN_VEGETATION = create("ocean_vegetation");
   public static final ResourceKey<PlacedFeature> SPIRAL_KELP_FOREST_VEGETATION = create("spiral_kelp_forest_vegetation");
   public static final ResourceKey<PlacedFeature> LUSH_SHALLOW_SEA_VEGETATION = create("lush_shallow_sea_vegetation");
   public static final ResourceKey<PlacedFeature> ABYSSLATE_PATCH = create("abysslate_patch");
   public static final ResourceKey<PlacedFeature> THERMABYSSLATE_PATCH = create("thermabysslate_patch");
   public static final ResourceKey<PlacedFeature> CRYOBYSSLATE_PATCH = create("cryobysslate_patch");
   public static final ResourceKey<PlacedFeature> ABYSSAL_CAVE = create("abyssal_cave");
   public static final ResourceKey<PlacedFeature> VELVETUMOSS = create("velvetumoss");
   public static final ResourceKey<PlacedFeature> RED_VELVETUMOSS = create("red_velvetumoss");
   public static final ResourceKey<PlacedFeature> LUNAR_TREE_CHECKED = create("lunar_tree_checked");
   public static final ResourceKey<PlacedFeature> LUNAR_HUGE_TREE_CHECKED = create("lunar_huge_tree_checked");
   public static final ResourceKey<PlacedFeature> LUNAR_HUGE_STARFIRE_BIRDS_TREE_CHECKED = create("lunar_huge_starfire_birds_tree_checked");
   public static final ResourceKey<PlacedFeature> LUNAR_SMALL_TREE_CHECKED = create("lunar_small_tree_checked");
   public static final ResourceKey<PlacedFeature> NORTHLAND_TREE_CHECKED = create("northland_tree_checked");
   public static final ResourceKey<PlacedFeature> NORTHLAND_THIN_TREE_CHECKED = create("northland_thin_tree_checked");
   public static final ResourceKey<PlacedFeature> NORTHLAND_TALL_TREE_CHECKED = create("northland_tall_tree_checked");
   public static final ResourceKey<PlacedFeature> BANYIN_TREE_CHECKED = create("banyin_tree_checked");
   public static final ResourceKey<PlacedFeature> BANYIN_HUGE_TREE_CHECKED = create("banyin_huge_tree_checked");
   public static final ResourceKey<PlacedFeature> SCARLET_TREE_CHECKED = create("scarlet_tree_checked");
   public static final ResourceKey<PlacedFeature> TORREYA_TREE_CHECKED = create("torreya_tree_checked");
   public static final ResourceKey<PlacedFeature> TORREYA_STARFIRE_BIRDS_TREE_CHECKED = create("torreya_starfire_birds_tree_checked");
   public static final ResourceKey<PlacedFeature> JINGLESTEM_CHECKED = create("jinglestem_checked");
   public static final ResourceKey<PlacedFeature> HUGE_MARIMOLD_CHECKED = create("huge_marimold_checked");
   public static final ResourceKey<PlacedFeature> HUGE_GLOWING_MUSHROOM_CHECKED = create("huge_glowing_mushroom_checked");
   public static final ResourceKey<PlacedFeature> HUGE_SHINING_MUSHROOM_CHECKED = create("huge_shining_mushroom_checked");
   public static final ResourceKey<PlacedFeature> NORTHLAND_ON_SNOW = create("northland_on_snow");
   public static final ResourceKey<PlacedFeature> STARLIGHT_FOREST = create("starlight_forest");
   public static final ResourceKey<PlacedFeature> DENSE_FOREST = create("dense_forest");
   public static final ResourceKey<PlacedFeature> SPARSE_FOREST = create("sparse_forest");
   public static final ResourceKey<PlacedFeature> SCRUBLAND_FOREST = create("scrubland_forest");
   public static final ResourceKey<PlacedFeature> SWAMP_FOREST = create("swamp_forest");
   public static final ResourceKey<PlacedFeature> PERMAFROST_FOREST = create("permafrost_forest");
   public static final ResourceKey<PlacedFeature> TAIGA_FOREST = create("taiga_forest");
   public static final ResourceKey<PlacedFeature> SCARLET_FOREST = create("scarlet_forest");
   public static final ResourceKey<PlacedFeature> TORREYA_FOREST = create("torreya_forest");
   public static final ResourceKey<PlacedFeature> MUSHROOM_FOREST = create("mushroom_forest");
   public static final ResourceKey<PlacedFeature> JINGLESTEM_FOREST = create("jinglestem_forest");
   public static final ResourceKey<PlacedFeature> DEAD_LUNAR_TREE = create("dead_lunar_tree");
   public static final ResourceKey<PlacedFeature> LUNARIS_CACTUS = create("lunaris_cactus");
   public static final ResourceKey<PlacedFeature> FOREST_VEGETATION = create("forest_vegetation");
   public static final ResourceKey<PlacedFeature> PLAINS_VEGETATION = create("plains_vegetation");
   public static final ResourceKey<PlacedFeature> SWAMP_VEGETATION = create("swamp_vegetation");
   public static final ResourceKey<PlacedFeature> UNDERGROUND_SWAMP_VEGETATION = create("underground_swamp_vegetation");
   public static final ResourceKey<PlacedFeature> PERMAFROST_FOREST_VEGETATION = create("permafrost_forest_vegetation");
   public static final ResourceKey<PlacedFeature> SCARLET_FOREST_VEGETATION = create("scarlet_forest_vegetation");
   public static final ResourceKey<PlacedFeature> TORREYA_FOREST_VEGETATION = create("torreya_forest_vegetation");
   public static final ResourceKey<PlacedFeature> DESERT_VEGETATION = create("desert_vegetation");
   public static final ResourceKey<PlacedFeature> BEACH_VEGETATION = create("beach_vegetation");
   public static final ResourceKey<PlacedFeature> MUSHROOM_VEGETATION = create("mushroom_vegetation");
   public static final ResourceKey<PlacedFeature> CAVE_MOSS_PATCH = create("cave_moss_patch");
   public static final ResourceKey<PlacedFeature> WATERSIDE_VEGETATION = create("waterside_vegetation");
   public static final ResourceKey<PlacedFeature> WATER_SURFACE_VEGETATION = create("water_surface_plant");
   public static final ResourceKey<PlacedFeature> CRYSTAL_CAVES_VEGETATION = create("crystal_caves_vegetation");
   public static final ResourceKey<PlacedFeature> RED_CRYSTAL_MOSS_PATCH = create("red_crystal_moss_patch");
   public static final ResourceKey<PlacedFeature> RED_CRYSTAL_MOSS_PATCH_CEILING = create("red_crystal_moss_patch_ceiling");
   public static final ResourceKey<PlacedFeature> BLUE_CRYSTAL_MOSS_PATCH = create("blue_crystal_moss_patch");
   public static final ResourceKey<PlacedFeature> BLUE_CRYSTAL_MOSS_PATCH_CEILING = create("blue_crystal_moss_patch_ceiling");
   public static final ResourceKey<PlacedFeature> SWAMP_WATER = create("swamp_water");
   public static final ResourceKey<PlacedFeature> HOT_SPRING = create("hot_spring");

   public static void bootstrap(BootstrapContext<PlacedFeature> context) {
      HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
      HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
      BlockPredicate snowPredicate = BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), new Block[]{Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW});
      List<PlacementModifier> onSnow = List.of(
         EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(new Block[]{Blocks.POWDER_SNOW})), 8),
         BlockPredicateFilter.forPredicate(snowPredicate)
      );
      List<BlockPredicate> nearWater = new ArrayList<>();

      for (int x = -3; x <= 3; x++) {
         for (int y = -2; y <= 2; y++) {
            for (int z = -3; z <= 3; z++) {
               nearWater.add(BlockPredicate.matchesFluids(new BlockPos(x, y, z), new Fluid[]{Fluids.WATER, Fluids.FLOWING_WATER}));
            }
         }
      }

      PlacementUtils.register(
         context,
         FINAL_MODIFICATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.FINAL_MODIFICATION),
         new PlacementModifier[]{PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         STONE_SPIKE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.STONE_SPIKE),
         new PlacementModifier[]{
            RarityFilter.onAverageOnceEvery(25),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_TOP_SOLID,
            new AvoidStructureFilter(structures.getOrThrow(ESTags.Structures.BOSS_LANDMARKS)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         MONOLITH,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.MONOLITH),
         new PlacementModifier[]{
            RarityFilter.onAverageOnceEvery(35),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_TOP_SOLID,
            new AvoidStructureFilter(structures.getOrThrow(ESTags.Structures.BOSS_LANDMARKS)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         LUSH_MONOLITH,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.LUSH_MONOLITH),
         new PlacementModifier[]{
            RarityFilter.onAverageOnceEvery(35),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_TOP_SOLID,
            new AvoidStructureFilter(structures.getOrThrow(ESTags.Structures.BOSS_LANDMARKS)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         GLACITE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.GLACITE),
         new PlacementModifier[]{
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)),
            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         ICICLE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.ICICLE),
         new PlacementModifier[]{
            CountPlacement.of(30),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(65)),
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.hasSturdyFace(Direction.UP), BlockPredicate.ONLY_IN_AIR_PREDICATE, 20),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            new AvoidStructureFilter(structures.getOrThrow(ESTags.Structures.BOSS_LANDMARKS)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         HANGING_ICICLE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.ICICLE),
         new PlacementModifier[]{
            CountPlacement.of(30),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(65)),
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 20),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            new AvoidStructureFilter(structures.getOrThrow(ESTags.Structures.BOSS_LANDMARKS)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         STONE_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.STONE_ORE),
         commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top()))
      );
      PlacementUtils.register(
         context,
         DEEPSLATE_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.DEEPSLATE_ORE),
         commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0)))
      );
      PlacementUtils.register(
         context,
         GLOWING_GRIMSTONE_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.GLOWING_GRIMSTONE_ORE),
         commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top()))
      );
      PlacementUtils.register(
         context,
         GLOWING_VOIDSTONE_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.GLOWING_VOIDSTONE_ORE),
         commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0)))
      );
      PlacementUtils.register(
         context,
         GLOWING_NIGHTFALL_MUD_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.GLOWING_NIGHTFALL_MUD_ORE),
         commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top()))
      );
      PlacementUtils.register(
         context,
         NIGHTFALL_DIRT_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.NIGHTFALL_DIRT_ORE),
         commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top()))
      );
      PlacementUtils.register(
         context,
         DIMSLAG_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.DIMSLAG_ORE),
         commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()))
      );
      PlacementUtils.register(
         context,
         SAND_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SAND_ORE),
         commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top()))
      );
      PlacementUtils.register(
         context,
         UPPER_STARLIT_DIAMOND_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.STARLIT_DIAMOND_ORE),
         commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top()))
      );
      PlacementUtils.register(
         context,
         LOWER_STARLIT_DIAMOND_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.STARLIT_DIAMOND_ORE),
         commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0)))
      );
      PlacementUtils.register(
         context,
         UPPER_DEEPSILVER_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.DEEPSILVER_ORE),
         commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top()))
      );
      PlacementUtils.register(
         context,
         LOWER_DEEPSILVER_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.DEEPSILVER_ORE),
         commonOrePlacement(15, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0)))
      );
      PlacementUtils.register(
         context,
         UPPER_MALARITE_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.MALARITE_ORE),
         commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top()))
      );
      PlacementUtils.register(
         context,
         LOWER_MALARITE_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.MALARITE_ORE),
         commonOrePlacement(15, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0)))
      );
      PlacementUtils.register(
         context,
         UPPER_REDSTONE_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.REDSTONE_ORE),
         commonOrePlacement(15, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top()))
      );
      PlacementUtils.register(
         context,
         LOWER_REDSTONE_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.REDSTONE_ORE),
         commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0)))
      );
      PlacementUtils.register(
         context,
         UPPER_SALTPETER_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SALTPETER_ORE),
         commonOrePlacement(18, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top()))
      );
      PlacementUtils.register(
         context,
         LOWER_SALTPETER_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SALTPETER_ORE),
         commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0)))
      );
      PlacementUtils.register(
         context,
         UPPER_STARCORE_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.STARCORE_ORE),
         commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top()))
      );
      PlacementUtils.register(
         context,
         LOWER_STARCORE_ORE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.STARCORE_ORE),
         commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0)))
      );
      PlacementUtils.register(
         context,
         FALLEN_LUNAR_LOG,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.FALLEN_LUNAR_LOG),
         new PlacementModifier[]{
            RarityFilter.onAverageOnceEvery(12), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         FALLEN_NORTHLAND_LOG,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.FALLEN_NORTHLAND_LOG),
         new PlacementModifier[]{
            RarityFilter.onAverageOnceEvery(12), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         FALLEN_BANYIN_LOG,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.FALLEN_BANYIN_LOG),
         new PlacementModifier[]{
            RarityFilter.onAverageOnceEvery(12), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         FALLEN_SCARLET_LOG,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.FALLEN_SCARLET_LOG),
         new PlacementModifier[]{
            RarityFilter.onAverageOnceEvery(12), CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         SCARLET_LEAVES_PILE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SCARLET_LEAVES_PILE),
         new PlacementModifier[]{InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         ASHEN_SNOW,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.ASHEN_SNOW),
         new PlacementModifier[]{
            CountPlacement.of(30),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0)),
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.hasSturdyFace(Direction.UP), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         STARLIGHT_CRYSTAL,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.STARLIGHT_CRYSTAL),
         new PlacementModifier[]{
            CountPlacement.of(5),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)),
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.hasSturdyFace(Direction.UP), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         STARLIGHT_CRYSTAL_SURFACE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.STARLIGHT_CRYSTAL),
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         ICE_SPIKE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.ICE_SPIKE),
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         STELLAGMITE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.STELLAGMITE),
         new PlacementModifier[]{
            RarityFilter.onAverageOnceEvery(3),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)),
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.hasSturdyFace(Direction.UP), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         THIOQUARTZ_GEODE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.THIOQUARTZ_GEODE),
         new PlacementModifier[]{
            RarityFilter.onAverageOnceEvery(20),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(30)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         CAVE_VINE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.CAVE_VINE),
         new PlacementModifier[]{
            CountPlacement.of(25),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            new AvoidStructureFilter(structures.getOrThrow(ESTags.Structures.BOSS_LANDMARKS)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         CAVE_MOSS,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.CAVE_MOSS),
         new PlacementModifier[]{
            CountPlacement.of(25),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            new AvoidStructureFilter(structures.getOrThrow(ESTags.Structures.BOSS_LANDMARKS)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         CAVE_MOSS_VEIN,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.CAVE_MOSS_VEIN),
         new PlacementModifier[]{
            CountPlacement.of(UniformInt.of(15, 20)),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            InSquarePlacement.spread(),
            SurfaceRelativeThresholdFilter.of(Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         BOULDERSHROOM,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.BOULDERSHROOM),
         new PlacementModifier[]{
            CountPlacement.of(UniformInt.of(5, 10)),
            HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(10), VerticalAnchor.aboveBottom(85)),
            InSquarePlacement.spread(),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         HANGING_FANTAGRASS,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.HANGING_FANTAGRASS),
         new PlacementModifier[]{
            CountPlacement.of(25),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         ABYSSAL_KELP,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.ABYSSAL_KELP),
         new PlacementModifier[]{
            NoiseBasedCountPlacement.of(20, 80.0, 0.0), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         ORBFLORA,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.ORBFLORA),
         new PlacementModifier[]{
            NoiseBasedCountPlacement.of(15, 90.0, 0.0), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         SPIRAL_KELP,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SPIRAL_KELP),
         new PlacementModifier[]{
            NoiseBasedCountPlacement.of(35, 120.0, 0.5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         SEA_ROSA,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SEA_ROSA),
         new PlacementModifier[]{
            NoiseBasedCountPlacement.of(40, 80.0, 0.75), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         LUMENSTEM,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.LUMENSTEM),
         new PlacementModifier[]{
            NoiseBasedCountPlacement.of(15, 80.0, 0.0), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         OCEAN_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.OCEAN_VEGETATION),
         new PlacementModifier[]{
            NoiseBasedCountPlacement.of(15, 400.0, 0.0), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         SPIRAL_KELP_FOREST_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SPIRAL_KELP_FOREST_VEGETATION),
         new PlacementModifier[]{
            CountPlacement.of(25),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_TOP_SOLID,
            BlockPredicateFilter.forPredicate(
               BlockPredicate.allOf(
                  new BlockPredicate[]{
                     BlockPredicate.matchesBlocks(
                        Direction.DOWN.getNormal(), new Block[]{(Block)ESBlocks.DUSTED_GRAVEL.get(), (Block)ESBlocks.TWILIGHT_SAND.get()}
                     ),
                     BlockPredicate.matchesBlocks(BlockPos.ZERO, new Block[]{Blocks.WATER}),
                     BlockPredicate.matchesBlocks(Direction.UP.getNormal(), new Block[]{Blocks.WATER})
                  }
               )
            ),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         LUSH_SHALLOW_SEA_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.LUSH_SHALLOW_SEA_VEGETATION),
         new PlacementModifier[]{
            CountPlacement.of(25),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_TOP_SOLID,
            BlockPredicateFilter.forPredicate(
               BlockPredicate.allOf(
                  new BlockPredicate[]{
                     BlockPredicate.matchesBlocks(
                        Direction.DOWN.getNormal(),
                        new Block[]{
                           ESBlocks.MOSSY_DUSTED_GRAVEL.get(),
                           ESBlocks.GLOWING_MOSSY_DUSTED_GRAVEL.get(),
                           (Block)ESBlocks.DUSTED_GRAVEL.get(),
                           (Block)ESBlocks.TWILIGHT_SAND.get()
                        }
                     ),
                     BlockPredicate.matchesBlocks(BlockPos.ZERO, new Block[]{Blocks.WATER}),
                     BlockPredicate.matchesBlocks(Direction.UP.getNormal(), new Block[]{Blocks.WATER})
                  }
               )
            ),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         ABYSSLATE_PATCH,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.ABYSSLATE_PATCH),
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         THERMABYSSLATE_PATCH,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.THERMABYSSLATE_PATCH),
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         CRYOBYSSLATE_PATCH,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.CRYOBYSSLATE_PATCH),
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         ABYSSAL_CAVE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.ABYSSAL_CAVE),
         new PlacementModifier[]{InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         VELVETUMOSS,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.VELVETUMOSS),
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         RED_VELVETUMOSS,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.RED_VELVETUMOSS),
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         LUNAR_TREE_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.LUNAR),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.LUNAR_SAPLING.get())}
      );
      PlacementUtils.register(
         context,
         LUNAR_HUGE_TREE_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.LUNAR_HUGE),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.LUNAR_SAPLING.get())}
      );
      PlacementUtils.register(
         context,
         LUNAR_HUGE_STARFIRE_BIRDS_TREE_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.LUNAR_HUGE_STARFIRE_BIRDS),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.LUNAR_SAPLING.get())}
      );
      PlacementUtils.register(
         context,
         LUNAR_SMALL_TREE_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.LUNAR_SMALL),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.LUNAR_SAPLING.get())}
      );
      PlacementUtils.register(
         context,
         NORTHLAND_TREE_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.NORTHLAND),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.NORTHLAND_SAPLING.get())}
      );
      PlacementUtils.register(
         context,
         NORTHLAND_THIN_TREE_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.NORTHLAND_THIN),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.NORTHLAND_SAPLING.get())}
      );
      PlacementUtils.register(
         context,
         NORTHLAND_TALL_TREE_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.NORTHLAND_TALL),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.NORTHLAND_SAPLING.get())}
      );
      PlacementUtils.register(
         context,
         BANYIN_TREE_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.BANYIN),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.BANYIN_SAPLING.get())}
      );
      PlacementUtils.register(
         context,
         BANYIN_HUGE_TREE_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.BANYIN_HUGE),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.BANYIN_SAPLING.get())}
      );
      PlacementUtils.register(
         context,
         SCARLET_TREE_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SCARLET),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.SCARLET_SAPLING.get())}
      );
      PlacementUtils.register(
         context,
         TORREYA_TREE_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.TORREYA),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.TORREYA_SAPLING.get())}
      );
      PlacementUtils.register(
         context,
         TORREYA_STARFIRE_BIRDS_TREE_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.TORREYA_STARFIRE_BIRDS),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.TORREYA_SAPLING.get())}
      );
      PlacementUtils.register(
         context,
         JINGLESTEM_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.JINGLESTEM),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.JINGLESTEM_SAPLING.get())}
      );
      PlacementUtils.register(
         context,
         HUGE_MARIMOLD_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.HUGE_MARIMOLD),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.MARIMOLD.get())}
      );
      PlacementUtils.register(
         context,
         HUGE_GLOWING_MUSHROOM_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.HUGE_GLOWING_MUSHROOM),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.GLOWING_MUSHROOM.get())}
      );
      PlacementUtils.register(
         context,
         HUGE_SHINING_MUSHROOM_CHECKED,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.HUGE_SHINING_MUSHROOM),
         new PlacementModifier[]{PlacementUtils.filteredByBlockSurvival((Block)ESBlocks.SHINING_MUSHROOM.get())}
      );
      PlacementUtils.register(context, NORTHLAND_ON_SNOW, configuredFeatures.getOrThrow(ESConfiguredFeatures.NORTHLAND), onSnow);
      PlacementUtils.register(
         context,
         STARLIGHT_FOREST,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.STARLIGHT_FOREST),
         VegetationPlacements.treePlacement(PlacementUtils.countExtra(8, 0.1F, 1))
      );
      PlacementUtils.register(
         context,
         DENSE_FOREST,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.DENSE_FOREST),
         VegetationPlacements.treePlacement(PlacementUtils.countExtra(9, 0.1F, 2))
      );
      PlacementUtils.register(
         context,
         SPARSE_FOREST,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SPARSE_FOREST),
         new Builder().add(RarityFilter.onAverageOnceEvery(6)).addAll(VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 2))).build()
      );
      PlacementUtils.register(
         context,
         SCRUBLAND_FOREST,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SCRUBLAND_FOREST),
         VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 5))
      );
      PlacementUtils.register(
         context,
         PERMAFROST_FOREST,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.PERMAFROST_FOREST),
         VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.2F, 2))
      );
      PlacementUtils.register(
         context,
         TAIGA_FOREST,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.TAIGA_FOREST),
         VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.2F, 2))
      );
      PlacementUtils.register(
         context,
         SWAMP_FOREST,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SWAMP_FOREST),
         VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.1F, 1))
      );
      PlacementUtils.register(
         context,
         SCARLET_FOREST,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SCARLET_FOREST),
         VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 2))
      );
      PlacementUtils.register(
         context,
         TORREYA_FOREST,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.TORREYA_FOREST),
         VegetationPlacements.treePlacement(PlacementUtils.countExtra(8, 0.1F, 1))
      );
      PlacementUtils.register(
         context,
         MUSHROOM_FOREST,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.MUSHROOM_FOREST),
         VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.05F, 3))
      );
      PlacementUtils.register(
         context,
         JINGLESTEM_FOREST,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.JINGLESTEM_FOREST),
         new PlacementModifier[]{PlacementUtils.countExtra(5, 0.2F, 1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         DEAD_LUNAR_TREE,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.DEAD_LUNAR_TREE),
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         LUNARIS_CACTUS,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.LUNARIS_CACTUS),
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         FOREST_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.FOREST_VEGETATION),
         new PlacementModifier[]{InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         PLAINS_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.PLAINS_VEGETATION),
         new PlacementModifier[]{InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         SWAMP_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SWAMP_VEGETATION),
         new PlacementModifier[]{InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         UNDERGROUND_SWAMP_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.UNDERGROUND_SWAMP_VEGETATION),
         new PlacementModifier[]{
            CountPlacement.of(15),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.absolute(22), VerticalAnchor.absolute(50)),
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.hasSturdyFace(Direction.UP), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         PERMAFROST_FOREST_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.PERMAFROST_FOREST_VEGETATION),
         new PlacementModifier[]{InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         SCARLET_FOREST_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SCARLET_FOREST_VEGETATION),
         new PlacementModifier[]{InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         TORREYA_FOREST_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.TORREYA_FOREST_VEGETATION),
         new PlacementModifier[]{InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         DESERT_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.DESERT_VEGETATION),
         new PlacementModifier[]{InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         BEACH_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.BEACH_VEGETATION),
         new PlacementModifier[]{InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         MUSHROOM_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.MUSHROOM_VEGETATION),
         new PlacementModifier[]{InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         CAVE_MOSS_PATCH,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.CAVE_MOSS_PATCH),
         new PlacementModifier[]{
            CountPlacement.of(5),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)),
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 20),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         WATERSIDE_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.WATERSIDE_VEGETATION),
         new PlacementModifier[]{
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BlockPredicateFilter.forPredicate(BlockPredicate.anyOf(nearWater)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         WATER_SURFACE_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.WATER_SURFACE_VEGETATION),
         new PlacementModifier[]{InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         CRYSTAL_CAVES_VEGETATION,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.CRYSTAL_CAVES_VEGETATION),
         new PlacementModifier[]{
            CountPlacement.of(15),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)),
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.hasSturdyFace(Direction.UP), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         RED_CRYSTAL_MOSS_PATCH,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.RED_CRYSTAL_MOSS_PATCH),
         new PlacementModifier[]{
            CountPlacement.of(5),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)),
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.hasSturdyFace(Direction.UP), BlockPredicate.ONLY_IN_AIR_PREDICATE, 20),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         RED_CRYSTAL_MOSS_PATCH_CEILING,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.RED_CRYSTAL_MOSS_PATCH),
         new PlacementModifier[]{
            CountPlacement.of(10),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)),
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 20),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         BLUE_CRYSTAL_MOSS_PATCH,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.BLUE_CRYSTAL_MOSS_PATCH),
         new PlacementModifier[]{
            CountPlacement.of(5),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)),
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.hasSturdyFace(Direction.UP), BlockPredicate.ONLY_IN_AIR_PREDICATE, 20),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         BLUE_CRYSTAL_MOSS_PATCH_CEILING,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.BLUE_CRYSTAL_MOSS_PATCH),
         new PlacementModifier[]{
            CountPlacement.of(10),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)),
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 20),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            BiomeFilter.biome()
         }
      );
      PlacementUtils.register(
         context,
         SWAMP_WATER,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.SWAMP_WATER),
         new PlacementModifier[]{PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
      PlacementUtils.register(
         context,
         HOT_SPRING,
         configuredFeatures.getOrThrow(ESConfiguredFeatures.HOT_SPRING),
         new PlacementModifier[]{RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()}
      );
   }

   private static ResourceKey<PlacedFeature> create(String name) {
      return ResourceKey.create(Registries.PLACED_FEATURE, EternalStarlight.id(name));
   }

   public static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier1) {
      return List.of(modifier, InSquarePlacement.spread(), modifier1, BiomeFilter.biome());
   }

   public static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
      return orePlacement(CountPlacement.of(count), modifier);
   }
}
