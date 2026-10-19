package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public class EnchantRandomlyFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      if (stack.is(Items.BOOK)) {
         stack = new ItemStack(Items.ENCHANTED_BOOK);
         return new LootTableParser.LootFunctionResult(TextKey.of("emi_loot.function.randomly_enchanted_book"), stack, conditionTexts);
      } else {
         return new LootTableParser.LootFunctionResult(TextKey.of("emi_loot.function.randomly_enchanted_item"), ItemStack.EMPTY, conditionTexts);
      }
   }
}
