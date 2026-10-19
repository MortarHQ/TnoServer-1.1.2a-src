package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.handler.FeldwayRewardHandler;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record ClaimFeldwayRewardPayload(ResourceLocation skill) implements CustomPacketPayload {
   public static final Type<ClaimFeldwayRewardPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "claim_feldway_reward"));
   public static final StreamCodec<FriendlyByteBuf, ClaimFeldwayRewardPayload> STREAM_CODEC = CustomPacketPayload.codec(
      ClaimFeldwayRewardPayload::encode, ClaimFeldwayRewardPayload::new
   );

   public ClaimFeldwayRewardPayload(FriendlyByteBuf buf) {
      this(buf.readResourceLocation());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeResourceLocation(this.skill);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.SERVER) {
         context.queue(() -> {
            if (context.getPlayer() instanceof ServerPlayer player) {
               FeldwayRewardHandler.claim(player, this.skill);
            }
         });
      }
   }

   public @NotNull Type<ClaimFeldwayRewardPayload> type() {
      return TYPE;
   }
}
