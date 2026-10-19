package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.item.AzazelScytheItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AzazelScytheModel extends GeoModel<AzazelScytheItem> {
   public ResourceLocation getModelResource(AzazelScytheItem animatable) {
      return NetherExp.location("geo/item/azazel_scythe.geo.json");
   }

   public ResourceLocation getTextureResource(AzazelScytheItem animatable) {
      return NetherExp.location("textures/item/azazel_spear.png");
   }

   public ResourceLocation getAnimationResource(AzazelScytheItem animatable) {
      return NetherExp.location("animations/item/azazel_scythe.animation.json");
   }
}
