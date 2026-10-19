package net.blay09.mods.waystones.item;

import java.util.List;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystonesAPI;
import net.blay09.mods.waystones.block.entity.WaystoneBlockEntityBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.UseOnContext;

public class BlankScrollItem extends Item {
   public BlankScrollItem(Properties properties) {
      super(properties.stacksTo(64));
   }

   public InteractionResult useOn(UseOnContext context) {
      if (!context.getLevel().isClientSide
         && context.getLevel().getBlockEntity(context.getClickedPos()) instanceof WaystoneBlockEntityBase waystoneBlockEntityBase) {
         Waystone waystone = waystoneBlockEntityBase.getWaystone();
         ItemStack boundScrollStack = new ItemStack(ModItems.boundScroll);
         WaystonesAPI.setBoundWaystone(boundScrollStack, waystone);
         Player player = context.getPlayer();
         int emptySlot = player.getInventory().getFreeSlot();
         int stackableSlot = player.getInventory().getSlotWithRemainingSpace(boundScrollStack);
         if (emptySlot != -1 || stackableSlot != -1 || !player.hasInfiniteMaterials() && context.getItemInHand().getCount() == 1) {
            context.getItemInHand().consume(1, player);
            if (!player.addItem(boundScrollStack)) {
               player.drop(boundScrollStack, false);
            }

            return InteractionResult.SUCCESS;
         } else {
            return InteractionResult.FAIL;
         }
      } else {
         return super.useOn(context);
      }
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> list, TooltipFlag flag) {
      list.add(Component.translatable("tooltip.waystones.blank_scroll").withStyle(ChatFormatting.GRAY));
   }
}
