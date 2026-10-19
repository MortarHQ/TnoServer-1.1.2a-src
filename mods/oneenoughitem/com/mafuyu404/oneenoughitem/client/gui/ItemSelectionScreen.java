package com.mafuyu404.oneenoughitem.client.gui;

import com.mafuyu404.oneenoughitem.client.gui.cache.ItemGlobalReplacementCache;
import com.mafuyu404.oneenoughitem.client.gui.util.GuiUtils;
import com.mafuyu404.oneenoughitem.init.ItemReplacementCache;
import com.mafuyu404.oneenoughitem.util.ReplacementControl;
import com.mafuyu404.oneenoughitem.util.Utils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class ItemSelectionScreen extends BaseObjectSelectionScreen<Item> {
   public ItemSelectionScreen(ReplacementEditorScreen parent, boolean isForMatch) {
      super(parent, isForMatch, Component.translatable("gui.oneenoughitem.item_selection.title"));
      this.allObjects = this.loadAllObjects();
      this.filteredObjects = new ArrayList<>(this.allObjects);
   }

   @Override
   protected Component sortLabel() {
      return Component.translatable("gui.oneenoughitem.sort.name");
   }

   @Override
   protected List<Item> loadAllObjects() {
      return BuiltInRegistries.ITEM.stream().filter(item -> item != Items.AIR).collect(Collectors.toList());
   }

   protected String getId(Item obj) {
      return Utils.getItemRegistryName(obj);
   }

   protected String getName(Item obj) {
      return obj.getDescription().getString();
   }

   protected void renderObject(Item obj, GuiGraphics graphics, int x, int y) {
      ItemStack stack = ReplacementControl.withSkipReplacement(() -> new ItemStack(obj));
      GuiUtils.drawItemBox(graphics, x, y, 18, 18);
      graphics.renderItem(stack, x + 1, y + 1);
      graphics.renderItemDecorations(Minecraft.getInstance().font, stack, x + 1, y + 1);
   }

   @Override
   protected void onSelectSingle(String id) {
      ResourceLocation rl = ResourceLocation.tryParse(id);
      if (rl != null) {
         Item item = (Item)BuiltInRegistries.ITEM.get(rl);
         if (item != Items.AIR) {
            if (this.isForMatch) {
               this.parent.addMatchItem(item);
            } else {
               this.parent.setResultItem(item);
            }
         }
      }
   }

   @Override
   protected boolean isSelectable(String id, boolean forMatch) {
      String runtimeReplacement = ItemReplacementCache.matchItem(id);
      String globalReplacement = ItemGlobalReplacementCache.get().getDataReplacement(id);
      if (runtimeReplacement != null || globalReplacement != null) {
         return false;
      } else {
         return forMatch ? !ItemGlobalReplacementCache.isItemUsedAsResult(id) : !ItemGlobalReplacementCache.get().isDataReplaced(id);
      }
   }

   @Override
   protected void onSort() {
      super.onSort();
   }

   @Override
   protected Comparator<Item> getComparator(BaseObjectSelectionScreen.SortMode mode) {
      return switch (mode) {
         case NAME -> Comparator.comparing(item -> {
            ItemStack stack = ReplacementControl.withSkipReplacement(() -> new ItemStack(item));
            return stack.getHoverName().getString();
         });
         case MOD -> Comparator.comparing(item -> Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)).getNamespace());
         case ID -> Comparator.comparing(item -> Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)).toString());
      };
   }

   @Override
   protected void updateGrid() {
      if (this.itemGrid != null) {
         List<ItemStack> pageItems = new ArrayList<>();
         int startIndex = this.currentPage * 45;
         int endIndex = Math.min(startIndex + 45, this.filteredObjects.size());

         for (int i = startIndex; i < endIndex; i++) {
            int finalI = i;
            ItemStack itemStack = ReplacementControl.withSkipReplacement(() -> new ItemStack((ItemLike)this.filteredObjects.get(finalI)));
            pageItems.add(itemStack);
         }

         this.itemGrid.setItems(pageItems);
         this.itemGrid.setSelectedItemIds(this.selectedIds);
      }
   }
}
