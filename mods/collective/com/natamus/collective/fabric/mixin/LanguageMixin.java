package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveLifecycleEvents;
import net.minecraft.class_2477;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = class_2477.class, priority = 1001)
public class LanguageMixin {
   @Inject(method = "loadDefault()Lnet/minecraft/locale/Language;", at = @At("TAIL"))
   private static void loadDefault(CallbackInfoReturnable<class_2477> cir) {
      ((CollectiveLifecycleEvents.Default_Language_Loaded)CollectiveLifecycleEvents.DEFAULT_LANGUAGE_LOADED.invoker()).onLanguageLoad();
   }
}
