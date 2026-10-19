package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelFrostmaw;
import com.bobmowzie.mowziesmobs.client.render.MowzieRenderUtils;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.ItemLayer;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrostmaw;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemIceCrystal;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

public class RenderFrostmaw extends MobRenderer<EntityFrostmaw, ModelFrostmaw<EntityFrostmaw>> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/entity/frostmaw.png");

   public RenderFrostmaw(Context mgr) {
      super(mgr, new ModelFrostmaw(), 3.5F);
      this.addLayer(
         new ItemLayer(
            this,
            ((ModelFrostmaw)this.getModel()).iceCrystalHand,
            ((ItemIceCrystal)ItemHandler.ICE_CRYSTAL.get()).getDefaultInstance(),
            ItemDisplayContext.GROUND
         )
      );
      this.addLayer(
         new ItemLayer(
            this, ((ModelFrostmaw)this.getModel()).iceCrystal, ((ItemIceCrystal)ItemHandler.ICE_CRYSTAL.get()).getDefaultInstance(), ItemDisplayContext.GROUND
         )
      );
   }

   protected float getFlipDegrees(EntityFrostmaw entity) {
      return 0.0F;
   }

   public ResourceLocation getTextureLocation(EntityFrostmaw entity) {
      return TEXTURE;
   }

   public void render(EntityFrostmaw entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      if (entity.getAnimation() == EntityFrostmaw.SWIPE_ANIMATION
         || entity.getAnimation() == EntityFrostmaw.SWIPE_TWICE_ANIMATION
         || entity.getAnimation() == EntityFrostmaw.ICE_BREATH_ANIMATION
         || entity.getAnimation() == EntityFrostmaw.ICE_BALL_ANIMATION
         || !entity.getActive()) {
         Vec3 rightHandPos = MowzieRenderUtils.getWorldPosFromModel(entity, entityYaw, ((ModelFrostmaw)this.model).rightHandSocket);
         Vec3 leftHandPos = MowzieRenderUtils.getWorldPosFromModel(entity, entityYaw, ((ModelFrostmaw)this.model).leftHandSocket);
         Vec3 mouthPos = MowzieRenderUtils.getWorldPosFromModel(entity, entityYaw, ((ModelFrostmaw)this.model).mouthSocket);
         entity.setSocketPosArray(0, rightHandPos);
         entity.setSocketPosArray(1, leftHandPos);
         entity.setSocketPosArray(2, mouthPos);
      }
   }
}
