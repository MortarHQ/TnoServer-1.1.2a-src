package org.anti_ad.mc.ipnext.gui.inject;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.gui.layout.Overflow;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.render.ColorKt;
import org.anti_ad.mc.common.vanilla.render.glue.RectKt;
import org.anti_ad.mc.ipnext.config.Debugs;
import org.anti_ad.mc.ipnext.gui.inject.base.InsertableWidget;
import org.anti_ad.mc.ipnext.integration.HintClassData;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nSortingButtonCollectionWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SortingButtonCollectionWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/SortingButtonCollectionWidget\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,545:1\n119#2,4:546\n1#3:550\n*S KotlinDebug\n*F\n+ 1 SortingButtonCollectionWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/SortingButtonCollectionWidget\n*L\n86#1:546,4\n*E\n"
)
public final class SortingButtonCollectionWidget extends InsertableWidget {
   @NotNull
   private final AbstractContainerScreen screen;
   @NotNull
   private final HintClassData hints;
   @NotNull
   private final AbstractContainerMenu container;
   private boolean initialized;
   @NotNull
   private Function0 rehint;

   public SortingButtonCollectionWidget(@NotNull AbstractContainerScreen var1, @NotNull HintClassData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.screen = var1;
      this.hints = var2;
      this.container = Vanilla.INSTANCE.container();
      this.rehint = SortingButtonCollectionWidget::rehint$lambda$0;
      this.setOverflow(Overflow.VISIBLE);
      this.getHintableList().clear();
   }

   @NotNull
   @Override
   public final AbstractContainerScreen getScreen() {
      return this.screen;
   }

   @NotNull
   public final HintClassData getHints() {
      return this.hints;
   }

   @NotNull
   @Override
   public final AbstractContainerMenu getContainer() {
      return this.container;
   }

   @Override
   public final void render(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   @Override
   public final void postBackgroundRender(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   @Override
   public final void postForegroundRender(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.rehint.invoke();
      this.setOverflow(Overflow.VISIBLE);
      AbstractContainerScreen var5 = this.getScreen();
      this.setAbsoluteBounds(new Rectangle(var5.getGuiLeft(), var5.getGuiTop(), var5.getXSize(), var5.getYSize()));
      this.init();
      super.render(var1, var2, var3, var4);
      if (Debugs.INSTANCE.getDEBUG_RENDER().getBooleanValue()) {
         RectKt.rDrawOutline(var1, this.getAbsoluteBounds().inflated(1), ColorKt.getOpaque(16776960));
      }
   }

   public final boolean getInitialized() {
      return this.initialized;
   }

   public final void setInitialized(boolean var1) {
      this.initialized = var1;
   }

   @NotNull
   public final Function0 getRehint() {
      return this.rehint;
   }

   public final void setRehint(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.rehint = var1;
   }

   public final void init() {
      if (!this.initialized) {
         this.initialized = true;
         SortingButtonCollectionWidget$InitWidgets var1 = new SortingButtonCollectionWidget$InitWidgets(this);
         this.rehint = new SortingButtonCollectionWidget$init$1$1(var1);
      }
   }

   private static final Unit rehint$lambda$0() {
      return Unit.INSTANCE;
   }
}
