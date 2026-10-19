package com.blackgear.vanillabackport.core.neoforge.emissive;

import com.blackgear.vanillabackport.client.util.EmissiveQuad;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EmissiveModelWrapper implements BakedModel {
   private final BakedModel baseModel;
   private final BakedModel emissiveModel;

   public EmissiveModelWrapper(BakedModel baseModel, BakedModel emissiveModel) {
      this.baseModel = baseModel;
      this.emissiveModel = emissiveModel;
   }

   @NotNull
   public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand) {
      return this.getQuads(state, side, rand, ModelData.EMPTY, null);
   }

   @NotNull
   public List<BakedQuad> getQuads(
      @Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType
   ) {
      List<BakedQuad> quads = new ArrayList<>(this.baseModel.getQuads(state, side, rand, extraData, renderType));

      for (BakedQuad quad : this.emissiveModel.getQuads(state, side, rand, extraData, renderType)) {
         quads.add(new EmissiveQuad(quad));
      }

      return quads;
   }

   public boolean useAmbientOcclusion() {
      return this.baseModel.useAmbientOcclusion();
   }

   public boolean isGui3d() {
      return this.baseModel.isGui3d();
   }

   public boolean usesBlockLight() {
      return this.baseModel.usesBlockLight();
   }

   public boolean isCustomRenderer() {
      return this.baseModel.isCustomRenderer();
   }

   @NotNull
   public TextureAtlasSprite getParticleIcon() {
      return this.baseModel.getParticleIcon();
   }

   @NotNull
   public ItemTransforms getTransforms() {
      return this.baseModel.getTransforms();
   }

   @NotNull
   public ItemOverrides getOverrides() {
      return this.baseModel.getOverrides();
   }

   @NotNull
   public ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
      return this.baseModel.getModelData(level, pos, state, modelData);
   }
}
