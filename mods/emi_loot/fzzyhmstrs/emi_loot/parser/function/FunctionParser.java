package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public interface FunctionParser {
   FunctionParser EMPTY = (function, stack, conditionTexts, parentIsAlternative) -> LootTableParser.LootFunctionResult.EMPTY;

   LootTableParser.LootFunctionResult parseFunction(LootItemFunction var1, ItemStack var2, boolean var3, List<TextKey> var4);

   default ItemStack parseStack(ItemStack stack) {
      return stack;
   }
}
