package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.Optional;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.network.chat.Component;

public class DamageSourcePredicateParser {
   public static Component parseDamageSourcePredicate(DamageSourcePredicate predicate) {
      Optional<EntityPredicate> directPredicate = predicate.directEntity();
      if (directPredicate.isPresent()) {
         return EntityPredicateParser.parseEntityPredicate(directPredicate.get());
      }

      Optional<EntityPredicate> sourcePredicate = predicate.sourceEntity();
      if (sourcePredicate.isPresent()) {
         return EntityPredicateParser.parseEntityPredicate(sourcePredicate.get());
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Empty or unparsable damage source predicate in table: " + LootTableParser.currentTable);
      }

      return LText.translatable("emi_loot.predicate.invalid");
   }
}
