package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.client.model.entity.ShimmerLacewingModel;
import cn.leolezury.eternalstarlight.common.entity.living.animal.ShimmerLacewing;
import cn.leolezury.eternalstarlight.common.entity.living.animal.ShimmerLacewingVariant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;

public class ShimmerLacewingGlowLayer<T extends ShimmerLacewing> extends RenderLayer<T, ShimmerLacewingModel<T>> {
   private final ShimmerLacewingModel<T> model;

   public ShimmerLacewingGlowLayer(RenderLayerParent<T, ShimmerLacewingModel<T>> parent, EntityModelSet modelSet) {
      super(parent);
      this.model = new ShimmerLacewingModel<>(modelSet.bakeLayer(ShimmerLacewingModel.LAYER_LOCATION));
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      int packedLight,
      T entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!entity.isInvisible()) {
         ((ShimmerLacewingModel)this.getParentModel()).copyPropertiesTo(this.model);
         this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
         this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         VertexConsumer consumer = bufferSource.getBuffer(
            RenderType.entityTranslucentEmissive(((ShimmerLacewingVariant)entity.getVariant().value()).glowTextureFull())
         );
         this.model
            .renderToBuffer(
               poseStack,
               consumer,
               packedLight,
               LivingEntityRenderer.getOverlayCoords(entity, 0.0F),
               ARGB32.color((int)(Math.max(0.0F, Mth.cos(ageInTicks * 0.1F + (float) Math.PI)) * 255.0F), 255, 255, 255)
            );
      }
   }
}
