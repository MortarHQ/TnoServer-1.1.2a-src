package xaero.map;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import xaero.map.biome.BiomeGetter;
import xaero.map.cache.BlockStateShortShapeCache;
import xaero.map.region.OverlayManager;

public class MapWriterNeoForge extends MapWriter {
   public MapWriterNeoForge(OverlayManager overlayManager, BlockStateShortShapeCache blockStateShortShapeCache, BiomeGetter biomeGetter) {
      super(overlayManager, blockStateShortShapeCache, biomeGetter);
   }

   @Override
   protected boolean blockStateHasTranslucentRenderType(BlockState blockState) {
      BlockModelShaper bms = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper();
      BakedModel model = bms.getBlockModel(blockState);
      return model == null ? true : model.getRenderTypes(blockState, this.usedRandom, ModelData.EMPTY).contains(RenderType.translucent());
   }

   @Override
   protected List<BakedQuad> getQuads(BakedModel model, BlockState state, Direction direction) {
      return model.getQuads(state, direction, this.usedRandom, ModelData.EMPTY, null);
   }

   @Override
   protected TextureAtlasSprite getParticleIcon(BlockModelShaper bms, BakedModel model, BlockState state) {
      return model.getParticleIcon(ModelData.EMPTY);
   }
}
