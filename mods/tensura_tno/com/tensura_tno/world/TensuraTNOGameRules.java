package com.tensura_tno.world;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.IntegerValue;
import net.minecraft.world.level.GameRules.Key;

public final class TensuraTNOGameRules {
   public static Key<BooleanValue> COOK_FOOD_EP;
   public static Key<BooleanValue> SUBORDINATE_SKILL_AI;
   public static Key<BooleanValue> SPIRIT_SUMMON_VANILLA_ONLY;
   public static Key<IntegerValue> SPIRIT_SUMMON_MAX_HEALTH;

   private TensuraTNOGameRules() {
   }

   public static void init() {
      COOK_FOOD_EP = GameRules.register("cookFoodEP", Category.PLAYER, BooleanValue.create(true));
      SUBORDINATE_SKILL_AI = GameRules.register("subordinateSkillAi", Category.MOBS, BooleanValue.create(false));
      SPIRIT_SUMMON_VANILLA_ONLY = GameRules.register("spiritSummonVanillaOnly", Category.PLAYER, BooleanValue.create(false));
      SPIRIT_SUMMON_MAX_HEALTH = GameRules.register("spiritSummonMaxHealth", Category.PLAYER, IntegerValue.create(Integer.MAX_VALUE));
   }

   public static boolean isCookFoodEpEnabled(Level level) {
      return !level.isClientSide() && COOK_FOOD_EP != null && level.getGameRules().getBoolean(COOK_FOOD_EP);
   }

   public static boolean isSubordinateSkillAiEnabled(Level level) {
      return SUBORDINATE_SKILL_AI != null && level.getGameRules().getBoolean(SUBORDINATE_SKILL_AI);
   }
}
