package com.teamresourceful.resourcefullib.common.collections;

import com.google.common.collect.Lists;
import java.util.AbstractList;
import java.util.List;
import org.apache.commons.lang3.Validate;

public class SelectableList<E> extends AbstractList<E> {
   protected E defaultValue;
   protected List<E> list;
   protected int index = 0;

   @SafeVarargs
   public static <E> SelectableList<E> of(E pDefaultValue, E... pElements) {
      return new SelectableList<>(pDefaultValue, Lists.newArrayList(pElements));
   }

   public SelectableList(E defaultValue, List<E> list) {
      this.defaultValue = defaultValue;
      this.list = list;
   }

   public void setSelectedIndex(int index) {
      if (index >= this.size()) {
         this.index = 0;
      } else {
         this.index = Math.max(index, 0);
      }
   }

   public E getSelected() {
      return this.get(this.index);
   }

   public int getSelectedIndex() {
      return this.index;
   }

   @Override
   public void add(int index, E element) {
      Validate.notNull(element);
      this.list.add(index, element);
   }

   @Override
   public E get(int index) {
      if (index >= 0 && index < this.size()) {
         E item = this.list.get(index);
         return item == null ? this.defaultValue : item;
      } else {
         return this.defaultValue;
      }
   }

   @Override
   public E set(int pIndex, E pValue) {
      Validate.notNull(pValue);
      return this.list.set(pIndex, pValue);
   }

   @Override
   public E remove(int index) {
      if (index == this.index) {
         this.index = 0;
      }

      return this.list.remove(index);
   }

   @Override
   public int size() {
      return this.list.size();
   }
}
