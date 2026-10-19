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
import xaero.map.world.MapWorld;

public class GuiPlayerTpCommand extends ScreenBase {
   private MySmallButton confirmButton;
   private EditBox commandFormatTextField;
   private boolean usingDefault;
   private String commandFormat;

   public GuiPlayerTpCommand(Screen parent, Screen escape) {
      super(parent, escape, Component.translatable("gui.xaero_wm_player_teleport_command"));
      WorldMapSession session = WorldMapSession.getCurrentSession();
      MapWorld mapWorld = session.getMapProcessor().getMapWorld();
      this.usingDefault = mapWorld.isUsingDefaultPlayerTeleport();
      this.commandFormat = mapWorld.getPlayerTeleportCommandFormat();
      this.canSkipWorldRender = true;
   }

   public void init() {
      super.init();
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      String defaultPlayerTeleportFormat = (String)configManager.getEffective(WorldMapProfiledConfigOptions.DEFAULT_PLAYER_TELEPORT_FORMAT);
      WorldMapSession session = WorldMapSession.getCurrentSession();
      MapWorld mapWorld = session.getMapProcessor().getMapWorld();
      this.commandFormatTextField = new EditBox(
         this.font, this.width / 2 - 100, this.height / 7 + 60, 200, 20, Component.translatable("gui.xaero_wm_player_teleport_command")
      );
      this.commandFormatTextField.setValue(this.usingDefault ? defaultPlayerTeleportFormat : this.commandFormat);
      this.commandFormatTextField.setMaxLength(500);
      if (this.usingDefault) {
         this.commandFormatTextField.setTextColor(-11184811);
         this.commandFormatTextField.active = false;
      } else {
         this.commandFormatTextField.setResponder(text -> this.commandFormat = text);
      }

      this.addWidget(this.commandFormatTextField);
      this.addRenderableWidget(
         this.confirmButton = new MySmallButton(
            0, this.width / 2 - 155, this.height / 6 + 168, Component.translatable("gui.xaero_confirm", new Object[0]), b -> {
               if (this.canConfirm()) {
                  if (!this.usingDefault && this.commandFormat.equals(defaultPlayerTeleportFormat)) {
                     this.usingDefault = true;
                  }

                  mapWorld.setUseDefaultPlayerTeleport(this.usingDefault);
                  mapWorld.setPlayerTeleportCommandFormat(this.commandFormat);
                  mapWorld.saveConfig();
                  this.goBack();
               }
            }
         )
      );
      this.addRenderableWidget(
         new MySmallButton(1, this.width / 2 + 5, this.height / 6 + 168, Component.translatable("gui.xaero_cancel", new Object[0]), b -> this.goBack())
      );
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
      guiGraphics.drawCenteredString(this.font, "{x} {y} {z} {name}", this.width / 2, this.height / 7 + 46, -5592406);
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partial) {
      this.renderEscapeScreen(guiGraphics, mouseX, mouseY, partial);
      super.render(guiGraphics, mouseX, mouseY, partial);
      this.commandFormatTextField.render(guiGraphics, mouseX, mouseY, partial);
   }

   private boolean canConfirm() {
      return this.commandFormat != null && this.commandFormat.length() > 0;
   }

   public void tick() {
      super.tick();
      this.confirmButton.active = this.canConfirm();
   }

   public boolean keyPressed(int par1, int par2, int par3) {
      if (par1 == 257 && this.commandFormat != null && this.commandFormat.length() > 0) {
         this.confirmButton.onClick(0.0, 0.0);
      }

      return super.keyPressed(par1, par2, par3);
   }
}
