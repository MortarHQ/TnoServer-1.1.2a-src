package xaero.common.minimap.write;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.PitcherCropBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.EmptyLevelChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.lwjgl.opengl.GL11;
import xaero.common.HudMod;
import xaero.common.IXaeroMinimap;
import xaero.common.cache.BlockStateShortShapeCache;
import xaero.common.core.XaeroMinimapCore;
import xaero.common.exception.SilentException;
import xaero.common.minimap.MinimapInterface;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.minimap.highlight.DimensionHighlighterHandler;
import xaero.common.minimap.highlight.HighlighterRegistry;
import xaero.common.minimap.mcworld.MinimapClientWorldData;
import xaero.common.minimap.mcworld.MinimapClientWorldDataHelper;
import xaero.common.minimap.region.MinimapChunk;
import xaero.common.minimap.region.MinimapTile;
import xaero.common.minimap.write.biome.BiomeBlendCalculator;
import xaero.common.misc.CachedFunction;
import xaero.common.misc.Misc;
import xaero.common.misc.OptimizedMath;
import xaero.common.mods.SupportMods;
import xaero.common.settings.ModSettings;
import xaero.hud.minimap.MinimapLogs;
import xaero.hud.minimap.common.config.MinimapConfigConstants;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.config.util.MinimapConfigClientUtils;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.minimap.world.MinimapWorld;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.common.util.ImageIOUtils;

public abstract class MinimapWriter {
   private static final int VOID_COLOR = -16121833;
   private static final float DEFAULT_AMBIENT_LIGHT = 0.7F;
   private static final float DEFAULT_AMBIENT_LIGHT_COLORED = 0.2F;
   private static final float DEFAULT_AMBIENT_LIGHT_WHITE = 0.5F;
   private static final float DEFAULT_MAX_DIRECT_LIGHT = 0.6666667F;
   private static final float GLOWING_MAX_DIRECT_LIGHT = 0.22222224F;
   private static final String[] dimensionsToIgnore = new String[]{"FZHammer"};
   private static final int UPDATE_EVERY_RUNS = 5;
   private static final int MAXIMUM_OVERLAYS = 5;
   public static final int SUN_MINIMUM = 9;
   public static final int NO_Y_VALUE = Integer.MAX_VALUE;
   private static final int MAX_TRANSPARENCY_BLEND_DEPTH = 5;
   private IXaeroMinimap modMain;
   private MinimapSession minimapSession;
   private MinimapWriterHelper helper;
   private MinimapInterface minimapInterface;
   protected final RandomSource usedRandom = RandomSource.create(0L);
   private int loadingSideInChunks;
   private int updateRadius;
   private MinimapChunk[][] loadingBlocks;
   private int loadingMapChunkX;
   private int loadingMapChunkZ;
   private int loadingStartX;
   private int loadingStartZ;
   private int loadingEndX;
   private int loadingEndZ;
   private int loadingCaving;
   private int loadingLevels;
   private boolean loadingLighting;
   private float loadingSingleLevelBrightness;
   private int loadingTerrainSlopes;
   private boolean loadingTerrainDepth;
   private boolean loadingRedstone;
   private int loadingColours;
   private boolean loadingTransparency;
   private boolean loadingBiomesVanillaMode;
   private ResourceKey<Level> loadingDimension;
   private boolean loadingIgnoreHeightmaps;
   private int loadingCaveMapsDepth;
   public int loadingLightOverlayType;
   public int loadingLightOverlayMaxLight;
   public int loadingLightOverlayMinLight;
   public int loadingLightOverlayColor;
   private boolean loadingFlowers;
   private boolean loadingAdjustHeightForCarpetLikeBlocks;
   private boolean loadingStainedGlass;
   private boolean loadingLegibleCaveMode;
   private boolean loadingBiomeBlending;
   private boolean loadingNonWorldMap;
   private Long loadingSlimeSeed;
   private int loadingHighlightVersion;
   private int loadedSideInChunks;
   private MinimapChunk[][] loadedBlocks;
   private int loadedMapChunkX;
   private int loadedMapChunkZ;
   private int loadedCaving;
   private int prevLoadedCaving;
   private int loadedLevels;
   private boolean loadedLighting;
   private int loadedTerrainSlopes;
   private boolean loadedTerrainDepth;
   private boolean loadedRedstone;
   private int loadedColours;
   private boolean loadedTransparency;
   private boolean loadedBiomesVanillaMode;
   private ResourceKey<Level> loadedDimension;
   private boolean loadedIgnoreHeightmaps;
   private int loadedCaveMapsDepth;
   public int loadedLightOverlayType;
   public int loadedLightOverlayMaxLight;
   public int loadedLightOverlayMinLight;
   public int loadedLightOverlayColor;
   private boolean loadedFlowers;
   private boolean loadedAdjustHeightForCarpetLikeBlocks;
   private boolean loadedStainedGlass;
   private boolean loadedLegibleCaveMode;
   private boolean loadedBiomeBlending;
   private boolean loadedNonWorldMap;
   private Long loadedSlimeSeed;
   private int loadedHighlightVersion;
   private long loadedTime;
   private boolean settingsChanged;
   private ArrayList<Long> detectedChunkChanges;
   private int workingFrameCount;
   private long framesFreedTime = -1L;
   public long writeFreeSinceLastWrite = -1L;
   private int writeFreeSizeTiles;
   private int writeFreeFullUpdateTargetTime;
   private int updateChunkX;
   private int updateChunkZ;
   private int tileInsideX;
   private int tileInsideZ;
   private int runNumber;
   private boolean previousShouldLoad;
   private int lastCaving;
   private boolean clearBlockColours;
   private final HashMap<String, Integer> textureColours;
   private final HashMap<Integer, Integer> blockColours;
   private final Object2IntMap<BlockState> blockTintIndices;
   private final CachedFunction<StateHolder<?, ?>, Boolean> transparentCache;
   private final Map<BlockState, Boolean> glowingCache;
   private long lastWrite = -1L;
   private long lastWriteTry = -1L;
   private boolean forcedRefresh;
   private MinimapChunk oldChunk;
   private BlockState lastBlockStateForTextureColor = null;
   private int lastBlockStateForTextureColorResult = -1;
   private CachedFunction<FluidState, BlockState> fluidToBlock;
   private int updates;
   private int loads;
   private long before;
   private int processingTime;
   public boolean debugTotalTime = false;
   public long minTime = -1L;
   public long maxTime = -1L;
   public long totalTime;
   public long totalRuns;
   public long lastDebugTime = -1L;
   public long minTimeDebug;
   public long maxTimeDebug;
   public long averageTimeDebug;
   private long currentComparisonCode;
   private final List<Integer> pixelTransparentSizes;
   private final List<BlockState> pixelBlockStates;
   private final List<Integer> pixelBlockLights;
   private int firstBlockY;
   boolean isglowing;
   private final int[] underRed;
   private final int[] underGreen;
   private final int[] underBlue;
   private int sun;
   private float currentTransparencyMultiplier;
   private int blockY;
   private int blockColor;
   private final int[] red;
   private final int[] green;
   private final int[] blue;
   private final float[] brightness;
   private final float[] postBrightness;
   private final int[] tempColor;
   private boolean underair;
   private BlockState previousTransparentState;
   private int firstTransparentStateY;
   private final MutableBlockPos mutableBlockPos;
   private final MutableBlockPos mutableBlockPos2;
   private final MutableBlockPos mutableBlockPos3;
   private final int[][] intUpdateArrayBuffers;
   private ArrayList<BlockState> buggedStates;
   private final Minecraft mc;
   private final BiomeBlendCalculator biomeBlendCalculator;
   private final BlockStateShortShapeCache blockStateShortShapeCache;
   private final HighlighterRegistry highlighterRegistry;
   private Level prevWorld;
   private DimensionHighlighterHandler dimensionHighlightHandler;

   public MinimapWriter(
      IXaeroMinimap modMain, MinimapSession minimapSession, BlockStateShortShapeCache blockStateShortShapeCache, HighlighterRegistry highlighterRegistry
   ) {
      this.modMain = modMain;
      this.minimapSession = minimapSession;
      this.loadingSideInChunks = 9;
      this.updateRadius = 16;
      this.prevLoadedCaving = this.loadedCaving = this.loadingCaving = Integer.MAX_VALUE;
      this.lastCaving = Integer.MAX_VALUE;
      this.textureColours = new HashMap<>();
      this.blockColours = new HashMap<>();
      this.loadedCaving = Integer.MAX_VALUE;
      this.red = new int[5];
      this.green = new int[5];
      this.blue = new int[5];
      this.underRed = new int[5];
      this.underGreen = new int[5];
      this.underBlue = new int[5];
      this.brightness = new float[5];
      this.postBrightness = new float[5];
      this.tempColor = new int[3];
      this.helper = new MinimapWriterHelper();
      this.mutableBlockPos = new MutableBlockPos();
      this.mutableBlockPos2 = new MutableBlockPos();
      this.mutableBlockPos3 = new MutableBlockPos();
      this.intUpdateArrayBuffers = new int[5][4096];
      this.pixelBlockStates = new ArrayList<>();
      this.pixelTransparentSizes = new ArrayList<>();
      this.pixelBlockLights = new ArrayList<>();
      this.transparentCache = new CachedFunction<>(
         state -> {
            if (state instanceof BlockState blockState) {
               return !(blockState.getBlock() instanceof AirBlock) && !(blockState.getBlock() instanceof TransparentBlock)
                  ? this.blockStateHasTranslucentRenderType(blockState)
                  : true;
            } else {
               FluidState fluidState = (FluidState)state;
               return ItemBlockRenderTypes.getRenderLayer(fluidState) == RenderType.translucent();
            }
         }
      );
      this.glowingCache = new HashMap<>();
      this.minimapInterface = modMain.getInterfaces().getMinimapInterface();
      this.buggedStates = new ArrayList<>();
      this.detectedChunkChanges = new ArrayList<>();
      this.mc = Minecraft.getInstance();
      this.biomeBlendCalculator = new BiomeBlendCalculator();
      this.blockStateShortShapeCache = blockStateShortShapeCache;
      this.fluidToBlock = new CachedFunction<>(FluidState::createLegacyBlock);
      this.highlighterRegistry = highlighterRegistry;
      this.blockTintIndices = new Object2IntOpenHashMap();
   }

   protected abstract boolean blockStateHasTranslucentRenderType(BlockState var1);

   protected abstract int getBlockStateLightEmission(BlockState var1, Level var2, BlockPos var3);

   public void setupDimensionHighlightHandler(ResourceKey<Level> dimension) {
      this.dimensionHighlightHandler = new DimensionHighlighterHandler(dimension, this.highlighterRegistry, this);
   }

   private void updateTimeDebug(long before) {
      if (this.debugTotalTime) {
         long debugPassed = System.nanoTime() - before;
         this.totalTime += debugPassed;
         this.totalRuns++;
         if (debugPassed > this.maxTime) {
            this.maxTime = debugPassed;
         }

         if (this.minTime == -1L || debugPassed < this.minTime) {
            this.minTime = debugPassed;
         }

         long time = System.currentTimeMillis();
         if (this.lastDebugTime == -1L) {
            this.lastDebugTime = time;
         } else if (time - this.lastDebugTime > 1000L) {
            this.maxTimeDebug = this.maxTime;
            this.minTimeDebug = this.minTime;
            this.averageTimeDebug = this.totalTime / this.totalRuns;
            this.maxTime = -1L;
            this.minTime = -1L;
            this.totalTime = 0L;
            this.totalRuns = 0L;
            this.lastDebugTime = time;
         }
      }
   }

