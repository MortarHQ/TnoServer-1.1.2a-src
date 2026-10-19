package com.tom.storagemod.block.entity;

import com.tom.storagemod.inventory.IInventoryAccess;
import java.util.Collection;

public interface IInventoryConnector {
   IInventoryAccess getMergedHandler();

   Collection<IInventoryAccess> getConnectedInventories();

   Collection<IInventoryConnector> getConnectedConnectors();

   boolean hasConnectedInventories();
}
