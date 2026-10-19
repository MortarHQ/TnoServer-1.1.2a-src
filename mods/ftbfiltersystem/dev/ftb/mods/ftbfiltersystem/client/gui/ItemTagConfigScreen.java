package dev.ftb.mods.ftbfiltersystem.client.gui;

import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterConfigScreen;
import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterScreen;
import dev.ftb.mods.ftbfiltersystem.client.gui.widget.CustomSelectionList;
import dev.ftb.mods.ftbfiltersystem.filter.ItemTagFilter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class ItemTagConfigScreen extends AbstractFilterConfigScreen<ItemTagFilter> {
   private static String lastSearch;
   private final List<TagKey<Item>> matchingTags = new ArrayList<>();
   private EditBox searchField;
   private ItemTagConfigScreen.ItemTagList itemTagList;

   public ItemTagConfigScreen(ItemTagFilter filter, AbstractFilterScreen parentScreen) {
      super(filter, parentScreen, 200, 176);
   }

   @Override
   protected void init() {
      super.init();
      this.searchField = this.makeSearchEditBox(this.leftPos + 8, this.topPos + 18, () -> lastSearch, s -> lastSearch = s);
      this.updateSearchEntries();
      this.itemTagList = new ItemTagConfigScreen.ItemTagList(this.minecraft, this.topPos + 32, this.getListWidth(), this.getListHeight());
      this.itemTagList.setX(this.leftPos + 8);
      this.addWidget(this.itemTagList);
      this.itemTagList
         .children()
         .stream()
         .filter(child -> child.tagKey.equals(this.filter.getTagKey()))
         .findFirst()
         .ifPresent(entry -> this.itemTagList.selectAndCenter(entry));
   }

   @Override
   public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
      this.itemTagList.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
   }

   @Nullable
   protected ItemTagFilter makeNewFilter() {
      if (this.itemTagList.getSelected() != null) {
         return new ItemTagFilter(this.filter.getParent(), ((ItemTagConfigScreen.ItemTagList.ItemTagEntry)this.itemTagList.getSelected()).tagKey);
      }

      ResourceLocation location;
      return this.itemTagList.children().isEmpty() && (location = ResourceLocation.tryParse(this.searchField.getValue())) != null
         ? new ItemTagFilter(this.filter.getParent(), TagKey.create(Registries.ITEM, location))
         : null;
   }

   @Override
   protected void doScheduledUpdate() {
      this.updateSearchEntries();
      this.itemTagList.addChildren();
   }

   private int getListHeight() {
      return 148;
   }

   private int getListWidth() {
      return 176;
   }

   private void updateSearchEntries() {
      String srch = this.searchField.getValue().toLowerCase(Locale.ROOT);
      this.matchingTags.clear();
      this.matchingTags
         .addAll(
            BuiltInRegistries.ITEM
               .getTagNames()
               .filter(tagKey -> srch.isEmpty() || tagKey.location().toString().toLowerCase(Locale.ROOT).contains(srch))
               .sorted(Comparator.comparing(TagKey::location))
               .toList()
         );
   }

   private class ItemTagList extends CustomSelectionList<ItemTagConfigScreen.ItemTagList.ItemTagEntry> {
      private static final int ELEMENT_HEIGHT = 12;

      public ItemTagList(Minecraft minecraft, int y, int width, int height) {
         super(minecraft, width, height, y, 12);
      }

      protected void renderListBackground(GuiGraphics guiGraphics) {
      }

      @Override
      protected List<ItemTagConfigScreen.ItemTagList.ItemTagEntry> buildChildrenList() {
         return ItemTagConfigScreen.this.matchingTags.stream().map(x$0 -> new ItemTagConfigScreen.ItemTagList.ItemTagEntry((TagKey<Item>)x$0)).toList();
      }

      private class ItemTagEntry extends CustomSelectionList.Entry<ItemTagConfigScreen.ItemTagList.ItemTagEntry> {
         private final TagKey<Item> tagKey;

         private ItemTagEntry(TagKey<Item> tagKey) {
            this.tagKey = tagKey;
         }

         public void render(
            GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick
         ) {
            Component txt = Component.literal(this.tagKey.location().getNamespace())
               .withStyle(Style.EMPTY.withColor(2105440))
               .append(Component.literal(":"))
               .append(Component.literal(this.tagKey.location().getPath()).withStyle(Style.EMPTY.withColor(8405024)));
            guiGraphics.drawString(ItemTagConfigScreen.this.font, txt, left + 1, top + 1, 4210752, false);
         }

         @Override
         protected boolean onMouseClick(double x, double y, int button, boolean isDoubleClick) {
            ItemTagList.this.setSelected(this);
            if (isDoubleClick) {
               ItemTagConfigScreen.this.applyChanges();
            }

            return true;
         }

         public Component getNarration() {
            return Component.translatable("narrator.select", new Object[]{this.tagKey.location().toString()});
         }
      }
   }
}
