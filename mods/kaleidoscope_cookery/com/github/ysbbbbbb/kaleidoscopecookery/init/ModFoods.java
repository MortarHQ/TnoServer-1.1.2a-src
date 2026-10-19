package com.github.ysbbbbbb.kaleidoscopecookery.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;

public interface ModFoods {
   FoodProperties TOMATO = new Builder().nutrition(2).saturationModifier(0.5F).alwaysEdible().build();
   FoodProperties CHILI = new Builder().nutrition(1).saturationModifier(0.0F).alwaysEdible().build();
   FoodProperties LETTUCE = new Builder().nutrition(2).saturationModifier(0.0F).alwaysEdible().build();
   FoodProperties CATERPILLAR = new Builder()
      .nutrition(18)
      .saturationModifier(0.2F)
      .alwaysEdible()
      .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200), 1.0F)
      .build();
   FoodProperties SASHIMI = new Builder().nutrition(1).saturationModifier(0.5F).alwaysEdible().build();
   FoodProperties RAW_LAMB_CHOPS = new Builder().nutrition(1).saturationModifier(0.5F).alwaysEdible().build();
   FoodProperties RAW_COW_OFFAL = new Builder().nutrition(2).saturationModifier(0.3F).alwaysEdible().build();
   FoodProperties RAW_PORK_BELLY = new Builder().nutrition(2).saturationModifier(0.3F).alwaysEdible().build();
   FoodProperties RAW_DONKEY_MEAT = new Builder().nutrition(2).saturationModifier(0.3F).alwaysEdible().build();
   FoodProperties RAW_CUT_SMALL_MEATS = new Builder().nutrition(2).saturationModifier(0.3F).alwaysEdible().build();
   FoodProperties RAW_MEATBALL = new Builder().nutrition(4).saturationModifier(0.3F).alwaysEdible().build();
   FoodProperties COOKED_LAMB_CHOPS = new Builder().nutrition(3).saturationModifier(0.8F).alwaysEdible().build();
   FoodProperties COOKED_COW_OFFAL = new Builder().nutrition(4).saturationModifier(0.8F).alwaysEdible().build();
   FoodProperties COOKED_PORK_BELLY = new Builder().nutrition(4).saturationModifier(0.8F).alwaysEdible().build();
   FoodProperties COOKED_DONKEY_MEAT = new Builder().nutrition(6).saturationModifier(0.8F).alwaysEdible().build();
   FoodProperties COOKED_CUT_SMALL_MEATS = new Builder().nutrition(4).saturationModifier(0.8F).alwaysEdible().build();
   FoodProperties COOKED_MEATBALL = new Builder().nutrition(8).saturationModifier(0.8F).alwaysEdible().build();
   FoodProperties DONKEY_BURGER = new Builder()
      .nutrition(12)
      .saturationModifier(0.8F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 900), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BAOZI = new Builder()
      .nutrition(8)
      .saturationModifier(1.0F)
      .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties DUMPLING = new Builder()
      .nutrition(8)
      .saturationModifier(1.0F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SAMSA = new Builder()
      .nutrition(8)
      .saturationModifier(1.0F)
      .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 2000, 1), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties MANTOU = new Builder().nutrition(6).saturationModifier(0.9F).fast().alwaysEdible().build();
   FoodProperties MEAT_PIE = new Builder()
      .nutrition(8)
      .saturationModifier(1.0F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties QINGTUAN = new Builder().nutrition(5).saturationModifier(0.6F).alwaysEdible().build();
   FoodProperties STICKY_CANDY = new Builder().nutrition(6).saturationModifier(1.0F).alwaysEdible().build();
   FoodProperties STICKY_RICE_CAKE = new Builder().nutrition(8).saturationModifier(0.875F).alwaysEdible().build();
   FoodProperties ZONGZI = new Builder()
      .nutrition(8)
      .saturationModifier(0.625F)
      .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties RAW_BAMBOO_TUBE_RICE = new Builder().nutrition(10).saturationModifier(0.5F).alwaysEdible().build();
   FoodProperties BAMBOO_TUBE_RICE = new Builder().nutrition(10).saturationModifier(0.5F).alwaysEdible().build();
   FoodProperties BEEF_NOODLE = new Builder()
      .nutrition(14)
      .saturationModifier(0.643F)
      .effect(() -> new MobEffectInstance(ModEffects.VITALITY, 9600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties HUI_NOODLE = new Builder()
      .nutrition(14)
      .saturationModifier(0.643F)
      .effect(() -> new MobEffectInstance(ModEffects.VITALITY, 9600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties UDON_NOODLE = new Builder()
      .nutrition(14)
      .saturationModifier(0.643F)
      .effect(() -> new MobEffectInstance(ModEffects.VITALITY, 9600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties HOT_DRY_NOODLES = new Builder()
      .nutrition(14)
      .saturationModifier(0.643F)
      .effect(() -> new MobEffectInstance(ModEffects.VITALITY, 9600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties LABA_CONGEE = new Builder()
      .nutrition(12)
      .saturationModifier(0.5F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FRIED_EGG = new Builder().nutrition(4).saturationModifier(0.5F).alwaysEdible().build();
   FoodProperties DARK_CUISINE_BLOCK = new Builder()
      .nutrition(1)
      .saturationModifier(0.0F)
      .alwaysEdible()
      .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 300), 0.33F)
      .effect(() -> new MobEffectInstance(MobEffects.POISON, 100), 0.33F)
      .build();
   FoodProperties DARK_CUISINE_ITEM = new Builder()
      .nutrition(2)
      .saturationModifier(0.0F)
      .alwaysEdible()
      .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 300), 0.33F)
      .effect(() -> new MobEffectInstance(MobEffects.POISON, 100), 0.33F)
      .build();
   FoodProperties SUSPICIOUS_STIR_FRY_BLOCK = new Builder()
      .nutrition(4)
      .saturationModifier(0.4F)
      .alwaysEdible()
      .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.JUMP, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.LUCK, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400), 0.15F)
      .build();
   FoodProperties SUSPICIOUS_STIR_FRY_ITEM = new Builder()
      .nutrition(4)
      .saturationModifier(0.4F)
      .alwaysEdible()
      .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.JUMP, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.LUCK, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400), 0.15F)
      .build();
   FoodProperties SLIME_BALL_MEAL_BLOCK = new Builder()
      .nutrition(3)
      .saturationModifier(0.0F)
      .effect(() -> new MobEffectInstance(ModEffects.FLATULENCE, 700), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SLIME_BALL_MEAL_ITEM = new Builder()
      .nutrition(9)
      .saturationModifier(0.0F)
      .effect(() -> new MobEffectInstance(ModEffects.FLATULENCE, 700), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FONDANT_PIE_BLOCK = new Builder()
      .nutrition(5)
      .saturationModifier(0.55F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FONDANT_PIE_ITEM = new Builder()
      .nutrition(20)
      .saturationModifier(0.55F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties DONGPO_PORK_BLOCK = new Builder()
      .nutrition(7)
      .saturationModifier(0.521F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties DONGPO_PORK_ITEM = new Builder()
      .nutrition(20)
      .saturationModifier(0.55F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FONDANT_SPIDER_EYE_BLOCK = new Builder()
      .nutrition(2)
      .saturationModifier(0.0F)
      .effect(() -> new MobEffectInstance(ModEffects.FLATULENCE, 700), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FONDANT_SPIDER_EYE_ITEM = new Builder()
      .nutrition(8)
      .saturationModifier(0.0F)
      .effect(() -> new MobEffectInstance(ModEffects.FLATULENCE, 700), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties CHORUS_FRIED_EGG_BLOCK = new Builder()
      .nutrition(3)
      .saturationModifier(0.0F)
      .effect(() -> new MobEffectInstance(ModEffects.FLATULENCE, 700), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties CHORUS_FRIED_EGG_ITEM = new Builder()
      .nutrition(9)
      .saturationModifier(0.0F)
      .effect(() -> new MobEffectInstance(ModEffects.FLATULENCE, 700), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BRAISED_FISH_BLOCK = new Builder()
      .nutrition(3)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BRAISED_FISH_ITEM = new Builder()
      .nutrition(13)
      .saturationModifier(0.615F)
      .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties GOLDEN_SALAD_BLOCK = new Builder()
      .nutrition(5)
      .saturationModifier(0.55F)
      .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2000), 1.0F)
      .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties GOLDEN_SALAD_ITEM = new Builder()
      .nutrition(20)
      .saturationModifier(0.55F)
      .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2000), 1.0F)
      .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SPICY_CHICKEN_BLOCK = new Builder()
      .nutrition(5)
      .saturationModifier(0.55F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SPICY_CHICKEN_ITEM = new Builder()
      .nutrition(20)
      .saturationModifier(0.55F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties YAKITORI_BLOCK = new Builder()
      .nutrition(5)
      .saturationModifier(0.55F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties YAKITORI_ITEM = new Builder()
      .nutrition(20)
      .saturationModifier(0.55F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties CRYSTAL_LAMB_CHOP_BLOCK = new Builder()
      .nutrition(4)
      .saturationModifier(0.625F)
      .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties CRYSTAL_LAMB_CHOP_ITEM = new Builder()
      .nutrition(13)
      .saturationModifier(0.615F)
      .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties NETHER_STYLE_SASHIMI_BLOCK = new Builder()
      .nutrition(4)
      .saturationModifier(0.1875F)
      .effect(() -> new MobEffectInstance(ModEffects.MUSTARD, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties NETHER_STYLE_SASHIMI_ITEM = new Builder()
      .nutrition(16)
      .saturationModifier(0.1875F)
      .effect(() -> new MobEffectInstance(ModEffects.MUSTARD, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties PAN_SEARED_KNIGHT_STEAK_BLOCK = new Builder()
      .nutrition(4)
      .saturationModifier(0.625F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1800), 1.0F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 800), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties PAN_SEARED_KNIGHT_STEAK_ITEM = new Builder()
      .nutrition(13)
      .saturationModifier(0.615F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 1800), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties STARGAZY_PIE_BLOCK = new Builder()
      .nutrition(3)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.FLATULENCE, 600), 1.0F)
      .effect(() -> new MobEffectInstance(MobEffects.UNLUCK, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties STARGAZY_PIE_ITEM = new Builder()
      .nutrition(13)
      .saturationModifier(0.615F)
      .effect(() -> new MobEffectInstance(ModEffects.FLATULENCE, 600), 1.0F)
      .effect(() -> new MobEffectInstance(MobEffects.UNLUCK, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SWEET_AND_SOUR_ENDER_PEARLS_BLOCK = new Builder()
      .nutrition(3)
      .saturationModifier(0.0F)
      .effect(() -> new MobEffectInstance(ModEffects.FLATULENCE, 700), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SWEET_AND_SOUR_ENDER_PEARLS_ITEM = new Builder()
      .nutrition(9)
      .saturationModifier(0.0F)
      .effect(() -> new MobEffectInstance(ModEffects.FLATULENCE, 700), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BLAZE_LAMB_CHOP_BLOCK = new Builder()
      .nutrition(4)
      .saturationModifier(0.625F)
      .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BLAZE_LAMB_CHOP_ITEM = new Builder()
      .nutrition(13)
      .saturationModifier(0.615F)
      .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FROST_LAMB_CHOP_BLOCK = new Builder()
      .nutrition(4)
      .saturationModifier(0.625F)
      .effect(() -> new MobEffectInstance(ModEffects.TUNDRA_STRIDER, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FROST_LAMB_CHOP_ITEM = new Builder()
      .nutrition(13)
      .saturationModifier(0.615F)
      .effect(() -> new MobEffectInstance(ModEffects.TUNDRA_STRIDER, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties END_STYLE_SASHIMI_BLOCK = new Builder()
      .nutrition(4)
      .saturationModifier(0.1875F)
      .effect(() -> new MobEffectInstance(ModEffects.MUSTARD, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties END_STYLE_SASHIMI_ITEM = new Builder()
      .nutrition(16)
      .saturationModifier(0.1875F)
      .effect(() -> new MobEffectInstance(ModEffects.MUSTARD, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties DESERT_STYLE_SASHIMI_BLOCK = new Builder()
      .nutrition(4)
      .saturationModifier(0.1875F)
      .effect(() -> new MobEffectInstance(ModEffects.MUSTARD, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties DESERT_STYLE_SASHIMI_ITEM = new Builder()
      .nutrition(16)
      .saturationModifier(0.1875F)
      .effect(() -> new MobEffectInstance(ModEffects.MUSTARD, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties TUNDRA_STYLE_SASHIMI_BLOCK = new Builder()
      .nutrition(4)
      .saturationModifier(0.1875F)
      .effect(() -> new MobEffectInstance(ModEffects.MUSTARD, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties TUNDRA_STYLE_SASHIMI_ITEM = new Builder()
      .nutrition(16)
      .saturationModifier(0.1875F)
      .effect(() -> new MobEffectInstance(ModEffects.MUSTARD, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties COLD_STYLE_SASHIMI_BLOCK = new Builder()
      .nutrition(4)
      .saturationModifier(0.1875F)
      .effect(() -> new MobEffectInstance(ModEffects.MUSTARD, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties COLD_STYLE_SASHIMI_ITEM = new Builder()
      .nutrition(16)
      .saturationModifier(0.1875F)
      .effect(() -> new MobEffectInstance(ModEffects.MUSTARD, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SHENGJIAN_MANTOU_ITEM = new Builder()
      .nutrition(8)
      .saturationModifier(1.0F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SHENGJIAN_MANTOU_BLOCK = new Builder()
      .nutrition(2)
      .saturationModifier(1.0F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SCRAMBLE_EGG_WITH_TOMATOES = new Builder()
      .nutrition(9)
      .saturationModifier(0.611F)
      .effect(() -> new MobEffectInstance(ModEffects.VIGOR, 1800), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties STIR_FRIED_BEEF_OFFAL = new Builder()
      .nutrition(9)
      .saturationModifier(0.611F)
      .effect(() -> new MobEffectInstance(ModEffects.VIGOR, 1800), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BRAISED_BEEF = new Builder()
      .nutrition(9)
      .saturationModifier(0.611F)
      .effect(() -> new MobEffectInstance(ModEffects.VIGOR, 1800), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties STIR_FRIED_PORK_WITH_PEPPERS = new Builder()
      .nutrition(9)
      .saturationModifier(0.611F)
      .effect(() -> new MobEffectInstance(ModEffects.VIGOR, 1800), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SWEET_AND_SOUR_PORK = new Builder()
      .nutrition(9)
      .saturationModifier(0.611F)
      .effect(() -> new MobEffectInstance(ModEffects.VIGOR, 1800), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FISH_FLAVORED_SHREDDED_PORK = new Builder()
      .nutrition(9)
      .saturationModifier(0.611F)
      .effect(() -> new MobEffectInstance(ModEffects.VIGOR, 1800), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties COUNTRY_STYLE_MIXED_VEGETABLES = new Builder()
      .nutrition(9)
      .saturationModifier(0.611F)
      .effect(() -> new MobEffectInstance(ModEffects.PRESERVATION, 1800), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties COOKED_RICE = new Builder().nutrition(6).saturationModifier(1.0F).alwaysEdible().build();
   FoodProperties EGG_FRIED_RICE = new Builder().nutrition(8).saturationModifier(0.875F).alwaysEdible().build();
   FoodProperties DELICIOUS_EGG_FRIED_RICE = new Builder()
      .nutrition(12)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SUSPICIOUS_STIR_FRY_RICE_BOWL = new Builder()
      .nutrition(8)
      .saturationModifier(0.4F)
      .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.JUMP, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.LUCK, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 1200), 0.15F)
      .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400), 0.15F)
      .alwaysEdible()
      .build();
   FoodProperties SCRAMBLE_EGG_WITH_TOMATOES_RICE_BOWL = new Builder()
      .nutrition(14)
      .saturationModifier(0.643F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties STIR_FRIED_BEEF_OFFAL_RICE_BOWL = new Builder()
      .nutrition(14)
      .saturationModifier(0.643F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BRAISED_BEEF_RICE_BOWL = new Builder()
      .nutrition(14)
      .saturationModifier(0.643F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties STIR_FRIED_PORK_WITH_PEPPERS_RICE_BOWL = new Builder()
      .nutrition(14)
      .saturationModifier(0.643F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SWEET_AND_SOUR_PORK_RICE_BOWL = new Builder()
      .nutrition(14)
      .saturationModifier(0.643F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FISH_FLAVORED_SHREDDED_PORK_RICE_BOWL = new Builder()
      .nutrition(14)
      .saturationModifier(0.643F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BRAISED_FISH_RICE_BOWL = new Builder()
      .nutrition(14)
      .saturationModifier(0.643F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SPICY_CHICKEN_RICE_BOWL = new Builder()
      .nutrition(14)
      .saturationModifier(0.643F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties PORK_BONE_SOUP = new Builder()
      .nutrition(6)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.VIGOR, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SEAFOOD_MISO_SOUP = new Builder()
      .nutrition(6)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 9600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FEARSOME_THICK_SOUP = new Builder()
      .nutrition(4)
      .saturationModifier(0.0F)
      .effect(() -> new MobEffectInstance(ModEffects.SULFUR, 9600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties LAMB_AND_RADISH_SOUP = new Builder()
      .nutrition(6)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.TUNDRA_STRIDER, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BRAISED_BEEF_WITH_POTATOES = new Builder()
      .nutrition(6)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 9600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties WILD_MUSHROOM_RABBIT_SOUP = new Builder()
      .nutrition(6)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 9600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties TOMATO_BEEF_BRISKET_SOUP = new Builder()
      .nutrition(6)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 9600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties PUFFERFISH_SOUP = new Builder()
      .nutrition(4)
      .saturationModifier(0.0F)
      .effect(() -> new MobEffectInstance(ModEffects.MUSTARD, 12000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BORSCHT = new Builder()
      .nutrition(6)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.FLATULENCE, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BEEF_MEATBALL_SOUP = new Builder()
      .nutrition(6)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 9600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties CHICKEN_AND_MUSHROOM_STEW = new Builder()
      .nutrition(6)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 9600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties DONKEY_SOUP = new Builder()
      .nutrition(6)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 9600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties CANDIED_POTATO_BLOCK = new Builder()
      .nutrition(5)
      .saturationModifier(0.55F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties CANDIED_POTATO_ITEM = new Builder()
      .nutrition(20)
      .saturationModifier(0.55F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties DOUGH_DROP_SOUP_BLOCK = new Builder()
      .nutrition(2)
      .saturationModifier(1.0F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties DOUGH_DROP_SOUP_ITEM = new Builder()
      .nutrition(8)
      .saturationModifier(1.0F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties STUFFED_TIGER_SKIN_PEPPER_BLOCK = new Builder()
      .nutrition(3)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties STUFFED_TIGER_SKIN_PEPPER_ITEM = new Builder()
      .nutrition(13)
      .saturationModifier(0.615F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SPICY_RABBIT_HEAD_BLOCK = new Builder()
      .nutrition(3)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SPICY_RABBIT_HEAD_ITEM = new Builder()
      .nutrition(13)
      .saturationModifier(0.615F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FOUR_JOY_MEATBALL_SOUP_BLOCK = new Builder()
      .nutrition(3)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FOUR_JOY_MEATBALL_SOUP_ITEM = new Builder()
      .nutrition(13)
      .saturationModifier(0.615F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties NUMBING_SPICY_CHICKEN_BLOCK = new Builder()
      .nutrition(3)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2400), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties NUMBING_SPICY_CHICKEN_ITEM = new Builder()
      .nutrition(13)
      .saturationModifier(0.615F)
      .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2400), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FRIED_CATERPILLAR_BLOCK = new Builder()
      .nutrition(4)
      .saturationModifier(0.8F)
      .effect(() -> new MobEffectInstance(ModEffects.FLATULENCE, 200), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FRIED_CATERPILLAR_ITEM = new Builder()
      .nutrition(12)
      .saturationModifier(0.8F)
      .effect(() -> new MobEffectInstance(ModEffects.FLATULENCE, 200), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FRIED_SPRING_ROLL_BLOCK = new Builder()
      .nutrition(3)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties FRIED_SPRING_ROLL_ITEM = new Builder()
      .nutrition(13)
      .saturationModifier(0.615F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SPICY_BLOOD_STEW_BLOCK = new Builder()
      .nutrition(3)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties SPICY_BLOOD_STEW_ITEM = new Builder()
      .nutrition(13)
      .saturationModifier(0.615F)
      .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BROWN_MUSHROOM_POT_SOUP_BLOCK = new Builder()
      .nutrition(6)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BROWN_MUSHROOM_POT_SOUP_ITEM = new Builder()
      .nutrition(18)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties RED_MUSHROOM_POT_SOUP_BLOCK = new Builder()
      .nutrition(6)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties RED_MUSHROOM_POT_SOUP_ITEM = new Builder()
      .nutrition(18)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 6000), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties WARPED_FUNGUS_POT_SOUP_BLOCK = new Builder()
      .nutrition(6)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2400), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties WARPED_FUNGUS_POT_SOUP_ITEM = new Builder()
      .nutrition(18)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2400), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties CRIMSON_FUNGUS_POT_SOUP_BLOCK = new Builder()
      .nutrition(6)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2400), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties CRIMSON_FUNGUS_POT_SOUP_ITEM = new Builder()
      .nutrition(18)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2400), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BUDDHA_JUMPS_OVER_THE_WALL_BLOCK = new Builder()
      .nutrition(7)
      .saturationModifier(0.521F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BUDDHA_JUMPS_OVER_THE_WALL_ITEM = new Builder()
      .nutrition(20)
      .saturationModifier(0.55F)
      .effect(() -> new MobEffectInstance(ModEffects.SATIATED_SHIELD, 3600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BRAISED_PORK_RIBS_BLOCK = new Builder()
      .nutrition(4)
      .saturationModifier(0.8F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties BRAISED_PORK_RIBS_ITEM = new Builder()
      .nutrition(16)
      .saturationModifier(0.8F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties COLD_ROASTED_MEAT_BLOCK = new Builder().nutrition(4).saturationModifier(0.8F).alwaysEdible().build();
   FoodProperties COLD_ROASTED_MEAT_ITEM = new Builder().nutrition(16).saturationModifier(0.8F).alwaysEdible().build();
   FoodProperties OIL_SPLASHED_FISH_BLOCK = new Builder()
      .nutrition(3)
      .saturationModifier(0.667F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties OIL_SPLASHED_FISH_ITEM = new Builder()
      .nutrition(13)
      .saturationModifier(0.615F)
      .effect(() -> new MobEffectInstance(ModEffects.WARMTH, 1600), 1.0F)
      .alwaysEdible()
      .build();
   FoodProperties COLD_CUT_HAM_SLICES_BLOCK = new Builder().nutrition(4).saturationModifier(0.8F).alwaysEdible().build();
}
