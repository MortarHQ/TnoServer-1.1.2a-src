package com.finderfeed.fdlib.systems.screen.default_components.text;

import com.finderfeed.fdlib.systems.screen.FDEditorComponent;
import com.finderfeed.fdlib.systems.screen.FDScreen;
import com.finderfeed.fdlib.systems.screen.ValueComponent;
import com.finderfeed.fdlib.systems.screen.annotations.FDScale;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;

public abstract class FDTextBox<T> extends ValueComponent<T> {
   private int firstLineId = 0;
   private int lastLineId = 0;
   private float scrollerWidth = 6.0F;
   private float scrollAmount = 0.0F;
   private boolean mouseSelecting = false;
   private boolean isSelecting;
   protected int selectionCursor = 0;
   protected int cursor = 0;
   protected StringBuilder value = new StringBuilder();
   protected List<FDTextBox.Line> lines = new ArrayList<>(List.of(new FDTextBox.Line(0, 0)));
   public float textOffsetLeft = 3.0F;
   public float textOffsetRight = 3.0F;
   public float textOffsetY = 3.0F;
   public float textScale = 1.0F;
   public Component emptyComponentText = Component.empty();
   public String filter = "(.|\\n)*";

   public FDTextBox(FDScreen screen, String uniqueId, float x, float y, float width, float height) {
      super(screen, uniqueId, x, y, width, height);
   }

   @Override
   public void renderComponent(GuiGraphics graphics, float x, float y, float mx, float my, float partialTicks) {
      this.renderBackground(graphics, x, y, mx, my, partialTicks);
      this.renderText(graphics, x, y, mx, my, partialTicks);
      this.renderScrollBar(graphics, x, y, mx, my, partialTicks);
   }

   public void renderScrollBar(GuiGraphics graphics, float x, float y, float mx, float my, float partialTicks) {
      float scrollAmount = this.getMaxScrollAmount();
      if (scrollAmount != 0.0F) {
         float xp = x + this.getWidth() - this.scrollerWidth - this.textOffsetY;
         FDRenderUtil.renderScrollBar(
            graphics.pose(),
            xp,
            y + this.getTextOffsetY(),
            this.scrollerWidth,
            this.getHeight() - this.getTextOffsetY() * 2.0F,
            this.scrollAmount,
            scrollAmount,
            0.3F,
            0.3F,
            0.3F,
            1.0F,
            0.8F,
            0.8F,
            0.8F,
            1.0F
         );
      }
   }

   public void renderText(GuiGraphics graphics, float x, float y, float mx, float my, float partialTicks) {
      String str = this.value.toString();
      x += this.textOffsetLeft;
      y += this.textOffsetY;
      Font font = Minecraft.getInstance().font;
      float lineHeight = 9.0F * this.textScale;
      int cursorLine = this.getLineAtCursor(this.cursor);
      this.firstLineId = -1;
      this.lastLineId = -1;

      for (int i = 0; i < this.lines.size(); i++) {
         FDTextBox.Line line = this.lines.get(i);
         String toRender = str.substring(line.begin, line.end);
         float offs = i * (9.0F * this.textScale);
         float yPos = y + offs - this.scrollAmount;
         float ys = yPos - y;
         if (!(ys < 0.0F)) {
            if (this.firstLineId == -1) {
               this.firstLineId = i;
            }

            if (ys > this.getHeight() - this.getTextOffsetY() - 9.0F * this.textScale) {
               if (this.lastLineId == -1) {
                  this.lastLineId = i - 1;
               }
            } else {
               FDRenderUtil.renderScaledText(graphics, toRender.replace("\n", ""), x, yPos, this.textScale, false, 16777215);
               if (this.isFocused() && i == cursorLine) {
                  float alpha = Util.getMillis() % 800L < 400L ? 0.0F : 1.0F;
                  int index = this.cursor - line.begin;
                  String w = toRender.substring(0, index);
                  float sw = font.width(w) * this.textScale;
                  FDRenderUtil.fill(graphics.pose(), x + sw, y + offs - this.scrollAmount, this.textScale, lineHeight, 1.0F, 1.0F, 1.0F, alpha);
               }
            }
         }
      }

      if (this.lastLineId == -1) {
         this.lastLineId = this.lines.size() - 1;
      }

      if (this.isSelecting && this.selectionCursor != this.cursor) {
         int selectionLine = this.getLineAtCursor(this.selectionCursor);
         int line1 = Math.min(cursorLine, selectionLine);
         int line2 = Math.max(cursorLine, selectionLine);
         if (line1 != line2) {
            for (int i = line1; i <= line2; i++) {
               FDTextBox.Line line = this.lines.get(i);
               int begin = line.begin;
               int end = line.end;
               float startOffset = 0.0F;
               if (i == line1) {
                  begin = Math.min(this.cursor, this.selectionCursor);
                  String ws = this.value.substring(line.begin, begin);
                  startOffset = font.width(ws) * this.textScale;
               } else if (i == line2) {
                  end = Math.max(this.cursor, this.selectionCursor);
               }

               String ws = this.value.substring(begin, end);
               float w = font.width(ws) * this.textScale;
               float yRenderPos = y + i * lineHeight - this.scrollAmount;
               if (!(yRenderPos - y < 0.0F) && !(yRenderPos - y > this.getHeight() - this.getTextOffsetY() - 9.0F * this.textScale)) {
                  FDRenderUtil.fill(
                     RenderType.guiTextHighlight(), graphics.pose(), x + startOffset, yRenderPos, w + this.textScale, lineHeight, 0.0F, 0.0F, 1.0F, 1.0F
                  );
               }
            }
         } else {
            FDTextBox.Line line = this.lines.get(line1);
            int begin = Math.min(this.cursor, this.selectionCursor);
            int end = Math.max(this.cursor, this.selectionCursor);
            String ws = this.value.substring(begin, end);
            String ws2 = this.value.substring(line.begin, begin);
            float w = font.width(ws) * this.textScale;
            float w2 = font.width(ws2) * this.textScale;
            FDRenderUtil.fill(
               RenderType.guiTextHighlight(),
               graphics.pose(),
               x + w2,
               y + line1 * lineHeight - this.scrollAmount,
               w + this.textScale,
               lineHeight,
               0.0F,
               0.0F,
               1.0F,
               1.0F
            );
         }
      }
   }

