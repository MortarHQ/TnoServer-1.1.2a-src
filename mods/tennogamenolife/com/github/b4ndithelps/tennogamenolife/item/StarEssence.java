package com.github.b4ndithelps.tennogamenolife.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class StarEssence extends Item {
   public StarEssence(Properties properties) {
      super(properties.food(new Builder().nutrition(1).alwaysEdible().effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 600, 0), 1.0F).build()));
   }
}
