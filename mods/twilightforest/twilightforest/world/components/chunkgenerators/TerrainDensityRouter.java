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

public class TerrainDensityRouter implements SimpleFunction {
   public static final MapCodec<TerrainDensityRouter> CODEC = RecordCodecBuilder.mapCodec(
      inst -> inst.group(
            RegistryFileCodec.create(TFRegistries.Keys.BIOME_TERRAIN_DATA, BiomeDensitySource.CODEC, false)
               .fieldOf("terrain_source")
               .forGetter(TerrainDensityRouter::biomeDensitySourceHolder),
            Codec.doubleRange(-64.0, 0.0).fieldOf("lower_density_bound").forGetter(TerrainDensityRouter::lowerDensityBound),
            Codec.doubleRange(0.0, 64.0).fieldOf("upper_density_bound").forGetter(TerrainDensityRouter::upperDensityBound),
            Codec.doubleRange(0.0, 32.0).orElse(8.0).fieldOf("depth_scalar").forGetter(TerrainDensityRouter::depthScalar),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("base_factor").forGetter(TerrainDensityRouter::baseFactor),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("base_offset").forGetter(TerrainDensityRouter::baseOffset)
         )
         .apply(inst, TerrainDensityRouter::new)
   );
   public static final KeyDispatchDataCodec<TerrainDensityRouter> KEY_CODEC = KeyDispatchDataCodec.of(CODEC);
   private final Holder<BiomeDensitySource> biomeDensitySourceHolder;
   private final double lowerDensityBound;
   private final double upperDensityBound;
   private final double depthScalar;
   private final DensityFunction baseFactor;
   private final DensityFunction baseOffset;

   public TerrainDensityRouter(
      Holder<BiomeDensitySource> biomeDensitySource,
      double lowerDensityBound,
      double upperDensityBound,
      double depthScalar,
      DensityFunction baseFactor,
      DensityFunction baseOffset
   ) {
      this.biomeDensitySourceHolder = biomeDensitySource;
      this.lowerDensityBound = lowerDensityBound;
      this.upperDensityBound = upperDensityBound;
      this.depthScalar = depthScalar;
      this.baseFactor = baseFactor;
      this.baseOffset = baseOffset;
   }

   public double compute(FunctionContext context) {
      BiomeDensitySource.DensityData densityData = this.computeTerrain(context);
      double depth = this.baseOffset.compute(context) + densityData.depth * this.baseFactor.compute(context);
      return depth + densityData.depth;
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

   public DensityFunction baseFactor() {
      return this.baseFactor;
   }

   public DensityFunction baseOffset() {
      return this.baseOffset;
   }

   public DensityFunction mapAll(Visitor visitor) {
      return visitor.apply(
         new TerrainDensityRouter.ChunkCachedDensityRouter(
            this.biomeDensitySourceHolder, this.lowerDensityBound, this.upperDensityBound, this.depthScalar, this.baseFactor, this.baseOffset
         )
      );
   }

   public static class ChunkCachedDensityRouter extends TerrainDensityRouter {
      private final BiomeDensitySource biomeDensitySource;
      private final BiomeDensitySource.DensityData[] horizontalCache = new BiomeDensitySource.DensityData[256];

      public ChunkCachedDensityRouter(
         Holder<BiomeDensitySource> biomeDensitySource,
         double lowerDensityBound,
         double upperDensityBound,
         double depthScalar,
         DensityFunction baseFactor,
         DensityFunction baseOffset
      ) {
         super(biomeDensitySource, lowerDensityBound, upperDensityBound, depthScalar, baseFactor, baseOffset);
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
