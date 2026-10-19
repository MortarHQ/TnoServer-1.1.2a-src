package com.ilexiconn.llibrary.server.util;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

public class ListHashMap<K, V> extends LinkedHashMap<K, V> {
   public V getValue(int index) {
      Entry<K, V> entry = this.getEntry(index);
      return entry == null ? null : entry.getValue();
   }

   public Entry<K, V> getEntry(int index) {
      Set<Entry<K, V>> entries = this.entrySet();
      int j = 0;

      for (Entry<K, V> entry : entries) {
         if (j++ == index) {
            return entry;
         }
      }

      return null;
   }
}
