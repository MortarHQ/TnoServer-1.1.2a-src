package live;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import listeners.ListenerHandle;
import listeners.ListenerList;
import zsynthetic.FunctionBoolBoolToVoid;

public class LivePredicateBool<T> implements LiveBool, AutoCloseable, BiConsumer<T, T> {
   private final ListenerList<FunctionBoolBoolToVoid> listeners = new ListenerList<>();
   private final LiveObject<T> source;
   private final Predicate<T> predicate;
   private final ListenerHandle<BiConsumer<T, T>> sourceListener;
   private boolean value;

   public LivePredicateBool(LiveObject<T> source, Predicate<T> predicate) {
      this.source = source;
      this.predicate = predicate;
      this.sourceListener = source.addListener(this);
      this.setValueInternal(predicate.test(source.getValue()));
   }

   @Override
   public void close() {
      this.sourceListener.close();
   }

   @Override
   public boolean getValue() {
      return this.value;
   }

   @Override
   public ListenerHandle<FunctionBoolBoolToVoid> addListener(FunctionBoolBoolToVoid listener) {
      return this.listeners.add(listener);
   }

   private void setValueInternal(boolean value) {
      if (value != this.value) {
         boolean oldValue = this.value;
         this.value = value;
         this.listeners.accept(listener -> listener.invoke(oldValue, value));
      }
   }

   @Override
   public void accept(T oldValue, T newValue) {
      this.setValueInternal(this.predicate.test(newValue));
   }
}
