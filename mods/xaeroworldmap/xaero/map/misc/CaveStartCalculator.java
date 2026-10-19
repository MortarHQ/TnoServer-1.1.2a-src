package xaero.map.misc;

import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.material.PushReaction;
import xaero.lib.client.config.ClientConfigManager;
import xaero.map.MapWriter;
import xaero.map.WorldMap;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;

public class CaveStartCalculator {
   private final MutableBlockPos mutableBlockPos = new MutableBlockPos();
   private final CachedFunction<StateHolder<?, ?>, Boolean> transparentCache;
   private final MapWriter mapWriter;

   public CaveStartCalculator(MapWriter mapWriter) {
      this.mapWriter = mapWriter;
      this.transparentCache = new CachedFunction<>(state -> mapWriter.shouldOverlay(state));
   }

   public int getCaving(double playerX, double playerY, double playerZ, Level world) {
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      int autoCaveModeConfig = (Integer)configManager.getEffective(WorldMapProfiledConfigOptions.AUTO_CAVE_MODE);
      if (autoCaveModeConfig == 0) {
         return Integer.MAX_VALUE;
      }

      int worldBottomY = world.getMinBuildHeight();
      int worldTopY = world.getMaxBuildHeight() - 1;
      int y = (int)playerY + 1;
      int defaultCaveStart = y + 3;
      int defaultResult = Integer.MAX_VALUE;
      if (y <= worldTopY && y >= worldBottomY) {
         int x = Mth.floor(playerX);
         int z = Mth.floor(playerZ);
         int roofRadius = autoCaveModeConfig < 0 ? 1 : autoCaveModeConfig - 1;
         int roofDiameter = 1 + roofRadius * 2;
         int startX = x - roofRadius;
         int startZ = z - roofRadius;
         boolean ignoringHeightmaps = this.mapWriter.getMapProcessor().getMapWorld().isIgnoreHeightmaps();
         int bottom = y;
         int top = Integer.MAX_VALUE;
         ChunkAccess prevBChunk = null;
         int potentialResult = defaultCaveStart;

         for (int o = 0; o < roofDiameter; o++) {
            label108:
            for (int p = 0; p < roofDiameter; p++) {
               int currentX = startX + o;
               int currentZ = startZ + p;
               this.mutableBlockPos.set(currentX, y, currentZ);
               ChunkAccess bchunk = world.getChunk(currentX >> 4, currentZ >> 4);
               if (bchunk == null) {
                  return defaultResult;
               }

               int skyLight = world.getBrightness(LightLayer.SKY, this.mutableBlockPos);
               if (!ignoringHeightmaps) {
                  if (skyLight >= 15) {
                     return defaultResult;
                  }

                  int insideX = currentX & 15;
                  int insideZ = currentZ & 15;
                  top = bchunk.getHeight(Types.WORLD_SURFACE, insideX, insideZ);
               } else if (bchunk != prevBChunk) {
                  LevelChunkSection[] sections = bchunk.getSections();
                  if (sections.length == 0) {
                     return defaultResult;
                  }

                  int playerSection = y - worldBottomY >> 4;
                  boolean foundSomething = false;

                  for (int i = playerSection; i < sections.length; i++) {
                     LevelChunkSection searchedSection = sections[i];
                     if (!searchedSection.hasOnlyAir()) {
                        if (!foundSomething) {
                           bottom = Math.max(bottom, worldBottomY + (i << 4));
                           foundSomething = true;
                        }

                        top = worldBottomY + (i << 4) + 15;
                     }
                  }

                  if (!foundSomething) {
                     return defaultResult;
                  }

                  prevBChunk = bchunk;
               }

               if (top < worldBottomY) {
                  return defaultResult;
               }

               if (top > worldTopY) {
                  top = worldTopY;
               }

               for (int i = bottom; i <= top; i++) {
                  this.mutableBlockPos.setY(i);
                  BlockState state = world.getBlockState(this.mutableBlockPos);
                  if (!state.isAir()
                     && state.getPistonPushReaction() != PushReaction.DESTROY
                     && !(state.getBlock() instanceof LiquidBlock)
                     && !state.is(BlockTags.LEAVES)
                     && !this.transparentCache.apply(state)
                     && state.getBlock() != Blocks.BARRIER) {
                     if (o == p && o == roofRadius) {
                        potentialResult = Math.min(i, defaultCaveStart);
                     }
                     continue label108;
                  }
               }

               return defaultResult;
            }
         }

         return potentialResult;
      } else {
         return defaultResult;
      }
   }
}
