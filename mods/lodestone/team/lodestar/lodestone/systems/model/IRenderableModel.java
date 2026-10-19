package team.lodestar.lodestone.systems.model;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexBuffer.Usage;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.joml.Matrix4f;
import team.lodestar.lodestone.systems.rendering.IVertexBuffer;

public interface IRenderableModel {
   ResourceLocation getModelLocation();

   void loadModel();

   void render(PoseStack var1, VertexConsumer var2, RenderType var3);

   VertexBuffer getModelBuffer();

   void setModelBuffer(VertexBuffer var1);

   MeshData createMesh(PoseStack var1, VertexFormat var2, Mode var3);

   default void render(PoseStack poseStack, RenderType renderType, BufferSource bufferSource) {
      VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
      this.render(poseStack, vertexConsumer, renderType);
   }

   default void renderInstanced(PoseStack poseStack, Matrix4f frustrumMatrix, Matrix4f projectionMatrix, RenderType renderType, int instances) {
      this.createModelBuffer(poseStack, renderType);
      this.getModelBuffer().bind();
      renderType.setupRenderState();
      IVertexBuffer.cast(this.getModelBuffer()).drawWithShaderInstanced(frustrumMatrix, projectionMatrix, RenderSystem.getShader(), instances);
      renderType.clearRenderState();
      VertexBuffer.unbind();
   }

   @Internal
   default void createModelBuffer(PoseStack poseStack, RenderType renderType) {
      if (this.getModelBuffer() == null) {
         this.setModelBuffer(new VertexBuffer(Usage.DYNAMIC));
      }

      this.getModelBuffer().bind();
      this.getModelBuffer().upload(this.createMesh(poseStack, renderType));
      VertexBuffer.unbind();
   }

   @Internal
   default MeshData createMesh(PoseStack poseStack, RenderType renderType) {
      return this.createMesh(poseStack, renderType.format(), renderType.mode());
   }

   @Internal
   default void cleanup() {
      VertexBuffer buffer = this.getModelBuffer();
      if (buffer != null) {
         buffer.close();
         this.setModelBuffer(null);
      }
   }
}
