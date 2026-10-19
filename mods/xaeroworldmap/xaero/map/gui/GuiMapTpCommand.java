package xaero.map.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.client.gui.widget.MySmallButton;
import xaero.lib.common.config.util.ConfigUtils;
import xaero.map.WorldMap;
import xaero.map.WorldMapSession;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.mods.SupportMods;
import xaero.map.world.MapWorld;

public class GuiMapTpCommand extends ScreenBase {
   private MySmallButton confirmButton;
   private EditBox commandFormatTextField;
   private EditBox dimensionCommandFormatTextField;
   private boolean usingDefault;
   private String commandFormat;
   private String dimensionCommandFormat;
   private Component waypointCommandHint = Component.translatable("gui.xaero_wm_teleport_command_waypoints_hint");

   public GuiMapTpCommand(Screen parent, Screen escape) {
      super(parent, escape, Component.translatable("gui.xaero_wm_teleport_command"));
      WorldMapSession session = WorldMapSession.getCurrentSession();
      MapWorld mapWorld = session.getMapProcessor().getMapWorld();
      this.usingDefault = mapWorld.isUsingDefaultMapTeleport();
      this.commandFormat = mapWorld.getTeleportCommandFormat();
      this.dimensionCommandFormat = mapWorld.getDimensionTeleportCommandFormat();
      this.canSkipWorldRender = true;
   }

   public void init() {
      super.init();
      WorldMapSession session = WorldMapSession.getCurrentSession();
      MapWorld mapWorld = session.getMapProcessor().getMapWorld();
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      String defaultMapTeleportFormat = (String)configManager.getEffective(WorldMapProfiledConfigOptions.DEFAULT_MAP_TELEPORT_FORMAT);
      String defaultMapTeleportDimensionFormat = (String)configManager.getEffective(WorldMapProfiledConfigOptions.DEFAULT_MAP_TELEPORT_DIMENSION_FORMAT);
      this.commandFormatTextField = new EditBox(
         this.font, this.width / 2 - 100, this.height / 7 + 60, 200, 20, Component.translatable("gui.xaero_wm_teleport_command")
      );
      this.commandFormatTextField.setMaxLength(500);
      this.commandFormatTextField.setValue(this.usingDefault ? defaultMapTeleportFormat : this.commandFormat);
      this.dimensionCommandFormatTextField = new EditBox(
         this.font, this.width / 2 - 100, this.height / 7 + 90, 200, 20, Component.translatable("gui.xaero_wm_dimension_teleport_command")
      );
      this.dimensionCommandFormatTextField.setMaxLength(500);
      this.dimensionCommandFormatTextField.setValue(this.usingDefault ? defaultMapTeleportDimensionFormat : this.dimensionCommandFormat);
      if (this.usingDefault) {
         this.commandFormatTextField.active = this.dimensionCommandFormatTextField.active = false;
         this.commandFormatTextField.setTextColor(-11184811);
         this.dimensionCommandFormatTextField.setTextColor(-11184811);
      } else {
         this.commandFormatTextField.setResponder(text -> this.commandFormat = text);
         this.dimensionCommandFormatTextField.setResponder(text -> this.dimensionCommandFormat = text);
      }

      this.addWidget(this.commandFormatTextField);
      this.addWidget(this.dimensionCommandFormatTextField);
      if (SupportMods.minimap()) {
         this.addRenderableWidget(
            new MySmallButton(
               0,
               this.width / 2 - 75,
               this.height / 7 + 138,
               Component.translatable("gui.xaero_wm_teleport_command_waypoints"),
               b -> SupportMods.xaeroMinimap.openWaypointWorldTeleportCommandScreen(this, this.escape)
            )
         );
      }

      this.addRenderableWidget(
         this.confirmButton = new MySmallButton(
            1,
            this.width / 2 - 155,
            this.height / 6 + 168,
            Component.translatable("gui.xaero_confirm"),
            b -> {
               if (this.canConfirm()) {
                  if (!this.usingDefault
                     && this.commandFormat.equals(defaultMapTeleportFormat)
                     && this.dimensionCommandFormat.equals(defaultMapTeleportDimensionFormat)) {
                     this.usingDefault = true;
                  }

                  mapWorld.setTeleportCommandFormat(this.commandFormat);
                  mapWorld.setDimensionTeleportCommandFormat(this.dimensionCommandFormat);
                  mapWorld.setUseDefaultMapTeleport(this.usingDefault);
                  mapWorld.saveConfig();
                  this.goBack();
               }
            }
         )
      );
      this.addRenderableWidget(new MySmallButton(2, this.width / 2 + 5, this.height / 6 + 168, Component.translatable("gui.xaero_cancel"), b -> this.goBack()));
      this.addRenderableWidget(
         new MySmallButton(
            202,
            this.width / 2 - 75,
            this.height / 7 + 20,
            CommonComponents.optionNameValue(Component.translatable("gui.xaero_wm_use_default"), ConfigUtils.getDisplayForBoolean(null, this.usingDefault)),
            b -> {
               this.usingDefault = !this.usingDefault;
               this.init(this.minecraft, this.width, this.height);
            }
         )
      );
   }

   public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
      super.renderBackground(guiGraphics, i, j, f);
      guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
      if (SupportMods.minimap()) {
         guiGraphics.drawCenteredString(this.font, this.waypointCommandHint, this.width / 2, this.height / 7 + 124, -5592406);
      }

      guiGraphics.drawCenteredString(this.font, "{x} {y} {z} {d}", this.width / 2, this.height / 7 + 46, -5592406);
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partial) {
      this.renderEscapeScreen(guiGraphics, mouseX, mouseY, partial);
      super.render(guiGraphics, mouseX, mouseY, partial);
      this.commandFormatTextField.render(guiGraphics, mouseX, mouseY, partial);
      this.dimensionCommandFormatTextField.render(guiGraphics, mouseX, mouseY, partial);
   }

   private boolean canConfirm() {
      return this.commandFormat != null && this.commandFormat.length() > 0 && this.dimensionCommandFormat != null && this.dimensionCommandFormat.length() > 0;
   }

   public void tick() {
      this.confirmButton.active = this.canConfirm();
   }

   public boolean keyPressed(int par1, int par2, int par3) {
      if (par1 == 257 && this.commandFormat != null && this.commandFormat.length() > 0) {
         this.confirmButton.onClick(0.0, 0.0);
      }

      return super.keyPressed(par1, par2, par3);
   }
}
