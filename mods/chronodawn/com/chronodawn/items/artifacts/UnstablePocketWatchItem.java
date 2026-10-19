package com.chronodawn.items.artifacts;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class UnstablePocketWatchItem extends Item {
   public UnstablePocketWatchItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).rarity(Rarity.EPIC);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (!level.isClientSide()) {
         UnstablePocketWatchSwapHandler.swapEffects(player, level);
      }

      return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
   }
}
