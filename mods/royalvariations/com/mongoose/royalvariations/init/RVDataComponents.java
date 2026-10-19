package com.mongoose.royalvariations.init;

import com.mojang.serialization.Codec;
import com.mongoose.royalvariations.common.items.component.TelelocatorTracker;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RVDataComponents {
   public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, "royalvariations");
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<TelelocatorTracker>> TELELOCATOR_TRACKER = register(
      "telelocator_tracker", TelelocatorTracker.CODEC
   );

   @NotNull
   private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, Codec<T> codec) {
      return register(name, codec, null);
   }

   @NotNull
   private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(
      String name, Codec<T> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec
   ) {
      return streamCodec == null
         ? DATA_COMPONENT_TYPES.register(name, () -> DataComponentType.builder().persistent(codec).build())
         : DATA_COMPONENT_TYPES.register(name, () -> DataComponentType.builder().persistent(codec).networkSynchronized(streamCodec).build());
   }
}
