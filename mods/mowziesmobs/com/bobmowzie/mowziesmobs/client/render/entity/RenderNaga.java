package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelNaga;
import com.bobmowzie.mowziesmobs.client.render.MowzieRenderUtils;
import com.bobmowzie.mowziesmobs.server.entity.naga.EntityNaga;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class RenderNaga extends MobRenderer<EntityNaga, ModelNaga<EntityNaga>> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/entity/naga.png");

   public RenderNaga(Context mgr) {
      super(mgr, new ModelNaga(), 0.0F);
   }

   protected float getFlipDegrees(EntityNaga entity) {
      return 0.0F;
   }

   public ResourceLocation getTextureLocation(EntityNaga entity) {
      return TEXTURE;
   }

   public void render(EntityNaga entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      if (entityIn.getAnimation() == EntityNaga.SPIT_ANIMATION && entityIn.mouthPos != null && entityIn.mouthPos.length > 0) {
         entityIn.mouthPos[0] = MowzieRenderUtils.getWorldPosFromModel(entityIn, entityYaw, ((ModelNaga)this.getModel()).mouthSocket);
      }
   }
}
