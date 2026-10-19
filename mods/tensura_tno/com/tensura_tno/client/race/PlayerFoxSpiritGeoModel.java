package com.tensura_tno.client.race;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public final class PlayerFoxSpiritGeoModel extends GeoModel<PlayerFoxSpiritAnimatable> {
   private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath("tensura_tno", "geo/entity/player_fox_spirit.geo.json");
   private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath("tensura_tno", "animations/entity/player_fox_spirit.animation.json");
   private static final ResourceLocation PRONE_ANIMATION = ResourceLocation.fromNamespaceAndPath(
      "tensura_tno", "animations/entity/player_fox_spirit_prone.animation.json"
   );
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_tno", "textures/entity/player_fox_spirit.png");
   private static final String[] DISABLED_TAILS = new String[]{
      "tail_01_base", "tail_02_base", "tail_03_base", "tail_04_base", "tail_06_base", "tail_07_base", "tail_08_base", "tail_09_base"
   };

   public ResourceLocation getModelResource(PlayerFoxSpiritAnimatable animatable) {
      return GEO;
   }

   public ResourceLocation getTextureResource(PlayerFoxSpiritAnimatable animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(PlayerFoxSpiritAnimatable animatable) {
      return animatable.getPlayer() != null && PlayerFoxSpiritAnimatable.shouldProne(animatable.getPlayer()) ? PRONE_ANIMATION : ANIMATION;
   }

   public RenderType getRenderType(PlayerFoxSpiritAnimatable animatable, ResourceLocation texture) {
      return RenderType.entityCutoutNoCull(texture, false);
   }

   public void setCustomAnimations(PlayerFoxSpiritAnimatable animatable, long instanceId, AnimationState<PlayerFoxSpiritAnimatable> animationState) {
      super.setCustomAnimations(animatable, instanceId, animationState);

      for (String boneName : DISABLED_TAILS) {
         GeoBone bone = this.getAnimationProcessor().getBone(boneName);
         if (bone != null) {
            bone.setHidden(true);
         }
      }

      GeoBone fifthTail = this.getAnimationProcessor().getBone("tail_05_base");
      if (fifthTail != null) {
         fifthTail.setHidden(false);
      }
   }
}
