package org.anti_ad.mc.ipnext.compat.integrations;

import org.jetbrains.annotations.NotNull;

public final class Integrations {
   @NotNull
   public static final Integrations INSTANCE = new Integrations();
   private static final int carpetEmptyShulkersStackSize = 1;

   private Integrations() {
   }

   public final int getCarpetEmptyShulkersStackSize() {
      return carpetEmptyShulkersStackSize;
   }

   public final boolean init() {
      return false;
   }
}
