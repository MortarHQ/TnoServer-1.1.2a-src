package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.entity.Ceraunus_Model;
import com.github.L_Ender.cataclysm.client.model.entity.Scylla_Model;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Scylla.Scylla_Ceraunus_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Scylla.Scylla_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Scylla_Ceraunus_Renderer extends EntityRenderer<Scylla_Ceraunus_Entity> {
   private final Ceraunus_Model model;
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/scylla/ceraunus.png");
   private static final ResourceLocation CHAIN_TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/scylla/scylla_chain.png");

   public Scylla_Ceraunus_Renderer(Context renderManagerIn) {
      super(renderManagerIn);
      this.model = new Ceraunus_Model(renderManagerIn.bakeLayer(CMModelLayers.CERAUNUS_MODEL));
   }

   public void render(Scylla_Ceraunus_Entity entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource provider, int light) {
      matrices.pushPose();
      float yRot = Mth.lerp(tickDelta, entity.yRotO, entity.getYRot());
      float xRot = Mth.lerp(tickDelta, entity.xRotO, entity.getXRot());
      matrices.mulPose(Axis.YP.rotationDegrees(yRot - 90.0F));
      matrices.mulPose(Axis.ZP.rotationDegrees(xRot + 90.0F));
      VertexConsumer vertexConsumer = provider.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
      this.model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
      matrices.popPose();
      Entity fromEntity = entity.getController();
      if (fromEntity != null) {
         Vec3 entityPos = entity.getPosition(tickDelta);
         PoseStack poseForModel = new PoseStack();
         poseForModel.mulPose(Axis.YP.rotationDegrees(yRot - 90.0F));
         poseForModel.mulPose(Axis.ZP.rotationDegrees(xRot + 90.0F));
         Vec3 modelOffset = this.model.getChainPosition(new Vec3(0.0, 0.0, 0.0), poseForModel);
         Vec3 fromPos = this.getPositionOfPriorMob(fromEntity, tickDelta);
         Vec3 chainTo = fromPos.subtract(entityPos);
         Vec3 chainBase = modelOffset;
         matrices.pushPose();
         matrices.translate(chainBase.x, chainBase.y, chainBase.z);
         VertexConsumer chainBuffer = provider.getBuffer(RenderType.entityCutoutNoCull(CHAIN_TEXTURE));
         renderChainCube(chainTo.subtract(chainBase), matrices, chainBuffer, light, OverlayTexture.NO_OVERLAY);
         matrices.popPose();
      }
   }

   public boolean shouldRender(Scylla_Ceraunus_Entity entity, Frustum camera, double camX, double camY, double camZ) {
      if (super.shouldRender(entity, camera, camX, camY, camZ)) {
         return true;
      } else {
         Entity weapon = entity.getController();
         if (weapon != null) {
            Vec3 vec3 = entity.position();
            Vec3 vec31 = weapon.position();
            return camera.isVisible(new AABB(vec31.x, vec31.y, vec31.z, vec3.x, vec3.y, vec3.z));
         } else {
            return false;
         }
      }
   }

   private Vec3 getPositionOfPriorMob(Entity mob, float partialTicks) {
      double d4 = Mth.lerp(partialTicks, mob.xo, mob.getX());
      double d5 = Mth.lerp(partialTicks, mob.yo, mob.getY());
      double d6 = Mth.lerp(partialTicks, mob.zo, mob.getZ());
      if (mob instanceof Scylla_Entity living) {
         float bodyYaw = Mth.rotLerp(partialTicks, living.yBodyRotO, living.yBodyRot);
         if (Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(living) instanceof Scylla_Renderer livingRenderer
            && livingRenderer.getModel() instanceof Scylla_Model targetModel) {
            Vec3 toPos = targetModel.getHandPosition(new Vec3(0.0, 0.0, 0.0)).yRot((float)(Math.PI - bodyYaw * (float) (Math.PI / 180.0)));
            return new Vec3(d4 + toPos.x, d5 + toPos.y, d6 + toPos.z);
         }
      }

      return new Vec3(d4, d5, d6);
   }

   public static void renderChainCube(Vec3 to, PoseStack poseStack, VertexConsumer buffer, int packedLightIn, int setOverlay) {
      double d = to.horizontalDistance();
      float rotY = (float)(Mth.atan2(to.x, to.z) * 180.0F / (float)Math.PI);
      float rotX = (float)(-(Mth.atan2(to.y, d) * 180.0F / (float)Math.PI)) - 90.0F;
      float chainWidth = 0.1875F;
      float chainOffset = chainWidth * -0.5F;
      float chainLength = (float)to.length() / 2.3F;
      poseStack.pushPose();
      poseStack.scale(2.3F, 2.3F, 2.3F);
      poseStack.mulPose(Axis.YP.rotationDegrees(rotY));
      poseStack.mulPose(Axis.XP.rotationDegrees(rotX));
      poseStack.translate(0.0F, -chainLength, 0.0F);
      Pose posestack$pose = poseStack.last();
      buffer.addVertex(posestack$pose, chainOffset, 0.0F, 0.0F)
         .setColor(255, 255, 255, 255)
         .setUv(0.0F, chainLength)
         .setOverlay(setOverlay)
         .setLight(packedLightIn)
         .setNormal(posestack$pose, 0.0F, 1.0F, 0.0F);
      buffer.addVertex(posestack$pose, chainWidth + chainOffset, 0.0F, 0.0F)
         .setColor(255, 255, 255, 255)
         .setUv(chainWidth, chainLength)
         .setOverlay(setOverlay)
         .setLight(packedLightIn)
         .setNormal(posestack$pose, 0.0F, 1.0F, 0.0F);
      buffer.addVertex(posestack$pose, chainWidth + chainOffset, chainLength, 0.0F)
         .setColor(255, 255, 255, 255)
         .setUv(chainWidth, 0.0F)
         .setOverlay(setOverlay)
         .setLight(packedLightIn)
         .setNormal(posestack$pose, 0.0F, 1.0F, 0.0F);
      buffer.addVertex(posestack$pose, chainOffset, chainLength, 0.0F)
         .setColor(255, 255, 255, 255)
         .setUv(0.0F, 0.0F)
         .setOverlay(setOverlay)
         .setLight(packedLightIn)
         .setNormal(posestack$pose, 0.0F, 1.0F, 0.0F);
      float pixelSkip = 0.25F;
      buffer.addVertex(posestack$pose, 0.0F, pixelSkip, chainOffset)
         .setColor(255, 255, 255, 255)
         .setUv(chainWidth, chainLength + pixelSkip)
         .setOverlay(setOverlay)
         .setLight(packedLightIn)
         .setNormal(posestack$pose, 0.0F, 1.0F, 0.0F);
      buffer.addVertex(posestack$pose, 0.0F, pixelSkip, chainWidth + chainOffset)
         .setColor(255, 255, 255, 255)
         .setUv(chainWidth * 2.0F, chainLength + pixelSkip)
         .setOverlay(setOverlay)
         .setLight(packedLightIn)
         .setNormal(posestack$pose, 0.0F, 1.0F, 0.0F);
      buffer.addVertex(posestack$pose, 0.0F, chainLength + pixelSkip, chainWidth + chainOffset)
         .setColor(255, 255, 255, 255)
         .setUv(chainWidth * 2.0F, pixelSkip)
         .setOverlay(setOverlay)
         .setLight(packedLightIn)
         .setNormal(posestack$pose, 0.0F, 1.0F, 0.0F);
      buffer.addVertex(posestack$pose, 0.0F, chainLength + pixelSkip, chainOffset)
         .setColor(255, 255, 255, 255)
         .setUv(chainWidth, pixelSkip)
         .setOverlay(setOverlay)
         .setLight(packedLightIn)
         .setNormal(posestack$pose, 0.0F, 1.0F, 0.0F);
      poseStack.popPose();
   }

   public ResourceLocation getTextureLocation(Scylla_Ceraunus_Entity entity) {
      return TEXTURE;
   }
}