   public abstract void renderBackground(GuiGraphics var1, float var2, float var3, float var4, float var5, float var6);

   public void updateLines() {
      this.lines.clear();
      if (this.value.isEmpty()) {
         this.lines.add(new FDTextBox.Line(0, 0));
      } else {
         int splitLength;
         if (this.textScale != 0.0F) {
            splitLength = Math.round((this.getWidth() - this.getTextOffsetLeft() - this.getTextOffsetRight()) / this.textScale);
         } else {
            splitLength = 0;
         }

         Minecraft.getInstance()
            .font
            .getSplitter()
            .splitLines(this.value.toString(), splitLength, Style.EMPTY, true, (style, begin, end) -> this.lines.add(new FDTextBox.Line(begin, end)));
         if (this.value.charAt(this.value.length() - 1) == '\n') {
            this.lines.add(new FDTextBox.Line(this.value.length(), this.value.length()));
         }
      }
   }

   public void moveCursorLeft() {
      this.cursor = Mth.clamp(this.cursor - 1, 0, this.value.length());
      if (!this.isSelecting) {
         this.selectionCursor = this.cursor;
      } else if (!Screen.hasShiftDown()) {
         this.isSelecting = false;
      }

      this.scrollToCursor(this.cursor);
   }

   public void moveCursorRight() {
      this.cursor = Mth.clamp(this.cursor + 1, 0, this.value.length());
      if (!this.isSelecting) {
         this.selectionCursor = this.cursor;
      } else if (!Screen.hasShiftDown()) {
         this.isSelecting = false;
      }

      this.scrollToCursor(this.cursor);
   }

   public void moveCursorUp() {
      int line = this.getLineAtCursor(this.cursor);
      if (line > 0) {
         FDTextBox.Line prev = this.lines.get(line - 1);
         FDTextBox.Line current = this.lines.get(line);
         int cdelta = prev.end - prev.begin;
         int currentLocalPos = this.cursor - current.begin;
         if (currentLocalPos > cdelta) {
            char endChar = this.value.charAt(prev.end - 1);
            this.cursor = prev.end;
            if (endChar == '\n') {
               this.cursor--;
            }
         } else {
            this.cursor = prev.begin + currentLocalPos;
         }
      }

      if (!this.isSelecting) {
         this.selectionCursor = this.cursor;
      } else if (!Screen.hasShiftDown()) {
         this.isSelecting = false;
      }

      this.scrollToCursor(this.cursor);
   }

   public void moveCursorDown() {
      int line = this.getLineAtCursor(this.cursor);
      if (line < this.lines.size() - 1) {
         FDTextBox.Line next = this.lines.get(line + 1);
         FDTextBox.Line current = this.lines.get(line);
         int cdelta = next.end - next.begin;
         int currentLocalPos = this.cursor - current.begin;
         if (currentLocalPos > cdelta) {
            char endChar = this.value.charAt(next.end - 1);
            boolean flag = next.end != this.value.length() || line + 1 != this.lines.size() - 1;
            this.cursor = next.end;
            if (endChar == '\n' && flag) {
               this.cursor--;
            }
         } else {
            this.cursor = next.begin + currentLocalPos;
         }
      }

      if (!this.isSelecting) {
         this.selectionCursor = this.cursor;
      } else if (!Screen.hasShiftDown()) {
         this.isSelecting = false;
      }

      this.scrollToCursor(this.cursor);
   }

