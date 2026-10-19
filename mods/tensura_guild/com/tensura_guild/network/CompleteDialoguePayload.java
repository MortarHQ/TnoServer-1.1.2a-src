package com.tensura_guild.network;

import com.tensura_guild.entity.YuukiKagurazakaEntity;
import com.tensura_guild.registry.GuildAttachments;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CompleteDialoguePayload(ResourceLocation id, int entityId) implements CustomPacketPayload {
   public static final Type<CompleteDialoguePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "complete_dialogue"));
   public static final StreamCodec<RegistryFriendlyByteBuf, CompleteDialoguePayload> STREAM_CODEC = StreamCodec.composite(
      ResourceLocation.STREAM_CODEC, CompleteDialoguePayload::id, ByteBufCodecs.VAR_INT, CompleteDialoguePayload::entityId, CompleteDialoguePayload::new
   );

   public Type<CompleteDialoguePayload> type() {
      return TYPE;
   }

   public static void handle(CompleteDialoguePayload payload, IPayloadContext context) {
      context.enqueueWork(() -> {
         Player player = context.player();
         Set<ResourceLocation> seen = new HashSet<>((Collection<? extends ResourceLocation>)player.getData(GuildAttachments.SEEN_DIALOGUES));
         if (seen.add(payload.id())) {
            player.setData(GuildAttachments.SEEN_DIALOGUES, seen);
         }

         if (player.level().getEntity(payload.entityId()) instanceof YuukiKagurazakaEntity yuuki) {
            yuuki.stopDialogue(player);
         }
      });
   }
}
