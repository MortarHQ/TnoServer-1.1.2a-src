package com.nododiiiii.ponderer.ui;

import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;

public interface HeldItemButtonHost {
   void useHeldItemFromButton(Consumer<ItemStack> var1);
}
