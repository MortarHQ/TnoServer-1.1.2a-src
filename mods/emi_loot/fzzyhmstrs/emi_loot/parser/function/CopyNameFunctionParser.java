package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.mixins.CopyNameLootFunctionAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction.NameSource;

public class CopyNameFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      NameSource source = ((CopyNameLootFunctionAccessor)function).getSource();
      Component sourceText = LText.translatable("emi_loot.function.copy_name." + source.name());
      return new LootTableParser.LootFunctionResult(TextKey.of("emi_loot.function.copy_name", sourceText), ItemStack.EMPTY, conditionTexts);
   }
}
