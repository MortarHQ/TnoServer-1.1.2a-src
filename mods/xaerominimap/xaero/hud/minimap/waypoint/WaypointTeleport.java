package xaero.hud.minimap.waypoint;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.level.Level;
import xaero.common.HudMod;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.config.primary.option.MinimapPrimaryClientConfigOptions;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.minimap.world.MinimapWorld;
import xaero.hud.minimap.world.container.MinimapWorldRootContainer;
import xaero.hud.minimap.world.container.config.RootConfig;
import xaero.hud.path.XaeroPath;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.common.config.Config;
import xaero.lib.common.config.single.SingleConfigManager;

public class WaypointTeleport {
   public static final String TELEPORT_ANYWAY_COMMAND = "xaero_tp_anyway";
   public static final String SLASH_TELEPORT_ANYWAY_COMMAND = "/xaero_tp_anyway";
   private final HudMod modMain;
   private final Minecraft mc;
   private final WaypointSession session;
   private final MinimapSession minimapSession;
   private Waypoint teleportAnywayWP;
   private MinimapWorld teleportAnywayWorld;

   public WaypointTeleport(HudMod modMain, WaypointSession session, MinimapSession minimapSession) {
      this.modMain = modMain;
      this.session = session;
      this.minimapSession = minimapSession;
      this.mc = Minecraft.getInstance();
   }

   public boolean canTeleport(boolean displayingTeleportableWorld, MinimapWorld displayedWorld) {
      ClientConfigManager configManager = this.modMain.getHudConfigs().getClientConfigManager();
      SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
      return ((Boolean)primaryConfigManager.getEffective(MinimapPrimaryClientConfigOptions.WRONG_WORLD_TELEPORT) || displayingTeleportableWorld)
         && displayedWorld.getRootConfig().isTeleportationEnabled();
   }

   public void teleportAnyway() {
      if (this.teleportAnywayWP != null) {
         Screen dummyScreen = new Screen(Component.literal("")) {};
         Minecraft minecraft = Minecraft.getInstance();
         dummyScreen.init(minecraft, minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight());
         this.teleportToWaypoint(this.teleportAnywayWP, this.teleportAnywayWorld, dummyScreen, false);
      }
   }

   public void teleportToWaypoint(Waypoint waypoint, MinimapWorld world, Screen screen) {
      this.teleportToWaypoint(waypoint, world, screen, true);
   }

