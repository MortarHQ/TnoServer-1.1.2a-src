package com.nododiiiii.ponderer.ui;

import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.HintableTextFieldWidget;

public interface HintableTextWidgetBinding extends FieldBinding<String> {
   void attach(HintableTextFieldWidget var1);

   @Nullable
   HintableTextFieldWidget widget();
}
