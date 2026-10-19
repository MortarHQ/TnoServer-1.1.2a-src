package com.natamus.collective_common_fabric.implementations.event;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.class_2960;

class EventPhaseData<T> {
   final class_2960 id;
   T[] listeners;
   final List<EventPhaseData<T>> subsequentPhases = new ArrayList<>();
   final List<EventPhaseData<T>> previousPhases = new ArrayList<>();
   int visitStatus = 0;

   EventPhaseData(class_2960 id, Class<?> listenerClass) {
      this.id = id;
      this.listeners = (T[])((Object[])Array.newInstance(listenerClass, 0));
   }

   void addListener(T listener) {
      int oldLength = this.listeners.length;
      this.listeners = (T[])Arrays.copyOf(this.listeners, oldLength + 1);
      this.listeners[oldLength] = listener;
   }
}
