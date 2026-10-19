package dev.ftb.mods.ftbfiltersystem.api.client.gui;

import dev.ftb.mods.ftbfiltersystem.api.NumericComparison;
import dev.ftb.mods.ftbfiltersystem.api.client.gui.widget.CustomCheckbox;
import dev.ftb.mods.ftbfiltersystem.api.filter.AbstractComparisonFilter;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import java.util.function.BiFunction;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.layouts.SpacerElement;
import net.minecraft.client.gui.layouts.GridLayout.RowHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.apache.commons.lang3.math.NumberUtils;

public abstract class AbstractComparisonConfigScreen<T extends AbstractComparisonFilter> extends AbstractFilterConfigScreen<T> {
   private final BiFunction<SmartFilter.Compound, NumericComparison, T> comparisonFactory;
   protected CycleButton<NumericComparison.ComparisonOp> opBtn;
   protected EditBox numBox;
   protected CustomCheckbox pctCheckBox;

   public AbstractComparisonConfigScreen(T filter, AbstractFilterScreen parentScreen, BiFunction<SmartFilter.Compound, NumericComparison, T> comparisonFactory) {
      super(filter, parentScreen, 176, 55);
      this.comparisonFactory = comparisonFactory;
   }

   @Override
   protected void init() {
      super.init();
      GridLayout layout = new GridLayout(this.leftPos + 8, this.topPos + 20);
      RowHelper rowHelper = layout.createRowHelper(5);
      this.opBtn = (CycleButton<NumericComparison.ComparisonOp>)rowHelper.addChild(
         CycleButton.builder(NumericComparison.ComparisonOp::getDisplayName)
            .withValues(NumericComparison.ComparisonOp.values())
            .withInitialValue(this.filter.getComparison().op())
            .displayOnlyValue()
            .create(0, 0, 20, 9 + 8, Component.empty(), (btn, val) -> {})
      );
      rowHelper.addChild(SpacerElement.width(10));
      rowHelper.addChild(
         Button.builder(Component.literal("-"), b -> this.adjustVal(-1)).size(12, 12).build(),
         LayoutSettings.defaults().alignVerticallyMiddle().paddingRight(2)
      );
      this.numBox = (EditBox)rowHelper.addChild(new EditBox(this.font, 0, 0, 30, 9 + 8, Component.empty()));
      rowHelper.addChild(
         Button.builder(Component.literal("+"), b -> this.adjustVal(1)).size(12, 12).build(), LayoutSettings.defaults().alignVerticallyMiddle().paddingLeft(2)
      );
      this.numBox.setValue(Integer.toString(this.filter.getComparison().value()));
      this.numBox.setResponder(str -> this.adjustVal(0));
      this.numBox.setFilter(str -> this.isValidNumber(str) || str.isEmpty());
      if (this.filter.allowsPercentage()) {
         rowHelper.addChild(SpacerElement.height(5), 5);
         MutableComponent txt = Component.translatable("ftbfiltersystem.gui.percentage");
         this.pctCheckBox = (CustomCheckbox)rowHelper.addChild(
            new CustomCheckbox(0, 0, this.font.width(txt), 20, txt, this.filter.getComparison().percentage()), 5
         );
      }

      layout.arrangeElements();
      layout.visitWidgets(x$0 -> {
         AbstractWidget var10000 = (AbstractWidget)this.addRenderableWidget(x$0);
      });
   }

   public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
      if ((pKeyCode == 257 || pKeyCode == 335) && this.numBox.canConsumeInput()) {
         this.applyChanges();
      }

      return super.keyPressed(pKeyCode, pScanCode, pModifiers);
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double dirX, double dirY) {
      this.adjustVal(dirY < 0.0 ? -1 : 1);
      return true;
   }

   private boolean isValidNumber(String str) {
      if (NumberUtils.isDigits(str)) {
         if (!this.isPercent()) {
            return true;
         }

         int v = Integer.parseInt(str);
         return v >= 0 && v <= 100;
      } else {
         return false;
      }
   }

   private void adjustVal(int amount) {
      try {
         String s = this.numBox.getValue();
         int value = (s.isEmpty() ? 0 : Integer.parseInt(s)) + amount;
         this.numBox.setResponder(str -> {});
         this.numBox.setValue(String.valueOf(value));
         this.numBox.setResponder(str -> this.adjustVal(0));
      } catch (NumberFormatException var4) {
      }
   }

   protected T makeNewFilter() {
      try {
         int value = Integer.parseInt(this.numBox.getValue());
         NumericComparison comparison = new NumericComparison((NumericComparison.ComparisonOp)this.opBtn.getValue(), value, this.isPercent());
         return this.comparisonFactory.apply(this.filter.getParent(), comparison);
      } catch (NumberFormatException e) {
         return null;
      }
   }

   protected boolean isPercent() {
      return this.pctCheckBox != null && this.pctCheckBox.selected();
   }
}
