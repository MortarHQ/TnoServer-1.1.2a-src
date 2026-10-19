package com.tensura_guild.network;

import com.tensura_guild.data.DwarfTaskService;
import com.tensura_guild.entity.YuukiKagurazakaEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CloseDialoguePayload(int entityId) implements CustomPacketPayload {
   public static final Type<CloseDialoguePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "close_dialogue"));
   public static final StreamCodec<RegistryFriendlyByteBuf, CloseDialoguePayload> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.VAR_INT, CloseDialoguePayload::entityId, CloseDialoguePayload::new
   );

   public Type<CloseDialoguePayload> type() {
      return TYPE;
   }

   public static void handle(CloseDialoguePayload payload, IPayloadContext context) {
      context.enqueueWork(() -> {
         Player player = context.player();
         if (player.level().getEntity(payload.entityId()) instanceof YuukiKagurazakaEntity yuuki) {
            yuuki.stopDialogue(player);
         }

         DwarfTaskService.stopDialogue(player, payload.entityId());
      });
   }
}
