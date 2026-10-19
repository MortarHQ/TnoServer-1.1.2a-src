package dev.ftb.mods.ftbquests.item;

import dev.ftb.mods.ftbquests.registry.ModDataComponents;
import dev.ftb.mods.ftbquests.registry.ModItems;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class MissingItem extends Item {
   public MissingItem() {
      super(ModItems.defaultProps().stacksTo(1));
   }

   public Component getName(ItemStack stack) {
      return (Component)(stack.has((DataComponentType)ModDataComponents.MISSING_ITEM_DESC.get())
         ? Component.literal((String)stack.get((DataComponentType)ModDataComponents.MISSING_ITEM_DESC.get()))
         : super.getName(stack));
   }

   @OnlyIn(Dist.CLIENT)
   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      if (stack.has((DataComponentType)ModDataComponents.MISSING_ITEM_DESC.get())) {
         tooltip.add(Component.translatable("item.ftbquests.missing_item").withStyle(ChatFormatting.RED));
      }
   }
}
