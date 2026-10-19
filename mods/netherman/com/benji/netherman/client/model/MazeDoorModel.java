package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.entity.MazeDoorBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MazeDoorModel extends GeoModel<MazeDoorBlockEntity> {
   private static final ResourceLocation MODEL = NetherExp.location("geo/maze_door.geo.json");
   private static final ResourceLocation TEXTURE = NetherExp.location("textures/block/maze_door.png");
   private static final ResourceLocation ANIMATION = NetherExp.location("animations/maze_door.animation.json");

   public ResourceLocation getModelResource(MazeDoorBlockEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(MazeDoorBlockEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(MazeDoorBlockEntity animatable) {
      return ANIMATION;
   }
}
