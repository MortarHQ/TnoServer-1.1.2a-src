package com.nododiiiii.ponderer.ui;

import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class ShowInterfaceExperimentalNoticeScreen {
   private static boolean hideForSession = false;

   private ShowInterfaceExperimentalNoticeScreen() {
   }

   public static void openIfNeeded(@Nonnull Screen source, Runnable onProceed) {
      if (hideForSession) {
         onProceed.run();
      } else {
         new PondererDialogScreen(
               source,
               List.of(Component.translatable("ponderer.ui.show_interface.experimental_notice.title")),
               List.of(Component.translatable("ponderer.ui.show_interface.experimental_notice.message")),
               List.of(PondererDialogScreen.button(Component.translatable("ponderer.ui.confirm"), dialog -> {
                  dialog.closeToSource();
                  onProceed.run();
               }), PondererDialogScreen.button(Component.translatable("ponderer.ui.show_interface.experimental_notice.hide_for_session"), dialog -> {
                  hideForSession = true;
                  dialog.closeToSource();
                  onProceed.run();
               }), PondererDialogScreen.closeButton(Component.translatable("ponderer.ui.cancel")))
            )
            .open();
      }
   }
}
