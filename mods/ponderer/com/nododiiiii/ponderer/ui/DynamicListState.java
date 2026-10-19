package com.nododiiiii.ponderer.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class DynamicListState<T> implements SnapshotParticipant {
   private final String snapshotPrefix;
   private final DynamicListState.ItemCodec<T> codec;
   private final Supplier<T> emptyItemFactory;
   private final int minimumSize;
   private final List<T> items = new ArrayList<>();

   public DynamicListState(String snapshotPrefix, DynamicListState.ItemCodec<T> codec, Supplier<T> emptyItemFactory, int minimumSize) {
      this.snapshotPrefix = snapshotPrefix;
      this.codec = codec;
      this.emptyItemFactory = emptyItemFactory;
      this.minimumSize = Math.max(0, minimumSize);
      this.ensureMinimumSize();
   }

   public List<T> items() {
      return Collections.unmodifiableList(this.items);
   }

   public int size() {
      return this.items.size();
   }

   public T get(int index) {
      return this.items.get(index);
   }

   public void set(int index, T value) {
      if (index >= 0 && index < this.items.size()) {
         this.items.set(index, value);
      }
   }

   public void add(T value) {
      this.items.add(value);
   }

   public void addEmpty() {
      this.items.add(this.emptyItemFactory.get());
   }

   public void remove(int index) {
      if (index >= 0 && index < this.items.size()) {
         this.items.remove(index);
      }

      this.ensureMinimumSize();
   }

   public void clear() {
      this.items.clear();
      this.ensureMinimumSize();
   }

   public void replaceAll(Collection<T> values) {
      this.items.clear();
      if (values != null) {
         this.items.addAll(values);
      }

      this.ensureMinimumSize();
   }

   @Override
   public void snapshot(Map<String, String> snapshot) {
      snapshot.put(this.snapshotPrefix + "_count", String.valueOf(this.items.size()));

      for (int i = 0; i < this.items.size(); i++) {
         this.codec.snapshot(this.snapshotPrefix + "_" + i, this.items.get(i), snapshot);
      }
   }

   @Override
   public void restore(Map<String, String> snapshot) {
      String rawCount = snapshot.get(this.snapshotPrefix + "_count");
      if (rawCount != null) {
         int count;
         try {
            count = Integer.parseInt(rawCount);
         } catch (NumberFormatException ignored) {
            return;
         }

         this.items.clear();

         for (int i = 0; i < count; i++) {
            T value = this.codec.restore(this.snapshotPrefix + "_" + i, snapshot);
            if (value != null) {
               this.items.add(value);
            }
         }

         this.ensureMinimumSize();
      }
   }

   protected void ensureMinimumSize() {
      while (this.items.size() < this.minimumSize) {
         this.items.add(this.emptyItemFactory.get());
      }
   }

   public interface ItemCodec<T> {
      void snapshot(String var1, T var2, Map<String, String> var3);

      @Nullable
      T restore(String var1, Map<String, String> var2);
   }
}
