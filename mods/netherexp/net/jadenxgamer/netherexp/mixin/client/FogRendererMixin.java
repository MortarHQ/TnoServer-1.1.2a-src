package net.jadenxgamer.netherexp.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.FogRenderer.FogData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {
   @WrapOperation(method = "setupFog", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/FogRenderer$FogData;start:F", ordinal = 6))
   private static void netherexp$netherFogStart(FogData instance, float value, Operation<Void> original) {
      if ((Boolean)JNEConfigs.UNCAPPED_NETHER_FOG_DISTANCE.get()) {
         float farPlaneDistance = Minecraft.getInstance().gameRenderer.getRenderDistance();
         original.call(new Object[]{instance, farPlaneDistance * 0.05F});
      } else {
         original.call(new Object[]{instance, value});
      }
   }

   @WrapOperation(method = "setupFog", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/FogRenderer$FogData;end:F", ordinal = 12))
   private static void netherexp$netherFogEnd(FogData instance, float value, Operation<Void> original) {
      if ((Boolean)JNEConfigs.UNCAPPED_NETHER_FOG_DISTANCE.get()) {
         float farPlaneDistance = Minecraft.getInstance().gameRenderer.getRenderDistance();
         original.call(new Object[]{instance, farPlaneDistance});
      } else {
         original.call(new Object[]{instance, value});
      }
   }
}
