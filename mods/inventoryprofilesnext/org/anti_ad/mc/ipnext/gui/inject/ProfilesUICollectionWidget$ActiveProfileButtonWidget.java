package org.anti_ad.mc.ipnext.gui.inject;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.gui.TooltipsManager;
import org.anti_ad.mc.common.gui.widgets.CustomButtonWidget;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.jetbrains.annotations.NotNull;

public final class ProfilesUICollectionWidget$ActiveProfileButtonWidget extends CustomButtonWidget {
   @NotNull
   private String vText;
   @NotNull
   private String tooltipText;

   public ProfilesUICollectionWidget$ActiveProfileButtonWidget(@NotNull ProfilesUICollectionWidget var1, Function0 var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      this.this$0 = var1;
      super(var2);
      this.vText = "";
      this.tooltipText = "";
   }

   public final boolean getVisible() {
      return GuiSettings.INSTANCE.getINVENTORY_OVERLAY_BUTTONS_VISIBLE().getValue() && super.getVisible();
   }

   public final void setVisible(boolean var1) {
      super.setVisible(var1);
   }

   @NotNull
   public final String getText() {
      return ProfilesUICollectionWidget.access$getCurrentProfileName(this.this$0);
   }

   public final void setText(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   @NotNull
   public final String getVText() {
      return this.vText;
   }

   public final void setVText(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.vText = var1;
   }

   @NotNull
   public final String getTooltipText() {
      return this.tooltipText;
   }

   public final void setTooltipText(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.tooltipText = var1;
   }

   public final void render(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      super.render(var1, var2, var3, var4);
      String var5 = ProfilesUICollectionWidget.access$getCurrentProfileName(this.this$0);
      if (!Intrinsics.areEqual(this.vText, var5)) {
         this.vText = var5;
         this.setVanillaMessage(var5);
      }

      if (GuiSettings.INSTANCE.getSHOW_BUTTON_TOOLTIPS().getBooleanValue() && this.contains(var2, var3) && this.tooltipText.length() > 0) {
         TooltipsManager.INSTANCE.addTooltip(this.tooltipText, var2, var3);
      }
   }

   public final boolean mouseClicked(int var1, int var2, int var3) {
      return super.mouseClicked(var1, var2, var3) && this.getVisible();
   }
}
