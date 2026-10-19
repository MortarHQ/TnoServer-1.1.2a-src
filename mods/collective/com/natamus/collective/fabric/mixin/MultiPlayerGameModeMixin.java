package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveBlockEvents;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_636;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = class_636.class, priority = 1001)
public class MultiPlayerGameModeMixin {
   @Shadow
   @Final
   private class_310 field_3712;

   @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
   public void MultiPlayerGameMode_useItemOn(
      class_746 localPlayer, class_1268 interactionHand, class_3965 blockHitResult, CallbackInfoReturnable<class_1269> cir
   ) {
      if (!((CollectiveBlockEvents.Block_Right_Click)CollectiveBlockEvents.BLOCK_RIGHT_CLICK.invoker())
         .onBlockRightClick(localPlayer.method_37908(), localPlayer, interactionHand, blockHitResult.method_17777(), blockHitResult)) {
         cir.setReturnValue(class_1269.field_5814);
      }
   }

   @Inject(
      method = "startDestroyBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;startPrediction(Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/client/multiplayer/prediction/PredictiveAction;)V",
         ordinal = 0
      ),
      cancellable = true
   )
   public void MultiPlayerGameMode_startDestroyBlock_creative(class_2338 blockPos, class_2350 direction, CallbackInfoReturnable<Boolean> cir) {
      if (!((CollectiveBlockEvents.Block_Left_Click)CollectiveBlockEvents.BLOCK_LEFT_CLICK.invoker())
         .onBlockLeftClick(this.field_3712.field_1687, this.field_3712.field_1724, blockPos, direction)) {
         cir.setReturnValue(false);
      }
   }

   @Inject(
      method = "startDestroyBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;startPrediction(Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/client/multiplayer/prediction/PredictiveAction;)V",
         ordinal = 1
      ),
      cancellable = true
   )
   public void MultiPlayerGameMode_startDestroyBlock_survival(class_2338 blockPos, class_2350 direction, CallbackInfoReturnable<Boolean> cir) {
      if (!((CollectiveBlockEvents.Block_Left_Click)CollectiveBlockEvents.BLOCK_LEFT_CLICK.invoker())
         .onBlockLeftClick(this.field_3712.field_1687, this.field_3712.field_1724, blockPos, direction)) {
         cir.setReturnValue(false);
      }
   }
}
