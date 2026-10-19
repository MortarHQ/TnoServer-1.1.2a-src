package com.blackgear.platform.core;

import com.blackgear.platform.core.neoforge.CoreRegistryImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public abstract class CoreRegistry<T> {
   protected final String modId;
   protected boolean isPresent = false;

   protected CoreRegistry(String modId) {
      this.modId = modId;
   }

   @ExpectPlatform
   @Transformed
   public static <T> CoreRegistry<T> create(ResourceKey<? extends Registry<T>> key, String modId) {
      return CoreRegistryImpl.create(key, modId);
   }

   @ExpectPlatform
   @Transformed
   public static <T> CoreRegistry<T> create(Registry<T> registry, String modId) {
      return CoreRegistryImpl.create(registry, modId);
   }

   public abstract <E extends T> Supplier<E> register(String var1, Supplier<E> var2);

   public abstract <E extends T> Holder<T> holder(String var1, Supplier<E> var2);

   public abstract <E extends T> RegistryHolder<E> registerHolder(String var1, Supplier<E> var2);

   public <E extends T> ResourceKey<T> resource(String name, Supplier<E> entry) {
      this.register(name, entry);
      return ResourceKey.create(this.key(), ResourceLocation.fromNamespaceAndPath(this.modId, name));
   }

   public abstract ResourceKey<? extends Registry<T>> key();

   public abstract Registry<T> registry();

   public String modId() {
      return this.modId;
   }

   public void register() {
      if (this.isPresent) {
         throw new IllegalArgumentException("Duplication of Registry: " + this.key());
      }

      this.isPresent = true;
      this.bootstrap();
   }

   protected abstract void bootstrap();
}
