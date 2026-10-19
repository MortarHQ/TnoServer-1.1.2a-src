package com.tensura_guild.network;

import com.tensura_guild.client.GuildPartyClient;
import com.tensura_guild.data.GuildPartyState;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record GuildPartyStatePayload(GuildPartyState state) implements CustomPacketPayload {
   public static final Type<GuildPartyStatePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "guild_party_state"));
   public static final StreamCodec<ByteBuf, GuildPartyStatePayload> STREAM_CODEC = new StreamCodec<ByteBuf, GuildPartyStatePayload>() {
      public GuildPartyStatePayload decode(ByteBuf buffer) {
         return new GuildPartyStatePayload(GuildPartyState.decode(buffer));
      }

      public void encode(ByteBuf buffer, GuildPartyStatePayload value) {
         (value.state == null ? GuildPartyState.EMPTY : value.state).encode(buffer);
      }
   };

   public Type<GuildPartyStatePayload> type() {
      return TYPE;
   }

   public static void handle(GuildPartyStatePayload payload, IPayloadContext context) {
      context.enqueueWork(() -> GuildPartyClient.handleState(payload.state()));
   }
}
