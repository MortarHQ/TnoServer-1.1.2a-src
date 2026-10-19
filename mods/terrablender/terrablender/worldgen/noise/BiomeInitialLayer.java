package terrablender.worldgen.noise;

import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class BiomeInitialLayer extends WeightedRandomLayer<Wrapper<ResourceKey<Biome>>> {
   private final Registry<Biome> biomeRegistry;

   public BiomeInitialLayer(RegistryAccess registryAccess, List<Wrapper<ResourceKey<Biome>>> entries) {
      super(entries);
      this.biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);
   }

   protected int getEntryIndex(Wrapper<ResourceKey<Biome>> entry) {
      return this.resolveId((ResourceKey<Biome>)entry.data());
   }

   @Override
   protected int getDefaultIndex() {
      return this.resolveId(Biomes.OCEAN);
   }

   private int resolveId(ResourceKey<Biome> key) {
      if (!this.biomeRegistry.containsKey(key)) {
         throw new RuntimeException("Attempted to resolve id for unregistered biome " + key);
      } else {
         return this.biomeRegistry.getId((Biome)this.biomeRegistry.get(key));
      }
   }
}
