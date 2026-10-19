package com.blackgear.platform.core.api.registrar;

import com.blackgear.platform.core.api.registrar.neoforge.RegistrarBuilderImpl;
import com.blackgear.platform.core.helper.DynamicRegistry;
import com.mojang.serialization.Codec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.ArrayList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public abstract class RegistrarBuilder {
   private final ArrayList<RegistrarBuilder.DynamicEntry<?>> entries = new ArrayList<>();
   protected final String modId;
   protected boolean isPresent = false;

   protected RegistrarBuilder(String modId) {
      this.modId = modId;
   }

   @ExpectPlatform
   @Transformed
   public static RegistrarBuilder create(String modId) {
      return RegistrarBuilderImpl.create(modId);
   }

   public abstract <T> Registry<T> registerSimple(String var1);

   public <T> ResourceKey<Registry<T>> registerDynamic(String name, Codec<T> codec) {
      ResourceKey<Registry<T>> key = this.resource(name);
      this.entries.add(new RegistrarBuilder.DynamicEntry(key, codec));
      return key;
   }

   protected <T> ResourceKey<Registry<T>> resource(String name) {
      return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(this.modId, name));
   }

   public void bootstrap() {
      if (!this.isPresent) {
         this.isPresent = true;
         DynamicRegistry.onRegister(registrar -> this.entries.forEach(entry -> this.registerDynamic(registrar, (RegistrarBuilder.DynamicEntry<?>)entry)));
      }
   }

   private <T> void registerDynamic(DynamicRegistry.Registrar registrar, RegistrarBuilder.DynamicEntry<T> entry) {
      registrar.registerDynamicRegistry(entry.key, entry.codec);
   }

   private record DynamicEntry<T>(ResourceKey<Registry<T>> key, Codec<T> codec) {
   }
}
