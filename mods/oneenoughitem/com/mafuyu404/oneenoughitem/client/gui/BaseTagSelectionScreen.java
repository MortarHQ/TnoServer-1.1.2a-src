package com.mafuyu404.oneenoughitem.client.gui;

import com.mafuyu404.oneenoughitem.api.DomainRegistry;
import com.mafuyu404.oneenoughitem.client.gui.cache.AbstractGlobalReplacementCache;
import com.mafuyu404.oneenoughitem.client.gui.components.TagListWidget;
import com.mafuyu404.oneenoughitem.client.gui.util.GuiUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class BaseTagSelectionScreen extends Screen {
   protected final ReplacementEditorScreen parent;
   protected final boolean isForMatch;
   protected EditBox searchBox;
   protected Button backButton;
   protected TagListWidget tagList;
   protected List<ResourceLocation> allTags;
   protected List<ResourceLocation> filteredTags;
   protected final Set<ResourceLocation> selectedTags = new HashSet<>();
   protected Button confirmSelectionButton;
   protected Button clearSelectionButton;
   private int lastSearchLength = 0;

   protected BaseTagSelectionScreen(ReplacementEditorScreen parent, boolean isForMatch, Component title) {
      super(title);
      this.parent = parent;
      this.isForMatch = isForMatch;
      this.allTags = new ArrayList<>();
      this.filteredTags = new ArrayList<>();
   }

   protected abstract List<ResourceLocation> loadAllTags();

   private void onSelectTag(ResourceLocation tagId) {
      String tagStr = tagId != null ? tagId.toString() : null;
      if (tagStr != null) {
         AbstractGlobalReplacementCache global = DomainRegistry.current().globalCache();
         if (!global.isTagUsedAsResult(tagStr) && !global.isTagReplaced(tagStr)) {
            if (this.isForMatch) {
               this.parent.addMatchTag(tagId);
            } else {
               this.parent.setResultTag(tagId);
            }
         }
      }
   }

   protected void init() {
      int centerX = this.width / 2;
      this.searchBox = new EditBox(this.font, centerX - 80, 15, 160, 18, Component.translatable("gui.oneenoughitem.search"));
      this.addRenderableWidget(this.searchBox);
      this.tagList = new TagListWidget(this.minecraft, this.width - 40, this.height - 100, 40, 22, this::handleTagClick);
      this.tagList.setX(20);
      this.tagList.setSelectedTags(this.selectedTags);
      this.addRenderableWidget(this.tagList);
      if (this.isForMatch) {
         int buttonY = this.height - 50;
         int totalWidth = 290;
         int startX = centerX - totalWidth / 2;
         this.confirmSelectionButton = GuiUtils.createButton(
            Component.translatable("gui.oneenoughitem.add_selected"), btn -> this.confirmSelectedTags(), startX, buttonY, 100, 18
         );
         this.addRenderableWidget(this.confirmSelectionButton);
         this.backButton = GuiUtils.createButton(Component.translatable("gui.oneenoughitem.back"), btn -> this.onClose(), startX + 110, buttonY, 80, 18);
         this.addRenderableWidget(this.backButton);
         this.clearSelectionButton = GuiUtils.createButton(Component.translatable("gui.oneenoughitem.clear_selected"), btn -> {
            this.selectedTags.clear();
            this.updateTagList();
            this.updateConfirmButtonsVisibility();
         }, startX + 200, buttonY, 100, 18);
         this.addRenderableWidget(this.clearSelectionButton);
         this.updateConfirmButtonsVisibility();
      } else {
         this.backButton = GuiUtils.createButton(
            Component.translatable("gui.oneenoughitem.back"), btn -> this.onClose(), centerX - 40, this.height - 50, 80, 18
         );
         this.addRenderableWidget(this.backButton);
      }

      this.allTags = this.loadAllTags();
      this.filteredTags = new ArrayList<>(this.allTags);
      this.updateTagList();
   }

   public void tick() {
      super.tick();
      if (this.searchBox.getValue().length() != this.getLastSearchLength()) {
         this.filterTags();
         this.updateTagList();
      }
   }

   private int getLastSearchLength() {
      int current = this.lastSearchLength;
      this.lastSearchLength = this.searchBox.getValue().length();
      return current;
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      int panelX = 20;
      int panelY = 40;
      int panelW = this.width - 40;
      int panelH = this.height - 100;
      GuiUtils.drawPanelBackground(graphics, panelX, panelY, panelW, panelH);
      super.render(graphics, mouseX, mouseY, partialTick);
      String tagCount = this.filteredTags.size() + " tags";
      graphics.drawString(this.font, tagCount, 10, this.height - 30, 16777215);
   }

   protected void filterTags() {
      String search = this.searchBox.getValue().toLowerCase();
      if (search.isEmpty()) {
         this.filteredTags = new ArrayList<>(this.allTags);
      } else {
         this.filteredTags = this.allTags.stream().filter(tag -> tag.toString().toLowerCase().contains(search)).toList();
      }
   }

   protected void updateTagList() {
      this.tagList.setTags(this.filteredTags);
   }

   private void handleTagClick(ResourceLocation tagId) {
      if (this.isForMatch && hasControlDown()) {
         if (this.selectedTags.contains(tagId)) {
            this.selectedTags.remove(tagId);
         } else {
            this.selectedTags.add(tagId);
         }

         this.updateTagList();
         this.updateConfirmButtonsVisibility();
      } else {
         this.onSelectTag(tagId);
         this.onClose();
      }
   }

   protected void confirmSelectedTags() {
      if (this.isForMatch && !this.selectedTags.isEmpty()) {
         for (ResourceLocation tagId : new ArrayList<>(this.selectedTags)) {
            this.onSelectTag(tagId);
         }

         this.selectedTags.clear();
         this.updateTagList();
         this.updateConfirmButtonsVisibility();
         this.onClose();
      }
   }

   protected void updateConfirmButtonsVisibility() {
      if (this.isForMatch) {
         boolean hasSelection = !this.selectedTags.isEmpty();
         if (this.confirmSelectionButton != null) {
            this.confirmSelectionButton.active = hasSelection;
         }

         if (this.clearSelectionButton != null) {
            this.clearSelectionButton.active = hasSelection;
         }
      }
   }

   public void onClose() {
      this.minecraft.setScreen(this.parent);
   }
}
