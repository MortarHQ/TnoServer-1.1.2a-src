package twilightforest.compat.rei.filter;

import com.google.common.collect.Streams;
import java.util.stream.Stream;
import me.shedaniel.rei.api.client.entry.filtering.FilteringContext;
import me.shedaniel.rei.api.client.entry.filtering.FilteringResult;
import me.shedaniel.rei.api.client.entry.filtering.FilteringResultFactory;
import me.shedaniel.rei.api.client.entry.filtering.FilteringRule;
import me.shedaniel.rei.api.client.entry.filtering.FilteringRuleType;
import me.shedaniel.rei.api.common.entry.EntryStack;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFItems;

public class HideItemFilterRule implements FilteringRule<Object> {
   public static HideItemFilterRule INSTANCE = new HideItemFilterRule();

   public FilteringRuleType<? extends FilteringRule<Object>> getType() {
      return HideItemFilterType.INSTANCE;
   }

   public FilteringResult processFilteredStacks(FilteringContext context, FilteringResultFactory resultFactory, Object o, boolean async) {
      FilteringResult filteringResult = resultFactory.create();
      Stream<EntryStack<?>> all = Streams.concat(new Stream[]{context.getShownStacks().stream(), context.getUnsetStacks().stream()});
      filteringResult.hide(
         all.filter(
               stack -> TFItems.MAGIC_PAINTING.getId().equals(stack.getIdentifier())
                  || TFItems.FOUR_LEAF_CLOVER.getId().equals(stack.getIdentifier())
                  || TFItems.STALE_BREAD.getId().equals(stack.getIdentifier())
                  || stack.getTagsFor().anyMatch(tagKey -> tagKey.equals(ItemTagGenerator.WIP))
            )
            .toList()
      );
      return filteringResult;
   }
}
