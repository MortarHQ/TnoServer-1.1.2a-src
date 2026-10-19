package com.finderfeed.fdlib.systems.shake;

import com.mojang.blaze3d.vertex.PoseStack;

public interface ScreenShake {
   void process(PoseStack var1, int var2, float var3);

   boolean hasEnded(int var1);
}
