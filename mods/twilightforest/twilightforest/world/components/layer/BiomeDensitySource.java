package twilightforest.world.components.layer;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import org.jetbrains.annotations.NotNull;
import twilightforest.init.custom.BiomeLayerStack;
import twilightforest.world.components.chunkgenerators.TerrainColumn;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerFactory;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;
import twilightforest.world.components.layer.vanillalegacy.context.LazyAreaContext;

public class BiomeDensitySource {
   public static final Codec<BiomeDensitySource> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            TerrainColumn.CODEC
               .listOf()
               .fieldOf("biome_landscape")
               .xmap(
                  l -> l.stream().collect(Collectors.toMap(TerrainColumn::getResourceKey, Function.identity())),
                  m -> m.values().stream().sorted(Comparator.comparing(TerrainColumn::getResourceKey)).toList()
               )
               .forGetter(o -> o.biomeList),
            BiomeLayerStack.HOLDER_CODEC.fieldOf("biome_layer_config").forGetter(BiomeDensitySource::getBiomeConfig)
         )
         .apply(instance, instance.stable(BiomeDensitySource::new))
   );
   private final Map<ResourceKey<Biome>, TerrainColumn> biomeList;
   private final Holder<BiomeLayerFactory> genBiomeConfig;
   private final Supplier<LazyArea> genBiomes;
   private static final double BLEND_RADIUS = 8.75;
   private static final int BLEND_RADIUS_INT = Mth.floor(9.75);
   private static final int BLOCK_XYZ_OFFSET = 2;

   public BiomeDensitySource(List<TerrainColumn> list, Holder<BiomeLayerFactory> biomeLayerFactory) {
      this(list.stream().collect(Collectors.toMap(TerrainColumn::getResourceKey, Function.identity())), biomeLayerFactory);
   }

   public BiomeDensitySource(Map<ResourceKey<Biome>, TerrainColumn> list, Holder<BiomeLayerFactory> biomeLayerFactory) {
      this.genBiomeConfig = biomeLayerFactory;
      this.genBiomes = Suppliers.memoize(() -> ((BiomeLayerFactory)this.genBiomeConfig.value()).build(salt -> new LazyAreaContext(25, salt)));
      this.biomeList = list;
   }

   private Holder<BiomeLayerFactory> getBiomeConfig() {
      return this.genBiomeConfig;
   }

   @NotNull
   public Holder<Biome> getBiomeColumnKey(int biomeX, int biomeZ) {
      return this.biomeList.get(this.genBiomes.get().getBiome(biomeX, biomeZ)).getMainBiome();
   }

   public Holder<Biome> getNoiseBiome(int biomeX, int biomeY, int biomeZ) {
      return this.biomeList.get(this.genBiomes.get().getBiome(biomeX, biomeZ)).getBiome(biomeY);
   }

   public Optional<TerrainColumn> getTerrainColumn(int biomeX, int biomeZ) {
      return this.getTerrainColumn(this.genBiomes.get().getBiome(biomeX, biomeZ));
   }

   public Optional<TerrainColumn> getTerrainColumn(ResourceKey<Biome> biome) {
      return Optional.ofNullable(this.biomeList.get(biome));
   }

   public Stream<Holder<Biome>> collectPossibleBiomes() {
      return this.biomeList.values().stream().flatMap(TerrainColumn::getBiomes);
   }

   public void addDebugInfo(List<String> info, BlockPos cameraPos) {
      ResourceKey<Biome> biomeKey = this.genBiomes.get().getBiome(cameraPos.getX() >> 2, cameraPos.getZ() >> 2);
      TerrainColumn biomeColumn = this.biomeList.get(biomeKey);
      Holder<Biome> biomeAtY = biomeColumn.getBiome(cameraPos.getY() >> 2);
      info.add("BiomeDensitySource at " + cameraPos + ":");
      info.add("Twilight Biome Column:");
      biomeColumn.getBiomesDebug(info::add);
      info.add("Primary Biome: " + biomeKey.location());
      info.add("Biome at elevation: " + biomeAtY.unwrapKey().map(ResourceKey::location).<String>map(ResourceLocation::toString).orElse("NOT REFERENCED"));
   }

   public BiomeDensitySource.DensityData sampleTerrain(int blockX, int blockZ, FunctionContext context) {
      double totalMappedDepth = 0.0;
      double totalContribution = 0.0;
      double totalScale = 0.0;
      double totalScaleContribution = 0.0;
      int blockXWithOffset = blockX - 2;
      int blockZWithOffset = blockZ - 2;
      int xQuartStart = blockXWithOffset - BLEND_RADIUS_INT >> 2;
      int zQuartStart = blockZWithOffset - BLEND_RADIUS_INT >> 2;
      int xQuartEnd = blockXWithOffset + BLEND_RADIUS_INT >> 2;
      int zQuartEnd = blockZWithOffset + BLEND_RADIUS_INT >> 2;
      int xCount = xQuartEnd - xQuartStart + 1;
      int zCount = zQuartEnd - zQuartStart + 1;
      double xQuartDelta = (blockXWithOffset - (xQuartStart << 2)) * 0.25;
      double zQuartDelta = (blockZWithOffset - (zQuartStart << 2)) * 0.25;
      int cz = 0;
      int cx = 0;

      while (true) {
         double dX = xQuartDelta - cx;
         double dZ = zQuartDelta - cz;
         double distSq = dX * dX + dZ * dZ;
         if (distSq < 76.5625) {
            Optional<TerrainColumn> terrainColumn = this.getTerrainColumn(cx + xQuartStart, cz + zQuartStart);
            if (terrainColumn.isPresent()) {
               double falloff = 76.5625 * terrainColumn.get().weight(context);
               double scaleFalloff = 76.5625 * terrainColumn.get().weight(context);
               double neighborDepth = terrainColumn.get().depth(context);
               double neighborScale = terrainColumn.get().scale(context);
               falloff *= Math.exp((distSq * 2.0 + neighborDepth) * -0.4F);
               totalMappedDepth += neighborDepth * falloff;
               totalContribution += falloff;
               scaleFalloff *= Math.exp((distSq * 2.0 + neighborScale) * -0.4F);
               totalScale += neighborScale * scaleFalloff;
               totalScaleContribution += scaleFalloff;
            }
         }

         if (++cz >= zCount) {
            cz = 0;
            if (++cx >= xCount) {
               return new BiomeDensitySource.DensityData(totalMappedDepth / totalContribution, totalScale / totalScaleContribution);
            }
         }
      }
   }

   public static final class DensityData {
      public final double depth;
      public final double scale;

      public DensityData(double depth, double scale) {
         this.depth = depth;
         this.scale = scale;
      }
   }
}
