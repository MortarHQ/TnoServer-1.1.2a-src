package team.creative.creativecore.common.util.type.list;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import team.creative.creativecore.common.util.type.itr.FunctionIterator;

public class TupleList<K, V> extends ArrayList<Tuple<K, V>> {
   public TupleList() {
   }

   public TupleList(int capacity) {
      super(capacity);
   }

   public TupleList(List<Tuple<K, V>> list) {
      super(list);
   }

   public boolean add(K key, V value) {
      return this.add(new Tuple<>(key, value));
   }

   public boolean containsKey(K key) {
      return this.indexOfKey(key) != -1;
   }

   public int indexOfKey(K key) {
      for (int i = 0; i < this.size(); i++) {
         if (this.get(i).key.equals(key)) {
            return i;
         }
      }

      return -1;
   }

   public Iterable<V> values() {
      return new FunctionIterator<>(this, x -> x.value);
   }

   public Iterable<K> keys() {
      return new FunctionIterator<>(this, x -> x.key);
   }

   @Nullable
   public V findValue(K key) {
      Tuple<K, V> tuple = this.findTuple(key);
      return tuple != null ? tuple.value : null;
   }

   @Nullable
   public Tuple<K, V> findTuple(K key) {
      int index = this.indexOfKey(key);
      return index != -1 ? this.get(index) : null;
   }
}
