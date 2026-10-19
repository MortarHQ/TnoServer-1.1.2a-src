package live;

public interface MutableLiveBool extends LiveBool {
   void setValue(boolean var1);

   default void toggle() {
      this.setValue(!this.getValue());
   }
}
