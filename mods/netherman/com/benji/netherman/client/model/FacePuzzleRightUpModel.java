package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.entity.FacePuzzleBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FacePuzzleRightUpModel extends GeoModel<FacePuzzleBlockEntity> {
   public ResourceLocation getModelResource(FacePuzzleBlockEntity animatable) {
      return NetherExp.location("geo/face_puzzle_right_up.geo.json");
   }

   public ResourceLocation getTextureResource(FacePuzzleBlockEntity animatable) {
      return NetherExp.location("textures/block/face_puzzle.png");
   }

   public ResourceLocation getAnimationResource(FacePuzzleBlockEntity animatable) {
      return NetherExp.location("animations/right_up.animation.json");
   }
}