   public void onRender() {
      if (ModSettings.canEditIngameSettings()) {
         long before = System.nanoTime();
         MinimapProcessor minimapProcessor = this.minimapSession.getProcessor();

         try {
            Entity player = Minecraft.getInstance().getCameraEntity();
            if (player == null) {
               return;
            }

            Level world = player.level();
            if (world != this.prevWorld) {
               if (world != null) {
                  this.setupDimensionHighlightHandler(world.dimension());
               } else {
                  this.dimensionHighlightHandler = null;
               }

               this.loadedDimension = null;
               this.tileInsideX = this.tileInsideZ = this.updateChunkX = this.updateChunkZ = 0;
               this.prevWorld = world;
               if (this.modMain.getSupportMods().framedBlocks()) {
                  this.modMain.getSupportMods().supportFramedBlocks.onWorldChange();
               }
            }

            double playerX = player.getX();
            double playerY = player.getY();
            double playerZ = player.getZ();
            ClientConfigManager configManager = HudMod.INSTANCE.getHudConfigs().getClientConfigManager();
            boolean displayMinimap = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.DISPLAY_MINIMAP);
            if (this.modMain.getSettings() == null || !displayMinimap || world == null) {
               this.updateTimeDebug(before);
               return;
            }

            int lightOverlayTypeConfig = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.LIGHT_OVERLAY_TYPE);
            int cavingDestination = this.getCaving(playerX, playerY, playerZ, world);
            boolean attemptUsingWorldMapChunks = this.modMain.getSupportMods().shouldUseWorldMapChunks()
               && (cavingDestination == Integer.MAX_VALUE || this.modMain.getSupportMods().shouldUseWorldMapCaveChunks())
               && lightOverlayTypeConfig <= 0;
            boolean shouldLoad = !this.ignoreWorld(world)
               && (
                  !attemptUsingWorldMapChunks
                     || this.loadedNonWorldMap
                     || this.loadingNonWorldMap
                     || this.loadedCaving != cavingDestination
                     || this.loadedCaving != this.loadingCaving
               );
            if (shouldLoad != this.previousShouldLoad) {
               this.tileInsideX = this.tileInsideZ = this.updateChunkX = this.updateChunkZ = 0;
               this.previousShouldLoad = shouldLoad;
            }

            if (!shouldLoad) {
               this.updateTimeDebug(before);
               return;
            }

            XaeroMinimapCore.ensureField();
            int lengthX = Math.min(this.loadingSideInChunks, this.loadingEndX - this.loadingStartX + 1);
            int lengthZ = Math.min(this.loadingSideInChunks, this.loadingEndZ - this.loadingStartZ + 1);
            if (this.lastWriteTry == -1L) {
               lengthX = 3;
               lengthZ = 3;
            } else {
               if (lengthX > this.loadingSideInChunks) {
                  lengthX = this.loadingSideInChunks;
               }

               if (lengthZ > this.loadingSideInChunks) {
                  lengthZ = this.loadingSideInChunks;
               }
            }

            int sizeTileChunks = lengthX * lengthZ;
            int sizeTiles = sizeTileChunks * 4 * 4;
            int sizeBasedTargetTime = sizeTiles * 1000 / 1500;
            int fullUpdateTargetTime = Math.max(100, sizeBasedTargetTime);
            long time = System.currentTimeMillis();
            long passed = this.lastWrite == -1L ? 0L : time - this.lastWrite;
            if (this.lastWriteTry == -1L
               || this.writeFreeSizeTiles != sizeTiles
               || this.writeFreeFullUpdateTargetTime != fullUpdateTargetTime
               || this.workingFrameCount > 30) {
               this.framesFreedTime = time;
               this.writeFreeSizeTiles = sizeTiles;
               this.writeFreeFullUpdateTargetTime = fullUpdateTargetTime;
               this.workingFrameCount = 0;
            }

            long sinceLastWrite = Math.min(passed, this.writeFreeSinceLastWrite);
            if (this.framesFreedTime != -1L) {
               sinceLastWrite = time - this.framesFreedTime;
            }

            long tilesToUpdate = Math.min(sinceLastWrite * sizeTiles / fullUpdateTargetTime, 100L);
            if (this.lastWrite == -1L || tilesToUpdate != 0L) {
               this.lastWrite = time;
            }

            int flickeringTimer = ((Double)configManager.getEffective(MinimapProfiledConfigOptions.CAVE_MODE_TOGGLE_TIMER)).intValue();
            if (tilesToUpdate != 0L) {
               if (this.framesFreedTime == -1L) {
                  int timeLimit = (int)(Math.min(sinceLastWrite, 50L) * 86960L);
                  long writeStartNano = System.nanoTime();
                  if (cavingDestination == Integer.MAX_VALUE != (this.loadingCaving == Integer.MAX_VALUE)
                     || attemptUsingWorldMapChunks == this.loadingNonWorldMap) {
                     this.tileInsideX = this.tileInsideZ = this.updateChunkX = this.updateChunkZ = 0;
                     this.loadedTime = time;
                  }

                  for (int i = 0; i < tilesToUpdate && !this.beforeWriting(attemptUsingWorldMapChunks, cavingDestination, flickeringTimer, time); i++) {
                     if (this.writeChunk(minimapProcessor, playerX, playerY, playerZ, world, cavingDestination, attemptUsingWorldMapChunks)) {
                        i--;
                     }

                     if (System.nanoTime() - writeStartNano >= timeLimit) {
                        break;
                     }
                  }

                  this.workingFrameCount++;
               } else {
                  this.writeFreeSinceLastWrite = sinceLastWrite;
                  this.framesFreedTime = -1L;
               }
            }

            this.lastWriteTry = time;
         } catch (Throwable e) {
            this.minimapInterface.setCrashedWith(e);
         }

