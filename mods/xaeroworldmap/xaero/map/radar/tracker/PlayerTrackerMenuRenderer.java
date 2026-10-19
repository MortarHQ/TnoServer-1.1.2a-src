package xaero.map.radar.tracker;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.client.controls.util.KeyMappingUtils;
import xaero.lib.client.gui.widget.Tooltip;
import xaero.map.WorldMap;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.config.util.WorldMapClientConfigUtils;
import xaero.map.element.MapElementMenuRenderer;
import xaero.map.element.render.ElementRenderer;
import xaero.map.gui.GuiMap;
import xaero.map.gui.GuiTexturedButton;

public final class PlayerTrackerMenuRenderer extends MapElementMenuRenderer<PlayerTrackerMapElement<?>, PlayerTrackerMenuRenderContext> {
   private final PlayerTrackerIconRenderer iconRenderer;
   private final PlayerTrackerMapElementRenderer renderer;
   private Button showPlayersButton;

   private PlayerTrackerMenuRenderer(
      PlayerTrackerMapElementRenderer renderer,
      PlayerTrackerIconRenderer iconRenderer,
      PlayerTrackerMenuRenderContext context,
      PlayerTrackerMapElementRenderProvider<PlayerTrackerMenuRenderContext> provider
   ) {
      super(context, provider);
      this.iconRenderer = iconRenderer;
      this.renderer = renderer;
   }

   @Override
   public void onMapInit(GuiMap screen, Minecraft mc, int width, int height) {
      super.onMapInit(screen, mc, width, height);
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      boolean trackedPlayers = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.DISPLAY_TRACKED_PLAYERS);
      Tooltip showPlayersTooltip = new Tooltip(
         Component.translatable(
            trackedPlayers ? "gui.xaero_box_showing_tracked_players" : "gui.xaero_box_hiding_tracked_players",
            new Object[]{
               Component.literal(KeyMappingUtils.getKeyName(screen.getTrackedPlayerKeyBinding())).withStyle(s -> s.withColor(ChatFormatting.DARK_GREEN))
            }
         ),
         true
      );
      screen.addButton(
         this.showPlayersButton = new GuiTexturedButton(
            width - 173,
            height - 33,
            20,
            20,
            trackedPlayers ? 197 : 213,
            48,
            16,
            16,
            WorldMap.guiTextures,
            b -> this.onShowPlayersButton(screen, width, height),
            () -> showPlayersTooltip
         )
      );
      this.showPlayersButton.active = !WorldMapClientConfigUtils.isOptionServerEnforced(WorldMapProfiledConfigOptions.DISPLAY_TRACKED_PLAYERS);
   }

   public void onShowPlayersButton(GuiMap screen, int width, int height) {
      WorldMapClientConfigUtils.tryTogglingCurrentProfileOption(WorldMapProfiledConfigOptions.DISPLAY_TRACKED_PLAYERS);
      screen.init(this.mc, width, height);
      screen.setFocused(this.showPlayersButton);
   }

   @Override
   protected void beforeMenuRender() {
   }

   @Override
   protected void afterMenuRender() {
   }

   public void renderInMenu(
      PlayerTrackerMapElement<?> element,
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
      PlayerTrackerMapElement<?> playerElement = element;
      PlayerInfo info = mc.getConnection().getPlayerInfo(playerElement.getPlayerId());
      if (info != null) {
         Player clientPlayer = mc.level.getPlayerByUUID(playerElement.getPlayerId());
         this.iconRenderer.renderIcon(guiGraphics, clientPlayer, this.renderer.getTrackedPlayerIconManager().getPlayerSkin(clientPlayer, info));
      }
   }

   @Override
   protected void beforeFiltering() {
   }

   @Override
   public int menuStartPos(int height) {
      return height - 59;
   }

   @Override
   public int menuSearchPadding() {
      return 1;
   }

   @Override
   protected String getFilterPlaceholder() {
      return "gui.xaero_filter_players_by_name";
   }

   protected ElementRenderer<? super PlayerTrackerMapElement<?>, ?, ?> getRenderer(PlayerTrackerMapElement<?> element) {
      return this.renderer;
   }

   public boolean canJumpTo(PlayerTrackerMapElement<?> element) {
      return !this.renderer.getReader().isHidden(element, this.renderer.getContext());
   }

   public static final class Builder {
      private PlayerTrackerMapElementRenderer renderer;

      private Builder() {
      }

      private PlayerTrackerMenuRenderer.Builder setDefault() {
         this.setRenderer(null);
         return this;
      }

      public PlayerTrackerMenuRenderer.Builder setRenderer(PlayerTrackerMapElementRenderer renderer) {
         this.renderer = renderer;
         return this;
      }

      public PlayerTrackerMenuRenderer build() {
         if (this.renderer == null) {
            throw new IllegalStateException();
         } else {
            return new PlayerTrackerMenuRenderer(
               this.renderer,
               new PlayerTrackerIconRenderer(),
               new PlayerTrackerMenuRenderContext(),
               new PlayerTrackerMapElementRenderProvider<>(this.renderer.getCollector())
            );
         }
      }

      public static PlayerTrackerMenuRenderer.Builder begin() {
         return new PlayerTrackerMenuRenderer.Builder().setDefault();
      }
   }
}
