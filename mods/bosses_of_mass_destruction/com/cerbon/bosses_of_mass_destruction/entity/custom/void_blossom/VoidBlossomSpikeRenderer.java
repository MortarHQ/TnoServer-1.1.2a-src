package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopies;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import java.util.Map.Entry;
import java.util.function.Function;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class VoidBlossomSpikeRenderer implements IRenderer<VoidBlossomEntity> {
   private final ResourceLocation spikeTexture = ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "textures/entity/void_blossom_spike.png");
   private final RenderType type = RenderType.entityCutoutNoCull(this.spikeTexture);

   public void render(VoidBlossomEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      for (Entry<BlockPos, VoidBlossomClientSpikeHandler.Spike> kv : entity.clientSpikeHandler.getSpikes().entrySet()) {
         this.renderBeam(entity, kv.getValue(), partialTicks, poseStack, buffer, this.type);
      }
   }

   private void renderBeam(
      LivingEntity actor, VoidBlossomClientSpikeHandler.Spike spike, float tickDelta, PoseStack poseStack, MultiBufferSource bufferSource, RenderType type
   ) {
      float numTextures = 8.0F;
      float lifeRatio = 2.0F;
      float textureProgress = Math.max(0.0F, (spike.age() + tickDelta) * lifeRatio / spike.maxAge() - lifeRatio + 1.0F);
      if (!(textureProgress >= 1.0F)) {
         float spikeHeight = spike.height();
         float textureRatio = 0.34375F;
         float spikeWidth = textureRatio * spikeHeight * 0.5F;
         double upProgress = (Math.sin(Math.min((spike.age() + tickDelta) / (spike.maxAge() * 0.4), 1.0) * Math.PI * 0.5) - 1.0) * spikeHeight;
         Function<Float, Float> texTransformer = this.textureMultiplier(1.0F / numTextures, (float)(Math.floor(textureProgress * numTextures) / numTextures));
         poseStack.pushPose();
         Vec3 offset = VanillaCopies.fromLerpedPosition(actor, 0.0, tickDelta).subtract(spike.pos());
         poseStack.translate(-offset.x, upProgress - offset.y, -offset.z);
         Vec3 bottomPos = spike.offset();
         float n = (float)Math.acos(bottomPos.y);
         float o = (float)Math.atan2(bottomPos.z, bottomPos.x);
         poseStack.mulPose(Axis.YP.rotationDegrees(((float) (Math.PI / 2) - o) * (180.0F / (float)Math.PI)));
         poseStack.mulPose(Axis.XP.rotationDegrees(n * (180.0F / (float)Math.PI)));
         float q = 0.0F;
         int red = (int)(Vec3Colors.WHITE.x * 255.0);
         int green = (int)(Vec3Colors.WHITE.y * 255.0);
         int blue = (int)(Vec3Colors.WHITE.z * 255.0);
         float af = Mth.cos(q + (float) Math.PI) * spikeWidth;
         float ag = Mth.sin(q + (float) Math.PI) * spikeWidth;
         float ah = Mth.cos(q + 0.0F) * spikeWidth;
         float ai = Mth.sin(q + 0.0F) * spikeWidth;
         float aj = Mth.cos(q + (float) (Math.PI / 2)) * spikeWidth;
         float ak = Mth.sin(q + (float) (Math.PI / 2)) * spikeWidth;
         float al = Mth.cos(q + (float) (Math.PI * 3.0 / 2.0)) * spikeWidth;
         float am = Mth.sin(q + (float) (Math.PI * 3.0 / 2.0)) * spikeWidth;
         VertexConsumer vertexConsumer = bufferSource.getBuffer(type);
         Pose entry = poseStack.last();
         Matrix4f matrix4f = entry.pose();
         float c0 = texTransformer.apply(0.4999F);
         float c2 = texTransformer.apply(0.0F);
         float c1 = texTransformer.apply(1.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, entry, af, spikeHeight, ag, red, green, blue, c0, 0.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, entry, af, 0.0F, ag, red, green, blue, c0, 1.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, entry, ah, 0.0F, ai, red, green, blue, c2, 1.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, entry, ah, spikeHeight, ai, red, green, blue, c2, 0.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, entry, aj, spikeHeight, ak, red, green, blue, c1, 0.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, entry, aj, 0.0F, ak, red, green, blue, c1, 1.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, entry, al, 0.0F, am, red, green, blue, c0, 1.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, entry, al, spikeHeight, am, red, green, blue, c0, 0.0F);
         poseStack.popPose();
      }
   }

   public Function<Float, Float> textureMultiplier(Float multiplier, float adjustment) {
      return texCoord -> texCoord * multiplier + adjustment;
   }
}
