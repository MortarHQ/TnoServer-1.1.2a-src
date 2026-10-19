package com.b1n_ry.yigd.networking.packets;

import com.b1n_ry.yigd.networking.LightGraveData;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.ResolvableProfile;
import org.jetbrains.annotations.NotNull;

public record GraveSelectionS2CPacket(List<LightGraveData> data, ResolvableProfile profile) implements CustomPacketPayload {
   public static final Type<GraveSelectionS2CPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("yigd", "grave_selection"));
   public static final StreamCodec<RegistryFriendlyByteBuf, GraveSelectionS2CPacket> STREAM_CODEC = StreamCodec.ofMember(
      GraveSelectionS2CPacket::write, GraveSelectionS2CPacket::new
   );

   public GraveSelectionS2CPacket(RegistryFriendlyByteBuf buf) {
      this(
         buf.readList(friendlyByteBuf -> LightGraveData.fromNbt(friendlyByteBuf.readNbt(), buf.registryAccess())),
         (ResolvableProfile)ResolvableProfile.STREAM_CODEC.decode(buf)
      );
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeCollection(this.data, (buf1, value) -> buf1.writeNbt(value.toNbt(buf.registryAccess())));
      ResolvableProfile.STREAM_CODEC.encode(buf, this.profile);
   }
}
