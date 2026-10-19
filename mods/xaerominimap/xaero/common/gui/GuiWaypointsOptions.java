package xaero.common.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.apache.commons.io.FileUtils;
import xaero.common.HudMod;
import xaero.common.IXaeroMinimap;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.settings.ModSettings;
import xaero.hud.minimap.MinimapLogs;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.minimap.waypoint.set.WaypointSet;
import xaero.hud.minimap.world.MinimapWorld;
import xaero.hud.minimap.world.MinimapWorldManager;
import xaero.hud.minimap.world.container.MinimapWorldContainer;
import xaero.hud.minimap.world.container.MinimapWorldContainerUtil;
import xaero.hud.minimap.world.container.MinimapWorldRootContainer;
import xaero.hud.path.XaeroPath;
import xaero.lib.client.gui.ScreenBase;
import xaero.lib.client.gui.widget.MyTinyButton;
import xaero.lib.client.gui.widget.Tooltip;

public class GuiWaypointsOptions extends ScreenBase {
   private MinimapSession session;
   private MinimapWorldManager manager;
   private Button automaticButton;
   private Button subAutomaticButton;
   private Button deleteButton;
   private Button subDeleteButton;
   private Button connectButton;
   private boolean buttonTest;
   private MinimapWorld minimapWorld;
   private MinimapWorld automaticMinimapWorld;
   private MinimapWorldRootContainer rootContainer;
   private boolean teleportationOptionShown;
   private boolean selectedWorldIsConnected;
   public Tooltip mwTooltip = new Tooltip("gui.xaero_use_multiworld_tooltip");
   public Tooltip teleportationTooltip = new Tooltip("gui.xaero_teleportation_tooltip", Style.EMPTY.withColor(ChatFormatting.RED));
   public Tooltip connectionTooltip = new Tooltip("gui.xaero_world_connection_tooltip");
   private final IXaeroMinimap modMain;

   public GuiWaypointsOptions(
      IXaeroMinimap modMain, MinimapSession session, Screen parent, Screen escapeScreen, MinimapWorld minimapWorld, XaeroPath frozenAutoWorldPath
   ) {
      super(parent, escapeScreen, Component.translatable("gui.xaero_options"));
      this.modMain = modMain;
      this.session = session;
      this.manager = this.session.getWorldManager();
      this.minimapWorld = minimapWorld;
      this.rootContainer = minimapWorld.getContainer().getRoot();
      this.automaticMinimapWorld = this.manager.getWorld(frozenAutoWorldPath);
      this.teleportationOptionShown = this.rootContainer.getConfig().isTeleportationEnabled();
   }

