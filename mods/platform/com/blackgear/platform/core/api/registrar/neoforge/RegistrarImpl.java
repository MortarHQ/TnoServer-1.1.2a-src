package com.blackgear.platform.core.api.registrar.neoforge;

import com.blackgear.platform.core.api.registrar.Registrar;
import com.blackgear.platform.core.util.EventBus;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class RegistrarImpl<T> extends Registrar<T> {
   public RegistrarImpl(Registry<T> registry, String modId) {
      super(registry, modId);
   }

   public static <T> Registrar<T> create(Registry<T> registry, String modId) {
      return new RegistrarImpl<>(registry, modId);
   }

   public static <T> Registrar<T> create(ResourceKey<? extends Registry<T>> key, String modId) {
      Registry<?> registry = (Registry<?>)BuiltInRegistries.REGISTRY.get(key.location());
      if (registry == null) {
         throw new IllegalArgumentException("Unknown registry: " + key.location());
      } else {
         return new RegistrarImpl<>((Registry<T>)registry, modId);
      }
   }

   @Override
   public <E extends T> Holder<T> holder(String name, E entry) {
      return null;
   }

   public static <T> void bind(Registry<T> registry, Consumer<BiConsumer<ResourceLocation, T>> bootstrap) {
      EventBus.get(EventBus.MOD).addListener(event -> {
         if (registry.key().equals(event.getRegistryKey())) {
            bootstrap.accept((identifier, entry) -> event.register(registry.key(), identifier, () -> entry));
         }
      });
   }
}
