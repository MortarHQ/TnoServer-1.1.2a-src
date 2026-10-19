package com.github.b4ndithelps.tennogamenolife.mixin.client;

import com.github.b4ndithelps.tennogamenolife.registry.item.Tenreincarnationitems;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class SickleGuiItemRendererMixin {
   private static final ModelResourceLocation SICKLE_GUI_MODEL = ModelResourceLocation.standalone(
      ResourceLocation.fromNamespaceAndPath("tennogamenolife", "item/sickle_gui")
   );
   @Shadow
   @Final
   private ItemModelShaper itemModelShaper;

   @ModifyVariable(
      method = "render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V",
      at = @At("HEAD"),
      argsOnly = true,
      index = 8
   )
   private BakedModel tennogamenolife$useSickleIcon(
      BakedModel originalModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ItemDisplayContext context
   ) {
      return stack.is((Item)Tenreincarnationitems.SICKLE.get())
            && (context == ItemDisplayContext.GUI || context == ItemDisplayContext.GROUND || context == ItemDisplayContext.FIXED)
         ? this.itemModelShaper.getModelManager().getModel(SICKLE_GUI_MODEL)
         : originalModel;
   }
}
