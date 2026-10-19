package xaero.map.events;

import net.minecraft.client.renderer.texture.TextureAtlas;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.WorldMapSession;
import xaero.map.mods.SupportMods;

public class ModClientEvents {
   private boolean listenToTextureStitch;

   public void handleTextureStitchEventPost(TextureAtlas atlasTexture) {
      if (atlasTexture.location() == TextureAtlas.LOCATION_BLOCKS) {
         boolean shouldListenToStitch = this.listenToTextureStitch;
         this.listenToTextureStitch = true;
         WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
         if (worldmapSession != null) {
            MapProcessor mapProcessor = worldmapSession.getMapProcessor();
            if (shouldListenToStitch) {
               mapProcessor.getMapWriter().requestCachedColoursClear();
               mapProcessor.getBlockStateShortShapeCache().reset();
            }
         }

         if (shouldListenToStitch) {
            if (SupportMods.minimap()) {
               WorldMap.waypointSymbolCreator.resetChars();
            }

            if (WorldMap.settings != null) {
               WorldMap.settings.updateRegionCacheHashCode();
            }
         }
      }
   }
}
