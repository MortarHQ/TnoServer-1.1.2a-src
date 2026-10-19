package com.teamresourceful.resourcefullib.client.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

public interface ScreenHistory {
   void setLastScreen(Screen var1);

   @Nullable
   Screen getLastScreen();

   boolean canGoBack();

   default void goBack() {
      if (this.canGoBack()) {
         Screen lastScreen = this.getLastScreen();
         if (lastScreen != null) {
            Minecraft.getInstance().setScreen(lastScreen);
         }
      }
   }
}
