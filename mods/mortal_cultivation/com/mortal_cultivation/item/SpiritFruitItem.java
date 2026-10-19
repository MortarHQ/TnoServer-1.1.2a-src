package com.mortal_cultivation.item;

import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public final class SpiritFruitItem extends Item {
   public SpiritFruitItem(Properties properties) {
      super(properties);
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
      ItemStack result = super.finishUsingItem(stack, level, entity);
      if (!level.isClientSide && entity instanceof Player player) {
         double maximum = EnergyHelper.getMaxMagicule(player);
         if (maximum > 0.0) {
            EnergyHelper.gainMagicule(player, maximum * 0.01, GainType.NORMAL);
         }
      }

      return result;
   }
}
