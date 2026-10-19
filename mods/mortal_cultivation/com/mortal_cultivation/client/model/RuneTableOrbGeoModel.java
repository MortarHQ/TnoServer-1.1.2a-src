package com.mortal_cultivation.client.model;

import com.mortal_cultivation.block.entity.RuneTableBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public final class RuneTableOrbGeoModel extends GeoModel<RuneTableBlockEntity> {
   public static final ResourceLocation BASE_ORB_TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/entity/zhao_ying_zhu.png");
   public static final ResourceLocation STORED_ORB_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "mortal_cultivation", "textures/entity/zhao_ying_zhu_stored.png"
   );

   public RenderType getRenderType(RuneTableBlockEntity animatable, ResourceLocation texture) {
      return RenderType.entityCutoutNoCull(texture, false);
   }

   public ResourceLocation getModelResource(RuneTableBlockEntity animatable) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "geo/entity/zhao_ying_zhu.geo.json");
   }

   public ResourceLocation getTextureResource(RuneTableBlockEntity animatable) {
      return STORED_ORB_TEXTURE;
   }

   public ResourceLocation getAnimationResource(RuneTableBlockEntity animatable) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "animations/entity/zhao_ying_zhu.animation.json");
   }
}
