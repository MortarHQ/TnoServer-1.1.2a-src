package com.mafuyu404.oneenoughitem.client.gui;

import com.mafuyu404.oneenoughitem.api.DomainAdapter;
import com.mafuyu404.oneenoughitem.api.DomainRegistry;
import com.mafuyu404.oneenoughitem.client.gui.cache.EditorCache;
import com.mafuyu404.oneenoughitem.client.gui.components.DataDisplayWidget;
import com.mafuyu404.oneenoughitem.client.gui.components.ScrollablePanel;
import com.mafuyu404.oneenoughitem.client.gui.components.TagDisplayWidget;
import com.mafuyu404.oneenoughitem.client.gui.editor.DataController;
import com.mafuyu404.oneenoughitem.client.gui.editor.FileActions;
import com.mafuyu404.oneenoughitem.client.gui.editor.ObjectDropdownController;
import com.mafuyu404.oneenoughitem.client.gui.editor.PanelsLayoutHelper;
import com.mafuyu404.oneenoughitem.client.gui.manager.ReplacementEditorManager;
import com.mafuyu404.oneenoughitem.client.gui.util.GuiUtils;
import com.mafuyu404.oneenoughitem.client.gui.util.ModUtils;
import com.mafuyu404.oneenoughitem.web.WebEditorServer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ReplacementEditorScreen extends Screen {
   private static final int PANEL_WIDTH = 220;
   private static final int PANEL_HEIGHT = 140;
   private static final int BUTTON_WIDTH = 70;
   private static final int BUTTON_HEIGHT = 18;
   private static final int MARGIN = 8;
   private static final float EDITOR_SCALE_X = 1.24F;
   private static final float EDITOR_SCALE_Y = 2.255F;
   private static final int EDITOR_SHIFT_X = 5;
   private static final int EDITOR_SHIFT_Y = 0;
   private static final int EDITOR_INNER_MARGIN_X = 13;
   private static final int EDITOR_HEADER_GAP = 13;
   private static final int EDITOR_BOTTOM_MARGIN = 14;
   private final ReplacementEditorManager manager;
   private EditBox datapackNameBox;
   private EditBox fileNameBox;
   private Button createFileButton;
   private Button selectFileButton;
   private Button reloadButton;
   private Button clearAllButton;
   private Button saveToJSONButton;
   private Button openWebEditorButton;
   private Button objectDropdownButton;
   private boolean showObjectDropdown = false;
   private final List<Button> objectIndexButtons = new ArrayList<>();
   private ScrollablePanel objectDropdownPanel;
   private Button domainDropdownButton;
   private boolean showDomainDropdown = false;
   private final List<Button> domainButtons = new ArrayList<>();
   private ScrollablePanel domainDropdownPanel;
   private Button addMatchItemButton;
   private Button addMatchTagButton;
   private Button clearMatchButton;
   private ScrollablePanel matchPanel;
   private final List<TagDisplayWidget> matchTagWidgets;
   private final List<DataDisplayWidget> matchDataWidgets;
   private DataDisplayWidget resultDataWidget;
   private Button selectResultItemButton;
   private Button clearResultButton;
   private ScrollablePanel resultPanel;
   private TagDisplayWidget resultTagWidget;
   private final PanelsLayoutHelper panelsHelper = new PanelsLayoutHelper();
   private final FileActions fileActions;
   private final DataController dataController;

   public ReplacementEditorScreen() {
      super(Component.translatable("gui.oneenoughitem.replacement_editor.title"));
      this.manager = new ReplacementEditorManager();
      this.matchDataWidgets = new ArrayList<>();
      this.matchTagWidgets = new ArrayList<>();
      this.fileActions = new FileActions(this.manager);
      this.dataController = new DataController(this.manager, this::syncManagerDataToWidgets);
      this.loadFromCache();
   }

   private void loadFromCache() {
      EditorCache.CacheData cache = EditorCache.loadCache();
      if (cache != null) {
         for (String dataId : cache.matchItems()) {
            this.manager.addMatchDataId(dataId);
            DataDisplayWidget widget = new DataDisplayWidget(0, 0, dataId, button -> this.removeMatchDataId(dataId), true);
            this.matchDataWidgets.add(widget);
         }

         for (String tagId : cache.matchTags()) {
            ResourceLocation id = ResourceLocation.parse(tagId);
            this.manager.addMatchTag(id);
            TagDisplayWidget widget = new TagDisplayWidget(0, 0, id, button -> this.removeMatchTag(id));
            this.matchTagWidgets.add(widget);
         }

         if (cache.resultItem() != null) {
            this.manager.setResultDataId(cache.resultItem());
            this.resultDataWidget = new DataDisplayWidget(0, 0, cache.resultItem(), null, false);
         }

         if (cache.resultTag() != null) {
            ResourceLocation id = ResourceLocation.parse(cache.resultTag());
            this.manager.setResultTag(id);
            this.resultTagWidget = new TagDisplayWidget(0, 0, id, null);
         }

         if (cache.fileName() != null) {
            this.manager.setCurrentFileName(cache.fileName());
         }
      }
   }

   private void saveToCache() {
      EditorCache.saveCache(
         this.manager.getMatchDataIds(),
         this.manager.getMatchTags(),
         this.manager.getResultDataId(),
         this.manager.getResultTag(),
         this.manager.getCurrentFileName()
      );
      this.showMessage(Component.translatable("message.oneenoughitem.cache_saved").withStyle(ChatFormatting.GREEN));
   }

   protected void init() {
      super.init();
      int centerX = this.width / 2;
      int topY = 15;
      this.manager.setUiUpdateCallback(this::syncManagerDataToWidgets);
      this.objectDropdownButton = GuiUtils.createButton(
         Component.translatable("gui.oneenoughitem.object.select_element"), button -> this.toggleObjectDropdown(), 10, 10, 140, 18
      );
      this.updateObjectDropdownVisibility();
      int fileY = topY + 25;
      this.datapackNameBox = new EditBox(Minecraft.getInstance().font, centerX - 140, fileY, 80, 18, Component.translatable("gui.oneenoughitem.datapack_name"));
      this.datapackNameBox.setHint(Component.translatable("gui.oneenoughitem.datapack_name.hint"));
      this.addRenderableWidget(this.datapackNameBox);
      this.fileNameBox = new EditBox(Minecraft.getInstance().font, centerX - 55, fileY, 80, 18, Component.translatable("gui.oneenoughitem.file_name"));
      this.fileNameBox.setHint(Component.translatable("gui.oneenoughitem.file_name.hint"));
      this.fileNameBox.setValue(this.manager.getCurrentFileName());
      this.addRenderableWidget(this.fileNameBox);
      this.createFileButton = GuiUtils.createButton(
         Component.translatable("gui.oneenoughitem.create_file"), button -> this.createFile(), centerX + 30, fileY, 70, 18
      );
      this.addRenderableWidget(this.createFileButton);
      this.selectFileButton = GuiUtils.createButton(
         Component.translatable("gui.oneenoughitem.select_file"), button -> this.selectFile(), centerX + 105, fileY, 70, 18
      );
      this.addRenderableWidget(this.selectFileButton);
      this.reloadButton = GuiUtils.createButton(
         Component.translatable("gui.oneenoughitem.reload"), button -> this.reloadDatapacks(), centerX - 140, fileY + 25, 60, 18
      );
      this.addRenderableWidget(this.reloadButton);
      this.clearAllButton = GuiUtils.createButton(
         Component.translatable("gui.oneenoughitem.clear_all"), button -> this.clearAll(), centerX - 75, fileY + 25, 60, 18
      );
      this.addRenderableWidget(this.clearAllButton);
      this.saveToJSONButton = GuiUtils.createButton(
         Component.translatable("gui.oneenoughitem.save_to_json"), button -> this.saveToJson(), centerX - 10, fileY + 25, 80, 18
      );
      this.addRenderableWidget(this.saveToJSONButton);
      int webBtnX = centerX - 10 + 80 + 8;
      this.openWebEditorButton = GuiUtils.createButton(
         Component.translatable("gui.oneenoughitem.web_rules_injector"), button -> this.openWebEditor(), webBtnX, fileY + 25, 90, 18
      );
      this.addRenderableWidget(this.openWebEditorButton);
      int panelY = fileY + 55;
      int leftPanelX = centerX - 220 - 8;
      int rightPanelX = centerX + 8;
      int offsetX = 10;
      this.addMatchItemButton = GuiUtils.createButton(
         DomainRegistry.current().selectObjectLabel(), button -> this.openObjectSelection(true), leftPanelX + 5 + offsetX, panelY, 70, 18
      );
      this.addRenderableWidget(this.addMatchItemButton);
      this.addMatchTagButton = GuiUtils.createButton(
         DomainRegistry.current().selectTagLabel(), button -> this.openTagSelection(true), leftPanelX + 70 + 8 + offsetX, panelY, 70, 18
      );
      this.addRenderableWidget(this.addMatchTagButton);
      this.clearMatchButton = GuiUtils.createButton(
         Component.translatable("gui.oneenoughitem.clear"), button -> this.clearMatchData(), leftPanelX + 140 + 11 + offsetX, panelY, 50, 18
      );
      this.addRenderableWidget(this.clearMatchButton);
      int resultButtonSpacing = 10;
      int totalResultButtonWidth = 70 + resultButtonSpacing + 50;
      int resultButtonStartX = rightPanelX + (220 - totalResultButtonWidth) / 2 + offsetX;
      this.selectResultItemButton = GuiUtils.createButton(
         DomainRegistry.current().selectObjectLabel(), button -> this.openObjectSelection(false), resultButtonStartX, panelY, 70, 18
      );
      this.addRenderableWidget(this.selectResultItemButton);
      if (ModUtils.hasAnyDomainModLoaded()) {
         this.domainDropdownButton = GuiUtils.createButton(
            Component.translatable("gui.oneenoughitem.domain_switch", new Object[]{DomainRegistry.current().id()}), b -> {
               this.showDomainDropdown = !this.showDomainDropdown;
               this.updateDomainDropdown();
            }, this.width - 90, 8, 90, 18
         );
         this.addRenderableWidget(this.domainDropdownButton);
      }

      this.clearResultButton = GuiUtils.createButton(
         Component.translatable("gui.oneenoughitem.clear"), button -> this.clearResultData(), resultButtonStartX + 70 + resultButtonSpacing, panelY, 50, 18
      );
      this.addRenderableWidget(this.clearResultButton);
      int editorW = Math.round(272.8F);
      int editorH = Math.round(315.7F);
      int editorY = panelY + 0;
      int leftEditorX = leftPanelX + 5;
      int marginX = Math.round(16.12F);
      int headerGap = Math.round(29.315002F);
      int bottomMargin = Math.round(31.570002F);
      this.matchPanel = new ScrollablePanel(leftEditorX + marginX, editorY + headerGap, editorW - marginX * 2, editorH - headerGap - bottomMargin);
      this.addRenderableWidget(this.matchPanel);
      this.resultPanel = new ScrollablePanel(rightPanelX + 12, panelY + 32, 210, 110);
      this.addRenderableWidget(this.resultPanel);
      this.rebuildPanels();
   }

   private void toggleObjectDropdown() {
      this.showObjectDropdown = !this.showObjectDropdown;
      this.updateObjectDropdown();
   }

   private void syncManagerDataToWidgets() {
      this.matchDataWidgets.clear();
      this.matchTagWidgets.clear();
      this.resultDataWidget = null;
      this.resultTagWidget = null;

      for (String dataId : this.manager.getMatchDataIds()) {
         DataDisplayWidget widget = new DataDisplayWidget(0, 0, dataId, button -> this.removeMatchDataId(dataId), true);
         this.matchDataWidgets.add(widget);
      }

      for (ResourceLocation tagId : this.manager.getMatchTags()) {
         TagDisplayWidget widget = new TagDisplayWidget(0, 0, tagId, button -> this.removeMatchTag(tagId));
         this.matchTagWidgets.add(widget);
      }

      if (this.manager.getResultDataId() != null) {
         this.resultDataWidget = new DataDisplayWidget(0, 0, this.manager.getResultDataId(), null, false);
      }

      if (this.manager.getResultTag() != null) {
         this.resultTagWidget = new TagDisplayWidget(0, 0, this.manager.getResultTag(), null);
      }

      this.rebuildPanels();
   }

   private void removeMatchDataId(String dataId) {
      if (dataId != null) {
         this.manager.removeMatchDataId(dataId);
         this.matchDataWidgets.removeIf(widget -> dataId.equals(widget.getDataId()));
         this.rebuildPanels();
      }
   }

   private void updateObjectDropdown() {
      for (Button b : this.objectIndexButtons) {
         this.removeWidget(b);
      }

      this.objectIndexButtons.clear();
      this.objectDropdownPanel = ObjectDropdownController.rebuildDropdownPanel(
         this.objectDropdownButton, this.showObjectDropdown, this.manager, this.objectIndexButtons, this::selectObjectIndex, this::deleteCurrentObjectElement
      );
      if (this.objectDropdownButton != null) {
         this.objectDropdownButton.setMessage(ObjectDropdownController.buildDropdownButtonText(this.showObjectDropdown, this.manager));
      }
   }

   private void selectObjectIndex(int index) {
      this.manager.setCurrentObjectIndex(index);
      this.showObjectDropdown = false;
      this.updateObjectDropdown();
   }

   private void deleteCurrentObjectElement() {
      if (this.manager.getCurrentObjectIndex() >= 0) {
         this.manager.deleteObjectElement(this.manager.getCurrentObjectIndex());
         this.showObjectDropdown = false;
         this.updateObjectDropdown();
      }
   }

   private void updateObjectDropdownVisibility() {
      if (this.objectDropdownButton != null) {
         boolean shouldShow = this.manager.getCurrentObjectIndex() >= -1 && this.manager.getObjectSize() > 0;
         this.objectDropdownButton.visible = shouldShow;
         if (shouldShow) {
            this.addRenderableWidget(this.objectDropdownButton);
         }
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 83 && (modifiers & 2) != 0) {
         this.saveToCache();
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      int centerX = this.width / 2;
      int topY = 15;
      int fileY = topY + 25;
      int panelY = fileY + 55;
      int leftPanelX = centerX - 220 - 8;
      int rightPanelX = centerX + 8;
      int editorW = Math.round(272.8F);
      int editorH = Math.round(315.7F);
      int editorY = panelY + 0;
      int leftEditorX = leftPanelX + 5;
      int rightEditorX = rightPanelX + 5;
      super.renderBackground(graphics, mouseX, mouseY, partialTick);
      GuiUtils.drawEditorPanel(graphics, leftEditorX, editorY, editorW, editorH);
      GuiUtils.drawEditorPanel(graphics, rightEditorX, editorY, editorW, editorH);
      graphics.drawCenteredString(Minecraft.getInstance().font, this.title, centerX, 20, 16777215);
      Component fileName = this.manager.getCurrentFileName().isEmpty()
         ? Component.translatable("gui.oneenoughitem.current_file.none").withStyle(ChatFormatting.GRAY)
         : Component.literal(this.manager.getCurrentFileName()).withStyle(ChatFormatting.AQUA);
      Component fileInfo = Component.translatable("gui.oneenoughitem.current_file", new Object[]{fileName});
      graphics.drawCenteredString(Minecraft.getInstance().font, fileInfo, centerX, 8, 11184810);
      Component matchComponent = Component.translatable("gui.oneenoughitem.match", new Object[]{this.getDataIdComponent()});
      graphics.drawCenteredString(Minecraft.getInstance().font, matchComponent, leftPanelX + 110, panelY - 12, 16777215);
      Component resultComponent = Component.translatable("gui.oneenoughitem.result", new Object[]{this.getDataIdComponent()});
      graphics.drawCenteredString(Minecraft.getInstance().font, resultComponent, rightPanelX + 110, panelY - 12, 16777215);
      Component summaryComponent = Component.translatable(
         "gui.oneenoughitem.match_summary", new Object[]{this.getDataIdComponent(), this.matchDataWidgets.size(), this.matchTagWidgets.size()}
      );
      graphics.drawString(Minecraft.getInstance().font, summaryComponent, leftPanelX, panelY + 140 + 8, 11184810);
      Component resultText = this.resultDataWidget != null
         ? Component.translatable("gui.oneenoughitem.selected", new Object[]{this.getDataIdComponent()})
         : (this.resultTagWidget != null ? Component.translatable("gui.oneenoughitem.tag_selected") : Component.translatable("gui.oneenoughitem.no_result"));
      graphics.drawString(Minecraft.getInstance().font, resultText, rightPanelX, panelY + 140 + 8, 11184810);
      graphics.drawCenteredString(Minecraft.getInstance().font, Component.translatable("gui.oneenoughitem.save_to_cache"), centerX, panelY + 140 + 25, 16776960);
      if (this.showObjectDropdown && this.objectDropdownPanel != null) {
         this.objectDropdownPanel.render(graphics, mouseX, mouseY, partialTick);
      }

      if (this.showDomainDropdown && this.domainDropdownPanel != null) {
         this.domainDropdownPanel.render(graphics, mouseX, mouseY, partialTick);
      }

      for (Renderable renderable : this.renderables) {
         renderable.render(graphics, mouseX, mouseY, partialTick);
      }
   }

   private void createFile() {
      String datapackName = this.datapackNameBox.getValue().trim();
      String fileName = this.fileNameBox.getValue().trim();
      if (fileName.isEmpty()) {
         this.showError(Component.translatable("error.oneenoughitem.file_name_empty").withStyle(ChatFormatting.RED));
      } else {
         String newName = this.fileActions.createFile(datapackName, fileName);
         this.fileNameBox.setValue(newName);
      }
   }

   private void saveToJson() {
      this.fileActions.saveToJson();
   }

   private void openWebEditor() {
      String message = WebEditorServer.openInBrowser();
      if (message != null) {
         this.showMessage(Component.literal(message).withStyle(ChatFormatting.YELLOW));
      }
   }

   private void selectFile() {
      this.fileActions.selectFile(this.minecraft, this);
   }

   public void onFileSelected(Path filePath, int mode) {
      switch (mode) {
         case 0:
            this.manager.selectJsonFile(filePath, 0);
            this.syncManagerDataToWidgets();
            break;
         case 1:
            this.manager.selectJsonFile(filePath, 1);
            this.syncManagerDataToWidgets();
            break;
         case 2:
            this.manager.deleteFile(filePath);
      }
   }

   private void reloadDatapacks() {
      this.manager.reloadDatapacks();
   }

   private void clearAll() {
      this.manager.clearAll();
      this.matchDataWidgets.clear();
      this.matchTagWidgets.clear();
      this.resultDataWidget = null;
      this.resultTagWidget = null;
      this.fileNameBox.setValue("");
      this.rebuildPanels();
      this.updateObjectDropdownVisibility();
      this.updateObjectDropdown();
      EditorCache.clearCache();
   }

   private void updateDomainDropdown() {
      for (Button b : this.domainButtons) {
         this.removeWidget(b);
      }

      this.domainButtons.clear();
      if (!this.showDomainDropdown) {
         this.domainDropdownPanel = null;
      } else {
         int x = this.domainDropdownButton.getX();
         int y = this.domainDropdownButton.getY() + 18 + 2;
         int w = 120;
         int h = 4 + (DomainRegistry.all().size() - 1) * 22;
         this.domainDropdownPanel = new ScrollablePanel(x, y, w, Math.max(h, 60));
         int btnY = y + 4;
         String currentId = DomainRegistry.current() != null ? DomainRegistry.current().id() : "oei";

         for (Entry<String, DomainAdapter> entry : DomainRegistry.all().entrySet()) {
            String id = entry.getKey();
            if (!id.equalsIgnoreCase(currentId)) {
               Button btn = GuiUtils.createButton(Component.translatable("gui.domain_dropdown.page", new Object[]{id.toUpperCase(Locale.ROOT)}), b -> {
                  if (DomainRegistry.switchTo(id)) {
                     this.onDomainSwitched();
                  }

                  this.showDomainDropdown = false;
                  this.updateDomainDropdown();
               }, x + 4, btnY, w - 8, 18);
               btn.active = DomainRegistry.has(id);
               this.domainButtons.add(btn);
               this.addRenderableWidget(btn);
               btnY += 22;
            }
         }
      }
   }

   private void onDomainSwitched() {
      this.showDomainDropdown = false;
      this.domainDropdownButton.setMessage(Component.translatable("gui.oneenoughitem.domain_switch", new Object[]{DomainRegistry.current().id()}));
      this.addMatchItemButton.setMessage(DomainRegistry.current().selectObjectLabel());
      this.addMatchTagButton.setMessage(DomainRegistry.current().selectTagLabel());
      this.selectResultItemButton.setMessage(DomainRegistry.current().selectObjectLabel());
      this.refreshForCurrentDomain();
   }

   private void refreshForCurrentDomain() {
      this.manager.clearAll();
      this.matchDataWidgets.clear();
      this.matchTagWidgets.clear();
      this.resultDataWidget = null;
      this.resultTagWidget = null;
      if (this.fileNameBox != null) {
         this.fileNameBox.setValue("");
      }

      this.loadFromCache();
      this.rebuildPanels();
      this.updateObjectDropdownVisibility();
      this.showObjectDropdown = false;
      this.updateObjectDropdown();
      this.showDomainDropdown = false;
      this.updateDomainDropdown();
   }

   private void openObjectSelection(boolean isForMatch) {
      this.minecraft.setScreen(DomainRegistry.current().createObjectSelectionScreen(this, isForMatch));
   }

   private void openTagSelection(boolean isForMatch) {
      this.minecraft.setScreen(DomainRegistry.current().createTagSelectionScreen(this, isForMatch));
   }

   private void clearMatchData() {
      this.matchDataWidgets.clear();
      this.matchTagWidgets.clear();
      this.dataController.clearMatchData();
      this.rebuildPanels();
   }

   private void clearResultData() {
      this.resultDataWidget = null;
      this.resultTagWidget = null;
      this.dataController.clearResultData();
      this.rebuildPanels();
   }

   public void addMatchDataId(String dataId) {
      if (dataId != null) {
         String runtimeReplacement = DomainRegistry.current().runtimeCache().matchData(dataId);
         String globalReplacement = DomainRegistry.current().globalCache().getDataReplacement(dataId);
         if (runtimeReplacement != null || globalReplacement != null) {
            this.showError(Component.translatable("error.oneenoughitem.item_already_replaced").withStyle(ChatFormatting.RED));
            return;
         }

         if (DomainRegistry.current().globalCache().isDataUsedAsResult(dataId)) {
            this.showError(Component.translatable("error.oneenoughitem.item_used_as_result").withStyle(ChatFormatting.RED));
            return;
         }
      }

      this.dataController.addMatchDataId(dataId);
      this.syncManagerDataToWidgets();
   }

   public void setResultDataId(String dataId) {
      if (dataId != null) {
         String runtimeReplacement = DomainRegistry.current().runtimeCache().matchData(dataId);
         String globalReplacement = DomainRegistry.current().globalCache().getDataReplacement(dataId);
         if (runtimeReplacement != null || globalReplacement != null) {
            this.showError(Component.translatable("error.oneenoughitem.result_item_already_replaced").withStyle(ChatFormatting.RED));
            return;
         }

         if (DomainRegistry.current().globalCache().isDataReplaced(dataId)) {
            this.showError(Component.translatable("error.oneenoughitem.result_item_used_as_match").withStyle(ChatFormatting.RED));
            return;
         }
      }

      this.dataController.setResultDataId(dataId);
      this.syncManagerDataToWidgets();
   }

   public void addMatchItem(Item item) {
      String id = DomainRegistry.current().dataIdFromItem(item);
      if (id != null) {
         this.addMatchDataId(id);
      }
   }

   public void setResultItem(Item item) {
      String id = DomainRegistry.current().dataIdFromItem(item);
      if (id != null) {
         this.setResultDataId(id);
      }
   }

   public void addMatchTag(ResourceLocation tagId) {
      if (this.manager.getMatchTags().contains(tagId)) {
         this.showWarn(Component.translatable("warning.oneenoughitem.tag_exists").withStyle(ChatFormatting.YELLOW));
      } else if (!DomainRegistry.current().runtimeCache().isTagReplaced(tagId)
         && !DomainRegistry.current().globalCache().isTagReplaced(tagId.toString())
         && !DomainRegistry.current().globalCache().isTagUsedAsResult(tagId.toString())) {
         this.dataController.addMatchTag(tagId);
         this.syncManagerDataToWidgets();
      } else {
         this.showError(Component.translatable("error.oneenoughitem.tag_already_replaced").withStyle(ChatFormatting.RED));
      }
   }

   public void setResultTag(ResourceLocation tagId) {
      this.dataController.setResultTag(tagId);
      this.resultTagWidget = new TagDisplayWidget(0, 0, tagId, null);
      this.resultDataWidget = null;
      this.rebuildPanels();
   }

   private void removeMatchTag(ResourceLocation tagId) {
      this.dataController.removeMatchTag(tagId);
      this.matchTagWidgets.removeIf(widget -> widget.getTagId().equals(tagId));
      this.rebuildPanels();
   }

   protected void rebuildPanels() {
      this.panelsHelper
         .rebuildPanels(this.matchPanel, this.resultPanel, this.matchDataWidgets, this.matchTagWidgets, this.resultDataWidget, this.resultTagWidget);
   }

   private void showError(Component message) {
      if (this.minecraft.player != null) {
         this.minecraft.player.displayClientMessage(message, false);
      }
   }

   private void showWarn(Component message) {
      if (this.minecraft.player != null) {
         this.minecraft.player.displayClientMessage(message, false);
      }
   }

   private void showMessage(Component message) {
      if (this.minecraft.player != null) {
         this.minecraft.player.displayClientMessage(message, false);
      }
   }

   public List<Path> scanReplacementFiles() {
      return this.fileActions.scanReplacementFiles();
   }

   private Component getDataIdComponent() {
      String currentDataId = DomainRegistry.currentDataId().toLowerCase(Locale.ROOT);
      return Component.translatable("gui.oneenoughitem.data_id." + currentDataId);
   }

   public boolean isPauseScreen() {
      return false;
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.showObjectDropdown && this.objectDropdownPanel != null && this.objectDropdownPanel.mouseClicked(mouseX, mouseY, button)) {
         return true;
      } else {
         return this.showObjectDropdown && this.domainDropdownPanel != null && this.domainDropdownPanel.mouseClicked(mouseX, mouseY, button)
            ? true
            : super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (this.showObjectDropdown && this.objectDropdownPanel != null && this.objectDropdownPanel.mouseReleased(mouseX, mouseY, button)) {
         return true;
      } else {
         return this.showObjectDropdown && this.domainDropdownPanel != null && this.domainDropdownPanel.mouseReleased(mouseX, mouseY, button)
            ? true
            : super.mouseReleased(mouseX, mouseY, button);
      }
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      if (this.showObjectDropdown && this.objectDropdownPanel != null && this.objectDropdownPanel.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
         return true;
      } else {
         return this.showObjectDropdown && this.domainDropdownPanel != null && this.domainDropdownPanel.mouseDragged(mouseX, mouseY, button, dragX, dragY)
            ? true
            : super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
      if (this.showObjectDropdown && this.objectDropdownPanel != null && this.objectDropdownPanel.mouseScrolled(mouseX, mouseY, scrollX, scrollY)) {
         return true;
      } else {
         return this.showObjectDropdown && this.domainDropdownPanel != null && this.domainDropdownPanel.mouseScrolled(mouseX, mouseY, scrollX, scrollY)
            ? true
            : super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
      }
   }
}
