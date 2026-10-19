package com.mongoose.royalvariations.mixin;

import com.mongoose.royalvariations.common.attachments.RVCapHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {
   @Inject(method = "getPackedLightCoords(Lnet/minecraft/world/entity/Entity;F)I", at = @At("HEAD"), cancellable = true)
   public void getPackedLightCoords(T p_115334_, float p_115335_, CallbackInfoReturnable<Integer> cir) {
      if (p_115334_ instanceof LivingEntity livingEntity && RVCapHelper.isFlashing(livingEntity)) {
         cir.setReturnValue(15728880);
      }
   }
}
