package xaero.hud.minimap.radar.render.element;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.Entity;
import xaero.common.HudMod;
import xaero.common.minimap.element.render.MinimapElementRenderProvider;
import xaero.common.settings.ModSettings;
import xaero.hud.minimap.BuiltInHudModules;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.element.render.MinimapElementRenderLocation;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.minimap.radar.category.setting.EntityRadarCategorySettings;
import xaero.hud.minimap.radar.state.RadarList;

public abstract class RadarRenderProvider extends MinimapElementRenderProvider<Entity, RadarRenderContext> {
   private boolean used;
   private Entity renderEntity;
   private Iterator<RadarList> entityLists;
   private RadarList currentList;
   private RadarList listForContext;
   private int currentListIndex;

   public void begin(MinimapElementRenderLocation location, RadarRenderContext context) {
      MinimapSession minimapSession = BuiltInHudModules.MINIMAP.getCurrentSession();
      this.used = true;
      this.renderEntity = Minecraft.getInstance().getCameraEntity();
      context.reversedOrder = ModSettings.keyReverseEntityRadar.isDown();
      Screen screenBU = Minecraft.getInstance().screen;
      Minecraft.getInstance().screen = null;
      context.playerListDown = Minecraft.getInstance().options.keyPlayerList.isDown() || ModSettings.keyAlternativeListPlayers.isDown();
      Minecraft.getInstance().screen = screenBU;
      this.entityLists = minimapSession.getRadarSession().getState().getRadarLists().iterator();
      this.currentList = null;
      this.listForContext = null;
      this.currentListIndex = 0;
   }

   private void ensureList(MinimapElementRenderLocation location, RadarRenderContext context) {
      label38:
      while (this.currentList == null || this.currentListIndex >= this.currentList.size() || this.currentListIndex < 0) {
         while (this.entityLists.hasNext()) {
            this.currentList = this.entityLists.next();
            this.currentListIndex = context.reversedOrder ? this.currentList.size() - 1 : 0;
            if (location != MinimapElementRenderLocation.IN_MINIMAP && location != MinimapElementRenderLocation.OVER_MINIMAP
               || location == MinimapElementRenderLocation.OVER_MINIMAP == this.shouldRenderOverMinimap(context)) {
               continue label38;
            }
         }

         this.currentList = null;
         this.currentListIndex = 0;
         break;
      }
   }

   private boolean shouldRenderOverMinimap(RadarRenderContext context) {
      int settingValue = this.currentList.getEffective(EntityRadarCategorySettings.RENDER_OVER_MINIMAP).intValue();
      return settingValue == 2 || settingValue == 1 && context.playerListDown;
   }

   public boolean hasNext(MinimapElementRenderLocation location, RadarRenderContext context) {
      this.ensureList(location, context);
      return this.currentList == null
         ? false
         : !context.reversedOrder && this.currentListIndex < this.currentList.size() || context.reversedOrder && this.currentListIndex >= 0;
   }

   public Entity setupContextAndGetNext(MinimapElementRenderLocation location, RadarRenderContext context) {
      this.ensureList(location, context);
      if (this.listForContext != this.currentList) {
         context.radarList = this.currentList;
         this.setupContextForList(this.currentList, context);
         this.listForContext = this.currentList;
      }

      Entity result = this.getNext(location, context);
      if (result == null) {
         return null;
      }

      this.setupContextForEntity(result, context);
      return result;
   }

   public Entity getNext(MinimapElementRenderLocation location, RadarRenderContext context) {
      Entity result = this.currentList.get(this.currentListIndex);
      this.currentListIndex = this.currentListIndex + (context.reversedOrder ? -1 : 1);
      return this.renderEntity == result ? null : result;
   }

   public void end(MinimapElementRenderLocation location, RadarRenderContext context) {
      this.used = false;
      this.renderEntity = null;
      context.radarList = null;
   }

   public void setupContextForList(RadarList radarList, RadarRenderContext context) {
      context.iconScale = radarList.getEffective(EntityRadarCategorySettings.ICON_SCALE);
      context.dotSize = context.isMainDot
         ? (Integer)HudMod.INSTANCE.getHudConfigs().getClientConfigManager().getEffective(MinimapProfiledConfigOptions.RADAR_MAIN_DOT_SIZE)
         : radarList.getEffective(EntityRadarCategorySettings.DOT_SIZE).intValue();
      context.dotScale = 1.0 + 0.5 * (context.dotSize - 1);
      int icons = radarList.getEffective(EntityRadarCategorySettings.ICONS).intValue();
      context.iconsForCategory = icons == 1 && context.playerListDown || icons == 2;
   }

   public void setupContextForEntity(Entity entity, RadarRenderContext context) {
      context.icon = context.iconsForCategory;
   }

   public boolean isUsed() {
      return this.used;
   }
}
