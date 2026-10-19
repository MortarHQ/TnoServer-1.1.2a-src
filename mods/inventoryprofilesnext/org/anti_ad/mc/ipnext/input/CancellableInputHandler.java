package org.anti_ad.mc.ipnext.input;

import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.IInputHandler;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;
import org.anti_ad.mc.ipnext.event.ProfileSwitchHandler;
import org.anti_ad.mc.ipnext.event.autorefill.AutoRefillHandler;
import org.anti_ad.mc.ipnext.event.villagers.VillagerTradeManager;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nCancellableInputHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CancellableInputHandler.kt\norg/anti_ad/mc/ipnext/input/CancellableInputHandler\n+ 2 common.kt\norg/anti_ad/mc/common/extensions/CommonKt\n*L\n1#1,54:1\n74#2,10:55\n*S KotlinDebug\n*F\n+ 1 CancellableInputHandler.kt\norg/anti_ad/mc/ipnext/input/CancellableInputHandler\n*L\n34#1:55,10\n*E\n"
)
public final class CancellableInputHandler implements IInputHandler {
   @NotNull
   public static final CancellableInputHandler INSTANCE = new CancellableInputHandler();

   private CancellableInputHandler() {
   }

   public final boolean onInput(int var1, int var2) {
      try {
         if (LockSlotsHandler.INSTANCE.onCancellableInput()) {
            return true;
         } else if (ProfileSwitchHandler.INSTANCE.onInput(var1, var2)) {
            return true;
         } else {
            return VillagerTradeManager.INSTANCE.onInput(var1, var2) ? true : AutoRefillHandler.INSTANCE.onInput(var1, var2);
         }
      } catch (Throwable var3) {
         var3.printStackTrace();
         return false;
      }
   }
}
