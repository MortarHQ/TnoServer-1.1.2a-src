package com.bobmowzie.mowziesmobs.client.model.armor;

import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class UmvuthanaMaskModel extends GeoModel<ItemUmvuthanaMask> {
   public ResourceLocation getModelResource(ItemUmvuthanaMask object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "geo/mask_" + object.getMaskType().name + ".geo.json");
   }

   public ResourceLocation getTextureResource(ItemUmvuthanaMask object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/item/umvuthana_mask_" + object.getMaskType().name + ".png");
   }

   public ResourceLocation getAnimationResource(ItemUmvuthanaMask animatable) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "animations/umvuthana_mask.animation.json");
   }

   public RenderType getRenderType(ItemUmvuthanaMask animatable, ResourceLocation texture) {
      return RenderType.entityCutoutNoCull(texture);
   }
}
