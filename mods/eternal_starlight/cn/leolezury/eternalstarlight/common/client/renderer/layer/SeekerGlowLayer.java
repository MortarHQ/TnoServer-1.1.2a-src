package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.client.model.entity.SeekerModel;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Seeker;
import cn.leolezury.eternalstarlight.common.entity.living.monster.SeekerVariant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class SeekerGlowLayer<T extends Seeker, M extends SeekerModel<T>> extends RenderLayer<T, M> {
   public SeekerGlowLayer(RenderLayerParent<T, M> parent) {
      super(parent);
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight,
      T entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      VertexConsumer consumer = buffer.getBuffer(RenderType.eyes(((SeekerVariant)entity.getVariant().value()).glowTextureFull()));
      ((SeekerModel)this.getParentModel()).renderToBuffer(poseStack, consumer, 15728880, OverlayTexture.NO_OVERLAY);
   }
}
