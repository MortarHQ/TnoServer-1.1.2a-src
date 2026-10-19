package com.nododiiiii.ponderer.ui;

import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.HintableTextFieldWidget;

public interface JeiAwareScreen {
   @Nullable
   HintableTextFieldWidget getJeiTargetField();

   void deactivateJei();

   void showJeiIncompatibleWarning(IdFieldMode var1);

   int getGuiLeft();

   int getGuiTop();

   int getGuiWidth();

   int getGuiHeight();
}
