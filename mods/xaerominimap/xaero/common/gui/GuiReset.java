package xaero.common.gui;

import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GuiReset extends ConfirmScreen {
   public GuiReset(IXaeroConfirmScreenCallback callback, Screen parent, Screen escScreen) {
      super(
         callback::accept,
         Component.translatable("gui.xaero_reset_config_profile_default_message"),
         Component.translatable("gui.xaero_reset_config_profile_default_message2")
      );
   }
}
