package com.tensura_guild.network;

import com.tensura_guild.data.GuildPartyService;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record GuildPartyActionPayload(GuildPartyActionPayload.Action action, UUID targetId, String value) implements CustomPacketPayload {
   private static final UUID EMPTY_UUID = new UUID(0L, 0L);
   public static final Type<GuildPartyActionPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "guild_party_action"));
   public static final StreamCodec<ByteBuf, GuildPartyActionPayload> STREAM_CODEC = new StreamCodec<ByteBuf, GuildPartyActionPayload>() {
      public GuildPartyActionPayload decode(ByteBuf buffer) {
         FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
         return new GuildPartyActionPayload((GuildPartyActionPayload.Action)buf.readEnum(GuildPartyActionPayload.Action.class), buf.readUUID(), buf.readUtf(64));
      }

      public void encode(ByteBuf buffer, GuildPartyActionPayload value) {
         FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
         buf.writeEnum(value.action);
         buf.writeUUID(value.targetId == null ? GuildPartyActionPayload.EMPTY_UUID : value.targetId);
         buf.writeUtf(value.value == null ? "" : value.value, 64);
      }
   };

   public static GuildPartyActionPayload requestSync() {
      return new GuildPartyActionPayload(GuildPartyActionPayload.Action.REQUEST_SYNC, EMPTY_UUID, "");
   }

   public static GuildPartyActionPayload create(String name) {
      return new GuildPartyActionPayload(GuildPartyActionPayload.Action.CREATE, EMPTY_UUID, name);
   }

   public static GuildPartyActionPayload join(String name) {
      return new GuildPartyActionPayload(GuildPartyActionPayload.Action.JOIN, EMPTY_UUID, name);
   }

   public static GuildPartyActionPayload leave() {
      return new GuildPartyActionPayload(GuildPartyActionPayload.Action.LEAVE, EMPTY_UUID, "");
   }

   public static GuildPartyActionPayload kick(UUID targetId) {
      return new GuildPartyActionPayload(GuildPartyActionPayload.Action.KICK, targetId, "");
   }

   public Type<GuildPartyActionPayload> type() {
      return TYPE;
   }

   public static void handle(GuildPartyActionPayload payload, IPayloadContext context) {
      context.enqueueWork(() -> {
         if (context.player() instanceof ServerPlayer player) {
            switch (payload.action) {
               case REQUEST_SYNC:
                  GuildPartyService.syncToPlayer(player);
                  break;
               case CREATE:
                  GuildPartyService.createParty(player, payload.value);
                  break;
               case JOIN:
                  GuildPartyService.requestJoin(player, payload.value);
                  break;
               case LEAVE:
                  GuildPartyService.leaveParty(player);
                  break;
               case KICK:
                  GuildPartyService.kickMember(player, payload.targetId);
            }
         }
      });
   }

   public enum Action {
      REQUEST_SYNC,
      CREATE,
      JOIN,
      LEAVE,
      KICK;
   }
}
