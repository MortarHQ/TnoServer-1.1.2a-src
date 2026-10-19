package org.anti_ad.mc.ipnext.gui.inject;

import kotlin.Unit;
import org.anti_ad.mc.ipnext.gui.inject.base.SortButtonWidget;
import org.anti_ad.mc.ipnext.inventory.GeneralInventoryActions;

public final class SortingButtonCollectionWidget$InitWidgets$MoveButton extends SortButtonWidget {
   public SortingButtonCollectionWidget$InitWidgets$MoveButton(SortingButtonCollectionWidget$InitWidgets var1, boolean var2) {
      this.this$0 = var1;
      this.this$0.this$0.getSnapableList().add(this);
      this.setClickEvent(SortingButtonCollectionWidget$InitWidgets$MoveButton::_init_$lambda$0);
   }

   private static final Unit _init_$lambda$0(boolean var0, int var1) {
      GeneralInventoryActions.INSTANCE.doMoveMatch(var0, true);
      return Unit.INSTANCE;
   }
}
