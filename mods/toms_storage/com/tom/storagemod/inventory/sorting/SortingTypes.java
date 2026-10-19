package com.tom.storagemod.inventory.sorting;

import com.tom.storagemod.inventory.TerminalItemStack;
import java.util.Comparator;
import java.util.function.Supplier;

public enum SortingTypes {
   AMOUNT(ComparatorAmount::new),
   NAME(ComparatorName::new),
   MOD(ComparatorMod::new),
   SPACE_EFFICIENCY(ComparatorSpaceEfficiency::new),
   ID(ComparatorID::new),
   TYPE(ComparatorType::new);

   public static final SortingTypes[] VALUES = values();
   private final Supplier<Comparator<TerminalItemStack>> factory;

   SortingTypes(Supplier<Comparator<TerminalItemStack>> factory) {
      this.factory = factory;
   }

   public Comparator<TerminalItemStack> create(boolean reversed) {
      return reversed ? this.factory.get().reversed() : this.factory.get();
   }
}
