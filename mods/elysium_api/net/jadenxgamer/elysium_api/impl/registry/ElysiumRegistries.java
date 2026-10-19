package net.jadenxgamer.elysium_api.impl.registry;

import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.jadenxgamer.elysium_api.ElysiumAPI;
import net.jadenxgamer.elysium_api.api.surface_rules.condition.BiomeTagConditionSource;
import net.jadenxgamer.elysium_api.api.surface_rules.condition.BlockMatchConditionSource;
import net.jadenxgamer.elysium_api.impl.core.biome.MosaicBiomeSource;
import net.jadenxgamer.elysium_api.impl.core.datadriven.block.BlockSoundTransformer;
import net.jadenxgamer.elysium_api.impl.core.datadriven.block.properties_transformer.BlockPropertiesTransformer;
import net.jadenxgamer.elysium_api.impl.core.datadriven.block.use_behaviors.UseBehavior;
import net.jadenxgamer.elysium_api.impl.core.datadriven.brewing_recipe.ElysiumBrewingRecipe;
import net.jadenxgamer.elysium_api.impl.core.datadriven.item.properties_transformer.ItemPropertiesTransformer;
import net.jadenxgamer.elysium_api.impl.core.datadriven.mosaic.MosaicBiomeEntry;
import net.jadenxgamer.elysium_api.impl.core.misc.neoforge.EffectsBiomeModifier;
import net.jadenxgamer.elysium_api.impl.core.worldgen.feature.StructureStamp;
import net.jadenxgamer.elysium_api.impl.core.worldgen.structure.GiantJigsawStructure;
import net.jadenxgamer.elysium_api.impl.core.worldgen.structure.MultilayerJigsawStructure;
import net.jadenxgamer.elysium_api.impl.core.worldgen.structure.processor.ProtectNonReplaceableProcessor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent.NewRegistry;

public class ElysiumRegistries {
   public static final DeferredRegister<MapCodec<? extends BiomeSource>> BIOME_SOURCES = DeferredRegister.create(BuiltInRegistries.BIOME_SOURCE, "elysium_api");
   private static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(
      net.neoforged.neoforge.registries.NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, "elysium_api"
   );
   public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, "elysium_api");
   public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPE = DeferredRegister.create(BuiltInRegistries.STRUCTURE_TYPE, "elysium_api");
   public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR = DeferredRegister.create(
      BuiltInRegistries.STRUCTURE_PROCESSOR, "elysium_api"
   );
   public static final DeferredRegister<MapCodec<? extends ConditionSource>> MATERIAL_CONDITIONS = DeferredRegister.create(
      BuiltInRegistries.MATERIAL_CONDITION, "elysium_api"
   );
   public static final Supplier<MapCodec<MosaicBiomeSource>> MOSAIC = BIOME_SOURCES.register("mosaic", () -> MosaicBiomeSource.CODEC);
   public static final Supplier<MapCodec<EffectsBiomeModifier>> EFFECTS_MODIFIER = BIOME_MODIFIERS.register(
      "effects_modifier", () -> EffectsBiomeModifier.CODEC
   );
   public static final Supplier<Feature<StructureStamp.Config>> STRUCTURE_STAMP = FEATURES.register(
      "structure_stamp", () -> new StructureStamp(StructureStamp.Config.CODEC)
   );
   public static final Supplier<StructureType<MultilayerJigsawStructure>> MULTILAYERED_JIGSAW = STRUCTURE_TYPE.register(
      "multilayered_jigsaw", () -> () -> MultilayerJigsawStructure.CODEC
   );
   public static final Supplier<StructureType<GiantJigsawStructure>> GIANT_JIGSAW = STRUCTURE_TYPE.register(
      "giant_jigsaw", () -> () -> GiantJigsawStructure.CODEC
   );
   public static final Supplier<StructureProcessorType<ProtectNonReplaceableProcessor>> PROTECT_NON_REPLACEABLE = STRUCTURE_PROCESSOR.register(
      "protect_non_replaceable", () -> () -> ProtectNonReplaceableProcessor.CODEC
   );
   public static final Supplier<MapCodec<? extends ConditionSource>> BIOME_TAG = MATERIAL_CONDITIONS.register("biome_tag", BiomeTagConditionSource.CODEC::codec);
   public static final Supplier<MapCodec<? extends ConditionSource>> BLOCK_MATCH = MATERIAL_CONDITIONS.register(
      "block_match", BlockMatchConditionSource.CODEC::codec
   );

   private static <T> ResourceKey<Registry<T>> key(String name) {
      return ResourceKey.createRegistryKey(ElysiumAPI.elysiumPath(name));
   }

   public static void init(IEventBus eventBus) {
      BIOME_SOURCES.register(eventBus);
      BIOME_MODIFIERS.register(eventBus);
      FEATURES.register(eventBus);
      STRUCTURE_TYPE.register(eventBus);
      STRUCTURE_PROCESSOR.register(eventBus);
      MATERIAL_CONDITIONS.register(eventBus);
   }

   public static void registryInit(NewRegistryEvent event) {
   }

   public static void datapackInit(NewRegistry event) {
      event.dataPackRegistry(ElysiumRegistries.Keys.USE_BEHAVIORS, UseBehavior.CODEC);
      event.dataPackRegistry(ElysiumRegistries.Keys.BLOCK_SOUND_TRANSFORMERS, BlockSoundTransformer.CODEC, BlockSoundTransformer.CODEC);
      event.dataPackRegistry(ElysiumRegistries.Keys.BREWING_RECIPES, ElysiumBrewingRecipe.CODEC, ElysiumBrewingRecipe.CODEC);
      event.dataPackRegistry(ElysiumRegistries.Keys.MOSAIC_BIOME_ENTRY, MosaicBiomeEntry.CODEC);
      event.dataPackRegistry(ElysiumRegistries.Keys.BLOCK_PROPERTIES_TRANSFORMERS, BlockPropertiesTransformer.CODEC, BlockPropertiesTransformer.CODEC);
      event.dataPackRegistry(ElysiumRegistries.Keys.ITEM_PROPERTIES_TRANSFORMERS, ItemPropertiesTransformer.CODEC, ItemPropertiesTransformer.CODEC);
   }

   public static final class Keys {
      public static final ResourceKey<Registry<UseBehavior>> USE_BEHAVIORS = ElysiumRegistries.key("block/use_behaviors");
      public static final ResourceKey<Registry<BlockSoundTransformer>> BLOCK_SOUND_TRANSFORMERS = ElysiumRegistries.key("block/sound_transformers");
      public static final ResourceKey<Registry<ElysiumBrewingRecipe>> BREWING_RECIPES = ElysiumRegistries.key("brewing_recipes");
      public static final ResourceKey<Registry<MosaicBiomeEntry>> MOSAIC_BIOME_ENTRY = ElysiumRegistries.key("mosaic_biome_entry");
      public static final ResourceKey<Registry<BlockPropertiesTransformer>> BLOCK_PROPERTIES_TRANSFORMERS = ElysiumRegistries.key(
         "block/properties_transformer"
      );
      public static final ResourceKey<Registry<ItemPropertiesTransformer>> ITEM_PROPERTIES_TRANSFORMERS = ElysiumRegistries.key("item/properties_transformer");
   }
}
