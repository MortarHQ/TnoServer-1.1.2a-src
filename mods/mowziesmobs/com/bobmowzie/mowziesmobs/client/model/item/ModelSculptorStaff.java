package com.bobmowzie.mowziesmobs.client.model.item;

import com.bobmowzie.mowziesmobs.server.item.ItemSculptorStaff;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ModelSculptorStaff extends GeoModel<ItemSculptorStaff> {
   public ResourceLocation getModelResource(ItemSculptorStaff object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "geo/sculptor_staff.geo.json");
   }

   public ResourceLocation getTextureResource(ItemSculptorStaff object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/item/sculptor_staff.png");
   }

   public ResourceLocation getAnimationResource(ItemSculptorStaff animatable) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "animations/sculptor.animation.json");
   }
}
