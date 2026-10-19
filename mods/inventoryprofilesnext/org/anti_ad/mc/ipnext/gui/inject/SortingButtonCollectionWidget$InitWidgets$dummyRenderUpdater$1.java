package org.anti_ad.mc.ipnext.gui.inject;

import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.gui.widgets.Widget;
import org.anti_ad.mc.ipnext.gui.inject.base.SortButtonWidget;

@SourceDebugExtension(
   "SMAP\nSortingButtonCollectionWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SortingButtonCollectionWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/SortingButtonCollectionWidget$InitWidgets$dummyRenderUpdater$1\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,545:1\n133#2:546\n1878#3,3:547\n1563#3:550\n1634#3,3:551\n*S KotlinDebug\n*F\n+ 1 SortingButtonCollectionWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/SortingButtonCollectionWidget$InitWidgets$dummyRenderUpdater$1\n*L\n135#1:546\n136#1:547,3\n129#1:550\n129#1:551,3\n*E\n"
)
public final class SortingButtonCollectionWidget$InitWidgets$dummyRenderUpdater$1 extends Widget {
   private final Lazy buttons$delegate;
   private final Lazy originalVisibles$delegate;

   SortingButtonCollectionWidget$InitWidgets$dummyRenderUpdater$1(SortingButtonCollectionWidget var1, SortingButtonCollectionWidget$InitWidgets var2) {
      this.this$0 = var1;
      var1.addChild(this);
      this.buttons$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, SortingButtonCollectionWidget$InitWidgets$dummyRenderUpdater$1::buttons_delegate$lambda$0);
      this.originalVisibles$delegate = LazyKt.lazy(
         LazyThreadSafetyMode.NONE, SortingButtonCollectionWidget$InitWidgets$dummyRenderUpdater$1::originalVisibles_delegate$lambda$2
      );
   }

   public final List getButtons() {
      return (List)this.buttons$delegate.getValue();
   }

   public final List getOriginalVisibles() {
      return (List)this.originalVisibles$delegate.getValue();
   }

   public final void render(NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (this.this$0.getScreen() instanceof CreativeModeInventoryScreen) {
         boolean var6 = ((CreativeModeInventoryScreen)this.this$0.getScreen()).isInventoryOpen();
         Iterable var7 = this.getButtons();
         var3 = 0;

         for (Object var10 : var7) {
            int var5;
            if ((var5 = var3++) < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            SortButtonWidget var11 = (SortButtonWidget)var10;
            var5 = var5;
            var11.setVisible((Boolean)this.getOriginalVisibles().get(var5) && var6);
         }
      }
   }

   private static final List buttons_delegate$lambda$0(SortingButtonCollectionWidget$InitWidgets var0) {
      SortButtonWidget[] var1;
      (var1 = new SortButtonWidget[3])[0] = SortingButtonCollectionWidget$InitWidgets.access$getSortButton$p(var0);
      var1[1] = SortingButtonCollectionWidget$InitWidgets.access$getSortInColumnButton$p(var0);
      var1[2] = SortingButtonCollectionWidget$InitWidgets.access$getSortInRowButton$p(var0);
      return CollectionsKt.listOf(var1);
   }

   private static final List originalVisibles_delegate$lambda$2(SortingButtonCollectionWidget$InitWidgets$dummyRenderUpdater$1 var0) {
      Iterable var4;
      Iterable var1 = var4 = var0.getButtons();
      var var5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10));

      for (SortButtonWidget var2 : var1) {
         var2 = var2;
         ArrayList var3 = var5;
         var3.add(var2.getVisible());
      }

      return var5;
   }
}
