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
import org.crypticdev.ancient_artifacts.artifact.models.AmuletModel;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.crypticdev.ancient_artifacts.registry.Artifacts;

public class AmuletLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("ancient_artifacts", "textures/artifacts/amulet.png");
   private static final ResourceLocation TEXTURE2 = ResourceLocation.fromNamespaceAndPath("ancient_artifacts", "textures/artifacts/amulet2.png");
   private final AmuletModel<Entity> model = new AmuletModel<>(AmuletModel.createBodyLayer().bakeRoot());

   public AmuletLayer(RenderLayerParent<T, M> parent) {
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
      if (PlatformEquipment.hasArtifact(livingEntity, Artifacts.AmuletOfTheSylphs)) {
         ResourceLocation finalTexture = TEXTURE;
         ItemStack stack = PlatformEquipment.getArtifactStack(livingEntity, Artifacts.AmuletOfTheSylphs);
         if (stack.has((DataComponentType)TensuraDataComponents.EP.get())) {
            double ep = (Double)stack.get((DataComponentType)TensuraDataComponents.EP.get());
            if (ep < AncientArtifacts.CONFIG.getGradeConfig(2).EffectUnlockEp.get(1)) {
               finalTexture = TEXTURE;
               this.model.root().getChild("Amuelt2").visible = false;
               this.model.root().getChild("Amulet1").visible = true;
            } else {
               finalTexture = TEXTURE2;
               this.model.root().getChild("Amuelt2").visible = true;
               this.model.root().getChild("Amulet1").visible = false;
            }
         }

         VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(finalTexture));
         poseStack.pushPose();
         ((HumanoidModel)this.getParentModel()).body.translateAndRotate(poseStack);
         this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
         poseStack.popPose();
      }
   }
}
