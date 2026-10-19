package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.item.AzazelSpearItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AzazelSpearModel extends GeoModel<AzazelSpearItem> {
   public ResourceLocation getModelResource(AzazelSpearItem animatable) {
      return NetherExp.location("geo/item/azazel_spear.geo.json");
   }

   public ResourceLocation getTextureResource(AzazelSpearItem animatable) {
      return NetherExp.location("textures/item/azazel_spear.png");
   }

   public ResourceLocation getAnimationResource(AzazelSpearItem animatable) {
      return NetherExp.location("animations/item/azazel_spear.animation.json");
   }
}
