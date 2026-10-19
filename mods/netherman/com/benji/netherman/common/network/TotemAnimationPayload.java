package com.benji.netherman.common.network;

import com.benji.netherman.NetherExp;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.world.item.ItemStack;

public record TotemAnimationPayload(ItemStack stack) implements CustomPacketPayload {
   public static final Type<TotemAnimationPayload> TYPE = new Type(NetherExp.location("totem_animation"));
   public static final StreamCodec<RegistryFriendlyByteBuf, TotemAnimationPayload> CODEC = StreamCodec.composite(
      ItemStack.STREAM_CODEC, TotemAnimationPayload::stack, TotemAnimationPayload::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
