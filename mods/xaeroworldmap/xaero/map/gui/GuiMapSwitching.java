package xaero.map.gui;

import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.lib.client.gui.widget.Tooltip;
import xaero.lib.client.gui.widget.dropdown.DropDownWidget;
import xaero.lib.client.gui.widget.dropdown.DropDownWidget.Builder;
import xaero.lib.common.util.KeySortableByOther;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.config.util.WorldMapClientConfigUtils;
import xaero.map.graphics.CustomRenderTypes;
import xaero.map.graphics.MapRenderHelper;
import xaero.map.world.MapConnectionManager;
import xaero.map.world.MapConnectionNode;
import xaero.map.world.MapDimension;

public class GuiMapSwitching {
   private static final Component CONNECT_MAP = Component.translatable("gui.xaero_connect_map");
   private static final Component DISCONNECT_MAP = Component.translatable("gui.xaero_disconnect_map");
   private MapProcessor mapProcessor;
   private MapDimension settingsDimension;
   private String[] mwDropdownValues;
   private DropDownWidget createdDimensionDropdown;
   private DropDownWidget createdMapDropdown;
   private Button switchingButton;
   private Button multiworldTypeOptionButton;
   private Button renameButton;
   private Button connectButton;
   private Button deleteButton;
   private Button confirmButton;
   private Tooltip serverSelectionModeBox = new Tooltip("gui.xaero_mw_server_box");
   private Tooltip mapSelectionBox = new Tooltip("gui.xaero_map_selection_box");
   public boolean active;
   private boolean writableOnInit;
   private boolean uiPausedOnUpdate;
   private boolean mapSwitchingAllowed;

   public GuiMapSwitching(MapProcessor mapProcessor) {
      this.mapProcessor = mapProcessor;
      this.mapSelectionBox.setStartWidth(200);
      this.serverSelectionModeBox.setStartWidth(200);
   }

