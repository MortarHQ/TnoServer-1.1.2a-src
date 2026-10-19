package xaero.map.region;

import java.util.ArrayList;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import xaero.map.MapProcessor;
import xaero.map.MapWriter;
import xaero.map.biome.BlockTintProvider;
import xaero.map.cache.BlockStateShortShapeCache;
import xaero.map.world.MapDimension;

public class MapPixel {
   private static final int VOID_COLOR = -16121833;
   private static final float DEFAULT_AMBIENT_LIGHT = 0.7F;
   private static final float DEFAULT_AMBIENT_LIGHT_COLORED = 0.2F;
   private static final float DEFAULT_AMBIENT_LIGHT_WHITE = 0.5F;
   private static final float DEFAULT_MAX_DIRECT_LIGHT = 0.6666667F;
   private static final float GLOWING_MAX_DIRECT_LIGHT = 0.22222224F;
   protected BlockState state;
   protected byte light = 0;
   protected boolean glowing = false;

   private int getVanillaTransparency(Block b) {
      return b instanceof LiquidBlock ? 191 : (b instanceof IceBlock ? 216 : 127);
   }

   public void getPixelColours(
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
      MapBlock block,
      int height,
      int topHeight,
      int caveStart,
      int caveDepth,
      ArrayList<Overlay> overlays,
      MutableBlockPos mutableGlobalPos,
      Registry<Biome> biomeRegistry,
      Registry<DimensionType> dimensionTypes,
      float shadowR,
      float shadowG,
      float shadowB,
      BlockTintProvider blockTintProvider,
      MapProcessor mapProcessor,
      OverlayManager overlayManager,
      BlockStateShortShapeCache blockStateShortShapeCache,
      MapUpdateFastConfig updateConfig
   ) {
      int colour = block != null && caveStart != Integer.MAX_VALUE ? 0 : -16121833;
      int topLightValue = this.light;
      int lightMin = 9;
      float brightnessR = 1.0F;
      float brightnessG = 1.0F;
      float brightnessB = 1.0F;
      mutableGlobalPos.set(mapTile.getChunkX() * 16 + x, height, mapTile.getChunkZ() * 16 + z);
      BlockState state = this.state;
      boolean isAir = state.getBlock() instanceof AirBlock;
      boolean isFinalBlock = this instanceof MapBlock;
      if (!isAir) {
         if (updateConfig.blockColors == 0) {
            colour = mapWriter.loadBlockColourFromTexture(state, true, world, blockRegistry, mutableGlobalPos);
         } else {
            try {
               Block b = state.getBlock();
               int a = this.getVanillaTransparency(b);
               colour = state.getMapColor(world, mutableGlobalPos).col;
               if (!isFinalBlock && colour == 0) {
                  result_dest[0] = -1;
                  return;
               }

               colour = a << 24 | colour & 16777215;
            } catch (Exception var60) {
            }
         }

         if (!isFinalBlock
            && !updateConfig.stainedGlass
            && (state.getBlock() instanceof StainedGlassBlock || state.getBlock() instanceof StainedGlassPaneBlock)) {
            result_dest[0] = -1;
            return;
         }
      }

      int r = colour >> 16 & 0xFF;
      int g = colour >> 8 & 0xFF;
      int b = colour & 0xFF;
      if (updateConfig.biomeColorsInVanilla || updateConfig.blockColors == 0) {
         int c = blockTintProvider.getBiomeColor(mutableGlobalPos, state, !isFinalBlock, mapTile, tileChunk.getInRegion().getCaveLayer());
         float rMultiplier = r / 255.0F;
         float gMultiplier = g / 255.0F;
         float bMultiplier = b / 255.0F;
         r = (int)((c >> 16 & 0xFF) * rMultiplier);
         g = (int)((c >> 8 & 0xFF) * gMultiplier);
         b = (int)((c & 0xFF) * bMultiplier);
      }

      if (this.glowing) {
         int total = r + g + b;
         float minBrightness = 407.0F;
         float brightener = Math.max(1.0F, minBrightness / total);
         r = (int)(r * brightener);
         g = (int)(g * brightener);
         b = (int)(b * brightener);
         topLightValue = 15;
      }

      int overlayRed = 0;
      int overlayGreen = 0;
      int overlayBlue = 0;
      float currentTransparencyMultiplier = 1.0F;
      boolean legibleCaveMaps = updateConfig.legibleCaveMaps && caveStart != Integer.MAX_VALUE;
      boolean hasValidOverlay = false;
      if (overlays != null && !overlays.isEmpty()) {
         int sun = 15;

         for (int i = 0; i < overlays.size(); i++) {
            Overlay o = overlays.get(i);
            o.getPixelColour(
               block,
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
               caveStart,
               caveDepth,
               mutableGlobalPos,
               biomeRegistry,
               dimensionTypes,
               shadowR,
               shadowG,
               shadowB,
               blockTintProvider,
               mapProcessor,
               overlayManager,
               updateConfig
            );
            if (result_dest[0] != -1) {
               hasValidOverlay = true;
               if (i == 0) {
                  topLightValue = o.light;
               }

               float transparency = result_dest[3] / 255.0F;
               float overlayIntensity = this.getBlockBrightness(lightMin, o.light, sun) * transparency * currentTransparencyMultiplier;
               overlayRed = (int)(overlayRed + result_dest[0] * overlayIntensity);
               overlayGreen = (int)(overlayGreen + result_dest[1] * overlayIntensity);
               overlayBlue = (int)(overlayBlue + result_dest[2] * overlayIntensity);
               sun -= o.getOpacity();
               if (sun < 0) {
                  sun = 0;
               }

               currentTransparencyMultiplier *= 1.0F - transparency;
            }
         }

         if (!legibleCaveMaps && hasValidOverlay && !this.glowing && !isAir) {
            brightnessR = brightnessG = brightnessB = this.getBlockBrightness(lightMin, this.light, sun);
         }
      }

      if (isFinalBlock) {
         if (block.slopeUnknown) {
            if (!isAir) {
               block.fixHeightType(
                  x, z, mapTile, tileChunk, prevChunk, prevChunkDiagonal, prevChunkHorisontal, height, false, blockStateShortShapeCache, updateConfig
               );
            } else {
               block.setVerticalSlope((byte)0);
               block.setDiagonalSlope((byte)0);
               block.slopeUnknown = false;
            }
         }

         float depthBrightness = 1.0F;
         int slopes = updateConfig.terrainSlopes;
         if (legibleCaveMaps) {
            topLightValue = 15;
         }

         if (height != 32767) {
            if (legibleCaveMaps && (!isAir || hasValidOverlay)) {
               int depthCalculationBase = 0;
               int depthCalculationHeight = height;
               int depthCalculationBottom = caveStart + 1 - caveDepth;
               int depthCalculationTop = caveStart;
               if (caveStart == Integer.MIN_VALUE) {
                  depthCalculationBottom = 0;
                  depthCalculationTop = 63;
                  int odd = depthCalculationHeight >> 6 & 1;
                  depthCalculationHeight = 63 * odd + (1 - 2 * odd) * (depthCalculationHeight & 63);
                  depthCalculationBase = 16;
               }

               int caveRange = 1 + depthCalculationTop - depthCalculationBottom;
               if (!isAir && !this.glowing) {
                  float caveBrightness = (1.0F + depthCalculationBase + depthCalculationHeight - depthCalculationBottom) / (depthCalculationBase + caveRange);
                  brightnessR *= caveBrightness;
                  brightnessG *= caveBrightness;
                  brightnessB *= caveBrightness;
               }

               if (hasValidOverlay) {
                  depthCalculationHeight = topHeight;
                  if (caveStart == Integer.MIN_VALUE) {
                     int odd = depthCalculationHeight >> 6 & 1;
                     depthCalculationHeight = 63 * odd + (1 - 2 * odd) * (depthCalculationHeight & 63);
                  }

                  float caveBrightness = (1.0F + depthCalculationBase + depthCalculationHeight - depthCalculationBottom) / (depthCalculationBase + caveRange);
                  overlayRed = (int)(overlayRed * caveBrightness);
                  overlayGreen = (int)(overlayGreen * caveBrightness);
                  overlayBlue = (int)(overlayBlue * caveBrightness);
               }
            } else if (!isAir && !this.glowing && updateConfig.terrainDepth) {
               if (caveStart == Integer.MAX_VALUE) {
                  depthBrightness = height / 63.0F;
               } else if (caveStart == Integer.MIN_VALUE) {
                  depthBrightness = 0.7F + 0.3F * height / dim.getDimensionType(dimensionTypes).logicalHeight();
               } else {
                  int caveBottom = caveStart - caveDepth;
                  depthBrightness = 0.7F + 0.3F * (height - caveBottom) / caveDepth;
               }

               float max = slopes >= 2 ? 1.0F : 1.15F;
               float min = slopes >= 2 ? 0.9F : 0.7F;
               if (depthBrightness > max) {
                  depthBrightness = max;
               } else if (depthBrightness < min) {
                  depthBrightness = min;
               }
            }
         }

         if (!isAir && slopes > 0 && !block.slopeUnknown) {
            int verticalSlope = block.getVerticalSlope();
            if (slopes == 1) {
               if (verticalSlope > 0) {
                  depthBrightness = (float)(depthBrightness * 1.15);
               } else if (verticalSlope < 0) {
                  depthBrightness = (float)(depthBrightness * 0.85);
               }
            } else {
               int diagonalSlope = block.getDiagonalSlope();
               float ambientLightColored = 0.2F;
               float ambientLightWhite = 0.5F;
               float maxDirectLight = 0.6666667F;
               if (this.glowing) {
                  ambientLightColored = 0.0F;
                  ambientLightWhite = 1.0F;
                  maxDirectLight = 0.22222224F;
               }

               float cos = 0.0F;
               if (slopes == 2) {
                  float crossZ = -verticalSlope;
                  if (crossZ < 1.0F) {
                     if (verticalSlope == 1 && diagonalSlope == 1) {
                        cos = 1.0F;
                     } else {
                        float crossX = verticalSlope - diagonalSlope;
                        float cast = 1.0F - crossZ;
                        float crossMagnitude = (float)Math.sqrt(crossX * crossX + 1.0F + crossZ * crossZ);
                        cos = (float)(cast / crossMagnitude / Math.sqrt(2.0));
                     }
                  }
               } else if (verticalSlope >= 0) {
                  if (verticalSlope == 1) {
                     cos = 1.0F;
                  } else {
                     float surfaceDirectionMagnitude = (float)Math.sqrt(verticalSlope * verticalSlope + 1);
                     float castToMostLit = verticalSlope + 1;
                     cos = (float)(castToMostLit / surfaceDirectionMagnitude / Math.sqrt(2.0));
                  }
               }

               float directLightClamped = 0.0F;
               if (cos == 1.0F) {
                  directLightClamped = maxDirectLight;
               } else if (cos > 0.0F) {
                  directLightClamped = (float)Math.ceil(cos * 10.0F) / 10.0F * maxDirectLight * 0.88388F;
               }

               float whiteLight = ambientLightWhite + directLightClamped;
               brightnessR *= shadowR * ambientLightColored + whiteLight;
               brightnessG *= shadowG * ambientLightColored + whiteLight;
               brightnessB *= shadowB * ambientLightColored + whiteLight;
            }
         }

         brightnessR *= depthBrightness;
         brightnessG *= depthBrightness;
         brightnessB *= depthBrightness;
         result_dest[3] = (int)(this.getPixelLight(lightMin, topLightValue) * 255.0F);
      } else {
         result_dest[3] = colour >> 24 & 0xFF;
         if (result_dest[3] == 0) {
            result_dest[3] = this.getVanillaTransparency(state.getBlock());
         }
      }

      result_dest[0] = (int)(r * brightnessR * currentTransparencyMultiplier + overlayRed);
      if (result_dest[0] > 255) {
         result_dest[0] = 255;
      }

      result_dest[1] = (int)(g * brightnessG * currentTransparencyMultiplier + overlayGreen);
      if (result_dest[1] > 255) {
         result_dest[1] = 255;
      }

      result_dest[2] = (int)(b * brightnessB * currentTransparencyMultiplier + overlayBlue);
      if (result_dest[2] > 255) {
         result_dest[2] = 255;
      }
   }

   public float getBlockBrightness(float min, int l, int sun) {
      return (min + Math.max(sun, l)) / (15.0F + min);
   }

   private float getPixelLight(float min, int topLightValue) {
      return topLightValue == 0 ? 0.0F : this.getBlockBrightness(min, topLightValue, 0);
   }

   public BlockState getState() {
      return this.state;
   }

   public void setState(BlockState state) {
      this.state = state;
   }

   public void setLight(byte light) {
      this.light = light;
   }

   public void setGlowing(boolean glowing) {
      this.glowing = glowing;
   }
}
