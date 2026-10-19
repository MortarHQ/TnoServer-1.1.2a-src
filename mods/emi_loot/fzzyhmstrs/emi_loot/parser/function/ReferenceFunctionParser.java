package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.mixins.ReferenceLootFunctionAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.parser.registry.LootParserRegistry;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;

public class ReferenceFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      ResourceKey<LootItemFunction> id = ((ReferenceLootFunctionAccessor)function).getName();
      if (LootTableParser.lootManager != null) {
         Optional<Optional<? extends Holder<LootItemFunction>>> referenceFunction = LootTableParser.registryOps
            .getter(Registries.ITEM_MODIFIER)
            .map(reg -> reg.get(id));
         if (referenceFunction.isPresent()
            && referenceFunction.get().isPresent()
            && ((LootItemFunction)referenceFunction.get().get().value()).getType() != LootItemFunctions.REFERENCE) {
            return LootParserRegistry.parseFunction(
               (LootItemFunction)referenceFunction.get().get().value(),
               stack,
               ((LootItemFunction)referenceFunction.get().get().value()).getType(),
               parentIsAlternative,
               conditionTexts
            );
         }
      }

      return new LootTableParser.LootFunctionResult(TextKey.of("emi_loot.function.reference", LText.literal(id.toString())), ItemStack.EMPTY, conditionTexts);
   }
}
