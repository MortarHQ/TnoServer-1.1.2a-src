package org.crypticdev.ancient_artifacts.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.crypticdev.ancient_artifacts.artifact.models.BootsModel;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.crypticdev.ancient_artifacts.registry.Artifacts;

public class BootsLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("ancient_artifacts", "textures/artifacts/boots.png");
   private final BootsModel<Entity> Model = new BootsModel<>(BootsModel.createBodyLayer().bakeRoot());

   public BootsLayer(RenderLayerParent<T, M> parent) {
      super(parent);
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight,
      T livingEntity,
      float limbSwing,
      float limbSwingAmount,
      float partialTick,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (PlatformEquipment.hasArtifact(livingEntity, Artifacts.TitanstrideGreaves)) {
         VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
         poseStack.pushPose();
         ((HumanoidModel)this.getParentModel()).leftLeg.translateAndRotate(poseStack);
         poseStack.translate(-0.11875F, 0.75F, 0.0F);
         this.Model.renderLeftToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
         poseStack.popPose();
         poseStack.pushPose();
         ((HumanoidModel)this.getParentModel()).rightLeg.translateAndRotate(poseStack);
         poseStack.translate(0.11875F, 0.75F, 0.0F);
         this.Model.renderRightToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
         poseStack.popPose();
      }
   }
}
