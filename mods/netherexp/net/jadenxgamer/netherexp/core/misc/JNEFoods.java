package net.jadenxgamer.netherexp.core.misc;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;

public class JNEFoods {
   public static final FoodProperties HOGHAM = new Builder()
      .nutrition(6)
      .saturationModifier(0.6F)
      .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 1200, 0), 0.9F)
      .build();
   public static final FoodProperties COOKED_HOGHAM = new Builder().nutrition(8).saturationModifier(0.8F).build();
   public static final FoodProperties GLOWCHEESE = new Builder()
      .nutrition(1)
      .saturationModifier(0.1F)
      .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 300, 5), 0.6F)
      .build();
   public static final FoodProperties PIZZA_SLICE = new Builder()
      .nutrition(6)
      .saturationModifier(1.0F)
      .effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 0), 1.0F)
      .build();
   public static final FoodProperties WRAITHING_FLESH = new Builder()
      .nutrition(3)
      .saturationModifier(0.1F)
      .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 2), 0.8F)
      .build();
   public static final FoodProperties CEREBRAGE = new Builder().nutrition(5).saturationModifier(0.6F).fast().build();
   public static final FoodProperties ROASTED_BONE = new Builder()
      .nutrition(9)
      .saturationModifier(0.8F)
      .fast()
      .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 80, 0), 0.5F)
      .build();
}
