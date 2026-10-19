package xaero.map.biome;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import xaero.map.MapProcessor;
import xaero.map.region.MapTile;

public class BiomeColorCalculator {
   public final ResourceKey<Biome> UNREACHABLE_BIOME = ResourceKey.create(Registries.BIOME, ResourceLocation.parse("xaeroworldmap:unreachable_biome"));
   public final ResourceKey<Biome> RIVER_BIOME = Biomes.RIVER;
   private int startO;
   private int endO;
   private int startP;
   private int endP;

   public void prepare(boolean biomeBlending) {
      this.startO = this.endO = this.startP = this.endP = 0;
      if (biomeBlending) {
         this.startO = -1;
         this.endO = 1;
         this.startP = -1;
         this.endP = 1;
      }
   }

   public int getBiomeColor(
      ColorResolver stateColorResolver,
      boolean overlay,
      MutableBlockPos pos,
      MapTile tile,
      int caveLayer,
      Registry<Biome> biomeRegistry,
      MapProcessor mapProcessor
   ) {
      if (stateColorResolver == null) {
         return -1;
      }

      int i = 0;
      int j = 0;
      int k = 0;
      int total = 0;
      int initX = pos.getX();
      int initZ = pos.getZ();

      for (int o = this.startO; o <= this.endO; o++) {
         for (int p = this.startP; p <= this.endP; p++) {
            if (o == 0 || p == 0) {
               pos.set(initX + o, pos.getY(), initZ + p);
               ResourceKey<Biome> b = this.getBiomeAtPos(pos, tile, caveLayer, mapProcessor);
               if (b != this.UNREACHABLE_BIOME) {
                  if (b == null && overlay) {
                     b = this.RIVER_BIOME;
                  }

                  if (b != null) {
                     int l = 0;
                     Biome gen = (Biome)biomeRegistry.get(b);
                     if (gen != null) {
                        l = stateColorResolver.getColor(gen, pos.getX(), pos.getZ());
                        i += l & 0xFF0000;
                        j += l & 0xFF00;
                        k += l & 0xFF;
                        total++;
                     }
                  }
               }
            }
         }
      }

      pos.set(initX, pos.getY(), initZ);
      if (total == 0) {
         Biome defaultBiome = (Biome)biomeRegistry.get(Biomes.RIVER);
         return defaultBiome == null ? -1 : stateColorResolver.getColor(defaultBiome, pos.getX(), pos.getZ());
      } else {
         return i / total & 0xFF0000 | j / total & 0xFF00 | k / total;
      }
   }

   public ResourceKey<Biome> getBiomeAtPos(BlockPos pos, MapTile centerTile, int caveLayer, MapProcessor mapProcessor) {
      int tileX = pos.getX() >> 4;
      int tileZ = pos.getZ() >> 4;
      MapTile tile = tileX == centerTile.getChunkX() && tileZ == centerTile.getChunkZ() ? centerTile : mapProcessor.getMapTile(caveLayer, tileX, tileZ);
      return tile != null && tile.isLoaded() ? tile.getBlock(pos.getX() & 15, pos.getZ() & 15).getBiome() : this.UNREACHABLE_BIOME;
   }
}
