package team.creative.creativecore.common.util.type.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.ToIntFunction;
import team.creative.creativecore.common.util.type.itr.InverseConsecutiveIterator;
import team.creative.creativecore.common.util.type.itr.InverseListIterator;
import team.creative.creativecore.common.util.type.itr.NestedIterator;

public class BucketList<T> implements Iterable<T> {
   private final List<List<T>> buckets = new ArrayList<>();
   private int size = 0;

   public BucketList() {
   }

   public BucketList(int initialBucketSize) {
      this.ensureBucketSize(initialBucketSize);
   }

   public BucketList(Iterable<T> content, ToIntFunction<T> bucketSorter) {
      this.addAll(content, bucketSorter);
   }

   private void ensureBucketSize(int size) {
      for (int i = this.buckets.size(); i < size; i++) {
         this.buckets.add(new ArrayList<>());
      }
   }

   private void recountSize() {
      this.size = 0;

      for (List<T> bucket : this.buckets) {
         this.size = this.size + bucket.size();
      }
   }

   protected List<T> getBucket(int bucket, boolean create) {
      if (create) {
         this.ensureBucketSize(bucket);
         return this.buckets.get(bucket);
      } else if (this.buckets.size() > bucket) {
         throw new IllegalArgumentException("Bucket index '" + bucket + "' is out of bounds (total " + this.buckets.size() + ")");
      } else {
         return this.buckets.get(bucket);
      }
   }

   public void add(int bucket, T element) {
      this.ensureBucketSize(bucket);
      this.getBucket(bucket, true).add(element);
      this.size++;
   }

   public void addAll(Iterable<T> content, ToIntFunction<T> bucketSorter) {
      for (T element : content) {
         this.add(bucketSorter.applyAsInt(element), element);
      }
   }

   public void remove(int bucket, T element) {
      if (this.buckets.size() > bucket && this.getBucket(bucket, false).remove(element)) {
         this.size--;
      }
   }

   public void removeAll(int bucket, Collection<T> elements) {
      if (this.buckets.size() > bucket && this.getBucket(bucket, false).removeAll(elements)) {
         this.recountSize();
      }
   }

   public List<T> removeBucket(int bucket) {
      return this.buckets.remove(bucket);
   }

   public void clear() {
      for (List<T> bucket : this.buckets) {
         bucket.clear();
      }
   }

   public void removeBuckets() {
      this.buckets.clear();
   }

   public Iterable<? extends Iterable<T>> buckets() {
      return this.buckets;
   }

   public int bucketCount() {
      return this.buckets.size();
   }

   public int size() {
      return this.size;
   }

   @Override
   public Iterator<T> iterator() {
      return new NestedIterator<>(this.buckets);
   }

   public Iterator<T> inverseIterator() {
      Iterator[] itrs = new Iterator[this.buckets.size()];

      for (int i = 0; i < itrs.length; i++) {
         itrs[i] = new InverseListIterator<>(this.buckets.get(i));
      }

      return new InverseConsecutiveIterator<>(itrs);
   }
}
