package org.anti_ad.mc.ipnext.item.rule.natives;

import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.item.ComponentUtils;
import org.anti_ad.mc.ipnext.item.ItemType;

public final class AllComponentsRule extends NativeRule {
   public AllComponentsRule() {
      this.setComparator(AllComponentsRule::_init_$lambda$0);
   }

   private static final int _init_$lambda$0(ItemType var0, ItemType var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      return ComponentUtils.INSTANCE.compareComponents(var0, var1);
   }
}