   public void init() {
      super.init();
      this.parent.resize(this.minecraft, this.width, this.height);
      this.selectedWorldIsConnected = this.rootContainer.getSubWorldConnections().isConnected(this.automaticMinimapWorld, this.minimapWorld);
      this.addRenderableWidget(
         new MyTinyButton(this.width / 2 - 203, 32, Component.translatable("gui.xaero_close", new Object[0]), b -> this.actionPerformed(b, 5))
      );
      this.addRenderableWidget(
         new MyBigButton(6, this.width / 2 - 203, 57, Component.translatable("gui.xaero_transfer", new Object[0]), b -> this.actionPerformed(b, 6))
      );
      this.addRenderableWidget(
         this.automaticButton = new MyBigButton(
            7, this.width / 2 - 203, 82, Component.translatable("gui.xaero_make_automatic", new Object[0]), b -> this.actionPerformed(b, 7)
         )
      );
      this.addRenderableWidget(
         this.subAutomaticButton = new MyBigButton(
            8, this.width / 2 - 203, 107, Component.translatable("gui.xaero_make_multi_automatic", new Object[0]), b -> this.actionPerformed(b, 8)
         )
      );
      this.addRenderableWidget(
         this.deleteButton = new MyBigButton(
            9, this.width / 2 - 203, 132, Component.translatable("gui.xaero_delete_world", new Object[0]), b -> this.actionPerformed(b, 9)
         )
      );
      this.addRenderableWidget(
         this.subDeleteButton = new MyBigButton(
            10, this.width / 2 - 203, 157, Component.translatable("gui.xaero_delete_multi_world", new Object[0]), b -> this.actionPerformed(b, 10)
         )
      );
      this.addRenderableWidget(
         new MyBigButton(200, this.width / 2 + 3, 57, Component.literal(this.getConfigButtonName(0)), b -> this.onConfigButtonClick((MyBigButton)b))
      );
      MyBigButton teleportationEnabledButton = (MyBigButton)this.addRenderableWidget(
         new MyBigButton(201, this.width / 2 + 3, 82, Component.literal(this.getConfigButtonName(1)), b -> this.onConfigButtonClick((MyBigButton)b))
      );
      teleportationEnabledButton.active = this.teleportationOptionShown;
      this.addRenderableWidget(
         new MyBigButton(13, this.width / 2 + 3, 107, Component.translatable("gui.xaero_world_teleport_command"), b -> this.actionPerformed(b, 13))
      );
      this.addRenderableWidget(
         this.connectButton = new MyBigButton(14, this.width / 2 + 3, 132, Component.literal(this.getConfigButtonName(4)), b -> this.actionPerformed(b, 14))
      );
      this.connectButton.active = MinimapWorldContainerUtil.isMultiplayer(this.rootContainer.getPath())
         && this.rootContainer == this.automaticMinimapWorld.getContainer().getRoot();
      this.addRenderableWidget(
         new MyBigButton(202, this.width / 2 + 3, 182, Component.literal(this.getConfigButtonName(2)), b -> this.onConfigButtonClick((MyBigButton)b))
      );
      this.addRenderableWidget(
         new MyBigButton(203, this.width / 2 + 3, 207, Component.literal(this.getConfigButtonName(3)), b -> this.onConfigButtonClick((MyBigButton)b))
      );
   }

   private String getConfigButtonName(int buttonId) {
      switch (buttonId) {
         case 0:
            return I18n.get("gui.xaero_use_multiworld", new Object[0])
               + ": "
               + ModSettings.getTranslation(this.rootContainer.getConfig().isUsingMultiworldDetection());
         case 1:
            return I18n.get("gui.xaero_teleportation", new Object[0])
               + ": "
               + ModSettings.getTranslation(this.rootContainer.getConfig().isTeleportationEnabled());
         case 2:
            return I18n.get("gui.xaero_sort", new Object[0]) + ": " + I18n.get(this.rootContainer.getConfig().getSortType().optionName, new Object[0]);
         case 3:
            return I18n.get("gui.xaero_sort_reversed", new Object[0]) + ": " + ModSettings.getTranslation(this.rootContainer.getConfig().isSortReversed());
         case 4:
            return this.selectedWorldIsConnected
               ? I18n.get("gui.xaero_disconnect_from_auto", new Object[0])
               : I18n.get("gui.xaero_connect_with_auto", new Object[0]);
         default:
            return "";
      }
   }

   private void onConfigButtonClick(MyBigButton button) {
      this.buttonTest = true;
      MinimapWorldRootContainer wc = this.rootContainer;
      switch (button.getId() - 200) {
         case 0:
            wc.getConfig().setUsingMultiworldDetection(!this.rootContainer.getConfig().isUsingMultiworldDetection());
            wc.getConfig().setDefaultMultiworldId(null);
            break;
         case 1:
            wc.getConfig().setTeleportationEnabled(!wc.getConfig().isTeleportationEnabled());
            break;
         case 2:
            this.rootContainer.getConfig().toggleSortType();
            this.parent.init(this.minecraft, this.width, this.height);
            break;
         case 3:
            this.rootContainer.getConfig().toggleSortReversed();
            this.parent.init(this.minecraft, this.width, this.height);
      }

      this.session.getWorldManagerIO().getRootConfigIO().save(wc);
      button.setMessage(Component.literal(this.getConfigButtonName(button.getId() - 200)));
   }

   public boolean mouseClicked(double par1, double par2, int par3) {
      this.buttonTest = false;
      boolean toReturn = super.mouseClicked(par1, par2, par3);
      if (!this.buttonTest) {
         this.goBack();
      }

      return toReturn;
   }

