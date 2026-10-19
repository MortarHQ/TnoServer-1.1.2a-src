package fzzyhmstrs.emi_loot.parser.condition;

import fzzyhmstrs.emi_loot.parser.DamageSourcePredicateParser;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.Collections;
import java.util.List;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class DamageSourceConditionParser implements ConditionParser {
   @Override
   public List<LootTableParser.LootConditionResult> parseCondition(LootItemCondition condition, ItemStack stack, boolean parentIsAlternative) {
      DamageSourcePredicate damageSourcePredicate = (DamageSourcePredicate)((DamageSourceCondition)condition).predicate().orElse(null);
      if (damageSourcePredicate == null) {
         return Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.damage_source", LText.literal("Unknown"))));
      }

      Component damageText = DamageSourcePredicateParser.parseDamageSourcePredicate(damageSourcePredicate);
      return Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.damage_source", damageText)));
   }
}
