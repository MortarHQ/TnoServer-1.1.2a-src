package xaero.map.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.InputConstants.Type;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.client.controls.util.KeyMappingUtils;
import xaero.lib.client.graphics.shader.LibShaders;
import xaero.lib.client.gui.config.context.BuiltInEditConfigScreenContexts;
import xaero.lib.client.gui.widget.Tooltip;
import xaero.lib.client.gui.widget.dropdown.DropDownWidget;
import xaero.lib.common.config.Config;
import xaero.lib.common.config.single.SingleConfigManager;
import xaero.lib.common.util.MathUtils;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.animation.Animation;
import xaero.map.animation.SinAnimation;
import xaero.map.animation.SlowingAnimation;
import xaero.map.common.config.WorldMapConfigConstants;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.config.primary.option.WorldMapPrimaryClientConfigOptions;
import xaero.map.config.util.WorldMapClientConfigUtils;
import xaero.map.controls.ControlsRegister;
import xaero.map.core.IWorldMapMinecraftClient;
import xaero.map.effects.Effects;
import xaero.map.element.HoveredMapElementHolder;
import xaero.map.graphics.CustomRenderTypes;
import xaero.map.graphics.ImprovedFramebuffer;
import xaero.map.graphics.MapRenderHelper;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRenderer;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import xaero.map.gui.dropdown.rightclick.GuiRightClickMenu;
import xaero.map.gui.dropdown.rightclick.RightClickOption;
import xaero.map.misc.Misc;
import xaero.map.misc.OptimizedMath;
import xaero.map.mods.SupportMods;
import xaero.map.mods.gui.Waypoint;
import xaero.map.radar.tracker.PlayerTeleporter;
import xaero.map.radar.tracker.PlayerTrackerMapElement;
import xaero.map.region.BranchLeveledRegion;
import xaero.map.region.LayeredRegionManager;
import xaero.map.region.LeveledRegion;
import xaero.map.region.MapBlock;
import xaero.map.region.MapRegion;
import xaero.map.region.MapTile;
import xaero.map.region.MapTileChunk;
import xaero.map.region.texture.RegionTexture;
import xaero.map.teleport.MapTeleporter;
import xaero.map.world.MapDimension;
import xaero.map.world.MapWorld;

public class GuiMap extends ScreenBase implements IRightClickableElement {
   private static final Component FULL_RELOAD_IN_PROGRESS = Component.translatable("gui.xaero_full_reload_in_progress");
   private static final Component UNKNOWN_DIMENSION_TYPE1 = Component.translatable("gui.xaero_unknown_dimension_type1");
   private static final Component UNKNOWN_DIMENSION_TYPE2 = Component.translatable("gui.xaero_unknown_dimension_type2");
   private static final double ZOOM_STEP = 1.2;
   private static final int white = -1;
   private static final int black = -16777216;
   private static int lastAmountOfRegionsViewed = 1;
   private long loadingAnimationStart;
   private Entity player;
   private double screenScale = 0.0;
   private int mouseDownPosX = -1;
   private int mouseDownPosY = -1;
   private double mouseDownCameraX = -1.0;
   private double mouseDownCameraZ = -1.0;
   private int mouseCheckPosX = -1;
   private int mouseCheckPosY = -1;
   private long mouseCheckTimeNano = -1L;
   private int prevMouseCheckPosX = -1;
   private int prevMouseCheckPosY = -1;
   private long prevMouseCheckTimeNano = -1L;
   private double cameraX = 0.0;
   private double cameraZ = 0.0;
   private boolean shouldResetCameraPos;
   private int[] cameraDestination = null;
   private SlowingAnimation cameraDestinationAnimX = null;
   private SlowingAnimation cameraDestinationAnimZ = null;
   private double scale;
   private double userScale;
   private static double destScale = 3.0;
   private boolean pauseZoomKeys;
   private int lastZoomMethod;
   private double prevPlayerDimDiv;
   private HoveredMapElementHolder<?, ?> viewed = null;
   private boolean viewedInList;
   private HoveredMapElementHolder<?, ?> viewedOnMousePress = null;
   private boolean overWaypointsMenu;
   private Animation zoomAnim;
   public boolean waypointMenu = false;
   private boolean overPlayersMenu;
   public boolean playersMenu = false;
   private static ImprovedFramebuffer primaryScaleFBO = null;
   private float[] colourBuffer = new float[4];
   private ArrayList<MapRegion> regionBuffer = new ArrayList<>();
   private ArrayList<BranchLeveledRegion> branchRegionBuffer = new ArrayList<>();
   private boolean prevWaitingForBranchCache = true;
   private boolean prevLoadingLeaves = true;
   private ResourceKey<Level> lastNonNullViewedDimensionId;
   private ResourceKey<Level> lastViewedDimensionId;
   private String lastViewedMultiworldId;
   private int mouseBlockPosX;
   private int mouseBlockPosY;
   private int mouseBlockPosZ;
   private ResourceKey<Level> mouseBlockDim;
   private double mouseBlockCoordinateScale = 1.0;
   private long lastStartTime;
   private final GuiMapSwitching mapSwitchingGui;
   private MapMouseButtonPress leftMouseButton;
   private MapMouseButtonPress rightMouseButton;
   private MapProcessor mapProcessor;
   private MapDimension futureDimension;
   public boolean noUploadingLimits;
   private boolean[] waitingForBranchCache = new boolean[1];
   private Button settingsButton;
   private Button exportButton;
   private Button waypointsButton;
   private Button playersButton;
   private Button radarButton;
   private Button claimsButton;
   private Button zoomInButton;
   private Button zoomOutButton;
   private Button keybindingsButton;
   private Button caveModeButton;
   private Button dimensionToggleButton;
   private Button buttonPressed;
   private GuiRightClickMenu rightClickMenu;
   private int rightClickX;
   private int rightClickY;
   private int rightClickZ;
   private ResourceKey<Level> rightClickDim;
   private double rightClickCoordinateScale;
   private boolean lastFrameRenderedRootTextures;
   private MapTileSelection mapTileSelection;
   private boolean tabPressed;
   private GuiCaveModeOptions caveModeOptions;
   private static final Matrix4f identityMatrix = new Matrix4f();

