package net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.rei;

import me.shedaniel.rei.api.common.entry.comparison.ItemComparatorRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.forge.REIPluginCommon;
import net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.common.subtypes.SubtypeInterpreters;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.rei.comparator.ReiSubtypeInterpreter;

@REIPluginCommon
public class BackpackReiCommonPlugin implements REIServerPlugin {
   public double getPriority() {
      return 0.0;
   }

   public void registerItemComparators(ItemComparatorRegistry registry) {
      SubtypeInterpreters.getSubtypeInterpreters().forEach((item, subtypeInterpreter) -> registry.register(ReiSubtypeInterpreter.of(subtypeInterpreter), item));
   }
}
