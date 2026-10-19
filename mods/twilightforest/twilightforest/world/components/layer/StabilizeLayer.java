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

public enum StabilizeLayer implements AreaTransformer1 {
   INSTANCE;

   @Override
   public int getParentX(int x) {
      return x & 3;
   }

   @Override
   public int getParentY(int z) {
      return z & 3;
   }

   @Override
   public ResourceKey<Biome> applyPixel(RandomContext randomContext, Area layer, int x, int z) {
      int offX = this.getParentX(x << 4);
      int offZ = this.getParentY(z << 4);
      int centerX = (x + offX + 1 & -4) - offX;
      int centerZ = (z + offZ + 1 & -4) - offZ;
      return x <= centerX + 1 && x >= centerX - 1 && z <= centerZ + 1 && z >= centerZ - 1 ? layer.getBiome(centerX, centerZ) : layer.getBiome(x, z);
   }

   public record Factory(long salt, Holder<BiomeLayerFactory> parent) implements BiomeLayerFactory {
      public static final MapCodec<StabilizeLayer.Factory> CODEC = RecordCodecBuilder.mapCodec(
         inst -> inst.group(
               Codec.LONG.fieldOf("salt").forGetter(StabilizeLayer.Factory::salt),
               BiomeLayerStack.HOLDER_CODEC.fieldOf("parent").forGetter(StabilizeLayer.Factory::parent)
            )
            .apply(inst, StabilizeLayer.Factory::new)
      );

      @Override
      public LazyArea build(LongFunction<LazyAreaContext> contextFactory) {
         return StabilizeLayer.INSTANCE.run(contextFactory.apply(this.salt), ((BiomeLayerFactory)this.parent.value()).build(contextFactory));
      }

      @Override
      public BiomeLayerType getType() {
         return (BiomeLayerType)BiomeLayerTypes.STABILIZE.get();
      }
   }
}
