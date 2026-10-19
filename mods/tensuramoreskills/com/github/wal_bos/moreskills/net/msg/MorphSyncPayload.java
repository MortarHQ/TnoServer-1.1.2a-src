package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.net.client.ClientPayloadExecutor;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record MorphSyncPayload(UUID playerId, String entityId) implements CustomPacketPayload {
   public static final Type<MorphSyncPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "morph_sync"));
   public static final StreamCodec<FriendlyByteBuf, MorphSyncPayload> STREAM_CODEC = CustomPacketPayload.codec(MorphSyncPayload::encode, MorphSyncPayload::new);

   public MorphSyncPayload(FriendlyByteBuf buf) {
      this(buf.readUUID(), buf.readUtf());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeUUID(this.playerId);
      buf.writeUtf(this.entityId == null ? "" : this.entityId);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> ClientPayloadExecutor.handleMorphSync(this.playerId, this.entityId));
      }
   }

   @NotNull
   public Type<MorphSyncPayload> type() {
      return TYPE;
   }
}
