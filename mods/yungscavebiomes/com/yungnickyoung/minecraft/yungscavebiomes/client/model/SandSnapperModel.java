package com.yungnickyoung.minecraft.yungscavebiomes.client.model;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SandSnapperModel extends GeoModel<SandSnapperEntity> {
   private static final ResourceLocation MODEL = YungsCaveBiomesCommon.id("geo/sand_snapper/sand_snapper.geo.json");
   private static final ResourceLocation TEXTURE = YungsCaveBiomesCommon.id("textures/entity/sand_snapper/sand_snapper.png");
   private static final ResourceLocation ANIMATION = YungsCaveBiomesCommon.id("animations/sand_snapper/sand_snapper.animation.json");

   public ResourceLocation getModelResource(SandSnapperEntity sandSnapper) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(SandSnapperEntity sandSnapper) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(SandSnapperEntity sandSnapper) {
      return ANIMATION;
   }

   public void setCustomAnimations(SandSnapperEntity animatable, long instanceId, AnimationState<SandSnapperEntity> animationState) {
      super.setCustomAnimations(animatable, instanceId, animationState);
      if (animatable.isLookingAtPlayer()) {
         GeoBone head = this.getAnimationProcessor().getBone("neck");
         if (head != null) {
            EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * (float) (Math.PI / 180.0));
            head.setRotY(entityData.netHeadYaw() * (float) (Math.PI / 180.0));
         }
      }
   }
}
