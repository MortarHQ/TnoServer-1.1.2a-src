package com.tom.storagemod.util;

import com.tom.storagemod.Content;
import com.tom.storagemod.components.SimpleItemFilterComponent;
import java.util.ArrayList;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class AbstractSimpleItemFilter {
   protected SimpleContainer filter;
   protected ItemStack stack;
   protected boolean matchNBT;
   protected boolean allowList;

   public AbstractSimpleItemFilter(ItemStack stack) {
      this.stack = stack;
      SimpleItemFilterComponent c = (SimpleItemFilterComponent)stack.get(Content.simpleItemFilterComponent.get());
      if (c != null) {
         this.filter = new SimpleContainer(c.stacks().toArray(ItemStack[]::new));
         this.matchNBT = c.matchComp();
         this.allowList = c.allowList();
      } else {
         this.filter = new SimpleContainer(9);
      }
   }

   public void flush() {
      SimpleItemFilterComponent c = new SimpleItemFilterComponent(new ArrayList<>(this.filter.getItems()), this.matchNBT, this.allowList);
      this.stack.applyComponents(DataComponentPatch.builder().set(Content.simpleItemFilterComponent.get(), c).build());
   }

   public SimpleContainer getContainer() {
      return this.filter;
   }

   public boolean isMatchNBT() {
      return this.matchNBT;
   }

   public void setMatchNBT(boolean matchNBT) {
      this.matchNBT = matchNBT;
   }

   public boolean isAllowList() {
      return this.allowList;
   }

   public void setAllowList(boolean allowList) {
      this.allowList = allowList;
   }
}
