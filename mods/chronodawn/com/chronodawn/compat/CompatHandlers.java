package com.chronodawn.compat;

public class CompatHandlers {
   public static final ItemDataHandler ITEM_DATA = new ItemDataHandler121();

   private CompatHandlers() {
      throw new UnsupportedOperationException("Utility class - do not instantiate");
   }
}
