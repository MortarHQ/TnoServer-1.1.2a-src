package com.sighs.oneenoughblock.api.adapter;

import com.mafuyu404.oneenoughitem.api.DomainAdapter;
import com.mafuyu404.oneenoughitem.api.DomainRuntimeCache;
import com.mafuyu404.oneenoughitem.api.ReplacementUiAdapter;
import com.mafuyu404.oneenoughitem.client.gui.ReplacementEditorScreen;
import com.mafuyu404.oneenoughitem.client.gui.cache.AbstractGlobalReplacementCache;
import com.mafuyu404.oneenoughitem.client.gui.util.GuiUtils;
import com.mafuyu404.oneenoughitem.util.ReplacementControl;
import com.sighs.oneenoughblock.api.adapter.ui.BlockReplacementUiAdapter;
import com.sighs.oneenoughblock.client.gui.BlockSelectionScreen;
import com.sighs.oneenoughblock.client.gui.BlockTagSelectionScreen;
import com.sighs.oneenoughblock.client.gui.cache.GlobalBlockReplacementCache;
import com.sighs.oneenoughblock.init.BlockReplacementCache;
import java.util.Collection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockDomainAdapter implements DomainAdapter {
   public String modId() {
      return "oneenoughblock";
   }

   public String id() {
      return "oeb";
   }

   public String dataId() {
      return "Blocks";
   }

   public Component selectObjectLabel() {
      return Component.translatable("gui.oneenoughblock.add_block");
   }

   public Component selectTagLabel() {
      return Component.translatable("gui.oneenoughblock.add_block_tag");
   }

   @OnlyIn(Dist.CLIENT)
   public Screen createObjectSelectionScreen(ReplacementEditorScreen parent, boolean isForMatch) {
      return new BlockSelectionScreen(parent, isForMatch);
   }

   @OnlyIn(Dist.CLIENT)
   public Screen createTagSelectionScreen(ReplacementEditorScreen parent, boolean isForMatch) {
      return new BlockTagSelectionScreen(parent, isForMatch);
   }

   public boolean isAvailable() {
      return true;
   }

   public AbstractGlobalReplacementCache globalCache() {
      return GlobalBlockReplacementCache.get();
   }

   public DomainRuntimeCache runtimeCache() {
      return new DomainRuntimeCache() {
         public String matchData(String id) {
            return BlockReplacementCache.matchBlock(id);
         }

         public String matchTag(ResourceLocation tagId) {
            return BlockReplacementCache.matchTag(tagId);
         }

         public void removeReplacements(Collection<String> dataIds, Collection<String> tagIds) {
            BlockReplacementCache.removeReplacements(dataIds, tagIds);
         }

         public boolean isTagReplaced(String tagId) {
            return BlockReplacementCache.isTagReplaced(tagId);
         }

         public boolean isTagReplaced(ResourceLocation tagId) {
            return BlockReplacementCache.isTagReplaced(tagId);
         }
      };
   }

   public String dataIdFromItem(Item item) {
      if (item instanceof BlockItem bi) {
         ResourceLocation key = BuiltInRegistries.BLOCK.getKey(bi.getBlock());
         return key.toString();
      } else {
         return null;
      }
   }

   public ItemStack iconForDataId(String dataId) {
      ResourceLocation rl = ResourceLocation.tryParse(dataId);
      Block block = rl != null ? (Block)BuiltInRegistries.BLOCK.get(rl) : null;
      return block == null ? ItemStack.EMPTY : (ItemStack)ReplacementControl.withSkipReplacement(() -> new ItemStack(block.asItem()));
   }

   @OnlyIn(Dist.CLIENT)
   public void renderDataId(GuiGraphics graphics, String dataId, int x, int y) {
      ItemStack stack = this.iconForDataId(dataId);
      GuiUtils.drawItemBox(graphics, x, y, 18, 18);
      graphics.renderItem(stack, x + 1, y + 1);
      graphics.renderItemDecorations(Minecraft.getInstance().font, stack, x + 1, y + 1);
   }

   public Component displayName(String dataId) {
      ResourceLocation rl = ResourceLocation.tryParse(dataId);
      Block block = rl != null ? (Block)BuiltInRegistries.BLOCK.get(rl) : null;
      if (block == null) {
         return Component.literal(dataId);
      }

      ItemStack stack = new ItemStack(block.asItem());
      return stack.getHoverName();
   }

   public ReplacementUiAdapter uiAdapter() {
      return new BlockReplacementUiAdapter(this.runtimeCache(), this.globalCache());
   }
}
