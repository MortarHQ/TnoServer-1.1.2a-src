package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveBlockEvents;
import com.natamus.collective.fabric.callbacks.CollectiveItemEvents;
import com.natamus.collective_common_fabric.functions.TaskFunctions;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2626;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3225;
import net.minecraft.class_3965;
import net.minecraft.class_2846.class_2847;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = class_3225.class, priority = 1001)
public class ServerPlayerGameModeMixin {
   @Shadow
   protected class_3218 field_14007;
   @Shadow
   @Final
   protected class_3222 field_14008;
   @Shadow
   private class_2338 field_20327;

   @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
   public void ServerPlayerGameMode_useItemOn(
      class_3222 serverPlayer,
      class_1937 level,
      class_1799 itemStack,
      class_1268 interactionHand,
      class_3965 blockHitResult,
      CallbackInfoReturnable<class_1269> ci
   ) {
      if (!((CollectiveBlockEvents.Block_Right_Click)CollectiveBlockEvents.BLOCK_RIGHT_CLICK.invoker())
         .onBlockRightClick(level, serverPlayer, interactionHand, blockHitResult.method_17777(), blockHitResult)) {
         ci.setReturnValue(class_1269.field_5814);
      }
   }

   @Inject(
      method = "handleBlockBreakAction(Lnet/minecraft/core/BlockPos;Lnet/minecraft/network/protocol/game/ServerboundPlayerActionPacket$Action;Lnet/minecraft/core/Direction;II)V",
      at = @At("HEAD"),
      cancellable = true
   )
   public void ServerPlayerGameMode_handleBlockBreakAction(class_2338 blockPos, class_2847 action, class_2350 direction, int i, int j, CallbackInfo ci) {
      if (!((CollectiveBlockEvents.Block_Left_Click)CollectiveBlockEvents.BLOCK_LEFT_CLICK.invoker())
         .onBlockLeftClick(this.field_14007, this.field_14008, blockPos, direction)) {
         this.field_14008.field_13987.method_14364(new class_2626(blockPos, this.field_14007.method_8320(blockPos)));
         this.field_14007.method_8413(blockPos, this.field_14007.method_8320(blockPos), this.field_14007.method_8320(blockPos), 3);
         ci.cancel();
      }
   }

   @Inject(
      method = "handleBlockBreakAction(Lnet/minecraft/core/BlockPos;Lnet/minecraft/network/protocol/game/ServerboundPlayerActionPacket$Action;Lnet/minecraft/core/Direction;II)V",
      at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/Objects;equals(Ljava/lang/Object;Ljava/lang/Object;)Z", ordinal = 0),
      cancellable = true
   )
   public void ServerPlayerGameMode_silence_block_mismatch(class_2338 blockPos, class_2847 action, class_2350 direction, int i, int j, CallbackInfo ci) {
      this.field_14007.method_8517(this.field_14008.method_5628(), this.field_20327, -1);
      this.field_14008.field_13987.method_14364(new class_2626(this.field_20327, this.field_14007.method_8320(this.field_20327)));
      this.field_14007.method_8517(this.field_14008.method_5628(), blockPos, -1);
      this.field_14008.field_13987.method_14364(new class_2626(blockPos, this.field_14007.method_8320(blockPos)));
      ci.cancel();
   }

   @Inject(
      method = "destroyBlock(Lnet/minecraft/core/BlockPos;)Z",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/item/ItemStack;mineBlock(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;)V"
      )
   )
   public void destroyBlock(class_2338 blockPos, CallbackInfoReturnable<Boolean> cir) {
      class_1799 itemStack = this.field_14008.method_6047();
      if (itemStack.method_7936() - itemStack.method_7919() < 3) {
         class_1799 copy = itemStack.method_7972();
         TaskFunctions.enqueueTask(
            this.field_14008.method_5770(),
            () -> {
               if (itemStack.method_7960() && !copy.method_7960()) {
                  ((CollectiveItemEvents.Item_Destroyed)CollectiveItemEvents.ON_ITEM_DESTROYED.invoker())
                     .onItemDestroyed(this.field_14008, copy, class_1268.field_5808);
               }
            },
            0
         );
      }
   }
}
