package com.b1n_ry.yigd.networking.packets;

import com.b1n_ry.yigd.networking.LightPlayerData;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record PlayerSelectionS2CPacket(List<LightPlayerData> data) implements CustomPacketPayload {
   public static final Type<PlayerSelectionS2CPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("yigd", "player_selection"));
   public static final StreamCodec<RegistryFriendlyByteBuf, PlayerSelectionS2CPacket> STREAM_CODEC = StreamCodec.ofMember(
      PlayerSelectionS2CPacket::write, PlayerSelectionS2CPacket::new
   );

   public PlayerSelectionS2CPacket(RegistryFriendlyByteBuf buf) {
      this(buf.readList(friendlyByteBuf -> LightPlayerData.fromNbt(friendlyByteBuf.readNbt(), buf.registryAccess())));
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeCollection(this.data, (buf1, value) -> buf1.writeNbt(value.toNbt(buf.registryAccess())));
   }
}
