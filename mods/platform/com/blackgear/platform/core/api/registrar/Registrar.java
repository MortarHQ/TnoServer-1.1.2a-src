package com.blackgear.platform.core.api.registrar;

import com.blackgear.platform.core.api.registrar.neoforge.RegistrarImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.LinkedHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public abstract class Registrar<T> {
   protected final LinkedHashMap<ResourceLocation, T> entries = new LinkedHashMap<>();
   public final Registry<T> registry;
   protected final String modId;
   protected boolean isPresent = false;

   public Registrar(Registry<T> registry, String modId) {
      this.registry = registry;
      this.modId = modId;
   }

   @ExpectPlatform
   @Transformed
   public static <T> Registrar<T> create(ResourceKey<? extends Registry<T>> key, String modId) {
      return RegistrarImpl.create(key, modId);
   }

   @ExpectPlatform
   @Transformed
   public static <T> Registrar<T> create(Registry<T> registry, String modId) {
      return RegistrarImpl.create(registry, modId);
   }

   @ExpectPlatform
   @Transformed
   public static <T> void bind(Registry<T> registry, Consumer<BiConsumer<ResourceLocation, T>> consumer) {
      RegistrarImpl.bind(registry, consumer);
   }

   public <E extends T> E register(String name, E entry) {
      this.entries.put(ResourceLocation.fromNamespaceAndPath(this.modId, name), (T)entry);
      return entry;
   }

   public <E extends T> ResourceKey<T> resource(String name, E entry) {
      this.register(name, entry);
      return ResourceKey.create(this.registry.key(), ResourceLocation.fromNamespaceAndPath(this.modId, name));
   }

   public abstract <E extends T> Holder<T> holder(String var1, E var2);

   public void registrar() {
      if (!this.isPresent) {
         this.isPresent = true;
         this.register();
      }
   }

   protected void register() {
      bind(this.registry, this.entries::forEach);
   }
}
