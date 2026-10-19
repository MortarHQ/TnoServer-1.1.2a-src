package dev.ftb.mods.ftblibrary.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class TooltipList {
   private final List<Component> lines = new ArrayList<>();
   public int backgroundColor = -1072693232;
   public int borderColorStart = 1347420415;
   public int borderColorEnd = (this.borderColorStart & 16711422) >> 1 | this.borderColorStart & 0xFF000000;
   public int maxWidth = 0;
   public int xOffset = 0;
   public int yOffset = 0;

   public boolean shouldRender() {
      return !this.lines.isEmpty();
   }

   public void reset() {
      this.lines.clear();
      this.backgroundColor = -1072693232;
      this.borderColorStart = 1347420415;
      this.borderColorEnd = (this.borderColorStart & 16711422) >> 1 | this.borderColorStart & 0xFF000000;
      this.maxWidth = 0;
      this.xOffset = 0;
      this.yOffset = 0;
   }

   public void add(Component component) {
      this.lines.add(component);
   }

   public void blankLine() {
      this.add(Component.empty());
   }

   public void styledString(String text, Style style) {
      this.add(Component.literal(text).withStyle(style));
   }

   public void styledString(String text, ChatFormatting color) {
      this.add(Component.literal(text).withStyle(color));
   }

   public void styledTranslate(String key, Style style, Object... objects) {
      this.add(Component.translatable(key, objects).withStyle(style));
   }

   public void string(String text) {
      this.styledString(text, Style.EMPTY);
   }

   public void translate(String key, Object... objects) {
      this.styledTranslate(key, Style.EMPTY, objects);
   }

   public List<Component> getLines() {
      return this.lines;
   }
}
