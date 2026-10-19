package com.blackgear.platform.common.worldgen.modifier;

import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public abstract class BiomeWriter {
   public void add(BiConsumer<BiomeWriter, BiomeContext> modifier) {
      modifier.accept(this, this.context());
   }

   public abstract ResourceLocation name();

   public abstract BiomeContext context();

   public abstract void addFeature(Decoration var1, ResourceKey<PlacedFeature> var2);

   public abstract void removeFeature(Decoration var1, ResourceKey<PlacedFeature> var2);

   public void replaceFeature(Decoration decoration, ResourceKey<PlacedFeature> feature, ResourceKey<PlacedFeature> replacement) {
      if (this.context().hasFeature(feature)) {
         this.removeFeature(decoration, feature);
         this.addFeature(decoration, replacement);
      }
   }

   public abstract void addCarver(Carving var1, ResourceKey<ConfiguredWorldCarver<?>> var2);

   public abstract void removeCarver(Carving var1, ResourceKey<ConfiguredWorldCarver<?>> var2);

   public abstract void addSpawn(MobCategory var1, SpawnerData var2);

   public abstract void removeSpawn(EntityType<?> var1);

   public void replaceSpawn(MobCategory category, SpawnerData data) {
      if (this.context().hasEntity(() -> data.type)) {
         this.removeSpawn(data.type);
         this.addSpawn(category, data);
      }
   }
}
