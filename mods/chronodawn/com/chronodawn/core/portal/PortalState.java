package com.chronodawn.core.portal;

public enum PortalState {
   INACTIVE,
   ACTIVATED,
   DEACTIVATED,
   STABILIZED;

   public boolean allowsTravel() {
      return this == ACTIVATED || this == STABILIZED;
   }

   public boolean isBidirectional() {
      return this == STABILIZED;
   }

   public boolean hasPortalBlocks() {
      return this == ACTIVATED || this == STABILIZED;
   }

   public boolean canBeStabilized() {
      return this == DEACTIVATED;
   }
}
