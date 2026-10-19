package net.regions_unexplored.registry;

import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.regions_unexplored.module.platform.Registrar;
import net.regions_unexplored.worldgen.foliageplacer.AspenFoliagePlacer;
import net.regions_unexplored.worldgen.foliageplacer.BioshroomFoliagePlacer;
import net.regions_unexplored.worldgen.foliageplacer.FancyPineFoliagePlacer;
import net.regions_unexplored.worldgen.foliageplacer.MagnoliaFoliagePlacer;
import net.regions_unexplored.worldgen.foliageplacer.MapleFoliagePlacer;
import net.regions_unexplored.worldgen.foliageplacer.RedwoodFoliagePlacer;
import net.regions_unexplored.worldgen.foliageplacer.SakuraFoliagePlacer;
import net.regions_unexplored.worldgen.foliageplacer.SkinnyPineFoliagePlacer;
import net.regions_unexplored.worldgen.foliageplacer.WillowFoliagePlacer;

public interface RUFoliagePlacerTypes {
   Supplier<FoliagePlacerType<AspenFoliagePlacer>> ASPEN = register("aspen", AspenFoliagePlacer.TYPE);
   Supplier<FoliagePlacerType<BioshroomFoliagePlacer>> BIOSHROOM = register("bioshroom", BioshroomFoliagePlacer.TYPE);
   Supplier<FoliagePlacerType<FancyPineFoliagePlacer>> FANCY_PINE = register("fancy_pine", FancyPineFoliagePlacer.TYPE);
   Supplier<FoliagePlacerType<MagnoliaFoliagePlacer>> MAGNOLIA = register("magnolia", MagnoliaFoliagePlacer.TYPE);
   Supplier<FoliagePlacerType<MapleFoliagePlacer>> MAPLE = register("maple", MapleFoliagePlacer.TYPE);
   Supplier<FoliagePlacerType<RedwoodFoliagePlacer>> REDWOOD = register("redwood", RedwoodFoliagePlacer.TYPE);
   Supplier<FoliagePlacerType<SakuraFoliagePlacer>> SAKURA = register("sakura", SakuraFoliagePlacer.TYPE);
   Supplier<FoliagePlacerType<SkinnyPineFoliagePlacer>> SKINNY_PINE = register("skinny_pine", SkinnyPineFoliagePlacer.TYPE);
   Supplier<FoliagePlacerType<WillowFoliagePlacer>> WILLOW = register("willow", WillowFoliagePlacer.TYPE);

   static <T extends FoliagePlacer> Supplier<FoliagePlacerType<T>> register(String name, FoliagePlacerType<T> type) {
      Registrar.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, name, () -> type);
      return () -> type;
   }

   static void init() {
   }
}
