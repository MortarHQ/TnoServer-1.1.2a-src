package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.entity.PointedBlackstoneBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import software.bernie.geckolib.model.GeoModel;

public class PointedBlackstoneModel extends GeoModel<PointedBlackstoneBlockEntity> {
   public ResourceLocation getModelResource(PointedBlackstoneBlockEntity animatable) {
      return NetherExp.location("geo/pointed_blackstone.geo.json");
   }

   public ResourceLocation getTextureResource(PointedBlackstoneBlockEntity animatable) {
      BlockState state = animatable.getBlockState();
      if (state.hasProperty(PointedDripstoneBlock.TIP_DIRECTION) && state.hasProperty(PointedDripstoneBlock.THICKNESS)) {
         Direction dir = (Direction)state.getValue(PointedDripstoneBlock.TIP_DIRECTION);
         DripstoneThickness thickness = (DripstoneThickness)state.getValue(PointedDripstoneBlock.THICKNESS);
         String dirStr = dir == Direction.DOWN ? "down" : "up";
         String thickStr = thickness.getSerializedName();
         if (thickStr.equals("merge")) {
            thickStr = "tip_merge";
         }

         return NetherExp.location("textures/block/pointed_blackstone_" + dirStr + "_" + thickStr + ".png");
      } else {
         return NetherExp.location("textures/block/pointed_blackstone_down_base.png");
      }
   }

   public ResourceLocation getAnimationResource(PointedBlackstoneBlockEntity animatable) {
      return NetherExp.location("animations/empty.animation.json");
   }
}
