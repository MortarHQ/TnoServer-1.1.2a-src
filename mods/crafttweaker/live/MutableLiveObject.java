package live;

public interface MutableLiveObject<T> extends LiveObject<T> {
   void setValue(T var1);
}
