package com.tensura_tno.mixin.client;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.PostChain;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.finderfeed.fdlib.systems.post_shaders.FDPostShadersReloadableResourceListener", remap = false)
public class FDLibShaderCrashFixMixin {
   private static final Logger LOGGER = LoggerFactory.getLogger("tensura_tno/FDLibShaderFix");

   @Inject(method = "initializeShaders", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
   private static void tensuraTno$safeInitializeShaders(CallbackInfo ci) {
      try {
         Class<?> handlerClass = Class.forName("com.finderfeed.fdlib.systems.post_shaders.FDPostShadersHandler");
         List<PostChain> postShaders = (List<PostChain>)handlerClass.getField("POST_SHADERS").get(null);

         for (PostChain shader : new ArrayList<>(postShaders)) {
            try {
               shader.close();
            } catch (Exception var15) {
            }
         }

         postShaders.clear();
         Class<?> eventClass = Class.forName("com.finderfeed.fdlib.systems.post_shaders.FDPostShaderInitializeEvent");
         Event event = (Event)eventClass.getDeclaredConstructor().newInstance();
         NeoForge.EVENT_BUS.post(event);
         Method getRegistryMethod = eventClass.getMethod("getPostChainRegistry");
         List<Object> registry = (List<Object>)getRegistryMethod.invoke(event);
         Class<?> listenerClass = Class.forName("com.finderfeed.fdlib.systems.post_shaders.FDPostShadersReloadableResourceListener");
         Class<?> shaderLoadInstanceClass = Class.forName("com.finderfeed.fdlib.systems.post_shaders.FDPostShaderInitializeEvent$PostChainShaderLoadInstance");
         Method loadPostChainMethod = listenerClass.getMethod("loadPostChain", shaderLoadInstanceClass);
         List<Throwable> errors = new ArrayList<>();

         for (Object shader : registry) {
            try {
               loadPostChainMethod.invoke(null, shader);
            } catch (Exception e) {
               Throwable cause = e.getCause() != null ? e.getCause() : e;
               errors.add(cause);
            }
         }

         if (!errors.isEmpty()) {
            LOGGER.error("[tensura_tno] {} FDLib post-shader(s) failed to load. Suppressing crash:", errors.size());

            for (Throwable e : errors) {
               LOGGER.error("  Shader load error:", e);
            }

            LOGGER.warn("[tensura_tno] Game will continue without the failed shaders.");
         } else {
            LOGGER.info("[tensura_tno] FDLib shaders initialized successfully (safe wrapper).");
         }
      } catch (Exception e) {
         LOGGER.error("[tensura_tno] FDLib shader init safe-wrapper encountered an unexpected error:", e);
      }

      ci.cancel();
   }
}
