package twilightforest.compat.rei.filter;

import me.shedaniel.rei.api.client.entry.filtering.FilteringRuleType;
import net.minecraft.nbt.CompoundTag;

public class HideItemFilterType implements FilteringRuleType<HideItemFilterRule> {
   public static HideItemFilterType INSTANCE = new HideItemFilterType();

   public CompoundTag saveTo(HideItemFilterRule rule, CompoundTag tag) {
      return tag;
   }

   public HideItemFilterRule readFrom(CompoundTag tag) {
      return HideItemFilterRule.INSTANCE;
   }

   public HideItemFilterRule createNew() {
      return HideItemFilterRule.INSTANCE;
   }

   public boolean isSingular() {
      return true;
   }
}
