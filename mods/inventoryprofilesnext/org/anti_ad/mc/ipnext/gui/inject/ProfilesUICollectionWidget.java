package org.anti_ad.mc.ipnext.gui.inject;

import java.util.Set;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.render.ColorKt;
import org.anti_ad.mc.common.vanilla.render.glue.RectKt;
import org.anti_ad.mc.ipn.api.IPNButton;
import org.anti_ad.mc.ipnext.config.Debugs;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.anti_ad.mc.ipnext.event.ProfileSwitchHandler;
import org.anti_ad.mc.ipnext.gui.inject.base.InsertableWidget;
import org.anti_ad.mc.ipnext.gui.widgets.Hintable;
import org.anti_ad.mc.ipnext.gui.widgets.Hintable$DefaultImpls;
import org.anti_ad.mc.ipnext.gui.widgets.Hintable$HintManagementRenderer;
import org.anti_ad.mc.ipnext.integration.ButtonPositionHint;
import org.anti_ad.mc.ipnext.integration.HintClassData;
import org.anti_ad.mc.ipnext.inventory.ContainerType;
import org.anti_ad.mc.ipnext.inventory.ContainerTypes;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nProfilesUICollectionWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProfilesUICollectionWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/ProfilesUICollectionWidget\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,229:1\n119#2,4:230\n*S KotlinDebug\n*F\n+ 1 ProfilesUICollectionWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/ProfilesUICollectionWidget\n*L\n88#1:230,4\n*E\n"
)
public final class ProfilesUICollectionWidget extends InsertableWidget implements Hintable {
   @NotNull
   private final AbstractContainerScreen screen;
   @NotNull
   private ButtonPositionHint hints;
   @NotNull
   private Hintable$HintManagementRenderer hintManagementRenderer;
   private boolean underManagement;
   @NotNull
   private final AbstractContainerMenu container;
   @NotNull
   private final Set types;
   @NotNull
   private Function0 visibleSource;
   private boolean initialized;

   public ProfilesUICollectionWidget(@NotNull AbstractContainerScreen var1, @NotNull HintClassData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.screen = var1;
      this.hints = var2.hintFor(IPNButton.PROFILE_SELECTOR);
      this.hintManagementRenderer = new Hintable$HintManagementRenderer(this);
      this.container = Vanilla.INSTANCE.container();
      this.types = ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, this.getContainer(), null, 2, null);
      this.setVisible(this.types.contains(ContainerType.PLAYER));
      this.visibleSource = ProfilesUICollectionWidget::_init_$lambda$0;
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
   public final AbstractContainerMenu getContainer() {
      return this.container;
   }

   @NotNull
   public final Function0 getVisibleSource() {
      return this.visibleSource;
   }

   public final void setVisibleSource(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.visibleSource = var1;
   }

   public final boolean getVisible() {
      return (Boolean)this.visibleSource.invoke() && super.getVisible();
   }

   public final void setVisible(boolean var1) {
      super.setVisible(var1);
   }

   @Override
   public final void postBackgroundRender(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   @Override
   public final void postForegroundRender(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      AbstractContainerScreen var6 = this.getScreen();
      Rectangle var5 = new Rectangle(var6.getGuiLeft(), var6.getGuiTop(), var6.getXSize(), var6.getYSize());
      int var8 = var5.getBottom() + 3 + this.getHints().getBottom();
      int var7 = var5.getX() + this.getHints().getHorizontalOffset();
      this.setAbsoluteBounds(Rectangle.copy$default(var5, var7, var8, 0, 20, 4, null));
      this.init();
      super.render(var1, var2, var3, var4);
      if (Debugs.INSTANCE.getDEBUG_RENDER().getBooleanValue()) {
         RectKt.rDrawOutline(var1, this.getAbsoluteBounds().inflated(1), ColorKt.getOpaque(16776960));
      }

      this.getHintManagementRenderer().renderUnderManagement(var1);
   }

   @Override
   public final void moveUp(int var1) {
      this.getHints().setBottom(this.getHints().getBottom() - var1);
      this.getHints().setDirty(true);
   }

   @Override
   public final void moveDown(int var1) {
      this.getHints().setBottom(this.getHints().getBottom() + var1);
      this.getHints().setDirty(true);
   }

   private final String getCurrentProfileName() {
      String var10000 = ProfileSwitchHandler.INSTANCE.getActiveProfileName();
      if (var10000 == null) {
         var10000 = "§cNONE§r";
      }

      return var10000;
   }

   public final void init() {
      if (!this.initialized) {
         this.initialized = true;
         new ProfilesUICollectionWidget$InitWidgets(this);
         this.getHintableList().add(this);
      }
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

   private static final boolean _init_$lambda$0() {
      return GuiSettings.INSTANCE.getENABLE_PROFILES_UI().getValue();
   }
}
