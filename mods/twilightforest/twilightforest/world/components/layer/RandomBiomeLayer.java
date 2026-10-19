package twilightforest.world.components.layer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.LongFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.init.custom.BiomeLayerTypes;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerFactory;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerType;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;
import twilightforest.world.components.layer.vanillalegacy.context.LazyAreaContext;
import twilightforest.world.components.layer.vanillalegacy.context.RandomContext;
import twilightforest.world.components.layer.vanillalegacy.traits.AreaTransformer0;

public class RandomBiomeLayer implements AreaTransformer0 {
   private final int rareBiomeChance;
   private final List<ResourceKey<Biome>> commonBiomes;
   private final List<ResourceKey<Biome>> rareBiomes;

   public RandomBiomeLayer(int rareBiomeChance, List<ResourceKey<Biome>> commonBiomes, List<ResourceKey<Biome>> rareBiomes) {
      this.rareBiomeChance = rareBiomeChance;
      this.commonBiomes = commonBiomes;
      this.rareBiomes = rareBiomes;
   }

   @Override
   public ResourceKey<Biome> applyPixel(RandomContext randomContext, int x, int z) {
      return randomContext.nextRandom(this.rareBiomeChance) == 0
         ? this.rareBiomes.get(randomContext.nextRandom(this.rareBiomes.size()))
         : this.commonBiomes.get(randomContext.nextRandom(this.commonBiomes.size()));
   }

   public static final class Factory implements BiomeLayerFactory {
      public static final MapCodec<RandomBiomeLayer.Factory> CODEC = RecordCodecBuilder.mapCodec(
         inst -> inst.group(
               Codec.LONG.fieldOf("salt").forGetter(RandomBiomeLayer.Factory::salt),
               Codec.INT.fieldOf("rare_biome_chance").forGetter(RandomBiomeLayer.Factory::rareBiomeChance),
               ResourceKey.codec(Registries.BIOME).listOf().fieldOf("common_biomes").forGetter(RandomBiomeLayer.Factory::commonBiomes),
               ResourceKey.codec(Registries.BIOME).listOf().fieldOf("rare_biomes").forGetter(RandomBiomeLayer.Factory::rareBiomes)
            )
            .apply(inst, RandomBiomeLayer.Factory::new)
      );
      private final long salt;
      private final int rareBiomeChance;
      private final List<ResourceKey<Biome>> commonBiomes;
      private final List<ResourceKey<Biome>> rareBiomes;
      private final RandomBiomeLayer instance;

      public Factory(long salt, int rareBiomeChance, List<ResourceKey<Biome>> commonBiomes, List<ResourceKey<Biome>> rareBiomes) {
         this.salt = salt;
         this.rareBiomeChance = rareBiomeChance;
         this.commonBiomes = commonBiomes;
         this.rareBiomes = rareBiomes;
         this.instance = new RandomBiomeLayer(rareBiomeChance, commonBiomes, rareBiomes);
      }

      @Override
      public LazyArea build(LongFunction<LazyAreaContext> contextFactory) {
         return this.instance.run(contextFactory.apply(this.salt));
      }

      @Override
      public BiomeLayerType getType() {
         return (BiomeLayerType)BiomeLayerTypes.RANDOM_BIOMES.get();
      }

      public long salt() {
         return this.salt;
      }

      public int rareBiomeChance() {
         return this.rareBiomeChance;
      }

      public List<ResourceKey<Biome>> commonBiomes() {
         return this.commonBiomes;
      }

      public List<ResourceKey<Biome>> rareBiomes() {
         return this.rareBiomes;
      }
   }
}
