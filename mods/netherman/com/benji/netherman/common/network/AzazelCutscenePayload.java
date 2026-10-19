package com.benji.netherman.common.network;

import com.benji.netherman.NetherExp;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record AzazelCutscenePayload(int actionId, int entityId) implements CustomPacketPayload {
   public static final Type<AzazelCutscenePayload> TYPE = new Type(NetherExp.location("azazel_cutscene"));
   public static final StreamCodec<FriendlyByteBuf, AzazelCutscenePayload> CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, AzazelCutscenePayload::actionId, ByteBufCodecs.INT, AzazelCutscenePayload::entityId, AzazelCutscenePayload::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
