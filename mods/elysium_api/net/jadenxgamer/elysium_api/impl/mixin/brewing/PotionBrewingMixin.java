package net.jadenxgamer.elysium_api.impl.mixin.brewing;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.jadenxgamer.elysium_api.impl.core.datadriven.brewing_recipe.ElysiumBrewingRecipeHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionBrewing.class)
public abstract class PotionBrewingMixin {
   @ModifyReturnValue(method = "isIngredient", at = @At("RETURN"))
   private static boolean elysium_api$isIngredient(boolean original, ItemStack stack) {
      return ElysiumBrewingRecipeHelper.isIngredient(stack) ? true : original;
   }

   @ModifyReturnValue(method = "hasMix", at = @At("RETURN"))
   private static boolean elysium_api$hasMix(boolean original, ItemStack input, ItemStack ingredient) {
      return ElysiumBrewingRecipeHelper.hasMix(input, ingredient) ? true : original;
   }

   @ModifyReturnValue(method = "mix", at = @At("RETURN"))
   private static ItemStack elysium_api$mix(ItemStack original, ItemStack ingredient, ItemStack input) {
      ItemStack result = ElysiumBrewingRecipeHelper.mix(input, ingredient);
      return !result.isEmpty() ? result : original;
   }
}
