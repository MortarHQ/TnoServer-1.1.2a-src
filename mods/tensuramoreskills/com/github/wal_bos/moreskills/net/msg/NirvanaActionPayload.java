package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.handler.NirvanaHandler;
import com.github.wal_bos.moreskills.nirvana.NirvanaData;
import dev.architectury.networking.NetworkManager.PacketContext;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record NirvanaActionPayload(NirvanaActionPayload.Action action, NirvanaData.Sort sort, UUID lifeId, ResourceLocation skill, int mode, String name)
   implements CustomPacketPayload {
   public static final Type<NirvanaActionPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "nirvana_action"));
   public static final StreamCodec<FriendlyByteBuf, NirvanaActionPayload> STREAM_CODEC = StreamCodec.of(NirvanaActionPayload::write, NirvanaActionPayload::read);
   public static final UUID EMPTY_UUID = new UUID(0L, 0L);
   public static final ResourceLocation EMPTY_SKILL = ResourceLocation.withDefaultNamespace("empty");

   public static NirvanaActionPayload sort(NirvanaData.Sort sort) {
      return new NirvanaActionPayload(NirvanaActionPayload.Action.SORT, sort, EMPTY_UUID, EMPTY_SKILL, 0, "");
   }

   public static NirvanaActionPayload delete(NirvanaData.Sort sort, UUID lifeId) {
      return new NirvanaActionPayload(NirvanaActionPayload.Action.DELETE, sort, lifeId, EMPTY_SKILL, 0, "");
   }

   public static NirvanaActionPayload rename(NirvanaData.Sort sort, UUID lifeId, String name) {
      return new NirvanaActionPayload(NirvanaActionPayload.Action.RENAME, sort, lifeId, EMPTY_SKILL, 0, name == null ? "" : name);
   }

   public static NirvanaActionPayload unlock(NirvanaData.Sort sort, UUID lifeId, ResourceLocation skill, int mode) {
      return new NirvanaActionPayload(NirvanaActionPayload.Action.UNLOCK, sort, lifeId, skill, mode, "");
   }

   private static void write(FriendlyByteBuf buffer, NirvanaActionPayload payload) {
      buffer.writeEnum(payload.action);
      buffer.writeEnum(payload.sort);
      buffer.writeUUID(payload.lifeId == null ? EMPTY_UUID : payload.lifeId);
      buffer.writeResourceLocation(payload.skill == null ? EMPTY_SKILL : payload.skill);
      buffer.writeInt(payload.mode);
      buffer.writeUtf(payload.name == null ? "" : payload.name);
   }

   private static NirvanaActionPayload read(FriendlyByteBuf buffer) {
      return new NirvanaActionPayload(
         (NirvanaActionPayload.Action)buffer.readEnum(NirvanaActionPayload.Action.class),
         (NirvanaData.Sort)buffer.readEnum(NirvanaData.Sort.class),
         buffer.readUUID(),
         buffer.readResourceLocation(),
         buffer.readInt(),
         buffer.readUtf(32767)
      );
   }

   public static void handle(NirvanaActionPayload payload, PacketContext context) {
      context.queue(() -> {
         if (context.getPlayer() instanceof ServerPlayer player) {
            NirvanaHandler.handleClientAction(player, payload);
         }
      });
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public enum Action {
      SORT,
      RENAME,
      DELETE,
      UNLOCK;
   }
}
