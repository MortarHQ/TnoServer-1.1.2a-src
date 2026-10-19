package org.anti_ad.mc.ipnext.inventory.sandbox;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ContainerSandboxKt {
   @NotNull
   public static final List toList(@Nullable SandboxClick var0) {
      List var1 = new ArrayList();

      while (var0 != null) {
         var1.add(var0);
         var0 = var0.getPreviousClick();
      }

      CollectionsKt.reverse(var1);
      return var1;
   }
}
