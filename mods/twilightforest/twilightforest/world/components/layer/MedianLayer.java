package twilightforest.world.components.layer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.LongFunction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.init.custom.BiomeLayerStack;
import twilightforest.init.custom.BiomeLayerTypes;
import twilightforest.world.components.layer.vanillalegacy.Area;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerFactory;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerType;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;
import twilightforest.world.components.layer.vanillalegacy.context.LazyAreaContext;
import twilightforest.world.components.layer.vanillalegacy.context.RandomContext;
import twilightforest.world.components.layer.vanillalegacy.traits.AreaTransformer1;

public enum MedianLayer implements AreaTransformer1 {
   INSTANCE;

   @Override
   public ResourceKey<Biome> applyPixel(RandomContext randomContext, Area layer, int x, int z) {
      ResourceKey[] biomes = new ResourceKey[9];

      for (int pos = 0; pos < 9; pos++) {
         biomes[pos] = layer.getBiome(x + pos % 3, z + pos / 3);
      }

      int biomeRecordIndex = 0;
      int biomeRecordCount = 0;

      for (int index = 0; index < 9; index++) {
         int iterationQuantity = 0;

         for (int test = 0; test < 9; test++) {
            if (biomes[index] == biomes[test]) {
               if (++iterationQuantity > 4) {
                  return biomes[index];
               }
            }
         }

         if (biomeRecordCount == iterationQuantity && (index == 5 || biomeRecordIndex != 5 && randomContext.nextRandom(2) == 0)) {
            biomeRecordIndex = index;
         }

         if (biomeRecordCount > iterationQuantity) {
            biomeRecordIndex = index;
            biomeRecordCount = iterationQuantity;
         }
      }

      return biomes[biomeRecordIndex];
   }

   @Override
   public int getParentX(int x) {
      return x;
   }

   @Override
   public int getParentY(int z) {
      return z;
   }

   public record Factory(long salt, Holder<BiomeLayerFactory> parent) implements BiomeLayerFactory {
      public static final MapCodec<MedianLayer.Factory> CODEC = RecordCodecBuilder.mapCodec(
         inst -> inst.group(
               Codec.LONG.fieldOf("salt").forGetter(MedianLayer.Factory::salt),
               BiomeLayerStack.HOLDER_CODEC.fieldOf("parent").forGetter(MedianLayer.Factory::parent)
            )
            .apply(inst, MedianLayer.Factory::new)
      );

      @Override
      public LazyArea build(LongFunction<LazyAreaContext> contextFactory) {
         return MedianLayer.INSTANCE.run(contextFactory.apply(this.salt), ((BiomeLayerFactory)this.parent.value()).build(contextFactory));
      }

      @Override
      public BiomeLayerType getType() {
         return (BiomeLayerType)BiomeLayerTypes.MEDIAN.get();
      }
   }
}
