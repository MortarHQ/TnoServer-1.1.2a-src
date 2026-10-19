package com.mortal_cultivation.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class TalismanManualItem extends Item {
   private static final String UNLOCKED_TAG = "talisman_making_unlocked";

   public TalismanManualItem(Properties properties) {
      super(properties);
   }

   public static boolean hasUnlockedTalismanMaking(Player player) {
      CompoundTag playerData = player.getPersistentData();
      if (!playerData.contains("PlayerPersisted")) {
         return false;
      }

      CompoundTag persistedData = playerData.getCompound("PlayerPersisted");
      return !persistedData.contains("mortal_cultivation") ? false : persistedData.getCompound("mortal_cultivation").getBoolean("talisman_making_unlocked");
   }

   private static void unlockTalismanMaking(Player player) {
      CompoundTag playerData = player.getPersistentData();
      CompoundTag persistedData = playerData.getCompound("PlayerPersisted");
      CompoundTag modData = persistedData.getCompound("mortal_cultivation");
      modData.putBoolean("talisman_making_unlocked", true);
      persistedData.put("mortal_cultivation", modData);
      playerData.put("PlayerPersisted", persistedData);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      if (level.isClientSide) {
         return InteractionResultHolder.sidedSuccess(itemStack, true);
      }

      try {
         if (hasUnlockedTalismanMaking(player)) {
            player.displayClientMessage(Component.translatable("mortal_cultivation.item.talisman_manual.already_unlocked"), true);
            return InteractionResultHolder.success(itemStack);
         }

         unlockTalismanMaking(player);
         player.displayClientMessage(Component.translatable("mortal_cultivation.item.talisman_manual.unlocked"), true);
         if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
         }

         return InteractionResultHolder.success(itemStack);
      } catch (Exception exception) {
         player.displayClientMessage(Component.translatable("mortal_cultivation.item.talisman_manual.use_failed", new Object[]{exception.getMessage()}), true);
         return InteractionResultHolder.fail(itemStack);
      }
   }
}
