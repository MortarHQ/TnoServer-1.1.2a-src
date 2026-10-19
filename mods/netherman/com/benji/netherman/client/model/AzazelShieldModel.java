package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.item.AzazelShieldItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AzazelShieldModel extends GeoModel<AzazelShieldItem> {
   public ResourceLocation getModelResource(AzazelShieldItem animatable) {
      return NetherExp.location("geo/item/azazel_shield.geo.json");
   }

   public ResourceLocation getTextureResource(AzazelShieldItem animatable) {
      return NetherExp.location("textures/item/azazel_shield.png");
   }

   public ResourceLocation getAnimationResource(AzazelShieldItem animatable) {
      return NetherExp.location("animations/item/azazel_shield.animation.json");
   }
}
