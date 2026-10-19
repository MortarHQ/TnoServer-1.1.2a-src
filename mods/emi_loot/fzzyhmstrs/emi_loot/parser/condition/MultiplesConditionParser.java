package fzzyhmstrs.emi_loot.parser.condition;

import fzzyhmstrs.emi_loot.mixins.AlternativeLootConditionAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.Collections;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class MultiplesConditionParser implements ConditionParser {
   private final String key;

   public MultiplesConditionParser(String key) {
      this.key = key;
   }

   @Override
   public List<LootTableParser.LootConditionResult> parseCondition(LootItemCondition condition, ItemStack stack, boolean parentIsAlternative) {
      List<LootItemCondition> terms = ((AlternativeLootConditionAccessor)condition).getConditions();
      List<Component> args = terms.stream().map(term -> {
         List<LootTableParser.LootConditionResult> termResults = LootTableParser.parseLootCondition(term, stack);
         return LootTableParser.compileConditionTexts(stack, termResults);
      }).toList();
      return Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of(this.key, args)));
   }
}
