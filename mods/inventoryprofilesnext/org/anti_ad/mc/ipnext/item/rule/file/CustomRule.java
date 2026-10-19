package org.anti_ad.mc.ipnext.item.rule.file;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.item.rule.BaseRule;
import org.jetbrains.annotations.NotNull;

public final class CustomRule extends BaseRule {
   public CustomRule(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.setComparator(new CustomRule$1(var1));
   }
}
