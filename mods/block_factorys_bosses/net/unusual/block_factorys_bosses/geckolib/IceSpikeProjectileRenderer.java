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
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeProjectileEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IceSpikeProjectileRenderer extends GeoEntityRenderer<IceSpikeProjectileEntity> {
   public static ResourceLocation TEXTURE = BossesRise.prefix("textures/entity/ice_spike_cluster.png");

   public IceSpikeProjectileRenderer(Context context) {
      super(context, new CustomEntityGeoModel<IceSpikeProjectileEntity>(BossesRise.prefix("ice_spike_projectile")) {
         public ResourceLocation getTextureResource(IceSpikeProjectileEntity animatable) {
            return IceSpikeProjectileRenderer.TEXTURE;
         }
      });
   }

   public void defaultRender(
      PoseStack poseStack,
      IceSpikeProjectileEntity animatable,
      MultiBufferSource bufferSource,
      @Nullable RenderType renderType,
      @Nullable VertexConsumer buffer,
      float yaw,
      float partialTick,
      int packedLight
   ) {
      poseStack.pushPose();
      poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, animatable.yRotO, animatable.getYRot()) - 90.0F));
      poseStack.mulPose(Axis.ZP.rotationDegrees(270.0F + Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot())));
      super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
      poseStack.popPose();
   }
}
