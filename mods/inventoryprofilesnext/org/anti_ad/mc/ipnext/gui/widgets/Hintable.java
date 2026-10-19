package org.anti_ad.mc.ipnext.gui.widgets;

import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.ipnext.integration.ButtonPositionHint;
import org.jetbrains.annotations.NotNull;

public interface Hintable {
   @NotNull
   ButtonPositionHint getHints();

   void setHints(@NotNull ButtonPositionHint var1);

   boolean getUnderManagement();

   void setUnderManagement(boolean var1);

   @NotNull
   Hintable$HintManagementRenderer getHintManagementRenderer();

   void setHintManagementRenderer(@NotNull Hintable$HintManagementRenderer var1);

   void renderUnderManagement(@NotNull NativeContext var1);

   void moveLeft(int var1);

   void moveRight(int var1);

   void moveUp(int var1);

   void moveDown(int var1);
}
