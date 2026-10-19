package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.mixins.SetStewEffectLootFunctionAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetStewEffectFunction.EffectEntry;

public class SetStewFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      List<EffectEntry> effects = ((SetStewEffectLootFunctionAccessor)function).getEffects();
      List<MutableComponent> list = new LinkedList<>();

      for (EffectEntry effect : effects) {
         list.add(((MobEffect)effect.effect().value()).getDisplayName().copy());
      }

      return list.isEmpty()
         ? new LootTableParser.LootFunctionResult(
            TextKey.of("emi_loot.function.set_stew", LText.translatable("emi_loot.function.set_stew_unknown")), ItemStack.EMPTY, conditionTexts
         )
         : new LootTableParser.LootFunctionResult(TextKey.of("emi_loot.function.set_stew", ListProcessors.buildOrList(list)), ItemStack.EMPTY, conditionTexts);
   }
}