   public void init(GuiMap mapScreen, Minecraft minecraft, int width, int height) {
      boolean dimensionDDWasOpen = this.createdDimensionDropdown != null && !this.createdDimensionDropdown.isClosed();
      boolean mapDDWasOpen = this.createdMapDropdown != null && !this.createdMapDropdown.isClosed();
      this.createdDimensionDropdown = null;
      this.createdMapDropdown = null;
      this.switchingButton = null;
      this.multiworldTypeOptionButton = null;
      this.renameButton = null;
      this.deleteButton = null;
      this.confirmButton = null;
      this.settingsDimension = this.mapProcessor.getMapWorld().getFutureDimension();
      this.mapSwitchingAllowed = this.settingsDimension != null;
      synchronized (this.mapProcessor.uiPauseSync) {
         this.uiPausedOnUpdate = this.isUIPaused();
         mapScreen.addButton(this.switchingButton = new GuiMapSwitchingButton(this.active, 0, height - 20, b -> {
            synchronized (this.mapProcessor.uiPauseSync) {
               if (this.canToggleThisScreen()) {
                  this.active = !this.active;
                  mapScreen.init(minecraft, width, height);
                  mapScreen.setFocused(this.switchingButton);
               }
            }
         }));
         if (this.mapSwitchingAllowed) {
            this.writableOnInit = this.settingsDimension.futureMultiworldWritable;
            if (this.active) {
               this.createdDimensionDropdown = this.createDimensionDropdown(this.uiPausedOnUpdate, width, mapScreen, minecraft);
               this.createdMapDropdown = this.createMapDropdown(this.uiPausedOnUpdate, width, mapScreen, minecraft);
               mapScreen.addWidget(this.createdDimensionDropdown);
               mapScreen.addWidget(this.createdMapDropdown);
               if (dimensionDDWasOpen) {
                  this.createdDimensionDropdown.setClosed(false);
               }

               if (mapDDWasOpen) {
                  this.createdMapDropdown.setClosed(false);
               }

               mapScreen.addButton(
                  this.multiworldTypeOptionButton = new TooltipButton(
                     width / 2 - 90, 24, 180, 20, Component.literal(this.getMultiworldTypeButtonMessage()), b -> {
                        synchronized (this.mapProcessor.uiPauseSync) {
                           if (this.isMapSelectionOptionEnabled()) {
                              this.mapProcessor.toggleMultiworldType(this.settingsDimension);
                              b.setMessage(Component.literal(this.getMultiworldTypeButtonMessage()));
                           }
                        }
                     }, this.settingsDimension.isFutureMultiworldServerBased() ? () -> this.serverSelectionModeBox : () -> this.mapSelectionBox
                  )
               );
               mapScreen.addButton(this.renameButton = Button.builder(Component.translatable("gui.xaero_rename"), b -> {
                  synchronized (this.mapProcessor.uiPauseSync) {
                     if (this.canRenameMap()) {
                        String currentMultiworld = this.settingsDimension.getFutureMultiworldUnsynced();
                        if (currentMultiworld != null) {
                           minecraft.setScreen(new GuiMapName(this.mapProcessor, mapScreen, mapScreen, this.settingsDimension, currentMultiworld));
                        }
                     }
                  }
               }).bounds(width / 2 + 109, 80, 60, 20).build());
               mapScreen.addButton(
                  this.connectButton = Button.builder(
                        this.getConnectButtonLabel(),
                        b -> {
                           if (this.canConnectMap()) {
                              MapConnectionNode playerMapKey = this.settingsDimension.getMapWorld().getPlayerMapKey();
                              if (playerMapKey != null) {
                                 MapConnectionNode destinationMapKey = this.settingsDimension.getSelectedMapKeyUnsynced();
                                 if (destinationMapKey != null) {
                                    String autoMapName = playerMapKey.getNamedString(this.settingsDimension.getMapWorld());
                                    String selectedMapName = destinationMapKey.getNamedString(this.settingsDimension.getMapWorld());
                                    String connectionDisplayString = autoMapName + "   §e<=>§r   " + selectedMapName;
                                    MapConnectionManager mapConnections = this.settingsDimension.getMapWorld().getMapConnections();
                                    boolean connected = mapConnections.isConnected(playerMapKey, destinationMapKey);
                                    BooleanConsumer confirmationConsumer = result -> {
                                       if (result) {
                                          synchronized (this.mapProcessor.uiSync) {
                                             if (connected) {
                                                mapConnections.removeConnection(playerMapKey, destinationMapKey);
                                             } else {
                                                mapConnections.addConnection(playerMapKey, destinationMapKey);
                                             }

                                             b.setMessage(this.getConnectButtonLabel());
                                             this.settingsDimension.getMapWorld().saveConfig();
                                          }
                                       }

                                       minecraft.setScreen(mapScreen);
                                    };
                                    if (connected) {
                                       minecraft.setScreen(
                                          new ConfirmScreen(
                                             confirmationConsumer,
                                             Component.translatable("gui.xaero_wm_disconnect_from_auto_msg"),
                                             Component.literal(connectionDisplayString)
                                          )
                                       );
                                    } else {
                                       minecraft.setScreen(
                                          new ConfirmScreen(
                                             confirmationConsumer,
                                             Component.translatable("gui.xaero_wm_connect_with_auto_msg"),
                                             Component.literal(connectionDisplayString)
                                          )
                                       );
                                    }
                                 }
                              }
                           }
                        }
                     )
                     .bounds(width / 2 + 109, 102, 60, 20)
                     .build()
               );
               mapScreen.addButton(
                  this.deleteButton = Button.builder(
                        Component.translatable("gui.xaero_delete"),
                        b -> {
                           synchronized (this.mapProcessor.uiPauseSync) {
                              if (this.canDeleteMap()) {
                                 String selectedMWId = this.settingsDimension.getFutureCustomSelectedMultiworld();
                                 minecraft.setScreen(
                                    new ConfirmScreen(
                                       result -> {
                                          if (result) {
                                             String mapNameAndIdLine = I18n.get("gui.xaero_delete_map_msg4", new Object[0])
                                                + ": "
                                                + this.settingsDimension.getMultiworldName(selectedMWId)
                                                + " ("
                                                + selectedMWId
                                                + ")";
                                             minecraft.setScreen(
                                                new ConfirmScreen(
                                                   result2 -> {
                                                      if (result2) {
                                                         synchronized (this.mapProcessor.uiSync) {
                                                            if (this.mapProcessor.getMapWorld() == this.settingsDimension.getMapWorld()) {
                                                               MapDimension currentDimension = !this.mapProcessor.isMapWorldUsable()
                                                                  ? null
                                                                  : this.mapProcessor.getMapWorld().getCurrentDimension();
                                                               if (this.settingsDimension == currentDimension
                                                                  && this.settingsDimension.getCurrentMultiworld().equals(selectedMWId)) {
                                                                  if (WorldMapClientConfigUtils.getDebug()) {
                                                                     WorldMap.LOGGER.info("Delayed map deletion!");
                                                                  }

                                                                  this.mapProcessor.requestCurrentMapDeletion();
                                                               } else {
                                                                  if (WorldMapClientConfigUtils.getDebug()) {
                                                                     WorldMap.LOGGER.info("Instant map deletion!");
                                                                  }

                                                                  this.settingsDimension.deleteMultiworldMapDataUnsynced(selectedMWId);
                                                               }

                                                               this.settingsDimension.deleteMultiworldId(selectedMWId);
                                                               this.settingsDimension.pickDefaultCustomMultiworldUnsynced();
                                                               this.settingsDimension.saveConfigUnsynced();
                                                               this.settingsDimension.futureMultiworldWritable = false;
                                                            }
                                                         }
                                                      }

                                                      minecraft.setScreen(mapScreen);
                                                   },
                                                   Component.translatable("gui.xaero_delete_map_msg3"),
                                                   Component.literal(mapNameAndIdLine)
                                                )
                                             );
                                          } else {
                                             minecraft.setScreen(mapScreen);
                                          }
                                       },
                                       Component.translatable("gui.xaero_delete_map_msg1"),
                                       Component.translatable("gui.xaero_delete_map_msg2")
                                    )
                                 );
                              }
                           }
                        }
                     )
                     .bounds(width / 2 - 168, 80, 60, 20)
                     .build()
               );
               mapScreen.addButton(this.confirmButton = Button.builder(Component.translatable("gui.xaero_confirm"), b -> {
                  synchronized (this.mapProcessor.uiPauseSync) {
                     if (this.canConfirm()) {
                        this.confirm(mapScreen, minecraft, width, height);
                     }
                  }
               }).bounds(width / 2 - 50, 104, 100, 20).build());
               this.updateButtons(mapScreen, width, minecraft);
            } else {
               this.switchingButton.active = this.canToggleThisScreen();
            }
         } else {
            this.switchingButton.active = false;
         }
      }
   }

