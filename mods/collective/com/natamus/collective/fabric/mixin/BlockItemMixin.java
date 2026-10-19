package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveBlockEvents;
import net.minecraft.class_1269;
import net.minecraft.class_1747;
import net.minecraft.class_1750;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = class_1747.class, priority = 1001)
public class BlockItemMixin {
   @Inject(method = "place", at = @At("HEAD"), cancellable = true)
   public void Block_setPlacedBy(class_1750 context, CallbackInfoReturnable<class_1269> cir) {
      class_1937 level = context.method_8045();
      class_2338 clickedPos = context.method_8037();
      if (!((CollectiveBlockEvents.On_Block_Place)CollectiveBlockEvents.BLOCK_PLACE.invoker())
         .onBlockPlace(level, clickedPos, level.method_8320(clickedPos), context.method_8036(), context.method_8041())) {
         cir.setReturnValue(class_1269.field_5814);
      }
   }
}