   public int getLineAtCursor(int cursor) {
      for (int i = 0; i < this.lines.size(); i++) {
         FDTextBox.Line line = this.lines.get(i);
         if (cursor >= line.begin && cursor < line.end) {
            return i;
         }
      }

      return this.lines.size() - 1;
   }

   public boolean removeCharacterAtCursor(boolean update) {
      if (!this.isSelecting) {
         int deletePos = this.cursor - 1;
         if (deletePos >= 0 && deletePos < this.value.length()) {
            this.value.deleteCharAt(deletePos);
            this.cursor--;
            this.updateLines();
            this.scrollToCursor(this.cursor);
            return true;
         } else {
            return false;
         }
      } else {
         int begin = Mth.clamp(Math.min(this.cursor, this.selectionCursor), 0, this.value.length());
         int end = Mth.clamp(Math.max(this.cursor, this.selectionCursor), 0, this.value.length());
         this.value.delete(begin, end);
         this.cursor = begin;
         if (update) {
            this.updateLines();
         }

         this.isSelecting = false;
         this.selectionCursor = this.cursor;
         this.scrollToCursor(this.cursor);
         return true;
      }
   }

   public boolean insertText(String text) {
      text = text.replace("\r\n", "\n");
      text = text.replace("\r", "\n");
      if (!this.isSelecting) {
         String potentialValue = new StringBuilder(this.value).insert(this.cursor, text).toString();
         if (this.checkString(potentialValue)) {
            this.value = new StringBuilder(potentialValue);
            this.cursor = this.cursor + text.length();
            this.updateLines();
            this.scrollToCursor(this.cursor);
            return true;
         }
      } else {
         String cache = this.value.toString();
         this.removeCharacterAtCursor(false);
         this.value.insert(this.cursor, text);
         if (this.checkString(this.value.toString())) {
            this.updateLines();
            this.isSelecting = false;
            this.cursor = this.cursor + text.length();
            this.scrollToCursor(this.cursor);
            return true;
         }

         this.value = new StringBuilder(cache);
      }

      return false;
   }

   public boolean checkString(String check) {
      int i = 0;
      int substrAmount = 100;

      do {
         int end = Mth.clamp(i + substrAmount, 0, check.length());
         String s = check.substring(i, end);
         if (!s.matches(this.filter)) {
            return false;
         }

         i += substrAmount;
      } while (i < check.length());

      return true;
   }

   public void setCursorAtMouse(float mx, float my) {
      if (!this.value.isEmpty()) {
         Font font = Minecraft.getInstance().font;
         float x = mx - this.getTextOffsetLeft();
         float y = my - this.textOffsetY;
         y = Mth.clamp(y, 0.0F, this.getHeight() / this.textScale - this.textOffsetY - 9.0F * this.textScale) + this.scrollAmount;
         int lineId = (int)Math.floor(y / (9.0F * this.textScale));
         lineId = Mth.clamp(lineId, 0, this.lines.size() - 1);
         FDTextBox.Line line = this.lines.get(lineId);
         if (line.begin != line.end) {
            float prevWidth = font.width(this.value.substring(line.begin, line.begin + 1)) * this.textScale / 2.0F;

            for (int i = 0; i < line.end - line.begin; i++) {
               String ws = this.value.substring(line.begin, line.begin + i);
               float width = font.width(ws) * this.textScale;
               if (x < width + Math.abs(width - prevWidth) / 2.0F) {
                  this.cursor = line.begin + i;
                  return;
               }

               prevWidth = width;
            }
         }

         this.cursor = line.end;
         if (lineId != this.lines.size() - 1) {
            this.cursor--;
         }
      } else {
         this.cursor = 0;
      }
   }

   public boolean scroll(int times) {
      Font font = Minecraft.getInstance().font;
      float amount = this.textScale * 9.0F * times;
      float prev = this.scrollAmount;
      this.scrollAmount = Mth.clamp(this.scrollAmount + amount, 0.0F, this.getMaxScrollAmount());
      return prev != this.scrollAmount;
   }

   public void scrollToCursor(int cursor) {
      int lineId = this.getLineAtCursor(cursor);
      this.lastLineId = Mth.clamp(this.lastLineId, 0, this.lines.size() - 1);
      if (lineId > this.lastLineId) {
         this.scroll(lineId - this.lastLineId);
      } else if (lineId < this.firstLineId) {
         this.scroll(lineId - this.firstLineId);
      }
   }

