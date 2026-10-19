package com.nododiiiii.ponderer.ui;

import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.HintableTextFieldWidget;
import net.minecraft.client.gui.screens.Screen;

public abstract class AbstractJeiAwareFormScreen extends AbstractStatefulDeclarativeFormScreen implements JeiTextButtonHost {
   private final JeiFieldController jeiController;

   protected AbstractJeiAwareFormScreen(
      @Nullable Screen parent, String scopeKey, String titleKey, int preferredListWidth, BiConsumer<JeiAwareScreen, IdFieldMode> activationAction
   ) {
      super(parent, scopeKey, titleKey, preferredListWidth);
      this.jeiController = new JeiFieldController(activationAction);
   }

   protected AbstractJeiAwareFormScreen(@Nullable Screen parent, String scopeKey, String titleKey, BiConsumer<JeiAwareScreen, IdFieldMode> activationAction) {
      super(parent, scopeKey, titleKey);
      this.jeiController = new JeiFieldController(activationAction);
   }

   @Nullable
   @Override
   public HintableTextFieldWidget getJeiTargetField() {
      return this.jeiController.targetField();
   }

   @Override
   public final void toggleJeiForField(HintableTextFieldWidget field, IdFieldMode mode) {
      this.jeiController.toggle(this, field, mode);
      this.onJeiStateChanged();
   }

   @Override
   public final boolean isJeiActiveForField(HintableTextFieldWidget field) {
      return this.jeiController.isActiveFor(field);
   }

   @Override
   public void deactivateJei() {
      if (this.jeiController.isActive()) {
         this.jeiController.deactivate();
         this.onJeiStateChanged();
      }
   }

   @Override
   public void showJeiIncompatibleWarning(IdFieldMode mode) {
      this.setErrorMessage(switch (mode) {
         case BLOCK -> UIText.of("ponderer.ui.jei.error.not_block");
         case ENTITY -> UIText.of("ponderer.ui.jei.error.not_spawn_egg");
         case ITEM, INGREDIENT -> null;
      });
   }

   @Override
   public int getGuiLeft() {
      return this.width / 2 - this.currentListWidthValue() / 2 - 40;
   }

   @Override
   public int getGuiTop() {
      return 35;
   }

   @Override
   public int getGuiWidth() {
      return this.currentListWidthValue() + 80;
   }

   @Override
   public int getGuiHeight() {
      return this.height - 60;
   }

   public void removed() {
      super.removed();
      if (this.jeiController.isActive()) {
         this.deactivateJei();
      }
   }

   protected boolean rebuildOnJeiStateChange() {
      return false;
   }

   protected void onJeiStateChanged() {
      if (this.rebuildOnJeiStateChange()) {
         this.rebuildListPreservingScroll();
      }
   }
}
