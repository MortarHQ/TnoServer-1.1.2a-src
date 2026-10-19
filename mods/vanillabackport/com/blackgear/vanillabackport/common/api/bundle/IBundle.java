package com.blackgear.vanillabackport.common.api.bundle;

public interface IBundle {
   void setSelectedItem(int var1);

   default int getSelectedItem() {
      return -1;
   }

   int getNumberOfItemsToShow();

   interface Mutable {
      void toggleSelectedItem(int var1);

      boolean indexIsOutsideAllowedBounds(int var1);
   }
}
