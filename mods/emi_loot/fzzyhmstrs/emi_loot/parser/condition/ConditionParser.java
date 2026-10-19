package fzzyhmstrs.emi_loot.parser.condition;

import fzzyhmstrs.emi_loot.parser.LootTableParser;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public interface ConditionParser {
   ConditionParser EMPTY = (condition, stack, parentIsAlternative) -> Collections.singletonList(LootTableParser.LootConditionResult.EMPTY);

   List<LootTableParser.LootConditionResult> parseCondition(LootItemCondition var1, ItemStack var2, boolean var3);
}
