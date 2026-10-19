package xaero.hud.category.ui.entry;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import xaero.hud.category.ui.GuiCategoryEditor;
import xaero.lib.client.gui.widget.Tooltip;

public abstract class EditorListEntry {
   protected final int entryRelativeX;
   protected final int entryRelativeY;
   protected final int entryW;
   protected final int entryH;
   protected final int index;
   protected final GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList;
   protected final List<EditorListEntry> subEntries;
   protected final Supplier<Tooltip> tooltipSupplier;
   protected int focusedSubEntryIndex;
   protected EditorListEntry hoveredSubEntry;

   public EditorListEntry(
      int entryX, int entryY, int entryW, int entryH, int index, GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList, Supplier<Tooltip> tooltipSupplier
   ) {
      this.entryRelativeX = entryX;
      this.entryRelativeY = entryY;
      this.entryW = entryW;
      this.entryH = entryH;
      this.index = index;
      this.rowList = rowList;
      this.subEntries = new ArrayList<>();
      this.focusedSubEntryIndex = -1;
      this.tooltipSupplier = tooltipSupplier;
   }

   public EditorListEntry onSelected() {
      if (!this.subEntries.isEmpty() && this.focusedSubEntryIndex >= 0) {
         EditorListEntry subEntry = this.subEntries.get(this.focusedSubEntryIndex);
         return subEntry.onSelected();
      }

      if (!this.selectAction()) {
         return this;
      }

      if (!(this instanceof EditorListEntryWidget)) {
         Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      }

      this.rowList.updateEntries();
      return this;
   }

   public boolean mouseClicked(GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList.Entry entry, double relativeMouseX, double relativeMouseY, int i) {
      for (int subIndex = 0; subIndex < this.subEntries.size(); subIndex++) {
         EditorListEntry subEntry = this.subEntries.get(subIndex);
         if (subEntry.isHoveredOver(relativeMouseX, relativeMouseY)) {
            double subRelativeMouseX = relativeMouseX - subEntry.entryRelativeX;
            double subRelativeMouseY = relativeMouseY - subEntry.entryRelativeY;
            if (this.focusedSubEntryIndex != subIndex) {
               this.unfocusRecursively();
               this.focusedSubEntryIndex = subIndex;
            }

            if (!subEntry.mouseClicked(entry, subRelativeMouseX, subRelativeMouseY, subIndex)) {
               subEntry.confirmSelection();
            }

            return true;
         }
      }

      return false;
   }

   public EditorListEntry confirmSelection() {
      return this.focusedSubEntryIndex >= 0 ? this.subEntries.get(this.focusedSubEntryIndex).confirmSelection() : this.onSelected();
   }

   public boolean mouseReleased(double relativeMouseX, double relativeMouseY, int i) {
      for (EditorListEntry subEntry : this.subEntries) {
         double subRelativeMouseX = relativeMouseX - subEntry.entryRelativeX;
         double subRelativeMouseY = relativeMouseY - subEntry.entryRelativeY;
         subEntry.mouseReleased(subRelativeMouseX, subRelativeMouseY, i);
      }

      return false;
   }

   public boolean mouseScrolled(double relativeMouseX, double relativeMouseY, double f, double g) {
      for (EditorListEntry subEntry : this.subEntries) {
         double subRelativeMouseX = relativeMouseX - subEntry.entryRelativeX;
         double subRelativeMouseY = relativeMouseY - subEntry.entryRelativeY;
         if (subEntry.isHoveredOver(relativeMouseX, relativeMouseY)) {
            return subEntry.mouseScrolled(subRelativeMouseX, subRelativeMouseY, f, g);
         }
      }

      return false;
   }

   public boolean mouseDragged(double relativeMouseX, double relativeMouseY, int i, double f, double g) {
      for (EditorListEntry subEntry : this.subEntries) {
         double subRelativeMouseX = relativeMouseX - subEntry.entryRelativeX;
         double subRelativeMouseY = relativeMouseY - subEntry.entryRelativeY;
         subEntry.mouseDragged(subRelativeMouseX, subRelativeMouseY, i, f, g);
      }

      return false;
   }

