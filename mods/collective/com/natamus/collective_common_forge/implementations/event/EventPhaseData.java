package com.natamus.collective_common_forge.implementations.event;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

class EventPhaseData<T> {
   final ResourceLocation id;
   T[] listeners;
   final List<EventPhaseData<T>> subsequentPhases = new ArrayList<>();
   final List<EventPhaseData<T>> previousPhases = new ArrayList<>();
   int visitStatus = 0;

   EventPhaseData(ResourceLocation id, Class<?> listenerClass) {
      this.id = id;
      this.listeners = (T[])((Object[])Array.newInstance(listenerClass, 0));
   }

   void addListener(T listener) {
      int oldLength = this.listeners.length;
      this.listeners = (T[])Arrays.copyOf(this.listeners, oldLength + 1);
      this.listeners[oldLength] = listener;
   }
}
