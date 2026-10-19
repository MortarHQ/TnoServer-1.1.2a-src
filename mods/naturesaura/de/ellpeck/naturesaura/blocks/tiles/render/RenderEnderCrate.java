package de.ellpeck.naturesaura.blocks.tiles.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityEnderCrate;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class RenderEnderCrate implements BlockEntityRenderer<BlockEntityEnderCrate> {
   public RenderEnderCrate(Context context) {
   }

   public void render(
      BlockEntityEnderCrate tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn
   ) {
      Matrix4f matrix4f = matrixStackIn.last().pose();
      float f1 = getOffsetUp();
      renderFace(matrix4f, bufferIn.getBuffer(renderType()), f1, f1);
   }

   private static void renderFace(Matrix4f p_173696_, VertexConsumer p_173697_, float p_173700_, float p_173701_) {
      p_173697_.addVertex(p_173696_, 0.0625F, p_173700_, 0.9375F);
      p_173697_.addVertex(p_173696_, 0.9375F, p_173700_, 0.9375F);
      p_173697_.addVertex(p_173696_, 0.9375F, p_173701_, 0.0625F);
      p_173697_.addVertex(p_173696_, 0.0625F, p_173701_, 0.0625F);
   }

   private static float getOffsetUp() {
      return 1.001F;
   }

   private static RenderType renderType() {
      return RenderType.endPortal();
   }
}
