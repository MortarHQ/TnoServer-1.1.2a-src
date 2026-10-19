package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.skill.GateEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public class GateEntityModel extends GeoModel<GateEntity> {
   public ResourceLocation getModelResource(GateEntity gateEntity) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "geo/gates.geo.json");
   }

   public ResourceLocation getTextureResource(GateEntity gateEntity) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/gates.png");
   }

   public ResourceLocation getAnimationResource(GateEntity gateEntity) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "animations/gates.animation.json");
   }

   public void setCustomAnimations(GateEntity animatable, long instanceId, AnimationState<GateEntity> animationState) {
      if (!animatable.getIsSurround()) {
         GeoBone gateBone = this.getAnimationProcessor().getBone("bone");
         if (gateBone != null) {
            LivingEntity owner = animatable.getOwner();
            float yaw = owner != null ? owner.getYRot() : animatable.getYRot();
            float pitch = owner != null ? owner.getXRot() : animatable.getXRot();
            gateBone.setRotX(-pitch * (float) (Math.PI / 180.0));
            gateBone.setRotY(-yaw * (float) (Math.PI / 180.0));
         }
      }
   }
}
