package xaero.map.region;

import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import xaero.map.MapProcessor;
import xaero.map.MapWriter;
import xaero.map.biome.BlockTintProvider;
import xaero.map.world.MapDimension;

public class Overlay extends MapPixel {
   private byte opacity;

   public Overlay(BlockState state, byte light, boolean glowing) {
      this.write(state, light, glowing);
   }

   public void write(BlockState state, byte light, boolean glowing) {
      this.opacity = 0;
      this.state = state;
      this.light = light;
      this.glowing = glowing;
   }

   public boolean isWater() {
      return this.state.getBlock() == Blocks.WATER;
   }

   public int getParametres() {
      int parametres = 0;
      parametres |= !this.isWater() ? 1 : 0;
      parametres |= this.light << 4;
      return parametres | (this.opacity & 15) << 11;
   }

   public void getPixelColour(
      MapBlock block,
      int[] result_dest,
      MapWriter mapWriter,
      Level world,
      MapDimension dim,
      Registry<Block> blockRegistry,
      MapTileChunk tileChunk,
      MapTileChunk prevChunk,
      MapTileChunk prevChunkDiagonal,
      MapTileChunk prevChunkHorisontal,
      MapTile mapTile,
      int x,
      int z,
      int caveStart,
      int caveDepth,
      MutableBlockPos mutableGlobalPos,
      Registry<Biome> biomeRegistry,
      Registry<DimensionType> dimensionTypes,
      float shadowR,
      float shadowG,
      float shadowB,
      BlockTintProvider blockTintProvider,
      MapProcessor mapProcessor,
      OverlayManager overlayManager,
      MapUpdateFastConfig config
   ) {
      super.getPixelColours(
         result_dest,
         mapWriter,
         world,
         dim,
         blockRegistry,
         tileChunk,
         prevChunk,
         prevChunkDiagonal,
         prevChunkHorisontal,
         mapTile,
         x,
         z,
         block,
         -1,
         -1,
         caveStart,
         caveDepth,
         null,
         mutableGlobalPos,
         biomeRegistry,
         dimensionTypes,
         shadowR,
         shadowG,
         shadowB,
         blockTintProvider,
         mapProcessor,
         overlayManager,
         null,
         config
      );
   }

   public String toRenderString() {
      return "(S: " + this.getState() + ", O: " + this.opacity + ", L: " + this.light + ")";
   }

   public boolean equals(Overlay p) {
      return p != null && this.opacity == p.opacity && this.light == p.light && this.getState() == p.getState();
   }

   void fillManagerKeyHolder(Object[] keyHolder) {
      keyHolder[0] = this.state;
      keyHolder[1] = this.light;
      keyHolder[2] = this.opacity;
   }

   public void increaseOpacity(int toAdd) {
      if (toAdd > 15) {
         toAdd = 15;
      }

      this.opacity = (byte)(this.opacity + toAdd);
      if (this.opacity > 15) {
         this.opacity = 15;
      }
   }

   public int getOpacity() {
      return this.opacity;
   }
}
