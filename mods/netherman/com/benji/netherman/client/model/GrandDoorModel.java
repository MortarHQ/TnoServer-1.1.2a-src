package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.entity.GrandDoorBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GrandDoorModel extends GeoModel<GrandDoorBlockEntity> {
   private static final ResourceLocation MODEL = NetherExp.location("geo/grand_door.geo.json");
   private static final ResourceLocation TEXTURE = NetherExp.location("textures/block/grand_door.png");
   private static final ResourceLocation ANIMATION = NetherExp.location("animations/grand_door.animation.json");

   public ResourceLocation getModelResource(GrandDoorBlockEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(GrandDoorBlockEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(GrandDoorBlockEntity animatable) {
      return ANIMATION;
   }
}
