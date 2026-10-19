package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.mixins.SetFireworksLootFunctionAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetFireworksFunction;

public class SetWrittenPagesFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      if (!stack.isEmpty()) {
         stack.update(DataComponents.FIREWORKS, SetFireworksFunction.DEFAULT_VALUE, ((SetFireworksLootFunctionAccessor)function)::callApply);
      }

      return new LootTableParser.LootFunctionResult(TextKey.of("emi_loot.function.fireworks"), ItemStack.EMPTY, conditionTexts);
   }
}
