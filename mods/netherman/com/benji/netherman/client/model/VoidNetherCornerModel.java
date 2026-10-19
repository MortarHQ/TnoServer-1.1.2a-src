package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.entity.VoidNetherCornerBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class VoidNetherCornerModel extends GeoModel<VoidNetherCornerBlockEntity> {
   private static final ResourceLocation MODEL = NetherExp.location("geo/voidnether_corner.geo.json");
   private static final ResourceLocation TEXTURE = NetherExp.location("textures/block/void_nether.png");
   private static final ResourceLocation ANIMATION = NetherExp.location("animations/voidnether_corner.animation.json");

   public ResourceLocation getModelResource(VoidNetherCornerBlockEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(VoidNetherCornerBlockEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(VoidNetherCornerBlockEntity animatable) {
      return ANIMATION;
   }
}
