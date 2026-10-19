package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityPillar;
import net.minecraft.resources.ResourceLocation;

public class ModelPillar extends MowzieGeoModel<EntityPillar> {
   public ResourceLocation getModelResource(EntityPillar object) {
      return ResourceLocation.fromNamespaceAndPath("mowziesmobs", "geo/geomancy_pillar.geo.json");
   }

   public ResourceLocation getTextureResource(EntityPillar object) {
      return null;
   }

   public ResourceLocation getAnimationResource(EntityPillar object) {
      return null;
   }
}
