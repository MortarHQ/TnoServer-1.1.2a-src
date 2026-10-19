package com.bobmowzie.mowziesmobs.client.model.item;

import com.bobmowzie.mowziesmobs.server.item.ItemEarthrendGauntlet;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ModelEarthrendGauntlet extends GeoModel<ItemEarthrendGauntlet> {
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/item/earthrend_gauntlet.png");

   public ResourceLocation getModelResource(ItemEarthrendGauntlet object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "geo/earthrend_gauntlet.geo.json");
   }

   public ResourceLocation getTextureResource(ItemEarthrendGauntlet object) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(ItemEarthrendGauntlet animatable) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "animations/earthrend_gauntlet.animation.json");
   }
}
