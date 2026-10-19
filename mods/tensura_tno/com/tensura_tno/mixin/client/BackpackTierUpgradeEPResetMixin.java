package com.tensura_tno.mixin.client;

import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.p3pp3rf1y.sophisticatedbackpacks.crafting.BackpackUpgradeRecipe", remap = false)
public class BackpackTierUpgradeEPResetMixin {
   @Inject(method = "assemble", at = @At("RETURN"), remap = false, require = 0)
   private void tensura_tno$resetEPOnTierUpgrade(CraftingInput inv, Provider registries, CallbackInfoReturnable<ItemStack> cir) {
      ItemStack result = (ItemStack)cir.getReturnValue();
      if (result != null && !result.isEmpty()) {
         result.remove((DataComponentType)TensuraDataComponents.EP.get());
         result.remove((DataComponentType)TensuraDataComponents.MAX_EP.get());
         result.remove((DataComponentType)TensuraDataComponents.EP_DURABILITY.get());
         result.remove((DataComponentType)TensuraDataComponents.EP_GAIN.get());
         result.remove((DataComponentType)TensuraDataComponents.EVOLUTION.get());
         result.remove((DataComponentType)TensuraDataComponents.UNIQUE_EVOLUTIONS.get());
      }
   }
}
