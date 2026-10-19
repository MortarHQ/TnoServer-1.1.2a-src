package live;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import listeners.ListenerHandle;
import listeners.ListenerList;

public class LiveMappedList<T, U> implements AutoCloseable, LiveList<U> {
   private final ListenerList<LiveList.Listener<U>> listeners = new ListenerList<>();
   private final Function<T, U> projection;
   private final List<U> mapped;
   private final ListenerHandle<LiveList.Listener<T>> mappingListenerHandle;

   public LiveMappedList(LiveList<T> original, Function<T, U> projection) {
      this.projection = projection;
      this.mappingListenerHandle = original.addListener(new LiveMappedList.MappingListener());
      this.mapped = new ArrayList<>();

      for (T originalItem : original) {
         this.mapped.add(projection.apply(originalItem));
      }
   }

   @Override
   public void close() {
      this.mappingListenerHandle.close();
   }

   @Override
   public int indexOf(U value) {
      return this.mapped.indexOf(value);
   }

   @Override
   public int getLength() {
      return this.mapped.size();
   }

   @Override
   public U getAt(int index) {
      return this.mapped.get(index);
   }

   @Override
   public Iterator<U> iterator() {
      return this.mapped.iterator();
   }

   @Override
   public ListenerHandle<LiveList.Listener<U>> addListener(LiveList.Listener<U> listener) {
      return this.listeners.add(listener);
   }

   private class MappingListener implements LiveList.Listener<T> {
      @Override
      public void onInserted(int index, T value) {
         U mappedValue = LiveMappedList.this.projection.apply(value);
         LiveMappedList.this.mapped.add(index, mappedValue);
         LiveMappedList.this.listeners.accept(listener -> listener.onInserted(index, mappedValue));
      }

      @Override
      public void onChanged(int index, T oldValue, T newValue) {
         U mappedNewValue = LiveMappedList.this.projection.apply(newValue);
         U mappedOldValue = LiveMappedList.this.mapped.set(index, mappedNewValue);
         LiveMappedList.this.listeners.accept(listener -> listener.onChanged(index, mappedOldValue, mappedNewValue));
      }

      @Override
      public void onRemoved(int index, T oldValue) {
         U oldMappedValue = LiveMappedList.this.mapped.remove(index);
         if (oldMappedValue instanceof AutoCloseable) {
            try {
               ((AutoCloseable)oldMappedValue).close();
            } catch (Exception var5) {
            }
         }

         LiveMappedList.this.listeners.accept(listener -> listener.onRemoved(index, oldMappedValue));
      }
   }
}
