package com.sjyueluo.tensura_skill_books.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.sjyueluo.tensura_skill_books.TensuraSkillBooksMod;
import com.sjyueluo.tensura_skill_books.client.icon.IconData;
import com.sjyueluo.tensura_skill_books.client.icon.TensuraSkillIconResolver;
import com.sjyueluo.tensura_skill_books.util.SkillItemNbt;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

public final class SkillStudyBookItemRenderer extends BlockEntityWithoutLevelRenderer {
   private static final boolean DEBUG_GUI_ALIGN = Boolean.getBoolean("tensura_skill_books.debugGuiAlign");
   private static volatile boolean debugPrinted;

   public SkillStudyBookItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
      super(dispatcher, modelSet);
   }

   public void renderByItem(
      ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay
   ) {
      ResourceLocation iconId = SkillItemNbt.getRenderIconId(stack);
      IconData icon = TensuraSkillIconResolver.resolve(iconId);
      boolean gui = transformType == ItemDisplayContext.GUI;
      boolean inHand = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
         || transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
         || transformType == ItemDisplayContext.THIRD_PERSON_LEFT_HAND
         || transformType == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
      poseStack.pushPose();
      if (inHand) {
         poseStack.translate(0.5F, 0.5F, 0.0F);
         poseStack.scale(0.5F, 0.5F, 0.5F);
         poseStack.translate(-0.5F, -0.5F, 0.0F);
      }

      Pose pose = poseStack.last();
      Matrix4f poseMat = pose.pose();
      float iconZ = gui ? 1.0E-4F : 0.5F;
      int width = Math.max(1, icon.pixelWidth());
      int height = Math.max(1, icon.pixelHeight());
      int max = Math.max(width, height);
      float scale = 16.0F / max;
      float renderWidth = width * scale / 16.0F;
      float renderHeight = height * scale / 16.0F;
      float x0 = 0.5F - renderWidth * 0.5F;
      float y0 = 0.5F - renderHeight * 0.5F;
      int light = gui ? 15728880 : packedLight;
      int overlay = gui ? OverlayTexture.NO_OVERLAY : packedOverlay;
      RenderType iconType = gui ? RenderType.entityTranslucentEmissive(icon.texture()) : RenderType.entityCutoutNoCull(icon.texture());
      drawQuad(
         buffer,
         poseMat,
         pose,
         true,
         iconType,
         x0,
         y0,
         iconZ,
         renderWidth,
         renderHeight,
         icon.u0(),
         icon.v0(),
         icon.u1(),
         icon.v1(),
         light,
         overlay,
         0.0F,
         0.0F,
         1.0F
      );
      if (!gui) {
         float backZ = iconZ - 1.0E-4F;
         drawQuad(
            buffer,
            poseMat,
            pose,
            true,
            RenderType.entityCutoutNoCull(icon.texture()),
            x0,
            y0,
            backZ,
            renderWidth,
            renderHeight,
            icon.u0(),
            icon.v0(),
            icon.u1(),
            icon.v1(),
            light,
            packedOverlay,
            0.0F,
            0.0F,
            -1.0F
         );
      }

      if (DEBUG_GUI_ALIGN && gui && !debugPrinted) {
         debugPrinted = true;
         TensuraSkillBooksMod.LOGGER
            .info(
               "[{}] GUI icon align: ctx={}, icon_id={}, tex={}, iconWH={}x{}, scale={}, drawWH={}x{}",
               "tensura_skill_books",
               transformType,
               iconId,
               icon.texture(),
               width,
               height,
               scale,
               renderWidth,
               renderHeight
            );
      }

      poseStack.popPose();
   }

   private static void vertex(
      VertexConsumer consumer,
      Matrix4f matrix,
      Pose pose,
      boolean transformNormal,
      float x,
      float y,
      float z,
      float u,
      float v,
      int light,
      int overlay,
      float nx,
      float ny,
      float nz
   ) {
      VertexConsumer built = consumer.addVertex(matrix, x, y, z).setColor(255, 255, 255, 255).setUv(u, v).setOverlay(overlay).setLight(light);
      if (transformNormal) {
         built.setNormal(pose, nx, ny, nz);
      } else {
         built.setNormal(nx, ny, nz);
      }
   }

   private static void drawQuad(
      MultiBufferSource buffer,
      Matrix4f matrix,
      Pose pose,
      boolean transformNormal,
      RenderType type,
      float x,
      float y,
      float z,
      float width,
      float height,
      float u0,
      float v0,
      float u1,
      float v1,
      int packedLight,
      int packedOverlay,
      float nx,
      float ny,
      float nz
   ) {
      VertexConsumer consumer = buffer.getBuffer(type);
      vertex(consumer, matrix, pose, transformNormal, x, y + height, z, u0, v0, packedLight, packedOverlay, nx, ny, nz);
      vertex(consumer, matrix, pose, transformNormal, x + width, y + height, z, u1, v0, packedLight, packedOverlay, nx, ny, nz);
      vertex(consumer, matrix, pose, transformNormal, x + width, y, z, u1, v1, packedLight, packedOverlay, nx, ny, nz);
      vertex(consumer, matrix, pose, transformNormal, x, y, z, u0, v1, packedLight, packedOverlay, nx, ny, nz);
   }
}
