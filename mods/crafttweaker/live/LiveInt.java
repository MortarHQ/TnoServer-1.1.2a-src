package live;

import listeners.ListenerHandle;
import zsynthetic.FunctionIntIntToVoid;

public interface LiveInt {
   int getValue();

   void setValue(int var1);

   ListenerHandle<FunctionIntIntToVoid> addListener(FunctionIntIntToVoid var1);
}