   public static GuiDimensionOptions getSortedDimensionOptions(MapDimension dim) {
      int selected = 0;
      ResourceKey<Level> currentDim = dim.getDimId();
      List<KeySortableByOther<ResourceKey<Level>>> sortableList = new ArrayList<>();

      for (MapDimension dimension : dim.getMapWorld().getDimensionsList()) {
         sortableList.add(new KeySortableByOther(dimension.getDimId(), new Comparable[]{dimension.getDimId().location().toString()}));
      }

      Collections.sort(sortableList);
      selected = getDropdownSelectionIdFromValue(sortableList, currentDim);
      ResourceKey<Level>[] values = new ResourceKey[0];
      values = ((ArrayList)sortableList.stream().map(KeySortableByOther::getKey).collect(ArrayList::new, ArrayList::add, ArrayList::addAll)).toArray(values);
      return new GuiDimensionOptions(selected, values);
   }

   private DropDownWidget createDimensionDropdown(boolean paused, int width, GuiMap mapScreen, Minecraft minecraft) {
      GuiDimensionOptions dimOptions = getSortedDimensionOptions(this.settingsDimension);
      List<String> dropdownLabels = new ArrayList<>();
      ResourceKey<Level> currentWorldDim = this.mapProcessor.getWorld() == null ? null : this.mapProcessor.getWorld().dimension();

      for (ResourceKey<Level> k : dimOptions.values) {
         String result = k.location().toString();
         if (result.startsWith("minecraft:")) {
            result = result.substring(10);
         }

         if (k == currentWorldDim) {
            result = result + " (auto)";
         }

         dropdownLabels.add(result);
      }

      ResourceKey<Level>[] finalValues = dimOptions.values;
      return Builder.begin()
         .setOptions(dropdownLabels.toArray(new String[0]))
         .setX(width / 2 - 100)
         .setY(64)
         .setW(200)
         .setSelected(dimOptions.selected)
         .setCallback((dd, i) -> {
            ResourceKey<Level> selectedValue = finalValues[i];
            this.settingsDimension = this.settingsDimension.getMapWorld().getDimension(selectedValue);
            if (selectedValue == currentWorldDim) {
               selectedValue = null;
            }

            this.settingsDimension.getMapWorld().setCustomDimensionId(selectedValue);
            this.mapProcessor.checkForWorldUpdate();
            DropDownWidget newDropDown = this.createMapDropdown(this.uiPausedOnUpdate, width, mapScreen, minecraft);
            mapScreen.replaceWidget(this.createdMapDropdown, newDropDown);
            this.createdMapDropdown = newDropDown;
            this.updateButtons(mapScreen, width, minecraft);
            return true;
         })
         .setContainer(mapScreen)
         .setNarrationTitle(Component.translatable("gui_xaero_wm_dropdown_dimension_select"))
         .build();
   }

