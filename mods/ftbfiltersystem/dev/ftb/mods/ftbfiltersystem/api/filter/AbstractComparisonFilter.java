package dev.ftb.mods.ftbfiltersystem.api.filter;

import dev.ftb.mods.ftbfiltersystem.api.NumericComparison;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractComparisonFilter extends AbstractSmartFilter {
   protected final NumericComparison comparison;

   protected AbstractComparisonFilter(SmartFilter.Compound parent, NumericComparison comparison) {
      super(parent);
      this.comparison = comparison;
   }

   public NumericComparison getComparison() {
      return this.comparison;
   }

   protected abstract int getValueToCompare(ItemStack var1);

   public boolean allowsPercentage() {
      return false;
   }

   public boolean test(ItemStack stack) {
      return this.comparison.test(this.getValueToCompare(stack));
   }

   @Override
   public String getStringArg(Provider registryAccess) {
      return this.comparison.toString();
   }
}
