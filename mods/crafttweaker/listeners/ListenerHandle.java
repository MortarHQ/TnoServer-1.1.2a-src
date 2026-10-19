package listeners;

public interface ListenerHandle<T> extends AutoCloseable {
   @Override
   void close();

   T getListener();
}
