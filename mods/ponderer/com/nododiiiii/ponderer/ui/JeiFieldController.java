package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.compat.jei.JeiCompat;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.HintableTextFieldWidget;

public class JeiFieldController {
   private final BiConsumer<JeiAwareScreen, IdFieldMode> activateAction;
   private boolean active = false;
   @Nullable
   private HintableTextFieldWidget targetField = null;

   public JeiFieldController(BiConsumer<JeiAwareScreen, IdFieldMode> activateAction) {
      this.activateAction = activateAction;
   }

   @Nullable
   public HintableTextFieldWidget targetField() {
      return this.targetField;
   }

   public boolean isActiveFor(HintableTextFieldWidget field) {
      return this.active && this.targetField == field;
   }

   public void toggle(JeiAwareScreen host, HintableTextFieldWidget field, IdFieldMode mode) {
      if (JeiCompat.isAvailable()) {
         if (this.active && this.targetField == field) {
            this.deactivate();
         } else {
            this.active = true;
            this.targetField = field;
            this.activateAction.accept(host, mode);
         }
      }
   }

   public void deactivate() {
      this.active = false;
      this.targetField = null;
      JeiCompat.clearActiveEditor();
   }

   public boolean isActive() {
      return this.active;
   }
}
