package fzzyhmstrs.emi_loot.parser.condition;

import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.parser.StatePredicateParser;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.Collections;
import java.util.List;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class BlockStatePropertyConditionParser implements ConditionParser {
   @Override
   public List<LootTableParser.LootConditionResult> parseCondition(LootItemCondition condition, ItemStack stack, boolean parentIsAlternative) {
      Block block = (Block)((LootItemBlockStatePropertyCondition)condition).block().value();
      MutableComponent bsText;
      if (block != null) {
         bsText = LText.translatable("emi_loot.condition.blockstate.block", block.getName());
      } else {
         StatePropertiesPredicate predicate = (StatePropertiesPredicate)((LootItemBlockStatePropertyCondition)condition).properties().orElseThrow();
         bsText = (MutableComponent)StatePredicateParser.parseStatePredicate(predicate);
      }

      return Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.blockstate", bsText)));
   }
}
