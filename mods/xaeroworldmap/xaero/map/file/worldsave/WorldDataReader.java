package xaero.map.file.worldsave;

import com.mojang.datafixers.DataFixer;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.BitStorage;
import net.minecraft.util.Mth;
import net.minecraft.util.SimpleBitStorage;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.chunk.storage.ChunkStorage;
import net.minecraft.world.level.chunk.storage.RegionFile;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import xaero.lib.client.config.ClientConfigManager;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.cache.BlockStateShortShapeCache;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.executor.Executor;
import xaero.map.file.worldsave.biome.WorldDataBiomeManager;
import xaero.map.file.worldsave.biome.WorldDataReaderSectionBiomeData;
import xaero.map.misc.CachedFunction;
import xaero.map.mods.SupportMods;
import xaero.map.region.MapBlock;
import xaero.map.region.MapRegion;
import xaero.map.region.MapTile;
import xaero.map.region.MapTileChunk;
import xaero.map.region.OverlayBuilder;
import xaero.map.region.OverlayManager;

public class WorldDataReader {
   private MapProcessor mapProcessor;
   private boolean[] shouldEnterGround;
   private boolean[] underair;
   private boolean[] blockFound;
   private byte[] lightLevels;
   private byte[] skyLightLevels;
   private int[] topH;
   private MapBlock buildingObject = new MapBlock();
   private OverlayBuilder[] overlayBuilders;
   private MutableBlockPos mutableBlockPos;
   private List<BlockState> blockStatePalette;
   private BitStorage heightMapBitArray;
   private BitStorage blockStatesBitArray;
   private CompletableFuture<Optional<CompoundTag>>[] chunkNBTCompounds;
   public Object taskCreationSync;
   private BlockStateShortShapeCache blockStateShortShapeCache;
   private ResourceKey<Biome> defaultBiomeKey;
   private final CachedFunction<StateHolder<?, ?>, Boolean> transparentCache;
   private int[] firstTransparentStateY;
   private boolean[] shouldExtendTillTheBottom;
   private CachedFunction<FluidState, BlockState> fluidToBlock;
   private WorldDataBiomeManager biomeManager;
   private final BiomeManager biomeZoomer;

   public WorldDataReader(
      OverlayManager overlayManager, BlockStateShortShapeCache blockStateShortShapeCache, WorldDataBiomeManager biomeManager, long biomeZoomSeed
   ) {
      this.underair = new boolean[256];
      this.shouldEnterGround = new boolean[256];
      this.blockFound = new boolean[256];
      this.lightLevels = new byte[256];
      this.skyLightLevels = new byte[256];
      this.overlayBuilders = new OverlayBuilder[256];
      this.mutableBlockPos = new MutableBlockPos();
      this.blockStatePalette = new ArrayList<>();
      this.heightMapBitArray = new SimpleBitStorage(9, 256);
      this.taskCreationSync = new Object();

      for (int i = 0; i < this.overlayBuilders.length; i++) {
         this.overlayBuilders[i] = new OverlayBuilder(overlayManager);
      }

      CompletableFuture<Optional<CompoundTag>>[] chunkNBTCompounds = new CompletableFuture[16];
      this.chunkNBTCompounds = chunkNBTCompounds;
      this.topH = new int[256];
      this.blockStateShortShapeCache = blockStateShortShapeCache;
      this.defaultBiomeKey = Biomes.THE_VOID;
      this.transparentCache = new CachedFunction<>(state -> this.mapProcessor.getMapWriter().shouldOverlay(state));
      this.shouldExtendTillTheBottom = new boolean[256];
      this.firstTransparentStateY = new int[256];
      this.fluidToBlock = new CachedFunction<>(FluidState::createLegacyBlock);
      this.biomeManager = biomeManager;
      this.biomeZoomer = new BiomeManager(biomeManager, biomeZoomSeed);
   }

   public void setMapProcessor(MapProcessor mapProcessor) {
      this.mapProcessor = mapProcessor;
   }

   private void updateHeightArray(int bitsPerHeight) {
      if (this.heightMapBitArray.getBits() != bitsPerHeight) {
         this.heightMapBitArray = new SimpleBitStorage(bitsPerHeight, 256);
      }
   }

