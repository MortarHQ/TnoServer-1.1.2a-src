package net.unusual.block_factorys_bosses.geckolib;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.yeti.GlacialShoveEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GlacialShoveRenderer extends GeoEntityRenderer<GlacialShoveEntity> {
   public GlacialShoveRenderer(Context context) {
      super(context, new CustomEntityGeoModel(BossesRise.prefix("glacial_shove")));
      this.addRenderLayer(new DangerZonesRenderLayer(this));
   }

   public void defaultRender(
      PoseStack poseStack,
      GlacialShoveEntity animatable,
      MultiBufferSource bufferSource,
      @Nullable RenderType renderType,
      @Nullable VertexConsumer buffer,
      float yaw,
      float partialTick,
      int packedLight
   ) {
      if (animatable.tickCount < animatable.getDelay()) {
         RenderType type = this.getRenderType(animatable, this.getTextureLocation(animatable), bufferSource, partialTick);
         if (type == null) {
            return;
         }

         BakedGeoModel model = this.getGeoModel().getBakedModel(this.getGeoModel().getModelResource(animatable, this));
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
      } else {
         poseStack.pushPose();
         float tick = Math.min((animatable.tickCount - animatable.getDelay() + partialTick) / 20.0F * 7.5F, 1.0F);
         poseStack.translate(0.0F, -3.0F + tick * 2.5F, 0.0F);
         super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
         poseStack.popPose();
      }
   }
}
