package com.sighs.oneenoughblock.client.gui;

import com.mafuyu404.oneenoughitem.client.gui.BaseObjectSelectionScreen;
import com.mafuyu404.oneenoughitem.client.gui.ReplacementEditorScreen;
import com.mafuyu404.oneenoughitem.client.gui.BaseObjectSelectionScreen.SortMode;
import com.mafuyu404.oneenoughitem.client.gui.util.GuiUtils;
import com.mafuyu404.oneenoughitem.util.ReplacementControl;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class BlockSelectionScreen extends BaseObjectSelectionScreen<Block> {
   public BlockSelectionScreen(ReplacementEditorScreen parent, boolean isForMatch) {
      super(parent, isForMatch, Component.literal("gui.oneenoughblock.add_block"));
      this.allObjects = this.loadAllObjects();
      this.filteredObjects = new ArrayList(this.allObjects);
      this.applySort();
   }

   protected Component sortLabel() {
      return Component.translatable("gui.oneenoughitem.sort.name");
   }

   protected List<Block> loadAllObjects() {
      return BuiltInRegistries.BLOCK.stream().filter(b -> b.asItem() != Items.AIR).collect(Collectors.toList());
   }

   protected String getId(Block obj) {
      ResourceLocation key = BuiltInRegistries.BLOCK.getKey(obj);
      return key.toString();
   }

   protected String getName(Block obj) {
      return obj.getName().getString();
   }

   protected void renderObject(Block obj, GuiGraphics graphics, int x, int y) {
      ItemStack stack = (ItemStack)ReplacementControl.withSkipReplacement(() -> new ItemStack(obj.asItem()));
      GuiUtils.drawItemBox(graphics, x, y, 18, 18);
      graphics.renderItem(stack, x + 1, y + 1);
      graphics.renderItemDecorations(Minecraft.getInstance().font, stack, x + 1, y + 1);
   }

   protected void onSelectSingle(String id) {
      ResourceLocation key = ResourceLocation.tryParse(id);
      if (key != null) {
         Block block = (Block)BuiltInRegistries.BLOCK.get(key);
         if (block.asItem() != Items.AIR) {
            if (this.isForMatch) {
               this.parent.addMatchItem(block.asItem());
            } else {
               this.parent.setResultItem(block.asItem());
            }
         }
      }
   }

   protected boolean isSelectable(String id, boolean forMatch) {
      ResourceLocation key = ResourceLocation.tryParse(id);
      if (key == null) {
         return false;
      }

      Block block = (Block)BuiltInRegistries.BLOCK.get(key);
      return block.asItem() != Items.AIR;
   }

   protected Comparator<Block> getComparator(SortMode mode) {
      return switch (mode) {
         case NAME -> Comparator.comparing(b -> new ItemStack(b.asItem()).getHoverName().getString());
         case MOD -> Comparator.comparing(b -> Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(b)).getNamespace());
         case ID -> Comparator.comparing(b -> Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(b)).toString());
         default -> throw new MatchException(null, null);
      };
   }

   protected void updateGrid() {
      if (this.itemGrid != null) {
         List<ItemStack> pageItems = new ArrayList<>();
         int startIndex = this.currentPage * 45;
         int endIndex = Math.min(startIndex + 45, this.filteredObjects.size());

         for (int i = startIndex; i < endIndex; i++) {
            Block b = (Block)this.filteredObjects.get(i);
            ItemStack stack = (ItemStack)ReplacementControl.withSkipReplacement(() -> new ItemStack(b.asItem()));
            pageItems.add(stack);
         }

         this.itemGrid.setItems(pageItems);
         this.itemGrid.setSelectedItemIds(this.selectedIds);
      }
   }
}