         this.updateTimeDebug(before);
      }
   }

   private boolean beforeWriting(boolean attemptUsingWorldMapChunks, int cavingDestination, int flickeringTimer, long time) {
      if (this.tileInsideX == 0 && this.tileInsideZ == 0 && this.updateChunkX == 0 && this.updateChunkZ == 0 && attemptUsingWorldMapChunks) {
         this.loadingCaving = cavingDestination;
         this.loadingNonWorldMap = false;
         if (this.loadedCaving == Integer.MAX_VALUE == (this.loadingCaving == Integer.MAX_VALUE)
            || this.loadedTime == 0L
            || time - this.loadedTime >= flickeringTimer) {
            this.loadedCaving = this.loadingCaving;
            this.loadedNonWorldMap = false;
         }

         if (!this.loadedNonWorldMap) {
            this.detectedChunkChanges.clear();
         }

         return true;
      } else {
         return this.tileInsideX == 3
            && this.tileInsideZ == 3
            && this.updateChunkX == this.loadingSideInChunks - 1
            && this.updateChunkZ == this.loadingSideInChunks - 1
            && this.loadingCaving == Integer.MAX_VALUE != (this.loadedCaving == Integer.MAX_VALUE)
            && this.loadedTime != 0L
            && time - this.loadedTime < flickeringTimer;
      }
   }

   private boolean writeChunk(
      MinimapProcessor minimapProcessor, double playerX, double playerY, double playerZ, Level world, int cavingDestination, boolean attemptUsingWorldMapChunks
   ) {
      long processStart = System.nanoTime();
      if (this.tileInsideX == 0 && this.tileInsideZ == 0) {
         if (this.updateChunkX == 0 && this.updateChunkZ == 0) {
            this.settingsChanged = false;
            if (this.clearBlockColours) {
               this.settingsChanged = true;
               this.clearBlockColours = false;
               if (!this.blockColours.isEmpty()) {
                  this.blockColours.clear();
                  this.blockTintIndices.clear();
                  this.textureColours.clear();
                  this.lastBlockStateForTextureColor = null;
                  this.lastBlockStateForTextureColorResult = -1;
                  MinimapLogs.LOGGER.info("Minimap block colour cache cleaned.");
               }
            }

            this.loadingSideInChunks = this.getLoadSide();
            this.updateRadius = this.getUpdateRadiusInChunks();
            int playerXInt = OptimizedMath.myFloor(playerX);
            int playerZInt = OptimizedMath.myFloor(playerZ);
            this.loadingMapChunkX = this.getMapCoord(this.loadingSideInChunks, playerXInt);
            this.loadingMapChunkZ = this.getMapCoord(this.loadingSideInChunks, playerZInt);
            int loadDistance = (Integer)this.mc.options.renderDistance().get();
            int playerTileX = playerXInt >> 4;
            int playerTileZ = playerZInt >> 4;
            int globalStartX = playerTileX - loadDistance >> 2;
            int globalStartZ = playerTileZ - loadDistance >> 2;
            int globalEndX = playerTileX + loadDistance >> 2;
            int globalEndZ = playerTileZ + loadDistance >> 2;
            this.loadingStartX = globalStartX - this.loadingMapChunkX;
            this.loadingStartZ = globalStartZ - this.loadingMapChunkZ;
            this.loadingEndX = globalEndX - this.loadingMapChunkX;
            this.loadingEndZ = globalEndZ - this.loadingMapChunkZ;
            this.loadingCaving = cavingDestination;
            if (this.loadingCaving != Integer.MAX_VALUE || this.loadedCaving != Integer.MAX_VALUE) {
               int maxDistance = 2;
               if (this.loadingCaving != Integer.MAX_VALUE && this.loadedCaving == Integer.MAX_VALUE) {
                  maxDistance = 1;
               }

               this.loadingStartX = Math.max(this.loadingStartX, this.loadingSideInChunks / 2 - maxDistance);
               this.loadingStartZ = Math.max(this.loadingStartZ, this.loadingSideInChunks / 2 - maxDistance);
               this.loadingEndX = Math.min(this.loadingEndX, this.loadingSideInChunks / 2 + maxDistance);
               this.loadingEndZ = Math.min(this.loadingEndZ, this.loadingSideInChunks / 2 + maxDistance);
            }

            if (this.loadingCaving != this.loadedCaving || this.loadingCaving != Integer.MAX_VALUE && this.prevLoadedCaving == Integer.MAX_VALUE) {
               this.runNumber = 0;
            }

            ClientConfigManager configManager = HudMod.INSTANCE.getHudConfigs().getClientConfigManager();
            this.loadingLighting = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.LIGHTING);
            this.loadingLevels = this.loadingLighting ? 5 : 1;
            this.loadingSingleLevelBrightness = 1.0F;
            this.loadingLegibleCaveMode = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.LEGIBLE_CAVE_MAPS);
            this.loadingBiomeBlending = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.BIOME_BLENDING);
            if (!((ClientLevel)world).effects().forceBrightLightmap() && (!this.loadingLegibleCaveMode || this.loadingCaving == Integer.MAX_VALUE)) {
               if (this.loadingLighting && !world.dimensionType().hasSkyLight()) {
                  this.loadingLevels = 1;
                  this.loadingSingleLevelBrightness = this.minimapInterface.getMinimapFBORenderer().getSunBrightness(minimapProcessor, true);
               }
            } else {
               this.loadingLighting = false;
               this.loadingLevels = 1;
            }

            this.loadingTerrainSlopes = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.TERRAIN_SLOPES);
            this.loadingTerrainDepth = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.TERRAIN_DEPTH);
            this.loadingRedstone = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.DISPLAY_REDSTONE);
            this.loadingColours = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.BLOCK_COLORS);
            this.loadingTransparency = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.BLOCK_TRANSPARENCY);
            this.loadingBiomesVanillaMode = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.BIOMES_IN_VANILLA_COLORS);
            this.loadingDimension = world.dimension();
            this.loadingCaveMapsDepth = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.CAVE_MODE_DEPTH);
            this.loadingIgnoreHeightmaps = this.modMain.getSettings().isIgnoreHeightmaps();
            this.loadingLightOverlayColor = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.LIGHT_OVERLAY_COLOR);
            this.loadingLightOverlayMaxLight = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.LIGHT_OVERLAY_MAX_LIGHT);
            this.loadingLightOverlayMinLight = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.LIGHT_OVERLAY_MIN_LIGHT);
            this.loadingLightOverlayType = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.LIGHT_OVERLAY_TYPE);
            this.loadingFlowers = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.DISPLAY_FLOWERS);
            this.loadingAdjustHeightForCarpetLikeBlocks = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.ADJUST_HEIGHT_FOR_SHORT_BLOCKS);
            MinimapWorld minimapWorld = this.minimapSession.getWorldManager().getAutoWorld();
            if (minimapWorld != null) {
               this.loadingSlimeSeed = MinimapConfigClientUtils.getEffectiveSlimeChunksSeed(minimapWorld);
            }

            this.loadingHighlightVersion = this.dimensionHighlightHandler.getVersion();
            this.loadingStainedGlass = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.DISPLAY_STAINED_GLASS);
            this.loadingNonWorldMap = true;
            this.settingsChanged = this.settingsChanged || this.loadedDimension != this.loadingDimension;
            this.settingsChanged = this.settingsChanged || this.loadedTerrainSlopes != this.loadingTerrainSlopes;
            this.settingsChanged = this.settingsChanged || this.loadedTerrainDepth != this.loadingTerrainDepth;
            this.settingsChanged = this.settingsChanged || this.loadedRedstone != this.loadingRedstone;
            this.settingsChanged = this.settingsChanged || this.loadedColours != this.loadingColours;
            this.settingsChanged = this.settingsChanged || this.loadedTransparency != this.loadingTransparency;
            this.settingsChanged = this.settingsChanged || this.loadingBiomesVanillaMode != this.loadedBiomesVanillaMode;
            this.settingsChanged = this.settingsChanged || this.loadingCaveMapsDepth != this.loadedCaveMapsDepth;
            this.settingsChanged = this.settingsChanged || this.loadingIgnoreHeightmaps != this.loadedIgnoreHeightmaps;
            this.settingsChanged = this.settingsChanged || this.loadingLightOverlayColor != this.loadedLightOverlayColor;
            this.settingsChanged = this.settingsChanged || this.loadingLightOverlayMaxLight != this.loadedLightOverlayMaxLight;
            this.settingsChanged = this.settingsChanged || this.loadingLightOverlayMinLight != this.loadedLightOverlayMinLight;
            this.settingsChanged = this.settingsChanged || this.loadingLightOverlayType != this.loadedLightOverlayType;
            this.settingsChanged = this.settingsChanged || this.loadingFlowers != this.loadedFlowers;
            this.settingsChanged = this.settingsChanged || this.loadingAdjustHeightForCarpetLikeBlocks != this.loadedAdjustHeightForCarpetLikeBlocks;
            this.settingsChanged = this.settingsChanged || this.loadingCaving == Integer.MAX_VALUE != (this.loadedCaving == Integer.MAX_VALUE);
            this.settingsChanged = this.settingsChanged || !Objects.equals(this.loadingSlimeSeed, this.loadedSlimeSeed);
            this.settingsChanged = this.settingsChanged || this.loadingHighlightVersion != this.loadedHighlightVersion;
            this.settingsChanged = this.settingsChanged || this.loadingStainedGlass != this.loadedStainedGlass;
            this.settingsChanged = this.settingsChanged || this.loadingLegibleCaveMode != this.loadedLegibleCaveMode;
            this.settingsChanged = this.settingsChanged || this.loadingLighting != this.loadedLighting;
            this.settingsChanged = this.settingsChanged || this.loadingBiomeBlending != this.loadedBiomeBlending;
            this.settingsChanged = this.settingsChanged || this.loadingNonWorldMap != this.loadedNonWorldMap;
            if (this.loadingBlocks == null || this.loadingBlocks.length != this.loadingSideInChunks) {
               this.loadingBlocks = new MinimapChunk[this.loadingSideInChunks][this.loadingSideInChunks];
            }

            if (this.minimapInterface.usingFBO() && minimapProcessor.isToResetImage()) {
               this.forcedRefresh = true;
               minimapProcessor.setToResetImage(false);
            }

            this.biomeBlendCalculator.prepare(world, this.loadingBiomeBlending);
         }

         this.oldChunk = null;
         if (this.loadedBlocks != null) {
            int updateChunkXOld = this.loadingMapChunkX + this.updateChunkX - this.loadedMapChunkX;
            int updateChunkZOld = this.loadingMapChunkZ + this.updateChunkZ - this.loadedMapChunkZ;
            if (updateChunkXOld > -1 && updateChunkXOld < this.loadedBlocks.length && updateChunkZOld > -1 && updateChunkZOld < this.loadedBlocks.length) {
               this.oldChunk = this.loadedBlocks[updateChunkXOld][updateChunkZOld];
            }
         }
      }

      MinimapChunk mchunk = this.loadingBlocks[this.updateChunkX][this.updateChunkZ];
      if (mchunk == null) {
         mchunk = this.loadingBlocks[this.updateChunkX][this.updateChunkZ] = new MinimapChunk(
            this.loadingMapChunkX + this.updateChunkX, this.loadingMapChunkZ + this.updateChunkZ
         );
      } else if (this.tileInsideX == 0 && this.tileInsideZ == 0) {
         mchunk.reset(this.loadingMapChunkX + this.updateChunkX, this.loadingMapChunkZ + this.updateChunkZ);
      }

      boolean onlyLoad = this.runNumber % 5 != 0 && this.loadingLightOverlayType <= 0;
      boolean outsideRender = this.updateChunkX < this.loadingStartX
         || this.updateChunkX > this.loadingEndX
         || this.updateChunkZ < this.loadingStartZ
         || this.updateChunkZ > this.loadingEndZ;
      MinimapChunk topChunk = this.updateChunkZ > 0 ? this.loadingBlocks[this.updateChunkX][this.updateChunkZ - 1] : null;
      MinimapChunk topLeftChunk = this.updateChunkX > 0 && this.updateChunkZ > 0 ? this.loadingBlocks[this.updateChunkX - 1][this.updateChunkZ - 1] : null;
      MinimapChunk leftChunk = this.updateChunkX > 0 ? this.loadingBlocks[this.updateChunkX - 1][this.updateChunkZ] : null;
      boolean wasProperWrite = this.writeTile(
         minimapProcessor,
         playerX,
         playerY,
         playerZ,
         world,
         mchunk,
         this.oldChunk,
         topChunk,
         topLeftChunk,
         leftChunk,
         this.updateChunkX,
         this.updateChunkZ,
         this.tileInsideX,
         this.tileInsideZ,
         onlyLoad,
         outsideRender
      );
      if (this.loadingLightOverlayType > 0 && this.loadedLightOverlayType > 0) {
         onlyLoad = true;
      }

      this.tileInsideZ++;
      if (this.tileInsideZ >= 4) {
         this.tileInsideZ = 0;
         this.tileInsideX++;
         if (this.tileInsideX >= 4) {
            this.tileInsideX = 0;
            mchunk = this.loadingBlocks[this.updateChunkX][this.updateChunkZ];
            if (this.minimapInterface.usingFBO() && mchunk.isHasSomething() && mchunk.isChanged()) {
               mchunk.updateBuffers(this.loadingLevels, this.intUpdateArrayBuffers);
               mchunk.setChanged(false);
            }

            mchunk.setLevelsBuffered(this.loadingLevels);
            if (this.updateChunkX == this.loadingSideInChunks - 1 && this.updateChunkZ == this.loadingSideInChunks - 1) {
               if (this.runNumber % 5 == 0 && !MinimapTile.recycled.isEmpty()) {
                  MinimapTile.recycled.subList(MinimapTile.recycled.size() / 2, MinimapTile.recycled.size()).clear();
               }

               if (this.loadedBlocks != null) {
                  for (int i = 0; i < this.loadedBlocks.length; i++) {
                     for (int j = 0; j < this.loadedBlocks.length; j++) {
                        MinimapChunk m = this.loadedBlocks[i][j];
                        MinimapChunk lm = null;
                        if (m != null) {
                           m.recycleTiles();
                           int loadingX = this.loadedMapChunkX + i - this.loadingMapChunkX;
                           int loadingZ = this.loadedMapChunkZ + j - this.loadingMapChunkZ;
                           if (loadingX > -1 && loadingZ > -1 && loadingX < this.loadingSideInChunks && loadingZ < this.loadingSideInChunks) {
                              lm = this.loadingBlocks[loadingX][loadingZ];
                           }

                           boolean shouldTransfer = m.getLevelsBuffered() == this.loadingLevels && lm != null;
                           if (shouldTransfer) {
                              synchronized (m) {
                                 m.setBlockTextureUpload(true);
                              }
                           }

                           for (int l = 0; l < m.getLevelsBuffered(); l++) {
                              if (m.getGlTexture(l) != 0) {
                                 if (shouldTransfer) {
                                    lm.setGlTexture(l, m.getGlTexture(l));
                                 } else {
                                    GL11.glDeleteTextures(m.getGlTexture(l));
                                 }
                              }

                              if (shouldTransfer && !lm.isRefreshRequired(l) && m.isRefreshRequired(l)) {
                                 lm.copyBuffer(l, m.getBuffer(l));
                                 lm.setRefreshRequired(l, true);
                                 m.setRefreshRequired(l, false);
                              }
                           }
                        }
                     }
                  }
               }

               synchronized (this) {
                  MinimapChunk[][] bu = this.loadedBlocks;
                  this.loadedSideInChunks = this.loadingSideInChunks;
                  this.loadedBlocks = this.loadingBlocks;
                  this.loadingBlocks = bu;
                  this.loadedMapChunkX = this.loadingMapChunkX;
                  this.loadedMapChunkZ = this.loadingMapChunkZ;
                  this.loadedLevels = this.loadingLevels;
                  this.loadedLighting = this.loadingLighting;
                  this.loadedTerrainSlopes = this.loadingTerrainSlopes;
                  this.loadedTerrainDepth = this.loadingTerrainDepth;
                  this.loadedRedstone = this.loadingRedstone;
                  this.loadedColours = this.loadingColours;
                  this.loadedTransparency = this.loadingTransparency;
                  this.loadedBiomesVanillaMode = this.loadingBiomesVanillaMode;
                  this.loadedDimension = this.loadingDimension;
                  this.loadedCaveMapsDepth = this.loadingCaveMapsDepth;
                  this.loadedIgnoreHeightmaps = this.loadingIgnoreHeightmaps;
                  this.loadedLightOverlayColor = this.loadingLightOverlayColor;
                  this.loadedLightOverlayMaxLight = this.loadingLightOverlayMaxLight;
                  this.loadedLightOverlayMinLight = this.loadingLightOverlayMinLight;
                  this.loadedLightOverlayType = this.loadingLightOverlayType;
                  this.loadedFlowers = this.loadingFlowers;
                  this.loadedAdjustHeightForCarpetLikeBlocks = this.loadingAdjustHeightForCarpetLikeBlocks;
                  this.loadedSlimeSeed = this.loadingSlimeSeed;
                  this.loadedHighlightVersion = this.loadingHighlightVersion;
                  this.loadedStainedGlass = this.loadingStainedGlass;
                  this.loadedLegibleCaveMode = this.loadingLegibleCaveMode;
                  this.loadedBiomeBlending = this.loadingBiomeBlending;
                  this.loadedTime = System.currentTimeMillis();
               }

               this.detectedChunkChanges.clear();
               this.prevLoadedCaving = this.loadedCaving;
               this.loadedCaving = this.loadingCaving;
               this.loadedNonWorldMap = true;
               this.forcedRefresh = false;
               this.runNumber++;
            }

            this.updateChunkZ++;
            if (this.updateChunkZ >= this.loadingSideInChunks) {
               this.updateChunkZ = 0;
               this.updateChunkX = (this.updateChunkX + 1) % this.loadingSideInChunks;
            }
         }
      }

      int passed = (int)(System.nanoTime() - processStart);
      return outsideRender && !wasProperWrite;
   }

   private boolean writeTile(
      MinimapProcessor minimapProcessor,
      double playerX,
      double playerY,
      double playerZ,
      Level world,
      MinimapChunk mchunk,
      MinimapChunk oldChunk,
      MinimapChunk topChunk,
      MinimapChunk topLeftChunk,
      MinimapChunk leftChunk,
      int canvasX,
      int canvasZ,
      int insideX,
      int insideZ,
      boolean onlyLoad,
      boolean outsideRender
   ) {
      int tileX = mchunk.getX() * 4 + insideX;
      int tileZ = mchunk.getZ() * 4 + insideZ;
      int halfSide = this.loadingSideInChunks / 2;
      int tileFromCenterX = canvasX - halfSide;
      int tileFromCenterZ = canvasZ - halfSide;
      MinimapTile oldTile = null;
      if (oldChunk != null) {
         oldTile = oldChunk.getTile(insideX, insideZ);
      }

      LevelChunk bchunk = (LevelChunk)world.getChunk(tileX, tileZ, ChunkStatus.FULL, false);
      boolean neighborsLoaded = true;

      for (int i = -1; i < 2; i++) {
         for (int j = -1; j < 2; j++) {
            if (i != 0 || j != 0) {
               ChunkAccess nChunk = world.getChunk(tileX + i, tileZ + j);
               if (nChunk == null || nChunk instanceof EmptyLevelChunk) {
                  neighborsLoaded = false;
                  break;
               }
            }
         }
      }

      boolean chunkUpdated = false;
      boolean chunkIsClean = true;

      try {
         chunkIsClean = bchunk == null || (Boolean)XaeroMinimapCore.chunkCleanField.get(bchunk);
      } catch (IllegalArgumentException | IllegalAccessException e) {
         throw new RuntimeException(e);
      }

      chunkUpdated = !chunkIsClean;
      if (chunkIsClean && bchunk != null && this.detectedChunkChanges.contains(Misc.getChunkPosAsLong(bchunk))) {
         chunkUpdated = true;
      }

      chunkUpdated = chunkUpdated || oldTile == null || oldTile.caveLevel != this.loadingCaving;
      boolean effectivelyUnloaded = bchunk == null || bchunk instanceof EmptyLevelChunk || !neighborsLoaded;
      int mRegionX = mchunk.getX() >> 3;
      int mRegionZ = mchunk.getZ() >> 3;
      int mChunkInsideX = mchunk.getX() & 7;
      int mChunkInsideZ = mchunk.getZ() & 7;
      boolean needsHighlights = this.dimensionHighlightHandler
         .shouldApplyTileChunkHighlights(mRegionX, mRegionZ, mChunkInsideX, mChunkInsideZ, !effectivelyUnloaded);
      if ((needsHighlights && oldTile == null || !outsideRender && !effectivelyUnloaded)
         && (
            chunkUpdated
                  && !onlyLoad
                  && tileFromCenterX <= this.updateRadius
                  && tileFromCenterZ <= this.updateRadius
                  && tileFromCenterX >= -this.updateRadius
                  && tileFromCenterZ >= -this.updateRadius
               || oldTile == null
               || !oldTile.isSuccess()
               || oldChunk.getLevelsBuffered() != this.loadingLevels
               || this.settingsChanged
         )) {
         if (oldTile != null && oldChunk.getLevelsBuffered() != this.loadingLevels) {
            oldTile = null;
         }

         MinimapTile tile = null;
         Types typeWorldSurface = Types.WORLD_SURFACE;
         MinimapClientWorldData worldData = MinimapClientWorldDataHelper.getWorldData((ClientLevel)world);
         float shadowR = worldData.shadowR;
         float shadowG = worldData.shadowG;
         float shadowB = worldData.shadowB;
         int playerYi = (int)playerY;
         boolean sameCaveLevel = oldTile != null && this.loadingCaving == oldTile.caveLevel;
         boolean sameHighlights = oldTile != null
            && this.loadedDimension == this.loadingDimension
            && this.loadingHighlightVersion == oldTile.getHighlightVersion();
         boolean settingsChanged = this.settingsChanged;
         int loadingCaving = this.loadingCaving;
         int loadingLevels = this.loadingLevels;
         boolean loadingLighting = this.loadingLighting;
         float loadingSingleLevelBrightness = this.loadingSingleLevelBrightness;
         int loadingTerrainSlopes = this.loadingTerrainSlopes;
         boolean loadingTerrainDepth = this.loadingTerrainDepth;
         List<Integer> pixelTransparentSizes = this.pixelTransparentSizes;
         List<BlockState> pixelBlockStates = this.pixelBlockStates;
         List<Integer> pixelBlockLights = this.pixelBlockLights;
         int[] underRed = this.underRed;
         int[] underGreen = this.underGreen;
         int[] underBlue = this.underBlue;
         float[] postBrightness = this.postBrightness;
         float[] brightness = this.brightness;
         int[] red = this.red;
         int[] green = this.green;
         int[] blue = this.blue;
         int[] tempColor = this.tempColor;
         boolean loadingIgnoreHeightmaps = this.loadingIgnoreHeightmaps;
         int loadingCaveMapsDepth = this.loadingCaveMapsDepth;
         MutableBlockPos mutableBlockPos = this.mutableBlockPos;
         MutableBlockPos mutableBlockPos2 = this.mutableBlockPos2;
         MutableBlockPos mutableBlockPos3 = this.mutableBlockPos3;
         Long loadingSlimeSeed = this.loadingSlimeSeed;
         int loadedLevels = this.loadedLevels;
         IXaeroMinimap modMain = this.modMain;
         MinimapWriterHelper helper = this.helper;
         int loadingColours = this.loadingColours;
         boolean loadingRedstone = this.loadingRedstone;
         boolean loadingTransparency = this.loadingTransparency;
         int loadingLightOverlayType = this.loadingLightOverlayType;
         int loadingLightOverlayMaxLight = this.loadingLightOverlayMaxLight;
         int loadingLightOverlayMinLight = this.loadingLightOverlayMinLight;
         int loadingLightOverlayColor = this.loadingLightOverlayColor;
         boolean loadingFlowers = this.loadingFlowers;
         boolean adjustHeightForCarpetLikeBlocks = this.loadingAdjustHeightForCarpetLikeBlocks;
         boolean loadingStainedGlass = this.loadingStainedGlass;
         boolean loadingLegibleCaveMode = this.loadingLegibleCaveMode;
         boolean framedBlocksExist = modMain.getSupportMods().framedBlocks();
         int[] highlights = oldTile != null && needsHighlights ? oldTile.getHighlights() : null;
         if (needsHighlights && (highlights == null || !sameHighlights)) {
            highlights = this.dimensionHighlightHandler.applyChunkHighlightColors(tileX, tileZ);
         }

         if (effectivelyUnloaded) {
            tile = MinimapTile.getANewTile(modMain.getSettings(), tileX, tileZ, loadingSlimeSeed);

            for (int l = 0; l < loadingLevels; l++) {
               float overlayBrightness = this.getBlockBrightness(
                  9.0F, loadingCaving == Integer.MAX_VALUE || !loadingLighting && !loadingLegibleCaveMode ? 15 : 0, l, 0
               );

               for (int blockX = 0; blockX < 16; blockX++) {
                  for (int blockZ = 0; blockZ < 16; blockZ++) {
                     int highlight = highlights[blockZ << 4 | blockX];
                     int hlRed = highlight >> 8 & 0xFF;
                     int hlGreen = highlight >> 16 & 0xFF;
                     int hlBlue = highlight >> 24 & 0xFF;
                     tile.setRGB(l, blockX, blockZ, (int)(hlRed * overlayBrightness), (int)(hlGreen * overlayBrightness), (int)(hlBlue * overlayBrightness));
                     tile.setCode(blockX, blockZ, 0L, (byte)0, (byte)0, (byte)0, (byte)0);
                  }
               }
            }

            mchunk.setTile(this.tileInsideX, this.tileInsideZ, tile);
            tile.setSuccess(false);
            tile.setHasSomething(true);
            mchunk.setHasSomething(true);
            mchunk.setChanged(true);
         } else {
            int sectionBasedHeight = loadingIgnoreHeightmaps ? this.getSectionBasedHeight(bchunk, 64) : 0;

            for (int blockX = 0; blockX < 16; blockX++) {
               for (int blockZ = 0; blockZ < 16; blockZ++) {
                  tile = this.loadBlockColor(
                     playerYi,
                     world,
                     blockX,
                     blockZ,
                     bchunk,
                     tileX,
                     tileZ,
                     insideX,
                     insideZ,
                     sectionBasedHeight,
                     typeWorldSurface,
                     oldTile,
                     mchunk,
                     topChunk,
                     topLeftChunk,
                     leftChunk,
                     shadowR,
                     shadowG,
                     shadowB,
                     sameCaveLevel,
                     sameHighlights,
                     canvasX,
                     canvasZ,
                     !needsHighlights ? 0 : highlights[blockZ << 4 | blockX],
                     settingsChanged,
                     loadingCaving,
                     loadingLevels,
                     loadingLighting,
                     loadingSingleLevelBrightness,
                     loadingTerrainSlopes,
                     loadingTerrainDepth,
                     pixelTransparentSizes,
                     pixelBlockStates,
                     pixelBlockLights,
                     underRed,
                     underGreen,
                     underBlue,
                     postBrightness,
                     brightness,
                     red,
                     green,
                     blue,
                     tempColor,
                     loadingIgnoreHeightmaps,
                     loadingCaveMapsDepth,
                     mutableBlockPos,
                     mutableBlockPos2,
                     loadingSlimeSeed,
                     loadedLevels,
                     modMain,
                     helper,
                     loadingColours,
                     loadingRedstone,
                     loadingTransparency,
                     loadingLightOverlayType,
                     loadingLightOverlayMaxLight,
                     loadingLightOverlayMinLight,
                     loadingLightOverlayColor,
                     loadingFlowers,
                     adjustHeightForCarpetLikeBlocks,
                     loadingStainedGlass,
                     loadingLegibleCaveMode,
                     mutableBlockPos3,
                     framedBlocksExist
                  );
               }
            }

            tile.setHasTerrain(true);
         }

         tile.caveLevel = loadingCaving;
         tile.setHighlights(highlights);
         tile.setHighlightVersion(this.loadingHighlightVersion);
         if (!chunkIsClean) {
            long chunkPosLong = Misc.getChunkPosAsLong(bchunk);
            if (!this.detectedChunkChanges.contains(chunkPosLong)) {
               this.detectedChunkChanges.add(chunkPosLong);
            }

            try {
               XaeroMinimapCore.chunkCleanField.set(bchunk, true);
            } catch (IllegalArgumentException | IllegalAccessException e) {
               throw new RuntimeException(e);
            }
         }

         return true;
      } else {
         if (oldTile != null && oldChunk.getLevelsBuffered() == this.loadingLevels && !this.settingsChanged) {
            mchunk.setTile(insideX, insideZ, oldTile);
            oldTile.setWasTransfered(true);
            if (oldTile.isHasSomething()) {
               mchunk.setHasSomething(true);
            }

            if (this.forcedRefresh) {
               mchunk.setChanged(true);
            }
         }

         return false;
      }
   }

   public MinimapTile loadBlockColor(
      int playerYi,
      Level world,
      int insideX,
      int insideZ,
      LevelChunk bchunk,
      int tileX,
      int tileZ,
      int tileInsideX,
      int tileInsideZ,
      int sectionBasedHeight,
      Types typeWorldSurface,
      MinimapTile oldTile,
      MinimapChunk mchunk,
      MinimapChunk topChunk,
      MinimapChunk topLeftChunk,
      MinimapChunk leftChunk,
      float shadowR,
      float shadowG,
      float shadowB,
      boolean sameCaveLevel,
      boolean sameHighlights,
      int canvasX,
      int canvasZ,
      int highlight,
      boolean settingsChanged,
      int loadingCaving,
      int loadingLevels,
      boolean loadingLighting,
      float loadingSingleLevelBrightness,
      int loadingTerrainSlopes,
      boolean loadingTerrainDepth,
      List<Integer> pixelTransparentSizes,
      List<BlockState> pixelBlockStates,
      List<Integer> pixelBlockLights,
      int[] underRed,
      int[] underGreen,
      int[] underBlue,
      float[] postBrightness,
      float[] brightness,
      int[] red,
      int[] green,
      int[] blue,
      int[] tempColor,
      boolean loadingIgnoreHeightmaps,
      int loadingCaveMapsDepth,
      MutableBlockPos mutableBlockPos,
      MutableBlockPos mutableBlockPos2,
      Long loadingSlimeSeed,
      int loadedLevels,
      IXaeroMinimap modMain,
      MinimapWriterHelper helper,
      int loadingColours,
      boolean loadingRedstone,
      boolean loadingTransparency,
      int loadingLightOverlayType,
      int loadingLightOverlayMaxLight,
      int loadingLightOverlayMinLight,
      int loadingLightOverlayColor,
      boolean loadingFlowers,
      boolean adjustHeightForCarpetLikeBlocks,
      boolean loadingStainedGlass,
      boolean loadingLegibleCaveMode,
      MutableBlockPos mutableBlockPos3,
      boolean framedBlocksExist
   ) {
      int worldBottomY = world.getMinBuildHeight();
      int highY;
      if (loadingCaving != Integer.MAX_VALUE) {
         highY = loadingCaving;
      } else {
         int height = bchunk.getHeight(typeWorldSurface, insideX, insideZ);
         if (!loadingIgnoreHeightmaps && height >= worldBottomY) {
            highY = height;
         } else {
            highY = sectionBasedHeight;
         }
      }

      if (highY >= world.getMaxBuildHeight()) {
         highY = world.getMaxBuildHeight() - 1;
      }

      int bottom = loadingCaving != Integer.MAX_VALUE ? highY + 1 - loadingCaveMapsDepth : worldBottomY;
      int lowY = bottom;
      if (lowY < worldBottomY) {
         lowY = worldBottomY;
      }

      pixelTransparentSizes.clear();
      pixelBlockStates.clear();
      pixelBlockLights.clear();
      this.currentComparisonCode = 0L;
      byte currentComparisonCodeAdd = 0;
      byte currentComparisonCodeAdd2 = 0;
      this.blockY = 0;

      for (int i = 0; i < loadingLevels; i++) {
         underRed[i] = 0;
         underGreen[i] = 0;
         underBlue[i] = 0;
      }

      this.currentTransparencyMultiplier = 1.0F;
      this.sun = 15;
      this.blockColor = 0;
      this.isglowing = false;
      double secondaryBR = 1.0;
      double secondaryBG = 1.0;
      double secondaryBB = 1.0;
      Block block = this.findBlock(
         world,
         bchunk,
         insideX,
         insideZ,
         highY,
         lowY,
         loadingCaving,
         loadingRedstone,
         mutableBlockPos,
         mutableBlockPos2,
         loadingColours,
         loadingTransparency,
         pixelBlockLights,
         pixelBlockStates,
         loadingLevels,
         loadingLighting,
         pixelTransparentSizes,
         loadingFlowers,
         loadingStainedGlass,
         mutableBlockPos3,
         framedBlocksExist
      );
      BlockState state = pixelBlockStates.isEmpty() ? null : pixelBlockStates.get(pixelBlockStates.size() - 1);
      if (adjustHeightForCarpetLikeBlocks && state != null && this.blockStateShortShapeCache.isShort(state)) {
         this.blockY--;
      }

      boolean isglowing = this.isglowing;
      int blockY = this.blockY;
      long currentComparisonCode = this.currentComparisonCode;
      boolean success = true;
      int prevHeight = Integer.MAX_VALUE;
      int prevHeightDiagonal = Integer.MAX_VALUE;
      int prevInsideX = insideX - 1;
      int prevInsideZ = insideZ - 1;
      boolean xEdge = prevInsideX < 0;
      boolean zEdge = prevInsideZ < 0;
      MinimapTile tile = mchunk.getTile(tileInsideX, tileInsideZ);
      MinimapTile prevHeightSrc = tile;
      MinimapTile prevHeightDiagonalSrc = tile;
      if (zEdge) {
         prevInsideZ = 15;
         if (tileInsideZ > 0) {
            prevHeightSrc = mchunk.getTile(tileInsideX, tileInsideZ - 1);
         } else if (topChunk != null) {
            prevHeightSrc = topChunk.getTile(tileInsideX, 3);
         }
      }

      if (xEdge) {
         prevInsideX = 15;
         if (zEdge) {
            if (tileInsideZ > 0 && tileInsideX > 0) {
               prevHeightDiagonalSrc = mchunk.getTile(tileInsideX - 1, tileInsideZ - 1);
            } else if (tileInsideX == 0 && tileInsideZ == 0) {
               if (topLeftChunk != null) {
                  prevHeightDiagonalSrc = topLeftChunk.getTile(3, 3);
               }
            } else if (tileInsideX == 0) {
               if (leftChunk != null) {
                  prevHeightDiagonalSrc = leftChunk.getTile(3, tileInsideZ - 1);
               }
            } else if (topChunk != null) {
               prevHeightDiagonalSrc = topChunk.getTile(tileInsideX - 1, 3);
            }
         } else if (tileInsideX > 0) {
            prevHeightDiagonalSrc = mchunk.getTile(tileInsideX - 1, tileInsideZ);
         } else if (leftChunk != null) {
            prevHeightDiagonalSrc = leftChunk.getTile(3, tileInsideZ);
         }
      } else {
         prevHeightDiagonalSrc = prevHeightSrc;
      }

      if (prevHeightSrc != null && (prevHeightSrc == tile || prevHeightSrc.hasTerrain())) {
         prevHeight = prevHeightSrc.getHeight(insideX, prevInsideZ);
         if (prevHeightSrc != tile && prevHeightSrc.caveLevel != loadingCaving) {
            success = false;
         }
      } else if (zEdge) {
         prevHeight = blockY;
         if (pixelTransparentSizes.isEmpty()) {
            try {
               ChunkAccess prevChunk = world.getChunk(tileX, tileZ - 1);
               if (prevChunk != null) {
                  prevHeight = prevChunk.getHeight(typeWorldSurface, insideX, prevInsideZ);
               }
            } catch (IllegalStateException var116) {
            }
         }

         success = false;
      }

      if (prevHeightDiagonalSrc == null || prevHeightDiagonalSrc != tile && !prevHeightDiagonalSrc.hasTerrain()) {
         if (xEdge || zEdge) {
            prevHeightDiagonal = blockY;
            if (pixelTransparentSizes.isEmpty()) {
               try {
                  ChunkAccess prevChunk = xEdge && zEdge
                     ? world.getChunk(tileX - 1, tileZ - 1)
                     : (zEdge ? world.getChunk(tileX, tileZ - 1) : world.getChunk(tileX - 1, tileZ));
                  if (prevChunk != null) {
                     prevHeightDiagonal = prevChunk.getHeight(typeWorldSurface, prevInsideX, prevInsideZ);
                  }
               } catch (IllegalStateException var117) {
               }
            }

            success = false;
         }
      } else {
         prevHeightDiagonal = prevHeightDiagonalSrc.getHeight(prevInsideX, prevInsideZ);
         if (prevHeightDiagonalSrc != tile && prevHeightDiagonalSrc.caveLevel != loadingCaving) {
            success = false;
         }
      }

      int verticalSlope = 0;
      int diagonalSlope = 0;
      if (loadingTerrainSlopes > 0) {
         if (prevHeight != Integer.MAX_VALUE) {
            verticalSlope = Math.max(-128, Math.min(127, blockY - prevHeight));
         }

         if (prevHeightDiagonal != Integer.MAX_VALUE) {
            diagonalSlope = Math.max(-128, Math.min(127, blockY - prevHeightDiagonal));
         }
      }

      for (int i = 0; i < pixelBlockLights.size(); i++) {
         int l = pixelBlockLights.get(i);
         if (i <= 1) {
            currentComparisonCodeAdd = (byte)(currentComparisonCodeAdd | l << 4 * i + 1);
         }

         if (i >= 1) {
            currentComparisonCode |= l << 4 * (i - 1) >> 3;
         }
      }

      int add2Calculation = 17;

      for (int i = 0; i < pixelTransparentSizes.size(); i++) {
         add2Calculation = add2Calculation * 37 + pixelTransparentSizes.get(i);
      }

      currentComparisonCodeAdd = (byte)(currentComparisonCodeAdd | add2Calculation >> 8 & 1);
      currentComparisonCodeAdd2 = (byte)add2Calculation;
      boolean reuseColour = !settingsChanged
         && sameCaveLevel
         && sameHighlights
         && !oldTile.pixelChanged(
            insideX, insideZ, currentComparisonCode, currentComparisonCodeAdd, currentComparisonCodeAdd2, (byte)verticalSlope, (byte)diagonalSlope
         );
      if (!reuseColour) {
         if (highlight != 0 && block == null) {
            this.sun = 0;
         }

         int firstSun = this.sun;
         boolean hasTransparentLayer = highlight != 0 || !pixelTransparentSizes.isEmpty();
         if (hasTransparentLayer && firstSun != 15) {
            this.sun = 15;
         }

         if (highlight != 0) {
            int hlRed = highlight >> 8 & 0xFF;
            int hlGreen = highlight >> 16 & 0xFF;
            int hlBlue = highlight >> 24 & 0xFF;
            int hlAlpha = highlight & 0xFF;
            float hlAlphaFloat = hlAlpha / 255.0F;
            this.applyTransparentLayer(hlRed, hlGreen, hlBlue, hlAlphaFloat, true);
         }

         boolean legibleCaveMode = loadingLegibleCaveMode && loadingCaving != Integer.MAX_VALUE;
         this.calculateBlockColors(
            world,
            bchunk,
            insideX,
            insideZ,
            mutableBlockPos2,
            pixelTransparentSizes,
            pixelBlockStates,
            pixelBlockLights,
            loadingColours,
            loadingLightOverlayColor,
            loadingCaving,
            loadingLevels,
            loadingLighting,
            loadingSingleLevelBrightness,
            legibleCaveMode
         );
         int blockColor = this.blockColor;
         float currentTransparencyMultiplier = this.currentTransparencyMultiplier;
         int sun = this.sun;
         if (block == null) {
            sun = 15;
         }

         isglowing = block != null && isglowing;
         int topLight = pixelBlockLights.isEmpty() ? 0 : pixelBlockLights.get(0);
         int cr = blockColor >> 16 & 0xFF;
         int cg = blockColor >> 8 & 0xFF;
         int cb = blockColor & 0xFF;
         if (isglowing) {
            helper.getGlowingColour(cr, cg, cb, tempColor);
            cr = tempColor[0];
            cg = tempColor[1];
            cb = tempColor[2];
            if (hasTransparentLayer && pixelTransparentSizes.isEmpty()) {
               topLight = 15;
            }
         }

         if (!isglowing || hasTransparentLayer) {
            int blockLight = pixelBlockLights.isEmpty() ? 0 : pixelBlockLights.get(pixelBlockLights.size() - 1);
            int firstBlockY = this.firstBlockY;

            for (int i = 0; i < loadingLevels; i++) {
               postBrightness[i] = 1.0F;
               if (legibleCaveMode) {
                  if (!isglowing) {
                     brightness[i] = block == null ? 1.0F : (1.0F + blockY - bottom) / (1 + highY - bottom);
                  }

                  if (hasTransparentLayer) {
                     float transparentLayerCaveBrightness = block == null && pixelTransparentSizes.isEmpty()
                        ? this.getFixedSkyLightBlockBrightness(9.0F, 0.0F, 0)
                        : (1.0F + firstBlockY - bottom) / (1 + highY - bottom);
                     underRed[i] = (int)(underRed[i] * transparentLayerCaveBrightness);
                     underGreen[i] = (int)(underGreen[i] * transparentLayerCaveBrightness);
                     underBlue[i] = (int)(underBlue[i] * transparentLayerCaveBrightness);
                  }
               } else {
                  if (!isglowing) {
                     if (!hasTransparentLayer) {
                        if (block == null) {
                           brightness[i] = 1.0F;
                        } else {
                           brightness[i] = loadingLevels != 1
                              ? this.getBlockBrightness(9.0F, sun, i, blockLight)
                              : this.getFixedSkyLightBlockBrightness(9.0F, loadingSingleLevelBrightness, blockLight);
                        }
                     } else {
                        brightness[i] = this.getBlockBrightness(9.0F, sun, 0, blockLight);
                     }
                  }

                  if (hasTransparentLayer) {
                     postBrightness[i] = loadingLevels != 1
                        ? this.getBlockBrightness(9.0F, firstSun, i, topLight)
                        : this.getFixedSkyLightBlockBrightness(9.0F, loadingSingleLevelBrightness, topLight);
                  }
               }
            }
         }

         float depthBrightness = 1.0F;
         if (block != null && !isglowing && loadingTerrainDepth && !legibleCaveMode) {
            if (loadingCaving != Integer.MAX_VALUE) {
               depthBrightness = 0.7F + 0.3F * (blockY - bottom) / (highY - bottom);
            } else {
               depthBrightness = blockY / 63.0F;
            }

            float max = loadingTerrainSlopes >= 2 ? 1.0F : 1.15F;
            float min = loadingTerrainSlopes >= 2 ? 0.9F : 0.7F;
            if (depthBrightness > max) {
               depthBrightness = max;
            } else if (depthBrightness < min) {
               depthBrightness = min;
            }
         }

         if (block != null && loadingTerrainSlopes > 0) {
            if (loadingTerrainSlopes == 1) {
               if (!isglowing) {
                  if (verticalSlope > 0) {
                     depthBrightness = (float)(depthBrightness * 1.15);
                  } else if (verticalSlope < 0) {
                     depthBrightness = (float)(depthBrightness * 0.85);
                  }
               }
            } else {
               float ambientLightColored = 0.2F;
               float ambientLightWhite = 0.5F;
               float maxDirectLight = 0.6666667F;
               if (isglowing) {
                  ambientLightColored = 0.0F;
                  ambientLightWhite = 1.0F;
                  maxDirectLight = 0.22222224F;
               }

               float cos = 0.0F;
               if (loadingTerrainSlopes == 2) {
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
               secondaryBR *= shadowR * ambientLightColored + whiteLight;
               secondaryBG *= shadowG * ambientLightColored + whiteLight;
               secondaryBB *= shadowB * ambientLightColored + whiteLight;
            }
         }

         secondaryBR *= depthBrightness;
         secondaryBG *= depthBrightness;
         secondaryBB *= depthBrightness;
         if (loadingLightOverlayType > 0) {
            int blockLight = pixelBlockLights.isEmpty() ? 0 : pixelBlockLights.get(0);
            int testLight = loadingLightOverlayType == 1 ? blockLight : (loadingLightOverlayType == 2 ? firstSun : Math.max(blockLight, firstSun));
            if (testLight >= loadingLightOverlayMinLight && testLight <= loadingLightOverlayMaxLight) {
               int overlayColor = MinimapConfigConstants.COLORS[loadingLightOverlayColor];
               int overlayRed = (overlayColor >> 16 & 0xFF) * 2 / 3;
               int overlayGreen = (overlayColor >> 8 & 0xFF) * 2 / 3;
               int overlayBlue = (overlayColor & 0xFF) * 2 / 3;

               for (int i = 0; i < loadingLevels; i++) {
                  float destColorScale = (isglowing ? 1.0F : postBrightness[i]) / 3.0F;
                  underRed[i] = (int)(underRed[i] * destColorScale);
                  underGreen[i] = (int)(underGreen[i] * destColorScale);
                  underBlue[i] = (int)(underBlue[i] * destColorScale);
                  brightness[i] *= destColorScale;
                  postBrightness[i] = 1.0F;
                  underRed[i] += overlayRed;
                  underGreen[i] += overlayGreen;
                  underBlue[i] += overlayBlue;
               }

               if (isglowing) {
                  secondaryBR /= 3.0;
                  secondaryBG /= 3.0;
                  secondaryBB /= 3.0;
               }
            }
         }

         for (int i = 0; i < loadingLevels; i++) {
            float b;
            if (isglowing) {
               b = 1.0F;
               if (!hasTransparentLayer) {
                  postBrightness[i] = 1.0F;
               }
            } else {
               b = brightness[i];
            }

            red[i] = (int)((cr * b * secondaryBR * currentTransparencyMultiplier + underRed[i]) * postBrightness[i]);
            if (red[i] > 255) {
               red[i] = 255;
            }

            green[i] = (int)((cg * b * secondaryBG * currentTransparencyMultiplier + underGreen[i]) * postBrightness[i]);
            if (green[i] > 255) {
               green[i] = 255;
            }

            blue[i] = (int)((cb * b * secondaryBB * currentTransparencyMultiplier + underBlue[i]) * postBrightness[i]);
            if (blue[i] > 255) {
               blue[i] = 255;
            }
         }
      } else {
         for (int i = 0; i < loadingLevels; i++) {
            red[i] = oldTile.getRed(i, insideX, insideZ);
            green[i] = oldTile.getGreen(i, insideX, insideZ);
            blue[i] = oldTile.getBlue(i, insideX, insideZ);
         }
      }

      if (tile == null) {
         tile = MinimapTile.getANewTile(modMain.getSettings(), tileX, tileZ, loadingSlimeSeed);
         mchunk.setTile(tileInsideX, tileInsideZ, tile);
      }

      if (this.notEmptyColor(red, green, blue)) {
         tile.setHasSomething(true);
         mchunk.setHasSomething(true);
      }

      tile.setHeight(insideX, insideZ, blockY);
      tile.setCode(insideX, insideZ, currentComparisonCode, currentComparisonCodeAdd, currentComparisonCodeAdd2, (byte)verticalSlope, (byte)diagonalSlope);
      if (tile.isSuccess()) {
         tile.setSuccess(success);
      }

      if (oldTile != null) {
         int oldTileDarkestLevel = loadedLevels - 1;
         int tileDarkestLevel = loadingLevels - 1;
         if (oldTile.getRed(oldTileDarkestLevel, insideX, insideZ) != red[tileDarkestLevel]
            || oldTile.getGreen(oldTileDarkestLevel, insideX, insideZ) != green[tileDarkestLevel]
            || oldTile.getBlue(oldTileDarkestLevel, insideX, insideZ) != blue[tileDarkestLevel]) {
            mchunk.setChanged(true);
         }
      } else {
         mchunk.setChanged(true);
      }

      for (int i = 0; i < loadingLevels; i++) {
         tile.setRGB(i, insideX, insideZ, red[i], green[i], blue[i]);
      }

      return tile;
   }

   private BlockState unpackFramedBlocks(BlockState original, Level world, BlockPos globalPos) {
      if (original.getBlock() instanceof AirBlock) {
         return original;
      }

      BlockState result = original;
      SupportMods supportMods = this.modMain.getSupportMods();
      if (supportMods.supportFramedBlocks.isFrameBlock(world, null, original)) {
         BlockEntity tileEntity = world.getBlockEntity(globalPos);
         if (tileEntity != null) {
            result = supportMods.supportFramedBlocks.unpackFramedBlock(world, null, original, tileEntity);
            if (result == null || result.getBlock() instanceof AirBlock) {
               result = original;
            }
         }
      }

      return result;
   }

   public Block findBlock(
      Level world,
      LevelChunk bchunk,
      int insideX,
      int insideZ,
      int highY,
      int lowY,
      int loadingCaving,
      boolean loadingRedstone,
      MutableBlockPos mutableBlockPos,
      MutableBlockPos mutableBlockPos2,
      int loadingColours,
      boolean loadingTransparency,
      List<Integer> pixelBlockLights,
      List<BlockState> pixelBlockStates,
      int loadingLevels,
      boolean loadingLighting,
      List<Integer> pixelTransparentSizes,
      boolean loadingFlowers,
      boolean loadingStainedGlass,
      MutableBlockPos mutableBlockPos3,
      boolean framedBlocksExist
   ) {
      this.underair = loadingCaving == Integer.MAX_VALUE;
      this.previousTransparentState = null;
      if (highY != Integer.MAX_VALUE && lowY != Integer.MAX_VALUE) {
         boolean shouldExtendTillTheBottom = false;
         int transparentSkipY = 0;

         for (int i = highY; i >= lowY; i = shouldExtendTillTheBottom ? transparentSkipY : i - 1) {
            mutableBlockPos.set(insideX, i, insideZ);
            BlockPos globalPos = mutableBlockPos2.set((bchunk.getPos().x << 4) + insideX, i, (bchunk.getPos().z << 4) + insideZ);
            BlockState state = bchunk.getBlockState(mutableBlockPos);
            if (state == null) {
               state = Blocks.AIR.defaultBlockState();
            }

            if (framedBlocksExist) {
               state = this.unpackFramedBlocks(state, world, globalPos);
            }

            FluidState fluidFluidState = state.getFluidState();
            shouldExtendTillTheBottom = !shouldExtendTillTheBottom && !pixelBlockStates.isEmpty() && this.firstTransparentStateY - i >= 5;
            if (shouldExtendTillTheBottom) {
               for (transparentSkipY = i - 1; transparentSkipY >= lowY; transparentSkipY--) {
                  BlockState traceState = bchunk.getBlockState(mutableBlockPos3.set(insideX, transparentSkipY, insideZ));
                  if (traceState == null) {
                     traceState = Blocks.AIR.defaultBlockState();
                  }

                  FluidState traceFluidState = traceState.getFluidState();
                  if (!traceFluidState.isEmpty()) {
                     if (!this.isTransparent(traceFluidState)) {
                        break;
                     }

                     if (!(traceState.getBlock() instanceof AirBlock) && traceState.getBlock() == this.fluidToBlock.apply(traceFluidState).getBlock()) {
                        continue;
                     }
                  }

                  if (!this.isTransparent(traceState)) {
                     break;
                  }
               }
            }

            if (!fluidFluidState.isEmpty()) {
               this.underair = true;
               BlockState fluidState = this.fluidToBlock.apply(fluidFluidState);
               if (this.findBlockHelp(
                  world,
                  bchunk,
                  insideX,
                  i,
                  insideZ,
                  fluidState,
                  fluidFluidState,
                  transparentSkipY,
                  shouldExtendTillTheBottom,
                  loadingCaving,
                  loadingRedstone,
                  mutableBlockPos,
                  mutableBlockPos2,
                  loadingColours,
                  loadingTransparency,
                  pixelBlockLights,
                  pixelBlockStates,
                  loadingLevels,
                  loadingLighting,
                  pixelTransparentSizes,
                  loadingFlowers,
                  loadingStainedGlass
               )) {
                  return fluidState.getBlock();
               }

               if (!(state.getBlock() instanceof AirBlock) && state.getBlock() == this.fluidToBlock.apply(fluidFluidState).getBlock()) {
                  continue;
               }
            }

            if ((state.getBlock() instanceof AirBlock || this.underair)
               && this.findBlockHelp(
                  world,
                  bchunk,
                  insideX,
                  i,
                  insideZ,
                  state,
                  null,
                  transparentSkipY,
                  shouldExtendTillTheBottom,
                  loadingCaving,
                  loadingRedstone,
                  mutableBlockPos,
                  mutableBlockPos2,
                  loadingColours,
                  loadingTransparency,
                  pixelBlockLights,
                  pixelBlockStates,
                  loadingLevels,
                  loadingLighting,
                  pixelTransparentSizes,
                  loadingFlowers,
                  loadingStainedGlass
               )) {
               return state.getBlock();
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private boolean findBlockHelp(
      Level world,
      ChunkAccess bchunk,
      int insideX,
      int i,
      int insideZ,
      BlockState state,
      FluidState fluidFluidState,
      int transparentSkipY,
      boolean shouldExtendTillTheBottom,
      int loadingCaving,
      boolean loadingRedstone,
      MutableBlockPos mutableBlockPos,
      MutableBlockPos mutableBlockPos2,
      int loadingColours,
      boolean loadingTransparency,
      List<Integer> pixelBlockLights,
      List<BlockState> pixelBlockStates,
      int loadingLevels,
      boolean loadingLighting,
      List<Integer> pixelTransparentSizes,
      boolean loadingFlowers,
      boolean loadingStainedGlass
   ) {
      Block got = state.getBlock();
      if (!(got instanceof AirBlock)) {
         boolean isRedstone = false;
         if (!(got instanceof LiquidBlock) && state.getRenderShape() == RenderShape.INVISIBLE) {
            return false;
         }

         if (got == Blocks.TORCH) {
            return false;
         }

         if (got == Blocks.SHORT_GRASS) {
            return false;
         }

         if (got != Blocks.GLASS
            && got != Blocks.GLASS_PANE
            && (loadingStainedGlass || !(got instanceof StainedGlassBlock) && !(got instanceof StainedGlassPaneBlock))) {
            boolean isFlower = got instanceof PitcherCropBlock || got instanceof TallFlowerBlock || got instanceof FlowerBlock || state.is(BlockTags.FLOWERS);
            if (got instanceof DoublePlantBlock && !isFlower) {
               return false;
            }

            if (isFlower && !loadingFlowers) {
               return false;
            }

            isRedstone = got == Blocks.REDSTONE_TORCH || got == Blocks.REDSTONE_WIRE || got instanceof RepeaterBlock || got instanceof ComparatorBlock;
            if (!loadingRedstone && isRedstone) {
               return false;
            }

            if (this.buggedStates.contains(state)) {
               return false;
            }

            this.blockY = i;
            BlockPos globalPos = mutableBlockPos2;
            MapColor mapColor = null;

            try {
               mapColor = state.getMapColor(world, globalPos);
            } catch (Throwable t) {
               this.buggedStates.add(state);
               MinimapLogs.LOGGER
                  .info("Broken vanilla map color definition found: " + ((Registry)world.registryAccess().registry(Registries.BLOCK).get()).getKey(got));
            }

            boolean isTransparent = loadingTransparency
               && (state == this.previousTransparentState || this.isTransparent((StateHolder<?, ?>)(fluidFluidState == null ? state : fluidFluidState)));
            if ((isTransparent || isRedstone) && loadingColours != 1 || mapColor != null && mapColor.col != 0) {
               if (!this.underair) {
                  return !isTransparent;
               }

               BlockPos lightPos = mutableBlockPos.set(globalPos.getX(), globalPos.getY() + 1, globalPos.getZ());
               if (this.currentComparisonCode == 0L) {
                  this.firstBlockY = i;
                  if (loadingLighting && loadingCaving != Integer.MAX_VALUE) {
                     this.sun = world.getBrightness(LightLayer.SKY, lightPos);
                  }
               }

               if (loadingTransparency && isTransparent) {
                  if (!shouldExtendTillTheBottom && pixelBlockStates.size() < 5 && state != this.previousTransparentState) {
                     if (pixelBlockStates.isEmpty()) {
                        this.firstTransparentStateY = i;
                     }

                     this.currentComparisonCode = this.currentComparisonCode + (Block.getId(state) & 4294967295L);
                     pixelBlockStates.add(state);
                     pixelTransparentSizes.add(1);
                     pixelBlockLights.add(!loadingLighting ? 0 : world.getBrightness(LightLayer.BLOCK, lightPos));
                     this.previousTransparentState = state;
                  } else {
                     int depthToAdd = 1;
                     if (shouldExtendTillTheBottom) {
                        depthToAdd = i - transparentSkipY;
                     }

                     pixelTransparentSizes.set(pixelTransparentSizes.size() - 1, pixelTransparentSizes.get(pixelTransparentSizes.size() - 1) + depthToAdd);
                  }

                  return false;
               } else {
                  this.currentComparisonCode = this.currentComparisonCode + (Block.getId(state) & 4294967295L);
                  this.currentComparisonCode <<= 29;
                  this.currentComparisonCode |= (i & 4095L) << 17;
                  pixelBlockLights.add(!loadingLighting ? 0 : world.getBrightness(LightLayer.BLOCK, lightPos));
                  pixelBlockStates.add(state);
                  this.isglowing = this.isGlowing(state, world, globalPos);
                  return true;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         if (got instanceof AirBlock) {
            this.underair = true;
         }

         return false;
      }
   }

   private void calculateBlockColors(
      Level world,
      LevelChunk bchunk,
      int insideX,
      int insideZ,
      MutableBlockPos mutableBlockPos2,
      List<Integer> pixelTransparentSizes,
      List<BlockState> pixelBlockStates,
      List<Integer> pixelBlockLights,
      int loadingColours,
      int loadingLightOverlayColor,
      int loadingCaving,
      int loadingLevels,
      boolean loadingLighting,
      float loadingSingleLevelBrightness,
      boolean legibleCaveMaps
   ) {
      int firstBlockY = this.firstBlockY;
      MutableBlockPos globalPos = mutableBlockPos2.set(bchunk.getPos().x * 16 + insideX, firstBlockY, bchunk.getPos().z * 16 + insideZ);
      if (!pixelTransparentSizes.isEmpty()) {
         for (int i = 0; i < pixelTransparentSizes.size(); i++) {
            BlockState state = pixelBlockStates.get(i);
            Block b = state.getBlock();
            int size = pixelTransparentSizes.get(i);
            int opacity = state.getLightBlock(bchunk.getLevel(), globalPos);
            this.applyTransparentLayer(
               world, bchunk, b, state, opacity * size, globalPos, pixelBlockLights.get(i), loadingLighting, loadingSingleLevelBrightness, legibleCaveMaps
            );
            int nextY = globalPos.getY() - size;
            globalPos.setY(nextY);
         }
      }

      if (pixelBlockStates.size() > pixelTransparentSizes.size()) {
         BlockState state = pixelBlockStates.get(pixelBlockStates.size() - 1);
         Block b = state.getBlock();
         int color;
         if (loadingColours == 1) {
            MapColor minimapColor = state.getMapColor(world, globalPos);
            color = minimapColor.col;
         } else {
            color = this.loadBlockColourFromTexture(world, state, b, globalPos, true);
         }

         this.blockColor = this.addBlockColorMultipliers(color, state, world, globalPos);
      } else {
         this.blockColor = loadingCaving != Integer.MAX_VALUE ? 0 : -16121833;
      }
   }

   private boolean isTransparent(StateHolder<?, ?> state) {
      return this.transparentCache.apply(state);
   }

   private boolean isGlowing(BlockState state, Level world, BlockPos pos) {
      Boolean cachedValue = this.glowingCache.get(state);
      if (cachedValue != null) {
         return cachedValue;
      }

      boolean isGlowing = false;

      try {
         isGlowing = this.getBlockStateLightEmission(state, world, pos) > 0;
      } catch (Exception var7) {
      }

      this.glowingCache.put(state, isGlowing);
      return isGlowing;
   }

   private void applyTransparentLayer(
      Level world,
      LevelChunk bchunk,
      Block b,
      BlockState state,
      int opacity,
      BlockPos globalPos,
      int blockLight,
      boolean lighting,
      float loadingSingleLevelBrightness,
      boolean legibleCaveMaps
   ) {
      int red = 0;
      int green = 0;
      int blue = 0;
      float vanillaTransparency = b instanceof LiquidBlock ? 0.75F : (b instanceof IceBlock ? 0.85F : 0.5F);
      int color;
      if (this.loadingColours == 0) {
         color = this.loadBlockColourFromTexture(world, state, b, globalPos, true);
      } else {
         color = state.getMapColor(world, globalPos).col;
         color = (int)(255.0F * vanillaTransparency) << 24 | color & 16777215;
      }

      color = this.addBlockColorMultipliers(color, state, world, globalPos);
      red = color >> 16 & 0xFF;
      green = color >> 8 & 0xFF;
      blue = color & 0xFF;
      float transparency = (color >> 24 & 0xFF) / 255.0F;
      if (transparency == 0.0F) {
         transparency = vanillaTransparency;
      }

      if (this.isGlowing(state, bchunk.getLevel(), globalPos)) {
         this.helper.getGlowingColour(red, green, blue, this.tempColor);
         red = this.tempColor[0];
         green = this.tempColor[1];
         blue = this.tempColor[2];
      }

      float brightness = legibleCaveMaps
         ? 1.0F
         : (
            lighting
               ? this.getBlockBrightness(9.0F, this.sun, 0, blockLight)
               : this.getFixedSkyLightBlockBrightness(9.0F, loadingSingleLevelBrightness, blockLight)
         );
      this.applyTransparentLayer(red, green, blue, transparency * brightness, false);
      this.sun -= opacity;
      if (this.sun < 0) {
         this.sun = 0;
      }
   }

   private void applyTransparentLayer(int red, int green, int blue, float transparency, boolean premultiplied) {
      float overlayIntensity = this.currentTransparencyMultiplier * (premultiplied ? 1.0F : transparency);

      for (int i = 0; i < this.loadingLevels; i++) {
         this.underRed[i] = (int)(this.underRed[i] + red * overlayIntensity);
         this.underGreen[i] = (int)(this.underGreen[i] + green * overlayIntensity);
         this.underBlue[i] = (int)(this.underBlue[i] + blue * overlayIntensity);
      }

      this.currentTransparencyMultiplier *= 1.0F - transparency;
   }

   protected abstract List<BakedQuad> getQuads(BakedModel var1, BlockState var2, Direction var3);

   protected abstract TextureAtlasSprite getParticleIcon(BlockModelShaper var1, BakedModel var2, BlockState var3);

   private int loadBlockColourFromTexture(Level world, BlockState state, Block b, BlockPos pos, boolean convert) {
      if (state == this.lastBlockStateForTextureColor) {
         return this.lastBlockStateForTextureColorResult;
      }

      int stateHash = Block.getId(state);
      Integer c = this.blockColours.get(stateHash);
      int red = 0;
      int green = 0;
      int blue = 0;
      int alpha = 0;
      if (c == null) {
         String name = null;
         int tintIndex = -1;

         try {
            List<BakedQuad> upQuads = null;
            BlockModelShaper bms = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper();
            BakedModel model = bms.getBlockModel(state);
            if (convert) {
               upQuads = this.getQuads(model, state, Direction.UP);
            }

            TextureAtlasSprite missingTexture = Minecraft.getInstance()
               .getModelManager()
               .getAtlas(TextureAtlas.LOCATION_BLOCKS)
               .getSprite(MissingTextureAtlasSprite.getLocation());
            TextureAtlasSprite texture;
            if (upQuads != null && !upQuads.isEmpty() && upQuads.get(0).getSprite() != missingTexture) {
               texture = upQuads.get(0).getSprite();
               tintIndex = upQuads.get(0).getTintIndex();
            } else {
               texture = this.getParticleIcon(bms, model, state);
               tintIndex = 0;
               if (texture == missingTexture) {
                  for (int i = Direction.values().length - 1; i >= 0; i--) {
                     if (i != 1) {
                        List<BakedQuad> quads = this.getQuads(model, state, Direction.values()[i]);
                        if (!quads.isEmpty()) {
                           texture = quads.get(0).getSprite();
                           tintIndex = quads.get(0).getTintIndex();
                           if (texture != missingTexture) {
                              break;
                           }
                        }
                     }
                  }
               }
            }

            if (texture == null) {
               throw new SilentException("No texture for " + state);
            }

            name = texture.contents().name() + ".png";
            c = -1;
            String[] args = name.split(":");
            if (args.length < 2) {
               args = new String[]{"minecraft", args[0]};
            }

            Integer cachedColour = this.textureColours.get(name);
            if (cachedColour == null) {
               ResourceLocation location = ResourceLocation.fromNamespaceAndPath(args[0], "textures/" + args[1]);
               Resource resource = (Resource)Minecraft.getInstance().getResourceManager().getResource(location).orElse(null);
               if (resource == null) {
                  throw new SilentException("No texture " + location);
               }

               ImageInputStream input = ImageIO.createImageInputStream(resource.open());
               BufferedImage img = ImageIOUtils.getImageThroughZipError(input, location.toString());
               input.close();
               if (img == null) {
                  throw new SilentException("No image loaded " + location);
               }

               red = 0;
               green = 0;
               blue = 0;
               int total = 0;
               int ts = Math.min(img.getWidth(), img.getHeight());
               if (ts > 0) {
                  int diff = Math.max(1, Math.min(4, ts / 8));
                  int parts = ts / diff;
                  Raster raster = img.getData();
                  int[] colorHolder = null;

                  for (int i = 0; i < parts; i++) {
                     for (int j = 0; j < parts; j++) {
                        int rgb;
                        if (img.getColorModel().getNumComponents() < 3) {
                           colorHolder = raster.getPixel(i * diff, j * diff, colorHolder);
                           int sample = colorHolder[0] & 0xFF;
                           int a = 255;
                           if (colorHolder.length > 1) {
                              a = colorHolder[1];
                           }

                           rgb = a << 24 | sample << 16 | sample << 8 | sample;
                        } else {
                           rgb = img.getRGB(i * diff, j * diff);
                        }

                        int a = rgb >> 24 & 0xFF;
                        if (rgb != 0 && a != 0) {
                           red += rgb >> 16 & 0xFF;
                           green += rgb >> 8 & 0xFF;
                           blue += rgb & 0xFF;
                           alpha += a;
                           total++;
                        }
                     }
                  }
               }

               if (total == 0) {
                  total = 1;
               }

               red /= total;
               green /= total;
               blue /= total;
               alpha /= total;
               if (convert && red == 0 && green == 0 && blue == 0) {
                  throw new SilentException("Black texture " + ts);
               }

               c = alpha << 24 | red << 16 | green << 8 | blue;
               this.textureColours.put(name, c);
            } else {
               c = cachedColour;
            }
         } catch (FileNotFoundException e) {
            if (convert) {
               return this.loadBlockColourFromTexture(world, state, b, pos, false);
            }

            MinimapLogs.LOGGER.info("Block file not found: " + ((Registry)world.registryAccess().registry(Registries.BLOCK).get()).getKey(b));
            c = 0;
            if (state != null && state.getMapColor(world, pos) != null) {
               c = state.getMapColor(world, pos).col;
            }

            if (name != null) {
               this.textureColours.put(name, c);
            }
         } catch (Exception e) {
            MinimapLogs.LOGGER
               .info(
                  "Exception when loading "
                     + ((Registry)world.registryAccess().registry(Registries.BLOCK).get()).getKey(b)
                     + " texture, using material colour."
               );
            c = 0;
            if (state.getMapColor(world, pos) != null) {
               c = state.getMapColor(world, pos).col;
            }

            if (name != null) {
               this.textureColours.put(name, c);
            }

            if (e instanceof SilentException) {
               MinimapLogs.LOGGER.info(e.getMessage());
            } else {
               MinimapLogs.LOGGER.error("suppressed exception", e);
            }
         }

         if (c != null) {
            this.blockColours.put(stateHash, c);
            this.blockTintIndices.put(state, tintIndex);
         }
      }

      this.lastBlockStateForTextureColor = state;
      this.lastBlockStateForTextureColorResult = c;
      return c;
   }

   private int addBlockColorMultipliers(int c, BlockState state, Level world, BlockPos pos) {
      if (this.loadingColours == 1 && !this.loadingBiomesVanillaMode) {
         return c;
      }

      int grassColor = 16777215;

      try {
         grassColor = Minecraft.getInstance().getBlockColors().getColor(state, this.biomeBlendCalculator, pos, this.blockTintIndices.getInt(state));
      } catch (Throwable t) {
         MinimapLogs.LOGGER.error("suppressed exception", t);
      }

      if (grassColor != -1 && grassColor != 16777215) {
         float rMultiplier = (c >> 16 & 0xFF) / 255.0F;
         float gMultiplier = (c >> 8 & 0xFF) / 255.0F;
         float bMultiplier = (c & 0xFF) / 255.0F;
         int red = (int)((grassColor >> 16 & 0xFF) * rMultiplier);
         int green = (int)((grassColor >> 8 & 0xFF) * gMultiplier);
         int blue = (int)((grassColor & 0xFF) * bMultiplier);
         c = c & 0xFF000000 | red << 16 | green << 8 | blue;
      }

      return c;
   }

   private boolean ignoreWorld(Level world) {
      for (int i = 0; i < dimensionsToIgnore.length; i++) {
         if (dimensionsToIgnore[i].equals(world.dimension().location().getPath())) {
            return true;
         }
      }

      return false;
   }

   private int getCaving(double playerX, double playerY, double playerZ, Level world) {
      boolean caveModeConfig = MinimapConfigClientUtils.getEffectiveCaveModeAllowed();
      if (!caveModeConfig) {
         return Integer.MAX_VALUE;
      }

      boolean manualCaveMode = this.minimapSession.getProcessor().isManualCaveMode();
      ClientConfigManager configManager = HudMod.INSTANCE.getHudConfigs().getClientConfigManager();
      int autoCaveModeConfig = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.AUTO_CAVE_MODE);
      if (autoCaveModeConfig == 0 && !manualCaveMode) {
         return Integer.MAX_VALUE;
      }

      if (this.ignoreWorld(world)) {
         return this.lastCaving;
      }

      if (manualCaveMode) {
         int customCaveStart = (Integer)configManager.getEffective(MinimapProfiledConfigOptions.MANUAL_CAVE_MODE_START);
         if (customCaveStart != Integer.MAX_VALUE) {
            return customCaveStart;
         }
      } else if (this.modMain.getSupportMods().worldmap() && this.modMain.getSupportMods().worldmapSupport.shouldPreventAutoCaveMode(world)) {
         return Integer.MAX_VALUE;
      }

      int worldBottomY = world.getMinBuildHeight();
      int worldTopY = world.getMaxBuildHeight() - 1;
      int y = (int)playerY + 1;
      int defaultCaveStart = y + 3;
      int defaultResult = manualCaveMode ? defaultCaveStart : Integer.MAX_VALUE;
      if (y <= worldTopY && y >= worldBottomY) {
         int x = OptimizedMath.myFloor(playerX);
         int z = OptimizedMath.myFloor(playerZ);
         int roofRadius = autoCaveModeConfig - 1;
         int roofDiameter = 1 + roofRadius * 2;
         int startX = x - roofRadius;
         int startZ = z - roofRadius;
         boolean ignoringHeightmaps = this.modMain.getSettings().isIgnoreHeightmaps();
         int bottom = y;
         int top = Integer.MAX_VALUE;
         ChunkAccess prevBChunk = null;
         int potentialResult = defaultCaveStart;

         for (int o = 0; o < roofDiameter; o++) {
            label125:
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
                     && !this.isTransparent(state)
                     && state.getBlock() != Blocks.BARRIER) {
                     if (o == p && o == roofRadius) {
                        potentialResult = Math.min(i, defaultCaveStart);
                     }
                     continue label125;
                  }
               }

               return defaultResult;
            }
         }

         return this.lastCaving = potentialResult;
      } else {
         return defaultResult;
      }
   }

   public int getSectionBasedHeight(LevelChunk bchunk, int startY) {
      LevelChunkSection[] sections = bchunk.getSections();
      if (sections.length == 0) {
         return 0;
      }

      int chunkBottomY = bchunk.getMinBuildHeight();
      int playerSection = Math.min(startY - chunkBottomY >> 4, sections.length - 1);
      if (playerSection < 0) {
         playerSection = 0;
      }

      int result = 0;

      for (int i = playerSection; i < sections.length; i++) {
         LevelChunkSection searchedSection = sections[i];
         if (!searchedSection.hasOnlyAir()) {
            result = chunkBottomY + (i << 4) + 15;
         }
      }

      if (playerSection > 0 && result == 0) {
         for (int i = playerSection - 1; i >= 0; i--) {
            LevelChunkSection searchedSection = sections[i];
            if (!searchedSection.hasOnlyAir()) {
               result = chunkBottomY + (i << 4) + 15;
               break;
            }
         }
      }

      return result;
   }

   public int getLoadSide() {
      return 9;
   }

   public int getUpdateRadiusInChunks() {
      return (int)Math.ceil(this.loadingSideInChunks / 2.0 / this.minimapSession.getProcessor().getMinimapZoom());
   }

   public int getMapCoord(int side, int coord) {
      return (coord >> 6) - side / 2;
   }

   public int getLoadedCaving() {
      return this.loadedCaving;
   }

   private boolean notEmptyColor(int[] red, int[] green, int[] blue) {
      return red[0] != 0 || green[0] != 0 || blue[0] != 0;
   }

   public float getFixedSkyLightBlockBrightness(float min, float fixedSun, int blockLight) {
      return (min + Math.max(fixedSun * 15.0F, blockLight)) / (15.0F + min);
   }

   public float getBlockBrightness(float min, int sun, int lightLevel, int blockLight) {
      return (
            min
               + Math.max(
                  (lightLevel != -1 && lightLevel != 0 ? (this.loadingLevels - 1.0F - lightLevel) / (this.loadingLevels - 1.0F) : 1.0F) * sun, blockLight
               )
         )
         / (15.0F + min);
   }

   public int getLoadingMapChunkX() {
      return this.loadingMapChunkX;
   }

   public int getLoadingMapChunkZ() {
      return this.loadingMapChunkZ;
   }

   public int getLoadingSideInChunks() {
      return this.loadingSideInChunks;
   }

   public MinimapChunk[][] getLoadedBlocks() {
      return this.loadedBlocks;
   }

   public int getLoadedMapChunkZ() {
      return this.loadedMapChunkZ;
   }

   public int getLoadedMapChunkX() {
      return this.loadedMapChunkX;
   }

   public int getLoadedLevels() {
      return this.loadedLevels;
   }

   public void setClearBlockColours(boolean clearBlockColours) {
      this.clearBlockColours = clearBlockColours;
   }

   public void cleanup() {
      if (this.loadedBlocks != null) {
         for (int i = 0; i < this.loadedBlocks.length; i++) {
            for (int j = 0; j < this.loadedBlocks.length; j++) {
               MinimapChunk m = this.loadedBlocks[i][j];
               if (m != null) {
                  m.cleanup(this.minimapInterface);
               }
            }
         }
      }
   }

   public void resetShortBlocks() {
      this.blockStateShortShapeCache.reset();
   }

   public DimensionHighlighterHandler getDimensionHighlightHandler() {
      return this.dimensionHighlightHandler;
   }

   public int getLoadedSideInChunks() {
      return this.loadedSideInChunks;
   }

   public boolean isLoadedNonWorldMap() {
      return this.loadedNonWorldMap;
   }
}