   protected void actionPerformed(Button p_146284_1_, int id) {
      this.buttonTest = true;
      if (p_146284_1_.active) {
         switch (id) {
            case 5:
               this.goBack();
               break;
            case 6:
               this.minecraft.setScreen(new GuiTransfer(this.modMain, this.session, this.parent, this.escape));
               break;
            case 7:
               this.minecraft
                  .setScreen(
                     new ConfirmScreen(
                        result -> this.confirmResult(result, id),
                        Component.translatable("gui.xaero_make_automatic_msg1"),
                        Component.translatable("gui.xaero_make_automatic_msg2")
                     )
                  );
               break;
            case 8:
               this.minecraft
                  .setScreen(
                     new ConfirmScreen(
                        result -> this.confirmResult(result, id),
                        Component.translatable("gui.xaero_make_multi_automatic_msg1"),
                        Component.translatable("gui.xaero_make_multi_automatic_msg2")
                     )
                  );
               break;
            case 9:
               this.minecraft
                  .setScreen(
                     new ConfirmScreen(
                        result -> this.confirmResult(result, id),
                        Component.translatable("gui.xaero_delete_world_msg1"),
                        Component.translatable("gui.xaero_delete_world_msg2")
                     )
                  );
               break;
            case 10:
               this.minecraft
                  .setScreen(
                     new ConfirmScreen(
                        result -> this.confirmResult(result, id),
                        Component.translatable("gui.xaero_delete_multi_world_msg1"),
                        Component.translatable("gui.xaero_delete_multi_world_msg2")
                     )
                  );
               break;
            case 11:
               this.minecraft
                  .setScreen(
                     new ConfirmScreen(
                        result -> this.confirmResult(result, id),
                        Component.translatable("gui.xaero_multiply_msg1"),
                        Component.translatable("gui.xaero_multiply_msg2")
                     )
                  );
               break;
            case 12:
               this.minecraft
                  .setScreen(
                     new ConfirmScreen(
                        result -> this.confirmResult(result, id),
                        Component.translatable("gui.xaero_multiply_msg1"),
                        Component.translatable("gui.xaero_divide_msg2")
                     )
                  );
               break;
            case 13:
               this.minecraft.setScreen(new GuiWorldTpCommand(this.modMain, this, this.escape, this.minimapWorld.getContainer().getRoot()));
               break;
            case 14:
               MinimapWorldContainer autoContainer = this.automaticMinimapWorld.getContainer();
               MinimapWorldContainer selectedContainer = this.minimapWorld.getContainer();
               String autoWorldName = autoContainer.getFullWorldName(this.automaticMinimapWorld.getNode(), autoContainer.getSubName()) + " (auto)";
               String selectedWorldName = selectedContainer.getFullWorldName(this.minimapWorld.getNode(), selectedContainer.getSubName());
               String connectionDisplayString = autoWorldName + "   §e<=>§r   " + selectedWorldName;
               if (this.selectedWorldIsConnected) {
                  this.minecraft
                     .setScreen(
                        new ConfirmScreen(
                           result -> this.confirmResult(result, id),
                           Component.translatable("gui.xaero_disconnect_from_auto_msg"),
                           Component.literal(connectionDisplayString)
                        )
                     );
               } else {
                  this.minecraft
                     .setScreen(
                        new ConfirmScreen(
                           result -> this.confirmResult(result, id),
                           Component.translatable("gui.xaero_connect_with_auto_msg"),
                           Component.literal(connectionDisplayString)
                        )
                     );
               }
         }
      }
   }

