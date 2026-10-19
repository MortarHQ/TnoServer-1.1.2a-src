package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.renderer;

import com.finderfeed.fdlib.data_structures.Pair;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimatedObject;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimationSystem;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.BoneTransformationController;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.LayerAttachments;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.ModelSystem;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachment;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachmentRenderContext;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachmentRenderer;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachmentType;
import com.finderfeed.fdlib.systems.bedrock.models.FDModel;
import com.finderfeed.fdlib.systems.bedrock.models.FDModelPart;
import com.finderfeed.fdlib.util.FDColor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Function;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix4f;

public class FDEntityRenderer<T extends Entity & AnimatedObject> extends EntityRenderer<T> {
   public List<FDEntityRenderLayer<T>> layers;
   public IShouldEntityRender<T> shouldRender;
   public FDFreeEntityRenderer<T> freeRender;
   public FDEntityIgnoreYaw<T> ignoreYaw;

   public FDEntityRenderer(
      Context ctx,
      FDEntityIgnoreYaw<T> ignoreYaw,
      IShouldEntityRender<T> shouldRender,
      List<FDEntityRenderLayerOptions<T>> layerDefinitions,
      FDFreeEntityRenderer<T> freeEntityRenderer
   ) {
      super(ctx);
      this.freeRender = freeEntityRenderer;
      this.shouldRender = shouldRender;
      this.ignoreYaw = ignoreYaw;
      this.layers = new ArrayList<>();

      for (FDEntityRenderLayerOptions<T> layer : layerDefinitions) {
         FDModel model = new FDModel(layer.layerModel.get());
         FDEntityRenderLayer<T> l = new FDEntityRenderLayer<>(
            model, layer.renderType, layer.renderCondition, layer.transform, layer.layerColor, layer.boneControllers, layer.ignoreHurtOverlay, layer.light
         );
         this.layers.add(l);
      }
   }

   public void render(T entity, float yaw, float partialTicks, PoseStack matrices, MultiBufferSource src, int light) {
      super.render(entity, yaw, partialTicks, matrices, src, light);
      this.applyAnimations(entity, yaw, partialTicks, matrices, src, light);
      this.renderLayers(entity, yaw, partialTicks, matrices, src, light);
      renderModelAttachments(this.layers, entity, yaw, partialTicks, matrices, src, light);
      if (this.freeRender != null) {
         this.freeRender.render(entity, yaw, partialTicks, matrices, src, light);
      }
   }

   public void applyAnimations(T entity, float yaw, float partialTicks, PoseStack matrices, MultiBufferSource src, int light) {
      AnimationSystem system = entity.getAnimationSystem();
      boolean ignoreYaw = false;
      if (this.ignoreYaw != null) {
         ignoreYaw = this.ignoreYaw.ignoreYaw(entity, partialTicks);
      }

      for (FDEntityRenderLayer<T> layer : this.layers) {
         FDModel model = layer.model();
         system.applyAnimations(model, partialTicks);
         if (!ignoreYaw) {
            model.main.addYRot(-yaw);
         }

         for (Entry<String, BoneTransformationController<T>> entry : layer.boneControllers().entrySet()) {
            FDModelPart modelPart = model.getModelPart(entry.getKey());
            if (modelPart != null) {
               BoneTransformationController<T> controller = entry.getValue();
               int overlay = OverlayTexture.NO_OVERLAY;
               if (entity instanceof LivingEntity livingEntity && !layer.ignoreHurtOverlay()) {
                  overlay = LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F);
               }

               controller.transformBone(entity, model, modelPart, matrices, src, entry.getKey(), light, overlay, partialTicks);
            }
         }
      }
   }

   public void renderLayers(T entity, float yaw, float partialTicks, PoseStack matrices, MultiBufferSource src, int light) {
      for (FDEntityRenderLayer<T> layer : this.layers) {
         Function<T, Boolean> condition = layer.renderCondition();
         if (condition.apply(entity)) {
            matrices.pushPose();
            FDModel model = layer.model();
            RenderType type = layer.renderType().getValue(entity, partialTicks);
            VertexConsumer consumer = src.getBuffer(type);
            int overlay = OverlayTexture.NO_OVERLAY;
            if (entity instanceof LivingEntity livingEntity && !layer.ignoreHurtOverlay()) {
               overlay = LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F);
            }

            layer.matrixTransform().apply(entity, matrices, partialTicks);
            FDColor color = layer.color().getValue(entity, partialTicks);
            int l = light;
            if (layer.light() != -1) {
               l = layer.light();
            }

            model.render(matrices, consumer, l, overlay, color.r, color.g, color.b, color.a);
            matrices.popPose();
         }
      }
   }

   public static <T extends Entity & AnimatedObject> void renderModelAttachments(
      List<FDEntityRenderLayer<T>> layers, T entity, float yaw, float partialTicks, PoseStack matrices, MultiBufferSource src, int light
   ) {
      ModelSystem modelSystem = entity.getModelSystem();

      for (int layer : modelSystem.allLayers()) {
         if (layer >= 0 && layer < layers.size()) {
            FDEntityRenderLayer<T> layer1 = layers.get(layer);
            Function<T, Boolean> condition = layer1.renderCondition();
            if (condition.apply(entity)) {
               LayerAttachments layerAttachments = modelSystem.getLayerAttachments(layer);
               int overlay = OverlayTexture.NO_OVERLAY;
               if (entity instanceof LivingEntity livingEntity && !layer1.ignoreHurtOverlay()) {
                  overlay = LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F);
               }

               for (String bone : layerAttachments.getAllBones()) {
                  matrices.pushPose();
                  Matrix4f transform = layer1.model().getModelPartTransformation(bone);
                  layer1.matrixTransform().apply(entity, matrices, partialTicks);
                  matrices.mulPose(transform);

                  for (Pair<UUID, ModelAttachment<?, ?>> pair : layerAttachments.getAllBoneAttachments(bone)) {
                     ModelAttachment attachment = pair.second;
                     ModelAttachmentType type = attachment.attachmentData().type();
                     ModelAttachmentRenderer renderer = type.renderer();
                     renderer.render((T)attachment, ModelAttachmentRenderContext.create(entity), modelSystem, matrices, src, partialTicks, light, overlay);
                  }

                  matrices.popPose();
               }
            }
         }
      }
   }

   public ResourceLocation getTextureLocation(T p_114482_) {
      return TextureAtlas.LOCATION_BLOCKS;
   }

   public boolean shouldRender(T entity, Frustum frustum, double x, double y, double z) {
      return this.shouldRender != null ? this.shouldRender.shouldRender(entity, frustum, x, y, z) : super.shouldRender(entity, frustum, x, y, z);
   }
}
