package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveItemEvents;
import net.minecraft.class_1268;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = class_1799.class, priority = 1001)
public abstract class ItemStackMixin {
   @Unique
   private static final ThreadLocal<class_1799> COLLECTIVE$PROCESSED_STACK = new ThreadLocal<>();

   @Inject(
      method = "finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;",
      at = @At("HEAD")
   )
   public void finishUsingItemA(class_1937 level, class_1309 livingEntity, CallbackInfoReturnable<class_1799> cir) {
      if (livingEntity instanceof class_1657) {
         class_1799 itemStack = (class_1799)this;
         COLLECTIVE$PROCESSED_STACK.set(itemStack.method_7972());
      }
   }

   @Inject(
      method = "finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;",
      at = @At("RETURN"),
      cancellable = true
   )
   public void finishUsingItemB(class_1937 level, class_1309 livingEntity, CallbackInfoReturnable<class_1799> cir) {
      if (livingEntity instanceof class_1657) {
         class_1268 hand = livingEntity.method_6058();
         class_1799 copyStack = COLLECTIVE$PROCESSED_STACK.get();
         class_1799 newStack = (class_1799)cir.getReturnValue();
         class_1799 changedStack = ((CollectiveItemEvents.Item_Use_Finished)CollectiveItemEvents.ON_ITEM_USE_FINISHED.invoker())
            .onItemUsedFinished((class_1657)livingEntity, copyStack, newStack, hand);
         if (changedStack != null) {
            cir.setReturnValue(changedStack);
         }
      }
   }
}
