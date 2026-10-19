package dev.shadowako.trlagbegon.mixin;

import io.github.manasmods.tensura.entity.magic.barrier.BarrierEntity;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityFluidBypassMixin {
   @Inject(method = "updateFluidHeightAndDoFluidPushing", at = @At("HEAD"), cancellable = true, remap = false)
   private void trlagbegon$skipBarrierFluidPush(TagKey<Fluid> fluidTag, double speed, CallbackInfoReturnable<Boolean> cir) {
      if (this instanceof BarrierEntity) {
         cir.setReturnValue(false);
      }
   }

   @Inject(method = "updateFluidOnEyes", at = @At("HEAD"), cancellable = true, remap = false)
   private void trlagbegon$skipBarrierFluidEyes(CallbackInfo ci) {
      if (this instanceof BarrierEntity) {
         ci.cancel();
      }
   }
}
