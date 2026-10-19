package live;

import java.util.function.BiConsumer;
import listeners.ListenerHandle;
import listeners.ListenerList;

public final class SimpleLiveString implements MutableLiveString {
   private final ListenerList<BiConsumer<String, String>> listeners = new ListenerList<>();
   private String value;

   public SimpleLiveString(String value) {
      this.value = value;
   }

   @Override
   public ListenerHandle<BiConsumer<String, String>> addListener(BiConsumer<String, String> listener) {
      return this.listeners.add(listener);
   }

   @Override
   public String getValue() {
      return this.value;
   }

   @Override
   public void setValue(String value) {
      if (!value.equals(this.value)) {
         String oldValue = this.value;
         this.value = value;
         this.listeners.accept(listener -> listener.accept(oldValue, this.value));
      }
   }
}
