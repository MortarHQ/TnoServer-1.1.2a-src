package com.mortal_cultivation.item;

import com.mortal_cultivation.block.entity.AncientScholarTableBlockEntity;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public final class AncientBookItem extends Item {
   private static final int PROGRESS_SEGMENTS = 8;
   private static final String PROGRESS_SYMBOL = "■";

   public AncientBookItem(Properties properties) {
      super(properties);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(stack, context, tooltip, flag);
      int connectedCells = AncientScholarTableBlockEntity.getConnectedDecipherCellCount(stack);
      int filledSegments = connectedCells * 8 / 24;
      MutableComponent progress = Component.translatable("item.mortal_cultivation.ancient_book.decipher_progress")
         .withStyle(ChatFormatting.GRAY)
         .append(Component.literal("[").withStyle(ChatFormatting.DARK_GRAY));

      for (int segment = 0; segment < 8; segment++) {
         progress.append(Component.literal("■").withStyle(segment < filledSegments ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY));
      }

      progress.append(Component.literal("]").withStyle(ChatFormatting.DARK_GRAY));
      tooltip.add(progress);
   }
}
