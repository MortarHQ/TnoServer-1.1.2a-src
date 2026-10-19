package fzzyhmstrs.emi_loot.parser.condition;

import com.mojang.brigadier.Message;
import fzzyhmstrs.emi_loot.mixins.AlternativeLootConditionAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class AlternativesConditionParser implements ConditionParser {
   @Override
   public List<LootTableParser.LootConditionResult> parseCondition(LootItemCondition condition, ItemStack stack, boolean parentIsAlternative) {
      List<LootItemCondition> terms = ((AlternativeLootConditionAccessor)condition).getConditions();
      int size = terms.size();
      if (size == 1) {
         List<LootTableParser.LootConditionResult> termResults = LootTableParser.parseLootCondition(terms.get(0), stack);
         Component termText = LootTableParser.compileConditionTexts(stack, termResults);
         return Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.alternates", termText)));
      }

      if (size == 2) {
         List<LootTableParser.LootConditionResult> termResults1 = LootTableParser.parseLootCondition(terms.get(0), stack);
         List<LootTableParser.LootConditionResult> termResults2 = LootTableParser.parseLootCondition(terms.get(1), stack);
         Component termText1 = LootTableParser.compileConditionTexts(stack, termResults1);
         Component termText2;
         if (termResults2.size() == 1) {
            TextKey key = termResults2.get(0).getText();
            if (key.args().size() == 1) {
               termText2 = Component.translationArg((Message)key.args().get(0));
            } else {
               termText2 = LootTableParser.compileConditionTexts(stack, termResults2);
            }
         } else {
            termText2 = LootTableParser.compileConditionTexts(stack, termResults2);
         }

         List<Component> args = new LinkedList<>(Arrays.stream(new Component[]{termText1, termText2}).toList());
         return Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.alternates_2", args)));
      } else {
         List<Component> args = new LinkedList<>();
         terms.forEach(term -> {
            List<LootTableParser.LootConditionResult> termResults = LootTableParser.parseLootCondition(term, stack);
            Component termText = LootTableParser.compileConditionTexts(stack, termResults);
            args.add(termText);
         });
         return Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.alternates_3", args)));
      }
   }
}
