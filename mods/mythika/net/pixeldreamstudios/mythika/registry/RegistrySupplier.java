package net.pixeldreamstudios.mythika.registry;

import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public final class RegistrySupplier<T> implements Supplier<T> {
   private final ResourceLocation id;
   private final Supplier<? extends T> factory;
   private Supplier<? extends T> resolved;

   RegistrySupplier(ResourceLocation id, Supplier<? extends T> factory) {
      this.id = id;
      this.factory = factory;
   }

   @Override
   public T get() {
      if (this.resolved == null) {
         throw new IllegalStateException("Registry entry " + this.id + " was accessed before it was registered");
      } else {
         return (T)this.resolved.get();
      }
   }

   public ResourceLocation getId() {
      return this.id;
   }

   public Object createRaw() {
      return this.factory.get();
   }

   public void bindRaw(Supplier<?> resolved) {
      this.resolved = (Supplier<? extends T>)resolved;
   }
}
