package com.blamejared.searchables.api.autcomplete;

import com.blamejared.searchables.api.SearchableType;
import com.blamejared.searchables.api.TokenRange;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.joml.Vector2d;

public class AutoComplete<T> extends AbstractWidget implements Consumer<String> {
   private final SearchableType<T> type;
   private final AutoCompletingEditBox<T> editBox;
   private final Supplier<List<T>> entries;
   private final int suggestionHeight;
   private final int maxSuggestions;
   private List<CompletionSuggestion> suggestions;
   private int displayOffset;
   private int selectedIndex;
   private final Vector2d lastMousePosition;
   private int lastCursorPosition;

   public AutoComplete(SearchableType<T> type, AutoCompletingEditBox<T> editBox, Supplier<List<T>> entries, int x, int y, int width, int suggestionHeight) {
      this(type, editBox, entries, x, y, width, suggestionHeight, 7);
   }

   public AutoComplete(
      SearchableType<T> type, AutoCompletingEditBox<T> editBox, Supplier<List<T>> entries, int x, int y, int width, int suggestionHeight, int maxSuggestions
   ) {
      super(x, y, width, suggestionHeight * maxSuggestions, Component.empty());
      this.type = type;
      this.editBox = editBox;
      this.entries = entries;
      this.suggestionHeight = suggestionHeight;
      this.maxSuggestions = maxSuggestions;
      this.suggestions = List.of();
      this.displayOffset = 0;
      this.selectedIndex = -1;
      this.lastMousePosition = new Vector2d(0.0, 0.0);
      this.lastCursorPosition = -1;
   }

   public void accept(String value) {
      int position = this.editBox().getCursorPosition();
      if (this.lastCursorPosition != position) {
         this.displayOffset = 0;
         this.selectedIndex = 0;
         TokenRange replacementRange = this.editBox().completionVisitor().rangeAt(position);
         this.suggestions = this.type.getSuggestionsFor(this.entries.get(), value, position, replacementRange);
      }

      this.lastCursorPosition = position;
   }

   protected boolean clicked(double xpos, double ypos) {
      return super.clicked(xpos, ypos) && ypos < this.getY() + this.suggestionHeight * this.shownSuggestions();
   }

   public boolean isMouseOver(double xpos, double ypos) {
      return super.isMouseOver(xpos, ypos) && ypos < this.getY() + this.suggestionHeight * this.shownSuggestions();
   }

   public boolean mouseScrolled(double xpos, double ypos, double xDelta, double yDelta) {
      if (!this.isMouseOver(xpos, ypos) && !this.editBox().isMouseOver(xpos, ypos)) {
         return false;
      }

      this.displayOffset = (int)Mth.clamp(this.displayOffset - yDelta, 0.0, Math.max(this.suggestions.size() - this.maxSuggestions, 0));
      this.lastMousePosition.set(0.0);
      return true;
   }

   public boolean mouseClicked(double mx, double my, int mb) {
      if (super.mouseClicked(mx, my, mb)) {
         this.updateHoveringState(mx, my);
         if (this.selectedIndex != -1) {
            this.insertSuggestion();
         }

         return true;
      } else {
         return false;
      }
   }

   public void insertSuggestion() {
      int index = this.displayOffset + this.selectedIndex;
      if (index >= 0 && index < this.suggestions.size()) {
         CompletionSuggestion suggestion = this.suggestions.get(index);
         this.editBox.deleteChars(suggestion.replacementRange());
         this.editBox.insertText(suggestion.toInsert());
      }
   }

   public void renderWidget(GuiGraphics guiGraphics, int mx, int my, float partial) {
      if (this.editBox.isFocused()) {
         this.updateHoveringState(mx, my);

         for (int i = this.displayOffset; i < Math.min(this.displayOffset + this.maxSuggestions, this.suggestions.size()); i++) {
            CompletionSuggestion suggestion = this.suggestions.get(i);
            int minX = this.getX() + 2;
            int minY = this.getY() + this.suggestionHeight * (i - this.displayOffset);
            int maxY = minY + this.suggestionHeight;
            boolean hovered = this.selectedIndex != -1 && this.displayOffset + this.selectedIndex == i;
            guiGraphics.fill(RenderType.guiOverlay(), this.getX(), minY, this.getX() + this.getWidth(), maxY, hovered ? -535752431 : -536870912);
            guiGraphics.drawString(
               Minecraft.getInstance().font, suggestion.display(), minX, minY + 1, hovered ? Objects.requireNonNull(ChatFormatting.YELLOW.getColor()) : -1
            );
         }

         this.lastMousePosition.set(mx, my);
      }
   }

   private void updateHoveringState(double xpos, double ypos) {
      if (!this.lastMousePosition.equals(xpos, ypos)) {
         this.selectedIndex = -1;
         if (this.isMouseOver(xpos, ypos)) {
            int minY = this.getY();

            for (int i = 0; i < this.shownSuggestions(); i++) {
               int maxY = minY + this.suggestionHeight;
               if (xpos >= this.getX() && xpos <= this.getX() + this.getWidth() && ypos >= minY && ypos < maxY) {
                  this.selectedIndex = i;
               }

               minY = maxY;
            }
         }
      }
   }

   public void scrollUp() {
      this.scrollUp(1);
   }

   public void scrollUp(int amount) {
      this.offsetDisplay(this.selectedIndex - amount);
   }

   public void scrollDown() {
      this.scrollDown(1);
   }

   public void scrollDown(int amount) {
      this.offsetDisplay(this.selectedIndex + amount);
   }

   private void offsetDisplay(int offset) {
      offset = Mth.clamp(offset, 0, this.shownSuggestions() - 1);
      int halfSuggestions = Math.floorDiv(this.maxSuggestions, 2);
      int currentItem = this.displayOffset + offset;
      if (currentItem < this.displayOffset + halfSuggestions) {
         this.displayOffset = Math.max(currentItem - halfSuggestions, 0);
      } else if (currentItem > this.displayOffset + halfSuggestions) {
         this.displayOffset = Math.min(currentItem - halfSuggestions, Math.max(this.suggestions.size() - this.maxSuggestions, 0));
      }

      this.selectedIndex = currentItem - this.displayOffset;
   }

   private int shownSuggestions() {
      return Math.min(this.maxSuggestions, this.suggestions.size());
   }

   public int maxSuggestions() {
      return this.maxSuggestions;
   }

   public AutoCompletingEditBox<T> editBox() {
      return this.editBox;
   }

   protected void updateWidgetNarration(NarrationElementOutput output) {
   }
}
