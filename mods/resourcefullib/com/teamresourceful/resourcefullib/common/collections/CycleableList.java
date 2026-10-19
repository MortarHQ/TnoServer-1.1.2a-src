package com.teamresourceful.resourcefullib.common.collections;

import java.util.ArrayList;

public class CycleableList<E> extends SelectableList<E> {
   public CycleableList() {
      super(null, new ArrayList<>());
   }

   public void next() {
      this.setSelectedIndex(this.getSelectedIndex() + 1);
   }
}
