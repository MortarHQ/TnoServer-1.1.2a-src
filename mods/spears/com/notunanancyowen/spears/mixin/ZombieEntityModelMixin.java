package com.notunanancyowen.spears.mixin;

import com.notunanancyowen.spears.Spears;
import com.notunanancyowen.spears.components.SwingAnimation;
import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.world.entity.monster.Monster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractZombieModel.class)
public abstract class ZombieEntityModelMixin {
   @Inject(
      method = "setupAnim(Lnet/minecraft/world/entity/monster/Monster;FFFFF)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/model/AnimationUtils;animateZombieArms(Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;ZFF)V"
      ),
      cancellable = true
   )
   private void isUsingASpear(Monster hostileEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
      if (hostileEntity.getMainHandItem().get(Spears.SWING_ANIMATION) instanceof SwingAnimation s && s.swingType().equals("stab")) {
         ci.cancel();
      }
   }
}
