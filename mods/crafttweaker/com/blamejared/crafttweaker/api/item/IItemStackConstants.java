package com.blamejared.crafttweaker.api.item;

import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
class IItemStackConstants {
   static final Supplier<IItemStack> EMPTY_STACK = Suppliers.memoize(() -> IItemStack.of(ItemStack.EMPTY));
}
