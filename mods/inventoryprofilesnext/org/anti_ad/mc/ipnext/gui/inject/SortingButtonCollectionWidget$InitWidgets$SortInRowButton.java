package org.anti_ad.mc.ipnext.gui.inject;

import kotlin.Unit;
import org.anti_ad.mc.ipnext.inventory.GeneralInventoryActions;

public final class SortingButtonCollectionWidget$InitWidgets$SortInRowButton extends SortingButtonCollectionWidget$InitWidgets$AutoHideSortButton {
   public SortingButtonCollectionWidget$InitWidgets$SortInRowButton(SortingButtonCollectionWidget$InitWidgets var1, boolean var2) {
      super(var1);
      this.this$0 = var1;
      this.this$0.this$0.getSnapableList().add(this);
      this.setClickEvent(SortingButtonCollectionWidget$InitWidgets$SortInRowButton::_init_$lambda$0);
   }

   private static final Unit _init_$lambda$0(SortingButtonCollectionWidget var0, boolean var1, int var2) {
      if (var2 == 0) {
         GeneralInventoryActions.INSTANCE.doSortInRows(var0.getContainer(), true, var1);
      }

      return Unit.INSTANCE;
   }
}
