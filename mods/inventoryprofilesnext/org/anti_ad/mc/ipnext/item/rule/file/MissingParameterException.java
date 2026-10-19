package org.anti_ad.mc.ipnext.item.rule.file;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class MissingParameterException extends RuntimeException {
   public MissingParameterException(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super(var1);
   }
}
