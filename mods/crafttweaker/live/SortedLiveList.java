package live;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import listeners.ListenerHandle;
import listeners.ListenerList;

public class SortedLiveList<T> implements LiveList<T>, LiveList.Listener<T> {
   private final LiveList<T> original;
   private final Comparator<T> ordering;
   private final List<T> sorted;
   private final ListenerHandle<LiveList.Listener<T>> originalListener;
   private final ListenerList<LiveList.Listener<T>> listeners = new ListenerList<>();

   public SortedLiveList(LiveList<T> original, Comparator<T> ordering) {
      this.original = original;
      this.ordering = ordering;
      this.sorted = new ArrayList<>();

      for (T item : original) {
         this.sorted.add(item);
      }

      Collections.sort(this.sorted, ordering);
      this.originalListener = original.addListener(this);
   }

   @Override
   public void close() {
      this.original.close();
      this.originalListener.close();
   }

   @Override
   public int indexOf(T value) {
      return this.sorted.indexOf(value);
   }

   @Override
   public int getLength() {
      return this.sorted.size();
   }

   @Override
   public T getAt(int index) {
      return this.sorted.get(index);
   }

   @Override
   public Iterator<T> iterator() {
      return this.sorted.iterator();
   }

   @Override
   public ListenerHandle<LiveList.Listener<T>> addListener(LiveList.Listener<T> listener) {
      return this.listeners.add(listener);
   }

   @Override
   public void onInserted(int index, T value) {
      this.internalAdd(value);
   }

   @Override
   public void onChanged(int index, T oldValue, T newValue) {
      this.internalRemove(oldValue);
      this.internalAdd(newValue);
   }

   @Override
   public void onRemoved(int index, T oldValue) {
      this.internalRemove(oldValue);
   }

   private void internalAdd(T value) {
      int atIndex = 0;

      while (atIndex < this.sorted.size() && this.ordering.compare(value, this.sorted.get(atIndex)) > 0) {
         atIndex++;
      }

      this.sorted.add(atIndex, value);
      int finalAtIndex = atIndex;
      this.listeners.accept(listener -> listener.onInserted(finalAtIndex, value));
   }

   private void internalRemove(T value) {
      int index = this.sorted.indexOf(value);
      if (index >= 0) {
         this.sorted.remove(index);
         this.listeners.accept(listener -> listener.onRemoved(index, value));
      }
   }
}
