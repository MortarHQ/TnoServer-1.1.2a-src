package com.github.wal_bos.moreskills.client.renderer;

import com.github.wal_bos.moreskills.client.model.TheCoordinateModel;
import com.github.wal_bos.moreskills.world.entity.TheCoordinateEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.phys.Vec3;

public class TheCoordinateRenderer extends MobRenderer<TheCoordinateEntity, TheCoordinateModel<TheCoordinateEntity>> {
   private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/entity/thecoordinate.png");
   private static final ResourceLocation TEX_EMISSIVE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/entity/thecoordinate_emissive.png");
   private static final float VISUAL_SCALE = 200.0F;
   private static final double RENDER_DISTANCE = 24000.0;
   private static final int FULL_BRIGHT = 15728880;
   private static final int WHITE = ARGB32.color(255, 255, 255, 255);

   public TheCoordinateRenderer(Context ctx) {
      super(ctx, new TheCoordinateModel(ctx.bakeLayer(TheCoordinateModel.LAYER_LOCATION)), 0.0F);
      this.addLayer(
         new RenderLayer<TheCoordinateEntity, TheCoordinateModel<TheCoordinateEntity>>(this) {
            public void render(
               PoseStack poseStack,
               MultiBufferSource buffer,
               int packedLight,
               TheCoordinateEntity entity,
               float limbSwing,
               float limbSwingAmount,
               float partialTicks,
               float ageInTicks,
               float netHeadYaw,
               float headPitch
            ) {
               TheCoordinateRenderer.renderEmissive((TheCoordinateModel<TheCoordinateEntity>)this.getParentModel(), poseStack, buffer);
               TheCoordinateRenderer.renderEmissive((TheCoordinateModel<TheCoordinateEntity>)this.getParentModel(), poseStack, buffer);
            }
         }
      );
   }

   public ResourceLocation getTextureLocation(TheCoordinateEntity entity) {
      return TEX;
   }

   protected int getBlockLightLevel(TheCoordinateEntity entity, BlockPos pos) {
      return 15;
   }

   protected RenderType getRenderType(TheCoordinateEntity entity, boolean bodyVisible, boolean translucent, boolean glowing) {
      return RenderType.entityTranslucentEmissive(TEX);
   }

   protected void scale(TheCoordinateEntity entity, PoseStack poseStack, float partialTickTime) {
      poseStack.scale(200.0F, 200.0F, 200.0F);
   }

   public boolean shouldRender(TheCoordinateEntity entity, Frustum frustum, double camX, double camY, double camZ) {
      Vec3 p = entity.position();
      double dx = p.x - camX;
      double dy = p.y - camY;
      double dz = p.z - camZ;
      return dx * dx + dy * dy + dz * dz <= 5.76E8;
   }

   public void render(TheCoordinateEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      super.render(entity, entityYaw, partialTicks, poseStack, buffer, 15728880);
   }

   public boolean affectedByCulling(TheCoordinateEntity entity) {
      return false;
   }

   private static void renderEmissive(TheCoordinateModel<TheCoordinateEntity> model, PoseStack poseStack, MultiBufferSource buffer) {
      model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.eyes(TEX_EMISSIVE)), 15728880, OverlayTexture.NO_OVERLAY, WHITE);
   }
}
