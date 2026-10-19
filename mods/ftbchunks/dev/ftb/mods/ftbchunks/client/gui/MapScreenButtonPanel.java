package dev.ftb.mods.ftbchunks.client.gui;

import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.map.WaypointType;
import dev.ftb.mods.ftblibrary.config.manager.ConfigManagerClient;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.DropDownMenu;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleButton;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.SimpleButton.Callback;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.KeyReferenceScreen;
import dev.ftb.mods.ftblibrary.util.TextComponentUtils;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

class MapScreenButtonPanel extends Panel {
   private static final Icon MINIMAP_INFO = Icon.getIcon(FTBChunksAPI.rl("textures/minimap_info.png"));
   private final LargeMapScreen largeMapScreen;
   private final Button claimChunksButton;
   private final Button dimensionButton;
   private final Button waypointManagerButton;
   private final Button infoButton;
   private final Button settingsButton;
   private final Button serverSettingsButton;
   private final Button clearDeathpointsButton;
   private final Button infoSortScreen;

   MapScreenButtonPanel(LargeMapScreen largeMapScreen) {
      super(largeMapScreen);
      this.largeMapScreen = largeMapScreen;
      this.claimChunksButton = new MapScreenButtonPanel.SimpleTooltipButton(
         this,
         Component.translatable("ftbchunks.gui.claimed_chunks"),
         Icons.MAP,
         (b, mb) -> ChunkScreen.openChunkScreen(),
         TextComponentUtils.hotkeyTooltip("C")
      );
      Component tooltip = Component.literal("[")
         .append(FTBChunksClient.INSTANCE.waypointManagerKey.getTranslatedKeyMessage())
         .append(Component.literal("]"))
         .withStyle(ChatFormatting.GRAY);
      this.waypointManagerButton = new MapScreenButtonPanel.SimpleTooltipButton(
         this, Component.translatable("ftbchunks.gui.waypoints"), Icons.COMPASS, (b, mb) -> new WaypointEditorScreen().openGui(), tooltip
      );
      this.infoButton = new SimpleButton(
         this, Component.translatable("ftbchunks.gui.large_map_info"), Icons.INFO, (b, mb) -> new MapScreenButtonPanel.MapKeyReferenceScreen().openGui()
      );
      this.clearDeathpointsButton = new MapScreenButtonPanel.ClearDeathPointButton(this);
      List<ContextMenuItem> dimItems = AddWaypointOverlay.createDimContextItems(largeMapScreen::switchToDimension);
      this.dimensionButton = new SimpleButton(this, List.of(Component.empty()), Icons.GLOBE, (b, mb) -> {
         DropDownMenu dropDownMenu = this.getGui().openDropdownMenu(dimItems);
         dropDownMenu.setPos(b.getX() + b.width, b.getY() + b.height - dropDownMenu.height);
      }) {
         public void addMouseOverText(TooltipList list) {
            list.add(Component.translatable("ftbchunks.gui.label.dimension"));
            list.add(TextComponentUtils.translatedDimension(largeMapScreen.dimension.dimension).copy().withStyle(ChatFormatting.GRAY));
         }
      };
      this.infoSortScreen = new MapScreenButtonPanel.SimpleTooltipButton(
         this, Component.translatable("ftbchunks.gui.sort_minimap_info"), MINIMAP_INFO, (b, mb) -> new MinimapInfoSortScreen().openGui(), tooltip
      );
      this.settingsButton = new MapScreenButtonPanel.SimpleTooltipButton(
         this,
         Component.translatable("ftbchunks.config.client"),
         Icons.SETTINGS,
         (b, mb) -> ConfigManagerClient.editConfig("ftbchunks-client"),
         TextComponentUtils.hotkeyTooltip("S")
      );
      boolean adminPlayer = Minecraft.getInstance().player.hasPermissions(2);
      this.serverSettingsButton = new MapScreenButtonPanel.SimpleTooltipButton(
         this,
         Component.translatable("ftbchunks.config.server"),
         Icons.SETTINGS.withTint(Color4I.rgb(10502399)),
         (b, mb) -> ConfigManagerClient.editConfig("ftbchunks-world", !adminPlayer),
         TextComponentUtils.hotkeyTooltip("Ctrl + S")
      );
   }

