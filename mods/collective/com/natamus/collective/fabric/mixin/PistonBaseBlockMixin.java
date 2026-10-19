package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectivePistonEvents;
import net.minecraft.class_1937;
import net.minecraft.class_2318;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2665;
import net.minecraft.class_2680;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = class_2665.class, priority = 1001)
public class PistonBaseBlockMixin {
   @Inject(
      method = "triggerEvent(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;II)Z",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/block/piston/PistonBaseBlock;moveBlocks(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Z)Z",
         ordinal = 0
      ),
      cancellable = true
   )
   public void triggerEvent_default(class_2680 blockState, class_1937 level, class_2338 blockPos, int i, int j, CallbackInfoReturnable<Boolean> cir) {
      if (!((CollectivePistonEvents.Piston_Activate)CollectivePistonEvents.PRE_PISTON_ACTIVATE.invoker())
         .onPistonActivate(level, blockPos, (class_2350)blockState.method_11654(class_2318.field_10927), false)) {
         cir.setReturnValue(false);
      }
   }

   @Inject(
      method = "triggerEvent(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;II)Z",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;",
         ordinal = 0
      ),
      cancellable = true
   )
   public void triggerEvent_extended(class_2680 blockState, class_1937 level, class_2338 blockPos, int i, int j, CallbackInfoReturnable<Boolean> cir) {
      if (!((CollectivePistonEvents.Piston_Activate)CollectivePistonEvents.PRE_PISTON_ACTIVATE.invoker())
         .onPistonActivate(level, blockPos, (class_2350)blockState.method_11654(class_2318.field_10927), true)) {
         cir.setReturnValue(false);
      }
   }
}
