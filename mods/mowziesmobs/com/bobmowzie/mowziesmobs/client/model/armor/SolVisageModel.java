package com.bobmowzie.mowziesmobs.client.model.armor;

import com.bobmowzie.mowziesmobs.client.render.entity.RenderUmvuthi;
import com.bobmowzie.mowziesmobs.server.item.ItemSolVisage;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SolVisageModel extends GeoModel<ItemSolVisage> {
   public ResourceLocation getModelResource(ItemSolVisage object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "geo/sol_visage.geo.json");
   }

   public ResourceLocation getTextureResource(ItemSolVisage object) {
      return RenderUmvuthi.TEXTURE;
   }

   public ResourceLocation getAnimationResource(ItemSolVisage animatable) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "animations/sol_visage.animation.json");
   }

   public RenderType getRenderType(ItemSolVisage animatable, ResourceLocation texture) {
      return RenderType.entityCutoutNoCull(texture);
   }
}
