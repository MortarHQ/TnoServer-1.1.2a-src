package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.entity.TraphiveBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TraphiveModel extends GeoModel<TraphiveBlockEntity> {
   private static final ResourceLocation MODEL = NetherExp.location("geo/traphive.geo.json");
   private static final ResourceLocation TEXTURE = NetherExp.location("textures/block/traphive.png");
   private static final ResourceLocation ANIMATION = NetherExp.location("animations/traphive.animation.json");

   public ResourceLocation getModelResource(TraphiveBlockEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(TraphiveBlockEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(TraphiveBlockEntity animatable) {
      return ANIMATION;
   }
}
