package twilightforest.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import twilightforest.world.components.feature.BerryBushFeature;
import twilightforest.world.components.feature.BigMushgloomFeature;
import twilightforest.world.components.feature.BlockSpikeFeature;
import twilightforest.world.components.feature.CheckAbovePatchFeature;
import twilightforest.world.components.feature.DarkForestFeature;
import twilightforest.world.components.feature.EnchantedForestVinesFeature;
import twilightforest.world.components.feature.FallenHollowLogFeature;
import twilightforest.world.components.feature.FallenLeavesFeature;
import twilightforest.world.components.feature.FireJetFeature;
import twilightforest.world.components.feature.FoundationFeature;
import twilightforest.world.components.feature.HugeLilypadFeature;
import twilightforest.world.components.feature.HugeWaterLilyFeature;
import twilightforest.world.components.feature.LampostFeature;
import twilightforest.world.components.feature.MonolithFeature;
import twilightforest.world.components.feature.SmallFallenLogFeature;
import twilightforest.world.components.feature.TFSmallLakeFeature;
import twilightforest.world.components.feature.ThornFeature;
import twilightforest.world.components.feature.UndergroundPlantFeature;
import twilightforest.world.components.feature.WebFeature;
import twilightforest.world.components.feature.WeightedListFeature;
import twilightforest.world.components.feature.WoodRootFeature;
import twilightforest.world.components.feature.config.BerryBushConfig;
import twilightforest.world.components.feature.config.HollowLogConfig;
import twilightforest.world.components.feature.config.RootConfig;
import twilightforest.world.components.feature.config.RuinedFoundationConfig;
import twilightforest.world.components.feature.config.SwizzleConfig;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;
import twilightforest.world.components.feature.config.ThornsConfig;
import twilightforest.world.components.feature.config.WeightedListFeatureConfig;
import twilightforest.world.components.feature.templates.DruidHutFeature;
import twilightforest.world.components.feature.templates.FancyWellFeature;
import twilightforest.world.components.feature.templates.GraveyardFeature;
import twilightforest.world.components.feature.templates.GroveRuinsFeature;
import twilightforest.world.components.feature.templates.SimpleWellFeature;
import twilightforest.world.components.feature.templates.StoneCircleFeature;
import twilightforest.world.components.feature.trees.BrownCanopyMushroomFeature;
import twilightforest.world.components.feature.trees.DarkCanopyTreeFeature;
import twilightforest.world.components.feature.trees.HollowStumpFeature;
import twilightforest.world.components.feature.trees.LargeWinterTreeFeature;
import twilightforest.world.components.feature.trees.MegaCanopyTreeFeature;
import twilightforest.world.components.feature.trees.MegaOakTreeFeature;
import twilightforest.world.components.feature.trees.MiningTreeFeature;
import twilightforest.world.components.feature.trees.RedCanopyMushroomFeature;
import twilightforest.world.components.feature.trees.SnowTreeFeature;
import twilightforest.world.components.feature.trees.SnowUnderTreeFeature;
import twilightforest.world.components.feature.trees.TimeTreeFeature;
import twilightforest.world.components.feature.trollcave.TrollBigMushgloomFeature;
import twilightforest.world.components.feature.trollcave.TrollHugeBrownMushroomFeature;
import twilightforest.world.components.feature.trollcave.TrollHugeRedMushroomFeature;

