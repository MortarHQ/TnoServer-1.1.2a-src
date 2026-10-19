package com.sjyueluo.tensura_skill_books.client;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;

public final class SkillStudyBookBakedModel implements BakedModel {
   private final BakedModel guiModel;
   private final BakedModel emptyGuiModel;
   private final BakedModel customRendererModel;

   public SkillStudyBookBakedModel(BakedModel guiModel) {
      this.guiModel = guiModel;
      this.emptyGuiModel = new SkillStudyBookBakedModel.EmptyModel(guiModel, false);
      this.customRendererModel = new SkillStudyBookBakedModel.CustomRendererModel(guiModel);
   }

   public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
      this.guiModel.applyTransform(transformType, poseStack, applyLeftHandTransform);
      return transformType == ItemDisplayContext.GUI ? this.emptyGuiModel : this.customRendererModel;
   }

   public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource random) {
      return this.guiModel.getQuads(state, side, random);
   }

   public boolean useAmbientOcclusion() {
      return this.guiModel.useAmbientOcclusion();
   }

   public boolean isGui3d() {
      return this.guiModel.isGui3d();
   }

   public boolean usesBlockLight() {
      return this.guiModel.usesBlockLight();
   }

   public boolean isCustomRenderer() {
      return false;
   }

   public TextureAtlasSprite getParticleIcon() {
      return this.guiModel.getParticleIcon();
   }

   public ItemTransforms getTransforms() {
      return this.guiModel.getTransforms();
   }

   public ItemOverrides getOverrides() {
      return this.guiModel.getOverrides();
   }

   private static final class CustomRendererModel implements BakedModel {
      private final BakedModel delegate;

      private CustomRendererModel(BakedModel delegate) {
         this.delegate = delegate;
      }

      public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource random) {
         return List.of();
      }

      public boolean useAmbientOcclusion() {
         return this.delegate.useAmbientOcclusion();
      }

      public boolean isGui3d() {
         return this.delegate.isGui3d();
      }

      public boolean usesBlockLight() {
         return this.delegate.usesBlockLight();
      }

      public boolean isCustomRenderer() {
         return true;
      }

      public TextureAtlasSprite getParticleIcon() {
         return this.delegate.getParticleIcon();
      }

      public ItemTransforms getTransforms() {
         return this.delegate.getTransforms();
      }

      public ItemOverrides getOverrides() {
         return this.delegate.getOverrides();
      }
   }

   private static final class EmptyModel implements BakedModel {
      private final BakedModel delegate;
      private final boolean customRenderer;

      private EmptyModel(BakedModel delegate, boolean customRenderer) {
         this.delegate = delegate;
         this.customRenderer = customRenderer;
      }

      public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource random) {
         return List.of();
      }

      public boolean useAmbientOcclusion() {
         return this.delegate.useAmbientOcclusion();
      }

      public boolean isGui3d() {
         return this.delegate.isGui3d();
      }

      public boolean usesBlockLight() {
         return this.delegate.usesBlockLight();
      }

      public boolean isCustomRenderer() {
         return this.customRenderer;
      }

      public TextureAtlasSprite getParticleIcon() {
         return this.delegate.getParticleIcon();
      }

      public ItemTransforms getTransforms() {
         return this.delegate.getTransforms();
      }

      public ItemOverrides getOverrides() {
         return this.delegate.getOverrides();
      }
   }
}
