package com.iafenvoy.iceandfire.render.block;

import com.iafenvoy.iceandfire.item.block.entity.DreadPortalBlockEntity;
import com.iafenvoy.iceandfire.registry.IafRenderLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class DreadPortalBlockEntityRenderer<T extends DreadPortalBlockEntity> implements BlockEntityRenderer<T> {
   public static final ResourceLocation DREAD_PORTAL_BACKGROUND = ResourceLocation.fromNamespaceAndPath(
      "iceandfire", "textures/environment/dread_portal_background.png"
   );
   public static final ResourceLocation DREAD_PORTAL = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/environment/dread_portal.png");

   public DreadPortalBlockEntityRenderer(Context context) {
   }

   public void render(T entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
      Matrix4f matrix4f = matrices.last().pose();
      VertexConsumer consumer = vertexConsumers.getBuffer(this.renderType());
      consumer.addVertex(matrix4f, 0.0F, 0.0F, 1.0F).setColor(-1);
      consumer.addVertex(matrix4f, 1.0F, 0.0F, 1.0F).setColor(-1);
      consumer.addVertex(matrix4f, 1.0F, 1.0F, 1.0F).setColor(-1);
      consumer.addVertex(matrix4f, 0.0F, 1.0F, 1.0F).setColor(-1);
      consumer.addVertex(matrix4f, 0.0F, 0.0F, 0.0F).setColor(-1);
      consumer.addVertex(matrix4f, 0.0F, 1.0F, 0.0F).setColor(-1);
      consumer.addVertex(matrix4f, 1.0F, 1.0F, 0.0F).setColor(-1);
      consumer.addVertex(matrix4f, 1.0F, 0.0F, 0.0F).setColor(-1);
      consumer.addVertex(matrix4f, 0.0F, 0.0F, 0.0F).setColor(-1);
      consumer.addVertex(matrix4f, 0.0F, 0.0F, 1.0F).setColor(-1);
      consumer.addVertex(matrix4f, 0.0F, 1.0F, 1.0F).setColor(-1);
      consumer.addVertex(matrix4f, 0.0F, 1.0F, 0.0F).setColor(-1);
      consumer.addVertex(matrix4f, 1.0F, 0.0F, 1.0F).setColor(-1);
      consumer.addVertex(matrix4f, 1.0F, 0.0F, 0.0F).setColor(-1);
      consumer.addVertex(matrix4f, 1.0F, 1.0F, 0.0F).setColor(-1);
      consumer.addVertex(matrix4f, 1.0F, 1.0F, 1.0F).setColor(-1);
      consumer.addVertex(matrix4f, 0.0F, 1.0F, 0.0F).setColor(-1);
      consumer.addVertex(matrix4f, 0.0F, 1.0F, 1.0F).setColor(-1);
      consumer.addVertex(matrix4f, 1.0F, 1.0F, 1.0F).setColor(-1);
      consumer.addVertex(matrix4f, 1.0F, 1.0F, 0.0F).setColor(-1);
      consumer.addVertex(matrix4f, 0.0F, 0.0F, 0.0F).setColor(-1);
      consumer.addVertex(matrix4f, 1.0F, 0.0F, 0.0F).setColor(-1);
      consumer.addVertex(matrix4f, 1.0F, 0.0F, 1.0F).setColor(-1);
      consumer.addVertex(matrix4f, 0.0F, 0.0F, 1.0F).setColor(-1);
   }

   protected RenderType renderType() {
      return IafRenderLayers.getDreadlandsPortal();
   }
}
