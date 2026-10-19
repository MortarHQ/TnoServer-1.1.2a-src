package net.pixeldreamstudios.mythika.neoforge.platform;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.DisplayItemsGenerator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.pixeldreamstudios.mythika.platform.services.IRegistryHelper;
import net.pixeldreamstudios.mythika.registry.DeferredRegister;
import net.pixeldreamstudios.mythika.registry.RegistrySupplier;

public class NeoForgeRegistryHelper implements IRegistryHelper {
   private static IEventBus modEventBus;

   public static void setModEventBus(IEventBus bus) {
      modEventBus = bus;
   }

   @Override
   public <T> void register(DeferredRegister<T> register) {
      if (modEventBus == null) {
         throw new IllegalStateException("Mod event bus was not supplied before registration");
      }

      net.neoforged.neoforge.registries.DeferredRegister<Object> deferred = net.neoforged.neoforge.registries.DeferredRegister.create(
         register.getRegistryKey(), register.getModId()
      );

      for (RegistrySupplier<? extends T> entry : register.getEntries()) {
         DeferredHolder<Object, Object> holder = deferred.register(entry.getId().getPath(), entry::createRaw);
         entry.bindRaw(holder);
      }

      deferred.register(modEventBus);
   }

   @Override
   public CreativeModeTab createCreativeTab(Component title, Supplier<ItemStack> icon, DisplayItemsGenerator displayItems) {
      return CreativeModeTab.builder().title(title).icon(icon).displayItems(displayItems).build();
   }
}
