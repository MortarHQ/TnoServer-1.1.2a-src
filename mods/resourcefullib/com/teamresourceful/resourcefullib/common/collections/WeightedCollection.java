package com.teamresourceful.resourcefullib.common.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;
import java.util.stream.Stream;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

public class WeightedCollection<E> implements Collection<E> {
   private final NavigableMap<Double, E> map = new TreeMap<>();
   private WeightedCollection.WeightedCollectionRandom random;
   private double total = 0.0;

   public WeightedCollection() {
      this(new Random());
   }

   public WeightedCollection(Random random) {
      this(random::nextDouble);
   }

   public WeightedCollection(RandomSource random) {
      this(random::nextDouble);
   }

   public WeightedCollection(WeightedCollection.WeightedCollectionRandom random) {
      this.random = random;
   }

   public WeightedCollection<E> add(double weight, E result) {
      if (weight <= 0.0) {
         return this;
      }

      this.total += weight;
      this.map.put(Math.min(this.total, Double.MAX_VALUE), result);
      return this;
   }

   public E get(int index) {
      Double key = new LinkedList<>(this.map.keySet()).get(index);
      return this.map.get(key);
   }

   public E next() {
      double value = this.random.nextDouble() * this.total;
      return this.map.higherEntry(value).getValue();
   }

   public NavigableMap<Double, E> getMap() {
      return this.map;
   }

   public double getTotal() {
      return this.total;
   }

   public double getAdjustedWeight(double weight) {
      return weight / this.total;
   }

   public void forEachWithSelf(BiConsumer<WeightedCollection<E>, ? super E> action) {
      this.forEach(element -> action.accept(this, element));
   }

   public void setRandom(WeightedCollection.WeightedCollectionRandom random) {
      this.random = random;
   }

   public void setRandom(Random random) {
      this.setRandom(random::nextDouble);
   }

   public void setRandom(RandomSource random) {
      this.setRandom(random::nextDouble);
   }

   @Override
   public Stream<E> stream() {
      return this.map.values().stream();
   }

   @Override
   public int size() {
      return this.map.size();
   }

   @Override
   public boolean isEmpty() {
      return this.map.isEmpty();
   }

   @Override
   public boolean contains(Object o) {
      try {
         return this.map.containsValue(o);
      } catch (Exception e) {
         return false;
      }
   }

   @NotNull
   @Override
   public Iterator<E> iterator() {
      return this.map.values().iterator();
   }

   @Override
   public Object @NotNull [] toArray() {
      return this.map.values().toArray();
   }

   @Override
   public <T> T @NotNull [] toArray(T @NotNull [] a) {
      return (T[])this.map.values().toArray(a);
   }

   @Override
   public boolean add(E e) {
      throw new UnsupportedOperationException("Default add is not supported, use add that takes in a weight.");
   }

   @Override
   public boolean remove(Object o) {
      throw new UnsupportedOperationException("Removing not supported.");
   }

   @Override
   public boolean containsAll(@NotNull Collection<?> c) {
      for (Object o : c) {
         if (!this.contains(o)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean addAll(@NotNull Collection<? extends E> c) {
      throw new UnsupportedOperationException("Default add is not supported, use add that takes in a weight.");
   }

   @Override
   public boolean removeAll(@NotNull Collection<?> c) {
      throw new UnsupportedOperationException("Removing not supported.");
   }

   @Override
   public boolean retainAll(@NotNull Collection<?> c) {
      throw new UnsupportedOperationException("Removing not supported.");
   }

   @Override
   public void clear() {
      this.map.clear();
      this.total = 0.0;
   }

   @Override
   public boolean equals(Object o) {
      return o == this ? true : o instanceof WeightedCollection && this.map.equals(((WeightedCollection)o).map);
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.total, this.map);
   }

   public static <T> WeightedCollection<T> of(Collection<T> collection, ToDoubleFunction<T> weightGetter) {
      return collection.stream().collect(getCollector(weightGetter));
   }

   public static <T> Collector<T, ?, WeightedCollection<T>> getCollector(ToDoubleFunction<T> weightGetter) {
      return (Collector<T, ?, WeightedCollection<T>>)Collector.of(
         WeightedCollection::new, (c, t) -> c.add(weightGetter.applyAsDouble(t), (E)t), (left, right) -> {
            left.forEach(item -> right.add(weightGetter.applyAsDouble((T)item), (E)item));
            return right;
         }
      );
   }

   @FunctionalInterface
   public interface WeightedCollectionRandom {
      double nextDouble();
   }
}
