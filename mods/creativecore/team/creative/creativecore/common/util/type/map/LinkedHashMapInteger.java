package team.creative.creativecore.common.util.type.map;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LinkedHashMapInteger<K> extends LinkedHashMap<K, Integer> {
   public LinkedHashMapInteger() {
   }

   public LinkedHashMapInteger(Map<? extends K, ? extends Integer> paramMap) {
      super(paramMap);
   }

   public Integer put(K paramK, Integer paramV) {
      Integer value = this.get(paramK);
      if (value != null) {
         value = value + paramV;
      } else {
         value = paramV;
      }

      return super.put(paramK, value);
   }

   @Override
   public void putAll(Map<? extends K, ? extends Integer> paramMap) {
      for (Entry<? extends K, ? extends Integer> entry : paramMap.entrySet()) {
         this.put((K)entry.getKey(), entry.getValue());
      }
   }
}
