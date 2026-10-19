package org.anti_ad.mc.ipnext.gui.inject;

import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.screens.Screen;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.inventory.GeneralInventoryActions;

public final class SortingButtonCollectionWidget$InitWidgets$SortButton extends SortingButtonCollectionWidget$InitWidgets$AutoHideSortButton {
   public SortingButtonCollectionWidget$InitWidgets$SortButton(SortingButtonCollectionWidget$InitWidgets var1, boolean var2) {
      super(var1);
      this.this$0 = var1;
      this.this$0.this$0.getSnapableList().add(this);
      this.setClickEvent(SortingButtonCollectionWidget$InitWidgets$SortButton::_init_$lambda$0);
   }

   private static final Unit _init_$lambda$0(SortingButtonCollectionWidget var0, boolean var1, int var2) {
      Screen var3;
      if ((var3 = Vanilla.INSTANCE.screen()) != null && var3 == var0.getScreen() && Intrinsics.areEqual(Vanilla.INSTANCE.screen(), var0.getScreen())) {
         if (GuiSettings.INSTANCE.getCHANGE_SORT_ORDER_WITH_SCROLL_WHEEL().getBooleanValue()) {
            switch (var2) {
               case -10001:
                  if (Vanilla.INSTANCE.screen() == null) {
                     Log.INSTANCE.trace("Received scroll event with no active screen", new Exception());
                  }

                  ModSettings.INSTANCE.getSORT_ORDER().toggleNext();
                  break;
               case -10000:
                  if (Vanilla.INSTANCE.screen() == null) {
                     Log.INSTANCE.trace("Received scroll event with no active screen", new Exception());
                  }

                  ModSettings.INSTANCE.getSORT_ORDER().togglePrevious();
                  break;
               case 0:
                  GeneralInventoryActions.INSTANCE.doSort(var0.getContainer(), true, var1);
            }
         } else if (var2 == 0) {
            GeneralInventoryActions.INSTANCE.doSort(var0.getContainer(), true, var1);
         }
      }

      return Unit.INSTANCE;
   }
}
