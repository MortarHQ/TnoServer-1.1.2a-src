package com.nododiiiii.ponderer.ui.catnip;

import com.nododiiiii.ponderer.ui.UIText;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreen;
import net.createmod.catnip.gui.ConfirmationScreen;
import net.createmod.catnip.gui.ScreenOpener;
import net.createmod.catnip.gui.UIRenderHelper;
import net.createmod.catnip.gui.ConfirmationScreen.Response;
import net.createmod.catnip.gui.widget.AbstractSimiWidget;
import net.createmod.catnip.theme.Color;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class AbstractDeclarativeScreen extends ConfigScreen {
   protected final String scopeKey;
   protected final String titleKey;
   @Nullable
   private String errorMessage;
   @Nullable
   private String infoMessage;

   protected AbstractDeclarativeScreen(@Nullable Screen parent, String scopeKey, String titleKey) {
      super(parent);
      this.scopeKey = scopeKey;
      this.titleKey = titleKey;
   }

   protected final void clearStatusMessages() {
      this.errorMessage = null;
      this.infoMessage = null;
   }

   protected final void setErrorMessage(@Nullable String message) {
      this.errorMessage = message;
      if (message != null && !message.isBlank()) {
         this.infoMessage = null;
      }
   }

   protected final void setInfoMessage(@Nullable String message) {
      this.infoMessage = message;
      if (message != null && !message.isBlank()) {
         this.errorMessage = null;
      }
   }

   protected final void renderBreadcrumb(GuiGraphics graphics, int centerX, int y) {
      String breadcrumb = UIText.of("ponderer.ui.mod_name") + " > " + this.getBreadcrumbScopeText() + " > " + this.getBreadcrumbTitleText();
      graphics.drawCenteredString(this.minecraft.font, breadcrumb, centerX, y, ((Color)UIRenderHelper.COLOR_TEXT.getFirst()).getRGB());
   }

   protected final void renderStatusMessage(GuiGraphics graphics, int x, int y, int maxWidth) {
      String message = this.errorMessage != null && !this.errorMessage.isBlank() ? this.errorMessage : this.infoMessage;
      if (message != null && !message.isBlank()) {
         int color = this.errorMessage != null && !this.errorMessage.isBlank()
            ? ((Color)AbstractSimiWidget.COLOR_FAIL.getFirst()).getRGB()
            : ((Color)AbstractSimiWidget.COLOR_SUCCESS.getFirst()).getRGB();
         graphics.drawString(this.minecraft.font, this.minecraft.font.plainSubstrByWidth(message, maxWidth), x, y, color);
      }
   }

   protected String getBreadcrumbScopeText() {
      return UIText.of(this.scopeKey);
   }

   protected String getBreadcrumbTitleText() {
      return UIText.of(this.titleKey);
   }

   protected void attemptBackToParent() {
      if (!this.hasUnsavedChanges()) {
         ScreenOpener.open(this.parent);
      } else {
         this.showLeavingPrompt(response -> {
            if (response != Response.Cancel) {
               if (response == Response.Confirm) {
                  if (!this.saveEdits()) {
                     return;
                  }
               } else {
                  this.discardEdits();
               }

               ScreenOpener.open(this.parent);
            }
         });
      }
   }

   protected void showLeavingPrompt(Consumer<Response> action) {
      int dirtyFields = this.getUnsavedChangeCount();
      new ConfirmationScreen()
         .centered()
         .withThreeActions(action)
         .addText(
            Component.translatable(
               "catnip.ui.leaving_with_changes_message",
               new Object[]{dirtyFields, Component.translatable(dirtyFields != 1 ? "catnip.ui.value_changes_plural" : "catnip.ui.value_changes_singular")}
            )
         )
         .open(this);
   }

   public void onClose() {
      this.attemptBackToParent();
   }

   protected abstract boolean hasUnsavedChanges();

   protected abstract int getUnsavedChangeCount();

   protected abstract boolean saveEdits();

   protected abstract void discardEdits();
}
