package com.tensura_tno.client.compat;

import com.mojang.blaze3d.vertex.PoseStack;

public final class NebLuminousChestTransform {
   private NebLuminousChestTransform() {
   }

   public static void applyBodyAge(PoseStack poseStack, boolean young) {
      if (young) {
         poseStack.scale(0.5F, 0.5F, 0.5F);
         poseStack.translate(0.0F, 1.5F, 0.0F);
      }
   }
}
