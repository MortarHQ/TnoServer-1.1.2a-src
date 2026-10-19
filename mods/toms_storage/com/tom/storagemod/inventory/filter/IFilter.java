package com.tom.storagemod.inventory.filter;

import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.util.Priority;
import java.util.ArrayList;
import java.util.List;

public interface IFilter {
   ItemPredicate getItemPred();

   boolean isKeepLast();

   Priority getPriority();

   class MultiFilter implements IFilter, ItemPredicate {
      private List<IFilter> filters = new ArrayList<>();

      public MultiFilter(IFilter... filters) {
         for (IFilter f : filters) {
            if (f instanceof IFilter.MultiFilter mf) {
               for (IFilter nf : mf.filters) {
                  this.add(nf);
               }
            } else {
               this.add(f);
            }
         }
      }

      private void add(IFilter nf) {
         if (!this.filters.contains(nf)) {
            this.filters.add(nf);
         }
      }

      @Override
      public ItemPredicate getItemPred() {
         return this;
      }

      @Override
      public boolean isKeepLast() {
         for (IFilter f : this.filters) {
            if (f.isKeepLast()) {
               return true;
            }
         }

         return false;
      }

      @Override
      public Priority getPriority() {
         int s = 0;

         for (IFilter f : this.filters) {
            s += f.getPriority().getSorting();
         }

         return Priority.fromSorting(s);
      }

      @Override
      public boolean test(StoredItemStack stack) {
         for (IFilter f : this.filters) {
            if (!f.getItemPred().test(stack)) {
               return false;
            }
         }

         return true;
      }

      @Override
      public void updateState() {
         for (IFilter f : this.filters) {
            f.getItemPred().updateState();
         }
      }

      @Override
      public String toString() {
         return this.filters.toString();
      }
   }
}
