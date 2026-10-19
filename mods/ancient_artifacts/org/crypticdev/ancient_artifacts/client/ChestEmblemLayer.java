package org.crypticdev.ancient_artifacts.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.AncientArtifacts;
import org.crypticdev.ancient_artifacts.artifact.models.ChestEmblemModel;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.crypticdev.ancient_artifacts.registry.Artifacts;

public class ChestEmblemLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("ancient_artifacts", "textures/artifacts/chest_emblem.png");
   private static final ResourceLocation TEXTURE2 = ResourceLocation.fromNamespaceAndPath("ancient_artifacts", "textures/artifacts/chest_emblem2.png");
   private static final ResourceLocation TEXTURE3 = ResourceLocation.fromNamespaceAndPath("ancient_artifacts", "textures/artifacts/chest_emblem3.png");
   private final ChestEmblemModel<Entity> model = new ChestEmblemModel<>(ChestEmblemModel.createBodyLayer().bakeRoot());

   public ChestEmblemLayer(RenderLayerParent<T, M> parent) {
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
      if (PlatformEquipment.hasArtifact(livingEntity, Artifacts.LionEmblem)) {
         ResourceLocation finalTexture = TEXTURE;
         ItemStack stack = PlatformEquipment.getArtifactStack(livingEntity, Artifacts.LionEmblem);
         if (stack.has((DataComponentType)TensuraDataComponents.EP.get())) {
            double ep = (Double)stack.get((DataComponentType)TensuraDataComponents.EP.get());
            if (ep < AncientArtifacts.CONFIG.getGradeConfig(1).EffectUnlockEp.get(1)) {
               finalTexture = TEXTURE;
               this.model.root().getChild("ChestEmblem").visible = true;
               this.model.root().getChild("ChestEmblem2").visible = false;
               this.model.root().getChild("ChestEmblem3").visible = false;
            } else if (ep < AncientArtifacts.CONFIG.getGradeConfig(1).EffectUnlockEp.get(2)) {
               finalTexture = TEXTURE2;
               this.model.root().getChild("ChestEmblem").visible = false;
               this.model.root().getChild("ChestEmblem2").visible = true;
               this.model.root().getChild("ChestEmblem3").visible = false;
            } else {
               finalTexture = TEXTURE3;
               this.model.root().getChild("ChestEmblem").visible = false;
               this.model.root().getChild("ChestEmblem2").visible = false;
               this.model.root().getChild("ChestEmblem3").visible = true;
            }
         }

         VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(finalTexture));
         poseStack.pushPose();
         ((HumanoidModel)this.getParentModel()).body.translateAndRotate(poseStack);
         this.model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
         poseStack.popPose();
      }
   }
}
