package org.anti_ad.mc.ipnext.gui.inject.base;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.gui.widgets.IPNButtonWidget;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.vanilla.render.glue.IdentifierHolder;
import org.anti_ad.mc.common.vanilla.render.glue.Sprite;
import org.anti_ad.mc.common.vanilla.render.glue.TextureKt;
import org.anti_ad.mc.ipnext.gui.widgets.Hintable;
import org.anti_ad.mc.ipnext.gui.widgets.Hintable$DefaultImpls;
import org.anti_ad.mc.ipnext.integration.ButtonPositionHint;
import org.anti_ad.mc.ipnext.specific.SmallHelpersKt;
import org.jetbrains.annotations.NotNull;

public abstract class TexturedButtonWidget extends IPNButtonWidget implements Hintable {
   @NotNull
   private Function0 tooltipTextSource = TexturedButtonWidget::tooltipTextSource$lambda$0;
   private int tx;
   private int ty;
   @NotNull
   private String tooltipText = "";
   private boolean underManagement;

   public TexturedButtonWidget(@NotNull Function1 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super(var1);
      this.tooltipTextSource = TexturedButtonWidget::tooltipTextSource$lambda$0;
      this.tooltipText = "";
   }

   public TexturedButtonWidget(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super(var1);
      this.tooltipTextSource = TexturedButtonWidget::tooltipTextSource$lambda$0;
      this.tooltipText = "";
   }

   public TexturedButtonWidget() {
   }

   @NotNull
   public abstract IdentifierHolder getTexture();

   @NotNull
   public abstract Point getTexturePt();

   @NotNull
   public abstract Point getHoveringTexturePt();

   @NotNull
   public Function0 getTooltipTextSource() {
      return this.tooltipTextSource;
   }

   public void setTooltipTextSource(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.tooltipTextSource = var1;
   }

   public int getTx() {
      return this.tx;
   }

   public void setTx(int var1) {
      this.tx = var1;
   }

   public int getTy() {
      return this.ty;
   }

   public void setTy(int var1) {
      this.ty = var1;
   }

   @NotNull
   public String getTooltipText() {
      return this.tooltipText;
   }

   public void setTooltipText(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.tooltipText = var1;
   }

   @NotNull
   @Override
   public abstract ButtonPositionHint getHints();

   @Override
   public abstract void setHints(@NotNull ButtonPositionHint var1);

   @Override
   public boolean getUnderManagement() {
      return this.underManagement;
   }

   @Override
   public void setUnderManagement(boolean var1) {
      this.underManagement = var1;
   }

   public void renderButton(@NotNull NativeContext var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Point var3 = var2 ? this.getHoveringTexturePt() : this.getTexturePt();
      TextureKt.rDrawSprite(
         SmallHelpersKt.getAsOverlayContext(var1), new Sprite(this.getTexture(), new Rectangle(var3, this.getSize())), this.getScreenX(), this.getScreenY()
      );
   }

   public boolean mouseClicked(int var1, int var2, int var3) {
      return super.mouseClicked(var1, var2, var3) && this.getVisible();
   }

   public boolean mouseScrolled(int var1, int var2, double var3, double var5) {
      if (this.getActive()) {
         if (var3 != 0.0) {
            if (var3 > 0.0) {
               this.onClick(-10002);
            } else {
               this.onClick(-10003);
            }
         }

         if (var5 != 0.0) {
            if (var5 > 0.0) {
               this.onClick(-10000);
            } else {
               this.onClick(-10001);
            }
         }
      }

      return super.mouseScrolled(var1, var2, var3, var5) && this.getVisible();
   }

   @Override
   public void renderUnderManagement(@NotNull NativeContext var1) {
      Hintable$DefaultImpls.renderUnderManagement(this, var1);
   }

   @Override
   public void moveLeft(int var1) {
      Hintable$DefaultImpls.moveLeft(this, var1);
   }

   @Override
   public void moveRight(int var1) {
      Hintable$DefaultImpls.moveRight(this, var1);
   }

   @Override
   public void moveUp(int var1) {
      Hintable$DefaultImpls.moveUp(this, var1);
   }

   @Override
   public void moveDown(int var1) {
      Hintable$DefaultImpls.moveDown(this, var1);
   }

   private static final String tooltipTextSource$lambda$0(TexturedButtonWidget var0) {
      return var0.getTooltipText();
   }
}
