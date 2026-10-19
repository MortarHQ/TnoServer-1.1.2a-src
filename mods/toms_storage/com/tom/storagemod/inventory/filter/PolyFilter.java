package com.tom.storagemod.inventory.filter;

import com.tom.storagemod.Content;
import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.util.BlockFaceReference;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;

public class PolyFilter implements ItemFilter {
   private BlockFaceReference face;
   private Set<ItemStack> filter;
   private long lastCheck;

   public PolyFilter(BlockFaceReference face) {
      this.face = face;
      this.filter = new HashSet<>();
   }

   @Override
   public void updateState() {
      long time = this.face.level().getGameTime();
      if (time - this.lastCheck >= 10L) {
         this.lastCheck = time;
         this.filter.clear();
         IItemHandler ih = (IItemHandler)this.face.level().getCapability(ItemHandler.BLOCK, this.face.pos(), this.face.from());
         if (ih != null) {
            IntStream.range(0, ih.getSlots())
               .<ItemStack>mapToObj(ih::getStackInSlot)
               .filter(s -> !s.isEmpty())
               .map(StoredItemStack::new)
               .distinct()
               .map(StoredItemStack::getStack)
               .forEach(this.filter::add);
         }
      }
   }

   @Override
   public boolean test(StoredItemStack stack) {
      for (ItemStack is : this.filter) {
         if (ItemStack.isSameItemSameComponents(stack.getStack(), is)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean configMatch(ItemStack stack) {
      return stack.getItem() == Content.polyItemFilter.get();
   }
}
