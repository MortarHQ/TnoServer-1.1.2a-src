package com.mafuyu404.oneenoughitem.client.gui.components;

import com.mafuyu404.oneenoughitem.client.gui.util.GuiUtils;
import com.mafuyu404.oneenoughitem.client.gui.util.PathUtils;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList.Entry;
import net.minecraft.network.chat.Component;

public class FileListWidget extends ObjectSelectionList<FileListWidget.FileEntry> {
   private final BiConsumer<Path, Integer> onFileSelect;
   private FileListWidget.FileEntry selectedEntry = null;
   private final List<Button> actionButtons = new ArrayList<>();

   public FileListWidget(Minecraft minecraft, int width, int height, int y, int itemHeight, BiConsumer<Path, Integer> onFileSelect) {
      super(minecraft, width, height, y, itemHeight);
      this.onFileSelect = onFileSelect;
   }

   public void setFiles(List<PathUtils.FileInfo> files) {
      this.clearEntries();
      this.clearActionButtons();

      for (PathUtils.FileInfo file : files) {
         this.addEntry(new FileListWidget.FileEntry(file));
      }
   }

   private void clearActionButtons() {
      this.actionButtons.clear();
      this.selectedEntry = null;
   }

   private void showActionButtons(FileListWidget.FileEntry entry) {
      this.clearActionButtons();
      this.selectedEntry = entry;
      int entryIndex = this.children().indexOf(entry);
      int entryX = this.getRowLeft();
      int entryY = this.getRowTop(entryIndex);
      int entryWidth = this.getRowWidth();
      int entryHeight = this.itemHeight;
      int fileNameX = entryX + 16;
      String fileName = entry.fileInfo.displayName();
      String datapackName = "[" + entry.fileInfo.datapackName() + "]";
      String fullPath = entry.fileInfo.fullPath();
      int fileNameWidth = this.minecraft.font.width(fileName);
      int datapackWidth = this.minecraft.font.width(datapackName);
      int pathWidth = this.minecraft.font.width(fullPath);
      int pathX = fileNameX + datapackWidth + 5;
      int contentEndX = Math.max(fileNameX + fileNameWidth, pathX + pathWidth);
      int buttonWidth = 30;
      int buttonHeight = 12;
      int spacing = 2;
      int buttonStartX = contentEndX + 8;
      int maxButtonX = entryX + entryWidth - (buttonWidth * 3 + spacing * 2 + 5);
      if (buttonStartX > maxButtonX) {
         buttonStartX = maxButtonX;
      }

      int buttonY = entryY + (entryHeight - buttonHeight) / 2;
      Button addButton = GuiUtils.createButton(
         Component.translatable("gui.oneenoughitem.add_button"),
         btn -> this.onFileSelect.accept(entry.fileInfo.filePath(), 0),
         buttonStartX,
         buttonY,
         buttonWidth,
         buttonHeight
      );
      Button editButton = GuiUtils.createButton(
         Component.translatable("gui.oneenoughitem.modify_button"),
         btn -> this.onFileSelect.accept(entry.fileInfo.filePath(), 1),
         buttonStartX + buttonWidth + spacing,
         buttonY,
         buttonWidth,
         buttonHeight
      );
      Button deleteButton = GuiUtils.createButton(
         Component.translatable("gui.oneenoughitem.remove_button"),
         btn -> this.onFileSelect.accept(entry.fileInfo.filePath(), 2),
         buttonStartX + (buttonWidth + spacing) * 2,
         buttonY,
         buttonWidth,
         buttonHeight
      );
      this.actionButtons.add(addButton);
      this.actionButtons.add(editButton);
      this.actionButtons.add(deleteButton);
   }

   public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      super.renderWidget(graphics, mouseX, mouseY, partialTick);

      for (Button button : this.actionButtons) {
         button.render(graphics, mouseX, mouseY, partialTick);
      }
   }

   protected void renderListBackground(GuiGraphics guiGraphics) {
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      for (Button actionButton : this.actionButtons) {
         if (actionButton.mouseClicked(mouseX, mouseY, button)) {
            return true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   public class FileEntry extends Entry<FileListWidget.FileEntry> {
      private final PathUtils.FileInfo fileInfo;

      public FileEntry(PathUtils.FileInfo fileInfo) {
         this.fileInfo = fileInfo;
      }

      public void render(
         GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isMouseOver, float partialTick
      ) {
         boolean isSelected = FileListWidget.this.selectedEntry == this;
         GuiUtils.drawFileEntryBackground(graphics, x, y, entryWidth, entryHeight, isMouseOver, isSelected);
         String fileName = this.fileInfo.displayName();
         String iconChar = fileName.isEmpty() ? "?" : String.valueOf(fileName.charAt(0)).toUpperCase();
         GuiUtils.drawFileIcon(graphics, x + 1, y + 4, iconChar);
         int fileNameX = x + 16;
         graphics.drawString(FileListWidget.this.minecraft.font, fileName, fileNameX, y + 2, 16777215);
         String datapackName = "[" + this.fileInfo.datapackName() + "]";
         graphics.drawString(FileListWidget.this.minecraft.font, datapackName, fileNameX, y + 12, 11184810);
         String fullPath = this.fileInfo.fullPath();
         int pathX = fileNameX + FileListWidget.this.minecraft.font.width(datapackName) + 5;
         graphics.drawString(FileListWidget.this.minecraft.font, fullPath, pathX, y + 12, 8947848);
      }

      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         if (button == 0) {
            FileListWidget.this.showActionButtons(this);
            return true;
         } else {
            return false;
         }
      }

      public Component getNarration() {
         return Component.literal(this.fileInfo.displayName() + " in " + this.fileInfo.datapackName());
      }
   }
}
