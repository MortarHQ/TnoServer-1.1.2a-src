package org.crypticdev.stextras.net.C2S;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.net.STExtrasQuestSync;
import org.crypticdev.stextras.net.S2C.STExtrasBossKillsSync;
import org.crypticdev.stextras.net.S2C.STExtrasPlayerStorageSync;
import org.crypticdev.stextras.quest.assignment.RepeatableQuestService;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;
import org.crypticdev.stextras.utils.PrestigeUtils;

public record OpenPrestigeScreenPacket() implements CustomPacketPayload {
   public static final Type<OpenPrestigeScreenPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("stextras", "open_prestige_screen"));
   public static final StreamCodec<FriendlyByteBuf, OpenPrestigeScreenPacket> STREAM_CODEC = StreamCodec.of(
      (buf, pkt) -> {}, buf -> new OpenPrestigeScreenPacket()
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void registerCommon() {
   }

   public static void registerServer() {
      NetworkManager.registerReceiver(NetworkManager.c2s(), TYPE, STREAM_CODEC, (payload, context) -> {
         if (context.getPlayer() instanceof ServerPlayer player) {
            STExtrasPlayerStorageSync.sync(player);
            context.queue(() -> {
               RepeatableQuestService.ensureRepeatablesApplied(player);
               STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
               STExtrasQuestSync.sendWithRaceIds(player, store.toNet());
               STExtrasBossKillsSync.sendTo(player, PrestigeUtils.getBossKillCount(player));
               store.clearDirty();
            });
         }
      });
   }

   public static void sendToServer() {
      NetworkManager.sendToServer(new OpenPrestigeScreenPacket());
   }
}