   public void confirmResult(boolean result, int id) {
      boolean differentRoot = this.isDifferentRootContainer();
      boolean differentSub = this.isDifferentSubWorld(differentRoot);
      boolean exitOptions = true;
      if (result) {
         switch (id) {
            case 7:
               if (differentRoot) {
                  MinimapWorldRootContainer selected = this.rootContainer;
                  MinimapWorldRootContainer auto = this.manager.getAutoRootContainer();
                  if (selected != null && auto != null) {
                     XaeroPath buKey = selected.getPath();
                     this.manager.removeContainer(selected.getPath());
                     this.manager.removeContainer(auto.getPath());
                     selected.setPath(auto.getPath());
                     auto.setPath(buKey);
                     this.manager.addRootWorldContainer(selected);
                     this.manager.addRootWorldContainer(auto);
                     selected.updateConnectionsField(this.session.getWaypointSession());
                     auto.updateConnectionsField(this.session.getWaypointSession());
                     Path selectedPath = selected.getDirectoryPath();
                     Path autoPath = auto.getDirectoryPath();
                     Path tempFolder = this.modMain.getWaypointsFolder().resolve("temp_to_add");

                     try {
                        Files.createDirectories(tempFolder);
                        Path selectedTemp = tempFolder.resolve(selectedPath.getFileName());
                        if (Files.exists(selectedPath)) {
                           Files.move(selectedPath, selectedTemp);
                        }

                        if (Files.exists(autoPath)) {
                           Files.move(autoPath, selectedPath);
                        }

                        if (Files.exists(selectedTemp)) {
                           Files.move(selectedTemp, autoPath);
                        }

                        Files.deleteIfExists(tempFolder);
                        this.session.getWorldManagerIO().getRootConfigIO().load(selected);
                        this.session.getWorldManagerIO().getRootConfigIO().load(auto);
                     } catch (Throwable e) {
                        this.modMain.getInterfaces().getMinimapInterface().setCrashedWith(e);
                     }

                     this.session.getWorldState().setCustomWorldPath(null);
                  }
               }
               break;
            case 8:
               if (!differentSub) {
                  break;
               }

               MinimapWorld autoWorld = this.automaticMinimapWorld;
               MinimapWorld selectedWorld = this.minimapWorld;

               try {
                  Path autoFile = this.session.getWorldManagerIO().getWorldFile(autoWorld);
                  Path selectedFile = this.session.getWorldManagerIO().getWorldFile(selectedWorld);
                  Path autoTempFile = autoFile.getParent().resolve("temp_to_add").resolve(autoFile.getFileName());
                  Files.createDirectories(autoTempFile.getParent());
                  if (!Files.exists(autoFile)) {
                     Files.createFile(autoFile);
                  }

                  Files.move(autoFile, autoTempFile);
                  if (!Files.exists(selectedFile)) {
                     Files.createFile(selectedFile);
                  }

                  Files.move(selectedFile, autoFile);
                  if (Files.exists(autoTempFile)) {
                     Files.move(autoTempFile, selectedFile);
                  }

                  Files.deleteIfExists(autoTempFile.getParent());
               } catch (Throwable e) {
                  this.modMain.getInterfaces().getMinimapInterface().setCrashedWith(e);
                  break;
               }

               MinimapWorldContainer autoWc = autoWorld.getContainer();
               MinimapWorldContainer selectedWc = selectedWorld.getContainer();
               autoWorld.setContainer(selectedWc);
               selectedWorld.setContainer(autoWc);
               selectedWc.removeWorld(selectedWorld.getNode());
               autoWc.removeWorld(autoWorld.getNode());
               String buSelected = selectedWorld.getNode();
               selectedWorld.setNode(autoWorld.getNode());
               autoWorld.setNode(buSelected);
               selectedWc.addWorld(autoWorld);
               autoWc.addWorld(selectedWorld);
               ResourceKey<Level> buDimId = selectedWorld.getDimId();
               selectedWorld.setDimId(autoWorld.getDimId());
               autoWorld.setDimId(buDimId);
               this.rootContainer.getSubWorldConnections().swapConnections(autoWorld, selectedWorld);
               this.session.getWorldManagerIO().getRootConfigIO().save(this.rootContainer);
               this.session.getWorldState().setCustomWorldPath(null);
               break;
            case 9:
               if (!differentRoot) {
                  break;
               }

               XaeroPath selectedRootContainerId = this.rootContainer.getPath();

               try {
                  File directory = selectedRootContainerId.applyToFilePath(this.modMain.getWaypointsFolder()).toFile();
                  if (directory.exists()) {
                     FileUtils.deleteDirectory(directory);
                  }
               } catch (Throwable e) {
                  this.modMain.getInterfaces().getMinimapInterface().setCrashedWith(e);
                  break;
               }

               this.manager.removeContainer(selectedRootContainerId);
               this.session.getWorldState().setCustomWorldPath(null);
               break;
            case 10:
               if (differentSub) {
                  MinimapWorld selectedWorld = this.minimapWorld;

                  try {
                     Files.deleteIfExists(this.session.getWorldManagerIO().getWorldFile(selectedWorld));
                  } catch (IOException e) {
                     MinimapLogs.LOGGER.error("suppressed exception", e);
                  }

                  selectedWorld.getContainer().removeWorld(selectedWorld.getNode());
                  selectedWorld.getContainer().removeName(selectedWorld.getNode());
                  this.session.getWorldState().setCustomWorldPath(null);
               }
               break;
            case 11:
               this.multiplyWaypoints(this.minimapWorld, 8.0);
               break;
            case 12:
               this.multiplyWaypoints(this.minimapWorld, 0.125);
            case 13:
            default:
               break;
            case 14:
               if (!this.selectedWorldIsConnected) {
                  this.rootContainer.getSubWorldConnections().addConnection(this.automaticMinimapWorld, this.minimapWorld);
               } else {
                  this.rootContainer.getSubWorldConnections().removeConnection(this.automaticMinimapWorld, this.minimapWorld);
               }

               this.session.getWorldManagerIO().getRootConfigIO().save(this.rootContainer);
         }

         if (exitOptions) {
            if (this.parent instanceof GuiWaypoints) {
               this.minecraft.setScreen(new GuiWaypoints((HudMod)this.modMain, this.session, ((GuiWaypoints)this.parent).parent, this.escape));
            } else {
               this.goBack();
            }
         }
      } else {
         this.minecraft.setScreen(this);
      }
   }

