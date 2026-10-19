package com.chronodawn.items.consumables;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class TimelessMushroomSoupItem extends Item {
   public TimelessMushroomSoupItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      FoodProperties foodProperties = new Builder()
         .nutrition(6)
         .saturationModifier(0.6F)
         .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 1.0F)
         .build();
      return new Properties().food(foodProperties).craftRemainder(Items.BOWL).stacksTo(1);
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
      ItemStack result = super.finishUsingItem(stack, level, entity);
      if (entity instanceof Player player && !player.getAbilities().instabuild) {
         return new ItemStack(Items.BOWL);
      } else {
         return result.isEmpty() ? new ItemStack(Items.BOWL) : result;
      }
   }
}
