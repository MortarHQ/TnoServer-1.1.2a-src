package dev.denismasterherobrine.afterdark.neoforge.registry;

import dev.denismasterherobrine.afterdark.blocks.entity.TeleportBlockEntity;
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
import dev.denismasterherobrine.afterdark.registry.AfterdarkRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class AfterdarkNeoForgeRegistry {
   public static final Blocks BLOCKS = DeferredRegister.createBlocks("the_afterdark");
   public static final Items ITEMS = DeferredRegister.createItems("the_afterdark");
   public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, "the_afterdark");
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "the_afterdark");
   public static final DeferredBlock<Block> TELEPORT_BLOCK = BLOCKS.register("teleport_block", () -> AfterdarkRegistry.TELEPORT_BLOCK);
   public static final DeferredItem<Item> TELEPORT_BLOCK_ITEM = ITEMS.register("teleport_block", () -> AfterdarkRegistry.TELEPORT_BLOCK_ITEM);
   public static final DeferredItem<Item> TELEPORT_CATALYST_ITEM = ITEMS.register("teleport_catalyst", () -> AfterdarkRegistry.TELEPORT_CATALYST_ITEM);
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TeleportBlockEntity>> TELEPORT_BLOCK_ENTITY = BLOCK_ENTITIES.register(
      TeleportBlockEntity.TELEPORT_BE_ID, () -> Builder.of(TeleportBlockEntity::new, new Block[]{(Block)TELEPORT_BLOCK.get()}).build(null)
   );
   public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, "the_afterdark");
   public static final DeferredHolder<Feature<?>, LargeBasaltPillarFeature> LARGE_BASALT_PILLAR = FEATURES.register(
      "large_basalt_pillar", () -> new LargeBasaltPillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, CrystalSpikeFeature> CRYSTAL_SPIKE = FEATURES.register(
      "crystal_spike", () -> new CrystalSpikeFeature(VerticalBlobConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, SpiralFeature> SPIRAL = FEATURES.register("spiral", () -> new SpiralFeature(SpiralConfiguration.CODEC));
   public static final DeferredHolder<Feature<?>, AdditiveBlobFeature> ADDITIVE_BLOB = FEATURES.register(
      "additive_blob", () -> new AdditiveBlobFeature(VerticalBlobConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, AnvilRockFeature> ANVIL_ROCK_FEATURE = FEATURES.register(
      "anvil_rock", () -> new AnvilRockFeature(AnvilRockConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, CatchingFallFeature> CATCHING_FALL = FEATURES.register(
      "catching_fall", () -> new CatchingFallFeature(CatchingFallConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, PondFeature> POND = FEATURES.register("pond", () -> new PondFeature(DoubleBlockConfiguration.CODEC));
   public static final DeferredHolder<Feature<?>, AdditiveGroundBlobFeature> ADDITIVE_GROUND_BLOB = FEATURES.register(
      "additive_ground_blob", () -> new AdditiveGroundBlobFeature(VerticalBlobConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargeCalcitePillarFeature> LARGE_CALCITE_PILLAR = FEATURES.register(
      "large_calcite_pillar", () -> new LargeCalcitePillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargePrismarinePillarFeature> LARGE_PRISMARINE_PILLAR = FEATURES.register(
      "large_prismarine_pillar", () -> new LargePrismarinePillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargeDarkPrismarinePillarFeature> LARGE_DARK_PRISMARINE_PILLAR = FEATURES.register(
      "large_dark_prismarine_pillar", () -> new LargeDarkPrismarinePillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargeSlimePillarFeature> LARGE_SLIME_PILLAR = FEATURES.register(
      "large_slime_pillar", () -> new LargeSlimePillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, WaterloggableRandomPatchFeature> WATERLOGGABLE_RANDOM_PATCH_FEATURE = FEATURES.register(
      "waterloggable_random_patch", () -> new WaterloggableRandomPatchFeature(WaterloggableRandomPatchConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, CaveKelpFeature> CAVE_KELP_FEATURE = FEATURES.register(
      "cave_kelp", () -> new CaveKelpFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, CavePickleFeature> CAVE_PICKLE_FEATURE = FEATURES.register(
      "cave_pickle", () -> new CavePickleFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargeDeepslatePillarFeature> LARGE_DEEPSLATE_PILLAR = FEATURES.register(
      "large_deepslate_pillar", () -> new LargeDeepslatePillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, CaveSeagrassFeature> CAVE_SEAGRASS_FEATURE = FEATURES.register(
      "cave_seagrass", () -> new CaveSeagrassFeature(ProbabilityFeatureConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargeObsidianPillarFeature> LARGE_OBSIDIAN_PILLAR = FEATURES.register(
      "large_obsidian_pillar", () -> new LargeObsidianPillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, SupportedDiskFeature> SUPPORTED_DISK_FEATURE = FEATURES.register(
      "supported_disk", () -> new SupportedDiskFeature(DiskConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargePackedIcePillarFeature> LARGE_PACKED_ICE_PILLAR = FEATURES.register(
      "large_packed_ice_pillar", () -> new LargePackedIcePillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargeIcePillarFeature> LARGE_ICE_PILLAR = FEATURES.register(
      "large_ice_pillar", () -> new LargeIcePillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargeBlueIcePillarFeature> LARGE_BLUE_ICE_PILLAR = FEATURES.register(
      "large_blue_ice_pillar", () -> new LargeBlueIcePillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargeRawIronPillarFeature> LARGE_RAW_IRON_PILLAR = FEATURES.register(
      "raw_iron_pillar", () -> new LargeRawIronPillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargeRawCopperPillarFeature> LARGE_RAW_COPPER_PILLAR = FEATURES.register(
      "raw_copper_pillar", () -> new LargeRawCopperPillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargeRawGoldPillarFeature> LARGE_RAW_GOLD_PILLAR = FEATURES.register(
      "raw_gold_pillar", () -> new LargeRawGoldPillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargeRedstoneOrePillarFeature> LARGE_REDSTONE_ORE_PILLAR = FEATURES.register(
      "redstone_ore_pillar", () -> new LargeRedstoneOrePillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargeLapisOrePillarFeature> LARGE_LAPIS_ORE_PILLAR = FEATURES.register(
      "lapis_ore_pillar", () -> new LargeLapisOrePillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargeEmeraldOrePillarFeature> LARGE_EMERALD_ORE_PILLAR = FEATURES.register(
      "emerald_ore_pillar", () -> new LargeEmeraldOrePillarFeature(LargeDripstoneConfiguration.CODEC)
   );
   public static final DeferredHolder<Feature<?>, LargeDiamondOrePillarFeature> LARGE_DIAMOND_ORE_PILLAR = FEATURES.register(
      "diamond_ore_pillar", () -> new LargeDiamondOrePillarFeature(LargeDripstoneConfiguration.CODEC)
   );

   public static void register(IEventBus eventBus) {
      CREATIVE_MODE_TABS.register("afterdark", () -> AfterdarkRegistry.AFTERDARK);
      BLOCKS.register(eventBus);
      BLOCK_ENTITIES.register(eventBus);
      ITEMS.register(eventBus);
      CREATIVE_MODE_TABS.register(eventBus);
      FEATURES.register(eventBus);
   }
}
