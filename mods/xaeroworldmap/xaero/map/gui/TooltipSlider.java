package xaero.map.gui;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import xaero.lib.client.gui.widget.ITooltipHaver;
import xaero.lib.client.gui.widget.Tooltip;

public class TooltipSlider extends AbstractSliderButton implements ITooltipHaver {
   protected final Supplier<Tooltip> tooltipSupplier;
   protected final Consumer<Double> onValue;
   protected final Function<TooltipSlider, Component> messageUpdater;

   public TooltipSlider(
      int x,
      int y,
      int w,
      int h,
      Component message,
      double value,
      Consumer<Double> onValue,
      Function<TooltipSlider, Component> messageUpdater,
      Supplier<Tooltip> tooltipSupplier
   ) {
      super(x, y, w, h, message, value);
      this.tooltipSupplier = tooltipSupplier;
      this.onValue = onValue;
      this.messageUpdater = messageUpdater;
   }

   public Supplier<Tooltip> getXaero_tooltip() {
      return this.tooltipSupplier;
   }

   protected void updateMessage() {
      this.setMessage(this.messageUpdater.apply(this));
   }

   protected void applyValue() {
      this.onValue.accept(this.value);
   }
}
