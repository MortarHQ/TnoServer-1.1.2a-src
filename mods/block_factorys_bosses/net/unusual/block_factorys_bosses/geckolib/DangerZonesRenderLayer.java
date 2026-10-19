package net.unusual.block_factorys_bosses.geckolib;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import java.util.Collection;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.EasingType;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoQuad;
import software.bernie.geckolib.cache.object.GeoVertex;
import software.bernie.geckolib.cache.texture.AnimatableTexture;
import software.bernie.geckolib.loading.json.raw.FaceUV.Rotation;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.RenderUtil;

public class DangerZonesRenderLayer<T extends GeoAnimatable & DangerZonesProvider> extends GeoRenderLayer<T> {
   private static final ResourceLocation DANGER_ZONE_TEXTURE = ResourceLocation.fromNamespaceAndPath("block_factorys_bosses", "textures/entity/danger_zone.png");
   private static final Double2DoubleFunction GHOST_EASING = EasingType.easeOut(EasingType::quadratic);
   private static final GeoQuad QUAD = GeoQuad.build(
      new GeoVertex[]{new GeoVertex(-0.5, 0.0, -0.5), new GeoVertex(-0.5, 0.0, 0.5), new GeoVertex(0.5, 0.0, 0.5), new GeoVertex(0.5, 0.0, -0.5)},
      0.0F,
      0.0F,
      48.0F,
      48.0F,
      Rotation.NONE,
      48.0F,
      48.0F,
      false,
      Direction.UP
   );

   public DangerZonesRenderLayer(GeoRenderer<T> entityRendererIn) {
      super(entityRendererIn);
   }

   protected ResourceLocation getTextureResource(T animatable) {
      return DANGER_ZONE_TEXTURE;
   }

   public void render(
      PoseStack poseStack,
      T animatable,
      BakedGeoModel bakedModel,
      @Nullable RenderType renderType,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      Collection<DangerZonesProvider.DangerZone> dangerZones = animatable.getDangerZones();
      if (!dangerZones.isEmpty()) {
         renderType = RenderType.entityTranslucent(DANGER_ZONE_TEXTURE);
         if (renderType != null) {
            buffer = bufferSource.getBuffer(renderType);

            for (DangerZonesProvider.DangerZone dangerZone : dangerZones) {
               this.renderDangerZone(dangerZone, poseStack, animatable, buffer, partialTick, packedLight, packedOverlay);
            }
         }
      }
   }

   public void renderDangerZone(
      DangerZonesProvider.DangerZone dangerZone,
      PoseStack poseStack,
      T animatable,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      packedLight = 15728640;
      poseStack.pushPose();
      Vector3f offset = dangerZone.getOffset();
      poseStack.translate(offset.x, offset.y + 0.251, offset.z);
      poseStack.rotateAround(new Quaternionf(new AxisAngle4f(dangerZone.getRotation(), 0.0F, 1.0F, 0.0F)), 0.0F, 0.0F, 0.0F);
      Vector2f scale = dangerZone.getSize();
      poseStack.scale(scale.x, 1.0F, scale.y);
      AnimatableTexture.setAndUpdate(DANGER_ZONE_TEXTURE);
      this.renderQuad(poseStack, buffer, packedLight, packedOverlay, dangerZone.getColor());
      double floatingProgress = RenderUtil.getCurrentTick() % 10.0 / 10.0;
      poseStack.translate(0.0, (Double)GHOST_EASING.apply(floatingProgress) * 0.3F, 0.0);
      this.renderQuad(poseStack, buffer, packedLight, packedOverlay, setColorAlpha(dangerZone.getColor(), 1.0 - floatingProgress * 1.2));
      poseStack.popPose();
   }

   private static int setColorAlpha(int color, double alpha) {
      return color & 16777215 | (int)Math.floor(255.0 * Math.clamp(alpha, 0.0, 1.0)) << 24;
   }

   private void renderQuad(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
      Vector3f normal = poseStack.last().normal().transform(new Vector3f(QUAD.normal()));
      Matrix4f poseState = new Matrix4f(poseStack.last().pose());
      this.getRenderer().createVerticesOfQuad(QUAD, poseState, normal, buffer, packedLight, packedOverlay, color);
   }
}
