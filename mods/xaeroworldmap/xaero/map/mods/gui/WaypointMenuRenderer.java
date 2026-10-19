package xaero.map.mods.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.io.IOException;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import xaero.common.HudMod;
import xaero.common.IXaeroMinimap;
import xaero.common.XaeroMinimapSession;
import xaero.common.gui.GuiNewSet;
import xaero.common.gui.GuiWaypointSets;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.settings.ModSettings;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.client.controls.util.KeyMappingUtils;
import xaero.lib.client.gui.widget.Tooltip;
import xaero.lib.client.gui.widget.dropdown.DropDownWidget;
import xaero.lib.client.gui.widget.dropdown.IDropDownWidgetCallback;
import xaero.lib.client.gui.widget.dropdown.DropDownWidget.Builder;
import xaero.lib.common.config.Config;
import xaero.lib.common.config.single.SingleConfigManager;
import xaero.map.WorldMap;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.config.primary.option.WorldMapPrimaryClientConfigOptions;
import xaero.map.config.util.WorldMapClientConfigUtils;
import xaero.map.element.MapElementMenuRenderer;
import xaero.map.element.render.ElementRenderer;
import xaero.map.gui.GuiMap;
import xaero.map.gui.GuiTexturedButton;
import xaero.map.mods.SupportMods;

public class WaypointMenuRenderer extends MapElementMenuRenderer<Waypoint, WaypointMenuRenderContext> {
   private final WaypointRenderer renderer;
   private Button renderWaypointsButton;
   private Button showDisabledButton;
   private Button closeMenuWhenHoppingButton;
   private Button currentMapWaypointsButton;
   private Button renderAllSetsButton;

   public WaypointMenuRenderer(WaypointMenuRenderContext context, WaypointMenuRenderProvider provider, WaypointRenderer renderer) {
      super(context, provider);
      this.renderer = renderer;
   }

