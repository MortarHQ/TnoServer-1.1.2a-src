package net.p3pp3rf1y.sophisticatedcore.inventory;

import java.util.Set;
import java.util.function.Consumer;

public interface ITrackedContentsItemHandler extends IItemHandlerSimpleInserter, IItemHandlerSimpleExtractor, IInsertBlockOverride {
   Set<ItemStackKey> getTrackedStacks();

   void registerTrackingListeners(Consumer<ItemStackKey> var1, Consumer<ItemStackKey> var2, Runnable var3, Runnable var4);

   void unregisterStackKeyListeners();

   boolean hasEmptySlots();
}
