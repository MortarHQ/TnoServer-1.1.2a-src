package xaero.map.highlight;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.nio.ByteBuffer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import xaero.map.WorldMap;
import xaero.map.mods.SupportMods;
import xaero.map.pool.buffer.PoolTextureDirectBufferUnit;
import xaero.map.world.MapDimension;

public class DimensionHighlighterHandler {
   private final MapDimension mapDimension;
   private final ResourceKey<Level> dimension;
   private final HighlighterRegistry registry;
   private final Long2ObjectMap<Integer> hashCodeCache;
   private final Component SUBTLE_TOOLTIP_SEPARATOR = Component.literal(" | ");
   private final Component BLUNT_TOOLTIP_SEPARATOR = Component.literal(" \n ");

   public DimensionHighlighterHandler(MapDimension mapDimension, ResourceKey<Level> dimension, HighlighterRegistry registry) {
      this.mapDimension = mapDimension;
      this.dimension = dimension;
      this.registry = registry;
      this.hashCodeCache = new Long2ObjectOpenHashMap();
   }

   public int getRegionHash(int regionX, int regionZ) {
      synchronized (this) {
         long key = getKey(regionX, regionZ);
         Integer cachedHash = (Integer)this.hashCodeCache.get(key);
         if (cachedHash == null) {
            cachedHash = this.recalculateHash(regionX, regionZ);
         }

         return cachedHash;
      }
   }

   public boolean shouldApplyRegionHighlights(int regionX, int regionZ, boolean discovered) {
      ResourceKey<Level> dimension = this.dimension;

      for (AbstractHighlighter hl : this.registry.getHighlighters()) {
         if ((discovered || hl.isCoveringOutsideDiscovered()) && hl.regionHasHighlights(dimension, regionX, regionZ)) {
            return true;
         }
      }

      return false;
   }

   public boolean shouldApplyTileChunkHighlights(int regionX, int regionZ, int insideTileChunkX, int insideTileChunkZ, boolean discovered) {
      int startChunkX = regionX << 5 | insideTileChunkX << 2;
      int startChunkZ = regionZ << 5 | insideTileChunkZ << 2;

      for (AbstractHighlighter hl : this.registry.getHighlighters()) {
         if (this.shouldApplyTileChunkHighlightsHelp(hl, regionX, regionZ, startChunkX, startChunkZ, discovered)) {
            return true;
         }
      }

      return false;
   }

   private boolean shouldApplyTileChunkHighlights(
      AbstractHighlighter hl, int regionX, int regionZ, int insideTileChunkX, int insideTileChunkZ, boolean discovered
   ) {
      int startChunkX = regionX << 5 | insideTileChunkX << 2;
      int startChunkZ = regionZ << 5 | insideTileChunkZ << 2;
      return this.shouldApplyTileChunkHighlightsHelp(hl, regionX, regionZ, startChunkX, startChunkZ, discovered);
   }

