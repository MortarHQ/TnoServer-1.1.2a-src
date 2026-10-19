package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.mixins.SetItemLootFunctionAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public class SetItemFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      Holder<Item> item = ((SetItemLootFunctionAccessor)function).getItem();
      stack = stack.transmuteCopy((ItemLike)item.value(), stack.getCount());
      return new LootTableParser.LootFunctionResult(TextKey.of("emi_loot.function.set_item", ((Item)item.value()).getDescription()), stack, conditionTexts);
   }
}
