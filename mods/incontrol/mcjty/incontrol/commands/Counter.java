package mcjty.incontrol.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Counter<T> {
   private Map<T, Integer> internalMap = new HashMap<>();

   public void add(T key) {
      if (!this.internalMap.containsKey(key)) {
         this.internalMap.put(key, 0);
      }

      this.internalMap.put(key, this.internalMap.get(key) + 1);
   }

   public Map<T, Integer> getMap() {
      return this.internalMap;
   }

   public int get(T key) {
      return this.internalMap.containsKey(key) ? this.internalMap.get(key) : 0;
   }

   public T getMostOccuring() {
      T max = null;
      int maxCount = -1;

      for (Entry<T, Integer> entry : this.internalMap.entrySet()) {
         if (entry.getValue() > maxCount) {
            maxCount = entry.getValue();
            max = entry.getKey();
         }
      }

      return max;
   }
}
