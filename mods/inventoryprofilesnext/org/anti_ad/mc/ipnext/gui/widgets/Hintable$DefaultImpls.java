package org.anti_ad.mc.ipnext.gui.widgets;

import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.gui.widgets.Widget;
import org.jetbrains.annotations.NotNull;

public final class Hintable$DefaultImpls {
   public static void renderUnderManagement(@NotNull Hintable var0, @NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      var0.getHintManagementRenderer().renderUnderManagement(var1);
   }

   public static void moveLeft(@NotNull Hintable var0, int var1) {
      if (var0 instanceof Widget) {
         var0.getHints().setDirty(true);
         if (((Widget)var0).getAnchor().getRight()) {
            var0.getHints().setHorizontalOffset(var0.getHints().getHorizontalOffset() + var1);
            return;
         }

         var0.getHints().setHorizontalOffset(var0.getHints().getHorizontalOffset() - var1);
      }
   }

   public static void moveRight(@NotNull Hintable var0, int var1) {
      if (var0 instanceof Widget) {
         var0.getHints().setDirty(true);
         if (((Widget)var0).getAnchor().getRight()) {
            var0.getHints().setHorizontalOffset(var0.getHints().getHorizontalOffset() - var1);
            return;
         }

         var0.getHints().setHorizontalOffset(var0.getHints().getHorizontalOffset() + var1);
      }
   }

   public static void moveUp(@NotNull Hintable var0, int var1) {
      if (var0 instanceof Widget) {
         var0.getHints().setDirty(true);
         if (((Widget)var0).getAnchor().getBottom()) {
            var0.getHints().setBottom(var0.getHints().getBottom() + var1);
            return;
         }

         var0.getHints().setTop(var0.getHints().getTop() - var1);
      }
   }

   public static void moveDown(@NotNull Hintable var0, int var1) {
      if (var0 instanceof Widget) {
         var0.getHints().setDirty(true);
         if (((Widget)var0).getAnchor().getBottom()) {
            var0.getHints().setBottom(var0.getHints().getBottom() - var1);
            return;
         }

         var0.getHints().setTop(var0.getHints().getTop() + var1);
      }
   }
}
