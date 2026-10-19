package twilightforest.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class MinotaurAxeItem extends AxeItem {
   public MinotaurAxeItem(Tier material, Properties properties) {
      super(material, properties);
   }

   public int getEnchantmentValue() {
      return Tiers.GOLD.getEnchantmentValue();
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
      super.appendHoverText(stack, context, tooltip, flags);
      tooltip.add(Component.translatable("item.twilightforest.minotaur_axe.desc").withStyle(ChatFormatting.GRAY));
   }
}