   private DropDownWidget createMapDropdown(boolean paused, int width, GuiMap mapScreen, Minecraft minecraft) {
      int selected = 0;
      List<String> mwDropdownNames;
      if (!paused) {
         String currentMultiworld = this.settingsDimension.getFutureMultiworldUnsynced();
         List<KeySortableByOther<String>> sortableList = new ArrayList<>();

         for (String mwId : this.settingsDimension.getMultiworldIdsCopy()) {
            sortableList.add(new KeySortableByOther(mwId, new Comparable[]{this.settingsDimension.getMultiworldName(mwId).toLowerCase()}));
         }

         if (currentMultiworld != null) {
            int currentIndex = getDropdownSelectionIdFromValue(sortableList, currentMultiworld);
            if (currentIndex == -1) {
               sortableList.add(
                  new KeySortableByOther(currentMultiworld, new Comparable[]{this.settingsDimension.getMultiworldName(currentMultiworld).toLowerCase()})
               );
            }
         }

         Collections.sort(sortableList);
         if (currentMultiworld != null) {
            selected = getDropdownSelectionIdFromValue(sortableList, currentMultiworld);
         }

         this.mwDropdownValues = ((ArrayList)sortableList.stream().map(KeySortableByOther::getKey).collect(ArrayList::new, ArrayList::add, ArrayList::addAll))
            .toArray(new String[0]);
         mwDropdownNames = sortableList.stream()
            .<String>map(KeySortableByOther::getKey)
            .map(this.settingsDimension::getMultiworldName)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
         mwDropdownNames.add("§8" + I18n.get("gui.xaero_create_new_map", new Object[0]));
      } else {
         mwDropdownNames = new ArrayList<>();
         this.mwDropdownValues = null;
         mwDropdownNames.add("§7" + I18n.get("gui.xaero_map_menu_please_wait", new Object[0]));
      }

      DropDownWidget result = Builder.begin()
         .setOptions(mwDropdownNames.toArray(new String[0]))
         .setX(width / 2 - 100)
         .setY(84)
         .setW(200)
         .setSelected(selected)
         .setCallback((dd, i) -> {
            synchronized (this.mapProcessor.uiPauseSync) {
               if (this.isUIPaused() || this.uiPausedOnUpdate) {
                  return false;
               } else if (i < this.mwDropdownValues.length) {
                  this.mapProcessor.setMultiworld(this.settingsDimension, this.mwDropdownValues[i]);
                  this.updateButtons(mapScreen, width, minecraft);
                  return true;
               } else {
                  minecraft.setScreen(new GuiMapName(this.mapProcessor, mapScreen, mapScreen, this.settingsDimension, null));
                  return false;
               }
            }
         })
         .setContainer(mapScreen)
         .setNarrationTitle(Component.translatable("gui_xaero_wm_dropdown_map_select"))
         .build();
      result.setActive(!paused);
      return result;
   }

