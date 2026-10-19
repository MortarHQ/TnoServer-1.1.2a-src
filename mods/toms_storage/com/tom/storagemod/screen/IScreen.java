package com.tom.storagemod.screen;

import java.util.function.Consumer;

public interface IScreen {
   void getExclusionAreas(Consumer<IScreen.Box> var1);

   record Box(int x, int y, int width, int height) {
   }
}
