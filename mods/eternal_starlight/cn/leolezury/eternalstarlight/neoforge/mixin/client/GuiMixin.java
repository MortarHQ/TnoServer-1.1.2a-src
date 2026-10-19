package cn.leolezury.eternalstarlight.neoforge.mixin.client;

import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
   @Inject(
      method = "renderAirLevel(Lnet/minecraft/client/gui/GuiGraphics;)V",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V", remap = false)
   )
   public void beforeRenderAirLevel(GuiGraphics guiGraphics, CallbackInfo ci) {
      ESClientHandler.setAirBubbleColor();
   }

   @Inject(
      method = "renderAirLevel(Lnet/minecraft/client/gui/GuiGraphics;)V",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V", remap = false)
   )
   public void afterRenderAirLevel(GuiGraphics guiGraphics, CallbackInfo ci) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   @WrapOperation(
      method = "renderAirLevel(Lnet/minecraft/client/gui/GuiGraphics;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V")
   )
   public void offsetAirBubbles(GuiGraphics instance, ResourceLocation location, int x, int y, int width, int height, Operation<Void> original) {
      original.call(new Object[]{instance, location, x, y + ESClientHandler.getAirBubbleYOffset(), width, height});
   }
}
