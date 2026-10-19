package org.crypticdev.stextras.net.C2S;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.net.S2C.STExtrasPlayerStorageSync;

public record OpenSkillLockScreenPacket() implements CustomPacketPayload {
   public static final Type<OpenSkillLockScreenPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("stextras", "open_skill_lock_screen"));
   public static final StreamCodec<FriendlyByteBuf, OpenSkillLockScreenPacket> STREAM_CODEC = StreamCodec.of(
      (buf, pkt) -> {}, buf -> new OpenSkillLockScreenPacket()
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void registerCommon() {
   }

   public static void registerServer() {
      NetworkManager.registerReceiver(NetworkManager.c2s(), TYPE, STREAM_CODEC, (payload, context) -> {
         if (context.getPlayer() instanceof ServerPlayer player) {
            context.queue(() -> STExtrasPlayerStorageSync.sync(player));
         }
      });
   }

   public static void sendToServer() {
      NetworkManager.sendToServer(new OpenSkillLockScreenPacket());
   }
}