   private boolean isUIPaused() {
      return this.mapProcessor.isUIPaused() || this.mapProcessor.isWaitingForWorldUpdate();
   }

   private boolean isMapSelectionOptionEnabled() {
      return !this.isUIPaused() && !this.settingsDimension.isFutureMultiworldServerBased() && this.settingsDimension.getMapWorld().isMultiplayer();
   }

   private boolean canToggleThisScreen() {
      return !this.isUIPaused() && this.settingsDimension != null && this.settingsDimension.futureMultiworldWritable;
   }

   private boolean canDeleteMap() {
      return !this.isUIPaused()
         && !this.settingsDimension.isFutureUsingWorldSaveUnsynced()
         && this.mwDropdownValues != null
         && this.mwDropdownValues.length > 1
         && this.settingsDimension.getFutureCustomSelectedMultiworld() != null;
   }

   private boolean canRenameMap() {
      return !this.isUIPaused() && !this.settingsDimension.isFutureUsingWorldSaveUnsynced();
   }

   private boolean canConnectMap() {
      if (!this.mapProcessor.getMapWorld().isMultiplayer()) {
         return false;
      }

      MapConnectionNode playerMapKey = this.settingsDimension.getMapWorld().getPlayerMapKey();
      if (playerMapKey == null) {
         return false;
      }

      MapConnectionNode destinationMapKey = this.settingsDimension.getSelectedMapKeyUnsynced();
      return destinationMapKey == null ? false : !destinationMapKey.equals(playerMapKey);
   }

   private boolean canConfirm() {
      return !this.isUIPaused();
   }

   private Component getConnectButtonLabel() {
      synchronized (this.mapProcessor.uiPauseSync) {
         if (this.isUIPaused()) {
            return CONNECT_MAP;
         }

         MapConnectionNode playerMapKey = this.settingsDimension.getMapWorld().getPlayerMapKey();
         if (playerMapKey == null) {
            return CONNECT_MAP;
         }

         MapConnectionNode destinationMapKey = this.settingsDimension.getSelectedMapKeyUnsynced();
         if (destinationMapKey == null) {
            return CONNECT_MAP;
         }

         MapConnectionManager mapConnections = this.settingsDimension.getMapWorld().getMapConnections();
         return mapConnections.isConnected(playerMapKey, destinationMapKey) ? DISCONNECT_MAP : CONNECT_MAP;
      }
   }

   private void updateButtons(GuiMap mapScreen, int width, Minecraft minecraft) {
      synchronized (this.mapProcessor.uiPauseSync) {
         boolean isPaused = this.isUIPaused();
         if (this.uiPausedOnUpdate != isPaused) {
            DropDownWidget newDropDown = !this.active ? null : this.createMapDropdown(isPaused, width, mapScreen, minecraft);
            if (newDropDown != null) {
               if (this.createdMapDropdown != null) {
                  mapScreen.replaceWidget(this.createdMapDropdown, newDropDown);
               } else {
                  mapScreen.addWidget(newDropDown);
               }
            } else if (this.createdMapDropdown != null) {
               mapScreen.removeWidget(this.createdMapDropdown);
            }

            this.createdMapDropdown = !this.active ? null : newDropDown;
            this.uiPausedOnUpdate = isPaused;
         }

         this.switchingButton.active = this.canToggleThisScreen();
         if (this.deleteButton != null) {
            this.deleteButton.active = this.canDeleteMap();
         }

         if (this.renameButton != null) {
            this.renameButton.active = this.canRenameMap();
         }

         if (this.connectButton != null) {
            this.connectButton.active = this.canConnectMap();
            this.connectButton.setMessage(this.getConnectButtonLabel());
         }

         if (this.multiworldTypeOptionButton != null) {
            this.multiworldTypeOptionButton.active = this.isMapSelectionOptionEnabled();
         }

         if (this.confirmButton != null) {
            this.confirmButton.active = this.canConfirm();
         }
      }
   }

