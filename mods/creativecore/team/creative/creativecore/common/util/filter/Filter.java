package team.creative.creativecore.common.util.filter;

@FunctionalInterface
public interface Filter<T> {
   FilterSerializer SERIALIZER = new FilterSerializer();

   boolean is(T var1);

   static <T> Filter<T> and(Filter<T>... filters) {
      return new Filter.FilterAnd<>(filters);
   }

   static <T> Filter<T> or(Filter<T>... filters) {
      return new Filter.FilterOr<>(filters);
   }

   static <T> Filter<T> not(Filter<T> filter) {
      return new Filter.FilterNot<>(filter);
   }

   record FilterAnd<T>(Filter<T>... filters) implements Filter<T> {
      @Override
      public boolean is(T t) {
         for (int i = 0; i < this.filters.length; i++) {
            if (!this.filters[i].is(t)) {
               return false;
            }
         }

         return true;
      }
   }

   record FilterNot<T>(Filter<T> filter) implements Filter<T> {
      @Override
      public boolean is(T t) {
         return !this.filter.is(t);
      }
   }

   record FilterOr<T>(Filter<T>... filters) implements Filter<T> {
      @Override
      public boolean is(T t) {
         for (int i = 0; i < this.filters.length; i++) {
            if (this.filters[i].is(t)) {
               return true;
            }
         }

         return false;
      }
   }
}
