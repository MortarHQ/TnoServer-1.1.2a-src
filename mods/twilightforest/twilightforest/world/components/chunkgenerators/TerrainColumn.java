package twilightforest.world.components.chunkgenerators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.doubles.Double2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectSortedMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap.Entry;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import twilightforest.util.Codecs;

public final class TerrainColumn {
   public static final Codec<TerrainColumn> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            RegistryFixedCodec.create(Registries.BIOME).fieldOf("key_biome").forGetter(o -> o.keyBiome),
            Codecs.doubleTreeCodec(Biome.CODEC).fieldOf("biome_layers").forGetter(o -> o.biomes),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("depth").forGetter(o -> o.noiseDepth),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("scale").forGetter(o -> o.noiseScale),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("weight").forGetter(o -> o.noiseWeight)
         )
         .apply(instance, TerrainColumn::new)
   );
   private final ResourceKey<Biome> resourceKey;
   private final Holder<Biome> keyBiome;
   private final Double2ObjectSortedMap<Holder<Biome>> biomes;
   private final DensityFunction noiseDepth;
   private final DensityFunction noiseScale;
   private final DensityFunction noiseWeight;

   public TerrainColumn(
      Holder<Biome> keyBiome, Double2ObjectSortedMap<Holder<Biome>> biomes, DensityFunction noiseDepth, DensityFunction noiseScale, DensityFunction noiseWeight
   ) {
      this.keyBiome = keyBiome;
      this.resourceKey = (ResourceKey<Biome>)this.keyBiome.unwrapKey().get();
      this.biomes = biomes;
      this.noiseDepth = noiseDepth;
      this.noiseScale = noiseScale;
      this.noiseWeight = noiseWeight;
      if (this.biomes instanceof Double2ObjectAVLTreeMap<Holder<Biome>> treeMap) {
         treeMap.defaultReturnValue(this.keyBiome);
      }
   }

   public Stream<Holder<Biome>> getBiomes() {
      return this.biomes.double2ObjectEntrySet().stream().map(java.util.Map.Entry::getValue);
   }

   public void getBiomesDebug(Consumer<String> accumulator) {
      this.biomes
         .double2ObjectEntrySet()
         .stream()
         .map(
            e -> e.getDoubleKey()
               + ": "
               + ((Holder)e.getValue()).unwrapKey().map(ResourceKey::location).map(ResourceLocation::toString).orElse("NOT REFERENCED")
         )
         .forEach(accumulator);
   }

   public boolean is(Holder<Biome> biome) {
      return ((Biome)this.keyBiome.value()).equals(biome.value());
   }

   public boolean is(ResourceKey<Biome> biome) {
      return this.keyBiome.is(biome);
   }

   public Holder<Biome> getMainBiome() {
      return this.keyBiome;
   }

   public Holder<Biome> getBiome(int biomeElevationQuartile) {
      return this.reduce((a, b) -> {
         double aDelta = a.getDoubleKey() - biomeElevationQuartile;
         double bDelta = b.getDoubleKey() - biomeElevationQuartile;
         return Math.abs(aDelta) <= Math.abs(bDelta) ? a : b;
      }, this.keyBiome);
   }

   private Holder<Biome> reduce(BinaryOperator<Entry<Holder<Biome>>> reducer, Holder<Biome> other) {
      return this.biomes.double2ObjectEntrySet().stream().reduce(reducer).map(java.util.Map.Entry::getValue).orElse(other);
   }

   public double depth(FunctionContext context) {
      return this.noiseDepth.compute(context);
   }

   public double scale(FunctionContext context) {
      return this.noiseScale.compute(context);
   }

   public double weight(FunctionContext context) {
      return this.noiseWeight.compute(context);
   }

   public ResourceKey<Biome> getResourceKey() {
      return this.resourceKey;
   }
}
