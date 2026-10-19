package io.github.Memoires.mysticism.client.renderer.entity.skill;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.Memoires.mysticism.ability.skill.unique.EngineerSkill;
import io.github.Memoires.mysticism.client.model.BubbleShieldBarrierEntityModel;
import io.github.Memoires.mysticism.entity.skill.BubbleShieldBarrierEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BubbleShieldBarrierRenderer extends GeoEntityRenderer<BubbleShieldBarrierEntity> {
   public BubbleShieldBarrierRenderer(Context renderManager) {
      super(renderManager, new BubbleShieldBarrierEntityModel());
      this.shadowRadius = 0.0F;
   }

   @Nullable
   public RenderType getRenderType(BubbleShieldBarrierEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucent(this.getTextureLocation(animatable));
   }

   public void preRender(
      PoseStack poseStack,
      BubbleShieldBarrierEntity animatable,
      BakedGeoModel model,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      float scale = EngineerSkill.CONFIG.bubbleShieldSize / 3.0F;
      poseStack.scale(scale, scale, scale);
      super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
   }
}
