package com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block;

import com.finderfeed.fdlib.systems.simple_screen.FDScrollableWidget;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.interactions.InteractionBox;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.text_block_parser.TextBlockParser;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TextBlockWidget extends FDScrollableWidget {
   private List<TextBlockEntry> textBlockEntries = new ArrayList<>();
   private List<InteractionBox> interactionBoxes = new ArrayList<>();
   private float maxScroll;
   private int textColor = 16777215;
   private boolean debug = false;

   public TextBlockWidget(Screen owner, float x, float y, float width, float height) {
      super(owner, x, y, width, height);
   }

   @Override
   public void renderWidget(GuiGraphics graphics, float mx, float my, float pticks) {
      TextBlockCursor cursor = new TextBlockCursor(this.getX(), this.getY() - this.getCurrentScroll());
      if (this.debug) {
         FDRenderUtil.fill(graphics.pose(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), 1.0F, 1.0F, 1.0F, 0.25F);
      }

      this.clearInteractions();
      FDRenderUtil.Scissor.pushScissors(this.getX(), this.getY(), this.getWidth(), this.getHeight());

      for (int i = 0; i < this.textBlockEntries.size(); i++) {
         TextBlockEntry entry = this.textBlockEntries.get(i);
         boolean last = i == this.textBlockEntries.size() - 1;
         entry.render(graphics, this, cursor, mx, my, pticks, last);
      }

      FDRenderUtil.Scissor.popScissors();
      this.maxScroll = Math.max(0.0F, Math.max(this.maxScroll, cursor.y - this.getY() - this.getHeight()));
      if (this.maxScroll != 0.0F) {
         float r = ((this.textColor & 0xFF0000) >> 16) / 255.0F;
         float g = ((this.textColor & 0xFF00) >> 8) / 255.0F;
         float b = (this.textColor & 0xFF) / 255.0F;
         FDRenderUtil.renderScrollBar(
            graphics.pose(),
            this.getX() + this.getWidth() + 1.0F,
            this.getY(),
            2.0F,
            this.getHeight(),
            this.getCurrentScroll(),
            this.maxScroll,
            Math.max(r - 0.2F, 0.0F),
            Math.max(g - 0.2F, 0.0F),
            Math.max(b - 0.2F, 0.0F),
            1.0F,
            Math.min(r + 0.1F, 1.0F),
            Math.min(g + 0.1F, 1.0F),
            Math.min(b + 0.1F, 1.0F),
            1.0F
         );
      }

      InteractionBox box = this.getHoverOverInteractionBoxUnderMouse(mx, my);
      if (box != null) {
         box.interaction.getOnHover().onHoverOver(this, graphics, mx, my);
      }
   }

   public InteractionBox getHoverOverInteractionBoxUnderMouse(float mx, float my) {
      return this.getInteractionBoxUnderMouse(mx, my, box -> box.interaction.isHoverOver());
   }

   public InteractionBox getClickInteractionBoxUnderMouse(float mx, float my) {
      return this.getInteractionBoxUnderMouse(mx, my, box -> box.interaction.isClick());
   }

   public InteractionBox getScrollInteractionBoxUnderMouse(float mx, float my) {
      return this.getInteractionBoxUnderMouse(mx, my, box -> box.interaction.isScroll());
   }

   public InteractionBox getInteractionBoxUnderMouse(float mx, float my, Predicate<InteractionBox> boxPredicate) {
      if (!FDRenderUtil.isMouseInBounds(mx, my, this.getX(), this.getY(), this.getWidth(), this.getHeight())) {
         return null;
      }

      for (InteractionBox box : this.interactionBoxes) {
         if (box.isMouseInside(mx, my) && boxPredicate.test(box)) {
            return box;
         }
      }

      return null;
   }

   private void clearInteractions() {
      this.interactionBoxes.clear();
   }

   public void addInteractionBox(InteractionBox box) {
      this.interactionBoxes.add(box);
   }

   public void removeTextEntries() {
      this.textBlockEntries.clear();
      this.clearInteractions();
      this.setCurrentScroll(0.0F);
      this.maxScroll = 0.0F;
   }

   public TextBlockWidget addTextBlockEntry(TextBlockEntry entry) {
      this.textBlockEntries.add(entry);
      this.setCurrentScroll(0.0F);
      this.maxScroll = 0.0F;
      return this;
   }

   public TextBlockWidget addTextBlockEntries(Collection<TextBlockEntry> entry) {
      this.textBlockEntries.addAll(entry);
      this.setCurrentScroll(0.0F);
      this.maxScroll = 0.0F;
      return this;
   }

   public void setText(Component component, float textScale, int color, boolean renderShadow) {
      this.clearInteractions();
      this.textBlockEntries = TextBlockParser.parseComponent(component, textScale, renderShadow, color);
      this.setCurrentScroll(0.0F);
      this.maxScroll = 0.0F;
      this.textColor = color;
   }

   public List<TextBlockEntry> getTextBlockEntries() {
      return this.textBlockEntries;
   }

   public float getBorderX() {
      return this.getX() + this.getWidth();
   }

   @Override
   public boolean onMouseClick(float mx, float my, int key) {
      InteractionBox box = this.getClickInteractionBoxUnderMouse(mx, my);
      if (box != null) {
         box.interaction.getOnClick().click(this, mx, my, key);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean onMouseRelease(float v, float v1, int i) {
      return false;
   }

   @Override
   public boolean onMouseScroll(float mx, float my, float scrollX, float scrollY) {
      InteractionBox box = this.getScrollInteractionBoxUnderMouse(mx, my);
      if (box != null) {
         box.interaction.getOnScroll().scroll(this, mx, my, scrollX, scrollY);
         return true;
      } else {
         return super.onMouseScroll(mx, my, scrollX, scrollY);
      }
   }

   @Override
   public float getMaxScroll() {
      return this.maxScroll;
   }

   @Override
   public void onScroll(float v) {
   }

   @Override
   public float scrollAmount() {
      return 4.0F;
   }

   @Override
   public boolean onCharTyped(char c, int i) {
      return false;
   }

   @Override
   public boolean onKeyPress(int i, int i1, int i2) {
      return false;
   }

   @Override
   public boolean onKeyRelease(int i, int i1, int i2) {
      return false;
   }

   public ScreenRectangle getRectangle() {
      return ScreenRectangle.empty();
   }

   public TextBlockWidget setDebug(boolean debug) {
      this.debug = debug;
      return this;
   }
}
