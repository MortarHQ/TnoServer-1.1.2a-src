package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.entity.VoidNetherMidCornerBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class VoidNetherMidCornerModel extends GeoModel<VoidNetherMidCornerBlockEntity> {
   private static final ResourceLocation MODEL = NetherExp.location("geo/voidnether_midcorner.geo.json");
   private static final ResourceLocation TEXTURE = NetherExp.location("textures/block/void_nether.png");
   private static final ResourceLocation ANIMATION = NetherExp.location("animations/voidnether_midcorner.animation.json");

   public ResourceLocation getModelResource(VoidNetherMidCornerBlockEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(VoidNetherMidCornerBlockEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(VoidNetherMidCornerBlockEntity animatable) {
      return ANIMATION;
   }
}
