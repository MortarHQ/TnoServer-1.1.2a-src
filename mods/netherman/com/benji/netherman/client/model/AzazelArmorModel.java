package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.item.AzazelArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AzazelArmorModel extends GeoModel<AzazelArmorItem> {
   public ResourceLocation getModelResource(AzazelArmorItem animatable) {
      return NetherExp.location("geo/azazel_armor.geo.json");
   }

   public ResourceLocation getTextureResource(AzazelArmorItem animatable) {
      return NetherExp.location("textures/models/armor/believer_armor.png");
   }

   public ResourceLocation getAnimationResource(AzazelArmorItem animatable) {
      return NetherExp.location("animations/azazel_armor.animation.json");
   }
}
