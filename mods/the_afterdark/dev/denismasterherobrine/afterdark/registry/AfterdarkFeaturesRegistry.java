package dev.denismasterherobrine.afterdark.registry;

import dev.denismasterherobrine.afterdark.features.AdditiveBlobFeature;
import dev.denismasterherobrine.afterdark.features.AdditiveGroundBlobFeature;
import dev.denismasterherobrine.afterdark.features.AnvilRockFeature;
import dev.denismasterherobrine.afterdark.features.CatchingFallFeature;
import dev.denismasterherobrine.afterdark.features.CaveKelpFeature;
import dev.denismasterherobrine.afterdark.features.CavePickleFeature;
import dev.denismasterherobrine.afterdark.features.CaveSeagrassFeature;
import dev.denismasterherobrine.afterdark.features.CrystalSpikeFeature;
import dev.denismasterherobrine.afterdark.features.LargeBasaltPillarFeature;
import dev.denismasterherobrine.afterdark.features.LargeBlueIcePillarFeature;
import dev.denismasterherobrine.afterdark.features.LargeCalcitePillarFeature;
import dev.denismasterherobrine.afterdark.features.LargeDarkPrismarinePillarFeature;
import dev.denismasterherobrine.afterdark.features.LargeDeepslatePillarFeature;
import dev.denismasterherobrine.afterdark.features.LargeDiamondOrePillarFeature;
import dev.denismasterherobrine.afterdark.features.LargeEmeraldOrePillarFeature;
import dev.denismasterherobrine.afterdark.features.LargeIcePillarFeature;
import dev.denismasterherobrine.afterdark.features.LargeLapisOrePillarFeature;
import dev.denismasterherobrine.afterdark.features.LargeObsidianPillarFeature;
import dev.denismasterherobrine.afterdark.features.LargePackedIcePillarFeature;
import dev.denismasterherobrine.afterdark.features.LargePrismarinePillarFeature;
import dev.denismasterherobrine.afterdark.features.LargeRawCopperPillarFeature;
import dev.denismasterherobrine.afterdark.features.LargeRawGoldPillarFeature;
import dev.denismasterherobrine.afterdark.features.LargeRawIronPillarFeature;
import dev.denismasterherobrine.afterdark.features.LargeRedstoneOrePillarFeature;
import dev.denismasterherobrine.afterdark.features.LargeSlimePillarFeature;
import dev.denismasterherobrine.afterdark.features.PondFeature;
import dev.denismasterherobrine.afterdark.features.SpiralFeature;
import dev.denismasterherobrine.afterdark.features.SupportedDiskFeature;
import dev.denismasterherobrine.afterdark.features.WaterloggableRandomPatchFeature;
import dev.denismasterherobrine.afterdark.features.configuration.AnvilRockConfiguration;
import dev.denismasterherobrine.afterdark.features.configuration.CatchingFallConfiguration;
import dev.denismasterherobrine.afterdark.features.configuration.DoubleBlockConfiguration;
import dev.denismasterherobrine.afterdark.features.configuration.SpiralConfiguration;
import dev.denismasterherobrine.afterdark.features.configuration.VerticalBlobConfiguration;
import dev.denismasterherobrine.afterdark.features.configuration.WaterloggableRandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class AfterdarkFeaturesRegistry {
   public static final Feature<LargeDripstoneConfiguration> LARGE_BASALT_PILLAR = new LargeBasaltPillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<VerticalBlobConfiguration> CRYSTAL_SPIKE = new CrystalSpikeFeature(VerticalBlobConfiguration.CODEC);
   public static final Feature<SpiralConfiguration> SPIRAL = new SpiralFeature(SpiralConfiguration.CODEC);
   public static final Feature<VerticalBlobConfiguration> ADDITIVE_BLOB = new AdditiveBlobFeature(VerticalBlobConfiguration.CODEC);
   public static final Feature<AnvilRockConfiguration> ANVIL_ROCK_FEATURE = new AnvilRockFeature(AnvilRockConfiguration.CODEC);
   public static final Feature<CatchingFallConfiguration> CATCHING_FALL = new CatchingFallFeature(CatchingFallConfiguration.CODEC);
   public static final Feature<DoubleBlockConfiguration> POND = new PondFeature(DoubleBlockConfiguration.CODEC);
   public static final Feature<VerticalBlobConfiguration> ADDITIVE_GROUND_BLOB = new AdditiveGroundBlobFeature(VerticalBlobConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_CALCITE_PILLAR = new LargeCalcitePillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_PRISMARINE_PILLAR = new LargePrismarinePillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_DARK_PRISMARINE_PILLAR = new LargeDarkPrismarinePillarFeature(
      LargeDripstoneConfiguration.CODEC
   );
   public static final Feature<LargeDripstoneConfiguration> LARGE_SLIME_PILLAR = new LargeSlimePillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<WaterloggableRandomPatchConfiguration> WATERLOGGABLE_RANDOM_PATCH_FEATURE = new WaterloggableRandomPatchFeature(
      WaterloggableRandomPatchConfiguration.CODEC
   );
   public static final Feature<NoneFeatureConfiguration> CAVE_KELP_FEATURE = new CaveKelpFeature(NoneFeatureConfiguration.CODEC);
   public static final Feature<NoneFeatureConfiguration> CAVE_PICKLE_FEATURE = new CavePickleFeature(NoneFeatureConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_DEEPSLATE_PILLAR = new LargeDeepslatePillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<ProbabilityFeatureConfiguration> CAVE_SEAGRASS_FEATURE = new CaveSeagrassFeature(ProbabilityFeatureConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_OBSIDIAN_PILLAR = new LargeObsidianPillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<DiskConfiguration> SUPPORTED_DISK_FEATURE = new SupportedDiskFeature(DiskConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_PACKED_ICE_PILLAR = new LargePackedIcePillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_ICE_PILLAR = new LargeIcePillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_BLUE_ICE_PILLAR = new LargeBlueIcePillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_RAW_IRON_PILLAR = new LargeRawIronPillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_RAW_GOLD_PILLAR = new LargeRawGoldPillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_RAW_COPPER_PILLAR = new LargeRawCopperPillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_DIAMOND_ORE_PILLAR = new LargeDiamondOrePillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_EMERALD_ORE_PILLAR = new LargeEmeraldOrePillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_LAPIS_ORE_PILLAR = new LargeLapisOrePillarFeature(LargeDripstoneConfiguration.CODEC);
   public static final Feature<LargeDripstoneConfiguration> LARGE_REDSTONE_ORE_PILLAR = new LargeRedstoneOrePillarFeature(LargeDripstoneConfiguration.CODEC);
}
