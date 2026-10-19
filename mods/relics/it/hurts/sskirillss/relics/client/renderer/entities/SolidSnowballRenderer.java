package it.hurts.sskirillss.relics.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.hurts.sskirillss.relics.client.models.entities.SolidSnowballModel;
import it.hurts.sskirillss.relics.entities.SolidSnowballEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SolidSnowballRenderer extends EntityRenderer<SolidSnowballEntity> {
   public SolidSnowballRenderer(Context renderManager) {
      super(renderManager);
   }

   public void render(SolidSnowballEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      float time = entityIn.tickCount + (Minecraft.getInstance().isPaused() ? 0.0F : partialTicks);
      matrixStackIn.pushPose();
      matrixStackIn.translate(0.0F, 0.25F, 0.0F);
      float speed = 20.0F;
      matrixStackIn.mulPose(Axis.YP.rotationDegrees(time * speed));
      matrixStackIn.mulPose(Axis.XN.rotationDegrees(time * speed));
      float scale = 0.1F + entityIn.getSize() * 0.009F;
      matrixStackIn.scale(scale, scale, scale);
      new SolidSnowballModel()
         .renderToBuffer(
            matrixStackIn,
            bufferIn.getBuffer(RenderType.entityCutout(ResourceLocation.fromNamespaceAndPath("relics", "textures/entities/solid_snowball.png"))),
            packedLightIn,
            OverlayTexture.NO_OVERLAY
         );
      matrixStackIn.popPose();
   }

   public ResourceLocation getTextureLocation(SolidSnowballEntity entity) {
      return ResourceLocation.fromNamespaceAndPath("relics", "textures/entities/solid_snowball.png");
   }
}
