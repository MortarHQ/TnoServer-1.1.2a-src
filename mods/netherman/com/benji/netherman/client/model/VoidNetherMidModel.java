package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.entity.VoidNetherMidBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class VoidNetherMidModel extends GeoModel<VoidNetherMidBlockEntity> {
   private static final ResourceLocation MODEL = NetherExp.location("geo/voidnether_mid.geo.json");
   private static final ResourceLocation TEXTURE = NetherExp.location("textures/block/void_nether.png");
   private static final ResourceLocation ANIMATION = NetherExp.location("animations/voidnether_mid.animation.json");

   public ResourceLocation getModelResource(VoidNetherMidBlockEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(VoidNetherMidBlockEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(VoidNetherMidBlockEntity animatable) {
      return ANIMATION;
   }
}
