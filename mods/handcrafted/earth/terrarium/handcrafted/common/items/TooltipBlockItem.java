package earth.terrarium.handcrafted.common.items;

import earth.terrarium.handcrafted.common.utils.TooltipUtils;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.block.Block;

public class TooltipBlockItem extends BlockItem {
   private final Component tooltip;

   public TooltipBlockItem(Block block, Component tooltip, Properties properties) {
      super(block, properties);
      this.tooltip = tooltip;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      TooltipUtils.addDescriptionComponent(tooltipComponents, this.tooltip);
   }
}
