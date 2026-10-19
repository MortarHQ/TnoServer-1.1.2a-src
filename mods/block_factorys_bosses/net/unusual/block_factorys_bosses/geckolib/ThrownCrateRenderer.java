package net.unusual.block_factorys_bosses.geckolib;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.projectile.ThrownCrateEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ThrownCrateRenderer extends GeoEntityRenderer<ThrownCrateEntity> {
   public ThrownCrateRenderer(Context context) {
      super(context, new CustomEntityGeoModel(BossesRise.prefix("crate")));
   }

   public void defaultRender(
      PoseStack poseStack,
      ThrownCrateEntity animatable,
      MultiBufferSource bufferSource,
      @Nullable RenderType renderType,
      @Nullable VertexConsumer buffer,
      float yaw,
      float partialTick,
      int packedLight
   ) {
      poseStack.pushPose();
      float time = animatable.getId() + animatable.tickCount + partialTick;
      poseStack.mulPose(Axis.YP.rotationDegrees(-animatable.getViewYRot(partialTick) + 180.0F + time * 2.0F));
      poseStack.mulPose(Axis.XP.rotationDegrees(animatable.getViewXRot(partialTick) + time * 3.5F));
      poseStack.translate(0.0F, 0.0F, 0.0F);
      super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
      poseStack.popPose();
   }
}
