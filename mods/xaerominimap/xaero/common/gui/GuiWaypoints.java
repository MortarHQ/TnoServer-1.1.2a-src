package xaero.common.gui;

import com.mojang.blaze3d.platform.InputConstants.Type;
import com.mojang.blaze3d.vertex.PoseStack;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Predicate;
import net.minecraft.client.Camera;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList.Entry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import xaero.common.HudMod;
import xaero.common.IXaeroMinimap;
import xaero.common.XaeroMinimapSession;
import xaero.common.graphics.CustomRenderTypes;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointsSort;
import xaero.common.settings.ModSettings;
import xaero.hud.minimap.BuiltInHudModules;
import xaero.hud.minimap.MinimapLogs;
import xaero.hud.minimap.config.primary.option.MinimapPrimaryClientConfigOptions;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.minimap.world.MinimapWorld;
import xaero.hud.minimap.world.MinimapWorldManager;
import xaero.hud.path.XaeroPath;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.client.controls.util.KeyMappingUtils;
import xaero.lib.client.gui.ScreenBase;
import xaero.lib.client.gui.widget.MyTinyButton;
import xaero.lib.client.gui.widget.dropdown.DropDownWidget;
import xaero.lib.client.gui.widget.dropdown.IDropDownWidgetCallback;
import xaero.lib.client.gui.widget.dropdown.DropDownWidget.Builder;
import xaero.lib.common.config.Config;
import xaero.lib.common.config.single.SingleConfigManager;
import xaero.lib.common.util.KeySortableByOther;

public class GuiWaypoints extends ScreenBase implements IDropDownWidgetCallback {
   private static final int FRAME_TOP_SIZE = 58;
   private static final int FRAME_BOTTOM_SIZE = 61;
   public static double distanceDivided;
   private GuiWaypoints.List list;
   private MinimapWorld displayedWorld;
   private ConcurrentSkipListSet<Integer> selectedListSet;
   private GuiWaypointContainers containers;
   private GuiWaypointWorlds worlds;
   private GuiWaypointSets sets;
   private DropDownWidget containersDD;
   private DropDownWidget worldsDD;
   private DropDownWidget setsDD;
   private MinimapSession session;
   private MinimapWorldManager manager;
   private int draggingFromX;
   private int draggingFromY;
   private int draggingFromSlot;
   private Waypoint draggingWaypoint;
   private boolean displayingTeleportableWorld;
   private int shiftSelectFirst;
   private ArrayList<Waypoint> waypointsSorted;
   private final XaeroPath frozenAutoWorldPath;
   private Button deleteButton;
   private Button editButton;
   private Button teleportButton;
   private Button disableEnableButton;
   private Button clearButton;
   private Button shareButton;
   private final HudMod modMain;

   @Deprecated
   public GuiWaypoints(IXaeroMinimap modMain, XaeroMinimapSession minimapSession, Screen par1GuiScreen) {
      this(modMain, minimapSession, par1GuiScreen, null);
   }

   @Deprecated
   public GuiWaypoints(IXaeroMinimap modMain, XaeroMinimapSession minimapSession, Screen par1GuiScreen, Screen escapeScreen) {
      this((HudMod)modMain, BuiltInHudModules.MINIMAP.getCurrentSession(), par1GuiScreen, escapeScreen);
   }

   public GuiWaypoints(HudMod modMain, MinimapSession session, Screen par1GuiScreen, Screen escapeScreen) {
      super(par1GuiScreen, escapeScreen, Component.translatable("gui.xaero_waypoints"));
      this.modMain = modMain;
      this.session = session;
      this.manager = session.getWorldManager();
      this.frozenAutoWorldPath = session.getWorldState().getAutoWorldPath();
      this.displayedWorld = this.manager.getCurrentWorld(this.frozenAutoWorldPath);
      if (this.displayedWorld != null) {
         this.selectedListSet = new ConcurrentSkipListSet<>();
         this.draggingFromX = -1;
         this.draggingFromY = -1;
         this.draggingFromSlot = -1;
         XaeroPath currentContainer = this.displayedWorld.getContainer().getRoot().getPath();
         this.containers = new GuiWaypointContainers(modMain, this.manager, currentContainer, this.frozenAutoWorldPath);
         this.worlds = new GuiWaypointWorlds(
            this.manager.getRootWorldContainer(this.containers.getCurrentKey()), session, this.displayedWorld.getFullPath(), this.frozenAutoWorldPath
         );
         this.displayingTeleportableWorld = session.getWaypointSession().getTeleport().isWorldTeleportable(this.displayedWorld);
         this.waypointsSorted = new ArrayList<>();
      }
   }

