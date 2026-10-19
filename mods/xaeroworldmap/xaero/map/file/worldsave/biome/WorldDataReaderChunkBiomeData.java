package xaero.map.file.worldsave.biome;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import xaero.map.misc.CachedFunction;

public class WorldDataReaderChunkBiomeData {
   private Int2ObjectMap<WorldDataReaderSectionBiomeData> sections;

   public void addSection(int sectionIndex, WorldDataReaderSectionBiomeData section) {
      if (this.sections == null) {
         this.sections = new Int2ObjectOpenHashMap();
      }

      this.sections.put(sectionIndex, section);
   }

   public Biome getNoiseBiome(int quadX, int quadY, int quadZ, Registry<Biome> biomeRegistry, CachedFunction<String, ResourceLocation> resourceLocationCache) {
      if (this.sections == null) {
         return null;
      }

      int sectionIndex = quadY >> 2;
      WorldDataReaderSectionBiomeData section = (WorldDataReaderSectionBiomeData)this.sections.get(sectionIndex);
      if (section == null) {
         return null;
      }

      int sectionQuadY = quadY & 3;
      ResourceLocation biomeLocation = resourceLocationCache.apply(section.get(quadX, sectionQuadY, quadZ));
      return biomeLocation == null ? null : (Biome)biomeRegistry.get(biomeLocation);
   }

   public void clear() {
      this.sections = null;
   }
}
