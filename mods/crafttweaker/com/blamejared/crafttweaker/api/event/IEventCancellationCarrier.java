package com.blamejared.crafttweaker.api.event;

public interface IEventCancellationCarrier<T> {
   boolean isCanceled(T var1);

   void cancel(T var1);
}
