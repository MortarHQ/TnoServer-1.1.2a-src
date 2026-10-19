package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.magic.field.BlueFlare;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class BlueFlareModel extends DefaultedEntityGeoModel<BlueFlare> {
   public BlueFlareModel() {
      super(ResourceLocation.fromNamespaceAndPath("tensura", "hell_flare"), false);
   }

   protected String subtype() {
      return "entity/misc";
   }

   public ResourceLocation getTextureResource(BlueFlare instance) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/field/blueflare/blueflare.png");
   }

   public RenderType getRenderType(BlueFlare animatable, ResourceLocation texture) {
      return RenderType.entityTranslucent(texture);
   }

   public void setCustomAnimations(BlueFlare animatable, long instanceId, AnimationState<BlueFlare> animationState) {
      GeoBone backfire = this.getAnimationProcessor().getBone("smoke_sphere_2");
      backfire.setHidden(true);
      super.setCustomAnimations(animatable, instanceId, animationState);
   }
}
