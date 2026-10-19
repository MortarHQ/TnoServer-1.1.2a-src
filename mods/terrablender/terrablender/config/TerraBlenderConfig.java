package terrablender.config;

import java.nio.file.Path;

public class TerraBlenderConfig extends Config {
   public int overworldRegionSize;
   public int netherRegionSize;
   public int vanillaOverworldRegionWeight;
   public int vanillaNetherRegionWeight;
   public int endHighlandsBiomeSize;
   public int endMidlandsBiomeSize;
   public int endEdgeBiomeSize;
   public int endIslandBiomeSize;
   public int vanillaEndHighlandsWeight;
   public int vanillaEndMidlandsWeight;
   public int vanillaEndBarrensWeight;
   public int vanillaSmallEndIslandsWeight;

   public TerraBlenderConfig(Path path) {
      super(path);
   }

   @Override
   public void load() {
      this.overworldRegionSize = this.addNumber(
         "general.overworld_region_size", 3, 2, 6, "The size of overworld biome regions from each mod that uses TerraBlender."
      );
      this.netherRegionSize = this.addNumber("general.nether_region_size", 2, 2, 6, "The size of nether biome regions from each mod that uses TerraBlender.");
      this.vanillaOverworldRegionWeight = this.addNumber(
         "general.vanilla_overworld_region_weight", 10, 0, Integer.MAX_VALUE, "The weighting of vanilla biome regions in the overworld."
      );
      this.vanillaNetherRegionWeight = this.addNumber(
         "general.vanilla_nether_region_weight", 10, 0, Integer.MAX_VALUE, "The weighting of vanilla biome regions in the nether."
      );
      this.endHighlandsBiomeSize = this.addNumber("end.highlands_biome_size", 4, 2, 6, "The size of highlands end biomes.");
      this.endMidlandsBiomeSize = this.addNumber("end.midlands_biome_size", 4, 2, 6, "The size of midlands end biomes.");
      this.endEdgeBiomeSize = this.addNumber("end.edge_biome_size", 3, 2, 6, "The size of edge end biomes.");
      this.endIslandBiomeSize = this.addNumber("end.island_biome_size", 2, 2, 6, "The size of island end biomes.");
      this.vanillaEndHighlandsWeight = this.addNumber(
         "end.vanilla_end_highlands_weight", 10, 0, Integer.MAX_VALUE, "The weight of Vanilla end highlands biomes."
      );
      this.vanillaEndMidlandsWeight = this.addNumber("end.vanilla_end_midlands_weight", 10, 0, Integer.MAX_VALUE, "The weight of Vanilla end midlands biomes.");
      this.vanillaEndBarrensWeight = this.addNumber("end.vanilla_end_barrens_weight", 10, 0, Integer.MAX_VALUE, "The weight of Vanilla end barrens biomes.");
      this.vanillaSmallEndIslandsWeight = this.addNumber(
         "end.vanilla_small_end_islands_weight", 10, 0, Integer.MAX_VALUE, "The weight of Vanilla small end islands biomes."
      );
   }
}
