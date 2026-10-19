package com.blackgear.platform.common.worldgen.modifier;

import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList.Preset;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public interface BiomeContext {
   Predicate<BiomeContext> OVERWORLD_BIOME = context -> Preset.OVERWORLD.usedBiomes().anyMatch(context::is);

   ResourceKey<Biome> resource();

   Biome biome();

   boolean is(TagKey<Biome> var1);

   boolean is(ResourceKey<Biome> var1);

   boolean is(Predicate<BiomeContext> var1);

   boolean hasFeature(ResourceKey<PlacedFeature> var1);

   default boolean hasEntity(Supplier<EntityType<?>> entities) {
      return this.hasEntity(ImmutableSet.of(entities));
   }

   default boolean hasEntity(Set<Supplier<EntityType<?>>> entitySet) {
      Set<EntityType<?>> entities = entitySet.stream().map(Supplier::get).collect(Collectors.toSet());
      MobSpawnSettings settings = this.biome().getMobSettings();
      return Arrays.stream(MobCategory.values())
         .flatMap(category -> settings.getMobs(category).unwrap().stream())
         .anyMatch(spawner -> entities.contains(spawner.type));
   }
}
