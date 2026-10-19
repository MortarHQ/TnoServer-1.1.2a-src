package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveFurnaceEvents;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2609;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = class_2609.class, priority = 1001)
public class AbstractFurnaceBlockEntityMixin {
   @Inject(method = "getBurnDuration", at = @At("HEAD"), cancellable = true)
   public void AbstractFurnaceBlockEntity_getBurnDuration(class_1799 itemStack, CallbackInfoReturnable<Integer> ci) {
      if (!itemStack.method_7960()) {
         class_1792 item = itemStack.method_7909();
         int burntime = class_2609.method_11196().getOrDefault(item, 0);
         int newburntime = ((CollectiveFurnaceEvents.Calculate_Furnace_Burn_Time)CollectiveFurnaceEvents.CALCULATE_FURNACE_BURN_TIME.invoker())
            .getFurnaceBurnTime(itemStack, burntime);
         if (burntime != newburntime) {
            ci.setReturnValue(newburntime);
         }
      }
   }
}
