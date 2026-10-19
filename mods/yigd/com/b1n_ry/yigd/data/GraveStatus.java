package com.b1n_ry.yigd.data;

public enum GraveStatus {
   UNCLAIMED,
   CLAIMED,
   DESTROYED;

   public int getTransparentColor() {
      return switch (this) {
         case UNCLAIMED -> 654311168;
         case CLAIMED -> 637599488;
         case DESTROYED -> 654245888;
      };
   }
}
