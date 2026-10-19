package org.anti_ad.mc.ipnext.gui.inject;

import kotlin.Unit;
import org.anti_ad.mc.common.gui.layout.WidgetExtensionsKt;
import org.anti_ad.mc.common.gui.widgets.Widget;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.anti_ad.mc.ipnext.gui.inject.base.ProfileButtonWidget;
import org.jetbrains.annotations.NotNull;

public final class EditorWidget$InitWidgets {
   @NotNull
   private final ProfileButtonWidget showHideButton;

   public EditorWidget$InitWidgets(EditorWidget var1) {
      this.this$0 = var1;
      ProfileButtonWidget var5 = new ProfileButtonWidget(EditorWidget$InitWidgets::showHideButton$lambda$0);
      EditorWidget var2 = this.this$0;
      ProfileButtonWidget var3 = var5;
      EditorWidget$InitWidgets var4 = this;
      var3.setTx(160);
      var3.setTy(40);
      var3.setHints(var2.getHints());
      var2.getSnapableList().add(var3);
      var2.addChild((Widget)var3);
      var3.setVisible(GuiSettings.INSTANCE.getENABLE_INVENTORY_EDITOR_BUTTON().getValue());
      var3.setTooltipText(I18n.INSTANCE.translate("inventoryprofiles.tooltip.editor_toggle", new Object[0]));
      var3.setZIndex(0);
      var2.getHintableList().add(var3);
      var4.showHideButton = var5;
      this.reHint();
   }

   public final void reHint() {
      int var1 = GuiSettings.INSTANCE.getEDITOR_TOP().getIntegerValue();
      int var2 = GuiSettings.INSTANCE.getEDITOR_LEFT().getIntegerValue();
      if (this.this$0.getHints().getTop() < -var1) {
         this.this$0.getHints().setTop(-var1);
      }

      if (this.this$0.getHints().getTop() > this.this$0.getContainerHeight() - 20) {
         this.this$0.getHints().setTop(this.this$0.getContainerHeight() - 20);
      }

      if (this.this$0.getHints().getHorizontalOffset() < -var2) {
         this.this$0.getHints().setHorizontalOffset(-var2);
      }

      if (this.this$0.getHints().getHorizontalOffset() > this.this$0.getContainerWidth() - 20) {
         this.this$0.getHints().setHorizontalOffset(this.this$0.getContainerWidth() - 20);
      }

      WidgetExtensionsKt.setTopLeft((Widget)this.showHideButton, var1 + this.this$0.getHints().getTop(), var2 + this.this$0.getHints().getHorizontalOffset());
   }

   private static final Unit showHideButton$lambda$0(EditorWidget var0) {
      var0.showEditorScreen();
      return Unit.INSTANCE;
   }
}
