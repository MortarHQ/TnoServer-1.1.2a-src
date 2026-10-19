package org.openzen.zencode.shared;

import stdlib.EqualsComparable;

public final class ConcatMap<K extends EqualsComparable<K>, V> {
   private final K key;
   private final V value;
   private final ConcatMap<K, V> remaining;

   private ConcatMap(K key, V value, ConcatMap<K, V> remaining) {
      this.key = key;
      this.value = value;
      this.remaining = remaining;
   }

   public static final <K extends EqualsComparable<K>, V> ConcatMap<K, V> empty(Class<K> typeOfK, Class<V> typeOfV) {
      return new ConcatMap<>(null, null, null);
   }

   public boolean getIsEmpty() {
      return this.key == null;
   }

   public ConcatMap<K, V> concat(K key, V value) {
      return new ConcatMap<>(key, value, this);
   }

   public boolean contains(K key) {
      if (this.key == null) {
         return false;
      } else if (key.equals_(this.key)) {
         return true;
      } else {
         return this.remaining == null ? false : this.remaining.contains(key);
      }
   }

   public V getAt(K key) {
      if (this.key == null) {
         return null;
      } else {
         return key.equals_(this.key) ? this.value : this.remaining.getAt(key);
      }
   }

   public V getOrDefault(K key, V defaultValue) {
      if (this.key == null) {
         return defaultValue;
      } else {
         return key.equals_(this.key) ? this.value : this.remaining.getOrDefault(key, defaultValue);
      }
   }
}
