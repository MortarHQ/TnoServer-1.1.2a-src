package cc.sighs.oelib.config.ui.widget;

import cc.sighs.oelib.config.ui.entries.AbstractConfigEntry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class DynamicElementListWidget {
   protected final Minecraft minecraft;
   protected final List<AbstractConfigEntry<?>> children = new ArrayList<>();
   public int left;
   public int width;
   public int top;
   public int bottom;

   public DynamicElementListWidget(Minecraft minecraft, int width, int height, int top, int bottom) {
      this.minecraft = minecraft;
      this.left = 0;
      this.width = width;
      this.top = top;
      this.bottom = bottom;
   }

   public void setLeftPos(int left) {
      this.left = left;
   }

   public int getItemWidth() {
      return this.width - 24;
   }

   public List<AbstractConfigEntry<?>> children() {
      return this.children;
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
      int y = this.top;

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
}
