package com.natamus.collective.fabric.mixin;

import com.natamus.collective_common_fabric.globalcallbacks.GlobalCropCallback;
import net.minecraft.class_1269;
import net.minecraft.class_1752;
import net.minecraft.class_1799;
import net.minecraft.class_1838;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = class_1752.class, priority = 1001)
public class BoneMealItemMixin {
   @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
   public void BoneMealItem_useOn(class_1838 useOnContext, CallbackInfoReturnable<class_1269> ci) {
      class_1937 level = useOnContext.method_8045();
      class_2338 blockPos = useOnContext.method_8037();
      if (!GlobalCropCallback.ON_BONE_MEAL_APPLY
         .invoker()
         .onBoneMealApply(useOnContext.method_8036(), level, blockPos, level.method_8320(blockPos), useOnContext.method_8041())) {
         ci.setReturnValue(class_1269.field_5811);
      }
   }

   @Inject(method = "growCrop(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z", at = @At("HEAD"))
   private static void growCrop(class_1799 itemStack, class_1937 level, class_2338 blockPos, CallbackInfoReturnable<Boolean> cir) {
      GlobalCropCallback.ON_GENERAL_BONE_MEAL_APPLY.invoker().onGeneralBoneMealApply(level, blockPos, level.method_8320(blockPos), itemStack);
   }
}
