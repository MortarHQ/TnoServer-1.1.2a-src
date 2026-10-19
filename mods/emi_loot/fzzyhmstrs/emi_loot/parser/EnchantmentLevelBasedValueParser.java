package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.LevelBasedValue.Clamped;
import net.minecraft.world.item.enchantment.LevelBasedValue.Constant;
import net.minecraft.world.item.enchantment.LevelBasedValue.Fraction;
import net.minecraft.world.item.enchantment.LevelBasedValue.LevelsSquared;
import net.minecraft.world.item.enchantment.LevelBasedValue.Linear;
import net.minecraft.world.item.enchantment.LevelBasedValue.Lookup;

public class EnchantmentLevelBasedValueParser {
   public static Component parseValue(LevelBasedValue value) {
      return parseValue(value, "", 1.0F);
   }

   public static Component parseValue(LevelBasedValue value, String percent, float multiplier) {
      if (value instanceof Constant constant) {
         return LText.literal(constant.value() * multiplier + percent);
      } else if (value instanceof Lookup lookup) {
         return LText.translatable(
            "emi_loot.enchantment_value.lookup",
            ListProcessors.buildOrList(lookup.values().stream().map(f -> LText.literal(f * multiplier + percent)).toList())
         );
      } else if (value instanceof Linear linear) {
         return linear.base() == linear.perLevelAboveFirst()
            ? LText.translatable("emi_loot.enchantment_value.linear.simple", linear.base() * multiplier + percent)
            : LText.translatable("emi_loot.enchantment_value.linear", linear.base() * multiplier + percent, linear.perLevelAboveFirst() * multiplier + percent);
      } else if (value instanceof LevelsSquared levelsSquared) {
         return LText.translatable("emi_loot.enchantment_value.level_squared", levelsSquared.added() * multiplier + percent);
      } else if (value instanceof Fraction fraction) {
         return LText.translatable(
            "emi_loot.enchantment_value.fraction",
            parseValue(fraction.numerator(), percent, multiplier),
            parseValue(fraction.denominator(), percent, multiplier)
         );
      } else if (value instanceof Clamped clamped) {
         return LText.translatable(
            "emi_loot.enchantment_value.clamped",
            parseValue(clamped.value(), percent, multiplier),
            clamped.min() * multiplier + percent,
            clamped.max() * multiplier + percent
         );
      } else {
         if (EMILoot.DEBUG) {
            EMILoot.LOGGER.warn("Unknown or unparsable enchantment-based value in table: " + LootTableParser.currentTable);
         }

         return LText.translatable("emi_loot.enchantment_value.level_squared");
      }
   }
}
