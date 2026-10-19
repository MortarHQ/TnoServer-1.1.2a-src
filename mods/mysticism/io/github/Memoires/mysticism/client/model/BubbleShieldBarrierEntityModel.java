package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.skill.BubbleShieldBarrierEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BubbleShieldBarrierEntityModel extends GeoModel<BubbleShieldBarrierEntity> {
   public ResourceLocation getModelResource(BubbleShieldBarrierEntity bubbleShieldBarrierEntity) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/bubble_shield.geo.json");
   }

   public ResourceLocation getTextureResource(BubbleShieldBarrierEntity bubbleShieldBarrierEntity) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/skill/bubble_shield.png");
   }

   public ResourceLocation getAnimationResource(BubbleShieldBarrierEntity bubbleShieldBarrierEntity) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "animations/bubble_shield.animation.json");
   }
}
