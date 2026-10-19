package org.anti_ad.mc.ipnext.item.rule.file;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class SelfReferenceException extends RuntimeException {
   public SelfReferenceException(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super(var1);
   }
}
