package org.anti_ad.mc.ipnext.gui.widgets;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.gui.widgets.Widget;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.vanilla.render.ColorKt;
import org.anti_ad.mc.common.vanilla.render.glue.RectKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nHintable.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Hintable.kt\norg/anti_ad/mc/ipnext/gui/widgets/Hintable$HintManagementRenderer\n+ 2 kt_number.kt\norg/anti_ad/mc/common/extensions/Kt_numberKt\n*L\n1#1,134:1\n41#2:135\n*S KotlinDebug\n*F\n+ 1 Hintable.kt\norg/anti_ad/mc/ipnext/gui/widgets/Hintable$HintManagementRenderer\n*L\n105#1:135\n*E\n"
)
public final class Hintable$HintManagementRenderer {
   @NotNull
   private final Hintable target;
   private int tick;
   private int alphaChannel;
   private int step;
   @NotNull
   private final Widget widget;
   private int pingStep;

   public Hintable$HintManagementRenderer(@NotNull Hintable var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.target = var1;
      this.step = 5;
      Hintable var10001 = this.target;
      Intrinsics.checkNotNull(this.target);
      this.widget = (Widget)var10001;
      this.pingStep = 50;
   }

   @NotNull
   public final Hintable getTarget() {
      return this.target;
   }

   public final int getTick() {
      return this.tick;
   }

   public final void setTick(int var1) {
      this.tick = var1;
   }

   public final int getAlphaChannel() {
      return this.alphaChannel;
   }

   public final void setAlphaChannel(int var1) {
      this.alphaChannel = var1;
   }

   public final int getStep() {
      return this.step;
   }

   public final void setStep(int var1) {
      this.step = var1;
   }

   @NotNull
   public final Widget getWidget() {
      return this.widget;
   }

   public final int getPingStep() {
      return this.pingStep;
   }

   public final void setPingStep(int var1) {
      this.pingStep = var1;
   }

   public final void renderUnderManagement(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (!this.target.getUnderManagement()) {
         this.pingStep = 50;
      } else {
         int var2 = this.tick++;
         if (this.tick == 3) {
            this.tick = 0;
            this.alphaChannel = this.alphaChannel + this.step;
            if (this.alphaChannel >= 150 || this.alphaChannel <= 0) {
               this.step = ~this.step;
            }

            if (this.pingStep > 0) {
               RectKt.rDrawOutline(var1, this.widget.getAbsoluteBounds().inflated(this.pingStep), ColorKt.getOpaque(65280));
               RectKt.rDrawOutline(var1, this.widget.getAbsoluteBounds().inflated(this.pingStep + 2), ColorKt.getOpaque(65280));
               RectKt.rDrawOutline(var1, this.widget.getAbsoluteBounds().inflated(this.pingStep + 4), ColorKt.getOpaque(65280));
               this.pingStep -= 5;
            }
         }

         Widget var3 = this.widget;
         RectKt.rFillRect(
            var1,
            new Rectangle(
               var3.getAbsoluteBounds().getX(), var3.getAbsoluteBounds().getY(), var3.getAbsoluteBounds().getWidth(), var3.getAbsoluteBounds().getHeight()
            ),
            ColorKt.alpha(ColorKt.asGreen(235), this.alphaChannel)
         );
      }
   }
}
