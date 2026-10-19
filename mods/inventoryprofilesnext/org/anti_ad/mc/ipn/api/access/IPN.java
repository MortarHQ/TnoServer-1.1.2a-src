package org.anti_ad.mc.ipn.api.access;

import java.util.List;
import kotlin.jvm.JvmStatic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class IPN {
   @NotNull
   public static final IPN$Companion Companion = new IPN$Companion(null);
   @Nullable
   private static IPN _IPN;

   @NotNull
   public abstract IContainerClicker getContainerClicker();

   @NotNull
   public abstract List getLockedSlots();

   @Nullable
   protected static final IPN get_IPN() {
      return Companion.get_IPN();
   }

   @JvmStatic
   protected static final void set_IPN(@Nullable IPN var0) {
      Companion.set_IPN(var0);
   }

   @JvmStatic
   @NotNull
   public static final IPN getInstance() {
      return Companion.getInstance();
   }
}
