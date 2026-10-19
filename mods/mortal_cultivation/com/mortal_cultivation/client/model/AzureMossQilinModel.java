package com.mortal_cultivation.client.model;

import com.mortal_cultivation.entity.AzureMossQilinEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public final class AzureMossQilinModel extends GeoModel<AzureMossQilinEntity> {
   private static final ResourceLocation MODEL = location("geo/azure_moss_qilin.geo.json");
   private static final ResourceLocation TEXTURE = location("textures/entity/azure_moss_qilin.png");
   private static final ResourceLocation ANIMATION = location("animations/azure_moss_qilin.animation.json");

   private static ResourceLocation location(String path) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", path);
   }

   public ResourceLocation getModelResource(AzureMossQilinEntity entity) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(AzureMossQilinEntity entity) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(AzureMossQilinEntity entity) {
      return ANIMATION;
   }

   public void setCustomAnimations(AzureMossQilinEntity entity, long instanceId, AnimationState<AzureMossQilinEntity> state) {
      if (entity.getAttack() == AzureMossQilinEntity.Attack.NONE) {
         GeoBone head = this.getAnimationProcessor().getBone("head");
         EntityModelData data = (EntityModelData)state.getData(DataTickets.ENTITY_MODEL_DATA);
         if (head != null && data != null) {
            head.setRotX(head.getInitialSnapshot().getRotX() + Mth.clamp(data.headPitch(), -25.0F, 25.0F) * (float) (Math.PI / 180.0));
            head.setRotY(head.getInitialSnapshot().getRotY() + Mth.clamp(data.netHeadYaw(), -35.0F, 35.0F) * (float) (Math.PI / 180.0));
         }
      }
   }
}
