package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveRenderEvents;
import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_742;
import net.minecraft.class_759;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = class_759.class, priority = 1001)
public class ItemInHandRendererMixin {
   @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
   private void renderArmWithItem(
      class_742 abstractClientPlayer,
      float f,
      float g,
      class_1268 interactionHand,
      float h,
      class_1799 itemStack,
      float i,
      class_4587 poseStack,
      class_4597 multiBufferSource,
      int j,
      CallbackInfo ci
   ) {
      if (!((CollectiveRenderEvents.Render_Specific_Hand)CollectiveRenderEvents.RENDER_SPECIFIC_HAND.invoker())
         .onRenderSpecificHand(interactionHand, poseStack, itemStack)) {
         ci.cancel();
      }
   }
}
