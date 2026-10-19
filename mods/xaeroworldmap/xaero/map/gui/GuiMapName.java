package xaero.map.gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import xaero.lib.client.gui.widget.MySmallButton;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.world.MapDimension;

public class GuiMapName extends ScreenBase {
   private EditBox nameTextField;
   private MapDimension mapDimension;
   private String editingMWId;
   private String currentNameFieldContent;
   private MapProcessor mapProcessor;
   private Button confirmButton;

   public GuiMapName(MapProcessor mapProcessor, Screen par1GuiScreen, Screen escape, MapDimension mapDimension, String editingMWId) {
      super(par1GuiScreen, escape, Component.translatable("gui.xaero_map_name"));
      this.mapDimension = mapDimension;
      this.editingMWId = editingMWId;
      this.currentNameFieldContent = editingMWId == null ? "" : mapDimension.getMultiworldName(editingMWId);
      this.mapProcessor = mapProcessor;
      this.canSkipWorldRender = true;
   }

   public void init() {
      super.init();
      if (this.nameTextField != null) {
         this.currentNameFieldContent = this.nameTextField.getValue();
      }

      this.nameTextField = new EditBox(this.font, this.width / 2 - 100, 60, 200, 20, Component.translatable("gui.xaero_map_name"));
      this.nameTextField.setValue(this.currentNameFieldContent);
      this.setFocused(this.nameTextField);
      this.addRenderableWidget(this.nameTextField);
      this.addRenderableWidget(
         this.confirmButton = new MySmallButton(
            0, this.width / 2 - 155, this.height / 6 + 168, Component.translatable("gui.xaero_confirm", new Object[0]), b -> {
               if (this.canConfirm()) {
                  synchronized (this.mapProcessor.uiSync) {
                     if (this.mapProcessor.getMapWorld() == this.mapDimension.getMapWorld()) {
                        String unfilteredName = this.nameTextField.getValue();
                        String mwIdFixed;
                        if (this.editingMWId == null) {
                           String mwId = unfilteredName.toLowerCase().replaceAll("[^a-z0-9]+", "");
                           if (mwId.isEmpty()) {
                              mwId = "map";
                           }

                           mwId = "cm$" + mwId;
                           boolean mwAdded = false;
                           mwIdFixed = mwId;
                           int fix = 1;

                           while (!mwAdded) {
                              mwAdded = this.mapDimension.addMultiworldChecked(mwIdFixed);
                              if (!mwAdded) {
                                 mwIdFixed = mwId + ++fix;
                              }
                           }

                           Path dimensionFolderPath = this.mapDimension.getMainFolderPath();
                           Path multiworldFolderPath = dimensionFolderPath.resolve(mwIdFixed);

                           try {
                              Files.createDirectories(multiworldFolderPath);
                           } catch (IOException e) {
                              WorldMap.LOGGER.error("suppressed exception", e);
                           }

                           this.mapDimension.setMultiworldUnsynced(mwIdFixed);
                        } else {
                           mwIdFixed = this.editingMWId;
                        }

                        this.mapDimension.setMultiworldName(mwIdFixed, unfilteredName);
                        this.mapDimension.saveConfigUnsynced();
                        this.goBack();
                     }
                  }
               }
            }
         )
      );
      this.addRenderableWidget(
         new MySmallButton(1, this.width / 2 + 5, this.height / 6 + 168, Component.translatable("gui.xaero_cancel", new Object[0]), b -> this.goBack())
      );
      this.updateConfirmButton();
   }

   protected void setInitialFocus() {
   }

   private boolean canConfirm() {
      return this.nameTextField.getValue().length() > 0;
   }

   private void updateConfirmButton() {
      this.confirmButton.active = this.canConfirm();
   }

   public boolean keyPressed(int par1, int par2, int par3) {
      boolean result = super.keyPressed(par1, par2, par3);
      if (par1 == 257 && this.canConfirm()) {
         this.confirmButton.onClick(0.0, 0.0);
         return true;
      } else {
         return result;
      }
   }

   public void tick() {
      this.updateConfirmButton();
   }

   public void renderBackground(GuiGraphics guiGraphics, int par1, int par2, float par3) {
      super.renderBackground(guiGraphics, par1, par2, par3);
      guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
      this.nameTextField.render(guiGraphics, par1, par2, par3);
   }

   public void render(GuiGraphics guiGraphics, int par1, int par2, float par3) {
      this.renderEscapeScreen(guiGraphics, par1, par2, par3);
      super.render(guiGraphics, par1, par2, par3);
   }
}
