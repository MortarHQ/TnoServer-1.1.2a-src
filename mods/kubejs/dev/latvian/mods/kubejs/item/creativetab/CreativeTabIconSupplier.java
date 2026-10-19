package dev.latvian.mods.kubejs.item.creativetab;

import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@FunctionalInterface
public interface CreativeTabIconSupplier {
   CreativeTabIconSupplier DEFAULT = () -> ItemStack.EMPTY;

   ItemStack getIcon();

   record Wrapper(CreativeTabIconSupplier supplier) implements Supplier<ItemStack> {
      public ItemStack get() {
         try {
            ItemStack i = this.supplier.getIcon();
            return i.isEmpty() ? Items.PURPLE_DYE.getDefaultInstance() : i;
         } catch (Exception ex) {
            ex.printStackTrace();
            return Items.PURPLE_DYE.getDefaultInstance();
         }
      }
   }
}
