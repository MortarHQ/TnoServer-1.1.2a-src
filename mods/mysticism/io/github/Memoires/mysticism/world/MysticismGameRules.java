package io.github.Memoires.mysticism.world;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.Key;

public class MysticismGameRules {
   public static Key<BooleanValue> UNIQUE_SE_COST = GameRules.register("uniqueSECost", Category.PLAYER, BooleanValue.create(true));

   public static void init() {
   }
}
