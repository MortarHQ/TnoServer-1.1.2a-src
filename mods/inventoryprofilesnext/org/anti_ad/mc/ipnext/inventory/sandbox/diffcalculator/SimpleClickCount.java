package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class SimpleClickCount implements Comparable {
   @NotNull
   private final List clicks = new ArrayList();

   public final int getSize() {
      return this.clicks.size();
   }

   @NotNull
   public final List getClicks() {
      return this.clicks;
   }

   public final int compareTo(@NotNull SimpleClickCount var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return Intrinsics.compare(this.getSize(), var1.getSize());
   }
}
