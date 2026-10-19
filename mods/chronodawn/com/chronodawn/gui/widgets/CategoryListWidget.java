package com.chronodawn.gui.widgets;

import com.chronodawn.gui.ChronicleScreen;
import com.chronodawn.gui.data.Category;
import com.chronodawn.gui.data.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CategoryListWidget extends AbstractWidget {
   private final List<Category> categories;
   private Category selectedCategory;
   private Entry selectedEntry;
   private final CategoryListWidget.EntrySelectionCallback selectionCallback;
   private final Map<Category, Boolean> expandedCategories = new HashMap<>();
   private int scrollOffset = 0;
   private String hoveredTooltipText = null;
   private static final int TITLE_HEIGHT = 20;
   private static final int CATEGORY_HEIGHT = 20;
   private static final int ENTRY_HEIGHT = 18;
   private static final int ICON_SIZE = 16;
   private static final int PADDING = 2;
   private static final int ENTRY_INDENT = 10;
   private static final int SCROLL_AMOUNT = 18;

   public CategoryListWidget(int x, int y, int width, int height, List<Category> categories, CategoryListWidget.EntrySelectionCallback selectionCallback) {
      super(x, y, width, height, Component.empty());
      this.categories = categories;
      this.selectionCallback = selectionCallback;
      if (!categories.isEmpty()) {
         this.selectedCategory = categories.get(0);
         this.expandedCategories.put(this.selectedCategory, true);
         if (!this.selectedCategory.getEntries().isEmpty()) {
            this.selectedEntry = this.selectedCategory.getEntries().get(0);
            if (selectionCallback != null) {
               selectionCallback.onEntrySelected(this.selectedEntry);
            }
         }
      }
   }

   public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      graphics.enableScissor(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height);
      this.hoveredTooltipText = null;
      int currentY = this.getY() - this.scrollOffset;
      String languageCode = ChronicleScreen.getLanguageCode();
      if (currentY + 20 > this.getY() && currentY < this.getY() + this.height) {
         Component title = Component.translatable("gui.chronodawn.chronicle.title");
         int titleX = this.getX() + (this.width - Minecraft.getInstance().font.width(title)) / 2;
         int titleY = currentY + 6;
         graphics.drawString(Minecraft.getInstance().font, title, titleX, titleY, 4144959, false);
      }

      currentY += 20;

      for (Category category : this.categories) {
         boolean isExpanded = this.expandedCategories.getOrDefault(category, false);
         if (currentY + 20 > this.getY() && currentY < this.getY() + this.height) {
            boolean isCategoryHovered = mouseX >= this.getX() && mouseX < this.getX() + this.width && mouseY >= currentY && mouseY < currentY + 20;
            boolean isCategorySelected = category == this.selectedCategory;
            int backgroundColor = isCategorySelected ? 1087422624 : (isCategoryHovered ? 550551712 : 0);
            if (backgroundColor != 0) {
               graphics.fill(this.getX(), currentY, this.getX() + this.width, currentY + 20, backgroundColor);
            }

            String expandIndicator = isExpanded ? "▼" : "▶";
            graphics.drawString(Minecraft.getInstance().font, expandIndicator, this.getX() + 2, currentY + 6, 6250335, false);
            ItemStack icon = this.getIconStack(category);
            graphics.renderItem(icon, this.getX() + 2 + 10, currentY + 2);
            String title = category.getTitle().get(languageCode);
            int textX = this.getX() + 16 + 4 + 10;
            int textY = currentY + 6;
            int textColor = isCategorySelected ? 4144959 : 6250335;
            int maxCategoryWidth = this.width - 16 - 4 - 12;
            String displayTitle = Minecraft.getInstance().font.plainSubstrByWidth(title, maxCategoryWidth);
            graphics.drawString(Minecraft.getInstance().font, displayTitle, textX, textY, textColor, false);
            if (isCategoryHovered && !displayTitle.equals(title)) {
               this.hoveredTooltipText = title;
            }
         }

         currentY += 20;
         if (isExpanded) {
            for (Entry entry : category.getEntries()) {
               if (currentY + 18 > this.getY() && currentY < this.getY() + this.height) {
                  boolean isEntryHovered = mouseX >= this.getX() && mouseX < this.getX() + this.width && mouseY >= currentY && mouseY < currentY + 18;
                  boolean isEntrySelected = entry == this.selectedEntry;
                  int entryBgColor = isEntrySelected ? 815829119 : (isEntryHovered ? 362844287 : 0);
                  if (entryBgColor != 0) {
                     graphics.fill(this.getX(), currentY, this.getX() + this.width, currentY + 18, entryBgColor);
                  }

                  ItemStack entryIcon = this.getIconStack(entry);
                  graphics.renderItem(entryIcon, this.getX() + 10 + 2, currentY + 1);
                  String entryTitle = entry.getTitle().get(languageCode);
                  int entryTextX = this.getX() + 10 + 16 + 2;
                  int entryTextY = currentY + 5;
                  int entryTextColor = isEntrySelected ? 3092271 : 7303023;
                  int maxWidth = this.width - 10 - 16 - 4;
                  String displayText = Minecraft.getInstance().font.plainSubstrByWidth(entryTitle, maxWidth);
                  graphics.drawString(Minecraft.getInstance().font, displayText, entryTextX, entryTextY, entryTextColor, false);
                  if (isEntryHovered && !displayText.equals(entryTitle)) {
                     this.hoveredTooltipText = entryTitle;
                  }
               }

               currentY += 18;
            }
         }
      }

      graphics.disableScissor();
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button != 0) {
         return false;
      }

      int currentY = this.getY() - this.scrollOffset;
      currentY += 20;

      for (Category category : this.categories) {
         boolean isExpanded = this.expandedCategories.getOrDefault(category, false);
         if (mouseX >= this.getX()
            && mouseX < this.getX() + this.width
            && mouseY >= currentY
            && mouseY < currentY + 20
            && mouseY >= this.getY()
            && mouseY < this.getY() + this.height) {
            this.expandedCategories.put(category, !isExpanded);
            this.selectedCategory = category;
            return true;
         }

         currentY += 20;
         if (isExpanded) {
            for (Entry entry : category.getEntries()) {
               if (mouseX >= this.getX()
                  && mouseX < this.getX() + this.width
                  && mouseY >= currentY
                  && mouseY < currentY + 18
                  && mouseY >= this.getY()
                  && mouseY < this.getY() + this.height) {
                  this.selectedCategory = category;
                  this.selectedEntry = entry;
                  if (this.selectionCallback != null) {
                     this.selectionCallback.onEntrySelected(entry);
                  }

                  Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
                  return true;
               }

               currentY += 18;
            }
         }
      }

      return false;
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
      if (mouseX >= this.getX() && mouseX < this.getX() + this.width && mouseY >= this.getY() && mouseY < this.getY() + this.height) {
         int totalHeight = 20;

         for (Category category : this.categories) {
            totalHeight += 20;
            if (this.expandedCategories.getOrDefault(category, false)) {
               totalHeight += category.getEntries().size() * 18;
            }
         }

         this.scrollOffset -= (int)(scrollY * 18.0);
         int maxScroll = Math.max(0, totalHeight - this.height);
         this.scrollOffset = Math.max(0, Math.min(this.scrollOffset, maxScroll));
         return true;
      } else {
         return false;
      }
   }

   private ItemStack getIconStack(Category category) {
      ResourceLocation iconId = category.getIcon();
      Item item = (Item)BuiltInRegistries.ITEM.get(iconId);
      return item != null && item != Items.AIR ? new ItemStack(item) : new ItemStack(Items.BOOK);
   }

   private ItemStack getIconStack(Entry entry) {
      ResourceLocation iconId = entry.getIcon();
      Item item = (Item)BuiltInRegistries.ITEM.get(iconId);
      return item != null && item != Items.AIR ? new ItemStack(item) : new ItemStack(Items.PAPER);
   }

   public void playDownSound(SoundManager soundManager) {
   }

   protected void updateWidgetNarration(NarrationElementOutput output) {
      if (this.selectedEntry != null) {
         String languageCode = ChronicleScreen.getLanguageCode();
         output.add(NarratedElementType.TITLE, Component.literal(this.selectedEntry.getTitle().get(languageCode)));
      } else if (this.selectedCategory != null) {
         String languageCode = ChronicleScreen.getLanguageCode();
         output.add(NarratedElementType.TITLE, Component.literal(this.selectedCategory.getTitle().get(languageCode)));
      }
   }

   public String getHoveredTooltip() {
      return this.hoveredTooltipText;
   }

   public interface EntrySelectionCallback {
      void onEntrySelected(Entry var1);
   }
}
