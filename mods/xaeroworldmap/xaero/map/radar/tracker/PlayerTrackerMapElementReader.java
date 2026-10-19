package xaero.map.radar.tracker;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import xaero.lib.client.config.ClientConfigManager;
import xaero.map.WorldMap;
import xaero.map.WorldMapSession;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.element.MapElementReader;
import xaero.map.element.render.ElementRenderLocation;
import xaero.map.gui.IRightClickableElement;
import xaero.map.gui.dropdown.rightclick.RightClickOption;
import xaero.map.mods.SupportMods;

public class PlayerTrackerMapElementReader
   extends MapElementReader<PlayerTrackerMapElement<?>, PlayerTrackerMapElementRenderContext, PlayerTrackerMapElementRenderer> {
   public boolean isHidden(PlayerTrackerMapElement<?> element, PlayerTrackerMapElementRenderContext context) {
      return Minecraft.getInstance().level.dimension() != element.getDimension() && context.mapDimId != element.getDimension();
   }

   public double getRenderX(PlayerTrackerMapElement<?> element, PlayerTrackerMapElementRenderContext context, float partialTicks) {
      return Minecraft.getInstance().level.dimension() != element.getDimension() ? element.getX() * context.mapDimDiv : element.getX();
   }

   public double getRenderZ(PlayerTrackerMapElement<?> element, PlayerTrackerMapElementRenderContext context, float partialTicks) {
      return Minecraft.getInstance().level.dimension() != element.getDimension() ? element.getZ() * context.mapDimDiv : element.getZ();
   }

   public int getInteractionBoxLeft(PlayerTrackerMapElement<?> element, PlayerTrackerMapElementRenderContext context, float partialTicks) {
      return -16;
   }

   public int getInteractionBoxRight(PlayerTrackerMapElement<?> element, PlayerTrackerMapElementRenderContext context, float partialTicks) {
      return 16;
   }

   public int getInteractionBoxTop(PlayerTrackerMapElement<?> element, PlayerTrackerMapElementRenderContext context, float partialTicks) {
      return -16;
   }

   public int getInteractionBoxBottom(PlayerTrackerMapElement<?> element, PlayerTrackerMapElementRenderContext context, float partialTicks) {
      return 16;
   }

   public int getRenderBoxLeft(PlayerTrackerMapElement<?> element, PlayerTrackerMapElementRenderContext context, float partialTicks) {
      return -20;
   }

   public int getRenderBoxRight(PlayerTrackerMapElement<?> element, PlayerTrackerMapElementRenderContext context, float partialTicks) {
      return 20;
   }

   public int getRenderBoxTop(PlayerTrackerMapElement<?> element, PlayerTrackerMapElementRenderContext context, float partialTicks) {
      return -20;
   }

   public int getRenderBoxBottom(PlayerTrackerMapElement<?> element, PlayerTrackerMapElementRenderContext context, float partialTicks) {
      return 20;
   }

   public int getLeftSideLength(PlayerTrackerMapElement<?> element, Minecraft mc) {
      PlayerInfo info = Minecraft.getInstance().getConnection().getPlayerInfo(element.getPlayerId());
      return info == null ? 9 : 9 + mc.font.width(info.getProfile().getName());
   }

   public String getMenuName(PlayerTrackerMapElement<?> element) {
      PlayerInfo info = Minecraft.getInstance().getConnection().getPlayerInfo(element.getPlayerId());
      return info == null ? element.getPlayerId() + "" : info.getProfile().getName();
   }

   public String getFilterName(PlayerTrackerMapElement<?> element) {
      return this.getMenuName(element);
   }

   public int getMenuTextFillLeftPadding(PlayerTrackerMapElement<?> element) {
      return 0;
   }

   public int getRightClickTitleBackgroundColor(PlayerTrackerMapElement<?> element) {
      return -11184641;
   }

   @Override
   public boolean shouldScaleBoxWithOptionalScale() {
      return true;
   }

   public boolean isRightClickValid(PlayerTrackerMapElement<?> element) {
      return WorldMap.trackedPlayerRenderer.getCollector().playerExists(element.getPlayerId());
   }

   public ArrayList<RightClickOption> getRightClickOptions(final PlayerTrackerMapElement<?> element, IRightClickableElement target) {
      ArrayList<RightClickOption> rightClickOptions = new ArrayList<>();
      rightClickOptions.add(new RightClickOption(this.getMenuName(element), rightClickOptions.size(), target) {
         @Override
         public void onAction(Screen screen) {
         }
      });
      rightClickOptions.add(
         new RightClickOption("", rightClickOptions.size(), target) {
            @Override
            public String getName() {
               ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
               return !configManager.getEffective(WorldMapProfiledConfigOptions.COORDINATES)
                  ? "hidden"
                  : String.format("X: %d, Y: %s, Z: %d", (int)Math.floor(element.getX()), (int)Math.floor(element.getY()), (int)Math.floor(element.getZ()));
            }

            @Override
            public void onAction(Screen screen) {
            }
         }
      );
      rightClickOptions.add((new RightClickOption("gui.xaero_right_click_player_teleport", rightClickOptions.size(), target) {
         @Override
         public void onAction(Screen screen) {
            WorldMapSession session = WorldMapSession.getCurrentSession();
            new PlayerTeleporter().teleportToPlayer(screen, session.getMapProcessor().getMapWorld(), element);
         }
      }).setNameFormatArgs("T"));
      if (SupportMods.pac()) {
         rightClickOptions.add((new RightClickOption("gui.xaero_right_click_player_config", rightClickOptions.size(), target) {
            @Override
            public void onAction(Screen screen) {
               SupportMods.xaeroPac.openPlayerConfigScreen(screen, screen, element);
            }

            @Override
            public boolean isActive() {
               return Minecraft.getInstance().player.hasPermissions(2) && Minecraft.getInstance().getConnection().getPlayerInfo(element.getPlayerId()) != null;
            }
         }).setNameFormatArgs("C"));
      }

      return rightClickOptions;
   }

   @Deprecated
   public boolean isInteractable(int location, PlayerTrackerMapElement<?> element) {
      return this.isInteractable(ElementRenderLocation.fromIndex(location), element);
   }

   public boolean isInteractable(ElementRenderLocation location, PlayerTrackerMapElement<?> element) {
      return true;
   }
}
