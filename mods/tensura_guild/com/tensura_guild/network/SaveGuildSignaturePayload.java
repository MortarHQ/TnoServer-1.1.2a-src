package com.tensura_guild.network;

import com.tensura_guild.data.GuildMemberService;
import com.tensura_guild.data.GuildSignatureData;
import com.tensura_guild.registry.GuildAttachments;
import io.netty.buffer.ByteBuf;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SaveGuildSignaturePayload(GuildSignatureData signature) implements CustomPacketPayload {
   public static final Type<SaveGuildSignaturePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "save_guild_signature"));
   private static final ResourceLocation INTRO_DIALOGUE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "yuuki_intro");
   public static final StreamCodec<ByteBuf, SaveGuildSignaturePayload> STREAM_CODEC = GuildSignatureData.STREAM_CODEC
      .map(SaveGuildSignaturePayload::new, SaveGuildSignaturePayload::signature);

   public Type<SaveGuildSignaturePayload> type() {
      return TYPE;
   }

   public static void handle(SaveGuildSignaturePayload payload, IPayloadContext context) {
      context.enqueueWork(() -> {
         GuildSignatureData signature = payload.signature();
         if (signature.hasSignature()) {
            Player player = context.player();
            player.setData(GuildAttachments.GUILD_SIGNATURE, signature);
            player.syncData(GuildAttachments.GUILD_SIGNATURE);
            Set<ResourceLocation> seen = new HashSet<>((Collection<? extends ResourceLocation>)player.getData(GuildAttachments.SEEN_DIALOGUES));
            if (seen.add(INTRO_DIALOGUE)) {
               player.setData(GuildAttachments.SEEN_DIALOGUES, seen);
            }

            if (player instanceof ServerPlayer serverPlayer) {
               GuildMemberService.ensureRegistered(serverPlayer);
            }
         }
      });
   }
}
