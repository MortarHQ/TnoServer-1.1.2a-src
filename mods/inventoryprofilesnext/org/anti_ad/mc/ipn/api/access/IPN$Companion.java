package org.anti_ad.mc.ipn.api.access;

import kotlin.jvm.JvmStatic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class IPN$Companion {
   private IPN$Companion() {
   }

   @Nullable
   protected final IPN get_IPN() {
      return IPN.access$get_IPN$cp();
   }

   @JvmStatic
   protected final void set_IPN(@Nullable IPN var1) {
      IPN.access$set_IPN$cp(var1);
   }

   @JvmStatic
   @NotNull
   public final IPN getInstance() {
      IPN var10000 = IPN.get_IPN();
      if (var10000 == null) {
         var10000 = new IPN$Companion$DummyIPN();
      }

      return var10000;
   }
}
