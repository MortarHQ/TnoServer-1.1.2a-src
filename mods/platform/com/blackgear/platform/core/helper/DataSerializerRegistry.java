package com.blackgear.platform.core.helper;

import com.blackgear.platform.core.helper.neoforge.DataSerializerRegistryImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.function.Supplier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;

public abstract class DataSerializerRegistry {
   @ExpectPlatform
   @Transformed
   public static DataSerializerRegistry create(String modId) {
      return DataSerializerRegistryImpl.create(modId);
   }

   public <T> Supplier<EntityDataSerializer<T>> register(String name, StreamCodec<? super RegistryFriendlyByteBuf, T> codec) {
      return this.register(name, () -> EntityDataSerializer.forValueType(codec));
   }

   public abstract <T> Supplier<EntityDataSerializer<T>> register(String var1, Supplier<EntityDataSerializer<T>> var2);

   public abstract void register();
}
