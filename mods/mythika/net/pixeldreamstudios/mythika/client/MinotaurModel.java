package net.pixeldreamstudios.mythika.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.pixeldreamstudios.mythika.Mythika;
import net.pixeldreamstudios.mythika.entity.MinotaurEntity;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MinotaurModel extends GeoModel<MinotaurEntity> {
   private static final ResourceLocation MODEL = Mythika.id("geo/entity/minotaur.geo.json");
   private static final ResourceLocation TEXTURE = Mythika.id("textures/entity/minotaur.png");
   private static final ResourceLocation ANIMATION = Mythika.id("animations/entity/minotaur.animation.json");

   public ResourceLocation getModelResource(MinotaurEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(MinotaurEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(MinotaurEntity animatable) {
      return ANIMATION;
   }

   public void setCustomAnimations(MinotaurEntity animatable, long instanceId, AnimationState<MinotaurEntity> animationState) {
      super.setCustomAnimations(animatable, instanceId, animationState);
      GeoBone head = this.getAnimationProcessor().getBone("head");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(Mth.clamp(entityData.headPitch(), -25.0F, 25.0F) * (float) (Math.PI / 180.0));
         head.setRotY(Mth.clamp(entityData.netHeadYaw(), -60.0F, 60.0F) * (float) (Math.PI / 180.0));
      }
   }
}
