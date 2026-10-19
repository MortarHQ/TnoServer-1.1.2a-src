package com.tom.storagemod.util;

import java.util.Comparator;
import java.util.function.Function;
import net.minecraft.util.Mth;

public enum Priority {
   LOWEST,
   LOW,
   NORMAL,
   HIGH,
   HIGHEST;

   public static final Priority[] VALUES = values();

   public Priority add(Priority other) {
      if (this == NORMAL) {
         return other;
      } else {
         return other == NORMAL ? this : fromSorting(this.getSorting() + other.getSorting());
      }
   }

   public int getSorting() {
      return this.ordinal() - 2;
   }

   public static Priority fromSorting(int sorting) {
      return values()[Mth.clamp(sorting + 2, 0, values().length - 1)];
   }

   public interface IPriority {
      Function<Object, Priority> GETTER = Priority.IPriority::get;

      Priority getPriority();

      static <T> Comparator<T> compare() {
         return Comparator.comparing(GETTER);
      }

      static Priority get(Object o) {
         return o instanceof Priority.IPriority p ? p.getPriority() : Priority.NORMAL;
      }
   }
}
