package com.natamus.collective.fabric.mixin;

import com.mojang.serialization.Lifecycle;
import com.natamus.collective_common_fabric.services.Services;
import net.minecraft.class_525;
import net.minecraft.class_7193;
import net.minecraft.class_7659;
import net.minecraft.class_7780;
import net.minecraft.class_31.class_7729;
import net.minecraft.class_7723.class_7725;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = class_525.class, priority = 1001)
public abstract class CreateWorldScreenMixin {
   @Shadow
   private boolean field_42502;

   @Shadow
   protected abstract void method_41847(class_7729 var1, class_7780<class_7659> var2, Lifecycle var3);

   @Inject(
      method = "onCreate",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows;confirmWorldCreation(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;Lcom/mojang/serialization/Lifecycle;Ljava/lang/Runnable;Z)V"
      ),
      locals = LocalCapture.CAPTURE_FAILSOFT,
      cancellable = true
   )
   private void onCreate(
      CallbackInfo ci,
      class_7193 worldCreationContext,
      class_7725 complete,
      class_7780 layeredRegistryAccess,
      Lifecycle lifecycle,
      Lifecycle lifecycle2,
      Lifecycle lifecycle3,
      boolean bl
   ) {
      if (!this.field_42502) {
         if (Services.MODLOADER.isDevelopmentEnvironment() || Services.MODLOADER.isModLoaded("hideexperimentalwarning")) {
            this.method_41847(complete.comp_1018(), layeredRegistryAccess, lifecycle3);
            ci.cancel();
         }
      }
   }
}
