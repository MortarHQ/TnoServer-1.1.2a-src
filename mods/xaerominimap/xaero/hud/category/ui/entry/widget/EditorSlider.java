package xaero.hud.category.ui.entry.widget;

import java.util.function.IntConsumer;
import java.util.function.Supplier;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import xaero.common.gui.IXaeroNarratableWidget;
import xaero.hud.category.ui.GuiCategoryEditor;

public class EditorSlider extends AbstractOptionSliderButton implements IXaeroNarratableWidget {
   protected int currentIndex;
   protected int prevNarrationIndex;
   protected int optionCount;
   protected IntConsumer updatedIndexConsumer;
   protected Supplier<Component> messageSupplier;
   protected final GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList;

   public EditorSlider(
      IntConsumer updatedIndexConsumer,
      Supplier<Component> messageSupplier,
      int currentIndex,
      int optionCount,
      int widthIn,
      int heightIn,
      GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList
   ) {
      super(null, 2, 2, widthIn, heightIn, 0.0);
      this.updatedIndexConsumer = updatedIndexConsumer;
      this.messageSupplier = messageSupplier;
      this.optionCount = optionCount;
      this.currentIndex = this.prevNarrationIndex = currentIndex;
      this.value = this.toSliderValue(currentIndex);
      this.rowList = rowList;
      this.updateMessage();
   }

   public boolean keyPressed(int i, int j, int k) {
      if (i == 263) {
         this.manualOptionChange(this.currentIndex - 1);
         return false;
      } else if (i == 262) {
         this.manualOptionChange(this.currentIndex + 1);
         return false;
      } else {
         return super.keyPressed(i, j, k);
      }
   }

   private void manualOptionChange(int index) {
      if (index < 0) {
         index = 0;
      } else if (index >= this.optionCount) {
         index = this.optionCount - 1;
      }

      this.value = this.toSliderValue(index);
      this.applyValue();
      this.updateMessage();
   }

   @Override
   public MutableComponent createNarrationMessage() {
      return Component.literal("");
   }

   protected void applyValue() {
      this.currentIndex = this.toValue(this.value);
      this.updatedIndexConsumer.accept(this.currentIndex);
   }

   protected void updateMessage() {
      this.setMessage(this.messageSupplier.get());
      if (this.currentIndex != this.prevNarrationIndex) {
         this.rowList.narrateSelection();
      }

      this.prevNarrationIndex = this.currentIndex;
   }

   public double toSliderValue(int i) {
      return (double)i / (this.optionCount - 1);
   }

   public int toValue(double d) {
      return (int)this.clamp(Mth.lerp(Mth.clamp(d, 0.0, 1.0), 0.0, this.optionCount - 1));
   }

   private double clamp(double d) {
      d = Math.round(d);
      return Mth.clamp(d, 0.0, this.optionCount - 1);
   }
}