   private boolean shouldApplyTileChunkHighlightsHelp(AbstractHighlighter hl, int regionX, int regionZ, int startChunkX, int startChunkZ, boolean discovered) {
      if (!discovered && !hl.isCoveringOutsideDiscovered()) {
         return false;
      }

      ResourceKey<Level> dimension = this.dimension;
      if (!hl.regionHasHighlights(dimension, regionX, regionZ)) {
         return false;
      }

      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            if (hl.chunkIsHighlit(dimension, startChunkX | i, startChunkZ | j)) {
               return true;
            }
         }
      }

      return false;
   }

   public PoolTextureDirectBufferUnit applyChunkHighlightColors(
      int chunkX,
      int chunkZ,
      int innerChunkX,
      int innerChunkZ,
      PoolTextureDirectBufferUnit buffer,
      PoolTextureDirectBufferUnit highlitColorBuffer,
      boolean highlitBufferPrepared,
      boolean discovered,
      boolean separateBuffer
   ) {
      boolean hasSomething = false;
      ResourceKey<Level> dimension = this.dimension;
      if (!separateBuffer) {
         highlitBufferPrepared = true;
         highlitColorBuffer = buffer;
      }

      ByteBuffer highlitColorBufferDirect = highlitColorBuffer == null ? null : highlitColorBuffer.getDirectBuffer();

      for (AbstractHighlighter hl : this.registry.getHighlighters()) {
         if (discovered || hl.isCoveringOutsideDiscovered()) {
            int[] highlightColors = hl.getChunkHighlitColor(dimension, chunkX, chunkZ);
            if (highlightColors != null) {
               if (!hasSomething && !highlitBufferPrepared) {
                  highlitColorBuffer = WorldMap.textureDirectBufferPool.get(buffer == null);
                  highlitColorBufferDirect = highlitColorBuffer.getDirectBuffer();
                  if (buffer != null) {
                     highlitColorBufferDirect.put(buffer.getDirectBuffer());
                  }

                  highlitColorBufferDirect.position(0);
                  if (buffer != null) {
                     buffer.getDirectBuffer().position(0);
                  }
               }

               hasSomething = true;
               int textureOffset = innerChunkZ << 4 << 6 | innerChunkX << 4;

               for (int i = 0; i < highlightColors.length; i++) {
                  int highlightColor = highlightColors[i];
                  int hlAlpha = highlightColor & 0xFF;
                  float hlAlphaFloat = hlAlpha / 255.0F;
                  float oneMinusHlAlpha = 1.0F - hlAlphaFloat;
                  int hlRed = highlightColor >> 8 & 0xFF;
                  int hlGreen = highlightColor >> 16 & 0xFF;
                  int hlBlue = highlightColor >> 24 & 0xFF;
                  int index = textureOffset | i >> 4 << 6 | i & 15;
                  int originalColor = highlitColorBufferDirect.getInt(index * 4);
                  int red = originalColor >> 8 & 0xFF;
                  int green = originalColor >> 16 & 0xFF;
                  int blue = originalColor >> 24 & 0xFF;
                  int alpha = originalColor & 0xFF;
                  red = (int)(red * oneMinusHlAlpha + hlRed * hlAlphaFloat);
                  green = (int)(green * oneMinusHlAlpha + hlGreen * hlAlphaFloat);
                  blue = (int)(blue * oneMinusHlAlpha + hlBlue * hlAlphaFloat);
                  if (red > 255) {
                     red = 255;
                  }

                  if (green > 255) {
                     green = 255;
                  }

                  if (blue > 255) {
                     blue = 255;
                  }

                  highlitColorBufferDirect.putInt(index * 4, blue << 24 | green << 16 | red << 8 | alpha);
               }
            }
         }
      }

      return !hasSomething ? null : highlitColorBuffer;
   }

   private int recalculateHash(int regionX, int regionZ) {
      HashCodeBuilder hashcodeBuilder = new HashCodeBuilder();

      for (AbstractHighlighter hl : this.registry.getHighlighters()) {
         hashcodeBuilder.append(hl.calculateRegionHash(this.dimension, regionX, regionZ));
         hashcodeBuilder.append(hl.isCoveringOutsideDiscovered());
      }

      int builtHash = hashcodeBuilder.build();
      long key = getKey(regionX, regionZ);
      this.hashCodeCache.put(key, builtHash);
      return builtHash;
   }

   public void clearCachedHash(int regionX, int regionZ) {
      long key = getKey(regionX, regionZ);
      this.hashCodeCache.remove(key);
      this.mapDimension.onClearCachedHighlightHash(regionX, regionZ);
      if (SupportMods.minimap()) {
         SupportMods.xaeroMinimap.onClearHighlightHash(regionX, regionZ);
      }
   }

   public void clearCachedHashes() {
      this.hashCodeCache.clear();
      this.mapDimension.onClearCachedHighlightHashes();
      if (SupportMods.minimap()) {
         SupportMods.xaeroMinimap.onClearHighlightHashes();
      }
   }

   public Component getBlockHighlightSubtleTooltip(int blockX, int blockZ, boolean discovered) {
      return this.getBlockHighlightTooltip(blockX, blockZ, discovered, true);
   }

   public Component getBlockHighlightBluntTooltip(int blockX, int blockZ, boolean discovered) {
      return this.getBlockHighlightTooltip(blockX, blockZ, discovered, false);
   }

   private Component getBlockHighlightTooltip(int blockX, int blockZ, boolean discovered, boolean subtle) {
      ResourceKey<Level> dimension = this.dimension;
      int tileChunkX = blockX >> 6;
      int tileChunkZ = blockZ >> 6;
      int regionX = tileChunkX >> 3;
      int regionZ = tileChunkZ >> 3;
      if (!this.shouldApplyRegionHighlights(regionX, regionZ, discovered)) {
         return null;
      }

      int localTileChunkX = tileChunkX & 7;
      int localTileChunkZ = tileChunkZ & 7;
      Component result = null;

      for (AbstractHighlighter hl : this.registry.getHighlighters()) {
         if (this.shouldApplyTileChunkHighlights(hl, regionX, regionZ, localTileChunkX, localTileChunkZ, discovered)) {
            Component hlTooltip = subtle
               ? hl.getBlockHighlightSubtleTooltip(dimension, blockX, blockZ)
               : hl.getBlockHighlightBluntTooltip(dimension, blockX, blockZ);
            if (hlTooltip != null) {
               if (result == null) {
                  result = Component.literal("");
               } else {
                  result.getSiblings().add(subtle ? this.SUBTLE_TOOLTIP_SEPARATOR : this.BLUNT_TOOLTIP_SEPARATOR);
               }

               result.getSiblings().add(hlTooltip);
            }
         }
      }

      return result;
   }

   public static long getKey(int regionX, int regionZ) {
      return (long)regionZ << 32 | regionX & 4294967295L;
   }

   public static int getXFromKey(long key) {
      return (int)(key & -1L);
   }

   public static int getZFromKey(long key) {
      return (int)(key >> 32);
   }
}
