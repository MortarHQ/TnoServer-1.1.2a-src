package com.tensura_tno.mixin.client;

import com.tensura_tno.util.PondererPaperFeatureAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.nododiiiii.ponderer.blueprint.BlueprintFeature", remap = false)
public abstract class PondererBlueprintCarrierGuardMixin {
   @Inject(method = "matchesCarrierStack(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
   private static void tensuraTno$gateByAccess(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
      if (!PondererPaperFeatureAccess.isEnabled()) {
         if (stack != null && !stack.isEmpty()) {
            Item item = stack.getItem();
            if (item == null) {
               cir.setReturnValue(false);
            } else {
               ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
               if (id == null || !"ponderer".equals(id.getNamespace()) || !"blueprint".equals(id.getPath())) {
                  cir.setReturnValue(false);
               }
            }
         }
      }
   }
}
