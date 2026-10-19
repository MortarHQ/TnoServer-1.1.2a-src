package terrablender.worldgen.noise;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.level.biome.Biome;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;

public class InitialLayer extends WeightedRandomLayer<Wrapper<Region>> {
   private final RegionType regionType;

   public InitialLayer(RegistryAccess registryAccess, RegionType regionType) {
      super(createEntries(registryAccess, regionType));
      this.regionType = regionType;
   }

   protected int getEntryIndex(Wrapper<Region> entry) {
      return Regions.getIndex(this.regionType, ((Region)entry.data()).getName());
   }

   private static List<Wrapper<Region>> createEntries(RegistryAccess registryAccess, RegionType regionType) {
      Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);
      return Regions.get(regionType).stream().filter(region -> {
         AtomicBoolean biomesAdded = new AtomicBoolean(false);
         region.addBiomes(biomeRegistry, pair -> biomesAdded.set(true));
         return region.getType() == regionType && biomesAdded.get();
      }).map(region -> WeightedEntry.wrap(region, region.getWeight())).collect(ImmutableList.toImmutableList());
   }
}
