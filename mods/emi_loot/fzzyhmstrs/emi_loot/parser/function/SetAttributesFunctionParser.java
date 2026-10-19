package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.mixins.SetAttributesLootFunctionAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.parser.processor.NumberProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction.Modifier;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SetAttributesFunctionParser implements FunctionParser {
   @Override
   public LootTableParser.LootFunctionResult parseFunction(
      LootItemFunction function, ItemStack stack, boolean parentIsAlternative, List<TextKey> conditionTexts
   ) {
      List<Modifier> attributes = ((SetAttributesLootFunctionAccessor)function).getModifiers();
      List<MutableComponent> list = new LinkedList<>();

      for (Modifier attribute : attributes) {
         NumberProvider amount = attribute.amount();
         Holder<Attribute> entityAttribute = attribute.attribute();
         Operation operation = attribute.operation();
         if (operation == Operation.ADD_VALUE) {
            list.add(
               LText.translatable(
                  "emi_loot.function.set_attributes.add",
                  NumberProcessors.getRollAvg(amount),
                  LText.translatable(((Attribute)entityAttribute.value()).getDescriptionId())
               )
            );
         } else if (operation == Operation.ADD_MULTIPLIED_BASE) {
            list.add(
               LText.translatable(
                  "emi_loot.function.set_attributes.multiply_base",
                  NumberProcessors.getRollAvg(amount) + 1.0F,
                  LText.translatable(((Attribute)entityAttribute.value()).getDescriptionId())
               )
            );
         } else {
            list.add(
               LText.translatable(
                  "emi_loot.function.set_attributes.multiply",
                  NumberProcessors.getRollAvg(amount) + 1.0F,
                  LText.translatable(((Attribute)entityAttribute.value()).getDescriptionId())
               )
            );
         }
      }

      return new LootTableParser.LootFunctionResult(
         TextKey.of("emi_loot.function.set_attributes", ListProcessors.buildAndList(list)), ItemStack.EMPTY, conditionTexts
      );
   }
}
