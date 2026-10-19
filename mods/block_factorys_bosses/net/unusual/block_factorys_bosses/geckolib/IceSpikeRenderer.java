package net.unusual.block_factorys_bosses.geckolib;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IceSpikeRenderer extends GeoEntityRenderer<IceSpikeEntity> {
   public IceSpikeRenderer(Context context) {
      super(context, new IceSpikeRenderer.IceSpikeModel());
      this.addRenderLayer(new DangerZonesRenderLayer(this));
   }

   public void defaultRender(
      PoseStack poseStack,
      IceSpikeEntity animatable,
      MultiBufferSource bufferSource,
      @Nullable RenderType renderType,
      @Nullable VertexConsumer buffer,
      float yaw,
      float partialTick,
      int packedLight
   ) {
      if (animatable.tickCount < animatable.getDelay()) {
         RenderType type = this.getRenderType(animatable, this.getTextureLocation(animatable), bufferSource, partialTick);
         if (type != null) {
            BakedGeoModel model = this.getGeoModel().getBakedModel(this.getGeoModel().getModelResource(animatable, this));
            if (animatable.getEvil()) {
               animatable.getDangerZones().forEach(dangerZone -> dangerZone.setColor(16711680));
            }

            this.applyRenderLayers(
               poseStack,
               animatable,
               model,
               type,
               bufferSource,
               bufferSource.getBuffer(type),
               partialTick,
               packedLight,
               this.getPackedOverlay(animatable, 0.0F, partialTick)
            );
         }
      } else {
         poseStack.pushPose();
         poseStack.mulPose(Axis.YP.rotationDegrees(-animatable.getViewYRot(partialTick) + 180.0F));
         poseStack.mulPose(Axis.XP.rotationDegrees(animatable.getViewXRot(partialTick)));
         float trueTick = animatable.tickCount - animatable.getDelay() + partialTick;
         float factor = 5.5F;
         float scaler = (trueTick - factor) / factor;
         float size = ((float)(-Math.sqrt(Math.sqrt(scaler * scaler))) + 1.0F) * 1.5F;
         if (size > 0.0F) {
            poseStack.scale(size, size, size);
            poseStack.translate(0.0F, -0.25F, 0.0F);
            super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
         }

         poseStack.popPose();
      }
   }

   private static class IceSpikeModel extends CustomEntityGeoModel<IceSpikeEntity> {
      private final ResourceLocation bigPath = this.buildFormattedModelPath(BossesRise.prefix("ice_spike_big"));
      private final ResourceLocation mediumPath = this.buildFormattedModelPath(BossesRise.prefix("ice_spike_medium"));
      private final ResourceLocation smallPath = this.buildFormattedModelPath(BossesRise.prefix("ice_spike_small"));
      private final ResourceLocation tinyPath = this.buildFormattedModelPath(BossesRise.prefix("ice_spike_tiny"));

      public IceSpikeModel() {
         super(BossesRise.prefix("ice_spike"));
      }

      public ResourceLocation getModelResource(IceSpikeEntity spike) {
         return switch (spike.getScale()) {
            case 1 -> this.tinyPath;
            case 2 -> this.mediumPath;
            case 3 -> this.smallPath;
            default -> this.bigPath;
         };
      }
   }
}
