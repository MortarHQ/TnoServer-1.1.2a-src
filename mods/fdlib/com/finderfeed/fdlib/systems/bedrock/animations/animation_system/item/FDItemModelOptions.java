package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item.interfaces.FDItemColor;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item.interfaces.FDItemRenderType;
import com.finderfeed.fdlib.systems.bedrock.models.FDModelInfo;
import com.finderfeed.fdlib.util.FDColor;
import java.util.function.Supplier;
import net.minecraft.client.renderer.RenderType;

public class FDItemModelOptions {
   private Supplier<FDModelInfo> modelInfo;
   private FDItemRenderType renderType;
   private FDItemColor fdItemColor;

   private FDItemModelOptions(FDItemModelOptions.Builder builder) {
      this.modelInfo = builder.modelInfo;
      this.renderType = builder.renderType;
      this.fdItemColor = builder.fdItemColor;
   }

   public Supplier<FDModelInfo> getModelInfo() {
      return this.modelInfo;
   }

   public FDItemRenderType getRenderType() {
      return this.renderType;
   }

   public FDItemColor getFdItemColor() {
      return this.fdItemColor;
   }

   public static FDItemModelOptions.Builder builder() {
      return new FDItemModelOptions.Builder();
   }

   public static class Builder {
      private Supplier<FDModelInfo> modelInfo;
      private FDItemRenderType renderType;
      private FDItemColor fdItemColor = (ctx, itemStack) -> new FDColor(1.0F, 1.0F, 1.0F, 1.0F);

      private Builder() {
      }

      public FDItemModelOptions.Builder modelInfo(Supplier<FDModelInfo> modelInfo) {
         this.modelInfo = modelInfo;
         return this;
      }

      public FDItemModelOptions.Builder renderType(FDItemRenderType renderType) {
         this.renderType = renderType;
         return this;
      }

      public FDItemModelOptions.Builder renderType(RenderType renderType) {
         this.renderType = (context, itemStack) -> renderType;
         return this;
      }

      public FDItemModelOptions.Builder itemColor(FDItemColor fdItemColor) {
         this.fdItemColor = fdItemColor;
         return this;
      }

      public FDItemModelOptions.Builder itemColor(FDColor color) {
         this.fdItemColor = (ctx, itemStack) -> color;
         return this;
      }

      public FDItemModelOptions build() {
         return new FDItemModelOptions(this);
      }
   }
}
