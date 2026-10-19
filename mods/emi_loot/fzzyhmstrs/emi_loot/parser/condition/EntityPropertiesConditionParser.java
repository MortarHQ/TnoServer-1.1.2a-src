package fzzyhmstrs.emi_loot.parser.condition;

import fzzyhmstrs.emi_loot.parser.EntityPredicateParser;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.Collections;
import java.util.List;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

public class EntityPropertiesConditionParser implements ConditionParser {
   @Override
   public List<LootTableParser.LootConditionResult> parseCondition(LootItemCondition condition, ItemStack stack, boolean parentIsAlternative) {
      EntityTarget entity = ((LootItemEntityPropertyCondition)condition).entityTarget();
      EntityPredicate predicate = (EntityPredicate)((LootItemEntityPropertyCondition)condition).predicate().orElse(null);
      if (predicate == null) {
         return Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.entity_props", LText.literal("Unknown"))));
      }

      MutableComponent propText;
      if (entity == EntityTarget.THIS) {
         propText = LText.translatable("emi_loot.entity_predicate.entity_this", EntityPredicateParser.parseEntityPredicate(predicate));
      } else {
         propText = LText.translatable("emi_loot.entity_predicate.entity_killer", EntityPredicateParser.parseEntityPredicate(predicate));
      }

      return Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.entity_props", propText)));
   }
}