   public boolean buildRegion(
      MapRegion region,
      ServerLevel serverWorld,
      HolderLookup<Block> blockLookup,
      Registry<Block> blockRegistry,
      Registry<Fluid> fluidRegistry,
      boolean loading,
      int[] chunkCountDest,
      Executor renderExecutor
   ) {
      if (!loading) {
         region.pushWriterPause();
      }

      boolean result = true;
      int prevRegX = region.getRegionX();
      int prevRegZ = region.getRegionZ() - 1;
      MapRegion prevRegion = this.mapProcessor.getLeafMapRegion(region.getCaveLayer(), prevRegX, prevRegZ, false);
      region.updateCaveMode();
      int caveStart = region.getCaveStart();
      int caveDepth = region.getCaveDepth();
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      boolean worldHasSkylight = serverWorld.dimensionType().hasSkyLight();
      boolean ignoreHeightmaps = this.mapProcessor.getMapWorld().isIgnoreHeightmaps();
      boolean flowers = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.FLOWERS);
      if (!loading && region.getLoadState() != 2) {
         result = false;
      } else {
         serverWorld.getServer().submit(() -> serverWorld.getChunkSource().save(false)).join();
         int worldBottomY = serverWorld.getMinBuildHeight();
         int worldTopY = serverWorld.getMaxBuildHeight();
         ChunkMap chunkManager = serverWorld.getChunkSource().chunkMap;
         Registry<Biome> biomeRegistry = region.getBiomeRegistry();
         Biome theVoid = (Biome)biomeRegistry.get(Biomes.THE_VOID);
         this.biomeManager.resetChunkBiomeData(region.getRegionX(), region.getRegionZ(), theVoid, biomeRegistry);
         CompletableFuture<?> lastFuture = null;

         for (int i = -1; i < 9; i++) {
            for (int j = -1; j < 9; j++) {
               if (i >= 0 && j >= 0 && i < 8 && j < 8) {
                  MapTileChunk tileChunk = region.getChunk(i, j);
                  if (tileChunk == null) {
                     region.setChunk(i, j, tileChunk = new MapTileChunk(region, (region.getRegionX() << 3) + i, (region.getRegionZ() << 3) + j));
                     synchronized (region) {
                        region.setAllCachePrepared(false);
                     }
                  }

                  if (region.isMetaLoaded()) {
                     tileChunk.getLeafTexture().setBufferedTextureVersion(region.getAndResetCachedTextureVersion(i, j));
                  }

                  this.readChunkNBTCompounds(chunkManager, tileChunk);
                  this.buildTileChunk(
                     tileChunk,
                     caveStart,
                     caveDepth,
                     worldHasSkylight,
                     ignoreHeightmaps,
                     prevRegion,
                     serverWorld,
                     blockLookup,
                     blockRegistry,
                     fluidRegistry,
                     biomeRegistry,
                     flowers,
                     worldBottomY,
                     worldTopY
                  );
                  if (!tileChunk.includeInSave() && !tileChunk.hasHighlightsIfUndiscovered()) {
                     region.uncountTextureBiomes(tileChunk.getLeafTexture());
                     region.setChunk(i, j, null);
                     tileChunk.getLeafTexture().deleteTexturesAndBuffers();
                     MapTileChunk var31 = null;
                  } else {
                     if (!loading && !tileChunk.includeInSave() && tileChunk.hasHadTerrain()) {
                        tileChunk.getLeafTexture().deleteColorBuffer();
                        tileChunk.unsetHasHadTerrain();
                        tileChunk.setChanged(false);
                     }

                     if (chunkCountDest != null) {
                        chunkCountDest[0]++;
                     }
                  }
               } else {
                  this.handleTileChunkOutsideRegion(
                     i, j, (region.getRegionX() << 3) + i, (region.getRegionZ() << 3) + j, caveStart, ignoreHeightmaps, biomeRegistry, flowers, chunkManager
                  );
               }

               if (i > 0 && j > 0) {
                  MapTileChunk topLeftTileChunk = region.getChunk(i - 1, j - 1);
                  if (topLeftTileChunk != null && topLeftTileChunk.includeInSave()) {
                     this.fillBiomes(topLeftTileChunk, this.biomeZoomer, biomeRegistry);
                     lastFuture = renderExecutor.submit(() -> {
                        this.transferFilledBiomes(topLeftTileChunk, this.biomeZoomer, biomeRegistry);
                        topLeftTileChunk.setToUpdateBuffers(true);
                        topLeftTileChunk.setChanged(false);
                        topLeftTileChunk.setLoadState((byte)2);
                     });
                  }

                  if (lastFuture != null && i == 8 && j == 8) {
                     lastFuture.join();
                  }
               }
            }
         }

         this.biomeManager.clear();
         if (region.isNormalMapData()) {
            region.setLastSaveTime(System.currentTimeMillis() - 60000L + 1500L);
         }
      }

      if (!loading) {
         region.popWriterPause();
      }

