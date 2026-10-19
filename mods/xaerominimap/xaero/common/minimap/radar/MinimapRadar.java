package xaero.common.minimap.radar;

import java.util.Iterator;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import xaero.common.minimap.MinimapProcessor;
import xaero.hud.entity.EntityUtils;
import xaero.hud.minimap.radar.RadarSession;
import xaero.hud.minimap.radar.category.EntityRadarCategory;
import xaero.hud.minimap.radar.category.EntityRadarCategoryManager;
import xaero.hud.minimap.radar.color.RadarColor;

@Deprecated
public class MinimapRadar extends RadarSession {
   @Deprecated
   public MinimapRadar(EntityRadarCategoryManager entityCategoryManager) {
      super(entityCategoryManager);
   }

   @Deprecated
   public void updateRadar(ClientLevel world, Player p, Entity renderEntity, MinimapProcessor minimap) {
      this.update(world, renderEntity, p);
   }

   @Deprecated
   public double getEntityX(Entity e, float partial) {
      return EntityUtils.getEntityX(e, partial);
   }

   @Deprecated
   public double getEntityY(Entity e, float partial) {
      return EntityUtils.getEntityY(e, partial);
   }

   @Deprecated
   public double getEntityZ(Entity e, float partial) {
      return EntityUtils.getEntityZ(e, partial);
   }

   @Deprecated
   public int getTeamColour(Entity e) {
      return this.getColorHelper().getTeamColor(e);
   }

   @Deprecated
   public int getEntityColour(
      Player p, Entity e, float offh, boolean cave, EntityRadarCategory category, int heightLimit, int startFadingAt, boolean heightBasedFade, int colorIndex
   ) {
      return this.getColorHelper()
         .getEntityColor(
            e,
            offh,
            cave,
            heightLimit,
            startFadingAt,
            heightBasedFade,
            RadarColor.fromIndex(colorIndex),
            this.getColorHelper().getFallbackColor(category, null)
         );
   }

   @Deprecated
   public float getEntityBrightness(float offh, int heightLimit, int startFadingAt, boolean heightBasedFade) {
      return !heightBasedFade ? 1.0F : this.getColorHelper().getEntityHeightFade(offh, heightLimit, startFadingAt);
   }

   @Deprecated
   public void setLastRenderViewEntity(Entity lastRenderViewEntity) {
      this.getStateUpdater().setLastRenderViewEntity(lastRenderViewEntity);
   }

   @Deprecated
   public EntityRadarCategoryManager getEntityCategoryManager() {
      return this.getCategoryManager();
   }

   @Deprecated
   public Iterator<MinimapRadarList> getRadarListsIterator() {
      return this.getState().getRadarLists().iterator();
   }

   @Deprecated
   public double getMaxDistance(MinimapProcessor minimap, boolean circle) {
      return xaero.hud.minimap.radar.util.RadarUtils.getMaxDistance(minimap, circle);
   }
}
