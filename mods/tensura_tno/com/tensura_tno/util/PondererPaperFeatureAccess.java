package com.tensura_tno.util;

public final class PondererPaperFeatureAccess {
   private static volatile boolean enabled = false;

   private PondererPaperFeatureAccess() {
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public static void setEnabled(boolean value) {
      enabled = value;
   }
}
