package com.tensura_guild.network;

import com.tensura_guild.GuildPlayerEvents;
import com.tensura_guild.data.GuildMemberData;
import com.tensura_guild.data.GuildMemberService;
import com.tensura_guild.data.GuildTitleData;
import com.tensura_guild.registry.GuildAttachments;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetGuildTitlePayload(String title) implements CustomPacketPayload {
   public static final Type<SetGuildTitlePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "set_guild_title"));
   public static final StreamCodec<ByteBuf, SetGuildTitlePayload> STREAM_CODEC = new StreamCodec<ByteBuf, SetGuildTitlePayload>() {
      public SetGuildTitlePayload decode(ByteBuf buffer) {
         return new SetGuildTitlePayload(new FriendlyByteBuf(buffer).readUtf(64));
      }

      public void encode(ByteBuf buffer, SetGuildTitlePayload value) {
         new FriendlyByteBuf(buffer).writeUtf(value.title == null ? "" : value.title, 64);
      }
   };

   public static SetGuildTitlePayload disable() {
      return new SetGuildTitlePayload("");
   }

   public Type<SetGuildTitlePayload> type() {
      return TYPE;
   }

   public static void handle(SetGuildTitlePayload payload, IPayloadContext context) {
      context.enqueueWork(() -> {
         if (context.player() instanceof ServerPlayer player) {
            GuildMemberData var7 = (GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER);
            Optional<String> selected = GuildTitleData.normalizeTitle(payload.title);
            if (selected.isEmpty()) {
               GuildMemberService.setMemberData(player, var7.withTitle(null));
               GuildPlayerEvents.refreshNames(player);
            } else {
               GuildTitleData titleData = (GuildTitleData)player.getData(GuildAttachments.GUILD_TITLES);
               String title = selected.get();
               if (titleData.contains(title) || !var7.title().filter(title::equals).isEmpty()) {
                  GuildMemberService.setMemberData(player, var7.withTitle(title));
                  GuildPlayerEvents.refreshNames(player);
               }
            }
         }
      });
   }
}
