package net.pixeldreamstudios.mythika.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mythika.platform.Services;

public final class DeferredRegister<T> {
   private final String modId;
   private final ResourceKey<? extends Registry<T>> registryKey;
   private final List<RegistrySupplier<? extends T>> entries = new ArrayList<>();
   private boolean registered;

   private DeferredRegister(String modId, ResourceKey<? extends Registry<T>> registryKey) {
      this.modId = modId;
      this.registryKey = registryKey;
   }

   public static <T> DeferredRegister<T> create(String modId, ResourceKey<? extends Registry<T>> registryKey) {
      return new DeferredRegister<>(modId, registryKey);
   }

   public <R extends T> RegistrySupplier<R> register(String name, Supplier<R> factory) {
      if (this.registered) {
         throw new IllegalStateException("Cannot add " + name + " to " + this.registryKey.location() + " after registration");
      }

      RegistrySupplier<R> entry = new RegistrySupplier<>(ResourceLocation.fromNamespaceAndPath(this.modId, name), factory);
      this.entries.add(entry);
      return entry;
   }

   public void register() {
      if (!this.registered) {
         this.registered = true;
         Services.REGISTRY.register(this);
      }
   }

   public String getModId() {
      return this.modId;
   }

   public ResourceKey<? extends Registry<T>> getRegistryKey() {
      return this.registryKey;
   }

   public List<RegistrySupplier<? extends T>> getEntries() {
      return Collections.unmodifiableList(this.entries);
   }
}
