package org.anti_ad.mc.ipnext.gui.inject.base;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.gui.layout.WidgetExtensionsKt;
import org.anti_ad.mc.common.gui.screen.ConfigScreenBase;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.VanillaScreenUtil;
import org.anti_ad.mc.common.vanilla.glue.IScreenMarker;
import org.anti_ad.mc.common.vanilla.render.ColorKt;
import org.anti_ad.mc.common.vanilla.render.glue.RectKt;
import org.anti_ad.mc.ipn.api.IPNButton;
import org.anti_ad.mc.ipnext.config.ConfigScreenSettings;
import org.anti_ad.mc.ipnext.config.Debugs;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.anti_ad.mc.ipnext.gui.widgets.Hintable;
import org.anti_ad.mc.ipnext.gui.widgets.Hintable$DefaultImpls;
import org.anti_ad.mc.ipnext.gui.widgets.Hintable$HintManagementRenderer;
import org.anti_ad.mc.ipnext.integration.ButtonPositionHint;
import org.anti_ad.mc.ipnext.integration.HintClassData;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nSettingsWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SettingsWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/base/SettingsWidget\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 common.kt\norg/anti_ad/mc/common/extensions/CommonKt\n*L\n1#1,153:1\n1#2:154\n70#3,14:155\n*S KotlinDebug\n*F\n+ 1 SettingsWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/base/SettingsWidget\n*L\n144#1:155,14\n*E\n"
)
public final class SettingsWidget extends InsertableWidget implements Hintable {
   @NotNull
   private final AbstractContainerScreen screen;
   @NotNull
   private ButtonPositionHint hints;
   private boolean underManagement;
   @NotNull
   private Hintable$HintManagementRenderer hintManagementRenderer;
   @NotNull
   private final AbstractContainerMenu container;
   @NotNull
   private Function0 rehint;
   private boolean initialized;

   public SettingsWidget(@NotNull AbstractContainerScreen var1, @NotNull HintClassData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.screen = var1;
      this.hints = var2.hintFor(IPNButton.SETTINGS);
      this.hintManagementRenderer = new Hintable$HintManagementRenderer(this);
      this.container = Vanilla.INSTANCE.container();
      this.rehint = SettingsWidget::rehint$lambda$0;
   }

   @NotNull
   @Override
   public final AbstractContainerScreen getScreen() {
      return this.screen;
   }

   @NotNull
   @Override
   public final ButtonPositionHint getHints() {
      return this.hints;
   }

   @Override
   public final void setHints(@NotNull ButtonPositionHint var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.hints = var1;
   }

   @Override
   public final boolean getUnderManagement() {
      return this.underManagement;
   }

   @Override
   public final void setUnderManagement(boolean var1) {
      this.underManagement = var1;
   }

   @NotNull
   @Override
   public final Hintable$HintManagementRenderer getHintManagementRenderer() {
      return this.hintManagementRenderer;
   }

   @Override
   public final void setHintManagementRenderer(@NotNull Hintable$HintManagementRenderer var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.hintManagementRenderer = var1;
   }

   @NotNull
   @Override
   public final AbstractContainerMenu getContainer() {
      return this.container;
   }

   @Override
   public final void postBackgroundRender(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   @Override
   public final void postForegroundRender(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      WidgetExtensionsKt.fillParent(this);
      this.setAbsoluteBounds(new Rectangle(0, 0, this.getContainerWidth(), this.getContainerHeight()));
      this.init();
      this.rehint.invoke();
      this.setVisible(GuiSettings.INSTANCE.getENABLE_INVENTORY_SETTINGS_BUTTON().getBooleanValue());
      super.render(var1, var2, var3, var4);
      this.getHintManagementRenderer().renderUnderManagement(var1);
      if (Debugs.INSTANCE.getDEBUG_RENDER().getBooleanValue()) {
         RectKt.rDrawOutline(var1, this.getAbsoluteBounds(), ColorKt.getOpaque(16776960));
      }
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
         SettingsWidget$InitWidgets var1 = new SettingsWidget$InitWidgets(this);
         this.rehint = new SettingsWidget$init$1$1(var1);
         WidgetExtensionsKt.fillParent(this);
         this.setAbsoluteBounds(new Rectangle(0, 0, this.getContainerWidth(), this.getContainerHeight()));
      }
   }

   public final void onClick() {
      Vanilla.INSTANCE.queueForMainThread(SettingsWidget::onClick$lambda$4);
   }

   @Override
   public final void renderUnderManagement(@NotNull NativeContext var1) {
      Hintable$DefaultImpls.renderUnderManagement(this, var1);
   }

   @Override
   public final void moveLeft(int var1) {
      Hintable$DefaultImpls.moveLeft(this, var1);
   }

   @Override
   public final void moveRight(int var1) {
      Hintable$DefaultImpls.moveRight(this, var1);
   }

   @Override
   public final void moveUp(int var1) {
      Hintable$DefaultImpls.moveUp(this, var1);
   }

   @Override
   public final void moveDown(int var1) {
      Hintable$DefaultImpls.moveDown(this, var1);
   }

   private static final Unit rehint$lambda$0() {
      return Unit.INSTANCE;
   }

   private static final void onClick$lambda$4() {
      try {
         VanillaScreenUtil.INSTANCE.closeScreenGracefully();
      } catch (Throwable var1) {
         var1.printStackTrace();
      }

      ConfigScreenBase var0 = new ConfigScreenBase(ConfigScreenSettings.INSTANCE);
      VanillaScreenUtil.INSTANCE.openDistinctScreen((IScreenMarker)var0);
   }
}
