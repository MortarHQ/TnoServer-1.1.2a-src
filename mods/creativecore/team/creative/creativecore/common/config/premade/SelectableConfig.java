package team.creative.creativecore.common.config.premade;

import org.apache.commons.lang3.ArrayUtils;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.api.IConfigObject;

public class SelectableConfig<T> implements IConfigObject {
   private T[] array;
   private int selected;
   private final int defaultSelected;

   public SelectableConfig(int selected, T... array) {
      this.selected = selected;
      this.defaultSelected = selected;
      this.array = array;
   }

   public void reset() {
      this.selected = this.defaultSelected;
   }

   public void select(int index) {
      if (this.selected >= 0 && this.selected < this.array.length) {
         this.selected = index;
      } else {
         this.reset();
      }
   }

   public T get() {
      return this.selected >= 0 && this.selected < this.array.length ? this.array[this.selected] : null;
   }

   public T[] getArray() {
      return this.array;
   }

   public int getSelected() {
      return this.selected;
   }

   public void updateArray(T[] array, T fallBack) {
      T pre = this.get();
      this.array = array;
      T post = this.get();
      if (pre == null || post == null) {
         this.select(ArrayUtils.indexOf(array, fallBack));
      } else if (!pre.equals(post)) {
         int index = ArrayUtils.indexOf(array, pre);
         if (index == -1) {
            this.select(ArrayUtils.indexOf(array, fallBack));
         } else {
            this.select(index);
         }
      }
   }

   @Override
   public boolean isDefault(Side side) {
      return this.selected == this.defaultSelected;
   }

   @Override
   public void restoreDefault(Side side, boolean ignoreRestart) {
      this.reset();
   }

   @Override
   public void configured(Side side) {
   }
}
