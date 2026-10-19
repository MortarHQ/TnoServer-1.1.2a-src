package team.creative.creativecore.common.util.type.list;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

public class PairList<K, V> extends ArrayList<Pair<K, V>> {
   protected HashMap<K, Integer> keyIndex = new LinkedHashMap<>();
   protected List<V> values = new ArrayList<>();

   public PairList() {
   }

   public PairList(List<Pair<K, V>> list) {
      super(list);
      this.updateEntireMap();
   }

   protected void updateEntireMap() {
      this.keyIndex.clear();
      this.values.clear();

      for (int i = 0; i < this.size(); i++) {
         this.keyIndex.put(this.get(i).key, i);
         this.values.add(this.get(i).value);
      }
   }

   public boolean add(Pair<K, V> e) {
      Objects.requireNonNull(e);
      if (this.keyIndex.containsKey(e.key)) {
         throw new IllegalArgumentException("Duplicates are not allowed key: " + e.key);
      } else if (super.add(e)) {
         this.keyIndex.put(e.key, this.size() - 1);
         this.values.add(e.value);
         return true;
      } else {
         return false;
      }
   }

   public void add(int index, Pair<K, V> element) {
      Objects.requireNonNull(element);
      if (this.keyIndex.containsKey(element.key)) {
         throw new IllegalArgumentException("Duplicates are not allowed key: " + element.key);
      }

      super.add(index, element);
      this.updateEntireMap();
   }

   @Override
   public boolean addAll(Collection<? extends Pair<K, V>> c) {
      Objects.requireNonNull(c);

      for (Pair<K, V> pair : c) {
         Objects.requireNonNull(pair);
         if (this.keyIndex.containsKey(pair.key)) {
            throw new IllegalArgumentException("Duplicates are not allowed key: " + pair.key);
         }
      }

      int sizeBefore = this.size();
      if (!super.addAll(c)) {
         return false;
      }

      for (int i = 0; i < c.size(); i++) {
         int index = sizeBefore + i;
         this.keyIndex.put(this.get(index).key, index);
         this.values.add(this.get(index).value);
      }

      return true;
   }

   @Override
   public boolean addAll(int index, Collection<? extends Pair<K, V>> c) {
      Objects.requireNonNull(c);

      for (Pair<K, V> pair : c) {
         Objects.requireNonNull(pair);
         if (this.keyIndex.containsKey(pair.key)) {
            throw new IllegalArgumentException("Duplicates are not allowed key: " + pair.key);
         }
      }

      if (super.addAll(index, c)) {
         this.updateEntireMap();
         return true;
      } else {
         return false;
      }
   }

   public boolean add(K key, V value) {
      return this.add(new Pair<>(key, value));
   }

   public void set(K key, V value) {
      Pair<K, V> pair = this.getPair(key);
      if (pair != null) {
         pair.value = value;
      }
   }

   public Pair<K, V> remove(int index) {
      Pair<K, V> pair;
      if ((pair = (Pair<K, V>)super.remove(index)) != null) {
         this.updateEntireMap();
         return pair;
      } else {
         return null;
      }
   }

   @Override
   public boolean remove(Object o) {
      if (super.remove(o)) {
         this.updateEntireMap();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean removeAll(Collection<?> c) {
      if (super.removeAll(c)) {
         this.updateEntireMap();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean removeIf(Predicate<? super Pair<K, V>> filter) {
      if (super.removeIf(filter)) {
         this.updateEntireMap();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean retainAll(Collection<?> c) {
      if (super.retainAll(c)) {
         this.updateEntireMap();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void clear() {
      super.clear();
      this.keyIndex.clear();
      this.values.clear();
   }

   public Pair<K, V> set(int index, Pair<K, V> element) {
      Objects.requireNonNull(element);
      Integer exisiting = this.keyIndex.get(element.key);
      if (exisiting != null && exisiting != index) {
         throw new IllegalArgumentException("Duplicates are not allowed key: " + element.key);
      }

      Pair<K, V> old = super.set(index, element);
      if (old != null) {
         this.keyIndex.remove(old.key);
         this.keyIndex.put(element.key, index);
         this.values.set(index, element.value);
      }

      return old;
   }

   @Override
   public void sort(Comparator<? super Pair<K, V>> c) {
      super.sort(c);
      this.updateEntireMap();
   }

   public boolean containsKey(K key) {
      return this.keyIndex.containsKey(key);
   }

   public int indexOfKey(K key) {
      return this.keyIndex.getOrDefault(key, -1);
   }

   public boolean removeKey(K key) {
      Integer index = this.keyIndex.get(key);
      return index != null ? this.remove(index.intValue()) != null : false;
   }

   public List<V> values() {
      return ImmutableList.copyOf(this.values);
   }

   public Set<K> keys() {
      return this.keyIndex.keySet();
   }

   public Pair<K, V> getFirst() {
      return this.isEmpty() ? null : this.get(0);
   }

   public Pair<K, V> getLast() {
      return this.isEmpty() ? null : this.get(this.size() - 1);
   }

   @Nullable
   public V getValue(K key) {
      Integer index = this.keyIndex.get(key);
      return index != null ? this.get(index).value : null;
   }

   @Nullable
   public Pair<K, V> getPair(K key) {
      Integer index = this.keyIndex.get(key);
      return index != null ? this.get(index) : null;
   }

   public void setKey(int index, K key) {
      this.set(index, new Pair<>(key, this.get(index).value));
      this.updateEntireMap();
   }
}
