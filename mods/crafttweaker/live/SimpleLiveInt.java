package live;

import listeners.ListenerHandle;
import listeners.ListenerList;
import zsynthetic.FunctionIntIntToVoid;

public final class SimpleLiveInt implements MutableLiveInt {
   private final ListenerList<FunctionIntIntToVoid> listeners = new ListenerList<>();
   private int value;

   public SimpleLiveInt(int value) {
      this.value = value;
   }

   @Override
   public ListenerHandle<FunctionIntIntToVoid> addListener(FunctionIntIntToVoid listener) {
      return this.listeners.add(listener);
   }

   @Override
   public int getValue() {
      return this.value;
   }

   @Override
   public void setValue(int value) {
      if (value != this.value) {
         int oldValue = this.value;
         this.value = value;
         this.listeners.accept(listener -> listener.invoke(oldValue, this.value));
      }
   }
}
