package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.StellarRackBlockEntity;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class StellarRackRenderer<T extends StellarRackBlockEntity> implements BlockEntityRenderer<T> {
   private static final RenderType STAR = ESRenderType.entityTranslucentAdditiveGlow(EternalStarlight.id("textures/entity/stellar_rack_shine.png"));

   public StellarRackRenderer(Context context) {
   }

   public void render(T rack, float f, PoseStack stack, MultiBufferSource bufferSource, int light, int overlay) {
      VertexConsumer vertexConsumer = ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(STAR);
      stack.pushPose();
      stack.translate(0.5F, 0.9F, 0.5F);
      stack.pushPose();
      stack.mulPose(new Quaternionf(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation()).rotateZ(rack.getStarRotation(f)));
      Pose pose = stack.last();
      float size = (float)(1.2 + Math.sin((rack.getTickCount() + f) * 0.1 * Math.PI) * 0.4);
      vertexConsumer.addVertex(pose, -size, -size, 0.0F)
         .setColor(rack.getColor(f))
         .setUv(0.0F, 0.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      vertexConsumer.addVertex(pose, -size, size, 0.0F)
         .setColor(rack.getColor(f))
         .setUv(0.0F, 1.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      vertexConsumer.addVertex(pose, size, size, 0.0F)
         .setColor(rack.getColor(f))
         .setUv(1.0F, 1.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      vertexConsumer.addVertex(pose, size, -size, 0.0F)
         .setColor(rack.getColor(f))
         .setUv(1.0F, 0.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      stack.popPose();
      stack.translate(0.01F, 0.01F, 0.01F);
      stack.pushPose();
      stack.mulPose(new Quaternionf(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation()).rotateZ(-rack.getStarRotation(f) / 2.0F));
      pose = stack.last();
      size = (float)(0.9 + Math.sin((rack.getTickCount() + f) * 1.2 * Math.PI) * 0.2);
      vertexConsumer.addVertex(pose, -size, -size, 0.0F)
         .setColor(rack.getColor(100.0F + f))
         .setUv(0.0F, 0.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      vertexConsumer.addVertex(pose, -size, size, 0.0F)
         .setColor(rack.getColor(100.0F + f))
         .setUv(0.0F, 1.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      vertexConsumer.addVertex(pose, size, size, 0.0F)
         .setColor(rack.getColor(100.0F + f))
         .setUv(1.0F, 1.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      vertexConsumer.addVertex(pose, size, -size, 0.0F)
         .setColor(rack.getColor(100.0F + f))
         .setUv(1.0F, 0.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(pose, 0.0F, 1.0F, 0.0F);
      stack.popPose();
      ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
      float angle = 360.0F / (float)rack.getItems().stream().filter(i -> !i.isEmpty()).count();
      float accumulatedAngle = rack.getStarRotation(f) * 0.4F * (180.0F / (float)Math.PI);

      for (ItemStack itemStack : rack.getItems()) {
         if (!itemStack.isEmpty()) {
            accumulatedAngle += angle;
            stack.pushPose();
            Vec3 pos = ESMathUtil.rotationToPosition(0.9F, 0.0F, accumulatedAngle);
            stack.translate(pos.x, 0.0, pos.z);
            stack.mulPose(new Quaternionf().rotateY(rack.getStarRotation(f) * 0.6F));
            renderer.render(
               itemStack,
               ItemDisplayContext.GROUND,
               false,
               stack,
               bufferSource,
               15728880,
               OverlayTexture.NO_OVERLAY,
               renderer.getModel(itemStack, rack.getLevel(), null, 0)
            );
            stack.popPose();
         }
      }

      stack.popPose();
   }
}
