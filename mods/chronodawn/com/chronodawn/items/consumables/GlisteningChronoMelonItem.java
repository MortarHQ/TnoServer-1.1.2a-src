package com.chronodawn.items.consumables;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class GlisteningChronoMelonItem extends Item {
   public GlisteningChronoMelonItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      FoodProperties foodProperties = new Builder()
         .nutrition(2)
         .saturationModifier(1.2F)
         .effect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 0), 1.0F)
         .build();
      return new Properties().food(foodProperties);
   }
}
