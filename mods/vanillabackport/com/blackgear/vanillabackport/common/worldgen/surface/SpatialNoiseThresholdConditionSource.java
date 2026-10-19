package com.blackgear.vanillabackport.common.worldgen.surface;

import com.blackgear.vanillabackport.common.api.worldgen.ContextNoiseSampler;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.DoubleSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules.Condition;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraft.world.level.levelgen.SurfaceRules.Context;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class SpatialNoiseThresholdConditionSource implements ConditionSource {
   private static final MapCodec<SpatialNoiseThresholdConditionSource> MAP_CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            ResourceKey.codec(Registries.NOISE).fieldOf("noise").forGetter(condition -> condition.noise),
            Codec.DOUBLE.fieldOf("min_threshold").forGetter(condition -> condition.minThreshold),
            Codec.DOUBLE.fieldOf("max_threshold").forGetter(condition -> condition.maxThreshold)
         )
         .apply(instance, SpatialNoiseThresholdConditionSource::new)
   );
   public static final KeyDispatchDataCodec<SpatialNoiseThresholdConditionSource> CODEC = KeyDispatchDataCodec.of(MAP_CODEC);
   private final ResourceKey<NoiseParameters> noise;
   private final double minThreshold;
   private final double maxThreshold;

   public SpatialNoiseThresholdConditionSource(ResourceKey<NoiseParameters> noise, double minThreshold, double maxThreshold) {
      this.noise = noise;
      this.minThreshold = minThreshold;
      this.maxThreshold = maxThreshold;
   }

   public KeyDispatchDataCodec<? extends ConditionSource> codec() {
      return CODEC;
   }

   public Condition apply(Context context) {
      DoubleSupplier noise = ((ContextNoiseSampler)context).getNoiseSampler3D(this.noise);
      return new SpatialNoiseThresholdConditionSource.NoiseThresholdCondition(this, noise);
   }

   private record NoiseThresholdCondition(SpatialNoiseThresholdConditionSource source, DoubleSupplier noise) implements Condition {
      public boolean test() {
         double value = this.noise.getAsDouble();
         return value >= this.source.minThreshold && value <= this.source.maxThreshold;
      }
   }
}
