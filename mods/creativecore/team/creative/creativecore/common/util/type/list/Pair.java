package team.creative.creativecore.common.util.type.list;

import java.util.Map.Entry;

public class Pair<K, V> implements Entry<K, V> {
   public final K key;
   public V value;

   public Pair(K key, V value) {
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
      return this.key != null ? this.key.equals(key) : false;
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof Pair ? this.key.equals(((Pair)obj).key) : false;
   }

   @Override
   public String toString() {
      return "[" + this.key + "=" + this.value + "]";
   }
}
