package com.mafuyu404.oneenoughitem.client.gui.util;

import com.mafuyu404.oneenoughitem.client.gui.cache.ItemGlobalReplacementCache;
import com.mafuyu404.oneenoughitem.init.ItemReplacementCache;
import com.mafuyu404.oneenoughitem.util.Utils;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ReplacementUtils {
   public static ReplacementUtils.ReplacementInfo getReplacementInfo(String itemId) {
      if (itemId == null) {
         return ReplacementUtils.ReplacementInfo.EMPTY;
      }

      String runtimeReplacement = ItemReplacementCache.matchItem(itemId);
      if (runtimeReplacement != null) {
         return new ReplacementUtils.ReplacementInfo(true, runtimeReplacement, true, ItemGlobalReplacementCache.get().isDataUsedAsResult(itemId));
      }

      String globalReplacement = ItemGlobalReplacementCache.get().getDataReplacement(itemId);
      return globalReplacement != null
         ? new ReplacementUtils.ReplacementInfo(true, globalReplacement, false, ItemGlobalReplacementCache.get().isDataUsedAsResult(itemId))
         : new ReplacementUtils.ReplacementInfo(false, null, false, ItemGlobalReplacementCache.get().isDataUsedAsResult(itemId));
   }

   public static ReplacementUtils.ReplacementInfo getTagReplacementInfo(ResourceLocation tagId) {
      String runtimeReplacement = ItemReplacementCache.matchTag(tagId);
      if (runtimeReplacement != null) {
         return new ReplacementUtils.ReplacementInfo(true, runtimeReplacement, true, false);
      }

      String globalReplacement = ItemGlobalReplacementCache.get().getTagReplacement(tagId.toString());
      return globalReplacement != null ? new ReplacementUtils.ReplacementInfo(true, globalReplacement, false, false) : ReplacementUtils.ReplacementInfo.EMPTY;
   }

   public static class ReplacementIndicator {
      public static void renderItemReplaced(GuiGraphics graphics, int x, int y) {
         graphics.fill(x, y, x + 5, y + 5, -65536);
         graphics.drawString(Minecraft.getInstance().font, "R", x + 1, y, -1, false);
      }

      public static void renderItemUsedAsResult(GuiGraphics graphics, int x, int y) {
         graphics.fill(x, y, x + 5, y + 5, -16733696);
         graphics.drawString(Minecraft.getInstance().font, "T", x + 1, y, -1, false);
      }

      public static void renderTagReplaced(GuiGraphics graphics, int x, int y) {
         graphics.fill(x, y, x + 15, y + 15, -65536);
         graphics.drawString(Minecraft.getInstance().font, "R", x + 5, y + 4, -1, false);
      }

      public static void renderTagUsedAsResult(GuiGraphics graphics, int x, int y) {
         graphics.fill(x, y, x + 15, y + 15, -16733696);
         graphics.drawString(Minecraft.getInstance().font, "T", x + 5, y + 4, -1, false);
      }
   }

   public static class ReplacementInfo {
      public static final ReplacementUtils.ReplacementInfo EMPTY = new ReplacementUtils.ReplacementInfo(false, null, false, false);
      private final boolean isReplaced;
      private final String replacement;
      private final boolean isRuntime;
      private final boolean isUsedAsResult;

      public ReplacementInfo(boolean isReplaced, String replacement, boolean isRuntime, boolean isUsedAsResult) {
         this.isReplaced = isReplaced;
         this.replacement = replacement;
         this.isRuntime = isRuntime;
         this.isUsedAsResult = isUsedAsResult;
      }

      public boolean isReplaced() {
         return this.isReplaced;
      }

      public boolean isUsedAsResult() {
         return this.isUsedAsResult;
      }

      public void addToTooltip(List<Component> tooltip) {
         if (this.isReplaced) {
            tooltip.add(Component.translatable("tooltip.oneenoughitem.item_replaced").withStyle(ChatFormatting.RED));
            if (this.replacement.startsWith("#")) {
               tooltip.add(Component.translatable("tooltip.oneenoughitem.replaced_with_tag", new Object[]{this.replacement}).withStyle(ChatFormatting.RED));
            } else {
               Item replacementItem = Utils.getItemById(this.replacement);
               if (replacementItem != null) {
                  tooltip.add(
                     Component.translatable("tooltip.oneenoughitem.replaced_with_item", new Object[]{new ItemStack(replacementItem).getHoverName().getString()})
                        .withStyle(ChatFormatting.AQUA)
                  );
               } else {
                  tooltip.add(Component.translatable("tooltip.oneenoughitem.replaced_with_item", new Object[]{this.replacement}).withStyle(ChatFormatting.AQUA));
               }
            }

            tooltip.add(
               Component.translatable(this.isRuntime ? "tooltip.oneenoughitem.source_runtime" : "tooltip.oneenoughitem.source_saved")
                  .withStyle(ChatFormatting.YELLOW)
            );
         }

         if (this.isUsedAsResult) {
            tooltip.add(Component.translatable("tooltip.oneenoughitem.item_used_as_result").withStyle(ChatFormatting.GREEN));
         }
      }
   }
}
