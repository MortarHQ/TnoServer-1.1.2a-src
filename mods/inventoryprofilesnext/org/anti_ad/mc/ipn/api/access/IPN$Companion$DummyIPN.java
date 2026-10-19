package org.anti_ad.mc.ipn.api.access;

import java.util.List;
import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;

final class IPN$Companion$DummyIPN extends IPN {
   public IPN$Companion$DummyIPN() {
   }

   @NotNull
   @Override
   public final IContainerClicker getContainerClicker() {
      String var1 = "Not yet implemented";
      throw new NotImplementedError("An operation is not implemented: ".concat(String.valueOf(var1)));
   }

   @NotNull
   @Override
   public final List getLockedSlots() {
      String var1 = "Not yet implemented";
      throw new NotImplementedError("An operation is not implemented: ".concat(String.valueOf(var1)));
   }
}