   public void teleportToWaypoint(Waypoint waypoint, MinimapWorld world, Screen screen, boolean respectHiddenCoords) {
      this.minimapSession.getWorldStateUpdater().update();
      boolean isTeleportableWorld = this.isWorldTeleportable(world);
      if (waypoint != null && this.canTeleport(isTeleportableWorld, world)) {
         this.mc.setScreen(null);
         if (!waypoint.isYIncluded() && this.mc.gameMode.canHurtPlayer()) {
            MutableComponent messageComponent = Component.literal(I18n.get("gui.xaero_teleport_y_unknown", new Object[0]));
            messageComponent.setStyle(messageComponent.getStyle().withColor(ChatFormatting.RED));
            this.mc.gui.getChat().addMessage(messageComponent);
         } else {
            String fullCommand = "";
            boolean crossDimension = false;
            MinimapWorldRootContainer rootContainer = world.getContainer().getRoot();
            MinimapWorld autoWorld = this.minimapSession.getWorldManager().getAutoWorld();
            if (isTeleportableWorld && world != autoWorld) {
               if (!this.isTeleportationSafe(world)) {
                  MutableComponent messageComponent = Component.literal(I18n.get("gui.xaero_teleport_not_connected", new Object[0]));
                  messageComponent.setStyle(messageComponent.getStyle().withColor(ChatFormatting.RED));
                  this.mc.gui.getChat().addMessage(messageComponent);
                  return;
               }

               boolean reachableDimension = true;
               if (autoWorld == null || autoWorld.getContainer() != world.getContainer()) {
                  crossDimension = true;
                  XaeroPath containerPath = world.getContainer().getPath();
                  if (containerPath.getNodeCount() > 1) {
                     String dimensionNode = containerPath.getAtIndex(1).getLastNode();
                     if (!dimensionNode.startsWith("dim%")) {
                        this.mc.gui.getChat().addMessage(Component.translatable("gui.xaero_visit_needed"));
                        return;
                     }

                     ResourceKey<Level> dimensionId = this.minimapSession.getDimensionHelper().getDimensionKeyForDirectoryName(dimensionNode);
                     if (dimensionId != null) {
                        this.minimapSession.getWorldState().setCustomWorldPath(null);
                        fullCommand = "/execute in " + dimensionId.location() + " run ";
                     } else {
                        reachableDimension = false;
                     }
                  } else {
                     reachableDimension = false;
                  }
               }

               if (!reachableDimension) {
                  this.mc.gui.getChat().addMessage(Component.literal(I18n.get("gui.xaero_unreachable_dimension", new Object[0])).withStyle(ChatFormatting.RED));
                  return;
               }
            }

            ClientConfigManager configManager = this.modMain.getHudConfigs().getClientConfigManager();
            boolean hideWaypointCoordinatesConfig = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.HIDE_WAYPOINT_COORDINATES);
            if (respectHiddenCoords && hideWaypointCoordinatesConfig && this.mc.options.chatVisibility().get() != ChatVisiblity.HIDDEN) {
               MutableComponent messageComponent = Component.literal(I18n.get("gui.xaero_teleport_coordinates_hidden", new Object[0]));
               messageComponent.setStyle(messageComponent.getStyle().withColor(ChatFormatting.AQUA));
               this.mc.gui.getChat().addMessage(messageComponent);
               MutableComponent clickableQuestion = Component.literal("§e[" + I18n.get("gui.xaero_teleport_anyway", new Object[0]) + "]");
               clickableQuestion.setStyle(
                  clickableQuestion.getStyle()
                     .withClickEvent(new ClickEvent(Action.RUN_COMMAND, "/xaero_tp_anyway"))
                     .withHoverEvent(
                        new HoverEvent(
                           net.minecraft.network.chat.HoverEvent.Action.SHOW_TEXT,
                           Component.literal(I18n.get("gui.xaero_teleport_shows_coordinates", new Object[0])).withStyle(ChatFormatting.RED)
                        )
                     )
               );
               this.teleportAnywayWP = waypoint;
               this.teleportAnywayWorld = world;
               this.mc.gui.getChat().addMessage(clickableQuestion);
            } else {
               int x = waypoint.getX();
               int z = waypoint.getZ();
               double dimDiv = this.minimapSession.getDimensionHelper().getDimensionDivision(world);
               if (!crossDimension && dimDiv != 1.0) {
                  x = (int)Math.floor(x / dimDiv);
                  z = (int)Math.floor(z / dimDiv);
               }

               RootConfig config = rootContainer.getConfig();
               String serverTpCommand = waypoint.isRotation() ? config.getServerTeleportCommandRotationFormat() : config.getServerTeleportCommandFormat();
               String defaultTpCommand = (String)configManager.getEffective(
                  waypoint.isRotation()
                     ? MinimapProfiledConfigOptions.WAYPOINT_DEFAULT_TELEPORT_ROTATION_FORMAT
                     : MinimapProfiledConfigOptions.WAYPOINT_DEFAULT_TELEPORT_FORMAT
               );
               String tpCommand = !config.isUsingDefaultTeleportCommand() && serverTpCommand != null ? serverTpCommand : defaultTpCommand;
               if (!fullCommand.isEmpty()) {
                  if (tpCommand.startsWith("/")) {
                     tpCommand = tpCommand.substring(1);
                  }

                  if (tpCommand.startsWith("minecraft:")) {
                     tpCommand = tpCommand.substring(10);
                  }
               }

               boolean partialYConfig = (Boolean)configManager.getEffective(MinimapProfiledConfigOptions.WAYPOINT_PARTIAL_Y_TELEPORT);
               String yString = !waypoint.isYIncluded() ? "~" : (partialYConfig ? waypoint.getY() + 0.5 + "" : waypoint.getY() + "");
               tpCommand = tpCommand.replace("{x}", x + "").replace("{y}", yString).replace("{z}", z + "").replace("{name}", waypoint.getLocalizedName());
               if (waypoint.isRotation()) {
                  tpCommand = tpCommand.replace("{yaw}", waypoint.getYaw() + "");
               }

               fullCommand = fullCommand + tpCommand;
               if (fullCommand.startsWith("/")) {
                  fullCommand = fullCommand.substring(1);
                  if (!this.mc.player.connection.sendUnsignedCommand(fullCommand)) {
                     this.mc.player.connection.sendCommand(fullCommand);
                  }
               } else {
                  this.mc.player.connection.sendChat(fullCommand);
               }
            }
         }
      }
   }

   public boolean isWorldTeleportable(MinimapWorld displayedWorld) {
      MinimapWorld autoWorld = this.minimapSession.getWorldManager().getAutoWorld();
      MinimapWorldRootContainer rootContainer = displayedWorld.getContainer().getRoot();
      if (!rootContainer.getPath().equals(this.minimapSession.getWorldState().getAutoRootContainerPath())) {
         return false;
      } else if (autoWorld == displayedWorld) {
         return true;
      } else if (autoWorld == null) {
         return false;
      } else {
         return autoWorld.getContainer() == displayedWorld.getContainer()
            ? true
            : (Boolean)this.modMain.getHudConfigs().getClientConfigManager().getEffective(MinimapProfiledConfigOptions.WAYPOINT_TELEPORT_CROSS_DIMENSION);
      }
   }

   public boolean isTeleportationSafe(MinimapWorld displayedWorld) {
      if (!Minecraft.getInstance().gameMode.canHurtPlayer()) {
         return true;
      }

      MinimapWorld autoWorld = this.minimapSession.getWorldManager().getAutoWorld();
      MinimapWorldRootContainer rootContainer = displayedWorld.getContainer().getRoot();
      return rootContainer.getSubWorldConnections().isConnected(autoWorld, displayedWorld);
   }
}
