package com.nododiiiii.ponderer.ui.catnip;

import javax.annotation.Nullable;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.minecraft.client.gui.screens.Screen;

public abstract class AbstractReadonlyDeclarativeListScreen extends AbstractDeclarativeListScreen {
   protected AbstractReadonlyDeclarativeListScreen(@Nullable Screen parent, String scopeKey, String titleKey) {
      super(parent, scopeKey, titleKey);
   }

   protected AbstractReadonlyDeclarativeListScreen(@Nullable Screen parent, String scopeKey, String titleKey, int preferredListWidth) {
      super(parent, scopeKey, titleKey, preferredListWidth);
   }

   @Override
   protected void init() {
      super.init();
      hideActionButton(this.saveChanges);
      hideActionButton(this.discardChanges);
   }

   @Override
   protected final boolean hasUnsavedChanges() {
      return false;
   }

   @Override
   protected final int getUnsavedChangeCount() {
      return 0;
   }

   @Override
   protected final boolean saveEdits() {
      return false;
   }

   @Override
   protected final void discardEdits() {
   }

   @Override
   protected int getEntryHeight() {
      return 24;
   }

   private static void hideActionButton(@Nullable BoxWidget button) {
      if (button != null) {
         button.visible = false;
         button.active = false;
      }
   }
}
