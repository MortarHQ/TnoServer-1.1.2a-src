package com.mafuyu404.oneenoughitem.client.gui.components;

import com.mafuyu404.oneenoughitem.api.DomainRegistry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList.Entry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TagListWidget extends ObjectSelectionList<TagListWidget.TagEntry> {
   private final Consumer<ResourceLocation> onTagSelect;
   private Set<ResourceLocation> selectedTags = Collections.emptySet();

   public TagListWidget(Minecraft minecraft, int width, int height, int y, int itemHeight, Consumer<ResourceLocation> onTagSelect) {
      super(minecraft, width, height, y, itemHeight);
      this.onTagSelect = onTagSelect;
   }

   public void setTags(List<ResourceLocation> tags) {
      this.clearEntries();

      for (ResourceLocation tag : tags) {
         this.addEntry(new TagListWidget.TagEntry(tag));
      }
   }

   public void setSelectedTags(Set<ResourceLocation> selectedTags) {
      this.selectedTags = selectedTags != null ? selectedTags : Collections.emptySet();
   }

   public TagListWidget.TagEntry getEntryAtMouse(double mouseX, double mouseY) {
      if (mouseX >= this.getX() && mouseX <= this.getX() + this.width && mouseY >= this.getY() && mouseY <= this.getY() + this.height) {
         int relativeY = (int)(mouseY - this.getY() + this.getScrollAmount());
         int index = relativeY / this.itemHeight;
         if (index >= 0 && index < this.children().size()) {
            return (TagListWidget.TagEntry)this.children().get(index);
         }
      }

      return null;
   }

   public class TagEntry extends Entry<TagListWidget.TagEntry> {
      private final ResourceLocation tagId;

      public TagEntry(ResourceLocation tagId) {
         this.tagId = tagId;
      }

      public void render(
         GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isMouseOver, float partialTick
      ) {
         if (TagListWidget.this.selectedTags.contains(this.tagId)) {
            graphics.fill(x, y, x + entryWidth, y + entryHeight, -2144097537);
         }

         String tagText = "#" + this.tagId.toString();
         boolean replaced = DomainRegistry.current().runtimeCache().isTagReplaced(this.tagId)
            || DomainRegistry.current().globalCache().getTagReplacement(this.tagId.toString()) != null;
         int textColor = replaced ? -43691 : -1;
         if (isMouseOver && !replaced) {
            textColor = -120;
         }

         graphics.drawString(TagListWidget.this.minecraft.font, tagText, x + 5, y + 5, textColor);
         if (replaced) {
            DomainRegistry.current().uiAdapter().renderTagIndicators(graphics, this.tagId, x + entryWidth - 20, y + 2);
         }

         if (isMouseOver) {
            graphics.renderComponentTooltip(TagListWidget.this.minecraft.font, this.getTooltip(), mouseX, mouseY);
         }
      }

      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         TagListWidget.this.onTagSelect.accept(this.tagId);
         return true;
      }

      public Component getNarration() {
         return Component.literal("#" + this.tagId.toString());
      }

      public List<Component> getTooltip() {
         List<Component> tooltip = new ArrayList<>();
         tooltip.add(Component.literal("#" + this.tagId.toString()));
         DomainRegistry.current().uiAdapter().addTagTooltip(tooltip, this.tagId);
         tooltip.add(Component.literal(this.tagId.getNamespace()).withStyle(ChatFormatting.BLUE));
         return tooltip;
      }
   }
}
