package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveEntityEvents;
import net.minecraft.class_1589;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = class_1589.class, priority = 1001)
public class MagmaCubeMixin {
   @Inject(method = "jumpFromGround", at = @At("TAIL"))
   public void MagmaCube_jumpFromGround(CallbackInfo ci) {
      class_1589 magmaCube = (class_1589)this;
      ((CollectiveEntityEvents.Entity_Is_Jumping)CollectiveEntityEvents.ON_ENTITY_IS_JUMPING.invoker()).onLivingJump(magmaCube.method_37908(), magmaCube);
   }
}
