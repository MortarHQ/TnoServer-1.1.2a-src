package org.anti_ad.mc.ipnext.gui.inject;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.container.ExtensionsKt;
import org.anti_ad.mc.ipnext.gui.inject.base.SortButtonWidget;

public abstract class SortingButtonCollectionWidget$InitWidgets$AutoHideSortButton extends SortButtonWidget {
   public SortingButtonCollectionWidget$InitWidgets$AutoHideSortButton(SortingButtonCollectionWidget$InitWidgets var1) {
      this.this$0 = var1;
   }

   @Override
   public boolean getVisible() {
      AbstractContainerMenu var1;
      return (var1 = Vanilla.INSTANCE.container()) instanceof AnvilMenu
         ? ExtensionsKt.get_levelCost_/* $VF was: get(levelCost) */((AnvilMenu)var1) == 0 && super.getVisible()
         : super.getVisible();
   }

   @Override
   public void setVisible(boolean var1) {
      super.setVisible(var1);
   }
}
