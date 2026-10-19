package org.anti_ad.mc.ipnext.event.autorefill;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class AutoRefillHandler$IdAndIndex {
   @NotNull
   private final Function0 id;
   @NotNull
   private final Function0 index;

   public AutoRefillHandler$IdAndIndex(@NotNull Function0 var1, @NotNull Function0 var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.id = var1;
      this.index = var2;
   }

   @NotNull
   public final Function0 getId() {
      return this.id;
   }

   @NotNull
   public final Function0 getIndex() {
      return this.index;
   }
}
