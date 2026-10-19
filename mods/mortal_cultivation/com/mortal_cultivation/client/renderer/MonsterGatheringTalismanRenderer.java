package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mortal_cultivation.client.model.MonsterGatheringTalismanModel;
import com.mortal_cultivation.entity.MonsterGatheringTalismanEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class MonsterGatheringTalismanRenderer extends EntityRenderer<MonsterGatheringTalismanEntity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/entity/monster_gathering_talisman.png");
   private final MonsterGatheringTalismanModel model;

   public MonsterGatheringTalismanRenderer(Context context) {
      super(context);
      this.model = new MonsterGatheringTalismanModel(context.bakeLayer(MonsterGatheringTalismanModel.LAYER_LOCATION));
   }

   public void render(
      MonsterGatheringTalismanEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight
   ) {
      poseStack.pushPose();
      this.model.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTicks, 0.0F, 0.0F);
      this.model
         .renderToBuffer(poseStack, bufferSource.getBuffer(this.model.renderType(this.getTextureLocation(entity))), packedLight, OverlayTexture.NO_OVERLAY, -1);
      poseStack.popPose();
      super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
   }

   public ResourceLocation getTextureLocation(MonsterGatheringTalismanEntity entity) {
      return TEXTURE;
   }
}
