package com.chronodawn.items.consumables;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
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

public class ChronoMelonJuiceItem extends Item {
   public ChronoMelonJuiceItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      FoodProperties foodProperties = new Builder()
         .nutrition(4)
         .saturationModifier(0.4F)
         .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0), 1.0F)
         .build();
      return new Properties().food(foodProperties).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
      super.finishUsingItem(stack, level, entity);
      if (entity instanceof ServerPlayer serverPlayer) {
         CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
         serverPlayer.awardStat(Stats.ITEM_USED.get(this));
      }

      if (stack.isEmpty()) {
         return new ItemStack(Items.GLASS_BOTTLE);
      }

      if (entity instanceof Player player && !player.getAbilities().instabuild) {
         ItemStack bottleStack = new ItemStack(Items.GLASS_BOTTLE);
         if (!player.getInventory().add(bottleStack)) {
            player.drop(bottleStack, false);
         }
      }

      return stack;
   }
}
