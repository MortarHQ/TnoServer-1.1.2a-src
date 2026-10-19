package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.FeldwayAvatarBeamClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record FeldwayAvatarBeamPayload(
   int bossId, int targetId, double startX, double startY, double startZ, double endX, double endY, double endZ, int duration, int seed
) implements CustomPacketPayload {
   public static final Type<FeldwayAvatarBeamPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "feldway_avatar_beam"));
   public static final StreamCodec<FriendlyByteBuf, FeldwayAvatarBeamPayload> STREAM_CODEC = CustomPacketPayload.codec(
      FeldwayAvatarBeamPayload::encode, FeldwayAvatarBeamPayload::new
   );

   public FeldwayAvatarBeamPayload(FriendlyByteBuf buf) {
      this(
         buf.readInt(),
         buf.readInt(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readInt(),
         buf.readInt()
      );
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.bossId);
      buf.writeInt(this.targetId);
      buf.writeDouble(this.startX);
      buf.writeDouble(this.startY);
      buf.writeDouble(this.startZ);
      buf.writeDouble(this.endX);
      buf.writeDouble(this.endY);
      buf.writeDouble(this.endZ);
      buf.writeInt(this.duration);
      buf.writeInt(this.seed);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(
            () -> FeldwayAvatarBeamClient.add(
               this.bossId, this.targetId, this.startX, this.startY, this.startZ, this.endX, this.endY, this.endZ, this.duration, this.seed
            )
         );
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
