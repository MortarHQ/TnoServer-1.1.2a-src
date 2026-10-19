package mezz.jei.core.util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class WeakList<T> {
   @Nullable
   private List<WeakReference<T>> list;

   public void add(T item) {
      if (this.list == null) {
         this.list = new ArrayList<>();
      }

      this.list.add(new WeakReference<>(item));
   }

   public void forEach(Consumer<T> consumer) {
      if (this.list != null) {
         Iterator<WeakReference<T>> iterator = this.list.iterator();

         while (iterator.hasNext()) {
            WeakReference<T> reference = iterator.next();
            T item = reference.get();
            if (item == null) {
               iterator.remove();
            } else {
               consumer.accept(item);
            }
         }

         if (this.list.isEmpty()) {
            this.list = null;
         }
      }
   }

   public boolean isEmpty() {
      if (this.list == null) {
         return true;
      }

      Iterator<WeakReference<T>> iterator = this.list.iterator();

      while (iterator.hasNext()) {
         WeakReference<T> reference = iterator.next();
         T item = reference.get();
         if (item != null) {
            return false;
         }

         iterator.remove();
      }

      return true;
   }
}
