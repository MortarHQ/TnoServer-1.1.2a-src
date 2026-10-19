package team.creative.creativecore.common.util.text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.util.mc.ColorUtils;
import team.creative.creativecore.common.util.text.content.ContentItemStack;

public class TextBuilder {
   private static final NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
   private final List<Component> lines = new ArrayList<>();

   public TextBuilder() {
   }

   public TextBuilder(List<Component> components) {
      this.lines.addAll(components);
   }

   public TextBuilder add(Component component) {
      if (this.lines.isEmpty()) {
         this.lines.add(component);
      } else {
         this.lines.get(this.lines.size() - 1).getSiblings().add(component);
      }

      return this;
   }

   public TextBuilder(String text) {
      this.text(text);
   }

   public TextBuilder translateIfCan(String text) {
      String translated = GuiControl.translate(text);
      if (!translated.equals(text)) {
         this.text(translated);
      }

      return this;
   }

   public TextBuilder translate(String text) {
      this.text(GuiControl.translate(text));
      return this;
   }

   public TextBuilder translate(String text, Object... param) {
      this.text(GuiControl.translate(text, param));
      return this;
   }

   public TextBuilder text(String text) {
      if (!text.contains("\n")) {
         this.add(Component.literal(text));
         return this;
      }

      String[] lines = text.split("\\n");
      this.add(Component.literal(lines[0]));

      for (int i = 1; i < lines.length; i++) {
         this.newLine();
         this.add(Component.literal(lines[i]));
      }

      return this;
   }

   public TextBuilder number(float number) {
      return this.number(number, false);
   }

   public TextBuilder number(float number, boolean rounded) {
      if (rounded) {
         this.text(Math.round(number) + "");
      } else {
         this.text(format.format(number));
      }

      return this;
   }

   public TextBuilder number(double number) {
      return this.number(number, false);
   }

   public TextBuilder number(double number, boolean rounded) {
      if (rounded) {
         this.text(Math.round(number) + "");
      } else {
         this.text(format.format(number));
      }

      return this;
   }

   public TextBuilder newLine() {
      this.lines.add(Component.empty());
      return this;
   }

   public TextBuilder bool(boolean value) {
      if (value) {
         this.color(-16711936).translate("gui.true");
      } else {
         this.color(-65536).translate("gui.false");
      }

      return this;
   }

   public TextBuilder color(int color) {
      int r = ColorUtils.red(color);
      int g = ColorUtils.green(color);
      int b = ColorUtils.blue(color);
      int a = ColorUtils.alpha(color);
      this.text("" + ChatFormatting.RED + r + " " + ChatFormatting.GREEN + g + " " + ChatFormatting.BLUE + b + (a < 255 ? " " + ChatFormatting.WHITE + a : ""));
      return this;
   }

   public TextBuilder stack(ItemStack stack) {
      this.add(MutableComponent.create(new ContentItemStack(stack)));
      return this;
   }

   public List<Component> build() {
      return this.lines;
   }
}