   public void addWidgets() {
      this.add(this.claimChunksButton);
      this.add(this.waypointManagerButton);
      this.add(this.infoButton);
      this.add(this.clearDeathpointsButton);
      this.add(this.infoSortScreen);
      this.add(this.dimensionButton);
      this.add(this.settingsButton);
      if (Minecraft.getInstance().player.hasPermissions(2)) {
         this.add(this.serverSettingsButton);
      }
   }

   public void alignWidgets() {
      this.claimChunksButton.setPosAndSize(1, 1, 16, 16);
      this.waypointManagerButton.setPosAndSize(1, 19, 16, 16);
      this.infoButton.setPosAndSize(1, 37, 16, 16);
      this.dimensionButton.setPosAndSize(1, 55, 16, 16);
      this.clearDeathpointsButton.setPosAndSize(1, 73, 16, 16);
      int yOff = Minecraft.getInstance().player.hasPermissions(2) ? 18 : 0;
      this.infoSortScreen.setPosAndSize(1, this.height - 36 - yOff, 16, 16);
      this.settingsButton.setPosAndSize(1, this.height - 18 - yOff, 16, 16);
      this.serverSettingsButton.setPosAndSize(1, this.height - 18, 16, 16);
   }

   public boolean keyPressed(Key key) {
      if (key.is(67)) {
         this.claimChunksButton.onClicked(MouseButton.LEFT);
         return true;
      }

      if (key.is(83)) {
         if (Screen.hasControlDown()) {
            if (Minecraft.getInstance().player.hasPermissions(2)) {
               this.serverSettingsButton.onClicked(MouseButton.LEFT);
            }
         } else {
            this.settingsButton.onClicked(MouseButton.LEFT);
         }

         return true;
      } else if (FTBChunksClient.INSTANCE.waypointManagerKey.isDown()) {
         this.waypointManagerButton.onClicked(MouseButton.LEFT);
         return true;
      } else {
         return false;
      }
   }

   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      graphics.fill(x, y, x + w, y + h, 805306368);
      graphics.vLine(x + w, y - 1, y + h, 1342177280);
   }

   private class ClearDeathPointButton extends SimpleButton {
      public ClearDeathPointButton(Panel panel) {
         super(panel, Component.translatable("ftbchunks.gui.clear_deathpoints"), Icons.CANCEL, (b, mb) -> {
            if (MapScreenButtonPanel.this.largeMapScreen.getWaypointManager().removeIf(wp -> wp.getType() == WaypointType.DEATH)) {
               MapScreenButtonPanel.this.refreshWidgets();
            }
         });
      }

      public boolean shouldDraw() {
         return super.shouldDraw() && MapScreenButtonPanel.this.largeMapScreen.getWaypointManager().hasDeathpoint();
      }

      public boolean isEnabled() {
         return this.shouldDraw();
      }
   }

   private static class MapKeyReferenceScreen extends KeyReferenceScreen {
      public MapKeyReferenceScreen() {
         super(new String[]{"ftbchunks.gui.large_map_info.text"});
      }

      public Component getTitle() {
         return Component.translatable("ftbchunks.gui.large_map_info");
      }
   }

   private static class SimpleTooltipButton extends SimpleButton {
      private final List<Component> tooltipLines;

      public SimpleTooltipButton(Panel panel, Component text, Icon icon, Callback c, Component tooltipLine) {
         super(panel, text, icon, c);
         this.tooltipLines = List.of(tooltipLine);
      }

      public void addMouseOverText(TooltipList list) {
         super.addMouseOverText(list);
         this.tooltipLines.forEach(list::add);
      }
   }
}
