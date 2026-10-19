package com.finderfeed.fdlib.data_structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public class IntRangedList<T> implements Iterable<T> {
   private List<IntRangedList.IntRangedListEntry<T>> entries = new ArrayList<>();

   public IntRangedList() {
   }

   public IntRangedList(IntRangedList<T> other) {
      this.entries.addAll(other.entries);
   }

   public IntRangedList(List<T> objects, boolean isSorted, Function<T, Integer> intExtractor) {
      if (isSorted) {
         for (T o : objects) {
            this.entries.add(new IntRangedList.IntRangedListEntry<>(intExtractor.apply(o), o));
         }
      } else {
         for (T o : objects) {
            this.add(intExtractor.apply(o), o, false);
         }
      }
   }

   public T getValue(int num) {
      return this.entries.get(this.indexOf(num)).value;
   }

   public int indexOf(int num) {
      if (this.entries.isEmpty()) {
         return -1;
      }

      if (this.entries.size() == 1) {
         return 0;
      }

      IntRangedList.IntRangedListEntry<T> first = this.entries.getFirst();
      if (num <= first.num) {
         return 0;
      }

      IntRangedList.IntRangedListEntry<T> last = this.entries.getLast();
      if (num >= last.num) {
         return this.entries.size() - 1;
      }

      for (int i = 1; i < this.entries.size(); i++) {
         IntRangedList.IntRangedListEntry<T> entry = this.entries.get(i);
         if (num < entry.num) {
            return i - 1;
         }
      }

      return -1;
   }

   public List<T> getValues(int number, int before, int after) {
      List<T> r = new ArrayList<>();

      for (int i = 0; i < before + after + 1; i++) {
         r.add(null);
      }

      int entryIndex = this.indexOf(number);
      r.set(before, this.entries.get(entryIndex).value);
      int listIndex = before + 1;

      for (int t = 1; t <= after; listIndex++) {
         int id = entryIndex + t;
         if (id >= this.entries.size()) {
            break;
         }

         r.set(listIndex, this.entries.get(id).value);
         t++;
      }

      for (int var10 = 0; before > 0; var10++) {
         int id = entryIndex - before;
         if (id >= 0) {
            r.set(var10, this.entries.get(id).value);
         }

         before--;
      }

      return r;
   }

   public List<T> getAllValuesAfter(int number) {
      if (this.entries.isEmpty()) {
         return null;
      }

      int index = this.indexOf(number);
      List<T> l = new ArrayList<>();
      IntRangedList.IntRangedListEntry<T> value = this.entries.get(index);
      if (value.num >= number) {
         l.add(value.value);
      }

      for (int i = index + 1; i < this.entries.size(); i++) {
         l.add(this.entries.get(i).value);
      }

      return l;
   }

   public void add(int num, T value, boolean replace) {
      int index = 0;

      for (IntRangedList.IntRangedListEntry<T> entry : this.entries) {
         int n = entry.num;
         if (n > num) {
            break;
         }

         if (num == n) {
            if (!replace) {
               throw new RuntimeException("Trying to add element with the same int value");
            }

            this.entries.set(index, new IntRangedList.IntRangedListEntry<>(num, value));
            return;
         }

         index++;
      }

      this.entries.add(index, new IntRangedList.IntRangedListEntry<>(num, value));
   }

   public T get(int index) {
      return this.entries.get(index).value;
   }

   public int size() {
      return this.entries.size();
   }

   public T getFirst() {
      return this.entries.getFirst().value;
   }

   public T getLast() {
      return this.entries.getLast().value;
   }

   @NotNull
   @Override
   public Iterator<T> iterator() {
      return this.entries.stream().map(IntRangedList.IntRangedListEntry::value).iterator();
   }

   public boolean isEmpty() {
      return this.entries.isEmpty();
   }

   private record IntRangedListEntry<T>(int num, T value) {
   }
}
