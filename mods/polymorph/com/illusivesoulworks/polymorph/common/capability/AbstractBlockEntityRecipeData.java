package com.illusivesoulworks.polymorph.common.capability;

import com.illusivesoulworks.polymorph.api.common.capability.IBlockEntityRecipeData;
import java.util.TreeSet;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class AbstractBlockEntityRecipeData<E extends BlockEntity> extends AbstractRecipeData<BlockEntity> implements IBlockEntityRecipeData {
   private boolean wasEmptyBefore = false;

   public AbstractBlockEntityRecipeData(BlockEntity owner) {
      super(owner);
   }

   protected abstract NonNullList<ItemStack> getInput();

   @Override
   public void tick() {
      if (!this.getListeners().isEmpty()) {
         NonNullList<ItemStack> currentInput = this.getInput();
         boolean isEmpty = true;

         for (ItemStack currentStack : currentInput) {
            if (!currentStack.isEmpty()) {
               isEmpty = false;
               break;
            }
         }

         if (!this.wasEmptyBefore && isEmpty) {
            this.updateRecipesList(new TreeSet<>());
         }

         this.wasEmptyBefore = isEmpty;
      }
   }

   @Override
   public boolean isEmpty() {
      for (ItemStack currentStack : this.getInput()) {
         if (!currentStack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public E getOwner() {
      return super.getOwner();
   }
}
