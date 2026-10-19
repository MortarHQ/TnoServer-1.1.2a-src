package io.github.Memoires.mysticism.item.misc;

import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class StrangeKeyLeftItem extends Item {
   public StrangeKeyLeftItem() {
      super(new Properties().arch$tab(TensuraCreativeTabs.MISCELLANEOUS).fireResistant().rarity(Rarity.RARE));
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
      list.add(Component.translatable("tooltip.mysticism.strange_key_left").withStyle(ChatFormatting.DARK_GRAY));
   }
}
