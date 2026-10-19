package twilightforest.world.components.structures;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.jetbrains.annotations.NotNull;
import twilightforest.TwilightForestMod;
import twilightforest.data.custom.stalactites.entry.SpeleothemVarietyConfig;
import twilightforest.data.custom.stalactites.entry.Stalactite;
import twilightforest.data.custom.stalactites.entry.StalactiteReloadListener;
import twilightforest.util.iterators.RectangleLatticeIterator;
import twilightforest.world.components.feature.BlockSpikeFeature;

public record StructureSpeleothemConfig(
   RectangleLatticeIterator.TriangularLatticeConfig latticeConfig,
   String speleothemVarietyType,
   Supplier<SpeleothemVarietyConfig> speleothemVarietyConfig,
   Supplier<Function<RandomSource, Stalactite>> stalactiteVariety,
   Supplier<Function<RandomSource, Stalactite>> stalagmiteVariety
) {
   public static final Codec<StructureSpeleothemConfig> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
            RectangleLatticeIterator.TriangularLatticeConfig.CODEC.fieldOf("lattice").forGetter(StructureSpeleothemConfig::latticeConfig),
            Codec.STRING.xmap(String::toLowerCase, String::toLowerCase).fieldOf("type").forGetter(StructureSpeleothemConfig::speleothemVarietyType)
         )
         .apply(inst, StructureSpeleothemConfig::fromLocation)
   );

   @NotNull
   public static StructureSpeleothemConfig fromLocation(RectangleLatticeIterator.TriangularLatticeConfig latticeConfig, String type) {
      Supplier<SpeleothemVarietyConfig> lazyConfigSupplier = Suppliers.memoize(() -> StalactiteReloadListener.HILL_CONFIGS.get(type));
      Supplier<Function<RandomSource, Stalactite>> lazyStalactiteGetter = Suppliers.memoize(() -> compileStalactites(lazyConfigSupplier));
      Supplier<Function<RandomSource, Stalactite>> lazyStalagmiteGetter = Suppliers.memoize(() -> compileStalagmites(lazyConfigSupplier));
      return new StructureSpeleothemConfig(latticeConfig, type, lazyConfigSupplier, lazyStalactiteGetter, lazyStalagmiteGetter);
   }

   @NotNull
   private static Function<RandomSource, Stalactite> compileStalagmites(Supplier<SpeleothemVarietyConfig> varietyConfigSupplier) {
      SpeleothemVarietyConfig varietyConfig = varietyConfigSupplier.get();
      TwilightForestMod.LOGGER.debug("Compiling Stalagmite configs for " + varietyConfig.type() + " type");
      List<Stalactite> stalactites = StalactiteReloadListener.STALAGMITES_PER_HILL.get(varietyConfig.type());
      return compileSpeleothemsSimple(stalactites);
   }

   @NotNull
   private static Function<RandomSource, Stalactite> compileStalactites(Supplier<SpeleothemVarietyConfig> varietyConfigSupplier) {
      SpeleothemVarietyConfig varietyConfig = varietyConfigSupplier.get();
      TwilightForestMod.LOGGER.debug("Compiling Stalactite configs for " + varietyConfig.type() + " type");
      float weightedListInterpolation = Mth.clamp(varietyConfig.oreChance(), 0.0F, 1.0F);
      List<Stalactite> stalactites = StalactiteReloadListener.STALACTITES_PER_HILL.get(varietyConfig.type());
      List<Stalactite> oreStalactites = StalactiteReloadListener.ORE_STALACTITES_PER_HILL.get(varietyConfig.type());
      int stoneWeightSum = stalactites.stream().mapToInt(Stalactite::weight).sum();
      int oreWeightSum = oreStalactites.stream().mapToInt(Stalactite::weight).sum();
      float totalWeight = stoneWeightSum + oreWeightSum;
      if (totalWeight <= 0.0F) {
         return BlockSpikeFeature::defaultRandom;
      }

      if (stalactites.isEmpty() || stoneWeightSum <= 0) {
         return compileSpeleothemsSimple(oreStalactites);
      }

      if (!oreStalactites.isEmpty() && oreWeightSum > 0) {
         double quantizationFactor = Math.ceil(Math.pow(10.0, Mth.clamp(("" + weightedListInterpolation + totalWeight).length() - 4, 2, 6)));
         double stoneCounterweight = quantizationFactor * (1.0F - weightedListInterpolation) / stoneWeightSum;
         double oreCounterweight = quantizationFactor * weightedListInterpolation / oreWeightSum;
         if (stoneCounterweight <= 0.0) {
            return compileSpeleothemsSimple(oreStalactites);
         }

         if (oreCounterweight <= 0.0) {
            return compileSpeleothemsSimple(stalactites);
         }

         ArrayList<Wrapper<Stalactite>> unbakedRandomList = stalactites.stream()
            .map(s -> WeightedEntry.wrap(s, Mth.ceil(s.weight() * stoneCounterweight)))
            .collect(Collectors.toCollection(ArrayList::new));
         oreStalactites.stream().map(s -> WeightedEntry.wrap(s, Mth.ceil(s.weight() * oreCounterweight))).forEachOrdered(unbakedRandomList::add);
         StringJoiner joiner = new StringJoiner("\n");
         joiner.add("")
            .add("Ore interpolation factor: " + weightedListInterpolation)
            .add("Stone Counterweight: " + stoneCounterweight)
            .add("Ore Counterweight: " + oreCounterweight);

         for (Wrapper<Stalactite> e : unbakedRandomList) {
            joiner.add(e.data() + " - After counterweight: " + e.getWeight().asInt());
         }

         joiner.add("Total weight after counterweights: " + unbakedRandomList.stream().mapToInt(ex -> ex.getWeight().asInt()).sum());
         TwilightForestMod.LOGGER.debug(joiner);
         return compileSpeleothems(unbakedRandomList);
      } else {
         return compileSpeleothemsSimple(stalactites);
      }
   }

   @NotNull
   private static Function<RandomSource, Stalactite> compileSpeleothemsSimple(List<Stalactite> stalactites) {
      return compileSpeleothems(stalactites.stream().map(stalactite -> WeightedEntry.wrap(stalactite, stalactite.weight())).toList());
   }

   @NotNull
   private static Function<RandomSource, Stalactite> compileSpeleothems(List<Wrapper<Stalactite>> unbakedRandomList) {
      WeightedRandomList<Wrapper<Stalactite>> randomList = WeightedRandomList.create(unbakedRandomList);
      return !randomList.isEmpty() && randomList.unwrap().stream().mapToInt(w -> w.getWeight().asInt()).sum() > 0
         ? random -> randomList.getRandom(random).<Stalactite>map(Wrapper::data).orElse(BlockSpikeFeature.STONE_STALACTITE)
         : BlockSpikeFeature::defaultRandom;
   }

   public SpeleothemVarietyConfig getVarietyConfig() {
      return this.speleothemVarietyConfig.get();
   }

   public boolean shouldDoAStalactite(RandomSource rand) {
      return this.getVarietyConfig().shouldDoAStalactite(rand);
   }

   public boolean shouldDoAStalagmite(RandomSource rand) {
      return this.getVarietyConfig().shouldDoAStalagmite(rand);
   }

   @NotNull
   public Stalactite getStalactite(RandomSource rand) {
      return this.stalactiteVariety.get().apply(rand);
   }

   @NotNull
   public Stalactite getStalagmite(RandomSource rand) {
      return this.stalagmiteVariety.get().apply(rand);
   }

   @NotNull
   public Stalactite getSpeleothem(boolean hanging, RandomSource rand) {
      return hanging ? this.getStalactite(rand) : this.getStalagmite(rand);
   }

   @NotNull
   public Iterable<MutableBlockPos> latticeIterator(@Nullable BoundingBox bounds, int yLevel) {
      return bounds == null ? List.of() : this.latticeConfig.boundedGrid(bounds, yLevel);
   }
}
