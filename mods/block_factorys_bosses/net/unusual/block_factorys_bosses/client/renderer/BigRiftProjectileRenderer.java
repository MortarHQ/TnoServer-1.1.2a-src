package net.unusual.block_factorys_bosses.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.model.ModelBigRiftProjectile;
import net.unusual.block_factorys_bosses.entity.projectile.RiftProjectileEntity;

public class BigRiftProjectileRenderer extends EntityRenderer<RiftProjectileEntity> {
   private final ModelBigRiftProjectile model;

   public BigRiftProjectileRenderer(Context context) {
      super(context);
      this.model = new ModelBigRiftProjectile(context.bakeLayer(ModelBigRiftProjectile.LAYER_LOCATION));
   }

   public void render(RiftProjectileEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
      VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
      poseStack.pushPose();
      poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
      poseStack.mulPose(Axis.YP.rotationDegrees(-entityIn.getYRot()));
      poseStack.scale(2.0F, 2.0F, 2.0F);
      this.model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY);
      poseStack.popPose();
      super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(RiftProjectileEntity entity) {
      return BossesRise.prefix("textures/entities/big_rift_projectile.png");
   }
}
