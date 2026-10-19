package live;

import listeners.ListenerHandle;

public interface LiveList<T> extends AutoCloseable, Iterable<T> {
   @Override
   void close();

   int getLength();

   int indexOf(T var1);

   T getAt(int var1);

   ListenerHandle<LiveList.Listener<T>> addListener(LiveList.Listener<T> var1);

   interface Listener<T> {
      void onInserted(int var1, T var2);

      void onChanged(int var1, T var2, T var3);

      void onRemoved(int var1, T var2);
   }
}
