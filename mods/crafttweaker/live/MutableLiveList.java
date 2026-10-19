package live;

public interface MutableLiveList<T> extends AutoCloseable, LiveList<T> {
   void add(T var1);

   void insert(int var1, T var2);

   void setAt(int var1, T var2);

   void remove(int var1);

   void remove(T var1);

   void clear();

   @Override
   void close();
}
