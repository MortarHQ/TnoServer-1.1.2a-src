package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.DoctorEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DoctorModel extends GeoModel<DoctorEntity> {
   public ResourceLocation getModelResource(DoctorEntity animatable) {
      return NetherExp.location("geo/doctor.geo.json");
   }

   public ResourceLocation getTextureResource(DoctorEntity animatable) {
      return NetherExp.location("textures/entity/doctor.png");
   }

   public ResourceLocation getAnimationResource(DoctorEntity animatable) {
      return NetherExp.location("animations/doctor.animation.json");
   }
}
