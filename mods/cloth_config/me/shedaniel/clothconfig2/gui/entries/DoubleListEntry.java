package me.shedaniel.clothconfig2.gui.entries;

import java.util.Optional;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.ApiStatus.Internal;

@OnlyIn(Dist.CLIENT)
public class DoubleListEntry extends AbstractNumberListEntry<Double> {
   @Deprecated
   @Internal
   public DoubleListEntry(Component fieldName, Double value, Component resetButtonKey, Supplier<Double> defaultValue, Consumer<Double> saveConsumer) {
      super(fieldName, value, resetButtonKey, defaultValue);
      this.saveCallback = saveConsumer;
   }

   @Deprecated
   @Internal
   public DoubleListEntry(
      Component fieldName,
      Double value,
      Component resetButtonKey,
      Supplier<Double> defaultValue,
      Consumer<Double> saveConsumer,
      Supplier<Optional<Component[]>> tooltipSupplier
   ) {
      this(fieldName, value, resetButtonKey, defaultValue, saveConsumer, tooltipSupplier, false);
   }

   @Deprecated
   @Internal
   public DoubleListEntry(
      Component fieldName,
      Double value,
      Component resetButtonKey,
      Supplier<Double> defaultValue,
      Consumer<Double> saveConsumer,
      Supplier<Optional<Component[]>> tooltipSupplier,
      boolean requiresRestart
   ) {
      super(fieldName, value, resetButtonKey, defaultValue, tooltipSupplier, requiresRestart);
      this.saveCallback = saveConsumer;
   }

   @Override
   protected Entry<Double, Double> getDefaultRange() {
      return new SimpleEntry<>(-Double.MAX_VALUE, Double.MAX_VALUE);
   }

   public DoubleListEntry setMinimum(double minimum) {
      this.minimum = minimum;
      return this;
   }

   public DoubleListEntry setMaximum(double maximum) {
      this.maximum = maximum;
      return this;
   }

   public Double getValue() {
      try {
         return Double.valueOf(this.textFieldWidget.getValue());
      } catch (Exception e) {
         return 0.0;
      }
   }

   @Override
   public Optional<Component> getError() {
      try {
         double i = Double.parseDouble(this.textFieldWidget.getValue());
         if (i > this.maximum) {
            return Optional.of(Component.translatable("text.cloth-config.error.too_large", new Object[]{this.maximum}));
         }

         if (i < this.minimum) {
            return Optional.of(Component.translatable("text.cloth-config.error.too_small", new Object[]{this.minimum}));
         }
      } catch (NumberFormatException ex) {
         return Optional.of(Component.translatable("text.cloth-config.error.not_valid_number_double"));
      }

      return super.getError();
   }
}
