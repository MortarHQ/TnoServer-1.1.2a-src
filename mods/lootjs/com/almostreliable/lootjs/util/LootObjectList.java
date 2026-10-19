package com.almostreliable.lootjs.util;

import com.almostreliable.lootjs.core.filters.IdFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;
import javax.annotation.Nullable;

public abstract class LootObjectList<T> extends ArrayList<T> {
   public LootObjectList() {
   }

   public LootObjectList(int initialCapacity) {
      super(initialCapacity);
   }

   public LootObjectList(Collection<T> entries) {
      super(entries);
   }

   public void removeById(IdFilter filter) {
      this.removeIf(entry -> this.entryMatches((T)entry, filter));
   }

   public void transform(NullableFunction<T, Object> onTransform) {
      ListIterator<T> it = this.listIterator();

      while (it.hasNext()) {
         T t = it.next();
         Object o = onTransform.apply(t);
         T transformed = this.wrapTransformed(o);
         if (transformed == null) {
            it.remove();
         } else if (transformed != t) {
            it.set(transformed);
         }
      }
   }

   @Nullable
   protected abstract T wrapTransformed(@Nullable Object var1);

   protected abstract boolean entryMatches(T var1, IdFilter var2);
}
