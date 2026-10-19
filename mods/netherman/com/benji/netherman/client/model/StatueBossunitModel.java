package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.StatueBossunitEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class StatueBossunitModel extends GeoModel<StatueBossunitEntity> {
   public ResourceLocation getModelResource(StatueBossunitEntity animatable) {
      return NetherExp.location("geo/statue_bossunit.geo.json");
   }

   public ResourceLocation getTextureResource(StatueBossunitEntity animatable) {
      return NetherExp.location("textures/entity/statue_bossunit.png");
   }

   public ResourceLocation getAnimationResource(StatueBossunitEntity animatable) {
      return NetherExp.location("animations/statue_bossunit.animation.json");
   }
}
