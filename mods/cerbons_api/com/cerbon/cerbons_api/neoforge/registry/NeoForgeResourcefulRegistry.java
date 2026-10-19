package com.cerbon.cerbons_api.neoforge.registry;

import com.cerbon.cerbons_api.api.registry.RegistryEntries;
import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import java.util.Collection;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeResourcefulRegistry<T> implements ResourcefulRegistry<T> {
   private final DeferredRegister<T> register;
   private final RegistryEntries<T> entries = new RegistryEntries<>();

   public NeoForgeResourcefulRegistry(Registry<T> registry, String id) {
      this.register = DeferredRegister.create(registry.key(), id);
   }

   @Override
   public <I extends T> RegistryEntry<I> register(String id, Supplier<I> supplier) {
      return this.entries.add(new NeoForgeRegistryEntry<>(this.register.register(id, supplier)));
   }

   @Override
   public Collection<RegistryEntry<T>> getEntries() {
      return this.entries.getEntries();
   }

   @Override
   public void register() {
      this.register.register(ModLoadingContext.get().getActiveContainer().getEventBus());
   }
}
