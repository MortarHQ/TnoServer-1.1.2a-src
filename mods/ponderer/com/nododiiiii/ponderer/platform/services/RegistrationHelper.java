package com.nododiiiii.ponderer.platform.services;

import java.util.function.Supplier;
import net.minecraft.world.item.Item;

public interface RegistrationHelper {
   Supplier<Item> registerItem(String var1, Supplier<Item> var2);

   void init();
}
