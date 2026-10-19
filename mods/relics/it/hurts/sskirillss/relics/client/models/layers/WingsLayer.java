package it.hurts.sskirillss.relics.client.models.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.models.parts.HaloModel;
import it.hurts.sskirillss.relics.client.models.parts.WingsModel;
import it.hurts.sskirillss.relics.init.EffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class WingsLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private final HaloModel haloModel = new HaloModel(HaloModel.createBodyLayer().bakeRoot());
   private final WingsModel wingsModel = new WingsModel(WingsModel.createBodyLayer().bakeRoot());

   public WingsLayer(RenderLayerParent<T, M> pRenderer) {
      super(pRenderer);
   }

   public void render(
      PoseStack pPoseStack,
      MultiBufferSource pBuffer,
      int pPackedLight,
      T pLivingEntity,
      float pLimbSwing,
      float pLimbSwingAmount,
      float pPartialTicks,
      float pAgeInTicks,
      float pNetHeadYaw,
      float pHeadPitch
   ) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && player.hasEffect(EffectRegistry.IMMORTALITY)) {
         pPoseStack.pushPose();
         pPoseStack.scale(0.75F, 0.75F, 0.75F);
         pPoseStack.translate(0.0, Math.sin(pLivingEntity.tickCount * 0.1F) * 0.05F, 0.0);
         ICurioRenderer.followBodyRotations(pLivingEntity, new HumanoidModel[]{this.haloModel});
         this.haloModel
            .renderToBuffer(
               pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucentCull(HaloModel.LAYER_LOCATION.getModel())), 15728880, OverlayTexture.NO_OVERLAY
            );
         pPoseStack.popPose();
         pPoseStack.pushPose();
         ICurioRenderer.followBodyRotations(pLivingEntity, new HumanoidModel[]{this.wingsModel});
         this.wingsModel
            .renderToBuffer(
               pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucentCull(WingsModel.LAYER_LOCATION.getModel())), 15728880, OverlayTexture.NO_OVERLAY
            );
         pPoseStack.popPose();
      }
   }
}
