package com.nododiiiii.ponderer.neoforge;

import com.nododiiiii.ponderer.platform.services.RegistrationHelper;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeRegistrationHelper implements RegistrationHelper {
   private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, "ponderer");
   static IEventBus modEventBus;

   @Override
   public Supplier<Item> registerItem(String id, Supplier<Item> itemSupplier) {
      return ITEMS.register(id, itemSupplier);
   }

   @Override
   public void init() {
      ITEMS.register(modEventBus);
   }
}
