package xaero.map.mods.pac;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import xaero.map.WorldMapSession;
import xaero.map.world.MapDimension;
import xaero.pac.common.claims.player.api.IPlayerChunkClaimAPI;
import xaero.pac.common.claims.tracker.api.IClaimsManagerListenerAPI;

public class ClientClaimChangeListener implements IClaimsManagerListenerAPI {
   public void onWholeRegionChange(ResourceLocation dimension, int regionX, int regionZ) {
      WorldMapSession session = WorldMapSession.getCurrentSession();
      MapDimension mapDim = session.getMapProcessor().getMapWorld().getDimension(ResourceKey.create(Registries.DIMENSION, dimension));
      if (mapDim != null) {
         for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
               if (i == 0 && j == 0 || i * i != j * j) {
                  mapDim.getHighlightHandler().clearCachedHash(regionX + i, regionZ + j);
               }
            }
         }
      }
   }

   public void onChunkChange(ResourceLocation dimension, int chunkX, int chunkZ, IPlayerChunkClaimAPI claim) {
      WorldMapSession session = WorldMapSession.getCurrentSession();
      MapDimension mapDim = session.getMapProcessor().getMapWorld().getDimension(ResourceKey.create(Registries.DIMENSION, dimension));
      if (mapDim != null) {
         for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
               if (i == 0 && j == 0 || i * i != j * j) {
                  mapDim.getHighlightHandler().clearCachedHash(chunkX + i >> 5, chunkZ + j >> 5);
               }
            }
         }
      }
   }

   public void onDimensionChange(ResourceLocation dimension) {
      WorldMapSession session = WorldMapSession.getCurrentSession();
      MapDimension mapDim = session.getMapProcessor().getMapWorld().getDimension(ResourceKey.create(Registries.DIMENSION, dimension));
      if (mapDim != null) {
         mapDim.getHighlightHandler().clearCachedHashes();
      }
   }
}
