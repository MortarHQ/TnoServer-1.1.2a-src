package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.items.Components.ChargeAnimationComponent;
import com.mojang.serialization.Codec;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModDataComponents {
   public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, "cataclysm");
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> LASER_GATLING = COMPONENTS.register(
      "laser_gatling", () -> DataComponentType.builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<ChargeAnimationComponent>> CHARGE_ANIMATION = COMPONENTS.register(
      "charge_animation",
      () -> DataComponentType.builder().persistent(ChargeAnimationComponent.CODEC).networkSynchronized(ChargeAnimationComponent.STREAM_CODEC).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> THROWN_ANCHOR = COMPONENTS.register(
      "thrown_anchor", () -> DataComponentType.builder().persistent(UUIDUtil.CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> THROWN_HAMMER = COMPONENTS.register(
      "thrown_hammer", () -> DataComponentType.builder().persistent(UUIDUtil.CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC).build()
   );

   @NotNull
   private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, Codec<T> codec) {
      return register2(name, codec, null);
   }

   @NotNull
   private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register2(
      String name, Codec<T> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec
   ) {
      return streamCodec == null
         ? COMPONENTS.register(name, () -> DataComponentType.builder().persistent(codec).build())
         : COMPONENTS.register(name, () -> DataComponentType.builder().persistent(codec).networkSynchronized(streamCodec).build());
   }
}
