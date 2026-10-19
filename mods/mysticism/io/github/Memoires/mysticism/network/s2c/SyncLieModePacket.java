package io.github.Memoires.mysticism.network.s2c;

import dev.architectury.networking.NetworkManager.PacketContext;
import io.github.Memoires.mysticism.client.ability.CaptivatorClientState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SyncLieModePacket(boolean lieMode) implements CustomPacketPayload {
   public static final Type<SyncLieModePacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mysticism", "sync_lie_mode"));
   public static final StreamCodec<FriendlyByteBuf, SyncLieModePacket> STREAM_CODEC = CustomPacketPayload.codec(
      SyncLieModePacket::encode, SyncLieModePacket::new
   );

   public SyncLieModePacket(FriendlyByteBuf buf) {
      this(buf.readBoolean());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeBoolean(this.lieMode);
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(SyncLieModePacket msg, PacketContext context) {
      context.queue(() -> CaptivatorClientState.setLieMode(msg.lieMode()));
   }
}