public class TFFeatures {
   public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, "twilightforest");
   public static final DeferredHolder<Feature<?>, Feature<TFSmallLakeFeature.Configuration>> SMALL_LAKE = FEATURES.register(
      "small_lake", () -> new TFSmallLakeFeature(TFSmallLakeFeature.Configuration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> BIG_MUSHGLOOM = FEATURES.register(
      "big_mushgloom", () -> new BigMushgloomFeature(HugeMushroomFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> CANOPY_BROWN_MUSHROOM = FEATURES.register(
      "canopy_brown_mushroom", () -> new BrownCanopyMushroomFeature(HugeMushroomFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> CANOPY_RED_VANILLA_MUSHROOM = FEATURES.register(
      "canopy_red_vanilla_mushroom", () -> new RedCanopyMushroomFeature(HugeMushroomFeatureConfiguration.CODEC, 0)
   );
   public static final DeferredHolder<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> CANOPY_RED_SMOOTH_MUSHROOM = FEATURES.register(
      "canopy_red_smooth_mushroom", () -> new RedCanopyMushroomFeature(HugeMushroomFeatureConfiguration.CODEC, 1)
   );
   public static final DeferredHolder<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> CANOPY_RED_SPHEROID_MUSHROOM = FEATURES.register(
      "canopy_red_spheroid_mushroom", () -> new RedCanopyMushroomFeature(HugeMushroomFeatureConfiguration.CODEC, 2)
   );
   public static final DeferredHolder<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> CANOPY_RED_FLAT_MUSHROOM = FEATURES.register(
      "canopy_red_flat_mushroom", () -> new RedCanopyMushroomFeature(HugeMushroomFeatureConfiguration.CODEC, 3)
   );
   public static final DeferredHolder<Feature<?>, Feature<TFTreeFeatureConfig>> MEGA_OAK = FEATURES.register(
      "mega_oak", () -> new MegaOakTreeFeature(TFTreeFeatureConfig.codecTFTreeConfig)
   );
   public static final DeferredHolder<Feature<?>, Feature<TFTreeFeatureConfig>> MEGA_CANOPY = FEATURES.register(
      "mega_canopy", () -> new MegaCanopyTreeFeature(TFTreeFeatureConfig.codecTFTreeConfig)
   );
   public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> CAVE_STALACTITE = FEATURES.register(
      "block_spike", () -> new BlockSpikeFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<TreeConfiguration>> DARK_CANOPY_TREE = FEATURES.register(
      "dark_canopy_tree", () -> new DarkCanopyTreeFeature(TreeConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<SwizzleConfig>> DRUID_HUT = FEATURES.register(
      "druid_hut", () -> new DruidHutFeature(SwizzleConfig.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> FALLEN_HOLLOW_LOG = FEATURES.register(
      "fallen_hollow_log", () -> new FallenHollowLogFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> FALLEN_LEAVES = FEATURES.register(
      "fallen_leaves", () -> new FallenLeavesFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<HollowLogConfig>> FALLEN_SMALL_LOG = FEATURES.register(
      "fallen_small_log", () -> new SmallFallenLogFeature(HollowLogConfig.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<BlockStateConfiguration>> FIRE_JET = FEATURES.register(
      "fire_jet", () -> new FireJetFeature(BlockStateConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<RuinedFoundationConfig>> FOUNDATION = FEATURES.register(
      "foundation", () -> new FoundationFeature(RuinedFoundationConfig.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> GRAVEYARD = FEATURES.register(
      "graveyard", () -> new GraveyardFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> GROVE_RUINS = FEATURES.register(
      "grove_ruins", () -> new GroveRuinsFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<TFTreeFeatureConfig>> HOLLOW_STUMP = FEATURES.register(
      "hollow_stump", () -> new HollowStumpFeature(TFTreeFeatureConfig.codecTFTreeConfig)
   );
   public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> HUGE_LILY_PAD = FEATURES.register(
      "huge_lily_pad", () -> new HugeLilypadFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> HUGE_WATER_LILY = FEATURES.register(
      "huge_water_lily", () -> new HugeWaterLilyFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<BlockStateConfiguration>> LAMPPOSTS = FEATURES.register(
      "lampposts", () -> new LampostFeature(BlockStateConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<TFTreeFeatureConfig>> LARGE_WINTER_TREE = FEATURES.register(
      "large_winter_tree", () -> new LargeWinterTreeFeature(TFTreeFeatureConfig.codecTFTreeConfig)
   );
   public static final DeferredHolder<Feature<?>, Feature<TFTreeFeatureConfig>> MINERS_TREE = FEATURES.register(
      "miners_tree", () -> new MiningTreeFeature(TFTreeFeatureConfig.codecTFTreeConfig)
   );
   public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> MONOLITH = FEATURES.register(
      "monolith", () -> new MonolithFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<DiskConfiguration>> MYCELIUM_BLOB = FEATURES.register(
      "mycelium_blob", () -> new CheckAbovePatchFeature(DiskConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<BerryBushConfig>> BERRY_BUSH = FEATURES.register(
      "berry_bush", () -> new BerryBushFeature(BerryBushConfig.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<BlockStateConfiguration>> UNDERGROUND_PLANTS = FEATURES.register(
      "underground_plants", () -> new UndergroundPlantFeature(BlockStateConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<BlockStateConfiguration>> UNDERGROUND_PLANTS_IN_STRUCTURE = FEATURES.register(
      "underground_plants_in_structure", () -> new UndergroundPlantFeature(BlockStateConfiguration.CODEC, true)
   );
   public static final DeferredHolder<Feature<?>, Feature<BlockStateConfiguration>> OREBERRY_BUSHES = FEATURES.register(
      "oreberry_bushes", () -> new UndergroundPlantFeature(BlockStateConfiguration.CODEC, 1)
   );
   public static final DeferredHolder<Feature<?>, Feature<BlockStateConfiguration>> TROLL_VINES = FEATURES.register(
      "troll_vines", () -> new UndergroundPlantFeature(BlockStateConfiguration.CODEC, true)
   );
   public static final DeferredHolder<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> TROLL_BIG_MUSHGLOOM = FEATURES.register(
      "troll_cave_big_mushgloom", () -> new TrollBigMushgloomFeature(HugeMushroomFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> TROLL_HUGE_RED_MUSHROOM = FEATURES.register(
      "troll_cave_huge_red_mushroom", () -> new TrollHugeRedMushroomFeature(HugeMushroomFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> TROLL_HUGE_BROWN_MUSHROOM = FEATURES.register(
      "troll_cave_huge_brown_mushroom", () -> new TrollHugeBrownMushroomFeature(HugeMushroomFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> STONE_CIRCLE = FEATURES.register(
      "stone_circle", () -> new StoneCircleFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<ThornsConfig>> THORNS = FEATURES.register("thorns", () -> new ThornFeature(ThornsConfig.CODEC));
   public static final DeferredHolder<Feature<?>, Feature<TFTreeFeatureConfig>> TREE_OF_TIME = FEATURES.register(
      "tree_of_time", () -> new TimeTreeFeature(TFTreeFeatureConfig.codecTFTreeConfig)
   );
   public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> WEBS = FEATURES.register(
      "webs", () -> new WebFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<SwizzleConfig>> SIMPLE_WELL = FEATURES.register(
      "simple_well", () -> new SimpleWellFeature(SwizzleConfig.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<SwizzleConfig>> FANCY_WELL = FEATURES.register(
      "fancy_well", () -> new FancyWellFeature(SwizzleConfig.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<RootConfig>> WOOD_ROOTS = FEATURES.register("wood_roots", () -> new WoodRootFeature(RootConfig.CODEC));
   public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SNOW_UNDER_TREES = FEATURES.register(
      "snow_under_trees", () -> new SnowUnderTreeFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<TreeConfiguration>> SNOW_TREE = FEATURES.register(
      "anywhere_tree", () -> new SnowTreeFeature(TreeConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> ENCHANTED_FOREST_VINES = FEATURES.register(
      "enchanted_forest_vines", () -> new EnchantedForestVinesFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<RandomPatchConfiguration>> DARK_FOREST_PLACER = FEATURES.register(
      "dark_forest_placer", () -> new DarkForestFeature(RandomPatchConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, Feature<WeightedListFeatureConfig>> WEIGHTED_LIST_SELECTOR = FEATURES.register(
      "weighted_list", () -> new WeightedListFeature(WeightedListFeatureConfig.CODEC)
   );
}
