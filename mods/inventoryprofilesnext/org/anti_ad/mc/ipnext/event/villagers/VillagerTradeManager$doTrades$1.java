package org.anti_ad.mc.ipnext.event.villagers;

import java.util.List;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;

// $VF: synthetic class
final class VillagerTradeManager$doTrades$1 extends FunctionReferenceImpl implements Function2 {
   VillagerTradeManager$doTrades$1(Object var1) {
      super(
         2, var1, VillagerTradeManager.class, "doTradesReal", "doTradesReal(Lnet/minecraft/client/gui/screens/inventory/MerchantScreen;Ljava/util/List;)V", 0
      );
   }

   public final void invoke(MerchantScreen var1, List var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      VillagerTradeManager.access$doTradesReal((VillagerTradeManager)this.receiver, var1, var2);
   }
}
