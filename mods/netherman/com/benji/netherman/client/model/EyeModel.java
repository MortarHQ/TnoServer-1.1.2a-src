package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.entity.EyeBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EyeModel extends GeoModel<EyeBlockEntity> {
   private static final ResourceLocation MODEL = NetherExp.location("geo/eye_block.geo.json");
   private static final ResourceLocation TEXTURE = NetherExp.location("textures/block/eye_block.png");
   private static final ResourceLocation ANIMATION = NetherExp.location("animations/eye_block.animation.json");

   public ResourceLocation getModelResource(EyeBlockEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(EyeBlockEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(EyeBlockEntity animatable) {
      return ANIMATION;
   }
}
