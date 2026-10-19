package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelEarthSpike;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.GeckoBlockLayer;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityEarthSpike;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.GeoCube;

public class RenderEarthSpike extends RenderGeomancyBase<EntityEarthSpike> {
   private static final ResourceLocation TEXTURE_DIRT = ResourceLocation.withDefaultNamespace("textures/block/dirt.png");

   public RenderEarthSpike(Context mgr) {
      super(mgr, new ModelEarthSpike());
      this.addRenderLayer(new GeckoBlockLayer(this, (bone, animatable) -> bone.getName().contains("block") ? animatable.getBlock() : null));
   }

   public ResourceLocation getTextureLocation(EntityEarthSpike entity) {
      return TEXTURE_DIRT;
   }

   public void render(EntityEarthSpike entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }

   protected void applyRotations(EntityEarthSpike animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
      float newRotationYaw = Mth.rotLerp(partialTick, animatable.yRotO, animatable.getYRot());
      super.applyRotations(animatable, poseStack, ageInTicks, newRotationYaw, partialTick, nativeScale);
   }

   public void renderCube(PoseStack poseStack, GeoCube cube, VertexConsumer buffer, int packedLight, int packedOverlay, int colour) {
   }
}
