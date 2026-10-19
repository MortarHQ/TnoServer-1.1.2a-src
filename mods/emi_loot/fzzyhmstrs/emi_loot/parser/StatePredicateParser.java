package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate.ExactMatcher;
import net.minecraft.advancements.critereon.StatePropertiesPredicate.PropertyMatcher;
import net.minecraft.advancements.critereon.StatePropertiesPredicate.RangedMatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class StatePredicateParser {
   public static Component parseStatePredicate(StatePropertiesPredicate predicate) {
      List<PropertyMatcher> list = predicate.properties();
      if (!list.isEmpty()) {
         List<MutableComponent> list2 = new LinkedList<>();

         for (PropertyMatcher condition : list) {
            if (condition.valueMatcher() instanceof RangedMatcher rangedValueMatcher) {
               String key = condition.name();
               String min = (String)rangedValueMatcher.minValue().orElse(null);
               String max = (String)rangedValueMatcher.maxValue().orElse(null);
               list2.add(LText.translatable("emi_loot.state_predicate.state_between", key, min, max));
            } else if (condition.valueMatcher() instanceof ExactMatcher exactValueMatcher) {
               String key = condition.name();
               String value = exactValueMatcher.value();
               list2.add(LText.translatable("emi_loot.state_predicate.state_exact", key, value));
            }
         }

         return LText.translatable("emi_loot.state_predicate.base", ListProcessors.buildAndList(list2));
      } else {
         if (EMILoot.DEBUG) {
            EMILoot.LOGGER.warn("Empty or unparsable block/fluid state predicate in table: " + LootTableParser.currentTable);
         }

         return LText.translatable("emi_loot.predicate.invalid");
      }
   }
}
