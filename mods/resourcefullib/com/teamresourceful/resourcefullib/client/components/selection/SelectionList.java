package com.teamresourceful.resourcefullib.client.components.selection;

import com.teamresourceful.resourcefullib.client.scissor.CloseableScissorStack;
import com.teamresourceful.resourcefullib.client.utils.RenderUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SelectionList<T extends ListEntry> extends AbstractContainerEventHandler implements Renderable, NarratableEntry {
   private final List<T> entries = new ArrayList<>();
   private final int x;
   private final int y;
   private final int width;
   private final int height;
   private final int itemHeight;
   private final Consumer<@Nullable T> onSelection;
   private final boolean relativeClicks;
   @Nullable
   private T selected;
   @Nullable
   private T hovered;
   private double scrollAmount;

   public SelectionList(int x, int y, int width, int height, int itemHeight, Consumer<@Nullable T> onSelection) {
      this(x, y, width, height, itemHeight, onSelection, false);
   }

   public SelectionList(int x, int y, int width, int height, int itemHeight, Consumer<@Nullable T> onSelection, boolean relativeClicks) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.itemHeight = itemHeight;
      this.onSelection = onSelection;
      this.relativeClicks = relativeClicks;
   }

   public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.hovered = this.getEntryAtPosition(mouseX, mouseY);

      try (CloseableScissorStack scissorStack = RenderUtils.createScissor(Minecraft.getInstance(), graphics, this.x, this.y, this.width, this.height)) {
         for (int i = 0; i < this.entries.size(); i++) {
            int scrollY = this.y - (int)this.scrollAmount + i * this.itemHeight;
            if (scrollY + this.itemHeight >= this.y && scrollY <= this.y + this.height) {
               ListEntry entry = this.entries.get(i);
               entry.render(
                  graphics,
                  scissorStack.stack(),
                  i,
                  this.x,
                  scrollY,
                  this.width,
                  this.itemHeight,
                  mouseX,
                  mouseY,
                  this.isHoveredItem(i),
                  partialTicks,
                  this.isSelectedItem(i)
               );
            }
         }
      }
   }

   @NotNull
   public List<T> children() {
      return this.entries;
   }

   public void addEntry(@NotNull T entry) {
      this.entries.add(entry);
   }

   public boolean removeEntry(T entry) {
      boolean removed = this.entries.remove(entry);
      if (removed && entry == this.selected) {
         this.setSelected(null);
      }

      return removed;
   }

   protected boolean isSelectedItem(int index) {
      return Objects.equals(this.selected, this.children().get(index));
   }

   protected boolean isHoveredItem(int index) {
      return Objects.equals(this.hovered, this.children().get(index));
   }

   @Nullable
   protected final T getEntryAtPosition(double mouseX, double mouseY) {
      if (!this.isMouseOver(mouseX, mouseY)) {
         return null;
      }

      int index = Mth.floor(this.scrollAmount + (mouseY - this.y)) / this.itemHeight;
      return index >= 0 && index < this.children().size() ? this.children().get(index) : null;
   }

   public void ensureVisible(T entry) {
      int i = (int)(this.y - this.scrollAmount + this.children().indexOf(entry) * this.itemHeight);
      int j = i - this.y - this.itemHeight;
      if (j < 0) {
         this.setScrollAmount(this.scrollAmount + j);
      }

      int k = this.y + this.height - i - this.itemHeight - this.itemHeight;
      if (k < 0) {
         this.setScrollAmount(this.scrollAmount - k);
      }
   }

   public boolean isMouseOver(double mouseX, double mouseY) {
      return mouseX <= this.x + this.width && mouseX >= this.x && mouseY <= this.y + this.height && mouseY >= this.y;
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      T entry = this.getEntryAtPosition(mouseX, mouseY);
      if (entry != null) {
         if (this.relativeClicks) {
            int scrollY = this.y - (int)this.scrollAmount + this.children().indexOf(entry) * this.itemHeight;
            mouseX -= this.x;
            mouseY -= scrollY;
         }

         if (entry.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(entry);
            this.setDragging(true);
         }

         this.setSelected(entry);
         return true;
      } else {
         return false;
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      for (int i = 0; i < this.entries.size(); i++) {
         int scrollY = this.y - (int)this.scrollAmount + i * this.itemHeight;
         T entry = this.entries.get(i);
         if (this.relativeClicks) {
            if (entry.mouseReleased(mouseX - this.x, mouseY - scrollY, button)) {
               return true;
            }
         } else if (entry.mouseReleased(mouseX, mouseY, button)) {
            return true;
         }
      }

      return false;
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalScroll, double verticalScroll) {
      this.setScrollAmount(this.scrollAmount - verticalScroll * this.itemHeight / 2.0);
      return true;
   }

   public boolean keyPressed(int key, int point, int mod) {
      if (super.keyPressed(key, point, mod)) {
         return true;
      }

      if (key != 265 && key != 264) {
         return false;
      }

      if (!this.children().isEmpty()) {
         int index = this.children().indexOf(this.selected);
         int clampedIndex = Mth.clamp(index + (key == 265 ? -1 : 1), 0, this.entries.size() - 1);
         if (index == clampedIndex) {
            return true;
         }

         T entry = this.children().get(clampedIndex);
         this.setSelected(entry);
         this.ensureVisible(entry);
      }

      return true;
   }

   public void setSelected(@Nullable T entry) {
      this.selected = entry;
      this.onSelection.accept(this.selected);
   }

   protected void setScrollAmount(double amount) {
      this.scrollAmount = Mth.clamp(amount, 0.0, Math.max(0, this.entries.size() * this.itemHeight - this.height));
   }

   public void updateEntries(List<? extends T> entries) {
      this.scrollAmount = 0.0;
      this.selected = null;
      this.hovered = null;
      this.entries.clear();
      entries.forEach(this::addEntry);
   }

   @NotNull
   public NarrationPriority narrationPriority() {
      return NarrationPriority.NONE;
   }

   public void updateNarration(@NotNull NarrationElementOutput output) {
   }

   public T getSelected() {
      return this.selected;
   }

   public T getHovered() {
      return this.hovered;
   }

   public double getScrollAmount() {
      return this.scrollAmount;
   }
}
