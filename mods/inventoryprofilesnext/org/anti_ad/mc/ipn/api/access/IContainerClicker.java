package org.anti_ad.mc.ipn.api.access;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

public interface IContainerClicker {
   void leftClick(int var1);

   void rightClick(int var1);

   void shiftClick(int var1);

   void qClick(int var1);

   void click(int var1, int var2);

   void swap(int var1, int var2);

   void executeQClicks(@NotNull Map var1);

   void executeSwapClicks(@NotNull Map var1);

   void executeClicks(@NotNull Map var1);
}
