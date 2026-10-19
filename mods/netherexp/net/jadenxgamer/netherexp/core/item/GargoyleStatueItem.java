package net.jadenxgamer.netherexp.core.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class GargoyleStatueItem extends BlockItem {
   public GargoyleStatueItem(Block block, Properties properties) {
      super(block, properties);
   }

   @NotNull
   public String getDescriptionId() {
      return "block.netherexp.gargoyle_statue";
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
      tooltip.add(Component.translatable(this.getBlock().getDescriptionId()).withStyle(ChatFormatting.DARK_AQUA));
      super.appendHoverText(stack, context, tooltip, tooltipFlag);
   }
}
