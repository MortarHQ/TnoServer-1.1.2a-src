package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.Objects;
import net.minecraft.advancements.critereon.LightPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.network.chat.Component;

public class LightPredicateParser {
   public static Component parseLightPredicate(LightPredicate predicate) {
      Ints range = predicate.composite();
      if (range.equals(Ints.ANY)) {
         if (EMILoot.DEBUG) {
            EMILoot.LOGGER.warn("Undefined light predicate in table: " + LootTableParser.currentTable);
         }

         return LText.translatable("emi_loot.predicate.invalid");
      } else {
         Integer min = (Integer)range.min().orElse(null);
         Integer max = (Integer)range.max().orElse(null);
         return Objects.equals(min, max) && min != null
            ? LText.translatable("emi_loot.location_predicate.light", min)
            : LText.translatable("emi_loot.location_predicate.light_2", min, max);
      }
   }
}
