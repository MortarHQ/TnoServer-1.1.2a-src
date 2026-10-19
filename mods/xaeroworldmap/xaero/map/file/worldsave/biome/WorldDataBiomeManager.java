package xaero.map.file.worldsave.biome;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.BiomeManager.NoiseBiomeSource;
import xaero.map.misc.CachedFunction;

public class WorldDataBiomeManager implements NoiseBiomeSource {
   private final WorldDataReaderChunkBiomeData[] chunkBiomeData = new WorldDataReaderChunkBiomeData[1156];
   private int regionX;
   private int regionZ;
   private Biome theVoid;
   private Biome defaultBiome;
   private Registry<Biome> biomeRegistry;
   private MutableBlockPos mutableBlockPos;
   private CachedFunction<String, ResourceLocation> resourceLocationCache;

   public WorldDataBiomeManager() {
      for (int i = 0; i < this.chunkBiomeData.length; i++) {
         this.chunkBiomeData[i] = new WorldDataReaderChunkBiomeData();
      }

      this.mutableBlockPos = new MutableBlockPos();
      this.resourceLocationCache = new CachedFunction<>(ResourceLocation::parse);
   }

   public void resetChunkBiomeData(int regionX, int regionZ, Biome defaultBiome, Registry<Biome> biomeRegistry) {
      this.regionX = regionX;
      this.regionZ = regionZ;
      this.biomeRegistry = biomeRegistry;
      this.theVoid = (Biome)biomeRegistry.get(Biomes.THE_VOID);
   }

   public void clear() {
      for (int i = 0; i < this.chunkBiomeData.length; i++) {
         this.chunkBiomeData[i].clear();
      }
   }

   private WorldDataReaderChunkBiomeData getChunkBiomeData(int chunkX, int chunkZ) {
      return chunkX >= -1 && chunkZ >= -1 && chunkX <= 32 && chunkZ <= 32 ? this.chunkBiomeData[(chunkZ + 1) * 34 + chunkX + 1] : null;
   }

   public void addBiomeSectionForRegionChunk(int chunkX, int chunkZ, int sectionIndex, WorldDataReaderSectionBiomeData section) {
      this.getChunkBiomeData(chunkX, chunkZ).addSection(sectionIndex, section);
   }

   public Biome getBiome(BiomeManager biomeZoomer, int x, int y, int z) {
      this.defaultBiome = null;
      this.defaultBiome = (Biome)this.getNoiseBiome(x >> 2, y >> 2, z >> 2).value();
      if (this.defaultBiome == null) {
         this.defaultBiome = this.theVoid;
      }

      this.mutableBlockPos.set(x, y, z);
      return (Biome)biomeZoomer.getBiome(this.mutableBlockPos).value();
   }

   public Holder<Biome> getNoiseBiome(int x, int y, int z) {
      int relativeX = x - this.regionX * 128;
      int relativeZ = z - this.regionZ * 128;
      int chunkX = relativeX >> 2;
      int chunkZ = relativeZ >> 2;
      int quadX = relativeX & 3;
      int quadZ = relativeZ & 3;
      WorldDataReaderChunkBiomeData chunkBiomeData = this.getChunkBiomeData(chunkX, chunkZ);
      if (chunkBiomeData == null) {
         return Holder.direct(this.defaultBiome);
      }

      Biome biome = chunkBiomeData.getNoiseBiome(quadX, y, quadZ, this.biomeRegistry, this.resourceLocationCache);
      return biome == null ? Holder.direct(this.defaultBiome) : Holder.direct(biome);
   }
}
