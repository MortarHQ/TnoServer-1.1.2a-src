package com.github.b4ndithelps.tennogamenolife.client.model;

import com.github.b4ndithelps.tennogamenolife.entity.DragonRoarCircle;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DragonRoarCircleModel extends GeoModel<DragonRoarCircle> {
   private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "geo/pseudo_dragon_roar.geo.json");
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/entity/pseudo_dragon_roar.png");
   private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "animations/pseudo_dragon_roar.animation.json");
   private static final ResourceLocation GODLY_SMITE_CHARGE_MODEL = ResourceLocation.fromNamespaceAndPath(
      "tennogamenolife", "geo/godly_smite_charge_circle.geo.json"
   );
   private static final ResourceLocation GODLY_SMITE_CHARGE_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "tennogamenolife", "textures/entity/godly_smite_charge_circle.png"
   );
   private static final ResourceLocation GODLY_SMITE_CHARGE_ANIMATION = ResourceLocation.fromNamespaceAndPath(
      "tennogamenolife", "animations/godly_smite_charge_circle.animation.json"
   );

   public ResourceLocation getModelResource(DragonRoarCircle animatable) {
      return animatable.isGodlySmiteChargeVfx() ? GODLY_SMITE_CHARGE_MODEL : MODEL;
   }

   public ResourceLocation getTextureResource(DragonRoarCircle animatable) {
      return animatable.isGodlySmiteChargeVfx() ? GODLY_SMITE_CHARGE_TEXTURE : TEXTURE;
   }

   public ResourceLocation getAnimationResource(DragonRoarCircle animatable) {
      return animatable.isGodlySmiteChargeVfx() ? GODLY_SMITE_CHARGE_ANIMATION : ANIMATION;
   }
}
