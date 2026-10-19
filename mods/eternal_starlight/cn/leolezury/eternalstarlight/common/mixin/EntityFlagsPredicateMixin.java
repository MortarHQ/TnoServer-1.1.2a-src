package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityFlagsPredicate.class)
public abstract class EntityFlagsPredicateMixin {
   @WrapOperation(
      method = "matches(Lnet/minecraft/world/entity/Entity;)Z",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isOnFire()Z")
   )
   private boolean isOnFire(Entity instance, Operation<Boolean> original) {
      return (Boolean)original.call(new Object[]{instance}) || ESDataAttachments.ABYSSAL_FIRE_TICKS.getData(instance) > 0;
   }
}
