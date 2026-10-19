package fzzyhmstrs.emi_loot.parser.condition;

import fzzyhmstrs.emi_loot.parser.ItemPredicateParser;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.Collections;
import java.util.List;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

public class MatchToolConditionParser implements ConditionParser {
   @Override
   public List<LootTableParser.LootConditionResult> parseCondition(LootItemCondition condition, ItemStack stack, boolean parentIsAlternative) {
      ItemPredicate predicate = (ItemPredicate)((MatchTool)condition).predicate().orElse(null);
      if (predicate == null) {
         return Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.match_tool", LText.literal("unknown"))));
      }

      Component predicateText = ItemPredicateParser.parseItemPredicate(predicate);
      return Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.match_tool", predicateText)));
   }
}
