package io.github.manasmods.tensura_neb.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.manasmods.tensura_neb.entity.VeldoraEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class VeldoraRenderer<E extends VeldoraEntity> extends GeoEntityRenderer<E> {
   public VeldoraRenderer(Context renderManager) {
      super(renderManager, new VeldoraModel());
   }

   protected float getShadowRadius(E entity) {
      return 2.0F;
   }

   public void preRender(
      PoseStack poseStack,
      E veldora,
      BakedGeoModel model,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      this.entityRenderTranslations = new Matrix4f(poseStack.last().pose());
      float scale = veldora.isBaby() ? 0.5F : 1.0F;
      this.scaleModelForRender(
         this.scaleWidth * scale, this.scaleHeight * scale, poseStack, veldora, model, isReRender, partialTick, packedLight, packedOverlay
      );
   }
}
