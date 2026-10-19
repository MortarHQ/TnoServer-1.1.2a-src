package com.natamus.collective.fabric.mixin.crop;

import com.natamus.collective.fabric.callbacks.CollectiveCropEvents;
import net.minecraft.class_2338;
import net.minecraft.class_2421;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_5819;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = class_2421.class, priority = 1001)
public class NetherWartBlockMixin {
   @Inject(method = "randomTick", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"), cancellable = true)
   public void NetherWartBlock_randomTick(class_2680 blockState, class_3218 serverLevel, class_2338 blockPos, class_5819 randomSource, CallbackInfo ci) {
      if (!((CollectiveCropEvents.On_Pre_Crop_Grow)CollectiveCropEvents.PRE_CROP_GROW.invoker()).onPreCropGrow(serverLevel, blockPos, blockState)) {
         ci.cancel();
      }
   }
}
