package io.github.Memoires.mysticism.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Axis;
import io.github.Memoires.mysticism.client.model.BlackHoleModel;
import io.github.Memoires.mysticism.entity.magic.misc.BlackHoleEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BlackHoleRenderer extends EntityRenderer<BlackHoleEntity> {
   private final BlackHoleModel model;
   private static final ResourceLocation WHITE_TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/white_concrete.png");
   private static final RenderType CEL_BEAM_OUTLINE = RenderType.create(
      "cel_beam_outline",
      DefaultVertexFormat.NEW_ENTITY,
      Mode.QUADS,
      1536,
      false,
      true,
      CompositeState.builder()
         .setShaderState(RenderStateShard.RENDERTYPE_BEACON_BEAM_SHADER)
         .setTextureState(new TextureStateShard(WHITE_TEXTURE, false, false))
         .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
         .setWriteMaskState(RenderStateShard.COLOR_WRITE)
         .createCompositeState(false)
   );

   public BlackHoleRenderer(Context context) {
      super(context);
      this.model = new BlackHoleModel(context.bakeLayer(ModModelLayers.SPINNING_CUBE));
   }

   public void render(BlackHoleEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      poseStack.pushPose();
      float time = entity.tickCount + partialTicks;
      int fullbright = 15728880;
      float currentGrowthScale = entity.getSizeModifier();
      poseStack.translate(0.0, 0.5 * currentGrowthScale, 0.0);
      poseStack.scale(currentGrowthScale, currentGrowthScale, currentGrowthScale);
      float speedMultiplier = 3.0F;
      int deathWindow = 200;
      if (entity.tickCount >= entity.getMaxLifetime() - deathWindow) {
         float ticksSinceDeath = time - (entity.getMaxLifetime() - deathWindow);
         float deathProgress = ticksSinceDeath / deathWindow;
         deathProgress = Mth.clamp(deathProgress, 0.0F, 1.0F);
         float speed = deathProgress * deathProgress * deathProgress;
         speedMultiplier = Mth.lerp(speed, 3.0F, 25.0F);
         float visualScaleReduction = Mth.lerp(deathProgress, 1.0F, 0.2F);
         poseStack.scale(visualScaleReduction, visualScaleReduction, visualScaleReduction);
      }

      poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
      poseStack.mulPose(Axis.ZP.rotationDegrees(45.0F));
      float dynamicYRotation = time * speedMultiplier;
      poseStack.mulPose(Axis.YP.rotationDegrees(dynamicYRotation));
      poseStack.pushPose();
      float thicknessScale = 1.1F;
      poseStack.scale(thicknessScale, thicknessScale, thicknessScale);
      VertexConsumer outlineVC = buffer.getBuffer(CEL_BEAM_OUTLINE);
      this.model.renderToBuffer(poseStack, outlineVC, fullbright, OverlayTexture.NO_OVERLAY);
      poseStack.popPose();
      VertexConsumer portalVC = buffer.getBuffer(RenderType.endPortal());
      this.model.renderToBuffer(poseStack, portalVC, fullbright, OverlayTexture.NO_OVERLAY);
      poseStack.popPose();
   }

   public ResourceLocation getTextureLocation(BlackHoleEntity entity) {
      return WHITE_TEXTURE;
   }
}
