package cc.sighs.oelib.config.ui.widget;

import cc.sighs.oelib.config.ui.entries.AbstractConfigEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class DynamicEntryListWidget extends DynamicElementListWidget {
   public static final ResourceLocation VERTICAL_HEADER_SEPARATOR = ResourceLocation.withDefaultNamespace("textures/gui/menu_list_background.png");
   private final DynamicEntryListWidget.Scroller scroller = new DynamicEntryListWidget.Scroller();

   public DynamicEntryListWidget(Minecraft minecraft, int width, int height, int top, int bottom) {
      super(minecraft, width, height, top, bottom);
   }

   public void scrollTo(int pixels, boolean animated) {
      this.scroller.setTarget(pixels, animated ? 10L : 0L);
   }

   public void offset(double pixels) {
      this.scroller.setTarget(this.scroller.currentInt() + (int)pixels, 10L);
   }

   public int getScrollOffset() {
      return this.scroller.currentInt();
   }

   @Override
   public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
      this.scroller.update(delta);
      int y = this.top - this.scroller.currentInt();
      int totalHeight = 0;

      for (AbstractConfigEntry<?> entry : this.children) {
         totalHeight += entry.getItemHeight();
      }

      int maxScroll = Math.max(0, totalHeight - (this.bottom - this.top));
      this.scroller.setMax(maxScroll);

      for (int i = 0; i < this.children.size(); i++) {
         AbstractConfigEntry<?> entry = this.children.get(i);
         int h = entry.getItemHeight();
         boolean hovered = mouseY >= y
            && mouseY < y + h
            && mouseX >= this.left
            && mouseX < this.left + this.width
            && mouseY >= this.top
            && mouseY < this.bottom;
         entry.render(graphics, i, y, this.left + 12, this.getItemWidth(), h, mouseX, mouseY, hovered, delta);
         y += h;
      }
   }

   private static final class Scroller {
      private double value;
      private double target;
      private int max;
      private long duration;
      private long start;

      void setMax(int max) {
         this.max = Math.max(0, max);
         this.clampAll();
      }

      void setTarget(int t, long duration) {
         this.target = t;
         this.duration = duration;
         this.start = System.currentTimeMillis();
         this.clampAll();
      }

      void update(float delta) {
         double diff = this.target - this.value;
         if (this.duration > 0L && !(Math.abs(diff) < 0.5)) {
            double factor = Math.min(1.0, (double)(System.currentTimeMillis() - this.start) / this.duration);
            this.value += diff * factor;
         } else {
            this.value = this.target;
         }
      }

      int currentInt() {
         return (int)Math.round(this.value);
      }

      private double clamp(double v) {
         if (v < 0.0) {
            return 0.0;
         } else {
            return v > this.max ? this.max : v;
         }
      }

      private void clampAll() {
         this.target = this.clamp(this.target);
         this.value = this.clamp(this.value);
      }
   }
}
