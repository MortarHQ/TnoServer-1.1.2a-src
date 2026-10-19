package it.hurts.octostudios.reliquified_twilight_forest.item;

import it.hurts.sskirillss.relics.init.CreativeTabRegistry;
import it.hurts.sskirillss.relics.items.misc.CreativeContentConstructor;
import it.hurts.sskirillss.relics.items.misc.ICreativeTabContent;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.ItemLike;

public class GemItem extends Item implements Gem, ICreativeTabContent {
   public GemItem() {
      super(new Properties().rarity(Rarity.UNCOMMON).stacksTo(16));
   }

   public boolean isFoil(ItemStack stack) {
      return true;
   }

   public GemItem(Properties properties) {
      super(properties);
   }

   public Component getTipText(ItemStack stack, TooltipContext context) {
      return Component.translatable("item.reliquified_twilight_forest.gem.tip").withStyle(ChatFormatting.GRAY);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
      tooltipComponents.add(this.getTipText(stack, context));
   }

   public void gatherCreativeTabContent(CreativeContentConstructor creativeContentConstructor) {
      creativeContentConstructor.entry((CreativeModeTab)CreativeTabRegistry.RELICS_TAB.get(), TabVisibility.PARENT_AND_SEARCH_TABS, new ItemLike[]{this});
   }
}
