package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveEntityEvents;
import net.minecraft.class_1496;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = class_1496.class, priority = 1001)
public class AbstractHorseMixin {
   @Inject(method = "tickRidden", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/horse/AbstractHorse;setIsJumping(Z)V", ordinal = 0))
   public void AbstractHorse_travel(class_1657 player, class_243 vec3, CallbackInfo ci) {
      class_1496 horse = (class_1496)this;
      ((CollectiveEntityEvents.Entity_Is_Jumping)CollectiveEntityEvents.ON_ENTITY_IS_JUMPING.invoker()).onLivingJump(horse.method_37908(), horse);
   }
}
