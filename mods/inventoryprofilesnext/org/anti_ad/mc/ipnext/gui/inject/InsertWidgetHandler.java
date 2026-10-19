package org.anti_ad.mc.ipnext.gui.inject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.Screen;
import org.anti_ad.mc.common.ScreenEventListener;
import org.anti_ad.mc.common.gui.widgets.Widget;
import org.anti_ad.mc.common.input.GlobalScreenEventListener;
import org.anti_ad.mc.common.math2d.Size;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nInsertWidgetHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InsertWidgetHandler.kt\norg/anti_ad/mc/ipnext/gui/inject/InsertWidgetHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,186:1\n1869#2,2:187\n1869#2,2:189\n1869#2,2:191\n1869#2,2:193\n1869#2,2:195\n1869#2,2:197\n1869#2,2:199\n1869#2,2:201\n*S KotlinDebug\n*F\n+ 1 InsertWidgetHandler.kt\norg/anti_ad/mc/ipnext/gui/inject/InsertWidgetHandler\n*L\n60#1:187,2\n71#1:189,2\n84#1:191,2\n96#1:193,2\n108#1:195,2\n122#1:197,2\n135#1:199,2\n146#1:201,2\n*E\n"
)
public final class InsertWidgetHandler implements ScreenEventListener {
   @NotNull
   public static final InsertWidgetHandler INSTANCE = new InsertWidgetHandler();
   @NotNull
   private static List currentWidgets = new ArrayList();
   @Nullable
   private static Screen currentScreen;
   @NotNull
   private static final Object initSync = new Object();

   private InsertWidgetHandler() {
   }

   @NotNull
   public final List getCurrentWidgets() {
      return currentWidgets;
   }

   public final void setCurrentWidgets(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      currentWidgets = var1;
   }

   @Nullable
   public final Screen getCurrentScreen() {
      return currentScreen;
   }

   public final void setCurrentScreen(@Nullable Screen var1) {
      currentScreen = var1;
   }

   public final void insertWidget(@Nullable List var1) {
      synchronized (initSync) {
         currentWidgets.clear();
         currentScreen = null;
         if (var1 != null) {
            currentScreen = Vanilla.INSTANCE.screen();
            currentWidgets.addAll(var1);
         }
      }
   }

   public final void resize(int var1, int var2) {
      if (this.checkIfValidScreen()) {
         Iterator var3 = currentWidgets.iterator();

         while (var3.hasNext()) {
            ((Widget)var3.next()).setSize(new Size(var1, var2));
         }
      }
   }

   public final boolean mouseClicked(double var1, double var3, int var5) {
      boolean var6 = false;
      if (this.checkIfValidScreen()) {
         for (Widget var8 : currentWidgets) {
            var6 = var6 || var8.mouseClicked((int)var1, (int)var3, var5);
         }
      }

      return var6;
   }

   public final boolean mouseRelease(double var1, double var3, int var5) {
      boolean var6 = false;
      if (this.checkIfValidScreen()) {
         for (Widget var8 : currentWidgets) {
            var6 = var6 || var8.mouseReleased((int)var1, (int)var3, var5);
         }
      }

      return var6;
   }

   public final boolean keyPressed(int var1, int var2, int var3) {
      boolean var4 = false;
      if (this.checkIfValidScreen()) {
         for (Widget var6 : currentWidgets) {
            var4 = var4 || var6.keyPressed(var1, var2, var3);
         }
      }

      return var4;
   }

   public final boolean keyReleased(int var1, int var2, int var3) {
      boolean var4 = false;
      if (this.checkIfValidScreen()) {
         for (Widget var6 : currentWidgets) {
            var4 = var4 || var6.keyReleased(var1, var2, var3);
         }
      }

      return var4;
   }

   public final boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      boolean var10 = false;
      if (this.checkIfValidScreen()) {
         for (Widget var12 : currentWidgets) {
            var10 = var10 || var12.mouseDragged(var1, var3, var5, var6, var8);
         }
      }

      return var10;
   }

   public final boolean mouseScrolled(double var1, double var3, double var5, double var7) {
      boolean var9 = false;
      if (this.checkIfValidScreen()) {
         for (Widget var11 : currentWidgets) {
            var9 = var9 || var11.mouseScrolled((int)var1, (int)var3, var5, var7);
         }
      }

      return var9;
   }

   public final boolean charTyped(char var1, int var2) {
      boolean var3 = false;
      if (this.checkIfValidScreen()) {
         for (Widget var5 : currentWidgets) {
            var3 = var3 || var5.charTyped(var1, var2);
         }
      }

      return var3;
   }

   public final void preScreenRender() {
      synchronized (initSync) {
         if (Vanilla.INSTANCE.screen() == null) {
            currentWidgets.clear();
            currentScreen = null;
         }

         if (currentScreen != null && !Intrinsics.areEqual(Vanilla.INSTANCE.screen(), currentScreen)) {
            currentWidgets.clear();
            currentScreen = null;
         }
      }
   }

   public final void onClientInit() {
      GlobalScreenEventListener.INSTANCE.registerPre(this);
   }

   private final boolean checkIfValidScreen() {
      synchronized (initSync) {
         if (Vanilla.INSTANCE.screen() == null) {
            currentWidgets.clear();
            currentScreen = null;
            return false;
         }
      }

      return true;
   }
}
