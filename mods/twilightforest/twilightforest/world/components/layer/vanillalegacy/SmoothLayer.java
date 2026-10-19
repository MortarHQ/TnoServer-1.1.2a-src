package twilightforest.world.components.layer.vanillalegacy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.LongFunction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.init.custom.BiomeLayerStack;
import twilightforest.init.custom.BiomeLayerTypes;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;
import twilightforest.world.components.layer.vanillalegacy.context.LazyAreaContext;
import twilightforest.world.components.layer.vanillalegacy.context.RandomContext;
import twilightforest.world.components.layer.vanillalegacy.traits.CastleTransformer;

public enum SmoothLayer implements CastleTransformer {
   INSTANCE;

   @Override
   public ResourceKey<Biome> apply(
      RandomContext randomContext, ResourceKey<Biome> up, ResourceKey<Biome> right, ResourceKey<Biome> down, ResourceKey<Biome> left, ResourceKey<Biome> center
   ) {
      boolean xMatch = right == left;
      boolean zMatch = up == down;
      if (xMatch == zMatch) {
         if (xMatch) {
            return randomContext.nextRandom(2) == 0 ? left : up;
         } else {
            return center;
         }
      } else {
         return xMatch ? left : up;
      }
   }

   public record Factory(long salt, Holder<BiomeLayerFactory> parent) implements BiomeLayerFactory {
      public static final MapCodec<SmoothLayer.Factory> CODEC = RecordCodecBuilder.mapCodec(
         inst -> inst.group(
               Codec.LONG.fieldOf("salt").forGetter(SmoothLayer.Factory::salt),
               BiomeLayerStack.HOLDER_CODEC.fieldOf("parent").forGetter(SmoothLayer.Factory::parent)
            )
            .apply(inst, SmoothLayer.Factory::new)
      );

      @Override
      public LazyArea build(LongFunction<LazyAreaContext> contextFactory) {
         return SmoothLayer.INSTANCE.run(contextFactory.apply(this.salt), ((BiomeLayerFactory)this.parent.value()).build(contextFactory));
      }

      @Override
      public BiomeLayerType getType() {
         return (BiomeLayerType)BiomeLayerTypes.SMOOTH.get();
      }
   }
}
