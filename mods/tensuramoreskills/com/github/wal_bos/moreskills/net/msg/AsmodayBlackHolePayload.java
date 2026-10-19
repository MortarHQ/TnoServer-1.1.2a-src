package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.AsmodayBlackHoleClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record AsmodayBlackHolePayload(UUID id, double x, double y, double z, float radius, int duration) implements CustomPacketPayload {
   public static final Type<AsmodayBlackHolePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "asmoday_black_hole"));
   public static final StreamCodec<FriendlyByteBuf, AsmodayBlackHolePayload> STREAM_CODEC = CustomPacketPayload.codec(
      AsmodayBlackHolePayload::encode, AsmodayBlackHolePayload::new
   );

   public AsmodayBlackHolePayload(FriendlyByteBuf buf) {
      this(buf.readUUID(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readInt());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeUUID(this.id);
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeFloat(this.radius);
      buf.writeInt(this.duration);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> AsmodayBlackHoleClient.add(this.id, this.x, this.y, this.z, this.radius, this.duration));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
