package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.AbstractDuskLightBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class DuskLightRenderer<T extends AbstractDuskLightBlockEntity> implements BlockEntityRenderer<T> {
   public static final ResourceLocation DUSK_BEAM_TEXTURE = EternalStarlight.id("textures/entity/dusk_beam.png");

   public DuskLightRenderer(Context context) {
   }

   public void render(T blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
      for (Direction direction : Direction.values()) {
         float progress = Mth.lerp(
            partialTicks, blockEntity.getOldBeamProgresses().getOrDefault(direction, 0.0F), blockEntity.getBeamProgresses().getOrDefault(direction, 0.0F)
         );
         Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
         Vec3 sight = camera.getPosition().subtract(blockEntity.getBlockPos().getCenter());
         Vec3 start = new Vec3(0.5, 0.5, 0.5).add(new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ()).scale(0.5));
         Vec3 end = start.add(new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ()).scale(progress * 30.0F));
         Vec3 sideOffset = end.subtract(start).cross(sight).normalize().scale(0.25);
         Pose pose = poseStack.last();
         VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(DUSK_BEAM_TEXTURE));
         vertexConsumer.addVertex(pose, start.add(sideOffset).toVector3f())
            .setColor(-1)
            .setUv(0.0F, 0.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, start.add(sideOffset.scale(-1.0)).toVector3f())
            .setColor(-1)
            .setUv(0.0F, 1.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, end.add(sideOffset.scale(-1.0)).toVector3f())
            .setColor(-1)
            .setUv(1.0F, 1.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
         vertexConsumer.addVertex(pose, end.add(sideOffset).toVector3f())
            .setColor(-1)
            .setUv(1.0F, 0.0F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880)
            .setNormal(pose, 0.0F, 1.0F, 0.0F);
      }
   }

   public boolean shouldRenderOffScreen(T blockEntity) {
      return true;
   }

   public int getViewDistance() {
      return 256;
   }
}
