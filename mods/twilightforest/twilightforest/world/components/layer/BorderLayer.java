package twilightforest.world.components.layer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.LongFunction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.init.custom.BiomeLayerStack;
import twilightforest.init.custom.BiomeLayerTypes;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerFactory;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerType;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;
import twilightforest.world.components.layer.vanillalegacy.context.LazyAreaContext;
import twilightforest.world.components.layer.vanillalegacy.context.RandomContext;

public record BorderLayer(ResourceKey<Biome> targetBiome, ResourceKey<Biome> borderBiome) implements IThornsTransformer {
   @Override
   public ResourceKey<Biome> apply(
      RandomContext randomContext,
      ResourceKey<Biome> up,
      ResourceKey<Biome> left,
      ResourceKey<Biome> down,
      ResourceKey<Biome> right,
      ResourceKey<Biome> center,
      ResourceKey<Biome> nw,
      ResourceKey<Biome> sw,
      ResourceKey<Biome> se,
      ResourceKey<Biome> ne
   ) {
      if (onBorder(this.targetBiome, center, right, left, up, down)) {
         return this.borderBiome;
      } else {
         return onBorder(this.targetBiome, center, ne, nw, se, sw) ? this.borderBiome : center;
      }
   }

   private static boolean onBorder(
      ResourceKey<Biome> biomeID, ResourceKey<Biome> center, ResourceKey<Biome> right, ResourceKey<Biome> left, ResourceKey<Biome> up, ResourceKey<Biome> down
   ) {
      return center != biomeID && (right == biomeID || left == biomeID || up == biomeID || down == biomeID);
   }

   public static final class Factory implements BiomeLayerFactory {
      public static final MapCodec<BorderLayer.Factory> CODEC = RecordCodecBuilder.mapCodec(
         inst -> inst.group(
               Codec.LONG.fieldOf("salt").forGetter(BorderLayer.Factory::salt),
               ResourceKey.codec(Registries.BIOME).fieldOf("target_biome").forGetter(BorderLayer.Factory::target),
               ResourceKey.codec(Registries.BIOME).fieldOf("bordering_biome").forGetter(BorderLayer.Factory::borderingBiome),
               BiomeLayerStack.HOLDER_CODEC.fieldOf("parent").forGetter(BorderLayer.Factory::parent)
            )
            .apply(inst, BorderLayer.Factory::new)
      );
      private final long salt;
      private final ResourceKey<Biome> target;
      private final ResourceKey<Biome> borderingBiome;
      private final Holder<BiomeLayerFactory> parent;
      private final BorderLayer instance;

      public Factory(long salt, ResourceKey<Biome> centerBiome, ResourceKey<Biome> borderingBiome, Holder<BiomeLayerFactory> parent) {
         this.salt = salt;
         this.target = centerBiome;
         this.borderingBiome = borderingBiome;
         this.parent = parent;
         this.instance = new BorderLayer(centerBiome, borderingBiome);
      }

      @Override
      public LazyArea build(LongFunction<LazyAreaContext> contextFactory) {
         return this.instance.run(contextFactory.apply(this.salt), ((BiomeLayerFactory)this.parent.value()).build(contextFactory));
      }

      @Override
      public BiomeLayerType getType() {
         return (BiomeLayerType)BiomeLayerTypes.BORDER.get();
      }

      public long salt() {
         return this.salt;
      }

      public ResourceKey<Biome> target() {
         return this.target;
      }

      public ResourceKey<Biome> borderingBiome() {
         return this.borderingBiome;
      }

      public Holder<BiomeLayerFactory> parent() {
         return this.parent;
      }
   }
}
