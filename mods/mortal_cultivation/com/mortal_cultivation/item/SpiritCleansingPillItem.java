package com.mortal_cultivation.item;

import com.mortal_cultivation.spiritroot.SpiritRootClientState;
import com.mortal_cultivation.spiritroot.SpiritRootData;
import com.mortal_cultivation.spiritroot.SpiritRootType;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class SpiritCleansingPillItem extends Item {
   public SpiritCleansingPillItem(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      if (level.isClientSide) {
         return SpiritRootClientState.getCurrentRoot().isPresent()
            ? ItemUtils.startUsingInstantly(level, player, hand)
            : InteractionResultHolder.fail(player.getItemInHand(hand));
      } else if (!SpiritRootData.hasRoot(player)) {
         player.sendSystemMessage(Component.translatable("mortal_cultivation.spirit_root.refreshed.none").withStyle(ChatFormatting.GRAY));
         return InteractionResultHolder.fail(player.getItemInHand(hand));
      } else {
         return ItemUtils.startUsingInstantly(level, player, hand);
      }
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
      ItemStack resultStack = super.finishUsingItem(stack, level, livingEntity);
      if (!level.isClientSide && livingEntity instanceof ServerPlayer player && SpiritRootData.hasRoot(player)) {
         SpiritRootType root = SpiritRootData.refreshRoot(player);
         player.sendSystemMessage(
            Component.translatable("mortal_cultivation.spirit_root.refreshed", new Object[]{root.getDisplayName()}).withStyle(ChatFormatting.AQUA)
         );
      }

      return resultStack;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(stack, context, tooltip, flag);
      tooltip.add(Component.translatable("item.mortal_cultivation.spirit_cleansing_pill.desc").withStyle(ChatFormatting.GRAY));
   }
}
