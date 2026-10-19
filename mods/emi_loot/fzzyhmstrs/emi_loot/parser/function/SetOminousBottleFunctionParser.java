package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.mixins.SetOminousBottleAmplifierLootFunctionAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.parser.processor.NumberProcessors;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SetOminousBottleFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      NumberProvider amplifier = ((SetOminousBottleAmplifierLootFunctionAccessor)function).getAmplifierGenerator();
      Component a = NumberProcessors.processLootNumberProvider(amplifier);
      return new LootTableParser.LootFunctionResult(TextKey.of("emi_loot.function.ominous_bottle", a), ItemStack.EMPTY, conditionTexts);
   }
}
