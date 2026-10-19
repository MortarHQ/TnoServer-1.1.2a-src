package org.anti_ad.mc.ipnext.gui.inject.base;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.jetbrains.annotations.NotNull;

public final class CheckBoxWidget extends SortButtonWidget {
   private int highlightTx;
   private int highlightTy;
   @NotNull
   private String highlightTooltip = "";
   private boolean highlightEnabled = true;

   public CheckBoxWidget(@NotNull Function1 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super(var1);
      this.highlightTooltip = "";
      this.highlightEnabled = true;
   }

   public CheckBoxWidget(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super(var1);
      this.highlightTooltip = "";
      this.highlightEnabled = true;
   }

   public CheckBoxWidget() {
   }

   public final int getHighlightTx() {
      return this.highlightTx;
   }

   public final void setHighlightTx(int var1) {
      this.highlightTx = var1;
   }

   public final int getHighlightTy() {
      return this.highlightTy;
   }

   public final void setHighlightTy(int var1) {
      this.highlightTy = var1;
   }

   @NotNull
   public final String getHighlightTooltip() {
      return this.highlightTooltip;
   }

   public final void setHighlightTooltip(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.highlightTooltip = var1;
   }

   public final boolean getHighlightEnabled() {
      return this.highlightEnabled;
   }

   public final void setHighlightEnabled(boolean var1) {
      this.highlightEnabled = var1;
   }

   @Override
   public final void render(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (this.highlightEnabled) {
         int var5 = this.getTx();
         int var6 = this.getTy();
         String var7 = this.getTooltipText();
         if (ModSettings.INSTANCE.getINCLUDE_HOTBAR_MODIFIER().isPressing()) {
            this.setTx(this.highlightTx);
            this.setTy(this.highlightTy);
            this.setTooltipText(this.highlightTooltip);
         }

         super.render(var1, var2, var3, var4);
         this.setTx(var5);
         this.setTy(var6);
         this.setTooltipText(var7);
      } else {
         super.render(var1, var2, var3, var4);
      }
   }
}
