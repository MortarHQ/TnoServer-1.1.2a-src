package org.anti_ad.mc.ipnext.item.rule.natives;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class NativeRules {
   @NotNull
   public static final NativeRules INSTANCE = new NativeRules();
   @NotNull
   private static final Map map = DefinedNativeRulesKt.access$getNATIVE_MAP$p();

   private NativeRules() {
   }

   @NotNull
   public final Map getMap() {
      return map;
   }
}
