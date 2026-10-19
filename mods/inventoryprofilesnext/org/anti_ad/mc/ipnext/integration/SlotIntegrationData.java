package org.anti_ad.mc.ipnext.integration;

import kotlinx.serialization.Serializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Serializable
public final class SlotIntegrationData {
   @NotNull
   public static final SlotIntegrationData$Companion Companion = new SlotIntegrationData$Companion(null);
   private boolean ignore;
   private boolean dirty;

   public SlotIntegrationData(boolean var1) {
      this.ignore = var1;
   }

   public final boolean getIgnore() {
      return this.ignore;
   }

   public final void setIgnore(boolean var1) {
      this.ignore = var1;
   }

   public final boolean component1() {
      return this.ignore;
   }

   @NotNull
   public final SlotIntegrationData copy(boolean var1) {
      return new SlotIntegrationData(var1);
   }

   @NotNull
   @Override
   public final String toString() {
      return "SlotIntegrationData(ignore=" + this.ignore + ")";
   }

   @Override
   public final int hashCode() {
      return Boolean.hashCode(this.ignore);
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else {
         return !(var1 instanceof SlotIntegrationData var2) ? false : this.ignore == var2.ignore;
      }
   }

   public SlotIntegrationData() {
      this(false, 1, null);
   }
}
