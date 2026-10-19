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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeClusterEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IceSpikeClusterRenderer extends GeoEntityRenderer<IceSpikeClusterEntity> {
   public static ResourceLocation PROJECTILE_MODEL = BossesRise.prefix("geo/entity/ice_spike_projectile.geo.json");
   private static final float GRAB_TIME = 5.0F;

   public IceSpikeClusterRenderer(Context context) {
      super(context, new CustomEntityGeoModel<IceSpikeClusterEntity>(BossesRise.prefix("ice_spike_cluster")) {
         public ResourceLocation getModelResource(IceSpikeClusterEntity animatable) {
            return animatable.getHeld() ? IceSpikeClusterRenderer.PROJECTILE_MODEL : super.getModelResource(animatable);
         }
      });
      this.addRenderLayer(new DangerZonesRenderLayer(this));
   }

   public void defaultRender(
      PoseStack poseStack,
      IceSpikeClusterEntity animatable,
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
      } else if (!(animatable.getOwner() instanceof Player player && !player.isUsingItem() && animatable.getHeld())) {
         poseStack.pushPose();
         if (animatable.getOwner() instanceof Player player && player.isUsingItem() && animatable.getHeld()) {
            float transition = (player.getTicksUsingItem() + partialTick) / 5.0F;
            if (transition > 1.0F) {
               transition = 1.0F;
            }

            float spin = player.getTicksUsingItem() + partialTick - 5.0F;
            if (spin <= 0.0F) {
               spin = 0.0F;
            }

            poseStack.translate(0.0F, Mth.lerp(transition, 0.0F, animatable.getBbHeight() * 0.5F), 0.0F);
            poseStack.mulPose(Axis.YP.rotationDegrees(-player.getViewYRot(partialTick) + 180.0F));
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(transition, 0.0F, -90.0F - player.getViewXRot(partialTick))));
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(transition, 0.0F, spin * 12.0F)));
         } else {
            float yRot = animatable.getViewYRot(partialTick);
            if ((Integer)animatable.getEntityData().get(IceSpikeClusterEntity.DATA_HIT_ANIMATION_TIME) > 0) {
               yRot += (float)(Math.cos(animatable.tickCount * 3.25) * Math.PI * 0.4F);
            }

            poseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
         }

         float trueTick = animatable.tickCount - animatable.getDelay() + partialTick;
         float factor = 5.0F;
         float size = trueTick * trueTick / (factor * factor);
         if (size >= 1.0F) {
            size = 1.0F;
         }

         if (size > 0.0F) {
            poseStack.scale(size, size, size);
            poseStack.translate(0.0F, -0.25F, 0.0F);
            super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
         }

         poseStack.popPose();
      }
   }
}
