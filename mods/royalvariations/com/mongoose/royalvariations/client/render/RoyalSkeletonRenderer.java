package com.mongoose.royalvariations.client.render;

import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.client.render.model.RoyalSkeletonModel;
import com.mongoose.royalvariations.common.entities.hostile.RoyalSkeleton;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class RoyalSkeletonRenderer extends MobRenderer<RoyalSkeleton, RoyalSkeletonModel<RoyalSkeleton>> {
   private static final ResourceLocation TEXTURE = RoyalVariations.location("textures/entity/royal_skeleton.png");

   public RoyalSkeletonRenderer(Context p_174008_) {
      super(p_174008_, new RoyalSkeletonModel(p_174008_.bakeLayer(RVModelLayer.ROYAL_SKELETON)), 0.5F);
   }

   public ResourceLocation getTextureLocation(RoyalSkeleton p_114482_) {
      return TEXTURE;
   }
}
