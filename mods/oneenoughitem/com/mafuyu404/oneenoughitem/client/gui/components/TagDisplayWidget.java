package com.mafuyu404.oneenoughitem.client.gui.components;

import com.mafuyu404.oneenoughitem.client.gui.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TagDisplayWidget extends AbstractWidget {
   private final ResourceLocation tagId;
   private final OnPress removeAction;
   private final Font font;
   private long hoverStartTime;
   private int scrollOffset;
   private boolean isScrolling;
   private static final int SCROLL_DELAY = 250;
   private long lastScrollTime;
   private static final int SCROLL_INTERVAL = 60;
   private static final ResourceLocation ITEM_BOX_TEX = ResourceLocation.fromNamespaceAndPath("oneenoughitem", "textures/gui/item_box.png");
   private static final ResourceLocation CROSS_TEX = ResourceLocation.fromNamespaceAndPath("oneenoughitem", "textures/gui/cross.png");

   public TagDisplayWidget(int x, int y, ResourceLocation tagId, OnPress removeAction) {
      super(x, y, 70, 20, Component.empty());
      this.font = Minecraft.getInstance().font;
      this.hoverStartTime = 0L;
      this.scrollOffset = 0;
      this.isScrolling = false;
      this.lastScrollTime = 0L;
      this.tagId = tagId;
      this.removeAction = removeAction;
   }

   protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      GuiUtils.drawItemBox(graphics, this.getX(), this.getY(), this.width, this.height);
      String fullTagText = "#" + this.tagId.toString();
      String displayText = fullTagText;
      int textX = this.getX() + 3;
      if (this.isHovered()) {
         if (this.hoverStartTime == 0L) {
            this.hoverStartTime = System.currentTimeMillis();
            this.scrollOffset = 0;
            this.isScrolling = false;
         }

         long hoverDuration = System.currentTimeMillis() - this.hoverStartTime;
         int textWidth = this.font.width(fullTagText);
         int availableWidth = this.width - 6 - (this.removeAction != null ? 12 : 0);
         if (textWidth > availableWidth && hoverDuration > 250L) {
            this.isScrolling = true;
            int maxScroll = textWidth - availableWidth + 20;
            long now = System.currentTimeMillis();
            if (now - this.lastScrollTime > 60L) {
               this.scrollOffset++;
               this.lastScrollTime = now;
            }

            if (this.scrollOffset > maxScroll) {
               this.scrollOffset = -50;
            }

            textX = this.getX() + 3 - this.scrollOffset;
         } else if (textWidth > availableWidth) {
            displayText = this.truncateText(fullTagText, availableWidth);
         }
      } else {
         this.hoverStartTime = 0L;
         this.scrollOffset = 0;
         this.isScrolling = false;
         int availableWidth = this.width - 6 - (this.removeAction != null ? 12 : 0);
         if (this.font.width(fullTagText) > availableWidth) {
            displayText = this.truncateText(fullTagText, availableWidth);
         }
      }

      if (this.isScrolling) {
         int clipX = this.getX() + 3;
         int clipY = this.getY();
         int clipWidth = this.width - 6 - (this.removeAction != null ? 12 : 0);
         int clipHeight = this.height;
         graphics.enableScissor(clipX, clipY, clipX + clipWidth, clipY + clipHeight);
      }

      graphics.drawString(this.font, displayText, textX, this.getY() + 6, -1, false);
      if (this.isScrolling) {
         graphics.disableScissor();
      }

      if (this.isHovered() && this.removeAction != null) {
         int crossX = this.getX() + this.width - 9;
         int crossY = this.getY() + 1;
         graphics.blit(CROSS_TEX, crossX, crossY, 0.0F, 0.0F, 8, 8, 8, 8);
      }
   }

   private String truncateText(String text, int maxWidth) {
      if (this.font.width(text) <= maxWidth) {
         return text;
      }

      String truncated = text;

      while (this.font.width(truncated + "...") > maxWidth && truncated.length() > 1) {
         truncated = truncated.substring(0, truncated.length() - 1);
      }

      return truncated + "...";
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.isHovered() && this.removeAction != null) {
         int crossX = this.getX() + this.width - 9;
         int crossY = this.getY() + 1;
         if (mouseX >= crossX && mouseX <= crossX + 8 && mouseY >= crossY && mouseY <= crossY + 8) {
            this.removeAction.onPress(null);
            return true;
         }
      }

      return false;
   }

   public ResourceLocation getTagId() {
      return this.tagId;
   }

   public void setPosition(int x, int y) {
      this.setX(x);
      this.setY(y);
   }

   protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
      narrationElementOutput.add(NarratedElementType.TITLE, Component.literal("#" + this.tagId.toString()));
   }
}
