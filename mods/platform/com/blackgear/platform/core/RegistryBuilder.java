package com.blackgear.platform.core;

import com.blackgear.platform.core.neoforge.RegistryBuilderImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public abstract class RegistryBuilder {
   protected final String modId;

   protected RegistryBuilder(String modId) {
      this.modId = modId;
   }

   @ExpectPlatform
   @Transformed
   public static RegistryBuilder create(String modId) {
      return RegistryBuilderImpl.create(modId);
   }

   public <T> ResourceKey<Registry<T>> resource(String name) {
      return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(this.modId, name));
   }

   public abstract <T> Supplier<Registry<T>> registry(ResourceKey<Registry<T>> var1);

   public <T> RegistryBuilder.RegistryReference<T> reference(String name) {
      Objects.requireNonNull(name, "Registry name cannot be null");
      ResourceKey<Registry<T>> resource = this.resource(name);
      return new RegistryBuilder.RegistryReference<>(resource, this.registry(resource));
   }

   public static void bootstrap() {
   }

   public record RegistryReference<T>(ResourceKey<Registry<T>> resource, Supplier<Registry<T>> registry) {
      public ResourceLocation location() {
         return this.resource.location();
      }
   }
}
