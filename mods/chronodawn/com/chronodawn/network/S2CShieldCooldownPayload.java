package com.chronodawn.network;

import com.chronodawn.compat.CompatResourceLocation;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record S2CShieldCooldownPayload(byte kind, int durationTicks) implements CustomPacketPayload {
   public static final byte KIND_SPEED = 0;
   public static final byte KIND_ECHO = 1;
   public static final Type<S2CShieldCooldownPayload> TYPE = new Type(CompatResourceLocation.create("chronodawn", "s2c_shield_cd"));
   public static final StreamCodec<RegistryFriendlyByteBuf, S2CShieldCooldownPayload> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.BYTE, S2CShieldCooldownPayload::kind, ByteBufCodecs.VAR_INT, S2CShieldCooldownPayload::durationTicks, S2CShieldCooldownPayload::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
