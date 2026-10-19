package terrablender.worldgen.noise;

import java.util.List;
import java.util.function.LongFunction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.level.biome.Biome;
import terrablender.api.RegionType;
import terrablender.core.TerraBlender;

public class LayeredNoiseUtil {
   public static Area uniqueness(RegistryAccess registryAccess, RegionType regionType, long seed) {
      return finalUniqueness(regionType, seed, initialUniqueness(registryAccess, regionType));
   }

   public static InitialLayer initialUniqueness(RegistryAccess registryAccess, RegionType regionType) {
      return new InitialLayer(registryAccess, regionType);
   }

   public static Area finalUniqueness(RegionType regionType, long seed, InitialLayer initialLayer) {
      int numZooms = TerraBlender.CONFIG.overworldRegionSize;
      if (regionType == RegionType.NETHER) {
         numZooms = TerraBlender.CONFIG.netherRegionSize;
      }

      return createZoomedArea(seed, numZooms, initialLayer);
   }

   public static Area biomeArea(RegistryAccess registryAccess, long seed, int size, List<Wrapper<ResourceKey<Biome>>> entries) {
      return createZoomedArea(seed, size, new BiomeInitialLayer(registryAccess, entries));
   }

   public static Area createZoomedArea(long seed, int zooms, AreaTransformer0 initialTransformer) {
      LongFunction<AreaContext> contextFactory = seedModifier -> new AreaContext(25, seed, seedModifier);
      AreaFactory factory = initialTransformer.run(contextFactory.apply(1L));
      factory = ZoomLayer.FUZZY.run(contextFactory.apply(2000L), factory);
      factory = zoom(2001L, ZoomLayer.NORMAL, factory, 3, contextFactory);
      factory = zoom(1001L, ZoomLayer.NORMAL, factory, zooms, contextFactory);
      return factory.make();
   }

   public static AreaFactory zoom(
      long seedModifier, AreaTransformer1 transformer, AreaFactory initialAreaFactory, int times, LongFunction<AreaContext> contextFactory
   ) {
      AreaFactory areaFactory = initialAreaFactory;

      for (int i = 0; i < times; i++) {
         areaFactory = transformer.run(contextFactory.apply(seedModifier + i), areaFactory);
      }

      return areaFactory;
   }
}
