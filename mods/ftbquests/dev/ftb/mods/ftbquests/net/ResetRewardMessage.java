package dev.ftb.mods.ftbquests.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.client.FTBQuestsNetClient;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record ResetRewardMessage(UUID teamId, UUID playerId, long id) implements CustomPacketPayload {
   public static final Type<ResetRewardMessage> TYPE = new Type(FTBQuestsAPI.rl("reset_reward_message"));
   public static final StreamCodec<FriendlyByteBuf, ResetRewardMessage> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC,
      ResetRewardMessage::teamId,
      UUIDUtil.STREAM_CODEC,
      ResetRewardMessage::playerId,
      ByteBufCodecs.VAR_LONG,
      ResetRewardMessage::id,
      ResetRewardMessage::new
   );

   public Type<ResetRewardMessage> type() {
      return TYPE;
   }

   public static void handle(ResetRewardMessage message, PacketContext context) {
      context.queue(() -> FTBQuestsNetClient.resetReward(message.teamId, message.playerId, message.id));
   }
}
