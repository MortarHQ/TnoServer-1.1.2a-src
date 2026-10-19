package com.tensura_tno.registry;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;

public final class TensuraTNOFoods {
   public static final FoodProperties STORM_DAEMON_HIPOKUTE_SOUP = new Builder().nutrition(20).saturationModifier(0.55F).alwaysEdible().build();
   public static final FoodProperties MONSTER_MEAT_PLATTER = new Builder().nutrition(30).saturationModifier(0.55F).alwaysEdible().build();
   public static final FoodProperties SLIME_SAUCE_BUN = new Builder().nutrition(6).saturationModifier(0.6F).alwaysEdible().build();
   public static final FoodProperties BLADE_TIGER_MINCED_MEAT = new Builder().nutrition(3).saturationModifier(0.3F).build();
   public static final FoodProperties COOKED_BLADE_TIGER_MINCED_MEAT = new Builder().nutrition(8).saturationModifier(0.8F).build();
   public static final FoodProperties CHARYBDIS_MINCED_MEAT = new Builder().nutrition(4).saturationModifier(0.4F).build();
   public static final FoodProperties COOKED_CHARYBDIS_MINCED_MEAT = new Builder().nutrition(12).saturationModifier(1.0F).build();
   public static final FoodProperties ARMORSAURUS_MINCED_MEAT = new Builder().nutrition(4).saturationModifier(0.4F).build();
   public static final FoodProperties COOKED_ARMORSAURUS_MINCED_MEAT = new Builder().nutrition(12).saturationModifier(1.0F).build();
   public static final FoodProperties KNIGHT_SPIDER_LEG_MEAT = new Builder().nutrition(3).saturationModifier(0.3F).build();
   public static final FoodProperties COOKED_KNIGHT_SPIDER_LEG_MEAT = new Builder().nutrition(8).saturationModifier(0.8F).build();
   public static final FoodProperties CURED_MEAT = new Builder().nutrition(8).saturationModifier(0.8F).build();
   public static final FoodProperties SLICED_CURED_MEAT = new Builder().nutrition(4).saturationModifier(0.6F).build();

   private TensuraTNOFoods() {
   }
}
