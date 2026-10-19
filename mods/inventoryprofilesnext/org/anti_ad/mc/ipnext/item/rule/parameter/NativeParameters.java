package org.anti_ad.mc.ipnext.item.rule.parameter;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class NativeParameters {
   @NotNull
   public static final NativeParameters INSTANCE = new NativeParameters();
   @NotNull
   private static final Map map = NativeParametersKt.access$getPARAMETER_MAP$p();

   private NativeParameters() {
   }

   @NotNull
   public final Map getMap() {
      return map;
   }
}
