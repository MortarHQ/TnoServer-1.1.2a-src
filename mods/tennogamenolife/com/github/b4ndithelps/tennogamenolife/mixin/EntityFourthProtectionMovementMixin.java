package com.github.b4ndithelps.tennogamenolife.mixin;

import com.github.b4ndithelps.tennogamenolife.ability.skill.intrinsic.FourthProtectionManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Entity.class)
public abstract class EntityFourthProtectionMovementMixin {
   @ModifyArg(
      method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;collide(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"),
      index = 0
   )
   private Vec3 tennogamenolife$clipFourthProtectionMovement(Vec3 requestedMovement) {
      return FourthProtectionManager.clipPlayerMovement((Entity)this, requestedMovement);
   }
}
