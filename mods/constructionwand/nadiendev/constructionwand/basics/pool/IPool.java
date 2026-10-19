package nadiendev.constructionwand.basics.pool;

import javax.annotation.Nullable;

public interface IPool<T> {
   void add(T var1);

   void remove(T var1);

   @Nullable
   T draw();

   void reset();
}
