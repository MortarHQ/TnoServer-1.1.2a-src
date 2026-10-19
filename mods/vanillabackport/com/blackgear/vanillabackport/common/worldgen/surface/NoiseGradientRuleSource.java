package com.blackgear.vanillabackport.common.worldgen.surface;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules.Context;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.SurfaceRules.SurfaceRule;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import org.jetbrains.annotations.Nullable;

public record NoiseGradientRuleSource(ResourceKey<NoiseParameters> noise, List<Optional<BlockState>> gradient) implements RuleSource {
   private static final Codec<Optional<BlockState>> OPTIONAL_STATE_CODEC = BlockState.CODEC.optionalFieldOf("state").codec();
   private static final MapCodec<NoiseGradientRuleSource> MAP_CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            ResourceKey.codec(Registries.NOISE).fieldOf("noise").forGetter(NoiseGradientRuleSource::noise),
            ExtraCodecs.nonEmptyList(OPTIONAL_STATE_CODEC.listOf()).fieldOf("gradient").forGetter(NoiseGradientRuleSource::gradient)
         )
         .apply(instance, NoiseGradientRuleSource::new)
   );
   public static final KeyDispatchDataCodec<NoiseGradientRuleSource> CODEC = KeyDispatchDataCodec.of(MAP_CODEC);

   public KeyDispatchDataCodec<? extends RuleSource> codec() {
      return CODEC;
   }

   public SurfaceRule apply(Context context) {
      NormalNoise noise = context.randomState.getOrCreateNoise(this.noise);
      return new NoiseGradientRuleSource.NoiseGradientRule(noise, this.gradient.stream().map(state -> state.orElse(null)).toList());
   }

   private record NoiseGradientRule(NormalNoise noise, List<BlockState> gradient) implements SurfaceRule {
      @Nullable
      public BlockState tryApply(int x, int y, int z) {
         double noiseValue = this.noise.getValue(x, y, z);
         double normalizedNoiseValue = (noiseValue + 1.0) / 2.0;
         int index = Mth.floor(normalizedNoiseValue * this.gradient.size());
         return this.gradient.get(Mth.clamp(index, 0, this.gradient.size() - 1));
      }
   }
}
