package dev.ftb.mods.ftblibrary.config.ui;

import dev.ftb.mods.ftblibrary.config.manager.ConfigManagerClient;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.net.EditConfigChoicePacket;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.TextField;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ChooseConfigScreen extends BaseScreen {
   private static final int BTN_HEIGHT = 22;
   private final EditConfigChoicePacket message;

   private ChooseConfigScreen(EditConfigChoicePacket message) {
      this.message = message;
   }

   public static void open(EditConfigChoicePacket message) {
      new ChooseConfigScreen(message).openGui();
   }

   @Override
   public void addWidgets() {
      this.add(new TextField(this).setText(this.message.title()).addFlags(6));
      this.add(
         new ChooseConfigScreen.TextButton(
            this,
            Component.translatable("ftblibrary.client_settings"),
            Icons.PLAYER,
            (widget, button) -> ConfigManagerClient.editConfig(this.message.clientConfig())
         )
      );
      this.add(
         new ChooseConfigScreen.TextButton(
            this,
            Component.translatable("ftblibrary.server_settings"),
            Icons.GLOBE,
            (widget, button) -> ConfigManagerClient.editConfig(this.message.serverConfig())
         )
      );
      this.add(new ChooseConfigScreen.TextButton(this, CommonComponents.GUI_BACK, Icons.BACK, (widget, button) -> this.closeGui()));
   }

   @Override
   public void alignWidgets() {
      int ySize = 76;
      int xSize = 50 + this.widgets.stream().map(wx -> this.getTheme().getStringWidth(wx.getTitle())).max(Integer::compare).orElse(100);
      int yPos = (this.getWindow().getGuiScaledHeight() - ySize) / 2;

      for (Widget w : this.widgets) {
         w.setPosAndSize((this.getWindow().getGuiScaledWidth() - xSize) / 2, yPos, xSize, 22);
         yPos += 27;
      }
   }

   @Override
   public boolean onInit() {
      return this.setFullscreen();
   }

   @Override
   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
   }

   private static class TextButton extends SimpleTextButton {
      private final ChooseConfigScreen.TextButton.Callback callback;

      public TextButton(Panel panel, Component txt, Icon icon, ChooseConfigScreen.TextButton.Callback callback) {
         super(panel, txt, icon);
         this.callback = callback;
      }

      @Override
      public void onClicked(MouseButton button) {
         this.callback.onClicked(this, button);
      }

      @Override
      public boolean renderTitleInCenter() {
         return true;
      }

      public interface Callback {
         void onClicked(ChooseConfigScreen.TextButton var1, MouseButton var2);
      }
   }
}
