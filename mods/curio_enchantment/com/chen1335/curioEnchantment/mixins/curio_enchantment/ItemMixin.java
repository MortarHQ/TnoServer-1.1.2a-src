package com.chen1335.curioEnchantment.mixins.curio_enchantment;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Item.class)
public abstract class ItemMixin {
   @Shadow
   public abstract ItemStack getDefaultInstance();

   @ModifyReturnValue(method = "getEnchantmentValue", at = @At("RETURN"))
   private int getEnchantmentValue(int original) {
      return original <= 0 && !CuriosApi.getItemStackSlots(this.getDefaultInstance(), false).isEmpty() ? 10 : original;
   }

   @Inject(method = "isEnchantable", at = @At("RETURN"), cancellable = true)
   private void isEnchantable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
      if (!(Boolean)cir.getReturnValue() && !CuriosApi.getItemStackSlots(stack, false).isEmpty()) {
         cir.setReturnValue(true);
      }
   }
}
