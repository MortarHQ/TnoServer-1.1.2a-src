package twilightforest.world.components.chunkgenerators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import net.minecraft.world.level.levelgen.DensityFunction.SimpleFunction;
import net.minecraft.world.level.levelgen.DensityFunction.Visitor;
import org.jetbrains.annotations.NotNull;
import twilightforest.TFRegistries;
import twilightforest.world.components.layer.BiomeDensitySource;

public class NoiseDensityRouter implements SimpleFunction {
   public static final MapCodec<NoiseDensityRouter> CODEC = RecordCodecBuilder.mapCodec(
      inst -> inst.group(
            RegistryFileCodec.create(TFRegistries.Keys.BIOME_TERRAIN_DATA, BiomeDensitySource.CODEC, false)
               .fieldOf("terrain_source")
               .forGetter(NoiseDensityRouter::biomeDensitySourceHolder),
            Codec.doubleRange(-64.0, 0.0).fieldOf("lower_density_bound").forGetter(NoiseDensityRouter::lowerDensityBound),
            Codec.doubleRange(0.0, 64.0).fieldOf("upper_density_bound").forGetter(NoiseDensityRouter::upperDensityBound),
            Codec.doubleRange(0.0, 32.0).orElse(8.0).fieldOf("depth_scalar").forGetter(NoiseDensityRouter::depthScalar)
         )
         .apply(inst, NoiseDensityRouter::new)
   );
   public static final KeyDispatchDataCodec<NoiseDensityRouter> KEY_CODEC = KeyDispatchDataCodec.of(CODEC);
   private final Holder<BiomeDensitySource> biomeDensitySourceHolder;
   private final double lowerDensityBound;
   private final double upperDensityBound;
   private final double depthScalar;

   public NoiseDensityRouter(Holder<BiomeDensitySource> biomeDensitySource, double lowerDensityBound, double upperDensityBound, double depthScalar) {
      this.biomeDensitySourceHolder = biomeDensitySource;
      this.lowerDensityBound = lowerDensityBound;
      this.upperDensityBound = upperDensityBound;
      this.depthScalar = depthScalar;
   }

   public double compute(FunctionContext context) {
      return this.computeTerrain(context).scale;
   }

   @NotNull
   public BiomeDensitySource.DensityData computeTerrain(FunctionContext context) {
      return ((BiomeDensitySource)this.biomeDensitySourceHolder.value()).sampleTerrain(context.blockX(), context.blockZ(), context);
   }

   public double minValue() {
      return this.lowerDensityBound;
   }

   public double maxValue() {
      return this.upperDensityBound;
   }

   public KeyDispatchDataCodec<? extends DensityFunction> codec() {
      return KEY_CODEC;
   }

   public Holder<BiomeDensitySource> biomeDensitySourceHolder() {
      return this.biomeDensitySourceHolder;
   }

   public double lowerDensityBound() {
      return this.lowerDensityBound;
   }

   public double upperDensityBound() {
      return this.upperDensityBound;
   }

   public double depthScalar() {
      return this.depthScalar;
   }

   public DensityFunction mapAll(Visitor visitor) {
      return visitor.apply(
         new NoiseDensityRouter.ChunkCachedNoiseDensityRouter(this.biomeDensitySourceHolder, this.lowerDensityBound, this.upperDensityBound, this.depthScalar)
      );
   }

   public static class ChunkCachedNoiseDensityRouter extends NoiseDensityRouter {
      private final BiomeDensitySource biomeDensitySource;
      private final BiomeDensitySource.DensityData[] horizontalCache = new BiomeDensitySource.DensityData[256];

      public ChunkCachedNoiseDensityRouter(
         Holder<BiomeDensitySource> biomeDensitySource, double lowerDensityBound, double upperDensityBound, double depthScalar
      ) {
         super(biomeDensitySource, lowerDensityBound, upperDensityBound, depthScalar);
         this.biomeDensitySource = (BiomeDensitySource)biomeDensitySource.value();
      }

      @NotNull
      @Override
      public BiomeDensitySource.DensityData computeTerrain(FunctionContext context) {
         int xInChunk = SectionPos.sectionRelative(context.blockX());
         int zInChunk = SectionPos.sectionRelative(context.blockZ());
         int arrayCoord = zInChunk + (xInChunk << 4);
         BiomeDensitySource.DensityData dataColumn = this.horizontalCache[arrayCoord];
         if (dataColumn == null) {
            dataColumn = this.biomeDensitySource.sampleTerrain(context.blockX(), context.blockZ(), context);
            this.horizontalCache[arrayCoord] = dataColumn;
         }

         return dataColumn;
      }
   }
}
