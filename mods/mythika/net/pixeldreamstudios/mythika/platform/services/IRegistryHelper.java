package net.pixeldreamstudios.mythika.platform.services;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.DisplayItemsGenerator;
import net.pixeldreamstudios.mythika.registry.DeferredRegister;

public interface IRegistryHelper {
   <T> void register(DeferredRegister<T> var1);

   CreativeModeTab createCreativeTab(Component var1, Supplier<ItemStack> var2, DisplayItemsGenerator var3);
}