   public boolean charTyped(char character, int key) {
      return this.insertText(Character.toString(character));
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode != 340 && keyCode != 344) {
         switch (keyCode) {
            case 65:
               if (Screen.hasControlDown()) {
                  this.isSelecting = true;
                  this.cursor = this.lines.getLast().end;
                  this.selectionCursor = 0;
                  return true;
               }

               return false;
            case 67:
               if (Screen.hasControlDown() && !this.value.isEmpty() && this.cursor != this.selectionCursor) {
                  int begin = Math.min(this.cursor, this.selectionCursor);
                  int end = Math.max(this.cursor, this.selectionCursor);
                  String s = this.value.substring(begin, end);
                  Minecraft.getInstance().keyboardHandler.setClipboard(s);
                  return true;
               }

               return false;
            case 86:
               if (Screen.hasControlDown()) {
                  String s = Minecraft.getInstance().keyboardHandler.getClipboard();
                  this.insertText(s);
                  return true;
               }

               return false;
            case 88:
               if (Screen.hasControlDown() && !this.value.isEmpty() && this.cursor != this.selectionCursor) {
                  int begin = Math.min(this.cursor, this.selectionCursor);
                  int end = Math.max(this.cursor, this.selectionCursor);
                  String s = this.value.substring(begin, end);
                  Minecraft.getInstance().keyboardHandler.setClipboard(s);
                  this.removeCharacterAtCursor(true);
                  return true;
               }

               return false;
            case 257:
               this.insertText("\n");
               return true;
            case 259:
               this.removeCharacterAtCursor(true);
               return true;
            case 262:
               this.moveCursorRight();
               return true;
            case 263:
               this.moveCursorLeft();
               return true;
            case 264:
               this.moveCursorDown();
               return true;
            case 265:
               this.moveCursorUp();
               return true;
            default:
               return false;
         }
      } else {
         if (!this.isSelecting) {
            this.selectionCursor = this.cursor;
            this.isSelecting = true;
         }

         return true;
      }
   }

   public boolean mouseScrolled(double mx, double my, double scrollX, double scrollY) {
      int scroll = (int)scrollY;
      return this.scroll(-scroll);
   }

   public boolean keyReleased(int key, int scanCode, int modifiers) {
      return super.keyReleased(key, scanCode, modifiers);
   }

   public boolean mouseClicked(double mx, double my, int key) {
      if (key == 0) {
         this.setCursorAtMouse((float)mx, (float)my);
         this.isSelecting = false;
         this.mouseSelecting = true;
         this.selectionCursor = this.cursor;
         return true;
      } else {
         return false;
      }
   }

   public void mouseMoved(double mx, double my) {
      if (this.mouseSelecting) {
         this.setCursorAtMouse((float)mx, (float)my);
         this.isSelecting = true;
      }

      super.mouseMoved(mx, my);
   }

   public boolean mouseReleased(double mx, double my, int key) {
      if (key == 0) {
         this.mouseSelecting = false;
         return true;
      } else {
         return false;
      }
   }

   public String getStringValue() {
      return this.value.toString();
   }

   public boolean isSelectionActive() {
      return this.isSelecting && this.selectionCursor != this.cursor;
   }

   public void setValue(String value) {
      if (value.matches(this.filter)) {
         this.value = new StringBuilder(value);
      }
   }

   public void setFilter(String filter) {
      this.filter = filter;
   }

   @Override
   public void setWidth(float width) {
      super.setWidth(Mth.clamp(width, this.scrollerWidth, 2.1474836E9F));
   }

   @Override
   public void setHeight(float height) {
      super.setHeight(height);
   }

   @Override
   public void onFocused(boolean state) {
      if (!state) {
         this.isSelecting = false;
         this.cursor = 0;
         this.selectionCursor = 0;
      }
   }

   public float getTextOffsetLeft() {
      return this.textOffsetLeft;
   }

   public float getTextOffsetRight() {
      return this.textOffsetRight + this.scrollerWidth + 2.0F;
   }

   public float getTextOffsetY() {
      return this.textOffsetY;
   }

   public float getMaxScrollAmount() {
      Font font = Minecraft.getInstance().font;
      float lineHeight = 9.0F * this.textScale;
      int lineCount = this.lines.size() + 1;
      return Mth.clamp(lineHeight * lineCount - this.getHeight() - this.textOffsetY * 2.0F, 0.0F, 2.1474836E9F);
   }

   @Override
   public void applyOptions(FDEditorComponent owner, Annotation annotation) {
      if (annotation instanceof FDScale scale) {
         this.textScale = scale.value();
      }
   }

   public record Line(int begin, int end) {
   }
}