   public void onMapInit(
      GuiMap screen, Minecraft mc, int width, int height, WaypointWorld waypointWorld, IXaeroMinimap modMain, XaeroMinimapSession minimapSession
   ) {
      super.onMapInit(screen, mc, width, height);
      GuiWaypointSets sets = waypointWorld != null ? new GuiWaypointSets(true, waypointWorld) : null;
      IDropDownWidgetCallback setsDropdownCallback = null;
      if (sets != null) {
         setsDropdownCallback = (menu, selected) -> {
            if (selected == menu.size() - 1) {
               GuiNewSet guiNewSet = new GuiNewSet(modMain, minimapSession, screen, screen, waypointWorld);
               Minecraft.getInstance().setScreen(guiNewSet);
               return false;
            }

            sets.setCurrentSet(selected);
            waypointWorld.setCurrent(sets.getCurrentSetKey());
            minimapSession.getWaypointsManager().updateWaypoints();

            try {
               modMain.getSettings().saveWaypoints(waypointWorld);
            } catch (IOException e) {
               WorldMap.LOGGER.error("suppressed exception", e);
            }

            return true;
         };
      }

      DropDownWidget setsDropdown = sets == null
         ? null
         : Builder.begin()
            .setOptions(sets.getOptions())
            .setX(width - 173)
            .setY(height - 56)
            .setW(151)
            .setSelected(sets.getCurrentSet())
            .setCallback(setsDropdownCallback)
            .setContainer(screen)
            .setOpeningUp(true)
            .setNarrationTitle(Component.translatable("gui.xaero_dropdown_waypoint_set"))
            .build();
      if (setsDropdown != null) {
         screen.addWidget(setsDropdown);
      }

      MutableComponent fullWaypointMenuTooltipText = Component.translatable(
         "gui.xaero_box_full_waypoints_menu",
         new Object[]{Component.literal(KeyMappingUtils.getKeyName(ModSettings.keyWaypoints)).withStyle(ChatFormatting.DARK_GREEN)}
      );
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      boolean renderWaypoints = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.RENDER_WAYPOINTS);
      SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
      boolean onlyCurrentMapWaypoints = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.ONLY_CURRENT_MAP_WAYPOINTS);
      boolean showDisabledWaypoints = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.DISPLAY_DISABLED_WAYPOINTS);
      boolean closeWaypointsWhenHopping = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.CLOSE_WAYPOINTS_AFTER_HOP);
      Tooltip fullWaypointMenuTooltip = new Tooltip(fullWaypointMenuTooltipText, true);
      Tooltip onlyCurrentMapWaypointsTooltip = new Tooltip(
         onlyCurrentMapWaypoints ? "gui.xaero_box_only_current_map_waypoints" : "gui.xaero_box_waypoints_selected_by_minimap", Style.EMPTY, true
      );
      Tooltip renderingWaypointsTooltip = new Tooltip(
         Component.translatable(
            renderWaypoints ? "gui.xaero_box_rendering_waypoints" : "gui.xaero_box_not_rendering_waypoints",
            new Object[]{Component.literal(KeyMappingUtils.getKeyName(ModSettings.keyToggleMapWaypoints)).withStyle(ChatFormatting.DARK_GREEN)}
         ),
         true
      );
      ClientConfigManager minimapConfigManager = HudMod.INSTANCE.getHudConfigs().getClientConfigManager();
      boolean renderAllSetsConfig = (Boolean)minimapConfigManager.getEffective(MinimapProfiledConfigOptions.WAYPOINTS_ALL_SETS);
      Tooltip renderAllSetsTooltip = new Tooltip(
         Component.translatable(
            !renderAllSetsConfig ? "gui.xaero_box_rendering_current_set" : "gui.xaero_box_rendering_all_sets",
            new Object[]{Component.literal(KeyMappingUtils.getKeyName(ModSettings.keyAllSets)).withStyle(ChatFormatting.DARK_GREEN)}
         ),
         true
      );
      Tooltip showingDisabledTooltip = new Tooltip(
         showDisabledWaypoints ? "gui.xaero_box_showing_disabled" : "gui.xaero_box_hiding_disabled", Style.EMPTY, true
      );
      Tooltip closeWhenHoppingTooltip = new Tooltip(
         closeWaypointsWhenHopping ? "gui.xaero_box_closing_menu_when_hopping" : "gui.xaero_box_not_closing_menu_when_hopping", Style.EMPTY, true
      );
      screen.addButton(
         new GuiTexturedButton(
            width - 173, height - 20, 20, 20, 229, 0, 16, 16, WorldMap.guiTextures, b -> this.onFullMenuButton(b, screen), () -> fullWaypointMenuTooltip
         )
      );
      screen.addButton(
         this.currentMapWaypointsButton = new GuiTexturedButton(
            width - 153,
            height - 20,
            20,
            20,
            onlyCurrentMapWaypoints ? 213 : 229,
            16,
            16,
            16,
            WorldMap.guiTextures,
            b -> this.onCurrentMapWaypointsButton(b, screen, width, height),
            () -> onlyCurrentMapWaypointsTooltip
         )
      );
      screen.addButton(
         this.renderWaypointsButton = new GuiTexturedButton(
            width - 133,
            height - 20,
            20,
            20,
            renderWaypoints ? 229 : 213,
            48,
            16,
            16,
            WorldMap.guiTextures,
            b -> this.onRenderWaypointsButton(screen, width, height),
            () -> renderingWaypointsTooltip
         )
      );
      screen.addButton(
         this.renderAllSetsButton = new GuiTexturedButton(
            width - 113,
            height - 20,
            20,
            20,
            !renderAllSetsConfig ? 81 : 97,
            16,
            16,
            16,
            WorldMap.guiTextures,
            b -> this.onRenderAllSetsButton(b, screen, width, height),
            () -> renderAllSetsTooltip
         )
      );
      screen.addButton(
         this.showDisabledButton = new GuiTexturedButton(
            width - 93,
            height - 20,
            20,
            20,
            showDisabledWaypoints ? 133 : 149,
            16,
            16,
            16,
            WorldMap.guiTextures,
            b -> this.onShowDisabledButton(b, screen, width, height),
            () -> showingDisabledTooltip
         )
      );
      screen.addButton(
         this.closeMenuWhenHoppingButton = new GuiTexturedButton(
            width - 73,
            height - 20,
            20,
            20,
            closeWaypointsWhenHopping ? 181 : 197,
            16,
            16,
            16,
            WorldMap.guiTextures,
            b -> this.onCloseMenuWhenHoppingButton(b, screen, width, height),
            () -> closeWhenHoppingTooltip
         )
      );
      this.renderWaypointsButton.active = !WorldMapClientConfigUtils.isOptionServerEnforced(WorldMapProfiledConfigOptions.RENDER_WAYPOINTS);
   }

   public void onRenderWaypointsButton(GuiMap screen, int width, int height) {
      WorldMapClientConfigUtils.tryTogglingCurrentProfileOption(WorldMapProfiledConfigOptions.RENDER_WAYPOINTS);
      screen.init(this.mc, width, height);
      screen.setFocused(this.renderWaypointsButton);
   }

   private void onFullMenuButton(Button b, GuiMap screen) {
      SupportMods.xaeroMinimap.openWaypointsMenu(this.mc, screen);
   }

   private void onRenderAllSetsButton(Button b, GuiMap screen, int width, int height) {
      SupportMods.xaeroMinimap.handleMinimapKeyBinding(ModSettings.keyAllSets, screen);
      screen.setFocused(this.renderAllSetsButton);
   }

   private void onShowDisabledButton(Button b, GuiMap screen, int width, int height) {
      WorldMapClientConfigUtils.togglePrimaryOption(WorldMapPrimaryClientConfigOptions.DISPLAY_DISABLED_WAYPOINTS);
      screen.init(this.mc, width, height);
      screen.setFocused(this.showDisabledButton);
   }

   private void onCloseMenuWhenHoppingButton(Button b, GuiMap screen, int width, int height) {
      WorldMapClientConfigUtils.togglePrimaryOption(WorldMapPrimaryClientConfigOptions.CLOSE_WAYPOINTS_AFTER_HOP);
      screen.init(this.mc, width, height);
      screen.setFocused(this.closeMenuWhenHoppingButton);
   }

   private void onCurrentMapWaypointsButton(Button b, GuiMap screen, int width, int height) {
      WorldMapClientConfigUtils.togglePrimaryOption(WorldMapPrimaryClientConfigOptions.ONLY_CURRENT_MAP_WAYPOINTS);
      screen.init(this.mc, width, height);
      screen.setFocused(this.currentMapWaypointsButton);
   }

   public void renderInMenu(
      Waypoint element,
      GuiGraphics guiGraphics,
      Screen gui,
      int mouseX,
      int mouseY,
      double scale,
      boolean enabled,
      boolean hovered,
      Minecraft mc,
      boolean pressed,
      int textX
   ) {
      PoseStack matrixStack = guiGraphics.pose();
      Waypoint w = element;
      boolean disabled = w.isDisabled();
      boolean temporary = w.isTemporary();
      int type = w.getType();
      int color = w.getColor();
      String symbol = w.getSymbol();
      matrixStack.translate(-4.0F, -4.0F, 0.0F);
      RenderSystem.enableBlend();
      if (type == 1) {
         guiGraphics.fill(0, 0, 9, 9, color);
         AbstractTexture texture = mc.getTextureManager().getTexture(Waypoint.minimapTextures);
         texture.setFilter(false, false);
         RenderSystem.setShaderColor(0.2431F, 0.2431F, 0.2431F, 1.0F);
         guiGraphics.blit(Waypoint.minimapTextures, 1, 1, 0, 78, 9, 9);
         RenderSystem.setShaderColor(0.9882F, 0.9882F, 0.9882F, 1.0F);
         guiGraphics.blit(Waypoint.minimapTextures, 0, 0, 0, 78, 9, 9);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         guiGraphics.fill(0, 0, 9, 9, color);
      }

      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      if (type != 1) {
         guiGraphics.drawString(mc.font, symbol, 5 - mc.font.width(symbol) / 2, 1, -1);
      }

      RenderSystem.enableBlend();
      int infoIconOffset = 10;
      if (disabled) {
         RenderSystem.setShaderColor(1.0F, 1.0F, 0.0F, 1.0F);
         guiGraphics.blit(WorldMap.guiTextures, textX - 1 - infoIconOffset, 0, 173, 16, 8, 8);
         infoIconOffset += 10;
      }

      if (temporary) {
         RenderSystem.setShaderColor(1.0F, 0.0F, 0.0F, 1.0F);
         guiGraphics.blit(WorldMap.guiTextures, textX - 1 - infoIconOffset, 0, 165, 16, 8, 8);
         infoIconOffset += 10;
      }

      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public int menuStartPos(int height) {
      return height - 59;
   }

   @Override
   public int menuSearchPadding() {
      return 14;
   }

   @Override
   protected String getFilterPlaceholder() {
      return "gui.xaero_filter_waypoints_by_name";
   }

   protected ElementRenderer<? super Waypoint, ?, ?> getRenderer(Waypoint element) {
      return this.renderer;
   }

   @Override
   protected void beforeFiltering() {
   }

   @Override
   protected void beforeMenuRender() {
   }

   @Override
   protected void afterMenuRender() {
   }
}