   private String getMultiworldTypeButtonMessage() {
      int multiworldType = this.settingsDimension.getMapWorld().getFutureMultiworldType(this.settingsDimension);
      return I18n.get("gui.xaero_map_selection", new Object[0])
         + ": "
         + I18n.get(
            this.settingsDimension.isFutureMultiworldServerBased()
               ? "gui.xaero_mw_server"
               : (multiworldType == 0 ? "gui.xaero_mw_single" : (multiworldType == 1 ? "gui.xaero_mw_manual" : "gui.xaero_mw_spawn")),
            new Object[0]
         );
   }

   public void confirm(GuiMap mapScreen, Minecraft minecraft, int width, int height) {
      if (this.mapProcessor.confirmMultiworld(this.settingsDimension)) {
         this.active = false;
         mapScreen.init(minecraft, width, height);
      }
   }

   private static <S> int getDropdownSelectionIdFromValue(List<KeySortableByOther<S>> values, S value) {
      for (int selected = 0; selected < values.size(); selected++) {
         if (values.get(selected).getKey().equals(value)) {
            return selected;
         }
      }

      return -1;
   }

   public void preMapRender(GuiMap mapScreen, Minecraft minecraft, int width, int height) {
      if (!this.active && this.settingsDimension != null && !this.settingsDimension.futureMultiworldWritable) {
         this.active = true;
         mapScreen.init(minecraft, width, height);
      }

      if (this.mapSwitchingAllowed && (this.createdMapDropdown == null || this.createdMapDropdown.isClosed())) {
         synchronized (this.mapProcessor.uiPauseSync) {
            if (this.uiPausedOnUpdate != this.isUIPaused()) {
               this.updateButtons(mapScreen, width, minecraft);
            }
         }
      }

      if (this.active && this.settingsDimension != null && this.createdMapDropdown.isClosed() && !this.uiPausedOnUpdate) {
         String currentMultiworld = this.settingsDimension.getFutureMultiworldUnsynced();
         if (currentMultiworld != null) {
            String currentDropdownSelection = this.mwDropdownValues[this.createdMapDropdown.getSelected()];
            if (!currentMultiworld.equals(currentDropdownSelection) || this.writableOnInit != this.settingsDimension.futureMultiworldWritable) {
               mapScreen.init(minecraft, width, height);
            }
         }
      }
   }

   public void renderText(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY, int width, int height) {
      if (this.active) {
         String selectMapString = I18n.get("gui.xaero_select_map", new Object[0]) + ":";
         BufferSource renderTypeBuffers = this.mapProcessor.getCvc().getRenderTypeBuffers();
         VertexConsumer backgroundVertexBuffer = renderTypeBuffers.getBuffer(CustomRenderTypes.MAP_COLOR_OVERLAY);
         MapRenderHelper.drawStringWithBackground(
            guiGraphics,
            minecraft.font,
            selectMapString,
            width / 2 - minecraft.font.width(selectMapString) / 2,
            49,
            -1,
            0.0F,
            0.0F,
            0.0F,
            0.4F,
            backgroundVertexBuffer
         );
         renderTypeBuffers.endBatch();
      }
   }

   public void postMapRender(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY, int width, int height) {
   }
}
