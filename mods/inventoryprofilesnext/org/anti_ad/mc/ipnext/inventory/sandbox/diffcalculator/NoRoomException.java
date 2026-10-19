package org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class NoRoomException extends RuntimeException {
   public NoRoomException(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super(var1);
   }
}
