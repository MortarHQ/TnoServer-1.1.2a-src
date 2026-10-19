package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.renderer;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimatedObject;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.BoneTransformationController;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.RenderFunction;
import com.finderfeed.fdlib.systems.bedrock.models.FDModelInfo;
import com.finderfeed.fdlib.util.FDColor;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;

public class FDEntityRenderLayerOptions<T extends Entity & AnimatedObject> {
   public Supplier<FDModelInfo> layerModel;
   public RenderFunction<T, RenderType> renderType;
   public Function<T, Boolean> renderCondition;
   public FDEntityTransformation<T> transform;
   public RenderFunction<T, FDColor> layerColor;
   public HashMap<String, BoneTransformationController<T>> boneControllers;
   public int light = -1;
   public boolean ignoreHurtOverlay;

   private FDEntityRenderLayerOptions() {
   }

   public static <T extends Entity & AnimatedObject> FDEntityRenderLayerOptions.Builder<T> builder() {
      return new FDEntityRenderLayerOptions.Builder<>();
   }

   public static class Builder<T extends Entity & AnimatedObject> {
      private Supplier<FDModelInfo> layerModel;
      private RenderFunction<T, RenderType> renderType;
      private Function<T, Boolean> renderCondition = entity -> true;
      private FDEntityTransformation<T> transform = (entity, stack, ticks) -> {};
      private RenderFunction<T, FDColor> layerColor = (entity, partialTicks) -> new FDColor(1.0F, 1.0F, 1.0F, 1.0F);
      public HashMap<String, BoneTransformationController<T>> boneControllers = new HashMap<>();
      private boolean ignoreHurtOverlay = false;
      private int light = -1;

      public FDEntityRenderLayerOptions.Builder<T> addBoneController(String bone, BoneTransformationController<T> controller) {
         this.boneControllers.put(bone, controller);
         return this;
      }

      public FDEntityRenderLayerOptions.Builder<T> color(RenderFunction<T, FDColor> layerColorFunction) {
         this.layerColor = layerColorFunction;
         return this;
      }

      public FDEntityRenderLayerOptions.Builder<T> ignoreHurtOverlay(boolean state) {
         this.ignoreHurtOverlay = state;
         return this;
      }

      public FDEntityRenderLayerOptions.Builder<T> light(int light) {
         this.light = light;
         return this;
      }

      public FDEntityRenderLayerOptions.Builder<T> color(float r, float g, float b, float a) {
         return this.color((entity, partialTicks) -> new FDColor(r, g, b, a));
      }

      public FDEntityRenderLayerOptions.Builder<T> model(Supplier<FDModelInfo> infoSupplier) {
         this.layerModel = infoSupplier;
         return this;
      }

      public FDEntityRenderLayerOptions.Builder<T> renderType(RenderFunction<T, RenderType> type) {
         this.renderType = type;
         return this;
      }

      public FDEntityRenderLayerOptions.Builder<T> renderType(RenderType type) {
         return this.renderType((tile, partialTicks) -> type);
      }

      public FDEntityRenderLayerOptions.Builder<T> renderCondition(Function<T, Boolean> renderCondition) {
         this.renderCondition = renderCondition;
         return this;
      }

      public FDEntityRenderLayerOptions.Builder<T> transformation(FDEntityTransformation<T> transformation) {
         this.transform = transformation;
         return this;
      }

      public FDEntityRenderLayerOptions<T> build() {
         if (this.layerModel == null) {
            throw new RuntimeException("Model for render layer is not registered.");
         }

         if (this.renderType == null) {
            throw new RuntimeException("RenderType for render layer is not registered.");
         }

         FDEntityRenderLayerOptions<T> layer = new FDEntityRenderLayerOptions<>();
         layer.renderType = this.renderType;
         layer.layerModel = this.layerModel;
         layer.renderCondition = this.renderCondition;
         layer.transform = this.transform;
         layer.layerColor = this.layerColor;
         layer.ignoreHurtOverlay = this.ignoreHurtOverlay;
         layer.light = this.light;
         layer.boneControllers = this.boneControllers;
         return layer;
      }
   }
}
