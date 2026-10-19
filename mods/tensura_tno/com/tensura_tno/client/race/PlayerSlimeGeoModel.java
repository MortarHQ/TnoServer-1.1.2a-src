package com.tensura_tno.client.race;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public final class PlayerSlimeGeoModel extends GeoModel<PlayerSlimeAnimatable> {
   private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath("tensura", "geo/entity/slime.geo.json");
   private static final ResourceLocation ANIM = ResourceLocation.fromNamespaceAndPath("tensura", "animations/entity/slime.animation.json");
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura", "textures/entity/slime/slime.png");
   private static final String[] HIDDEN_BONES = new String[]{"Saddle", "Chest", "SantaHat", "HalloweenSkull", "HeadArmor"};

   public ResourceLocation getModelResource(PlayerSlimeAnimatable animatable) {
      return GEO;
   }

   public ResourceLocation getTextureResource(PlayerSlimeAnimatable animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(PlayerSlimeAnimatable animatable) {
      return ANIM;
   }

   public RenderType getRenderType(PlayerSlimeAnimatable animatable, ResourceLocation texture) {
      return RenderType.entityTranslucent(texture);
   }

   public void setCustomAnimations(PlayerSlimeAnimatable animatable, long instanceId, AnimationState<PlayerSlimeAnimatable> animationState) {
      super.setCustomAnimations(animatable, instanceId, animationState);

      for (String name : HIDDEN_BONES) {
         GeoBone bone = this.getAnimationProcessor().getBone(name);
         if (bone != null && !bone.isHidden()) {
            bone.setHidden(true);
         }
      }

      if (animatable.getPlayer() != null && PlayerSlimeAnimatable.shouldKeepDefaultShape(animatable.getPlayer())) {
         GeoBone slime = this.getAnimationProcessor().getBone("slime");
         if (slime != null) {
            slime.updateScale(1.0F, 1.0F, 1.0F);
         }
      }
   }
}
