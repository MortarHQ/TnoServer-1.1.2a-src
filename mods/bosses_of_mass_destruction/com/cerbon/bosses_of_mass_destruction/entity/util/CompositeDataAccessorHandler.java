package com.cerbon.bosses_of_mass_destruction.entity.util;

import java.util.Arrays;
import java.util.List;
import net.minecraft.network.syncher.EntityDataAccessor;

public class CompositeDataAccessorHandler implements IDataAccessorHandler {
   private final List<IDataAccessorHandler> handlerList;

   public CompositeDataAccessorHandler(IDataAccessorHandler... dataHandlers) {
      this.handlerList = Arrays.asList(dataHandlers);
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
      for (IDataAccessorHandler handler : this.handlerList) {
         handler.onSyncedDataUpdated(data);
      }
   }
}
