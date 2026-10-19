package org.anti_ad.mc.ipnext.gui.inject;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.gui.layout.Overflow;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.render.ColorKt;
import org.anti_ad.mc.common.vanilla.render.GLKt;
import org.anti_ad.mc.common.vanilla.render.glue.RectKt;
import org.anti_ad.mc.ipnext.config.Debugs;
import org.anti_ad.mc.ipnext.gui.inject.base.InsertableWidget;
import org.anti_ad.mc.ipnext.integration.HintClassData;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nVillagerOverlayWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VillagerOverlayWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/VillagerOverlayWidget\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,474:1\n1#2:475\n119#3,4:476\n*S KotlinDebug\n*F\n+ 1 VillagerOverlayWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/VillagerOverlayWidget\n*L\n75#1:476,4\n*E\n"
)
public final class VillagerOverlayWidget extends InsertableWidget {
   @NotNull
   private final MerchantScreen screen;
   @NotNull
   private final HintClassData hints;
   @NotNull
   private final AbstractContainerMenu container;
   @NotNull
   private Function0 rehint;
   private boolean initialized;

   public VillagerOverlayWidget(@NotNull MerchantScreen var1, @NotNull HintClassData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.screen = var1;
      this.hints = var2;
      this.container = Vanilla.INSTANCE.container();
      this.rehint = VillagerOverlayWidget::rehint$lambda$0;
   }

   @NotNull
   public final MerchantScreen getScreen() {
      return this.screen;
   }

   @NotNull
   @Override
   public final AbstractContainerMenu getContainer() {
      return this.container;
   }

   @NotNull
   public final Function0 getRehint() {
      return this.rehint;
   }

   public final void setRehint(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.rehint = var1;
   }

   public final boolean getInitialized() {
      return this.initialized;
   }

   public final void setInitialized(boolean var1) {
      this.initialized = var1;
   }

   public final void init() {
      if (!this.initialized) {
         this.initialized = true;
         VillagerOverlayWidget$InitWidgets var1 = new VillagerOverlayWidget$InitWidgets(this);
         this.rehint = new VillagerOverlayWidget$init$1$1(var1);
      }
   }

   @Override
   public final void postBackgroundRender(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.rehint.invoke();
      GLKt.rStandardGlState();
      GLKt.rClearDepth(var1);
      this.setOverflow(Overflow.VISIBLE);
      AbstractContainerScreen var5 = (AbstractContainerScreen)this.getScreen();
      this.setAbsoluteBounds(new Rectangle(var5.getGuiLeft(), var5.getGuiTop(), var5.getXSize(), var5.getYSize()));
      this.init();
      super.render(var1, var2, var3, var4);
      if (Debugs.INSTANCE.getDEBUG_RENDER().getBooleanValue()) {
         RectKt.rDrawOutline(var1, this.getAbsoluteBounds().inflated(1), ColorKt.getOpaque(16776960));
      }
   }

   @Override
   public final void postForegroundRender(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   private static final Unit rehint$lambda$0() {
      return Unit.INSTANCE;
   }
}
