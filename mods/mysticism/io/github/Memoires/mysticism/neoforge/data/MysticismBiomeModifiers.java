package io.github.Memoires.mysticism.neoforge.data;

import io.github.Memoires.mysticism.data.MysticismBiomeTags;
import io.github.Memoires.mysticism.registry.world.MysticismPlacedFeatures;
import io.github.Memoires.mysticism.world.MysticismBiomeModification;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.MobSpawnCost;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddFeaturesBiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddSpawnCostsBiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddSpawnsBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class MysticismBiomeModifiers {
   public static final ResourceKey<BiomeModifier> ICE_ORE = registerKey("ice_ore");

   public static void bootstrap(BootstrapContext<BiomeModifier> context) {
      addSpawns(context);
      addFeatures(context);
   }

   private static void addSpawns(BootstrapContext<BiomeModifier> context) {
      MysticismBiomeModification.initMobSpawning();

      for (MysticismBiomeModification.MobSpawningTemplate template : MysticismBiomeModification.MOB_SPAWNING) {
         registerSpawn(
            context,
            template.getBiome(),
            template.getType(),
            template.getWeight(),
            template.getMinPack(),
            template.getMaxPack(),
            template.getBudget(),
            template.getCharge()
         );
      }
   }

   private static void addFeatures(BootstrapContext<BiomeModifier> context) {
      HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
      HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
      context.register(
         ICE_ORE,
         new AddFeaturesBiomeModifier(
            biomes.getOrThrow(MysticismBiomeTags.OreSpawn.ICE_SPIKES),
            HolderSet.direct(new Holder[]{placedFeatures.getOrThrow(MysticismPlacedFeatures.ICE_ORE)}),
            Decoration.RAW_GENERATION
         )
      );
   }

   private static ResourceKey<BiomeModifier> registerKey(String name) {
      return ResourceKey.create(Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath("mysticism", name));
   }

   private static void registerSpawn(
      BootstrapContext<BiomeModifier> context, TagKey<Biome> biome, EntityType<?> type, int weight, int min, int max, double budget, double charge
   ) {
      HolderSet<Biome> holderSet = context.lookup(Registries.BIOME).getOrThrow(biome);
      registerSpawn(context, holderSet, type, weight, min, max);
      registerSpawnCost(context, holderSet, type, budget, charge);
   }

   private static void registerSpawn(BootstrapContext<BiomeModifier> context, HolderSet<Biome> biome, EntityType<?> type, int weight, int min, int max) {
      context.register(
         registerKey(BuiltInRegistries.ENTITY_TYPE.getKey(type).getPath() + "_spawn"),
         AddSpawnsBiomeModifier.singleSpawn(biome, new SpawnerData(type, weight, min, max))
      );
   }

   private static void registerSpawnCost(BootstrapContext<BiomeModifier> context, HolderSet<Biome> biome, EntityType<?> type, double budget, double charge) {
      Reference<EntityType<?>> holder = context.lookup(Registries.ENTITY_TYPE)
         .getOrThrow((ResourceKey)BuiltInRegistries.ENTITY_TYPE.getResourceKey(type).get());
      context.register(
         registerKey(BuiltInRegistries.ENTITY_TYPE.getKey(type).getPath() + "_spawn_cost"),
         new AddSpawnCostsBiomeModifier(biome, HolderSet.direct(new Holder[]{holder}), new MobSpawnCost(budget, charge))
      );
   }
}