   private void multiplyWaypoints(MinimapWorld world, double factor) {
      for (WaypointSet set : world.getIterableWaypointSets()) {
         for (Waypoint wp : set.getWaypoints()) {
            wp.setX((int)Math.floor(wp.getX() * factor));
            wp.setZ((int)Math.floor(wp.getZ() * factor));
         }
      }

      try {
         this.session.getWorldManagerIO().saveWorld(world);
      } catch (IOException e) {
         MinimapLogs.LOGGER.error("suppressed exception", e);
      }
   }

   private boolean isDifferentRootContainer() {
      return this.session.getWorldState().getAutoRootContainerPath() != null
         && !this.session.getWorldState().getAutoRootContainerPath().equals(this.rootContainer.getPath());
   }

   private boolean isDifferentSubWorld(boolean differentRoot) {
      return !differentRoot && this.minimapWorld != this.automaticMinimapWorld;
   }

   public void render(GuiGraphics guiGraphics, int par1, int par2, float par3) {
      PoseStack matrixStack = guiGraphics.pose();
      this.automaticButton.active = this.deleteButton.active = this.isDifferentRootContainer();
      this.subAutomaticButton.active = this.subDeleteButton.active = this.isDifferentSubWorld(this.automaticButton.active);
      this.parent.render(guiGraphics, 0, 0, par3);
      guiGraphics.flush();
      GlStateManager._clear(256, Minecraft.ON_OSX);
      super.render(guiGraphics, par1, par2, par3);
      matrixStack.pushPose();
      matrixStack.translate(0.0, 0.0, 0.1);

      for (GuiEventListener el : this.children()) {
         if (el instanceof AbstractWidget w
            && w instanceof MyBigButton b
            && par1 >= b.getX()
            && par2 >= b.getY()
            && par1 < b.getX() + b.getWidth()
            && par2 < b.getY() + 20) {
            if (b.getId() >= 200) {
               switch (b.getId() - 200) {
                  case 0:
                     this.mwTooltip.drawBox(guiGraphics, par1, par2, this.width, this.height);
                     break;
                  case 1:
                     this.teleportationTooltip.drawBox(guiGraphics, par1, par2, this.width, this.height);
               }
            } else if (b.getId() == 14 && b.active) {
               this.connectionTooltip.drawBox(guiGraphics, par1, par2, this.width, this.height);
            }
         }
      }

      matrixStack.popPose();
   }
}
