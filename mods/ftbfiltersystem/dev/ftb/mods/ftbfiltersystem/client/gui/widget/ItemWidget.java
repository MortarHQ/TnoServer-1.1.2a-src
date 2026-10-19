package dev.ftb.mods.ftbfiltersystem.client.gui.widget;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag.Default;
import org.jetbrains.annotations.NotNull;

public class ItemWidget extends AbstractWidget {
   private ItemStack stack;
   private long lastClick;

   public ItemWidget(int pWidth, int pHeight, @NotNull ItemStack stack) {
      this(0, 0, pWidth, pHeight, stack);
   }

   public ItemWidget(int pX, int pY, int pWidth, int pHeight, @NotNull ItemStack stack) {
      super(pX, pY, pWidth, pHeight, Component.empty());
      this.setStack(stack);
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public void setStack(ItemStack stack) {
      this.stack = stack;
      this.updateTooltip();
   }

   protected void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getWidth(), -8355712);
      guiGraphics.fill(this.getX() + 1, this.getY() + 1, this.getX() + this.getWidth() - 1, this.getY() + this.getWidth() - 1, -6250336);
      guiGraphics.renderItem(this.stack, this.getX() + 1, this.getY() + 1);
   }

   protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
   }

   public void onClick(double mouseX, double mouseY) {
      if (!this.getStack().isEmpty()) {
         long now = Util.getMillis();
         if (now - this.lastClick < 250L) {
            this.handleClick(true);
         } else {
            this.handleClick(false);
            this.lastClick = now;
         }
      }
   }

   protected void handleClick(boolean doubleClick) {
   }

   private void updateTooltip() {
      if (this.stack.isEmpty()) {
         this.setTooltip(null);
      } else {
         Default flag = Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.ADVANCED : TooltipFlag.NORMAL;
         this.stack
            .getTooltipLines(TooltipContext.of(Minecraft.getInstance().level), Minecraft.getInstance().player, flag)
            .stream()
            .reduce((c1, c2) -> c1.copy().append("\n").append(c2))
            .ifPresent(c -> this.setTooltip(Tooltip.create(c)));
      }
   }
}
