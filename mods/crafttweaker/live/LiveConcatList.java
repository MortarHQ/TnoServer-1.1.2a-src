package live;

import java.util.Iterator;
import listeners.ListenerHandle;
import listeners.ListenerList;

public class LiveConcatList<T> implements AutoCloseable, LiveList<T> {
   private final ListenerList<LiveList.Listener<T>> listeners = new ListenerList<>();
   private final LiveList<T> a;
   private final LiveList<T> b;
   private final ListenerHandle<LiveList.Listener<T>> aListener;
   private final ListenerHandle<LiveList.Listener<T>> bListener;

   public LiveConcatList(LiveList<T> a, LiveList<T> b) {
      this.a = a;
      this.b = b;
      this.aListener = a.addListener(new LiveConcatList.FirstListListener());
      this.bListener = b.addListener(new LiveConcatList.SecondListListener());
   }

   @Override
   public void close() {
      this.aListener.close();
      this.bListener.close();
   }

   @Override
   public int indexOf(T value) {
      int result = this.a.indexOf(value);
      if (result >= 0) {
         return result;
      }

      result = this.b.indexOf(value);
      return result < 0 ? -1 : result + this.a.getLength();
   }

   @Override
   public int getLength() {
      return this.a.getLength() + this.b.getLength();
   }

   @Override
   public T getAt(int index) {
      return index < this.a.getLength() ? this.a.getAt(index) : this.b.getAt(index - this.a.getLength());
   }

   @Override
   public Iterator<T> iterator() {
      return new LiveConcatList.ConcatIterator();
   }

   @Override
   public ListenerHandle<LiveList.Listener<T>> addListener(LiveList.Listener<T> listener) {
      return this.listeners.add(listener);
   }

   private class ConcatIterator implements Iterator<T> {
      private boolean firstList = true;
      private Iterator<T> iterator = LiveConcatList.this.a.iterator();

      public ConcatIterator() {
         if (!this.iterator.hasNext()) {
            this.firstList = false;
            this.iterator = LiveConcatList.this.b.iterator();
         }
      }

      @Override
      public boolean hasNext() {
         return this.iterator.hasNext();
      }

      @Override
      public T next() {
         T result = this.iterator.next();
         if (this.firstList && !this.iterator.hasNext()) {
            this.firstList = false;
            this.iterator = LiveConcatList.this.b.iterator();
         }

         return result;
      }
   }

   private class FirstListListener implements LiveList.Listener<T> {
      @Override
      public void onInserted(int index, T value) {
         LiveConcatList.this.listeners.accept(listener -> listener.onInserted(index, value));
      }

      @Override
      public void onChanged(int index, T oldValue, T newValue) {
         LiveConcatList.this.listeners.accept(listener -> listener.onChanged(index, oldValue, newValue));
      }

      @Override
      public void onRemoved(int index, T oldValue) {
         LiveConcatList.this.listeners.accept(listener -> listener.onRemoved(index, oldValue));
      }
   }

   private class SecondListListener implements LiveList.Listener<T> {
      @Override
      public void onInserted(int index, T value) {
         LiveConcatList.this.listeners.accept(listener -> listener.onInserted(LiveConcatList.this.a.getLength() + index, value));
      }

      @Override
      public void onChanged(int index, T oldValue, T newValue) {
         LiveConcatList.this.listeners.accept(listener -> listener.onChanged(LiveConcatList.this.a.getLength() + index, oldValue, newValue));
      }

      @Override
      public void onRemoved(int index, T oldValue) {
         LiveConcatList.this.listeners.accept(listener -> listener.onRemoved(LiveConcatList.this.a.getLength() + index, oldValue));
      }
   }
}
