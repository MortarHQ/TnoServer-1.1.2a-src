package org.anti_ad.mc.ipnext.inventory;

import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class AreaType$Companion {
   private AreaType$Companion() {
   }

   @NotNull
   public final AreaType inSlots(boolean var1, @NotNull Function0 var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      return new AreaType$Companion$inSlots$1(var2, var1);
   }

   @NotNull
   public final AreaType match(boolean var1, @NotNull Function1 var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      return new AreaType$Companion$match$1(var1, var2);
   }

   @NotNull
   public final AreaType playerInvSlots(@NotNull int[] var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      return this.playerInvSlots(var2, AreaType$Companion::playerInvSlots$lambda$0);
   }

   @NotNull
   public final AreaType playerInvSlots(boolean var1, @NotNull Function0 var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      return new AreaType$Companion$playerInvSlots$2(var2);
   }

   private static final Iterable playerInvSlots$lambda$0(int[] var0) {
      return ArraysKt.asIterable(var0);
   }
}
