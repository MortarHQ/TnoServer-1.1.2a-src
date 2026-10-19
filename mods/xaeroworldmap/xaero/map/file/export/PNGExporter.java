package xaero.map.file.export;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.client.graphics.shader.LibShaders;
import xaero.lib.common.config.Config;
import xaero.lib.common.config.single.SingleConfigManager;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.biome.BlockTintProvider;
import xaero.map.cache.BlockStateShortShapeCache;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.config.primary.option.WorldMapPrimaryClientConfigOptions;
import xaero.map.exception.OpenGLException;
import xaero.map.file.MapRegionInfo;
import xaero.map.file.MapSaveLoad;
import xaero.map.file.OldFormatSupport;
import xaero.map.file.RegionDetection;
import xaero.map.graphics.CustomRenderTypes;
import xaero.map.graphics.ImprovedFramebuffer;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRenderer;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.map.gui.GuiMap;
import xaero.map.gui.MapTileSelection;
import xaero.map.misc.Misc;
import xaero.map.mods.SupportMods;
import xaero.map.region.ExportMapRegion;
import xaero.map.region.ExportMapTileChunk;
import xaero.map.region.LeveledRegion;
import xaero.map.region.MapLayer;
import xaero.map.region.MapRegion;
import xaero.map.region.MapTileChunk;
import xaero.map.region.MapUpdateFastConfig;
import xaero.map.region.OverlayManager;
import xaero.map.region.texture.ExportLeafRegionTexture;
import xaero.map.world.MapDimension;

public class PNGExporter {
   private final Calendar calendar = Calendar.getInstance();
   private Path destinationPath;
   private PoseStack matrixStack;

   public PNGExporter(Path destinationPath) {
      this.destinationPath = destinationPath;
      this.matrixStack = new PoseStack();
   }

