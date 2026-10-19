package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.AzazelSpikesProjectileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AzazelSpikeProjectileModel extends GeoModel<AzazelSpikesProjectileEntity> {
   public ResourceLocation getModelResource(AzazelSpikesProjectileEntity animatable) {
      return NetherExp.location("geo/azazel_spikes_projectile.geo.json");
   }

   public ResourceLocation getTextureResource(AzazelSpikesProjectileEntity animatable) {
      return NetherExp.location("textures/entity/azazel_spikes_projectile.png");
   }

   public ResourceLocation getAnimationResource(AzazelSpikesProjectileEntity animatable) {
      return NetherExp.location("animations/empty.animation.json");
   }
}
