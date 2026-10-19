package com.blackgear.vanillabackport.common.worldgen.generation;

import com.blackgear.platform.common.worldgen.modifier.BiomeContext;
import com.blackgear.platform.common.worldgen.modifier.BiomeWriter;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public abstract class FeatureManager {
   private final BiomeContext context;
   private final BiomeWriter writer;

   public FeatureManager(BiomeContext context, BiomeWriter writer) {
      this.context = context;
      this.writer = writer;
   }

   public abstract void bootstrap();

   protected FeatureManager.Builder getOrCreateBiomeBuilder(boolean filter) {
      return new FeatureManager.Builder(this.context, ctx -> filter);
   }

   protected FeatureManager.Builder getOrCreateBiomeBuilder(ResourceKey<Biome> biome) {
      return new FeatureManager.Builder(this.context, ctx -> ctx.is(biome));
   }

   protected FeatureManager.Builder getOrCreateBiomeBuilder(TagKey<Biome> biome) {
      return new FeatureManager.Builder(this.context, ctx -> ctx.is(biome));
   }

   protected FeatureManager.Builder getOrCreateBiomeBuilder(Predicate<BiomeContext> context) {
      return new FeatureManager.Builder(this.context, ctx -> ctx.is(context));
   }

   protected void add(BiConsumer<BiomeContext, BiomeWriter> feature) {
      feature.accept(this.context, this.writer);
   }

   protected void addIf(boolean filter, BiConsumer<BiomeContext, BiomeWriter> feature) {
      if (filter) {
         this.add(feature);
      }
   }

   protected void addIf(Predicate<BiomeContext> filter, BiConsumer<BiomeContext, BiomeWriter> feature) {
      this.addIf(filter.test(this.context), feature);
   }

   protected void addVegetation(ResourceKey<PlacedFeature> feature) {
      this.writer.addFeature(Decoration.VEGETAL_DECORATION, feature);
   }

   public class Builder {
      private final BiomeContext context;
      private final Predicate<BiomeContext> filter;

      public Builder(BiomeContext context, Predicate<BiomeContext> filter) {
         this.context = context;
         this.filter = filter;
      }

      public FeatureManager.Builder add(Runnable runnable) {
         if (this.filter.test(this.context)) {
            runnable.run();
         }

         return this;
      }

      public FeatureManager.Builder addIf(BiConsumer<BiomeContext, BiomeWriter> feature) {
         if (this.filter.test(this.context)) {
            FeatureManager.this.add(feature);
         }

         return this;
      }
   }
}
