package io.github.Memoires.mysticism.registry.world;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.world.features.ErodedRockMoundFeature;
import io.github.Memoires.mysticism.world.features.FireBlockBlobFeature;
import io.github.Memoires.mysticism.world.features.FloatingDebrisSpatialFeature;
import io.github.Memoires.mysticism.world.features.KamuiCubeFeature;
import io.github.Memoires.mysticism.world.features.LavaLakeFeature;
import io.github.Memoires.mysticism.world.features.StoneMountainFeature;
import io.github.Memoires.mysticism.world.features.WaterBlobBlockFeature;
import io.github.Memoires.mysticism.world.features.WaterCoarseDirtLakeFeature;
import io.github.Memoires.mysticism.world.features.WaterPodzolLakeFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature.Configuration;

public class MysticismFeatures {
   private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create("mysticism", Registries.FEATURE);
   public static final RegistrySupplier<FireBlockBlobFeature> FIRE_BLOB = FEATURES.register("fire_blob", FireBlockBlobFeature::new);
   public static final RegistrySupplier<WaterBlobBlockFeature> WATER_BLOB = FEATURES.register("water_blob", WaterBlobBlockFeature::new);
   public static final RegistrySupplier<ErodedRockMoundFeature> EARTH_BLOB = FEATURES.register("earth_blob", ErodedRockMoundFeature::new);
   public static final RegistrySupplier<StoneMountainFeature> WIND_BLOB = FEATURES.register("wind_blob", StoneMountainFeature::new);
   public static final RegistrySupplier<FloatingDebrisSpatialFeature> SPATIAL_BLOB = FEATURES.register("spatial_blob", FloatingDebrisSpatialFeature::new);
   public static final RegistrySupplier<LavaLakeFeature> LAVA_LAKE = FEATURES.register("lava_lake", () -> new LavaLakeFeature(Configuration.CODEC));
   public static final RegistrySupplier<WaterPodzolLakeFeature> PODZOL_LAKE = FEATURES.register(
      "podzol_lake", () -> new WaterPodzolLakeFeature(Configuration.CODEC)
   );
   public static final RegistrySupplier<WaterCoarseDirtLakeFeature> COARSE_LAKE = FEATURES.register(
      "coarse_lake", () -> new WaterCoarseDirtLakeFeature(Configuration.CODEC)
   );
   public static final RegistrySupplier<KamuiCubeFeature> KAMUI_CUBE = FEATURES.register("kamui_cube", KamuiCubeFeature::new);

   public static void init() {
      FEATURES.register();
   }
}
