package com.tensura_guild.network;

import com.tensura_guild.client.DwarfTaskClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenDwarfTaskDialoguePayload(
   int entityId,
   OpenDwarfTaskDialoguePayload.Mode mode,
   String speakerName,
   String offerKey,
   String activeKey,
   String readyKey,
   int heldCount,
   int requiredCount,
   long remainingTicks
) implements CustomPacketPayload {
   public static final Type<OpenDwarfTaskDialoguePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "open_dwarf_task_dialogue"));
   public static final StreamCodec<ByteBuf, OpenDwarfTaskDialoguePayload> STREAM_CODEC = new StreamCodec<ByteBuf, OpenDwarfTaskDialoguePayload>() {
      public OpenDwarfTaskDialoguePayload decode(ByteBuf buffer) {
         FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
         return new OpenDwarfTaskDialoguePayload(
            buf.readVarInt(),
            (OpenDwarfTaskDialoguePayload.Mode)buf.readEnum(OpenDwarfTaskDialoguePayload.Mode.class),
            buf.readUtf(64),
            buf.readUtf(128),
            buf.readUtf(128),
            buf.readUtf(128),
            buf.readVarInt(),
            buf.readVarInt(),
            buf.readVarLong()
         );
      }

      public void encode(ByteBuf buffer, OpenDwarfTaskDialoguePayload value) {
         FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
         buf.writeVarInt(value.entityId);
         buf.writeEnum(value.mode);
         buf.writeUtf(value.speakerName == null ? "" : value.speakerName, 64);
         buf.writeUtf(value.offerKey == null ? "" : value.offerKey, 128);
         buf.writeUtf(value.activeKey == null ? "" : value.activeKey, 128);
         buf.writeUtf(value.readyKey == null ? "" : value.readyKey, 128);
         buf.writeVarInt(value.heldCount);
         buf.writeVarInt(value.requiredCount);
         buf.writeVarLong(value.remainingTicks);
      }
   };

   public Type<OpenDwarfTaskDialoguePayload> type() {
      return TYPE;
   }

   public static void handle(OpenDwarfTaskDialoguePayload payload, IPayloadContext context) {
      context.enqueueWork(() -> DwarfTaskClient.open(payload));
   }

   public enum Mode {
      OFFER,
      ACTIVE,
      READY,
      GUILD_MAP_REWARD;
   }
}
