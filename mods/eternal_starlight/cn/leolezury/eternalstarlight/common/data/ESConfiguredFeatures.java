package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.AbyssalKelp;
import cn.leolezury.eternalstarlight.common.block.AbyssalKelpBlock;
import cn.leolezury.eternalstarlight.common.block.AbyssalKelpPlantBlock;
import cn.leolezury.eternalstarlight.common.block.AlwaysSurvivingBushBlock;
import cn.leolezury.eternalstarlight.common.block.BerriesVinesBlock;
import cn.leolezury.eternalstarlight.common.block.BerriesVinesPlantBlock;
import cn.leolezury.eternalstarlight.common.block.CaveMossBlock;
import cn.leolezury.eternalstarlight.common.block.CaveMossCarpetBlock;
import cn.leolezury.eternalstarlight.common.block.CaveMossVeinBlock;
import cn.leolezury.eternalstarlight.common.block.DesertBushBlock;
import cn.leolezury.eternalstarlight.common.block.DesertFlowerBlock;
import cn.leolezury.eternalstarlight.common.block.DoublePlantOnSandBlock;
import cn.leolezury.eternalstarlight.common.block.DoublePlantOnStoneBlock;
import cn.leolezury.eternalstarlight.common.block.JinglingPickleBlock;
import cn.leolezury.eternalstarlight.common.block.LunarisCactusBlock;
import cn.leolezury.eternalstarlight.common.block.MarimoldBlock;
import cn.leolezury.eternalstarlight.common.block.MoonlightBushBlock;
import cn.leolezury.eternalstarlight.common.block.NightfallMudBlock;
import cn.leolezury.eternalstarlight.common.block.PungencyFruitVinesBlock;
import cn.leolezury.eternalstarlight.common.block.SeaBushBlock;
import cn.leolezury.eternalstarlight.common.block.ShortBushBlock;
import cn.leolezury.eternalstarlight.common.block.WaterlilyWithFlowerBlock;
import cn.leolezury.eternalstarlight.common.block.WickGrassBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESFeatures;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.gen.feature.AbysslatePatchFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.BlockPatchFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.ESLakeFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.FallenLogFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.LeavesPileFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.NoiseBooleanSelectorFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.PillarFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.SpikeFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.VelvetumossFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.WaterPlantFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.CradlewoodFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.HugeMarimoldFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.JinglestemFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator.BanyinRootsDecorator;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator.HangingPlantDecorator;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator.StarfireBirdNestDecorator;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator.TorreyaVinesDecorator;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator.TrunkBerriesDecorator;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator.TrunkCobwebDecorator;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage.ScarletFoliagePlacer;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage.SpheroidFoliagePlacer;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage.TorreyaFoliagePlacer;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.trunk.BranchingTrunkPlacer;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration.TreeConfigurationBuilder;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class ESConfiguredFeatures {
   public static final ResourceKey<ConfiguredFeature<?, ?>> FINAL_MODIFICATION = create("final_modification");
   public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_SPIKE = create("stone_spike");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MONOLITH = create("monolith");
   public static final ResourceKey<ConfiguredFeature<?, ?>> LUSH_MONOLITH = create("lush_monolith");
   public static final ResourceKey<ConfiguredFeature<?, ?>> GLACITE = create("glacite");
   public static final ResourceKey<ConfiguredFeature<?, ?>> ICICLE = create("icicle");
   public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_ORE = create("stone_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_ORE = create("deepslate_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWING_GRIMSTONE_ORE = create("glowing_grimstone_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWING_VOIDSTONE_ORE = create("glowing_voidstone_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWING_NIGHTFALL_MUD_ORE = create("glowing_nightfall_mud_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> NIGHTFALL_DIRT_ORE = create("nightfall_dirt_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> DIMSLAG_ORE = create("dimslag_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SAND_ORE = create("sand_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> STARLIT_DIAMOND_ORE = create("starlit_diamond_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSILVER_ORE = create("deepsilver_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MALARITE_ORE = create("malarite_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> REDSTONE_ORE = create("redstone_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SALTPETER_ORE = create("saltpeter_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> STARCORE_ORE = create("starcore_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_LUNAR_LOG = create("fallen_lunar_log");
   public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_NORTHLAND_LOG = create("fallen_northland_log");
   public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BANYIN_LOG = create("fallen_banyin_log");
   public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_SCARLET_LOG = create("fallen_scarlet_log");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SCARLET_LEAVES_PILE = create("scarlet_leaves_pile");
   public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_SNOW = create("ashen_snow");
   public static final ResourceKey<ConfiguredFeature<?, ?>> STARLIGHT_CRYSTAL = create("starlight_crystal");
   public static final ResourceKey<ConfiguredFeature<?, ?>> ICE_SPIKE = create("ice_spike");
   public static final ResourceKey<ConfiguredFeature<?, ?>> STELLAGMITE = create("stellagmite");
   public static final ResourceKey<ConfiguredFeature<?, ?>> THIOQUARTZ_GEODE = create("thioquartz_geode");
   public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_VINE = create("cave_vine");
   public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_MOSS = create("cave_moss");
   public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_MOSS_VEIN = create("cave_moss_vein");
   public static final ResourceKey<ConfiguredFeature<?, ?>> BOULDERSHROOM = create("bouldershroom");
   public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_FANTAGRASS = create("hanging_fantagrass");
   public static final ResourceKey<ConfiguredFeature<?, ?>> ABYSSAL_KELP = create("abyssal_kelp");
   public static final ResourceKey<ConfiguredFeature<?, ?>> ORBFLORA = create("orbflora");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SPIRAL_KELP = create("spiral_kelp");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SEA_ROSA = create("sea_rosa");
   public static final ResourceKey<ConfiguredFeature<?, ?>> LUMENSTEM = create("lumenstem");
   public static final ResourceKey<ConfiguredFeature<?, ?>> OCEAN_VEGETATION = create("ocean_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SPIRAL_KELP_FOREST_VEGETATION = create("spiral_kelp_forest_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> LUSH_SHALLOW_SEA_VEGETATION = create("lush_shallow_sea_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> ABYSSLATE_PATCH = create("abysslate_patch");
   public static final ResourceKey<ConfiguredFeature<?, ?>> THERMABYSSLATE_PATCH = create("thermabysslate_patch");
   public static final ResourceKey<ConfiguredFeature<?, ?>> CRYOBYSSLATE_PATCH = create("cryobysslate_patch");
   public static final ResourceKey<ConfiguredFeature<?, ?>> ABYSSAL_CAVE = create("abyssal_cave");
   public static final ResourceKey<ConfiguredFeature<?, ?>> VELVETUMOSS = create("velvetumoss");
   public static final ResourceKey<ConfiguredFeature<?, ?>> RED_VELVETUMOSS = create("red_velvetumoss");
   public static final ResourceKey<ConfiguredFeature<?, ?>> LUNAR = create("lunar");
   public static final ResourceKey<ConfiguredFeature<?, ?>> LUNAR_HUGE = create("lunar_huge");
   public static final ResourceKey<ConfiguredFeature<?, ?>> LUNAR_HUGE_STARFIRE_BIRDS = create("lunar_huge_starfire_birds");
   public static final ResourceKey<ConfiguredFeature<?, ?>> LUNAR_SMALL = create("lunar_small");
   public static final ResourceKey<ConfiguredFeature<?, ?>> NORTHLAND = create("northland");
   public static final ResourceKey<ConfiguredFeature<?, ?>> NORTHLAND_THIN = create("northland_thin");
   public static final ResourceKey<ConfiguredFeature<?, ?>> NORTHLAND_TALL = create("northland_tall");
   public static final ResourceKey<ConfiguredFeature<?, ?>> BANYIN = create("banyin");
   public static final ResourceKey<ConfiguredFeature<?, ?>> BANYIN_HUGE = create("banyin_huge");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SCARLET = create("scarlet");
   public static final ResourceKey<ConfiguredFeature<?, ?>> TORREYA = create("torreya");
   public static final ResourceKey<ConfiguredFeature<?, ?>> TORREYA_STARFIRE_BIRDS = create("torreya_starfire_birds");
   public static final ResourceKey<ConfiguredFeature<?, ?>> JINGLESTEM = create("jinglestem");
   public static final ResourceKey<ConfiguredFeature<?, ?>> JINGLESTEM_PLANTED = create("jinglestem_planted");
   public static final ResourceKey<ConfiguredFeature<?, ?>> CRADLEWOOD = create("cradlewood");
   public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_MARIMOLD = create("huge_marimold");
   public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_GLOWING_MUSHROOM = create("huge_glowing_mushroom");
   public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_SHINING_MUSHROOM = create("huge_shining_mushroom");
   public static final ResourceKey<ConfiguredFeature<?, ?>> STARLIGHT_FOREST = create("starlight_forest");
   public static final ResourceKey<ConfiguredFeature<?, ?>> DENSE_FOREST = create("dense_forest");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SPARSE_FOREST = create("sparse_forest");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SCRUBLAND_FOREST = create("scrubland_forest");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_FOREST = create("swamp_forest");
   public static final ResourceKey<ConfiguredFeature<?, ?>> PERMAFROST_FOREST = create("permafrost_forest");
   public static final ResourceKey<ConfiguredFeature<?, ?>> TAIGA_FOREST = create("taiga_forest");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SCARLET_FOREST = create("scarlet_forest");
   public static final ResourceKey<ConfiguredFeature<?, ?>> TORREYA_FOREST = create("torreya_forest");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MUSHROOM_FOREST = create("mushroom_forest");
   public static final ResourceKey<ConfiguredFeature<?, ?>> JINGLESTEM_FOREST = create("jinglestem_forest");
   public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_LUNAR_TREE = create("dead_lunar_tree");
   public static final ResourceKey<ConfiguredFeature<?, ?>> LUNARIS_CACTUS = create("lunaris_cactus");
   public static final ResourceKey<ConfiguredFeature<?, ?>> FOREST_VEGETATION = create("forest_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> PLAINS_VEGETATION = create("plains_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_VEGETATION = create("swamp_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> UNDERGROUND_SWAMP_VEGETATION = create("underground_swamp_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> PERMAFROST_FOREST_VEGETATION = create("permafrost_forest_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SCARLET_FOREST_VEGETATION = create("scarlet_forest_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> TORREYA_FOREST_VEGETATION = create("torreya_forest_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> DESERT_VEGETATION = create("desert_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> BEACH_VEGETATION = create("beach_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MUSHROOM_VEGETATION = create("mushroom_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_MOSS_VEGETATION = create("cave_moss_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_MOSS_PATCH = create("cave_moss_patch");
   public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_MOSS_PATCH_BONEMEAL = create("cave_moss_patch_bonemeal");
   public static final ResourceKey<ConfiguredFeature<?, ?>> WATERSIDE_VEGETATION = create("waterside_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_SURFACE_VEGETATION = create("water_surface_plant");
   public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_CAVES_VEGETATION = create("crystal_caves_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> RED_CRYSTAL_MOSS_VEGETATION = create("red_crystal_moss_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_CRYSTAL_MOSS_VEGETATION = create("blue_crystal_moss_vegetation");
   public static final ResourceKey<ConfiguredFeature<?, ?>> RED_CRYSTAL_MOSS_PATCH = create("red_crystal_moss_patch");
   public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_CRYSTAL_MOSS_PATCH = create("blue_crystal_moss_patch");
   public static final ResourceKey<ConfiguredFeature<?, ?>> RED_CRYSTAL_MOSS_PATCH_BONEMEAL = create("red_crystal_moss_patch_bonemeal");
   public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_CRYSTAL_MOSS_PATCH_BONEMEAL = create("blue_crystal_moss_patch_bonemeal");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_WATER = create("swamp_water");
   public static final ResourceKey<ConfiguredFeature<?, ?>> HOT_SPRING = create("hot_spring");

   public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
      HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
      HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
      RuleTest baseStone = new TagMatchTest(ESTags.Blocks.BASE_STONE_STARLIGHT);
      RuleTest grimstone = new BlockMatchTest(ESBlocks.GRIMSTONE.get());
      RuleTest voidstone = new BlockMatchTest(ESBlocks.VOIDSTONE.get());
      RuleTest eternalIce = new BlockMatchTest(ESBlocks.ETERNAL_ICE.get());
      RuleTest hazeIce = new BlockMatchTest(ESBlocks.HAZE_ICE.get());
      RuleTest nightfallMud = new BlockMatchTest((Block)ESBlocks.NIGHTFALL_MUD.get());
      RuleTest packedNightfallMud = new BlockMatchTest(ESBlocks.PACKED_NIGHTFALL_MUD.get());
      RuleTest dustedGravel = new BlockMatchTest((Block)ESBlocks.DUSTED_GRAVEL.get());
      FeatureUtils.register(context, FINAL_MODIFICATION, ESFeatures.FINAL_MODIFICATION.get());
      FeatureUtils.register(
         context,
         STONE_SPIKE,
         ESFeatures.SPIKE.get(),
         new SpikeFeature.Configuration(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(ESBlocks.GRIMSTONE.get().defaultBlockState(), 8)
                  .add(ESBlocks.GLOWING_GRIMSTONE.get().defaultBlockState(), 1)
                  .build()
            ),
            UniformInt.of(10, 32),
            UniformInt.of(4, 7),
            UniformInt.of(1, 6),
            UniformInt.of(5, 17),
            UniformInt.of(3, 5)
         )
      );
      FeatureUtils.register(
         context,
         MONOLITH,
         ESFeatures.PILLAR.get(),
         new PillarFeature.Configuration(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(ESBlocks.GRIMSTONE.get().defaultBlockState(), 8)
                  .add(ESBlocks.GLOWING_GRIMSTONE.get().defaultBlockState(), 1)
                  .build()
            ),
            UniformInt.of(15, 25),
            UniformInt.of(3, 5),
            UniformInt.of(15, 30),
            HolderSet.empty(),
            0.0F
         )
      );
      FeatureUtils.register(
         context,
         LUSH_MONOLITH,
         ESFeatures.PILLAR.get(),
         new PillarFeature.Configuration(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(ESBlocks.GRIMSTONE.get().defaultBlockState(), 8)
                  .add(ESBlocks.GLOWING_GRIMSTONE.get().defaultBlockState(), 1)
                  .build()
            ),
            UniformInt.of(15, 25),
            UniformInt.of(3, 5),
            UniformInt.of(15, 30),
            HolderSet.direct(
               new Holder[]{
                  PlacementUtils.inlinePlaced(
                     configuredFeatures.getOrThrow(CAVE_VINE),
                     new PlacementModifier[]{
                        EnvironmentScanPlacement.scanningFor(
                           Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12
                        ),
                        RandomOffsetPlacement.vertical(ConstantInt.of(-1))
                     }
                  ),
                  PlacementUtils.inlinePlaced(
                     configuredFeatures.getOrThrow(CAVE_MOSS),
                     new PlacementModifier[]{
                        EnvironmentScanPlacement.scanningFor(
                           Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12
                        ),
                        RandomOffsetPlacement.vertical(ConstantInt.of(-1))
                     }
                  )
               }
            ),
            0.2F
         )
      );
      FeatureUtils.register(context, GLACITE, ESFeatures.GLACITE.get());
      FeatureUtils.register(context, ICICLE, ESFeatures.ICICLE.get());
      FeatureUtils.register(context, STONE_ORE, Feature.ORE, new OreConfiguration(baseStone, Blocks.STONE.defaultBlockState(), 64));
      FeatureUtils.register(context, DEEPSLATE_ORE, Feature.ORE, new OreConfiguration(baseStone, Blocks.DEEPSLATE.defaultBlockState(), 64));
      FeatureUtils.register(
         context, GLOWING_GRIMSTONE_ORE, Feature.ORE, new OreConfiguration(grimstone, ESBlocks.GLOWING_GRIMSTONE.get().defaultBlockState(), 20)
      );
      FeatureUtils.register(
         context, GLOWING_VOIDSTONE_ORE, Feature.ORE, new OreConfiguration(voidstone, ESBlocks.GLOWING_VOIDSTONE.get().defaultBlockState(), 20)
      );
      FeatureUtils.register(
         context,
         GLOWING_NIGHTFALL_MUD_ORE,
         Feature.ORE,
         new OreConfiguration(nightfallMud, ((NightfallMudBlock)ESBlocks.GLOWING_NIGHTFALL_MUD.get()).defaultBlockState(), 20)
      );
      FeatureUtils.register(context, NIGHTFALL_DIRT_ORE, Feature.ORE, new OreConfiguration(baseStone, ESBlocks.NIGHTFALL_DIRT.get().defaultBlockState(), 33));
      FeatureUtils.register(
         context, DIMSLAG_ORE, Feature.ORE, new OreConfiguration(baseStone, ((ColoredFallingBlock)ESBlocks.DIMSLAG.get()).defaultBlockState(), 25)
      );
      FeatureUtils.register(
         context, SAND_ORE, Feature.ORE, new OreConfiguration(dustedGravel, ((ColoredFallingBlock)ESBlocks.TWILIGHT_SAND.get()).defaultBlockState(), 64)
      );
      FeatureUtils.register(
         context,
         STARLIT_DIAMOND_ORE,
         Feature.ORE,
         new OreConfiguration(
            List.of(
               OreConfiguration.target(grimstone, ((DropExperienceBlock)ESBlocks.GRIMSTONE_STARLIT_DIAMOND_ORE.get()).defaultBlockState()),
               OreConfiguration.target(voidstone, ((DropExperienceBlock)ESBlocks.VOIDSTONE_STARLIT_DIAMOND_ORE.get()).defaultBlockState()),
               OreConfiguration.target(eternalIce, ((DropExperienceBlock)ESBlocks.ETERNAL_ICE_STARLIT_DIAMOND_ORE.get()).defaultBlockState()),
               OreConfiguration.target(hazeIce, ((DropExperienceBlock)ESBlocks.HAZE_ICE_STARLIT_DIAMOND_ORE.get()).defaultBlockState())
            ),
            6,
            0.5F
         )
      );
      FeatureUtils.register(
         context,
         DEEPSILVER_ORE,
         Feature.ORE,
         new OreConfiguration(
            List.of(
               OreConfiguration.target(grimstone, ESBlocks.GRIMSTONE_DEEPSILVER_ORE.get().defaultBlockState()),
               OreConfiguration.target(voidstone, ESBlocks.VOIDSTONE_DEEPSILVER_ORE.get().defaultBlockState()),
               OreConfiguration.target(eternalIce, ESBlocks.ETERNAL_ICE_DEEPSILVER_ORE.get().defaultBlockState()),
               OreConfiguration.target(hazeIce, ESBlocks.HAZE_ICE_DEEPSILVER_ORE.get().defaultBlockState()),
               OreConfiguration.target(nightfallMud, ESBlocks.NIGHTFALL_MUD_DEEPSILVER_ORE.get().defaultBlockState()),
               OreConfiguration.target(packedNightfallMud, ESBlocks.PACKED_NIGHTFALL_MUD_DEEPSILVER_ORE.get().defaultBlockState())
            ),
            10
         )
      );
      FeatureUtils.register(
         context,
         MALARITE_ORE,
         Feature.ORE,
         new OreConfiguration(
            List.of(
               OreConfiguration.target(grimstone, ((DropExperienceBlock)ESBlocks.GRIMSTONE_MALARITE_ORE.get()).defaultBlockState()),
               OreConfiguration.target(voidstone, ((DropExperienceBlock)ESBlocks.VOIDSTONE_MALARITE_ORE.get()).defaultBlockState()),
               OreConfiguration.target(nightfallMud, ((DropExperienceBlock)ESBlocks.NIGHTFALL_MUD_MALARITE_ORE.get()).defaultBlockState()),
               OreConfiguration.target(packedNightfallMud, ((DropExperienceBlock)ESBlocks.PACKED_NIGHTFALL_MUD_MALARITE_ORE.get()).defaultBlockState())
            ),
            10
         )
      );
      FeatureUtils.register(
         context,
         REDSTONE_ORE,
         Feature.ORE,
         new OreConfiguration(
            List.of(
               OreConfiguration.target(grimstone, ((RedStoneOreBlock)ESBlocks.GRIMSTONE_REDSTONE_ORE.get()).defaultBlockState()),
               OreConfiguration.target(voidstone, ((RedStoneOreBlock)ESBlocks.VOIDSTONE_REDSTONE_ORE.get()).defaultBlockState()),
               OreConfiguration.target(eternalIce, ESBlocks.ETERNAL_ICE_REDSTONE_ORE.get().defaultBlockState()),
               OreConfiguration.target(hazeIce, ESBlocks.HAZE_ICE_REDSTONE_ORE.get().defaultBlockState())
            ),
            7
         )
      );
      FeatureUtils.register(
         context,
         SALTPETER_ORE,
         Feature.ORE,
         new OreConfiguration(
            List.of(
               OreConfiguration.target(grimstone, ((DropExperienceBlock)ESBlocks.GRIMSTONE_SALTPETER_ORE.get()).defaultBlockState()),
               OreConfiguration.target(voidstone, ((DropExperienceBlock)ESBlocks.VOIDSTONE_SALTPETER_ORE.get()).defaultBlockState()),
               OreConfiguration.target(eternalIce, ((DropExperienceBlock)ESBlocks.ETERNAL_ICE_SALTPETER_ORE.get()).defaultBlockState()),
               OreConfiguration.target(hazeIce, ((DropExperienceBlock)ESBlocks.HAZE_ICE_SALTPETER_ORE.get()).defaultBlockState())
            ),
            20
         )
      );
      FeatureUtils.register(
         context,
         STARCORE_ORE,
         Feature.ORE,
         new OreConfiguration(
            List.of(
               OreConfiguration.target(grimstone, ((DropExperienceBlock)ESBlocks.GRIMSTONE_STARCORE_ORE.get()).defaultBlockState()),
               OreConfiguration.target(voidstone, ((DropExperienceBlock)ESBlocks.VOIDSTONE_STARCORE_ORE.get()).defaultBlockState()),
               OreConfiguration.target(eternalIce, ((DropExperienceBlock)ESBlocks.ETERNAL_ICE_STARCORE_ORE.get()).defaultBlockState()),
               OreConfiguration.target(hazeIce, ((DropExperienceBlock)ESBlocks.HAZE_ICE_STARCORE_ORE.get()).defaultBlockState())
            ),
            5
         )
      );
      FeatureUtils.register(
         context, FALLEN_LUNAR_LOG, ESFeatures.FALLEN_LOG.get(), new FallenLogFeature.Configuration(BlockStateProvider.simple((Block)ESBlocks.LUNAR_LOG.get()))
      );
      FeatureUtils.register(
         context,
         FALLEN_NORTHLAND_LOG,
         ESFeatures.FALLEN_LOG.get(),
         new FallenLogFeature.Configuration(BlockStateProvider.simple((Block)ESBlocks.NORTHLAND_LOG.get()))
      );
      FeatureUtils.register(
         context,
         FALLEN_BANYIN_LOG,
         ESFeatures.FALLEN_LOG.get(),
         new FallenLogFeature.Configuration(BlockStateProvider.simple((Block)ESBlocks.BANYIN_LOG.get()))
      );
      FeatureUtils.register(
         context,
         FALLEN_SCARLET_LOG,
         ESFeatures.FALLEN_LOG.get(),
         new FallenLogFeature.Configuration(BlockStateProvider.simple((Block)ESBlocks.SCARLET_LOG.get()))
      );
      FeatureUtils.register(
         context,
         SCARLET_LEAVES_PILE,
         ESFeatures.LEAVES_PILE.get(),
         new LeavesPileFeature.Configuration(BlockStateProvider.simple(ESBlocks.SCARLET_LEAVES_PILE.get()))
      );
      FeatureUtils.register(context, ASHEN_SNOW, ESFeatures.ASHEN_SNOW.get());
      FeatureUtils.register(context, STARLIGHT_CRYSTAL, ESFeatures.STARLIGHT_CRYSTAL.get());
      FeatureUtils.register(
         context,
         ICE_SPIKE,
         ESFeatures.SPIKE.get(),
         new SpikeFeature.Configuration(
            BlockStateProvider.simple((Block)ESBlocks.THIN_ETERNAL_ICE.get()),
            UniformInt.of(25, 35),
            UniformInt.of(4, 7),
            UniformInt.of(1, 4),
            UniformInt.of(10, 15),
            UniformInt.of(4, 7)
         )
      );
      FeatureUtils.register(context, STELLAGMITE, ESFeatures.STELLAGMITE.get());
      FeatureUtils.register(
         context,
         THIOQUARTZ_GEODE,
         Feature.GEODE,
         new GeodeConfiguration(
            new GeodeBlockSettings(
               BlockStateProvider.simple(Blocks.AIR),
               BlockStateProvider.simple(ESBlocks.THIOQUARTZ_BLOCK.get()),
               BlockStateProvider.simple(ESBlocks.BUDDING_THIOQUARTZ.get()),
               BlockStateProvider.simple(ESBlocks.TOXITE.get()),
               BlockStateProvider.simple(ESBlocks.TOXITE.get()),
               List.of(ESBlocks.THIOQUARTZ_CLUSTER.get().defaultBlockState()),
               BlockTags.FEATURES_CANNOT_REPLACE,
               BlockTags.GEODE_INVALID_BLOCKS
            ),
            new GeodeLayerSettings(1.7, 2.2, 3.2, 4.2),
            new GeodeCrackSettings(0.95, 2.0, 2),
            0.35,
            0.083,
            true,
            UniformInt.of(4, 6),
            UniformInt.of(3, 4),
            UniformInt.of(1, 2),
            -16,
            16,
            0.05,
            1
         )
      );
      FeatureUtils.register(
         context,
         CAVE_VINE,
         Feature.BLOCK_COLUMN,
         new BlockColumnConfiguration(
            List.of(
               BlockColumnConfiguration.layer(
                  new WeightedListInt(
                     SimpleWeightedRandomList.builder().add(UniformInt.of(0, 19), 2).add(UniformInt.of(0, 2), 3).add(UniformInt.of(0, 6), 10).build()
                  ),
                  new WeightedStateProvider(
                     SimpleWeightedRandomList.builder()
                        .add(((BerriesVinesPlantBlock)ESBlocks.BERRIES_VINES_PLANT.get()).defaultBlockState(), 4)
                        .add((BlockState)((BerriesVinesPlantBlock)ESBlocks.BERRIES_VINES_PLANT.get()).defaultBlockState().setValue(CaveVines.BERRIES, true), 1)
                  )
               ),
               BlockColumnConfiguration.layer(
                  ConstantInt.of(1),
                  new RandomizedIntStateProvider(
                     new WeightedStateProvider(
                        SimpleWeightedRandomList.builder()
                           .add(((BerriesVinesBlock)ESBlocks.BERRIES_VINES.get()).defaultBlockState(), 4)
                           .add((BlockState)((BerriesVinesBlock)ESBlocks.BERRIES_VINES.get()).defaultBlockState().setValue(CaveVines.BERRIES, true), 1)
                     ),
                     CaveVinesBlock.AGE,
                     UniformInt.of(23, 25)
                  )
               )
            ),
            Direction.DOWN,
            BlockPredicate.ONLY_IN_AIR_PREDICATE,
            true
         )
      );
      FeatureUtils.register(
         context,
         CAVE_MOSS,
         Feature.BLOCK_COLUMN,
         new BlockColumnConfiguration(
            List.of(
               BlockColumnConfiguration.layer(
                  new WeightedListInt(
                     SimpleWeightedRandomList.builder().add(UniformInt.of(10, 14), 1).add(UniformInt.of(6, 10), 2).add(UniformInt.of(0, 6), 6).build()
                  ),
                  BlockStateProvider.simple((Block)ESBlocks.CAVE_MOSS_PLANT.get())
               ),
               BlockColumnConfiguration.layer(
                  ConstantInt.of(1),
                  new RandomizedIntStateProvider(BlockStateProvider.simple((Block)ESBlocks.CAVE_MOSS.get()), CaveMossBlock.AGE, UniformInt.of(23, 25))
               )
            ),
            Direction.DOWN,
            BlockPredicate.ONLY_IN_AIR_PREDICATE,
            true
         )
      );
      FeatureUtils.register(
         context,
         CAVE_MOSS_VEIN,
         Feature.MULTIFACE_GROWTH,
         new MultifaceGrowthConfiguration(
            (MultifaceBlock)ESBlocks.CAVE_MOSS_VEIN.get(),
            20,
            false,
            true,
            true,
            0.5F,
            HolderSet.direct(new Holder[]{ESBlocks.GRIMSTONE.asHolder(), ESBlocks.VOIDSTONE.asHolder()})
         )
      );
      FeatureUtils.register(context, BOULDERSHROOM, ESFeatures.BOULDERSHROOM.get());
      FeatureUtils.register(
         context,
         HANGING_FANTAGRASS,
         Feature.BLOCK_COLUMN,
         new BlockColumnConfiguration(
            List.of(
               BlockColumnConfiguration.layer(
                  new WeightedListInt(
                     SimpleWeightedRandomList.builder().add(UniformInt.of(10, 14), 1).add(UniformInt.of(6, 10), 2).add(UniformInt.of(0, 6), 6).build()
                  ),
                  BlockStateProvider.simple((Block)ESBlocks.HANGING_FANTAGRASS_PLANT.get())
               ),
               BlockColumnConfiguration.layer(
                  ConstantInt.of(1),
                  new RandomizedIntStateProvider(BlockStateProvider.simple((Block)ESBlocks.HANGING_FANTAGRASS.get()), CaveMossBlock.AGE, UniformInt.of(23, 25))
               )
            ),
            Direction.DOWN,
            BlockPredicate.ONLY_IN_AIR_PREDICATE,
            true
         )
      );
      FeatureUtils.register(
         context,
         ABYSSAL_KELP,
         ESFeatures.WATER_PLANT.get(),
         new WaterPlantFeature.Configuration(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add((BlockState)((AbyssalKelpPlantBlock)ESBlocks.ABYSSAL_KELP_PLANT.get()).defaultBlockState().setValue(AbyssalKelp.BERRIES, true), 1)
                  .add(((AbyssalKelpPlantBlock)ESBlocks.ABYSSAL_KELP_PLANT.get()).defaultBlockState(), 4)
                  .build()
            ),
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add((BlockState)((AbyssalKelpBlock)ESBlocks.ABYSSAL_KELP.get()).defaultBlockState().setValue(AbyssalKelp.BERRIES, true), 1)
                  .add(((AbyssalKelpBlock)ESBlocks.ABYSSAL_KELP.get()).defaultBlockState(), 4)
                  .build()
            )
         )
      );
      FeatureUtils.register(context, ORBFLORA, ESFeatures.ORBFLORA.get());
      FeatureUtils.register(
         context,
         SPIRAL_KELP,
         ESFeatures.WATER_PLANT.get(),
         new WaterPlantFeature.Configuration(
            BlockStateProvider.simple((Block)ESBlocks.SPIRAL_KELP_PLANT.get()), BlockStateProvider.simple((Block)ESBlocks.SPIRAL_KELP.get())
         )
      );
      FeatureUtils.register(
         context,
         SEA_ROSA,
         Feature.MULTIFACE_GROWTH,
         new MultifaceGrowthConfiguration(
            (MultifaceBlock)ESBlocks.SEA_ROSA.get(),
            32,
            true,
            true,
            true,
            0.9F,
            HolderSet.direct(new Holder[]{ESBlocks.DUSTED_GRAVEL.asHolder(), ESBlocks.TWILIGHT_SAND.asHolder()})
         )
      );
      FeatureUtils.register(
         context,
         LUMENSTEM,
         ESFeatures.WATER_PLANT.get(),
         new WaterPlantFeature.Configuration(
            BlockStateProvider.simple((Block)ESBlocks.LUMENSTEM_PLANT.get()), BlockStateProvider.simple((Block)ESBlocks.LUMENSTEM.get())
         )
      );
      FeatureUtils.register(
         context,
         OCEAN_VEGETATION,
         Feature.SIMPLE_RANDOM_SELECTOR,
         new SimpleRandomFeatureConfiguration(
            HolderSet.direct(
               new Holder[]{
                  PlacementUtils.inlinePlaced(ESFeatures.CORAL_TREE.get(), FeatureConfiguration.NONE, new PlacementModifier[0]),
                  PlacementUtils.inlinePlaced(ESFeatures.CORAL_CLAW.get(), FeatureConfiguration.NONE, new PlacementModifier[0]),
                  PlacementUtils.inlinePlaced(ESFeatures.CORAL_MUSHROOM.get(), FeatureConfiguration.NONE, new PlacementModifier[0])
               }
            )
         )
      );
      FeatureUtils.register(
         context,
         SPIRAL_KELP_FOREST_VEGETATION,
         ESFeatures.UNDERWATER_SIMPLE_BLOCK.get(),
         new SimpleBlockConfiguration(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((WickGrassBlock)ESBlocks.WICK_GRASS.get()).defaultBlockState(), 9)
                  .add(((JinglingPickleBlock)ESBlocks.JINGLING_PICKLE.get()).defaultBlockState(), 2)
            )
         )
      );
      FeatureUtils.register(
         context,
         LUSH_SHALLOW_SEA_VEGETATION,
         ESFeatures.UNDERWATER_SIMPLE_BLOCK.get(),
         new SimpleBlockConfiguration(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((MarimoldBlock)ESBlocks.MARIMOLD.get()).defaultBlockState(), 1)
                  .add(((SeaBushBlock)ESBlocks.CIRCULUSH.get()).defaultBlockState(), 2)
                  .add(((SeaBushBlock)ESBlocks.STONETT.get()).defaultBlockState(), 2)
                  .add(((SeaBushBlock)ESBlocks.LUMINIS.get()).defaultBlockState(), 2)
                  .add(((SeaBushBlock)ESBlocks.GLOWLIS.get()).defaultBlockState(), 2)
                  .add(((SeaBushBlock)ESBlocks.GLOREED.get()).defaultBlockState(), 3)
                  .add(((SeaBushBlock)ESBlocks.STARLIGHT_SEAGRASS.get()).defaultBlockState(), 5)
            )
         )
      );
      FeatureUtils.register(
         context,
         ABYSSLATE_PATCH,
         ESFeatures.ABYSSLATE_PATCH.get(),
         new AbysslatePatchFeature.Configuration(
            ESBlocks.ABYSSLATE.get(),
            BlockStateProvider.simple((Block)ESBlocks.ABYSSAL_MAGMA_BLOCK.get()),
            BlockStateProvider.simple((Block)ESBlocks.ABYSSAL_GEYSER.get())
         )
      );
      FeatureUtils.register(
         context,
         THERMABYSSLATE_PATCH,
         ESFeatures.ABYSSLATE_PATCH.get(),
         new AbysslatePatchFeature.Configuration(
            ESBlocks.THERMABYSSLATE.get(),
            BlockStateProvider.simple((Block)ESBlocks.THERMABYSSAL_MAGMA_BLOCK.get()),
            BlockStateProvider.simple((Block)ESBlocks.THERMABYSSAL_GEYSER.get())
         )
      );
      FeatureUtils.register(
         context,
         CRYOBYSSLATE_PATCH,
         ESFeatures.ABYSSLATE_PATCH.get(),
         new AbysslatePatchFeature.Configuration(
            ESBlocks.CRYOBYSSLATE.get(),
            BlockStateProvider.simple((Block)ESBlocks.CRYOBYSSAL_MAGMA_BLOCK.get()),
            BlockStateProvider.simple((Block)ESBlocks.CRYOBYSSAL_GEYSER.get())
         )
      );
      FeatureUtils.register(context, ABYSSAL_CAVE, ESFeatures.ABYSSAL_CAVE.get());
      FeatureUtils.register(
         context,
         VELVETUMOSS,
         ESFeatures.VELVETUMOSS.get(),
         new VelvetumossFeature.Configuration(ESBlocks.VELVETUMOSS.get(), ESBlocks.VELVETUMOSS_VILLI.get(), Optional.empty())
      );
      FeatureUtils.register(
         context,
         RED_VELVETUMOSS,
         ESFeatures.VELVETUMOSS.get(),
         new VelvetumossFeature.Configuration(
            ESBlocks.RED_VELVETUMOSS.get(), ESBlocks.RED_VELVETUMOSS_VILLI.get(), Optional.of((Block)ESBlocks.RED_VELVETUMOSS_FLOWER.get())
         )
      );
      FeatureUtils.register(
         context,
         LUNAR,
         Feature.TREE,
         new TreeConfigurationBuilder(
               BlockStateProvider.simple((Block)ESBlocks.LUNAR_LOG.get()),
               new BranchingTrunkPlacer(8, 2, 10, ConstantInt.of(0), UniformInt.of(4, 5), ConstantInt.of(1), UniformInt.of(3, 4), false),
               BlockStateProvider.simple((Block)ESBlocks.LUNAR_LEAVES.get()),
               new SpheroidFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
               new TwoLayersFeatureSize(4, 0, 2)
            )
            .dirt(BlockStateProvider.simple(ESBlocks.NIGHTFALL_DIRT.get()))
            .decorators(List.of(new TrunkBerriesDecorator(UniformInt.of(2, 4))))
            .build()
      );
      FeatureUtils.register(
         context,
         LUNAR_HUGE,
         Feature.TREE,
         new TreeConfigurationBuilder(
               BlockStateProvider.simple((Block)ESBlocks.LUNAR_LOG.get()),
               new BranchingTrunkPlacer(32, 2, 10, UniformInt.of(3, 4), UniformInt.of(2, 4)),
               BlockStateProvider.simple((Block)ESBlocks.LUNAR_LEAVES.get()),
               new SpheroidFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
               new TwoLayersFeatureSize(4, 0, 2)
            )
            .dirt(BlockStateProvider.simple(ESBlocks.NIGHTFALL_DIRT.get()))
            .decorators(List.of(new TrunkBerriesDecorator(UniformInt.of(3, 6))))
            .build()
      );
      FeatureUtils.register(
         context,
         LUNAR_HUGE_STARFIRE_BIRDS,
         Feature.TREE,
         new TreeConfigurationBuilder(
               BlockStateProvider.simple((Block)ESBlocks.LUNAR_LOG.get()),
               new BranchingTrunkPlacer(32, 2, 10, UniformInt.of(3, 4), UniformInt.of(2, 4)),
               BlockStateProvider.simple((Block)ESBlocks.LUNAR_LEAVES.get()),
               new SpheroidFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
               new TwoLayersFeatureSize(4, 0, 2)
            )
            .dirt(BlockStateProvider.simple(ESBlocks.NIGHTFALL_DIRT.get()))
            .decorators(List.of(new TrunkBerriesDecorator(UniformInt.of(3, 6)), new StarfireBirdNestDecorator()))
            .build()
      );
      FeatureUtils.register(
         context,
         LUNAR_SMALL,
         Feature.TREE,
         new TreeConfigurationBuilder(
               BlockStateProvider.simple((Block)ESBlocks.LUNAR_LOG.get()),
               new StraightTrunkPlacer(2, 1, 1),
               BlockStateProvider.simple((BlockState)((LeavesBlock)ESBlocks.LUNAR_LEAVES.get()).defaultBlockState().setValue(LeavesBlock.PERSISTENT, true)),
               new SpheroidFoliagePlacer(UniformInt.of(4, 9), ConstantInt.of(0)),
               new TwoLayersFeatureSize(1, 0, 1)
            )
            .dirt(BlockStateProvider.simple(ESBlocks.NIGHTFALL_DIRT.get()))
            .build()
      );
      FeatureUtils.register(
         context,
         NORTHLAND,
         Feature.TREE,
         new TreeConfigurationBuilder(
               BlockStateProvider.simple((Block)ESBlocks.NORTHLAND_LOG.get()),
               new GiantTrunkPlacer(10, 2, 10),
               BlockStateProvider.simple((Block)ESBlocks.NORTHLAND_LEAVES.get()),
               new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)),
               new TwoLayersFeatureSize(1, 1, 2)
            )
            .dirt(BlockStateProvider.simple(ESBlocks.NIGHTFALL_DIRT.get()))
            .decorators(List.of(new AlterGroundDecorator(BlockStateProvider.simple((Block)ESBlocks.NIGHTFALL_PODZOL.get()))))
            .build()
      );
      FeatureUtils.register(
         context,
         NORTHLAND_THIN,
         Feature.TREE,
         new TreeConfigurationBuilder(
               BlockStateProvider.simple((Block)ESBlocks.NORTHLAND_LOG.get()),
               new StraightTrunkPlacer(6, 2, 5),
               BlockStateProvider.simple((Block)ESBlocks.NORTHLAND_LEAVES.get()),
               new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(1), UniformInt.of(5, 8)),
               new TwoLayersFeatureSize(1, 1, 2)
            )
            .dirt(BlockStateProvider.simple(ESBlocks.NIGHTFALL_DIRT.get()))
            .decorators(List.of(new AlterGroundDecorator(BlockStateProvider.simple((Block)ESBlocks.NIGHTFALL_PODZOL.get()))))
            .build()
      );
      FeatureUtils.register(
         context,
         NORTHLAND_TALL,
         Feature.TREE,
         new TreeConfigurationBuilder(
               BlockStateProvider.simple((Block)ESBlocks.NORTHLAND_LOG.get()),
               new StraightTrunkPlacer(18, 2, 5),
               BlockStateProvider.simple((Block)ESBlocks.NORTHLAND_LEAVES.get()),
               new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(1), UniformInt.of(9, 12)),
               new TwoLayersFeatureSize(1, 1, 2)
            )
            .dirt(BlockStateProvider.simple(ESBlocks.NIGHTFALL_DIRT.get()))
            .decorators(List.of(new AlterGroundDecorator(BlockStateProvider.simple((Block)ESBlocks.NIGHTFALL_PODZOL.get()))))
            .build()
      );
      FeatureUtils.register(
         context,
         BANYIN,
         Feature.TREE,
         new TreeConfigurationBuilder(
               BlockStateProvider.simple((Block)ESBlocks.BANYIN_LOG.get()),
               new BranchingTrunkPlacer(6, 2, 4, ConstantInt.of(0), UniformInt.of(3, 4), ConstantInt.of(1), UniformInt.of(3, 4), false),
               BlockStateProvider.simple((Block)ESBlocks.BANYIN_LEAVES.get()),
               new SpheroidFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
               new TwoLayersFeatureSize(4, 0, 2)
            )
            .dirt(BlockStateProvider.simple(ESBlocks.NIGHTFALL_DIRT.get()))
            .decorators(
               List.of(
                  new BanyinRootsDecorator(),
                  new HangingPlantDecorator(
                     ESBlocks.HANGING_FANTAGRASS.asHolder(), ESBlocks.HANGING_FANTAGRASS_PLANT.asHolder(), true, 0.3F, UniformInt.of(1, 4)
                  )
               )
            )
            .ignoreVines()
            .build()
      );
      FeatureUtils.register(
         context,
         BANYIN_HUGE,
         Feature.TREE,
         new TreeConfigurationBuilder(
               BlockStateProvider.simple((Block)ESBlocks.BANYIN_LOG.get()),
               new BranchingTrunkPlacer(7, 2, 4, ConstantInt.of(2), UniformInt.of(4, 5), ConstantInt.of(1), UniformInt.of(5, 6), true),
               BlockStateProvider.simple((Block)ESBlocks.BANYIN_LEAVES.get()),
               new SpheroidFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
               new TwoLayersFeatureSize(4, 0, 2)
            )
            .dirt(BlockStateProvider.simple(ESBlocks.NIGHTFALL_DIRT.get()))
            .decorators(
               List.of(
                  new BanyinRootsDecorator(),
                  new HangingPlantDecorator(
                     ESBlocks.HANGING_FANTAGRASS.asHolder(), ESBlocks.HANGING_FANTAGRASS_PLANT.asHolder(), true, 0.3F, UniformInt.of(2, 5)
                  )
               )
            )
            .ignoreVines()
            .build()
      );
      FeatureUtils.register(
         context,
         SCARLET,
         Feature.TREE,
         new TreeConfigurationBuilder(
               BlockStateProvider.simple((Block)ESBlocks.SCARLET_LOG.get()),
               new StraightTrunkPlacer(10, 2, 10),
               BlockStateProvider.simple((Block)ESBlocks.SCARLET_LEAVES.get()),
               new ScarletFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
               new TwoLayersFeatureSize(4, 0, 2)
            )
            .dirt(BlockStateProvider.simple(ESBlocks.NIGHTFALL_DIRT.get()))
            .decorators(List.of(new TrunkCobwebDecorator()))
            .build()
      );
      FeatureUtils.register(
         context,
         TORREYA,
         Feature.TREE,
         new TreeConfigurationBuilder(
               BlockStateProvider.simple((Block)ESBlocks.TORREYA_LOG.get()),
               new BranchingTrunkPlacer(32, 2, 10, UniformInt.of(3, 4), UniformInt.of(2, 4)),
               BlockStateProvider.simple((Block)ESBlocks.TORREYA_LEAVES.get()),
               new TorreyaFoliagePlacer(ConstantInt.of(5), ConstantInt.of(0)),
               new TwoLayersFeatureSize(4, 0, 2)
            )
            .dirt(BlockStateProvider.simple(ESBlocks.NIGHTFALL_DIRT.get()))
            .decorators(List.of(new TorreyaVinesDecorator()))
            .build()
      );
      FeatureUtils.register(
         context,
         TORREYA_STARFIRE_BIRDS,
         Feature.TREE,
         new TreeConfigurationBuilder(
               BlockStateProvider.simple((Block)ESBlocks.TORREYA_LOG.get()),
               new BranchingTrunkPlacer(32, 2, 10, UniformInt.of(3, 4), UniformInt.of(2, 4)),
               BlockStateProvider.simple((Block)ESBlocks.TORREYA_LEAVES.get()),
               new TorreyaFoliagePlacer(ConstantInt.of(5), ConstantInt.of(0)),
               new TwoLayersFeatureSize(4, 0, 2)
            )
            .dirt(BlockStateProvider.simple(ESBlocks.NIGHTFALL_DIRT.get()))
            .decorators(List.of(new TorreyaVinesDecorator(), new StarfireBirdNestDecorator()))
            .build()
      );
      FeatureUtils.register(
         context,
         JINGLESTEM,
         ESFeatures.JINGLESTEM.get(),
         new JinglestemFeature.Configuration(UniformInt.of(8, 11), UniformInt.of(3, 5), UniformInt.of(5, 7), UniformInt.of(4, 8), true)
      );
      FeatureUtils.register(
         context,
         JINGLESTEM_PLANTED,
         ESFeatures.JINGLESTEM.get(),
         new JinglestemFeature.Configuration(UniformInt.of(8, 11), UniformInt.of(3, 5), UniformInt.of(5, 7), UniformInt.of(4, 8), false)
      );
      FeatureUtils.register(
         context, CRADLEWOOD, ESFeatures.CRADLEWOOD.get(), new CradlewoodFeature.Configuration(UniformInt.of(9, 12), UniformInt.of(12, 16), ConstantInt.of(4))
      );
      FeatureUtils.register(
         context,
         HUGE_MARIMOLD,
         ESFeatures.HUGE_MARIMOLD.get(),
         new HugeMarimoldFeature.Configuration(UniformInt.of(9, 12), UniformInt.of(4, 5), UniformInt.of(6, 8))
      );
      FeatureUtils.register(
         context,
         HUGE_GLOWING_MUSHROOM,
         ESFeatures.HUGE_GLOWING_MUSHROOM.get(),
         new HugeMushroomFeatureConfiguration(
            BlockStateProvider.simple(((HugeMushroomBlock)ESBlocks.GLOWING_MUSHROOM_BLOCK.get()).defaultBlockState()),
            BlockStateProvider.simple(
               (BlockState)((BlockState)((HugeMushroomBlock)ESBlocks.GLOWING_MUSHROOM_STEM.get()).defaultBlockState().setValue(HugeMushroomBlock.UP, false))
                  .setValue(HugeMushroomBlock.DOWN, false)
            ),
            5
         )
      );
      FeatureUtils.register(
         context,
         HUGE_SHINING_MUSHROOM,
         ESFeatures.HUGE_SHINING_MUSHROOM.get(),
         new HugeMushroomFeatureConfiguration(
            BlockStateProvider.simple(((HugeMushroomBlock)ESBlocks.SHINING_MUSHROOM_BLOCK.get()).defaultBlockState()),
            BlockStateProvider.simple(
               (BlockState)((BlockState)((HugeMushroomBlock)ESBlocks.SHINING_MUSHROOM_STEM.get()).defaultBlockState().setValue(HugeMushroomBlock.UP, false))
                  .setValue(HugeMushroomBlock.DOWN, false)
            ),
            3
         )
      );
      FeatureUtils.register(
         context,
         STARLIGHT_FOREST,
         Feature.RANDOM_SELECTOR,
         new RandomFeatureConfiguration(
            List.of(
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.LUNAR_TREE_CHECKED), 0.05F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.LUNAR_HUGE_TREE_CHECKED), 0.4F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.LUNAR_HUGE_STARFIRE_BIRDS_TREE_CHECKED), 0.4F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.HUGE_GLOWING_MUSHROOM_CHECKED), 0.015F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.HUGE_SHINING_MUSHROOM_CHECKED), 0.015F)
            ),
            placedFeatures.getOrThrow(ESPlacedFeatures.LUNAR_TREE_CHECKED)
         )
      );
      FeatureUtils.register(
         context,
         DENSE_FOREST,
         Feature.RANDOM_SELECTOR,
         new RandomFeatureConfiguration(
            List.of(
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.LUNAR_TREE_CHECKED), 0.9F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.HUGE_GLOWING_MUSHROOM_CHECKED), 0.1F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.HUGE_SHINING_MUSHROOM_CHECKED), 0.01F)
            ),
            placedFeatures.getOrThrow(ESPlacedFeatures.LUNAR_TREE_CHECKED)
         )
      );
      FeatureUtils.register(
         context,
         SPARSE_FOREST,
         Feature.RANDOM_SELECTOR,
         new RandomFeatureConfiguration(
            List.of(
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.LUNAR_TREE_CHECKED), 0.9F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.SCARLET_TREE_CHECKED), 0.07F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.HUGE_GLOWING_MUSHROOM_CHECKED), 0.03F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.HUGE_SHINING_MUSHROOM_CHECKED), 0.03F)
            ),
            placedFeatures.getOrThrow(ESPlacedFeatures.LUNAR_TREE_CHECKED)
         )
      );
      FeatureUtils.register(
         context,
         SCRUBLAND_FOREST,
         ESFeatures.NOISE_BOOLEAN_SELECTOR.get(),
         new NoiseBooleanSelectorFeature.Configuration(
            0.01,
            -0.4,
            placedFeatures.getOrThrow(ESPlacedFeatures.LUNAR_SMALL_TREE_CHECKED),
            placedFeatures.getOrThrow(ESPlacedFeatures.LUNAR_HUGE_TREE_CHECKED)
         )
      );
      FeatureUtils.register(
         context,
         SWAMP_FOREST,
         Feature.RANDOM_SELECTOR,
         new RandomFeatureConfiguration(
            List.of(
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.BANYIN_TREE_CHECKED), 0.6F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.BANYIN_HUGE_TREE_CHECKED), 0.3F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.HUGE_GLOWING_MUSHROOM_CHECKED), 0.1F)
            ),
            placedFeatures.getOrThrow(ESPlacedFeatures.BANYIN_TREE_CHECKED)
         )
      );
      FeatureUtils.register(
         context,
         PERMAFROST_FOREST,
         Feature.RANDOM_SELECTOR,
         new RandomFeatureConfiguration(
            List.of(
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.NORTHLAND_TREE_CHECKED), 0.25F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.NORTHLAND_ON_SNOW), 0.25F)
            ),
            placedFeatures.getOrThrow(ESPlacedFeatures.NORTHLAND_TREE_CHECKED)
         )
      );
      FeatureUtils.register(
         context,
         TAIGA_FOREST,
         Feature.RANDOM_SELECTOR,
         new RandomFeatureConfiguration(
            List.of(
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.NORTHLAND_THIN_TREE_CHECKED), 0.25F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.NORTHLAND_TALL_TREE_CHECKED), 0.25F)
            ),
            placedFeatures.getOrThrow(ESPlacedFeatures.NORTHLAND_TREE_CHECKED)
         )
      );
      FeatureUtils.register(
         context,
         SCARLET_FOREST,
         Feature.RANDOM_SELECTOR,
         new RandomFeatureConfiguration(
            List.of(
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.SCARLET_TREE_CHECKED), 0.75F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.HUGE_GLOWING_MUSHROOM_CHECKED), 0.1F)
            ),
            placedFeatures.getOrThrow(ESPlacedFeatures.SCARLET_TREE_CHECKED)
         )
      );
      FeatureUtils.register(
         context,
         TORREYA_FOREST,
         Feature.RANDOM_SELECTOR,
         new RandomFeatureConfiguration(
            List.of(
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.TORREYA_TREE_CHECKED), 0.4F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.TORREYA_STARFIRE_BIRDS_TREE_CHECKED), 0.4F)
            ),
            placedFeatures.getOrThrow(ESPlacedFeatures.TORREYA_TREE_CHECKED)
         )
      );
      FeatureUtils.register(
         context,
         MUSHROOM_FOREST,
         Feature.RANDOM_SELECTOR,
         new RandomFeatureConfiguration(
            List.of(
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.HUGE_GLOWING_MUSHROOM_CHECKED), 0.6F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.HUGE_SHINING_MUSHROOM_CHECKED), 0.4F)
            ),
            placedFeatures.getOrThrow(ESPlacedFeatures.HUGE_GLOWING_MUSHROOM_CHECKED)
         )
      );
      FeatureUtils.register(
         context,
         JINGLESTEM_FOREST,
         Feature.RANDOM_SELECTOR,
         new RandomFeatureConfiguration(
            List.of(
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.HUGE_MARIMOLD_CHECKED), 0.2F),
               new WeightedPlacedFeature(placedFeatures.getOrThrow(ESPlacedFeatures.JINGLESTEM_CHECKED), 0.5F)
            ),
            placedFeatures.getOrThrow(ESPlacedFeatures.JINGLESTEM_CHECKED)
         )
      );
      FeatureUtils.register(context, DEAD_LUNAR_TREE, ESFeatures.DEAD_LUNAR_TREE.get());
      FeatureUtils.register(
         context,
         LUNARIS_CACTUS,
         Feature.RANDOM_PATCH,
         FeatureUtils.simpleRandomPatchConfiguration(
            10,
            PlacementUtils.inlinePlaced(
               Feature.BLOCK_COLUMN,
               new BlockColumnConfiguration(
                  List.of(
                     BlockColumnConfiguration.layer(UniformInt.of(1, 3), BlockStateProvider.simple(ESBlocks.LUNARIS_CACTUS.get())),
                     BlockColumnConfiguration.layer(
                        UniformInt.of(0, 1),
                        BlockStateProvider.simple((BlockState)ESBlocks.LUNARIS_CACTUS.get().defaultBlockState().setValue(LunarisCactusBlock.FRUIT, true))
                     )
                  ),
                  Direction.UP,
                  BlockPredicate.ONLY_IN_AIR_PREDICATE,
                  false
               ),
               new PlacementModifier[]{
                  BlockPredicateFilter.forPredicate(
                     BlockPredicate.allOf(
                        BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(ESBlocks.LUNARIS_CACTUS.get().defaultBlockState(), BlockPos.ZERO)
                     )
                  )
               }
            )
         )
      );
      FeatureUtils.register(
         context,
         FOREST_VEGETATION,
         Feature.RANDOM_PATCH,
         grassPatch(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((ShortBushBlock)ESBlocks.NIGHT_SPROUTS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.SMALL_NIGHT_SPROUTS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_NIGHT_SPROUTS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.SMALL_GLOWING_NIGHT_SPROUTS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.LUNAR_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_LUNAR_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.CRESCENT_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_CRESCENT_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.PARASOL_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_PARASOL_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.LUNAR_BUSH.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_LUNAR_BUSH.get()).defaultBlockState(), 2)
                  .add(((DoublePlantBlock)ESBlocks.TALL_CRESCENT_GRASS.get()).defaultBlockState(), 1)
                  .add(((DoublePlantBlock)ESBlocks.TALL_GLOWING_CRESCENT_GRASS.get()).defaultBlockState(), 1)
                  .add(((ShortBushBlock)ESBlocks.GLADESPIKE.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.VIVIDSTALK.get()).defaultBlockState(), 1)
                  .add(((DoublePlantBlock)ESBlocks.TALL_GLADESPIKE.get()).defaultBlockState(), 1)
                  .add(((ShortBushBlock)ESBlocks.MOONLIGHT_BUSH.get()).defaultBlockState(), 1)
                  .add((BlockState)((ShortBushBlock)ESBlocks.MOONLIGHT_BUSH.get()).defaultBlockState().setValue(MoonlightBushBlock.BERRIES, true), 1)
                  .add(((DoublePlantBlock)ESBlocks.GLINTGRASS.get()).defaultBlockState(), 1)
                  .add(((FlowerBlock)ESBlocks.PINK_ROSE.get()).defaultBlockState(), 2)
                  .add(((DoublePlantBlock)ESBlocks.PINK_ROSE_BUSH.get()).defaultBlockState(), 1)
                  .add(((FlowerBlock)ESBlocks.STARLIGHT_TORCHFLOWER.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.WHISPERBLOOM.get()).defaultBlockState(), 2)
                  .add(((FlowerBlock)ESBlocks.STARLIGHT_FLOWER.get()).defaultBlockState(), 2)
            ),
            48
         )
      );
      FeatureUtils.register(
         context,
         PLAINS_VEGETATION,
         Feature.RANDOM_PATCH,
         grassPatch(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((ShortBushBlock)ESBlocks.NIGHT_SPROUTS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.SMALL_NIGHT_SPROUTS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_NIGHT_SPROUTS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.SMALL_GLOWING_NIGHT_SPROUTS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.LUNAR_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_LUNAR_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.CRESCENT_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_CRESCENT_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.PARASOL_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_PARASOL_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.LUNAR_BUSH.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_LUNAR_BUSH.get()).defaultBlockState(), 2)
                  .add(((DoublePlantBlock)ESBlocks.TALL_CRESCENT_GRASS.get()).defaultBlockState(), 1)
                  .add(((DoublePlantBlock)ESBlocks.TALL_GLOWING_CRESCENT_GRASS.get()).defaultBlockState(), 1)
                  .add(((ShortBushBlock)ESBlocks.GLADESPIKE.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.VIVIDSTALK.get()).defaultBlockState(), 1)
                  .add(((DoublePlantBlock)ESBlocks.TALL_GLADESPIKE.get()).defaultBlockState(), 1)
                  .add(((ShortBushBlock)ESBlocks.MOONLIGHT_BUSH.get()).defaultBlockState(), 1)
                  .add((BlockState)((ShortBushBlock)ESBlocks.MOONLIGHT_BUSH.get()).defaultBlockState().setValue(MoonlightBushBlock.BERRIES, true), 1)
                  .add(((DoublePlantBlock)ESBlocks.GLINTGRASS.get()).defaultBlockState(), 5)
                  .add(((FlowerBlock)ESBlocks.PINK_ROSE.get()).defaultBlockState(), 2)
                  .add(((DoublePlantBlock)ESBlocks.PINK_ROSE_BUSH.get()).defaultBlockState(), 1)
                  .add(((FlowerBlock)ESBlocks.STARLIGHT_TORCHFLOWER.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.WHISPERBLOOM.get()).defaultBlockState(), 2)
                  .add(((FlowerBlock)ESBlocks.STARLIGHT_FLOWER.get()).defaultBlockState(), 2)
            ),
            48
         )
      );
      FeatureUtils.register(
         context,
         SWAMP_VEGETATION,
         Feature.RANDOM_PATCH,
         grassPatch(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((ShortBushBlock)ESBlocks.FANTABUD.get()).defaultBlockState(), 3)
                  .add(((ShortBushBlock)ESBlocks.GREEN_FANTABUD.get()).defaultBlockState(), 3)
                  .add(((ShortBushBlock)ESBlocks.FANTAFERN.get()).defaultBlockState(), 3)
                  .add(((ShortBushBlock)ESBlocks.GREEN_FANTAFERN.get()).defaultBlockState(), 3)
                  .add(((ShortBushBlock)ESBlocks.FANTAGRASS.get()).defaultBlockState(), 3)
                  .add(((ShortBushBlock)ESBlocks.GREEN_FANTAGRASS.get()).defaultBlockState(), 3)
                  .add(((ShortBushBlock)ESBlocks.VIVIDSTALK.get()).defaultBlockState(), 3)
                  .add(
                     (BlockState)((PungencyFruitVinesBlock)ESBlocks.PUNGENCY_FRUIT_VINES.get()).defaultBlockState().setValue(PungencyFruitVinesBlock.AGE, 7), 3
                  )
                  .add(((FlowerBlock)ESBlocks.SWAMP_ROSE.get()).defaultBlockState(), 2)
                  .add(((FlowerBlock)ESBlocks.NIGHTFAN.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.WHISPERBLOOM.get()).defaultBlockState(), 2)
                  .add(((FlowerBlock)ESBlocks.STARLIGHT_FLOWER.get()).defaultBlockState(), 1)
            ),
            48
         )
      );
      FeatureUtils.register(
         context,
         UNDERGROUND_SWAMP_VEGETATION,
         Feature.RANDOM_PATCH,
         grassPatch(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((ShortBushBlock)ESBlocks.FANTABUD.get()).defaultBlockState(), 3)
                  .add(((ShortBushBlock)ESBlocks.GREEN_FANTABUD.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.FANTAFERN.get()).defaultBlockState(), 3)
                  .add(((ShortBushBlock)ESBlocks.GREEN_FANTAFERN.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.FANTAGRASS.get()).defaultBlockState(), 3)
                  .add(((ShortBushBlock)ESBlocks.GREEN_FANTAGRASS.get()).defaultBlockState(), 2)
                  .add(((PungencyFruitVinesBlock)ESBlocks.PUNGENCY_FRUIT_VINES.get()).defaultBlockState(), 1)
                  .add(
                     (BlockState)((PungencyFruitVinesBlock)ESBlocks.PUNGENCY_FRUIT_VINES.get()).defaultBlockState().setValue(PungencyFruitVinesBlock.AGE, 2), 1
                  )
            ),
            48
         )
      );
      FeatureUtils.register(
         context,
         PERMAFROST_FOREST_VEGETATION,
         Feature.RANDOM_PATCH,
         grassPatch(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((ShortBushBlock)ESBlocks.NIGHT_SPROUTS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.SMALL_NIGHT_SPROUTS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_NIGHT_SPROUTS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.SMALL_GLOWING_NIGHT_SPROUTS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.LUNAR_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_LUNAR_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.CRESCENT_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_CRESCENT_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.PARASOL_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_PARASOL_GRASS.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.LUNAR_BUSH.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.GLOWING_LUNAR_BUSH.get()).defaultBlockState(), 2)
                  .add(((DoublePlantBlock)ESBlocks.TALL_CRESCENT_GRASS.get()).defaultBlockState(), 1)
                  .add(((DoublePlantBlock)ESBlocks.TALL_GLOWING_CRESCENT_GRASS.get()).defaultBlockState(), 1)
                  .add(((FlowerBlock)ESBlocks.CONEBLOOM.get()).defaultBlockState(), 2)
                  .add(((ShortBushBlock)ESBlocks.VIVIDSTALK.get()).defaultBlockState(), 1)
                  .add(((ShortBushBlock)ESBlocks.WHISPERBLOOM.get()).defaultBlockState(), 2)
                  .add(((FlowerBlock)ESBlocks.STARLIGHT_FLOWER.get()).defaultBlockState(), 2)
            ),
            48
         )
      );
      FeatureUtils.register(
         context,
         SCARLET_FOREST_VEGETATION,
         Feature.RANDOM_PATCH,
         grassPatch(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((ShortBushBlock)ESBlocks.ORANGE_SCARLET_BUD.get()).defaultBlockState(), 1)
                  .add(((ShortBushBlock)ESBlocks.PURPLE_SCARLET_BUD.get()).defaultBlockState(), 1)
                  .add(((ShortBushBlock)ESBlocks.RED_SCARLET_BUD.get()).defaultBlockState(), 1)
                  .add(((ShortBushBlock)ESBlocks.SCARLET_GRASS.get()).defaultBlockState(), 1)
                  .add(((DesertBushBlock)ESBlocks.MAUVE_FERN.get()).defaultBlockState(), 1)
                  .add(((ShortBushBlock)ESBlocks.WHISPERBLOOM.get()).defaultBlockState(), 1)
                  .add(((FlowerBlock)ESBlocks.WITHERED_STARLIGHT_FLOWER.get()).defaultBlockState(), 1)
                  .add(((FlowerBlock)ESBlocks.AUREATE_FLOWER.get()).defaultBlockState(), 1)
            ),
            48
         )
      );
      FeatureUtils.register(
         context,
         TORREYA_FOREST_VEGETATION,
         Feature.RANDOM_PATCH,
         grassPatch(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((FlowerBlock)ESBlocks.WITHERED_STARLIGHT_FLOWER.get()).defaultBlockState(), 1)
                  .add(((DesertBushBlock)ESBlocks.MAUVE_FERN.get()).defaultBlockState(), 2)
                  .add(((AlwaysSurvivingBushBlock)ESBlocks.AMARAMBER_GRASS.get()).defaultBlockState(), 2)
                  .add(((AlwaysSurvivingBushBlock)ESBlocks.AMARAMBER_GRASS_BUSH.get()).defaultBlockState(), 1)
                  .add(((ShortBushBlock)ESBlocks.GLOOMCANDLE_ROOT.get()).defaultBlockState(), 1)
            ),
            48
         )
      );
      FeatureUtils.register(
         context,
         DESERT_VEGETATION,
         Feature.RANDOM_PATCH,
         grassPatch(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((DeadBushBlock)ESBlocks.DEAD_LUNAR_BUSH.get()).defaultBlockState(), 3)
                  .add(((DesertFlowerBlock)ESBlocks.DESERT_AMETHYSIA.get()).defaultBlockState(), 1)
                  .add(((DesertFlowerBlock)ESBlocks.WITHERED_DESERT_AMETHYSIA.get()).defaultBlockState(), 1)
                  .add(((DesertBushBlock)ESBlocks.SUNSET_THORNBLOOM.get()).defaultBlockState(), 1)
                  .add(((DesertBushBlock)ESBlocks.AMETHYSIA_GRASS.get()).defaultBlockState(), 30)
                  .add(((DesertBushBlock)ESBlocks.MAUVE_FERN.get()).defaultBlockState(), 27)
            ),
            48
         )
      );
      FeatureUtils.register(
         context,
         BEACH_VEGETATION,
         Feature.RANDOM_PATCH,
         grassPatch(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((DesertBushBlock)ESBlocks.BLAZEBANK_GRASS.get()).defaultBlockState(), 5)
                  .add(((DesertFlowerBlock)ESBlocks.FIRE_ORCHID.get()).defaultBlockState(), 1)
            ),
            48
         )
      );
      FeatureUtils.register(
         context,
         MUSHROOM_VEGETATION,
         Feature.RANDOM_PATCH,
         grassPatch(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((MushroomBlock)ESBlocks.GLOWING_MUSHROOM.get()).defaultBlockState(), 20)
                  .add(((MushroomBlock)ESBlocks.SHINING_MUSHROOM.get()).defaultBlockState(), 20)
                  .add(Blocks.BROWN_MUSHROOM.defaultBlockState(), 9)
                  .add(Blocks.RED_MUSHROOM.defaultBlockState(), 9)
                  .add(((DesertBushBlock)ESBlocks.MAUVE_FERN.get()).defaultBlockState(), 7)
                  .add(((FlowerBlock)ESBlocks.CONEBLOOM.get()).defaultBlockState(), 5)
                  .add(((ShortBushBlock)ESBlocks.GLADESPIKE.get()).defaultBlockState(), 5)
                  .add(((DoublePlantBlock)ESBlocks.TALL_GLADESPIKE.get()).defaultBlockState(), 3)
            ),
            48
         )
      );
      FeatureUtils.register(
         context,
         CAVE_MOSS_VEGETATION,
         Feature.SIMPLE_BLOCK,
         new SimpleBlockConfiguration(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(
                     (BlockState)((CaveMossVeinBlock)ESBlocks.CAVE_MOSS_VEIN.get())
                        .defaultBlockState()
                        .setValue(MultifaceBlock.getFaceProperty(Direction.DOWN), true),
                     9
                  )
                  .add(((CaveMossCarpetBlock)ESBlocks.CAVE_MOSS_CARPET.get()).defaultBlockState(), 25)
            )
         )
      );
      FeatureUtils.register(
         context,
         CAVE_MOSS_PATCH,
         ESFeatures.BLOCK_PATCH.get(),
         new BlockPatchFeature.Configuration(BlockStateProvider.simple(ESBlocks.CAVE_MOSS_BLOCK.get()), BlockTags.MOSS_REPLACEABLE, UniformInt.of(4, 7))
      );
      FeatureUtils.register(
         context,
         CAVE_MOSS_PATCH_BONEMEAL,
         Feature.VEGETATION_PATCH,
         new VegetationPatchConfiguration(
            BlockTags.MOSS_REPLACEABLE,
            BlockStateProvider.simple(ESBlocks.CAVE_MOSS_BLOCK.get()),
            PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(CAVE_MOSS_VEGETATION), new PlacementModifier[0]),
            CaveSurface.FLOOR,
            ConstantInt.of(1),
            0.0F,
            5,
            0.6F,
            UniformInt.of(1, 2),
            0.75F
         )
      );
      FeatureUtils.register(
         context,
         WATERSIDE_VEGETATION,
         Feature.RANDOM_PATCH,
         grassPatch(
            new WeightedStateProvider(SimpleWeightedRandomList.builder().add(((DoublePlantOnSandBlock)ESBlocks.LUNAR_REED.get()).defaultBlockState(), 1)), 48
         )
      );
      FeatureUtils.register(
         context,
         WATER_SURFACE_VEGETATION,
         Feature.RANDOM_PATCH,
         grassPatch(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((WaterlilyBlock)ESBlocks.MOONLIGHT_DUCKWEED.get()).defaultBlockState(), 18)
                  .add(((WaterlilyBlock)ESBlocks.MOONLIGHT_LILY_PAD.get()).defaultBlockState(), 2)
                  .add(((WaterlilyWithFlowerBlock)ESBlocks.STARLIT_LILY_PAD.get()).defaultBlockState(), 1)
                  .add(
                     (BlockState)((WaterlilyWithFlowerBlock)ESBlocks.STARLIT_LILY_PAD.get()).defaultBlockState().setValue(WaterlilyWithFlowerBlock.LIT, true),
                     1
                  )
            ),
            48
         )
      );
      FeatureUtils.register(
         context,
         CRYSTAL_CAVES_VEGETATION,
         Feature.RANDOM_PATCH,
         grassPatch(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((DesertBushBlock)ESBlocks.CRYSTALLIZED_LUNAR_GRASS.get()).defaultBlockState(), 10)
                  .add(((DesertBushBlock)ESBlocks.RED_CRYSTAL_ROOTS.get()).defaultBlockState(), 10)
                  .add(((DesertBushBlock)ESBlocks.BLUE_CRYSTAL_ROOTS.get()).defaultBlockState(), 10)
                  .add(((DesertFlowerBlock)ESBlocks.DESERT_AMETHYSIA.get()).defaultBlockState(), 2)
                  .add(((DesertFlowerBlock)ESBlocks.WITHERED_DESERT_AMETHYSIA.get()).defaultBlockState(), 2)
                  .add(((DesertBushBlock)ESBlocks.SUNSET_THORNBLOOM.get()).defaultBlockState(), 2)
                  .add(((DoublePlantOnStoneBlock)ESBlocks.TWILVEWRYM_HERB.get()).defaultBlockState(), 1)
                  .add(((DoublePlantOnStoneBlock)ESBlocks.STELLAFLY_BUSH.get()).defaultBlockState(), 1)
                  .add(((DoublePlantOnStoneBlock)ESBlocks.GLIMMERFLY_BUSH.get()).defaultBlockState(), 1)
            ),
            48
         )
      );
      FeatureUtils.register(
         context,
         RED_CRYSTAL_MOSS_VEGETATION,
         Feature.SIMPLE_BLOCK,
         new SimpleBlockConfiguration(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((DesertBushBlock)ESBlocks.RED_CRYSTAL_ROOTS.get()).defaultBlockState(), 9)
                  .add(((CarpetBlock)ESBlocks.RED_CRYSTAL_MOSS_CARPET.get()).defaultBlockState(), 25)
            )
         )
      );
      FeatureUtils.register(
         context,
         BLUE_CRYSTAL_MOSS_VEGETATION,
         Feature.SIMPLE_BLOCK,
         new SimpleBlockConfiguration(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(((DesertBushBlock)ESBlocks.BLUE_CRYSTAL_ROOTS.get()).defaultBlockState(), 9)
                  .add(((CarpetBlock)ESBlocks.BLUE_CRYSTAL_MOSS_CARPET.get()).defaultBlockState(), 25)
            )
         )
      );
      FeatureUtils.register(
         context,
         RED_CRYSTAL_MOSS_PATCH,
         ESFeatures.BLOCK_PATCH.get(),
         new BlockPatchFeature.Configuration(BlockStateProvider.simple(ESBlocks.RED_CRYSTAL_MOSS_BLOCK.get()), BlockTags.MOSS_REPLACEABLE, UniformInt.of(4, 7))
      );
      FeatureUtils.register(
         context,
         BLUE_CRYSTAL_MOSS_PATCH,
         ESFeatures.BLOCK_PATCH.get(),
         new BlockPatchFeature.Configuration(BlockStateProvider.simple(ESBlocks.BLUE_CRYSTAL_MOSS_BLOCK.get()), BlockTags.MOSS_REPLACEABLE, UniformInt.of(4, 7))
      );
      FeatureUtils.register(
         context,
         RED_CRYSTAL_MOSS_PATCH_BONEMEAL,
         Feature.VEGETATION_PATCH,
         new VegetationPatchConfiguration(
            BlockTags.MOSS_REPLACEABLE,
            BlockStateProvider.simple(ESBlocks.RED_CRYSTAL_MOSS_BLOCK.get()),
            PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(RED_CRYSTAL_MOSS_VEGETATION), new PlacementModifier[0]),
            CaveSurface.FLOOR,
            ConstantInt.of(1),
            0.0F,
            5,
            0.6F,
            UniformInt.of(1, 2),
            0.75F
         )
      );
      FeatureUtils.register(
         context,
         BLUE_CRYSTAL_MOSS_PATCH_BONEMEAL,
         Feature.VEGETATION_PATCH,
         new VegetationPatchConfiguration(
            BlockTags.MOSS_REPLACEABLE,
            BlockStateProvider.simple(ESBlocks.BLUE_CRYSTAL_MOSS_BLOCK.get()),
            PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(BLUE_CRYSTAL_MOSS_VEGETATION), new PlacementModifier[0]),
            CaveSurface.FLOOR,
            ConstantInt.of(1),
            0.0F,
            5,
            0.6F,
            UniformInt.of(1, 2),
            0.75F
         )
      );
      FeatureUtils.register(context, SWAMP_WATER, ESFeatures.SWAMP_WATER.get());
      FeatureUtils.register(
         context,
         HOT_SPRING,
         ESFeatures.LAKE.get(),
         new ESLakeFeature.Configuration(
            BlockStateProvider.simple(Blocks.WATER.defaultBlockState()),
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(ESBlocks.SPRINGSTONE.get().defaultBlockState(), 10)
                  .add(ESBlocks.THERMAL_SPRINGSTONE.get().defaultBlockState(), 1)
                  .build()
            )
         )
      );
   }

   public static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
      return ResourceKey.create(Registries.CONFIGURED_FEATURE, EternalStarlight.id(name));
   }

   private static RandomPatchConfiguration grassPatch(BlockStateProvider stateProvider, int tries) {
      return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(stateProvider)));
   }
}
