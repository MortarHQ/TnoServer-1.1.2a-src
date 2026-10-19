package team.creative.creativecore.common.util.filter;

@FunctionalInterface
public interface BiFilter<T, U> {
   BiFilterSerializer SERIALIZER = new BiFilterSerializer();

   boolean is(T var1, U var2);

   @SafeVarargs
   static <T, U> BiFilter<T, U> and(BiFilter<T, U>... filters) {
      return new BiFilter.BiFilterAnd<>(filters);
   }

   @SafeVarargs
   static <T, U> BiFilter<T, U> or(BiFilter<T, U>... filters) {
      return new BiFilter.BiFilterOr<>(filters);
   }

   static <T, U> BiFilter<T, U> not(BiFilter<T, U> filter) {
      return new BiFilter.BiFilterNot<>(filter);
   }

   record BiFilterAnd<T, U>(BiFilter<T, U>... filters) implements BiFilter<T, U> {
      @SafeVarargs
      public BiFilterAnd {
      }

      @Override
      public boolean is(T t, U u) {
         for (int i = 0; i < this.filters.length; i++) {
            if (!this.filters[i].is(t, u)) {
               return false;
            }
         }

         return true;
      }
   }

   record BiFilterNot<T, U>(BiFilter<T, U> filter) implements BiFilter<T, U> {
      @Override
      public boolean is(T t, U u) {
         return !this.filter.is(t, u);
      }
   }

   record BiFilterOr<T, U>(BiFilter<T, U>... filters) implements BiFilter<T, U> {
      @SafeVarargs
      public BiFilterOr {
      }

      @Override
      public boolean is(T t, U u) {
         for (int i = 0; i < this.filters.length; i++) {
            if (this.filters[i].is(t, u)) {
               return true;
            }
         }

         return false;
      }
   }
}
