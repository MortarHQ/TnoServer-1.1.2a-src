package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveBlockEvents;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = class_2248.class, priority = 1001)
public class BlockMixin {
   @Inject(method = "playerDestroy", at = @At("HEAD"), cancellable = true)
   public void Block_playerDestroy(
      class_1937 level, class_1657 player, class_2338 blockPos, class_2680 blockState, class_2586 blockEntity, class_1799 itemStack, CallbackInfo ci
   ) {
      if (!((CollectiveBlockEvents.On_Block_Destroy)CollectiveBlockEvents.BLOCK_DESTROY.invoker())
         .onBlockDestroy(level, player, blockPos, blockState, blockEntity, itemStack)) {
         ci.cancel();
      }
   }
}
