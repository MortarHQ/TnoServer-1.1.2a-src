package org.anti_ad.mc.ipnext.gui;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.anti_ad.mc.common.gui.widgets.Page;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageScreenInfo\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,223:1\n111#2,7:224\n124#2:231\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageScreenInfo\n*L\n143#1:224,7\n146#1:231\n*E\n"
)
public final class DebugScreen$PageScreenInfo extends Page {
   public DebugScreen$PageScreenInfo(DebugScreen var1) {
      super("ScreenInfo");
      this.this$0 = var1;
   }

   @NotNull
   public final List getContent() {
      String[] var1;
      (var1 = new String[4])[0] = this.getScreen();
      var1[1] = this.getFocusedSlot();
      var1[2] = this.getScreenContainer();
      var1[3] = this.getContainer();
      return StringsKt.lines(CollectionsKt.joinToString$default(CollectionsKt.listOf(var1), "\n", null, null, 0, null, null, 62, null));
   }

   @NotNull
   public final String getScreen() {
      Screen var10000 = this.this$0.getParent();
      if (var10000 != null) {
         Class var1 = var10000.getClass();
         if (var1 != null) {
            return "screen: " + var1.getName();
         }
      }

      return "screen: " + null;
   }

   @NotNull
   public final String getFocusedSlot() {
      Screen var10000 = this.this$0.getParent();
      if (var10000 != null) {
         label24: {
            Screen var1 = var10000;
            AbstractContainerScreen var2 = var10000 instanceof AbstractContainerScreen ? (AbstractContainerScreen)var1 : null;
            if ((var10000 instanceof AbstractContainerScreen ? (AbstractContainerScreen)var1 : null) != null) {
               Slot var3 = var2.getSlotUnderMouse();
               if (var3 != null) {
                  var4 = InventoryKt.vPlayerSlotOf(var3, var1);
                  break label24;
               }
            }

            var4 = null;
         }

         if (var4 != null) {
            Class var5 = var4.getClass();
            if (var5 != null) {
               return "focusedSlot: " + var5.getName();
            }
         }
      }

      return "focusedSlot: " + null;
   }

   @NotNull
   public final String getScreenContainer() {
      Screen var1;
      AbstractContainerScreen var10000 = (var1 = this.this$0.getParent()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var1 : null;
      if (var10000 != null) {
         AbstractContainerScreen var2 = var10000;
         AbstractContainerMenu var10001 = var2.getMenu();
         Intrinsics.checkNotNullExpressionValue(var10001, "");
         String var3 = this.containerStringOf(var10001, "screenContainer");
         if (var3 != null) {
            return var3;
         }
      }

      return "screenContainer: null";
   }

   @NotNull
   public final String getContainer() {
      return this.containerStringOf(Vanilla.INSTANCE.container(), "container");
   }

   @NotNull
   public final String containerStringOf(@NotNull AbstractContainerMenu var1, @NotNull String var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return var2 + ": " + var1.getClass().getCanonicalName();
   }
}
