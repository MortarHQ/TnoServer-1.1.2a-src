package xaero.map.gui;

import java.util.function.Supplier;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;
import xaero.lib.client.gui.widget.ITooltipHaver;
import xaero.lib.client.gui.widget.Tooltip;

public class TooltipButton extends Button implements ITooltipHaver {
   protected Supplier<Tooltip> tooltipSupplier;

   public TooltipButton(int x, int y, int w, int h, Component message, OnPress action, Supplier<Tooltip> tooltipSupplier) {
      super(x, y, w, h, message, action, DEFAULT_NARRATION);
      this.tooltipSupplier = tooltipSupplier;
   }

   public Supplier<Tooltip> getXaero_tooltip() {
      return this.tooltipSupplier;
   }
}
