package net.jadenxgamer.elysium_api.api.surface_rules.condition;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules.Condition;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraft.world.level.levelgen.SurfaceRules.Context;
import net.minecraft.world.level.levelgen.SurfaceRules.LazyYCondition;

public record BiomeTagConditionSource(TagKey<Biome> biomeTag) implements ConditionSource {
   public static final KeyDispatchDataCodec<BiomeTagConditionSource> CODEC = KeyDispatchDataCodec.of(
      RecordCodecBuilder.mapCodec(
         instance -> instance.group(TagKey.hashedCodec(Registries.BIOME).fieldOf("biome_tag").forGetter(BiomeTagConditionSource::biomeTag))
            .apply(instance, BiomeTagConditionSource::new)
      )
   );

   public KeyDispatchDataCodec<? extends ConditionSource> codec() {
      return CODEC;
   }

   public Condition apply(final Context p_context) {
      class BiomeCondition extends LazyYCondition {
         BiomeCondition() {
            super(p_context);
         }

         protected boolean compute() {
            Holder<Biome> biome = (Holder<Biome>)this.context.biome.get();
            return biome.is(BiomeTagConditionSource.this.biomeTag);
         }
      }

      return new BiomeCondition();
   }

   @Override
   public String toString() {
      return "BiomeTagConditionSource[biomeTag=" + this.biomeTag + "]";
   }
}
