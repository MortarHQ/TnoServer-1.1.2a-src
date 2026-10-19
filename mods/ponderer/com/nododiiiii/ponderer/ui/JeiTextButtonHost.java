package com.nododiiiii.ponderer.ui;

import net.createmod.catnip.config.ui.HintableTextFieldWidget;

public interface JeiTextButtonHost extends JeiAwareScreen {
   void toggleJeiForField(HintableTextFieldWidget var1, IdFieldMode var2);

   boolean isJeiActiveForField(HintableTextFieldWidget var1);
}
