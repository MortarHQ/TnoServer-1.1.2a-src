package org.anti_ad.mc.ipnext.gui.inject.base;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.vanilla.render.glue.RectKt;
import org.anti_ad.mc.common.vanilla.render.glue.Sprite;
import org.anti_ad.mc.common.vanilla.render.glue.TextureKt;
import org.jetbrains.annotations.NotNull;

public class VillagerBookmarkButtonWidget extends SortButtonWidget {
   @NotNull
   private Function1 visibleOverride;
   @NotNull
   private final Function0 colorSource;
   private int ctx;
   private int cty;
   @NotNull
   private Function0 checked;

   @NotNull
   public final Function1 getVisibleOverride() {
      return this.visibleOverride;
   }

   public final void setVisibleOverride(@NotNull Function1 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.visibleOverride = var1;
   }

   @NotNull
   public final Function0 getColorSource() {
      return this.colorSource;
   }

   public final int getCtx() {
      return this.ctx;
   }

   public final void setCtx(int var1) {
      this.ctx = var1;
   }

   public final int getCty() {
      return this.cty;
   }

   public final void setCty(int var1) {
      this.cty = var1;
   }

   @NotNull
   public Function0 getChecked() {
      return this.checked;
   }

   public void setChecked(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.checked = var1;
   }

   @NotNull
   @Override
   public Point getHoveringTexturePt() {
      return new Point(this.getTx(), this.getTy());
   }

   @NotNull
   public Point getCheckedPt() {
      return new Point(this.ctx, this.cty);
   }

   public VillagerBookmarkButtonWidget(@NotNull Function0 var1, @NotNull Function1 var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super(var2);
      this.visibleOverride = VillagerBookmarkButtonWidget::visibleOverride$lambda$0;
      this.checked = VillagerBookmarkButtonWidget::checked$lambda$1;
      this.colorSource = var1;
   }

   public VillagerBookmarkButtonWidget(@NotNull Function0 var1, @NotNull Function0 var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super(var2);
      this.visibleOverride = VillagerBookmarkButtonWidget::visibleOverride$lambda$0;
      this.checked = VillagerBookmarkButtonWidget::checked$lambda$1;
      this.colorSource = var1;
   }

   public VillagerBookmarkButtonWidget(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.visibleOverride = VillagerBookmarkButtonWidget::visibleOverride$lambda$0;
      this.checked = VillagerBookmarkButtonWidget::checked$lambda$1;
      this.colorSource = var1;
   }

   @Override
   public void renderButton(@NotNull NativeContext var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      super.renderButton(var1, var2);
      RectKt.rFillRect(var1, this.getAbsoluteBounds(), ((Number)this.colorSource.invoke()).intValue());
      if ((Boolean)this.getChecked().invoke()) {
         TextureKt.rDrawSprite(var1, new Sprite(this.getTexture(), new Rectangle(this.getCheckedPt(), this.getSize())), this.getScreenX(), this.getScreenY());
      }
   }

   private static final boolean visibleOverride$lambda$0(boolean var0) {
      return var0;
   }

   private static final boolean checked$lambda$1() {
      return false;
   }
}
