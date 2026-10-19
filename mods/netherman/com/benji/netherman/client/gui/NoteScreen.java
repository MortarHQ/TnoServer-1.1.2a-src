package com.benji.netherman.client.gui;

import com.benji.netherman.NetherExp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class NoteScreen extends Screen {
   private static final ResourceLocation NOTE_TEXTURE = NetherExp.location("textures/gui/note_gui.png");
   private static final int IMAGE_WIDTH = 130;
   private static final int IMAGE_HEIGHT = 160;
   private long timeOpened;
   private static final float ANIMATION_DURATION_MS = 400.0F;

   public NoteScreen() {
      super(Component.empty());
   }

   public static void openScreen() {
      Minecraft.getInstance().setScreen(new NoteScreen());
   }

   protected void init() {
      super.init();
      this.timeOpened = System.currentTimeMillis();
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      guiGraphics.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
      long timePassed = System.currentTimeMillis() - this.timeOpened;
      float progress = Math.min(1.0F, (float)timePassed / 400.0F);
      float easeOutProgress = 1.0F - (1.0F - progress) * (1.0F - progress);
      int x = (this.width - 130) / 2;
      int startY = this.height;
      int endY = (this.height - 160) / 2;
      int currentY = (int)(startY + (endY - startY) * easeOutProgress);
      guiGraphics.blit(NOTE_TEXTURE, x, currentY, 0.0F, 0.0F, 130, 160, 130, 160);
   }

   public boolean isPauseScreen() {
      return false;
   }
}
