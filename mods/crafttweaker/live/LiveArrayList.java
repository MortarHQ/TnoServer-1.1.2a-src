package live;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import listeners.ListenerHandle;
import listeners.ListenerList;

public final class LiveArrayList<T> implements MutableLiveList<T>, AutoCloseable {
   private final List<T> values = new ArrayList<>();
   private final ListenerList<LiveList.Listener<T>> listeners = new ListenerList<>();

   @Override
   public void add(T value) {
      int index = this.values.size();
      this.values.add(value);
      this.listeners.accept(listener -> listener.onInserted(index, value));
   }

   @Override
   public void insert(int index, T value) {
      this.values.add(index, value);
      this.listeners.accept(listener -> listener.onInserted(index, value));
   }

   @Override
   public void setAt(int index, T value) {
      T oldValue = this.values.get(index);
      this.values.set(index, value);
      this.listeners.accept(listener -> listener.onChanged(index, oldValue, value));
   }

   @Override
   public void remove(int index) {
      T oldValue = this.values.remove(index);
      this.listeners.accept(listener -> listener.onRemoved(index, oldValue));
   }

   @Override
   public void remove(T value) {
      int index = this.indexOf(value);
      if (index >= 0) {
         this.remove(index);
      }
   }

   @Override
   public void clear() {
      int i = this.getLength();

      while (i > 0) {
         this.remove(--i);
      }
   }

   @Override
   public Iterator<T> iterator() {
      return this.values.iterator();
   }

   @Override
   public int indexOf(T value) {
      return this.values.indexOf(value);
   }

   @Override
   public int getLength() {
      return this.values.size();
   }

   @Override
   public T getAt(int index) {
      return this.values.get(index);
   }

   @Override
   public ListenerHandle<LiveList.Listener<T>> addListener(LiveList.Listener<T> listener) {
      return this.listeners.add(listener);
   }

   @Override
   public void close() {
   }
}
