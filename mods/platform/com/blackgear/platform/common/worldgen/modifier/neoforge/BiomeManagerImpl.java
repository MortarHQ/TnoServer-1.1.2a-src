package com.blackgear.platform.common.worldgen.modifier.neoforge;

import com.blackgear.platform.Platform;
import com.blackgear.platform.common.worldgen.modifier.BiomeContext;
import com.blackgear.platform.common.worldgen.modifier.BiomeManager;
import com.blackgear.platform.common.worldgen.modifier.BiomeWriter;
import com.blackgear.platform.core.Environment;
import com.blackgear.platform.core.util.EventBus;
import com.mojang.serialization.MapCodec;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifier.Phase;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import org.jetbrains.annotations.Nullable;

public class BiomeManagerImpl {
   @Nullable
   private static MapCodec<BiomeManagerImpl.PlatformBiomeModifier> codec = null;

   public static void bootstrap() {
      EventBus.get(EventBus.MOD)
         .addListener(
            event -> {
               event.register(
                  Keys.BIOME_MODIFIER_SERIALIZERS,
                  entry -> entry.register(Platform.resource("biome_modifier_codec"), codec = MapCodec.unit(BiomeManagerImpl.PlatformBiomeModifier.INSTANCE))
               );
               event.register(
                  Keys.BIOME_MODIFIERS, entry -> entry.register(Platform.resource("biome_modifier"), BiomeManagerImpl.PlatformBiomeModifier.INSTANCE)
               );
            }
         );
   }

   static class ForgeBiomeWriter extends BiomeWriter {
      private final Holder<Biome> biome;
      private final Builder builder;
      private final Optional<RegistryAccess> registries;

      ForgeBiomeWriter(Holder<Biome> biome, Builder builder) {
         Optional<MinecraftServer> server = Environment.getCurrentServer();
         this.registries = server.map(MinecraftServer::registryAccess);
         this.biome = biome;
         this.builder = builder;
      }

      @Override
      public ResourceLocation name() {
         return ((ResourceKey)this.biome.unwrapKey().orElseThrow()).location();
      }

      @Override
      public BiomeContext context() {
         return new BiomeContext() {
            @Override
            public ResourceKey<Biome> resource() {
               return (ResourceKey<Biome>)ForgeBiomeWriter.this.biome.unwrapKey().orElseThrow();
            }

            @Override
            public Biome biome() {
               return (Biome)ForgeBiomeWriter.this.biome.value();
            }

            @Override
            public boolean is(TagKey<Biome> tag) {
               return ForgeBiomeWriter.this.biome.is(tag);
            }

            @Override
            public boolean is(ResourceKey<Biome> biome) {
               return this.resource() == biome;
            }

            @Override
            public boolean is(Predicate<BiomeContext> context) {
               return context.test(this);
            }

            @Override
            public boolean hasFeature(ResourceKey<PlacedFeature> key) {
               for (HolderSet<PlacedFeature> featureSet : ForgeBiomeWriter.this.builder.getGenerationSettings().build().features()) {
                  for (Holder<PlacedFeature> feature : featureSet) {
                     Optional<ResourceKey<PlacedFeature>> featureKey = feature.unwrapKey();
                     if (featureKey.isPresent() && featureKey.get() == key) {
                        return true;
                     }
                  }
               }

               return false;
            }
         };
      }

      @Override
      public void addFeature(Decoration decoration, ResourceKey<PlacedFeature> key) {
         this.registries.ifPresent(registry -> {
            Registry<PlacedFeature> features = registry.registryOrThrow(Registries.PLACED_FEATURE);
            this.builder.getGenerationSettings().addFeature(decoration, features.getHolderOrThrow(key));
         });
      }

      @Override
      public void removeFeature(Decoration decoration, ResourceKey<PlacedFeature> key) {
         this.registries.ifPresent(registry -> {
            Registry<PlacedFeature> features = registry.registryOrThrow(Registries.PLACED_FEATURE);
            this.builder.getGenerationSettings().getFeatures(decoration).removeIf(holder -> holder.value() == features.getOrThrow(key));
         });
      }

      @Override
      public void addSpawn(MobCategory category, SpawnerData data) {
         this.builder.getMobSpawnSettings().addSpawn(category, data);
      }

      @Override
      public void removeSpawn(EntityType<?> entity) {
         this.builder.getMobSpawnSettings().getSpawner(entity.getCategory()).removeIf(spawner -> spawner.type == entity);
      }

      @Override
      public void addCarver(Carving carving, ResourceKey<ConfiguredWorldCarver<?>> key) {
         this.registries.ifPresent(registry -> {
            Registry<ConfiguredWorldCarver<?>> carvers = registry.registryOrThrow(Registries.CONFIGURED_CARVER);
            this.builder.getGenerationSettings().addCarver(carving, carvers.getHolderOrThrow(key));
         });
      }

      @Override
      public void removeCarver(Carving carving, ResourceKey<ConfiguredWorldCarver<?>> key) {
         this.registries.ifPresent(registry -> {
            Registry<ConfiguredWorldCarver<?>> carvers = registry.registryOrThrow(Registries.CONFIGURED_CARVER);
            this.builder.getGenerationSettings().getCarvers(carving).removeIf(holder -> holder.value() == carvers.getOrThrow(key));
         });
      }
   }

   static class PlatformBiomeModifier implements BiomeModifier {
      private static final BiomeManagerImpl.PlatformBiomeModifier INSTANCE = new BiomeManagerImpl.PlatformBiomeModifier();

      public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
         if (phase == Phase.ADD) {
            BiomeManager.INSTANCE.register(new BiomeManagerImpl.ForgeBiomeWriter(biome, builder));
         }
      }

      public MapCodec<? extends BiomeModifier> codec() {
         return BiomeManagerImpl.codec != null ? BiomeManagerImpl.codec : MapCodec.unit(INSTANCE);
      }
   }
}
