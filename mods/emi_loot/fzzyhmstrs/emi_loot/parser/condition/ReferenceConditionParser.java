package fzzyhmstrs.emi_loot.parser.condition;

import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.predicates.ConditionReference;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

public class ReferenceConditionParser implements ConditionParser {
   @Override
   public List<LootTableParser.LootConditionResult> parseCondition(LootItemCondition condition, ItemStack stack, boolean parentIsAlternative) {
      ResourceKey<LootItemCondition> id = ((ConditionReference)condition).name();
      if (LootTableParser.registryOps != null) {
         Optional<Optional<? extends Holder<LootItemCondition>>> referenceCondition = LootTableParser.registryOps
            .getter(Registries.PREDICATE)
            .map(reg -> reg.get(id));
         if (referenceCondition.isPresent()
            && referenceCondition.get().isPresent()
            && ((LootItemCondition)referenceCondition.get().get().value()).getType() != LootItemConditions.REFERENCE) {
            return LootTableParser.parseLootCondition((LootItemCondition)referenceCondition.get().get().value(), stack, parentIsAlternative);
         }
      }

      return Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.reference", LText.literal(id.toString()))));
   }
}
