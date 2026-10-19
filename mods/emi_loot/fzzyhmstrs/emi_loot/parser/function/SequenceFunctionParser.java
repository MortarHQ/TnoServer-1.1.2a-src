package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.mixins.AndLootFunctionAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.parser.registry.LootParserRegistry;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public class SequenceFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      List<LootItemFunction> list = ((AndLootFunctionAccessor)function).getFunctions();
      return new LootTableParser.LootFunctionResult(
         TextKey.of(
            "emi_loot.function.sequence",
            ListProcessors.buildAndList(
               list.stream()
                  .map(lf -> LootParserRegistry.parseFunction(lf, stack, lf.getType(), parentIsAlternative, List.of()).text().asText().plainCopy())
                  .toList()
            )
         ),
         stack,
         conditionTexts
      );
   }
}
