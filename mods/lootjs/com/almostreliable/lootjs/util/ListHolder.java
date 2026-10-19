package com.almostreliable.lootjs.util;

import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ListHolder<W, T> implements Iterable<W> {
   protected final ArrayList<T> elements;

   public static <T> ArrayList<T> asArrayList(List<T> list) {
      return list instanceof ArrayList ? (ArrayList)list : new ArrayList<>(list);
   }

   public ListHolder() {
      this.elements = new ArrayList<>();
   }

   public ListHolder(List<T> elements) {
      this.elements = asArrayList(elements);
   }

   @Override
   public abstract Iterator<W> iterator();

   protected abstract W wrap(T var1);

   protected abstract T unwrap(W var1);

   @HideFromJS
   public List<T> getElements() {
      return this.elements;
   }

   public int size() {
      return this.elements.size();
   }

   public boolean isEmpty() {
      return this.elements.isEmpty();
   }

   public boolean add(W entry) {
      T wrappedElement = this.unwrap(entry);
      return this.elements.add(wrappedElement);
   }

   public void clear() {
      this.elements.clear();
   }

   public W get(int index) {
      return this.wrap(this.elements.get(index));
   }

   public W set(int index, W element) {
      T replaced = this.elements.set(index, this.unwrap(element));
      return this.wrap(replaced);
   }

   public void add(int index, W element) {
      this.elements.add(index, this.unwrap(element));
   }

   public W remove(int index) {
      T removedElement = this.elements.remove(index);
      return this.wrap(removedElement);
   }
}