   public void init() {
      super.init();
      if (this.displayedWorld != null) {
         this.updateSortedList();
         this.list = new GuiWaypoints.List();
         this.sets = new GuiWaypointSets(true, this.displayedWorld, this.displayedWorld.getCurrentWaypointSetId());
         this.addWidget(this.list);
         this.addRenderableWidget(
            this.deleteButton = new MyTinyButton(this.width / 2 + 129, this.height - 53, Component.translatable("gui.xaero_delete"), b -> {
               if (this.isSomethingSelected()) {
                  this.undrag();
                  boolean shouldRestore = true;

                  for (int i : this.selectedListSet) {
                     Waypoint w = this.list.getWaypoint(i);
                     if (!w.isTemporary()) {
                        shouldRestore = false;
                        w.setTemporary(true);
                     }
                  }

                  if (shouldRestore) {
                     for (int i : this.selectedListSet) {
                        Waypoint w = this.list.getWaypoint(i);
                        w.setTemporary(false);
                     }
                  }

                  try {
                     this.session.getWorldManagerIO().saveWorld(this.displayedWorld);
                  } catch (IOException e) {
                     MinimapLogs.LOGGER.error("suppressed exception", e);
                  }
               }
            })
         );
         this.addRenderableWidget(
            Button.builder(Component.translatable("gui.done", new Object[0]), b -> this.goBack())
               .bounds(this.width / 2 - 100, this.height - 29, 200, 20)
               .build()
         );
         this.addRenderableWidget(
            this.editButton = new MyTinyButton(
               this.width / 2 - 203,
               this.height - 53,
               Component.translatable("gui.xaero_add_edit", new Object[0]),
               b -> {
                  if (this.isAddEditEnabled()) {
                     ArrayList<Waypoint> selectedWaypoints = this.getSelectedWaypointsList()
                        .stream()
                        .filter(w -> !w.isServerWaypoint())
                        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
                     this.minecraft
                        .setScreen(
                           new GuiAddWaypoint(
                              this.modMain,
                              this.session,
                              this,
                              this.escape,
                              selectedWaypoints,
                              this.displayedWorld.getContainer().getRoot().getPath(),
                              this.displayedWorld,
                              this.displayedWorld.getCurrentWaypointSetId(),
                              selectedWaypoints.isEmpty()
                           )
                        );
                     this.list.setSelected((GuiWaypoints.List.WaypointEntry)null);
                  }
               }
            )
         );
         this.addRenderableWidget(
            this.teleportButton = new MyTinyButton(
               this.width / 2 - 120,
               this.height - 53,
               Component.literal(I18n.get("gui.xaero_waypoint_teleport", new Object[0]) + " (T)"),
               b -> {
                  if (this.canTeleport()) {
                     this.displayingTeleportableWorld = this.session.getWaypointSession().getTeleport().isWorldTeleportable(this.displayedWorld);
                     this.session
                        .getWaypointSession()
                        .getTeleport()
                        .teleportToWaypoint(this.list.getWaypoint(this.selectedListSet.first()), this.displayedWorld, this);
                  }
               }
            )
         );
         this.addRenderableWidget(
            this.disableEnableButton = new MyTinyButton(
               this.width / 2 + 46, this.height - 53, Component.translatable("gui.xaero_disable_enable", new Object[0]), b -> {
                  if (this.isSomethingSelected()) {
                     ArrayList<Waypoint> selectedWaypoints = this.getSelectedWaypointsList();
                     if (allWaypointsAre(selectedWaypoints, Waypoint::isTemporary)) {
                        for (Waypoint selected : selectedWaypoints) {
                           this.displayedWorld.getCurrentWaypointSet().remove(selected);
                        }

                        this.selectedListSet.clear();
                     } else if (allWaypointsAre(selectedWaypoints, Waypoint::isDisabled)) {
                        for (Waypoint selected : selectedWaypoints) {
                           selected.setDisabled(false);
                        }
                     } else {
                        for (Waypoint selected : selectedWaypoints) {
                           selected.setDisabled(true);
                        }
                     }

                     this.updateSortedList();

                     try {
                        this.session.getWorldManagerIO().saveWorld(this.displayedWorld);
                     } catch (IOException e) {
                        MinimapLogs.LOGGER.error("suppressed exception", e);
                     }
                  }
               }
            )
         );
         this.addRenderableWidget(
            this.clearButton = new MyTinyButton(this.width / 2 + 130, 32, Component.translatable("gui.xaero_clear", new Object[0]), b -> {
               XaeroPath worldKeys = this.worlds.getCurrentKey();
               String name = this.sets.getOptions()[this.sets.getCurrentSet()];
               if (this.shouldDeleteSet()) {
                  this.minecraft.setScreen(new GuiDeleteSet(I18n.get(name, new Object[0]), worldKeys, name, this, this.escape, this.modMain, this.session));
               } else {
                  this.minecraft.setScreen(new GuiClearSet(I18n.get(name, new Object[0]), worldKeys, name, this, this.escape, this.modMain, this.session));
               }
            })
         );
         this.addRenderableWidget(
            new MyTinyButton(
               this.width / 2 - 203,
               32,
               Component.translatable("gui.xaero_options", new Object[0]),
               b -> this.minecraft
                  .setScreen(new GuiWaypointsOptions(this.modMain, this.session, this, this.escape, this.displayedWorld, this.frozenAutoWorldPath))
            )
         );
         this.addRenderableWidget(
            this.shareButton = new MyTinyButton(this.width / 2 - 37, this.height - 53, Component.translatable("gui.xaero_share", new Object[0]), b -> {
               if (this.isOneSelected()) {
                  Waypoint selected = this.selectedListSet.isEmpty() ? null : this.list.getWaypoint(this.selectedListSet.first());
                  if (selected != null) {
                     this.session.getWaypointSession().getSharing().shareWaypoint(this, selected, this.displayedWorld);
                  }
               }
            })
         );
         this.addWidget(this.containersDD = this.createContainersDropdown());
         this.addWidget(this.worldsDD = this.createWorldsDropdown());
         this.addWidget(this.setsDD = this.createSetsDropdown());
      }
   }

