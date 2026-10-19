package org.anti_ad.mc.ipnext.gui.inject.base;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.gui.TooltipsManager;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.math2d.Size;
import org.anti_ad.mc.common.vanilla.render.glue.IdentifierHolder;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.anti_ad.mc.ipnext.gui.widgets.Hintable$HintManagementRenderer;
import org.anti_ad.mc.ipnext.integration.ButtonPositionHint;
import org.jetbrains.annotations.NotNull;

public class SortButtonWidget extends TexturedButtonWidget {
   @NotNull
   public static final SortButtonWidget$Companion Companion = new SortButtonWidget$Companion(null);
   @NotNull
   private Hintable$HintManagementRenderer hintManagementRenderer;
   @NotNull
   private String id;
   public ButtonPositionHint hints;
   @NotNull
   private static final IdentifierHolder TEXTURE = new IdentifierHolder("inventoryprofilesnext", "textures/gui/gui_buttons.png");

   public boolean getVisible() {
      return GuiSettings.INSTANCE.getINVENTORY_OVERLAY_BUTTONS_VISIBLE().getValue() && super.getVisible();
   }

   public void setVisible(boolean var1) {
      super.setVisible(var1);
   }

   @NotNull
   @Override
   public Hintable$HintManagementRenderer getHintManagementRenderer() {
      return this.hintManagementRenderer;
   }

   @Override
   public void setHintManagementRenderer(@NotNull Hintable$HintManagementRenderer var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.hintManagementRenderer = var1;
   }

   @NotNull
   public String getId() {
      return "inventoryprofiles.injected.ui.element." + this.id;
   }

   public void setId(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.id = var1;
   }

   public SortButtonWidget(@NotNull Function1 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super(var1);
      this.setSize(new Size(10, 10));
      this.hintManagementRenderer = new Hintable$HintManagementRenderer(this);
      this.id = "";
   }

   public SortButtonWidget(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super(var1);
      this.setSize(new Size(10, 10));
      this.hintManagementRenderer = new Hintable$HintManagementRenderer(this);
      this.id = "";
   }

   public SortButtonWidget() {
      this.setSize(new Size(10, 10));
      this.hintManagementRenderer = new Hintable$HintManagementRenderer(this);
      this.id = "";
   }

   @NotNull
   @Override
   public ButtonPositionHint getHints() {
      if (this.hints != null) {
         return this.hints;
      }

      Intrinsics.throwUninitializedPropertyAccessException("");
      return null;
   }

   @Override
   public void setHints(@NotNull ButtonPositionHint var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.hints = var1;
   }

   @NotNull
   @Override
   public IdentifierHolder getTexture() {
      return TEXTURE;
   }

   @NotNull
   @Override
   public Point getTexturePt() {
      return new Point(this.getTx(), this.getTy());
   }

   @NotNull
   @Override
   public Point getHoveringTexturePt() {
      return new Point(this.getTx(), this.getTy() + 10);
   }

   public void render(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      super.render(var1, var2, var3, var4);
      this.getHintManagementRenderer().renderUnderManagement(var1);
      if (GuiSettings.INSTANCE.getSHOW_BUTTON_TOOLTIPS().getBooleanValue()
         && this.contains(var2, var3)
         && ((CharSequence)this.getTooltipTextSource().invoke()).length() > 0) {
         TooltipsManager.INSTANCE.addTooltip((String)this.getTooltipTextSource().invoke(), var2, var3);
      }
   }
}
