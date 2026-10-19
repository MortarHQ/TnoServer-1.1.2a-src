package xaero.map.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import xaero.lib.client.gui.config.EditStringConfigOptionScreen;
import xaero.lib.common.config.Config;
import xaero.lib.common.config.option.ConfigOption;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.mods.SupportMods;

public class EditDefaultTpCommandScreen extends EditStringConfigOptionScreen<String> {
   private Component waypointCommandHint = Component.translatable("gui.xaero_wm_teleport_command_waypoints_hint");
   private boolean playerFormat;
   private boolean dimensionFormat;

   public EditDefaultTpCommandScreen(
      Screen parent,
      Screen escape,
      Config config,
      Config enforcedConfig,
      ConfigOption<String> option,
      boolean allowEmpty,
      boolean emptyMeansNull,
      Runnable postConfirmAction
   ) {
      super(parent, escape, config, enforcedConfig, option, allowEmpty, emptyMeansNull, postConfirmAction);
      this.playerFormat = option == WorldMapProfiledConfigOptions.DEFAULT_PLAYER_TELEPORT_FORMAT;
      this.dimensionFormat = option == WorldMapProfiledConfigOptions.DEFAULT_MAP_TELEPORT_DIMENSION_FORMAT;
   }

   public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partial) {
      super.renderBackground(guiGraphics, mouseX, mouseY, partial);
      if (SupportMods.minimap()) {
         guiGraphics.drawCenteredString(this.font, this.waypointCommandHint, this.width / 2, this.height / 7 + 61, -5592406);
      }

      String hint = "{x} {y} {z}";
      if (this.playerFormat) {
         hint = hint + " {name}";
      }

      if (this.dimensionFormat) {
         hint = hint + " {d}";
      }

      guiGraphics.drawString(this.font, hint, this.width / 2 + 105, this.height / 7 + 33, -5592406);
   }
}
