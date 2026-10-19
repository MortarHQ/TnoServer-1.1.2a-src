package net.unusual.block_factorys_bosses.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.unusual.block_factorys_bosses.entity.decoration.CageEntity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CageEntityRenderer<T extends EntityModel<CageEntity>> extends EntityRenderer<CageEntity> implements RenderLayerParent<CageEntity, T> {
   protected final T model;
   protected final ResourceLocation texture;

   public CageEntityRenderer(Context context, T model, ResourceLocation texture) {
      super(context);
      this.model = model;
      this.texture = texture;
   }

   public T getModel() {
      return this.model;
   }

   public ResourceLocation getTextureLocation(CageEntity entity) {
      return this.texture;
   }

   public void render(CageEntity cage, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
      VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(cage)));
      poseStack.pushPose();
      poseStack.translate(0.0F, 1.5F, 0.0F);
      poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
      poseStack.mulPose(Axis.YP.rotationDegrees(cage.getYRot()));
      float hurtTime = cage.getHurtTime() - partialTicks;
      float damage = cage.getDamage() - partialTicks;
      if (damage < 0.0F) {
         damage = 0.0F;
      }

      if (hurtTime > 0.0F) {
         poseStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(hurtTime) * hurtTime * damage / 25.0F * cage.getHurtDir()));
      }

      this.model.setupAnim(cage, 0.0F, 0.0F, 0.0F, cage.getYRot(), cage.getXRot());
      this.model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY);
      poseStack.popPose();
      super.render(cage, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
   }
}
