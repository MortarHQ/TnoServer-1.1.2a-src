package com.tensura_guild.network;

import com.tensura_guild.client.GuildDialogueClient;
import com.tensura_guild.dialogue.GuildDialogue;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenDialoguePayload(ResourceLocation id, int entityId, GuildDialogue dialogue) implements CustomPacketPayload {
   public static final Type<OpenDialoguePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "open_dialogue"));
   public static final StreamCodec<RegistryFriendlyByteBuf, OpenDialoguePayload> STREAM_CODEC = StreamCodec.composite(
      ResourceLocation.STREAM_CODEC,
      OpenDialoguePayload::id,
      ByteBufCodecs.VAR_INT,
      OpenDialoguePayload::entityId,
      GuildDialogue.STREAM_CODEC,
      OpenDialoguePayload::dialogue,
      OpenDialoguePayload::new
   );

   public Type<OpenDialoguePayload> type() {
      return TYPE;
   }

   public static void handle(OpenDialoguePayload payload, IPayloadContext context) {
      context.enqueueWork(() -> GuildDialogueClient.open(payload));
   }
}
