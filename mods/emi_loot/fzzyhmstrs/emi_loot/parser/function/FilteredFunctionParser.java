package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.mixins.FilteredLootFunctionAccessor;
import fzzyhmstrs.emi_loot.parser.ItemPredicateParser;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.parser.registry.LootParserRegistry;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.List;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public class FilteredFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      ItemPredicate p = ((FilteredLootFunctionAccessor)function).getFilter();
      LootItemFunction f = ((FilteredLootFunctionAccessor)function).getModifier();
      return new LootTableParser.LootFunctionResult(
         TextKey.of(
            "emi_loot.function.filtered",
            ItemPredicateParser.parseItemPredicate(p),
            LootParserRegistry.parseFunction(f, stack, f.getType(), parentIsAlternative, List.of()).text().asText()
         ),
         ItemStack.EMPTY,
         conditionTexts
      );
   }
}
