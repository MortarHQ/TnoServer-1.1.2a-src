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
import twilightforest.world.components.layer.vanillalegacy.traits.AreaTransformer1;

public enum ZoomLayer implements AreaTransformer1 {
   NORMAL,
   FUZZY {
      @Override
      protected ResourceKey<Biome> modeOrRandom(
         RandomContext randomContext, ResourceKey<Biome> p_76980_, ResourceKey<Biome> p_76981_, ResourceKey<Biome> p_76982_, ResourceKey<Biome> p_76983_
      ) {
         return randomContext.random(p_76980_, p_76981_, p_76982_, p_76983_);
      }
   };

   private static final int ZOOM_BITS = 1;
   private static final int ZOOM_MASK = 1;

   @Override
   public int getParentX(int x) {
      return x >> 1;
   }

   @Override
   public int getParentY(int z) {
      return z >> 1;
   }

   @Override
   public ResourceKey<Biome> applyPixel(RandomContext randomContext, Area layer, int x, int z) {
      ResourceKey<Biome> i = layer.getBiome(this.getParentX(x), this.getParentY(z));
      randomContext.initRandom(x >> 1 << 1, z >> 1 << 1);
      int j = x & 1;
      int k = z & 1;
      if (j == 0 && k == 0) {
         return i;
      }

      ResourceKey<Biome> l = layer.getBiome(this.getParentX(x), this.getParentY(z + 1));
      ResourceKey<Biome> i1 = randomContext.random(i, l);
      if (j == 0 && k == 1) {
         return i1;
      }

      ResourceKey<Biome> j1 = layer.getBiome(this.getParentX(x + 1), this.getParentY(z));
      ResourceKey<Biome> k1 = randomContext.random(i, j1);
      if (j == 1 && k == 0) {
         return k1;
      }

      ResourceKey<Biome> l1 = layer.getBiome(this.getParentX(x + 1), this.getParentY(z + 1));
      return this.modeOrRandom(randomContext, i, j1, l, l1);
   }

   protected ResourceKey<Biome> modeOrRandom(
      RandomContext randomContext, ResourceKey<Biome> p_76961_, ResourceKey<Biome> p_76962_, ResourceKey<Biome> p_76963_, ResourceKey<Biome> p_76964_
   ) {
      if (p_76962_ == p_76963_ && p_76963_ == p_76964_) {
         return p_76962_;
      } else if (p_76961_ == p_76962_ && p_76961_ == p_76963_) {
         return p_76961_;
      } else if (p_76961_ == p_76962_ && p_76961_ == p_76964_) {
         return p_76961_;
      } else if (p_76961_ == p_76963_ && p_76961_ == p_76964_) {
         return p_76961_;
      } else if (p_76961_ == p_76962_ && p_76963_ != p_76964_) {
         return p_76961_;
      } else if (p_76961_ == p_76963_ && p_76962_ != p_76964_) {
         return p_76961_;
      } else if (p_76961_ == p_76964_ && p_76962_ != p_76963_) {
         return p_76961_;
      } else if (p_76962_ == p_76963_ && p_76961_ != p_76964_) {
         return p_76962_;
      } else if (p_76962_ == p_76964_ && p_76961_ != p_76963_) {
         return p_76962_;
      } else {
         return p_76963_ == p_76964_ && p_76961_ != p_76962_ ? p_76963_ : randomContext.random(p_76961_, p_76962_, p_76963_, p_76964_);
      }
   }

   public record Factory(long salt, boolean fuzzy, Holder<BiomeLayerFactory> parent) implements BiomeLayerFactory {
      public static final MapCodec<ZoomLayer.Factory> CODEC = RecordCodecBuilder.mapCodec(
         inst -> inst.group(
               Codec.LONG.fieldOf("salt").forGetter(ZoomLayer.Factory::salt),
               Codec.BOOL.fieldOf("fuzzy").forGetter(ZoomLayer.Factory::fuzzy),
               BiomeLayerStack.HOLDER_CODEC.fieldOf("parent").forGetter(ZoomLayer.Factory::parent)
            )
            .apply(inst, ZoomLayer.Factory::new)
      );

      @Override
      public LazyArea build(LongFunction<LazyAreaContext> contextFactory) {
         LazyAreaContext seededContext = contextFactory.apply(this.salt);
         LazyArea parentLayer = ((BiomeLayerFactory)this.parent.value()).build(contextFactory);
         return this.fuzzy ? ZoomLayer.FUZZY.run(seededContext, parentLayer) : ZoomLayer.NORMAL.run(seededContext, parentLayer);
      }

      @Override
      public BiomeLayerType getType() {
         return (BiomeLayerType)BiomeLayerTypes.ZOOM.get();
      }
   }
}
