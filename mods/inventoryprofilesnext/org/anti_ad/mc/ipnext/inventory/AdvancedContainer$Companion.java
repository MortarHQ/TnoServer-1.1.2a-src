package org.anti_ad.mc.ipnext.inventory;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen.ItemPickerMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.ipnext.config.Debugs;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nAdvancedContainer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AdvancedContainer.kt\norg/anti_ad/mc/ipnext/inventory/AdvancedContainer$Companion\n*L\n1#1,245:1\n154#1,6:246\n*S KotlinDebug\n*F\n+ 1 AdvancedContainer.kt\norg/anti_ad/mc/ipnext/inventory/AdvancedContainer$Companion\n*L\n166#1:246,6\n*E\n"
)
public final class AdvancedContainer$Companion {
   private AdvancedContainer$Companion() {
   }

   @NotNull
   public final AdvancedContainer create() {
      AbstractContainerMenu var1 = Vanilla.INSTANCE.container();
      return new AdvancedContainer(var1 instanceof ItemPickerMenu ? (AbstractContainerMenu)Vanilla.INSTANCE.playerContainer() : var1, null, 2, null);
   }

   public final void invoke(boolean var1, @NotNull Function1 var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      if (VanillaUtil.INSTANCE.inGame()) {
         AdvancedContainer var3 = this.create();
         var2.invoke(var3);
         var3.arrange(var1);
      }
   }

   public final void tracker(boolean var1, boolean var2, @NotNull Function1 var3) {
      Intrinsics.checkNotNullParameter(var3, "");
      if (VanillaUtil.INSTANCE.inGame()) {
         AdvancedContainer$Companion var4 = AdvancedContainer.Companion;
         if (VanillaUtil.INSTANCE.inGame()) {
            AdvancedContainer var6;
            AdvancedContainer var5 = var6 = var4.create();
            if (var2 && !Debugs.INSTANCE.getFORCE_NO_CLEAN_CURSOR().getBooleanValue()) {
               var5.cleanCursor();
            }

            var5.tracker(var3);
            var6.arrange(var1);
         }
      }
   }
}
