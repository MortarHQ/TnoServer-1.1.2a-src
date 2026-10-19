package xaero.hud.minimap.player.tracker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.entity.player.Player;
import xaero.hud.entity.EntityUtils;
import xaero.hud.minimap.element.render.MinimapElementReader;
import xaero.hud.minimap.element.render.MinimapElementRenderInfo;
import xaero.hud.minimap.element.render.MinimapElementRenderLocation;

public class PlayerTrackerMinimapElementReader extends MinimapElementReader<PlayerTrackerMinimapElement<?>, PlayerTrackerMinimapElementRenderContext> {
   public boolean isHidden(PlayerTrackerMinimapElement<?> element, PlayerTrackerMinimapElementRenderContext context) {
      return context.renderEntityDimId != element.getDimension() && context.mapDimId != element.getDimension();
   }

   public double getRenderX(PlayerTrackerMinimapElement<?> element, PlayerTrackerMinimapElementRenderContext context, float partialTicks) {
      Minecraft mc = Minecraft.getInstance();
      Player clientPlayer = mc.level.getPlayerByUUID(element.getPlayerId());
      return clientPlayer == null ? element.getX() : EntityUtils.getEntityX(clientPlayer, partialTicks);
   }

   public double getRenderY(PlayerTrackerMinimapElement<?> element, PlayerTrackerMinimapElementRenderContext context, float partialTicks) {
      Minecraft mc = Minecraft.getInstance();
      Player clientPlayer = mc.level.getPlayerByUUID(element.getPlayerId());
      return clientPlayer == null ? element.getY() : EntityUtils.getEntityY(clientPlayer, partialTicks);
   }

   public double getRenderZ(PlayerTrackerMinimapElement<?> element, PlayerTrackerMinimapElementRenderContext context, float partialTicks) {
      Minecraft mc = Minecraft.getInstance();
      Player clientPlayer = mc.level.getPlayerByUUID(element.getPlayerId());
      return clientPlayer == null ? element.getZ() : EntityUtils.getEntityZ(clientPlayer, partialTicks);
   }

   public double getCoordinateScale(
      PlayerTrackerMinimapElement<?> element, PlayerTrackerMinimapElementRenderContext context, MinimapElementRenderInfo renderInfo
   ) {
      return element.getDimension() == renderInfo.renderEntityDimension ? renderInfo.renderEntityDimensionScale : renderInfo.backgroundCoordinateScale;
   }

   public int getInteractionBoxLeft(PlayerTrackerMinimapElement<?> element, PlayerTrackerMinimapElementRenderContext context, float partialTicks) {
      return -10;
   }

   public int getInteractionBoxRight(PlayerTrackerMinimapElement<?> element, PlayerTrackerMinimapElementRenderContext context, float partialTicks) {
      return 10;
   }

   public int getInteractionBoxTop(PlayerTrackerMinimapElement<?> element, PlayerTrackerMinimapElementRenderContext context, float partialTicks) {
      return -10;
   }

   public int getInteractionBoxBottom(PlayerTrackerMinimapElement<?> element, PlayerTrackerMinimapElementRenderContext context, float partialTicks) {
      return 10;
   }

   public int getRenderBoxLeft(PlayerTrackerMinimapElement<?> element, PlayerTrackerMinimapElementRenderContext context, float partialTicks) {
      return -20;
   }

   public int getRenderBoxRight(PlayerTrackerMinimapElement<?> element, PlayerTrackerMinimapElementRenderContext context, float partialTicks) {
      return 20;
   }

   public int getRenderBoxTop(PlayerTrackerMinimapElement<?> element, PlayerTrackerMinimapElementRenderContext context, float partialTicks) {
      return -20;
   }

   public int getRenderBoxBottom(PlayerTrackerMinimapElement<?> element, PlayerTrackerMinimapElementRenderContext context, float partialTicks) {
      return 20;
   }

   public int getLeftSideLength(PlayerTrackerMinimapElement<?> element, Minecraft mc) {
      PlayerInfo info = Minecraft.getInstance().getConnection().getPlayerInfo(element.getPlayerId());
      return info == null ? 9 : 9 + mc.font.width(info.getProfile().getName());
   }

   public String getMenuName(PlayerTrackerMinimapElement<?> element) {
      PlayerInfo info = Minecraft.getInstance().getConnection().getPlayerInfo(element.getPlayerId());
      return info == null ? element.getPlayerId() + "" : info.getProfile().getName();
   }

   public String getFilterName(PlayerTrackerMinimapElement<?> element) {
      return this.getMenuName(element);
   }

   public int getMenuTextFillLeftPadding(PlayerTrackerMinimapElement<?> element) {
      return 0;
   }

   public int getRightClickTitleBackgroundColor(PlayerTrackerMinimapElement<?> element) {
      return -11184641;
   }

   @Override
   public boolean shouldScaleBoxWithOptionalScale() {
      return true;
   }

   public float getBoxScale(MinimapElementRenderLocation location, PlayerTrackerMinimapElement<?> element, PlayerTrackerMinimapElementRenderContext context) {
      return context.iconScale;
   }

   public boolean isInteractable(MinimapElementRenderLocation location, PlayerTrackerMinimapElement<?> element) {
      return true;
   }
}
