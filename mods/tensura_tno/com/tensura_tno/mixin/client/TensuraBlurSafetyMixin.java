package com.tensura_tno.mixin.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "io.github.manasmods.tensura.util.client.RenderHelper", remap = false)
public abstract class TensuraBlurSafetyMixin {
   private static volatile Field BLUR_FIELD;
   private static volatile boolean BLUR_FIELD_RESOLVED = false;

   private static PostChain tensuraTno$readBlurEffect(GameRenderer gr) {
      Field f = tensuraTno$resolveBlurField();
      if (f == null) {
         return null;
      }

      try {
         Object value = f.get(gr);
         return value instanceof PostChain ? (PostChain)value : null;
      } catch (IllegalAccessException ignored) {
         return null;
      }
   }

   private static Field tensuraTno$resolveBlurField() {
      if (BLUR_FIELD_RESOLVED) {
         return BLUR_FIELD;
      }

      synchronized (TensuraBlurSafetyMixin.class) {
         if (BLUR_FIELD_RESOLVED) {
            return BLUR_FIELD;
         }

         Field found = null;

         for (Field f : GameRenderer.class.getDeclaredFields()) {
            if (PostChain.class.isAssignableFrom(f.getType())) {
               try {
                  f.setAccessible(true);
                  found = f;
                  break;
               } catch (RuntimeException var8) {
               }
            }
         }

         BLUR_FIELD = found;
         BLUR_FIELD_RESOLVED = true;
         return found;
      }
   }

   @Inject(method = "renderCustomBlur(I)V", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
   private static void tensuraTno$safeBlur(int blurStrength, CallbackInfo ci) {
      ci.cancel();
      if (blurStrength > 0) {
         Minecraft mc = Minecraft.getInstance();
         if (mc != null) {
            GameRenderer gr = mc.gameRenderer;
            if (gr != null) {
               PostChain chain = tensuraTno$readBlurEffect(gr);
               if (chain != null) {
                  RenderTarget mainRT = mc.getMainRenderTarget();
                  if (mainRT != null) {
                     try {
                        chain.resize(mainRT.width, mainRT.height);
                        chain.setUniform("Radius", blurStrength);
                        chain.process(1.0F);
                     } catch (Throwable var15) {
                     } finally {
                        try {
                           mainRT.bindWrite(false);
                           RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        } catch (Throwable var14) {
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
