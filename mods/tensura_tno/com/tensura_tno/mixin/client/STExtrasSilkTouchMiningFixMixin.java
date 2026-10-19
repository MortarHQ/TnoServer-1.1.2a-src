package com.tensura_tno.mixin.client;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "org.crypticdev.stextras.handler.HandlePrestigeListeners", remap = false)
public class STExtrasSilkTouchMiningFixMixin {
   @Redirect(
      method = "isSilkTouchLikeEffect",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/item/ItemStack;remove(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;",
         remap = true
      ),
      require = 0,
      remap = false
   )
   private static Object tensuraTno$keepEmptyEnchantments(ItemStack stack, DataComponentType<?> ignoredComponent) {
      return stack.set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
   }
}
