package live;

import java.util.Iterator;
import listeners.ListenerHandle;
import listeners.ListenerList;

public class LivePrefixedList<T> implements LiveList<T> {
   private final ListenerList<LiveList.Listener<T>> listeners = new ListenerList<>();
   private final T prefix;
   private final LiveList<T> values;
   private final ListenerHandle<LiveList.Listener<T>> baseListener;

   public LivePrefixedList(T prefix, LiveList<T> values) {
      this.prefix = prefix;
      this.values = values;
      this.baseListener = values.addListener(new LivePrefixedList.BaseListener());
   }

   @Override
   public void close() {
      this.baseListener.close();
   }

   @Override
   public int indexOf(T value) {
      if (value.equals(this.prefix)) {
         return 0;
      }

      int base = this.values.indexOf(value);
      return base < 0 ? base : base - 1;
   }

   @Override
   public int getLength() {
      return this.values.getLength() + 1;
   }

   @Override
   public T getAt(int index) {
      return index == 0 ? this.prefix : this.values.getAt(index);
   }

   @Override
   public Iterator<T> iterator() {
      return new LivePrefixedList.PrefixIterator();
   }

   @Override
   public ListenerHandle<LiveList.Listener<T>> addListener(LiveList.Listener<T> listener) {
      return this.listeners.add(listener);
   }

   private class BaseListener implements LiveList.Listener<T> {
      @Override
      public void onInserted(int index, T value) {
         LivePrefixedList.this.listeners.accept(listener -> listener.onInserted(index + 1, value));
      }

      @Override
      public void onChanged(int index, T oldValue, T newValue) {
         LivePrefixedList.this.listeners.accept(listener -> listener.onChanged(index + 1, oldValue, newValue));
      }

      @Override
      public void onRemoved(int index, T oldValue) {
         LivePrefixedList.this.listeners.accept(listener -> listener.onRemoved(index + 1, oldValue));
      }
   }

   private class PrefixIterator implements Iterator<T> {
      private boolean first = true;
      private Iterator<T> others = LivePrefixedList.this.values.iterator();

      @Override
      public boolean hasNext() {
         return this.first || this.others.hasNext();
      }

      @Override
      public T next() {
         if (this.first) {
            this.first = false;
            return LivePrefixedList.this.prefix;
         } else {
            return this.others.next();
         }
      }
   }
}
