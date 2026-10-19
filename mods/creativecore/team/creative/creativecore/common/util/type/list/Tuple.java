package team.creative.creativecore.common.util.type.list;

import java.util.Map.Entry;

public class Tuple<K, V> implements Entry<K, V> {
   public K key;
   public V value;

   public Tuple(K key, V value) {
      this.key = key;
      this.value = value;
   }

   @Override
   public K getKey() {
      return this.key;
   }

   @Override
   public V getValue() {
      return this.value;
   }

   @Override
   public V setValue(V value) {
      this.value = value;
      return value;
   }

   @Override
   public int hashCode() {
      return this.key.hashCode();
   }

   public boolean is(K key) {
      return this.key != null ? this.key.equals(key) : key == null;
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof Tuple tuple ? this.key.equals(tuple.key) : false;
   }

   @Override
   public String toString() {
      return "(" + this.key + "," + this.value + ")";
   }
}