   public GuiMap(Screen parent, Screen escape, MapProcessor mapProcessor, Entity player) {
      super(parent, escape, Component.translatable("gui.xaero_world_map_screen"));
      this.player = player;
      this.shouldResetCameraPos = true;
      this.leftMouseButton = new MapMouseButtonPress();
      this.rightMouseButton = new MapMouseButtonPress();
      this.mapSwitchingGui = new GuiMapSwitching(mapProcessor);
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      boolean openingAnimationConfig = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.OPENING_ANIMATION);
      this.userScale = destScale * (openingAnimationConfig ? 1.5F : 1.0F);
      this.zoomAnim = new SlowingAnimation(this.userScale, destScale, 0.88, destScale * 0.001);
      this.mapProcessor = mapProcessor;
      this.caveModeOptions = new GuiCaveModeOptions();
      if (SupportMods.minimap()) {
         SupportMods.xaeroMinimap.onMapConstruct();
      }
   }

   private double getScaleMultiplier(int screenShortSide) {
      return screenShortSide <= 1080 ? 1.0 : screenShortSide / 1080.0;
   }

   public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T guiEventListener) {
      return (T)super.addRenderableWidget(guiEventListener);
   }

   public <T extends GuiEventListener & Renderable & NarratableEntry> T addButton(T guiEventListener) {
      return this.addRenderableWidget(guiEventListener);
   }

   public <T extends GuiEventListener & NarratableEntry> T addWidget(T guiEventListener) {
      return (T)super.addWidget(guiEventListener);
   }

   public void init() {
      super.init();
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      MapWorld mapWorld = this.mapProcessor.getMapWorld();
      this.futureDimension = mapWorld != null && mapWorld.getFutureDimensionId() != null ? mapWorld.getFutureDimension() : null;
      this.tabPressed = false;
      boolean waypointsEnabled = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.WAYPOINTS);
      this.waypointMenu = this.waypointMenu && waypointsEnabled;
      this.mapSwitchingGui.init(this, this.minecraft, this.width, this.height);
      boolean effectiveCaveModeAllowed = WorldMapClientConfigUtils.getEffectiveCaveModeAllowed();
      Tooltip caveModeButtonTooltip = new Tooltip(
         Component.translatable(effectiveCaveModeAllowed ? "gui.xaero_box_cave_mode" : "gui.xaero_box_cave_mode_not_allowed")
      );
      this.caveModeButton = new GuiTexturedButton(
         0, this.height - 40, 20, 20, 229, 64, 16, 16, WorldMap.guiTextures, this::onCaveModeButton, () -> caveModeButtonTooltip
      );
      this.caveModeButton.active = effectiveCaveModeAllowed;
      this.addButton(this.caveModeButton);
      this.caveModeOptions.onInit(this, this.mapProcessor);
      Tooltip dimensionToggleButtonTooltip = new Tooltip(
         Component.translatable("gui.xaero_dimension_toggle_button", new Object[]{KeyMappingUtils.getKeyName(ControlsRegister.keyToggleDimension)})
      );
      this.dimensionToggleButton = new GuiTexturedButton(
         0, this.height - 60, 20, 20, 197, 80, 16, 16, WorldMap.guiTextures, this::onDimensionToggleButton, () -> dimensionToggleButtonTooltip
      );
      this.addButton(this.dimensionToggleButton);
      this.loadingAnimationStart = System.currentTimeMillis();
      if (SupportMods.minimap()) {
         SupportMods.xaeroMinimap.requestWaypointsRefresh();
      }

      this.screenScale = Minecraft.getInstance().getWindow().getGuiScale();
      this.pauseZoomKeys = false;
      Tooltip openSettingsTooltip = new Tooltip(
         Component.translatable("gui.xaero_box_open_settings", new Object[]{KeyMappingUtils.getKeyName(ControlsRegister.keyOpenSettings)})
      );
      this.addButton(
         this.settingsButton = new GuiTexturedButton(0, 0, 30, 30, 113, 0, 20, 20, WorldMap.guiTextures, this::onSettingsButton, () -> openSettingsTooltip)
      );
      Tooltip waypointsTooltip;
      if (waypointsEnabled) {
         waypointsTooltip = new Tooltip(this.waypointMenu ? "gui.xaero_box_close_waypoints" : "gui.xaero_box_open_waypoints");
      } else {
         waypointsTooltip = new Tooltip(!SupportMods.minimap() ? "gui.xaero_box_waypoints_minimap_required" : "gui.xaero_box_waypoints_disabled");
      }

      Tooltip playersTooltip = new Tooltip(this.playersMenu ? "gui.xaero_box_close_players" : "gui.xaero_box_open_players");
      boolean displayClaimsConfig = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.OPAC_CLAIMS);
      Tooltip claimsTooltip;
      if (SupportMods.pac()) {
         claimsTooltip = new Tooltip(
            Component.translatable(
               displayClaimsConfig ? "gui.xaero_box_pac_displaying_claims" : "gui.xaero_box_pac_not_displaying_claims",
               new Object[]{Component.literal(KeyMappingUtils.getKeyName(SupportMods.xaeroPac.getPacClaimsKeyBinding())).withStyle(ChatFormatting.DARK_GREEN)}
            )
         );
      } else {
         claimsTooltip = new Tooltip(Component.translatable("gui.xaero_box_claims_pac_required"));
      }

      this.addButton(
         this.waypointsButton = new GuiTexturedButton(
            this.width - 20, this.height - 20, 20, 20, 213, 0, 16, 16, WorldMap.guiTextures, this::onWaypointsButton, () -> waypointsTooltip
         )
      );
      this.waypointsButton.active = waypointsEnabled;
      this.addButton(
         this.playersButton = new GuiTexturedButton(
            this.width - 20, this.height - 40, 20, 20, 197, 32, 16, 16, WorldMap.guiTextures, this::onPlayersButton, () -> playersTooltip
         )
      );
      boolean minimapRadarConfig = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.MINIMAP_RADAR);
      Tooltip radarButtonTooltip = new Tooltip(
         Component.translatable(
            minimapRadarConfig ? "gui.xaero_box_minimap_radar" : "gui.xaero_box_no_minimap_radar",
            new Object[]{
               Component.literal(KeyMappingUtils.getKeyName(SupportMods.minimap() ? SupportMods.xaeroMinimap.getToggleRadarKey() : null))
                  .withStyle(ChatFormatting.DARK_GREEN)
            }
         )
      );
      this.addButton(
         this.radarButton = new GuiTexturedButton(
            this.width - 20,
            this.height - 60,
            20,
            20,
            minimapRadarConfig ? 213 : 229,
            32,
            16,
            16,
            WorldMap.guiTextures,
            this::onRadarButton,
            () -> radarButtonTooltip
         )
      );
      this.getRadarButton().active = SupportMods.minimap();
      this.addButton(
         this.claimsButton = new GuiTexturedButton(
            this.width - 20,
            this.height - 80,
            20,
            20,
            displayClaimsConfig ? 197 : 213,
            64,
            16,
            16,
            WorldMap.guiTextures,
            this::onClaimsButton,
            () -> claimsTooltip
         )
      );
      this.claimsButton.active = SupportMods.pac() && !WorldMapClientConfigUtils.isOptionServerEnforced(WorldMapProfiledConfigOptions.OPAC_CLAIMS);
      Tooltip exportButtonTooltip = new Tooltip("gui.xaero_box_export");
      this.addButton(
         this.exportButton = new GuiTexturedButton(
            this.width - 20, this.height - 100, 20, 20, 133, 0, 16, 16, WorldMap.guiTextures, this::onExportButton, () -> exportButtonTooltip
         )
      );
      Tooltip controlsButtonTooltip = new Tooltip(
         I18n.get(
            "gui.xaero_box_controls",
            new Object[]{
               (SupportMods.minimap() ? SupportMods.xaeroMinimap.getControlsTooltip() : "")
                  + (SupportMods.pac() ? SupportMods.xaeroPac.getControlsTooltip() : "")
            }
         )
      );
      controlsButtonTooltip.setStartWidth(400);
      this.addButton(
         this.keybindingsButton = new GuiTexturedButton(
            this.width - 20, this.height - 120, 20, 20, 197, 0, 16, 16, WorldMap.guiTextures, this::onKeybindingsButton, () -> controlsButtonTooltip
         )
      );
      Tooltip zoomInButtonTooltip = new Tooltip(
         Component.translatable(
            "gui.xaero_box_zoom_in",
            new Object[]{Component.literal(KeyMappingUtils.getKeyName(ControlsRegister.keyZoomIn)).withStyle(ChatFormatting.DARK_GREEN)}
         )
      );
      this.zoomInButton = new GuiTexturedButton(
         this.width - 20, this.height - 160, 20, 20, 165, 0, 16, 16, WorldMap.guiTextures, this::onZoomInButton, () -> zoomInButtonTooltip
      );
      Tooltip zoomOutButtonTooltip = new Tooltip(
         Component.translatable(
            "gui.xaero_box_zoom_out",
            new Object[]{Component.literal(KeyMappingUtils.getKeyName(ControlsRegister.keyZoomOut)).withStyle(ChatFormatting.DARK_GREEN)}
         )
      );
      this.zoomOutButton = new GuiTexturedButton(
         this.width - 20, this.height - 140, 20, 20, 181, 0, 16, 16, WorldMap.guiTextures, this::onZoomOutButton, () -> zoomOutButtonTooltip
      );
      if ((Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.ZOOM_BUTTONS)) {
         this.addButton(this.zoomOutButton);
         this.addButton(this.zoomInButton);
      }

      if (this.rightClickMenu != null) {
         this.rightClickMenu.setClosed(true);
         this.rightClickMenu = null;
      }

      if (SupportMods.minimap() && this.waypointMenu) {
         SupportMods.xaeroMinimap.onMapInit(this, this.minecraft, this.width, this.height);
      }

      if (this.playersMenu) {
         WorldMap.trackedPlayerMenuRenderer.onMapInit(this, this.minecraft, this.width, this.height);
      }
   }

   protected void setInitialFocus() {
   }

   private void onCaveModeButton(Button b) {
      this.caveModeOptions.toggle(this);
      this.setFocused(this.caveModeButton);
   }

   private void onDimensionToggleButton(Button b) {
      this.mapProcessor.getMapWorld().toggleDimension(!hasShiftDown());
      String messageType = this.mapProcessor.getMapWorld().getCustomDimensionId() == null
         ? "gui.xaero_switched_to_current_dimension"
         : "gui.xaero_switched_to_dimension";
      ResourceLocation messageDimLoc = this.mapProcessor.getMapWorld().getFutureDimensionId() == null
         ? null
         : this.mapProcessor.getMapWorld().getFutureDimensionId().location();
      this.mapProcessor.getMessageBox().addMessage(Component.translatable(messageType, new Object[]{messageDimLoc.toString()}));
      this.init(this.minecraft, this.width, this.height);
      this.setFocused(this.dimensionToggleButton);
   }

   private void onSettingsButton(Button b) {
      this.minecraft.setScreen(new GuiWorldMapSettings(this, this, BuiltInEditConfigScreenContexts.CLIENT));
   }

   private void onKeybindingsButton(Button b) {
      this.minecraft.setScreen(new KeyBindsScreen(this, this.minecraft.options));
   }

   private void onExportButton(Button b) {
      this.minecraft.setScreen(new ExportScreen(this, this, this.mapProcessor, this.mapTileSelection));
   }

   private void toggleWaypointMenu() {
      if (this.playersMenu) {
         this.togglePlayerMenu();
      }

      this.waypointMenu = !this.waypointMenu;
      if (!this.waypointMenu) {
         SupportMods.xaeroMinimap.getWaypointMenuRenderer().onMenuClosed();
         this.unfocusAll();
      }
   }

   private void togglePlayerMenu() {
      if (this.waypointMenu) {
         this.toggleWaypointMenu();
      }

      this.playersMenu = !this.playersMenu;
      if (!this.playersMenu) {
         WorldMap.trackedPlayerMenuRenderer.onMenuClosed();
         this.unfocusAll();
      }
   }

   private void onPlayersButton(Button b) {
      this.togglePlayerMenu();
      this.init(this.minecraft, this.width, this.height);
      this.setFocused(this.playersButton);
   }

   public void onClaimsButton(Button unused) {
      WorldMapClientConfigUtils.tryTogglingCurrentProfileOption(WorldMapProfiledConfigOptions.OPAC_CLAIMS);
      this.init(this.minecraft, this.width, this.height);
      this.setFocused(this.claimsButton);
   }

   private void onWaypointsButton(Button b) {
      this.toggleWaypointMenu();
      this.init(this.minecraft, this.width, this.height);
      this.setFocused(this.waypointsButton);
   }

   public void onRadarButton(Button b) {
      WorldMapClientConfigUtils.tryTogglingCurrentProfileOption(WorldMapProfiledConfigOptions.MINIMAP_RADAR);
      this.init(this.minecraft, this.width, this.height);
      this.setFocused(this.radarButton);
   }

   private void onZoomInButton(Button b) {
      this.buttonPressed = this.buttonPressed == null ? b : null;
   }

   private void onZoomOutButton(Button b) {
      this.buttonPressed = this.buttonPressed == null ? b : null;
   }

   public boolean mouseClicked(double par1, double par2, int par3) {
      boolean toReturn = super.mouseClicked(par1, par2, par3);
      if (!toReturn) {
         if (par3 == 0) {
            this.leftMouseButton.isDown = this.leftMouseButton.clicked = true;
            this.leftMouseButton.pressedAtX = (int)Misc.getMouseX(this.minecraft, SupportMods.vivecraft);
            this.leftMouseButton.pressedAtY = (int)Misc.getMouseY(this.minecraft, SupportMods.vivecraft);
         } else if (par3 == 1) {
            this.rightMouseButton.isDown = this.rightMouseButton.clicked = true;
            this.rightMouseButton.pressedAtX = (int)Misc.getMouseX(this.minecraft, SupportMods.vivecraft);
            this.rightMouseButton.pressedAtY = (int)Misc.getMouseY(this.minecraft, SupportMods.vivecraft);
            this.viewedOnMousePress = this.viewed;
            this.rightClickX = this.mouseBlockPosX;
            this.rightClickY = this.mouseBlockPosY;
            this.rightClickZ = this.mouseBlockPosZ;
            this.rightClickDim = this.mouseBlockDim;
            this.rightClickCoordinateScale = this.mouseBlockCoordinateScale;
            if (SupportMods.minimap()) {
               SupportMods.xaeroMinimap.onRightClick();
            }

            if (this.viewedOnMousePress == null || !this.viewedOnMousePress.isRightClickValid()) {
               this.mapTileSelection = new MapTileSelection(this.rightClickX >> 4, this.rightClickZ >> 4);
            }
         } else {
            toReturn = this.onInputPress(Type.MOUSE, par3);
         }

         if (!toReturn && this.caveModeOptions.isEnabled()) {
            this.caveModeOptions.toggle(this);
            toReturn = true;
         }
      }

      return toReturn;
   }

   public boolean mouseReleased(double par1, double par2, int par3) {
      this.buttonPressed = null;
      int mouseX = (int)Misc.getMouseX(this.minecraft, SupportMods.vivecraft);
      int mouseY = (int)Misc.getMouseY(this.minecraft, SupportMods.vivecraft);
      if (this.leftMouseButton.isDown && par3 == 0) {
         this.leftMouseButton.isDown = false;
         if (Math.abs(this.leftMouseButton.pressedAtX - mouseX) < 5 && Math.abs(this.leftMouseButton.pressedAtY - mouseY) < 5) {
            this.mapClicked(0, this.leftMouseButton.pressedAtX, this.leftMouseButton.pressedAtY);
         }

         this.leftMouseButton.pressedAtX = -1;
         this.leftMouseButton.pressedAtY = -1;
      }

      if (this.rightMouseButton.isDown && par3 == 1) {
         this.rightMouseButton.isDown = false;
         this.mapClicked(1, mouseX, mouseY);
         this.rightMouseButton.pressedAtX = -1;
         this.rightMouseButton.pressedAtY = -1;
      }

      if (this.waypointMenu) {
         SupportMods.xaeroMinimap.onMapMouseRelease(par1, par2, par3);
      }

      if (this.playersMenu) {
         WorldMap.trackedPlayerMenuRenderer.onMapMouseRelease(par1, par2, par3);
      }

      boolean toReturn = super.mouseReleased(par1, par2, par3);
      if (!toReturn) {
         toReturn = this.onInputRelease(Type.MOUSE, par3);
      }

      return toReturn;
   }

   public boolean mouseScrolled(double par1, double par2, double g, double wheel) {
      int direction = wheel > 0.0 ? 1 : -1;
      if (this.waypointMenu && this.overWaypointsMenu) {
         SupportMods.xaeroMinimap.getWaypointMenuRenderer().mouseScrolled(direction);
      } else if (this.playersMenu && this.overPlayersMenu) {
         WorldMap.trackedPlayerMenuRenderer.mouseScrolled(direction);
      } else {
         this.changeZoom(wheel, 0);
      }

      return super.mouseScrolled(par1, par2, g, wheel);
   }

   private void changeZoom(double factor, int zoomMethod) {
      this.closeDropdowns();
      this.lastZoomMethod = zoomMethod;
      this.cameraDestinationAnimX = null;
      this.cameraDestinationAnimZ = null;
      if (hasControlDown()) {
         double destScaleBefore = destScale;
         if (destScale >= 1.0) {
            if (factor > 0.0) {
               destScale = Math.ceil(destScale);
            } else {
               destScale = Math.floor(destScale);
            }

            if (destScaleBefore == destScale) {
               destScale += factor > 0.0 ? 1.0 : -1.0;
            }

            if (destScale == 0.0) {
               destScale = 0.5;
            }
         } else {
            double reversedScale = 1.0 / destScale;
            double log2 = Math.log(reversedScale) / Math.log(2.0);
            if (factor > 0.0) {
               log2 = Math.floor(log2);
            } else {
               log2 = Math.ceil(log2);
            }

            destScale = 1.0 / Math.pow(2.0, log2);
            if (destScaleBefore == destScale) {
               destScale = 1.0 / Math.pow(2.0, log2 + (factor > 0.0 ? -1 : 1));
            }
         }
      } else {
         destScale = destScale * Math.pow(1.2, factor);
      }

      if (destScale < 0.0625) {
         destScale = 0.0625;
      } else if (destScale > 50.0) {
         destScale = 50.0;
      }
   }

   public void removed() {
      super.removed();
      this.leftMouseButton.isDown = false;
      this.rightMouseButton.isDown = false;
   }

   public void render(GuiGraphics guiGraphics, int scaledMouseX, int scaledMouseY, float partialTicks) {
      guiGraphics.flush();

      while (GL11.glGetError() != 0) {
      }

      GlStateManager._clearColor(0.0F, 0.0F, 0.0F, 0.0F);
      GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      LibShaders.ensureShaders();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      Minecraft mc = Minecraft.getInstance();
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      SingleConfigManager<Config> primaryConfigManager = WorldMap.INSTANCE.getConfigs().getPrimaryClientConfigManager();
      long startTime = System.currentTimeMillis();
      MapDimension currentFutureDim = !this.mapProcessor.isMapWorldUsable() ? null : this.mapProcessor.getMapWorld().getFutureDimension();
      if (currentFutureDim != this.futureDimension) {
         this.init(this.minecraft, this.width, this.height);
      }

      PoseStack matrixStack = guiGraphics.pose();
      double playerDimDiv = this.prevPlayerDimDiv;
      synchronized (this.mapProcessor.renderThreadPauseSync) {
         if (!this.mapProcessor.isRenderingPaused()) {
            Registry<DimensionType> dimTypes = this.mapProcessor.getWorldDimensionTypeRegistry();
            if (dimTypes != null) {
               playerDimDiv = this.mapProcessor.getMapWorld().getCurrentDimension().calculateDimDiv(dimTypes, this.player.level().dimensionType());
            }
         }
      }

      double scaledPlayerX = this.player.getX() / playerDimDiv;
      double scaledPlayerZ = this.player.getZ() / playerDimDiv;
      if (this.shouldResetCameraPos) {
         this.cameraX = (float)scaledPlayerX;
         this.cameraZ = (float)scaledPlayerZ;
         this.shouldResetCameraPos = false;
      } else if (this.prevPlayerDimDiv != 0.0 && playerDimDiv != this.prevPlayerDimDiv) {
         double oldScaledPlayerX = this.player.getX() / this.prevPlayerDimDiv;
         double oldScaledPlayerZ = this.player.getZ() / this.prevPlayerDimDiv;
         this.cameraX = this.cameraX - oldScaledPlayerX + scaledPlayerX;
         this.cameraZ = this.cameraZ - oldScaledPlayerZ + scaledPlayerZ;
         this.cameraDestinationAnimX = null;
         this.cameraDestinationAnimZ = null;
         this.cameraDestination = null;
      }

      this.prevPlayerDimDiv = playerDimDiv;
      double cameraXBefore = this.cameraX;
      double cameraZBefore = this.cameraZ;
      double scaleBefore = this.scale;
      this.mapSwitchingGui.preMapRender(this, this.minecraft, this.width, this.height);
      long passed = this.lastStartTime == 0L ? 16L : startTime - this.lastStartTime;
      double passedScrolls = (float)passed / 64.0F;
      int direction = this.buttonPressed != this.zoomInButton && !KeyMappingUtils.isPhysicallyDown(ControlsRegister.keyZoomIn)
         ? (this.buttonPressed != this.zoomOutButton && !KeyMappingUtils.isPhysicallyDown(ControlsRegister.keyZoomOut) ? 0 : -1)
         : 1;
      if (direction != 0) {
         boolean ctrlKey = hasControlDown();
         if (!ctrlKey || !this.pauseZoomKeys) {
            this.changeZoom(direction * passedScrolls, this.buttonPressed != this.zoomInButton && this.buttonPressed != this.zoomOutButton ? 1 : 2);
            if (ctrlKey) {
               this.pauseZoomKeys = true;
            }
         }
      } else {
         this.pauseZoomKeys = false;
      }

      this.lastStartTime = startTime;
      if (this.cameraDestination != null) {
         this.cameraDestinationAnimX = new SlowingAnimation(this.cameraX, this.cameraDestination[0], 0.9, 0.01);
         this.cameraDestinationAnimZ = new SlowingAnimation(this.cameraZ, this.cameraDestination[1], 0.9, 0.01);
         this.cameraDestination = null;
      }

      if (this.cameraDestinationAnimX != null) {
         this.cameraX = this.cameraDestinationAnimX.getCurrent();
         if (this.cameraX == this.cameraDestinationAnimX.getDestination()) {
            this.cameraDestinationAnimX = null;
         }
      }

      if (this.cameraDestinationAnimZ != null) {
         this.cameraZ = this.cameraDestinationAnimZ.getCurrent();
         if (this.cameraZ == this.cameraDestinationAnimZ.getDestination()) {
            this.cameraDestinationAnimZ = null;
         }
      }

      this.lastViewedDimensionId = null;
      this.lastViewedMultiworldId = null;
      this.mouseBlockPosY = 32767;
      boolean discoveredForHighlights = false;
      synchronized (this.mapProcessor.renderThreadPauseSync) {
         if (this.mapProcessor.isRenderingPaused()) {
            this.renderLoadingScreen(guiGraphics);
         } else {
            boolean mapLoaded = this.mapProcessor.getCurrentWorldId() != null
               && !this.mapProcessor.isWaitingForWorldUpdate()
               && this.mapProcessor.getMapSaveLoad().isRegionDetectionComplete();
            boolean noWorldMapEffect = mc.player == null
               || Misc.hasEffect(mc.player, Effects.NO_WORLD_MAP)
               || Misc.hasEffect(mc.player, Effects.NO_WORLD_MAP_HARMFUL);
            Item mapItem = this.mapProcessor.getMapItem();
            boolean allowedBasedOnItem = mapItem == null || mc.player != null && Misc.hasItem(mc.player, mapItem);
            boolean isLocked = this.mapProcessor.isCurrentMapLocked();
            if (mapLoaded && !noWorldMapEffect && allowedBasedOnItem && !isLocked) {
               if (SupportMods.vivecraft) {
                  GlStateManager._clearColor(0.0F, 0.0F, 0.0F, 1.0F);
                  GlStateManager._clear(16384, Minecraft.ON_OSX);
               }

               this.mapProcessor.updateCaveStart();
               this.lastNonNullViewedDimensionId = this.lastViewedDimensionId = this.mapProcessor.getMapWorld().getCurrentDimension().getDimId();
               this.lastViewedMultiworldId = this.mapProcessor.getMapWorld().getCurrentDimension().getCurrentMultiworld();
               if (SupportMods.minimap()) {
                  SupportMods.xaeroMinimap
                     .checkWaypoints(
                        this.mapProcessor.getMapWorld().isMultiplayer(),
                        this.lastViewedDimensionId,
                        this.lastViewedMultiworldId,
                        this.width,
                        this.height,
                        this,
                        this.mapProcessor.getMapWorld(),
                        this.mapProcessor.getWorldDimensionTypeRegistry()
                     );
               }

               int mouseXPos = (int)Misc.getMouseX(mc, false);
               int mouseYPos = (int)Misc.getMouseY(mc, false);
               double scaleMultiplier = this.getScaleMultiplier(Math.min(mc.getWindow().getWidth(), mc.getWindow().getHeight()));
               this.scale = this.userScale * scaleMultiplier;
               if (this.mouseCheckPosX == -1 || System.nanoTime() - this.mouseCheckTimeNano > 30000000L) {
                  this.prevMouseCheckPosX = this.mouseCheckPosX;
                  this.prevMouseCheckPosY = this.mouseCheckPosY;
                  this.prevMouseCheckTimeNano = this.mouseCheckTimeNano;
                  this.mouseCheckPosX = mouseXPos;
                  this.mouseCheckPosY = mouseYPos;
                  this.mouseCheckTimeNano = System.nanoTime();
               }

               if (!this.leftMouseButton.isDown) {
                  if (this.mouseDownPosX != -1) {
                     this.mouseDownPosX = -1;
                     this.mouseDownPosY = -1;
                     if (this.prevMouseCheckTimeNano != -1L) {
                        double downTime = 0.0;
                        int draggedX = 0;
                        int draggedY = 0;
                        downTime = System.nanoTime() - this.prevMouseCheckTimeNano;
                        draggedX = mouseXPos - this.prevMouseCheckPosX;
                        draggedY = mouseYPos - this.prevMouseCheckPosY;
                        double frameTime60FPS = 1.6666666666666666E7;
                        double speedScale = downTime / frameTime60FPS;
                        double speed_x = -draggedX / this.scale / speedScale;
                        double speed_z = -draggedY / this.scale / speedScale;
                        double speed = Math.sqrt(speed_x * speed_x + speed_z * speed_z);
                        if (speed > 0.0) {
                           double cos = speed_x / speed;
                           double sin = speed_z / speed;
                           double maxSpeed = 500.0 / this.userScale;
                           speed = Math.abs(speed) > maxSpeed ? Math.copySign(maxSpeed, speed) : speed;
                           double speed_factor = 0.9;
                           double ln = Math.log(speed_factor);
                           double move_distance = -speed / ln;
                           double moveX = cos * move_distance;
                           double moveZ = sin * move_distance;
                           this.cameraDestinationAnimX = new SlowingAnimation(this.cameraX, this.cameraX + moveX, 0.9, 0.01);
                           this.cameraDestinationAnimZ = new SlowingAnimation(this.cameraZ, this.cameraZ + moveZ, 0.9, 0.01);
                        }
                     }
                  }
               } else if (this.viewed == null || !this.viewedInList || this.mouseDownPosX != -1) {
                  if (this.mouseDownPosX != -1) {
                     this.cameraX = (this.mouseDownPosX - mouseXPos) / this.scale + this.mouseDownCameraX;
                     this.cameraZ = (this.mouseDownPosY - mouseYPos) / this.scale + this.mouseDownCameraZ;
                  } else {
                     this.mouseDownPosX = mouseXPos;
                     this.mouseDownPosY = mouseYPos;
                     this.mouseDownCameraX = this.cameraX;
                     this.mouseDownCameraZ = this.cameraZ;
                     this.cameraDestinationAnimX = null;
                     this.cameraDestinationAnimZ = null;
                  }
               }

               int mouseFromCentreX = mouseXPos - mc.getWindow().getWidth() / 2;
               int mouseFromCentreY = mouseYPos - mc.getWindow().getHeight() / 2;
               double oldMousePosX = mouseFromCentreX / this.scale + this.cameraX;
               double oldMousePosZ = mouseFromCentreY / this.scale + this.cameraZ;
               double preScale = this.scale;
               if (destScale != this.userScale) {
                  if (this.zoomAnim != null) {
                     this.userScale = this.zoomAnim.getCurrent();
                     this.scale = this.userScale * scaleMultiplier;
                  }

                  if (this.zoomAnim == null || MathUtils.round(this.zoomAnim.getDestination(), 4) != MathUtils.round(destScale, 4)) {
                     this.zoomAnim = new SinAnimation(this.userScale, destScale, 100L);
                  }
               }

               if (this.scale > preScale && this.lastZoomMethod != 2) {
                  this.cameraX = oldMousePosX - mouseFromCentreX / this.scale;
                  this.cameraZ = oldMousePosZ - mouseFromCentreY / this.scale;
               }

               int textureLevel = 0;
               double fboScale;
               if (this.scale >= 1.0) {
                  fboScale = Math.max(1.0, Math.floor(this.scale));
               } else {
                  fboScale = this.scale;
               }

               if (this.userScale < 1.0) {
                  double reversedScale = 1.0 / this.userScale;
                  double log2 = Math.floor(Math.log(reversedScale) / Math.log(2.0));
                  textureLevel = Math.min((int)log2, 3);
               }

               this.mapProcessor.getMapSaveLoad().mainTextureLevel = textureLevel;
               int leveledRegionShift = 9 + textureLevel;
               double secondaryScale = this.scale / fboScale;
               matrixStack.pushPose();
               double mousePosX = mouseFromCentreX / this.scale + this.cameraX;
               double mousePosZ = mouseFromCentreY / this.scale + this.cameraZ;
               matrixStack.pushPose();
               matrixStack.translate(0.0F, 0.0F, 971.0F);
               this.mouseBlockPosX = (int)Math.floor(mousePosX);
               this.mouseBlockPosZ = (int)Math.floor(mousePosZ);
               this.mouseBlockDim = this.mapProcessor.getMapWorld().getCurrentDimension().getDimId();
               this.mouseBlockCoordinateScale = this.getCurrentMapCoordinateScale();
               if (SupportMods.minimap()) {
                  SupportMods.xaeroMinimap.onBlockHover();
               }

               int mouseRegX = this.mouseBlockPosX >> leveledRegionShift;
               int mouseRegZ = this.mouseBlockPosZ >> leveledRegionShift;
               int renderedCaveLayer = this.mapProcessor.getCurrentCaveLayer();
               LeveledRegion<?> reg = this.mapProcessor.getLeveledRegion(renderedCaveLayer, mouseRegX, mouseRegZ, textureLevel);
               int maxRegBlockCoord = (1 << leveledRegionShift) - 1;
               int mouseRegPixelX = (this.mouseBlockPosX & maxRegBlockCoord) >> textureLevel;
               int mouseRegPixelZ = (this.mouseBlockPosZ & maxRegBlockCoord) >> textureLevel;
               this.mouseBlockPosX = (mouseRegX << leveledRegionShift) + (mouseRegPixelX << textureLevel);
               this.mouseBlockPosZ = (mouseRegZ << leveledRegionShift) + (mouseRegPixelZ << textureLevel);
               if (this.mapTileSelection != null && this.rightClickMenu == null) {
                  this.mapTileSelection.setEnd(this.mouseBlockPosX >> 4, this.mouseBlockPosZ >> 4);
               }

               MapRegion leafRegion = this.mapProcessor.getLeafMapRegion(renderedCaveLayer, this.mouseBlockPosX >> 9, this.mouseBlockPosZ >> 9, false);
               MapTileChunk chunk = leafRegion == null ? null : leafRegion.getChunk(this.mouseBlockPosX >> 6 & 7, this.mouseBlockPosZ >> 6 & 7);
               int debugTextureX = this.mouseBlockPosX >> leveledRegionShift - 3 & 7;
               int debugTextureY = this.mouseBlockPosZ >> leveledRegionShift - 3 & 7;
               RegionTexture tex = reg != null && reg.hasTextures() ? reg.getTexture(debugTextureX, debugTextureY) : null;
               boolean debugConfig = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.DEBUG);
               if (debugConfig) {
                  if (reg != null) {
                     List<String> debugLines = new ArrayList<>();
                     if (tex != null) {
                        tex.addDebugLines(debugLines);
                        MapTile mouseTile = chunk == null ? null : chunk.getTile(this.mouseBlockPosX >> 4 & 3, this.mouseBlockPosZ >> 4 & 3);
                        if (mouseTile != null) {
                           MapBlock block = mouseTile.getBlock(this.mouseBlockPosX & 15, this.mouseBlockPosZ & 15);
                           if (block != null) {
                              guiGraphics.drawCenteredString(mc.font, block.toRenderString(leafRegion.getBiomeRegistry()), this.width / 2, 22, -1);
                              if (block.getNumberOfOverlays() != 0) {
                                 for (int i = 0; i < block.getOverlays().size(); i++) {
                                    guiGraphics.drawCenteredString(mc.font, block.getOverlays().get(i).toRenderString(), this.width / 2, 32 + i * 10, -1);
                                 }
                              }
                           }
                        }
                     }

                     debugLines.add("");
                     debugLines.add(reg.toString());
                     reg.addDebugLines(debugLines, this.mapProcessor, debugTextureX, debugTextureY);

                     for (int i = 0; i < debugLines.size(); i++) {
                        guiGraphics.drawString(mc.font, debugLines.get(i), 5, 15 + 10 * i, -1);
                     }
                  }

                  DimensionType dimType = this.mapProcessor
                     .getMapWorld()
                     .getCurrentDimension()
                     .getDimensionType(this.mapProcessor.getWorldDimensionTypeRegistry());
                  ResourceLocation dimTypeId = this.mapProcessor.getMapWorld().getCurrentDimension().getDimensionTypeId();
                  guiGraphics.drawString(
                     mc.font,
                     "MultiWorld ID: " + this.mapProcessor.getMapWorld().getCurrentMultiworld() + " Dim Type: " + (dimType == null ? "unknown" : dimTypeId),
                     5,
                     265,
                     -1
                  );
                  LayeredRegionManager regions = this.mapProcessor.getMapWorld().getCurrentDimension().getLayeredMapRegions();
                  guiGraphics.drawString(
                     mc.font,
                     String.format(
                        "regions: %d loaded: %d processed: %d viewed: %d benchmarks %s",
                        regions.size(),
                        regions.loadedCount(),
                        this.mapProcessor.getProcessedCount(),
                        lastAmountOfRegionsViewed,
                        WorldMap.textureUploadBenchmark.getTotalsString()
                     ),
                     5,
                     275,
                     -1
                  );
                  guiGraphics.drawString(
                     mc.font,
                     String.format(
                        "toLoad: %d toSave: %d tile pool: %d overlays: %d toLoadBranchCache: %d buffers: %d",
                        this.mapProcessor.getMapSaveLoad().getSizeOfToLoad(),
                        this.mapProcessor.getMapSaveLoad().getToSave().size(),
                        this.mapProcessor.getTilePool().size(),
                        this.mapProcessor.getOverlayManager().getNumberOfUniqueOverlays(),
                        this.mapProcessor.getMapSaveLoad().getSizeOfToLoadBranchCache(),
                        WorldMap.textureDirectBufferPool.size()
                     ),
                     5,
                     285,
                     -1
                  );
                  long i = Runtime.getRuntime().maxMemory();
                  long j = Runtime.getRuntime().totalMemory();
                  long k = Runtime.getRuntime().freeMemory();
                  long l = j - k;
                  int debugFPS = ((IWorldMapMinecraftClient)mc).getXaeroWorldMap_fps();
                  guiGraphics.drawString(mc.font, String.format("FPS: %d", debugFPS), 5, 295, -1);
                  guiGraphics.drawString(mc.font, String.format("Mem: % 2d%% %03d/%03dMB", l * 100L / i, bytesToMb(l), bytesToMb(i)), 5, 315, -1);
                  guiGraphics.drawString(mc.font, String.format("Allocated: % 2d%% %03dMB", j * 100L / i, bytesToMb(j)), 5, 325, -1);
                  guiGraphics.drawString(
                     mc.font, String.format("Available VRAM: %dMB", this.mapProcessor.getMapLimiter().getAvailableVRAM() / 1024), 5, 335, -1
                  );
               }

               int pixelInsideTexX = mouseRegPixelX & 63;
               int pixelInsideTexZ = mouseRegPixelZ & 63;
               boolean hasAmbiguousHeight = false;
               int mouseBlockBottomY = 32767;
               int mouseBlockTopY = 32767;
               ResourceKey<Biome> pointedAtBiome = null;
               if (tex != null) {
                  mouseBlockBottomY = this.mouseBlockPosY = tex.getHeight(pixelInsideTexX, pixelInsideTexZ);
                  mouseBlockTopY = tex.getTopHeight(pixelInsideTexX, pixelInsideTexZ);
                  hasAmbiguousHeight = this.mouseBlockPosY != mouseBlockTopY;
                  pointedAtBiome = tex.getBiome(pixelInsideTexX, pixelInsideTexZ);
               }

               if (hasAmbiguousHeight) {
                  if (mouseBlockTopY != 32767) {
                     this.mouseBlockPosY = mouseBlockTopY;
                  } else if ((Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.DETECT_AMBIGUOUS_Y)) {
                     this.mouseBlockPosY = 32767;
                  }
               }

               matrixStack.popPose();
               if (primaryScaleFBO == null
                  || primaryScaleFBO.viewWidth != mc.getWindow().getWidth()
                  || primaryScaleFBO.viewHeight != mc.getWindow().getHeight()) {
                  primaryScaleFBO = new ImprovedFramebuffer(mc.getWindow().getWidth(), mc.getWindow().getHeight(), false);
               }

               if (primaryScaleFBO.frameBufferId == -1) {
                  matrixStack.popPose();
                  return;
               }

               primaryScaleFBO.bindAsMainTarget(false);
               GlStateManager._clearColor(0.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager._clear(16384, Minecraft.ON_OSX);
               matrixStack.scale((float)(1.0 / this.screenScale), (float)(1.0 / this.screenScale), 1.0F);
               matrixStack.translate(mc.getWindow().getWidth() / 2, mc.getWindow().getHeight() / 2, 0.0F);
               matrixStack.pushPose();
               int flooredCameraX = (int)Math.floor(this.cameraX);
               int flooredCameraZ = (int)Math.floor(this.cameraZ);
               double primaryOffsetX = 0.0;
               double primaryOffsetY = 0.0;
               double secondaryOffsetX;
               double secondaryOffsetY;
               if (fboScale < 1.0) {
                  double pixelInBlocks = 1.0 / fboScale;
                  int xInFullPixels = (int)Math.floor(this.cameraX / pixelInBlocks);
                  int zInFullPixels = (int)Math.floor(this.cameraZ / pixelInBlocks);
                  double fboOffsetX = xInFullPixels * pixelInBlocks;
                  double fboOffsetZ = zInFullPixels * pixelInBlocks;
                  flooredCameraX = (int)Math.floor(fboOffsetX);
                  flooredCameraZ = (int)Math.floor(fboOffsetZ);
                  primaryOffsetX = fboOffsetX - flooredCameraX;
                  primaryOffsetY = fboOffsetZ - flooredCameraZ;
                  secondaryOffsetX = (this.cameraX - fboOffsetX) * fboScale;
                  secondaryOffsetY = (this.cameraZ - fboOffsetZ) * fboScale;
               } else {
                  secondaryOffsetX = (this.cameraX - flooredCameraX) * fboScale;
                  secondaryOffsetY = (this.cameraZ - flooredCameraZ) * fboScale;
                  if (secondaryOffsetX >= 1.0) {
                     int offset = (int)secondaryOffsetX;
                     matrixStack.translate(-offset, 0.0F, 0.0F);
                     secondaryOffsetX -= offset;
                  }

                  if (secondaryOffsetY >= 1.0) {
                     int offset = (int)secondaryOffsetY;
                     matrixStack.translate(0.0F, offset, 0.0F);
                     secondaryOffsetY -= offset;
                  }
               }

               matrixStack.scale((float)fboScale, (float)(-fboScale), 1.0F);
               matrixStack.translate(-primaryOffsetX, -primaryOffsetY, 0.0);
               double leftBorder = this.cameraX - mc.getWindow().getWidth() / 2 / this.scale;
               double rightBorder = leftBorder + mc.getWindow().getWidth() / this.scale;
               double topBorder = this.cameraZ - mc.getWindow().getHeight() / 2 / this.scale;
               double bottomBorder = topBorder + mc.getWindow().getHeight() / this.scale;
               int minRegX = (int)Math.floor(leftBorder) >> leveledRegionShift;
               int maxRegX = (int)Math.floor(rightBorder) >> leveledRegionShift;
               int minRegZ = (int)Math.floor(topBorder) >> leveledRegionShift;
               int maxRegZ = (int)Math.floor(bottomBorder) >> leveledRegionShift;
               int blockToTextureConversion = 6 + textureLevel;
               int minTextureX = (int)Math.floor(leftBorder) >> blockToTextureConversion;
               int maxTextureX = (int)Math.floor(rightBorder) >> blockToTextureConversion;
               int minTextureZ = (int)Math.floor(topBorder) >> blockToTextureConversion;
               int maxTextureZ = (int)Math.floor(bottomBorder) >> blockToTextureConversion;
               int minLeafRegX = minTextureX << blockToTextureConversion >> 9;
               int maxLeafRegX = (maxTextureX + 1 << blockToTextureConversion) - 1 >> 9;
               int minLeafRegZ = minTextureZ << blockToTextureConversion >> 9;
               int maxLeafRegZ = (maxTextureZ + 1 << blockToTextureConversion) - 1 >> 9;
               lastAmountOfRegionsViewed = (maxRegX - minRegX + 1) * (maxRegZ - minRegZ + 1);
               if (this.mapProcessor.getMapLimiter().getMostRegionsAtATime() < lastAmountOfRegionsViewed) {
                  this.mapProcessor.getMapLimiter().setMostRegionsAtATime(lastAmountOfRegionsViewed);
               }

               this.regionBuffer.clear();
               this.branchRegionBuffer.clear();
               float brightness = this.mapProcessor.getBrightness();
               int globalRegionCacheHashCode = WorldMap.settings.getRegionCacheHashCode();
               int globalCaveStart = this.mapProcessor.getMapWorld().getCurrentDimension().getLayeredMapRegions().getLayer(renderedCaveLayer).getCaveStart();
               int globalCaveDepth = (Integer)configManager.getEffective(WorldMapProfiledConfigOptions.CAVE_MODE_DEPTH);
               boolean reloadEverything = (Boolean)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.RELOAD_VIEWED);
               int globalReloadVersion = (Integer)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.RELOAD_VIEWED_VERSION);
               int globalVersion = (Integer)primaryConfigManager.getEffective(WorldMapPrimaryClientConfigOptions.GLOBAL_VERSION);
               boolean prevWaitingForBranchCache = this.prevWaitingForBranchCache;
               this.waitingForBranchCache[0] = false;
               Matrix4f matrix = matrixStack.last().pose();
               BufferSource renderTypeBuffers = this.mapProcessor.getCvc().getRenderTypeBuffers();
               MultiTextureRenderTypeRendererProvider rendererProvider = this.mapProcessor.getMultiTextureRenderTypeRenderers();
               MultiTextureRenderTypeRenderer withLightRenderer = rendererProvider.getRenderer(
                  t -> RenderSystem.setShaderTexture(0, t), MultiTextureRenderTypeRendererProvider::defaultTextureBind, CustomRenderTypes.MAP
               );
               MultiTextureRenderTypeRenderer noLightRenderer = rendererProvider.getRenderer(
                  t -> RenderSystem.setShaderTexture(0, t), MultiTextureRenderTypeRendererProvider::defaultTextureBind, CustomRenderTypes.MAP
               );
               VertexConsumer overlayBuffer = renderTypeBuffers.getBuffer(CustomRenderTypes.MAP_COLOR_OVERLAY);
               LeveledRegion.setComparison(
                  this.mouseBlockPosX >> leveledRegionShift,
                  this.mouseBlockPosZ >> leveledRegionShift,
                  textureLevel,
                  this.mouseBlockPosX >> 9,
                  this.mouseBlockPosZ >> 9
               );
               LeveledRegion<?> lastUpdatedRootLeveledRegion = null;
               boolean cacheOnlyMode = this.mapProcessor.getMapWorld().isCacheOnlyMode();
               boolean frameRenderedRootTextures = false;
               boolean loadingLeaves = false;

               for (int leveledRegX = minRegX; leveledRegX <= maxRegX; leveledRegX++) {
                  for (int leveledRegZ = minRegZ; leveledRegZ <= maxRegZ; leveledRegZ++) {
                     int leveledSideInRegions = 1 << textureLevel;
                     int leveledSideInBlocks = leveledSideInRegions * 512;
                     int leafRegionMinX = leveledRegX * leveledSideInRegions;
                     int leafRegionMinZ = leveledRegZ * leveledSideInRegions;
                     LeveledRegion<?> leveledRegion = null;

                     for (int leafX = 0; leafX < leveledSideInRegions; leafX += 1) {
                        for (int leafZ = 0; leafZ < leveledSideInRegions; leafZ += 1) {
                           int regX = leafRegionMinX + leafX;
                           if (regX >= minLeafRegX && regX <= maxLeafRegX) {
                              int regZ = leafRegionMinZ + leafZ;
                              if (regZ >= minLeafRegZ && regZ <= maxLeafRegZ) {
                                 MapRegion region = this.mapProcessor.getLeafMapRegion(renderedCaveLayer, regX, regZ, false);
                                 if (region == null) {
                                    region = this.mapProcessor
                                       .getLeafMapRegion(renderedCaveLayer, regX, regZ, this.mapProcessor.regionExists(renderedCaveLayer, regX, regZ));
                                 }

                                 if (region != null) {
                                    if (leveledRegion == null) {
                                       leveledRegion = this.mapProcessor.getLeveledRegion(renderedCaveLayer, leveledRegX, leveledRegZ, textureLevel);
                                    }

                                    if (!prevWaitingForBranchCache) {
                                       synchronized (region) {
                                          if (textureLevel != 0
                                             && region.getLoadState() == 0
                                             && region.loadingNeededForBranchLevel != 0
                                             && region.loadingNeededForBranchLevel != textureLevel) {
                                             region.loadingNeededForBranchLevel = 0;
                                             region.getParent().setShouldCheckForUpdatesRecursive(true);
                                          }

                                          if (region.canRequestReload_unsynced()
                                             && (
                                                !cacheOnlyMode
                                                      && (
                                                         reloadEverything && region.getReloadVersion() != globalReloadVersion
                                                            || region.getCacheHashCode() != globalRegionCacheHashCode
                                                            || region.caveStartOutdated(globalCaveStart, globalCaveDepth)
                                                            || region.getVersion() != globalVersion
                                                            || region.getLoadState() != 2 && region.shouldCache()
                                                      )
                                                   || region.getLoadState() == 0
                                                      && (!region.isMetaLoaded() || textureLevel == 0 || region.loadingNeededForBranchLevel == textureLevel)
                                                   || (region.isMetaLoaded() || region.getLoadState() != 0 || !region.hasHadTerrain())
                                                      && region.getHighlightsHash()
                                                         != region.getDim().getHighlightHandler().getRegionHash(region.getRegionX(), region.getRegionZ())
                                             )) {
                                             loadingLeaves = true;
                                             region.calculateSortingDistance();
                                             Misc.addToListOfSmallest(10, this.regionBuffer, region);
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }

                     if (leveledRegion != null) {
                        LeveledRegion<?> rootLeveledRegion = leveledRegion.getRootRegion();
                        if (rootLeveledRegion == leveledRegion) {
                           rootLeveledRegion = null;
                        }

                        if (rootLeveledRegion != null && !rootLeveledRegion.isLoaded()) {
                           if (!rootLeveledRegion.recacheHasBeenRequested() && !rootLeveledRegion.reloadHasBeenRequested()) {
                              rootLeveledRegion.calculateSortingDistance();
                              Misc.addToListOfSmallest(10, this.branchRegionBuffer, (BranchLeveledRegion)rootLeveledRegion);
                           }

                           this.waitingForBranchCache[0] = true;
                           rootLeveledRegion = null;
                        }

                        if (!this.mapProcessor.isUploadingPaused() && !WorldMap.pauseRequests) {
                           if (leveledRegion instanceof BranchLeveledRegion branchRegion) {
                              branchRegion.checkForUpdates(
                                 this.mapProcessor,
                                 prevWaitingForBranchCache,
                                 this.waitingForBranchCache,
                                 this.branchRegionBuffer,
                                 textureLevel,
                                 minLeafRegX,
                                 minLeafRegZ,
                                 maxLeafRegX,
                                 maxLeafRegZ
                              );
                           }

                           if ((textureLevel != 0 && !prevWaitingForBranchCache || textureLevel == 0 && !this.prevLoadingLeaves)
                              && this.lastFrameRenderedRootTextures
                              && rootLeveledRegion != null
                              && rootLeveledRegion != lastUpdatedRootLeveledRegion) {
                              BranchLeveledRegion branchRegion = (BranchLeveledRegion)rootLeveledRegion;
                              branchRegion.checkForUpdates(
                                 this.mapProcessor,
                                 prevWaitingForBranchCache,
                                 this.waitingForBranchCache,
                                 this.branchRegionBuffer,
                                 textureLevel,
                                 minLeafRegX,
                                 minLeafRegZ,
                                 maxLeafRegX,
                                 maxLeafRegZ
                              );
                              lastUpdatedRootLeveledRegion = rootLeveledRegion;
                           }

                           this.mapProcessor.getMapWorld().getCurrentDimension().getLayeredMapRegions().bumpLoadedRegion(leveledRegion);
                           if (rootLeveledRegion != null) {
                              this.mapProcessor.getMapWorld().getCurrentDimension().getLayeredMapRegions().bumpLoadedRegion(rootLeveledRegion);
                           }
                        } else {
                           this.waitingForBranchCache[0] = prevWaitingForBranchCache;
                        }

                        int minXBlocks = leveledRegX * leveledSideInBlocks;
                        int minZBlocks = leveledRegZ * leveledSideInBlocks;
                        int textureSize = 64 * leveledSideInRegions;
                        int firstTextureX = leveledRegX << 3;
                        int firstTextureZ = leveledRegZ << 3;
                        int levelDiff = 3 - textureLevel;
                        int rootSize = 1 << levelDiff;
                        int maxInsideCoord = rootSize - 1;
                        int firstRootTextureX = firstTextureX >> levelDiff & 7;
                        int firstRootTextureZ = firstTextureZ >> levelDiff & 7;
                        int firstInsideTextureX = firstTextureX & maxInsideCoord;
                        int firstInsideTextureZ = firstTextureZ & maxInsideCoord;
                        boolean hasTextures = leveledRegion.hasTextures();
                        boolean rootHasTextures = rootLeveledRegion != null && rootLeveledRegion.hasTextures();
                        if (hasTextures || rootHasTextures) {
                           for (int o = 0; o < 8; o += 1) {
                              int textureX = minXBlocks + o * textureSize;
                              if (!(textureX > rightBorder) && !(textureX + textureSize < leftBorder)) {
                                 for (int p = 0; p < 8; p += 1) {
                                    int textureZ = minZBlocks + p * textureSize;
                                    if (!(textureZ > bottomBorder) && !(textureZ + textureSize < topBorder)) {
                                       RegionTexture<?> regionTexture = hasTextures ? leveledRegion.getTexture(o, p) : null;
                                       if (regionTexture != null && regionTexture.getGlColorTexture() != -1) {
                                          int texture = regionTexture.getGlColorTexture();
                                          if (texture != -1) {
                                             boolean hasLight = regionTexture.getTextureHasLight();
                                             renderTexturedModalRectWithLighting3(
                                                matrix,
                                                textureX - flooredCameraX,
                                                textureZ - flooredCameraZ,
                                                textureSize,
                                                textureSize,
                                                texture,
                                                hasLight,
                                                hasLight ? withLightRenderer : noLightRenderer
                                             );
                                          }
                                       } else if (rootHasTextures) {
                                          int insideX = firstInsideTextureX + o;
                                          int insideZ = firstInsideTextureZ + p;
                                          int rootTextureX = firstRootTextureX + (insideX >> levelDiff);
                                          int rootTextureZ = firstRootTextureZ + (insideZ >> levelDiff);
                                          regionTexture = rootLeveledRegion.getTexture(rootTextureX, rootTextureZ);
                                          if (regionTexture != null) {
                                             int texture = regionTexture.getGlColorTexture();
                                             if (texture != -1) {
                                                frameRenderedRootTextures = true;
                                                int insideTextureX = insideX & maxInsideCoord;
                                                int insideTextureZ = insideZ & maxInsideCoord;
                                                float textureX1 = (float)insideTextureX / rootSize;
                                                float textureX2 = (float)(insideTextureX + 1) / rootSize;
                                                float textureY1 = (float)insideTextureZ / rootSize;
                                                float textureY2 = (float)(insideTextureZ + 1) / rootSize;
                                                boolean hasLight = regionTexture.getTextureHasLight();
                                                renderTexturedModalSubRectWithLighting(
                                                   matrix,
                                                   textureX - flooredCameraX,
                                                   textureZ - flooredCameraZ,
                                                   textureX1,
                                                   textureY1,
                                                   textureX2,
                                                   textureY2,
                                                   textureSize,
                                                   textureSize,
                                                   texture,
                                                   hasLight,
                                                   hasLight ? withLightRenderer : noLightRenderer
                                                );
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }

                        if (leveledRegion.loadingAnimation()) {
                           matrixStack.pushPose();
                           matrixStack.translate(
                              leveledSideInBlocks * (leveledRegX + 0.5) - flooredCameraX, leveledSideInBlocks * (leveledRegZ + 0.5) - flooredCameraZ, 0.0
                           );
                           float loadingAnimationPassed = (float)(System.currentTimeMillis() - this.loadingAnimationStart);
                           if (loadingAnimationPassed > 0.0F) {
                              int period = 2000;
                              int numbersOfActors = 3;
                              float loadingAnimation = loadingAnimationPassed % period / period * 360.0F;
                              float step = 360.0F / numbersOfActors;
                              OptimizedMath.rotatePose(matrixStack, loadingAnimation, OptimizedMath.ZP);
                              int numberOfVisibleActors = 1 + (int)loadingAnimationPassed % (3 * period) / period;
                              matrixStack.scale(leveledSideInRegions, leveledSideInRegions, 1.0F);

                              for (int i = 0; i < numberOfVisibleActors; i += 1) {
                                 OptimizedMath.rotatePose(matrixStack, step, OptimizedMath.ZP);
                                 MapRenderHelper.fillIntoExistingBuffer(matrixStack.last().pose(), overlayBuffer, 16, -8, 32, 8, 1.0F, 1.0F, 1.0F, 1.0F);
                              }
                           }

                           matrixStack.popPose();
                        }

                        if (debugConfig && leveledRegion instanceof MapRegion region) {
                           matrixStack.pushPose();
                           matrixStack.translate(512 * region.getRegionX() + 32 - flooredCameraX, 512 * region.getRegionZ() + 32 - flooredCameraZ, 0.0F);
                           matrixStack.scale(10.0F, 10.0F, 1.0F);
                           Misc.drawNormalText(matrixStack, region.getLoadState() + "", 0.0F, 0.0F, -1, true, renderTypeBuffers);
                           matrixStack.popPose();
                        }

                        if (debugConfig && textureLevel > 0) {
                           for (int leafX = 0; leafX < leveledSideInRegions; leafX += 1) {
                              for (int leafZ = 0; leafZ < leveledSideInRegions; leafZ += 1) {
                                 int regX = leafRegionMinX + leafX;
                                 int regZ = leafRegionMinZ + leafZ;
                                 MapRegion region = this.mapProcessor.getLeafMapRegion(renderedCaveLayer, regX, regZ, false);
                                 if (region != null) {
                                    boolean currentlyLoading = this.mapProcessor.getMapSaveLoad().getNextToLoadByViewing() == region;
                                    if (currentlyLoading || region.isLoaded() || region.isMetaLoaded()) {
                                       matrixStack.pushPose();
                                       matrixStack.translate(512 * region.getRegionX() - flooredCameraX, 512 * region.getRegionZ() - flooredCameraZ, 0.0F);
                                       float r = 0.0F;
                                       float g = 0.0F;
                                       float b = 0.0F;
                                       float a = 0.1569F;
                                       if (currentlyLoading) {
                                          b = 1.0F;
                                          r = 1.0F;
                                       } else if (region.isLoaded()) {
                                          g = 1.0F;
                                       } else {
                                          g = 1.0F;
                                          r = 1.0F;
                                       }

                                       MapRenderHelper.fillIntoExistingBuffer(matrixStack.last().pose(), overlayBuffer, 0, 0, 512, 512, r, g, b, a);
                                       matrixStack.popPose();
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               this.lastFrameRenderedRootTextures = frameRenderedRootTextures;
               LibShaders.WORLD_MAP.setBrightness(brightness);
               LibShaders.WORLD_MAP.setWithLight(true);
               rendererProvider.draw(withLightRenderer);
               LibShaders.WORLD_MAP.setWithLight(false);
               rendererProvider.draw(noLightRenderer);
               LeveledRegion<?> nextToLoad = this.mapProcessor.getMapSaveLoad().getNextToLoadByViewing();
               boolean shouldRequest = false;
               if (nextToLoad != null) {
                  shouldRequest = nextToLoad.shouldAllowAnotherRegionToLoad();
               } else {
                  shouldRequest = true;
               }

               shouldRequest = shouldRequest && this.mapProcessor.getAffectingLoadingFrequencyCount() < 16;
               if (shouldRequest && !WorldMap.pauseRequests) {
                  int toRequest = 2;
                  int counter = 0;

                  for (int i = 0; i < this.branchRegionBuffer.size() && counter < toRequest; i += 1) {
                     BranchLeveledRegion region = this.branchRegionBuffer.get(i);
                     if (!region.reloadHasBeenRequested() && !region.recacheHasBeenRequested() && !region.isLoaded()) {
                        region.setReloadHasBeenRequested(true, "Gui");
                        this.mapProcessor.getMapSaveLoad().requestBranchCache(region, "Gui");
                        if (counter == 0) {
                           this.mapProcessor.getMapSaveLoad().setNextToLoadByViewing(region);
                        }

                        counter += 1;
                     }
                  }

                  int var226 = 1;
                  int var229 = 0;
                  if (!prevWaitingForBranchCache) {
                     for (int i = 0; i < this.regionBuffer.size() && var229 < var226; i += 1) {
                        MapRegion region = this.regionBuffer.get(i);
                        if (region != nextToLoad || this.regionBuffer.size() <= 1) {
                           synchronized (region) {
                              if (region.canRequestReload_unsynced()) {
                                 if (region.getLoadState() == 2) {
                                    region.requestRefresh(this.mapProcessor);
                                 } else {
                                    this.mapProcessor.getMapSaveLoad().requestLoad(region, "Gui");
                                 }

                                 if (var229 == 0) {
                                    this.mapProcessor.getMapSaveLoad().setNextToLoadByViewing(region);
                                 }

                                 var229 += 1;
                                 if (region.getLoadState() == 4) {
                                    break;
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               this.prevWaitingForBranchCache = this.waitingForBranchCache[0];
               this.prevLoadingLeaves = loadingLeaves;
               int highlightChunkX = this.mouseBlockPosX >> 4;
               int highlightChunkZ = this.mouseBlockPosZ >> 4;
               int chunkHighlightLeftX = highlightChunkX << 4;
               int chunkHighlightRightX = highlightChunkX + 1 << 4;
               int chunkHighlightTopZ = highlightChunkZ << 4;
               int chunkHighlightBottomZ = highlightChunkZ + 1 << 4;
               MapRenderHelper.renderDynamicHighlight(
                  matrixStack,
                  overlayBuffer,
                  flooredCameraX,
                  flooredCameraZ,
                  chunkHighlightLeftX,
                  chunkHighlightRightX,
                  chunkHighlightTopZ,
                  chunkHighlightBottomZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.2F,
                  1.0F,
                  1.0F,
                  1.0F,
                  0.1569F
               );
               MapTileSelection mapTileSelectionToRender = this.mapTileSelection;
               if (mapTileSelectionToRender == null && this.minecraft.screen instanceof ExportScreen) {
                  mapTileSelectionToRender = ((ExportScreen)this.minecraft.screen).getSelection();
               }

               if (mapTileSelectionToRender != null) {
                  MapRenderHelper.renderDynamicHighlight(
                     matrixStack,
                     overlayBuffer,
                     flooredCameraX,
                     flooredCameraZ,
                     mapTileSelectionToRender.getLeft() << 4,
                     mapTileSelectionToRender.getRight() + 1 << 4,
                     mapTileSelectionToRender.getTop() << 4,
                     mapTileSelectionToRender.getBottom() + 1 << 4,
                     0.0F,
                     0.0F,
                     0.0F,
                     0.2F,
                     1.0F,
                     0.5F,
                     0.5F,
                     0.4F
                  );
                  if (SupportMods.pac() && !this.mapProcessor.getMapWorld().isUsingCustomDimension()) {
                     int playerX = (int)Math.floor(this.player.getX());
                     int playerZ = (int)Math.floor(this.player.getZ());
                     int playerChunkX = playerX >> 4;
                     int playerChunkZ = playerZ >> 4;
                     int claimDistance = SupportMods.xaeroPac.getClaimDistance();
                     int claimableAreaLeft = playerChunkX - claimDistance;
                     int claimableAreaTop = playerChunkZ - claimDistance;
                     int claimableAreaRight = playerChunkX + claimDistance;
                     int claimableAreaBottom = playerChunkZ + claimDistance;
                     int claimableAreaHighlightLeftX = claimableAreaLeft << 4;
                     int claimableAreaHighlightRightX = claimableAreaRight + 1 << 4;
                     int claimableAreaHighlightTopZ = claimableAreaTop << 4;
                     int claimableAreaHighlightBottomZ = claimableAreaBottom + 1 << 4;
                     MapRenderHelper.renderDynamicHighlight(
                        matrixStack,
                        overlayBuffer,
                        flooredCameraX,
                        flooredCameraZ,
                        claimableAreaHighlightLeftX,
                        claimableAreaHighlightRightX,
                        claimableAreaHighlightTopZ,
                        claimableAreaHighlightBottomZ,
                        0.0F,
                        0.0F,
                        1.0F,
                        0.3F,
                        0.0F,
                        0.0F,
                        1.0F,
                        0.15F
                     );
                  }
               }

               RenderSystem.disableCull();
               renderTypeBuffers.endBatch();
               RenderSystem.enableCull();
               primaryScaleFBO.unbindWrite();
               primaryScaleFBO.bindDefaultFramebuffer(mc);
               matrixStack.popPose();
               matrixStack.pushPose();
               matrixStack.scale((float)secondaryScale, (float)secondaryScale, 1.0F);
               primaryScaleFBO.bindRead();
               GL11.glTexParameteri(3553, 10240, 9729);
               GL11.glTexParameteri(3553, 10241, 9729);
               RenderSystem.depthMask(false);
               VertexConsumer colorBackgroundConsumer = renderTypeBuffers.getBuffer(CustomRenderTypes.MAP_COLOR_FILLER);
               int lineX = -mc.getWindow().getWidth() / 2;
               int lineY = mc.getWindow().getHeight() / 2 - 5;
               int lineW = mc.getWindow().getWidth();
               int lineH = 6;
               MapRenderHelper.fillIntoExistingBuffer(
                  matrixStack.last().pose(), colorBackgroundConsumer, lineX, lineY, lineX + lineW, lineY + lineH, 0.0F, 0.0F, 0.0F, 1.0F
               );
               lineX = mc.getWindow().getWidth() / 2 - 5;
               lineY = -mc.getWindow().getHeight() / 2;
               int var257 = 6;
               lineH = mc.getWindow().getHeight();
               MapRenderHelper.fillIntoExistingBuffer(
                  matrixStack.last().pose(), colorBackgroundConsumer, lineX, lineY, lineX + var257, lineY + lineH, 0.0F, 0.0F, 0.0F, 1.0F
               );
               renderTypeBuffers.endBatch();
               RenderType mainFrameRenderType = CustomRenderTypes.GUI_BILINEAR;
               if (SupportMods.vivecraft) {
                  mainFrameRenderType = CustomRenderTypes.MAP_FRAME_TEXTURE_OVER_TRANSPARENT;
               }

               MultiTextureRenderTypeRenderer mainFrameRenderer = rendererProvider.getRenderer(
                  t -> RenderSystem.setShaderTexture(0, t), MultiTextureRenderTypeRendererProvider::defaultTextureBind, mainFrameRenderType
               );
               VertexConsumer mainFrameVertexConsumer = mainFrameRenderer.begin(primaryScaleFBO.getFramebufferTexture());
               renderTexturedModalRect(
                  matrixStack.last().pose(),
                  mainFrameVertexConsumer,
                  -mc.getWindow().getWidth() / 2 - (float)secondaryOffsetX,
                  -mc.getWindow().getHeight() / 2 - (float)secondaryOffsetY,
                  0,
                  0,
                  primaryScaleFBO.viewWidth,
                  primaryScaleFBO.viewHeight,
                  primaryScaleFBO.viewWidth,
                  primaryScaleFBO.viewHeight,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F
               );
               rendererProvider.draw(mainFrameRenderer);
               RenderSystem.depthMask(true);
               matrixStack.popPose();
               matrixStack.scale((float)this.scale, (float)this.scale, 1.0F);
               double screenSizeBasedScale = scaleMultiplier;
               WorldMap.trackedPlayerRenderer.update(mc);

               try {
                  this.viewed = WorldMap.mapElementRenderHandler
                     .render(
                        this,
                        guiGraphics,
                        renderTypeBuffers,
                        rendererProvider,
                        this.cameraX,
                        this.cameraZ,
                        mc.getWindow().getWidth(),
                        mc.getWindow().getHeight(),
                        screenSizeBasedScale,
                        this.scale,
                        playerDimDiv,
                        mousePosX,
                        mousePosZ,
                        brightness,
                        renderedCaveLayer != Integer.MAX_VALUE,
                        this.viewed,
                        mc,
                        partialTicks
                     );
               } catch (Throwable t) {
                  WorldMap.LOGGER.error("error rendering map elements", t);
                  throw t;
               }

               this.viewedInList = false;
               matrixStack.pushPose();
               matrixStack.translate(0.0F, 0.0F, 970.0F);
               VertexConsumer regularUIObjectConsumer = renderTypeBuffers.getBuffer(CustomRenderTypes.GUI_BILINEAR);
               if ((Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.FOOTSTEPS)) {
                  ArrayList<Double[]> footprints = this.mapProcessor.getFootprints();
                  synchronized (footprints) {
                     for (int i = 0; i < footprints.size(); i += 1) {
                        Double[] coords = footprints.get(i);
                        this.setColourBuffer(1.0F, 0.1F, 0.1F, 1.0F);
                        this.drawDotOnMap(
                           matrixStack,
                           regularUIObjectConsumer,
                           coords[0] / playerDimDiv - this.cameraX,
                           coords[1] / playerDimDiv - this.cameraZ,
                           0.0F,
                           1.0 / this.scale
                        );
                     }
                  }
               }

               if ((Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.ARROW)) {
                  boolean toTheLeft = scaledPlayerX < leftBorder;
                  boolean toTheRight = scaledPlayerX > rightBorder;
                  boolean down = scaledPlayerZ > bottomBorder;
                  boolean up = scaledPlayerZ < topBorder;
                  float configuredR = 1.0F;
                  float configuredG = 1.0F;
                  float configuredB = 1.0F;
                  int effectiveArrowColorIndex = (Integer)configManager.getEffective(WorldMapProfiledConfigOptions.ARROW_COLOR);
                  if (effectiveArrowColorIndex == -2 && !SupportMods.minimap()) {
                     effectiveArrowColorIndex = 0;
                  }

                  if (effectiveArrowColorIndex == -2 && SupportMods.xaeroMinimap.getArrowColorIndex() == -1) {
                     effectiveArrowColorIndex = -1;
                  }

                  if (effectiveArrowColorIndex == -1) {
                     int rgb = Misc.getTeamColour((Entity)(mc.player == null ? mc.getCameraEntity() : mc.player));
                     if (rgb == -1) {
                        effectiveArrowColorIndex = 0;
                     } else {
                        configuredR = (rgb >> 16 & 0xFF) / 255.0F;
                        configuredG = (rgb >> 8 & 0xFF) / 255.0F;
                        configuredB = (rgb & 0xFF) / 255.0F;
                     }
                  } else if (effectiveArrowColorIndex == -2) {
                     float[] c = SupportMods.xaeroMinimap.getArrowColor();
                     if (c == null) {
                        effectiveArrowColorIndex = 0;
                     } else {
                        configuredR = c[0];
                        configuredG = c[1];
                        configuredB = c[2];
                     }
                  }

                  if (effectiveArrowColorIndex >= 0) {
                     float[] c = WorldMapConfigConstants.ARROW_COLORS[effectiveArrowColorIndex];
                     configuredR = c[0];
                     configuredG = c[1];
                     configuredB = c[2];
                  }

                  if (!toTheLeft && !toTheRight && !up && !down) {
                     this.setColourBuffer(0.0F, 0.0F, 0.0F, 0.9F);
                     this.drawArrowOnMap(
                        matrixStack,
                        regularUIObjectConsumer,
                        scaledPlayerX - this.cameraX,
                        scaledPlayerZ + 2.0 * screenSizeBasedScale / this.scale - this.cameraZ,
                        this.player.getYRot(),
                        screenSizeBasedScale / this.scale
                     );
                     this.setColourBuffer(configuredR, configuredG, configuredB, 1.0F);
                     this.drawArrowOnMap(
                        matrixStack,
                        regularUIObjectConsumer,
                        scaledPlayerX - this.cameraX,
                        scaledPlayerZ - this.cameraZ,
                        this.player.getYRot(),
                        screenSizeBasedScale / this.scale
                     );
                  } else {
                     double arrowX = scaledPlayerX;
                     double arrowZ = scaledPlayerZ;
                     float a = 0.0F;
                     if (toTheLeft) {
                        a = up ? 1.5F : (down ? 0.5F : 1.0F);
                        arrowX = leftBorder;
                     } else if (toTheRight) {
                        a = up ? 2.5F : (down ? 3.5F : 3.0F);
                        arrowX = rightBorder;
                     }

                     if (down) {
                        arrowZ = bottomBorder;
                     } else if (up) {
                        if (a == 0.0F) {
                           a = 2.0F;
                        }

                        arrowZ = topBorder;
                     }

                     this.setColourBuffer(0.0F, 0.0F, 0.0F, 0.9F);
                     this.drawFarArrowOnMap(
                        matrixStack,
                        regularUIObjectConsumer,
                        arrowX - this.cameraX,
                        arrowZ + 2.0 * screenSizeBasedScale / this.scale - this.cameraZ,
                        a,
                        screenSizeBasedScale / this.scale
                     );
                     this.setColourBuffer(configuredR, configuredG, configuredB, 1.0F);
                     this.drawFarArrowOnMap(
                        matrixStack, regularUIObjectConsumer, arrowX - this.cameraX, arrowZ - this.cameraZ, a, screenSizeBasedScale / this.scale
                     );
                  }
               }

               this.minecraft.getTextureManager().bindForSetup(WorldMap.guiTextures);
               GL11.glTexParameteri(3553, 10240, 9729);
               GL11.glTexParameteri(3553, 10241, 9729);
               renderTypeBuffers.endBatch();
               this.minecraft.getTextureManager().bindForSetup(WorldMap.guiTextures);
               GL11.glTexParameteri(3553, 10240, 9728);
               GL11.glTexParameteri(3553, 10241, 9728);
               matrixStack.popPose();
               matrixStack.popPose();
               VertexConsumer backgroundVertexBuffer = renderTypeBuffers.getBuffer(CustomRenderTypes.MAP_COLOR_OVERLAY);
               int cursorDisplayOffset = 0;
               if ((Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.COORDINATES)) {
                  String coordsString = "X: " + this.mouseBlockPosX;
                  if (mouseBlockBottomY != 32767) {
                     coordsString = coordsString + " Y: " + mouseBlockBottomY;
                  }

                  if (hasAmbiguousHeight && mouseBlockTopY != 32767) {
                     coordsString = coordsString + " (" + mouseBlockTopY + ")";
                  }

                  coordsString = coordsString + " Z: " + this.mouseBlockPosZ;
                  MapRenderHelper.drawCenteredStringWithBackground(
                     guiGraphics, this.font, coordsString, this.width / 2, 2 + cursorDisplayOffset, -1, 0.0F, 0.0F, 0.0F, 0.4F, backgroundVertexBuffer
                  );
                  cursorDisplayOffset += 10;
               }

               if ((Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.DISPLAY_HOVERED_BIOME) && pointedAtBiome != null) {
                  ResourceLocation biomeRL = pointedAtBiome.location();
                  String biomeText = biomeRL == null
                     ? I18n.get("gui.xaero_wm_unknown_biome", new Object[0])
                     : I18n.get("biome." + biomeRL.getNamespace() + "." + biomeRL.getPath(), new Object[0]);
                  MapRenderHelper.drawCenteredStringWithBackground(
                     guiGraphics, this.font, biomeText, this.width / 2, 2 + cursorDisplayOffset, -1, 0.0F, 0.0F, 0.0F, 0.4F, backgroundVertexBuffer
                  );
               }

               int subtleTooltipOffset = 12;
               if ((Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.DISPLAY_ZOOM)) {
                  String zoomString = Math.round(destScale * 1000.0) / 1000.0 + "x";
                  MapRenderHelper.drawCenteredStringWithBackground(
                     guiGraphics, mc.font, zoomString, this.width / 2, this.height - subtleTooltipOffset, -1, 0.0F, 0.0F, 0.0F, 0.4F, backgroundVertexBuffer
                  );
               }

               if (this.mapProcessor.getMapWorld().getCurrentDimension().getFullReloader() != null) {
                  subtleTooltipOffset += 12;
                  MapRenderHelper.drawCenteredStringWithBackground(
                     guiGraphics,
                     mc.font,
                     FULL_RELOAD_IN_PROGRESS,
                     this.width / 2,
                     this.height - subtleTooltipOffset,
                     -1,
                     0.0F,
                     0.0F,
                     0.0F,
                     0.4F,
                     backgroundVertexBuffer
                  );
               }

               if (this.mapProcessor.getMapWorld().isUsingUnknownDimensionType()) {
                  int var288 = subtleTooltipOffset + 24;
                  MapRenderHelper.drawCenteredStringWithBackground(
                     guiGraphics, mc.font, UNKNOWN_DIMENSION_TYPE2, this.width / 2, this.height - var288, -1, 0.0F, 0.0F, 0.0F, 0.4F, backgroundVertexBuffer
                  );
                  subtleTooltipOffset = var288 + 12;
                  MapRenderHelper.drawCenteredStringWithBackground(
                     guiGraphics,
                     mc.font,
                     UNKNOWN_DIMENSION_TYPE1,
                     this.width / 2,
                     this.height - subtleTooltipOffset,
                     -1,
                     0.0F,
                     0.0F,
                     0.0F,
                     0.4F,
                     backgroundVertexBuffer
                  );
               }

               if ((Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.DISPLAY_CAVE_MODE_START)) {
                  subtleTooltipOffset += 12;
                  if (globalCaveStart != Integer.MAX_VALUE && globalCaveStart != Integer.MIN_VALUE) {
                     String caveModeStartString = I18n.get("gui.xaero_wm_cave_mode_start_display", new Object[]{globalCaveStart});
                     MapRenderHelper.drawCenteredStringWithBackground(
                        guiGraphics,
                        mc.font,
                        caveModeStartString,
                        this.width / 2,
                        this.height - subtleTooltipOffset,
                        -1,
                        0.0F,
                        0.0F,
                        0.0F,
                        0.4F,
                        backgroundVertexBuffer
                     );
                  }
               }

               if (SupportMods.minimap()) {
                  String subWorldNameToRender = SupportMods.xaeroMinimap.getSubWorldNameToRender();
                  if (subWorldNameToRender != null) {
                     subtleTooltipOffset += 24;
                     MapRenderHelper.drawCenteredStringWithBackground(
                        guiGraphics,
                        mc.font,
                        subWorldNameToRender,
                        this.width / 2,
                        this.height - subtleTooltipOffset,
                        -1,
                        0.0F,
                        0.0F,
                        0.0F,
                        0.4F,
                        backgroundVertexBuffer
                     );
                  }
               }

               discoveredForHighlights = mouseBlockBottomY != 32767;
               Component subtleHighlightTooltip = this.mapProcessor
                  .getMapWorld()
                  .getCurrentDimension()
                  .getHighlightHandler()
                  .getBlockHighlightSubtleTooltip(this.mouseBlockPosX, this.mouseBlockPosZ, discoveredForHighlights);
               if (subtleHighlightTooltip != null) {
                  subtleTooltipOffset += 12;
                  MapRenderHelper.drawCenteredStringWithBackground(
                     guiGraphics,
                     mc.font,
                     subtleHighlightTooltip,
                     this.width / 2,
                     this.height - subtleTooltipOffset,
                     -1,
                     0.0F,
                     0.0F,
                     0.0F,
                     0.4F,
                     backgroundVertexBuffer
                  );
               }

               renderTypeBuffers.endBatch();
               this.overWaypointsMenu = false;
               this.overPlayersMenu = false;
               boolean renderingMenus = this.waypointMenu || this.playersMenu;
               if (renderingMenus) {
                  matrixStack.pushPose();
                  matrixStack.translate(0.0F, 0.0F, 972.0F);
               }

               if (this.waypointMenu) {
                  if (SupportMods.xaeroMinimap.getWaypointsSorted() != null) {
                     HoveredMapElementHolder<?, ?> hovered = SupportMods.xaeroMinimap
                        .renderWaypointsMenu(
                           guiGraphics,
                           this,
                           this.scale,
                           this.width,
                           this.height,
                           scaledMouseX,
                           scaledMouseY,
                           this.leftMouseButton.isDown,
                           this.leftMouseButton.clicked,
                           this.viewed,
                           mc
                        );
                     if (hovered != null) {
                        this.overWaypointsMenu = true;
                        if (hovered.getElement() instanceof Waypoint) {
                           this.viewed = hovered;
                           this.viewedInList = true;
                           if (this.leftMouseButton.clicked) {
                              this.cameraDestination = new int[]{
                                 (int)((Waypoint)this.viewed.getElement()).getRenderX(), (int)((Waypoint)this.viewed.getElement()).getRenderZ()
                              };
                              this.leftMouseButton.isDown = false;
                              boolean closeWaypointsWhenHopping = (Boolean)primaryConfigManager.getEffective(
                                 WorldMapPrimaryClientConfigOptions.CLOSE_WAYPOINTS_AFTER_HOP
                              );
                              if (closeWaypointsWhenHopping) {
                                 this.onWaypointsButton(this.waypointsButton);
                              }
                           }
                        }
                     }
                  }
               } else if (this.playersMenu) {
                  HoveredMapElementHolder<?, ?> hovered = WorldMap.trackedPlayerMenuRenderer
                     .renderMenu(
                        guiGraphics,
                        this,
                        this.scale,
                        this.width,
                        this.height,
                        scaledMouseX,
                        scaledMouseY,
                        this.leftMouseButton.isDown,
                        this.leftMouseButton.clicked,
                        this.viewed,
                        mc
                     );
                  if (hovered != null) {
                     this.overPlayersMenu = true;
                     if (hovered.getElement() instanceof PlayerTrackerMapElement
                        && WorldMap.trackedPlayerMenuRenderer.canJumpTo((PlayerTrackerMapElement<?>)hovered.getElement())) {
                        this.viewed = hovered;
                        this.viewedInList = true;
                        if (this.leftMouseButton.clicked) {
                           PlayerTrackerMapElement<?> clickedPlayer = (PlayerTrackerMapElement<?>)this.viewed.getElement();
                           MapDimension clickedPlayerDim = this.mapProcessor.getMapWorld().getDimension(clickedPlayer.getDimension());
                           DimensionType clickedPlayerDimType = MapDimension.getDimensionType(
                              clickedPlayerDim, clickedPlayer.getDimension(), this.mapProcessor.getWorldDimensionTypeRegistry()
                           );
                           double clickedPlayerDimDiv = this.mapProcessor
                              .getMapWorld()
                              .getCurrentDimension()
                              .calculateDimDiv(this.mapProcessor.getWorldDimensionTypeRegistry(), clickedPlayerDimType);
                           double jumpX = clickedPlayer.getX() / clickedPlayerDimDiv;
                           double jumpZ = clickedPlayer.getZ() / clickedPlayerDimDiv;
                           this.cameraDestination = new int[]{(int)jumpX, (int)jumpZ};
                           this.leftMouseButton.isDown = false;
                        }
                     }
                  }
               }

               if (renderingMenus) {
                  matrixStack.popPose();
               }

               if (SupportMods.minimap()) {
                  SupportMods.xaeroMinimap.drawSetChange(guiGraphics);
               }

               if (SupportMods.pac()) {
                  SupportMods.xaeroPac
                     .onMapRender(
                        this.minecraft,
                        matrixStack,
                        scaledMouseX,
                        scaledMouseY,
                        partialTicks,
                        this.mapProcessor.getWorld().dimension().location(),
                        highlightChunkX,
                        highlightChunkZ
                     );
               }
            } else if (!mapLoaded) {
               this.renderLoadingScreen(guiGraphics);
            } else if (isLocked) {
               this.renderMessageScreen(
                  guiGraphics, I18n.get("gui.xaero_current_map_locked1", new Object[0]), I18n.get("gui.xaero_current_map_locked2", new Object[0])
               );
            } else if (noWorldMapEffect) {
               this.renderMessageScreen(guiGraphics, I18n.get("gui.xaero_no_world_map_message", new Object[0]));
            } else if (!allowedBasedOnItem) {
               String configuredMapItemString = (String)configManager.getEffective(WorldMapProfiledConfigOptions.MAP_ITEM);
               this.renderMessageScreen(
                  guiGraphics,
                  I18n.get("gui.xaero_no_world_map_item_message", new Object[0]),
                  mapItem.getDescription().getString() + " (" + configuredMapItemString + ")"
               );
            }
         }

         this.mapSwitchingGui.renderText(guiGraphics, this.minecraft, scaledMouseX, scaledMouseY, this.width, this.height);
         guiGraphics.blit(WorldMap.guiTextures, this.width - 34, 2, 0, 37, 32, 32);
      }

      matrixStack.pushPose();
      matrixStack.translate(0.0F, 0.0F, 973.0F);
      super.render(guiGraphics, scaledMouseX, scaledMouseY, partialTicks);
      if (this.rightClickMenu != null) {
         this.rightClickMenu.render(guiGraphics, scaledMouseX, scaledMouseY, partialTicks);
      }

      matrixStack.translate(0.0F, 0.0F, 10.0F);
      if (mc.screen == this) {
         if (!this.renderTooltips(guiGraphics, scaledMouseX, scaledMouseY, partialTicks) && !this.leftMouseButton.isDown && !this.rightMouseButton.isDown) {
            if (this.viewed != null) {
               Tooltip hoveredTooltip = this.hoveredElementTooltipHelper(this.viewed, this.viewedInList);
               if (hoveredTooltip != null) {
                  hoveredTooltip.drawBox(guiGraphics, scaledMouseX, scaledMouseY, this.width, this.height);
               }
            } else {
               synchronized (this.mapProcessor.renderThreadPauseSync) {
                  if (!this.mapProcessor.isRenderingPaused()
                     && this.mapProcessor.getCurrentWorldId() != null
                     && this.mapProcessor.getMapSaveLoad().isRegionDetectionComplete()) {
                     Component bluntHighlightTooltip = this.mapProcessor
                        .getMapWorld()
                        .getCurrentDimension()
                        .getHighlightHandler()
                        .getBlockHighlightBluntTooltip(this.mouseBlockPosX, this.mouseBlockPosZ, discoveredForHighlights);
                     if (bluntHighlightTooltip != null) {
                        new Tooltip(bluntHighlightTooltip).drawBox(guiGraphics, scaledMouseX, scaledMouseY, this.width, this.height);
                     }
                  }
               }
            }
         }

         matrixStack.translate(0.0F, 0.0F, 1.0F);
         this.mapProcessor.getMessageBoxRenderer().render(guiGraphics, this.mapProcessor.getMessageBox(), this.font, 1, this.height / 2, false);
      }

      matrixStack.popPose();
      this.leftMouseButton.clicked = this.rightMouseButton.clicked = false;
      this.noUploadingLimits = this.cameraX == cameraXBefore && this.cameraZ == cameraZBefore && scaleBefore == this.scale;
      MapRenderHelper.restoreDefaultShaderBlendState();
   }

   public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
   }

   protected void renderPreDropdown(GuiGraphics guiGraphics, int scaledMouseX, int scaledMouseY, float partialTicks) {
      super.renderPreDropdown(guiGraphics, scaledMouseX, scaledMouseY, partialTicks);
      if (this.waypointMenu) {
         SupportMods.xaeroMinimap.getWaypointMenuRenderer().postMapRender(guiGraphics, this, scaledMouseX, scaledMouseY, this.width, this.height, partialTicks);
      }

      if (this.playersMenu) {
         WorldMap.trackedPlayerMenuRenderer.postMapRender(guiGraphics, this, scaledMouseX, scaledMouseY, this.width, this.height, partialTicks);
      }

      this.mapSwitchingGui.postMapRender(guiGraphics, this.minecraft, scaledMouseX, scaledMouseY, this.width, this.height);
   }

   private <E, C> Tooltip hoveredElementTooltipHelper(HoveredMapElementHolder<E, C> hovered, boolean viewedInList) {
      return hovered.getRenderer().getReader().getTooltip(hovered.getElement(), hovered.getRenderer().getContext(), viewedInList);
   }

   private void renderLoadingScreen(GuiGraphics guiGraphics) {
      this.renderMessageScreen(guiGraphics, "Preparing World Map...");
   }

   private void renderMessageScreen(GuiGraphics guiGraphics, String message) {
      this.renderMessageScreen(guiGraphics, message, null);
   }

   private void renderMessageScreen(GuiGraphics guiGraphics, String message, String message2) {
      PoseStack matrixStack = guiGraphics.pose();
      guiGraphics.fill(0, 0, this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight(), -16777216);
      matrixStack.pushPose();
      matrixStack.translate(0.0F, 0.0F, 500.0F);
      guiGraphics.drawCenteredString(
         this.minecraft.font, message, this.minecraft.getWindow().getGuiScaledWidth() / 2, this.minecraft.getWindow().getGuiScaledHeight() / 2, -1
      );
      if (message2 != null) {
         guiGraphics.drawCenteredString(
            this.minecraft.font, message2, this.minecraft.getWindow().getGuiScaledWidth() / 2, this.minecraft.getWindow().getGuiScaledHeight() / 2 + 10, -1
         );
      }

      matrixStack.popPose();
   }

   public void drawDotOnMap(PoseStack matrixStack, VertexConsumer guiLinearBuffer, double x, double z, float angle, double sc) {
      this.drawObjectOnMap(matrixStack, guiLinearBuffer, x, z, angle, sc, 2.5F, 2.5F, 0, 69, 5, 5, 9729);
   }

   public void drawArrowOnMap(PoseStack matrixStack, VertexConsumer guiLinearBuffer, double x, double z, float angle, double sc) {
      this.drawObjectOnMap(matrixStack, guiLinearBuffer, x, z, angle, sc, 13.0F, 5.0F, 0, 0, 26, 28, 9729);
   }

   public void drawFarArrowOnMap(PoseStack matrixStack, VertexConsumer guiLinearBuffer, double x, double z, float angle, double sc) {
      this.drawObjectOnMap(matrixStack, guiLinearBuffer, x, z, angle * 90.0F, sc, 27.0F, 13.0F, 26, 0, 54, 13, 9729);
   }

   public void drawObjectOnMap(
      PoseStack matrixStack,
      VertexConsumer guiLinearBuffer,
      double x,
      double z,
      float angle,
      double sc,
      float offX,
      float offY,
      int textureX,
      int textureY,
      int w,
      int h,
      int filter
   ) {
      matrixStack.pushPose();
      matrixStack.translate(x, z, 0.0);
      matrixStack.scale((float)sc, (float)sc, 1.0F);
      if (angle != 0.0F) {
         OptimizedMath.rotatePose(matrixStack, angle, OptimizedMath.ZP);
      }

      Matrix4f matrix = matrixStack.last().pose();
      renderTexturedModalRect(
         matrix,
         guiLinearBuffer,
         -offX,
         -offY,
         textureX,
         textureY,
         w,
         h,
         256.0F,
         256.0F,
         this.colourBuffer[0],
         this.colourBuffer[1],
         this.colourBuffer[2],
         this.colourBuffer[3]
      );
      matrixStack.popPose();
   }

   public static void renderTexturedModalRectWithLighting3(
      Matrix4f matrix, float x, float y, float width, float height, int texture, boolean hasLight, MultiTextureRenderTypeRenderer renderer
   ) {
      buildTexturedModalRectWithLighting(matrix, renderer.begin(texture), x, y, width, height);
   }

   public static void renderTexturedModalSubRectWithLighting(
      Matrix4f matrix,
      float x,
      float y,
      float textureX1,
      float textureY1,
      float textureX2,
      float textureY2,
      float width,
      float height,
      int texture,
      boolean hasLight,
      MultiTextureRenderTypeRenderer renderer
   ) {
      buildTexturedModalSubRectWithLighting(matrix, renderer.begin(texture), x, y, textureX1, textureY1, textureX2, textureY2, width, height);
   }

   public static void buildTexturedModalRectWithLighting(Matrix4f matrix, BufferBuilder vertexBuffer, float x, float y, float width, float height) {
      vertexBuffer.addVertex(matrix, x + 0.0F, y + height, 0.0F).setUv(0.0F, 1.0F);
      vertexBuffer.addVertex(matrix, x + width, y + height, 0.0F).setUv(1.0F, 1.0F);
      vertexBuffer.addVertex(matrix, x + width, y + 0.0F, 0.0F).setUv(1.0F, 0.0F);
      vertexBuffer.addVertex(matrix, x + 0.0F, y + 0.0F, 0.0F).setUv(0.0F, 0.0F);
   }

   public static void buildTexturedModalSubRectWithLighting(
      Matrix4f matrix,
      BufferBuilder vertexBuffer,
      float x,
      float y,
      float textureX1,
      float textureY1,
      float textureX2,
      float textureY2,
      float width,
      float height
   ) {
      vertexBuffer.addVertex(matrix, x + 0.0F, y + height, 0.0F).setUv(textureX1, textureY2);
      vertexBuffer.addVertex(matrix, x + width, y + height, 0.0F).setUv(textureX2, textureY2);
      vertexBuffer.addVertex(matrix, x + width, y + 0.0F, 0.0F).setUv(textureX2, textureY1);
      vertexBuffer.addVertex(matrix, x + 0.0F, y + 0.0F, 0.0F).setUv(textureX1, textureY1);
   }

   public static void renderTexturedModalRect(
      Matrix4f matrix,
      VertexConsumer vertexBuffer,
      float x,
      float y,
      int textureX,
      int textureY,
      float width,
      float height,
      float textureWidth,
      float textureHeight,
      float r,
      float g,
      float b,
      float a
   ) {
      float normalizedTextureX = textureX / textureWidth;
      float normalizedTextureY = textureY / textureHeight;
      float normalizedTextureX2 = (textureX + width) / textureWidth;
      float normalizedTextureY2 = (textureY + height) / textureHeight;
      vertexBuffer.addVertex(matrix, x + 0.0F, y + height, 0.0F).setColor(r, g, b, a).setUv(normalizedTextureX, normalizedTextureY2);
      vertexBuffer.addVertex(matrix, x + width, y + height, 0.0F).setColor(r, g, b, a).setUv(normalizedTextureX2, normalizedTextureY2);
      vertexBuffer.addVertex(matrix, x + width, y + 0.0F, 0.0F).setColor(r, g, b, a).setUv(normalizedTextureX2, normalizedTextureY);
      vertexBuffer.addVertex(matrix, x + 0.0F, y + 0.0F, 0.0F).setColor(r, g, b, a).setUv(normalizedTextureX, normalizedTextureY);
   }

   public void mapClicked(int button, int x, int y) {
      if (button == 1) {
         if (this.viewedOnMousePress == null
            || !this.viewedOnMousePress.isRightClickValid()
            || this.viewedOnMousePress.getElement() instanceof Waypoint
               && !SupportMods.xaeroMinimap.waypointExists((Waypoint)this.viewedOnMousePress.getElement())) {
            this.handleRightClick(this, (int)(x / this.screenScale), (int)(y / this.screenScale));
         } else {
            this.handleRightClick(this.viewedOnMousePress, (int)(x / this.screenScale), (int)(y / this.screenScale));
            this.mouseDownPosX = -1;
            this.mouseDownPosY = -1;
            this.mapTileSelection = null;
         }
      }
   }

   private void handleRightClick(IRightClickableElement target, int x, int y) {
      if (this.rightClickMenu != null) {
         this.rightClickMenu.setClosed(true);
      }

      this.rightClickMenu = GuiRightClickMenu.getMenu(target, this, x, y, 150);
   }

   public boolean charTyped(char par1, int par2) {
      boolean result = super.charTyped(par1, par2);
      if (this.waypointMenu && SupportMods.xaeroMinimap.getWaypointMenuRenderer().charTyped()) {
         return true;
      } else {
         return this.playersMenu && WorldMap.trackedPlayerMenuRenderer.charTyped() ? true : result;
      }
   }

   public boolean keyPressed(int par1, int par2, int par3) {
      if (par1 == 258) {
         ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
         boolean minimapRadarConfig = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.MINIMAP_RADAR);
         if (this.tabPressed && SupportMods.minimap() && minimapRadarConfig && Minecraft.getInstance().options.keyPlayerList.matches(par1, par2)) {
            return true;
         }

         this.tabPressed = true;
      }

      boolean result = super.keyPressed(par1, par2, par3);
      if (this.isUsingTextField()) {
         if (this.waypointMenu && SupportMods.xaeroMinimap.getWaypointMenuRenderer().keyPressed(this, par1)) {
            result = true;
         } else if (this.playersMenu && WorldMap.trackedPlayerMenuRenderer.keyPressed(this, par1)) {
            result = true;
         }
      } else {
         result = this.onInputPress(par1 != -1 ? Type.KEYSYM : Type.SCANCODE, par1 != -1 ? par1 : par2) || result;
      }

      return result;
   }

   public boolean keyReleased(int par1, int par2, int par3) {
      if (par1 == 258) {
         this.tabPressed = false;
      }

      return this.onInputRelease(par1 != -1 ? Type.KEYSYM : Type.SCANCODE, par1 != -1 ? par1 : par2) ? true : super.keyReleased(par1, par2, par3);
   }

   private static long bytesToMb(long bytes) {
      return bytes / 1024L / 1024L;
   }

   private void setColourBuffer(float r, float g, float b, float a) {
      this.colourBuffer[0] = r;
      this.colourBuffer[1] = g;
      this.colourBuffer[2] = b;
      this.colourBuffer[3] = a;
   }

   private boolean isUsingTextField() {
      AbstractWidget currentFocused = (AbstractWidget)this.getFocused();
      return currentFocused != null && currentFocused.isFocused() && currentFocused instanceof EditBox;
   }

   public void tick() {
      super.tick();
      if (this.waypointMenu) {
         SupportMods.xaeroMinimap.getWaypointMenuRenderer().tick();
      }

      if (this.playersMenu) {
         WorldMap.trackedPlayerMenuRenderer.tick();
      }

      this.caveModeOptions.tick(this);
   }

   public KeyMapping getTrackedPlayerKeyBinding() {
      return SupportMods.minimap() ? SupportMods.xaeroMinimap.getToggleAllyPlayersKey() : ControlsRegister.keyToggleTrackedPlayers;
   }

   private boolean onInputPress(Type type, int code) {
      if (KeyMappingUtils.inputMatches(type, code, ControlsRegister.keyOpenSettings, 0)) {
         this.onSettingsButton(this.settingsButton);
         return true;
      }

      boolean result = false;
      if (KeyMappingUtils.inputMatches(type, code, this.minecraft.options.keyPlayerList, 0)) {
         this.minecraft.options.keyPlayerList.setDown(true);
         result = true;
      }

      if (KeyMappingUtils.inputMatches(type, code, ControlsRegister.keyOpenMap, 0)) {
         this.goBack();
         result = true;
      }

      if (KeyMappingUtils.inputMatches(type, code, this.getTrackedPlayerKeyBinding(), 0)) {
         WorldMap.trackedPlayerMenuRenderer.onShowPlayersButton(this, this.width, this.height);
         return true;
      }

      if ((type == Type.KEYSYM && code == 257 || KeyMappingUtils.inputMatches(type, code, ControlsRegister.keyQuickConfirm, 0)) && this.mapSwitchingGui.active) {
         this.mapSwitchingGui.confirm(this, this.minecraft, this.width, this.height);
         result = true;
      }

      if (KeyMappingUtils.inputMatches(type, code, ControlsRegister.keyToggleDimension, 1)) {
         this.onDimensionToggleButton(this.dimensionToggleButton);
         result = true;
      }

      if (SupportMods.minimap()) {
         SupportMods.xaeroMinimap.onMapKeyPressed(type, code, this);
         result = true;
      }

      if (SupportMods.pac()) {
         result = SupportMods.xaeroPac.onMapKeyPressed(type, code, this) || result;
      }

      IRightClickableElement hoverTarget = this.getHoverTarget();
      if (hoverTarget != null && type == Type.KEYSYM) {
         boolean isValid = hoverTarget.isRightClickValid();
         if (isValid) {
            if (hoverTarget instanceof HoveredMapElementHolder && ((HoveredMapElementHolder)hoverTarget).getElement() instanceof Waypoint) {
               switch (code) {
                  case 72:
                     SupportMods.xaeroMinimap.disableWaypoint((Waypoint)((HoveredMapElementHolder)hoverTarget).getElement());
                     this.closeRightClick();
                     result = true;
                     break;
                  case 261:
                     SupportMods.xaeroMinimap.deleteWaypoint((Waypoint)((HoveredMapElementHolder)hoverTarget).getElement());
                     this.closeRightClick();
                     result = true;
               }
            } else if (SupportMods.pac()
               && hoverTarget instanceof HoveredMapElementHolder
               && ((HoveredMapElementHolder)hoverTarget).getElement() instanceof PlayerTrackerMapElement) {
               switch (code) {
                  case 67:
                     SupportMods.xaeroPac.openPlayerConfigScreen(this, this, (PlayerTrackerMapElement<?>)((HoveredMapElementHolder)hoverTarget).getElement());
                     this.closeRightClick();
                     result = true;
               }
            }
         } else {
            this.closeRightClick();
         }
      }

      return result;
   }

   private double getCurrentMapCoordinateScale() {
      return this.mapProcessor.getMapWorld().getCurrentDimension().calculateDimScale(this.mapProcessor.getWorldDimensionTypeRegistry());
   }

   private boolean onInputRelease(Type type, int code) {
      boolean result = false;
      if (KeyMappingUtils.inputMatches(type, code, this.minecraft.options.keyPlayerList, 0)) {
         this.minecraft.options.keyPlayerList.setDown(false);
         result = true;
      }

      if (SupportMods.minimap() && SupportMods.xaeroMinimap.onMapKeyReleased(type, code, this)) {
         result = true;
      }

      if (SupportMods.minimap() && this.lastViewedDimensionId != null && !this.isUsingTextField()) {
         ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
         boolean waypointsConfig = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.WAYPOINTS);
         int waypointDestinationX = this.mouseBlockPosX;
         int waypointDestinationY = this.mouseBlockPosY;
         int waypointDestinationZ = this.mouseBlockPosZ;
         double waypointDestinationCoordinateScale = this.mouseBlockCoordinateScale;
         boolean waypointDestinationRightClick = false;
         if (this.rightClickMenu != null && this.rightClickMenu.getTarget() == this) {
            waypointDestinationX = this.rightClickX;
            waypointDestinationY = this.rightClickY;
            waypointDestinationZ = this.rightClickZ;
            waypointDestinationCoordinateScale = this.rightClickCoordinateScale;
            waypointDestinationRightClick = true;
         }

         if (KeyMappingUtils.inputMatches(type, code, SupportMods.xaeroMinimap.getWaypointKeyBinding(), 0) && waypointsConfig) {
            SupportMods.xaeroMinimap
               .createWaypoint(
                  this,
                  waypointDestinationX,
                  waypointDestinationY == 32767 ? 32767 : waypointDestinationY + 1,
                  waypointDestinationZ,
                  waypointDestinationCoordinateScale,
                  waypointDestinationRightClick
               );
            this.closeRightClick();
            result = true;
         }

         if (KeyMappingUtils.inputMatches(type, code, SupportMods.xaeroMinimap.getTempWaypointKeyBinding(), 0) && waypointsConfig) {
            this.closeRightClick();
            SupportMods.xaeroMinimap
               .createTempWaypoint(
                  waypointDestinationX,
                  waypointDestinationY == 32767 ? 32767 : waypointDestinationY + 1,
                  waypointDestinationZ,
                  waypointDestinationCoordinateScale,
                  waypointDestinationRightClick
               );
            result = true;
         }

         IRightClickableElement hoverTarget = this.getHoverTarget();
         if (hoverTarget != null && !KeyMappingUtils.inputMatches(type, code, ControlsRegister.keyOpenMap, 0) && type == Type.KEYSYM) {
            boolean isValid = hoverTarget.isRightClickValid();
            if (isValid) {
               if (hoverTarget instanceof HoveredMapElementHolder && ((HoveredMapElementHolder)hoverTarget).getElement() instanceof Waypoint) {
                  switch (code) {
                     case 69:
                        SupportMods.xaeroMinimap.openWaypoint(this, (Waypoint)((HoveredMapElementHolder)hoverTarget).getElement());
                        this.closeRightClick();
                        result = true;
                        break;
                     case 84:
                        SupportMods.xaeroMinimap.teleportToWaypoint(this, (Waypoint)((HoveredMapElementHolder)hoverTarget).getElement());
                        this.closeRightClick();
                        result = true;
                  }
               } else if (hoverTarget instanceof HoveredMapElementHolder
                  && ((HoveredMapElementHolder)hoverTarget).getElement() instanceof PlayerTrackerMapElement) {
                  switch (code) {
                     case 84:
                        new PlayerTeleporter()
                           .teleportToPlayer(
                              this, this.mapProcessor.getMapWorld(), (PlayerTrackerMapElement<?>)((HoveredMapElementHolder)hoverTarget).getElement()
                           );
                        this.closeRightClick();
                        result = true;
                  }
               }
            } else {
               this.closeRightClick();
            }
         }
      }

      return result;
   }

   private IRightClickableElement getHoverTarget() {
      return this.rightClickMenu != null ? this.rightClickMenu.getTarget() : this.viewed;
   }

   private void unfocusAll() {
      if (SupportMods.minimap()) {
         SupportMods.xaeroMinimap.getWaypointMenuRenderer().unfocusAll();
      }

      WorldMap.trackedPlayerMenuRenderer.unfocusAll();
      this.caveModeOptions.unfocusAll();
      this.setFocused(null);
   }

   public void closeRightClick() {
      if (this.rightClickMenu != null) {
         this.rightClickMenu.setClosed(true);
      }
   }

   public void onRightClickClosed() {
      this.rightClickMenu = null;
      this.mapTileSelection = null;
   }

   private void closeDropdowns() {
      if (this.openDropdown != null) {
         this.openDropdown.setClosed(true);
      }
   }

   @Override
   public ArrayList<RightClickOption> getRightClickOptions() {
      ArrayList<RightClickOption> options = new ArrayList<>();
      options.add(new RightClickOption("gui.xaero_right_click_map_title", options.size(), this) {
         @Override
         public void onAction(Screen screen) {
         }
      });
      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      boolean coordinatesConfig = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.COORDINATES);
      boolean waypointsConfig = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.WAYPOINTS);
      if (coordinatesConfig && (!SupportMods.minimap() || !SupportMods.xaeroMinimap.hidingWaypointCoordinates())) {
         if (this.mapTileSelection != null) {
            String chunkOption = this.mapTileSelection.getStartX() == this.mapTileSelection.getEndX()
                  && this.mapTileSelection.getStartZ() == this.mapTileSelection.getEndZ()
               ? String.format("C: (%d;%d)", this.mapTileSelection.getLeft(), this.mapTileSelection.getTop())
               : String.format(
                  "C: (%d;%d):(%d;%d)",
                  this.mapTileSelection.getLeft(),
                  this.mapTileSelection.getTop(),
                  this.mapTileSelection.getRight(),
                  this.mapTileSelection.getBottom()
               );
            options.add(new RightClickOption(chunkOption, options.size(), this) {
               @Override
               public void onAction(Screen screen) {
               }
            });
         }

         options.add(
            new RightClickOption(
               String.format(this.rightClickY != 32767 ? "X: %1$d, Y: %2$d, Z: %3$d" : "X: %1$d, Z: %3$d", this.rightClickX, this.rightClickY, this.rightClickZ),
               options.size(),
               this
            ) {
               @Override
               public void onAction(Screen screen) {
               }
            }
         );
      }

      if (SupportMods.minimap() && waypointsConfig) {
         options.add(
            (new RightClickOption("gui.xaero_right_click_map_create_waypoint", options.size(), this) {
                  @Override
                  public void onAction(Screen screen) {
                     SupportMods.xaeroMinimap
                        .createWaypoint(
                           GuiMap.this,
                           GuiMap.this.rightClickX,
                           GuiMap.this.rightClickY == 32767 ? 32767 : GuiMap.this.rightClickY + 1,
                           GuiMap.this.rightClickZ,
                           GuiMap.this.rightClickCoordinateScale,
                           true
                        );
                  }
               })
               .setNameFormatArgs(KeyMappingUtils.getKeyName(SupportMods.xaeroMinimap.getWaypointKeyBinding()))
         );
         options.add(
            (new RightClickOption("gui.xaero_right_click_map_create_temporary_waypoint", options.size(), this) {
                  @Override
                  public void onAction(Screen screen) {
                     SupportMods.xaeroMinimap
                        .createTempWaypoint(
                           GuiMap.this.rightClickX,
                           GuiMap.this.rightClickY == 32767 ? 32767 : GuiMap.this.rightClickY + 1,
                           GuiMap.this.rightClickZ,
                           GuiMap.this.rightClickCoordinateScale,
                           true
                        );
                  }
               })
               .setNameFormatArgs(KeyMappingUtils.getKeyName(SupportMods.xaeroMinimap.getTempWaypointKeyBinding()))
         );
      }

      MapDimension currentDimension = this.mapProcessor.getMapWorld().getCurrentDimension();
      if (this.minecraft.gameMode.canHurtPlayer() && currentDimension == null) {
         options.add(new RightClickOption("gui.xaero_right_click_map_cant_teleport_world", options.size(), this) {
            @Override
            public void onAction(Screen screen) {
            }
         });
      } else {
         boolean teleportAllowed = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.MAP_TELEPORT_ALLOWED);
         if (!teleportAllowed || this.rightClickY == 32767 && this.minecraft.gameMode.canHurtPlayer()) {
            if (!teleportAllowed) {
               options.add(new RightClickOption("gui.xaero_wm_right_click_map_teleport_not_allowed", options.size(), this) {
                  @Override
                  public void onAction(Screen screen) {
                  }
               });
            } else {
               options.add(new RightClickOption("gui.xaero_right_click_map_cant_teleport", options.size(), this) {
                  @Override
                  public void onAction(Screen screen) {
                  }
               });
            }
         } else {
            options.add(
               new RightClickOption("gui.xaero_right_click_map_teleport", options.size(), this) {
                  @Override
                  public void onAction(Screen screen) {
                     MapDimension currentDimension = GuiMap.this.mapProcessor.getMapWorld().getCurrentDimension();
                     if ((!GuiMap.this.minecraft.gameMode.canHurtPlayer() || currentDimension != null)
                        && (GuiMap.this.rightClickY != 32767 || !GuiMap.this.minecraft.gameMode.canHurtPlayer())) {
                        ResourceKey<Level> tpDim = GuiMap.this.rightClickDim != GuiMap.this.minecraft.level.dimension() ? GuiMap.this.rightClickDim : null;
                        new MapTeleporter()
                           .teleport(
                              GuiMap.this,
                              GuiMap.this.mapProcessor.getMapWorld(),
                              GuiMap.this.rightClickX,
                              GuiMap.this.rightClickY == 32767 ? 32767 : GuiMap.this.rightClickY + 1,
                              GuiMap.this.rightClickZ,
                              tpDim
                           );
                     }
                  }
               }
            );
         }
      }

      if (SupportMods.minimap()) {
         options.add(
            new RightClickOption("gui.xaero_right_click_map_share_location", options.size(), this) {
               @Override
               public void onAction(Screen screen) {
                  SupportMods.xaeroMinimap
                     .shareLocation(
                        GuiMap.this, GuiMap.this.rightClickX, GuiMap.this.rightClickY == 32767 ? 32767 : GuiMap.this.rightClickY + 1, GuiMap.this.rightClickZ
                     );
               }
            }
         );
         if (waypointsConfig) {
            options.add((new RightClickOption("gui.xaero_right_click_map_waypoints_menu", options.size(), this) {
               @Override
               public void onAction(Screen screen) {
                  SupportMods.xaeroMinimap.openWaypointsMenu(GuiMap.this.minecraft, GuiMap.this);
               }
            }).setNameFormatArgs(KeyMappingUtils.getKeyName(SupportMods.xaeroMinimap.getTempWaypointsMenuKeyBinding())));
         }
      }

      if (SupportMods.pac()) {
         SupportMods.xaeroPac.addRightClickOptions(this, options, this.mapTileSelection, this.mapProcessor);
      }

      options.add(new RightClickOption("gui.xaero_right_click_box_map_export", options.size(), this) {
         @Override
         public void onAction(Screen screen) {
            GuiMap.this.onExportButton(GuiMap.this.exportButton);
         }
      });
      options.add((new RightClickOption("gui.xaero_right_click_box_map_settings", options.size(), this) {
         @Override
         public void onAction(Screen screen) {
            GuiMap.this.onSettingsButton(GuiMap.this.settingsButton);
         }
      }).setNameFormatArgs(KeyMappingUtils.getKeyName(ControlsRegister.keyOpenSettings)));
      return options;
   }

   @Override
   public boolean isRightClickValid() {
      return true;
   }

   @Override
   public int getRightClickTitleBackgroundColor() {
      return -10461088;
   }

   public boolean shouldSkipWorldRender() {
      return true;
   }

   public double getUserScale() {
      return this.userScale;
   }

   public Button getRadarButton() {
      return this.radarButton;
   }

   public void onDropdownOpen(DropDownWidget menu) {
      super.onDropdownOpen(menu);
      this.unfocusAll();
   }

   public void onDropdownClosed(DropDownWidget menu) {
      super.onDropdownClosed(menu);
      if (menu == this.rightClickMenu) {
         this.onRightClickClosed();
      }
   }

   public void onCaveModeStartSet() {
      this.caveModeOptions.onCaveModeStartSet(this);
   }

   public MapDimension getFutureDimension() {
      return this.futureDimension;
   }

   public MapProcessor getMapProcessor() {
      return this.mapProcessor;
   }

   public void enableCaveModeOptions() {
      if (!this.caveModeOptions.isEnabled()) {
         this.caveModeOptions.toggle(this);
      }
   }

   public void removeWidget(GuiEventListener current) {
      super.removeWidget(current);
   }

   static {
      identityMatrix.identity();
   }
}