   private DropDownWidget createSetsDropdown() {
      return Builder.begin()
         .setOptions(this.sets.getOptions())
         .setX(this.width / 2 - 100)
         .setY(33)
         .setW(200)
         .setSelected(this.sets.getCurrentSet())
         .setCallback(this)
         .setContainer(this)
         .setNarrationTitle(Component.translatable("gui.xaero_dropdown_waypoint_set"))
         .build();
   }

   private DropDownWidget createContainersDropdown() {
      return Builder.begin()
         .setOptions(this.containers.options)
         .setX(this.width / 2 - 202)
         .setY(17)
         .setW(200)
         .setSelected(this.containers.current)
         .setCallback(this)
         .setContainer(this)
         .setNarrationTitle(Component.translatable("gui.xaero_dropdown_waypoint_container"))
         .build();
   }

   private DropDownWidget createWorldsDropdown() {
      return Builder.begin()
         .setOptions(this.worlds.options)
         .setX(this.width / 2 + 2)
         .setY(17)
         .setW(200)
         .setSelected(this.worlds.current)
         .setCallback(this)
         .setContainer(this)
         .setNarrationTitle(Component.translatable("gui.xaero_dropdown_waypoint_world"))
         .build();
   }

   private ArrayList<Waypoint> getSelectedWaypointsList() {
      return this.selectedListSet.stream().map(i -> this.list.getWaypoint(i)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
   }

   public static boolean allWaypointsAre(ArrayList<Waypoint> waypoints, Predicate<Waypoint> predicate) {
      boolean allTrue = true;

      for (Waypoint w : waypoints) {
         if (!predicate.test(w)) {
            allTrue = false;
            break;
         }
      }

      return allTrue;
   }

   public boolean shouldDeleteSet() {
      return !this.sets.getOptions()[this.sets.getCurrentSet()].equals("gui.xaero_default") && this.displayedWorld.getCurrentWaypointSet().isEmpty();
   }

   private void undrag() {
      this.draggingFromX = -1;
      this.draggingFromY = -1;
      this.draggingFromSlot = -1;
      this.draggingWaypoint = null;
   }

   public boolean mouseClicked(double par1, double par2, int par3) {
      if (this.openDropdown == null) {
         if (KeyMappingUtils.inputMatches(Type.MOUSE, par3, ModSettings.keyWaypoints, 0)) {
            this.goBack();
            return true;
         }

         if (par3 == 0) {
            if (par2 >= 58.0 && par2 < this.height - 61 && this.displayedWorld.getRootConfig().getSortType() == WaypointsSort.NONE) {
               this.draggingFromX = (int)par1;
               this.draggingFromY = (int)par2;
               this.draggingFromSlot = this.list.getEntryAt(par1, par2);
               if (this.draggingFromSlot >= this.displayedWorld.getCurrentWaypointSet().size()) {
                  this.draggingFromSlot = -1;
               }
            }
         } else {
            this.list.setSelected((GuiWaypoints.List.WaypointEntry)null);
         }
      }

      return super.mouseClicked(par1, par2, par3);
   }

   public boolean mouseReleased(double par1, double par2, int par3) {
      try {
         if (this.draggingWaypoint != null) {
            this.session.getWorldManagerIO().saveWorld(this.displayedWorld);
         }
      } catch (IOException e) {
         MinimapLogs.LOGGER.error("suppressed exception", e);
      }

      this.undrag();
      return !super.mouseReleased(par1, par2, par3) ? this.list.mouseReleased(par1, par2, par3) : true;
   }

   public boolean keyReleased(int par1, int par2, int par3) {
      switch (par1) {
         case 84:
            if (this.teleportButton.active) {
               this.teleportButton.onClick(0.0, 0.0);
            }

            return true;
         case 261:
            if (this.disableEnableButton.active) {
               for (int i : this.selectedListSet) {
                  this.list.getWaypoint(i).setTemporary(true);
               }

               this.disableEnableButton.onClick(0.0, 0.0);
            }

            return true;
         default:
            return super.keyReleased(par1, par2, par3);
      }
   }

   public void renderBackground(GuiGraphics guiGraphics, int par1, int par2, float par3) {
      super.renderBackground(guiGraphics, par1, par2, par3);
      this.list.render(guiGraphics, par1, par2, par3);
   }

   public void render(GuiGraphics guiGraphics, int par1, int par2, float par3) {
      if (this.displayedWorld == null) {
         this.minecraft.setScreen(this.parent);
      } else if (this.minecraft.player == null) {
         this.minecraft.setScreen(null);
      } else {
         this.updateButtons();
         super.render(guiGraphics, par1, par2, par3);
      }
   }

   protected void renderPreDropdown(GuiGraphics guiGraphics, int mouseX, int mouseY, float partial) {
      super.renderPreDropdown(guiGraphics, mouseX, mouseY, partial);
      guiGraphics.drawCenteredString(this.font, I18n.get("gui.xaero_world_server", new Object[0]), this.width / 2 - 102, 5, 16777215);
      guiGraphics.drawCenteredString(this.font, I18n.get("gui.xaero_subworld_dimension", new Object[0]), this.width / 2 + 102, 5, 16777215);
      if (this.draggingFromSlot != -1) {
         int distance = (int)Math.sqrt(Math.pow(mouseX - this.draggingFromX, 2.0) + Math.pow(mouseY - this.draggingFromY, 2.0));
         int toSlot = Math.min(this.displayedWorld.getCurrentWaypointSet().size() - 1, this.list.getEntryAt(mouseX, mouseY));
         if (distance > 4 && this.draggingWaypoint == null) {
            this.draggingWaypoint = this.displayedWorld.getCurrentWaypointSet().get(this.draggingFromSlot);
            this.list.setSelected((GuiWaypoints.List.WaypointEntry)null);
         }

         if (this.draggingWaypoint != null && this.draggingFromSlot != toSlot && toSlot != -1) {
            int direction = toSlot > this.draggingFromSlot ? 1 : -1;

            for (int i = this.draggingFromSlot; i != toSlot; i += direction) {
               this.displayedWorld.getCurrentWaypointSet().set(i, this.displayedWorld.getCurrentWaypointSet().get(i + direction));
            }

            this.displayedWorld.getCurrentWaypointSet().set(toSlot, this.draggingWaypoint);
            this.draggingFromSlot = toSlot;
            this.updateSortedList();
         }

         int fromCenter = this.draggingFromX - this.list.getWidth() / 2;
         this.list.drawWaypointSlot(guiGraphics, this.draggingWaypoint, mouseX - 108 - fromCenter, mouseY - this.list.getItemHeight() / 4);
      }
   }

   private void updateButtons() {
      this.deleteButton.active = this.disableEnableButton.active = this.isSomethingSelected();
      this.shareButton.active = this.isOneSelected();
      this.teleportButton.active = this.canTeleport();
      this.editButton.active = this.isAddEditEnabled();
      this.clearButton.setMessage(Component.translatable(this.shouldDeleteSet() ? "gui.xaero_delete_set" : "gui.xaero_clear", new Object[0]));
      ArrayList<Waypoint> selectedWaypointsList = this.getSelectedWaypointsList();
      if (this.isSomethingSelected() && allWaypointsAre(selectedWaypointsList, Waypoint::isTemporary)) {
         this.disableEnableButton.setMessage(Component.translatable("gui.xaero_delete"));
         this.deleteButton.setMessage(Component.translatable("gui.xaero_restore"));
      } else {
         this.deleteButton.setMessage(Component.translatable("gui.xaero_delete"));
         String[] enabledisable = I18n.get("gui.xaero_disable_enable", new Object[0]).split("/");
         this.disableEnableButton.setMessage(Component.literal(enabledisable[!allWaypointsAre(selectedWaypointsList, Waypoint::isDisabled) ? 0 : 1]));
      }
   }

   private boolean isAddEditEnabled() {
      ArrayList<Waypoint> selectedWaypointsList = this.getSelectedWaypointsList();
      return selectedWaypointsList.isEmpty() || !allWaypointsAre(selectedWaypointsList, Waypoint::isServerWaypoint);
   }

   private boolean isSomethingSelected() {
      return !this.selectedListSet.isEmpty();
   }

   private boolean isOneSelected() {
      return this.selectedListSet.size() == 1;
   }

   private boolean canTeleport() {
      if (!this.isOneSelected()) {
         return false;
      }

      if (!this.displayedWorld.getRootConfig().isTeleportationEnabled()) {
         return false;
      }

      if (this.displayingTeleportableWorld) {
         return true;
      }

      ClientConfigManager configManager = this.modMain.getHudConfigs().getClientConfigManager();
      SingleConfigManager<Config> primaryConfigManager = configManager.getPrimaryConfigManager();
      return (Boolean)primaryConfigManager.getEffective(MinimapPrimaryClientConfigOptions.WRONG_WORLD_TELEPORT);
   }

   public boolean onSelected(DropDownWidget menu, int selectedIndex) {
      if (menu != this.containersDD && menu != this.worldsDD) {
         if (menu == this.setsDD) {
            this.list.setSelected((GuiWaypoints.List.WaypointEntry)null);
            if (selectedIndex == menu.size() - 1) {
               MinimapLogs.LOGGER.info("New waypoint set gui");
               this.minecraft.setScreen(new GuiNewSet(this.modMain, this.session, this, this.escape, this.displayedWorld));
               return false;
            }

            this.sets.setCurrentSet(selectedIndex);
            this.displayedWorld.setCurrentWaypointSetId(this.sets.getCurrentSetKey());
            this.updateSortedList();

            try {
               this.session.getWorldManagerIO().saveWorld(this.displayedWorld);
            } catch (IOException e) {
               MinimapLogs.LOGGER.error("suppressed exception", e);
            }

            return true;
         } else {
            return false;
         }
      } else {
         if (menu == this.containersDD) {
            this.containers.current = selectedIndex;
            if (this.containers.current != this.containers.auto) {
               MinimapWorld firstWorld = this.manager.getRootWorldContainer(this.containers.getCurrentKey()).getFirstWorld();
               this.session.getWorldState().setCustomWorldPath(firstWorld.getFullPath());
            } else {
               this.session.getWorldState().setCustomWorldPath(null);
            }

            this.displayedWorld = this.manager.getCurrentWorld(this.frozenAutoWorldPath);
            this.updateSortedList();
            this.worlds = new GuiWaypointWorlds(
               this.manager.getRootWorldContainer(this.containers.getCurrentKey()), this.session, this.displayedWorld.getFullPath(), this.frozenAutoWorldPath
            );
            this.replaceWidget(this.worldsDD, this.worldsDD = this.createWorldsDropdown());
         } else {
            this.worlds.current = selectedIndex;
            if (this.worlds.current != this.worlds.auto) {
               XaeroPath selectedWorldPath = this.worlds.getCurrentKey();
               this.session.getWorldState().setCustomWorldPath(selectedWorldPath);
            } else {
               this.session.getWorldState().setCustomWorldPath(null);
            }

            this.displayedWorld = this.manager.getCurrentWorld(this.frozenAutoWorldPath);
            this.updateSortedList();
         }

         this.displayingTeleportableWorld = this.session.getWaypointSession().getTeleport().isWorldTeleportable(this.displayedWorld);
         this.list.setSelected((GuiWaypoints.List.WaypointEntry)null);
         this.sets = new GuiWaypointSets(true, this.displayedWorld, this.displayedWorld.getCurrentWaypointSetId());
         this.replaceWidget(this.setsDD, this.setsDD = this.createSetsDropdown());
         return true;
      }
   }

   private void updateSortedList() {
      WaypointsSort sortType = this.displayedWorld.getRootConfig().getSortType();
      this.waypointsSorted = new ArrayList<>();
      if (sortType == WaypointsSort.NONE) {
         for (Waypoint waypoint : this.displayedWorld.getCurrentWaypointSet().getWaypoints()) {
            this.waypointsSorted.add(waypoint);
         }
      } else {
         distanceDivided = this.session.getDimensionHelper().getDimensionDivision(this.displayedWorld);
         ArrayList<KeySortableByOther<Waypoint>> sortableKeys = new ArrayList<>();
         Camera camera = this.minecraft.gameRenderer.getMainCamera();

         for (Waypoint w : this.displayedWorld.getCurrentWaypointSet().getWaypoints()) {
            sortableKeys.add(
               new KeySortableByOther(
                  w,
                  new Comparable[]{
                     sortType == WaypointsSort.COLOR
                        ? w.getWaypointColor()
                        : (
                           sortType == WaypointsSort.ANGLE
                              ? -w.getComparisonAngleCos(camera, distanceDivided)
                              : (
                                 sortType == WaypointsSort.NAME
                                    ? w.getComparisonName()
                                    : (sortType == WaypointsSort.SYMBOL ? w.getInitials() : w.getComparisonDistance(camera, distanceDivided))
                              )
                        )
                  }
               )
            );
         }

         Collections.sort(sortableKeys);

         for (KeySortableByOther<Waypoint> k : sortableKeys) {
            this.waypointsSorted.add((Waypoint)k.getKey());
         }

         if (this.displayedWorld.getRootConfig().isSortReversed()) {
            Collections.reverse(this.waypointsSorted);
         }
      }
   }

   public boolean keyPressed(int par1, int par2, int par3) {
      if (!super.keyPressed(par1, par2, par3)) {
         if (KeyMappingUtils.inputMatches(par1 != -1 ? Type.KEYSYM : Type.SCANCODE, par1 != -1 ? par1 : par2, ModSettings.keyWaypoints, 0)) {
            this.goBack();
            return true;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   class List extends ObjectSelectionList<GuiWaypoints.List.WaypointEntry> {
      private int createdCount;

      public List() {
         super(GuiWaypoints.this.minecraft, GuiWaypoints.this.width, Math.max(4, GuiWaypoints.this.height - 61 - 58), 58, 18);
         this.createEntries(this.getWaypointCount());
      }

      protected int getWaypointCount() {
         int size = GuiWaypoints.this.displayedWorld.getCurrentWaypointSet().size();
         return size + GuiWaypoints.this.displayedWorld.getContainer().getServerWaypointManager().size();
      }

      private Waypoint getWaypoint(int slotIndex) {
         if (slotIndex < GuiWaypoints.this.displayedWorld.getCurrentWaypointSet().size()) {
            return GuiWaypoints.this.waypointsSorted.get(slotIndex);
         }

         int serverWPIndex = slotIndex - GuiWaypoints.this.displayedWorld.getCurrentWaypointSet().size();
         return serverWPIndex < GuiWaypoints.this.displayedWorld.getContainer().getServerWaypointManager().size()
            ? GuiWaypoints.this.displayedWorld.getContainer().getServerWaypointManager().getBySlot(serverWPIndex)
            : null;
      }

      protected boolean isSelectedItem(int p_148131_1_) {
         return !GuiWaypoints.this.selectedListSet.isEmpty() && GuiWaypoints.this.selectedListSet.contains(p_148131_1_);
      }

      private void createEntries(int count) {
         this.clearEntries();
         this.createdCount = count;

         for (int i = 0; i < count; i++) {
            GuiWaypoints.List.WaypointEntry entry = new GuiWaypoints.List.WaypointEntry(i);
            this.addEntry(entry);
         }
      }

      public void renderWidget(GuiGraphics guiGraphics, int p_render_1_, int p_render_2_, float p_render_3_) {
         int currentCount = this.getWaypointCount();
         if (currentCount != this.createdCount) {
            this.createEntries(currentCount);
            this.setScrollAmount(this.getScrollAmount());
         }

         super.renderWidget(guiGraphics, p_render_1_, p_render_2_, p_render_3_);
      }

      public boolean isFocused() {
         return GuiWaypoints.this.openDropdown == null && GuiWaypoints.this.draggingWaypoint == null ? GuiWaypoints.this.getFocused() == this : false;
      }

      public void setSelected(GuiWaypoints.List.WaypointEntry e) {
         if (e == null) {
            GuiWaypoints.this.selectedListSet.clear();
            GuiWaypoints.this.shiftSelectFirst = -1;
         } else {
            Waypoint waypoint = this.getWaypoint(e.index);
            int currentSize = GuiWaypoints.this.selectedListSet.size();
            boolean shiftPressed = Screen.hasShiftDown();
            if ((currentSize > 1 || currentSize == 1 && GuiWaypoints.this.selectedListSet.first() != e.index) && !Screen.hasControlDown() && !shiftPressed) {
               GuiWaypoints.this.selectedListSet.clear();
            }

            if (currentSize > 0 && shiftPressed) {
               int direction = e.index > GuiWaypoints.this.shiftSelectFirst ? 1 : -1;
               GuiWaypoints.this.selectedListSet.clear();

               for (int i = GuiWaypoints.this.shiftSelectFirst; i != e.index + direction; i += direction) {
                  GuiWaypoints.this.selectedListSet.add(i);
               }
            } else if (GuiWaypoints.this.selectedListSet.contains(e.index)) {
               GuiWaypoints.this.selectedListSet.remove(e.index);
            } else {
               GuiWaypoints.this.shiftSelectFirst = e.index;
               GuiWaypoints.this.selectedListSet.add(e.index);
            }

            super.setSelected(GuiWaypoints.this.selectedListSet.isEmpty() ? null : e);
         }
      }

      public int getItemHeight() {
         return this.itemHeight;
      }

      public void drawWaypointSlot(GuiGraphics guiGraphics, Waypoint w, int p_180791_2_, int p_180791_3_) {
         PoseStack matrixStack = guiGraphics.pose();
         if (w != null) {
            matrixStack.pushPose();
            matrixStack.translate(0.0F, 0.0F, 1.0F);
            guiGraphics.drawCenteredString(
               GuiWaypoints.this.font,
               w.getLocalizedName()
                  + (
                     w.isDisabled()
                        ? " §4" + I18n.get("gui.xaero_disabled", new Object[0])
                        : (w.isTemporary() ? " §4" + I18n.get("gui.xaero_temporary", new Object[0]) : "")
                  ),
               p_180791_2_ + 110,
               p_180791_3_ + 1,
               16777215
            );
            int rectX = p_180791_2_ + 8 + 4;
            int rectY = p_180791_3_ + 6;
            if (w.isGlobal()) {
               guiGraphics.drawCenteredString(GuiWaypoints.this.font, "*", rectX - 25, rectY - 3, 16777215);
            }

            int opacity = 90;
            BufferSource renderTypeBuffers = GuiWaypoints.this.modMain.getHudRenderer().getCustomVertexConsumers().getBetterPVPRenderTypeBuffers();
            GuiWaypoints.this.modMain
               .getMinimap()
               .getWaypointGuiRenderer()
               .drawIconOnGUI(
                  guiGraphics,
                  GuiWaypoints.this.modMain.getInterfaces().getMinimapInterface().getMinimapFBORenderer().getHelper(),
                  w,
                  rectX,
                  rectY,
                  opacity,
                  renderTypeBuffers,
                  renderTypeBuffers.getBuffer(CustomRenderTypes.COLORED_WAYPOINTS_BGS),
                  renderTypeBuffers.getBuffer(CustomRenderTypes.GUI_NEAREST)
               );
            renderTypeBuffers.endBatch();
            matrixStack.popPose();
         }
      }

      public int getEntryAt(double x, double y) {
         GuiWaypoints.List.WaypointEntry entry = (GuiWaypoints.List.WaypointEntry)this.getEntryAtPosition(x, y);
         return entry == null ? -1 : entry.index;
      }

      public class WaypointEntry extends Entry<GuiWaypoints.List.WaypointEntry> {
         private int index;

         public WaypointEntry(int index) {
            this.index = index;
         }

         public void render(
            GuiGraphics guiGraphics,
            int index,
            int p_render_2_,
            int p_render_3_,
            int p_render_4_,
            int p_render_5_,
            int p_render_6_,
            int p_render_7_,
            boolean p_render_8_,
            float p_render_9_
         ) {
            Waypoint w = List.this.getWaypoint(index);
            if (w != GuiWaypoints.this.draggingWaypoint) {
               List.this.drawWaypointSlot(guiGraphics, w, p_render_3_, p_render_2_);
            }
         }

         public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
            return p_mouseClicked_5_ == 0;
         }

         public Component getNarration() {
            Waypoint w = List.this.getWaypoint(this.index);
            String narration = "";
            if (w != null) {
               narration = narration
                  + I18n.get("narrator.select", new Object[]{w.getName()})
                  + (w.isDisabled() ? " " + I18n.get("gui.xaero_disabled", new Object[0]) : "")
                  + (w.isTemporary() ? " " + I18n.get("gui.xaero_temporary", new Object[0]) : "")
                  + ", ";
            }

            if (GuiWaypoints.this.selectedListSet.size() != 1) {
               narration = narration
                  + I18n.get("narrator.select", new Object[]{I18n.get("gui.xaero_waypoints", new Object[0]) + " " + GuiWaypoints.this.selectedListSet.size()});
            }

            return Component.literal(narration);
         }
      }
   }
}
