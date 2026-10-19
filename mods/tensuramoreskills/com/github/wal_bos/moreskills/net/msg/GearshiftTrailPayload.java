package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.GearshiftBlueTrailClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record GearshiftTrailPayload(int entityId, int targetId, int duration, int gear) implements CustomPacketPayload {
   public static final Type<GearshiftTrailPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "gearshift_trail"));
   public static final StreamCodec<FriendlyByteBuf, GearshiftTrailPayload> STREAM_CODEC = CustomPacketPayload.codec(
      GearshiftTrailPayload::encode, GearshiftTrailPayload::new
   );

   public GearshiftTrailPayload(int entityId, int duration, int gear) {
      this(entityId, -1, duration, gear);
   }

   public GearshiftTrailPayload(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.writeInt(this.targetId);
      buf.writeInt(this.duration);
      buf.writeInt(this.gear);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> GearshiftBlueTrailClient.enable(this.entityId, this.targetId, this.duration, this.gear));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
