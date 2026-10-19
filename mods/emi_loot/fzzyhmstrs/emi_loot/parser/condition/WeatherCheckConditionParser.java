package fzzyhmstrs.emi_loot.parser.condition;

import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.WeatherCheck;

public class WeatherCheckConditionParser implements ConditionParser {
   @Override
   public List<LootTableParser.LootConditionResult> parseCondition(LootItemCondition condition, ItemStack stack, boolean parentIsAlternative) {
      Optional<Boolean> raining = ((WeatherCheck)condition).isRaining();
      if (raining.isPresent()) {
         return raining.get()
            ? Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.raining_true")))
            : Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.raining_false")));
      } else {
         Optional<Boolean> thundering = ((WeatherCheck)condition).isThundering();
         if (thundering.isPresent()) {
            return thundering.get()
               ? Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.thundering_true")))
               : Collections.singletonList(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.thundering_false")));
         } else {
            return Collections.singletonList(LootTableParser.LootConditionResult.EMPTY);
         }
      }
   }
}
