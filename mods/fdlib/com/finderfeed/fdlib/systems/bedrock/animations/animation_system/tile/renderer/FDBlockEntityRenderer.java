package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.tile.renderer;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimatedObject;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimationSystem;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.BoneTransformationController;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.RenderFunction;
import com.finderfeed.fdlib.systems.bedrock.models.FDModel;
import com.finderfeed.fdlib.systems.bedrock.models.FDModelPart;
import com.finderfeed.fdlib.util.FDColor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class FDBlockEntityRenderer<T extends BlockEntity & AnimatedObject> implements BlockEntityRenderer<T> {
   private IShouldBERender<T> shouldBERender;
   private IBERenderOffScreen<T> renderOffScreen;
   private List<FDBlockEntityRenderLayer<T>> layers;
   private FDFreeBERenderer<T> freeRender;
   private RenderFunction<BlockEntity, AABB> renderBox;

   public FDBlockEntityRenderer(
      Context ctx,
      IShouldBERender<T> shouldBERender,
      IBERenderOffScreen<T> shouldRenderOffScreen,
      List<FDBlockRenderLayerOptions<T>> layers,
      FDFreeBERenderer<T> freeRender,
      RenderFunction<BlockEntity, AABB> renderBox
   ) {
      this.freeRender = freeRender;
      this.shouldBERender = shouldBERender;
      this.renderOffScreen = shouldRenderOffScreen;
      this.layers = new ArrayList<>();
      this.renderBox = renderBox;

      for (FDBlockRenderLayerOptions<T> layer : layers) {
         FDModel model = new FDModel(layer.layerModel.get());
         FDBlockEntityRenderLayer<T> l = new FDBlockEntityRenderLayer<>(
            model, layer.renderType, layer.renderCondition, layer.transform, layer.layerColor, layer.boneControllers, layer.light
         );
         this.layers.add(l);
      }
   }

   public void render(T blockEntity, float pticks, PoseStack matrices, MultiBufferSource src, int light, int overlay) {
      if (blockEntity.getLevel() != null) {
         matrices.pushPose();
         matrices.translate(0.5, 0.0, 0.5);
         this.applyAnimations(blockEntity, pticks, matrices, src, light, overlay);
         this.renderLayers(blockEntity, pticks, matrices, src, light, overlay);
         if (this.freeRender != null) {
            this.freeRender.render(blockEntity, pticks, matrices, src, light, overlay);
         }

         matrices.popPose();
      }
   }

   public void applyAnimations(T entity, float partialTicks, PoseStack matrices, MultiBufferSource src, int light, int overlay) {
      AnimationSystem system = entity.getAnimationSystem();
      if (system != null) {
         for (FDBlockEntityRenderLayer<T> layer : this.layers) {
            FDModel model = layer.model();
            system.applyAnimations(model, partialTicks);

            for (Entry<String, BoneTransformationController<T>> entry : layer.boneControllers().entrySet()) {
               FDModelPart modelPart = model.getModelPart(entry.getKey());
               if (modelPart != null) {
                  BoneTransformationController<T> controller = entry.getValue();
                  controller.transformBone(entity, model, modelPart, matrices, src, entry.getKey(), light, overlay, partialTicks);
               }
            }
         }
      }
   }

   public void renderLayers(T entity, float partialTicks, PoseStack matrices, MultiBufferSource src, int light, int overlay) {
      for (FDBlockEntityRenderLayer<T> layer : this.layers) {
         Function<T, Boolean> condition = layer.renderCondition();
         if (condition.apply(entity)) {
            matrices.pushPose();
            FDModel model = layer.model();
            RenderType type = layer.renderType().getValue(entity, partialTicks);
            VertexConsumer consumer = src.getBuffer(type);
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

   public AABB getRenderBoundingBox(T blockEntity) {
      return this.renderBox == null
         ? super.getRenderBoundingBox(blockEntity)
         : this.renderBox.getValue(blockEntity, Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true));
   }

   public boolean shouldRender(T blockEntity, Vec3 idk) {
      return this.shouldBERender.shouldRender(blockEntity, idk);
   }

   public boolean shouldRenderOffScreen(T renderOffScreen) {
      return this.renderOffScreen.shouldRenderOffScreen(renderOffScreen);
   }
}
