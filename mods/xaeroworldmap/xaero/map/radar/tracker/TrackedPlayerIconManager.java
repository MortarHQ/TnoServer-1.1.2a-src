package xaero.map.radar.tracker;

import com.mojang.blaze3d.platform.GlStateManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import xaero.map.icon.XaeroIcon;
import xaero.map.icon.XaeroIconAtlasManager;

public final class TrackedPlayerIconManager {
   private static final int ICON_WIDTH = 32;
   private static final int PREFERRED_ATLAS_WIDTH = 1024;
   private final TrackedPlayerIconPrerenderer prerenderer;
   private final XaeroIconAtlasManager iconAtlasManager;
   private final Map<ResourceLocation, XaeroIcon> icons;
   private final int iconWidth;

   private TrackedPlayerIconManager(
      TrackedPlayerIconPrerenderer prerenderer, XaeroIconAtlasManager iconAtlasManager, Map<ResourceLocation, XaeroIcon> icons, int iconWidth
   ) {
      this.prerenderer = prerenderer;
      this.iconAtlasManager = iconAtlasManager;
      this.icons = icons;
      this.iconWidth = iconWidth;
   }

   public ResourceLocation getPlayerSkin(Player player, PlayerInfo info) {
      ResourceLocation skinTextureLocation = player instanceof AbstractClientPlayer
         ? ((AbstractClientPlayer)player).getSkin().texture()
         : info.getSkin().texture();
      if (skinTextureLocation == null) {
         skinTextureLocation = DefaultPlayerSkin.get(player.getUUID()).texture();
      }

      return skinTextureLocation;
   }

   public XaeroIcon getIcon(GuiGraphics guiGraphics, Player player, PlayerInfo info, PlayerTrackerMapElement<?> element) {
      ResourceLocation skinTextureLocation = this.getPlayerSkin(player, info);
      XaeroIcon result = this.icons.get(skinTextureLocation);
      if (result == null) {
         this.icons.put(skinTextureLocation, result = this.iconAtlasManager.getCurrentAtlas().createIcon());
         this.prerenderer.prerender(guiGraphics, result, player, this.iconWidth, skinTextureLocation, element);
      }

      return result;
   }

   public static final class Builder {
      public TrackedPlayerIconManager build() {
         int maxTextureSize = GlStateManager._getInteger(3379);
         int atlasTextureSize = Math.min(maxTextureSize, 1024) / 32 * 32;
         return new TrackedPlayerIconManager(
            new TrackedPlayerIconPrerenderer(), new XaeroIconAtlasManager(32, atlasTextureSize, new ArrayList<>()), new HashMap<>(), 32
         );
      }

      public static TrackedPlayerIconManager.Builder begin() {
         return new TrackedPlayerIconManager.Builder();
      }
   }
}
