package me.decce.gnetum.mixins.compat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.entity.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ItemRenderer.class, priority = 2000)
public class ItemRendererMixin {
   @ModifyExpressionValue(
      method = "renderQuadList",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/color/item/ItemColors;getColor(Lnet/minecraft/world/item/ItemStack;I)I")
   )
   private int gnetum$getColor$fixAlpha(int original) {
      return original | 0xFF000000;
   }

   @ModifyExpressionValue(
      method = "renderBakedItemQuads",
      remap = false,
      require = 0,
      expect = 0,
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/color/item/ItemColors;getColor(Lnet/minecraft/world/item/ItemStack;I)I")
   )
   private int gnetum$getColor$fixAlpha$sodium(int original) {
      return original | 0xFF000000;
   }
}
