package com.tom.storagemod.client;

import com.tom.storagemod.block.entity.PaintedBlockEntity;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BakedPaintedModel implements IDynamicBakedModel {
   private Block blockFor;
   private BakedModel parent;

   public BakedPaintedModel(Block blockFor, BakedModel parent) {
      this.blockFor = blockFor;
      this.parent = parent;
   }

   public boolean useAmbientOcclusion() {
      return true;
   }

   public boolean isGui3d() {
      return false;
   }

   public boolean usesBlockLight() {
      return false;
   }

   public boolean isCustomRenderer() {
      return false;
   }

   public TextureAtlasSprite getParticleIcon() {
      return this.parent.getParticleIcon(ModelData.EMPTY);
   }

   public ItemOverrides getOverrides() {
      return null;
   }

   @NotNull
   public List<BakedQuad> getQuads(
      @Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData modelData, @Nullable RenderType layer
   ) {
      BakedModel model = null;
      Supplier<BlockState> blockstateSupp = (Supplier<BlockState>)modelData.get(PaintedBlockEntity.FACADE_STATE);
      BlockState blockstate = null;
      if (blockstateSupp != null) {
         blockstate = blockstateSupp.get();
      }

      if (blockstate == null || blockstate == Blocks.AIR.defaultBlockState()) {
         blockstate = state;
         model = this.parent;
         if (layer != null && layer != RenderType.solid()) {
            return Collections.emptyList();
         }
      }

      if (model == null) {
         model = Minecraft.getInstance().getBlockRenderer().getBlockModel(blockstate);
      }

      return model.getQuads(blockstate, side, rand, ModelData.EMPTY, layer);
   }

   public ModelData getModelData(BlockAndTintGetter world, BlockPos pos, BlockState state, ModelData tileData) {
      return tileData;
   }

   public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
      return ChunkRenderTypeSet.all();
   }
}