   public void mouseMoved(double relativeMouseX, double relativeMouseY) {
   }

   public boolean keyPressed(int i, int j, int k, boolean isRoot) {
      if (isRoot) {
         if (i == 263 && this.moveFocus(-1)) {
            return false;
         }

         if (i == 262 && this.moveFocus(1)) {
            return false;
         }
      }

      if (!this.subEntries.isEmpty() && this.focusedSubEntryIndex >= 0) {
         EditorListEntry subEntry = this.subEntries.get(this.focusedSubEntryIndex);
         return subEntry.keyPressed(i, j, k, false);
      } else {
         return false;
      }
   }

   public boolean keyReleased(int i, int j, int k) {
      if (this.subEntries.isEmpty()) {
         return false;
      }

      for (EditorListEntry subEntry : this.subEntries) {
         subEntry.keyReleased(i, j, k);
      }

      return false;
   }

   public boolean charTyped(char c, int i) {
      if (!this.subEntries.isEmpty() && this.focusedSubEntryIndex >= 0) {
         EditorListEntry subEntry = this.subEntries.get(this.focusedSubEntryIndex);
         return subEntry.charTyped(c, i);
      } else {
         return false;
      }
   }

   public void tick() {
      if (!this.subEntries.isEmpty()) {
         for (EditorListEntry subEntry : this.subEntries) {
            subEntry.tick();
         }
      }
   }

   public String getSubNarration() {
      return this.hoveredSubEntry == null ? this.getSelectedNarration() : this.getHoveredNarration();
   }

   public String getHoveredNarration() {
      return this.hoveredSubEntry == null ? this.getHoverNarration() : this.hoveredSubEntry.getHoveredNarration();
   }

   public String getSelectedNarration() {
      if (this.focusedSubEntryIndex == -1) {
         return this.getNarration();
      }

      EditorListEntry subEntry = this.subEntries.get(this.focusedSubEntryIndex);
      return subEntry.getSelectedNarration();
   }

   public Supplier<Tooltip> getTooltipSupplier() {
      return this.tooltipSupplier;
   }

   public abstract Component getMessage();

   public Component getNarrationMessage() {
      return this.getMessage();
   }

   public String getNarration() {
      StringBuilder narrationBuilder = new StringBuilder();
      narrationBuilder.append(this.getNarrationMessage().getString());
      if (this.tooltipSupplier == null) {
         return narrationBuilder.toString();
      }

      Tooltip tooltip = this.tooltipSupplier.get();
      if (tooltip != null) {
         narrationBuilder.append(" . ").append(this.tooltipSupplier.get().getPlainText());
      }

      return narrationBuilder.toString();
   }

   public String getHoverNarration() {
      return this.getNarration();
   }

   public void preRender(GuiGraphics guiGraphics, boolean includesSelected, boolean isRoot) {
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(this.entryRelativeX, this.entryRelativeY, 0.0F);
      if (includesSelected && this.focusedSubEntryIndex == -1) {
         guiGraphics.fill(0, 0, this.entryW, this.entryH, this.rowList.isFocused() ? -1 : -8355712);
         guiGraphics.fill(1, 1, this.entryW - 1, this.entryH - 1, -16777216);
      }
   }

