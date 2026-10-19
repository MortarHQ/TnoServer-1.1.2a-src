package net.darkhax.bookshelf.common.api.item;

import java.util.function.Consumer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public interface IItemHooks {
   default void addCreativeTabForms(CreativeModeTab tab, Consumer<ItemStack> displayItems) {
   }
}
