package me.decce.gnetum.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import me.decce.gnetum.ElementType;
import me.decce.gnetum.FramebufferManager;
import me.decce.gnetum.Gnetum;
import me.decce.gnetum.gl.FramebufferTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GlStateManager.class)
public class GlStateManagerMixin {
   @Inject(method = "_glBindFramebuffer", at = @At("TAIL"))
   private static void gnetum$bindFramebuffer(int target, int framebuffer, CallbackInfo ci) {
      if (target == 36160) {
         FramebufferTracker.setCurrentlyBoundFbo(framebuffer);
      }
   }

   @Inject(method = "_blendFunc(II)V", at = @At("HEAD"), cancellable = true)
   private static void gnetum$blendFunc(int srcFactor, int dstFactor, CallbackInfo ci) {
      if (Gnetum.rendering) {
         GlStateManager._blendFuncSeparate(srcFactor, dstFactor, 1, 771);
         ci.cancel();
      }
   }

   @Inject(method = "_blendFuncSeparate(IIII)V", at = @At("HEAD"), cancellable = true)
   private static void gnetum$blendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha, CallbackInfo ci) {
      if (Gnetum.rendering) {
         if (srcFactorAlpha == 1 && dstFactorAlpha == 771) {
            if (FramebufferTracker.getCurrentlyBoundFbo() == FramebufferManager.getInstance().id()
               && gnetum$isBlendFuncDangerous(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha)
               && (Gnetum.currentElementType != ElementType.VANILLA || !"xaerohud:hud".equals(Gnetum.currentElement))) {
               Gnetum.disableCachingForCurrentElement(String.format("Blending Function (%d, %d, %d, %d)", srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha));
            }
         } else {
            GlStateManager._blendFuncSeparate(srcFactor, dstFactor, 1, 771);
            ci.cancel();
         }
      }
   }

   @Unique
   private static boolean gnetum$isBlendFuncDangerous(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
      if (gnetum$isUsingDestColor(srcFactor) || gnetum$isUsingDestColor(dstFactor)) {
         return true;
      } else {
         return dstFactor != 768 && dstFactor != 769 ? (srcFactor == 1 || srcFactor == 0) && dstFactor == 1 : true;
      }
   }

   @Unique
   private static boolean gnetum$isUsingDestColor(int factor) {
      return factor == 774 || factor == 775;
   }
}
