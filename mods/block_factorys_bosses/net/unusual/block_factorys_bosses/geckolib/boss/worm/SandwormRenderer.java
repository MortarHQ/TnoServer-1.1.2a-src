package net.unusual.block_factorys_bosses.geckolib.boss.worm;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import java.util.function.BiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.unusual.block_factorys_bosses.entity.boss.sandworm.SandwormEntity;
import net.unusual.block_factorys_bosses.entity.boss.sandworm.SandwormEntityPart;
import net.unusual.block_factorys_bosses.geckolib.DangerZonesRenderLayer;
import net.unusual.block_factorys_bosses.geckolib.boneCache.BoneData;
import net.unusual.block_factorys_bosses.geckolib.boneCache.BoneDataCache;
import net.unusual.block_factorys_bosses.geckolib.boneCache.BoneDataCacheLayer;
import net.unusual.block_factorys_bosses.geckolib.boss.CinematicRenderer;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class SandwormRenderer extends GeoEntityRenderer<SandwormEntity> implements CinematicRenderer<SandwormEntity> {
   public SandwormRenderer(Context context) {
      super(context, new SandwormModel());
      this.addRenderLayer(new DangerZonesRenderLayer(this));
      this.addRenderLayer(new SandwormRenderer.SandwormDamageRenderLayer());
      this.addRenderLayer(BoneDataCacheLayer.forEntity(this).withAlwaysWanted("camera"));
   }

   public void preRender(
      PoseStack poseStack,
      SandwormEntity animatable,
      BakedGeoModel model,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      animatable.setRendererVariables();
      super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
   }

   public void actuallyRender(
      PoseStack poseStack,
      SandwormEntity animatable,
      BakedGeoModel model,
      @Nullable RenderType renderType,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      if (!animatable.isHiddenUnderground()) {
         super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      }
   }

   public void postRender(
      PoseStack poseStack,
      SandwormEntity sandworm,
      BakedGeoModel model,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      super.postRender(poseStack, sandworm, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      if (sandworm.realDisplayFireAnimation()) {
         this.renderFire(poseStack, sandworm, model, bufferSource);
      }
   }

   private void renderFire(PoseStack poseStack, SandwormEntity sandworm, BakedGeoModel model, MultiBufferSource bufferSource) {
      SandwormEntityPart[] parts = sandworm.getParts();

      for (int partIndex = 0; partIndex <= 4; partIndex++) {
         SandwormEntityPart part = parts[partIndex];
         float scale = 4.0F - 0.8F * Math.abs(partIndex - 1);
         this.renderFireOnPart(poseStack, sandworm, model, bufferSource, part, scale);
      }

      for (int partIndex = 0; partIndex <= 3; partIndex++) {
         SandwormEntityPart part = parts[parts.length - partIndex - 1];
         float scale = 4.0F - 1.7F * Math.abs(partIndex - 1);
         this.renderFireOnPart(poseStack, sandworm, model, bufferSource, part, scale);
      }
   }

   private void renderFireOnPart(
      PoseStack poseStack, SandwormEntity sandworm, BakedGeoModel model, MultiBufferSource bufferSource, SandwormEntityPart part, float fireScale
   ) {
      model.getBone(part.getAnchorBoneName())
         .ifPresent(
            bone -> {
               poseStack.pushPose();
               poseStack.translate(bone.getLocalPosition().x(), bone.getLocalPosition().y() - 0.8F, bone.getLocalPosition().z());
               poseStack.scale(fireScale, fireScale, fireScale);
               EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
               entityRenderDispatcher.renderFlame(
                  poseStack, bufferSource, sandworm, Mth.rotationAroundAxis(Mth.Y_AXIS, entityRenderDispatcher.cameraOrientation(), new Quaternionf())
               );
               poseStack.popPose();
            }
         );
   }

   public Pair<Float, Float> getCinematicYawAndPitch(SandwormEntity animatable, GeoBone cameraBone, LocalPlayer player) {
      Vector3f lookDir = new Vector3f(0.0F, 0.0F, 1.0F);
      BoneData boneData = BoneDataCache.getInitializedBoneData(animatable, cameraBone.getName());
      if (boneData != null) {
         boneData.worldTransform.transformDirection(lookDir);
      }

      float yaw = (float)Math.atan2(lookDir.x(), -lookDir.z()) * (180.0F / (float)Math.PI);
      float pitch = (float)Math.atan2(lookDir.y(), Mth.sqrt(lookDir.x() * lookDir.x() + lookDir.z() * lookDir.z())) * (180.0F / (float)Math.PI);
      return new Pair(yaw, pitch);
   }

   public class SandwormDamageRenderLayer extends GeoRenderLayer<SandwormEntity> {
      public SandwormDamageRenderLayer() {
         super(SandwormRenderer.this);
      }

      private void updateBones(BakedGeoModel model, SandwormEntity sandworm, BiConsumer<GeoBone, SandwormEntity> callback) {
         for (GeoBone bone : model.topLevelBones()) {
            this.updateBoneAndChildren(bone, sandworm, callback);
         }
      }

      private void updateBoneAndChildren(GeoBone bone, SandwormEntity sandworm, BiConsumer<GeoBone, SandwormEntity> callback) {
         callback.accept(bone, sandworm);

         for (GeoBone childBone : bone.getChildBones()) {
            this.updateBoneAndChildren(childBone, sandworm, callback);
         }
      }

      private void preRenderBone(GeoBone bone, SandwormEntity sandworm) {
         int segmentIndex = sandworm.getSegmentIndexFromBoneName(bone.getName());
         bone.setHidden(segmentIndex == -1 || !sandworm.isSegmentDamaged(segmentIndex));
         bone.setChildrenHidden(false);
      }

      private void postRenderBone(GeoBone bone, SandwormEntity sandworm) {
         bone.setHidden(false);
      }

      protected ResourceLocation getTextureResource(SandwormEntity animatable) {
         return ((SandwormModel)this.getGeoModel()).buildFormattedTexturePath(SandwormModel.SANDWORM_DAMAGED_BASE_LOCATION);
      }

      protected RenderType getRenderType(SandwormEntity animatable, @Nullable MultiBufferSource bufferSource, float partialTick) {
         return this.getRenderer().getRenderType(animatable, this.getTextureResource(animatable), bufferSource, partialTick);
      }

      public void render(
         PoseStack poseStack,
         SandwormEntity animatable,
         BakedGeoModel bakedModel,
         @Nullable RenderType renderType,
         MultiBufferSource bufferSource,
         @Nullable VertexConsumer buffer,
         float partialTick,
         int packedLight,
         int packedOverlay
      ) {
         renderType = this.getRenderType(animatable, bufferSource, partialTick);
         if (renderType != null) {
            this.updateBones(bakedModel, animatable, this::preRenderBone);
            this.getRenderer()
               .reRender(
                  bakedModel,
                  poseStack,
                  bufferSource,
                  animatable,
                  renderType,
                  bufferSource.getBuffer(renderType),
                  partialTick,
                  15728640,
                  packedOverlay,
                  this.getRenderer().getRenderColor(animatable, partialTick, packedLight).argbInt()
               );
            this.updateBones(bakedModel, animatable, this::postRenderBone);
         }
      }
   }
}
