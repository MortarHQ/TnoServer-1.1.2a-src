package com.tno_tensura_extras.mixin;

import com.tno_tensura_extras.util.AlignmentFieldManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Entity.class)
public abstract class EntityAlignmentFieldMixin {
   @ModifyArg(
      method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;collide(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"),
      index = 0
   )
   private Vec3 tno_tensura_extras$clipAlignmentFieldMovement(Vec3 requestedMovement) {
      return AlignmentFieldManager.clipMovement((Entity)this, requestedMovement);
   }
}
