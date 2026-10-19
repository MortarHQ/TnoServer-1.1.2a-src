package it.hurts.sskirillss.relics.api.events.common;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

public class TooltipDisplayEvent extends Event {
   private final ItemStack stack;
   private final GuiGraphics graphics;
   private final int width;
   private final int height;
   private final int x;
   private final int y;

   public ItemStack getStack() {
      return this.stack;
   }

   public GuiGraphics getGraphics() {
      return this.graphics;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TooltipDisplayEvent other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getWidth() != other.getWidth()) {
         return false;
      } else if (this.getHeight() != other.getHeight()) {
         return false;
      } else if (this.getX() != other.getX()) {
         return false;
      } else if (this.getY() != other.getY()) {
         return false;
      } else {
         Object this$stack = this.getStack();
         Object other$stack = other.getStack();
         if (this$stack == null ? other$stack == null : this$stack.equals(other$stack)) {
            Object this$graphics = this.getGraphics();
            Object other$graphics = other.getGraphics();
            return this$graphics == null ? other$graphics == null : this$graphics.equals(other$graphics);
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof TooltipDisplayEvent;
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getWidth();
      result = result * 59 + this.getHeight();
      result = result * 59 + this.getX();
      result = result * 59 + this.getY();
      Object $stack = this.getStack();
      result = result * 59 + ($stack == null ? 43 : $stack.hashCode());
      Object $graphics = this.getGraphics();
      return result * 59 + ($graphics == null ? 43 : $graphics.hashCode());
   }

   public String toString() {
      return "TooltipDisplayEvent(stack="
         + this.getStack()
         + ", graphics="
         + this.getGraphics()
         + ", width="
         + this.getWidth()
         + ", height="
         + this.getHeight()
         + ", x="
         + this.getX()
         + ", y="
         + this.getY()
         + ")";
   }

   public TooltipDisplayEvent(ItemStack stack, GuiGraphics graphics, int width, int height, int x, int y) {
      this.stack = stack;
      this.graphics = graphics;
      this.width = width;
      this.height = height;
      this.x = x;
      this.y = y;
   }
}
