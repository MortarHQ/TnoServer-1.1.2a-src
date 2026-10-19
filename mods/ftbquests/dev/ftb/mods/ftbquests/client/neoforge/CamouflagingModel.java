package dev.ftb.mods.ftbquests.client.neoforge;

import dev.ftb.mods.ftbquests.block.QuestBarrierBlock;
import dev.ftb.mods.ftbquests.block.neoforge.NeoForgeQuestBarrierBlockEntity;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class CamouflagingModel implements IDynamicBakedModel {
   private final BakedModel baseModel;

   CamouflagingModel(BakedModel baseModel) {
      this.baseModel = baseModel;
   }

   @NotNull
   public List<BakedQuad> getQuads(
      @Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData modelData, @Nullable RenderType renderType
   ) {
      if (state != null && state.getBlock() instanceof QuestBarrierBlock) {
         BlockState camoState = (BlockState)modelData.get(NeoForgeQuestBarrierBlockEntity.CAMOUFLAGE_STATE);
         if (renderType == null) {
            renderType = RenderType.solid();
         }

         if (camoState != null && !(camoState.getBlock() instanceof QuestBarrierBlock)) {
            if (camoState != null && this.getRenderTypes(camoState, rand, modelData).contains(renderType)) {
               BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(camoState);
               return model.getQuads(camoState, side, rand, modelData, renderType);
            } else {
               return List.of();
            }
         } else {
            return this.baseModel.getQuads(state, side, rand, modelData, renderType);
         }
      } else {
         return this.baseModel.getQuads(state, side, rand, modelData, renderType);
      }
   }

   public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
      BlockState camoState = (BlockState)data.get(NeoForgeQuestBarrierBlockEntity.CAMOUFLAGE_STATE);
      return super.getRenderTypes(camoState == null ? state : camoState, rand, data);
   }

   public boolean useAmbientOcclusion() {
      return this.baseModel.useAmbientOcclusion();
   }

   public boolean isGui3d() {
      return this.baseModel.isGui3d();
   }

   public boolean isCustomRenderer() {
      return this.baseModel.isCustomRenderer();
   }

   public TextureAtlasSprite getParticleIcon() {
      return this.baseModel.getParticleIcon();
   }

   public ItemTransforms getTransforms() {
      return this.baseModel.getTransforms();
   }

   public ItemOverrides getOverrides() {
      return this.baseModel.getOverrides();
   }

   public boolean usesBlockLight() {
      return false;
   }
}
