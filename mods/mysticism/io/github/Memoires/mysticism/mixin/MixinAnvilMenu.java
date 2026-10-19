package io.github.Memoires.mysticism.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.Memoires.mysticism.registry.component.MysticismDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilMenu.class)
public abstract class MixinAnvilMenu {
   @ModifyExpressionValue(
      method = "createResult()V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I"),
      require = 0
   )
   private int mysticism$allowEnhancedEnchantmentLevel(int original) {
      AnvilMenu menu = (AnvilMenu)this;
      ItemStack firstSlot = menu.getSlot(0).getItem();
      ItemStack secondSlot = menu.getSlot(1).getItem();
      if (!firstSlot.isEmpty() && !secondSlot.isEmpty()) {
         ItemStack book = secondSlot.is(Items.ENCHANTED_BOOK) ? secondSlot : (firstSlot.is(Items.ENCHANTED_BOOK) ? firstSlot : ItemStack.EMPTY);
         if (book.isEmpty()) {
            return original;
         }

         ResourceLocation enhancedEnchantment = (ResourceLocation)book.get((DataComponentType)MysticismDataComponents.ENHANCED_ENCHANTMENT.get());
         return enhancedEnchantment == null ? original : Integer.MAX_VALUE;
      } else {
         return original;
      }
   }
}
