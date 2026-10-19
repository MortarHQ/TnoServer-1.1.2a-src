package com.finderfeed.fdlib.mixin;

import com.finderfeed.fdlib.systems.post_shaders.FDPostShadersHandler;
import com.finderfeed.fdlib.systems.post_shaders.FDPostShadersReloadableResourceListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Minecraft.GameLoadCookie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
   @Inject(method = "onResourceLoadFinished", at = @At("TAIL"))
   public void init(GameLoadCookie p_299896_, CallbackInfo ci) {
      if (!FDPostShadersHandler.WAS_LOADED_ONCE) {
         FDPostShadersReloadableResourceListener.initializeShaders();
         FDPostShadersHandler.WAS_LOADED_ONCE = true;
      }
   }
}
