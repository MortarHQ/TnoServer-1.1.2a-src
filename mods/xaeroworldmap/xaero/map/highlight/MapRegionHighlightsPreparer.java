package xaero.map.highlight;

import net.minecraft.client.Minecraft;
import xaero.map.region.MapRegion;
import xaero.map.region.MapTileChunk;

public class MapRegionHighlightsPreparer {
   public void prepare(MapRegion region, boolean tileChunkDiscoveryKnown) {
      if (!Minecraft.getInstance().isSameThread()) {
         throw new RuntimeException(new IllegalAccessException());
      }

      region.updateTargetHighlightsHash();
      DimensionHighlighterHandler highlighterHandler = region.getDim().getHighlightHandler();
      boolean regionHasHighlights = highlighterHandler.shouldApplyRegionHighlights(region.getRegionX(), region.getRegionZ(), region.hasHadTerrain());

      for (int i = 0; i < 8; i++) {
         for (int j = 0; j < 8; j++) {
            this.prepare(region, i, j, regionHasHighlights, tileChunkDiscoveryKnown);
         }
      }
   }

   private void prepare(MapRegion region, int x, int z, boolean regionHasHighlights, boolean tileChunkDiscoveryKnown) {
      DimensionHighlighterHandler highlighterHandler = region.getDim().getHighlightHandler();
      MapTileChunk tileChunk = region.getChunk(x, z);
      boolean tileChunkHasHighlights = !regionHasHighlights
         ? false
         : highlighterHandler.shouldApplyTileChunkHighlights(
            region.getRegionX(), region.getRegionZ(), x, z, !tileChunkDiscoveryKnown ? true : tileChunk != null
         );
      boolean tileChunkHasHighlightsUndiscovered = !regionHasHighlights
         ? false
         : highlighterHandler.shouldApplyTileChunkHighlights(region.getRegionX(), region.getRegionZ(), x, z, false);
      if (tileChunk == null) {
         if (!tileChunkHasHighlights) {
            return;
         }

         tileChunk = region.createTexture(x, z).getTileChunk();
      }

      tileChunk.setHasHighlights(tileChunkHasHighlights);
      tileChunk.setHasHighlightsIfUndiscovered(tileChunkHasHighlightsUndiscovered);
   }

   public void prepare(MapRegion region, int x, int z, boolean tileChunkDiscoveryKnown) {
      if (!Minecraft.getInstance().isSameThread()) {
         throw new RuntimeException(new IllegalAccessException());
      }

      DimensionHighlighterHandler highlighterHandler = region.getDim().getHighlightHandler();
      this.prepare(
         region,
         x,
         z,
         highlighterHandler.shouldApplyRegionHighlights(region.getRegionX(), region.getRegionZ(), region.hasHadTerrain()),
         tileChunkDiscoveryKnown
      );
   }
}
