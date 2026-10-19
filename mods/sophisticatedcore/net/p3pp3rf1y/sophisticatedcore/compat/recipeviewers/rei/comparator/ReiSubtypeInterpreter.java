package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.rei.comparator;

import me.shedaniel.rei.api.common.entry.comparison.ComparisonContext;
import me.shedaniel.rei.api.common.entry.comparison.EntryComparator;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common.subtypes.PropertyBasedSubtypeInterpreter;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common.subtypes.PropertyBasedSubtypeInterpreterWrapper;

public class ReiSubtypeInterpreter extends PropertyBasedSubtypeInterpreterWrapper implements EntryComparator<ItemStack> {
   public static ReiSubtypeInterpreter of(PropertyBasedSubtypeInterpreter wrapped) {
      return new ReiSubtypeInterpreter(wrapped);
   }

   private ReiSubtypeInterpreter(PropertyBasedSubtypeInterpreter wrapped) {
      super(wrapped);
   }

   public long hash(ComparisonContext context, ItemStack stack) {
      long hashCode = 1L;

      for (PropertyBasedSubtypeInterpreter.IPropertyDefinition<?> definition : this.getPropertyDefinitions()) {
         Object value = definition.getPropertyValue(stack);
         hashCode = 31L * hashCode + (value == null ? 0 : value.hashCode());
      }

      return hashCode;
   }
}
