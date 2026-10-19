package dev.foxboss.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.foxboss.entity.ThrownBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public final class ThrownBlockRenderer extends EntityRenderer<ThrownBlockEntity> {
   private final BlockRenderDispatcher blocks;

   public ThrownBlockRenderer(Context context) {
      super(context);
      this.blocks = context.getBlockRenderDispatcher();
   }

   public void render(ThrownBlockEntity entity, float yaw, float partialTick, PoseStack pose, MultiBufferSource buffer, int light) {
      pose.pushPose();
      pose.translate(0.0, 0.4, 0.0);
      float spin = (entity.tickCount + partialTick) * 12.0F;
      pose.mulPose(Axis.YP.rotationDegrees(spin));
      pose.mulPose(Axis.XP.rotationDegrees(spin * 0.7F));
      pose.scale(0.8F, 0.8F, 0.8F);
      pose.translate(-0.5, -0.5, -0.5);
      this.blocks.renderSingleBlock(entity.blockState(), pose, buffer, light, OverlayTexture.NO_OVERLAY);
      pose.popPose();
      super.render(entity, yaw, partialTick, pose, buffer, light);
   }

   public ResourceLocation getTextureLocation(ThrownBlockEntity entity) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
