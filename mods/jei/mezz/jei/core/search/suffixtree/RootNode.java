package mezz.jei.core.search.suffixtree;

import mezz.jei.core.util.SubString;

public class RootNode<T> extends Node<T> {
   public RootNode() {
      super(new SubString(""));
   }

   @Override
   protected boolean contains(T value) {
      return true;
   }

   @Override
   protected void addValue(T value) {
   }
}
