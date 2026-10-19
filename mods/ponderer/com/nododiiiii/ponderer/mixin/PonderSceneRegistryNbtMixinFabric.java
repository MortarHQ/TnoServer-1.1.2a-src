package com.nododiiiii.ponderer.mixin;

import com.nododiiiii.ponderer.ponder.NbtSceneFilter;
import java.util.List;
import net.createmod.ponder.foundation.PonderScene;
import net.createmod.ponder.foundation.registration.PonderSceneRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PonderSceneRegistry.class)
public class PonderSceneRegistryNbtMixinFabric {
   @Inject(method = "compile(Lnet/minecraft/class_2960;)Ljava/util/List;", at = @At("RETURN"), cancellable = true, remap = false, require = 0)
   private void ponderer$filterByNbtFabric(ResourceLocation id, CallbackInfoReturnable<List<PonderScene>> cir) {
      ItemStack stack = NbtSceneFilter.getCurrentStack();
      if (stack != null && !stack.isEmpty()) {
         List<PonderScene> original = (List<PonderScene>)cir.getReturnValue();
         if (original != null && !original.isEmpty()) {
            List<PonderScene> filtered = NbtSceneFilter.filter(stack, original);
            if (filtered != original) {
               cir.setReturnValue(filtered);
            }
         }
      }
   }
}
