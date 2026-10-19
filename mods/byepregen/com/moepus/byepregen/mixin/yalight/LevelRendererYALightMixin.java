package com.moepus.byepregen.mixin.yalight;

import com.moepus.byepregen.yalight.YABlockStateLightClass;
import com.moepus.byepregen.yalight.YAChunkLightAccess;
import com.moepus.byepregen.yalight.YAChunkRunCache;
import com.moepus.byepregen.yalight.YAImmediateChunkAccess;
import com.moepus.byepregen.yalight.YALightStorage;
import com.moepus.byepregen.yalight.YAVisibleLightReader;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererYALightMixin {
   @Overwrite
   public static int getLightColor(BlockAndTintGetter blockGetter, BlockPos pos) {
      if (blockGetter instanceof Level level) {
         int x = pos.getX();
         int y = pos.getY();
         int z = pos.getZ();
         ChunkAccess chunk = byepregen$getChunkNow(level, x, z);
         int light = byepregen$levelLightColor(level, chunk, pos);
         if (!level.isOutsideBuildHeight(pos)) {
            int rawId = YAChunkRunCache.rawIdAt(chunk, x, y, z);
            int lightClass = YABlockStateLightClass.fromRawId(rawId);
            if (lightClass == 3 && rawId >= 0) {
               return getLightColor(level, Block.stateById(rawId), pos);
            }
         }

         return light;
      } else {
         return getLightColor(blockGetter, blockGetter.getBlockState(pos), pos);
      }
   }

   @Overwrite
   public static int getLightColor(BlockAndTintGetter blockGetter, BlockState state, BlockPos pos) {
      int light;
      if (blockGetter instanceof Level level) {
         ChunkAccess chunk = byepregen$getChunkNow(level, pos.getX(), pos.getZ());
         light = byepregen$levelLightColor(level, chunk, pos);
      } else {
         int sky = blockGetter.getBrightness(LightLayer.SKY, pos);
         int block = blockGetter.getBrightness(LightLayer.BLOCK, pos);
         light = sky << 20 | block << 4;
      }

      if (state.isAir()) {
         return light;
      } else {
         return state.emissiveRendering(blockGetter, pos) ? 15728880 : byepregen$applyStateLight(light, state.getLightEmission(blockGetter, pos));
      }
   }

   @Unique
   private static ChunkAccess byepregen$getChunkNow(Level level, int blockX, int blockZ) {
      int chunkX = SectionPos.blockToSectionCoord(blockX);
      int chunkZ = SectionPos.blockToSectionCoord(blockZ);
      return (ChunkAccess)(level.getChunkSource() instanceof YAImmediateChunkAccess access
         ? access.byepregen$getAnyChunkNow(chunkX, chunkZ)
         : level.getChunk(chunkX, chunkZ));
   }

   @Unique
   private static int byepregen$levelLightColor(Level level, ChunkAccess chunk, BlockPos pos) {
      if (chunk == null) {
         int sky = level.dimensionType().hasSkyLight() ? 15 : 0;
         return sky << 20;
      } else {
         YAChunkLightAccess access = (YAChunkLightAccess)chunk;
         int sectionIndex = SectionPos.blockToSectionCoord(pos.getY()) - YALightStorage.minLightSection(level);
         int block = YAVisibleLightReader.blockLight(access.byepregen$visibleBlock(), sectionIndex, pos);
         int sky = level.dimensionType().hasSkyLight() ? YAVisibleLightReader.skyLight(access.byepregen$visibleSky(), sectionIndex, pos) : 0;
         return sky << 20 | block << 4;
      }
   }

   @Unique
   private static int byepregen$applyStateLight(int light, int stateLight) {
      int block = light >> 4 & 15;
      return block < stateLight ? light & -241 | stateLight << 4 : light;
   }
}
