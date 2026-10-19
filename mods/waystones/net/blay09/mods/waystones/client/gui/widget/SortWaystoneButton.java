package net.blay09.mods.waystones.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SortWaystoneButton extends Button {
   private static final ResourceLocation MOVE_UP_HIGHLIGHTED_SPRITE = ResourceLocation.withDefaultNamespace("server_list/move_up_highlighted");
   private static final ResourceLocation MOVE_UP_SPRITE = ResourceLocation.withDefaultNamespace("server_list/move_up");
   private static final ResourceLocation MOVE_DOWN_HIGHLIGHTED_SPRITE = ResourceLocation.withDefaultNamespace("server_list/move_down_highlighted");
   private static final ResourceLocation MOVE_DOWN_SPRITE = ResourceLocation.withDefaultNamespace("server_list/move_down");
   private final int sortDir;
   private final int visibleRegionStart;
   private final int visibleRegionHeight;

   public SortWaystoneButton(int x, int y, int sortDir, int visibleRegionStart, int visibleRegionHeight, OnPress pressable) {
      super(x, y, 11, 7, Component.empty(), pressable, Button.DEFAULT_NARRATION);
      this.sortDir = sortDir;
      this.visibleRegionStart = visibleRegionStart;
      this.visibleRegionHeight = visibleRegionHeight;
   }

   public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partial) {
      if (mouseY >= this.visibleRegionStart && mouseY < this.visibleRegionStart + this.visibleRegionHeight) {
         this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
         int renderY = this.getY() - (this.sortDir == 1 ? 20 : 5);
         RenderSystem.enableBlend();
         if (this.active && this.isHovered) {
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
         } else if (this.active) {
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 0.75F);
         } else {
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 0.25F);
         }

         if (this.isHovered && this.active) {
            guiGraphics.blitSprite(this.sortDir == 1 ? MOVE_DOWN_HIGHLIGHTED_SPRITE : MOVE_UP_HIGHLIGHTED_SPRITE, this.getX() - 5, renderY, 32, 32);
         } else {
            guiGraphics.blitSprite(this.sortDir == 1 ? MOVE_DOWN_SPRITE : MOVE_UP_SPRITE, this.getX() - 5, renderY, 32, 32);
         }

         RenderSystem.disableBlend();
      }
   }
}
