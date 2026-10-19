package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.FloqModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.FloqEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FloqRenderer extends MobRenderer<FloqEntity, FloqModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/floq.png");

   public FloqRenderer(Context context) {
      super(context, new FloqModel(context.bakeLayer(FloqModel.LAYER_LOCATION)), 0.25F);
   }

   public ResourceLocation getTextureLocation(FloqEntity entity) {
      return TEXTURE;
   }

   protected void scale(FloqEntity entity, PoseStack poseStack, float partialTick) {
      float squish = Mth.lerp(partialTick, entity.oSquish, entity.squish);
      float yScale = squish * 0.5F + 1.0F;
      float xzScale = 1.0F / yScale;
      poseStack.scale(xzScale, yScale, xzScale);
      if (entity.onGround()) {
         float modelCenterHeight = 0.25F;
         float yOffset = modelCenterHeight * (1.0F / yScale - 1.0F);
         poseStack.translate(0.0F, yOffset, 0.0F);
      }
   }
}
