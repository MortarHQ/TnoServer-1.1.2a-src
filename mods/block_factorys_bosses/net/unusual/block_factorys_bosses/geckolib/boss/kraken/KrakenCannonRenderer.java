package net.unusual.block_factorys_bosses.geckolib.boss.kraken;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Objects;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.util.Mth;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.decoration.CannonEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class KrakenCannonRenderer extends GeoEntityRenderer<CannonEntity> {
   public KrakenCannonRenderer(Context context) {
      super(context, new CustomEntityGeoModel(BossesRise.prefix("kraken_cannon")));
   }

   public void preRender(
      PoseStack poseStack,
      CannonEntity animatable,
      BakedGeoModel model,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      model.getBone("rotating_horizontal")
         .ifPresent(bone -> bone.setRotY(-Mth.lerp(partialTick, animatable.yRotO, animatable.getYRot()) * (float) (Math.PI / 180.0)));
      model.getBone("rotating_vertical")
         .ifPresent(bone -> bone.setRotX(-Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot()) * (float) (Math.PI / 180.0)));
   }

   public void defaultRender(
      PoseStack poseStack,
      CannonEntity cannon,
      MultiBufferSource bufferSource,
      @Nullable RenderType renderType,
      @Nullable VertexConsumer buffer,
      float yaw,
      float partialTick,
      int packedLight
   ) {
      poseStack.pushPose();
      float hurtTime = cannon.getHurtTime() - partialTick;
      float damage = cannon.getDamage() - partialTick;
      if (damage < 0.0F) {
         damage = 0.0F;
      }

      if (hurtTime > 0.0F) {
         poseStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(hurtTime) * hurtTime * damage / 25.0F * cannon.getHurtDir()));
      }

      super.defaultRender(poseStack, cannon, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
      poseStack.popPose();
   }

   public void renderRecursively(
      PoseStack poseStack,
      CannonEntity cannon,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      poseStack.pushPose();
      RenderUtil.translateMatrixToBone(poseStack, bone);
      RenderUtil.translateToPivotPoint(poseStack, bone);
      RenderUtil.rotateMatrixAroundBone(poseStack, bone);
      RenderUtil.scaleMatrixForBone(poseStack, bone);
      if (Objects.equals(bone.getName(), "seat")) {
         Matrix4f poseState = new Matrix4f(poseStack.last().pose());
         Matrix4f localMatrix = RenderUtil.invertAndMultiplyMatrices(poseState, this.entityRenderTranslations);
         bone.setModelSpaceMatrix(RenderUtil.invertAndMultiplyMatrices(poseState, this.modelRenderTranslations));
         localMatrix.translate(new Vector3f(this.getRenderOffset((CannonEntity)this.animatable, 1.0F).toVector3f()));
         bone.setLocalSpaceMatrix(localMatrix);
         Matrix4f worldState = new Matrix4f(localMatrix);
         worldState.translate(new Vector3f(((CannonEntity)this.animatable).position().toVector3f()));
         bone.setWorldSpaceMatrix(worldState);
         cannon.bonePos = bone.getLocalPosition();
      } else if (bone.isTrackingMatrices()) {
         Matrix4f poseState = new Matrix4f(poseStack.last().pose());
         Matrix4f localMatrix = RenderUtil.invertAndMultiplyMatrices(poseState, this.entityRenderTranslations);
         bone.setModelSpaceMatrix(RenderUtil.invertAndMultiplyMatrices(poseState, this.modelRenderTranslations));
         bone.setLocalSpaceMatrix(RenderUtil.translateMatrix(localMatrix, this.getRenderOffset((CannonEntity)this.animatable, 1.0F).toVector3f()));
         bone.setWorldSpaceMatrix(RenderUtil.translateMatrix(new Matrix4f(localMatrix), ((CannonEntity)this.animatable).position().toVector3f()));
      }

      RenderUtil.translateAwayFromPivotPoint(poseStack, bone);
      this.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, colour);
      if (!isReRender) {
         this.applyRenderLayersForBone(poseStack, cannon, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
      }

      this.renderChildBones(poseStack, cannon, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      poseStack.popPose();
   }
}
