package com.teamresourceful.resourcefullib.client.screens;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public abstract class HistoryScreen extends Screen implements ScreenHistory {
   @Nullable
   private Screen lastScreen;

   protected HistoryScreen(Component title) {
      super(title);
   }

   @Override
   public void setLastScreen(@Nullable Screen screen) {
      this.lastScreen = screen;
   }

   @Nullable
   @Override
   public Screen getLastScreen() {
      return this.lastScreen;
   }

   @Override
   public boolean canGoBack() {
      return this.lastScreen != null;
   }
}
