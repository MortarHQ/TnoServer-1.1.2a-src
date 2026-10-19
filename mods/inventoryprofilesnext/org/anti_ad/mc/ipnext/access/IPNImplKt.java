package org.anti_ad.mc.ipnext.access;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.event.autorefill.AutoRefillHandler;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.inventory.ContainerClicker;
import org.anti_ad.mc.ipnext.inventory.GeneralInventoryActions;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;

@SourceDebugExtension(
   "SMAP\nIPNImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IPNImpl.kt\norg/anti_ad/mc/ipnext/access/IPNImplKt\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,174:1\n216#2,2:175\n216#2,2:177\n*S KotlinDebug\n*F\n+ 1 IPNImpl.kt\norg/anti_ad/mc/ipnext/access/IPNImplKt\n*L\n143#1:175,2\n151#1:177,2\n*E\n"
)
public final class IPNImplKt {
   private static final int getInterval() {
      return ModSettings.INSTANCE.getADD_INTERVAL_BETWEEN_CLICKS().getBooleanValue()
         ? ModSettings.INSTANCE.getINTERVAL_BETWEEN_CLICKS_MS().getIntegerValue()
         : 0;
   }

   private static final Map translateMap(Map var0) {
      Map var1 = new LinkedHashMap();
      Iterator var4 = var0.entrySet().iterator();

      while (var4.hasNext()) {
         Entry var2;
         int var3 = ((Number)(var2 = (Entry)var4.next()).getKey()).intValue();
         Integer var5 = ((Number)var2.getValue()).intValue();
         var1.put(var3 < 9 ? var3 + 36 : var3, var5);
      }

      return var1;
   }

   private static final Map translateMapValueToSlot(Map var0) {
      Map var1 = new LinkedHashMap();
      Iterator var4 = var0.entrySet().iterator();

      while (var4.hasNext()) {
         Entry var2;
         int var3 = ((Number)(var2 = (Entry)var4.next()).getKey()).intValue();
         int var5 = ((Number)var2.getValue()).intValue();
         var1.put(var3 < 9 ? var3 + 36 : var3, Vanilla.INSTANCE.container().getSlot(var5));
      }

      return var1;
   }

   private static final void swapSlots(int var0, int var1) {
      int var2 = var0 <= 8 ? var0 + 36 : var0;
      AutoRefillHandler.INSTANCE.getProfilesSwappedItems().add(var2);
      GeneralInventoryActions.INSTANCE.cleanCursor();
      if (var0 >= 0 ? var0 < 9 : false) {
         ContainerClicker.INSTANCE.swap(var1, var0);
      } else {
         ContainerClicker.INSTANCE.leftClick(var1);
         ContainerClicker.INSTANCE.leftClick(var0);
         if (!ItemStackExtensionsKt.isEmpty(InventoryKt.vCursorStack())) {
            ContainerClicker.INSTANCE.leftClick(var1);
         }
      }
   }
}
