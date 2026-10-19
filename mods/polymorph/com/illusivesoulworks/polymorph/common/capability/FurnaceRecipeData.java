package com.illusivesoulworks.polymorph.common.capability;

import com.illusivesoulworks.polymorph.mixin.core.AccessorAbstractFurnaceBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class FurnaceRecipeData extends AbstractBlockEntityRecipeData<AbstractFurnaceBlockEntity> {
   public FurnaceRecipeData(AbstractFurnaceBlockEntity owner) {
      super(owner);
   }

   @Override
   protected NonNullList<ItemStack> getInput() {
      return ((AccessorAbstractFurnaceBlockEntity)this.getOwner()).getItems() != null
         ? NonNullList.of(ItemStack.EMPTY, new ItemStack[]{this.getOwner().getItem(0)})
         : NonNullList.create();
   }
}