   public EditorListEntry render(
      GuiGraphics guiGraphics,
      int index,
      int rowWidth,
      int rowHeight,
      int relativeMouseX,
      int relativeMouseY,
      boolean isMouseOver,
      float partialTicks,
      Font font,
      int globalMouseX,
      int globalMouseY,
      boolean includesSelected,
      boolean isRoot
   ) {
      this.hoveredSubEntry = null;
      EditorListEntry result = isMouseOver ? this : null;

      for (int i = 0; i < this.subEntries.size(); i++) {
         EditorListEntry subEntry = this.subEntries.get(i);
         boolean subIsHovered = subEntry.isHoveredOver(relativeMouseX, relativeMouseY);
         boolean subIncludesSelected = includesSelected && this.focusedSubEntryIndex == i;
         subEntry.preRender(guiGraphics, subIncludesSelected, false);
         EditorListEntry subResult = subEntry.render(
            guiGraphics,
            index,
            rowWidth,
            rowHeight,
            relativeMouseX - subEntry.entryRelativeX,
            relativeMouseY - subEntry.entryRelativeY,
            subIsHovered,
            partialTicks,
            font,
            globalMouseX,
            globalMouseY,
            subIncludesSelected,
            false
         );
         subEntry.postRender(guiGraphics);
         if (subIsHovered) {
            this.hoveredSubEntry = subEntry;
            result = subResult;
         }
      }

      return result;
   }

   public void postRender(GuiGraphics guiGraphics) {
      PoseStack poseStack = guiGraphics.pose();
      poseStack.popPose();
   }

   public boolean isHoveredOver(double relativeMouseX, double relativeMouseY) {
      return relativeMouseX >= this.entryRelativeX
         && relativeMouseX < this.entryRelativeX + this.entryW
         && relativeMouseY >= this.entryRelativeY
         && relativeMouseY < this.entryRelativeY + this.entryH;
   }

   protected abstract boolean selectAction();

   public void setFocused(boolean bl) {
   }

   public void unhoverRecursively() {
      if (this.hoveredSubEntry != null) {
         this.hoveredSubEntry.unhoverRecursively();
         this.hoveredSubEntry = null;
      }
   }

   public boolean moveFocus(int direction) {
      this.unhoverRecursively();
      if (!this.moveFocus(direction, true)) {
         return false;
      }

      this.rowList.narrateSelection();
      return true;
   }

   public boolean moveFocus(int direction, boolean isRoot) {
      if (this.subEntries.isEmpty()) {
         return false;
      }

      if (this.focusedSubEntryIndex >= 0) {
         EditorListEntry focusedSub = this.subEntries.get(this.focusedSubEntryIndex);
         if (focusedSub.moveFocus(direction, false)) {
            return true;
         }
      }

      int potentialValue = this.focusedSubEntryIndex + direction;
      if (potentialValue < 0 || potentialValue >= this.subEntries.size()) {
         if (!isRoot) {
            return false;
         }

         potentialValue = potentialValue < 0 ? this.subEntries.size() - 1 : 0;
      }

      if (this.focusedSubEntryIndex == potentialValue) {
         return false;
      } else {
         this.focusedSubEntryIndex = potentialValue;
         EditorListEntry focusedSub = this.subEntries.get(this.focusedSubEntryIndex);
         if (direction < 0) {
            focusedSub.focusLastRecursively();
            return true;
         } else {
            focusedSub.focusFirstRecursively();
            return true;
         }
      }
   }

   public void unfocusRecursively() {
      this.setFocused(false);
      if (!this.subEntries.isEmpty()) {
         if (this.focusedSubEntryIndex >= 0) {
            this.subEntries.get(this.focusedSubEntryIndex).unfocusRecursively();
         }

         this.focusedSubEntryIndex = -1;
      }
   }

   public void focusFirstRecursively() {
      this.setFocused(true);
      if (!this.subEntries.isEmpty()) {
         this.focusedSubEntryIndex = 0;
         this.subEntries.get(this.focusedSubEntryIndex).focusFirstRecursively();
      }
   }

   public void focusLastRecursively() {
      this.setFocused(true);
      if (!this.subEntries.isEmpty()) {
         this.focusedSubEntryIndex = this.subEntries.size() - 1;
         this.subEntries.get(this.focusedSubEntryIndex).focusLastRecursively();
      }
   }

   public EditorListEntry withSubEntry(EditorListEntry entry) {
      this.subEntries.add(entry);
      return this;
   }

   public int getEntryRelativeX() {
      return this.entryRelativeX;
   }

   public int getEntryRelativeY() {
      return this.entryRelativeY;
   }
}
