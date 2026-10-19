package com.mortal_cultivation.registry;

import com.mortal_cultivation.worldgen.AncientCherryPiece;
import com.mortal_cultivation.worldgen.AncientCherryStructure;
import com.mortal_cultivation.worldgen.JiuyouColdPoolPiece;
import com.mortal_cultivation.worldgen.JiuyouColdPoolStructure;
import com.mortal_cultivation.worldgen.LightningWoodLingzhiDecorator;
import com.mortal_cultivation.worldgen.QilinBambooGroveFeature;
import com.mortal_cultivation.worldgen.QilinBambooGrovePiece;
import com.mortal_cultivation.worldgen.QilinBambooGroveStructure;
import com.mortal_cultivation.worldgen.SpiritForestBambooFeature;
import com.mortal_cultivation.worldgen.SpiritForestLandmarksFeature;
import com.mortal_cultivation.worldgen.SpiritForestRockFeature;
import com.mortal_cultivation.worldgen.SpiritSkyIslandsFeature;
import com.mortal_cultivation.worldgen.SpiritTreeFoliagePlacer;
import com.mortal_cultivation.worldgen.SpiritTreeTrunkPlacer;
import com.mortal_cultivation.worldgen.SteleGrottoPiece;
import com.mortal_cultivation.worldgen.SteleGrottoStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType.ContextlessType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class WorldgenRegistry {
   public static final ResourceKey<Biome> SPIRIT_FOREST = ResourceKey.create(
      Registries.BIOME, ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "spirit_forest")
   );
   public static final ResourceKey<Biome> SKY_SPIRIT_FOREST = ResourceKey.create(
      Registries.BIOME, ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "sky_spirit_forest")
   );
   public static final ResourceKey<Biome> JIUYOU_COLD_POOL_BIOME = ResourceKey.create(
      Registries.BIOME, ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "jiuyou_cold_pool")
   );
   public static final TagKey<Biome> SPIRIT_FORESTS = TagKey.create(
      Registries.BIOME, ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "spirit_forests")
   );
   public static final ResourceKey<ConfiguredFeature<?, ?>> SPIRIT_TREE = ResourceKey.create(
      Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "spirit_tree")
   );
   public static final ResourceKey<Structure> ANCIENT_CHERRY_TREE_STRUCTURE = ResourceKey.create(
      Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "ancient_cherry_tree")
   );
   public static final ResourceKey<Structure> QILIN_BAMBOO_GROVE_STRUCTURE = ResourceKey.create(
      Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "qilin_bamboo_grove")
   );
   public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, "mortal_cultivation");
   public static final DeferredHolder<Feature<?>, SpiritForestRockFeature> SPIRIT_FOREST_ROCK = FEATURES.register(
      "spirit_forest_rock", SpiritForestRockFeature::new
   );
   public static final DeferredHolder<Feature<?>, SpiritSkyIslandsFeature> SPIRIT_SKY_ISLANDS = FEATURES.register(
      "spirit_sky_islands", SpiritSkyIslandsFeature::new
   );
   public static final DeferredHolder<Feature<?>, SpiritForestLandmarksFeature> SPIRIT_FOREST_LANDMARKS = FEATURES.register(
      "spirit_forest_landmarks", SpiritForestLandmarksFeature::new
   );
   public static final DeferredHolder<Feature<?>, QilinBambooGroveFeature> QILIN_BAMBOO_GROVE = FEATURES.register(
      "qilin_bamboo_grove", QilinBambooGroveFeature::new
   );
   public static final DeferredHolder<Feature<?>, SpiritForestBambooFeature> SPIRIT_FOREST_BAMBOO = FEATURES.register(
      "spirit_forest_bamboo", SpiritForestBambooFeature::new
   );
   public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, "mortal_cultivation");
   public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(
      Registries.FOLIAGE_PLACER_TYPE, "mortal_cultivation"
   );
   public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(
      Registries.TREE_DECORATOR_TYPE, "mortal_cultivation"
   );
   public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<SpiritTreeTrunkPlacer>> SPIRIT_TREE_TRUNK = TRUNK_PLACER_TYPES.register(
      "spirit_tree_trunk", () -> new TrunkPlacerType(SpiritTreeTrunkPlacer.CODEC)
   );
   public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<SpiritTreeFoliagePlacer>> SPIRIT_TREE_FOLIAGE = FOLIAGE_PLACER_TYPES.register(
      "spirit_tree_foliage", () -> new FoliagePlacerType(SpiritTreeFoliagePlacer.CODEC)
   );
   public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<LightningWoodLingzhiDecorator>> LIGHTNING_WOOD_LINGZHI_DECORATOR = TREE_DECORATOR_TYPES.register(
      "lightning_wood_lingzhi", () -> new TreeDecoratorType(LightningWoodLingzhiDecorator.CODEC)
   );
   public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, "mortal_cultivation");
   public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, "mortal_cultivation");
   public static final DeferredHolder<StructureType<?>, StructureType<SteleGrottoStructure>> STELE_GROTTO = STRUCTURE_TYPES.register(
      "stele_grotto", () -> () -> SteleGrottoStructure.CODEC
   );
   public static final DeferredHolder<StructureType<?>, StructureType<AncientCherryStructure>> ANCIENT_CHERRY_TREE = STRUCTURE_TYPES.register(
      "ancient_cherry_tree", () -> () -> AncientCherryStructure.CODEC
   );
   public static final DeferredHolder<StructureType<?>, StructureType<JiuyouColdPoolStructure>> JIUYOU_COLD_POOL = STRUCTURE_TYPES.register(
      "jiuyou_cold_pool", () -> () -> JiuyouColdPoolStructure.CODEC
   );
   public static final DeferredHolder<StructureType<?>, StructureType<QilinBambooGroveStructure>> QILIN_BAMBOO_GROVE_TYPE = STRUCTURE_TYPES.register(
      "qilin_bamboo_grove", () -> () -> QilinBambooGroveStructure.CODEC
   );
   public static final DeferredHolder<StructurePieceType, ContextlessType> QILIN_BAMBOO_GROVE_PIECE = STRUCTURE_PIECE_TYPES.register(
      "qilin_bamboo_grove", () -> QilinBambooGrovePiece::new
   );
   public static final DeferredHolder<StructurePieceType, ContextlessType> JIUYOU_COLD_POOL_PIECE = STRUCTURE_PIECE_TYPES.register(
      "jiuyou_cold_pool", () -> JiuyouColdPoolPiece::new
   );
   public static final DeferredHolder<StructurePieceType, ContextlessType> ANCIENT_CHERRY_TREE_PIECE = STRUCTURE_PIECE_TYPES.register(
      "ancient_cherry_tree", () -> AncientCherryPiece::new
   );
   public static final DeferredHolder<StructurePieceType, ContextlessType> STELE_GROTTO_PIECE = STRUCTURE_PIECE_TYPES.register(
      "stele_grotto", () -> SteleGrottoPiece::new
   );

   private WorldgenRegistry() {
   }

   public static void register(IEventBus modEventBus) {
      FEATURES.register(modEventBus);
      TRUNK_PLACER_TYPES.register(modEventBus);
      FOLIAGE_PLACER_TYPES.register(modEventBus);
      TREE_DECORATOR_TYPES.register(modEventBus);
      STRUCTURE_TYPES.register(modEventBus);
      STRUCTURE_PIECE_TYPES.register(modEventBus);
   }
}
