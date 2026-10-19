package team.creative.creativecore.common.util.type.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

public class HashMapList<K, V> implements Iterable<V> {
   private final LinkedHashMap<K, ArrayList<V>> keys = new LinkedHashMap<>();

   public HashMapList() {
   }

   public HashMapList(HashMapList<K, V> object) {
      this();

      for (Entry<K, ArrayList<V>> entry : object.entrySet()) {
         this.keys.put(entry.getKey(), new ArrayList<>(entry.getValue()));
      }
   }

   public ArrayList<V> get(K key) {
      return this.keys.get(key);
   }

   public List<V> tryGet(K key) {
      List<V> result = this.keys.get(key);
      return result != null ? result : Collections.EMPTY_LIST;
   }

   public K keyOf(V search) {
      for (Entry<K, ArrayList<V>> entry : this.keys.entrySet()) {
         if (entry.getValue().contains(search)) {
            return entry.getKey();
         }
      }

      return null;
   }

   public Set<K> keySet() {
      return this.keys.keySet();
   }

   public Collection<ArrayList<V>> values() {
      return this.keys.values();
   }

   public Set<Entry<K, ArrayList<V>>> entrySet() {
      return this.keys.entrySet();
   }

   public boolean containsKey(K key) {
      return this.keys.containsKey(key);
   }

   public boolean contains(V value) {
      for (ArrayList<V> type : this.values()) {
         if (type.contains(value)) {
            return true;
         }
      }

      return false;
   }

   public boolean contains(K key, V value) {
      ArrayList<V> list = this.get(key);
      return list != null ? list.contains(value) : false;
   }

   public void add(K key, V[] values) {
      this.add(key, new ArrayList<>(Arrays.asList(values)));
   }

   public void add(K key, Collection<V> values) {
      ArrayList<V> list = this.get(key);
      if (list == null) {
         this.keys.put(key, new ArrayList<>(values));
      } else {
         list.addAll(values);
      }
   }

   public void add(K key, V value) {
      ArrayList<V> list = this.get(key);
      if (list == null) {
         list = new ArrayList<>();
         list.add(value);
         this.keys.put(key, list);
      } else {
         list.add(value);
      }
   }

   public ArrayList<V> removeKey(K key) {
      return this.keys.remove(key);
   }

   public boolean removeValue(K key, V value) {
      ArrayList<V> values = this.get(key);
      if (values != null && values.remove(value)) {
         if (values.isEmpty()) {
            this.removeKey(key);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean removeValue(V value) {
      Iterator<ArrayList<V>> iterator = this.keys.values().iterator();

      while (iterator.hasNext()) {
         ArrayList<V> values = iterator.next();
         if (values.remove(value)) {
            if (values.isEmpty()) {
               iterator.remove();
            }

            return true;
         }
      }

      return false;
   }

   public int sizeOfValues() {
      int size = 0;

      for (ArrayList<V> values : this.keys.values()) {
         size += values.size();
      }

      return size;
   }

   public int size() {
      return this.keys.size();
   }

   public void clear() {
      this.keys.clear();
   }

   @Override
   public String toString() {
      return this.keys.toString();
   }

   public boolean isEmpty() {
      return this.sizeOfValues() == 0;
   }

   public V getFirst() {
      if (this.size() > 0) {
         for (ArrayList<V> list : this.values()) {
            if (!list.isEmpty()) {
               return list.get(0);
            }
         }
      }

      return null;
   }

   @Override
   public Iterator<V> iterator() {
      return new Iterator<V>() {
         int index = 0;
         final Iterator<ArrayList<V>> iterator = HashMapList.this.values().iterator();
         ArrayList<V> currentList;

         @Override
         public boolean hasNext() {
            while (this.currentList == null || this.currentList.size() <= this.index) {
               if (!this.iterator.hasNext()) {
                  return false;
               }

               this.currentList = this.iterator.next();
               this.index = 0;
            }

            return true;
         }

         @Override
         public V next() {
            V value = this.currentList.get(this.index);
            this.index++;
            return value;
         }

         @Override
         public void remove() {
            this.currentList.remove(this.index - 1);
         }
      };
   }
}