      return result;
   }

   private void readChunkNBTCompounds(ChunkStorage chunkLoader, MapTileChunk tileChunk) {
      for (int xl = 0; xl < 4; xl++) {
         for (int zl = 0; zl < 4; zl++) {
            int i = zl << 2 | xl;
            this.chunkNBTCompounds[i] = chunkLoader.read(new ChunkPos(tileChunk.getX() * 4 + xl, tileChunk.getZ() * 4 + zl));
         }
      }
   }

   public CompoundTag readChunk(RegionFile regionFile, ChunkPos pos) throws IOException {
      try (DataInputStream datainputstream = regionFile.getChunkDataInputStream(pos)) {
         return datainputstream != null ? NbtIo.read(datainputstream) : null;
      }
   }

   private void buildTileChunk(
      MapTileChunk tileChunk,
      int caveStart,
      int caveDepth,
      boolean worldHasSkylight,
      boolean ignoreHeightmaps,
      MapRegion prevRegion,
      ServerLevel serverWorld,
      HolderLookup<Block> blockLookup,
      Registry<Block> blockRegistry,
      Registry<Fluid> fluidRegistry,
      Registry<Biome> biomeRegistry,
      boolean flowers,
      int worldBottomY,
      int worldTopY
   ) {
      tileChunk.unincludeInSave();
      tileChunk.resetHeights();

      for (int insideX = 0; insideX < 4; insideX++) {
         for (int insideZ = 0; insideZ < 4; insideZ++) {
            MapTile tile = tileChunk.getTile(insideX, insideZ);
            int chunkX = (tileChunk.getX() << 2) + insideX;
            int chunkZ = (tileChunk.getZ() << 2) + insideZ;
            CompoundTag nbttagcompound = null;

            try {
               Optional<CompoundTag> optional = this.chunkNBTCompounds[insideZ << 2 | insideX].get();
               if (optional.isPresent()) {
                  nbttagcompound = optional.get();
               }
            } catch (InterruptedException var25) {
            } catch (ExecutionException e) {
               e.printStackTrace();
            }

            if (nbttagcompound == null) {
               if (tile != null) {
                  tileChunk.setChanged(true);
                  tileChunk.setTile(insideX, insideZ, null, this.blockStateShortShapeCache);
                  this.mapProcessor.getTilePool().addToPool(tile);
               }
            } else {
               boolean createdTile = false;
               if (tile == null) {
                  tile = this.mapProcessor.getTilePool().get(this.mapProcessor.getCurrentDimension(), chunkX, chunkZ);
                  createdTile = true;
               }

               DataFixer fixer = Minecraft.getInstance().getFixerUpper();
               int i = nbttagcompound.contains("DataVersion", 99) ? nbttagcompound.getInt("DataVersion") : -1;
               nbttagcompound = DataFixTypes.CHUNK.updateToCurrentVersion(fixer, nbttagcompound, i);
               if (this.buildTile(
                  nbttagcompound,
                  tile,
                  tileChunk,
                  chunkX,
                  chunkZ,
                  chunkX & 31,
                  chunkZ & 31,
                  caveStart,
                  caveDepth,
                  worldHasSkylight,
                  ignoreHeightmaps,
                  serverWorld,
                  blockLookup,
                  blockRegistry,
                  fluidRegistry,
                  biomeRegistry,
                  flowers,
                  worldBottomY,
                  worldTopY
               )) {
                  tile.setWrittenCave(caveStart, caveDepth);
                  tileChunk.setTile(insideX, insideZ, tile, this.blockStateShortShapeCache);
                  if (createdTile) {
                     tileChunk.setChanged(true);
                  }
               } else {
                  tileChunk.setTile(insideX, insideZ, null, this.blockStateShortShapeCache);
                  this.mapProcessor.getTilePool().addToPool(tile);
               }
            }
         }
      }
   }

   private boolean buildTile(
      CompoundTag nbttagcompound,
      MapTile tile,
      MapTileChunk tileChunk,
      int chunkX,
      int chunkZ,
      int insideRegionX,
      int insideRegionZ,
      int caveStart,
      int caveDepth,
      boolean worldHasSkylight,
      boolean ignoreHeightmaps,
      ServerLevel serverWorld,
      HolderLookup<Block> blockLookup,
      Registry<Block> blockRegistry,
      Registry<Fluid> fluidRegistry,
      Registry<Biome> biomeRegistry,
      boolean flowers,
      int worldBottomY,
      int worldTopY
   ) {
      CompoundTag levelCompound = nbttagcompound;
      boolean oldOptimizedChunk = levelCompound.contains("below_zero_retrogen");
      String status = !oldOptimizedChunk ? levelCompound.getString("Status") : levelCompound.getCompound("below_zero_retrogen").getString("target_status");
      int chunkStatusIndex = ChunkStatus.byName(status).getIndex();
      if (chunkStatusIndex < ChunkStatus.BIOMES.getIndex()) {
         return false;
      }

      this.handleChunkBiomes(levelCompound, insideRegionX, insideRegionZ);
      if (chunkStatusIndex < ChunkStatus.FEATURES.getIndex()) {
         return false;
      }

      ListTag sectionsList = levelCompound.getList("sections", 10);
      int fillCounter = 256;
      int[] topH = this.topH;
      int chunkBottomY = levelCompound.getInt("yPos") * 16;
      boolean[] shouldExtendTillTheBottom = this.shouldExtendTillTheBottom;
      boolean cave = caveStart != Integer.MAX_VALUE;
      boolean fullCave = caveStart == Integer.MIN_VALUE;

      for (int i = 0; i < this.blockFound.length; i++) {
         this.overlayBuilders[i].startBuilding();
         this.blockFound[i] = false;
         this.underair[i] = this.shouldEnterGround[i] = fullCave;
         this.lightLevels[i] = 0;
         this.skyLightLevels[i] = (byte)(worldHasSkylight ? 15 : 0);
         topH[i] = worldBottomY;
         shouldExtendTillTheBottom[i] = false;
      }

      boolean oldHeightMap = !levelCompound.contains("Heightmaps", 10);
      int[] oldHeightMapArray = null;
      boolean heightMapExists;
      if (oldHeightMap) {
         oldHeightMapArray = levelCompound.getIntArray("HeightMap");
         heightMapExists = oldHeightMapArray.length == 256;
      } else {
         long[] heightMapArray = levelCompound.getCompound("Heightmaps").getLongArray("WORLD_SURFACE");
         int potentialBitsPerHeight = heightMapArray.length / 4;
         heightMapExists = potentialBitsPerHeight > 0 && potentialBitsPerHeight <= 10;
         if (heightMapExists) {
            this.updateHeightArray(potentialBitsPerHeight);
            System.arraycopy(heightMapArray, 0, this.heightMapBitArray.getRaw(), 0, heightMapArray.length);
         }
      }

      if (levelCompound.contains("isLightOn", 1) && !levelCompound.getBoolean("isLightOn")) {
         boolean lightIsOn = false;
      } else {
         boolean lightIsOn = true;
      }

      int caveStartSectionHeight = (fullCave ? serverWorld.getMaxBuildHeight() - 1 : caveStart) >> 4 << 4;
      int lowH = worldBottomY;
      if (cave && !fullCave) {
         lowH = caveStart + 1 - caveDepth;
         if (lowH < worldBottomY) {
            lowH = worldBottomY;
         }
      }

      int lowHSection = lowH >> 4 << 4;
      boolean transparency = true;
      if (sectionsList.size() == 0) {
         for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
               MapBlock currentPixel = tile.getBlock(i, j);
               this.buildingObject.prepareForWriting(worldBottomY);
               this.buildingObject.write(Blocks.AIR.defaultBlockState(), worldBottomY, worldBottomY, null, (byte)0, false, cave);
               tile.setBlock(i, j, this.buildingObject);
               if (currentPixel != null) {
                  this.buildingObject = currentPixel;
               } else {
                  this.buildingObject = new MapBlock();
               }
            }
         }
      } else {
         ListTag tileEntitiesNbt = levelCompound.getList("block_entities", 10);
         WorldDataChunkTileEntityLookup tileEntityLookup = null;
         if (!tileEntitiesNbt.isEmpty()) {
            tileEntityLookup = new WorldDataChunkTileEntityLookup(tileEntitiesNbt);
         }

         int prevSectionHeight = Integer.MAX_VALUE;
         int sectionHeight = Integer.MAX_VALUE;

         for (int i = sectionsList.size() - 1; i >= 0 && fillCounter > 0; i--) {
            CompoundTag sectionCompound = sectionsList.getCompound(i);
            sectionHeight = sectionCompound.getByte("Y") * 16;
            boolean hasBlocks = false;
            CompoundTag blockStatesCompound = null;
            if (sectionCompound.contains("block_states", 10)) {
               blockStatesCompound = sectionCompound.getCompound("block_states");
               hasBlocks = sectionHeight >= lowHSection;
               if (hasBlocks) {
                  hasBlocks = blockStatesCompound.contains("data", 12);
                  if (!hasBlocks && blockStatesCompound.contains("palette", 9)) {
                     ListTag paletteList = blockStatesCompound.getList("palette", 10);
                     hasBlocks = paletteList.size() == 1 && !((CompoundTag)paletteList.get(0)).get("Name").getAsString().equals("minecraft:air");
                  }
               }
            }

            if (i <= 0 || hasBlocks || sectionCompound.contains("BlockLight", 7) || cave && sectionCompound.contains("SkyLight", 7)) {
               boolean previousSectionExists = prevSectionHeight - sectionHeight == 16;
               boolean underAirByDefault = cave && !previousSectionExists && caveStartSectionHeight > sectionHeight;
               int sectionBasedHeight = sectionHeight + 15;
               boolean preparedSectionData = false;
               boolean hasDifferentBlockStates = false;
               byte[] lightMap = null;
               byte[] skyLightMap = null;
               prevSectionHeight = sectionHeight;

               for (int z = 0; z < 16; z++) {
                  for (int x = 0; x < 16; x++) {
                     int pos_2d = (z << 4) + x;
                     if (!this.blockFound[pos_2d]) {
                        int heightMapValue = heightMapExists
                           ? (oldHeightMap ? oldHeightMapArray[pos_2d] : chunkBottomY + this.heightMapBitArray.get(pos_2d))
                           : Integer.MIN_VALUE;
                        int startHeight;
                        if (cave && !fullCave) {
                           startHeight = caveStart;
                        } else if (!ignoreHeightmaps && heightMapValue >= chunkBottomY) {
                           startHeight = heightMapValue + 3;
                        } else {
                           startHeight = sectionBasedHeight;
                        }

                        if (startHeight >= worldTopY) {
                           startHeight = worldTopY - 1;
                        }

                        if (i <= 0 || ++startHeight >= sectionHeight) {
                           int localStartHeight = 15;
                           if (startHeight >> 4 << 4 == sectionHeight) {
                              localStartHeight = startHeight & 15;
                           }

                           if (!preparedSectionData) {
                              if (hasBlocks) {
                                 ListTag paletteList = blockStatesCompound.getList("palette", 10);
                                 hasDifferentBlockStates = blockStatesCompound.contains("data", 12) && paletteList.size() > 1;
                                 boolean shouldReadPalette = true;
                                 if (hasDifferentBlockStates) {
                                    long[] blockStatesArray = blockStatesCompound.getLongArray("data");
                                    int bits = blockStatesArray.length * 64 / 4096;
                                    int bitsOther = Math.max(4, Mth.ceillog2(paletteList.size()));
                                    if (bitsOther > 8) {
                                       bits = bitsOther;
                                    }

                                    if (this.blockStatesBitArray == null || this.blockStatesBitArray.getBits() != bits) {
                                       this.blockStatesBitArray = new SimpleBitStorage(bits, 4096);
                                    }

                                    if (blockStatesArray.length == this.blockStatesBitArray.getRaw().length) {
                                       System.arraycopy(blockStatesArray, 0, this.blockStatesBitArray.getRaw(), 0, blockStatesArray.length);
                                    } else {
                                       hasDifferentBlockStates = false;
                                       shouldReadPalette = false;
                                    }
                                 }

                                 this.blockStatePalette.clear();
                                 if (shouldReadPalette) {
                                    paletteList.forEach(stateTag -> {
                                       BlockState statex = NbtUtils.readBlockState(blockLookup, (CompoundTag)stateTag);
                                       this.blockStatePalette.add(statex);
                                    });
                                 }
                              }

                              if (sectionCompound.contains("BlockLight", 7)) {
                                 lightMap = sectionCompound.getByteArray("BlockLight");
                                 if (lightMap.length != 2048) {
                                    lightMap = null;
                                 }
                              }

                              if (cave && sectionCompound.contains("SkyLight", 7)) {
                                 skyLightMap = sectionCompound.getByteArray("SkyLight");
                                 if (skyLightMap.length != 2048) {
                                    skyLightMap = null;
                                 }
                              }

                              preparedSectionData = true;
                           }

                           if (underAirByDefault) {
                              this.underair[pos_2d] = true;
                           }

                           for (int y = localStartHeight; y >= 0; y--) {
                              int h = sectionHeight | y;
                              int pos = y << 8 | pos_2d;
                              BlockState state = null;
                              if (hasBlocks) {
                                 int indexInPalette = hasDifferentBlockStates ? this.blockStatesBitArray.get(pos) : 0;
                                 if (indexInPalette < this.blockStatePalette.size()) {
                                    state = this.blockStatePalette.get(indexInPalette);
                                 }
                              }

                              if (state != null
                                 && tileEntityLookup != null
                                 && !(state.getBlock() instanceof AirBlock)
                                 && SupportMods.framedBlocks()
                                 && SupportMods.supportFramedBlocks.isFrameBlock(serverWorld, null, state)) {
                                 CompoundTag tileEntityNbt = tileEntityLookup.getTileEntityNbt(x, h, z);
                                 if (tileEntityNbt != null) {
                                    if (tileEntityNbt.contains("camo_state", 10)) {
                                       try {
                                          state = NbtUtils.readBlockState(blockLookup, tileEntityNbt.getCompound("camo_state"));
                                       } catch (IllegalArgumentException iae) {
                                          state = null;
                                       }
                                    } else if (tileEntityNbt.contains("camo", 10)) {
                                       CompoundTag camoNbt = tileEntityNbt.getCompound("camo");
                                       if (camoNbt.contains("state", 10)) {
                                          try {
                                             state = NbtUtils.readBlockState(blockLookup, camoNbt.getCompound("state"));
                                          } catch (IllegalArgumentException iae) {
                                             state = null;
                                          }
                                       } else if (camoNbt.contains("fluid", 10)) {
                                          CompoundTag fluidTag = camoNbt.getCompound("fluid");
                                          if (fluidTag.contains("Name", 8)) {
                                             String fluidId = fluidTag.getString("Name");
                                             Fluid fluid = (Fluid)fluidRegistry.get(ResourceLocation.parse(fluidId));
                                             state = fluid == null ? null : this.fluidToBlock.apply(fluid.defaultFluidState());
                                          }
                                       }
                                    }
                                 }
                              }

                              if (state == null) {
                                 state = Blocks.AIR.defaultBlockState();
                              }

                              this.mutableBlockPos.set(chunkX << 4 | x, h, chunkZ << 4 | z);
                              OverlayBuilder overlayBuilder = this.overlayBuilders[pos_2d];
                              if (!shouldExtendTillTheBottom[pos_2d] && !overlayBuilder.isEmpty() && this.firstTransparentStateY[pos_2d] - h >= 5) {
                                 shouldExtendTillTheBottom[pos_2d] = true;
                              }

                              boolean buildResult = h >= lowH
                                 && h < startHeight
                                 && this.buildPixel(
                                    this.buildingObject,
                                    state,
                                    x,
                                    h,
                                    z,
                                    pos_2d,
                                    this.lightLevels[pos_2d],
                                    this.skyLightLevels[pos_2d],
                                    null,
                                    cave,
                                    fullCave,
                                    overlayBuilder,
                                    serverWorld,
                                    blockRegistry,
                                    this.mutableBlockPos,
                                    biomeRegistry,
                                    topH,
                                    shouldExtendTillTheBottom[pos_2d],
                                    flowers,
                                    transparency
                                 );
                              if (!buildResult && (y == 0 && i == 0 || h <= lowH)) {
                                 this.lightLevels[pos_2d] = 0;
                                 if (cave) {
                                    this.skyLightLevels[pos_2d] = 0;
                                 }

                                 h = worldBottomY;
                                 state = Blocks.AIR.defaultBlockState();
                                 buildResult = true;
                              }

                              if (buildResult) {
                                 this.buildingObject.prepareForWriting(worldBottomY);
                                 overlayBuilder.finishBuilding(this.buildingObject);
                                 boolean glowing = this.mapProcessor.getMapWriter().isGlowing(state);
                                 byte light = this.lightLevels[pos_2d];
                                 if (cave && light < 15 && this.buildingObject.getNumberOfOverlays() == 0) {
                                    byte skyLight = this.skyLightLevels[pos_2d];
                                    if (skyLight > light) {
                                       light = skyLight;
                                    }
                                 }

                                 this.buildingObject.write(state, h, topH[pos_2d], null, light, glowing, cave);
                                 MapBlock currentPixel = tile.getBlock(x, z);
                                 boolean equalsSlopesExcluded = this.buildingObject.equalsSlopesExcluded(currentPixel);
                                 boolean fullyEqual = this.buildingObject.equals(currentPixel, equalsSlopesExcluded);
                                 if (!fullyEqual) {
                                    tile.setBlock(x, z, this.buildingObject);
                                    if (currentPixel != null) {
                                       this.buildingObject = currentPixel;
                                    } else {
                                       this.buildingObject = new MapBlock();
                                    }

                                    if (!equalsSlopesExcluded) {
                                       tileChunk.setChanged(true);
                                    }
                                 }

                                 this.blockFound[pos_2d] = true;
                                 fillCounter--;
                                 break;
                              }

                              byte dataLight = lightMap == null ? 0 : this.nibbleValue(lightMap, pos);
                              if (cave && dataLight < 15 && worldHasSkylight) {
                                 byte dataSkyLight;
                                 if (!ignoreHeightmaps && !fullCave && startHeight > heightMapValue) {
                                    dataSkyLight = 15;
                                 } else {
                                    dataSkyLight = skyLightMap == null ? 0 : this.nibbleValue(skyLightMap, pos);
                                 }

                                 this.skyLightLevels[pos_2d] = dataSkyLight;
                              }

                              this.lightLevels[pos_2d] = dataLight;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      tile.setWorldInterpretationVersion(1);
      return true;
   }

   private boolean buildPixel(
      MapBlock pixel,
      BlockState state,
      int x,
      int h,
      int z,
      int pos_2d,
      byte light,
      byte skyLight,
      ResourceKey<Biome> biome,
      boolean cave,
      boolean fullCave,
      OverlayBuilder overlayBuilder,
      ServerLevel serverWorld,
      Registry<Block> blockRegistry,
      MutableBlockPos mutableBlockPos,
      Registry<Biome> biomeRegistry,
      int[] topH,
      boolean shouldExtendTillTheBottom,
      boolean flowers,
      boolean transparency
   ) {
      FluidState fluidFluidState = state.getFluidState();
      Block b = state.getBlock();
      if (!fluidFluidState.isEmpty() && (!cave || !this.shouldEnterGround[pos_2d])) {
         this.underair[pos_2d] = true;
         BlockState fluidState = this.fluidToBlock.apply(fluidFluidState);
         if (this.buildPixelHelp(
            pixel,
            fluidState,
            fluidState.getBlock(),
            fluidFluidState,
            pos_2d,
            h,
            cave,
            light,
            skyLight,
            biome,
            overlayBuilder,
            serverWorld,
            blockRegistry,
            biomeRegistry,
            topH,
            shouldExtendTillTheBottom,
            flowers,
            transparency
         )) {
            return true;
         }
      }

      if (b instanceof AirBlock) {
         this.underair[pos_2d] = true;
         return false;
      }

      if (!this.underair[pos_2d] && cave) {
         return false;
      }

      if (b == this.fluidToBlock.apply(fluidFluidState).getBlock()) {
         return false;
      }

      if (cave && this.shouldEnterGround[pos_2d]) {
         if (!state.ignitedByLava() && !state.canBeReplaced() && state.getPistonPushReaction() != PushReaction.DESTROY && !this.shouldOverlayCached(state)) {
            this.underair[pos_2d] = false;
            this.shouldEnterGround[pos_2d] = false;
         }

         return false;
      } else {
         return this.buildPixelHelp(
            pixel,
            state,
            state.getBlock(),
            null,
            pos_2d,
            h,
            cave,
            light,
            skyLight,
            biome,
            overlayBuilder,
            serverWorld,
            blockRegistry,
            biomeRegistry,
            topH,
            shouldExtendTillTheBottom,
            flowers,
            transparency
         );
      }
   }

   private boolean buildPixelHelp(
      MapBlock pixel,
      BlockState state,
      Block b,
      FluidState fluidFluidState,
      int pos_2d,
      int h,
      boolean cave,
      byte light,
      byte skyLight,
      ResourceKey<Biome> dataBiome,
      OverlayBuilder overlayBuilder,
      ServerLevel serverWorld,
      Registry<Block> blockRegistry,
      Registry<Biome> biomeRegistry,
      int[] topH,
      boolean shouldExtendTillTheBottom,
      boolean flowers,
      boolean transparency
   ) {
      if (this.mapProcessor.getMapWriter().isInvisible(state, b, flowers)) {
         return false;
      }

      if (this.shouldOverlayCached((StateHolder<?, ?>)(fluidFluidState == null ? state : fluidFluidState))) {
         if (cave && !this.underair[pos_2d]) {
            return !transparency;
         }

         if (h > topH[pos_2d]) {
            topH[pos_2d] = h;
         }

         byte overlayLight = light;
         if (overlayBuilder.isEmpty()) {
            this.firstTransparentStateY[pos_2d] = h;
            if (cave && skyLight > overlayLight) {
               overlayLight = skyLight;
            }
         }

         if (shouldExtendTillTheBottom) {
            overlayBuilder.getCurrentOverlay().increaseOpacity(overlayBuilder.getCurrentOverlay().getState().getLightBlock(serverWorld, this.mutableBlockPos));
         } else {
            overlayBuilder.build(state, state.getLightBlock(serverWorld, this.mutableBlockPos), overlayLight, this.mapProcessor, dataBiome);
         }

         return !transparency;
      } else {
         if (!this.mapProcessor.getMapWriter().hasVanillaColor(state, serverWorld, blockRegistry, this.mutableBlockPos)) {
            return false;
         }

         if (cave && !this.underair[pos_2d]) {
            return true;
         }

         if (h > topH[pos_2d]) {
            topH[pos_2d] = h;
         }

         return true;
      }
   }

   private void handleTileChunkOutsideRegion(
      int relativeX,
      int relativeZ,
      int actualX,
      int actualZ,
      int caveStart,
      boolean ignoreHeightmaps,
      Registry<Biome> biomeRegistry,
      boolean flowers,
      ChunkStorage chunkLoader
   ) {
      int minInsideX = relativeX < 0 ? 3 : 0;
      int maxInsideX = relativeX > 7 ? 0 : 3;
      int minInsideZ = relativeZ < 0 ? 3 : 0;
      int maxInsideZ = relativeZ > 7 ? 0 : 3;

      for (int insideX = minInsideX; insideX <= maxInsideX; insideX++) {
         for (int insideZ = minInsideZ; insideZ <= maxInsideZ; insideZ++) {
            this.chunkNBTCompounds[insideZ << 2 | insideX] = chunkLoader.read(new ChunkPos(actualX << 2 | insideX, actualZ << 2 | insideZ));
         }
      }

      for (int insideX = minInsideX; insideX <= maxInsideX; insideX++) {
         for (int insideZ = minInsideZ; insideZ <= maxInsideZ; insideZ++) {
            CompoundTag nbt = null;

            try {
               nbt = this.chunkNBTCompounds[insideZ << 2 | insideX].get().orElse(null);
            } catch (InterruptedException | ExecutionException e) {
               e.printStackTrace();
            }

            int insideRegionX = relativeX << 2 | insideX;
            int insideRegionZ = relativeZ << 2 | insideZ;
            if (nbt != null) {
               DataFixer fixer = Minecraft.getInstance().getFixerUpper();
               int i = nbt.contains("DataVersion", 99) ? nbt.getInt("DataVersion") : -1;
               nbt = DataFixTypes.CHUNK.updateToCurrentVersion(fixer, nbt, i);
               this.handleTileOutsideRegion(nbt, insideRegionX, insideRegionZ);
            }
         }
      }
   }

   private void handleTileOutsideRegion(CompoundTag nbt, int insideRegionX, int insideRegionZ) {
      CompoundTag levelCompound = nbt.getCompound("Level");
      String status = levelCompound.getString("Status");
      if (ChunkStatus.byName(status).getIndex() >= ChunkStatus.BIOMES.getIndex()) {
         this.handleChunkBiomes(levelCompound, insideRegionX, insideRegionZ);
      }
   }

   private void handleChunkBiomes(CompoundTag levelCompound, int insideRegionX, int insideRegionZ) {
      ListTag sectionsList = levelCompound.getList("sections", 10);

      for (int i = 0; i < sectionsList.size(); i++) {
         CompoundTag sectionCompound = sectionsList.getCompound(i);
         if (sectionCompound.contains("biomes", 10)) {
            CompoundTag biomesCompound = sectionCompound.getCompound("biomes");
            if (biomesCompound.contains("palette", 9)) {
               ListTag biomePaletteList = biomesCompound.getList("palette", 8);
               long[] biomesLongArray = null;
               if (biomesCompound.contains("data", 12) && biomePaletteList.size() > 1) {
                  biomesLongArray = biomesCompound.getLongArray("data");
               }

               WorldDataReaderSectionBiomeData biomeSection = new WorldDataReaderSectionBiomeData(biomePaletteList, biomesLongArray);
               int sectionIndex = sectionCompound.getByte("Y");
               this.biomeManager.addBiomeSectionForRegionChunk(insideRegionX, insideRegionZ, sectionIndex, biomeSection);
            }
         }
      }
   }

   private void fillBiomes(MapTileChunk tileChunk, BiomeManager biomeZoomer, Registry<Biome> biomeRegistry) {
      try {
         for (int insideX = 0; insideX < 4; insideX++) {
            for (int insideZ = 0; insideZ < 4; insideZ++) {
               MapTile mapTile = tileChunk.getTile(insideX, insideZ);
               if (mapTile != null) {
                  mapTile.setLoaded(true);

                  for (int x = 0; x < 16; x++) {
                     for (int z = 0; z < 16; z++) {
                        MapBlock mapBlock = mapTile.getBlock(x, z);
                        int topHeight = mapBlock.getTopHeight();
                        if (topHeight == 32767) {
                           topHeight = mapBlock.getHeight();
                        }

                        Biome biome = this.biomeManager.getBiome(biomeZoomer, mapTile.getChunkX() << 4 | x, topHeight, mapTile.getChunkZ() << 4 | z);
                        ResourceKey<Biome> biomeKey = (ResourceKey<Biome>)biomeRegistry.getResourceKey(biome).orElse(null);
                        if (biomeKey != null) {
                           mapBlock.setBiome(biomeKey);
                        }
                     }
                  }
               }
            }
         }
      } catch (Throwable t) {
         WorldMap.LOGGER.error("Error filling tile chunk with zoomed biomes", t);
      }
   }

   private void transferFilledBiomes(MapTileChunk tileChunk, BiomeManager biomeZoomer, Registry<Biome> biomeRegistry) {
      try {
         for (int insideX = 0; insideX < 4; insideX++) {
            for (int insideZ = 0; insideZ < 4; insideZ++) {
               MapTile mapTile = tileChunk.getTile(insideX, insideZ);
               if (mapTile != null && mapTile.isLoaded()) {
                  for (int x = 0; x < 16; x++) {
                     for (int z = 0; z < 16; z++) {
                        MapBlock mapBlock = mapTile.getBlock(x, z);
                        tileChunk.getLeafTexture().setBiome(insideX << 4 | x, insideZ << 4 | z, mapBlock.getBiome());
                     }
                  }
               }
            }
         }
      } catch (Throwable t) {
         WorldMap.LOGGER.error("Error transferring filled tile chunk with zoomed biomes", t);
      }
   }

   private boolean shouldOverlayCached(StateHolder<?, ?> state) {
      return this.transparentCache.apply(state);
   }

   private byte nibbleValue(byte[] array, int index) {
      byte b = array[index >> 1];
      return (index & 1) == 0 ? (byte)(b & 15) : (byte)(b >> 4 & 15);
   }
}
