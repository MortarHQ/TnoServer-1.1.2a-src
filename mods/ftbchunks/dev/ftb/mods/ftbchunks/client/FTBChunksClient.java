package dev.ftb.mods.ftbchunks.client;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.InputConstants.Type;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Axis;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.event.events.client.ClientReloadShadersEvent;
import dev.architectury.event.events.client.ClientScreenInputEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.client.ClientReloadShadersEvent.ShadersSink;
import dev.architectury.hooks.client.screen.ScreenAccess;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.architectury.platform.Platform;
import dev.architectury.registry.ReloadListenerRegistry;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.ftb.mods.ftbchunks.ColorMapLoader;
import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.FTBChunksWorldConfig;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.client.FTBChunksClientAPI;
import dev.ftb.mods.ftbchunks.api.client.event.MapIconEvent;
import dev.ftb.mods.ftbchunks.api.client.event.WaypointManagerEvent;
import dev.ftb.mods.ftbchunks.api.client.icon.MapIcon;
import dev.ftb.mods.ftbchunks.api.client.icon.MapType;
import dev.ftb.mods.ftbchunks.api.client.icon.WaypointIcon;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapContext;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapInfoComponent;
import dev.ftb.mods.ftbchunks.api.client.waypoint.Waypoint;
import dev.ftb.mods.ftbchunks.api.client.waypoint.WaypointManager;
import dev.ftb.mods.ftbchunks.client.gui.ChunkScreen;
import dev.ftb.mods.ftbchunks.client.gui.EntityIconSettingsScreen;
import dev.ftb.mods.ftbchunks.client.gui.LargeMapScreen;
import dev.ftb.mods.ftbchunks.client.gui.PointerIcon;
import dev.ftb.mods.ftbchunks.client.gui.WaypointAddScreen;
import dev.ftb.mods.ftbchunks.client.gui.WaypointEditorScreen;
import dev.ftb.mods.ftbchunks.client.map.ChunkUpdateTask;
import dev.ftb.mods.ftbchunks.client.map.MapDimension;
import dev.ftb.mods.ftbchunks.client.map.MapManager;
import dev.ftb.mods.ftbchunks.client.map.MapRegion;
import dev.ftb.mods.ftbchunks.client.map.MapRegionData;
import dev.ftb.mods.ftbchunks.client.map.MapTask;
import dev.ftb.mods.ftbchunks.client.map.RegionSyncKey;
import dev.ftb.mods.ftbchunks.client.map.UpdateChunkFromServerTask;
import dev.ftb.mods.ftbchunks.client.map.WaypointImpl;
import dev.ftb.mods.ftbchunks.client.map.WaypointManagerImpl;
import dev.ftb.mods.ftbchunks.client.map.WaypointType;
import dev.ftb.mods.ftbchunks.client.map.color.ColorUtils;
import dev.ftb.mods.ftbchunks.client.mapicon.EntityIconUtils;
import dev.ftb.mods.ftbchunks.client.mapicon.EntityMapIcon;
import dev.ftb.mods.ftbchunks.client.mapicon.InWorldMapIcon;
import dev.ftb.mods.ftbchunks.client.mapicon.MapIconComparator;
import dev.ftb.mods.ftbchunks.client.mapicon.TrackedPlayerMapIcon;
import dev.ftb.mods.ftbchunks.client.minimap.components.BiomeComponent;
import dev.ftb.mods.ftbchunks.client.minimap.components.DebugComponent;
import dev.ftb.mods.ftbchunks.client.minimap.components.FPSComponent;
import dev.ftb.mods.ftbchunks.client.minimap.components.GameTimeComponent;
import dev.ftb.mods.ftbchunks.client.minimap.components.PlayerPosInfoComponent;
import dev.ftb.mods.ftbchunks.client.minimap.components.RealTimeComponent;
import dev.ftb.mods.ftbchunks.client.minimap.components.ZoneInfoComponent;
import dev.ftb.mods.ftbchunks.client.neoforge.FTBChunksClientImpl;
import dev.ftb.mods.ftbchunks.data.ChunkSyncInfo;
import dev.ftb.mods.ftbchunks.net.PartialPackets;
import dev.ftb.mods.ftbchunks.net.SendGeneralDataPacket;
import dev.ftb.mods.ftblibrary.api.sidebar.SidebarButtonCreatedEvent;
import dev.ftb.mods.ftblibrary.config.StringConfig;
import dev.ftb.mods.ftblibrary.icon.EntityIconLoader;
import dev.ftb.mods.ftblibrary.icon.FaceIcon;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.EntityIconLoader.EntityIconSettings;
import dev.ftb.mods.ftblibrary.icon.EntityIconLoader.WidthHeight;
import dev.ftb.mods.ftblibrary.math.MathUtils;
import dev.ftb.mods.ftblibrary.math.XZ;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.CustomClickEvent;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.util.ModUtils;
import dev.ftb.mods.ftblibrary.util.client.ClientUtils;
import dev.ftb.mods.ftbteams.api.event.ClientTeamPropertiesChangedEvent;
import dev.ftb.mods.ftbteams.api.event.TeamEvent;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public enum FTBChunksClient {
   INSTANCE;

   public static final ExecutorService MAP_EXECUTOR = Executors.newSingleThreadExecutor();
   public static final ResourceLocation WAYPOINT_BEAM = FTBChunksAPI.rl("textures/waypoint_beam.png");
   private static final ResourceLocation BUTTON_ID_MAP = FTBChunksAPI.rl("open_gui");
   private static final ResourceLocation BUTTON_ID_CLAIM = FTBChunksAPI.rl("open_claim_gui");
   public static final ResourceLocation CIRCLE_MASK = FTBChunksAPI.rl("textures/circle_mask.png");
   public static final ResourceLocation CIRCLE_BORDER = FTBChunksAPI.rl("textures/circle_border.png");
   public static final ResourceLocation SQUARE_MASK = FTBChunksAPI.rl("textures/square_mask.png");
   public static final ResourceLocation SQUARE_BORDER = FTBChunksAPI.rl("textures/square_border.png");
   public static final ResourceLocation PLAYER = FTBChunksAPI.rl("textures/player.png");
   public static final ResourceLocation[] COMPASS = new ResourceLocation[]{
      FTBChunksAPI.rl("textures/compass_e.png"),
      FTBChunksAPI.rl("textures/compass_n.png"),
      FTBChunksAPI.rl("textures/compass_w.png"),
      FTBChunksAPI.rl("textures/compass_s.png")
   };
   public KeyMapping openMapKey;
   public KeyMapping toggleMinimapKey;
   public KeyMapping openClaimManagerKey;
   public KeyMapping zoomInKey;
   public KeyMapping zoomOutKey;
   public KeyMapping addWaypointKey;
   public KeyMapping waypointManagerKey;
   private final Map<UUID, TrackedPlayerMapIcon> longRangePlayerTracker = new HashMap<>();
   private long taskQueueTicks = 0L;
   private final Map<ChunkPos, IntOpenHashSet> rerenderCache = new HashMap<>();
   private int minimapTextureId = -1;
   private int currentPlayerChunkX;
   private int currentPlayerChunkZ;
   private double currentPlayerX;
   private double currentPlayerY;
   private double currentPlayerZ;
   private double prevPlayerX;
   private double prevPlayerY;
   private double prevPlayerZ;
   private int renderedDebugCount = 0;
   private boolean updateMinimapScheduled = false;
   private SendGeneralDataPacket.GeneralChunkData generalChunkData;
   private long nextRegionSave = 0L;
   private double prevZoom = (Double)FTBChunksClientConfig.MINIMAP_ZOOM.get();
   private long lastZoomTime = 0L;
   private final List<MapIcon> mapIcons = new ArrayList<>();
   private final List<InWorldMapIcon> inWorldMapIcons = new ArrayList<>();
   private long lastMapIconUpdate = 0L;
   private Matrix4f worldMatrix;
   private Vec3 cameraPos;
   private final List<MinimapInfoComponent> sortedComponents = new LinkedList<>();
   private static double vanillaEffectsOffsetX;

   public void init() {
      if (Minecraft.getInstance() != null) {
         this.maybeMigrateClientConfig();
         FTBChunksAPI._initClient(new FTBChunksClientAPIImpl());
         this.registerKeys();
         ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, new ColorMapLoader());
         ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(this::loggedOut);
         CustomClickEvent.EVENT.register(this::customClick);
         ClientRawInputEvent.KEY_PRESSED.register(this::keyPressed);
         ClientScreenInputEvent.KEY_PRESSED_PRE.register(this::keyPressed);
         ClientGuiEvent.RENDER_HUD.register(this::renderHud);
         ClientGuiEvent.INIT_PRE.register(this::screenOpened);
         ClientTickEvent.CLIENT_PRE.register(this::clientTick);
         TeamEvent.CLIENT_PROPERTIES_CHANGED.register(this::teamPropertiesChanged);
         MapIconEvent.LARGE_MAP.register(this::mapIcons);
         MapIconEvent.MINIMAP.register(this::mapIcons);
         ClientReloadShadersEvent.EVENT.register(this::reloadShaders);
         SidebarButtonCreatedEvent.EVENT.register(this::onSidebarButtonCreated);
         registerPlatform();
         if (ModUtils.isDevMode()) {
            WaypointManagerEvent.AVAILABLE.register(this::waypointManagerAvailable);
         }

         FTBChunksClientAPI clientApi = FTBChunksAPI.clientApi();
         clientApi.registerMinimapComponent(new PlayerPosInfoComponent());
         clientApi.registerMinimapComponent(new ZoneInfoComponent());
         clientApi.registerMinimapComponent(new BiomeComponent());
         clientApi.registerMinimapComponent(new GameTimeComponent());
         clientApi.registerMinimapComponent(new RealTimeComponent());
         clientApi.registerMinimapComponent(new FPSComponent());
         clientApi.registerMinimapComponent(new DebugComponent());
         ClientLifecycleEvent.CLIENT_STARTED.register(this::clientStarted);
      }
   }

   private void waypointManagerAvailable(WaypointManager mgr) {
      if (Minecraft.getInstance().level.dimension().equals(Level.OVERWORLD)) {
         mgr.addTransientWaypointAt(new BlockPos(0, 65, 0), "Transient Dev-Mode Waypoint").setColor(8421504);
      }
   }

   private void maybeMigrateClientConfig() {
      Path oldConfig = Platform.getGameFolder().resolve("local/ftbchunks/client-config.snbt");
      Path newConfig = Platform.getConfigFolder().resolve("ftbchunks-client.snbt");
      if (Files.exists(oldConfig) && !Files.exists(newConfig)) {
         try {
            Files.move(oldConfig, newConfig);
            FTBChunks.LOGGER.info("migrated {} to {}", oldConfig, newConfig);
         } catch (IOException e) {
            FTBChunks.LOGGER.error("can't migrate {} to {}: {}", oldConfig, newConfig, e.getMessage());
         }
      }
   }

   private void clientStarted(Minecraft minecraft) {
      this.setupComponents();
   }

   private void registerKeys() {
      this.openMapKey = new KeyMapping("key.ftbchunks.map", Type.KEYSYM, 77, "key.categories.ftbchunks");
      KeyMappingRegistry.register(this.openMapKey);
      this.toggleMinimapKey = new KeyMapping("key.ftbchunks.toggle_minimap", Type.KEYSYM, -1, "key.categories.ftbchunks");
      KeyMappingRegistry.register(this.toggleMinimapKey);
      this.openClaimManagerKey = new KeyMapping("key.ftbchunks.claim_manager", Type.KEYSYM, -1, "key.categories.ftbchunks");
      KeyMappingRegistry.register(this.openClaimManagerKey);
      this.zoomInKey = new KeyMapping("key.ftbchunks.minimap.zoomIn", Type.KEYSYM, 61, "key.categories.ftbchunks");
      KeyMappingRegistry.register(this.zoomInKey);
      this.zoomOutKey = new KeyMapping("key.ftbchunks.minimap.zoomOut", Type.KEYSYM, 45, "key.categories.ftbchunks");
      KeyMappingRegistry.register(this.zoomOutKey);
      this.addWaypointKey = new KeyMapping("key.ftbchunks.add_waypoint", Type.KEYSYM, -1, "key.categories.ftbchunks");
      KeyMappingRegistry.register(this.addWaypointKey);
      this.waypointManagerKey = new KeyMapping("key.ftbchunks.waypoint_manager", Type.KEYSYM, -1, "key.categories.ftbchunks");
      KeyMappingRegistry.register(this.waypointManagerKey);
   }

   public Set<ChunkPos> getPendingRerender() {
      return this.rerenderCache.keySet();
   }

   @ExpectPlatform
   @Transformed
   public static void registerPlatform() {
      FTBChunksClientImpl.registerPlatform();
   }

   @ExpectPlatform
   @Transformed
   public static boolean doesKeybindMatch(KeyMapping keyMapping, int keyCode, int scanCode, int modifiers) {
      return FTBChunksClientImpl.doesKeybindMatch(keyMapping, keyCode, scanCode, modifiers);
   }

   public static boolean doesKeybindMatch(KeyMapping keyMapping, Key key) {
      return doesKeybindMatch(keyMapping, key.keyCode, key.scanCode, key.modifiers.modifiers);
   }

   public void scheduleMinimapUpdate() {
      this.updateMinimapScheduled = true;
   }

   public void handlePlayerLogin(UUID serverId) {
      FTBChunks.LOGGER.info("Loading FTB Chunks client data from world {}", serverId);
      MapManager.startUp(serverId);
      this.scheduleMinimapUpdate();
      this.renderedDebugCount = 0;
      ChunkUpdateTask.init();
   }

   public void loggedOut(@Nullable LocalPlayer player) {
      MapManager.shutdown();
   }

   public void updateGeneralData(SendGeneralDataPacket.GeneralChunkData chunkData) {
      this.generalChunkData = chunkData;
   }

   public void updateChunksFromServer(ResourceKey<Level> dimId, UUID teamId, Collection<ChunkSyncInfo> chunkSyncInfoList) {
      MapManager.getInstance().ifPresent(manager -> {
         MapDimension dimension = manager.getDimension(dimId);
         Date now = new Date();
         chunkSyncInfoList.forEach(chunkSyncInfo -> ClientTaskQueue.queue(new UpdateChunkFromServerTask(dimension, chunkSyncInfo, teamId, now)));
      });
   }

   public void syncRegionFromServer(RegionSyncKey key, int offset, int total, byte[] data) {
      PartialPackets.REGION.read(key, offset, total, data);
   }

   public void handlePlayerDeath(GlobalPos pos, int deathNumber) {
      if ((Boolean)FTBChunksClientConfig.DEATH_WAYPOINTS.get() && FTBChunksWorldConfig.playerHasMapStage(Minecraft.getInstance().player)) {
         MapManager.getInstance().ifPresent(manager -> {
            MapDimension dimension = manager.getDimension(pos.dimension());

            for (WaypointImpl w : dimension.getWaypointManager()) {
               if (w.isDeathpoint()) {
                  w.setHidden(true);
                  w.refreshIcon();
               }
            }

            WaypointImpl deathPoint = new WaypointImpl(WaypointType.DEATH, dimension, pos.pos()).setName("Death #" + deathNumber).setColor(16711680);
            dimension.getWaypointManager().add(deathPoint);
         });
      }
   }

   public void handleBlockColorRequest() {
      Minecraft mc = Minecraft.getInstance();
      mc.submit(
         () -> {
            mc.setScreen(null);
            new Thread(
                  () -> {
                     try {
                        Thread.sleep(50L);
                     } catch (InterruptedException e) {
                        e.printStackTrace();
                     }

                     mc.submit(
                        () -> {
                           if (mc.hitResult instanceof BlockHitResult hitResult && mc.level != null && mc.player != null) {
                              ResourceLocation id = FTBChunks.BLOCK_REGISTRY.getId(mc.level.getBlockState(hitResult.getBlockPos()).getBlock());
                              Window window = mc.getWindow();
                              NativeImage image = Screenshot.takeScreenshot(mc.getMainRenderTarget());

                              try {
                                 int col = image.getPixelRGBA(
                                    image.getWidth() / 2 - (int)(2.0 * window.getGuiScale()), image.getHeight() / 2 - (int)(2.0 * window.getGuiScale())
                                 );
                                 String s = String.format("\"%s\": \"#%06X\"", id.getPath(), ColorUtils.convertFromNative(col) & 16777215);
                                 mc.player
                                    .displayClientMessage(
                                       Component.literal(id.getNamespace() + " - " + s)
                                          .withStyle(
                                             Style.EMPTY
                                                .applyFormat(ChatFormatting.GOLD)
                                                .withClickEvent(new ClickEvent(Action.COPY_TO_CLIPBOARD, s))
                                                .withHoverEvent(
                                                   new HoverEvent(net.minecraft.network.chat.HoverEvent.Action.SHOW_TEXT, Component.literal("Click to copy"))
                                                )
                                          ),
                                       false
                                    );
                              } catch (Throwable var8) {
                                 if (image != null) {
                                    try {
                                       image.close();
                                    } catch (Throwable var7) {
                                       var8.addSuppressed(var7);
                                    }
                                 }

                                 throw var8;
                              }

                              if (image != null) {
                                 image.close();
                              }
                           }
                        }
                     );
                  },
                  "Color getter"
               )
               .start();
         }
      );
   }

   public void syncLoadedChunkViewFromServer(ResourceKey<Level> dimension, Long2IntMap chunks) {
      MapManager.getInstance().ifPresent(manager -> {
         manager.getDimension(dimension).updateLoadedChunkView(chunks);
         manager.updateAllRegions(false);
      });
   }

   public boolean skipBlock(BlockState state) {
      ResourceLocation id = FTBChunks.BLOCK_REGISTRY.getId(state.getBlock());
      return id == null || ColorMapLoader.getBlockColor(id).isIgnored();
   }

   private void onSidebarButtonCreated(SidebarButtonCreatedEvent event) {
      if (event.getButton().getId().equals(FTBChunksAPI.rl("chunks"))) {
         event.getButton().addVisibilityCondition(() -> FTBChunksWorldConfig.playerHasMapStage(Minecraft.getInstance().player));
      }
   }

   public EventResult customClick(CustomClickEvent event) {
      if (event.id().equals(BUTTON_ID_MAP)) {
         return LargeMapScreen.openMap() ? EventResult.interruptTrue() : EventResult.pass();
      } else if (event.id().equals(BUTTON_ID_CLAIM)) {
         ChunkScreen.openChunkScreen();
         return EventResult.interruptTrue();
      } else {
         return EventResult.pass();
      }
   }

   public EventResult keyPressed(Minecraft client, int keyCode, int scanCode, int action, int modifiers) {
      if (action != 1 || client.screen != null || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 292)) {
         return EventResult.pass();
      } else if (doesKeybindMatch(this.openClaimManagerKey, keyCode, scanCode, modifiers)) {
         ChunkScreen.openChunkScreen();
         return EventResult.interruptTrue();
      } else if (!FTBChunksWorldConfig.playerHasMapStage(client.player)) {
         return EventResult.pass();
      } else if (doesKeybindMatch(this.openMapKey, keyCode, scanCode, modifiers)) {
         return LargeMapScreen.openMap() ? EventResult.interruptTrue() : EventResult.pass();
      } else if (doesKeybindMatch(this.toggleMinimapKey, keyCode, scanCode, modifiers)) {
         FTBChunksClientConfig.MINIMAP_ENABLED.set(!(Boolean)FTBChunksClientConfig.MINIMAP_ENABLED.get());
         FTBChunksClientConfig.saveConfig();
         return EventResult.interruptTrue();
      } else if (doesKeybindMatch(this.zoomInKey, keyCode, scanCode, modifiers)) {
         return this.changeZoom(true);
      } else if (doesKeybindMatch(this.zoomOutKey, keyCode, scanCode, modifiers)) {
         return this.changeZoom(false);
      } else if (doesKeybindMatch(this.addWaypointKey, keyCode, scanCode, modifiers)) {
         return this.addQuickWaypoint();
      } else if (doesKeybindMatch(this.waypointManagerKey, keyCode, scanCode, modifiers)) {
         new WaypointEditorScreen().openGui();
         return EventResult.interruptTrue();
      } else {
         return EventResult.pass();
      }
   }

   public EventResult keyPressed(Minecraft client, Screen screen, int keyCode, int scanCode, int modifiers) {
      if (doesKeybindMatch(this.openMapKey, keyCode, scanCode, modifiers) && Platform.isFabric()) {
         LargeMapScreen gui = (LargeMapScreen)ClientUtils.getCurrentGuiAs(LargeMapScreen.class);
         if (gui != null && !gui.anyModalPanelOpen()) {
            gui.closeGui(false);
            return EventResult.interruptTrue();
         }
      }

      return EventResult.pass();
   }

   private EventResult addQuickWaypoint() {
      StringConfig name = new StringConfig();
      Player player = Minecraft.getInstance().player;
      return player == null ? EventResult.pass() : MapManager.getInstance().map(manager -> {
         BaseScreen screen = new WaypointAddScreen(name, player);
         screen.openGuiLater();
         return EventResult.interruptTrue();
      }).orElse(EventResult.pass());
   }

   private EventResult changeZoom(boolean zoomIn) {
      this.prevZoom = (Double)FTBChunksClientConfig.MINIMAP_ZOOM.get();
      double zoom = this.prevZoom;
      double zoomFactor = zoomIn ? 1.0 : -1.0;
      if (zoom + zoomFactor > 4.0) {
         zoom = 4.0;
      } else if (zoom + zoomFactor < 1.0) {
         zoom = 1.0;
      } else {
         zoom += zoomFactor;
      }

      this.lastZoomTime = System.currentTimeMillis();
      FTBChunksClientConfig.MINIMAP_ZOOM.set(zoom);
      return EventResult.interruptTrue();
   }

   public float getZoom() {
      double z = (Double)FTBChunksClientConfig.MINIMAP_ZOOM.get();
      if (this.prevZoom != z) {
         long max = (long)(400.0 / z);
         long t = Mth.clamp(System.currentTimeMillis() - this.lastZoomTime, 0L, max);
         if (t == max) {
            this.lastZoomTime = 0L;
            return (float)z;
         } else {
            return (float)Mth.lerp((double)t / max, this.prevZoom, z);
         }
      } else {
         return (float)z;
      }
   }

   public int generateTextureId(int w, int h) {
      int textureId = TextureUtil.generateTextureId();
      TextureUtil.prepareImage(textureId, w, h);
      return textureId;
   }

   public void renderHud(GuiGraphics graphics, DeltaTracker tickDelta) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player != null && mc.level != null && !MapManager.getInstance().isEmpty() && !MapDimension.getCurrent().isEmpty()) {
         float partialTicks = tickDelta.getGameTimeDeltaPartialTick(false);
         double playerX = Mth.lerp(partialTicks, this.prevPlayerX, this.currentPlayerX);
         double playerY = Mth.lerp(partialTicks, this.prevPlayerY, this.currentPlayerY);
         double playerZ = Mth.lerp(partialTicks, this.prevPlayerZ, this.currentPlayerZ);
         double guiScale = mc.getWindow().getGuiScale();
         int scaledWidth = mc.getWindow().getGuiScaledWidth();
         int scaledHeight = mc.getWindow().getGuiScaledHeight();
         MapDimension dim = MapDimension.getCurrent().get();
         if (dim.dimension != mc.level.dimension()) {
            MapDimension.clearCurrentDimension();
            dim = MapDimension.getCurrent().orElseThrow();
            this.longRangePlayerTracker.clear();
         }

         long now = System.currentTimeMillis();
         if (this.nextRegionSave == 0L || now >= this.nextRegionSave) {
            this.nextRegionSave = now + 60000L;
            MapManager.getInstance().ifPresent(MapManager::saveAllRegions);
         }

         if (this.minimapTextureId == -1) {
            this.minimapTextureId = this.generateTextureId(240, 240);
            this.scheduleMinimapUpdate();
         }

         float zoom0 = this.getZoom();
         float zoom = zoom0 / 3.5F;
         MinimapBlurMode blurMode = (MinimapBlurMode)FTBChunksClientConfig.MINIMAP_BLUR_MODE.get();
         boolean minimapBlur = blurMode == MinimapBlurMode.AUTO ? zoom0 < 1.5F : blurMode == MinimapBlurMode.ON;
         int filter = minimapBlur ? 9729 : 9728;
         RenderSystem.bindTextureForSetup(this.minimapTextureId);
         RenderSystem.texParameter(3553, 10241, filter);
         RenderSystem.texParameter(3553, 10240, filter);
         int cx = Mth.floor(playerX) >> 4;
         int cz = Mth.floor(playerZ) >> 4;
         if (cx != this.currentPlayerChunkX || cz != this.currentPlayerChunkZ) {
            this.scheduleMinimapUpdate();
         }

         if (this.updateMinimapScheduled) {
            this.updateMinimapScheduled = false;

            for (int mz = 0; mz < 15; mz++) {
               for (int mx = 0; mx < 15; mx++) {
                  int ox = cx + mx - 7;
                  int oz = cz + mz - 7;
                  MapRegion region = dim.getRegion(XZ.regionFromChunk(ox, oz));
                  region.getRenderedMapImage().upload(0, mx * 16, mz * 16, (ox & 31) * 16, (oz & 31) * 16, 16, 16, minimapBlur, false, false, false);
               }
            }

            this.currentPlayerChunkX = cx;
            this.currentPlayerChunkZ = cz;
         }

         if (!mc.options.hideGui
            && !mc.getDebugOverlay().showDebugScreen()
            && (Boolean)FTBChunksClientConfig.MINIMAP_ENABLED.get()
            && (Integer)FTBChunksClientConfig.MINIMAP_VISIBILITY.get() != 0
            && FTBChunksWorldConfig.shouldShowMinimap(mc.player)) {
            float scale;
            if ((Boolean)FTBChunksClientConfig.MINIMAP_PROPORTIONAL.get()) {
               scale = (float)(4.0 / guiScale);
               scale *= scaledWidth / 10.0F / (scale * 64.0F) * ((Double)FTBChunksClientConfig.MINIMAP_SCALE.get()).floatValue();
            } else {
               scale = (float)((Double)FTBChunksClientConfig.MINIMAP_SCALE.get() * 4.0 / guiScale);
            }

            boolean rotationLocked = (Boolean)FTBChunksClientConfig.MINIMAP_LOCKED_NORTH.get() || (Boolean)FTBChunksClientConfig.SQUARE_MINIMAP.get();
            float minimapRotation = (rotationLocked ? 180.0F : -mc.player.getYRot()) % 360.0F;
            int size = (int)(64.0 * scale);
            double halfSizeD = size / 2.0;
            float halfSizeF = size / 2.0F;
            MinimapPosition minimapPosition = (MinimapPosition)FTBChunksClientConfig.MINIMAP_POSITION.get();
            int x = minimapPosition.getX(scaledWidth, size);
            int y = minimapPosition.getY(scaledHeight, size);
            int offsetX = (Integer)FTBChunksClientConfig.MINIMAP_OFFSET_X.get();
            int offsetY = (Integer)FTBChunksClientConfig.MINIMAP_OFFSET_Y.get();
            MinimapPosition.MinimapOffsetConditional offsetConditional = (MinimapPosition.MinimapOffsetConditional)FTBChunksClientConfig.MINIMAP_POSITION_OFFSET_CONDITION
               .get();
            if (offsetConditional.test(minimapPosition)) {
               x += minimapPosition.posX == 0 ? offsetX : -offsetX;
               y -= minimapPosition.posY > 1 ? offsetY : -offsetY;
            }

            if (!mc.player.getActiveEffects().isEmpty() && y <= 50 && x + size > scaledWidth - 50) {
               vanillaEffectsOffsetX = -(scaledWidth - x) - 5;
            } else {
               vanillaEffectsOffsetX = 0.0;
            }

            float border = 0.0F;
            int alpha = (Integer)FTBChunksClientConfig.MINIMAP_VISIBILITY.get();
            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder buffer = tessellator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            RenderSystem.enableDepthTest();
            PoseStack poseStack = graphics.pose();
            poseStack.pushPose();
            poseStack.translate(0.0, y + halfSizeD, 0.0);
            boolean textAboveMinimap = (Boolean)FTBChunksClientConfig.TEXT_ABOVE_MINIMAP.get();
            int componentsHeight = this.getMinimapComponentsTotalHeight(mc, dim, playerX, playerY, playerZ);
            if (textAboveMinimap) {
               int renderY = y - componentsHeight;
               if (renderY <= 0) {
                  int offset = -renderY + 1;
                  poseStack.translate(0.0F, offset, 0.0F);
                  y += offset;
               }

               poseStack.pushPose();
               poseStack.translate(0.0F, -componentsHeight, 0.0F);
               this.drawMinimapComponents(mc, dim, playerX, playerY, playerZ, scaledHeight, x, (int)(-halfSizeF) - 1, 0, halfSizeD, poseStack, graphics);
               poseStack.popPose();
            } else {
               int renderY = y + size + componentsHeight;
               if (renderY >= scaledHeight) {
                  int offset = -componentsHeight - 1;
                  poseStack.translate(0.0F, offset, 0.0F);
                  y += offset;
               }
            }

            poseStack.translate(x + halfSizeD, 0.0, 490.0);
            Matrix4f m = poseStack.last().pose();
            RenderSystem.colorMask(false, false, false, false);
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, FTBChunksClientConfig.SQUARE_MINIMAP.get() ? SQUARE_MASK : CIRCLE_MASK);
            buffer.addVertex(m, -halfSizeF + border, -halfSizeF + border, 0.0F).setColor(255, 255, 255, 255).setUv(0.0F, 0.0F);
            buffer.addVertex(m, -halfSizeF + border, halfSizeF - border, 0.0F).setColor(255, 255, 255, 255).setUv(0.0F, 1.0F);
            buffer.addVertex(m, halfSizeF - border, halfSizeF - border, 0.0F).setColor(255, 255, 255, 255).setUv(1.0F, 1.0F);
            buffer.addVertex(m, halfSizeF - border, -halfSizeF + border, 0.0F).setColor(255, 255, 255, 255).setUv(1.0F, 0.0F);
            BufferUploader.drawWithShader(buffer.buildOrThrow());
            RenderSystem.colorMask(true, true, true, true);
            poseStack.mulPose(Axis.ZP.rotationDegrees(minimapRotation + 180.0F));
            RenderSystem.depthFunc(518);
            float halfSizeBorderF = halfSizeF - border;
            float offX = 0.5F + (float)((MathUtils.mod(playerX, 16.0) / 16.0 - 0.5) / 15.0);
            float offZ = 0.5F + (float)((MathUtils.mod(playerZ, 16.0) / 16.0 - 0.5) / 15.0);
            float zws = 2.0F / (15.0F * zoom);
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.setShaderTexture(0, this.minimapTextureId);
            buffer = tessellator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            buffer.addVertex(m, -halfSizeBorderF, -halfSizeBorderF, 0.0F).setColor(255, 255, 255, alpha).setUv(offX - zws, offZ - zws);
            buffer.addVertex(m, -halfSizeBorderF, halfSizeBorderF, 0.0F).setColor(255, 255, 255, alpha).setUv(offX - zws, offZ + zws);
            buffer.addVertex(m, halfSizeBorderF, halfSizeBorderF, 0.0F).setColor(255, 255, 255, alpha).setUv(offX + zws, offZ + zws);
            buffer.addVertex(m, halfSizeBorderF, -halfSizeBorderF, 0.0F).setColor(255, 255, 255, alpha).setUv(offX + zws, offZ - zws);
            BufferUploader.drawWithShader(buffer.buildOrThrow());
            RenderSystem.disableDepthTest();
            RenderSystem.depthFunc(515);
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.setShaderTexture(0, FTBChunksClientConfig.SQUARE_MINIMAP.get() ? SQUARE_BORDER : CIRCLE_BORDER);
            buffer = tessellator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            buffer.addVertex(m, -halfSizeF, -halfSizeF, 0.0F).setColor(255, 255, 255, alpha).setUv(0.0F, 0.0F);
            buffer.addVertex(m, -halfSizeF, halfSizeF, 0.0F).setColor(255, 255, 255, alpha).setUv(0.0F, 1.0F);
            buffer.addVertex(m, halfSizeF, halfSizeF, 0.0F).setColor(255, 255, 255, alpha).setUv(1.0F, 1.0F);
            buffer.addVertex(m, halfSizeF, -halfSizeF, 0.0F).setColor(255, 255, 255, alpha).setUv(1.0F, 0.0F);
            BufferUploader.drawWithShader(buffer.buildOrThrow());
            if ((Boolean)FTBChunksClientConfig.MINIMAP_RETICLE.get()) {
               RenderSystem.setShader(GameRenderer::getPositionColorShader);
               buffer = tessellator.begin(Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
               buffer.addVertex(m, -halfSizeF, 0.0F, 0.0F).setColor(0, 0, 0, 30);
               buffer.addVertex(m, halfSizeF, 0.0F, 0.0F).setColor(0, 0, 0, 30);
               buffer.addVertex(m, 0.0F, -halfSizeF, 0.0F).setColor(0, 0, 0, 30);
               buffer.addVertex(m, 0.0F, halfSizeF, 0.0F).setColor(0, 0, 0, 30);
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            }

            poseStack.popPose();
            m = poseStack.last().pose();
            if ((Boolean)FTBChunksClientConfig.MINIMAP_COMPASS.get()) {
               this.drawMinimapCompassPoints(minimapRotation, size, halfSizeD, x, y, tessellator, buffer, m);
            }

            if (this.lastMapIconUpdate == 0L || now - this.lastMapIconUpdate >= ((Integer)FTBChunksClientConfig.MINIMAP_ICON_UPDATE_TIMER.get()).intValue()) {
               this.lastMapIconUpdate = now;
               this.mapIcons.clear();
               ((Consumer)MapIconEvent.MINIMAP.invoker()).accept(new MapIconEvent(dim.dimension, this.mapIcons, MapType.MINIMAP));
               if (this.mapIcons.size() >= 2) {
                  this.mapIcons.sort(new MapIconComparator(mc.player.position(), tickDelta.getGameTimeDeltaPartialTick(false)));
               }
            }

            for (MapIcon icon : this.mapIcons) {
               Vec3 pos = icon.getPos(tickDelta.getGameTimeDeltaPartialTick(false));
               double distance = MathUtils.dist(playerX, playerZ, pos.x, pos.z);
               double d = distance * scale * zoom;
               if (icon.isVisible(MapType.MINIMAP, distance, d > halfSizeD)) {
                  if (d > halfSizeD) {
                     d = halfSizeD;
                  }

                  double angle = Math.atan2(playerZ - pos.z, playerX - pos.x) + minimapRotation * Math.PI / 180.0;
                  double ws = size / (32.0 / icon.getIconScale(MapType.MINIMAP));
                  double wx = x + halfSizeD + Math.cos(angle) * d;
                  double wy = y + halfSizeD + Math.sin(angle) * d;
                  float wsf = (float)(ws * 2.0);
                  poseStack.pushPose();
                  poseStack.translate(wx - ws, wy - ws - (icon.isIconOnEdge(MapType.MINIMAP, d >= halfSizeD) ? ws / 2.0 : 0.0), 0.0);
                  poseStack.scale(wsf, wsf, 1.0F);
                  icon.draw(MapType.MINIMAP, graphics, 0, 0, 1, 1, d >= halfSizeD, 255);
                  poseStack.popPose();
               }
            }

            if (rotationLocked || (Boolean)FTBChunksClientConfig.SHOW_PLAYER_WHEN_UNLOCKED.get()) {
               RenderSystem.setShaderTexture(0, PLAYER);
               poseStack.pushPose();
               poseStack.translate(x + halfSizeD, y + halfSizeD, 0.0);
               if (rotationLocked) {
                  poseStack.mulPose(Axis.ZP.rotationDegrees(mc.player.getYRot() + 180.0F));
               }

               poseStack.scale(size / 16.0F, size / 16.0F, 1.0F);
               m = poseStack.last().pose();
               PointerIconMode mode = (PointerIconMode)FTBChunksClientConfig.POINTER_ICON_MODE_MINIMAP.get();
               if (mode.showPointer()) {
                  RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
                  buffer = tessellator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
                  buffer.addVertex(m, -1.0F, -1.0F, 0.0F).setColor(255, 255, 255, 200).setUv(0.0F, 0.0F);
                  buffer.addVertex(m, -1.0F, 1.0F, 0.0F).setColor(255, 255, 255, 200).setUv(0.0F, 1.0F);
                  buffer.addVertex(m, 1.0F, 1.0F, 0.0F).setColor(255, 255, 255, 200).setUv(1.0F, 1.0F);
                  buffer.addVertex(m, 1.0F, -1.0F, 0.0F).setColor(255, 255, 255, 200).setUv(1.0F, 0.0F);
                  BufferUploader.drawWithShader(buffer.buildOrThrow());
               }

               if (mode.showFace()) {
                  if (mode.showPointer()) {
                     poseStack.scale(0.75F, 0.75F, 0.75F);
                     poseStack.translate(0.0F, 0.32F, 0.0F);
                  }

                  poseStack.translate(-0.5F, -0.5F, 0.0F);
                  new EntityMapIcon(mc.player, FaceIcon.getFace(mc.player.getGameProfile())).draw(MapType.MINIMAP, graphics, 0, 0, 1, 1, false, 255);
               }

               poseStack.popPose();
            }

            if (!textAboveMinimap) {
               this.drawMinimapComponents(mc, dim, playerX, playerY, playerZ, scaledHeight, x, y, size, halfSizeD, poseStack, graphics);
            }

            RenderSystem.enableDepthTest();
            if (this.worldMatrix != null && (Boolean)FTBChunksClientConfig.IN_WORLD_WAYPOINTS.get()) {
               this.drawInWorldIcons(mc, graphics, tickDelta, playerX, playerY, playerZ, scaledWidth, scaledHeight);
            }
         }
      }
   }

   private int getMinimapComponentsTotalHeight(Minecraft mc, MapDimension dim, double playerX, double playerY, double playerZ) {
      MinimapContext context = new MinimapContext(
         mc,
         mc.player,
         dim,
         XZ.of(this.currentPlayerChunkX, this.currentPlayerChunkZ),
         new Vec3(playerX, playerY, playerZ),
         (Map<String, String>)FTBChunksClientConfig.MINIMAP_SETTINGS.get()
      );
      int sum = 0;

      for (MinimapInfoComponent c : this.sortedComponents) {
         if (c.shouldRender(context)) {
            sum += c.height(context);
         }
      }

      return sum;
   }

   private void drawMinimapComponents(
      Minecraft mc,
      MapDimension dim,
      double playerX,
      double playerY,
      double playerZ,
      int scaledHeight,
      int x,
      int y,
      int size,
      double halfSizeD,
      PoseStack poseStack,
      GuiGraphics graphics
   ) {
      MinimapContext context = new MinimapContext(
         mc,
         mc.player,
         dim,
         XZ.of(this.currentPlayerChunkX, this.currentPlayerChunkZ),
         new Vec3(playerX, playerY, playerZ),
         (Map<String, String>)FTBChunksClientConfig.MINIMAP_SETTINGS.get()
      );
      float fontScale = ((Double)FTBChunksClientConfig.MINIMAP_FONT_SCALE.get()).floatValue();
      int yOffset = 0;

      for (MinimapInfoComponent component : this.sortedComponents) {
         if (component.shouldRender(context)) {
            int height = component.height(context);
            boolean isBottom = y + size + height >= scaledHeight;
            float yOff = isBottom ? -height - yOffset : size + 2.0F + yOffset;
            poseStack.pushPose();
            poseStack.translate(x + halfSizeD, y + yOff, 0.0);
            poseStack.scale(fontScale, fontScale, 1.0F);
            component.render(context, graphics, mc.font);
            poseStack.popPose();
            yOffset += height;
         }
      }
   }

   private void drawMinimapCompassPoints(
      float minimapRotation, int size, double halfSizeD, int minimapX, int minimapY, Tesselator tessellator, BufferBuilder buffer, Matrix4f m
   ) {
      double d = size / 2.2;
      float ws = size / 32.0F;

      for (int face = 0; face < 4; face++) {
         double angle = (minimapRotation + 180.0 - face * 90.0) * Math.PI / 180.0;
         float wx = (float)(minimapX + halfSizeD + Math.cos(angle) * d);
         float wy = (float)(minimapY + halfSizeD + Math.sin(angle) * d);
         RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
         RenderSystem.setShaderTexture(0, COMPASS[face]);
         buffer = tessellator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
         buffer.addVertex(m, wx - ws, wy - ws, 0.0F).setColor(255, 255, 255, 255).setUv(0.0F, 0.0F);
         buffer.addVertex(m, wx - ws, wy + ws, 0.0F).setColor(255, 255, 255, 255).setUv(0.0F, 1.0F);
         buffer.addVertex(m, wx + ws, wy + ws, 0.0F).setColor(255, 255, 255, 255).setUv(1.0F, 1.0F);
         buffer.addVertex(m, wx + ws, wy - ws, 0.0F).setColor(255, 255, 255, 255).setUv(1.0F, 0.0F);
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   private void drawInWorldIcons(
      Minecraft mc, GuiGraphics graphics, DeltaTracker tickDelta, double playerX, double playerY, double playerZ, int scaledWidth, int scaledHeight
   ) {
      GuiHelper.setupDrawing();
      float scaledWidth2 = scaledWidth / 2.0F;
      float scaledHeight2 = scaledHeight / 2.0F;
      InWorldMapIcon focusedIcon = null;

      for (MapIcon icon : this.mapIcons) {
         Vec3 pos = icon.getPos(tickDelta.getGameTimeDeltaPartialTick(false));
         double playerDist = MathUtils.dist(pos.x, pos.y, pos.z, playerX, playerY, playerZ);
         if (icon.isVisible(MapType.WORLD_ICON, playerDist, false)) {
            Vector4f v = new Vector4f((float)(pos.x - this.cameraPos.x), (float)(pos.y - this.cameraPos.y), (float)(pos.z - this.cameraPos.z), 1.0F);
            double lookAngle = mc.player.getLookAngle().dot(new Vec3(v.x(), v.y(), v.z()).normalize());
            if (lookAngle > 0.0) {
               this.worldMatrix.transform(v);
               v.div(v.w());
               float ix = scaledWidth2 + v.x() * scaledWidth2;
               float iy = scaledHeight2 - v.y() * scaledHeight2;
               double mouseDist = MathUtils.dist(ix, iy, scaledWidth2, scaledHeight2);
               InWorldMapIcon inWorldMapIcon = new InWorldMapIcon(icon, ix, iy, playerDist, mouseDist);
               if (mouseDist <= 5.0 * (Double)FTBChunksClientConfig.WAYPOINT_FOCUS_DISTANCE.get()
                  && (focusedIcon == null || focusedIcon.distanceToMouse() > mouseDist)) {
                  focusedIcon = inWorldMapIcon;
               }

               this.inWorldMapIcons.add(inWorldMapIcon);
            }
         }
      }

      double fadeStart = (Double)FTBChunksClientConfig.WAYPOINT_DOT_FADE_DISTANCE.get();
      double fadeMin = fadeStart * 2.0 / 3.0;

      for (InWorldMapIcon icon : this.inWorldMapIcons) {
         if (icon.distanceToPlayer() > fadeMin) {
            int iconAlpha = icon.distanceToPlayer() < fadeStart ? (int)(255.0 * ((icon.distanceToPlayer() - fadeMin) / (fadeStart - fadeMin))) : 255;
            float minSize = 0.25F;
            float maxSize = (float)(minSize * (Double)FTBChunksClientConfig.WAYPOINT_FOCUS_SCALE.get());
            if (iconAlpha > 0) {
               float iconScale = Mth.lerp((50.0F - Math.min((float)icon.distanceToMouse(), 50.0F)) / 50.0F, minSize, maxSize);
               PoseStack poseStack = graphics.pose();
               poseStack.pushPose();
               poseStack.translate(icon.x(), icon.y(), icon == focusedIcon ? 50.0F : -100.0F);
               poseStack.scale(iconScale, iconScale, 1.0F);
               icon.icon().draw(MapType.WORLD_ICON, graphics, -8, -8, 16, 16, icon != focusedIcon, iconAlpha);
               poseStack.popPose();
            }
         }
      }

      this.inWorldMapIcons.clear();
      RenderSystem.disableBlend();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableDepthTest();
   }

   public void renderWorldLast(PoseStack poseStack, Matrix4f projectionMatrix, Matrix4f modelViewMatrix, Camera camera, DeltaTracker tickDelta) {
      Minecraft mc = Minecraft.getInstance();
      if (!mc.options.hideGui
         && !MapManager.getInstance().isEmpty()
         && mc.level != null
         && mc.player != null
         && !MapDimension.getCurrent().isEmpty()
         && FTBChunksWorldConfig.playerHasMapStage(mc.player)) {
         this.worldMatrix = new Matrix4f(projectionMatrix);
         this.worldMatrix.mul(modelViewMatrix);
         this.cameraPos = camera.getPosition();
         if ((Boolean)FTBChunksClientConfig.IN_WORLD_WAYPOINTS.get()) {
            List<WaypointIcon> visibleWaypoints = this.findVisibleWaypoints(mc.player, tickDelta);
            if (!visibleWaypoints.isEmpty()) {
               Vec3 cameraPos = camera.getPosition();
               poseStack.pushPose();
               poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
               RenderType renderType = RenderType.beaconBeam(WAYPOINT_BEAM, true);
               VertexConsumer depthBuffer = mc.renderBuffers().bufferSource().getBuffer(renderType);
               float y1 = (float)(cameraPos.y + 30.0);
               float y2 = y1 + 70.0F;
               int yMin = mc.level.getMinBuildHeight();

               for (WaypointIcon waypoint : visibleWaypoints) {
                  drawWaypointBeacon(poseStack, cameraPos, depthBuffer, y1, y2, yMin, waypoint);
               }

               poseStack.popPose();
               mc.renderBuffers().bufferSource().endBatch(renderType);
            }
         }
      }
   }

   private static void drawWaypointBeacon(PoseStack poseStack, Vec3 cameraPos, VertexConsumer depthBuffer, float y1, float y2, int yMin, WaypointIcon waypoint) {
      Vec3 pos = waypoint.getPos(1.0F);
      int alpha = waypoint.getAlpha();
      double angle = Math.atan2(cameraPos.z - pos.z, cameraPos.x - pos.x) * 180.0 / Math.PI;
      int r = waypoint.getColor().redi();
      int g = waypoint.getColor().greeni();
      int b = waypoint.getColor().bluei();
      poseStack.pushPose();
      poseStack.translate(pos.x, 0.0, pos.z);
      poseStack.mulPose(Axis.YP.rotationDegrees((float)(-angle - 135.0)));
      float s = 0.6F;
      Matrix4f m = poseStack.last().pose();
      depthBuffer.addVertex(m, -s, yMin, s)
         .setColor(r, g, b, alpha)
         .setUv(0.0F, 1.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(poseStack.last(), 0.0F, 1.0F, 0.0F);
      depthBuffer.addVertex(m, -s, y1, s)
         .setColor(r, g, b, alpha)
         .setUv(0.0F, 0.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(poseStack.last(), 0.0F, 1.0F, 0.0F);
      depthBuffer.addVertex(m, s, y1, -s)
         .setColor(r, g, b, alpha)
         .setUv(1.0F, 0.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(poseStack.last(), 0.0F, 1.0F, 0.0F);
      depthBuffer.addVertex(m, s, yMin, -s)
         .setColor(r, g, b, alpha)
         .setUv(1.0F, 1.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(poseStack.last(), 0.0F, 1.0F, 0.0F);
      depthBuffer.addVertex(m, -s, y1, s)
         .setColor(r, g, b, alpha)
         .setUv(0.0F, 1.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(poseStack.last(), 0.0F, 1.0F, 0.0F);
      depthBuffer.addVertex(m, -s, y2, s)
         .setColor(r, g, b, 0)
         .setUv(0.0F, 0.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(poseStack.last(), 0.0F, 1.0F, 0.0F);
      depthBuffer.addVertex(m, s, y2, -s)
         .setColor(r, g, b, 0)
         .setUv(1.0F, 0.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(poseStack.last(), 0.0F, 1.0F, 0.0F);
      depthBuffer.addVertex(m, s, y1, -s)
         .setColor(r, g, b, alpha)
         .setUv(1.0F, 1.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(15728880)
         .setNormal(poseStack.last(), 0.0F, 1.0F, 0.0F);
      poseStack.popPose();
   }

   private List<WaypointIcon> findVisibleWaypoints(Player player, DeltaTracker tickDelta) {
      return MapManager.getInstance().map(manager -> {
         List<WaypointIcon> visibleWaypoints = new ArrayList<>();
         double fadeOutDistance = (Double)FTBChunksClientConfig.WAYPOINT_BEACON_FADE_DISTANCE.get();
         double fadeOutDistanceP = fadeOutDistance * 2.0 / 3.0;
         MapDimension dim = manager.getDimension(player.level().dimension());

         for (Waypoint waypoint : dim.getWaypointManager()) {
            if (!waypoint.isHidden()) {
               double distance = Math.sqrt(waypoint.getDistanceSq(player));
               if (distance > fadeOutDistanceP && distance <= (Double)FTBChunksClientConfig.WAYPOINT_MAX_DISTANCE.get()) {
                  int alpha = 150;
                  if (distance < fadeOutDistance) {
                     alpha = (int)(alpha * ((distance - fadeOutDistanceP) / (fadeOutDistance - fadeOutDistanceP)));
                  }

                  if (alpha > 0) {
                     waypoint.getMapIcon().setAlpha(alpha);
                     visibleWaypoints.add(waypoint.getMapIcon());
                  }
               }
            }
         }

         visibleWaypoints.sort(new MapIconComparator(player.position(), tickDelta.getGameTimeDeltaPartialTick(false)));
         return visibleWaypoints;
      }).orElse(List.of());
   }

   private EventResult screenOpened(Screen screen, ScreenAccess access) {
      if (screen instanceof PauseScreen) {
         this.nextRegionSave = System.currentTimeMillis() + 60000L;
         MapManager.getInstance().ifPresent(MapManager::saveAllRegions);
      }

      return EventResult.pass();
   }

   private void clientTick(Minecraft mc) {
      if (mc.level != null) {
         MapManager.getInstance().ifPresent(manager -> {
            if (mc.player != null) {
               this.prevPlayerX = this.currentPlayerX;
               this.prevPlayerY = this.currentPlayerY;
               this.prevPlayerZ = this.currentPlayerZ;
               this.currentPlayerX = mc.player.getX();
               this.currentPlayerY = mc.player.getY();
               this.currentPlayerZ = mc.player.getZ();
            }

            Level level = Objects.requireNonNull(mc.level);
            if (this.taskQueueTicks % ((Integer)FTBChunksClientConfig.RERENDER_QUEUE_TICKS.get()).intValue() == 0L) {
               this.runRerenderTasks(level, manager);
            }

            if (this.taskQueueTicks % ((Integer)FTBChunksClientConfig.TASK_QUEUE_TICKS.get()).intValue() == 0L) {
               ClientTaskQueue.runQueuedTasks();
            }

            int releaseInterval = (Integer)FTBChunksClientConfig.REGION_RELEASE_TIME.get();
            if (releaseInterval > 0 && level.getGameTime() % (releaseInterval * 20L) == 0L) {
               manager.releaseStaleRegionData(releaseInterval * 1000L);
            }

            if (mc.screen == null) {
               manager.checkForRegionPurge();
            }

            manager.firePendingUpdateEvents();
            this.taskQueueTicks++;
         });
      }
   }

   private void runRerenderTasks(Level level, MapManager manager) {
      if (!this.rerenderCache.isEmpty()) {
         this.rerenderCache.forEach((chunkPos, blocks) -> {
            ChunkAccess chunkAccess = level.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.FULL, false);
            if (chunkAccess != null) {
               this.queueOrExecute(new ChunkUpdateTask(manager, level, chunkAccess, chunkPos, blocks.toIntArray()));
            }
         });
         this.rerenderCache.clear();
      }
   }

   private void teamPropertiesChanged(ClientTeamPropertiesChangedEvent event) {
      MapManager.getInstance().ifPresent(manager -> manager.updateAllRegions(false));
   }

   private void mapIcons(MapIconEvent event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level != null && mc.player != null) {
         if ((Boolean)FTBChunksClientConfig.MINIMAP_WAYPOINTS.get()) {
            MapDimension.getCurrent().ifPresent(mapDimension -> {
               for (Waypoint w : mapDimension.getWaypointManager()) {
                  if (!w.isHidden() || !event.getMapType().isMinimap()) {
                     event.add(w.getMapIcon());
                  }
               }
            });
         }

         if ((Boolean)FTBChunksClientConfig.MINIMAP_ENTITIES.get()) {
            for (Entity entity : mc.level.entitiesForRendering()) {
               if (EntityIconUtils.shouldEntityRender(entity, mc.player)) {
                  Icon icon = EntityIconLoader.getIcon(entity);
                  Optional<EntityIconSettings> settings = EntityIconLoader.getSettings(entity.getType());
                  if (!settings.isEmpty()) {
                     WidthHeight wh = settings.get().widthHeight();
                     if (!icon.isEmpty()) {
                        if ((Boolean)FTBChunksClientConfig.ONLY_SURFACE_ENTITIES.get() && !mc.level.dimensionType().hasCeiling()) {
                           MapDimension.getCurrent().ifPresent(mapDimension -> {
                              int x = Mth.floor(entity.getX());
                              int z = Mth.floor(entity.getZ());
                              MapRegion region = mapDimension.getRegion(XZ.regionFromBlock(x, z));
                              MapRegionData data = region.getData();
                              if (data != null) {
                                 int y = data.height[(x & 511) + (z & 511) * 512];
                                 if (entity.getY() >= y - 10) {
                                    event.add(new EntityMapIcon(entity, icon, wh));
                                 }
                              }
                           });
                        } else {
                           event.add(new EntityMapIcon(entity, icon, wh));
                        }
                     }
                  }
               }
            }
         }

         if ((Boolean)FTBChunksClientConfig.MINIMAP_PLAYER_HEADS.get()) {
            if (mc.level.players().size() > 1) {
               for (AbstractClientPlayer player : mc.level.players()) {
                  if (player != mc.player && !player.isInvisibleTo(mc.player) && VisibleClientPlayers.isPlayerVisible(player)) {
                     if (this.longRangePlayerTracker.remove(player.getUUID()) != null) {
                        LargeMapScreen.refreshIconsIfOpen();
                     }

                     event.add(new EntityMapIcon(player, FaceIcon.getFace(player.getGameProfile())));
                  }
               }
            }

            this.longRangePlayerTracker.forEach((id, iconx) -> event.add(iconx));
         }

         if (!event.getMapType().isMinimap()) {
            PointerIconMode pointerIconMode = (PointerIconMode)FTBChunksClientConfig.POINTER_ICON_MODE.get();
            if (pointerIconMode.showFace()) {
               event.add(new EntityMapIcon(mc.player, FaceIcon.getFace(mc.player.getGameProfile())));
            }

            if (pointerIconMode.showPointer()) {
               event.add(new PointerIcon());
            }
         }
      }
   }

   void refreshMinimapIcons() {
      this.lastMapIconUpdate = 0L;
   }

   private void reloadShaders(ResourceProvider resourceProvider, ShadersSink sink) {
   }

   public void rerender(BlockPos pos) {
      ChunkPos chunkPos = new ChunkPos(pos);
      IntOpenHashSet set = this.rerenderCache.get(chunkPos);
      if (set == null) {
         set = new IntOpenHashSet();
         this.rerenderCache.put(chunkPos, set);
      }

      if (set.add((pos.getX() & 15) + (pos.getZ() & 15) * 16) && (Boolean)FTBChunksClientConfig.DEBUG_INFO.get()) {
         this.renderedDebugCount++;
      }
   }

   public void handlePacket(ClientboundSectionBlocksUpdatePacket p) {
      SectionPos sectionPos = p.sectionPos;
      short[] positions = p.positions;

      for (short position : positions) {
         this.rerender(sectionPos.relativeToBlockPos(position));
      }
   }

   public void handlePacket(ClientboundLevelChunkWithLightPacket p) {
      MapManager manager = MapManager.getInstance().orElse(null);
      Level level = Minecraft.getInstance().level;
      if (level != null) {
         ChunkAccess chunkAccess = level.getChunk(p.getX(), p.getZ(), ChunkStatus.FULL, false);
         if (chunkAccess != null) {
            this.queueOrExecute(new ChunkUpdateTask(manager, level, chunkAccess, new ChunkPos(p.getX(), p.getZ())));
         }
      }
   }

   public void queueOrExecute(MapTask task) {
      MAP_EXECUTOR.execute(task);
   }

   public void handlePacket(ClientboundBlockUpdatePacket p) {
      this.rerender(p.getPos());
   }

   public void maybeClearDeathpoint(Player player) {
      int maxDist = (Integer)FTBChunksClientConfig.DEATH_WAYPOINT_AUTOREMOVE_DISTANCE.get();
      MapManager.getInstance()
         .ifPresent(
            manager -> {
               if (maxDist > 0 && Minecraft.getInstance().screen == null) {
                  WaypointManagerImpl wpm = manager.getDimension(player.level().dimension()).getWaypointManager();
                  wpm.getNearestDeathpoint(player)
                     .ifPresent(
                        wp -> {
                           if (wp.getDistanceSq(player) < maxDist * maxDist) {
                              wpm.remove(wp);
                              wpm.getNearestDeathpoint(player).ifPresent(wp1 -> wp1.setHidden(false));
                              player.displayClientMessage(
                                 Component.translatable("ftbchunks.deathpoint_removed", new Object[]{wp.getDisplayName()}).withStyle(ChatFormatting.YELLOW),
                                 true
                              );
                           }
                        }
                     );
               }
            }
         );
   }

   public void updateTrackedPlayerPos(GameProfile profile, BlockPos pos) {
      boolean changed = false;
      if (pos == null) {
         changed = this.longRangePlayerTracker.remove(profile.getId()) != null;
      } else {
         TrackedPlayerMapIcon icon = this.longRangePlayerTracker.get(profile.getId());
         if (icon == null) {
            this.longRangePlayerTracker.put(profile.getId(), new TrackedPlayerMapIcon(profile, Vec3.atCenterOf(pos), FaceIcon.getFace(profile)));
            changed = true;
         } else {
            icon.setPos(Vec3.atCenterOf(pos));
         }
      }

      if (changed) {
         LargeMapScreen.refreshIconsIfOpen();
      }
   }

   public List<Component> getChunkSummary() {
      if (this.generalChunkData == null) {
         return List.of();
      }

      List<Component> list = new ArrayList<>(4);
      list.add(Component.translatable("ftbchunks.gui.claimed"));
      int claimed = this.generalChunkData.claimed();
      int maxClaim = this.generalChunkData.maxClaimChunks();
      list.add(
         Component.literal(claimed + " / " + maxClaim)
            .withStyle(claimed > maxClaim ? ChatFormatting.RED : (claimed == maxClaim ? ChatFormatting.YELLOW : ChatFormatting.GREEN))
      );
      list.add(Component.translatable("ftbchunks.gui.force_loaded"));
      int loaded = this.generalChunkData.loaded();
      int maxLoaded = this.generalChunkData.maxForceLoadChunks();
      list.add(
         Component.literal(loaded + " / " + maxLoaded)
            .withStyle(loaded > maxLoaded ? ChatFormatting.RED : (loaded == maxLoaded ? ChatFormatting.YELLOW : ChatFormatting.GREEN))
      );
      return list;
   }

   public SendGeneralDataPacket.GeneralChunkData getGeneralChunkData() {
      return this.generalChunkData;
   }

   public int getMinimapTextureId() {
      return this.minimapTextureId;
   }

   public static Waypoint addWaypoint(String name, GlobalPos position, int color) {
      return FTBChunksAPI.clientApi().getWaypointManager(position.dimension()).map(mgr -> {
         Waypoint wp = mgr.addWaypointAt(position.pos(), name);
         wp.setColor(color);
         return wp;
      }).orElse(null);
   }

   public void setupComponents() {
      this.sortedComponents.clear();
      this.computeOrderedComponents();
   }

   private void computeOrderedComponents() {
      Map<ResourceLocation, MinimapInfoComponent> componentMap = FTBChunksAPI.clientApi()
         .getMinimapComponents()
         .stream()
         .collect(Collectors.toMap(MinimapInfoComponent::id, Function.identity()));
      List<ResourceLocation> order = ((List)FTBChunksClientConfig.MINIMAP_INFO_ORDER.get())
         .stream()
         .<ResourceLocation>map(ResourceLocation::parse)
         .collect(Collectors.toList());
      boolean save = false;

      for (ResourceLocation location : componentMap.keySet()) {
         if (!order.contains(location)) {
            order.add(location);
            save = true;
         }
      }

      if (save) {
         FTBChunksClientConfig.MINIMAP_INFO_ORDER.set(order.stream().map(ResourceLocation::toString).collect(Collectors.toList()));
         FTBChunksClientConfig.saveConfig();
      }

      for (ResourceLocation id : order) {
         MinimapInfoComponent minimapInfoComponent = componentMap.get(id);
         if (minimapInfoComponent != null && FTBChunksAPI.clientApi().isMinimapComponentEnabled(minimapInfoComponent)) {
            this.sortedComponents.add(minimapInfoComponent);
         }
      }
   }

   public static double getVanillaEffectsOffsetX() {
      return vanillaEffectsOffsetX;
   }

   public int getRenderedDebugCount() {
      return this.renderedDebugCount;
   }

   public static void openIconSettingsScreen() {
      Minecraft.getInstance().submit(() -> new EntityIconSettingsScreen(true).openGui());
   }
}