   public PNGExportResult export(
      MapProcessor mapProcessor,
      Registry<Biome> biomeRegistry,
      Registry<DimensionType> dimensionTypes,
      MapTileSelection selection,
      OldFormatSupport oldFormatSupport
   ) throws IllegalArgumentException, IllegalAccessException, OpenGLException {
      if (!mapProcessor.getMapSaveLoad().isRegionDetectionComplete()) {
         return new PNGExportResult(PNGExportResultType.NOT_PREPARED, null);
      }

      int exportedLayer = mapProcessor.getCurrentCaveLayer();
      MapDimension dim = mapProcessor.getMapWorld().getCurrentDimension();
      Set<LeveledRegion<?>> list = dim.getLayeredMapRegions().getUnsyncedSet();
      if (list.isEmpty()) {
         return new PNGExportResult(PNGExportResultType.EMPTY, null);
      }

      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
      boolean multipleImagesSetting = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.EXPORT_MULTIPLE_IMAGES);
      boolean nightExportSetting = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.NIGHT_EXPORT);
      int exportScaleDownSquareSetting = (Integer)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.EXPORT_SCALE_DOWN_SQUARE);
      boolean includingHighlights = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.EXPORT_HIGHLIGHTS);
      boolean full = selection == null;
      Integer minX = null;
      Integer maxX = null;
      Integer minZ = null;
      Integer maxZ = null;
      MapLayer mapLayer = dim.getLayeredMapRegions().getLayer(exportedLayer);
      if (full) {
         for (LeveledRegion<?> region : list) {
            if (region.getLevel() == 0 && ((MapRegion)region).hasHadTerrain() && region.getCaveLayer() == exportedLayer) {
               if (minX == null || region.getRegionX() < minX) {
                  minX = region.getRegionX();
               }

               if (maxX == null || region.getRegionX() > maxX) {
                  maxX = region.getRegionX();
               }

               if (minZ == null || region.getRegionZ() < minZ) {
                  minZ = region.getRegionZ();
               }

               if (maxZ == null || region.getRegionZ() > maxZ) {
                  maxZ = region.getRegionZ();
               }
            }
         }

         for (Hashtable<Integer, RegionDetection> column : !dim.isUsingWorldSave() ? mapLayer.getDetectedRegions().values() : dim.getWorldSaveDetectedRegions()) {
            for (RegionDetection regionDetection : column.values()) {
               if (regionDetection.isHasHadTerrain()) {
                  if (minX == null || regionDetection.getRegionX() < minX) {
                     minX = regionDetection.getRegionX();
                  }

                  if (maxX == null || regionDetection.getRegionX() > maxX) {
                     maxX = regionDetection.getRegionX();
                  }

                  if (minZ == null || regionDetection.getRegionZ() < minZ) {
                     minZ = regionDetection.getRegionZ();
                  }

                  if (maxZ == null || regionDetection.getRegionZ() > maxZ) {
                     maxZ = regionDetection.getRegionZ();
                  }
               }
            }
         }
      } else {
         minX = selection.getLeft() >> 5;
         minZ = selection.getTop() >> 5;
         maxX = selection.getRight() >> 5;
         maxZ = selection.getBottom() >> 5;
      }

      int minBlockX = minX * 512;
      int minBlockZ = minZ * 512;
      int maxBlockX = (maxX + 1) * 512 - 1;
      int maxBlockZ = (maxZ + 1) * 512 - 1;
      if (!full) {
         minBlockX = Math.max(minBlockX, selection.getLeft() << 4);
         minBlockZ = Math.max(minBlockZ, selection.getTop() << 4);
         maxBlockX = Math.min(maxBlockX, (selection.getRight() << 4) + 15);
         maxBlockZ = Math.min(maxBlockZ, (selection.getBottom() << 4) + 15);
      }

      int exportAreaWidthInRegions = maxX - minX + 1;
      int exportAreaHeightInRegions = maxZ - minZ + 1;
      long exportAreaSizeInRegions = (long)exportAreaWidthInRegions * exportAreaHeightInRegions;
      int exportAreaWidth = exportAreaWidthInRegions * 512;
      int exportAreaHeight = exportAreaHeightInRegions * 512;
      if (!full) {
         exportAreaWidth = maxBlockX - minBlockX + 1;
         exportAreaHeight = maxBlockZ - minBlockZ + 1;
      }

      int scaleDownSquareSquared = exportScaleDownSquareSetting * exportScaleDownSquareSetting;
      float scale = exportAreaSizeInRegions >= scaleDownSquareSquared && !multipleImagesSetting && scaleDownSquareSquared > 0
         ? (float)(exportScaleDownSquareSetting / Math.sqrt(exportAreaSizeInRegions))
         : 1.0F;
      int exportImageWidth = (int)(exportAreaWidth * scale);
      int exportImageHeight = (int)(exportAreaHeight * scale);
      if (!multipleImagesSetting && scaleDownSquareSquared > 0) {
         long maxExportAreaSizeInRegions = scaleDownSquareSquared * 262144L;
         if ((long)exportAreaWidth * exportAreaHeight / 512L / 512L > maxExportAreaSizeInRegions) {
            return new PNGExportResult(PNGExportResultType.TOO_BIG, null);
         }
      }

      int maxTextureSize = GL11.glGetInteger(3379);
      OpenGLException.checkGLError();
      int frameWidth = Math.min(1024, Math.min(maxTextureSize, exportImageWidth));
      int frameHeight = Math.min(1024, Math.min(maxTextureSize, exportImageHeight));
      int horizontalFrames = (int)Math.ceil((double)exportImageWidth / frameWidth);
      int verticalFrames = (int)Math.ceil((double)exportImageHeight / frameHeight);
      boolean multipleImages = multipleImagesSetting && horizontalFrames * verticalFrames > 1;
      if (multipleImages) {
         exportImageWidth = frameWidth;
         exportImageHeight = frameHeight;
      }

      int pixelCount = exportImageWidth * exportImageHeight;
      if (pixelCount != Integer.MAX_VALUE && pixelCount / exportImageHeight == exportImageWidth) {
         boolean debugConfig = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.DEBUG);
         if (debugConfig) {
            WorldMap.LOGGER
               .info(
                  String.format("Exporting PNG of size %dx%d using a framebuffer of size %dx%d.", exportImageWidth, exportImageHeight, frameWidth, frameHeight)
               );
         }

         BufferedImage image;
         ImprovedFramebuffer exportFrameBuffer;
         ByteBuffer frameDataBuffer;
         int[] bufferArray;
         try {
            image = new BufferedImage(exportImageWidth, exportImageHeight, 1);
            exportFrameBuffer = new ImprovedFramebuffer(frameWidth, frameHeight, false);
            frameDataBuffer = BufferUtils.createByteBuffer(frameWidth * frameHeight * 4);
            bufferArray = new int[frameWidth * frameHeight];
         } catch (OutOfMemoryError oome) {
            return new PNGExportResult(PNGExportResultType.OUT_OF_MEMORY, null);
         }

         if (exportFrameBuffer.frameBufferId == -1) {
            return new PNGExportResult(PNGExportResultType.BAD_FBO, null);
         }

         MapUpdateFastConfig updateConfig = new MapUpdateFastConfig();
         boolean lighting = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.LIGHTING);
         Lighting.setupForFlatItems();
         Matrix4f ortho = new Matrix4f().setOrtho(0.0F, frameWidth, 0.0F, frameHeight, 0.0F, 1000.0F);
         RenderSystem.setProjectionMatrix(ortho, VertexSorting.ORTHOGRAPHIC_Z);
         PoseStack matrixStack = this.matrixStack;
         BlockStateShortShapeCache shortShapeCache = mapProcessor.getBlockStateShortShapeCache();
         BlockTintProvider blockTintProvider = mapProcessor.getWorldBlockTintProvider();
         OverlayManager overlayManager = mapProcessor.getOverlayManager();
         MapSaveLoad mapSaveLoad = mapProcessor.getMapSaveLoad();
         MultiTextureRenderTypeRendererProvider rendererProvider = mapProcessor.getMultiTextureRenderTypeRenderers();
         Matrix4fStack shaderMatrixStack = RenderSystem.getModelViewStack();
         shaderMatrixStack.pushMatrix();
         shaderMatrixStack.identity();
         RenderSystem.applyModelViewMatrix();
         matrixStack.pushPose();
         exportFrameBuffer.bindAsMainTarget(true);
         matrixStack.scale(scale, scale, 1.0F);
         boolean[] justMetaDest = new boolean[1];
         Path imageDestination = this.destinationPath;
         if (multipleImages) {
            imageDestination = this.destinationPath.resolve(this.getExportBaseName());
         }

         boolean empty = true;
         PNGExportResultType resultType = PNGExportResultType.SUCCESS;

         for (int i = 0; i < horizontalFrames; i++) {
            for (int j = 0; j < verticalFrames; j++) {
               boolean renderedSomething = false;
               RenderSystem.bindTexture(0);
               RenderSystem.clearColor(0.0F, 0.0F, 0.0F, 1.0F);
               RenderSystem.clear(16640, Minecraft.ON_OSX);
               matrixStack.pushPose();
               float frameLeft = minBlockX + i * frameWidth / scale;
               float frameRight = minBlockX + (i + 1) * frameWidth / scale - 1.0F;
               float frameTop = minBlockZ + j * frameHeight / scale;
               float frameBottom = minBlockZ + (j + 1) * frameHeight / scale - 1.0F;
               if (!full) {
                  if (maxBlockX < frameRight) {
                     frameRight = maxBlockX;
                  }

                  if (maxBlockZ < frameBottom) {
                     frameBottom = maxBlockZ;
                  }
               }

               int minTileChunkX = (int)Math.floor(frameLeft) >> 6;
               int maxTileChunkX = (int)Math.floor(frameRight) >> 6;
               int minTileChunkZ = (int)Math.floor(frameTop) >> 6;
               int maxTileChunkZ = (int)Math.floor(frameBottom) >> 6;
               int minRegionX = minTileChunkX >> 3;
               int minRegionZ = minTileChunkZ >> 3;
               int maxRegionX = maxTileChunkX >> 3;
               int maxRegionZ = maxTileChunkZ >> 3;
               matrixStack.translate(0.1, 0.0, 0.0);
               Matrix4f matrix = matrixStack.last().pose();

               for (int regionX = minRegionX; regionX <= maxRegionX; regionX++) {
                  for (int regionZ = minRegionZ; regionZ <= maxRegionZ; regionZ++) {
                     MapRegion originalRegion = mapProcessor.getLeafMapRegion(exportedLayer, regionX, regionZ, false);
                     MapRegionInfo regionInfo = originalRegion;
                     if (originalRegion == null && mapLayer.regionDetectionExists(regionX, regionZ)) {
                        regionInfo = mapLayer.getRegionDetection(regionX, regionZ);
                     }

                     boolean regionHasHighlightsIfUndiscovered = includingHighlights
                        && dim.getHighlightHandler().shouldApplyRegionHighlights(regionX, regionZ, false);
                     if (regionInfo != null || regionHasHighlightsIfUndiscovered) {
                        File cacheFile = null;
                        boolean loadingFromCache = regionInfo != null
                           && (originalRegion == null || !originalRegion.isBeingWritten() || originalRegion.getLoadState() != 2);
                        if (loadingFromCache) {
                           cacheFile = regionInfo.getCacheFile();
                           if (cacheFile == null && !regionInfo.hasLookedForCache()) {
                              try {
                                 cacheFile = mapSaveLoad.getCacheFile(regionInfo, exportedLayer, true, false);
                              } catch (IOException var95) {
                              }
                           }

                           if (cacheFile == null) {
                              if (!regionHasHighlightsIfUndiscovered) {
                                 continue;
                              }

                              loadingFromCache = false;
                           }
                        }

                        ExportMapRegion region = new ExportMapRegion(dim, regionX, regionZ, exportedLayer, biomeRegistry);
                        if (loadingFromCache) {
                           region.setShouldCache(true, "png");
                           region.setHasHadTerrain();
                           region.setCacheFile(cacheFile);
                           region.loadCacheTextures(mapProcessor, biomeRegistry, false, null, 0, null, justMetaDest, 1, oldFormatSupport);
                        } else if (originalRegion != null) {
                           for (int o = 0; o < 8; o++) {
                              for (int p = 0; p < 8; p++) {
                                 MapTileChunk originalTileChunk = originalRegion.getChunk(o, p);
                                 if (originalTileChunk != null && originalTileChunk.hasHadTerrain()) {
                                    MapTileChunk tileChunk = region.createTexture(o, p).getTileChunk();

                                    for (int tx = 0; tx < 4; tx++) {
                                       for (int tz = 0; tz < 4; tz++) {
                                          tileChunk.setTile(tx, tz, originalTileChunk.getTile(tx, tz), shortShapeCache);
                                       }
                                    }

                                    tileChunk.setLoadState((byte)2);
                                    tileChunk.updateBuffers(
                                       mapProcessor, blockTintProvider, overlayManager, WorldMap.detailed_debug, shortShapeCache, updateConfig
                                    );
                                 }
                              }
                           }
                        }

                        if (includingHighlights) {
                           mapProcessor.getMapRegionHighlightsPreparer().prepare(region, true);
                        }

                        MultiTextureRenderTypeRenderer rendererLight = rendererProvider.getRenderer(
                           t -> RenderSystem.setShaderTexture(0, t), MultiTextureRenderTypeRendererProvider::defaultTextureBind, CustomRenderTypes.MAP
                        );
                        MultiTextureRenderTypeRenderer rendererNoLight = rendererProvider.getRenderer(
                           t -> RenderSystem.setShaderTexture(0, t), MultiTextureRenderTypeRendererProvider::defaultTextureBind, CustomRenderTypes.MAP
                        );
                        IntList texturesToDelete = new IntArrayList();

                        for (int localChunkX = 0; localChunkX < 8; localChunkX++) {
                           for (int localChunkZ = 0; localChunkZ < 8; localChunkZ++) {
                              ExportMapTileChunk tileChunk = region.getChunk(localChunkX, localChunkZ);
                              if (tileChunk != null) {
                                 ExportLeafRegionTexture tileChunkTexture = tileChunk.getLeafTexture();
                                 if (tileChunkTexture != null) {
                                    if (tileChunk.getX() >= minTileChunkX
                                       && tileChunk.getX() <= maxTileChunkX
                                       && tileChunk.getZ() >= minTileChunkZ
                                       && tileChunk.getZ() <= maxTileChunkZ) {
                                       int textureId = tileChunkTexture.bindColorTexture(true);
                                       if (tileChunkTexture.getColorBuffer() == null) {
                                          tileChunkTexture.prepareBuffer();
                                       }

                                       ByteBuffer colorBuffer = tileChunkTexture.getDirectColorBuffer();
                                       if (includingHighlights) {
                                          tileChunkTexture.applyHighlights(dim.getHighlightHandler(), tileChunkTexture.getColorBuffer());
                                       }

                                       if (tileChunkTexture.isColorBufferCompressed()) {
                                          GL13.glCompressedTexImage2D(3553, 0, tileChunkTexture.getColorBufferFormat(), 64, 64, 0, colorBuffer);
                                       } else {
                                          int internalFormat = tileChunkTexture.getColorBufferFormat() == -1 ? 32856 : tileChunkTexture.getColorBufferFormat();
                                          GL11.glTexImage2D(3553, 0, internalFormat, 64, 64, 0, 32993, 32821, colorBuffer);
                                       }

                                       tileChunkTexture.deleteColorBuffer();
                                       if (textureId != -1) {
                                          GL11.glTexParameteri(3553, 33085, 9);
                                          RenderSystem.texParameter(3553, 33083, 9);
                                          exportFrameBuffer.generateMipmaps();
                                          RenderSystem.texParameter(3553, 10241, 9987);
                                          GuiMap.renderTexturedModalRectWithLighting3(
                                             matrix,
                                             tileChunk.getX() * 64 - frameLeft,
                                             tileChunk.getZ() * 64 - frameTop,
                                             64.0F,
                                             64.0F,
                                             textureId,
                                             tileChunkTexture.getBufferHasLight(),
                                             tileChunkTexture.getBufferHasLight() ? rendererLight : rendererNoLight
                                          );
                                          renderedSomething = true;
                                          texturesToDelete.add(textureId);
                                       }
                                    } else {
                                       tileChunkTexture.deleteColorBuffer();
                                    }
                                 }
                              }
                           }
                        }

                        float brightness = nightExportSetting
                           ? mapProcessor.getAmbientBrightness(dim.getDimensionType(dimensionTypes))
                           : mapProcessor.getBrightness(exportedLayer, mapProcessor.getWorld(), lighting && exportedLayer != Integer.MAX_VALUE);
                        LibShaders.WORLD_MAP.setBrightness(brightness);
                        LibShaders.WORLD_MAP.setWithLight(true);
                        rendererProvider.draw(rendererLight);
                        LibShaders.WORLD_MAP.setWithLight(false);
                        rendererProvider.draw(rendererNoLight);
                        GL11.glDeleteTextures(texturesToDelete.toIntArray());
                        RenderSystem.bindTexture(0);
                     }
                  }
               }

               matrixStack.popPose();
               RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
               if (renderedSomething) {
                  empty = false;
                  exportFrameBuffer.bindRead();
                  frameDataBuffer.clear();
                  GL11.glGetTexImage(3553, 0, 32993, 33639, frameDataBuffer);
                  frameDataBuffer.asIntBuffer().get(bufferArray);
                  int insertOffsetX = i * frameWidth;
                  int insertOffsetZ = j * frameHeight;
                  if (multipleImages) {
                     insertOffsetX = 0;
                     insertOffsetZ = 0;
                  }

                  int actualFrameWidth = Math.min(frameWidth, exportImageWidth - insertOffsetX);
                  int actualFrameHeight = Math.min(frameHeight, exportImageHeight - insertOffsetZ);
                  image.setRGB(insertOffsetX, insertOffsetZ, actualFrameWidth, actualFrameHeight, bufferArray, 0, frameWidth);
                  if (multipleImages) {
                     PNGExportResultType saveResult = this.saveImage(image, imageDestination, i + "_" + j, "_x" + (int)frameLeft + "_z" + (int)frameTop);
                     if (saveResult != PNGExportResultType.SUCCESS) {
                        resultType = saveResult;
                     }
                  }
               }
            }
         }

         exportFrameBuffer.unbindWrite();
         Minecraft mc = Minecraft.getInstance();
         exportFrameBuffer.bindDefaultFramebuffer(mc);
         RenderSystem.enableCull();
         matrixStack.popPose();
         shaderMatrixStack.popMatrix();
         RenderSystem.applyModelViewMatrix();
         Misc.minecraftOrtho(mc, SupportMods.vivecraft);
         RenderSystem.bindTexture(0);
         exportFrameBuffer.destroyBuffers();
         mapProcessor.getBufferDeallocator().deallocate(frameDataBuffer, debugConfig);
         if (empty) {
            return new PNGExportResult(PNGExportResultType.EMPTY, null);
         } else if (multipleImages) {
            image.flush();
            return new PNGExportResult(resultType, imageDestination);
         } else {
            resultType = this.saveImage(image, imageDestination, null, "_x" + minBlockX + "_z" + minBlockZ);
            image.flush();
            return new PNGExportResult(resultType, imageDestination);
         }
      } else {
         return new PNGExportResult(PNGExportResultType.IMAGE_TOO_BIG, null);
      }
   }

   private PNGExportResultType saveImage(BufferedImage image, Path destinationPath, String baseName, String suffix) {
      if (baseName == null) {
         baseName = this.getExportBaseName();
      }

      baseName = baseName + suffix;
      int additionalIndex = 1;

      try {
         if (!Files.exists(destinationPath)) {
            Files.createDirectories(destinationPath);
         }

         Path imagePath = destinationPath.resolve(baseName + ".png");

         while (Files.exists(imagePath)) {
            imagePath = destinationPath.resolve(baseName + "_" + ++additionalIndex + ".png");
         }

         ImageIO.write(image, "png", imagePath.toFile());
         return PNGExportResultType.SUCCESS;
      } catch (IOException e1) {
         WorldMap.LOGGER.error("IO exception while exporting PNG: ", e1);
         return PNGExportResultType.IO_EXCEPTION;
      }
   }

   private String getExportBaseName() {
      this.calendar.setTimeInMillis(System.currentTimeMillis());
      int year = this.calendar.get(1);
      int month = 1 + this.calendar.get(2);
      int day = this.calendar.get(5);
      int hours = this.calendar.get(11);
      int minutes = this.calendar.get(12);
      int seconds = this.calendar.get(13);
      return String.format("%d-%02d-%02d_%02d.%02d.%02d", year, month, day, hours, minutes, seconds);
   }
}
